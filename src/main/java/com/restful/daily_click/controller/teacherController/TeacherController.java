package com.restful.daily_click.controller.teacherController;

import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.entity.AccountEntity;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.service.AccountService;
import com.restful.daily_click.service.StudentsService;
import com.restful.daily_click.service.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    TeachersService teachersService;
    @Autowired
    AccountService accountService;

    @UserLoginToken
    //---------------添加新的教师信息-----------------
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public JsonResult addTeacher(//@RequestParam int tea_id,//主键
                                 @RequestParam String tea_code,
                                 @RequestParam String tea_name,
                                 @RequestParam String tea_sex,
                                 @RequestParam String tea_tele,
                                 @RequestParam String tea_email){
        String password = "123";//默认密码123
        AccountEntity account = new AccountEntity();
        //account.setId(tea_id);
        account.setAccount(tea_code);
        account.setPassword(password);
        if(teachersService.addTeacher(tea_code, tea_name, tea_sex, tea_tele, tea_email)&&accountService.saveAccount(account,2)){

            return new JsonResult(0,"操作成功！");
        }
        return new JsonResult(1006,"此工号已被注册/操作失败！");
    }


    @UserLoginToken
    //---------------获取现有教师信息---------------------
    @RequestMapping(value = "/getAllTeacher", method = RequestMethod.POST)
    public JsonResult getStuSchedule(@RequestParam("page") int page,
                                     @RequestParam("size") int size){
        return new JsonResult(teachersService.getAllTeacher(page,size));
    }

    @UserLoginToken
    @Transactional
    //--------------------删除教师信息表和account表-----------------
    @RequestMapping(value = "/delTeacherInf", method = RequestMethod.POST)
    public JsonResult delTeacherInf(@RequestParam("account") String account){
        if(teachersService.delTeacher(account)&&accountService.delAccount(account)){
            return new JsonResult(0,"删除成功");
        }
        return new JsonResult(1006,"删除失败");
    }

    /**
     *  @param    tea_id: this.id,
     *           tea_code: this.ruleForm.tea_code,
     *           tea_email: this.ruleForm.tea_email,
     *           tea_name: this.ruleForm.tea_name,
     *           tea_sex: this.ruleForm.tea_sex,
     *           tea_tele: this.ruleForm.tea_tele
     * @return
     */
    @UserLoginToken
    //------------修改教师信息----------------
    @RequestMapping(value = "/editTeacher", method = RequestMethod.POST)
    public JsonResult editTeacher(
            @RequestParam("tea_id") String tea_id,
            @RequestParam("tea_code") String tea_code,
            @RequestParam("tea_email") String tea_email,
            @RequestParam("tea_name") String tea_name,
            @RequestParam("tea_sex") String tea_sex,
            @RequestParam("tea_tele") String tea_tele){
        if(teachersService.editTeacher(tea_id, tea_code, tea_email, tea_name, tea_sex, tea_tele)){
            return new JsonResult(0,"修改成功！");
        }
        return new JsonResult(1006,"修改失败");
    }
}
