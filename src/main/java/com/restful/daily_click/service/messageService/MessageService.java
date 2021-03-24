package com.restful.daily_click.service.messageService;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.MessageEntity;
import com.restful.daily_click.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: ZhangKaijie
 * @Date: 2019/7/28 11:30
 * @Description:
 */
@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    public ArrayList<JSONObject> getMessageList(String keylike, String code , int page){
        Pageable pageable = PageRequest.of(page, 10);
        return messageRepository.findByMsgTitleLikeOrMsgContentLikeByOrderByInsertTimeAtDesc(code,keylike,keylike,pageable);
    }

    @Transactional
    public int addMessage(String msgtitle, String msgcontent, String teccode, String classid, String iconurl) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMsgTitle(msgtitle);
        messageEntity.setMsgContent(msgcontent);
        messageEntity.setTeaCode(teccode);
        messageEntity.setClassId(classid);
        messageEntity.setIconUrl(iconurl);

        Date date = new Date();
        long times = date.getTime();
        System.out.println(times);
        messageEntity.setInsertTime(String.valueOf(times));

        MessageEntity obj = messageRepository.save(messageEntity);
        if( obj == null ) {
            return 0;
        } else {
            return 1;
        }
    }

    @Transactional
    public JSONObject getAllMessageByPage(int page, int size, String originator) {
        Pageable pageable = new PageRequest(page,size);
        List<JSONObject> json = messageRepository.getAllMessageByPage(pageable, originator);
        int number = messageRepository.getAllMessageByPageNum(originator);
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);

        return temp;
    }

    @Transactional
    public Object delectMessage(int id) {
        return messageRepository.delectMessage(id);
    }
}
