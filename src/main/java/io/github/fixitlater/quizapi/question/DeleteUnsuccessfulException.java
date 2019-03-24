package io.github.fixitlater.quizapi.question;

public class DeleteUnsuccessfulException extends RuntimeException {
    public DeleteUnsuccessfulException(String message) {
        super(message);
    }
}
