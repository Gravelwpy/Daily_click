package com.restful.daily_click.repository;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.GroupNumberEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupNumberRepository extends JpaRepository<GroupNumberEntity,Integer> {

    @Query(value = "select group_number.id,students.id as stu_id,students.stu_code,students.name,students.sex,students.grade,students.tele,students.email " +
            "from group_number,students " +
            "where group_id = ?1 AND students.stu_code = group_number.stu_code",nativeQuery = true)
    List<JSONObject> getGroupNumberByPage(Pageable pageable, int labid);

    @Query(value = "select count(*) from group_number,students " +
            "where group_id = ?1 AND students.stu_code = group_number.stu_code",nativeQuery = true)
    int getGroupNumberByPageNum(int labid);

}
