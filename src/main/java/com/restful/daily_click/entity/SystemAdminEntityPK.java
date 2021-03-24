package com.restful.daily_click.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;


public class SystemAdminEntityPK implements Serializable {
    private int id;
    private String adminName;

    @Column(name = "id", nullable = false)
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "admin_name", nullable = false, length = 50)
    @Id
    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemAdminEntityPK that = (SystemAdminEntityPK) o;
        return id == that.id &&
                Objects.equals(adminName, that.adminName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adminName);
    }
}
