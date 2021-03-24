package com.restful.daily_click.repository;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.GroupItemEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GroupRepository extends JpaRepository<GroupItemEntity,Integer> {


    @Query(value = "select * from group_item",nativeQuery = true)
    List<JSONObject> getAllGroupByPage(Pageable pageable);

    @Query(value = "select count(*) from group_item",nativeQuery = true)
    int getAllGroupByPageNum();


    @Transactional
    @Modifying
    @Query(value = "UPDATE group_item set group_name=?1, group_teacher=?2, group_introduce=?3 where id = ?4",nativeQuery = true)
    int editGroup(String groupname, String tcode, String groupiintroduce, String id);

    @Transactional
    @Modifying
    @Query(value = "DELETE from group_item where id = ?1",nativeQuery = true)
    int delGroup(String id);

    @Query(value = "select group_item.id ,group_item.group_name as name " +
            "from group_item " +
            "WHERE group_item.group_teacher = ?1",nativeQuery = true)
    List<JSONObject> miniPrograGetTeacherGroupList(String account);

    @Transactional
    @Modifying
    @Query(value = "DELETE from group_number where id = ?1",nativeQuery = true)
    int delLabNumber(String id);

    @Transactional
    @Modifying
    @Query(value = "DELETE from group_number where group_number.group_id = ?1",nativeQuery = true)
    void delGroupNumber(String id);

    @Query(value = "select count(group_number.stu_code) " +
            "from sign_item,group_item,group_number " +
            "WHERE sign_item.id = ?1 AND sign_item.sign_item_classid = group_item.id AND group_number.group_id = group_item.id",nativeQuery = true)
    Integer getLabNumber(Integer sign_item_id);
}
