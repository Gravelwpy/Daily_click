package com.restful.daily_click.service.questionService;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.JsonResult;
import com.restful.daily_click.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public JsonResult addQuestion(String desc) {
        return new JsonResult(questionRepository.addQuestion(desc));
    }

    @Transactional
    public JSONObject getAllQuestions(int page, int size) {
        Pageable pageable = new PageRequest(page,size);
        List<JSONObject> json = questionRepository.getAllQuestions(pageable);
        int number = questionRepository.getAllQuestionNum();
        JSONObject temp = new JSONObject();
        temp.put("number",number );
        temp.put("data", json);

        return temp;
    }


    @Transactional
    public int delQuestion(int id) {
        return questionRepository.delQuestion(id);
    }
}
