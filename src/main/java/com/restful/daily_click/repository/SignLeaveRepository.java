package com.restful.daily_click.repository;

import com.restful.daily_click.entity.SignLeaveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SignLeaveRepository extends JpaRepository<SignLeaveEntity,Integer> {

    @Query(value = "select count(*) from sign_leave WHERE sign_leave.sign_item_id = ?1",nativeQuery = true)
    int getAskLeaveNumber(int signItemId);
}
