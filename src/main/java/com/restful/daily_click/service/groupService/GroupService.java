package com.restful.daily_click.service.groupService;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.GroupEntity;
import com.restful.daily_click.entity.GroupItemEntity;
import com.restful.daily_click.entity.GroupNumberEntity;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.repository.GroupNumberRepository;
import com.restful.daily_click.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GroupNumberRepository groupNumberRepository;

    @Transactional
    public JsonResult addGroup(String groupname, String groupiintroduce, String tcode) {
        GroupItemEntity groupItemEntity = new GroupItemEntity();
        groupItemEntity.setGroupName(groupname);
        groupItemEntity.setGroupIntroduce(groupiintroduce);
        groupItemEntity.setGroupTeacher(tcode);
        GroupItemEntity obj = groupRepository.save(groupItemEntity);
        if( obj != null ) {
            return new JsonResult("保存成功");
        } else {
            return new JsonResult(40003,"添加失败");
        }
    }

    @Transactional
    public JSONObject getAllGroupByPage(int page, int size) {
        Pageable pageable = new PageRequest(page,size);
        List<JSONObject> json = groupRepository.getAllGroupByPage(pageable);
        int number = groupRepository.getAllGroupByPageNum();
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);

        return temp;
    }

    @Transactional
    public boolean editGroup(String id, String groupname, String tcode, String groupiintroduce) {
        int flag = groupRepository.editGroup(groupname, tcode, groupiintroduce, id);
        if(flag == 1){
            return true;
        }
        return false;
    }

    @Transactional
    public boolean delGroup(String id) {
        int flag = groupRepository.delGroup(id);
        groupRepository. delGroupNumber(id);
        if(flag == 1){
            return true;
        }
        return false;
    }

    @Transactional
    public List<JSONObject> miniPrograGetTeacherGroupList(String account) {
        return groupRepository.miniPrograGetTeacherGroupList(account);
    }

    @Transactional
    public JSONObject getGroupNumberByPage(int page, int size, int labid) {
        Pageable pageable = new PageRequest(page,size);
        List<JSONObject> json = groupNumberRepository.getGroupNumberByPage(pageable, labid);
        int number = groupNumberRepository.getGroupNumberByPageNum(labid);
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);

        return temp;
    }

    @Transactional
    public boolean delLabNumber(String id) {
        int flag = groupRepository.delLabNumber(id);
        if(flag == 1){
            return true;
        }
        return false;
    }

    @Transactional
    public JsonResult addItemNumber(String stucode, int groupid) {
        GroupNumberEntity groupNumberEntity = new GroupNumberEntity();
        groupNumberEntity.setGroupId(groupid);
        groupNumberEntity.setStuCode(stucode);
        GroupNumberEntity obj = groupNumberRepository.save(groupNumberEntity);
        if( obj != null ) {
            return new JsonResult("保存成功");
        } else {
            return new JsonResult(40003,"添加失败");
        }
    }
}
