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
    public List<Person> findPersonEmail(){
        String sql = " SELECT p.firstName , p.lastName , ea.rowguid FROM Person p INNER JOIN EmailAddress ea ON p.BusinessEntityID = ea.BusinessEntityID";
        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Person.class)
        );
    }

    public List<Person> multipleJoin() {
        String sql = """
            SELECT
                p.firstName,
                p.lastName,
                ea.rowguid
            FROM
                Person p
            JOIN BusinessEntity be_person ON p.BusinessEntityID = be_person.BusinessEntityID
            JOIN Password pwd ON p.BusinessEntityID = pwd.BusinessEntityID
            JOIN EmailAddress ea ON p.BusinessEntityID = ea.BusinessEntityID
            JOIN PersonPhone pph ON p.BusinessEntityID = pph.BusinessEntityID
            JOIN PhoneNumberType pnt ON pph.PhoneNumberTypeID = pnt.PhoneNumberTypeID
            JOIN BusinessEntityAddress bea_person ON p.BusinessEntityID = bea_person.BusinessEntityID
            JOIN Address adr ON bea_person.AddressID = adr.AddressID
            JOIN AddressType AS at_person ON bea_person.AddressTypeID = at_person.AddressTypeID
            JOIN StateProvince AS sp ON adr.StateProvinceID = sp.StateProvinceID
            JOIN CountryRegion AS cr ON sp.CountryRegionCode = cr.CountryRegionCode
            """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Person.class)
        );
    }

}

