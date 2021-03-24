package com.restful.daily_click.controller.wifi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.repository.WifiRepository;
import com.restful.daily_click.service.WifiService;
import com.restful.daily_click.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("wifi")
@CrossOrigin
public class WifiController {
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    WifiService wifiService;
    @Autowired
    WifiRepository wifiRepository;

    @UserLoginToken
    @RequestMapping(value = "get_wifi_info", method = RequestMethod.POST)
    public JsonResult getWifiList(@RequestParam("json_str") String json_str){
        JSONObject jsonObj = JSONObject.parseObject(json_str);
        JSONArray wifi_rec_id_list = jsonObj.getJSONArray("wifi_rec_id_list");
        return new JsonResult(wifiService.getWifiList(wifi_rec_id_list));
    }

    //保存wifi数据
    @UserLoginToken
    @RequestMapping(value = "save_wifi_detail", method = RequestMethod.POST)
    public JsonResult saveWifiDetail(@RequestParam("wifi_info_list") String wifi_info_list){
        JSONObject jsonObj = JSONObject.parseObject(wifi_info_list);
        JSONArray wifi_test_list = jsonObj.getJSONArray("wifi_test_list");

        //保存数据
        wifiRepository.saveTestRecord("1","1", wifi_info_list, wifi_test_list.size() );

        return new JsonResult(1);
    }
    // 匹配 1-近邻 找出最接近的那个点
    @UserLoginToken
    @RequestMapping(value = "wifi_match", method = RequestMethod.POST)
    public JsonResult getWifiMatchResult(@RequestParam("wifi_info_list") String wifi_info_list){
        JSONObject jsonObj = JSONObject.parseObject(wifi_info_list);
        JSONArray wifi_test_list = jsonObj.getJSONArray("wifi_test_list");
        return new JsonResult(wifiService.getMatchPoint(wifi_test_list,1));
    }

    /*
     * // type 1 android 2 安卓小程序 3 苹果小程序
     */
    @UserLoginToken
    @RequestMapping(value = "nn_match", method = RequestMethod.POST)
    public JsonResult getNNMatchResult(@RequestParam("wifi_info_list") String wifi_info_list,
                                       @RequestParam("num") int num,
                                       @RequestParam("average") int average,
                                       @RequestParam("type") int type){
        JSONObject jsonObj = JSONObject.parseObject(wifi_info_list);
        JSONArray wifi_test_list = jsonObj.getJSONArray("wifi_test_list");
        return wifiService.getNNResult(wifi_test_list,num,type, average);
    }

    // 匹配 n-近邻 找出可能的那个点
    @UserLoginToken
    @RequestMapping(value = "wifi_match_points", method = RequestMethod.POST)
    public JsonResult getWifisMatchResult(@RequestParam("wifi_info_list") String wifi_info_list, @RequestParam("number") Integer number){
        JSONObject jsonObj = JSONObject.parseObject(wifi_info_list);
        JSONArray wifi_test_list = jsonObj.getJSONArray("wifi_test_list");

//        System.out.println(wifi_test_list);
        //保存数据
//        wifiRepository.saveTestRecord("0","0", wifi_info_list.toString(), wifi_test_list.size() );

        return new JsonResult(wifiService.getMatchPoints(wifi_test_list,number));
    }

    //根据地点id获取测试记录
    @UserLoginToken
    @RequestMapping(value = "get_wifi_record", method = RequestMethod.POST)
    public JsonResult getWifiRecordByLocId(@RequestParam("loc_id") String loc_id){
        return new JsonResult(wifiService.WifiRecordByLocId(loc_id));
    }

    //    get_record_detail 根据record_id获取详细信息
    @UserLoginToken
    @RequestMapping(value = "get_record_detail", method = RequestMethod.POST)
    public JsonResult getWifiRecordDetail(@RequestParam("wifi_rec_id") String wifi_rec_id){
        return new JsonResult(wifiService.WifiRecordDetail(wifi_rec_id));
    }

    // 不同手机测试对比
    @UserLoginToken
    @RequestMapping(value = "get_record_difphone", method = RequestMethod.POST)
    public JsonResult getWifiRecordDifPhone(){
        return new JsonResult(wifiService.WifiRecordDifPhone());
    }

    // 测试数据保存
    @UserLoginToken
    @RequestMapping(value = "test_record_save", method = RequestMethod.POST)
    public JsonResult testRecordSave(){
        return new JsonResult(wifiService.wifiTestSave());
    }

}
