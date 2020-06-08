package com.example.staynear.model;

public class Appointment {
    private String id;
    private String roomID;
    private String clientID;
    private String date;

    public Appointment() {
    }

    public Appointment(String id, String roomID, String clientID, String date) {
        this.id = id;
        this.roomID = roomID;
        this.clientID = clientID;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getRoomID() {
        return roomID;
    }

    public String getClientID() {
        return clientID;
    }

    public String getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id='" + id + '\'' +
                ", roomID='" + roomID + '\'' +
                ", clientID='" + clientID + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
