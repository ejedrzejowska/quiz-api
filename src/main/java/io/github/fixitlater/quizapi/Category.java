package io.github.fixitlater.quizapi;

import java.util.Arrays;
import java.util.List;

public enum Category {
    ANY,
    ENGLISH,
    MULTIPLICATION;
    public static List<Category> getPossibleValues(){
        List<Category> categories = Arrays.asList(Category.values());
        categories.remove(Category.ANY);
        return categories;
    }
}
