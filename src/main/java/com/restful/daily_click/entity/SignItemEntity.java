package com.restful.daily_click.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "sign_item", schema = "location")
public class SignItemEntity {
    private int id;
    private int signItemType;
    private String signItemName;
    private String signItemOriginator;
    private String signItemClassid;
    private String signItemLatitude;
    private String signItemLongitude;
    private int signItemRadius;
    private String signItemBeginTime;
    private String signItemEndTime;
    private String signItemCheck;
    private int signItemSignType;
    private int signItemClassroomid;
    private int signItemInSigning;
    private String signItemGroupid;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "sign_item_type", nullable = false)
    public int getSignItemType() {
        return signItemType;
    }

    public void setSignItemType(int signItemType) {
        this.signItemType = signItemType;
    }

    @Basic
    @Column(name = "sign_item_name", nullable = false, length = 255)
    public String getSignItemName() {
        return signItemName;
    }

    public void setSignItemName(String signItemName) {
        this.signItemName = signItemName;
    }

    @Basic
    @Column(name = "sign_item_originator", nullable = false, length = 255)
    public String getSignItemOriginator() {
        return signItemOriginator;
    }

    public void setSignItemOriginator(String signItemOriginator) {
        this.signItemOriginator = signItemOriginator;
    }

    @Basic
    @Column(name = "sign_item_classid", nullable = true, length = 255)
    public String getSignItemClassid() {
        return signItemClassid;
    }

    public void setSignItemClassid(String signItemClassid) {
        this.signItemClassid = signItemClassid;
    }

    @Basic
    @Column(name = "sign_item_latitude", nullable = false, length = 255)
    public String getSignItemLatitude() {
        return signItemLatitude;
    }

    public void setSignItemLatitude(String signItemLatitude) {
        this.signItemLatitude = signItemLatitude;
    }

    @Basic
    @Column(name = "sign_item_longitude", nullable = false, length = 255)
    public String getSignItemLongitude() {
        return signItemLongitude;
    }

    public void setSignItemLongitude(String signItemLongitude) {
        this.signItemLongitude = signItemLongitude;
    }

    @Basic
    @Column(name = "sign_item_radius", nullable = false)
    public int getSignItemRadius() {
        return signItemRadius;
    }

    public void setSignItemRadius(int signItemRadius) {
        this.signItemRadius = signItemRadius;
    }

    @Basic
    @Column(name = "sign_item_begin_time", nullable = true, length = 255)
    public String getSignItemBeginTime() {
        return signItemBeginTime;
    }

    public void setSignItemBeginTime(String signItemBeginTime) {
        this.signItemBeginTime = signItemBeginTime;
    }

    @Basic
    @Column(name = "sign_item_end_time", nullable = true, length = 255)
    public String getSignItemEndTime() {
        return signItemEndTime;
    }

    public void setSignItemEndTime(String signItemEndTime) {
        this.signItemEndTime = signItemEndTime;
    }

    @Basic
    @Column(name = "sign_item_check", nullable = true, length = 255)
    public String getSignItemCheck() {
        return signItemCheck;
    }

    public void setSignItemCheck(String signItemCheck) {
        this.signItemCheck = signItemCheck;
    }

    @Basic
    @Column(name = "sign_item_sign_type", nullable = false)
    public int getSignItemSignType() {
        return signItemSignType;
    }

    public void setSignItemSignType(int signItemSignType) {
        this.signItemSignType = signItemSignType;
    }

    @Basic
    @Column(name = "sign_item_classroomid", nullable = false)
    public int getSignItemClassroomid() {
        return signItemClassroomid;
    }

    public void setSignItemClassroomid(int signItemClassroomid) {
        this.signItemClassroomid = signItemClassroomid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignItemEntity that = (SignItemEntity) o;
        return id == that.id &&
                signItemType == that.signItemType &&
                signItemRadius == that.signItemRadius &&
                signItemSignType == that.signItemSignType &&
                signItemClassroomid == that.signItemClassroomid &&
                Objects.equals(signItemName, that.signItemName) &&
                Objects.equals(signItemOriginator, that.signItemOriginator) &&
                Objects.equals(signItemClassid, that.signItemClassid) &&
                Objects.equals(signItemLatitude, that.signItemLatitude) &&
                Objects.equals(signItemLongitude, that.signItemLongitude) &&
                Objects.equals(signItemBeginTime, that.signItemBeginTime) &&
                Objects.equals(signItemEndTime, that.signItemEndTime) &&
                Objects.equals(signItemCheck, that.signItemCheck);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, signItemType, signItemName, signItemOriginator, signItemClassid, signItemLatitude, signItemLongitude, signItemRadius, signItemBeginTime, signItemEndTime, signItemCheck, signItemSignType, signItemClassroomid);
    }

    @Basic
    @Column(name = "sign_item_in_signing", nullable = false)
    public int getSignItemInSigning() {
        return signItemInSigning;
    }

    public void setSignItemInSigning(int signItemInSigning) {
        this.signItemInSigning = signItemInSigning;
    }

    @Basic
    @Column(name = "sign_item_groupid", nullable = true, length = 255)
    public String getSignItemGroupid() {
        return signItemGroupid;
    }

    public void setSignItemGroupid(String signItemGroupid) {
        this.signItemGroupid = signItemGroupid;
    }
}
