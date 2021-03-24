package com.restful.daily_click.controller.classController;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.service.classService.ClassService;
import com.restful.daily_click.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("class")
@CrossOrigin
public class ClassController{
    @Autowired
    ClassService classService;

    @Autowired
    TokenUtil tokenUtil;

    //---------------获取班级信息------------
    @GetMapping("getclasslist")
    public JsonResult getClassList(){
        List<JSONObject> list=null;
        try{
            list = classService.getClassList();
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(-1,"system error");
        }
        return new JsonResult(list);
    }

    @UserLoginToken
    @GetMapping("creatNewSignGetclassList")
    public JsonResult CreatNewSignGetclassList(){
        List<JSONObject> list=null;
        try{
            list = classService.creatNewSignGetclassList();
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(-1,"system error");
        }
        return new JsonResult(list);
    }
    @UserLoginToken
    //------微信小程序获取教师所在班级列表----------
    @GetMapping("miniPrograGetTeacherTeachClass")
    public JsonResult miniPrograGetTeacherTeachClass(HttpServletRequest httpServletRequest){
        List<JSONObject> list=null;
        try{
            String token = httpServletRequest.getHeader("token");
            String account = (String)tokenUtil.parseJwtToken(token).get("account");
            list = classService.miniPrograGetTeacherTeachClass(account);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(-1,"system error");
        }
        return new JsonResult(list);
    }

    @UserLoginToken
    @RequestMapping(value = "/getAllClass", method = RequestMethod.POST)
    public JsonResult getAllClassByMajorId(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("majorid") int majorid){
        return new JsonResult(classService.getAllClassByMajorId(page, size, majorid));
    }

    @UserLoginToken
    @Transactional
    @RequestMapping(value = "/delClass", method = RequestMethod.POST)
    public JsonResult delClass(@RequestParam("id") String id){
        System.out.println(id);
        if(classService.delClass(id)){
            return new JsonResult();
        }
        return new JsonResult(1006,"删除失败");
    }

    @UserLoginToken
    @RequestMapping(value = "/editClass", method = RequestMethod.POST)
    public JsonResult editClass(
            @RequestParam("id") String id,
            @RequestParam("class_id") String class_id,
            @RequestParam("class_name") String class_name,
            @RequestParam("class_year") String class_year){
        if(classService.editClass(id, class_id, class_name, class_year)){
            return new JsonResult();
        }
        return new JsonResult(1006,"修改失败");
    }

    @UserLoginToken
    @RequestMapping(value = "/addClass",method = RequestMethod.POST)
    public JsonResult addClass(@RequestParam String class_id,
                                 @RequestParam String class_name,
                                 @RequestParam String class_year,
                                 @RequestParam String majority_id){
        if(classService.addClass(class_id, class_name, class_year, majority_id)){
            return new JsonResult();
        }
        return new JsonResult(1006,"此课程已存在");
    }

}
