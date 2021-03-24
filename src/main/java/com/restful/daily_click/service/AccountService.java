package com.restful.daily_click.service;

import com.restful.daily_click.entity.AccountEntity;
import com.restful.daily_click.entity.StudentsEntity;
import com.restful.daily_click.repository.AccountRepository;
import com.restful.daily_click.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.synth.SynthOptionPaneUI;


@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    public AccountEntity findByAccount(String account){
        return accountRepository.findByAccount(account);
    }


    //-------------保存账号----------------
    public boolean saveAccount(AccountEntity account,int type) {
        if(isUnique(account.getAccount())){
            try{
                //加密密码
                String password = Md5Util.inputPassToDbPass(account.getPassword(),account.getAccount());
                accountRepository.addAccount(account.getAccount(),password,type);
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }
    public boolean isUnique(String account){
        AccountEntity obj = accountRepository.findByAccount(account);
        if(null == obj){
            return true;
        }
        return false;
    }

    @Transactional
    public boolean delAccount(String account) {
        int flag = accountRepository.delAccount(account);
        if(flag == 1){
            return true;
        }
        return false;
    }
}
