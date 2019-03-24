package io.github.fixitlater.quizapi.question;

import io.github.fixitlater.quizapi.Category;
import io.github.fixitlater.quizapi.Language;
import io.github.fixitlater.quizapi.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private QuestionService questionService;

    private AuthenticationService authenticationService;

    @Autowired
    public QuestionController(QuestionService questionService, AuthenticationService authenticationService) {
        this.questionService = questionService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/random")
    public ResponseEntity<QuestionDTO> getRandomQuestion(@RequestParam(value="category", required = false, defaultValue = "ANY") Category category,
                                                         @RequestParam(value="lang", required = false, defaultValue = "ANY") Language language,
                                                         @RequestHeader(name = "X-userKey", required = false) String userKey){
        if(!authenticationService.canRead(userKey)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        QuestionDTO randomQuestion = questionService.getRandomQuestion(category, language);
        System.out.println(UUID.randomUUID());
        return ResponseEntity.ok(randomQuestion);
    }

    @GetMapping("/randoms")
    public ResponseEntity<List<QuestionDTO>> getRandomQuestions(ServletRequest request,
                                                                @RequestParam(value="category", required = false, defaultValue = "ANY") Category category,
                                                                @RequestParam(value="lang", required = false, defaultValue = "ANY") Language language,
                                                                @RequestParam(value="number") int number,
                                                                @RequestHeader(name = "X-userKey", required = false) String userKey){
        if(!authenticationService.canRead(userKey)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        List<QuestionDTO> questionsWithAnswersDTO;
        questionsWithAnswersDTO = questionService.getMultipleRandomQuestions(category, language, number);
        return ResponseEntity.ok(questionsWithAnswersDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuestionDTO>> getAllQuestions(@RequestParam(value="category", required = false, defaultValue = "ANY") Category category,
                                                             @RequestParam(value="lang", required = false, defaultValue = "ANY") Language language,
                                                             @RequestHeader(name = "X-userKey", required = false) String userKey){
        if(!authenticationService.canRead(userKey)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        List<QuestionDTO> questionDTOs = questionService.getAllQuestions(category,language);
        if(questionDTOs.size()==0){
            throw  new NotEnoughQuestionsException("Database does not have any question meeting given criteria");
        }
        return ResponseEntity.ok(questionDTOs);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDTO> findQuestion(@PathVariable(value="questionId") Long questionId,
                                                    @RequestHeader(name = "X-userKey", required = false) String userKey){
        if(!authenticationService.canRead(userKey)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Optional<QuestionDTO> questionWithAnswersDTO = questionService.findQuestionById(questionId);
        if (!questionWithAnswersDTO.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(questionWithAnswersDTO.get());
    }



    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addQuestion(@RequestBody @Valid QuestionDTO question,
                                                  BindingResult bindingResult,
                                                  @RequestHeader(name = "X-userKey", required = false) String userKey){
        if(!authenticationService.canWrite(userKey)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if (bindingResult.hasErrors()) throw new UnableToSaveQuestionException("Unable to save question. Validation failed");
        if (question != null){
            questionService.addOne(question);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/delete/{questionId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteQuestion(@PathVariable(value="questionId") Long questionId,
                                           @RequestHeader(name = "X-userKey", required = false) String userKey) {
        if (!authenticationService.canWrite(userKey)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        questionService.deleteOne(questionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{questionId}", method = RequestMethod.PUT)
    public ResponseEntity updateQuestion(@PathVariable(value="questionId") Long questionId,
                                           @RequestBody @Valid QuestionDTO question,
                                           BindingResult bindingResult,
                                           @RequestHeader(name = "X-userKey", required = false) String userKey) {
        if (!authenticationService.canWrite(userKey)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if (bindingResult.hasErrors()) throw new UnableToSaveQuestionException("Unable to save question. Validation failed");
        questionService.updateOne(questionId, question);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
