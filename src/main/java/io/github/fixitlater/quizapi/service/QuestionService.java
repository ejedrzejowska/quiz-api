package io.github.fixitlater.quizapi.service;

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

    public QuestionWithAnswersDTO getRandomQuestion(){
        return QuestionWithAnswersDTO.fromEntity(getRandomQuestionFromList(questionRepository.findAll()));
    }

    public Optional<QuestionWithAnswersDTO> findQuestionById(Long id) {
        return questionRepository.findById(id).map(QuestionWithAnswersDTO::fromEntity);
    }
    public List<QuestionWithAnswersDTO> getAllQuestions(){
        List<QuestionEntity> questionEntities = questionRepository.findAll();
        return questionEntities.stream().map(QuestionWithAnswersDTO::fromEntity).collect(Collectors.toList());
    }

    public List<QuestionWithAnswersDTO> getAllQuestionsFromCategory(String category){
        List<QuestionEntity> questionEntities = questionRepository.findByCategory(category);
        return questionEntities.stream().map(QuestionWithAnswersDTO::fromEntity).collect(Collectors.toList());
    }

    private QuestionEntity getRandomQuestionFromList(List<QuestionEntity> questionEntities) {
        long i = 0L;
        Long randomKey;
        Map<Long, QuestionEntity> questionEntityMap = new HashMap<>();
        for (QuestionEntity questionEntity : questionEntities) {
            questionEntityMap.put(i++, questionEntity);
        }
        randomKey = new RandomDataGenerator().nextLong(0L, i - 1L);
        return questionEntityMap.get(randomKey);
    }

    public QuestionWithAnswersDTO getRandomQuestionFromCategory(String category) {
        return QuestionWithAnswersDTO.fromEntity(getRandomQuestionFromList(questionRepository.findByCategory(category)));
    }
}