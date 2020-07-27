package dev.emiliofranco.springjwtstudies.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final String SQL_INSERT_USER = "INSERT INTO app_user(user_name, password) VALUES ( :userName, :password)";

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public String addUser(String userName, String password) {
        //TODO: Hash the password;
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("userName", userName);
            mapSqlParameterSource.addValue("password", password);
            final KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(SQL_INSERT_USER, mapSqlParameterSource, holder);
            String message = String.format("User %s was created", holder.getKeys().get("user_name"));
            return message;
        } catch (Exception e) {
            //Todo: Throw an Exception
            return e.getMessage();
        }
    }
}
