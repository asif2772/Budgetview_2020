package bv

class VendorPostalAddress {
    Integer id
    static belongsTo = [vendor:VendorMaster,postalCountry:Countries]
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
        status (nullable: false, blank: false,size: 1..60)
    }

    static mapping =  {
        id column: "id",type: 'integer', length:11
        postalContactPersonName column: "postal_contact_person_name",type: 'string', length:255
        postalAddressLine1 column: "postal_address_line1",type: 'string', length:255
        postalAddressLine2 column: "postal_address_line2",type: 'string', length:255
        postalState column: "postal_state",type: 'string', length:100
        postalPostcode column: "postal_postcode",type: 'string', length:50
        postalCity column: "postal_city",type: 'string', length:50
    }

}

