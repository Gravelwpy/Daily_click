package com.restful.daily_click.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "questions", schema = "location")
public class QuestionsEntity {
    private int id;
    private String desc;
    private Timestamp creatTime;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "desc", nullable = false, length = 255)
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Basic
    @Column(name = "creat_time", nullable = false)
    public Timestamp getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Timestamp creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionsEntity that = (QuestionsEntity) o;
        return id == that.id &&
                Objects.equals(desc, that.desc) &&
                Objects.equals(creatTime, that.creatTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, desc, creatTime);
    }
}
