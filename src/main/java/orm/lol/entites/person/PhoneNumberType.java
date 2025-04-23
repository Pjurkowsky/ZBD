package orm.lol.entites.person;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phonenumbertype", uniqueConstraints = {
        @UniqueConstraint(name = "uq_phonenumbertype_name", columnNames = {"name"})
})
public class PhoneNumberType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phonenumbertypeid", nullable = false)
    private Integer phoneNumberTypeId;

    @Column(name = "name", nullable = false) // "Name" -> varchar
    private String name;

    @Column(name = "modifieddate", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime modifiedDate;

}