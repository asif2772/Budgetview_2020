package bv

class ProductUnit {
    String unitName
    Integer decimals
    Integer status
    static constraints = {
        unitName (nullable: false, blank: false, size: 1..20)
        decimals (nullable: false, blank: false, size: 1..2)
    }
}
