package com.restful.daily_click.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "sign_record", schema = "location")
public class SignRecordEntity {
    private int id;
    private String stuCode;
    private int signitemId;
    private Timestamp signTime;
    private int row;
    private int col;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "stu_code", nullable = false, length = 255)
    public String getStuCode() {
        return stuCode;
    }

    public void setStuCode(String stuCode) {
        this.stuCode = stuCode;
    }

    @Basic
    @Column(name = "signitem_id", nullable = false)
    public int getSignitemId() {
        return signitemId;
    }

    public void setSignitemId(int signitemId) {
        this.signitemId = signitemId;
    }

    @Basic
    @Column(name = "sign_time", nullable = false)
    public Timestamp getSignTime() {
        return signTime;
    }

    public void setSignTime(Timestamp signTime) {
        this.signTime = signTime;
    }

    @Basic
    @Column(name = "row", nullable = false)
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Basic
    @Column(name = "col", nullable = false)
    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignRecordEntity that = (SignRecordEntity) o;
        return id == that.id &&
                signitemId == that.signitemId &&
                row == that.row &&
                col == that.col &&
                Objects.equals(stuCode, that.stuCode) &&
                Objects.equals(signTime, that.signTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stuCode, signitemId, signTime, row, col);
    }
}
