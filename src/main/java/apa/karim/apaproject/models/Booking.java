package apa.karim.apaproject.models;

import java.io.Serializable;
import java.sql.Date;

public class Booking implements Serializable {
    private long bookingID;
    private String guestEmail;
    private long roomID;
    private String status;
    private int price;
    private Date checkIn;
    private Date checkOut;

    public Booking() {
    }

    public Booking(long bookingID, String guestEmail, long roomID, String status, int price, Date checkIn, Date checkOut) {
        this.bookingID = bookingID;
        this.guestEmail = guestEmail;
        this.roomID = roomID;
        this.status = status;
        this.price = price;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Booking(String guestEmail, long roomID, Date checkIn, Date checkOut) {
        this.status = "Paid";
        this.guestEmail = guestEmail;
        this.roomID = roomID;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public long getBookingID() {
        return bookingID;
    }

    public void setBookingID(long bookingID) {
        this.bookingID = bookingID;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public long getRoomID() {
        return roomID;
    }

    public void setRoomID(long roomID) {
        this.roomID = roomID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }
}
