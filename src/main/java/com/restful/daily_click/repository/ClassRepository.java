package com.restful.daily_click.repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.ClassInfoEntity;
import com.restful.daily_click.entity.ClassRoomEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;


public interface ClassRepository extends JpaRepository<ClassRoomEntity,Integer> {

    //------------得到班级名字集合------------
    @Query(value = "select class_id,class_name as  name from class_info",nativeQuery = true)
    List<JSONObject> getCLassNameList();

    @Query(value = "select * from class_room",nativeQuery = true)
    List<JSONObject> findRoomUidsByUserIdPageable(Pageable pageable);

    @Query(value = "select count(*) from class_room",nativeQuery = true)
    int getNumberfromClassRoom();

    @Modifying
    @Query(value = "DELETE FROM class_room WHERE id = ?1",nativeQuery = true)
    @Transactional
    int delClassRoom(int id);

    @Modifying
    @Query(value = "UPDATE class_room SET room_name = ?1, capacity = ?2 WHERE id = ?3 ",nativeQuery = true)
    @Transactional
    int changeClassData(Object room_name, Object capacity, Integer id);

    @Modifying
    @Query(value = "DELETE FROM class_room_seat WHERE room_id = ?1",nativeQuery = true)
    @Transactional
    int delectAllById(Integer id);

    @Query(value = "select * from class_room_seat WHERE room_id = ?1",nativeQuery = true)
    List<JSONObject> getSeatArr(int id);

    @Modifying
    //---------添加教室信息----------
    @Query(value = "INSERT INTO class_room(room_name, capacity, length) values(?1,?2,?3)",nativeQuery = true)
    @Transactional
    int addNewClassRoom(String room_name, Integer capacity, int length);

    @Query(value = "SELECT LAST_INSERT_ID()",nativeQuery = true)
    int getLastInsert();

    //----得到教室座位信息-------
    @Query(value = "select * from class_room_seat WHERE room_id = ?1",nativeQuery = true)
    JSONArray getSeatData(int classroomid);

    //-----得到教室信息---------
    @Query(value = "select * from class_room WHERE id = ?1",nativeQuery = true)
    JSONObject getRoomData(int classroomid);


    @Query(value = "select * from class_info where majority_id = ?1",nativeQuery = true)
    List<JSONObject> getAllClassByMajorId(Pageable pageable, int majorid);

    @Query(value = "select count(*) from class_info where majority_id = ?1",nativeQuery = true)
    int getAllClassByMajorIdNum(int majorid);

    @Transactional
    @Modifying
    @Query(value = "DELETE from class_info where id = ?1",nativeQuery = true)
    int delClass(String id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE class_info set class_id=?1, class_name=?2, class_year=?3 where id = ?4",nativeQuery = true)
    int editClass(String class_id, String class_name, String class_year, String id);


    @Query(value = "select * FROM class_info WHERE class_name like %?1%",nativeQuery = true)
    ClassInfoEntity findClassByClassName(String class_name);


    @Transactional
    @Modifying
    @Query(value = "insert into class_info(class_id,class_name,class_year,majority_id) values(?1,?2,?3,?4)",nativeQuery = true)
    int addClass(String class_id, String class_name, String class_year, String majority_id);

    @Query(value = "select class_id,class_name as name from class_info",nativeQuery = true)
    List<JSONObject> creatNewSignGetclassList();

    @Query(value = "select id, room_name AS name  from class_room",nativeQuery = true)
    List<JSONObject> getAllClassRoom();

    @Transactional
    @Modifying
    @Query(value = "UPDATE class_room set class_room.length = ?1 where id = ?2",nativeQuery = true)
    int changeLength(int length, int id);

    @Query(value = "select class_info.class_id,class_info.class_name as name " +
            "from class_info,information " +
            "WHERE information.tea_code = ?1 AND information.class_id = class_info.class_id " +
            "GROUP BY class_info.class_id",nativeQuery = true)
    List<JSONObject> miniPrograGetTeacherTeachClass(String account);

    @Query(value = "select class_room.id, class_room.room_name AS name  " +
            "from class_room,information " +
            "WHERE information.tea_code = ?1 AND information.room_id = class_room.room_name",nativeQuery = true)
    List<JSONObject> miniproTeacherGetClassRoomList(String account);


    @Query(value = "select class_room_seat.id,class_room_seat.room_id,class_room_seat.is_seat,class_room_seat.row,class_room_seat.col,class_room.length,class_room_seat.labstuname,class_room.room_name " +
            "from class_room,class_room_seat,group_item " +
            "WHERE group_item.id = ?1 and group_item.group_name = class_room.room_name and class_room.id = class_room_seat.room_id",nativeQuery = true)
    JSONArray getLabSeatInfoById(int seatid);
}
