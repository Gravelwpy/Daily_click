package com.restful.daily_click.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Author: ZhangKaijie
 * @Date: 2019/7/28 21:15
 * @Description:
 */
public class MessageEntityPK implements Serializable {
    private int id;
    private String teaCode;
    private String classId;

    @Column(name = "id", nullable = false)
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "tea_code", nullable = false, length = 50)
    @Id
    public String getTeaCode() {
        return teaCode;
    }

    public void setTeaCode(String teaCode) {
        this.teaCode = teaCode;
    }

    @Column(name = "class_id", nullable = false, length = 50)
    @Id
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEntityPK that = (MessageEntityPK) o;
        return id == that.id &&
                Objects.equals(teaCode, that.teaCode) &&
                Objects.equals(classId, that.classId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teaCode, classId);
    }
}
