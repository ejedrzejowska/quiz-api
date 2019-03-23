package io.github.fixitlater.quizapi.question;

import java.util.NoSuchElementException;

public class NotEnoughQuestionsException extends NoSuchElementException {
    public NotEnoughQuestionsException(String s) {
        super(s);
    }
}
