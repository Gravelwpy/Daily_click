package com.restful.daily_click.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Author: ZhangKaijie
 * @Date: 2019/7/28 10:57
 * @Description:
 */
public class CoursesEntityPK implements Serializable {
    private int id;
    private String courseId;

    @Column(name = "id", nullable = false)
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "course_id", nullable = false, length = 50)
    @Id
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoursesEntityPK that = (CoursesEntityPK) o;
        return id == that.id &&
                Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId);
    }
}
