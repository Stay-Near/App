package com.example.staynear.model;

import com.google.android.gms.maps.model.LatLng;

public class Room {
    private String id;
    private String title;
    private double price;
    private String location;
    private String description;
    private String owner;
    private String photo;
    private double lat;
    private double lng;

    public Room() {

    }

    public Room(String id, String title, double price, String location, String description, String owner, String photo, double lat, double lng) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.location = location;
        this.description = description;
        this.owner = owner;
        this.photo = photo;
        this.lat = lat;
        this.lng = lng;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", owner='" + owner + '\'' +
                ", photo='" + photo + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }

}
