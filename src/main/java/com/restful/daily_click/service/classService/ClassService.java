package com.restful.daily_click.service.classService;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.ClassInfoEntity;
import com.restful.daily_click.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
public class ClassService {

    @Autowired
    ClassRepository classRepository;
    public List<JSONObject> getClassList(){
        return classRepository.getCLassNameList();
    }

    @Transactional
    public JSONObject getAllClassByMajorId(int page, int size, int majorid) {
        Pageable pageable = new PageRequest(page,size);
        List<JSONObject> json = classRepository.getAllClassByMajorId(pageable, majorid);
        int number = classRepository.getAllClassByMajorIdNum(majorid);
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);

        return temp;
    }

    @Transactional
    public boolean delClass(String id) {
        System.out.println(id);
        int flag = classRepository.delClass(id);
        if(flag == 1){
            return true;
        }
        return false;
    }

    @Transactional
    public boolean editClass(String id, String class_id, String class_name, String class_year) {
        int flag = classRepository.editClass(class_id, class_name, class_year, id);
        if(flag == 1){
            return true;
        }
        return false;
    }

    @Transactional
    public boolean addClass(String class_id, String class_name, String class_year, String majority_id) {
        if(isUniqueClass(class_name)){
            try{
                classRepository.addClass(class_id,class_name,class_year,majority_id);
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        else{

        }
        return true;
    }
    public boolean isUniqueClass(String class_name){
        ClassInfoEntity obj = classRepository.findClassByClassName(class_name);
        if(null == obj){
            return true;
        }
        return false;
    }

    @Transactional
    public List<JSONObject> creatNewSignGetclassList() {
        return classRepository.creatNewSignGetclassList();
    }

    @Transactional
    public List<JSONObject> miniPrograGetTeacherTeachClass(String account) {
        return classRepository.miniPrograGetTeacherTeachClass(account);
    }
}
