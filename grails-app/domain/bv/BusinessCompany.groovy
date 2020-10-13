package bv

class BusinessCompany {

    String name
    String driverName
    String serverUrl
    String dbName
    String dbUser
    String dbPassword
    Integer createdBy
    //Date createdDate
    Date dateCreated
    Integer updatedBy
    //Date updatedDate
    Date lastUpdated
    Integer status
    String dns

    static constraints = {
        dns                    (nullable: true, blank: true)
    }

    def beforeInsert = {
        dateCreated = new Date()
    }
    def beforeUpdate ={
        lastUpdated = new Date()
    }


}
