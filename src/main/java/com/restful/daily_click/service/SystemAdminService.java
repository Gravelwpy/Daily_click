package com.restful.daily_click.service;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.SystemAdminEntity;
import com.restful.daily_click.repository.SystemAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: ZhangKaijie
 * @Date: 2019/7/30 14:02
 * @Description:
 */
@Service
public class SystemAdminService {
    @Autowired
    SystemAdminRepository systemAdminRepository;
    public boolean validateAdmin(SystemAdminEntity data){
        SystemAdminEntity obj = systemAdminRepository.findByAdminName(data.getAdminName());
//        System.out.println(obj.getAdminPass());
        if(obj.getAdminPass().equals(data.getAdminPass())){
            return true;
        }
        return false;
    }
}
