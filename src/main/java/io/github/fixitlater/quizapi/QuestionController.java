package io.github.fixitlater.quizapi;

import io.github.fixitlater.quizapi.model.QuestionWithAnswersDTO;
import io.github.fixitlater.quizapi.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/random")
    public ResponseEntity<QuestionWithAnswersDTO> getRandomQuestion() {
        QuestionWithAnswersDTO questionWithAnswersDTO = questionService.getRandomQuestion();
        return ResponseEntity.ok(questionWithAnswersDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuestionWithAnswersDTO>> getAllQuestions(){
        List<QuestionWithAnswersDTO> questionWithAnswersDTOs = questionService.getAllQuestions();
        return ResponseEntity.ok(questionWithAnswersDTOs);
    }

}
