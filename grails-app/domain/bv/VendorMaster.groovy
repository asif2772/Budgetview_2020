package bv

class VendorMaster {

    static hasMany = [acounts:VendorBankAccount, addresses: VendorGeneralAddress]
    String vendorCode
    String vendorName
    String gender
    String companyName
    String firstName
    String middleName
    String lastName
    String email
    static belongsTo = [payment_term:PaymentTerms]
    String momentOfSending
    //String frequencyOfInvoice
    String defaultGlAccount
    String currCode
    String creditStatus
    String chamOfCommerce
    String vat
    String comments
    Integer status
    Integer byShop
    String vendorType
    Integer directDebit

    static constraints = {
        vendorCode (nullable: false, blank: false, size: 1..15)
        vendorName (nullable: false, blank: false,size: 1..100)
        gender (nullable: true, blank: false,size: 1..30)
        /*COMMENTED BY ARAFAT*/
        companyName (nullable: true, blank: true,size: 1..100)
        firstName (nullable: true, blank: true,size: 1..30)
        middleName (nullable: true, blank: true,size: 1..30)
        lastName (nullable: true, blank: true,size: 1..30)
        email (nullable: true, blank: true,size: 1..60)
        /*COMMENTED BY ARAFAT*/
        momentOfSending (nullable: true, blank: true,size: 1..30)
        //frequencyOfInvoice (nullable: true, blank: true,size: 1..30)
        defaultGlAccount (nullable: true, blank: true,size: 1..15)
        currCode (nullable: true, blank: true,size: 1..3)
        creditStatus (nullable: false, blank: false,size: 1..50)
        chamOfCommerce (nullable: true, blank: true,size: 1..50)
        vat (nullable: true, blank: true,size: 1..55)
        comments (nullable: true, blank: true,size: 1..255)
        vendorType(nullable: true, blank: true,size: 1..10)
    }

    String toString(){
        return vendorName
    }
}
