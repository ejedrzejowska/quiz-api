package io.github.fixitlater.quizapi.question;

import io.github.fixitlater.quizapi.BaseEntity;
import io.github.fixitlater.quizapi.question.QuestionEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "answer")
@Builder
public class AnswerEntity extends BaseEntity {

    @Column(name = "answer_body", length = 200)
    private String answerBody;
    @Column(name = "is_correct")
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id", insertable = true)
    private QuestionEntity question;
}
