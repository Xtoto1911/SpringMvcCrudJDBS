package ru.smirnov.springmvc.dao;

import org.springframework.stereotype.Component;
import ru.smirnov.springmvc.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;

    private static  final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static  final String USER_NAME = "postgres";
    private static  final String PASSWORD = "Source1911_1911";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> index() {
        List<Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM public.person";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()){
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    public Person show(int id) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("select * from public.person where id = ?");

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return new Person(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("age"),
                    resultSet.getString("email")
            );


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void save(Person person) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("insert into public.person values(1, ?, ?, ?)");
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.setString(3, person.getEmail());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Person updatedPerson) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("update public.person set name=?, age=?, email=?");

            statement.setString(1, updatedPerson.getName());
            statement.setInt(2, updatedPerson.getAge());
            statement.setString(3, updatedPerson.getEmail());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("delete from public.person where id=?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
