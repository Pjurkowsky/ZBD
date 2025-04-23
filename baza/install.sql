-- AdventureWorks for Postgres
--  by Lorin Thwaits

-- How to use this script:

-- Download "Adventure Works 2014 OLTP Script" from:
--   https://msftdbprodsamples.codeplex.com/downloads/get/880662

-- Extract the .zip and copy all of the CSV files into the same folder containing
-- this install.sql file and the update_csvs.rb file.

-- Modify the CSVs to work with Postgres by running:
--   ruby update_csvs.rb

-- Create the database and tables, import the data, and set up the views and keys with:
--   psql -c "CREATE DATABASE \"Adventureworks\";"
--   psql -d Adventureworks < install.sql

-- All 68 tables are properly set up.
-- All 20 views are established.
-- 68 additional convenience views are added which:
--   * Provide a shorthand to refer to tables.
--   * Add an "id" column to a primary key or primary-ish key if it makes sense.
--
--   For example, with the convenience views you can simply do:
--       SELECT pe.p.firstname, hr.e.jobtitle
--       FROM pe.p
--         INNER JOIN hr.e ON pe.p.id = hr.e.id;
--   Instead of:
--       SELECT p.firstname, e.jobtitle
--       FROM person.person AS p
--         INNER JOIN humanresources.employee AS e ON p.businessentityid = e.businessentityid;
--
-- Schemas for these views:
--   pe = person
--   hr = humanresources
--   pr = production
--   pu = purchasing
--   sa = sales
-- Easily get a list of all of these with:  \dv (pe|hr|pr|pu|sa).*

-- Enjoy!


-- -- Disconnect all other existing connections
-- SELECT pg_terminate_backend(pid)
--   FROM pg_stat_activity
--   WHERE pid <> pg_backend_pid() AND datname='Adventureworks';

\pset tuples_only on

-- Support to auto-generate UUIDs (aka GUIDs)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Support crosstab function to do PIVOT thing for Sales.vSalesPersonSalesByFiscalYears
CREATE EXTENSION tablefunc;

-------------------------------------
-- Custom data types
-------------------------------------

CREATE DOMAIN "OrderNumber" varchar(25) NULL;
CREATE DOMAIN "AccountNumber" varchar(15) NULL;

CREATE DOMAIN "Flag" boolean NOT NULL;
CREATE DOMAIN "NameStyle" boolean NOT NULL;
CREATE DOMAIN "Name" varchar(50) NULL;
CREATE DOMAIN "Phone" varchar(25) NULL;


-------------------------------------
-- Five schemas, with tables and data
-------------------------------------

