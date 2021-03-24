package com.restful.daily_click.service;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.dto.InformationDto;
import com.restful.daily_click.entity.InformationEntity;
import com.restful.daily_click.repository.InformationRepository;
import org.hibernate.boot.spi.InFlightMetadataCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @Author: ZhangKaijie
 * @Date: 2019/7/29 10:47
 * @Description:
 */
@Service
public class InformationService {
    @Autowired
    InformationRepository informationRepository;

    public ArrayList<ArrayList<JSONObject>> getStuSchedule(String code){
        ArrayList<ArrayList<JSONObject>> totallist = new ArrayList<ArrayList<JSONObject>>();
        ArrayList<JSONObject> res = informationRepository.getStuScheduleByStuCode(code);
        return handledata(totallist, res);
    }
    public ArrayList<ArrayList<JSONObject>> getStuScheduleByGrade(String grade){
        ArrayList<ArrayList<JSONObject>> totallist = new ArrayList<ArrayList<JSONObject>>();
        ArrayList<JSONObject> res = informationRepository.getStuScheduleByGrade(grade);
        return handledata(totallist, res);
    }
    public ArrayList<ArrayList<JSONObject>> handledata(ArrayList<ArrayList<JSONObject>> totallist, ArrayList<JSONObject> res) {
        ArrayList<JSONObject> temp1 = new ArrayList<JSONObject>();
        ArrayList<JSONObject> temp2 = new ArrayList<JSONObject>();
        ArrayList<JSONObject> temp3 = new ArrayList<JSONObject>();
        ArrayList<JSONObject> temp4 = new ArrayList<JSONObject>();
        ArrayList<JSONObject> temp5 = new ArrayList<JSONObject>();
        ArrayList<JSONObject> temp6 = new ArrayList<JSONObject>();
        ArrayList<JSONObject> temp7 = new ArrayList<JSONObject>();
        for(int i=0;i<res.size();i++) {
            int i_week = res.get(i).getInteger("i_week");
            switch (i_week)
            {
                case 1 : temp1.add(res.get(i));break;
                case 2 : temp2.add(res.get(i));break;
                case 3 : temp3.add(res.get(i));break;
                case 4 : temp4.add(res.get(i));break;
                case 5 : temp5.add(res.get(i));break;
                case 6 : temp6.add(res.get(i));break;
                case 7 : temp7.add(res.get(i));break;
            }
        }
        totallist.add(temp1);
        totallist.add(temp2);
        totallist.add(temp3);
        totallist.add(temp4);
        totallist.add(temp5);
        totallist.add(temp6);
        totallist.add(temp7);
        totallist = sort_data(totallist);
        totallist = insert_empty_obj(totallist);
//        System.out.println(totallist.toString());
        return totallist;
    }

    public ArrayList<ArrayList<JSONObject>> sort_data(ArrayList<ArrayList<JSONObject>> totallist){
        for(int i=0;i<totallist.size();i++){
            if(totallist.get(i)!=null){
                Collections.sort(totallist.get(i), new Comparator<JSONObject>() {
                    @Override
                    public int compare(JSONObject o1, JSONObject o2) {
                        return o1.getInteger("i_start")-o2.getInteger("i_start");
                    }
                });
            }
        }
        return totallist;
    }

    public ArrayList<ArrayList<JSONObject>> insert_empty_obj(ArrayList<ArrayList<JSONObject>> totallist){
        for(int i=0;i<totallist.size();i++){
//            System.out.println(totallist.get(i).toString());
            if(totallist.get(i)!=null){
                for(int j=0;j<totallist.get(i).size()-1;j++){
                    int i_start = totallist.get(i).get(j).getInteger("i_start");
                    int i_duration = totallist.get(i).get(j).getInteger("i_duration");
                    int i_start_next = totallist.get(i).get(j+1).getInteger("i_start");
                    if(1 != totallist.get(i).get(0).getInteger("i_start")){
                        int empty_capacity = totallist.get(i).get(0).getInteger("i_start")-1;
                        JSONObject empty_obj = new JSONObject();
                        empty_obj.put("i_duration",empty_capacity);
                        totallist.get(i).add(0,empty_obj);
                        j++;
                    }
                    if(i_start+i_duration<i_start_next){
                        int empty_capacity = i_start_next-(i_start+i_duration);
                        JSONObject empty_obj = new JSONObject();
                        empty_obj.put("i_duration",empty_capacity);
                        totallist.get(i).add(j+1,empty_obj);
                        j++;
                    }
                }
                if( totallist.get(i).size() == 1 ) {
                    if(1 != totallist.get(i).get(0).getInteger("i_start")){
                        int empty_capacity = totallist.get(i).get(0).getInteger("i_start")-1;
                        JSONObject empty_obj = new JSONObject();
                        empty_obj.put("i_duration",empty_capacity);
                        totallist.get(i).add(0,empty_obj);
                    }
                }
            }
        }
        return totallist;
    }

    public int addSchedule(Integer course_id, String room_id, String class_id, String tea_code, Integer i_week, Integer i_start, Integer i_duration) {
        InformationEntity informationEntity = new InformationEntity();
        informationEntity.setCourseId(course_id);
        informationEntity.setRoomId(room_id);
        informationEntity.setClassId(class_id);
        informationEntity.setTeaCode(tea_code);
        informationEntity.setiWeek(i_week);
        informationEntity.setiStart(i_start);
        informationEntity.setiDuration(i_duration);
        InformationEntity obj = informationRepository.save(informationEntity);
        if( obj != null ) {
            return 1;
        } else {
            return 0;
        }
    }
}
