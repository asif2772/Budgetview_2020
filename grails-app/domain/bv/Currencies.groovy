package bv

class Currencies {
    Integer id
    String currency
    String currCode
    String currSymbol
    static belongsTo = [country:Countries]
    String hundredsName
    Integer status

    static constraints = {
        currency (nullable: false, blank: false, size: 1..60)
        currCode (nullable: false, blank: false,size: 1..3)
        currSymbol (nullable: false, blank: false,size: 1..10)
        hundredsName (nullable: false, blank: false,size: 1..50)
    }

    static mapping =  {
        id column: "id",type: 'integer', length:3
        currency column: "currency",type: 'string', length:60
        currCode column: "curr_code",type:'string', length: 3
        currSymbol column: "curr_symbol",type:'string', length: 10
        hundredsName column: "hundreds_name",type:'string', length: 10
        status column: "status",type:'integer', length: 1
    }
    String toString(){
        return currency
    }
}