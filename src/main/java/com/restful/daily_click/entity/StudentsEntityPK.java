package com.restful.daily_click.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;


public class StudentsEntityPK implements Serializable {
    private int id;
    private String stuCode;

    @Column(name = "id", nullable = false)
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "stu_code", nullable = false, length = 255)
    @Id
    public String getStuCode() {
        return stuCode;
    }

    public void setStuCode(String stuCode) {
        this.stuCode = stuCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentsEntityPK that = (StudentsEntityPK) o;
        return id == that.id &&
                Objects.equals(stuCode, that.stuCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stuCode);
    }
}
