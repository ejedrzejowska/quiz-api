package io.github.fixitlater.quizapi.question;

import io.github.fixitlater.quizapi.Category;
import io.github.fixitlater.quizapi.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<QuestionDTO> getRandomQuestion(@RequestParam(value="category", required = false, defaultValue = "ANY") Category category,
                                                         @RequestParam(value="lang", required = false, defaultValue = "ANY") Language language) {

        QuestionDTO radonQuestion = questionService.getRandomQuestion(category, language);
        return ResponseEntity.ok(radonQuestion);
    }

    private Category setCategoryEnum(String category, Category categoryEnum, Map<String, String[]> paramMap) throws IllegalArgumentException    {
        if(paramMap.containsKey("category")) {
            try {
                categoryEnum = Category.valueOf(category.toUpperCase());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return categoryEnum;
    }


    private Language setLanguageEnum(String language, Language languageEnum, Map<String, String[]> paramMap) throws IllegalArgumentException {
        if(paramMap.containsKey("language")) {
            try {
                languageEnum = Language.valueOf(language.toUpperCase());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return languageEnum;
    }

    @GetMapping("/randoms")
    public ResponseEntity<List<QuestionDTO>> getRandomQuestions(ServletRequest request,
                                                                @RequestParam(value="category", required = false) String category,
                                                                @RequestParam(value="lang", required = false) String language,
                                                                @RequestParam(value="number") int number) {
        List<QuestionDTO> questionsWithAnswersDTO;
        Map<String, String[]> paramMap = request.getParameterMap();
        Language languageEnum = Language.ANY;
        Category categoryEnum = Category.ANY;
        try {
            categoryEnum = setCategoryEnum(category, categoryEnum, paramMap);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            languageEnum = setLanguageEnum(language, languageEnum, paramMap);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            questionsWithAnswersDTO = questionService.getMultipleRandomQuestionsByCategoryAndOrLanguage(categoryEnum, languageEnum, number);
        } catch (NoSuchElementException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (questionsWithAnswersDTO.size() < number) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(questionsWithAnswersDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuestionDTO>> getAllQuestions(@RequestHeader(name = "X-userKey", required = false) String userKey,
                                                             ServletRequest request,
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
        List<QuestionDTO> questionDTOs
                = questionService.getAllQuestionsByCategoryAndOrLanguage(categoryEnum,languageEnum);
        if(questionDTOs.size()==0)return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(questionDTOs);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<Optional<QuestionDTO>> findQuestion(@PathVariable(value="questionId") String questionId){
        long id;
        try {
            id = Long.parseLong(questionId);
        } catch (NumberFormatException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<QuestionDTO> questionWithAnswersDTO = questionService.findQuestionById(id);
        if (!questionWithAnswersDTO.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(questionWithAnswersDTO);
    }



    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addQuestion(@RequestBody @Valid QuestionDTO question,
                                                  BindingResult bindingResult){
        if (bindingResult.hasErrors()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (question != null){
            if(!questionService.addOne(question)){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
