package com.restful.daily_click.repository;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.SignRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SignRecordRepository extends JpaRepository<SignRecordEntity,Integer> {

    @Query(value = "select * from sign_record WHERE sign_record.signitem_id = ?1",nativeQuery = true)
    List<JSONObject> getSignRecord(int signtiemid);

    @Query(value = "select * from sign_record WHERE sign_record.signitem_id = ?1 and sign_record.stu_code = ?2",nativeQuery = true)
    JSONObject getStudentSignResult(int signid, String account);

    @Modifying
    @Query(value = "DELETE FROM sign_item WHERE id = ?1",nativeQuery = true)
    @Transactional
    int deleteSign(int id);

    @Query(value = "select sign_item_check from sign_item WHERE sign_item.id = ?1",nativeQuery = true)
    String selectSignItemCheck(int signtiemid);

    @Query(value = "select count(*) from sign_record WHERE sign_record.signitem_id = ?1",nativeQuery = true)
    int getLabSignNumber(int signItemId);

    @Query(value = "select group_number.stu_code " +
            "from group_number " +
            "WHERE group_number.group_id = ?2 and group_number.stu_code not in (" +
            "select sign_record.stu_code from sign_record WHERE sign_record.signitem_id = ?1" +
            ")",nativeQuery = true)
    List<JSONObject> getLabNotSignNumber(int signItemId, Integer sign_item_classid);

    @Query(value = "select sign_record.sign_time from sign_record WHERE sign_record.signitem_id = ?1",nativeQuery = true)
    List<JSONObject> getLabSignList(int signItemId);

    @Query(value = "select * from sign_record WHERE sign_record.signitem_id = ?1 and to_days(sign_record.sign_time) = to_days(now())",nativeQuery = true)
    List<JSONObject> getSignRecordWithTime(int signtiemid);
}
