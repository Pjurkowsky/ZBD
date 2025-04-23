package orm.lol.entites.person;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stateprovince",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_stateprovince_name", columnNames = {"name"}),
                @UniqueConstraint(name = "uq_stateprovince_code", columnNames = {"stateprovincecode"}),
                @UniqueConstraint(name = "uq_stateprovince_rowguid", columnNames = {"rowguid"})
        }
//        indexes = {
//                @Index(name = "idx_stateprovince_countryregion", columnList = "countryregioncode"),
//                @Index(name = "idx_stateprovince_territory", columnList = "territoryid") // TerritoryID to FK do Sales.SalesTerritory
//        }
)
public class StateProvince {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stateprovinceid", nullable = false)
    private Integer stateProvinceId;

    @Column(name = "stateprovincecode", nullable = false, length = 3)
    private String stateProvinceCode;

    @Column(name = "countryregioncode", nullable = false, length = 3)
    private String countryRegionCode; // Klucz obcy do CountryRegion

    @Column(name = "isonlystateprovinceflag", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private boolean isOnlyStateProvinceFlag; // "Flag" -> boolean

    @Column(name = "name", nullable = false) // "Name" -> varchar
    private String name;

    @Column(name = "territoryid", nullable = false)
    private Integer territoryId;

    @Generated(GenerationTime.INSERT)
    @Column(name = "rowguid", updatable = false, nullable = false)
    private UUID rowguid;
    @Column(name = "modifieddate", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime modifiedDate;

}