COPY BusinessEntity(BusinessEntityID, rowguid, ModifiedDate) FROM '/var/lib/postgresql/imports/BusinessEntity.csv' DELIMITER E'\t' CSV;

COPY Person(BusinessEntityID, PersonType, NameStyle, Title, FirstName, MiddleName, LastName, Suffix, EmailPromotion, AdditionalContactInfo, Demographics, rowguid, ModifiedDate) FROM '/var/lib/postgresql/imports/Person.csv' DELIMITER E'\t' CSV;

COPY StateProvince(StateProvinceID, StateProvinceCode, CountryRegionCode, IsOnlyStateProvinceFlag,Name, TerritoryID, rowguid, ModifiedDate) FROM '/var/lib/postgresql/imports/StateProvince.csv' DELIMITER E'\t' CSV;

COPY Address(AddressID, AddressLine1, AddressLine2, City, StateProvinceID,PostalCode, SpatialLocation, rowguid, ModifiedDate) FROM '/var/lib/postgresql/imports/Address.csv' DELIMITER E'\t' CSV ENCODING 'latin1';

COPY AddressType(AddressTypeID, Name, rowguid, ModifiedDate) FROM '/var/lib/postgresql/imports/AddressType.csv' DELIMITER E'\t' CSV;

COPY BusinessEntityAddress(BusinessEntityID, AddressID, AddressTypeID, rowguid, ModifiedDate) FROM '/var/lib/postgresql/imports/BusinessEntityAddress.csv' DELIMITER E'\t' CSV;

COPY ContactType(ContactTypeID, Name, ModifiedDate) FROM '/var/lib/postgresql/imports/ContactType.csv' DELIMITER E'\t' CSV;

COPY BusinessEntityContact(BusinessEntityID, PersonID, ContactTypeID, rowguid, ModifiedDate) FROM '/var/lib/postgresql/imports/BusinessEntityContact.csv' DELIMITER E'\t' CSV;

COPY EmailAddress(BusinessEntityID, EmailAddressID, EmailAddress, rowguid, ModifiedDate) FROM '/var/lib/postgresql/imports/EmailAddress.csv' DELIMITER E'\t' CSV;

COPY Password(BusinessEntityID, PasswordHash, PasswordSalt, rowguid, ModifiedDate) FROM '/var/lib/postgresql/imports/Password.csv' DELIMITER E'\t' CSV;

COPY PhoneNumberType(PhoneNumberTypeID, Name, ModifiedDate) FROM '/var/lib/postgresql/imports/PhoneNumberType.csv' DELIMITER E'\t' CSV;

COPY PersonPhone(BusinessEntityID, PhoneNumber, PhoneNumberTypeID, ModifiedDate) FROM '/var/lib/postgresql/imports/PersonPhone.csv' DELIMITER E'\t' CSV;

COPY CountryRegion(CountryRegionCode, Name, ModifiedDate) FROM '/var/lib/postgresql/imports/CountryRegion.csv' DELIMITER E'\t' CSV;
