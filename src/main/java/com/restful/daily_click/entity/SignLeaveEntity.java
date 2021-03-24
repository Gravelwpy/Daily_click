package com.restful.daily_click.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "sign_leave", schema = "location")
public class SignLeaveEntity {
    private int id;
    private String studentId;
    private String reason;
    private Timestamp creatTime;
    private int signItemId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "student_id", nullable = false, length = 255)
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Basic
    @Column(name = "reason", nullable = false, length = 255)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Basic
    @Column(name = "creat_time", nullable = false)
    public Timestamp getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Timestamp creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignLeaveEntity that = (SignLeaveEntity) o;
        return id == that.id &&
                Objects.equals(studentId, that.studentId) &&
                Objects.equals(reason, that.reason) &&
                Objects.equals(creatTime, that.creatTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentId, reason, creatTime);
    }

    @Basic
    @Column(name = "sign_item_id", nullable = false)
    public int getSignItemId() {
        return signItemId;
    }

    public void setSignItemId(int signItemId) {
        this.signItemId = signItemId;
    }
}
