package com.restful.daily_click.repository;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.LocationInfoEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


public interface LocationRepository extends JpaRepository<LocationInfoEntity,Integer> {

    @Query(value = "select loc_id, loc_name as name, longitude, latitude, test_point as subname, public_point from location_info where account =?1",nativeQuery = true)
    ArrayList<JSONObject> getLocationInfoEntitiesByAccount(String account);

    @Query(value = "select distinct loc_name from location_info where account = ?1",nativeQuery = true)
    ArrayList<String> getLocNameByAccount(String account);

    @Query(value = "select * from location_info where account = ?1 and loc_name = ?2",nativeQuery = true)
    ArrayList<LocationInfoEntity> getTestPoint(String account,String loc_name);

    @Transactional
    @Modifying
    int deleteLocationInfoEntitiesByLocIdAndAccount(int loc_id,String account);

    @Transactional
    @Modifying
    @Query(value = "insert into location_info(account,loc_name,longitude,latitude,test_point) values(?1,?2,?3,?4,?5)",nativeQuery = true)
    int addTestPoint(String account,String loc_name,double longitude,double latitude,String test_point);

    @Query(value = "select loc_id from location_info where account = ?1",nativeQuery = true)
    ArrayList<Integer> getLocIdByAccount(String account);

    @Query(value = "select location_info.loc_id as id, teachers.name, location_info.loc_name, location_info.test_point as loc_number, location_info.longitude, location_info.latitude " +
            "from location_info,teachers " +
            "where location_info.account = teachers.tea_code GROUP BY location_info.loc_name",nativeQuery = true)
    List<JSONObject> getAllLocalName(Pageable pageable);

    @Query(value = "select count(location_info.loc_name) " +
            "from location_info,teachers " +
            "where location_info.account = teachers.tea_code GROUP BY location_info.loc_name",nativeQuery = true)
    List<JSONObject> getAllLocalNameNum();

    @Transactional
    @Modifying
    @Query(value = "UPDATE location_info set loc_name=?1, test_point=?2, longitude=?3, latitude=?4 where loc_id = ?5",nativeQuery = true)
    int editPoint(String loc_name, String test_point, String longitude, String latitude, String id);

    @Query(value = "select location_info.loc_id as id, teachers.name, " +
            "location_info.loc_name, location_info.test_point as loc_number, location_info.longitude, location_info.latitude " +
            "from location_info,teachers " +
            "where location_info.account = teachers.tea_code AND location_info.loc_name = ?1",nativeQuery = true)
    List<JSONObject> getTestPointByLocName(Pageable pageable, String loc_name);

    @Query(value = "select count(location_info.loc_name) " +
            "from location_info,teachers " +
            "where location_info.account = teachers.tea_code AND location_info.loc_name = ?1",nativeQuery = true)
    List<JSONObject> getTestPointByLocNameNum(String loc_name);

    @Transactional
    @Modifying
    @Query(value = "DELETE from location_info where location_info.loc_id = ?1",nativeQuery = true)
    int delLocationById(int loc_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE from location_info where location_info.loc_name = ?1",nativeQuery = true)
    int delLocationByLocName(String loc_name);
}
