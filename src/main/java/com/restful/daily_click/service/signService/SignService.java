package com.restful.daily_click.service.signService;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.*;
import com.restful.daily_click.repository.*;
import com.restful.daily_click.service.classService.ClassRoomSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.util.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static java.lang.Integer.valueOf;

@Service
public class SignService {

    @Autowired
    StudentsRepository studentsRepository;
    @Autowired
    SignRepository signRepository;
    @Autowired
    ClassRoomSeatService classRoomSeatService;
    @Autowired
    SignRecordRepository signRecordRepository;
    @Autowired
    TeachersRepository teachersRepository;
    @Autowired
    SignLeaveRepository signLeaveRepository;
    @Autowired
    GroupRepository groupRepository;
    @Transactional
    //-----添加签到事项-----------
    public SignItemEntity addSignItem(String type, String name, String originator, String classid,
                                      String latitude, String longitude, String radius, String begintime,
                                      String endtime, String check, String signtype, String classroomid) {
        SignItemEntity signItemEntity = new SignItemEntity();
        signItemEntity.setSignItemType(valueOf(type));
        signItemEntity.setSignItemName(name);
        signItemEntity.setSignItemOriginator(originator);
        signItemEntity.setSignItemClassid(classid);
        signItemEntity.setSignItemLatitude(latitude);
        signItemEntity.setSignItemLongitude(longitude);
        signItemEntity.setSignItemRadius(valueOf(radius));
        signItemEntity.setSignItemBeginTime(begintime);
        signItemEntity.setSignItemEndTime(endtime);
        signItemEntity.setSignItemCheck(check);
        signItemEntity.setSignItemSignType(valueOf(signtype));
        signItemEntity.setSignItemClassroomid(valueOf(classroomid));
        signItemEntity.setSignItemInSigning(1);

        SignItemEntity obj = signRepository.save(signItemEntity);

        return obj;
    }

    @Transactional
    public JSONObject getAllSign(int page, int size, String originator) {
        //Pageable pageable = new PageRequest(page,size);
        Pageable pageable = PageRequest.of(page,size);
        List<JSONObject> json = signRepository.getAllSign(pageable, originator);
        int number = signRepository.getAllSignNum(originator);
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);

