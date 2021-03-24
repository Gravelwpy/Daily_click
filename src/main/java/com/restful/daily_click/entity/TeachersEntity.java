package com.restful.daily_click.entity;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "teachers", schema = "location")
@IdClass(TeachersEntityPK.class)
public class TeachersEntity {
    private int id;
    private String teaCode;
    private String name;
    private String sex;
    private String tele;
    private String email;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "tea_code", nullable = false, length = 255)
    public String getTeaCode() {
        return teaCode;
    }

    public void setTeaCode(String teaCode) {
        this.teaCode = teaCode;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "sex", nullable = false, length = 255)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "tele", nullable = false, length = 255)
    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeachersEntity that = (TeachersEntity) o;
        return id == that.id &&
                Objects.equals(teaCode, that.teaCode) &&
                Objects.equals(name, that.name) &&
                Objects.equals(sex, that.sex) &&
                Objects.equals(tele, that.tele) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teaCode, name, sex, tele, email);
    }
}
