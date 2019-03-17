package io.github.fixitlater.quizapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question")

public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "question_body", length = 200)
    private String questionBody;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 45)
    private Category category;
    @Enumerated(EnumType.STRING)
    @Column(name = "language", length = 45)
    private Language language;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
    private List<AnswerEntity> answersEntities;
}
