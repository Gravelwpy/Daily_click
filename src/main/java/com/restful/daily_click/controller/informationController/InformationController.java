package com.restful.daily_click.controller.informationController;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.dto.InformationDto;
import com.restful.daily_click.entity.InformationEntity;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.service.InformationService;
import com.restful.daily_click.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;


@CrossOrigin
@RequestMapping("/information")
@RestController
public class InformationController {
    @Autowired
    InformationService informationService;
    @Autowired
    TokenUtil tokenUtil;

    @UserLoginToken
    //-----获取课表信息--------
    @RequestMapping(value = "schedule", method = RequestMethod.GET)
    public JsonResult getStuSchedule(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String code = (String)tokenUtil.parseJwtToken(token).get("account");
        return new JsonResult(informationService.getStuSchedule(code));
    }

    @UserLoginToken
    @RequestMapping(value = "scheduleGrade", method = RequestMethod.POST)
    public JsonResult getStuScheduleByGrade(@RequestParam("grade") String grade){
        return new JsonResult(informationService.getStuScheduleByGrade(grade));
    }

    @UserLoginToken
    //---------添加课程----------
    @RequestMapping(value = "addSchedule", method = RequestMethod.POST)
    public JsonResult addSchedule(
            @RequestParam("course_id") Integer course_id,
            @RequestParam("room_id") String room_id,
            @RequestParam("class_id") String class_id,
            @RequestParam("tea_code") String tea_code,
            @RequestParam("i_week") Integer i_week,
            @RequestParam("i_start") Integer i_start,
            @RequestParam("i_duration") Integer i_duration
    ){
        return new JsonResult(informationService.addSchedule(course_id, room_id, class_id, tea_code, i_week, i_start, i_duration));
    }

}