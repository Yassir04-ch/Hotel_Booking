package Model;

import java.math.BigDecimal;

public class Room {
    private String roomNumber ;
    private int capacity;
    private BigDecimal price;
    private RoomType  type;
    private RoomStatus status;

    public Room(String roomNumber, RoomType  type, int capacity, BigDecimal price, RoomStatus status) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.capacity = capacity;
        this.price = price;
        this.status = status;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {

        this.price = price;
    }

    public RoomType  getType() {
        return type;
    }

    public void setType(RoomType  type) {
        this.type = type;
    }

    public RoomStatus getStatus() {

        return this.status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }
}
