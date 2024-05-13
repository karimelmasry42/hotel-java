package apa.karim.apaproject;

import java.io.Serializable;
import java.sql.Date;

public class Booking implements Serializable {
    private long booking_id;
    private String guest_email;
    private long room_id;
    private String status;
    private int price;
    private Date check_in;
    private Date check_out;

    public Booking() {
    }

    public Booking(long booking_id, String guest_email, long room_id, String status, int price, Date check_in, Date check_out) {
        this.booking_id = booking_id;
        this.guest_email = guest_email;
        this.room_id = room_id;
        this.status = status;
        this.price = price;
        this.check_in = check_in;
        this.check_out = check_out;
    }

    public Booking(String guest_email, long room_id, Date check_in, Date check_out) {
        this.status = "Paid";
        this.guest_email = guest_email;
        this.room_id = room_id;
        this.check_in = check_in;
        this.check_out = check_out;
    }

    public long getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(long booking_id) {
        this.booking_id = booking_id;
    }

    public String getGuest_email() {
        return guest_email;
    }

    public void setGuest_email(String guest_email) {
        this.guest_email = guest_email;
    }

    public long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(long room_id) {
        this.room_id = room_id;
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

    public Date getCheck_in() {
        return check_in;
    }

    public void setCheck_in(Date check_in) {
        this.check_in = check_in;
    }

    public Date getCheck_out() {
        return check_out;
    }

    public void setCheck_out(Date check_out) {
        this.check_out = check_out;
    }
}
