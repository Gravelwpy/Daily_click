package com.restful.daily_click.controller;

import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.entity.AccountEntity;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("token")
@CrossOrigin
public class TokenController {
    @Autowired
    TokenUtil tokenUtil;

    @UserLoginToken
    @RequestMapping(value = "isdue",method = RequestMethod.GET)
    public JsonResult isdue(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        boolean res = tokenUtil.tokenDurationVerifier(token);
        if(res==false){
            return new JsonResult(1005,"token已过期");
        }else{
            return new JsonResult();
        }
    }


    @RequestMapping(value = "getToken", method = RequestMethod.POST)
    public String getToken(@RequestParam String account, @RequestParam int type){
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccount(account);
        accountEntity.setType(type);
        return tokenUtil.getToken(accountEntity);
    }
}
