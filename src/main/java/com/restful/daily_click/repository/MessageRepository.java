package com.restful.daily_click.repository;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.MessageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ZhangKaijie
 * @Date: 2019/7/28 11:23
 * @Description:
 */
public interface MessageRepository extends JpaRepository<MessageEntity,Integer> {
    @Query(value = "select msg_title,msg_content,teachers.name,icon_url,insert_time " +
            "from message,students,class_info,teachers " +
            "where message.class_id = students.grade " +
            "and class_info.class_id = students.grade " +
            "and teachers.tea_code = message.tea_code " +
            "and students.stu_code = ?1 " +
            "and (message.msg_title like %?2% or message.msg_content like %?3%)" +
            "order by insert_time desc ",nativeQuery = true)
    ArrayList<JSONObject> findByMsgTitleLikeOrMsgContentLikeByOrderByInsertTimeAtDesc(String stu_code, String title, String content, Pageable pageable);

    @Query(value = "select * from message WHERE tea_code = ?1",nativeQuery = true)
    List<JSONObject> getAllMessageByPage(Pageable pageable, String originator);

    @Query(value = "select count(*) from message WHERE tea_code = ?1",nativeQuery = true)
    int getAllMessageByPageNum(String originator);

    @Modifying
    @Query(value = "DELETE FROM message WHERE id = ?1",nativeQuery = true)
    @Transactional
    int delectMessage(int id);
}
