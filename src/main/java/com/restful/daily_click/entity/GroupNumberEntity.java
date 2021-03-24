package com.restful.daily_click.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "group_number", schema = "location")
@IdClass(GroupNumberEntityPK.class)
public class GroupNumberEntity {
    private int id;
    private String stuCode;
    private int groupId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "stu_code", nullable = false, length = 50)
    public String getStuCode() {
        return stuCode;
    }

    public void setStuCode(String stuCode) {
        this.stuCode = stuCode;
    }

    @Id
    @Column(name = "group_id", nullable = false)
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
        GroupNumberEntity that = (GroupNumberEntity) o;
        return id == that.id &&
                groupId == that.groupId &&
                Objects.equals(stuCode, that.stuCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stuCode, groupId);
    }
}
