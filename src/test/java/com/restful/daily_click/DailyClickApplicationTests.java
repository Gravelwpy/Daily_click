package com.restful.daily_click;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.ClassRoomEntity;
import com.restful.daily_click.error.BusinessException;
import com.restful.daily_click.error.EmBusinessError;
import com.restful.daily_click.repository.ClassRepository;
import com.restful.daily_click.service.classService.ClassRoomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DailyClickApplicationTests {
    @Test
    public void contextLoads() throws BusinessException {
//        throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
    }

    @Autowired
    ClassRepository classRepository;


}
