package orm.lol.entites.person;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "personphone") // PK (beid, phone, typeid) jest unikalny
public class PersonPhone {

    @EmbeddedId
    private PersonPhoneId id;

    @Column(name = "modifieddate", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime modifiedDate;

     @ManyToOne(fetch = FetchType.LAZY)
     @MapsId("businessEntityId")
     @JoinColumn(name = "businessentityid", referencedColumnName="businessentityid")
     private Person person;

     @ManyToOne(fetch = FetchType.LAZY)
     @MapsId("phoneNumberTypeId")
     @JoinColumn(name = "phonenumbertypeid")
     private PhoneNumberType phoneNumberType;
}