package orm.lol.entites.person;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;
import org.json.JSONObject;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "stateprovince",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_stateprovince_name",
            columnNames = { "name" }
        ),
        @UniqueConstraint(
            name = "uq_stateprovince_code",
            columnNames = { "stateprovincecode" }
        ),
        @UniqueConstraint(
            name = "uq_stateprovince_rowguid",
            columnNames = { "rowguid" }
        ),
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

    // @Column(name = "countryregioncode", nullable = false, length = 3)
    // private String countryRegionCode; // Klucz obcy do CountryRegion
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "countryregioncode", nullable = false)
    private CountryRegion countryRegion;

    @Column(
        name = "isonlystateprovinceflag",
        nullable = false,
        columnDefinition = "BOOLEAN DEFAULT true"
    )
    private boolean isOnlyStateProvinceFlag; // "Flag" -> boolean

    @Column(name = "name", nullable = false) // "Name" -> varchar
    private String name;

    @Column(name = "territoryid", nullable = false)
    private Integer territoryId;

    @Generated(GenerationTime.INSERT)
    @Column(name = "rowguid", updatable = false, nullable = false)
    private UUID rowguid;

    @Column(
        name = "modifieddate",
        nullable = false,
        columnDefinition = "TIMESTAMP DEFAULT NOW()"
    )
    private LocalDateTime modifiedDate;

    public JSONObject toJSON() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("stateprovinceid", this.stateProvinceId);
        map.put("stateprovincecode", this.stateProvinceCode);
        map.put("countryregioncode", this.countryRegionCode);
        map.put("isonlystateprovinceflag", this.isOnlyStateProvinceFlag);
        map.put("name", this.name);
        map.put("territoryid", this.territoryId);
        map.put("rowguid", this.rowguid);
        map.put("modifiedDate", this.modifiedDate);

        JSONObject object = new JSONObject(map);

        return object;
    }
}
