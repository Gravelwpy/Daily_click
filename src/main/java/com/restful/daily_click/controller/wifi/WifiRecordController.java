package com.restful.daily_click.controller.wifi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.service.WifiRecordService;
import com.restful.daily_click.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("wifi_record")
@CrossOrigin
public class WifiRecordController {

    @Autowired
    WifiRecordService wifiRecordService;
    @Autowired
    TokenUtil tokenUtil;

    @UserLoginToken
    @RequestMapping(value = "get_record", method = RequestMethod.POST)
    public JsonResult getRecord(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String account = (String)tokenUtil.parseJwtToken(token).get("account");
        return new JsonResult(wifiRecordService.getWifiRecord(account));
    }

    @UserLoginToken
    @RequestMapping(value = "add_record", method = RequestMethod.POST)
    public JsonResult addRecord(HttpServletRequest httpServletRequest,
                         @RequestParam("loc_id") int loc_id,
                         @RequestParam("phone_info") String phone_info,
                         @RequestParam("wifi_info_list") String wifi_info_list,
                         @RequestParam("type") int type){
        // type 1 android 2 安卓小程序 3 苹果小程序
        System.out.println(wifi_info_list);
        try{
            JSONObject jsonObj = JSONObject.parseObject(wifi_info_list);
            System.out.println(jsonObj.toString());
            JSONArray wifi_test_list = jsonObj.getJSONArray("wifi_test_list");
            System.out.println(wifi_test_list.toString());
            wifiRecordService.addWifiRecord(loc_id,phone_info,wifi_test_list,type);
            return new JsonResult();
        } catch (Exception e){
            e.getMessage();
            return new JsonResult(-1,"system error");
        }

    }
}
