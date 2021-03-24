package com.restful.daily_click.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class TeachersEntityPK implements Serializable {
    private int id;
    private String teaCode;

    @Column(name = "id", nullable = false)
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "tea_code", nullable = false, length = 255)
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
        TeachersEntityPK that = (TeachersEntityPK) o;
        return id == that.id &&
                Objects.equals(teaCode, that.teaCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teaCode);
    }
}
