package com.pluralsight.dao;

import com.pluralsight.models.Actor;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ActorDao {

    //create our BasicDataSource
    private static BasicDataSource dataSource;

    // constructor that sets up the BasicDataSource passed in
    public ActorDao(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    // getter and setter
    public static BasicDataSource getDataSource() {
        return dataSource;
    }

    public static void setDataSource(BasicDataSource dataSource) {
        ActorDao.dataSource = dataSource;
    }

    // method to search actor by last name
    public static List<Actor> searchByLastName(String lastName) {

        // creating new list
        List<Actor> actors = new ArrayList<>();

        // try with resources to handle closing resources properly
        try (Connection connection = dataSource.getConnection();
             // creating prepared statement
             PreparedStatement pS = connection.prepareStatement(
                "SELECT actor_id, first_name, last_name FROM actor WHERE last_name = ?"
             )) {

            // setting parameter from users input for last name
            pS.setString(1, lastName);

            // execute query
            try (ResultSet resultSet = pS.executeQuery()) {
                while (resultSet.next()) {
                    int actorId = resultSet.getInt("actor_id");
                    String firstName = resultSet.getString("first_name");
                    String actorLastName = resultSet.getString("last_name");
                    actors.add(new Actor(actorId, firstName, actorLastName));
                }
            }
        } catch (Exception e) {
            // error message to user in case of error
            System.out.println("Sorry, there's been an error while searching: " + e.getMessage());
        }
        // returns list of actors
        return actors;
    }

    public static List<Actor> searchByFirstAndLastName(String firstName, String actorLastName) {

        // creating new list
        List<Actor> actors = new ArrayList<>();

        // try with resources to handle closing resources properly
        try (Connection connection = dataSource.getConnection();
             // creating prepared statement
             PreparedStatement pS = connection.prepareStatement(
                     "SELECT actor_id, first_name, last_name FROM actor WHERE first_name = ? AND last_name = ?"
             )) {

            // setting parameter from users input for last name
            pS.setString(1, firstName);
            pS.setString(2, actorLastName);

            // execute query
            try (ResultSet resultSet = pS.executeQuery()) {
                while (resultSet.next()) {
                    int actorId = resultSet.getInt("actor_id");
                    String actorFirName = resultSet.getString("first_name");
                    String actorLasName = resultSet.getString("last_name");
                    actors.add(new Actor(actorId, firstName, actorLastName));
                }
            }
        } catch (Exception e) {
            // error message to user in case of error
            System.out.println("Sorry, there's been an error while searching: " + e.getMessage());
        }
        // returns list of actors
        return actors;
    }

}