CREATE SCHEMA Person
  CREATE TABLE BusinessEntity(
    BusinessEntityID SERIAL, --  NOT FOR REPLICATION
    rowguid uuid NOT NULL CONSTRAINT "DF_BusinessEntity_rowguid" DEFAULT (uuid_generate_v1()), -- ROWGUIDCOL
    ModifiedDate TIMESTAMP NOT NULL CONSTRAINT "DF_BusinessEntity_ModifiedDate" DEFAULT (NOW())
  )
  CREATE TABLE Person(
    BusinessEntityID INT NOT NULL,
    PersonType char(2) NOT NULL,
    NameStyle "NameStyle" NOT NULL CONSTRAINT "DF_Person_NameStyle" DEFAULT (false),
    Title varchar(8) NULL,
    FirstName "Name" NOT NULL,
    MiddleName "Name" NULL,
    LastName "Name" NOT NULL,
    Suffix varchar(10) NULL,
    EmailPromotion INT NOT NULL CONSTRAINT "DF_Person_EmailPromotion" DEFAULT (0),
    AdditionalContactInfo XML NULL, -- XML("AdditionalContactInfoSchemaCollection"),
    Demographics XML NULL, -- XML("IndividualSurveySchemaCollection"),
    rowguid uuid NOT NULL CONSTRAINT "DF_Person_rowguid" DEFAULT (uuid_generate_v1()), -- ROWGUIDCOL
    ModifiedDate TIMESTAMP NOT NULL CONSTRAINT "DF_Person_ModifiedDate" DEFAULT (NOW()),
    CONSTRAINT "CK_Person_EmailPromotion" CHECK (EmailPromotion BETWEEN 0 AND 2),
    CONSTRAINT "CK_Person_PersonType" CHECK (PersonType IS NULL OR UPPER(PersonType) IN ('SC', 'VC', 'IN', 'EM', 'SP', 'GC'))
  )
  CREATE TABLE StateProvince(
    StateProvinceID SERIAL,
    StateProvinceCode char(3) NOT NULL,
    CountryRegionCode varchar(3) NOT NULL,
    IsOnlyStateProvinceFlag "Flag" NOT NULL CONSTRAINT "DF_StateProvince_IsOnlyStateProvinceFlag" DEFAULT (true),
    Name "Name" NOT NULL,
    TerritoryID INT NOT NULL,
    rowguid uuid NOT NULL CONSTRAINT "DF_StateProvince_rowguid" DEFAULT (uuid_generate_v1()), -- ROWGUIDCOL
    ModifiedDate TIMESTAMP NOT NULL CONSTRAINT "DF_StateProvince_ModifiedDate" DEFAULT (NOW())
  )
  CREATE TABLE Address(
    AddressID SERIAL, --  NOT FOR REPLICATION
    AddressLine1 varchar(60) NOT NULL,
    AddressLine2 varchar(60) NULL,
    City varchar(30) NOT NULL,
    StateProvinceID INT NOT NULL,
    PostalCode varchar(15) NOT NULL,
    SpatialLocation bytea NULL,
    rowguid uuid NOT NULL CONSTRAINT "DF_Address_rowguid" DEFAULT (uuid_generate_v1()), -- ROWGUIDCOL
    ModifiedDate TIMESTAMP NOT NULL CONSTRAINT "DF_Address_ModifiedDate" DEFAULT (NOW())
  )
  CREATE TABLE AddressType(
    AddressTypeID SERIAL,
    Name "Name" NOT NULL,
    rowguid uuid NOT NULL CONSTRAINT "DF_AddressType_rowguid" DEFAULT (uuid_generate_v1()), -- ROWGUIDCOL
    ModifiedDate TIMESTAMP NOT NULL CONSTRAINT "DF_AddressType_ModifiedDate" DEFAULT (NOW())
  )
  CREATE TABLE BusinessEntityAddress(
    BusinessEntityID INT NOT NULL,
    AddressID INT NOT NULL,
    AddressTypeID INT NOT NULL,
    rowguid uuid NOT NULL CONSTRAINT "DF_BusinessEntityAddress_rowguid" DEFAULT (uuid_generate_v1()), -- ROWGUIDCOL
    ModifiedDate TIMESTAMP NOT NULL CONSTRAINT "DF_BusinessEntityAddress_ModifiedDate" DEFAULT (NOW())
  )
  CREATE TABLE ContactType(
    ContactTypeID SERIAL,
    Name "Name" NOT NULL,
    ModifiedDate TIMESTAMP NOT NULL CONSTRAINT "DF_ContactType_ModifiedDate" DEFAULT (NOW())
  )
  CREATE TABLE BusinessEntityContact(
    BusinessEntityID INT NOT NULL,
    PersonID INT NOT NULL,
    ContactTypeID INT NOT NULL,
    rowguid uuid NOT NULL CONSTRAINT "DF_BusinessEntityContact_rowguid" DEFAULT (uuid_generate_v1()), -- ROWGUIDCOL
    ModifiedDate TIMESTAMP NOT NULL CONSTRAINT "DF_BusinessEntityContact_ModifiedDate" DEFAULT (NOW())
  )
  CREATE TABLE EmailAddress(
    BusinessEntityID INT NOT NULL,
    EmailAddressID SERIAL,
    EmailAddress varchar(50) NULL,
    rowguid uuid NOT NULL CONSTRAINT "DF_EmailAddress_rowguid" DEFAULT (uuid_generate_v1()), -- ROWGUIDCOL
    ModifiedDate TIMESTAMP NOT NULL CONSTRAINT "DF_EmailAddress_ModifiedDate" DEFAULT (NOW())
  )
  CREATE TABLE Password(
    BusinessEntityID INT NOT NULL,
    PasswordHash VARCHAR(128) NOT NULL,
    PasswordSalt VARCHAR(10) NOT NULL,
    rowguid uuid NOT NULL CONSTRAINT "DF_Password_rowguid" DEFAULT (uuid_generate_v1()), -- ROWGUIDCOL
    ModifiedDate TIMESTAMP NOT NULL CONSTRAINT "DF_Password_ModifiedDate" DEFAULT (NOW())
  )
  CREATE TABLE PhoneNumberType(
    PhoneNumberTypeID SERIAL,
    Name "Name" NOT NULL,
    ModifiedDate TIMESTAMP NOT NULL CONSTRAINT "DF_PhoneNumberType_ModifiedDate" DEFAULT (NOW())
  )
  CREATE TABLE PersonPhone(
    BusinessEntityID INT NOT NULL,
    PhoneNumber "Phone" NOT NULL,
    PhoneNumberTypeID INT NOT NULL,
    ModifiedDate TIMESTAMP NOT NULL CONSTRAINT "DF_PersonPhone_ModifiedDate" DEFAULT (NOW())
  )
  CREATE TABLE CountryRegion(
    CountryRegionCode varchar(3) NOT NULL,
    Name "Name" NOT NULL,
    ModifiedDate TIMESTAMP NOT NULL CONSTRAINT "DF_CountryRegion_ModifiedDate" DEFAULT (NOW())
  );

