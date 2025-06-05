package orm.lol.jdbc.services;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import orm.lol.entites.person.AddressType;

import java.util.List;

@Service
public class AddressTypeService {
    private final JdbcTemplate jdbcTemplate;

    public AddressTypeService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AddressType> findAll() {
        String sql = "select * from addresstype";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AddressType.class));
    }

}
