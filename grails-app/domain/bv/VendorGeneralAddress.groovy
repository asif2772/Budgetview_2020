package bv

class VendorGeneralAddress {

    Integer id
    static belongsTo = [vendor:VendorMaster,country:Countries]
    String contactPersonName
    String contactPersonReference
    String contactDealType
    String phoneNo
    String mobileNo
    String fax
    String secondEmail
    String websiteAddress
    String addressLine1
    String addressLine2
    String state
    String postalCode
    String city
    Integer status

    static constraints = {
        contactPersonName (nullable: false, blank: false, size: 1..255)
        contactPersonReference (nullable: true, blank: true, size: 1..100)
        contactDealType (nullable: true, blank: true, size: 1..30)
        phoneNo (nullable: true, blank: true, size: 1..60)
        mobileNo (nullable: true, blank: true, size: 1..60)
        fax (nullable: true, blank: true,size: 1..60)
        secondEmail (nullable: true, blank: true,size: 1..60)
        websiteAddress (nullable: true, blank: true,size: 1..60)
        addressLine1 (nullable: true, blank: true,size: 1..255)
        addressLine2 (nullable: true, blank: true,size: 1..255)
        state (nullable: true, blank: true,size: 1..100)
        postalCode (nullable: false, blank: false,size: 1..50)
        city (nullable: false, blank: false,size: 1..50)
    }
}
