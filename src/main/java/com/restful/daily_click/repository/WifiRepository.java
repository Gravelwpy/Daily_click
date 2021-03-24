package com.restful.daily_click.repository;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.WifiInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


public interface WifiRepository extends JpaRepository<WifiInfoEntity,Integer> {
    @Transactional
    @Modifying
    @Query(value = "insert into wifi_info(wifi_rec_id) values(?1)",nativeQuery = true)
    int addWifi(int wifi_rec_id);

    @Query(value = "select * from wifi_info where wifi_rec_id = ?1",nativeQuery = true)
    ArrayList<WifiInfoEntity> getWifiList(int wifi_rec_id);

    @Query(value = "select last_insert_id()",nativeQuery = true)
    int getLastInsertId();

//    根据wifi的level获取数据
    @Query(value = "select wifi_details.SSID,wifi_details.BSSID,location_info.loc_id,COUNT(location_info.loc_id)  from wifi_details,location_info,wifi_info,wifi_record " +
            "where wifi_record.loc_id = location_info.loc_id " +
            "and  wifi_record.wifi_rec_id = wifi_info.wifi_rec_id " +
            "and wifi_info.wifi_id = wifi_details.wifi_id " +
            "and abs(wifi_details.level - ?1) < 8 " +
            "and BSSID = ?2 " +
            "GROUP BY location_info.loc_id",nativeQuery = true)
    ArrayList<JSONObject> getResultByBSSID(String level, String BSSID);

    @Query(value = "select wifi_details.SSID,wifi_details.BSSID,location_info.loc_id,COUNT(location_info.loc_id)  from wifi_details,location_info,wifi_info,wifi_record " +
            "where wifi_record.loc_id = location_info.loc_id " +
            "and  wifi_record.wifi_rec_id = wifi_info.wifi_rec_id " +
            "and wifi_info.wifi_id = wifi_details.wifi_id " +
            "and abs(wifi_details.level - ?1) < 8 " +
            "and BSSID = ?2 and wifi_details.type = ?3 " +
            "GROUP BY location_info.loc_id",nativeQuery = true)
    ArrayList<JSONObject> getResultByBSSIDSecond(String level, String BSSID, int type);


//    根据loc_id获取定位点经纬度
    @Query(value = "select * from location_info where loc_id = ?1",nativeQuery = true)
    ArrayList<JSONObject> getMatchLocal(String loc_id);

    @Query(value = "select * from wifi_record where loc_id = ?1",nativeQuery = true)
    ArrayList<JSONObject> getLocalList(String loc_id);

    @Query(value = "select wifi_info.wifi_id,wifi_details.BSSID,wifi_details.SSID,wifi_details.level " +
            "from wifi_info,wifi_details " +
            "where wifi_info.wifi_rec_id = ?1 and wifi_info.wifi_id = wifi_details.wifi_id",nativeQuery = true)
    ArrayList<JSONObject> getWifiRecordDetail(String wifi_rec_id);
//    根据wifi_rec_id获取wifi_id
    @Query(value = "select wifi_info.wifi_id " +
            "from wifi_info " +
            "where wifi_info.wifi_rec_id = ?1",nativeQuery = true)
    ArrayList<JSONObject> getWifiId(String wifi_rec_id);

    //    根据wifi_id获取wifi_detial
    @Query(value = "select * from wifi_details where wifi_details.wifi_id = ?1",nativeQuery = true)
    ArrayList<JSONObject> getWifiDetails(String wifi_id);

    //根据 wifi BSSID 求 SSID
    @Query(value = "select SSID from wifi_details where wifi_details.BSSID = ?1 LIMIT 1",nativeQuery = true)
    ArrayList<JSONObject> getSSIDByBSSID(String BSSID);

    @Transactional
    @Modifying
    @Query(value = "insert into test_record(latitude, longitude, wifidetail, detailnum) values(?1, ?2, ?3, ?4)",nativeQuery = true)
    void saveTestRecord(String latitude, String longitude, String wifidetail, int detailnum);

    @Transactional
    @Modifying
    @Query(value = "insert into test_record(latitude, longitude, wifidetail, detailnum, remarks) values(?1, ?2, ?3, ?4, ?5)",nativeQuery = true)
    void saveTestRecordIncludeDis(String latitude, String longitude, String wifidetail, int detailnum, Double remarks);

    @Query(value = "select * from test_record where test_record.id = 511 or test_record.id = 512",nativeQuery = true)
    ArrayList<JSONObject> getDifPhoneInf();

    @Query(value = "select * from test_record where test_record.id = 511 or test_record.id = ?1",nativeQuery = true)
    ArrayList<JSONObject> getPointList(Integer id);

    @Transactional
    @Modifying
    @Query(value = "insert into wifi_test_record_detail(SSID, BSSID, frequency, level, timestamp, capabilities) values(?1, ?2, ?3, ?4, ?5, ?6)",nativeQuery = true)
    void saveTestRecordDetials(String ssid, String bssid, String frequency, String level, String timestamp, String capabilities);


    // NN
    @Query(value = "SELECT wifi_details.level - ?1 AS levels " +
            "FROM wifi_record,wifi_info,wifi_details " +
            "WHERE wifi_record.loc_id = ?2 " +
            "AND wifi_record.wifi_rec_id = wifi_info.wifi_rec_id " +
            "AND wifi_info.wifi_id = wifi_details.wifi_id " +
            "AND wifi_details.BSSID=?3",nativeQuery = true)
    ArrayList<JSONObject> getLevelList(Integer level, String loc_id, String BSSID);

//    根据localid获取地点信息
    @Query(value = "SELECT longitude,latitude FROM location_info WHERE loc_id = ?1",nativeQuery = true)
    ArrayList<JSONObject> getLocalInfoByLocalId(String key);
}
