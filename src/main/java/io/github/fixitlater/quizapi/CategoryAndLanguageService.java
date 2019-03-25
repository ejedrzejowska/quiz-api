package io.github.fixitlater.quizapi;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryAndLanguageService {
    private List<Category> categories;
    private List<Language> languages;

    public CategoryAndLanguageService() {
        this.categories = Arrays.stream(Category.values()).filter(category -> !category.equals(Category.ANY)).collect(Collectors.toList());
        this.languages = Arrays.stream(Language.values()).filter(language -> !language.equals(Language.ANY)).collect(Collectors.toList());
    }

    public List<Category> getCategories(){
        return categories;
    }

    public List<Language> getLanguages(){
        return languages;
    }
}
