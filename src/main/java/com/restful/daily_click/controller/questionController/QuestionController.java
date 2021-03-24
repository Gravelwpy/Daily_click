package com.restful.daily_click.controller.questionController;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.service.questionService.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("question")
@CrossOrigin
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @UserLoginToken
    //--------提交问题-----------
    @RequestMapping(value = "/addquestion",method = RequestMethod.POST)
    public JsonResult getMsgList(@RequestParam("desc") String desc){
        //System.out.print(desc);
        return questionService.addQuestion(desc);
    }

    @UserLoginToken
    @RequestMapping(value = "/getAllQuestions", method = RequestMethod.POST)
    public JsonResult getStuSchedule(@RequestParam("page") int page, @RequestParam("size") int size){
        return new JsonResult(questionService.getAllQuestions(page, size));
    }

    @UserLoginToken
    @RequestMapping(value = "/delQuestion", method = RequestMethod.POST)
    public JsonResult getStuSchedule(@RequestParam("id") int id){
        return new JsonResult(questionService.delQuestion(id));
    }

}