COMMENT ON SCHEMA Person IS 'Contains objects related to names and addresses of customers, vendors, and employees';

SELECT 'Copying data into Person.BusinessEntity';
\copy Person.BusinessEntity FROM './BusinessEntity.csv' DELIMITER E'\t' CSV;
SELECT 'Copying data into Person.Person';
\copy Person.Person FROM './Person.csv' DELIMITER E'\t' CSV;
SELECT 'Copying data into Person.StateProvince';
\copy Person.StateProvince FROM './StateProvince.csv' DELIMITER E'\t' CSV;
SELECT 'Copying data into Person.Address';
\copy Person.Address FROM './Address.csv' DELIMITER E'\t' CSV ENCODING 'latin1';
SELECT 'Copying data into Person.AddressType';
\copy Person.AddressType FROM './AddressType.csv' DELIMITER E'\t' CSV;
SELECT 'Copying data into Person.BusinessEntityAddress';
\copy Person.BusinessEntityAddress FROM './BusinessEntityAddress.csv' DELIMITER E'\t' CSV;
SELECT 'Copying data into Person.ContactType';
\copy Person.ContactType FROM './ContactType.csv' DELIMITER E'\t' CSV;
SELECT 'Copying data into Person.BusinessEntityContact';
\copy Person.BusinessEntityContact FROM './BusinessEntityContact.csv' DELIMITER E'\t' CSV;
SELECT 'Copying data into Person.EmailAddress';
\copy Person.EmailAddress FROM './EmailAddress.csv' DELIMITER E'\t' CSV;
SELECT 'Copying data into Person.Password';
\copy Person.Password FROM './Password.csv' DELIMITER E'\t' CSV;
SELECT 'Copying data into Person.PhoneNumberType';
\copy Person.PhoneNumberType FROM './PhoneNumberType.csv' DELIMITER E'\t' CSV;
SELECT 'Copying data into Person.PersonPhone';
\copy Person.PersonPhone FROM './PersonPhone.csv' DELIMITER E'\t' CSV;
SELECT 'Copying data into Person.CountryRegion';
\copy Person.CountryRegion FROM './CountryRegion.csv' DELIMITER E'\t' CSV;




COMMENT ON TABLE Person.Address IS 'Street address information for customers, employees, and vendors.';
  COMMENT ON COLUMN Person.Address.AddressID IS 'Primary key for Address records.';
  COMMENT ON COLUMN Person.Address.AddressLine1 IS 'First street address line.';
  COMMENT ON COLUMN Person.Address.AddressLine2 IS 'Second street address line.';
  COMMENT ON COLUMN Person.Address.City IS 'Name of the city.';
  COMMENT ON COLUMN Person.Address.StateProvinceID IS 'Unique identification number for the state or province. Foreign key to StateProvince table.';
  COMMENT ON COLUMN Person.Address.PostalCode IS 'Postal code for the street address.';
  COMMENT ON COLUMN Person.Address.SpatialLocation IS 'Latitude and longitude of this address.';

COMMENT ON TABLE Person.AddressType IS 'Types of addresses stored in the Address table.';
  COMMENT ON COLUMN Person.AddressType.AddressTypeID IS 'Primary key for AddressType records.';
  COMMENT ON COLUMN Person.AddressType.Name IS 'Address type description. For example, Billing, Home, or Shipping.';

COMMENT ON TABLE Person.BusinessEntity IS 'Source of the ID that connects vendors, customers, and employees with address and contact information.';
  COMMENT ON COLUMN Person.BusinessEntity.BusinessEntityID IS 'Primary key for all customers, vendors, and employees.';

