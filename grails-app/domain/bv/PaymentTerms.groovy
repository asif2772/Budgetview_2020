package bv

class PaymentTerms {
    /*static      hasMany = [paymentTerm:VendorMaster]*/
    String      terms
    Integer     daysBeforeDue
    Integer     alertStartDays
    Integer     alertRepeatDays
    Integer     status

    static constraints = {
        terms           (nullable: false, blank: false, size: 1..80)
        daysBeforeDue   (nullable: false, blank: false,size: 1..3)
        alertStartDays  (nullable: false, blank: false,size: 1..3)
        alertRepeatDays (nullable: true, blank: true,size: 1..3)
    }

/*    static mapping =  {
        terms column: "terms",type: 'string', length:80
        daysBeforeDue column: "alert_repeat_days",type: 'integer', length:3
        alertStartDays column: "alert_start_days",type: 'integer', length:3
        alertRepeatDays column: "days_before_due",type: 'integer', length:3
    }*/

    String toString(){
        return terms
    }
}
