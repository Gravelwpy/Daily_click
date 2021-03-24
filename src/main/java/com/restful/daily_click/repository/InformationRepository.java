package com.restful.daily_click.repository;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.dto.InformationDto;
import com.restful.daily_click.entity.InformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;


public interface InformationRepository extends JpaRepository<InformationEntity,Integer> {
    //---------根据学生账号获取课程信息--------
    @Query(value = "select course_name,teachers.name tea_name,i_week,i_start,i_duration,room_id,information.course_id " +
            "from teachers,information,courses,students,class_info " +
            "where teachers.tea_code = information.tea_code "+
            "and information.course_id = courses.course_id "+
            "and information.class_id = class_info.class_id "+
            "and class_info.class_id = students.grade "+
            "and students.stu_code = ?1 "+
            "ORDER BY i_week",
            nativeQuery = true)
    ArrayList<JSONObject> getStuScheduleByStuCode(String code);

    @Query(value = "select course_name,teachers.name tea_name,i_week,i_start,i_duration,room_id,information.course_id " +
            "from teachers,information,courses,class_info " +
            "where teachers.tea_code = information.tea_code "+
            "and information.course_id = courses.course_id "+
            "and information.class_id = class_info.class_id "+
            "and class_info.class_id = ?1 "+
            "ORDER BY i_week",
            nativeQuery = true)
    ArrayList<JSONObject> getStuScheduleByGrade(String grade);
}