COMMENT ON TABLE Person.BusinessEntityAddress IS 'Cross-reference table mapping customers, vendors, and employees to their addresses.';
  COMMENT ON COLUMN Person.BusinessEntityAddress.BusinessEntityID IS 'Primary key. Foreign key to BusinessEntity.BusinessEntityID.';
  COMMENT ON COLUMN Person.BusinessEntityAddress.AddressID IS 'Primary key. Foreign key to Address.AddressID.';
  COMMENT ON COLUMN Person.BusinessEntityAddress.AddressTypeID IS 'Primary key. Foreign key to AddressType.AddressTypeID.';

COMMENT ON TABLE Person.BusinessEntityContact IS 'Cross-reference table mapping stores, vendors, and employees to people';
  COMMENT ON COLUMN Person.BusinessEntityContact.BusinessEntityID IS 'Primary key. Foreign key to BusinessEntity.BusinessEntityID.';
  COMMENT ON COLUMN Person.BusinessEntityContact.PersonID IS 'Primary key. Foreign key to Person.BusinessEntityID.';
  COMMENT ON COLUMN Person.BusinessEntityContact.ContactTypeID IS 'Primary key.  Foreign key to ContactType.ContactTypeID.';

COMMENT ON TABLE Person.ContactType IS 'Lookup table containing the types of business entity contacts.';
  COMMENT ON COLUMN Person.ContactType.ContactTypeID IS 'Primary key for ContactType records.';
  COMMENT ON COLUMN Person.ContactType.Name IS 'Contact type description.';

COMMENT ON TABLE Person.CountryRegion IS 'Lookup table containing the ISO standard codes for countries and regions.';
  COMMENT ON COLUMN Person.CountryRegion.CountryRegionCode IS 'ISO standard code for countries and regions.';
  COMMENT ON COLUMN Person.CountryRegion.Name IS 'Country or region name.';

COMMENT ON TABLE Person.EmailAddress IS 'Where to send a person email.';
  COMMENT ON COLUMN Person.EmailAddress.BusinessEntityID IS 'Primary key. Person associated with this email address.  Foreign key to Person.BusinessEntityID';
  COMMENT ON COLUMN Person.EmailAddress.EmailAddressID IS 'Primary key. ID of this email address.';
  COMMENT ON COLUMN Person.EmailAddress.EmailAddress IS 'E-mail address for the person.';

COMMENT ON TABLE Person.Password IS 'One way hashed authentication information';
  COMMENT ON COLUMN Person.Password.PasswordHash IS 'Password for the e-mail account.';
  COMMENT ON COLUMN Person.Password.PasswordSalt IS 'Random value concatenated with the password string before the password is hashed.';

COMMENT ON TABLE Person.Person IS 'Human beings involved with AdventureWorks: employees, customer contacts, and vendor contacts.';
  COMMENT ON COLUMN Person.Person.BusinessEntityID IS 'Primary key for Person records.';
  COMMENT ON COLUMN Person.Person.PersonType IS 'Primary type of person: SC = Store Contact, IN = Individual (retail) customer, SP = Sales person, EM = Employee (non-sales), VC = Vendor contact, GC = General contact';
  COMMENT ON COLUMN Person.Person.NameStyle IS '0 = The data in FirstName and LastName are stored in western style (first name, last name) order.  1 = Eastern style (last name, first name) order.';
  COMMENT ON COLUMN Person.Person.Title IS 'A courtesy title. For example, Mr. or Ms.';
  COMMENT ON COLUMN Person.Person.FirstName IS 'First name of the person.';
  COMMENT ON COLUMN Person.Person.MiddleName IS 'Middle name or middle initial of the person.';
  COMMENT ON COLUMN Person.Person.LastName IS 'Last name of the person.';
  COMMENT ON COLUMN Person.Person.Suffix IS 'Surname suffix. For example, Sr. or Jr.';
  COMMENT ON COLUMN Person.Person.EmailPromotion IS '0 = Contact does not wish to receive e-mail promotions, 1 = Contact does wish to receive e-mail promotions from AdventureWorks, 2 = Contact does wish to receive e-mail promotions from AdventureWorks and selected partners.';
  COMMENT ON COLUMN Person.Person.Demographics IS 'Personal information such as hobbies, and income collected from online shoppers. Used for sales analysis.';
  COMMENT ON COLUMN Person.Person.AdditionalContactInfo IS 'Additional contact information about the person stored in xml format.';

