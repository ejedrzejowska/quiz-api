package io.github.fixitlater.quizapi.question;

import io.github.fixitlater.quizapi.Category;
import io.github.fixitlater.quizapi.Language;
import io.github.fixitlater.quizapi.question.converters.DomainObjectConverter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final DomainObjectConverter<QuestionEntity, QuestionDTO> questionConverter;

    private final DomainObjectConverter<QuestionDTO, QuestionEntity> questionDTOConverter;

    private final DomainObjectConverter<AnswerDTO, AnswerEntity> answerDTOConverter;


    public Optional<QuestionDTO> findQuestionById(Long id) {
        return questionRepository.findById(id).map(questionConverter::convert);
    }

    public QuestionDTO getRandomQuestion(Category category, Language language) throws NoSuchElementException {
        String languageString = ((language == Language.ANY) ? "%" : language.name());
        String categoryString = ((category == Category.ANY) ? "%" : category.name());

        QuestionEntity maybeEntity = questionRepository.findRandomByCategoryAndOrLanguage(categoryString, languageString);
        if (maybeEntity == null) {
            throw new NoSuchElementException("Sorry, we don't have question following category : " + category + " and lang: " + language);
        }
        return questionConverter.convert(maybeEntity);

    }

    public List<QuestionDTO> getMultipleRandomQuestions(Category category, Language language, int numberOfQuestions) throws NoSuchElementException {
        String languageString = ((language == Language.ANY) ? "%" : language.name());
        String categoryString = ((category == Category.ANY) ? "%" : category.name());
        List<QuestionDTO> questions = questionRepository.findMultipleRandomByCategoryAndOrLanguage(categoryString, languageString, numberOfQuestions).stream().map(questionConverter::convert).collect(Collectors.toList());
        if (questions.size() < numberOfQuestions) {
            throw new NotEnoughQuestionsException("Database do not have required number of questions meeting given criteria");
        }
        return questions;
    }

    public List<QuestionDTO> getAllQuestions(Category category, Language language) {
        String languageString = ((language == Language.ANY) ? "%" : language.name());
        String categoryString = ((category == Category.ANY) ? "%" : category.name());
        return questionRepository.findByCategoryAndOrLanguage(categoryString, languageString)
                .stream().map(questionConverter::convert).collect(Collectors.toList());
    }

    public void addOne(QuestionDTO question) throws RuntimeException {
        QuestionEntity questionEntity;
        if (question != null) {
            questionEntity = questionDTOConverter.convert(question);
            questionRepository.save(questionEntity);
        }
    }

    public void deleteOne(Long questionId) {
        questionRepository.deleteById(questionId);
        if (findQuestionById(questionId).isPresent()) throw new DeleteUnsuccessfulException("Question was not deleted");
    }

    @Transactional()
    public void updateOne(Long questionId, QuestionDTO questionDTO) {
        QuestionEntity questionEntity = questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchElementException("Unable to modify question. Unable to find question with given ID"));
        questionEntity.setQuestionBody(questionDTO.getQuestionBody());
        questionEntity.setCategory(questionDTO.getCategory());
        questionEntity.setLanguage(questionDTO.getLanguage());

        if (questionDTO.getAnswers().size() != questionEntity.getAnswersEntities().size()) {
            throw new UnableToSaveQuestionException("Number of answers cannot be changed during modification.");
        }

        for (int i = 0; i < questionEntity.getAnswersEntities().size(); i++) {
            questionEntity.getAnswersEntities().get(i).setAnswerBody(questionDTO.getAnswers().get(i).getAnswerBody());
            questionEntity.getAnswersEntities().get(i).setCorrect(questionDTO.getAnswers().get(i).isCorrect());
        }

        questionRepository.save(questionEntity);
    }
}