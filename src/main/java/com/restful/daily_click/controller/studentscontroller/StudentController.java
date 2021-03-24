package com.restful.daily_click.controller.studentscontroller;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.controller.AccountController;
import com.restful.daily_click.entity.AccountEntity;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.entity.StudentsEntity;
import com.restful.daily_click.repository.AccountRepository;
import com.restful.daily_click.service.AccountService;
import com.restful.daily_click.service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("students")
public class StudentController {
    @Autowired
    StudentsService studentsService;
    @Autowired
    AccountService accountService;

    @UserLoginToken
    //---------------增加学生信息记录------------------
    @RequestMapping(value = "/addStudent",method = RequestMethod.POST)
    public JsonResult addTeacher(//@RequestParam("id") int id,
                                 @RequestParam("stu_code") String stu_code,
                                 @RequestParam("email") String email,
                                 @RequestParam("name") String name,
                                 @RequestParam("sex") String sex,
                                 @RequestParam("grade") String grade,
                                 @RequestParam("tele") String tele){
        String password = "123";//默认密码
        StudentsEntity students = new StudentsEntity();
        //students.setId(id);
        students.setName(name);
        students.setStuCode(stu_code);
        students.setSex(sex);
        students.setGrade(grade);
        students.setTele(tele);
        students.setEmail(email);
        AccountEntity account = new AccountEntity();
        //account.setId(id);
        account.setAccount(stu_code);
        account.setPassword(password);
        try{
            if(studentsService.signup(students)==false||accountService.saveAccount(account,1)==false){
                return new JsonResult(1004,"此学号已被注册");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(-1,"system error");
        }
        return new JsonResult(0,"新增成功");
    }

    @UserLoginToken
    //-------------获取学生名单---------------
    @RequestMapping(value = "getlist",method = RequestMethod.POST)
    public JsonResult getstulist(@RequestParam("class_id") String class_id, @RequestParam("page") int page, @RequestParam("size") int size){
        return new JsonResult(studentsService.getStuListByClassId(class_id, page, size));
    }



    @UserLoginToken
    @RequestMapping(value = "/getAllMajority", method = RequestMethod.POST)
    public JsonResult getAllMajority(@RequestParam("page") int page, @RequestParam("size") int size){
        return new JsonResult(studentsService.getAllMajority(page, size));
    }

    @UserLoginToken
    @Transactional
    @RequestMapping(value = "/delClassInf", method = RequestMethod.POST)
    public JsonResult delClassInf(@RequestParam("id") String id){
        if(studentsService.delClassInf(id)){
            return new JsonResult();
        }
        return new JsonResult(1006,"删除失败");
    }

    @UserLoginToken
    //----------删除学生信息-----------
    @RequestMapping(value = "/delStudentInf", method = RequestMethod.POST)
    public JsonResult delStudentInf(@RequestParam("stu_code") String stu_code){
        if(studentsService.delStudentInf(stu_code)&&accountService.delAccount(stu_code)){
            return new JsonResult(1005,"删除成功！");
        }
        return new JsonResult(1006,"删除失败");
    }

    @UserLoginToken
    //--------------编辑学生信息----------------
    @RequestMapping(value = "/editStudentInf", method = RequestMethod.POST)
    public JsonResult editStudent(
            @RequestParam("id") String id,
            @RequestParam("stu_code") String stu_code,
            @RequestParam("email") String email,
            @RequestParam("name") String name,
            @RequestParam("sex") String sex,
            @RequestParam("grade") String grade,
            @RequestParam("tele") String tele){
        if(studentsService.editStudent(id, stu_code, email, name, sex, tele, grade)){
            return new JsonResult();
        }
        return new JsonResult(1006,"修改失败");
    }

    @UserLoginToken
    @RequestMapping(value = "/searchStudentByName", method = RequestMethod.POST)
    public JsonResult searchStudentByName(@RequestParam("name") String name){
        return new JsonResult(studentsService.searchStudentByName(name));
    }

}
