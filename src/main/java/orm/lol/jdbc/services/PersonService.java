package orm.lol.jdbc.services;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import orm.lol.entites.person.Person;

import java.util.List;

@Service
public class PersonService {

    private final JdbcTemplate jdbcTemplate;

    public PersonService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> findAll() {
        String sql = "select * from person";
        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Person.class)
        );
    }

    public List<Person> findByPersonType(String personType) {
        String sql = "select * from person where persontype=?";
        return jdbcTemplate.query(
                sql,
                new Object[]{personType},
                new BeanPropertyRowMapper<>(Person.class)
        );
    }


}
