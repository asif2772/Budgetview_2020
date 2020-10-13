package bv

class MomentOfSendingInvoice {

    String      name
    Integer     invoiceSendValue
    String      description
    Integer     status

    static constraints = {
        name           (nullable: false, blank: false, size: 1..80)
        invoiceSendValue (nullable: false, blank:false)
        description     (nullable: true, size: 1..256)
    }

    String toString(){
        return name
    }

}
