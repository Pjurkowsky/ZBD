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
@Table(name = "emailaddress",
        uniqueConstraints = {
                // Adres email powinien być unikalny dla danego BusinessEntityID
                @UniqueConstraint(name = "uq_emailaddress_beid_email", columnNames = {"businessentityid", "emailaddress"}),
                @UniqueConstraint(name = "uq_emailaddress_rowguid", columnNames = {"rowguid"})
        },
        indexes = {
                @Index(name = "idx_emailaddress_email", columnList = "emailaddress") // Indeks dla wyszukiwania po emailu
        }
)
public class EmailAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emailaddressid", nullable = false)
    private Integer emailAddressId;

    @Column(name = "businessentityid", nullable = false)
    private Integer businessEntityId;

    @Column(name = "emailaddress", length = 50)
    private String emailAddressValue; // Zmieniono nazwę pola, żeby uniknąć konfliktu z nazwą klasy

    @Generated(GenerationTime.INSERT)
    @Column(name = "rowguid", updatable = false, nullable = false)
    private UUID rowguid;
    @Column(name = "modifieddate", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime modifiedDate;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "businessentityid", referencedColumnName = "businessentityid", insertable=false, updatable=false)
     private BusinessEntity businessEntity;
}