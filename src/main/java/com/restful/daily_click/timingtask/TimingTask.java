package com.restful.daily_click.timingtask;

import com.restful.daily_click.service.timingtaskService.LabTeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class TimingTask {
    @Autowired
    LabTeskService labTeskService;

//    @Scheduled(cron = "* * 22 * * ? ")
    //或直接指定时间间隔，例如：5秒
    // 每天统计实验室签到的出勤率
//    @Scheduled(fixedRate=50000)
//    private void configureTasks() {
//        labTeskService.LabTeskDayService();
//    }
}
