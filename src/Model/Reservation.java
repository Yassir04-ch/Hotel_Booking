package Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation {

    private UUID id;
    private String reservationCode;
    private UUID userId;
    private String roomNumber;

    private LocalDate checkIn;
    private LocalDate checkOut;

    private int numberOfGuests;
    private int numberOfNights;

    private BigDecimal totalPrice;

    private String status;

    private LocalDateTime createdAt;

    // Constructor
    public Reservation(UUID id, String reservationCode, UUID userId, String roomNumber, LocalDate checkIn,
                       LocalDate checkOut, int numberOfGuests, int numberOfNights, BigDecimal totalPrice,
                       String status, LocalDateTime createdAt)
    {
        this.id = id;
        this.reservationCode = reservationCode;
        this.userId = userId;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfGuests = numberOfGuests;
        this.numberOfNights = numberOfNights;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(String reservationCode) {
        this.reservationCode = reservationCode;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", reservationCode='" + reservationCode + '\'' +
                ", userId=" + userId +
                ", roomNumber='" + roomNumber + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", numberOfGuests=" + numberOfGuests +
                ", numberOfNights=" + numberOfNights +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}