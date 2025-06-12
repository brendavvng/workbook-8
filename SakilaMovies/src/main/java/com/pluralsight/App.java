package com.pluralsight;

import com.pluralsight.dao.ActorDao;
import com.pluralsight.dao.FilmDao;
import com.pluralsight.models.Actor;
import com.pluralsight.models.Film;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.stream.BaseStream;

public class App {

    // creating the datasource
    private static BasicDataSource dataSource = new BasicDataSource();


    // initializing scanner
    static Scanner theScanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        //making sure we passed in 2 arguments from the command line when we run the app
        //this is done with the app configuration in intellij (page 45 of the wb)
        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.UsingDriverManager <username> <password>"
            );
            System.exit(1);
        }

        String username = args[0];
        String password = args[1];

        // configure the datasource
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        // creating data manager for actor dao and film dao
        ActorDao actorDataManager = new ActorDao(dataSource);
        FilmDao filmDataManager = new FilmDao(dataSource);

        // creating boolean set to true for while loop
        boolean running = true;

        // while loop to keep running until condition is false
        while (running) {

            System.out.println("\n          Hello •ᴗ•");
            System.out.println("────────────୨ৎ────────────");
            System.out.println("What would you like to do?");
            System.out.println("1] Search actor by last name");
            System.out.println("2] Search actor by first and last name");
            System.out.println("0] Exit");
            System.out.print("Please select an option [1, 2, or 0]: ");

            // reading user input, creating int choice variable, and going to next line
            int choice = theScanner.nextInt();
            // consuming next line
            theScanner.nextLine();

            // now using switch statements for menu options and to call in methods
            switch (choice) {
                case 1:
                    // displays actors name
                    displayActorsByLastName(actorDataManager);
                    break;
                case 2:
                    // displays first & last name of actor
                    displayActorsByFirstAndLastName(actorDataManager);
                    break;
                case 0:
                    // if user chooses 0, exits the system
                    System.out.println("────────────୨ৎ────────────");
                    System.out.println("Exiting system now. Goodbye! •ᴗ•");
                    running = false;
                    break;
                default:
                    // if user does not choose a valid option, display this message
                    System.out.println("Invalid option, please choose between options 1, 2, or 0.");
            }
        }
    }

    // creating method to display acotrs by their last name
    private static void displayActorsByLastName(ActorDao actorDao) {
        try {
            // asking user for last name
            System.out.print("Please enter the last name of the actor: ");
            String theLastName = theScanner.nextLine();

            List<Actor> actors = ActorDao.searchByLastName(theLastName);

            if (actors.isEmpty()) {
                // if empty, prints out message to user
                System.out.println("No actors found with last name: " + theLastName);
                return;
            }

            // header for actors list
            System.out.println("\n            Actors: ");
            System.out.println("─────── ･ ｡ﾟ☆: *.☽ .*:☆ﾟ. ───────");

            // loop through the results
            for (Actor actor : actors) {
                System.out.printf(
                        "Actor ID: %d\nFirst Name: %s\nLast Name: %s\n─────── ･ ｡ﾟ☆: *.☽ .*:☆ﾟ. ───────\n",
                        actor.getActorId(),
                        actor.getFirstName(),
                        actor.getLastName()
                );
            }

        } catch (Exception e) {
            System.out.println("Sorry, there's been an error searching for actors: " + e.getMessage());
        }
    }

    // creating method to display actors by their first & last name
    private static void displayActorsByFirstAndLastName(ActorDao actorDao) {
        try {
            // asking user for first name
            System.out.println("Please enter the first name of the actor: ");
            String actorFirstName = theScanner.nextLine().toUpperCase();

            // asking user for last name
            System.out.println("Please enter the last name of the actor: ");
            String actorLastName = theScanner.nextLine().toUpperCase();

            // list for actors
            List<Actor> actors = ActorDao.searchByFirstAndLastName(actorFirstName, actorLastName);

            if (actors.isEmpty()) {
                System.out.println("Sorry, no actors have been found with the name: " + actorFirstName + actorLastName);
                return;
            }

            // header
            System.out.println("\n            Actors: ");
            System.out.println("─────── ･ ｡ﾟ☆: *.☽ .*:☆ﾟ. ───────");

            // looping through results
            for (Actor actor : actors) {
                System.out.printf(
                        "Actor ID: %d\nFirst Name: %s\nLast Name: %s\n─────── ･ ｡ﾟ☆: *.☽ .*:☆ﾟ. ───────\n",
                        actor.getActorId(),
                        actor.getFirstName(),
                        actor.getLastName()
                );
            }

            // asking user for the actor ID to look up the films
            System.out.print("Please enter the Actor ID to view all of their films: ");
            int inputActorId = theScanner.nextInt();
            theScanner.nextLine();

            // creating new film dao object and passing in data source
            FilmDao filmDao = new FilmDao(dataSource);
            // making a list of films from the actor id given by the user
            List<Film> films = filmDao.getFilmsByActorId(inputActorId);

            if (films.isEmpty()) {
                System.out.println("Sorry, no films have been found for this actor.");
            } else {
                System.out.println("\nHere are the films for Actor ID: " + inputActorId);
                System.out.println("─────── ･ ｡ﾟ☆: *.☽ .*:☆ﾟ. ───────");
                for (Film film : films) {
                    System.out.println(film.getTitle());
                }
            }

        } catch (Exception e) {
            System.out.println("Sorry, there's been an error searching for actors or films: " + e.getMessage());
        }
    }

}
