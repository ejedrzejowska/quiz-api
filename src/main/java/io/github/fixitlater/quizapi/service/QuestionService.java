package io.github.fixitlater.quizapi.service;

import io.github.fixitlater.quizapi.entity.QuestionEntity;
import io.github.fixitlater.quizapi.model.AnswerDTO;
import io.github.fixitlater.quizapi.model.QuestionWithAnswersDTO;
import io.github.fixitlater.quizapi.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public QuestionWithAnswersDTO getRandomQuestion(){
        List<QuestionEntity> questionEntities = questionRepository.findByIdIsNotNull();
        QuestionEntity questionEntity = questionEntities.get(1);
        return QuestionWithAnswersDTO.fromEntity(questionEntity);
    }

    public List<QuestionWithAnswersDTO> getAllQuestions(){
        List<QuestionEntity> questionEntities = questionRepository.findAll();
        return questionEntities.stream().map(QuestionWithAnswersDTO::fromEntity).collect(Collectors.toList());
    }
}