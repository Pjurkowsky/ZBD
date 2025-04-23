package orm.lol.entites.person;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_address_rowguid", columnNames = {"rowguid"})
        }
//        indexes = {
//                @Index(name = "idx_address_stateprovince", columnList = "stateprovinceid"),
//                @Index(name = "idx_address_postalcode", columnList = "postalcode") // Indeks na kod pocztowy dla wyszukiwania
//        }
)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addressid", nullable = false)
    private Integer addressId;

    @Column(name = "addressline1", nullable = false, length = 60)
    private String addressLine1;

    @Column(name = "addressline2", length = 60)
    private String addressLine2;

    @Column(name = "city", nullable = false, length = 30)
    private String city;

    @Column(name = "stateprovinceid", nullable = false)
    private Integer stateProvinceId; // Klucz obcy do StateProvince

    @Column(name = "postalcode", nullable = false, length = 15)
    private String postalCode;

    @Lob // Dla typu bytea
    @Column(name = "spatiallocation")
    private byte[] spatialLocation; // Można użyć typów PostGIS z hibernate-spatial

    @Generated(GenerationTime.INSERT)
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "rowguid", updatable = false, nullable = false)
    private UUID rowguid;

    @Column(name = "modifieddate", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime modifiedDate;

}