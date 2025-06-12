package com.pluralsight.dao;

import com.pluralsight.models.Film;
import org.apache.commons.dbcp2.BasicDataSource;


import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDao {

    //create our BasicDataSource
    private BasicDataSource dataSource;

    // constructor
    public FilmDao(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    // getters and setters
    public BasicDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    // method to return a list of films by the actor id
    public List<Film> getFilmsByActorId(int actorId) {
        List<Film> films = new ArrayList<>();

        String filmQuery = """
                SELECT F.film_id
                , F.title
                , F.description
                , F.release_year
                , F.length
                FROM film F
                JOIN film_actor FA ON F.film_id = FA.film_id
                WHERE FA.actor_id = ?
                """;

        try (
                Connection connec = dataSource.getConnection();
                PreparedStatement prepStatement = connec.prepareStatement(filmQuery)
        ) {

            // setting parameter for actorId
            prepStatement.setInt(1, actorId);

            // executes query
            ResultSet res = prepStatement.executeQuery();

            while (res.next()) {
                Film film = new Film();
                film.setFilmId(res.getInt("film_id"));
                film.setTitle(res.getString("title"));
                film.setDescription(res.getString("description"));
                film.setReleaseYear(res.getInt("release_year"));
                film.setLength(res.getInt("length"));

                films.add(film);

            }
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return films;
    }
}
