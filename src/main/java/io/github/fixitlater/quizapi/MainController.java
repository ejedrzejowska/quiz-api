package io.github.fixitlater.quizapi;

import io.github.fixitlater.quizapi.authentication.AuthenticationService;
import io.github.fixitlater.quizapi.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    private CategoryAndLanguageService categoryAndLanguageService;

    private AuthenticationService authenticationService;

    @Autowired
    public MainController(QuestionService questionService, CategoryAndLanguageService categoryAndLanguageService, AuthenticationService authenticationService) {
        this.categoryAndLanguageService = categoryAndLanguageService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategoryList(@RequestHeader(name = "X-userKey", required = false) String userKey) {
        if (!authenticationService.canRead(userKey)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(categoryAndLanguageService.getCategories());
    }

    @GetMapping("/languages")
    public ResponseEntity<List<Language>> getLanguageList(@RequestHeader(name = "X-userKey", required = false) String userKey) {
        if (!authenticationService.canRead(userKey)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(categoryAndLanguageService.getLanguages());
    }

}
