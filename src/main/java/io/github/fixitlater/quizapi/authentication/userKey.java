package io.github.fixitlater.quizapi.authentication;

import io.github.fixitlater.quizapi.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
public class userKey extends BaseEntity {

    @Column(length = 60)

    private String key;

    private String user;

}
