package io.github.fixitlater.quizapi.question.converters;

import io.github.fixitlater.quizapi.question.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionDTOToQuestionEntity implements DomainObjectConverter<QuestionDTO, QuestionEntity> {

    private final DomainObjectConverter<AnswerDTO, AnswerEntity> answerDTOConverter;

    @Override
    public QuestionEntity convert(QuestionDTO source) {
        QuestionEntity questionEntity = QuestionEntity.builder()
                .questionBody(source.getQuestionBody())
                .category(source.getCategory())
                .answersEntities(new ArrayList<>())
                .language(source.getLanguage())
                .build();
        List<AnswerDTO> answers = source.getAnswers();
        for (AnswerDTO answer : answers) {
            AnswerEntity answerEntity = answerDTOConverter.convert(answer);
            answerEntity.setQuestion(questionEntity);
            questionEntity.getAnswersEntities().add(answerEntity);
        }
        return questionEntity;
    }
}