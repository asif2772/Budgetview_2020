package bv

class VatCategory {
    String  categoryName
    Double rate
    Integer status
    String salesGlAccount
    String purchaseGlAccount

    static constraints = {
        categoryName (nullable: false, blank: false, size: 1..60)
        rate (nullable: false, blank: false, size: 1..19)
        salesGlAccount (nullable: true, blank: true,size: 1..15)
        purchaseGlAccount (nullable: true, blank: true,size: 1..15)
    }

    String toString(){
        return categoryName
    }
}







