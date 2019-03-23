package io.github.fixitlater.quizapi.authentication;

import io.github.fixitlater.quizapi.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "user_key", indexes = {@Index(name = "idx_user_key_key", unique = true, columnList = "api_key")})

public class UserKey extends BaseEntity {

    @Column(length = 60, name = "api_key")
    private String apiKey;

    @Column(name = "api_user", length = 30)
    private String apiUser;

    @Column(name = "is_active")
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name="role", length = 15)
    private ApiRole role;

}
