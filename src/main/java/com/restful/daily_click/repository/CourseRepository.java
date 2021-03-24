package com.restful.daily_click.repository;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.CoursesEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseRepository extends JpaRepository<CoursesEntity,Integer> {

    @Query(value = "select * from courses",nativeQuery = true)
    List<JSONObject> getAllCourse(Pageable pageable);

    @Query(value = "select count(*) from courses",nativeQuery = true)
    int getAllCourseNum();

    @Transactional
    @Modifying
    @Query(value = "UPDATE courses set course_name=?1, course_id=?2, course_type=?3, course_hour=?4, course_credit=?5 where id = ?6",nativeQuery = true)
    int editCourse(String course_name, String course_id, String course_type, String course_hour, String course_credit, String id);

    @Modifying
    @Query(value = "DELETE FROM courses WHERE id = ?1",nativeQuery = true)
    @Transactional
    int delCourse(String id);

    @Query(value = "select * FROM courses WHERE course_id = ?1",nativeQuery = true)
    CoursesEntity findCourseByCourseId(String course_id);


    @Transactional
    @Modifying
    @Query(value = "insert into courses(course_name,course_id,course_type,course_hour,course_credit) values(?1,?2,?3,?4,?5)",nativeQuery = true)
    int addCourse(String course_name, String course_id, String course_type, String course_hour, String course_credit);
}