        return temp;
    }

    @Transactional
    public Object getStudentAllSign(int page, int size, String grade, String account) {
        //Pageable pageable = new PageRequest(page,size);
        Pageable pageable = PageRequest.of(page,size);
        List<JSONObject> json = signRepository.getStudentAllSign(pageable, grade);
        for( int i=0;i<json.size(); i++ ) {
            int num = signRepository.getStudentIfSign(json.get(i).getInteger("id"), account);
            json.get(i).put("signflag", num);
        }
        int number = signRepository.getStudentAllSignNum(grade);
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);
        return temp;
    }

    @Transactional
    //---------获取学生签到页面信息----------
    public JsonResult getStudentSignDetial(int signid, String account) {
        JSONObject signitemlist = signRepository.getSigItem(signid);
        if( signitemlist == null ) {
            return new JsonResult(3001,"未获取签到信息");
        }
        int leavenum = 0;
        // 2为实验室签到
        if(signitemlist.getInteger("sign_item_type") == 2) {
            leavenum = signRepository.getStudentIfAskLeaveWithTime(signid, account);
            //否则为教室签到
        } else {
            leavenum = signRepository.getStudentIfAskLeave(signid, account);
        }
        Integer classroomid = signitemlist.getInteger("sign_item_classroomid");
        JsonResult list = classRoomSeatService.getStudentClassRoomSignData(classroomid, signid, signitemlist.getInteger("sign_item_type"));
        JSONObject temp = new JSONObject();
        temp.put("signitem",signitemlist );
        temp.put("roomseatlist", list);
        temp.put("leavenum", leavenum);
        return new JsonResult(temp);
    }

    @Transactional
    public Object addSignRecord(int signtiemid, int row, int col, String account) {
        SignRecordEntity signRecordEntity = new SignRecordEntity();
        signRecordEntity.setCol(col);
        signRecordEntity.setRow(row);
        signRecordEntity.setSignitemId(signtiemid);
        signRecordEntity.setStuCode(account);
        Date date = new Date();
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        Timestamp timestamp = Timestamp.valueOf(nowTime);
        signRecordEntity.setSignTime(timestamp);
        SignRecordEntity obj = signRecordRepository.save(signRecordEntity);
        return obj;
    }

    @Transactional
    public JSONObject getStudentSignResult(int signid, String account) {
        return signRecordRepository.getStudentSignResult(signid, account);
    }

    @Transactional
    public Object deleteSign(int id) {
        return signRecordRepository.deleteSign(id);
    }

    @Transactional
    public JsonResult addSignRecordCheckPass(int signtiemid, int row, int col, String account, String checkpass) {
//        验证签到码
        String passcheck = signRecordRepository.selectSignItemCheck(signtiemid);
        if( passcheck== null ) {
            return new JsonResult(4002, "签到活动出错，请重新签到");
        }
        System.out.println(passcheck.equals(checkpass));
        if( passcheck.equals(checkpass)) {
            return new JsonResult(addSignRecord(signtiemid, row, col, account));
        } else {
            return new JsonResult(4001, "验证码错误");
        }
    }

    @Transactional
    public Object getStudentLabSign(int page, int size, String grade, String account) {
        //Pageable pageable = new PageRequest(page,size);
        Pageable pageable = PageRequest.of(page,size);
        List<JSONObject> json = signRepository.getStudentLabSign(pageable, account);
        for( int i=0;i<json.size(); i++ ) {
            int num = signRepository.getStudentIfSignWithTime(json.get(i).getInteger("id"), account);
            int leavenum = signRepository.getStudentIfAskLeaveWithTime(json.get(i).getInteger("id"), account);
            String name = teachersRepository.getTnameByCode(json.get(i).getInteger("sign_item_originator"));
            json.get(i).put("signflag", num);
            json.get(i).put("leaveflag", leavenum);
            json.get(i).put("tname", name);
        }
        int number = signRepository.getStudentLabSignNum(grade);
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);
        return temp;
    }

    //--------获取学生签到项信息-----------
    @Transactional
    public Object getStudentNomalSign(int page, int size, String grade, String account) {
        //Pageable pageable = new PageRequest(page,size);
        Pageable pageable = PageRequest.of(page,size);
        List<JSONObject> json = signRepository.getStudentNomalSign(pageable, grade);
        for( int i=0;i<json.size(); i++ ) {
            int num = signRepository.getStudentIfSign(json.get(i).getInteger("id"), account);
            String name = teachersRepository.getTnameByCode(json.get(i).getInteger("sign_item_originator"));
            json.get(i).put("signflag", num);
            json.get(i).put("tname", name);
        }
        int number = signRepository.getStudentNomalSignNum(grade);
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);
        return temp;
    }

    @Transactional
    public Object getStudeentRecorded(int page, int size, String account) {
        //Pageable pageable = new PageRequest(page,size);
        Pageable pageable = PageRequest.of(page,size);
        List<JSONObject> json = signRepository.getStudeentRecorded(pageable, account);
        int number = signRepository.getStudeentRecordedNum(account);
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);
        return temp;
    }

    @Transactional
    public Object getTeacherAddSignRecorded(int page, int size, String account) {
        //Pageable pageable = new PageRequest(page,size);
        Pageable pageable = PageRequest.of(page,size);
        List<JSONObject> json = signRepository.getTeacherAddSignRecorded(pageable, account);
        int number = signRepository.getTeacherAddSignRecordedNum(account);
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);
        return temp;
    }

    @Transactional
    public Object getTeacherNomalSignList(int page, int size, String account) {
        //Pageable pageable = new PageRequest(page,size);
        Pageable pageable = PageRequest.of(page,size);
        List<JSONObject> json = signRepository.getTeacherNomalSignList(pageable, account);
        int number = signRepository.getTeacherNomalSignListNum(account);
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);
        return temp;
    }

    @Transactional
    public Object getTeacherLablSignList(int page, int size, String account) {
        //Pageable pageable = new PageRequest(page,size);
        Pageable pageable = PageRequest.of(page,size);
        List<JSONObject> json = signRepository.getTeacherLablSignList(pageable, account);
        int number = signRepository.getTeacherLablSignListNum(account);
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);
        return temp;
    }

    @Transactional
    public List<JSONObject> getAllLablSignList() {
        List<JSONObject> json = signRepository.getAllLablSignList();
        return json;
    }

    @Transactional
    public Object teacherBeginSign(int signtiemid) {
        int flag = signRepository.teacherBeginSign(signtiemid);
        if( flag == 1 ) {
            return new JsonResult("修改成功");
        } else   {
            return new JsonResult(40002, "修改失败");
        }
    }

    @Transactional
    public Object teacherStopSign(int signtiemid) {
        int flag = signRepository.teacherStopSign(signtiemid);
        if( flag == 1 ) {
            return new JsonResult("修改成功");
        } else   {
            return new JsonResult(40002, "修改失败");
        }
    }

    @Transactional
    //-----------获取签到信息----------
    public JsonResult getLinkSignDetial(int signid, int flag) {
        JSONObject signitemlist = signRepository.getSigItem(signid);
        if( signitemlist == null ) {
            return new JsonResult(3001,"未获取签到信息");
        }
        Integer classroomid = signitemlist.getInteger("sign_item_classroomid");
        JsonResult list = classRoomSeatService.getStudentClassRoomSignData(classroomid, signid, 1);
        List<JSONObject> notsignlist = null;
        if( flag == 1 ) {
            notsignlist = signRepository.getNotSignStudent(signid);
        } else {
            notsignlist = signRepository.getLabNotSignStudent(signid);
        }
        JSONObject temp = new JSONObject();
        temp.put("signitem",signitemlist );
        temp.put("roomseatlist", list);
        temp.put("notsignlist", notsignlist);
        return new JsonResult(temp);
    }

    @Transactional
    public Object addLeaveRecord(int signtiemid, String leavereason, String account) {
        SignLeaveEntity signLeaveEntity = new SignLeaveEntity();
        signLeaveEntity.setStudentId(account);
        signLeaveEntity.setSignItemId(signtiemid);
        signLeaveEntity.setReason(leavereason);
        Date date = new Date();
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        Timestamp timestamp = Timestamp.valueOf(nowTime);
        signLeaveEntity.setCreatTime(timestamp);
        SignLeaveEntity obj = signLeaveRepository.save(signLeaveEntity);
        if( obj == null ) {
            return new JsonResult(6001, "请假失败");
        } else {
            return obj;
        }
    }

    public JSONObject getClassItemResult(int signitemid) {
        JSONObject obj = signRepository.getClassItem(signitemid);
//        {"sign_item_originator":"3302", "sign_item_name":"sss", "sign_item_longitude":"121.578468",
//                "sign_item_classid":"CS172", "sign_item_begin_time":"17:57", "sign_item_check":"",
//                "sign_item_end_time":"18:57", "sign_item_latitude":"29.82399", "sign_item_radius":200,
//                "sign_item_sign_type":0, "sign_item_in_signing":0, "sign_item_type":1,"id":4, "sign_item_classroomid":43}
        //获取签到总人数
        Integer all = signRepository.getClassNumberByClassid(obj.getString("sign_item_classid"));
        //获取已签到人数
        Integer signnumber = signRepository.getSignNumber(obj.getInteger("id"));
        // 获取请假人数
        Integer askleve = signRepository.getAskLeveNumber(obj.getInteger("id"));
        // 获取缺勤人数
        Integer notsignnumber = all - signnumber - askleve;
        //获取未签到名单
        List<JSONObject> notsignlist = signRepository.getNotSignStudent(obj.getInteger("id"));
        // 统计迟到人数
        Integer signlatenumber = 0;

        Integer begintimehour = Integer.parseInt(obj.getString("sign_item_begin_time").substring(0,2));
        Integer begintimeminite = Integer.parseInt(obj.getString("sign_item_begin_time").substring(3,5));

        // 获取签到记录
        List<JSONObject> signlatelist = signRepository.getsignLateList(obj.getInteger("id"));
        // 统计迟到人数
        for(int j = 0; j < signlatelist.size(); j++) {
            // 超过一个小时为迟到
            if( (Integer.parseInt(signlatelist.get(j).getString("sign_time").substring(14,16)) - begintimehour)*60 + (Integer.parseInt(signlatelist.get(j).getString("sign_time").substring(17,19)) - begintimeminite) > 60) {
                signlatenumber++;
            }
        }
        JSONObject temp = new JSONObject();
        int[] piedata = {all, signnumber,askleve, notsignnumber, signlatenumber};
        JSONObject jso = new JSONObject();
        jso.put("expectedData", piedata);
        temp.put("piedata", jso);
        temp.put("notsignlist", notsignlist);
        return temp;
    }

    public JSONObject getsignResultByTime(String beg_time, String end_time, int type, int labid) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String begintime = sdf.format(new Date(Long.valueOf(beg_time)));
        String endtime = sdf.format(new Date(Long.valueOf(end_time)));
        List<JSONObject> obj = signRepository.getSignResultBetween(begintime, endtime, labid);
        //签到人员列表
        HashMap<String, Integer> map = new HashMap<>();
        // 总签到人数
        Integer sum = 0;
        // 统计共有多少签到项参与统计
        Integer signnumber = obj.size();
        // 统计请假人数
        Integer signlievenumber = 0;
        // 统计缺勤人数
        Integer notsignnumber = 0;
        // 统计迟到人数
        Integer signlatenumber = 0;
        for(int i = 0; i < signnumber; i++) {
            sum += groupRepository.getLabNumber(obj.get(i).getInteger("sign_item_id"));

            signlievenumber += signRepository.getsignLieveNumber(obj.get(i).getInteger("sign_item_id"));
            // 获取签到记录
            List<JSONObject> signlatelist = signRepository.getsignLateList(obj.get(i).getInteger("sign_item_id"));
            // 获取签到项记录
            JSONObject item = signRepository.getSigItemCreatTimeAndEndTime(obj.get(i).getInteger("sign_item_id"));
            Integer begintimehour = Integer.parseInt(item.getString("listbegintime").substring(0,2));
            Integer begintimeminite = Integer.parseInt(item.getString("listbegintime").substring(3,5));
//            Integer endtimehour = Integer.parseInt(item.getString("listendtime").substring(0,2));
//            Integer endtimeminite = Integer.parseInt(item.getString("listendtime").substring(3,5));
            // 统计迟到人数
            for(int j = 0; j < signlatelist.size(); j++) {

                if(map.get(signlatelist.get(j).getString("stu_code")) != null) {
                    int v = map.get(signlatelist.get(j).getString("stu_code"));
                    map.put(signlatelist.get(j).getString("stu_code"),v+1);
                } else {
                    map.put(signlatelist.get(j).getString("stu_code"),1);
                }

//                personlist.add(signlatelist.get(j));
                // 超过一个小时为迟到
                if( (Integer.parseInt(signlatelist.get(j).getString("sign_time").substring(14,16)) - begintimehour)*60 + (Integer.parseInt(signlatelist.get(j).getString("sign_time").substring(17,19)) - begintimeminite) > 60) {
                    signlatenumber++;
                }
            }
            // 早退人数晚点再搞
            // 统计缺勤人数
            if( type == 1) {
                // 实验室获取缺勤人数
                notsignnumber += signRepository.getLabNotSignStudent(obj.get(i).getInteger("sign_item_id")).size();
            } else {
                // 课堂签到获取缺勤人数
            }

        }
        JSONObject temp = new JSONObject();
        if( sum != 0 ) {
            temp.put("rate", new Float(sum - notsignnumber) / sum );
        } else {
            temp.put("rate", 0);
        }

        ArrayList<JSONObject> persionlist = new ArrayList<JSONObject>();
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        for (Map.Entry s : list)
        {
            JSONObject job = new JSONObject();
            job.put("number", s.getValue());
            job.put("name", studentsRepository.getStuInfonById((String)s.getKey()).getName());
            persionlist.add(job);
//            s.setValue(studentsRepository.getStuInfonById((String)s.getKey()).getName());
        }

        temp.put("personlist",persionlist );
        temp.put("signnumber",signnumber );

