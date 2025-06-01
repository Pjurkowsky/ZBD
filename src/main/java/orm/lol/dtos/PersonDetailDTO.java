package orm.lol.dtos;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import orm.lol.entites.person.PersonPhoneId;

@Getter
@Setter
public class PersonDetailDTO {

    private Integer personBusinessEntityId;
    private String personType;
    private boolean nameStyle; // "NameStyle" -> boolean
    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
    private Integer emailPromotion;
    private String additionalContactInfo; // XML -> String
    private String demographics; // XML -> String
    private UUID rowguid;
    private LocalDateTime modifiedDate;

    private Integer businessEntityBusinessEntityId;

    private Integer passwordBusinessEntityId;
    private String passwordHash;
    private String passwordSalt;

    private Integer emailAddressId;
    private Integer emailAddressBusinessEntityId;
    private String emailAddressValue;

    private Integer personPhoneBusinessEntityId;
    private String personPhoneNumber;
    private PersonPhoneId personPhoneId;
    // ... fields from PersonPhone

    private Integer phoneNumberTypePhoneNumberTypeId;
    private String phoneNumberTypeName;

    private Integer businessEntityAddressBusinessEntityId;
    // ... fields from BusinessEntityAddress

    private Integer addressAddressId;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private Integer stateProvinceId; // Klucz obcy do StateProvince
    private String postalCode;
    private byte[] spatialLocation;
    // ... fields from Address

    private Integer addressTypeAddressTypeId;
    private String addressTypeName;
    // ... fields from AddressType

    private Integer stateProvinceStateProvinceId;
    private String stateProvinceName;
    private String stateProvinceCode;
    private String stateProvinceCountryRegionCode; // Klucz obcy do CountryRegion
    private boolean stateProvinceIsOnlyStateProvinceFlag; // "Flag" -> boolean
    private Integer stateProvinceTerritoryId;

    private String countryRegionCountryRegionCode;
    private String countryRegionName;

    // ... fields from CountryRegion

    // Constructor(s), Getters, and Setters
    public PersonDetailDTO(
        Integer personBusinessEntityId,
        String personType,
        boolean nameStyle, // "NameStyle" -> boolean
        String title,
        String firstName,
        String middleName,
        String lastName,
        String suffix,
        Integer emailPromotion,
        String additionalContactInfo, // XML -> String
        String demographics, // XML -> String
        UUID rowguid,
        LocalDateTime modifiedDate,
        Integer businessEntityBusinessEntityId,/* ... other BusinessEntity fields */
        Integer passwordBusinessEntityId,
        String passwordHash,
        String passwordSalt,
        Integer emailAddressId,
        Integer emailAddressBusinessEntityId,
        String emailAddressValue,/* ... other EmailAddress fields */
        Integer personPhoneBusinessEntityId,
        String personPhoneNumber,
        PersonPhoneId personPhoneId,/* ... other PersonPhone fields */
        Integer phoneNumberTypePhoneNumberTypeId,
        String phoneNumberTypeName,/* ... other PhoneNumberType fields */
        Integer businessEntityAddressBusinessEntityId,/* ... other BusinessEntityAddress fields */
        Integer addressAddressId,
        String addressLine1,
        String addressLine2,
        String city,
        Integer stateProvinceId,
        String postalCode,
        byte[] spatialLocation,/* ... other Address fields */
        Integer addressTypeAddressTypeId,
        String addressTypeName,/* ... other AddressType fields */
        Integer stateProvinceStateProvinceId,
        String stateProvinceName,
        String stateProvinceCode,
        String stateProvinceCountryRegionCode, // Klucz obcy do CountryRegion
        boolean stateProvinceIsOnlyStateProvinceFlag, // "Flag" -> boolean
        Integer stateProvinceTerritoryId,/* ... other StateProvince fields */
        String countryRegionCountryRegionCode,
        String countryRegionName/* ... other CountryRegion fields */
    ) {
        this.personBusinessEntityId = personBusinessEntityId;
        this.personType = personType;
        this.nameStyle = nameStyle; // "NameStyle" -> boolean
        this.title = title;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.suffix = suffix;
        this.emailPromotion = emailPromotion;
        this.additionalContactInfo = additionalContactInfo; // XML -> String
        this.demographics = demographics; // XML -> String
        this.rowguid = rowguid;
        this.modifiedDate = modifiedDate;

        this.businessEntityBusinessEntityId = businessEntityBusinessEntityId;

        this.passwordBusinessEntityId = passwordBusinessEntityId;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;

        this.emailAddressId = emailAddressId;
        this.emailAddressBusinessEntityId = emailAddressBusinessEntityId;
        this.emailAddressValue =
            emailAddressValue;/* ... other EmailAddress fields */

        this.personPhoneBusinessEntityId = personPhoneBusinessEntityId;
        this.personPhoneNumber = personPhoneNumber;
        this.personPhoneId = personPhoneId;/* ... other PersonPhone fields */

        this.phoneNumberTypePhoneNumberTypeId =
            phoneNumberTypePhoneNumberTypeId;
        this.phoneNumberTypeName = phoneNumberTypeName;

        this.businessEntityAddressBusinessEntityId =
            businessEntityAddressBusinessEntityId;

        this.addressAddressId = addressAddressId;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.stateProvinceId = stateProvinceId;
        this.postalCode = postalCode;
        this.spatialLocation = spatialLocation;/* ... other Address fields */

        this.addressTypeAddressTypeId = addressTypeAddressTypeId;
        this.addressTypeName = addressTypeName;

        this.stateProvinceStateProvinceId = stateProvinceStateProvinceId;
        this.stateProvinceName = stateProvinceName;
        this.stateProvinceCode = stateProvinceCode;
        this.stateProvinceCountryRegionCode = stateProvinceCountryRegionCode; // Klucz obcy do CountryRegion
        this.stateProvinceIsOnlyStateProvinceFlag =
            stateProvinceIsOnlyStateProvinceFlag; // "Flag" -> boolean
        this.stateProvinceTerritoryId =
            stateProvinceTerritoryId;/* ... other StateProvince fields */

        this.countryRegionCountryRegionCode = countryRegionCountryRegionCode;
        this.countryRegionName = countryRegionName;
    }
}
