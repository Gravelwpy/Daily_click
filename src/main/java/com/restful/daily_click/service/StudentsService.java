package com.restful.daily_click.service;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.StudentsEntity;
import com.restful.daily_click.entity.TeachersEntity;
import com.restful.daily_click.repository.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class StudentsService {
    @Autowired
    StudentsRepository studentsRepository;

    /**
     * 保存学生类数据
     * @param students
     * @return
     */
    public boolean signup(StudentsEntity students){
        if(isUnique(students.getStuCode())){//判断账号是否唯一
            try{
                studentsRepository.addStudent(students.getStuCode(),students.getName(),students.getSex(),students.getGrade(),students.getTele(), students.getEmail());
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean isUnique(String stu_code){
        StudentsEntity obj = studentsRepository.findByStuCode(stu_code);
        if(null == obj){
            return true;
        }
        return false;
    }

    public JSONObject getStudentByStuCode(String stucode){

        return studentsRepository.findStuInfoByStuCode(stucode);
    }

    @Transactional
    public JSONObject getStuListByClassId(String class_id, int page, int size){
        //Pageable pageable = new PageRequest(page,size);
        Pageable pageable = PageRequest.of(page,size);
        List<JSONObject> json = studentsRepository.getAllStudentByClassId(pageable, class_id);
        int number = studentsRepository.getAllStudentByClassIdNum(class_id);
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);
        return temp;
    }

    @Transactional
    public Object getAllMajority(int page, int size) {
        //Pageable pageable = new PageRequest(page,size);
        Pageable pageable = PageRequest.of(page,size);
        List<JSONObject> json = studentsRepository.getAllMajority(pageable);
        int number = studentsRepository.getAllMajorityNum();
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);

        return temp;
    }

    @Transactional
    public boolean delClassInf(String id) {
        int flag = studentsRepository.delClassInf(id);
        if(flag == 1){
            return true;
        }
        return false;
    }

    @Transactional
    public boolean delStudentInf(String stu_code) {
        int flag = studentsRepository.delStudentInf(stu_code);
        if(flag == 1){
            return true;
        }
        return false;
    }

    @Transactional
    public boolean editStudent(String id, String stu_code, String email, String name, String sex, String tele, String grade) {
        int flag = studentsRepository.editStudent(stu_code, email, name, sex, tele, grade, id);
        if(flag == 1){
            return true;
        }
        return false;
    }

    @Transactional
    public StudentsEntity getStuInfonById(String stu_code) {
        StudentsEntity stu = studentsRepository.getStuInfonById(stu_code);
        return stu;
    }

    @Transactional
    public String getStuGradeFromStuCode(String stu_code) {
        return studentsRepository.getStuCode(stu_code);
    }

    @Transactional
    public JSONObject searchStudentByName(String name) {
        List<JSONObject> json = studentsRepository.searchStudentByName(name);
        JSONObject temp = new JSONObject();
        temp.put("data", json);
        return temp;
    }

}
