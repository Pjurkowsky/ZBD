package orm.lol.entites.person;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "countryregion", uniqueConstraints = {
        @UniqueConstraint(name = "uq_countryregion_name", columnNames = {"name"})
})
public class CountryRegion {

    @Id
    @Column(name = "countryregioncode", nullable = false, length = 3)
    private String countryRegionCode;

    @Column(name = "name", nullable = false) // "Name" -> varchar
    private String name;

    @Column(name = "modifieddate", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime modifiedDate;

}