package orm.lol.entites.person;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BusinessEntityContactId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "businessentityid", nullable = false)
    private Integer businessEntityId;

    @Column(name = "personid", nullable = false) // Odnosi się do Person.BusinessEntityID
    private Integer personId;

    @Column(name = "contacttypeid", nullable = false)
    private Integer contactTypeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessEntityContactId that = (BusinessEntityContactId) o;
        return Objects.equals(businessEntityId, that.businessEntityId) &&
                Objects.equals(personId, that.personId) &&
                Objects.equals(contactTypeId, that.contactTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessEntityId, personId, contactTypeId);
    }
}