COMMENT ON TABLE Person.PersonPhone IS 'Telephone number and type of a person.';
  COMMENT ON COLUMN Person.PersonPhone.BusinessEntityID IS 'Business entity identification number. Foreign key to Person.BusinessEntityID.';
  COMMENT ON COLUMN Person.PersonPhone.PhoneNumber IS 'Telephone number identification number.';
  COMMENT ON COLUMN Person.PersonPhone.PhoneNumberTypeID IS 'Kind of phone number. Foreign key to PhoneNumberType.PhoneNumberTypeID.';

COMMENT ON TABLE Person.PhoneNumberType IS 'Type of phone number of a person.';
  COMMENT ON COLUMN Person.PhoneNumberType.PhoneNumberTypeID IS 'Primary key for telephone number type records.';
  COMMENT ON COLUMN Person.PhoneNumberType.Name IS 'Name of the telephone number type';

COMMENT ON TABLE Person.StateProvince IS 'State and province lookup table.';
  COMMENT ON COLUMN Person.StateProvince.StateProvinceID IS 'Primary key for StateProvince records.';
  COMMENT ON COLUMN Person.StateProvince.StateProvinceCode IS 'ISO standard state or province code.';
  COMMENT ON COLUMN Person.StateProvince.CountryRegionCode IS 'ISO standard country or region code. Foreign key to CountryRegion.CountryRegionCode.';
  COMMENT ON COLUMN Person.StateProvince.IsOnlyStateProvinceFlag IS '0 = StateProvinceCode exists. 1 = StateProvinceCode unavailable, using CountryRegionCode.';
  COMMENT ON COLUMN Person.StateProvince.Name IS 'State or province description.';
  COMMENT ON COLUMN Person.StateProvince.TerritoryID IS 'ID of the territory in which the state or province is located. Foreign key to SalesTerritory.SalesTerritoryID.';


-------------------------------------
-- PRIMARY KEYS
-------------------------------------
ALTER TABLE Person.Address ADD
    CONSTRAINT "PK_Address_AddressID" PRIMARY KEY
    (AddressID);
CLUSTER Person.Address USING "PK_Address_AddressID";
ALTER TABLE Person.AddressType ADD
    CONSTRAINT "PK_AddressType_AddressTypeID" PRIMARY KEY
    (AddressTypeID);
CLUSTER Person.AddressType USING "PK_AddressType_AddressTypeID";
ALTER TABLE Person.BusinessEntity ADD
    CONSTRAINT "PK_BusinessEntity_BusinessEntityID" PRIMARY KEY
    (BusinessEntityID);
CLUSTER Person.BusinessEntity USING "PK_BusinessEntity_BusinessEntityID";
ALTER TABLE Person.BusinessEntityAddress ADD
    CONSTRAINT "PK_BusinessEntityAddress_BusinessEntityID_AddressID_AddressType" PRIMARY KEY
    (BusinessEntityID, AddressID, AddressTypeID);
CLUSTER Person.BusinessEntityAddress USING "PK_BusinessEntityAddress_BusinessEntityID_AddressID_AddressType";
ALTER TABLE Person.BusinessEntityContact ADD
    CONSTRAINT "PK_BusinessEntityContact_BusinessEntityID_PersonID_ContactTypeI" PRIMARY KEY
    (BusinessEntityID, PersonID, ContactTypeID);
CLUSTER Person.BusinessEntityContact USING "PK_BusinessEntityContact_BusinessEntityID_PersonID_ContactTypeI";
ALTER TABLE Person.ContactType ADD
    CONSTRAINT "PK_ContactType_ContactTypeID" PRIMARY KEY
    (ContactTypeID);
CLUSTER Person.ContactType USING "PK_ContactType_ContactTypeID";
ALTER TABLE Person.CountryRegion ADD
    CONSTRAINT "PK_CountryRegion_CountryRegionCode" PRIMARY KEY
    (CountryRegionCode);
CLUSTER Person.CountryRegion USING "PK_CountryRegion_CountryRegionCode";
ALTER TABLE Person.EmailAddress ADD
    CONSTRAINT "PK_EmailAddress_BusinessEntityID_EmailAddressID" PRIMARY KEY
    (BusinessEntityID, EmailAddressID);
CLUSTER Person.EmailAddress USING "PK_EmailAddress_BusinessEntityID_EmailAddressID";
ALTER TABLE Person.Password ADD
    CONSTRAINT "PK_Password_BusinessEntityID" PRIMARY KEY
    (BusinessEntityID);
CLUSTER Person.Password USING "PK_Password_BusinessEntityID";
ALTER TABLE Person.Person ADD
    CONSTRAINT "PK_Person_BusinessEntityID" PRIMARY KEY
    (BusinessEntityID);
