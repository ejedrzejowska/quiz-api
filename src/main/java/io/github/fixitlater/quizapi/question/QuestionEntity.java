package io.github.fixitlater.quizapi.question;

import io.github.fixitlater.quizapi.BaseEntity;
import io.github.fixitlater.quizapi.Category;
import io.github.fixitlater.quizapi.Language;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "question", indexes = {@Index(name = "idx_question_id_category", columnList = "category"),
        @Index(name = "idx_question_id_language", columnList = "language")})
@Builder

public class QuestionEntity extends BaseEntity {

    @Column(name = "question_body", length = 200)
    private String questionBody;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 45)
    private Category category;
    @Enumerated(EnumType.STRING)
    @Column(name = "language", length = 45)
    private Language language;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question", cascade = CascadeType.PERSIST)
    private List<AnswerEntity> answersEntities;
}
