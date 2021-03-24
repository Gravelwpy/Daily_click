package com.restful.daily_click.controller.signController;

import com.restful.daily_click.annotation.UserLoginToken;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.service.StudentsService;
import com.restful.daily_click.service.signService.SignService;
import com.restful.daily_click.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@RestController
@RequestMapping("sign")
@CrossOrigin
public class SignController {
    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    SignService signService;

    @Autowired
    StudentsService studentsService;

    @UserLoginToken
    //----------新建签到事项-------------
    @RequestMapping(value = "/addSignItem", method = RequestMethod.POST)
    public JsonResult addSignItem(@RequestParam("type") String type,
                                  @RequestParam("name") String name,
                                  @RequestParam("classid") String classid,
                                  @RequestParam("latitude") String latitude,
                                  @RequestParam("longitude") String longitude,
                                  @RequestParam("radius") String radius,
                                  @RequestParam("begintime") String begintime,
                                  @RequestParam("endtime") String endtime,
                                  @RequestParam("check") String check,
                                  @RequestParam("signtype") String signtype,
                                  @RequestParam("classroomid") String classroomid,
                                  HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String originator = (String)tokenUtil.parseJwtToken(token).get("account");
        return new JsonResult(signService.addSignItem(type, name, originator, classid, latitude, longitude, radius, begintime, endtime, check, signtype, classroomid));
    }

    @UserLoginToken
    @RequestMapping(value = "/getAllSign", method = RequestMethod.POST)
    public JsonResult getAllSign(@RequestParam("page") int page, @RequestParam("size") int size, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String originator = (String)tokenUtil.parseJwtToken(token).get("account");
        return new JsonResult(signService.getAllSign(page - 1, size, originator));
    }

    @UserLoginToken
    @RequestMapping(value = "/getStudentAllSign", method = RequestMethod.POST)
    public JsonResult getStudentAllSign(@RequestParam("page") int page, @RequestParam("size") int size, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String account = (String)tokenUtil.parseJwtToken(token).get("account");
        String grade = studentsService.getStuGradeFromStuCode(account);
        return new JsonResult(signService.getStudentAllSign(page - 1, size, grade, account));
    }

    @UserLoginToken
    //--------获取学生实验室签到信息----------
    @RequestMapping(value = "/getStudentLabSign", method = RequestMethod.POST)
    public JsonResult getStudentLabSign(@RequestParam("page") int page, @RequestParam("size") int size, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String account = (String)tokenUtil.parseJwtToken(token).get("account");
        String grade = studentsService.getStuGradeFromStuCode(account);
        return new JsonResult(signService.getStudentLabSign(page - 1, size, grade, account));
    }

    @UserLoginToken
    //--------获取学生正常签到信息-----------
    @RequestMapping(value = "/getStudentNomalSign", method = RequestMethod.POST)
    public JsonResult getStudentNomalSign(@RequestParam("page") int page, @RequestParam("size") int size, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String account = (String)tokenUtil.parseJwtToken(token).get("account");
        String grade = studentsService.getStuGradeFromStuCode(account);
        return new JsonResult(signService.getStudentNomalSign(page - 1, size, grade, account));
    }

    @UserLoginToken
    @RequestMapping(value = "/getTeacherNomalSignList", method = RequestMethod.POST)
    public JsonResult getTeacherNomalSignList(@RequestParam("page") int page, @RequestParam("size") int size, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String account = (String)tokenUtil.parseJwtToken(token).get("account");
        return new JsonResult(signService.getTeacherNomalSignList(page - 1, size, account));
    }

    @UserLoginToken
    @RequestMapping(value = "/getTeacherLablSignList", method = RequestMethod.POST)
    public JsonResult getTeacherLablSignList(@RequestParam("page") int page, @RequestParam("size") int size, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String account = (String)tokenUtil.parseJwtToken(token).get("account");
        return new JsonResult(signService.getTeacherLablSignList(page - 1, size, account));
    }

    @UserLoginToken
    //---------获取学生签到页面详细信息---------
    @RequestMapping(value = "/getStudentSignDetial", method = RequestMethod.POST)
    public JsonResult getStudentSignDetial(@RequestParam("signid") int signid, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String account = (String)tokenUtil.parseJwtToken(token).get("account");
        return signService.getStudentSignDetial(signid, account);
    }

    @UserLoginToken
    //-------获取签到详细信息-----------
    @RequestMapping(value = "/getLinkSignDetial", method = RequestMethod.POST)
    public JsonResult getLinkSignDetial(@RequestParam("signid") int signid, @RequestParam("flag") int flag){
        return signService.getLinkSignDetial(signid, flag);
    }

