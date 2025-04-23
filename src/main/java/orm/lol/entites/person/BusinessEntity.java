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
@Table(name = "businessentity", uniqueConstraints = {
        @UniqueConstraint(name = "uq_businessentity_rowguid", columnNames = {"rowguid"})
})
public class BusinessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "businessentityid", nullable = false)
    private Integer businessEntityId;

    @Generated(GenerationTime.INSERT)
    @Column(name = "rowguid", updatable = false, nullable = false)
    private UUID rowguid;

    @Column(name = "modifieddate", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime modifiedDate;

}