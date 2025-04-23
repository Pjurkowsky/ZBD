package orm.lol.entites.person;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "businessentitycontact", uniqueConstraints = {
        @UniqueConstraint(name = "uq_businessentitycontact_rowguid", columnNames = {"rowguid"})
})
public class BusinessEntityContact {

    @EmbeddedId
    private BusinessEntityContactId id;

    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    @Column(name = "rowguid", updatable = false, nullable = false)
    private UUID rowguid;
    @Column(name = "modifieddate", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime modifiedDate;

     @ManyToOne(fetch = FetchType.LAZY)
     @MapsId("businessEntityId")
     @JoinColumn(name = "businessentityid")
     private BusinessEntity businessEntity;

     @ManyToOne(fetch = FetchType.LAZY)
     @MapsId("personId")
     @JoinColumn(name = "personid", referencedColumnName="businessentityid") // Łączymy z PK Person
     private Person person;

     @ManyToOne(fetch = FetchType.LAZY)
     @MapsId("contactTypeId")
     @JoinColumn(name = "contacttypeid")
     private ContactType contactType;
}