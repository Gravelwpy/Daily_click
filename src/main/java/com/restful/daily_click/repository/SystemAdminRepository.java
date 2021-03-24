package com.restful.daily_click.repository;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.SystemAdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: ZhangKaijie
 * @Date: 2019/7/30 14:05
 * @Description:
 */
public interface SystemAdminRepository extends JpaRepository<SystemAdminEntity,Integer> {
    @Query(value = "select * from system_admin where admin_name = ?1",nativeQuery = true)
    SystemAdminEntity findByAdminName(String AdminName);
}
