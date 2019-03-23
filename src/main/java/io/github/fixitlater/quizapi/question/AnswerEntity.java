package io.github.fixitlater.quizapi.question;

import io.github.fixitlater.quizapi.BaseEntity;
import io.github.fixitlater.quizapi.question.QuestionEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "answer", indexes = {@Index(name="idx_answer_question_id", columnList = "question_id")})
@Builder
public class AnswerEntity extends BaseEntity {

    @Column(name = "answer_body", length = 200)
    private String answerBody;
    @Column(name = "is_correct")
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;
}
