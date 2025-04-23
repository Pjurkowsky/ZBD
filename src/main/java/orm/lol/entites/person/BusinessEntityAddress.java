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
@Table(name = "businessentityaddress", uniqueConstraints = {
        @UniqueConstraint(name = "uq_businessentityaddress_rowguid", columnNames = {"rowguid"})
        // Klucz główny (businessentityid, addressid, addresstypeid) jest już unikalny
})
public class BusinessEntityAddress {

    @EmbeddedId
    private BusinessEntityAddressId id;

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
     @MapsId("addressId")
     @JoinColumn(name = "addressid")
     private Address address;

     @ManyToOne(fetch = FetchType.LAZY)
     @MapsId("addressTypeId")
     @JoinColumn(name = "addresstypeid")
     private AddressType addressType;
}