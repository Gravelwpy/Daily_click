package com.restful.daily_click.controller;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.annotation.PassToken;
import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.entity.AccountEntity;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.entity.StudentsEntity;
import com.restful.daily_click.entity.SystemAdminEntity;
import com.restful.daily_click.service.AccountService;
import com.restful.daily_click.service.StudentsService;
import com.restful.daily_click.service.SystemAdminService;
import com.restful.daily_click.service.TeachersService;
import com.restful.daily_click.utils.Md5Util;
import com.restful.daily_click.utils.TokenUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;


@RestController
@RequestMapping("account")
@CrossOrigin
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    StudentsService studentsService;
    @Autowired
    TeachersService teachersService;
    @Autowired
    SystemAdminService  systemAdminService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    /*
     *  2020.2.4
     *  未完成
     */
    @RequestMapping(value = "/otplogin",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject otpLogin(@RequestParam("telphone")String telphone) {
        //生成otp验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);

        //使用session保存
        httpServletRequest.getSession().setAttribute(telphone, otpCode);

        // 通过第三方短信发送

        return null;
    }
    //-----------------登陆-----------------
    @RequestMapping(value = "/login",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public JsonResult account_login(@Validated AccountEntity account){
        AccountEntity accountForBase = accountService.findByAccount(account.getAccount());
        if(null == accountForBase){
            return new JsonResult(1001,"登录失败，用户不存在");
        }
//        if( 2 != account.getType() ){
//            return new JsonResult(1002,"无登录权限！");
//        }
        else{
            AccountEntity account_res = accountService.findByAccount(account.getAccount());

            if(!Md5Util.md5Verifier(account.getPassword(),account_res.getPassword(),account.getAccount()))
            {
                return new JsonResult(1002,"登录失败，密码错误！");
            }
            else{
                String token = tokenUtil.getToken(account);

                JSONObject obj = new JSONObject();
                if(account_res.getType()==1)//学生类
                {
                    obj.put("account",studentsService.getStudentByStuCode(account.getAccount()));
                }
                else{//老师类或管理员
                    obj.put("account",teachersService.getTeacherByTeaCode(account.getAccount()));
                }


                obj.put("token",token);
                obj.put("type",accountForBase.getType());
                return new JsonResult(obj);//给前端返回一个token
            }
        }
    }

    @PassToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }

    @RequestMapping(value = "/parsejwt",method = RequestMethod.POST)
    public Claims parsejwt(@RequestBody JSONObject token) {
        String tokenstr = token.getString("x-token");
        Claims ret = tokenUtil.parseJwtToken(tokenstr);
        return ret;
    }

    //---APP(小程序）端注册页面---------
    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    public JsonResult account_signup(
            //@RequestParam int id,
            @RequestParam String stu_code,
            @RequestParam String password,
            @RequestParam String stu_name,
            @RequestParam String sex,
            @RequestParam String grade,
            @RequestParam String tele,
            @RequestParam(value = "email", required = false, defaultValue = "") String email
            ){
//        if (stu_code==null||password==null||stu_name==null||sex==null||grade==null||tele==null){
//            return new JsonResult(1005,"参数不完整");
//        }
        StudentsEntity students = new StudentsEntity();
        //students.setId(id);
        students.setName(stu_name);
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
        return new JsonResult();
    }

    @UserLoginToken
    @RequestMapping(value = "/adminlogin",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public JsonResult adminLogin(@RequestBody SystemAdminEntity data){
//        System.out.println(data.toString());
        try{
            if(systemAdminService.validateAdmin(data)){
                String token = tokenUtil.getAdminToken(data.getAdminName());
                JSONObject obj = new JSONObject();
                obj.put("token",token);
                obj.put("type",3);
                return new JsonResult(obj);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
//        System.out.println(adminname+" "+adminpass);
        return new JsonResult(1006,"管理员登录失败");
    }
}
