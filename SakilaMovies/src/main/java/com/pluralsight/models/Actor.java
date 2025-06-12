package com.pluralsight.models;

import org.apache.commons.dbcp2.BasicDataSource;

public class Actor {

    // creating variables for actor
    private int actorId;
    private String firstName;
    private String lastName;

    // generating constructor
    public Actor(int actorId, String firstName, String lastName) {
        this.actorId = actorId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // getters and setters
    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // toString method to print first & last name
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}