package io.github.fixitlater.quizapi.service;

import io.github.fixitlater.quizapi.entity.Category;
import io.github.fixitlater.quizapi.entity.Language;
import io.github.fixitlater.quizapi.entity.QuestionEntity;
import io.github.fixitlater.quizapi.model.AnswerDTO;
import io.github.fixitlater.quizapi.model.QuestionWithAnswersDTO;
import io.github.fixitlater.quizapi.repository.QuestionRepository;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.stream.Collectors;

@Service
public class QuestionService {

    private QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Optional<QuestionWithAnswersDTO> findQuestionById(Long id) {
        return questionRepository.findById(id).map(QuestionWithAnswersDTO::fromEntity);
    }

    private QuestionEntity getRandomQuestionFromList(List<QuestionEntity> questionEntities) throws NoSuchElementException {
        if (questionEntities.size() == 0) throw new NoSuchElementException("No question can be found for given criteria");
        if (questionEntities.size() == 1) return questionEntities.get(0);
        long i = 0L;
        Long randomKey;
        Map<Long, QuestionEntity> questionEntityMap = new HashMap<>();
        for (QuestionEntity questionEntity : questionEntities) {
            questionEntityMap.put(i++, questionEntity);
        }
        randomKey = new RandomDataGenerator().nextLong(0L, i - 1L);
        return questionEntityMap.get(randomKey);
    }

    public QuestionWithAnswersDTO getRandomQuestionByCategoryAndOrLanguage(Category category, Language language) throws NoSuchElementException {
        String languageString = ((language == Language.ANY) ? "%" : language.name());
        String categoryString = ((category == Category.ANY) ? "%" : category.name());
        try {
            return QuestionWithAnswersDTO.fromEntity(getRandomQuestionFromList(questionRepository.findByCategoryAndOrLanguage(categoryString, languageString)));
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

}