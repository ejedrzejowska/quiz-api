package io.github.fixitlater.quizapi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "answer_body", length = 200)
    private String answerBody;
    @Column(name = "is_correct")
    private boolean isCorrect;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
