package com.restful.daily_click.entity;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "courses", schema = "location")
@IdClass(CoursesEntityPK.class)
public class CoursesEntity {
    private int id;
    private String courseName;
    private String courseId;
    private int courseCredit;
    private byte courseType;
    private double courseHour;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "course_name", nullable = false, length = 70)
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Id
    @Column(name = "course_id", nullable = false, length = 50)
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "course_credit", nullable = false)
    public int getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }

    @Basic
    @Column(name = "course_type", nullable = false)
    public byte getCourseType() {
        return courseType;
    }

    public void setCourseType(byte courseType) {
        this.courseType = courseType;
    }

    @Basic
    @Column(name = "course_hour", nullable = false, precision = 0)
    public double getCourseHour() {
        return courseHour;
    }

    public void setCourseHour(double courseHour) {
        this.courseHour = courseHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoursesEntity that = (CoursesEntity) o;
        return id == that.id &&
                courseCredit == that.courseCredit &&
                courseType == that.courseType &&
                Double.compare(that.courseHour, courseHour) == 0 &&
                Objects.equals(courseName, that.courseName) &&
                Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseName, courseId, courseCredit, courseType, courseHour);
    }
}
