package io.github.fixitlater.quizapi.question;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
public class AnswerDTO {
    private int answerNo;
    @NotBlank
    private String answerBody;
    private boolean isCorrect;

    public AnswerDTO(String answerBody, boolean isCorrect) {
        this.answerNo = 0;
        this.answerBody = answerBody;
        this.isCorrect = isCorrect;
    }
}
