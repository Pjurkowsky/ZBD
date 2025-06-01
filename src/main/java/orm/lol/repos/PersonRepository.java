package orm.lol.repos;

import jakarta.persistence.Tuple;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import orm.lol.dtos.PersonDto;
import orm.lol.entites.person.Person;

public interface PersonRepository
    extends Repository<Person, Integer>, JpaSpecificationExecutor<Person> {
    List<Person> findAll();

    // @Query(value = "SELECT * FROM Person;")
    List<Tuple> findAll(Specification<Tuple> spec);

    Optional<Person> findByRowguid(UUID guid);

    List<Person> findByLastNameStartingWith(String prefix);

    // select p1_0.* from person p1_0 left join businessentity be1_0 on be1_0.businessentityid=p1_0.businessentityid where be1_0.businessentityid=?
    Optional<Person> findByBusinessEntity_BusinessEntityId(Integer id);

    @Query(
        value = "SELECT p.* FROM Person p LEFT JOIN BusinessEntity be on be.businessentityid = p.businessentityid WHERE be.businessentityid = :entityId",
        nativeQuery = true
    )
    Optional<Person> findByBusinessEntity_BusinessEntityId_Native(
        @Param("entityId") Integer id
    );
}
