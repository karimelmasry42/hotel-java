package apa.karim.apaproject;

import java.io.Serializable;

public class Room implements Serializable {
    private long room_id;
    private String name;
    private String description;
    private int price;
    private int capacity;

    public Room() {
    }

    public Room(int room_id, String name, String description, int price, int capacity) {
        this.room_id = room_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.capacity = capacity;
    }

    public long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(long room_id) {
        this.room_id = room_id;
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
