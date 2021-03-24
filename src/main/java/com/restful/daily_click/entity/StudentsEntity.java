package com.restful.daily_click.entity;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "students", schema = "location")
@IdClass(StudentsEntityPK.class)
public class StudentsEntity {
    private int id;
    private String stuCode;
    private String name;
    private String sex;
    private String grade;
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
    @Column(name = "stu_code", nullable = false, length = 255)
    public String getStuCode() {
        return stuCode;
    }

    public void setStuCode(String stuCode) {
        this.stuCode = stuCode;
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
    @Column(name = "sex", nullable = true, length = 255)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "grade", nullable = true, length = 255)
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Basic
    @Column(name = "tele", nullable = true, length = 255)
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
        StudentsEntity that = (StudentsEntity) o;
        return id == that.id &&
                Objects.equals(stuCode, that.stuCode) &&
                Objects.equals(name, that.name) &&
                Objects.equals(sex, that.sex) &&
                Objects.equals(grade, that.grade) &&
                Objects.equals(tele, that.tele) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stuCode, name, sex, grade, tele, email);
    }
}
