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
    @Column(name = "category", length = 45)
    private String category;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
    private List<AnswerEntity> answersEntities;
}
