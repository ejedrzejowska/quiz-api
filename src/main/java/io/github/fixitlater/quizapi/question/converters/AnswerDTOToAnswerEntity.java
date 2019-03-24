package io.github.fixitlater.quizapi.question.converters;

import io.github.fixitlater.quizapi.question.AnswerDTO;
import io.github.fixitlater.quizapi.question.AnswerEntity;
import org.springframework.stereotype.Component;

@Component
public class AnswerDTOToAnswerEntity implements DomainObjectConverter<AnswerDTO, AnswerEntity> {
    @Override
    public AnswerEntity convert(AnswerDTO source) {
        return AnswerEntity.builder()
                .answerBody(source.getAnswerBody())
                .isCorrect(source.isCorrect())
                .build();
    }
}
