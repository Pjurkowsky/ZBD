package orm.lol.repos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.Repository;
import orm.lol.entites.person.BusinessEntity;

public interface BusinessEntityRepository
    extends Repository<BusinessEntity, Integer> {
    List<BusinessEntity> findAll();

    Optional<BusinessEntity> findByRowguid(UUID guid);

    Optional<BusinessEntity> findByBusinessEntityId(Integer id);
}
