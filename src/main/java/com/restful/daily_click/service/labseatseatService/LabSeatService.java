package com.restful.daily_click.service.labseatseatService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.repository.ClassRepository;
import com.restful.daily_click.repository.LabSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LabSeatService {

    @Autowired
    LabSeatRepository labSeatRepository;
    @Autowired
    ClassRepository classRepository;

    @Transactional
    public JSONObject getLabListByTCode(String account) {

        List<JSONObject> list = labSeatRepository.getLabList(account);
        JSONObject temp = new JSONObject();
        temp.put("data", list);

        return temp;
    }

    public JsonResult getLabSeatDetial(int seatid) {

        JSONArray seatdata = classRepository.getLabSeatInfoById(seatid);
        int length = seatdata.getJSONArray(0).getInteger(5);
        System.out.println(length);
        int width = seatdata.size() / length;


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
            obj.put("seatid", seatdata.getJSONArray(i).getInteger(0));
            obj.put("is_seat", seatdata.getJSONArray(i).getInteger(2));
            obj.put("labstuname", seatdata.getJSONArray(i).getString(6));
            obj.put("labname", seatdata.getJSONArray(i).getString(7));
            seatarr.getJSONArray(seatdata.getJSONArray(i).getInteger(3)).set(seatdata.getJSONArray(i).getInteger(4), obj);
        }
        return new JsonResult(seatarr);
    }

    @Transactional
    public JsonResult changeSeatName(int seatid, String name) {
        int n = labSeatRepository.changeSeatName(seatid, name);
        if( n == 1 ) {
            return new JsonResult("修改成功");
        } else {
            return new JsonResult("修改失败");
        }
    }
}
