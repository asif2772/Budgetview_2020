package bv

class CustomerShipmentAddress {
    Integer id
    static belongsTo = [customer:CustomerMaster,shipCountry:Countries]
    String shipContactName
    String shipAddLine1
    String shipAddLine2
    String shipPhoneNo1
    String shipPhoneNo2
    String shipFax
    String shipEmail
    String shipWebsite
    String shipState
    String shipPostCode
    String shipCity
    String note
    Integer status

    static constraints = {
        shipContactName (nullable: false, blank: false, size: 1..255)
        shipAddLine1 (nullable: true, blank: true, size: 1..255)
        shipAddLine2 (nullable: true, blank: true, size: 1..255)
        shipPhoneNo1 (nullable: true, blank: true, size: 1..100)
        shipPhoneNo2 (nullable: true, blank: true, size: 1..50)
        shipFax (nullable: true, blank: true,size: 1..50)
        shipEmail (nullable: true, blank: true,size: 1..50)
        shipWebsite (nullable: true, blank: true,size: 1..50)
        shipState (nullable: true, blank: true,size: 1..50)
        shipPostCode (nullable: true, blank: true,size: 1..50)
        shipCity (nullable: true, blank: true,size: 1..50)
        status (nullable: false, blank: false,size: 1..60)
    }
}
