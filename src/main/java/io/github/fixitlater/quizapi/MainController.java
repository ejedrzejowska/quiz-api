package io.github.fixitlater.quizapi;

import io.github.fixitlater.quizapi.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class MainController {

    private QuestionService questionService;

    @Autowired
    public MainController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategoryList() {
        return ResponseEntity.ok(Arrays.asList(Category.values()));
    }

    @GetMapping("/languages")
    public ResponseEntity<List<Language>> getLanguageList() {
        return ResponseEntity.ok(Arrays.asList(Language.values()));
    }

}
