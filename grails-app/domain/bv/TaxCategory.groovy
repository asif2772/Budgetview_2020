package bv

class TaxCategory {
    String  categoryName
    Double rate
    String salesGlCode
    Integer status

    static constraints = {
        categoryName (nullable: false, blank: false, size: 1..60)
        rate (nullable: false, blank: false, size: 1..19)
        salesGlCode (nullable: false, blank: false, size: 1..15)
    }

    String toString(){
        return categoryName
    }
}