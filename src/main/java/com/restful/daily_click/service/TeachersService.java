package com.restful.daily_click.service;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.StudentsEntity;
import com.restful.daily_click.entity.TeachersEntity;
import com.restful.daily_click.repository.TeachersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class TeachersService {
    @Autowired
    TeachersRepository teachersRepository;

    public TeachersEntity getTeacherByTeaCode(String teacode){
        return teachersRepository.findByTeaCode(teacode);
    }

    //添加教师信息
    public boolean addTeacher (//int tea_id,
                               String tea_code,
                               String tea_name,
                               String tea_sex,
                               String tea_tele,
                               String tea_email){
        if(isUnique(tea_code)){//判断工号是否唯一
            try{
                teachersRepository.addTeacher(tea_code,tea_name,tea_sex,tea_tele,tea_email);
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    //----判断是否唯一
    public boolean isUnique(String tea_code){
        TeachersEntity obj = teachersRepository.findByTeaCode(tea_code);
        if(null == obj){
            return true;
        }
        return false;
    }

    @Transactional
    //----------获取教师信息------------
    public JSONObject getAllTeacher(int page, int size) {
        //Pageable pageable = new PageRequest(page,size);
        Pageable pageable = PageRequest.of(page,size);
        List<JSONObject> json = teachersRepository.getAllTeacher(pageable);
        int number = teachersRepository.getAllTeacherNum();
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);

        return temp;
    }

    @Transactional
    public boolean delTeacher(String account) {
        int flag = teachersRepository.delTeacher(account);
        if(flag == 1){
            return true;
        }
        return false;
    }

    @Transactional
    public boolean editTeacher(String tea_id, String tea_code, String tea_email, String tea_name, String tea_sex, String tea_tele) {
        int flag = teachersRepository.editTeacher(tea_code, tea_email, tea_name, tea_sex, tea_tele, tea_id);
        if(flag == 1){
            return true;
        }
        return false;
    }
}
