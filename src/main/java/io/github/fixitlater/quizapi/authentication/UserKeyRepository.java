package io.github.fixitlater.quizapi.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserKeyRepository extends JpaRepository<UserKey, Long> {

    Optional<UserKey> findFirstByApiKey(String apiKey);
}
