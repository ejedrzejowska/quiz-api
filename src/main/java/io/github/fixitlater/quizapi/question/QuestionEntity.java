package io.github.fixitlater.quizapi.question;

import io.github.fixitlater.quizapi.Category;
import io.github.fixitlater.quizapi.Language;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question")
@Builder

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

    public static QuestionEntity fromDTO(QuestionWithAnswersDTO questionWithAnswersDTO) {
        return QuestionEntity.builder()
                .questionBody(questionWithAnswersDTO.getQuestionBody())
                .category(questionWithAnswersDTO.getCategory())
                .language(questionWithAnswersDTO.getLanguage())
                .answersEntities(questionWithAnswersDTO.getAnswers().stream().map(AnswerEntity::fromDTO).collect(Collectors.toList()))
                .build();
    }
}
