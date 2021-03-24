package com.restful.daily_click.controller.classController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.entity.ClassRoomSeatEntity;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.service.classService.ClassRoomSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("classseat")
@CrossOrigin
public class ClassSeatController {

    @Autowired
    ClassRoomSeatService classRoomSeatService;

    @UserLoginToken
    //---------修改/新增教室信息---------
    @RequestMapping(value = "/changeClassSeatInf", method = RequestMethod.POST)
    public JsonResult getStuSchedule(@RequestParam("seatArr") String seatArr,
                                     @RequestParam("form") String form,
                                     @RequestParam("flags") int flags) throws Exception{
        JSONArray seatarr = JSON.parseArray(seatArr);
        JSONObject fromdata = JSON.parseObject(form);

//        System.out.println("2:" + fromdata.toString());
        if(flags == 1 ) {//新增
            int length = seatarr.getJSONArray(0).size();
            int num = classRoomSeatService.addClassRoom(fromdata, length);
            fromdata.put("id", num);
        } else {//修改
            int length = seatarr.getJSONArray(0).size();
            classRoomSeatService.changeLength(length, fromdata.getIntValue("id"));
        }

        ArrayList<ClassRoomSeatEntity> arrlist = new ArrayList<>();
//        System.out.println("seatarr.size():" + seatarr.size());
//        System.out.println("seatarr.getJSONArray(i).size():" + seatarr.getJSONArray(0).size());
        for(int i=0; i < seatarr.size(); i++) {
            for(int j=0; j < seatarr.getJSONArray(i).size(); j++ ) {
                JSONArray obj = seatarr.getJSONArray(i);

                ClassRoomSeatEntity c = new ClassRoomSeatEntity();
                c.setRoomId(fromdata.getIntValue("id"));
                c.setIsSeat((Integer) obj.get(j));
                c.setRow(i);
                c.setCol(j);
                arrlist.add(c);
            }
        }
//        System.out.println("1:" + fromdata.toString());
//        return null;
//        保存教室数据
        return classRoomSeatService.saveRoomSeat(arrlist, fromdata, flags);
    }

    @UserLoginToken
    @RequestMapping(value = "/getClassSeatInf", method = RequestMethod.POST)
    public JsonResult getStuSchedule(@RequestParam("id") int id){
        return classRoomSeatService.getRoomSeatData(id);
    }

    @UserLoginToken
    @RequestMapping(value = "/getSignSeatData", method = RequestMethod.POST)
    public JsonResult getSignSeatData(@RequestParam("classroomid") int classroomid){
        System.out.println(classroomid);
        return classRoomSeatService.getClassRoomSignData(classroomid);
    }

//    @UserLoginToken
//    @RequestMapping(value = "/getStudentSignSeatData", method = RequestMethod.POST)
//    public JsonResult getStudentSignSeatData(@RequestParam("classroomid") int classroomid, @RequestParam("signtiemid") int signtiemid){
//        System.out.println(classroomid +signtiemid );
//        return classRoomSeatService.getStudentClassRoomSignData(classroomid, signtiemid);
//    }

}
