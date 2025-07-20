package ru.smirnov.springmvc.dao;

import org.jspecify.annotations.Nullable;
import org.springframework.jdbc.core.RowMapper;
import ru.smirnov.springmvc.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
Если уже колонки называются так же как и поля, то можно использовать BeanPropertyRowMapper<>()
 */
public class PersoneMapper implements RowMapper<Person> {
    @Override
    public @Nullable Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Person(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("email")
        );
    }
}
