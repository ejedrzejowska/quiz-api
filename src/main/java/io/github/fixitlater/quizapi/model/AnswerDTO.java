package io.github.fixitlater.quizapi.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AnswerDTO {
    private String answerBody;
    private boolean isCorrect;
}
