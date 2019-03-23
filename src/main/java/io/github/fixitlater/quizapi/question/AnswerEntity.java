package io.github.fixitlater.quizapi.question;

import io.github.fixitlater.quizapi.question.QuestionEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "answer")
@Builder
class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "answer_body", length = 200)
    private String answerBody;
    @Column(name = "is_correct")
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    static AnswerEntity fromDTO(AnswerDTO answerDTO){
        return AnswerEntity.builder()
                .answerBody(answerDTO.getAnswerBody())
                .isCorrect(answerDTO.isCorrect())
                .build();
    }

}
