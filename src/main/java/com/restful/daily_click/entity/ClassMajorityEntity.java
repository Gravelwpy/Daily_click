package com.restful.daily_click.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "class_majority", schema = "location")
public class ClassMajorityEntity {
    private int id;
    private String majorityName;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "majority_name", nullable = false, length = 255)
    public String getMajorityName() {
        return majorityName;
    }

    public void setMajorityName(String majorityName) {
        this.majorityName = majorityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassMajorityEntity that = (ClassMajorityEntity) o;
        return id == that.id &&
                Objects.equals(majorityName, that.majorityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, majorityName);
    }
}
