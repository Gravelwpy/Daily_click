package com.restful.daily_click.controller;

import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.error.BusinessException;
import com.restful.daily_click.error.EmBusinessError;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class Test {

//    @RequestMapping(value = "test", method = RequestMethod.GET)
    public JsonResult getWifiRecordDetail() throws BusinessException {
        throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
    }
}
