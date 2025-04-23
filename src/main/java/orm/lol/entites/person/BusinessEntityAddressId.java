package orm.lol.entites.person;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BusinessEntityAddressId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "businessentityid", nullable = false)
    private Integer businessEntityId;

    @Column(name = "addressid", nullable = false)
    private Integer addressId;

    @Column(name = "addresstypeid", nullable = false)
    private Integer addressTypeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessEntityAddressId that = (BusinessEntityAddressId) o;
        return Objects.equals(businessEntityId, that.businessEntityId) &&
                Objects.equals(addressId, that.addressId) &&
                Objects.equals(addressTypeId, that.addressTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessEntityId, addressId, addressTypeId);
    }
}
