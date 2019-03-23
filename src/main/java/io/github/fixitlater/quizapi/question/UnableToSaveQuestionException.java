package io.github.fixitlater.quizapi.question;


public class UnableToSaveQuestionException extends RuntimeException {
    public UnableToSaveQuestionException(String message) {
        super(message);
    }
}
