package io.github.fixitlater.quizapi.question;

import io.github.fixitlater.quizapi.Category;
import io.github.fixitlater.quizapi.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
                                    @RequestParam(value="category", required = false) String category,
                                    @RequestParam(value="lang", required = false) String language) {
        QuestionWithAnswersDTO questionWithAnswersDTO;
        Map<String, String[]> paramMap = request.getParameterMap();
        Language languageEnum;
        Category categoryEnum;
        String categoryNotNull;
        if(!paramMap.containsKey("category")) category = "ANY";
        try {
            categoryEnum = Category.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!paramMap.containsKey("lang")) language = "ANY";
        try {
            languageEnum = Language.valueOf(language.toUpperCase());
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            questionWithAnswersDTO = questionService.getRandomQuestionByCategoryAndOrLanguage(categoryEnum, languageEnum);
        } catch (NoSuchElementException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(questionWithAnswersDTO);
    }

    @GetMapping("/randoms")
    public ResponseEntity<List<QuestionWithAnswersDTO>> getRandomQuestion(ServletRequest request,
                                                                    @RequestParam(value="category", required = false) String category,
                                                                    @RequestParam(value="lang", required = false) String language,
                                                                    @RequestParam(value="number") int number) {
        List<QuestionWithAnswersDTO> questionsWithAnswersDTO;
        Map<String, String[]> paramMap = request.getParameterMap();
        Language languageEnum;
        Category categoryEnum;
        String categoryNotNull;
        if(!paramMap.containsKey("category")) category = "ANY";
        try {
            categoryEnum = Category.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!paramMap.containsKey("lang")) language = "ANY";
        try {
            languageEnum = Language.valueOf(language.toUpperCase());
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            questionsWithAnswersDTO = questionService.getMultipleRandomQuestionsByCategoryAndOrLanguage(categoryEnum, languageEnum, number);
        } catch (NoSuchElementException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(questionsWithAnswersDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuestionWithAnswersDTO>> getAllQuestions(@RequestHeader(name = "X-userKey", required = false) String userKey,ServletRequest request,
                                    @RequestParam(value="category", required = false) String category,
                                    @RequestParam(value="lang", required = false) String language){
        Map<String, String[]> paramMap = request.getParameterMap();
        Language languageEnum;
        Category categoryEnum;
        String categoryNotNull;
        if(!paramMap.containsKey("category")) category = "ANY";
        try {
            categoryEnum = Category.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!paramMap.containsKey("lang")) language = "ANY";
        try {
            languageEnum = Language.valueOf(language.toUpperCase());
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<QuestionWithAnswersDTO> questionWithAnswersDTOs
                = questionService.getAllQuestionsByCategoryAndOrLanguage(categoryEnum,languageEnum);
        if(questionWithAnswersDTOs.size()==0)return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(questionWithAnswersDTOs);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<Optional<QuestionWithAnswersDTO>> findQuestion(@PathVariable(value="questionId") String questionId){
        long id;
        try {
            id = Long.parseLong(questionId);
        } catch (NumberFormatException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<QuestionWithAnswersDTO> questionWithAnswersDTO = questionService.findQuestionById(id);
        if (!questionWithAnswersDTO.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(questionWithAnswersDTO);
    }



    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addQuestion(@RequestBody @Valid QuestionWithAnswersDTO question){
        if (question != null){
            if(!questionService.addOne(question)){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
