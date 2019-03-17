package io.github.fixitlater.quizapi.model;

import io.github.fixitlater.quizapi.model.AnswerDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionWithAnswersDTO {

    private Long questionId;

    private String questionBody;
    private List<AnswerDTO> answers;



}
