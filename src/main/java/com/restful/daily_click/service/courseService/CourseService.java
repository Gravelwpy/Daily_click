package com.restful.daily_click.service.courseService;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.CoursesEntity;
import com.restful.daily_click.entity.TeachersEntity;
import com.restful.daily_click.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Transactional
    public JSONObject getAllCourse(int page, int size) {
        Pageable pageable = new PageRequest(page,size);
        List<JSONObject> json = courseRepository.getAllCourse(pageable);
        int number = courseRepository.getAllCourseNum();
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);

        return temp;
    }

    @Transactional
    public boolean editCourse(String id, String course_name, String course_id, String course_type, String course_hour, String course_credit) {
        int flag = courseRepository.editCourse(course_name, course_id, course_type, course_hour, course_credit, id);
        if(flag == 1){
            return true;
        }
        return false;
    }

    @Transactional
    public boolean delCourseInf(String id) {
        int flag = courseRepository.delCourse(id);
        if(flag == 1){
            return true;
        }
        return false;
    }

    @Transactional
    public boolean addCourse(String course_name, String course_id, String course_type, String course_hour, String course_credit) {
        if(isUniqueCourse(course_id)){
            try{
                courseRepository.addCourse(course_name,course_id,course_type,course_hour,course_credit);
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        else{

        }
        return true;
    }
    public boolean isUniqueCourse(String course_id){
        CoursesEntity obj = courseRepository.findCourseByCourseId(course_id);
        if(null == obj){
            return true;
        }
        return false;
    }
}
