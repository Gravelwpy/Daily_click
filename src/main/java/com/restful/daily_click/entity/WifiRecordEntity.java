package com.restful.daily_click.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;


@Entity
@Table(name = "wifi_record", schema = "location")
@IdClass(WifiRecordEntityPK.class)
public class WifiRecordEntity {
    private int wifiRecId;
    private int locId;
    private String phoneInfo;
    private Timestamp createTime;

    @Id
    @Column(name = "wifi_rec_id", nullable = false)
    public int getWifiRecId() {
        return wifiRecId;
    }

    public void setWifiRecId(int wifiRecId) {
        this.wifiRecId = wifiRecId;
    }

    @Id
    @Column(name = "loc_id", nullable = false)
    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    @Basic
    @Column(name = "phone_info", nullable = false, length = 50)
    public String getPhoneInfo() {
        return phoneInfo;
    }

    public void setPhoneInfo(String phoneInfo) {
        this.phoneInfo = phoneInfo;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WifiRecordEntity that = (WifiRecordEntity) o;
        return wifiRecId == that.wifiRecId &&
                locId == that.locId &&
                Objects.equals(phoneInfo, that.phoneInfo) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wifiRecId, locId, phoneInfo, createTime);
    }
}
