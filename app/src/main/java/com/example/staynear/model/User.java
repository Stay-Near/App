package com.example.staynear.model;

public class User {
    private String id;
    private String Nombre;
    private String Telefono;
    private String Correo;
    private String Password;

    public User(String id, String nombre, String telefono, String correo, String password) {
        this.id = id;
        Nombre = nombre;
        Telefono = telefono;
        Correo = correo;
        Password = password;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                ", Nombre='" + Nombre + '\'' +
                ", Telefono='" + Telefono + '\'' +
                ", Correo='" + Correo + '\'' +
                '}';
    }
}
