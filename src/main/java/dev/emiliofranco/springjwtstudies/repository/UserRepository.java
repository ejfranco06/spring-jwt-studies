package dev.emiliofranco.springjwtstudies.repository;

import dev.emiliofranco.springjwtstudies.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {

    private final String SQL_INSERT_USER = "INSERT INTO app_user(user_name, password) VALUES ( :userName, :password)";
    private final String SQL_FIND_BY_USER_NAME = "SELECT id, user_name, password FROM app_user WHERE user_name = :userName";

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

    public AppUser findByUserName(String userName){
        try {
            MapSqlParameterSource paramMap = new MapSqlParameterSource();
            paramMap.addValue("userName", userName);

            AppUser user =  jdbcTemplate.queryForObject(SQL_FIND_BY_USER_NAME, paramMap, new AppUserMapper());

            return user;

        } catch (Exception e){
            //Todo: Throw an Exception
            return null;
        }
    }


    private class AppUserMapper implements RowMapper<AppUser>{
        @Override
        public AppUser mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String userName = resultSet.getString("user_name");
            String password = resultSet.getString("password");
            return new AppUser(id, userName, password);
        }
    }
}
