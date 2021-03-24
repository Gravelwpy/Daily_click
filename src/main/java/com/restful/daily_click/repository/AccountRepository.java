package com.restful.daily_click.repository;

import com.restful.daily_click.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AccountRepository extends JpaRepository<AccountEntity,Integer> {
    AccountEntity findByAccount(String account);

    @Transactional
    @Modifying
    //-------------插入一条account记录--------------
    @Query(value = "insert into account(account,password,type) values(?1,?2,?3)",nativeQuery = true)
    int addAccount(String account,String password,int type);


    @Transactional
    @Modifying
    //-------------根据account删除一条记录-----------
    @Query(value = "DELETE from account where account = ?1",nativeQuery = true)
    int delAccount(String account);

}
