package orm.lol.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import orm.lol.entites.person.AddressType;

import java.util.List;


public interface AddressTypeRepository
        extends JpaRepository<AddressType, Integer> {

@Query(value = "SELECT * FROM AddressType", nativeQuery = true)
List<AddressType> findAllNative();
}
