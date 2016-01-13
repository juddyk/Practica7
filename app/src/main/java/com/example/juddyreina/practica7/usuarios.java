package com.example.juddyreina.practica7;

public class usuarios {
    private String name;
    private String passw;
    private String state;

    public usuarios(String name, String passw, String state) {
        this.name = name;
        this.passw = passw;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
