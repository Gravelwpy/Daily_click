package com.restful.daily_click.service.classService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.ClassRoomEntity;
import com.restful.daily_click.entity.ClassRoomSeatEntity;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.repository.ClassRepository;
import com.restful.daily_click.repository.SignRecordRepository;
import com.restful.daily_click.service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClassRoomSeatService {

    @Autowired
    StudentsService studentsService;

    @Autowired
    ClassRepository classRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    SignRecordRepository signRecordRepository;

    @Transactional
    public JsonResult saveRoomSeat(ArrayList<ClassRoomSeatEntity> arrlist, JSONObject fromdata, int flags) {
        int savenumber=-1,delectnumber = -1;
        if( arrlist.size() > 0 ) {
            delectnumber = classRepository.delectAllById(fromdata.getInteger("id"));
            savenumber = batchInsert(arrlist);
        } else {
            savenumber = classRepository.delectAllById(fromdata.getInteger("id"));
        }
        if( flags != 1 ) {
            int changeclass = classRepository.changeClassData(fromdata.get("room_name"), fromdata.get("capacity"), fromdata.getInteger("id"));
        }
        return new JsonResult(fromdata.getInteger("id"));
    }

//    批量保存座位数据
    private int batchInsert(List<ClassRoomSeatEntity> arrlist) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO class_room_seat(room_id, is_seat, row, col) VALUES ");
        for(ClassRoomSeatEntity roomData : arrlist) {
            sb.append("(?, ?, ?, ?),");
        }
        System.out.println(sb.toString());
        String sql = sb.toString().substring(0, sb.length() - 1);
        Query query = entityManager.createNativeQuery(sql);
        int paramIndex = 1;
        for(ClassRoomSeatEntity roomData : arrlist) {
            query.setParameter(paramIndex++, roomData.getRoomId());
            query.setParameter(paramIndex++, roomData.getIsSeat());
            query.setParameter(paramIndex++, roomData.getRow());
            query.setParameter(paramIndex++, roomData.getCol());
        }
        return query.executeUpdate();
    }

    @Transactional
    public JsonResult getRoomSeatData(int id) {
        List<JSONObject> seatarr = classRepository.getSeatArr(id);
        JSONArray data = new JSONArray();
        for( int i = 0; i < 18; i++ ) {
            JSONArray idata = new JSONArray();
            for( int j = 0; j < 18; j++) {
                JSONObject object = new JSONObject();
                object.put("is_seat", -1);
                idata.add(object);
            }
            data.add(idata);
        }
        for(int i=0; i < seatarr.size(); i++ ) {
            JSONObject object = new JSONObject();
            object.put("is_seat", seatarr.get(i).getInteger("is_seat"));
            data.getJSONArray(seatarr.get(i).getInteger("row")).set(seatarr.get(i).getInteger("col"), object);
        }
//        System.out.println(data.toString());

        return new JsonResult(data);
    }

    @Transactional
    public int addClassRoom(JSONObject fromdata, int length) {
        int f = classRepository.addNewClassRoom(fromdata.getString("room_name"), fromdata.getInteger("capacity"), length);
        return classRepository.getLastInsert();
    }

    @Transactional
    public JsonResult getClassRoomSignData(int classroomid) {
        JSONArray seatdata = classRepository.getSeatData(classroomid);
        JSONObject classroomdata = classRepository.getRoomData(classroomid);
        int length = classroomdata.getInteger("length");
        if( length - 0 <= 0 ) {
            return new JsonResult("分母为0");
        }
        int width = seatdata.size() / length;

//        组装座位二维数组
        JSONArray seatarr = new JSONArray();
        for(int i=0; i < width; i++ ) {
            JSONArray barr = new JSONArray();
            for(int j=0; j < length; j++ ) {
                JSONObject obj = new JSONObject();
                obj.put("is_seat", 0);
                barr.add(obj);
            }
            seatarr.add(barr);
        }
        for(int i = 0; i < seatdata.size(); i++ ) {
            JSONObject obj = new JSONObject();
            obj.put("is_seat", seatdata.getJSONArray(i).getInteger(2));
            seatarr.getJSONArray(seatdata.getJSONArray(i).getInteger(3)).set(seatdata.getJSONArray(i).getInteger(4), obj);
        }

        return new JsonResult(seatarr);
    }

    @Transactional
    public JsonResult getStudentClassRoomSignData(int classroomid, int signtiemid, int type) {
        // type = 2 为实验室签到
        JSONArray seatdata = classRepository.getSeatData(classroomid);
        JSONObject classroomdata = classRepository.getRoomData(classroomid);
        int length = classroomdata.getInteger("length");
        if( length - 0 <= 0 ) {
            return new JsonResult("分母为0");
        }
        int width = seatdata.size() / length;
//        System.out.println("width:" + width);
//        System.out.println("length:" + length);
//        System.out.println("seatdata.size():" + seatdata.size());
//        组装座位二维数组
        JSONArray seatarr = new JSONArray();
        for(int i=0; i < width; i++ ) {
            JSONArray barr = new JSONArray();
            for(int j=0; j < length; j++ ) {
                JSONObject obj = new JSONObject();
                obj.put("is_seat", 0);
                barr.add(obj);
            }
            seatarr.add(barr);
        }
        for(int i = 0; i < seatdata.size(); i++ ) {
            JSONObject obj = new JSONObject();
            obj.put("is_seat", seatdata.getJSONArray(i).getInteger(2));
            seatarr.getJSONArray(seatdata.getJSONArray(i).getInteger(3)).set(seatdata.getJSONArray(i).getInteger(4), obj);
        }
        List<JSONObject> list = null;
        if(type == 2) {
            list = signRecordRepository.getSignRecordWithTime(signtiemid);
        } else {
            list = signRecordRepository.getSignRecord(signtiemid);
        }


        for(int i=0;i<list.size();i++ ) {
            System.out.println(list.get(i).getString("row"));
            JSONObject obj = new JSONObject();
            obj.put("is_seat", 2);
            obj.put("stuinfo", studentsService.getStuInfonById(list.get(i).getString("stu_code")));
            seatarr.getJSONArray(list.get(i).getInteger("row")).set(list.get(i).getInteger("col"), obj);
        }
        return new JsonResult(seatarr);
    }

    @Transactional
    public void changeLength(int length, int id) {
        int num = classRepository.changeLength(length, id);
    }
}
