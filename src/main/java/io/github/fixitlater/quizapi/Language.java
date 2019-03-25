package io.github.fixitlater.quizapi;

import java.util.Arrays;
import java.util.List;

public enum Language {
    POLISH,
    ENGLISH,
    ANY;
    public List<Language> getPossibleValues(){
        List<Language> languages = Arrays.asList(Language.values());
        languages.remove(Language.ANY);
        return languages;
    }
}
