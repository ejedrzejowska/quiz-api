package io.github.fixitlater.quizapi.repository;

import io.github.fixitlater.quizapi.entity.Language;
import io.github.fixitlater.quizapi.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity,Long> {

     List<QuestionEntity> findByIdIsNotNull();

     List<QuestionEntity> findAll();

     List<QuestionEntity> findByCategory(String category);

     List<QuestionEntity> findByLanguage(String language);

     Optional<QuestionEntity> findById(Long id);

     @Query(value="select * from question where category like ?1 and language like ?2", nativeQuery = true)
     List<QuestionEntity> findByCategoryAndOrLanguage(String category, String language);

}
