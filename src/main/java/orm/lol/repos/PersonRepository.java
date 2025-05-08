package orm.lol.repos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.Repository;
import orm.lol.entites.person.Person;

public interface PersonRepository extends Repository<Person, Integer> {
    List<Person> findAll();

    Optional<Person> findByRowguid(UUID guid);

    List<Person> findByLastNameStartingWith(String prefix);

    Optional<Person> findByBusinessEntity_BusinessEntityId(Integer id);
}
