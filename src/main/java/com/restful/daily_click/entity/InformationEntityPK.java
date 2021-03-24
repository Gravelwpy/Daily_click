package com.restful.daily_click.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;


public class InformationEntityPK implements Serializable {
    private int id;
    private int courseId;
    private String roomId;
    private String classId;
    private String teaCode;

    @Column(name = "id", nullable = false)
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "course_id", nullable = false)
    @Id
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Column(name = "room_id", nullable = false, length = 50)
    @Id
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Column(name = "class_id", nullable = false, length = 70)
    @Id
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Column(name = "tea_code", nullable = false, length = 50)
    @Id
    public String getTeaCode() {
        return teaCode;
    }

    public void setTeaCode(String teaCode) {
        this.teaCode = teaCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InformationEntityPK that = (InformationEntityPK) o;
        return id == that.id &&
                courseId == that.courseId &&
                Objects.equals(roomId, that.roomId) &&
                Objects.equals(classId, that.classId) &&
                Objects.equals(teaCode, that.teaCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, roomId, classId, teaCode);
    }
}
