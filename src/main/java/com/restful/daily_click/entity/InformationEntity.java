package com.restful.daily_click.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "information", schema = "location")
@IdClass(InformationEntityPK.class)
public class InformationEntity {
    private int id;
    private int courseId;
    private String roomId;
    private String classId;
    private String teaCode;
    private int iWeek;
    private int iStart;
    private int iDuration;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "course_id", nullable = false)
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Id
    @Column(name = "room_id", nullable = false, length = 50)
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Id
    @Column(name = "class_id", nullable = false, length = 70)
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Id
    @Column(name = "tea_code", nullable = false, length = 50)
    public String getTeaCode() {
        return teaCode;
    }

    public void setTeaCode(String teaCode) {
        this.teaCode = teaCode;
    }

    @Basic
    @Column(name = "i_week", nullable = false)
    public int getiWeek() {
        return iWeek;
    }

    public void setiWeek(int iWeek) {
        this.iWeek = iWeek;
    }

    @Basic
    @Column(name = "i_start", nullable = false)
    public int getiStart() {
        return iStart;
    }

    public void setiStart(int iStart) {
        this.iStart = iStart;
    }

    @Basic
    @Column(name = "i_duration", nullable = false)
    public int getiDuration() {
        return iDuration;
    }

    public void setiDuration(int iDuration) {
        this.iDuration = iDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InformationEntity that = (InformationEntity) o;
        return id == that.id &&
                courseId == that.courseId &&
                iWeek == that.iWeek &&
                iStart == that.iStart &&
                iDuration == that.iDuration &&
                Objects.equals(roomId, that.roomId) &&
                Objects.equals(classId, that.classId) &&
                Objects.equals(teaCode, that.teaCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, roomId, classId, teaCode, iWeek, iStart, iDuration);
    }
}
