package io.github.fixitlater.quizapi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "question_body", length = 200)
    private String questionBody;
    @Column(name = "category", length = 45)
    private String category;
}
