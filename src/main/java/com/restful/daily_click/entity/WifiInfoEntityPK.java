package com.restful.daily_click.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class WifiInfoEntityPK implements Serializable {
    private int wifiId;
    private int wifiRecId;

    @Column(name = "wifi_id", nullable = false)
    @Id
    public int getWifiId() {
        return wifiId;
    }

    public void setWifiId(int wifiId) {
        this.wifiId = wifiId;
    }

    @Column(name = "wifi_rec_id", nullable = false)
    @Id
    public int getWifiRecId() {
        return wifiRecId;
    }

    public void setWifiRecId(int wifiRecId) {
        this.wifiRecId = wifiRecId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WifiInfoEntityPK that = (WifiInfoEntityPK) o;
        return wifiId == that.wifiId &&
                wifiRecId == that.wifiRecId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(wifiId, wifiRecId);
    }
}
