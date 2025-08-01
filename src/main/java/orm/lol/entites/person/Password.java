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
@Table(name = "password", uniqueConstraints = {
        @UniqueConstraint(name = "uq_password_rowguid", columnNames = {"rowguid"})
})
public class Password {

    // Klucz główny jest współdzielony z BusinessEntity/Person (relacja 1-do-1)
    @Id
    @Column(name = "businessentityid", nullable = false)
    private Integer businessEntityId;

    @Column(name = "passwordhash", nullable = false, length = 128)
    private String passwordHash;

    @Column(name = "passwordsalt", nullable = false, length = 10)
    private String passwordSalt;

    @Generated(GenerationTime.INSERT)
    @Column(name = "rowguid", updatable = false, nullable = false)
    private UUID rowguid;
    @Column(name = "modifieddate", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime modifiedDate;

     @OneToOne(fetch = FetchType.LAZY)
     @MapsId
     @JoinColumn(name = "businessentityid")
     private BusinessEntity businessEntity;
}