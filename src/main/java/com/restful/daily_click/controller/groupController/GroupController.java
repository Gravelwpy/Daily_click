package com.restful.daily_click.controller.groupController;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.service.groupService.GroupService;
import com.restful.daily_click.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("group")
@CrossOrigin
public class GroupController {
    @Autowired
    GroupService groupService;
    @Autowired
    TokenUtil tokenUtil;

    @UserLoginToken
    @RequestMapping(value = "/addGroup",method = RequestMethod.POST)
    public JsonResult addGroup(@RequestParam("groupname") String groupname,
                               @RequestParam("groupiintroduce") String groupiintroduce,
                               @RequestParam("tcode") String tcode){
        return groupService.addGroup(groupname, groupiintroduce, tcode);
    }
    @UserLoginToken
    @RequestMapping(value = "/addItemNumber",method = RequestMethod.POST)
    public JsonResult addItemNumber(@RequestParam("stucode") String stucode,
                               @RequestParam("groupid") int groupid){
        return groupService.addItemNumber(stucode, groupid);
    }
    @UserLoginToken
    @RequestMapping(value = "/getAllGroupByPage", method = RequestMethod.POST)
    public JsonResult getAllGroupByPage(@RequestParam("page") int page, @RequestParam("size") int size){
        return new JsonResult(groupService.getAllGroupByPage(page, size));
    }

    @UserLoginToken
    @RequestMapping(value = "/getGroupNumberByPage", method = RequestMethod.POST)
    public JsonResult getGroupNumberByPage(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("labid") int labid){
        return new JsonResult(groupService.getGroupNumberByPage(page, size, labid));
    }

    @UserLoginToken
    @RequestMapping(value = "/editGroup", method = RequestMethod.POST)
    public JsonResult editGroup(
            @RequestParam("id") String id,
            @RequestParam("groupname") String groupname,
            @RequestParam("tcode") String tcode,
            @RequestParam("groupiintroduce") String groupiintroduce ){
        if(groupService.editGroup(id, groupname, tcode, groupiintroduce)){
            return new JsonResult();
        }
        return new JsonResult(1006,"修改失败");
    }

    @UserLoginToken
    @Transactional
    @RequestMapping(value = "/delGroup", method = RequestMethod.POST)
    public JsonResult delGroup(@RequestParam("id") String id){
        if(groupService.delGroup(id)){
            return new JsonResult();
        }
        return new JsonResult(1006,"删除失败");
    }

    @UserLoginToken
    @Transactional
    @RequestMapping(value = "/delLabNumber", method = RequestMethod.POST)
    public JsonResult delLabNumber(@RequestParam("id") String id){
        if(groupService.delLabNumber(id)){
            return new JsonResult();
        }
        return new JsonResult(1006,"删除失败");
    }

    @UserLoginToken
    @GetMapping("miniPrograGetTeacherGroupList")
    public JsonResult miniPrograGetTeacherGroupList(HttpServletRequest httpServletRequest){
        List<JSONObject> list = null;
        try{
            String token = httpServletRequest.getHeader("token");
            String account = (String)tokenUtil.parseJwtToken(token).get("account");
            list = groupService.miniPrograGetTeacherGroupList(account);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(-1,"system error");
        }
        return new JsonResult(list);
    }
}
