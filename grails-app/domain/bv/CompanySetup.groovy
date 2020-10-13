package bv

import auth.User

class CompanySetup {

    String companyFullName
    String companyShortName
    String addressLine1
    String addressLine2
    String generalStreet
    String generalPostalCode
    String zipCode
    String city
    String state
    String country
    String postalAddressLine1
    String postalZipCode
    String postalCity
    String postalState
    String postalCountry
    String phoneNo
    String mobileNo
    String faxNo
    String emailAddress
    String secondEmailAddress
    String websiteAddress
    String logo
    Double incomeTaxReservation
    Double unforeseenExpenseReservation
    Integer numberOfBookingPeriod
    String companyDateFormat
    String vatNo
    String taxNo
    String chamberCommerceNo
    Integer amountDecimalPoint
    Integer quantityDecimalPoint
    Integer percentageDecimalPoint
    String decimalRoundingType
    String dateSeperator
    String thousandSeperator
    String decimalSeprator
    String showGlcodeInReport
    String showItemcodeInReport
    String LANGUAGE
    String reportPageSize
    String notes
    String iban

    static belongsTo = [paymentTerm:PaymentTerms,currency:Currencies,taxCategory:TaxCategory,
                        vatCategory:VatCategory,momentOfSendingInvoice:MomentOfSendingInvoice]

    static constraints = {
        companyFullName (nullable: false)
        companyShortName (nullable: false)
        addressLine1 (nullable: true)
        generalPostalCode (nullable: true)
        generalStreet (nullable: true)
        addressLine2 (nullable: true)
        zipCode (nullable: true)
        city (nullable: true)
        state (nullable: true)
        country (nullable: true)
        postalAddressLine1 (nullable: true)
        postalZipCode (nullable: true)
        postalCity (nullable: true)
        postalState (nullable: true)
        postalCountry (nullable: true)
        phoneNo (nullable: false)
        mobileNo (nullable: true)
        faxNo (nullable: true)
        emailAddress (nullable: false)
        secondEmailAddress (nullable: true)
        websiteAddress (nullable: true)
        logo (nullable: true)
        incomeTaxReservation (nullable: true)
        unforeseenExpenseReservation (nullable: true)
        numberOfBookingPeriod (nullable: true)
        chamberCommerceNo (nullable: true)
        notes (nullable: true)
    }

}