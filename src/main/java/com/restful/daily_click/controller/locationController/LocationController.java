package com.restful.daily_click.controller.locationController;

import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.entity.LocationInfoEntity;
import com.restful.daily_click.service.LocationService;
import com.restful.daily_click.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@CrossOrigin
@RequestMapping("/location")
@RestController
public class LocationController {
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    LocationService locationService;

    @UserLoginToken
    @RequestMapping(value = "/getAllLocalName", method = RequestMethod.POST)
    public JsonResult getAllLocalName(@RequestParam("page") int page, @RequestParam("size") int size){
        return new JsonResult(locationService.getAllLocalName(page, size));
    }

    @UserLoginToken
    @RequestMapping(value = "/getTestPointByLocName", method = RequestMethod.POST)
    public JsonResult getTestPointByLocName(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("loc_name") String loc_name){
        return new JsonResult(locationService.getTestPointByLocName(page, size, loc_name));
    }

    @UserLoginToken
    //----获取已有定位信息-----
    @RequestMapping(value = "getLoc", method = RequestMethod.POST)
    public JsonResult getLocation(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String account = (String)tokenUtil.parseJwtToken(token).get("account");
        return new JsonResult(locationService.getLocationInfoByAccount(account));
    }

    @UserLoginToken
    //------获取考勤点----------
    @RequestMapping(value = "getLocList", method = RequestMethod.POST)
    public JsonResult getLocList(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String account = (String)tokenUtil.parseJwtToken(token).get("account");
        return new JsonResult(locationService.getLocationInfo(account));
    }

    @UserLoginToken
    @RequestMapping(value = "delPoint", method = RequestMethod.POST)
    public JsonResult delPoint(@RequestParam("loc_id") int loc_id,HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String account = (String)tokenUtil.parseJwtToken(token).get("account");
        try{
            if(locationService.delPoint(loc_id,account)==1){
                return new JsonResult();
            }else{
                return new JsonResult(1006,"删除失败");
            }
        } catch (Exception e){
            e.getMessage();
            return new JsonResult(-1,"system error");
        }

    }
    // pc管理删除测试点
    @UserLoginToken
    @RequestMapping(value = "delLocPoint", method = RequestMethod.POST)
    public JsonResult delLocPoint(@RequestParam("loc_id") int loc_id) {
        try{
            if(locationService.delLocPoint(loc_id)==1){
                return new JsonResult();
            }else{
                return new JsonResult(1006,"删除失败");
            }
        } catch (Exception e){
            e.getMessage();
            System.out.println(e);
            return new JsonResult(-1,"system error");
        }

    }
    // pc管理删除地点
    @UserLoginToken
    @RequestMapping(value = "delLocPointByLocalName", method = RequestMethod.POST)
    public JsonResult delLocPointByLocalName(@RequestParam("loc_name") String loc_name) {
        try{
            if(locationService.delLocPointByLocalName(loc_name) >= 1){
                return new JsonResult();
            }else{
                return new JsonResult(1006,"删除失败");
            }
        } catch (Exception e){
            e.getMessage();
            return new JsonResult(-1,"system error");
        }

    }
    @UserLoginToken
    //---------添加测试点----------
    @RequestMapping(value = "addPoint", method = RequestMethod.POST)
    public JsonResult addPoint(HttpServletRequest httpServletRequest,
                               @RequestParam("loc_name") String loc_name,
                               @RequestParam("longitude") double longitude,
                               @RequestParam("latitude") double latitude,
                               @RequestParam("test_point") String test_point
                               ){
        String token = httpServletRequest.getHeader("token");
        String account = (String)tokenUtil.parseJwtToken(token).get("account");
        LocationInfoEntity temp = new LocationInfoEntity();
        temp.setAccount(account);
        temp.setLatitude(latitude);
        temp.setLongitude(longitude);
        temp.setLocName(loc_name);
        temp.setTestPoint(test_point);
        try{
            if(locationService.addPoint(temp)!=1){
                return new JsonResult(1007,"添加失败");
            }
            return new JsonResult();
        } catch (Exception e){
            e.getMessage();
            return new JsonResult(-1,"system error");
        }

    }

    @RequestMapping(value = "/editPoint", method = RequestMethod.POST)
    public JsonResult editPoint(
            @RequestParam("id") String id,
            @RequestParam("loc_name") String loc_name,
            @RequestParam("test_point") String test_point,
            @RequestParam("longitude") String longitude,
            @RequestParam("latitude") String latitude){
        if(locationService.editPoint(id, loc_name, test_point, longitude, latitude)){
            return new JsonResult();
        }
        return new JsonResult(1006,"修改失败");
    }
}
