package com.restful.daily_click.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class GroupNumberEntityPK implements Serializable {
    private int id;
    private String stuCode;
    private int groupId;

    @Column(name = "id", nullable = false)
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "stu_code", nullable = false, length = 50)
    @Id
    public String getStuCode() {
        return stuCode;
    }

    public void setStuCode(String stuCode) {
        this.stuCode = stuCode;
    }

    @Column(name = "group_id", nullable = false)
    @Id
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupNumberEntityPK that = (GroupNumberEntityPK) o;
        return id == that.id &&
                groupId == that.groupId &&
                Objects.equals(stuCode, that.stuCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stuCode, groupId);
    }
}
