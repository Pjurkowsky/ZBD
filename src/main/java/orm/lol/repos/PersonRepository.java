package orm.lol.repos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import orm.lol.entites.person.Person;

public interface PersonRepository extends Repository<Person, Integer> {
    List<Person> findAll();

    Optional<Person> findByRowguid(UUID guid);

    List<Person> findByLastNameStartingWith(String prefix);

    Optional<Person> findByBusinessEntity_BusinessEntityId(Integer id);

    List<Person> findByPersonType(String personType);

    @Query(value = "SELECT * " +
            "FROM Person WHERE PersonType = :personType", nativeQuery = true)
    List<Person> findByPersonTypeNative(@Param("personType") String personType);


}
