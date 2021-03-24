package com.restful.daily_click.repository;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.QuestionsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionsEntity,Integer> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO `questions` (`desc`) VALUES (?1)",nativeQuery = true)
    int addQuestion(String desc);

    @Query(value = "select * from questions",nativeQuery = true)
    List<JSONObject> getAllQuestions(Pageable pageable);

    @Query(value = "select count(*) from questions",nativeQuery = true)
    int getAllQuestionNum();

    @Modifying
    @Query(value = "DELETE FROM questions WHERE id = ?1",nativeQuery = true)
    @Transactional
    int delQuestion(int id);
}
