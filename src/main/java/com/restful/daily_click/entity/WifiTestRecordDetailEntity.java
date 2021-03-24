package com.restful.daily_click.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "wifi_test_record_detail", schema = "location")
public class WifiTestRecordDetailEntity {
    private int id;
    private String ssid;
    private String bssid;
    private String frequency;
    private String level;
    private String timestamp;
    private String capabilities;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "SSID", nullable = false, length = 255)
    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    @Basic
    @Column(name = "BSSID", nullable = false, length = 255)
    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    @Basic
    @Column(name = "frequency", nullable = false, length = 255)
    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Basic
    @Column(name = "level", nullable = false, length = 255)
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Basic
    @Column(name = "timestamp", nullable = false, length = 255)
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Basic
    @Column(name = "capabilities", nullable = false, length = 255)
    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WifiTestRecordDetailEntity that = (WifiTestRecordDetailEntity) o;
        return id == that.id &&
                Objects.equals(ssid, that.ssid) &&
                Objects.equals(bssid, that.bssid) &&
                Objects.equals(frequency, that.frequency) &&
                Objects.equals(level, that.level) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(capabilities, that.capabilities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ssid, bssid, frequency, level, timestamp, capabilities);
    }
}
