package bv

class CustomerPostalAddress {
    Integer id
    static belongsTo = [customer:CustomerMaster,postalCountry:Countries]
    String postalContactPersonName
    String postalAddressLine1
    String postalAddressLine2
    String postalState
    String postalPostcode
    String postalCity
    Integer status

    static constraints = {
        postalContactPersonName (nullable: true, blank: true, size: 1..255)
        postalAddressLine1 (nullable: true, blank: true, size: 1..255)
        postalAddressLine2 (nullable: true, blank: true, size: 1..255)
        postalState (nullable: true, blank: true, size: 1..100)
        postalPostcode (nullable: true, blank: true, size: 1..50)
        postalCity (nullable: true, blank: true,size: 1..50)
    }
}
