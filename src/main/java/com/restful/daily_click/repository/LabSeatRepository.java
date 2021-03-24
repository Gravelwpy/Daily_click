package com.restful.daily_click.repository;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.GroupItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LabSeatRepository extends JpaRepository<GroupItemEntity,Integer> {

//    @Transactional
//    @Modifying
    @Query(value = "SELECT * from group_item where group_teacher = ?1",nativeQuery = true)
    List<JSONObject> getLabList(String account);

    @Transactional
    @Modifying
    @Query(value = "UPDATE class_room_seat set class_room_seat.labstuname = ?2 where id = ?1",nativeQuery = true)
    int changeSeatName(int seatid, String name);
}
