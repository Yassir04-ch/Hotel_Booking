package Model;

import java.math.BigDecimal;

public class Room {
    private int roomNumber ;
    private int capacity;
    private BigDecimal price;
    private String type;
    private String status;

    public Room(int roomNumber, String type, int capacity, BigDecimal price, String status) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.capacity = capacity;
        this.price = price;
        this.status = status;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
