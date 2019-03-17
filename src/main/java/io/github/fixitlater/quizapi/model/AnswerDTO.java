package io.github.fixitlater.quizapi.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AnswerDTO {
    private int answerNo;
    private String answerBody;
    private boolean isCorrect;

    public AnswerDTO(String answerBody, boolean isCorrect) {
        this.answerNo = 0;
        this.answerBody = answerBody;
        this.isCorrect = isCorrect;
    }
}
