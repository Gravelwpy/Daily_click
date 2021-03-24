package com.restful.daily_click.controller.courseConroller;

import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.entity.AccountEntity;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.service.courseService.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("course")
@CrossOrigin
public class CourseController {

    @Autowired
    CourseService courseService;

    @UserLoginToken
    @RequestMapping(value = "/getAllCourse", method = RequestMethod.POST)
    public JsonResult getStuSchedule(@RequestParam("page") int page, @RequestParam("size") int size){
        return new JsonResult(courseService.getAllCourse(page, size));
    }

    @UserLoginToken
    @RequestMapping(value = "/editCourse", method = RequestMethod.POST)
    public JsonResult editTeacher(
            @RequestParam("id") String id,
            @RequestParam("course_name") String course_name,
            @RequestParam("course_id") String course_id,
            @RequestParam("course_type") String course_type,
            @RequestParam("course_hour") String course_hour,
            @RequestParam("course_credit") String course_credit){
        if(courseService.editCourse(id, course_name, course_id, course_type, course_hour, course_credit)){
            return new JsonResult();
        }
        return new JsonResult(1006,"修改失败");
    }

    @UserLoginToken
    @Transactional
    @RequestMapping(value = "/delCourseInf", method = RequestMethod.POST)
    public JsonResult delTeacherInf(@RequestParam("id") String id){
        if(courseService.delCourseInf(id)){
            return new JsonResult();
        }
        return new JsonResult(1006,"删除失败");
    }

    @UserLoginToken
    @RequestMapping(value = "/addCourse",method = RequestMethod.POST)
    public JsonResult addTeacher(@RequestParam String course_name,
                                 @RequestParam String course_id,
                                 @RequestParam String course_type,
                                 @RequestParam String course_hour,
                                 @RequestParam String course_credit){
        if(courseService.addCourse(course_name, course_id, course_type, course_hour, course_credit)){
            return new JsonResult();
        }
        return new JsonResult(1006,"此课程已存在");
    }

}
