package io.github.fixitlater.quizapi.question;

import org.springframework.stereotype.Component;

@Component
public class QuestionEntityToQuestionWithAnswersDTO implements DomainObjectConverter<QuestionEntity, QuestionWithAnswersDTO> {
    @Override
    public QuestionWithAnswersDTO convert(QuestionEntity source) {
        return null;
    }
}