CLUSTER Person.Person USING "PK_Person_BusinessEntityID";
ALTER TABLE Person.PersonPhone ADD
    CONSTRAINT "PK_PersonPhone_BusinessEntityID_PhoneNumber_PhoneNumberTypeID" PRIMARY KEY
    (BusinessEntityID, PhoneNumber, PhoneNumberTypeID);
CLUSTER Person.PersonPhone USING "PK_PersonPhone_BusinessEntityID_PhoneNumber_PhoneNumberTypeID";
ALTER TABLE Person.PhoneNumberType ADD
    CONSTRAINT "PK_PhoneNumberType_PhoneNumberTypeID" PRIMARY KEY
    (PhoneNumberTypeID);
CLUSTER Person.PhoneNumberType USING "PK_PhoneNumberType_PhoneNumberTypeID";
ALTER TABLE Person.StateProvince ADD
    CONSTRAINT "PK_StateProvince_StateProvinceID" PRIMARY KEY
    (StateProvinceID);
CLUSTER Person.StateProvince USING "PK_StateProvince_StateProvinceID";

-------------------------------------
-- FOREIGN KEYS
-------------------------------------

ALTER TABLE Person.Address ADD
    CONSTRAINT "FK_Address_StateProvince_StateProvinceID" FOREIGN KEY
    (StateProvinceID) REFERENCES Person.StateProvince(StateProvinceID);
ALTER TABLE Person.BusinessEntityAddress ADD
    CONSTRAINT "FK_BusinessEntityAddress_Address_AddressID" FOREIGN KEY
    (AddressID) REFERENCES Person.Address(AddressID);
ALTER TABLE Person.BusinessEntityAddress ADD
    CONSTRAINT "FK_BusinessEntityAddress_AddressType_AddressTypeID" FOREIGN KEY
    (AddressTypeID) REFERENCES Person.AddressType(AddressTypeID);
ALTER TABLE Person.BusinessEntityAddress ADD
    CONSTRAINT "FK_BusinessEntityAddress_BusinessEntity_BusinessEntityID" FOREIGN KEY
    (BusinessEntityID) REFERENCES Person.BusinessEntity(BusinessEntityID);
ALTER TABLE Person.BusinessEntityContact ADD
    CONSTRAINT "FK_BusinessEntityContact_Person_PersonID" FOREIGN KEY
    (PersonID) REFERENCES Person.Person(BusinessEntityID);
ALTER TABLE Person.BusinessEntityContact ADD
    CONSTRAINT "FK_BusinessEntityContact_ContactType_ContactTypeID" FOREIGN KEY
    (ContactTypeID) REFERENCES Person.ContactType(ContactTypeID);
ALTER TABLE Person.BusinessEntityContact ADD
    CONSTRAINT "FK_BusinessEntityContact_BusinessEntity_BusinessEntityID" FOREIGN KEY
    (BusinessEntityID) REFERENCES Person.BusinessEntity(BusinessEntityID);
ALTER TABLE Person.EmailAddress ADD
    CONSTRAINT "FK_EmailAddress_Person_BusinessEntityID" FOREIGN KEY
    (BusinessEntityID) REFERENCES Person.Person(BusinessEntityID);
ALTER TABLE Person.Password ADD
    CONSTRAINT "FK_Password_Person_BusinessEntityID" FOREIGN KEY
    (BusinessEntityID) REFERENCES Person.Person(BusinessEntityID);
ALTER TABLE Person.Person ADD
    CONSTRAINT "FK_Person_BusinessEntity_BusinessEntityID" FOREIGN KEY
    (BusinessEntityID) REFERENCES Person.BusinessEntity(BusinessEntityID);
ALTER TABLE Person.PersonPhone ADD
    CONSTRAINT "FK_PersonPhone_Person_BusinessEntityID" FOREIGN KEY
    (BusinessEntityID) REFERENCES Person.Person(BusinessEntityID);
ALTER TABLE Person.PersonPhone ADD
    CONSTRAINT "FK_PersonPhone_PhoneNumberType_PhoneNumberTypeID" FOREIGN KEY
    (PhoneNumberTypeID) REFERENCES Person.PhoneNumberType(PhoneNumberTypeID);
ALTER TABLE Person.StateProvince ADD
    CONSTRAINT "FK_StateProvince_CountryRegion_CountryRegionCode" FOREIGN KEY
    (CountryRegionCode) REFERENCES Person.CountryRegion(CountryRegionCode);
