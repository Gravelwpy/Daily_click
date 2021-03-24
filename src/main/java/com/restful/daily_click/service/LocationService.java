package com.restful.daily_click.service;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.LocationInfoEntity;
import com.restful.daily_click.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class LocationService {
    @Autowired
    LocationRepository locationRepository;

    public ArrayList<JSONObject> getLocationInfoByAccount(String account){
        ArrayList<JSONObject> res_list = new ArrayList<JSONObject>();
        ArrayList<String> loc_list = locationRepository.getLocNameByAccount(account);
        for(String loc:loc_list){
            JSONObject temp = new JSONObject();
            temp.put("loc_name",loc);
            temp.put("test_point",locationRepository.getTestPoint(account,loc));
            res_list.add(temp);
        }
        return res_list;
    }

    public int delPoint(int loc_id,String account){
        return locationRepository.deleteLocationInfoEntitiesByLocIdAndAccount(loc_id,account);
    }

    public int addPoint(LocationInfoEntity temp){
        return locationRepository.addTestPoint(temp.getAccount(),temp.getLocName(),temp.getLongitude(),temp.getLatitude(),temp.getTestPoint());
    }

    public Object getLocationInfo(String account) {
        ArrayList<JSONObject> res_list = new ArrayList<JSONObject>();
        ArrayList<JSONObject> loc_list = locationRepository.getLocationInfoEntitiesByAccount(account);
        JSONObject temp = new JSONObject();
        temp.put("list",loc_list);
        res_list.add(temp);
        return res_list;
    }

    @Transactional
    public JSONObject getAllLocalName(int page, int size) {
        //Pageable pageable = new PageRequest(page,size);
        Pageable pageable = PageRequest.of(page,size);
        List<JSONObject> json = locationRepository.getAllLocalName(pageable);
        List<JSONObject> number = locationRepository.getAllLocalNameNum();
        JSONObject temp = new JSONObject();
        temp.put("number",number.size() );
        temp.put("data", json);

        return temp;
    }

    @Transactional
    public boolean editPoint(String id, String loc_name, String test_point, String longitude, String latitude) {
        int flag = locationRepository.editPoint(loc_name, test_point, longitude, latitude, id);
        if(flag == 1){
            return true;
        }
        return false;
    }

    @Transactional
    public JSONObject getTestPointByLocName(int page, int size, String loc_name) {
        //Pageable pageable = new PageRequest(page,size);
        Pageable pageable = PageRequest.of(page,size);
        List<JSONObject> json = locationRepository.getTestPointByLocName(pageable, loc_name);
        List<JSONObject> number = locationRepository.getTestPointByLocNameNum(loc_name);
        JSONObject temp = new JSONObject();
        temp.put("number",number.size() );
        temp.put("data", json);

        return temp;
    }

    @Transactional
    public int delLocPoint(int loc_id){
        return locationRepository.delLocationById(loc_id);
    }

    @Transactional
    public int delLocPointByLocalName(String loc_name) {

        return locationRepository.delLocationByLocName(loc_name);
    }
}
