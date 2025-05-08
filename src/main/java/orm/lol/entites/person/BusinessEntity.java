package orm.lol.entites.person;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
import org.json.JSONObject;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "businessentity",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_businessentity_rowguid",
            columnNames = { "rowguid" }
        ),
    }
)
public class BusinessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "businessentityid", nullable = false)
    private Integer businessEntityId;

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
        map.put("businessentityid", this.businessEntityId);
        map.put("rowguid", this.rowguid);
        map.put("modifieddate", this.modifiedDate);

        JSONObject object = new JSONObject(map);

        return object;
    }
}
