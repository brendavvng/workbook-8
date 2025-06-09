package com.pluralsight;

import java.sql.*;

public class App {

    // main method
    public static void main(String[] args) throws SQLException {

        Connection connection = null;
        try {

            // 1. open a connection to the database
            // use the database URL to point to the correct database

            //this is like opening MySQL workbench and clicking localhost
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", "root", "yearup");

            // create statement
            // the statement is tied to the open connection
            // like me opening a new query window
            Statement statement = connection.createStatement();

            // define your query
            // like me typing the query in the new query windows
            String query = "SELECT * FROM products";


            // 2. Execute your query
            // this is like me clicking the lightning bolt
            ResultSet results = statement.executeQuery(query);

            // process the results
            // this is a way to view the result set but java doesnt have a spreadsheet view for us
            while (results.next()) {
                String products = results.getString("ProductName");
                // adding productID just because
                String productId = results.getString("ProductID");
                System.out.println(products + " - " + productId);
            }

            // 3. Close the connection
            // closing mysql workbench
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //closing mysql workbench
            connection.close();
        }

    }
}
