package bv

class CustomerBankAccount {
    Integer id
   /* static belongsTo = [customer:CustomerMaster]*/
    Integer customerId
    String bankAccountName
    String ibanPrefix
    String bankAccountNo
    Integer status

    static constraints = {
        bankAccountName (nullable: false, blank: false,size: 1..100)
        customerId (nullable: false, blank: false,size: 1..11)
        ibanPrefix (nullable: false, blank: false,size: 1..50)
        bankAccountNo (nullable: false, blank: false,size: 1..50)
    }
}
