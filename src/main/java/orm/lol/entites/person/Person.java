package orm.lol.entites.person;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.json.JSONArray;
import org.json.JSONObject;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "person",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_person_rowguid",
            columnNames = { "rowguid" }
        ),
    }
)
@Check(
    constraints = "(emailpromotion BETWEEN 0 AND 2) " +
    "AND (persontype IS NULL OR UPPER(persontype) IN ('SC', 'VC', 'IN', 'EM', 'SP', 'GC'))"
)
public class Person {

    // Klucz główny jest współdzielony z BusinessEntity (relacja 1-do-1)
    @Id
    @Column(name = "businessentityid", nullable = false)
    private Integer businessEntityId;

    @Column(name = "persontype", nullable = false, length = 2)
    private String personType;

    @Column(
        name = "namestyle",
        nullable = false,
        columnDefinition = "BOOLEAN DEFAULT false"
    )
    private boolean nameStyle; // "NameStyle" -> boolean

    @Column(name = "title", length = 8)
    private String title;

    @Column(name = "firstname", nullable = false) // "Name" -> varchar
    private String firstName;

    @Column(name = "middlename") // "Name" -> varchar
    private String middleName;

    @Column(name = "lastname", nullable = false) // "Name" -> varchar
    private String lastName;

    @Column(name = "suffix", length = 10)
    private String suffix;

    @Column(
        name = "emailpromotion",
        nullable = false,
        columnDefinition = "INTEGER DEFAULT 0"
    )
    private Integer emailPromotion;

    @Column(name = "additionalcontactinfo", columnDefinition = "XML")
    private String additionalContactInfo; // XML -> String

    @Column(name = "demographics", columnDefinition = "XML")
    private String demographics; // XML -> String

    @Generated(GenerationTime.INSERT)
    @Column(name = "rowguid", updatable = false, nullable = false)
    private UUID rowguid;

    @Column(
        name = "modifieddate",
        nullable = false,
        columnDefinition = "TIMESTAMP DEFAULT NOW()"
    )
    private LocalDateTime modifiedDate;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "businessentityid")
    private BusinessEntity businessEntity;

    @Override
    public String toString() {
        return firstName + middleName;
    }

    public JSONObject toJSON() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("businessentityid", this.businessEntityId);
        map.put("persontype", this.personType);
        map.put("namestyle", this.nameStyle);
        map.put("title", this.title);
        map.put("firstname", this.firstName);
        map.put("middlename", this.middleName);
        map.put("lastname", this.lastName);
        map.put("suffix", this.suffix);
        map.put("emailpromotion", this.emailPromotion);
        // XML
        // map.put("additionalcontactinfo", this.additionalContactInfo);
        // map.put("demographics", this.demographics);
        map.put("rowguid", this.rowguid);
        map.put("modifieddate", this.modifiedDate);

        JSONObject object = new JSONObject(map);

        return object;
    }
}
