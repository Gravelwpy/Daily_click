package com.restful.daily_click.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class LocationInfoEntityPK implements Serializable {
    private int locId;
    private String account;

    @Column(name = "loc_id", nullable = false)
    @Id
    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    @Column(name = "account", nullable = false, length = 50)
    @Id
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationInfoEntityPK that = (LocationInfoEntityPK) o;
        return locId == that.locId &&
                Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locId, account);
    }
}
