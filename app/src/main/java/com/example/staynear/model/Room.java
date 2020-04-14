package com.example.staynear.model;

public class Room {
    private String id;
    private String title;
    private Integer price;
    private String location;
    private String description;
    private String owner;
    private String photo;

    public Room() {

    }

    public Room(String id, String title, Integer price, String location, String description, String owner, String photo) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.location = location;
        this.description = description;
        this.owner = owner;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Room{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}