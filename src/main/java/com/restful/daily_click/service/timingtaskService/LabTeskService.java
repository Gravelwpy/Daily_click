package com.restful.daily_click.service.timingtaskService;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.SignItemEntity;
import com.restful.daily_click.entity.SignResultEntity;
import com.restful.daily_click.repository.SignLeaveRepository;
import com.restful.daily_click.repository.SignRecordRepository;
import com.restful.daily_click.repository.SignResultRepository;
import com.restful.daily_click.service.signService.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class LabTeskService {

    @Autowired
    SignService signService;
    @Autowired
    SignRecordRepository signRecordRepository;
    @Autowired
    SignLeaveRepository signLeaveRepository;
    @Autowired
    SignResultRepository signResultRepository;

    @Transactional
    public void LabTeskDayService() {
        List<JSONObject> list = signService.getAllLablSignList();
        for( int i=0; i < list.size(); i++ ) {
            int signItemId = list.get(i).getInteger("id");
            int signNumber = signRecordRepository.getLabSignNumber(signItemId);
            List<JSONObject> notSignNumberList = signRecordRepository.getLabNotSignNumber(signItemId, list.get(i).getInteger("sign_item_classid"));
            int notSignNumber = notSignNumberList.size();
            int askLeaveNumber = signLeaveRepository.getAskLeaveNumber(signItemId);
            String signItemName = list.get(i).getString("sign_item_name");
            int leaveEarlyNumber = 0;
            // 计算迟到人数
            int lateNumber = 0;
            Integer bighour = Integer.parseInt( list.get(i).getString("sign_item_begin_time").substring(0,2));
            Integer bigmini = Integer.parseInt( list.get(i).getString("sign_item_begin_time").substring(3,5));
            List<JSONObject> signlist = signRecordRepository.getLabSignList(signItemId);
            for( int j=0; j<signlist.size(); j++ ) {
                Integer signhour = Integer.parseInt( String.valueOf(signlist.get(j).getTimestamp("sign_time")).substring(11,13) );
                Integer signmini = Integer.parseInt( String.valueOf(signlist.get(j).getTimestamp("sign_time")).substring(14,16) );
                if( (signhour - bighour) * 60 + ( 60 - bigmini ) + signmini > 120 ) { // 两个小时
                    lateNumber++;
                }
            }
            // save
            SignResultEntity signResultEntity = new SignResultEntity();
            signResultEntity.setSignItemId(signItemId);
            signResultEntity.setSignNumber(signNumber);
            signResultEntity.setNotSignNumber(notSignNumber);
            signResultEntity.setAskLeaveNumber(askLeaveNumber);
            signResultEntity.setLeaveEarlyNumber(leaveEarlyNumber);
            signResultEntity.setLateNumber(lateNumber);
            signResultEntity.setSignItemName(signItemName);
            Date date = new Date();
            String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            Timestamp timestamp = Timestamp.valueOf(nowTime);
            signResultEntity.setCreatTime(timestamp);
            signResultRepository.save(signResultEntity);
        }
    }
}
