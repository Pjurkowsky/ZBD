package orm.lol.entites.person;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PersonPhoneId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "businessentityid", nullable = false)
    private Integer businessEntityId;

    @Column(name = "phonenumber", nullable = false) // "Phone" -> varchar
    private String phoneNumber;

    @Column(name = "phonenumbertypeid", nullable = false)
    private Integer phoneNumberTypeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonPhoneId that = (PersonPhoneId) o;
        return Objects.equals(businessEntityId, that.businessEntityId) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(phoneNumberTypeId, that.phoneNumberTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessEntityId, phoneNumber, phoneNumberTypeId);
    }
}
