package com.restful.daily_click.repository;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.TeachersEntity;
import com.restful.daily_click.entity.TeachersEntityPK;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface TeachersRepository extends JpaRepository<TeachersEntity,Integer> {
    TeachersEntity findByTeaCode(String teacode);

    @Transactional
    @Modifying
    //--------------插入一条教师信息------------
    @Query(value = "insert into teachers(tea_code,name,sex,tele,email) values(?1,?2,?3,?4,?5)",nativeQuery = true)
    int addTeacher(
                   //int tea_id,
                   String tea_code,
                   String tea_name,
                   String tea_sex,
                   String tea_tele,
                   String tea_email);


    //---------------查询教师信息------------
    @Query(value = "select teachers.id, tea_code, name, sex, tele, email, account.type from teachers, account where teachers.tea_code = account.account",nativeQuery = true)
    List<JSONObject> getAllTeacher(Pageable pageable);

    //---------获取教师数量---------
    @Query(value = "select count(*) from teachers",nativeQuery = true)
    int getAllTeacherNum();

    @Transactional
    @Modifying
    //------------------删除教师信息-----------
    @Query(value = "DELETE from teachers where tea_code = ?1",nativeQuery = true)
    int delTeacher(String account);


    @Transactional
    @Modifying
    //-----------------修改教师信息(根据id号主键）-------------------
    @Query(value = "UPDATE teachers set tea_code=?1, email=?2, name=?3, sex=?4, tele=?5 where id = ?6",nativeQuery = true)
    int editTeacher(String tea_code, String tea_email, String tea_name, String tea_sex, String tea_tele, String tea_id);



    @Query(value = "select name from teachers where teachers.tea_code = ?1",nativeQuery = true)
    String getTnameByCode(Integer sign_item_originator);
}
