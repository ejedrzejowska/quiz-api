package io.github.fixitlater.quizapi;

import io.github.fixitlater.quizapi.model.QuestionWithAnswersDTO;
import io.github.fixitlater.quizapi.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    private QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/randomQuestion")
    public ResponseEntity<QuestionWithAnswersDTO> gerRandomQuestion(){
        QuestionWithAnswersDTO questionWithAnswersDTO = questionService.returnRandomQuestion();
        return ResponseEntity.ok(questionWithAnswersDTO);
    }
}
