package com.restful.daily_click.controller.majorController;

import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.service.majorService.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("major")
@CrossOrigin
public class MajorController {
    @Autowired
    MajorService majorService;

    @UserLoginToken
    @RequestMapping(value = "/getAllMajor", method = RequestMethod.POST)
    public JsonResult getAllMajor(@RequestParam("page") int page, @RequestParam("size") int size){
        return new JsonResult(majorService.getAllMajor(page, size));
    }

    @UserLoginToken
    @RequestMapping(value = "/editMajor", method = RequestMethod.POST)
    public JsonResult editTeacher(
            @RequestParam("id") String id,
            @RequestParam("majority_name") String majority_name){
        if(majorService.editMajor(id, majority_name)){
            return new JsonResult();
        }
        return new JsonResult(1006,"修改失败");
    }

    @UserLoginToken
    @Transactional
    @RequestMapping(value = "/delMajoy", method = RequestMethod.POST)
    public JsonResult delMajoy(@RequestParam("id") String id){
        if(majorService.delMajoy(id)){
            return new JsonResult();
        }
        return new JsonResult(1006,"删除失败");
    }

    @UserLoginToken
    @RequestMapping(value = "/addMajoy",method = RequestMethod.POST)
    public JsonResult addMajoy(@RequestParam String majority_name){
        if(majorService.addMajoy(majority_name)){
            return new JsonResult();
        }
        return new JsonResult(1006,"此课程已存在");
    }
}
