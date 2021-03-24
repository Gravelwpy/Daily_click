package com.restful.daily_click.repository;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.SignItemEntity;
import com.restful.daily_click.entity.SignRecordEntity;
import com.restful.daily_click.entity.StudentsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SignRepository extends JpaRepository<SignItemEntity,Integer> {

    @Query(value = "select * from sign_item WHERE sign_item_originator = ?1",nativeQuery = true)
    List<JSONObject> getAllSign(Pageable pageable, String originator);

    @Query(value = "select count(*) from sign_item WHERE sign_item_originator = ?1",nativeQuery = true)
    int getAllSignNum(String originator);

    @Query(value = "select * from sign_item WHERE sign_item_classid = ?1",nativeQuery = true)
    List<JSONObject> getStudentAllSign(Pageable pageable, String grade);

    @Query(value = "select count(*) from sign_item WHERE sign_item_classid = ?1",nativeQuery = true)
    int getStudentAllSignNum(String grade);

    //----根据sign_item_id获取签到项信息--------
    @Query(value = "select sign_item.sign_item_classid, sign_item.sign_item_name,sign_item.sign_item_sign_type,sign_item.sign_item_latitude,sign_item.id,sign_item.sign_item_in_signing," +
            "sign_item.sign_item_longitude,sign_item.sign_item_radius,sign_item.sign_item_begin_time,sign_item.sign_item_end_time," +
            "sign_item.sign_item_classroomid,sign_item.sign_item_type," +
            "class_room.room_name,class_room.id as  classid," +
            "teachers.name " +
            "from sign_item,class_room,teachers " +
            "WHERE sign_item.id = ?1 AND class_room.id = sign_item.sign_item_classroomid AND sign_item.sign_item_originator = teachers.tea_code",nativeQuery = true)
    JSONObject getSigItem(int signid);

    @Query(value = "select count(*) from sign_record WHERE stu_code = ?1",nativeQuery = true)
    int getIfStudentSign(String account);

    //--------根据signitem_id查找签到数量-------
    @Query(value = "select count(*) from sign_record WHERE signitem_id = ?1 and stu_code = ?2",nativeQuery = true)
    int getStudentIfSign(Integer id, String account);

    @Query(value = "select sign_item.id,sign_item.sign_item_type,sign_item.sign_item_name,sign_item.sign_item_originator,sign_item.sign_item_classid," +
            "sign_item.sign_item_groupid,sign_item.sign_item_latitude,sign_item.sign_item_longitude,sign_item.sign_item_radius,sign_item.sign_item_begin_time," +
            "sign_item.sign_item_end_time,sign_item.sign_item_check,sign_item.sign_item_sign_type,sign_item.sign_item_classroomid,sign_item.sign_item_in_signing " +
            "from sign_item,group_item,group_number " +
            "WHERE group_number.stu_code = ?1 and group_number.group_id = group_item.id and sign_item.sign_item_classid = group_item.id and sign_item.sign_item_type = 2",nativeQuery = true)
    List<JSONObject> getStudentLabSign(Pageable pageable, String account);

    @Query(value = "select count(*) from sign_item WHERE sign_item_classid = ?1 and sign_item.sign_item_type = 2",nativeQuery = true)
    int getStudentLabSignNum(String grade);

    //------获取班级签到项信息---
    @Query(value = "select * from sign_item WHERE sign_item_classid = ?1 and sign_item.sign_item_type = 1",nativeQuery = true)
    List<JSONObject> getStudentNomalSign(Pageable pageable, String grade);

    @Query(value = "select count(*) from sign_item WHERE sign_item_classid = ?1 and sign_item.sign_item_type =1",nativeQuery = true)
    int getStudentNomalSignNum(String grade);


    @Query(value = "select sign_record.sign_time as signtime, " +
            "sign_item.sign_item_type, sign_item.sign_item_name,sign_item.id," +
            "teachers.name as tname, class_room.room_name " +
            "from sign_record,sign_item,teachers,class_room " +
            "WHERE sign_record.stu_code = ?1 AND sign_record.signitem_id = sign_item.id " +
            "AND sign_item.sign_item_originator = teachers.tea_code AND sign_item.sign_item_classroomid = class_room.id",nativeQuery = true)
    List<JSONObject> getStudeentRecorded(Pageable pageable, String account);

    @Query(value = "select count(*) from sign_record,sign_item,teachers,class_room " +
            "WHERE sign_record.stu_code = ?1 AND sign_record.signitem_id = sign_item.id " +
            "AND sign_item.sign_item_originator = teachers.tea_code AND sign_item.sign_item_classroomid = class_room.id",nativeQuery = true)
    int getStudeentRecordedNum(String account);

    @Query(value = "select * from sign_item WHERE sign_item.sign_item_originator = ?1",nativeQuery = true)
    List<JSONObject> getTeacherAddSignRecorded(Pageable pageable, String account);

    @Query(value = "select count(*) from sign_item WHERE sign_item.sign_item_originator = ?1",nativeQuery = true)
    int getTeacherAddSignRecordedNum(String account);

    @Query(value = "select * from sign_item WHERE sign_item.sign_item_originator = ?1 AND sign_item.sign_item_type = 1",nativeQuery = true)
    List<JSONObject> getTeacherNomalSignList(Pageable pageable, String account);

    @Query(value = "select count(*) from sign_item WHERE sign_item.sign_item_originator = ?1 AND sign_item.sign_item_type = 1",nativeQuery = true)
    int getTeacherNomalSignListNum(String account);

    @Query(value = "select * from sign_item WHERE sign_item.sign_item_originator = ?1 AND sign_item.sign_item_type = 2",nativeQuery = true)
    List<JSONObject> getTeacherLablSignList(Pageable pageable, String account);

    @Query(value = "select count(*) from sign_item WHERE sign_item.sign_item_originator = ?1 AND sign_item.sign_item_type = 2",nativeQuery = true)
    int getTeacherLablSignListNum(String account);

    @Transactional
    @Modifying
    @Query(value = "UPDATE sign_item set sign_item.sign_item_in_signing = 1 where sign_item.id = ?1",nativeQuery = true)
    int teacherBeginSign(int signtiemid);

    @Transactional
    @Modifying
    @Query(value = "UPDATE sign_item set sign_item.sign_item_in_signing = 0 where sign_item.id = ?1",nativeQuery = true)
    int teacherStopSign(int signtiemid);

    @Query(value = "select * from sign_record WHERE sign_record.signitem_id = ?1",nativeQuery = true)
    List<SignRecordEntity> getSigItemRecord(int signid);

    //--------获取未签到学生---------
    @Query(value = "select students.id,students.stu_code as stuCode,students.name,students.sex,students.grade,students.tele,students.email " +
            "from sign_record,sign_item,students " +
            "WHERE sign_item.id = ?1 AND students.grade = sign_item.sign_item_classid AND students.id NOT IN ( " +
            "select students.id from sign_record,sign_item,students  " +
            "WHERE sign_item.id = ?1 AND students.grade = sign_item.sign_item_classid AND sign_record.stu_code = students.stu_code ) " +
            "GROUP BY students.stu_code",nativeQuery = true)
    List<JSONObject> getNotSignStudent(int signid);

    @Query(value = "select students.id,students.stu_code as stuCode,students.name,students.sex,students.grade,students.tele,students.email " +
            "from sign_record,sign_item,students,group_item,group_number " +
            "WHERE sign_item.id = ?1 AND sign_item.sign_item_classid = group_number.group_id AND group_number.stu_code = students.stu_code AND students.id NOT IN ( " +
            "select students.id from sign_record,sign_item,students  " +
            "WHERE sign_record.signitem_id = ?1 AND sign_record.stu_code = students.stu_code ) " +
            "GROUP BY students.stu_code",nativeQuery = true)
    List<JSONObject> getLabNotSignStudent(int signid);

    //-------查询请假人数-------
    @Query(value = "select count(*) from sign_leave WHERE sign_leave.sign_item_id = ?1 and student_id = ?2",nativeQuery = true)
    int getStudentIfAskLeave(Integer id, String account);

    @Query(value = "select * from sign_item WHERE sign_item.sign_item_type = 2",nativeQuery = true)
    List<JSONObject> getAllLablSignList();

    @Query(value = "select sign_result.id,sign_result.sign_item_id,sign_result.sign_number,sign_result.not_sign_number,sign_result.ask_leave_number,sign_result.leave_early_number,sign_result.late_number,sign_result.sign_item_name,sign_result.creat_time " +
            "from sign_result,sign_item " +
            "WHERE sign_result.creat_time > ?1 AND sign_result.creat_time < ?2 AND sign_result.sign_item_id = sign_item.id AND sign_item.sign_item_classid = ?3",nativeQuery = true)
    List<JSONObject> getSignResultBetween(String begintime, String endtime, int labid);

    @Query(value = "select count(*) from sign_leave WHERE sign_leave.sign_item_id = ?1",nativeQuery = true)
    Integer getsignLieveNumber(Integer sign_item_id);

    @Query(value = "select * " +
            "from sign_record " +
            "WHERE sign_record.signitem_id = ?1 ",nativeQuery = true)
    List<JSONObject> getsignLateList(Integer sign_item_id);

    @Query(value = "select sign_item.sign_item_begin_time as listbegintime,sign_item.sign_item_end_time as listendtime from sign_item WHERE sign_item.id = ?1",nativeQuery = true)
    JSONObject getSigItemCreatTimeAndEndTime(Integer sign_item_id);

    @Query(value = "select sign_item.id,sign_item.sign_item_type,sign_item.sign_item_name,sign_item.sign_item_originator,sign_item.sign_item_classid," +
            "sign_item.sign_item_begin_time,sign_item.sign_item_end_time " +
            "from sign_item,teachers where sign_item.sign_item_originator = teachers.tea_code AND teachers.id = ?1",nativeQuery = true)
    List<JSONObject> getsignItemByTid(Pageable pageable, int tid);

    @Query(value = "select count(sign_item.id) from sign_item where sign_item_originator = ?1",nativeQuery = true)
    int getsignItemByTidNum(int tid);

    @Query(value = "select * from sign_item where sign_item.id = ?1",nativeQuery = true)
    JSONObject getClassItem(int signitemid);

    @Query(value = "select count(students.stu_code) from students where students.grade = ?1",nativeQuery = true)
    Integer getClassNumberByClassid(String sign_item_classid);

    //-----获取已签到人数-----------//
    @Query(value = "select count(sign_record.stu_code) from sign_record where sign_record.signitem_id = ?1",nativeQuery = true)
    Integer getSignNumber(Integer id);

    @Query(value = "select count(sign_leave.sign_item_id) from sign_leave where sign_leave.sign_item_id = ?1",nativeQuery = true)
    Integer getAskLeveNumber(Integer id);

    @Query(value = "select count(*) from sign_record WHERE signitem_id = ?1 and stu_code = ?2 " +
            "and sign_record.sign_time >= date(now()) and sign_record.sign_time < DATE_ADD(date(now()),INTERVAL 1 DAY)",nativeQuery = true)
    int getStudentIfSignWithTime(Integer id, String account);

    @Query(value = "select count(*) from sign_leave WHERE sign_leave.sign_item_id = ?1 and student_id = ?2 " +
            "and sign_leave.creat_time >= date(now()) and sign_leave.creat_time < DATE_ADD(date(now()),INTERVAL 1 DAY)",nativeQuery = true)
    int getStudentIfAskLeaveWithTime(Integer id, String account);
}
