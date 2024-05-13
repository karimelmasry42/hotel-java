package apa.karim.apaproject.models;

import java.io.Serializable;

public class Guest implements Serializable {
    private String name;
    private String guestEmail;
    private String password;

    public Guest() {
    }
    public Guest(String name, String guestEmail, String password) {
        this.name = name;
        this.guestEmail = guestEmail;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
