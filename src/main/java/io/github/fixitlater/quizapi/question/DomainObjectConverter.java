package io.github.fixitlater.quizapi.question;


public interface DomainObjectConverter<S, T> {

    T convert(S source);


}
