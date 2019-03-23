package io.github.fixitlater.quizapi.question;

import io.github.fixitlater.quizapi.Category;
import io.github.fixitlater.quizapi.Language;
import lombok.AllArgsConstructor;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final DomainObjectConverter<QuestionEntity, QuestionWithAnswersDTO> questionConveerer;


    public Optional<QuestionWithAnswersDTO> findQuestionById(Long id) {
        return questionRepository.findById(id).map(QuestionWithAnswersDTO::fromEntity);
    }

    public QuestionWithAnswersDTO getRandomQuestionByCategoryAndOrLanguage(Category category, Language language) throws NoSuchElementException {
        String languageString = ((language == Language.ANY) ? "%" : language.name());
        String categoryString = ((category == Category.ANY) ? "%" : category.name());
        try {
            return QuestionWithAnswersDTO.fromEntity(questionRepository.findRandomByCategoryAndOrLanguage(categoryString, languageString));
        } catch (NoSuchElementException e){
            e.printStackTrace();
            throw e;
        }
    }

    public List<QuestionWithAnswersDTO> getMultipleRandomQuestionsByCategoryAndOrLanguage(Category category, Language language, int numberOfQuestions) throws NoSuchElementException {
        String languageString = ((language == Language.ANY) ? "%" : language.name());
        String categoryString = ((category == Category.ANY) ? "%" : category.name());
        try {
            return questionRepository.findMultipleRandomByCategoryAndOrLanguage(categoryString, languageString, numberOfQuestions).stream().map(QuestionWithAnswersDTO::fromEntity).collect(Collectors.toList());
        } catch (NoSuchElementException e){
            e.printStackTrace();
            throw e;
        }
    }

    public List<QuestionWithAnswersDTO> getAllQuestionsByCategoryAndOrLanguage(Category category, Language language) {
        String languageString = ((language == Language.ANY) ? "%" : language.name());
        String categoryString = ((category == Category.ANY) ? "%" : category.name());
        return questionRepository.findByCategoryAndOrLanguage(categoryString, languageString)
                .stream().map(QuestionWithAnswersDTO::fromEntity).collect(Collectors.toList());
    }

    public boolean addOne(QuestionWithAnswersDTO question) {
        QuestionEntity questionEntity;
        if(question != null){
            questionEntity = QuestionEntity.fromDTO(question);
            try {
                questionRepository.save(questionEntity);
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}