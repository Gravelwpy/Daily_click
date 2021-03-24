package com.restful.daily_click.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class WifiRecordEntityPK implements Serializable {
    private int wifiRecId;
    private int locId;

    @Column(name = "wifi_rec_id", nullable = false)
    @Id
    public int getWifiRecId() {
        return wifiRecId;
    }

    public void setWifiRecId(int wifiRecId) {
        this.wifiRecId = wifiRecId;
    }

    @Column(name = "loc_id", nullable = false)
    @Id
    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WifiRecordEntityPK that = (WifiRecordEntityPK) o;
        return wifiRecId == that.wifiRecId &&
                locId == that.locId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(wifiRecId, locId);
    }
}
