package com.restful.daily_click.controller.messageController;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.service.messageService.MessageService;
import com.restful.daily_click.service.signService.SignService;
import com.restful.daily_click.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * @Author: ZhangKaijie
 * @Date: 2019/7/28 11:22
 * @Description:
 */
@RestController
@CrossOrigin
@RequestMapping("message")
public class MessageController {
    @Autowired
    MessageService messageService;
    @Autowired
    TokenUtil tokenUtil;
    @UserLoginToken
    @RequestMapping(value = "/getmsglist",method = RequestMethod.POST)
    public JsonResult getMsgList(@RequestParam("searchKey") String searchKey,@RequestParam("page") int page,HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String code = (String)tokenUtil.parseJwtToken(token).get("account");
        ArrayList<JSONObject> list = null;
        try{
            list = messageService.getMessageList(searchKey,code,page);
        } catch (Exception e){
            e.printStackTrace();
            return new JsonResult(-1,"system error");
        }
        return new JsonResult(list);
    }
    @UserLoginToken
    @RequestMapping(value = "/addMessage",method = RequestMethod.POST)
    public JsonResult addMessage(@RequestParam("msgtitle") String msgtitle,
                                 @RequestParam("msgcontent") String msgcontent,
                                 @RequestParam("classid") String classid,
                                 HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String teccode = (String)tokenUtil.parseJwtToken(token).get("account");
        String iconurl = "../assets/logo.png";
        return new JsonResult(messageService.addMessage(msgtitle, msgcontent, teccode, classid, iconurl));
    }

    @UserLoginToken
    @RequestMapping(value = "/getAllMessageByPage", method = RequestMethod.POST)
    public JsonResult getAllMessageByPage(@RequestParam("page") int page, @RequestParam("size") int size, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String originator = (String)tokenUtil.parseJwtToken(token).get("account");
        return new JsonResult(messageService.getAllMessageByPage(page - 1, size, originator));
    }

    @UserLoginToken
    @RequestMapping(value = "/delectMessage", method = RequestMethod.POST)
    public JsonResult delectMessage(@RequestParam("id") int id){
        return new JsonResult(messageService.delectMessage(id));
    }

}
