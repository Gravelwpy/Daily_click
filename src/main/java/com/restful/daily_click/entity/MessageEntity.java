package com.restful.daily_click.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "message", schema = "location")
@IdClass(MessageEntityPK.class)
public class MessageEntity {
    private int id;
    private String msgTitle;
    private String msgContent;
    private String teaCode;
    private String classId;
    private String iconUrl;
    private String insertTime;
    private String updateTime;
    private String deleteTime;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "msg_title", nullable = true, length = 255)
    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    @Basic
    @Column(name = "msg_content", nullable = true, length = 255)
    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    @Id
    @Column(name = "tea_code", nullable = false, length = 50)
    public String getTeaCode() {
        return teaCode;
    }

    public void setTeaCode(String teaCode) {
        this.teaCode = teaCode;
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
    @Column(name = "icon_url", nullable = true, length = 255)
    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Basic
    @Column(name = "insert_time", nullable = false, length = 255)
    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    @Basic
    @Column(name = "update_time", nullable = true, length = 255)
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "delete_time", nullable = true, length = 255)
    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEntity that = (MessageEntity) o;
        return id == that.id &&
                Objects.equals(msgTitle, that.msgTitle) &&
                Objects.equals(msgContent, that.msgContent) &&
                Objects.equals(teaCode, that.teaCode) &&
                Objects.equals(classId, that.classId) &&
                Objects.equals(iconUrl, that.iconUrl) &&
                Objects.equals(insertTime, that.insertTime) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(deleteTime, that.deleteTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, msgTitle, msgContent, teaCode, classId, iconUrl, insertTime, updateTime, deleteTime);
    }
}
