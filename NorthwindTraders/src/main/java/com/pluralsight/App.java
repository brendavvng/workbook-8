package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Scanner;

public class App {

    // initializing scanner for user input
    private static Scanner theScanner = new Scanner(System.in);

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

        // creating the datasource
        BasicDataSource dataSource = new BasicDataSource();

        // configure the datasource
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(args[0]);
        dataSource.setPassword(args[1]);

        // try with resources ensures connection is automatically closed at end of block
        // establishing connection to northwind database with username and password
        try (Connection connection = dataSource.getConnection()) {

            // creating boolean to set running to true
            boolean running = true;

            // while loop so user can put in correct input/choice only
            while (running) {

                // header and menu
                System.out.println("\n          Hello •ᴗ•");
                System.out.println("────────────୨ৎ────────────");
                System.out.println("What would you like to do?");
                System.out.println("1] Display all products");
                System.out.println("2] Display all customers");
                System.out.println("3] Display all categories");
                System.out.println("0] Exit");
                System.out.print("Please select an option [1, 2, 3, or 0]: ");

                // reading user input, creating int choice variable, and going to next line
                int choice = theScanner.nextInt();


                // now using switch statements for menu options and to call in methods
                switch (choice) {
                    case 1:
                        // displays all products
                        displayAllProducts(connection);
                        break;
                    case 2:
                        // displays all customers
                        displayAllCustomers(connection);
                        break;
                    case 3:
                        // displays all categories first
                        displayAllCategories(connection);
                        // then displays question to ask user which category ID they'd like to choose to view all products in that category
                        displayProductsByCategory(connection, theScanner);
                        break;
                    case 0:
                        // if user chooses 0, exits the system
                        System.out.println("────────────୨ৎ────────────");
                        System.out.println("Exiting system now. Goodbye! •ᴗ•");
                        running = false;
                        break;
                    default:
                        // if user does not choose a valid option, display this message
                        System.out.println("Invalid option, please choose between options 1, 2, 3, or 0.");
                }
            }
        }
    }

    // creating methods to interact w/ switch statements
    private static void displayAllProducts(Connection connection) {

        // try with resources ensures the prepared statement is automatically closed after use
        // execute SQL statement
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products"
        );
             // execute the query and getting results, try with resources ensures result set is automatically closed after use
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {

            // header for products list
            System.out.println("\n            Products: ");
            System.out.println("─────── ･ ｡ﾟ☆: *.☽ .*:☆ﾟ. ───────");

            // loop through the results
            while (resultSet.next()) {
                // process the data
                System.out.printf(
                        "Product ID:    %d\nName:          %s\nPrice:         %.2f\nStock:         %d\n─────── ･ ｡ﾟ☆: *.☽ .*:☆ﾟ. ───────\n",
                        resultSet.getInt("ProductID"),
                        resultSet.getString("ProductName"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getInt("UnitsInStock")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void displayAllCustomers(Connection connection){

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT ContactName, CompanyName, City, Country, Phone FROM Customers"
        );
             // execute query
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // header for customers list
            System.out.println("\n            Customers: ");
            System.out.println("─────── ･ ｡ﾟ☆: *.☽ .*:☆ﾟ. ───────");

            // loop through the results
            while (resultSet.next()) {
                // process the data
                System.out.printf(
                        "Contact Name:     %s\nCompany Name:     %s\nCity:             %s\nCountry:          %s\nPhone:            %s\n─────── ･ ｡ﾟ☆: *.☽ .*:☆ﾟ. ───────\n",
                        resultSet.getString("ContactName"),
                        resultSet.getString("CompanyName"),
                        resultSet.getString("City"),
                        resultSet.getString("Country"),
                        resultSet.getString("Phone")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void displayAllCategories(Connection connection){

        // try with resources ensures the prepared statement is automatically closed after use
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT CategoryID, CategoryName FROM Categories"
        );
             // execute query
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            // header for categories list
            System.out.println("\n            Categories: ");
            System.out.println("─────── ･ ｡ﾟ☆: *.☽ .*:☆ﾟ. ───────");

            // loop through the results
            while (resultSet.next()) {
                // process the data
                System.out.printf(
                        "Category ID:       %s\nCategory Name:     %s\n─────── ･ ｡ﾟ☆: *.☽ .*:☆ﾟ. ───────\n",
                        resultSet.getInt("CategoryID"),
                        resultSet.getString("CategoryName")
                );

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // passing scanner object as a parameter
    private static void displayProductsByCategory(Connection connection, Scanner theScanner){

        // initializing categoryChoice outside of loop
        // using -1 as default vale to indicate it hasn't been assigned a valid category ID yet
        int categoryChoice = -1;

        // while true loop to keep asking user for input until it is valid
        while (true) {
            System.out.println("\nWhich Category ID would you like to see to display all products?");
            System.out.print("Please choose between categories 1-8: ");

            if (theScanner.hasNextInt()) {
                categoryChoice = theScanner.nextInt();

                // if number is between 1-8
                if (categoryChoice >= 1 && categoryChoice <= 8) {
                    // if user chooses valid input, exit the loop
                    break;
                } else {
                    // if user chooses invalid number, prints error message & goes back to start of loop to ask again
                    System.out.println("Invalid Category ID. Please enter a number between 1-8.");
                }
            } else {
                // prints out error message if user enters in anything other than a number, like a string
                System.out.println("Invalid input. Please enter a number.");
                theScanner.next(); // clear invalid input
            }
        }

        // printing out products table and listing columns, included the CategoryID column as well for validation
        // try with resources ensures the prepared statement is automatically closed after use
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT Cat.CategoryID, Prod.ProductID, Prod.ProductName, Prod.UnitPrice, Prod.UnitsInStock " +
                        "FROM Products Prod " +
                        "JOIN Categories Cat ON Prod.CategoryID = Cat.CategoryID " +
                        "WHERE Prod.CategoryID = ?" )
        ) {
            // 1 is the placeholder for the question mark, then asking for the users choice
            preparedStatement.setInt(1, categoryChoice);

            // executing query and getting results
            // try with resources ensures the result set is automatically closed after use
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("\nProduct Details from Category ID: " + categoryChoice);
                System.out.println("─────── ･ ｡ﾟ☆: *.☽ .*:☆ﾟ. ───────");

                while (resultSet.next()) {
                    System.out.printf(
                            "Category ID:   %s\nProduct ID:    %d\nProduct Name:  %s\nPrice:         %.2f\nStock:         %d\n─────── ･ ｡ﾟ☆: *.☽ .*:☆ﾟ. ───────\n",
                            resultSet.getInt("CategoryID"),
                            resultSet.getInt("ProductID"),
                            resultSet.getString("ProductName"),
                            resultSet.getDouble("UnitPrice"),
                            resultSet.getInt("UnitsInStock")
                    );
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}