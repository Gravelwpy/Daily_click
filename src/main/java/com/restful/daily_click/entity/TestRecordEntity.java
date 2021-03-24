package com.restful.daily_click.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "test_record", schema = "location")
public class TestRecordEntity {
    private int id;
    private String latitude;
    private String longitude;
    private String wifidetail;
    private int detailnum;
    private String remarks;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "latitude", nullable = false, length = 255)
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude", nullable = false, length = 255)
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "wifidetail", nullable = false, length = -1)
    public String getWifidetail() {
        return wifidetail;
    }

    public void setWifidetail(String wifidetail) {
        this.wifidetail = wifidetail;
    }

    @Basic
    @Column(name = "detailnum", nullable = false)
    public int getDetailnum() {
        return detailnum;
    }

    public void setDetailnum(int detailnum) {
        this.detailnum = detailnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestRecordEntity that = (TestRecordEntity) o;
        return id == that.id &&
                detailnum == that.detailnum &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(wifidetail, that.wifidetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude, wifidetail, detailnum);
    }

    @Basic
    @Column(name = "remarks", nullable = true, length = 255)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
