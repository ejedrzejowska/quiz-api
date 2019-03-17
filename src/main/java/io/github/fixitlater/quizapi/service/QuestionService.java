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

    public QuestionWithAnswersDTO returnRandomQuestion(){
        List<QuestionEntity> questionEntities = questionRepository.findByIdIsNotNull();
        QuestionEntity questionEntity = questionEntities.get(1);
        QuestionWithAnswersDTO questionWithAnswersDTO = new QuestionWithAnswersDTO();

        questionWithAnswersDTO.setQuestionId(questionEntity.getId());
        questionWithAnswersDTO.setQuestionBody(questionEntity.getQuestionBody());
        questionWithAnswersDTO.setAnswers(questionEntity.getAnswersEntities()
                .stream().map(a -> new AnswerDTO(a.getAnswerBody(),a.isCorrect())).collect(Collectors.toList()));

        return questionWithAnswersDTO;

    }
}

