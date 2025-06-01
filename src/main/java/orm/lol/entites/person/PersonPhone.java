package orm.lol.entites.person;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "personphone") // PK (beid, phone, typeid) jest unikalny
public class PersonPhone {

    @EmbeddedId
    private PersonPhoneId id;

    @Column(
        name = "phonenumber",
        nullable = false,
        insertable = false,
        updatable = false
    ) // "Name" -> varchar
    private String phoneNumber;

    @Column(
        name = "modifieddate",
        nullable = false,
        columnDefinition = "TIMESTAMP DEFAULT NOW()"
    )
    private LocalDateTime modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("businessEntityId")
    @JoinColumn(
        name = "businessentityid",
        referencedColumnName = "businessentityid"
    )
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("phoneNumberTypeId")
    @JoinColumn(name = "phonenumbertypeid")
    private PhoneNumberType phoneNumberType;
}
