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
@Table(name = "contacttype", uniqueConstraints = {
        @UniqueConstraint(name = "uq_contacttype_name", columnNames = {"name"})
})
public class ContactType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contacttypeid", nullable = false)
    private Integer contactTypeId;

    @Column(name = "name", nullable = false) // "Name" -> varchar
    private String name;

    @Column(name = "modifieddate", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime modifiedDate;

}