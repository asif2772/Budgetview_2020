package bv

import groovy.sql.GroovyRowResult
import groovy.sql.Sql

import javax.sql.DataSource

class UserTagLib {
//    static defaultEncodeAs = 'html'
    //static encodeAsForTags = [tagName: 'raw']
    DataSource dataSource

    public LinkedHashMap getConnectionInformation() {

        ArrayList companyConfig
        companyConfig = BusinessCompany.executeQuery("SELECT id,name,createdBy,dateCreated,dbName,dbPassword,dbUser,driverName,serverUrl,status,updatedBy,lastUpdated FROM bv.BusinessCompany WHERE dbName = 'tomcatbu_budgetview' ")

        Map map = ["id": 0, "companyName": '', "createdBy": 0, "createdDate": '', "dbName": '', "dbPassword": '', "dbUser": '', "driverName": '', "serverUrl": '', "status": 0, "updatedBy": 0, "updatedDate": '']

        map.id = companyConfig[0][0]
        map.companyName = companyConfig[0][1]
        map.createdBy = companyConfig[0][2]
        map.createdDate = companyConfig[0][3]
        map.dbName = companyConfig[0][4]
        map.dbPassword = companyConfig[0][5]
        map.dbUser = companyConfig[0][6]
        map.driverName = companyConfig[0][7]
        map.serverUrl = companyConfig[0][8]
        map.status = companyConfig[0][9]
        map.updatedBy = companyConfig[0][10]
        map.updatedDate = companyConfig[0][11]
        return [map]
    }


    def gettingPermittedDBInfo(userId){

        def companyConfig = getConnectionInformation()
        ///Get Configuration object//////
        def db = Sql.newInstance(companyConfig.serverUrl + companyConfig.dbName, companyConfig.dbUser, companyConfig.dbPassword, companyConfig.driverName)
/*

        def sqlQuery ="""SELECT up.id,up.version,up.business_company_id,up.main_user_id,up.permission_status,
                        up.permitted_user_id,bc.db_name as dbName,us.username FROM user_permission as up
                        INNER JOIN business_company as bc on up.business_company_id = bc.id
                        INNER JOIN `user` as us on up.main_user_id = us.id
                        WHERE  up.permitted_user_id = '${userId}' and up.permission_status ='1' """
*/

        def sqlQuery ="""SELECT up.id,up.version,up.business_company_id,up.main_user_id,up.permission_status,
                        up.permitted_user_id,bc.db_name as dbName,bc.`name` FROM user_permission as up
                        INNER JOIN business_company as bc on up.business_company_id = bc.id
                        WHERE  up.permitted_user_id = '${userId}' and up.permission_status ='1' """


        List<GroovyRowResult> dataGridList = db.rows(sqlQuery);


        return  dataGridList;

    }

    def gettingPermittedDBAllInfoForConn(userId){

        def companyConfig = getConnectionInformation()
        ///Get Configuration object//////
        def db = Sql.newInstance(companyConfig.serverUrl + companyConfig.dbName, companyConfig.dbUser, companyConfig.dbPassword, companyConfig.driverName)

        def sqlQuery ="""SELECT up.id,up.version,up.business_company_id,up.main_user_id,up.permission_status,
                        up.permitted_user_id,bc.db_name as dbName,bc.`name`, bc.db_password, bc.driver_name,
                        bc.db_user, bc.server_url FROM user_permission as up
                        INNER JOIN business_company as bc on up.business_company_id = bc.id
                        WHERE  up.permitted_user_id = '${userId}' and up.permission_status ='1' """


        List<GroovyRowResult> dataGridList = db.rows(sqlQuery);


        return  dataGridList;

    }
}
