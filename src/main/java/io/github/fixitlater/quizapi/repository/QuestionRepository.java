package io.github.fixitlater.quizapi.repository;

import io.github.fixitlater.quizapi.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity,Long> {

     List<QuestionEntity> findByIdIsNotNull();

     List<QuestionEntity> findAll();
}
