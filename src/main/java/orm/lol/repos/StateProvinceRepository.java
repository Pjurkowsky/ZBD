package orm.lol.repos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.Repository;
import orm.lol.entites.person.StateProvince;

public interface StateProvinceRepository
    extends Repository<StateProvince, Integer> {
    List<StateProvince> findAll();

    Optional<StateProvince> findByRowguid(UUID guid);

    Optional<StateProvince> findByStateProvinceId(Integer id);
}
