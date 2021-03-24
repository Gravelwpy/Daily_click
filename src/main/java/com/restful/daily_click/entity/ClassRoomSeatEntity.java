package com.restful.daily_click.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "class_room_seat", schema = "location")
public class ClassRoomSeatEntity {
    private int id;
    private int roomId;
    private int isSeat;
    private int row;
    private int col;
    private String labstuname;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "room_id", nullable = false)
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Basic
    @Column(name = "is_seat", nullable = false)
    public int getIsSeat() {
        return isSeat;
    }

    public void setIsSeat(int isSeat) {
        this.isSeat = isSeat;
    }

    @Basic
    @Column(name = "row", nullable = false)
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Basic
    @Column(name = "col", nullable = false)
    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassRoomSeatEntity that = (ClassRoomSeatEntity) o;
        return id == that.id &&
                roomId == that.roomId &&
                isSeat == that.isSeat &&
                row == that.row &&
                col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomId, isSeat, row, col);
    }

    @Basic
    @Column(name = "labstuname", nullable = true, length = 255)
    public String getLabstuname() {
        return labstuname;
    }

    public void setLabstuname(String labstuname) {
        this.labstuname = labstuname;
    }
}
