package com.pluralsight.dao;

import com.pluralsight.models.Actor;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActorDao {

    //create our BasicDataSource
    private BasicDataSource dataSource;

    // constructor that sets up the BasicDataSource passed in
    public ActorDao(BasicDataSource dataSource) {
        this.dataSource = dataSource;
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
