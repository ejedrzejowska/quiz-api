package io.github.fixitlater.quizapi.question;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
class AnswerDTO {
    private int answerNo;
    private String answerBody;
    private boolean isCorrect;

    AnswerDTO(String answerBody, boolean isCorrect) {
        this.answerNo = 0;
        this.answerBody = answerBody;
        this.isCorrect = isCorrect;
    }
}
