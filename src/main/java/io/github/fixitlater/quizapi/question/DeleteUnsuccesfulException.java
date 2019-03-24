package io.github.fixitlater.quizapi.question;

public class DeleteUnsuccesfulException extends RuntimeException {
    public DeleteUnsuccesfulException(String message) {
        super(message);
    }
}
