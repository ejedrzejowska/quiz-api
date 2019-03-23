package io.github.fixitlater.quizapi.question;

import io.github.fixitlater.quizapi.Category;
import io.github.fixitlater.quizapi.Language;
import io.github.fixitlater.quizapi.question.converters.DomainObjectConverter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final DomainObjectConverter<QuestionEntity, QuestionDTO> questionConverter;

    private final DomainObjectConverter<QuestionDTO, QuestionEntity> questionDTOconverter;


    public Optional<QuestionDTO> findQuestionById(Long id) {
        return questionRepository.findById(id).map(questionConverter::convert);
    }

    public QuestionDTO getRandomQuestionByCategoryAndOrLanguage(Category category, Language language) throws NoSuchElementException {
        String languageString = ((language == Language.ANY) ? "%" : language.name());
        String categoryString = ((category == Category.ANY) ? "%" : category.name());
        try {
            return questionConverter.convert(questionRepository.findRandomByCategoryAndOrLanguage(categoryString, languageString));
        } catch (NoSuchElementException e){
            e.printStackTrace();
            throw e;
        }
    }

    public List<QuestionDTO> getMultipleRandomQuestionsByCategoryAndOrLanguage(Category category, Language language, int numberOfQuestions) throws NoSuchElementException {
        String languageString = ((language == Language.ANY) ? "%" : language.name());
        String categoryString = ((category == Category.ANY) ? "%" : category.name());
        try {
            return questionRepository.findMultipleRandomByCategoryAndOrLanguage(categoryString, languageString, numberOfQuestions).stream().map(questionConverter::convert).collect(Collectors.toList());
        } catch (NoSuchElementException e){
            e.printStackTrace();
            throw e;
        }
    }

    public List<QuestionDTO> getAllQuestionsByCategoryAndOrLanguage(Category category, Language language) {
        String languageString = ((language == Language.ANY) ? "%" : language.name());
        String categoryString = ((category == Category.ANY) ? "%" : category.name());
        return questionRepository.findByCategoryAndOrLanguage(categoryString, languageString)
                .stream().map(questionConverter::convert).collect(Collectors.toList());
    }

    public boolean addOne(QuestionDTO question) {
        QuestionEntity questionEntity;
        if(question != null){
            questionEntity = questionDTOconverter.convert(question);
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