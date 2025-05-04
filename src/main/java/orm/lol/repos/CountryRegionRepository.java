package orm.lol.repos;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;
import orm.lol.entites.person.CountryRegion;

public interface CountryRegionRepository
    extends Repository<CountryRegion, Integer> {
    List<CountryRegion> findAll();

    Optional<CountryRegion> findByCountryRegionCode(String countryRegionCode);
}
