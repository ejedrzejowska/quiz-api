package io.github.fixitlater.quizapi.question.converters;

import io.github.fixitlater.quizapi.question.AnswerDTO;
import io.github.fixitlater.quizapi.question.QuestionDTO;
import io.github.fixitlater.quizapi.question.QuestionEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionEntityToQuestionDTO implements DomainObjectConverter<QuestionEntity, QuestionDTO> {
    @Override
    public QuestionDTO convert(QuestionEntity source) {
        QuestionDTO questionDTO = QuestionDTO.builder()
                .questionId(source.getId())
                .questionBody(source.getQuestionBody())
                .category(source.getCategory())
                .language(source.getLanguage())
                .build();


         setAnswersWithNumbers(questionDTO,
                 source.getAnswersEntities()
                         .stream().map(a -> new AnswerDTO(a.getAnswerBody(),a.isCorrect())).collect(Collectors.toList()));

        return questionDTO;
    }

    private void setAnswersWithNumbers(QuestionDTO questionDTO, List<AnswerDTO> answers) {
        questionDTO.setAnswers(answers);
        int answerNo = 1;
        for (AnswerDTO answerDTO : questionDTO.getAnswers()
        ) {
            if (answerDTO.getAnswerNo() == 0)
                answerDTO.setAnswerNo(answerNo++);
        }

    }
}
