package io.github.fixitlater.quizapi.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity,Long> {

     List<QuestionEntity> findAll();

     Optional<QuestionEntity> findById(Long id);

     @Query(value="select * from question where category like ?1 and language like ?2", nativeQuery = true)
     List<QuestionEntity> findByCategoryAndOrLanguage(String category, String language);

     @Query(value="select * from question where category like ?1 and language like ?2 order by rand() limit 1", nativeQuery = true)
     QuestionEntity findRandomByCategoryAndOrLanguage(String category, String language);

     @Query(value="select * from question where category like ?1 and language like ?2 order by rand() limit ?3", nativeQuery = true)
     List<QuestionEntity> findMultipleRandomByCategoryAndOrLanguage(String category, String language, int limit);


}
