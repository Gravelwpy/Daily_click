package com.restful.daily_click.repository;

import com.restful.daily_click.entity.WifiRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @Author: ZhangKaijie
 * @Date: 2019/9/23 20:50
 * @Description:
 */
public interface WifiRecordRepository extends JpaRepository<WifiRecordEntity,Integer> {

    @Query(value = "select * from wifi_record where loc_id in ?1",nativeQuery = true)
    ArrayList<WifiRecordEntity> getWifiRecordList(ArrayList<Integer> loc_id_list);

    @Transactional
    @Modifying
    @Query(value = "insert into wifi_record(loc_id,phone_info) values(?1,?2)",nativeQuery = true)
//    @SelectKey(statement = "select @@IDENTITY AS IdentityID" ,keyProperty = "id",keyColumn = "id",resultType = Integer.class,before = false)
    int AddWifiRecord(int loc_id,String phone_info);

//    插入wifi详细信息
    @Transactional
    @Modifying
    @Query(value = "insert into wifi_details(wifi_id, SSID, BSSID, frequency, level, timestamp, capabilities, RSSI, type) values(?1,?2,?3,?4,?5,?6,?7,?8,?9)",nativeQuery = true)
    int AddWifiDetial(int wifi_id, String SSID, String BSSID, String frequency, String level, String timestamp, String capabilities, String RSSI, int type);


    @Query(value = "select last_insert_id()",nativeQuery = true)
    int getLastInsertId();
}
