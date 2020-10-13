package bv

class Countries {

    String iso2;
    String name;
    String printablename;
    String iso3;
    Integer numcode;
    Integer status
    static constraints = {

    }
    static mapping = {
        iso2 column: "iso2",type: 'string', length:6
        name column: "name",type:'string', length: 20
        printablename column: "printablename",type:'string', length: 100
        iso3 column: "iso3",type:'string', length: 3, nullable:false
        numcode column: "numcode",type:'integer', length: 3, nullable:false, blank:false
    }
    String toString(){
        return printablename
    }
}
