package com.restful.daily_click.interceptor;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.restful.daily_click.annotation.PassToken;
import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.entity.AccountEntity;
import com.restful.daily_click.service.AccountService;
import com.restful.daily_click.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    AccountService accountService;
    @Autowired
    TokenUtil tokenUtil;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object)
            throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 account
                String account_str;
                try {
                    account_str = (String) tokenUtil.parseJwtToken(token).get("account");
                } catch (JWTDecodeException j) {
                    j.printStackTrace();
                    throw new RuntimeException("401");
                }
                AccountEntity account = accountService.findByAccount(account_str);
                if (account == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                if(!tokenUtil.tokenQualifier(token,account)){
                    throw new RuntimeException("非法的X-token");
                }
                if(!tokenUtil.tokenDurationVerifier(token)){
                    throw new RuntimeException("token已过期，请重新登陆");
                }
                // 验证 token
                try {
                    tokenUtil.jwtVerifier(token);
                } catch (JWTVerificationException e) {
                    e.printStackTrace();
                    throw new RuntimeException("401");
                }
                return true;
            }
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
