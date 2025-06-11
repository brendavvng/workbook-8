package com.pluralsight.models;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    // method to search actor by last name
    public static List<Actor> searchByLastName(BasicDataSource dataSource, String lastName) {
        String query = "SELECT actor_id, first_name, last_name FROM actor WHERE last_name = ?";
        // creating new list
        List<Actor> actors = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
        PreparedStatement pS = connection.prepareStatement(query)) {

        pS.setString(1, lastName);

        try (ResultSet resultSet = pS.executeQuery()) {
            while (resultSet.next()) {
                int actorId = resultSet.getInt("actor_id");
                String firstName = resultSet.getString("first_name");
                String theLastName = resultSet.getString("last_name");
                // adding actor to the list
                actors.add(new Actor(actorId, firstName, theLastName));
            }
        }
        } catch (SQLException e) {
            // printing out error message in case of error
            System.out.println("Sorry, there's been an error: " + e.getMessage());
        }
        // return back list of actor
        return actors;
    }

    // method to display list of actors
    public static void displayActor(List<Actor> actors, String searchLastName) {
        if (actors.isEmpty()) {
            System.out.println("Sorry, no actors found with last name: " + searchLastName);

        } else {
            System.out.println("\nHere are the list of actors with the last name: " + searchLastName);
            System.out.println("-------------------");
            for (Actor actor : actors) {
                System.out.println(actor.toString());
            }
        }
    }

}