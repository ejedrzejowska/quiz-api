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

    @GetMapping("/randoms")
    public ResponseEntity<List<QuestionDTO>> getRandomQuestions(ServletRequest request,
                                                                @RequestParam(value="category", required = false, defaultValue = "ANY") Category category,
                                                                @RequestParam(value="lang", required = false, defaultValue = "ANY") Language language,
                                                                @RequestParam(value="number") int number) {
        List<QuestionDTO> questionsWithAnswersDTO;
        questionsWithAnswersDTO = questionService.getMultipleRandomQuestions(category, language, number);
        return ResponseEntity.ok(questionsWithAnswersDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuestionDTO>> getAllQuestions(@RequestHeader(name = "X-userKey", required = false) String userKey,
                                                             @RequestParam(value="category", required = false) Category category,
                                                             @RequestParam(value="lang", required = false) Language language){

        List<QuestionDTO> questionDTOs = questionService.getAllQuestions(category,language);
        if(questionDTOs.size()==0){
            throw  new NotEnoughQuestionsException("Database does not have any question meeting given criteria");
        }
        return ResponseEntity.ok(questionDTOs);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDTO> findQuestion(@PathVariable(value="questionId") Long questionId){
        Optional<QuestionDTO> questionWithAnswersDTO = questionService.findQuestionById(questionId);
        if (!questionWithAnswersDTO.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(questionWithAnswersDTO.get());
    }



    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addQuestion(@RequestBody @Valid QuestionDTO question,
                                                  BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new UnableToSaveQuestionException("Unable to save question. Validation failed");
        if (question != null){
            questionService.addOne(question);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
