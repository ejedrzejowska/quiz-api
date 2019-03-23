package io.github.fixitlater.quizapi.question;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
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