    @UserLoginToken
    //-------------添加签到记录-----------
    @RequestMapping(value = "/addSignRecord", method = RequestMethod.POST)
    public JsonResult addSignRecord(@RequestParam("signtiemid") int signtiemid,
                                    @RequestParam("row") int row,
                                    @RequestParam("col") int col,
                                    HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String account = (String)tokenUtil.parseJwtToken(token).get("account");
        System.out.println("11111111111111111111111111111111111111111111111");
        return new JsonResult(signService.addSignRecord(signtiemid, row, col, account));
    }
    @UserLoginToken
    @RequestMapping(value = "/addLeaveRecord", method = RequestMethod.POST)
    public JsonResult addLeaveRecord(@RequestParam("signtiemid") int signtiemid,
                                     @RequestParam("leavereason") String leavereason,
                                     HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String account = (String)tokenUtil.parseJwtToken(token).get("account");
        return new JsonResult(signService.addLeaveRecord(signtiemid, leavereason, account));
    }
    @UserLoginToken
    @RequestMapping(value = "/addSignRecordCheckPass", method = RequestMethod.POST)
    public JsonResult addSignRecordCheckPass(@RequestParam("signtiemid") int signtiemid,
                                    @RequestParam("row") int row,
                                    @RequestParam("col") int col,
                                    @RequestParam("checkpass") String checkpass,
                                    HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String account = (String)tokenUtil.parseJwtToken(token).get("account");
        return signService.addSignRecordCheckPass(signtiemid, row, col, account, checkpass);
    }

    @UserLoginToken
    @RequestMapping(value = "/getStudentSignResult", method = RequestMethod.POST)
    public JsonResult getStudentSignResult(@RequestParam("signid") int signid, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String account = (String)tokenUtil.parseJwtToken(token).get("account");
        return new JsonResult(signService.getStudentSignResult(signid, account));
    }
    @UserLoginToken
    @RequestMapping(value = "/deleteSign", method = RequestMethod.POST)
    public JsonResult deleteSign(@RequestParam("id") int id){
        return new JsonResult(signService.deleteSign(id));
    }

    @UserLoginToken
    @RequestMapping(value = "/getStudeentRecorded", method = RequestMethod.POST)
    public JsonResult getStudeentRecorded(@RequestParam("page") int page, @RequestParam("size") int size, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String account = (String)tokenUtil.parseJwtToken(token).get("account");
        return new JsonResult(signService.getStudeentRecorded(page - 1, size, account));
    }

    @UserLoginToken
    @RequestMapping(value = "/getTeacherAddSignRecorded", method = RequestMethod.POST)
    public JsonResult getTeacherAddSignRecorded(@RequestParam("page") int page, @RequestParam("size") int size, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String account = (String)tokenUtil.parseJwtToken(token).get("account");
        return new JsonResult(signService.getTeacherAddSignRecorded(page - 1, size, account));
    }

    @UserLoginToken
    @RequestMapping(value = "/teacherBeginSign", method = RequestMethod.POST)
    public JsonResult teacherBeginSign(@RequestParam("signtiemid") int signtiemid){
        return new JsonResult(signService.teacherBeginSign(signtiemid));
    }

    @UserLoginToken
    @RequestMapping(value = "/teacherStopSign", method = RequestMethod.POST)
    public JsonResult teacherStopSign(@RequestParam("signtiemid") int signtiemid){
        return new JsonResult(signService.teacherStopSign(signtiemid));
    }

    @UserLoginToken
    @RequestMapping(value = "/getsignResultByTime", method = RequestMethod.POST)
    public JsonResult getsignResultByTime(@RequestParam("beg_time") String beg_time,
                                          @RequestParam("end_time") String end_time,
                                          @RequestParam("labid") int labid){
        int signtype = 1; // 实验室获取数据
        return new JsonResult(signService.getsignResultByTime(beg_time, end_time, 1, labid));
    }

    @UserLoginToken
    //---------获取班级签到信息-------------
    @RequestMapping(value = "/getClassItemResult", method = RequestMethod.POST)
    public JsonResult getClassItemResult(@RequestParam("signitemid") int signitemid){
        return new JsonResult(signService.getClassItemResult(signitemid));
    }

    @UserLoginToken
    @RequestMapping(value = "/getsignItemByTid", method = RequestMethod.POST)
    public JsonResult getsignItemByTid(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("tid") int tid){
        return new JsonResult(signService.getsignItemByTid(page, size, tid));
    }
}
