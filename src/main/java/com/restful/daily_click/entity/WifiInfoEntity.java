package com.restful.daily_click.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "wifi_info", schema = "location")
@IdClass(WifiInfoEntityPK.class)
public class WifiInfoEntity {
    private int wifiId;
    private int wifiRecId;
    private String wifiDetails;
    private Timestamp createTime;

    @Id
    @Column(name = "wifi_id", nullable = false)
    public int getWifiId() {
        return wifiId;
    }

    public void setWifiId(int wifiId) {
        this.wifiId = wifiId;
    }

    @Id
    @Column(name = "wifi_rec_id", nullable = false)
    public int getWifiRecId() {
        return wifiRecId;
    }

    public void setWifiRecId(int wifiRecId) {
        this.wifiRecId = wifiRecId;
    }

    @Basic
    @Column(name = "wifi_details", nullable = false, length = -1)
    public String getWifiDetails() {
        return wifiDetails;
    }

    public void setWifiDetails(String wifiDetails) {
        this.wifiDetails = wifiDetails;
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
        WifiInfoEntity that = (WifiInfoEntity) o;
        return wifiId == that.wifiId &&
                wifiRecId == that.wifiRecId &&
                Objects.equals(wifiDetails, that.wifiDetails) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wifiId, wifiRecId, wifiDetails, createTime);
    }
}
