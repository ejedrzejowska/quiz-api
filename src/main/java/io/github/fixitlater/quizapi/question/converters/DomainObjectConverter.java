package io.github.fixitlater.quizapi.question.converters;


public interface DomainObjectConverter<S, T> {

    T convert(S source);
}
