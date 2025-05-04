package orm.lol.entites.person;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "countryregion",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_countryregion_name",
            columnNames = { "name" }
        ),
    }
)
public class CountryRegion {

    @Id
    @Column(name = "countryregioncode", nullable = false, length = 3)
    private String countryRegionCode;

    @Column(name = "name", nullable = false) // "Name" -> varchar
    private String name;

    @Column(
        name = "modifieddate",
        nullable = false,
        columnDefinition = "TIMESTAMP DEFAULT NOW()"
    )
    private LocalDateTime modifiedDate;

    public JSONObject toJSON() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("countryregioncode", this.countryRegionCode);
        map.put("name", this.name);
        map.put("modifieddate", this.modifiedDate);

        JSONObject object = new JSONObject(map);

        return object;
    }
}
