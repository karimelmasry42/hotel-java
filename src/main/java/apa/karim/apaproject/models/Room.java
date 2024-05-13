package apa.karim.apaproject.models;

import java.io.Serializable;

public class Room implements Serializable {
    private long roomID;
    private String name;
    private String description;
    private int price;
    private int capacity;

    public Room() {
    }

    public Room(int roomID, String name, String description, int price, int capacity) {
        this.roomID = roomID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.capacity = capacity;
    }

    public long getRoomID() {
        return roomID;
    }

    public void setRoomID(long roomID) {
        this.roomID = roomID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
