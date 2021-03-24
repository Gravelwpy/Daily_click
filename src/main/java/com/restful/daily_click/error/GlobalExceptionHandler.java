package com.restful.daily_click.error;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.JsonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public Object handleException(HttpServletRequest request, Exception e) {
        BusinessException bse = (BusinessException) e;
//        System.out.println(bse.getErrMsg());
//        String msg = e.getMessage();
//        String msg = bse.getErrMsg();
        if (bse.getErrMsg() == null || bse.getErrMsg().equals("")) {
            bse.setErrMsg("未知错误");
        }
        return new JsonResult(bse.getErrCode(), bse.getErrMsg());

//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("message", msg);
//        return jsonObject;
    }
}
