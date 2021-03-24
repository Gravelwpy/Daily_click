package com.restful.daily_click.repository;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.ClassMajorityEntity;
import com.restful.daily_click.entity.CoursesEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MajorRepository  extends JpaRepository<ClassMajorityEntity,Integer> {

    @Query(value = "select * from class_majority",nativeQuery = true)
    List<JSONObject> getAllMajor(Pageable pageable);

    @Query(value = "select count(*) from class_majority",nativeQuery = true)
    int getAllMajorNum();

    @Transactional
    @Modifying
    @Query(value = "UPDATE class_majority set majority_name=?1 where id = ?2",nativeQuery = true)
    int editMajor(String majority_name, String id);

    @Modifying
    @Query(value = "DELETE FROM class_majority WHERE id = ?1",nativeQuery = true)
    @Transactional
    int delMajoy(String id);

    @Query(value = "select * FROM class_majority WHERE majority_name = ?1",nativeQuery = true)
    ClassMajorityEntity findMajoyByName(String majority_name);

    @Transactional
    @Modifying
    @Query(value = "insert into class_majority(majority_name) values(?1)",nativeQuery = true)
    int addMajority(String majority_name);
}
