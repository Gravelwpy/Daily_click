package com.restful.daily_click.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "class_info", schema = "location")
@IdClass(ClassInfoEntityPK.class)
public class ClassInfoEntity {
    private int id;
    private String classId;
    private String className;
    private int majorityId;
    private int classYear;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "class_id", nullable = false, length = 50)
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Basic
    @Column(name = "class_name", nullable = false, length = 50)
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Basic
    @Column(name = "majority_id", nullable = false)
    public int getMajorityId() {
        return majorityId;
    }

    public void setMajorityId(int majorityId) {
        this.majorityId = majorityId;
    }

    @Basic
    @Column(name = "class_year", nullable = false)
    public int getClassYear() {
        return classYear;
    }

    public void setClassYear(int classYear) {
        this.classYear = classYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassInfoEntity that = (ClassInfoEntity) o;
        return id == that.id &&
                majorityId == that.majorityId &&
                classYear == that.classYear &&
                Objects.equals(classId, that.classId) &&
                Objects.equals(className, that.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classId, className, majorityId, classYear);
    }
}
