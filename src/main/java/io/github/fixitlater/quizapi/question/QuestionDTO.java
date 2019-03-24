package io.github.fixitlater.quizapi.question;

import io.github.fixitlater.quizapi.Category;
import io.github.fixitlater.quizapi.Language;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Builder
public class QuestionDTO {

    private Long questionId;
    @NotBlank
    @Size(min=10)
    private String questionBody;
    @NotNull
    private List<AnswerDTO> answers;
    @NotNull
    private Category category;
    @NotNull
    private Language language;
}
