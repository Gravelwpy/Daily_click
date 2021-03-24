package com.restful.daily_click.controller.classController;

import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.service.classService.ClassRoomService;
import com.restful.daily_click.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("classroom")
@CrossOrigin
public class classRoomController {
    @Autowired
    ClassRoomService classRoomService;
    @Autowired
    TokenUtil tokenUtil;

    /**
     *
     * @param page 页数
     * @param size 每页条数
     * @return ClassRoomListByPageNumber 获取教室数据列表
     */
    @UserLoginToken
    @RequestMapping(value = "/getClassRoomListByPageNumber", method = RequestMethod.POST)
    public JsonResult getStuSchedule(@RequestParam("page") int page, @RequestParam("size") int size){
        return new JsonResult(classRoomService.getClassRoomListPage(page, size));
    }

    @UserLoginToken
    @RequestMapping(value = "/getAllClassRoom", method = RequestMethod.POST)
    public JsonResult getAllClassRoom(){
        return new JsonResult(classRoomService.getAllClassRoom());
    }

    @UserLoginToken
    @RequestMapping(value = "/miniproTeacherGetClassRoomList", method = RequestMethod.POST)
    public JsonResult miniproTeacherGetClassRoomList(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String account = (String)tokenUtil.parseJwtToken(token).get("account");
        return new JsonResult(classRoomService.miniproTeacherGetClassRoomList(account));
    }

    @UserLoginToken
    @RequestMapping(value = "/delClassRoom", method = RequestMethod.POST)
    public JsonResult getStuSchedule(@RequestParam("id") int id){
        return new JsonResult(classRoomService.delRoom(id));
    }

}
