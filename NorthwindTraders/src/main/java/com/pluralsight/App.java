package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // initializing scanner for user input
        Scanner theScanner = new Scanner(System.in);

        //making sure we passed in 2 arguments from the command line when we run the app
        //this is done with the app configuration in intellij (page 45 of the wb)
        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.UsingDriverManager <username> <password>"
            );
            System.exit(1);
        }

        // get the user name and password from the command line args
        String username = args[0];
        String password = args[1];

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // create the connection and prepared statement
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind", username, password
            );

            // creating boolean to set running to true
            boolean running = true;

            // while loop so user can put in correct input/choice only
            while (running) {

                System.out.println("\n          Hello •ᴗ•");
                System.out.println("────────────୨ৎ────────────");
                System.out.println("What would you like to do?");
                System.out.println("1] Display all products");
                System.out.println("2] Display all customers");
                System.out.println("0] Exit");
                System.out.print("Please select an option [1, 2, or 0]: ");
                // reading user input, creating int choice variable, and going to next line
                int choice = theScanner.nextInt();

                // if user chooses 1, display this
                if (choice == 1) {
                    //start our prepared statement
                    // adding in other columns
                    preparedStatement = connection.prepareStatement(
                            "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products"
                    );
                    // execute the query
                    resultSet = preparedStatement.executeQuery();

                    // header for products list
                    System.out.println("\n            Products: ");
                    System.out.println("\n─────── ･ ｡ﾟ☆: *.☽ .*:☆ﾟ. ───────\n");

                    // loop through the results
                    while (resultSet.next()) {
                        // process the data
                        System.out.printf(
                                "\nProduct ID:    %d\nName:          %s\nPrice:         %.2f\nStock:         %d\n \n─────── ･ ｡ﾟ☆: *.☽ .*:☆ﾟ. ───────\n",
                                resultSet.getInt("ProductID"),
                                resultSet.getString("ProductName"),
                                resultSet.getDouble("UnitPrice"),
                                resultSet.getInt("UnitsInStock")
                        );
                    }
                    // if user chooses 2, display this
                } else if (choice == 2) {
                    preparedStatement = connection.prepareStatement(
                            "SELECT ContactName, CompanyName, City, Country, Phone FROM Customers"
                    );
                    // execute query
                    resultSet = preparedStatement.executeQuery();

                    // header for customers list
                    System.out.println("\n            Customers: ");
                    System.out.println("\n─────── ･ ｡ﾟ☆: *.☽ .*:☆ﾟ. ───────\n");

                    // loop through the results
                    while (resultSet.next()) {
                        // process the data
                        System.out.printf(
                                "\nContact Name:     %s\nCompany Name:     %s\nCity:             %s\nCountry:          %s\nPhone             %s\n \n─────── ･ ｡ﾟ☆: *.☽ .*:☆ﾟ. ───────\n",
                                resultSet.getString("ContactName"),
                                resultSet.getString("CompanyName"),
                                resultSet.getString("City"),
                                resultSet.getString("Country"),
                                resultSet.getString("Phone")
                        );
                    }
                    // if user chooses 0, display this
                    // 0 = exit system, printing out goodbye message
                } else if (choice == 0) {
                    System.out.println("────────────୨ৎ────────────");
                    System.out.println("Exiting system now. Goodbye! •ᴗ•");
                    return;
                    // if user chooses invalid number, print this
                } else {
                    System.out.println("Invalid option, please choose between options 1, 2, or 0.");
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}