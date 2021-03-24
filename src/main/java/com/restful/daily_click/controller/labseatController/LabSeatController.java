package com.restful.daily_click.controller.labseatController;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.service.labseatseatService.LabSeatService;
import com.restful.daily_click.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("labseat")
@CrossOrigin
public class LabSeatController {
    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    LabSeatService labSeatService;

    @UserLoginToken
    @Transactional
    @RequestMapping(value = "/getLabListByTCode", method = RequestMethod.POST)
    public JsonResult getLabListByTCode(HttpServletRequest httpServletRequest){
//        HttpServletRequest httpServletRequest =
//        System.out.println(obj.getJSONArray("data").size());
//        if( obj.getJSONArray("data").size() > 0 ) {
//            return new JsonResult(obj);
//        } else {
            return new JsonResult(1007,"暂无实验室信息");
//        }

    }

    @UserLoginToken
    @Transactional
    @RequestMapping(value = "/getLabSeatDetial", method = RequestMethod.POST)
    public JsonResult getLabSeatDetial(int seatid){
        return labSeatService.getLabSeatDetial(seatid);
    }

    @UserLoginToken
    @Transactional
    @RequestMapping(value = "/changeSeatName", method = RequestMethod.POST)
    public JsonResult changeSeatName(int seatid, String name){
        return labSeatService.changeSeatName(seatid, name);
    }

}
