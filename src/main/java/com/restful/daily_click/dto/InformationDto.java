package com.restful.daily_click.dto;

/**
 * @Author: ZhangKaijie
 * @Date: 2019/7/30 9:15
 * @Description:
 */
public class InformationDto {
    private String room_id;
    private int course_id;
    private int i_duration;
    private int i_week;
    private String course_name;
    private int i_start;
    private String tea_name;

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getI_duration() {
        return i_duration;
    }

    public void setI_duration(int i_duration) {
        this.i_duration = i_duration;
    }

    public int getI_week() {
        return i_week;
    }

    public void setI_week(int i_week) {
        this.i_week = i_week;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getI_start() {
        return i_start;
    }

    public void setI_start(int i_start) {
        this.i_start = i_start;
    }

    public String getTea_name() {
        return tea_name;
    }

    public void setTea_name(String tea_name) {
        this.tea_name = tea_name;
    }

    @Override
    public String toString() {
        return "InformationDto{" +
                "room_id='" + room_id + '\'' +
                ", course_id=" + course_id +
                ", i_duration=" + i_duration +
                ", i_week=" + i_week +
                ", course_name='" + course_name + '\'' +
                ", i_start=" + i_start +
                ", tea_name='" + tea_name + '\'' +
                '}';
    }
}
