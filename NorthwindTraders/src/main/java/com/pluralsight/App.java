package com.pluralsight;

import java.sql.*;

public class App {
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

    // get the user name and password from the command line args
    String username = args[0];
    String password = args[1];

    // create the connection and prepared statement
    Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/northwind", username, password
    );

    //start our prepared statement
        // adding in other columns
    PreparedStatement preparedStatement = connection.prepareStatement(
            "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products"
    );

    // execute the query
    ResultSet resultSet = preparedStatement.executeQuery();

    System.out.println("\n            Products: ");
    System.out.println("\n─────── ･ ｡ﾟ☆: *.☽ .*:☆ﾟ. ───────\n");

    // loop thru the results
    while (resultSet.next()) {
        // process the data
        System.out.printf(
                "\nProduct ID: %d\nName: %s\nPrice: %.2f\nStock: %.2f\n \n─────── ･ ｡ﾟ☆: *.☽ .*:☆ﾟ. ───────\n",
                resultSet.getInt("ProductID"),
                resultSet.getString("ProductName"),
                resultSet.getDouble("UnitPrice"),
                resultSet.getDouble("UnitsInStock")

            );
        }

    // close the resources
    resultSet.close();
    preparedStatement.close();
    connection.close();
    }
}