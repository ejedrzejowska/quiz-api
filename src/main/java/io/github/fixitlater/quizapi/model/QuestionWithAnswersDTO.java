package io.github.fixitlater.quizapi.model;

import io.github.fixitlater.quizapi.entity.QuestionEntity;
import io.github.fixitlater.quizapi.model.AnswerDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class QuestionWithAnswersDTO {

    private Long questionId;

    private String questionBody;
    private List<AnswerDTO> answers;

    private QuestionWithAnswersDTO setAnswersWithNumbers(List<AnswerDTO> answers) {
        this.answers = answers;
        int answerNo = 1;
        for (AnswerDTO answerDTO :this.answers
             ) {
            if (answerDTO.getAnswerNo() == 0)
                answerDTO.setAnswerNo(answerNo++);
        }
        return this;
    }

    public static QuestionWithAnswersDTO fromEntity(QuestionEntity questionEntity) {
        return QuestionWithAnswersDTO.builder()
                .questionId(questionEntity.getId())
                .questionBody(questionEntity.getQuestionBody())
                .build()
                .setAnswersWithNumbers(questionEntity.getAnswersEntities()
                    .stream().map(a -> new AnswerDTO(a.getAnswerBody(),a.isCorrect())).collect(Collectors.toList()));

    }
}
