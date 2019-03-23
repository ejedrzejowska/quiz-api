package io.github.fixitlater.quizapi.question.converters;

import io.github.fixitlater.quizapi.question.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionDTOToQuestionEntity implements DomainObjectConverter<QuestionDTO, QuestionEntity> {

    private final DomainObjectConverter<AnswerDTO, AnswerEntity> answerDTOconverter;

    @Override
    public QuestionEntity convert(QuestionDTO source) {
        return QuestionEntity.builder()
                .questionBody(source.getQuestionBody())
                .category(source.getCategory())
                .language(source.getLanguage())
                .answersEntities(source.getAnswers().stream().map(answerDTOconverter::convert).collect(Collectors.toList()))
                .build();
    }
}
