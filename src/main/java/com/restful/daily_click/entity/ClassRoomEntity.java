package com.restful.daily_click.entity;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "class_room", schema = "location")
public class ClassRoomEntity {
    private int id;
    private String roomName;
    private int capacity;
    private double length;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "room_name", nullable = false, length = 20)
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Basic
    @Column(name = "capacity", nullable = false)
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassRoomEntity that = (ClassRoomEntity) o;
        return id == that.id &&
                capacity == that.capacity &&
                Objects.equals(roomName, that.roomName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomName, capacity);
    }

    @Basic
    @Column(name = "length", nullable = false, precision = 0)
    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
