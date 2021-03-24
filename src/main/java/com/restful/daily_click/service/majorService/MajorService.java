package com.restful.daily_click.service.majorService;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.ClassMajorityEntity;
import com.restful.daily_click.entity.CoursesEntity;
import com.restful.daily_click.repository.MajorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MajorService {

    @Autowired
    MajorRepository majorRepository;

    @Transactional
    public Object getAllMajor(int page, int size) {
        Pageable pageable = new PageRequest(page,size);
        List<JSONObject> json = majorRepository.getAllMajor(pageable);
        int number = majorRepository.getAllMajorNum();
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);

        return temp;
    }

    @Transactional
    public boolean editMajor(String id, String majority_name) {
        int flag = majorRepository.editMajor(majority_name, id);
        if(flag == 1){
            return true;
        }
        return false;
    }

    @Transactional
    public boolean delMajoy(String id) {
        int flag = majorRepository.delMajoy(id);
        if(flag == 1){
            return true;
        }
        return false;
    }

    @Transactional
    public boolean addMajoy(String majority_name) {
        if(isUniqueMajoy(majority_name)){
            try{
                majorRepository.addMajority(majority_name);
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        else{

        }
        return true;
    }
    public boolean isUniqueMajoy(String majority_name){
        ClassMajorityEntity obj = majorRepository.findMajoyByName(majority_name);
        if(null == obj){
            return true;
        }
        return false;
    }
}