//        ArrayList<JSONObject> piedata = new ArrayList<JSONObject>();

//        JSONObject signLieveNumberJob = new JSONObject();
//        signLieveNumberJob.put("value", signlievenumber);
//        signLieveNumberJob.put("name", "请假人数");
//        piedata.add(signLieveNumberJob);
//
//        JSONObject notSignNumber = new JSONObject();
//        notSignNumber.put("value", signlievenumber);
//        notSignNumber.put("name", "缺勤人数");
//        piedata.add(notSignNumber);
//
//        JSONObject signLateNumber = new JSONObject();
//        signLateNumber.put("value", signlievenumber);
//        signLateNumber.put("name", "迟到人数");
//        piedata.add(signLateNumber);

        int[] piedata = {sum, sum - notsignnumber,signlievenumber, notsignnumber, signlatenumber};
        JSONObject jso = new JSONObject();
        jso.put("expectedData", piedata);
        temp.put("piedata", jso);

//        temp.put("signlievenumber", signlievenumber);
//        temp.put("notsignnumber", notsignnumber);
//        temp.put("signlatenumber", signlatenumber);
        return temp;
    }

    @Transactional
    public Object getsignItemByTid(int page, int size, int tid) {
        //Pageable pageable = new PageRequest(page,size);
        Pageable pageable = PageRequest.of(page,size);
        List<JSONObject> json = signRepository.getsignItemByTid(pageable, tid);
        int number = signRepository.getsignItemByTidNum(tid);
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);
        return temp;
    }


}
