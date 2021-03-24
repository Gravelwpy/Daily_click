package com.restful.daily_click.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "group_item", schema = "location")
public class GroupItemEntity {
    private int id;
    private String groupName;
    private String groupIntroduce;
    private String groupTeacher;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "group_name", nullable = false, length = 255)
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Basic
    @Column(name = "group_introduce", nullable = false, length = 255)
    public String getGroupIntroduce() {
        return groupIntroduce;
    }

    public void setGroupIntroduce(String groupIntroduce) {
        this.groupIntroduce = groupIntroduce;
    }

    @Basic
    @Column(name = "group_teacher", nullable = false, length = 255)
    public String getGroupTeacher() {
        return groupTeacher;
    }

    public void setGroupTeacher(String groupTeacher) {
        this.groupTeacher = groupTeacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupItemEntity that = (GroupItemEntity) o;
        return id == that.id &&
                Objects.equals(groupName, that.groupName) &&
                Objects.equals(groupIntroduce, that.groupIntroduce) &&
                Objects.equals(groupTeacher, that.groupTeacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupName, groupIntroduce, groupTeacher);
    }
}
