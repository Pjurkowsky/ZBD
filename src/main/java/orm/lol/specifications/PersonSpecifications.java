package orm.lol.specifications;

import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import orm.lol.dtos.PersonDetailDTO;
import orm.lol.entites.person.Address;
import orm.lol.entites.person.AddressType;
import orm.lol.entites.person.BusinessEntity;
import orm.lol.entites.person.BusinessEntityAddress;
import orm.lol.entites.person.CountryRegion;
import orm.lol.entites.person.EmailAddress;
import orm.lol.entites.person.Password;
import orm.lol.entites.person.Person;
import orm.lol.entites.person.PersonPhone;
import orm.lol.entites.person.PhoneNumberType;
import orm.lol.entites.person.StateProvince;

public class PersonSpecifications {

    public static Specification<Person> hasEmailLike(String email) {
        return (root, query, criteriaBuilder) -> {
            // The join is now directly to EmailAddress through the 'emailAddresses' collection
            Join<Person, EmailAddress> emailJoin = root.join("emailAddresses");

            // The equality condition for businessEntityId is now implicitly handled
            // by the relationship mapping. We don't need to explicitly join on it.

            // Create the predicate for the WHERE clause to filter by email address
            Predicate emailLikeCondition = criteriaBuilder.like(
                emailJoin.get("emailAddressValue"),
                "%" + email + "%"
            );

            return emailLikeCondition; // We only need the email condition after the join
        };
    }

    public static Specification<Tuple> fetchAllRelatedEntitiesAsDTO() {
        return (
            Root<Tuple> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder
        ) -> {
            // Join all the necessary entities as before
            Join<Person, BusinessEntity> businessEntityJoin = root.join(
                "businessEntity"
            );
            Join<Person, Password> passwordJoin = root.join("passwords");
            Join<Person, EmailAddress> emailAddressJoin = root.join(
                "emailAddresses"
            );
            Join<Person, PersonPhone> personPhoneJoin = root.join(
                "personPhones"
            );
            Join<PersonPhone, PhoneNumberType> phoneNumberTypeJoin =
                personPhoneJoin.join("phoneNumberType");
            Join<
                BusinessEntity,
                BusinessEntityAddress
            > businessEntityAddressJoin = businessEntityJoin.join(
                "businessEntityAddresses"
            );
            Join<BusinessEntityAddress, Address> addressJoin =
                businessEntityAddressJoin.join("address");
            Join<BusinessEntityAddress, AddressType> addressTypeJoin =
                businessEntityAddressJoin.join("addressType");
            Join<Address, StateProvince> stateProvinceJoin = addressJoin.join(
                "stateProvince"
            );
            Join<StateProvince, CountryRegion> countryRegionJoin =
                stateProvinceJoin.join("countryRegion");

            // Construct the selection for the DTO
            Selection<?>[] selections = new Selection<?>[] {
                root.get("businessEntityId").alias("personBusinessEntityId"),
                root.get("firstName"),
                root.get("lastName"),
                // ... select other fields from Person

                businessEntityJoin
                    .get("businessEntityId")
                    .alias("businessEntityBusinessEntityId"),
                // ... select other fields from BusinessEntity

                passwordJoin.get("passwordHash"),
                // ... select other fields from Password

                emailAddressJoin
                    .get("businessEntityId")
                    .alias("emailAddressBusinessEntityId"),
                emailAddressJoin.get("emailAddressValue"),
                // ... select other fields from EmailAddress

                // personPhoneJoin
                //     .get("businessEntityId")
                //     .alias("personPhoneBusinessEntityId"),
                personPhoneJoin.get("phoneNumber"),
                // ... select other fields from PersonPhone

                phoneNumberTypeJoin
                    .get("phoneNumberTypeId")
                    .alias("phoneNumberTypePhoneNumberTypeId"),
                phoneNumberTypeJoin.get("name"),
                // ... select other fields from PhoneNumberType

                businessEntityAddressJoin
                    .get("businessEntity")
                    .get("businessEntityId")
                    .alias("businessEntityAddressBusinessEntityId"),
                // ... select other fields from BusinessEntityAddress

                addressJoin.get("addressId").alias("addressAddressId"),
                addressJoin.get("addressLine1"),
                addressJoin.get("city"),
                // ... select other fields from Address

                addressTypeJoin
                    .get("addressTypeId")
                    .alias("addressTypeAddressTypeId"),
                addressTypeJoin.get("name").alias("addressTypeName"),
                // ... select other fields from AddressType

                stateProvinceJoin
                    .get("stateProvinceId")
                    .alias("stateProvinceStateProvinceId"),
                stateProvinceJoin.get("name").alias("stateProvinceName"),
                // ... select other fields from StateProvince

                countryRegionJoin
                    .get("countryRegionCode")
                    .alias("countryRegionCountryRegionCode"),
                countryRegionJoin.get("name"),
                // ... select other fields from CountryRegion
            };

            query.multiselect(selections);
            return query.getRestriction(); // No explicit WHERE clause
            // Create the query to return a Tuple
            // CriteriaQuery<Tuple> tupleQuery =
            //     criteriaBuilder.createTupleQuery();
            // tupleQuery.multiselect(selections);

            // // Set the root and joins for the Tuple query
            // Root<Person> tupleRoot = tupleQuery.from(Person.class);
            // // Re-join all the entities on the tupleRoot
            // Join<?, ?> tupleBusinessEntityJoin = tupleRoot.join(
            //     "businessEntity"
            // );
            // Join<?, ?> tuplePasswordJoin = tupleRoot.join("password");
            // Join<?, ?> tupleEmailAddressJoin = tupleRoot.join("emailAddresses");
            // Join<Person, ?> tuplePersonPhoneJoin = tupleRoot.join(
            //     "personPhones"
            // );
            // Join<?, ?> tuplePhoneNumberTypeJoin = tuplePersonPhoneJoin.join(
            //     "phoneNumberType"
            // );
            // Join<?, ?> tupleBusinessEntityAddressJoin =
            //     tupleBusinessEntityJoin.join("businessEntityAddresses");
            // Join<?, ?> tupleAddressJoin = tupleBusinessEntityAddressJoin.join(
            //     "address"
            // );
            // Join<?, ?> tupleAddressTypeJoin =
            //     tupleBusinessEntityAddressJoin.join("addressType");
            // Join<?, ?> tupleStateProvinceJoin = tupleAddressJoin.join(
            //     "stateProvince"
            // );
            // Join<?, ?> tupleCountryRegionJoin = tupleStateProvinceJoin.join(
            //     "countryRegion"
            // );

            // // Return the Tuple query and the criteria builder (for the repository to execute)
            // return tupleQuery.getRestriction(); // No explicit WHERE clause
        };
    }
}
