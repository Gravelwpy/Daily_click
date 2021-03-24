package com.restful.daily_click.service.classService;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.ClassRoomEntity;
import com.restful.daily_click.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassRoomService{
    @Autowired
    ClassRepository classRepository;

    @Transactional
    public JSONObject getClassRoomListPage(int page, int size) {
        Pageable pageable = new PageRequest(page,size);
        List<JSONObject> json = classRepository.findRoomUidsByUserIdPageable(pageable);
        int number = classRepository.getNumberfromClassRoom();
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);

        return temp;
    }

    @Transactional
    public int delRoom(int id) {
        return classRepository.delClassRoom(id);
    }

    @Transactional
    public JSONObject getAllClassRoom() {
        List<JSONObject> json = classRepository.getAllClassRoom();
        JSONObject temp = new JSONObject();
        temp.put("data", json);
        return temp;
    }

    @Transactional
    public JSONObject miniproTeacherGetClassRoomList(String account) {
        List<JSONObject> json = classRepository.miniproTeacherGetClassRoomList(account);
        JSONObject temp = new JSONObject();
        temp.put("data", json);
        return temp;
    }
}
