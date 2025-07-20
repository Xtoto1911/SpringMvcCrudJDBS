package ru.smirnov.springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.smirnov.springmvc.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("select * from public.person", new PersoneMapper());
    }

    public Person show(int id) {
        return jdbcTemplate.query("select * from public.person where id=?", new Object[]{id}, new PersoneMapper())
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into public.person(name,age,email) values(?, ?, ?)", person.getName(), person.getAge(),
                person.getEmail());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("update public.person set name=?, age=?, email=?", updatedPerson.getName(),
                updatedPerson.getAge(), updatedPerson.getEmail());
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from public.person where id=?", id);
    }
}
