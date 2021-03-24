package com.restful.daily_click.entity;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "system_admin", schema = "location")
@IdClass(SystemAdminEntityPK.class)
public class SystemAdminEntity {
    private int id;
    private String adminName;
    private String adminPass;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "admin_name", nullable = false, length = 50)
    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    @Basic
    @Column(name = "admin_pass", nullable = true, length = 50)
    public String getAdminPass() {
        return adminPass;
    }

    public void setAdminPass(String adminPass) {
        this.adminPass = adminPass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemAdminEntity that = (SystemAdminEntity) o;
        return id == that.id &&
                Objects.equals(adminName, that.adminName) &&
                Objects.equals(adminPass, that.adminPass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adminName, adminPass);
    }

    @Override
    public String toString() {
        return "SystemAdminEntity{" +
                "id=" + id +
                ", adminName='" + adminName + '\'' +
                ", adminPass='" + adminPass + '\'' +
                '}';
    }
}
