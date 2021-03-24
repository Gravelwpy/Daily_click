package com.restful.daily_click.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "sign_result", schema = "location")
public class SignResultEntity {
    private int id;
    private int signItemId;
    private int signNumber;
    private int notSignNumber;
    private int askLeaveNumber;
    private int leaveEarlyNumber;
    private int lateNumber;
    private String signItemName;
    private Timestamp creatTime;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "sign_item_id", nullable = false)
    public int getSignItemId() {
        return signItemId;
    }

    public void setSignItemId(int signItemId) {
        this.signItemId = signItemId;
    }

    @Basic
    @Column(name = "sign_number", nullable = false)
    public int getSignNumber() {
        return signNumber;
    }

    public void setSignNumber(int signNumber) {
        this.signNumber = signNumber;
    }

    @Basic
    @Column(name = "not_sign_number", nullable = false)
    public int getNotSignNumber() {
        return notSignNumber;
    }

    public void setNotSignNumber(int notSignNumber) {
        this.notSignNumber = notSignNumber;
    }

    @Basic
    @Column(name = "ask_leave_number", nullable = false)
    public int getAskLeaveNumber() {
        return askLeaveNumber;
    }

    public void setAskLeaveNumber(int askLeaveNumber) {
        this.askLeaveNumber = askLeaveNumber;
    }

    @Basic
    @Column(name = "leave_early_number", nullable = false)
    public int getLeaveEarlyNumber() {
        return leaveEarlyNumber;
    }

    public void setLeaveEarlyNumber(int leaveEarlyNumber) {
        this.leaveEarlyNumber = leaveEarlyNumber;
    }

    @Basic
    @Column(name = "late_number", nullable = false)
    public int getLateNumber() {
        return lateNumber;
    }

    public void setLateNumber(int lateNumber) {
        this.lateNumber = lateNumber;
    }

    @Basic
    @Column(name = "sign_item_name", nullable = false, length = 255)
    public String getSignItemName() {
        return signItemName;
    }

    public void setSignItemName(String signItemName) {
        this.signItemName = signItemName;
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
        SignResultEntity that = (SignResultEntity) o;
        return id == that.id &&
                signItemId == that.signItemId &&
                signNumber == that.signNumber &&
                notSignNumber == that.notSignNumber &&
                askLeaveNumber == that.askLeaveNumber &&
                leaveEarlyNumber == that.leaveEarlyNumber &&
                lateNumber == that.lateNumber &&
                Objects.equals(signItemName, that.signItemName) &&
                Objects.equals(creatTime, that.creatTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, signItemId, signNumber, notSignNumber, askLeaveNumber, leaveEarlyNumber, lateNumber, signItemName, creatTime);
    }
}
