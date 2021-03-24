package com.restful.daily_click.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;


@Entity
@Table(name = "location_info", schema = "location")
@IdClass(LocationInfoEntityPK.class)
public class LocationInfoEntity {
    private int locId;
    private String account;
    private String locName;
    private double longitude;
    private double latitude;
    private String testPoint;
    private byte publicPoint;
    private Timestamp createTime;

    @Id
    @Column(name = "loc_id", nullable = false)
    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    @Id
    @Column(name = "account", nullable = false, length = 50)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "loc_name", nullable = false, length = 255)
    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    @Basic
    @Column(name = "longitude", nullable = false, precision = 6)
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "latitude", nullable = false, precision = 6)
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "test_point", nullable = false, length = 50)
    public String getTestPoint() {
        return testPoint;
    }

    public void setTestPoint(String testPoint) {
        this.testPoint = testPoint;
    }

    @Basic
    @Column(name = "public_point", nullable = false)
    public byte getPublicPoint() {
        return publicPoint;
    }

    public void setPublicPoint(byte publicPoint) {
        this.publicPoint = publicPoint;
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
        LocationInfoEntity that = (LocationInfoEntity) o;
        return locId == that.locId &&
                Double.compare(that.longitude, longitude) == 0 &&
                Double.compare(that.latitude, latitude) == 0 &&
                publicPoint == that.publicPoint &&
                Objects.equals(account, that.account) &&
                Objects.equals(locName, that.locName) &&
                Objects.equals(testPoint, that.testPoint) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locId, account, locName, longitude, latitude, testPoint, publicPoint, createTime);
    }
}
