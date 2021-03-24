package com.restful.daily_click.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "wifi_details", schema = "location")
public class WifiDetailsEntity {
    private int id;
    private int wifiId;
    private String ssid;
    private String bssid;
    private String frequency;
    private String level;
    private String timestamp;
    private String capabilities;
    private String rssi;
    private int type;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "wifi_id", nullable = false)
    public int getWifiId() {
        return wifiId;
    }

    public void setWifiId(int wifiId) {
        this.wifiId = wifiId;
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
        WifiDetailsEntity that = (WifiDetailsEntity) o;
        return id == that.id &&
                wifiId == that.wifiId &&
                Objects.equals(ssid, that.ssid) &&
                Objects.equals(bssid, that.bssid) &&
                Objects.equals(frequency, that.frequency) &&
                Objects.equals(level, that.level) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(capabilities, that.capabilities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, wifiId, ssid, bssid, frequency, level, timestamp, capabilities);
    }

    @Basic
    @Column(name = "RSSI", nullable = false, length = 255)
    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
