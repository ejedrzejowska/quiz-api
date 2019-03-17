package io.github.fixitlater.quizapi;

import io.github.fixitlater.quizapi.model.QuestionWithAnswersDTO;
import io.github.fixitlater.quizapi.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/random")
    public ResponseEntity<QuestionWithAnswersDTO> getRandomQuestion(ServletRequest request,
                                                                    @RequestParam(value="category", required = false) String category) {
        QuestionWithAnswersDTO questionWithAnswersDTO;
        Map<String, String[]> paramMap = request.getParameterMap();
        if(!paramMap.containsKey("category")) {
            questionWithAnswersDTO = questionService.getRandomQuestion();
        } else {
            questionWithAnswersDTO = questionService.getRandomQuestionFromCategory(category);
        }

        return ResponseEntity.ok(questionWithAnswersDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuestionWithAnswersDTO>> getAllQuestions(ServletRequest request,
                                                                        @RequestParam(value="category", required = false) String category){
        Map<String, String[]> paramMap = request.getParameterMap();
        if(!paramMap.containsKey("category")) {
            List<QuestionWithAnswersDTO> questionWithAnswersDTOs = questionService.getAllQuestions();
            return ResponseEntity.ok(questionWithAnswersDTOs);
        } else {
            List<QuestionWithAnswersDTO> questionWithAnswersDTOs = questionService.getAllQuestionsFromCategory(category);
            return ResponseEntity.ok(questionWithAnswersDTOs);
        }
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<Optional<QuestionWithAnswersDTO>> findQuestion(@PathVariable(value="questionId") String questionId){
        long id;
        try {
            id = Long.parseLong(questionId);
        } catch (NumberFormatException e){
            return null;
        }
        Optional<QuestionWithAnswersDTO> questionWithAnswersDTO = questionService.findQuestionById(id);
        return ResponseEntity.ok(questionWithAnswersDTO);
    }



}
