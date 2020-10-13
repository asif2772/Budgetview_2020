package bv

class CustomerMaster {
    Integer id
    static hasMany = [customerBankAccount:CustomerBankAccount, customerGeneralAddress: CustomerGeneralAddress, customerPostalAddress:CustomerPostalAddress, customerShipmentAddress:CustomerShipmentAddress]
    String customerCode
    String customerName
    String gender
    String companyName
    String firstName
    String middleName
    String lastName
    String email
    static belongsTo = [paymentTerm:PaymentTerms]
    String momentOfSending
    //String frequencyOfInvoice
    String defaultGlAccount
    String currCode
    String creditStatus
    String chamOfCommerce
    String vat
    String comments
    Integer status
    String customerType

    static constraints = {
        customerCode (nullable: false, blank: false, size: 1..15)
        customerName (nullable: false, blank: false,size: 1..100)
        gender (nullable: true, blank: false,size: 1..30)
        companyName (nullable: true, blank: true,size: 1..100)
        firstName (nullable: true, blank: true,size: 1..30)
        middleName (nullable: true, blank: true,size: 1..30)
        lastName (nullable: true, blank: true,size: 1..30)
        email (nullable: true, blank: true,size: 1..60)
        momentOfSending (nullable: true, blank: true,size: 1..30)
        //frequencyOfInvoice (nullable: true, blank: true,size: 1..30)
        defaultGlAccount (nullable: true, blank: true,size: 1..15)
        currCode (nullable: true, blank: true,size: 1..3)
        creditStatus (nullable: false, blank: false,size: 1..50)
        chamOfCommerce (nullable: true, blank: true,size: 1..50)
        vat (nullable: true, blank: true,size: 1..55)
        comments (nullable: true, blank: true,size: 1..255)
        customerType(nullable: true, blank: true,size: 1..10)
    }
}