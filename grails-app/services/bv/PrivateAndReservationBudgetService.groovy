package bv

import grails.converters.JSON
import grails.gorm.transactions.Transactional
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import vb.GridEntity

import java.text.SimpleDateFormat


@Transactional
class PrivateAndReservationBudgetService {
    BudgetViewDatabaseService budgetViewDatabaseService
    PrivateAndReservationBudgetService privateAndReservationBudgetService
    private String contextPath = Holders.applicationContext.servletContext.contextPath
//    private String contextPath = ConfigurationHolder.config.grails.applicationContext;

    def serviceMethod() {

    }

    def createReservationBudget(def dataArr) {
        def reservationCode = new BudgetDetailsTagLib().getGeneratedReservationCode()
        Map mapReservationMaster = [
                reservationCode : reservationCode,
                reservationName : "${dataArr.reservationName}",
                reservationType : "${dataArr.reservationType}",
                defaultGlAccount: "${dataArr.defaultGlAccount}",
                status          : 1,
                vat             : "${dataArr.vat}"
        ]

        def tableName = "reservation_budget_master"
        Integer newBudgetId = budgetViewDatabaseService.insert(mapReservationMaster, tableName)

        return newBudgetId;
    }


    def createPrivateBudget(def dataArr) {

        def privateCode = new BudgetDetailsTagLib().getGeneratedPrivateCode()
        Map mapPrivateMaster = [
                comments  : "",
                budgetCode: privateCode,
                budgetName: "${dataArr.privateName}",
                budgetType: "${dataArr.privateType}",
                status    : 1
        ]

        def tableName = "privateBudgetMaster"
        def newBudgetId = budgetViewDatabaseService.insert(mapPrivateMaster, tableName)

        return newBudgetId;

    }


    def Map createPrivateBudgetItems(def dataArr) {

        def newBudgetItemReservationId
        def budgetIntervalMonth = privateAndReservationBudgetService.getBudgetInterval(dataArr.BudgetFrequency)
        def FiscalYearRange = new CoreParamsHelper().getFiscalYearRange()
        def start_year = Integer.parseInt(dataArr.bookingPeriodStartYear)
        def start_month = Integer.parseInt(dataArr.bookingPeriodStartMonth)
        def end_year = 0
        def end_month = 12

        def budgetNameIdPr = dataArr.budgetNameId

        if(budgetNameIdPr) {
            end_year = Integer.parseInt(dataArr.bookingPeriodEndYear)
            end_month = Integer.parseInt(dataArr.bookingPeriodEndMonth)
        } else {
            if (Integer.parseInt(dataArr.budgetDuration) == 0) {
                end_year = FiscalYearRange.maxYear
            } else {
                end_year = start_year + Integer.parseInt(dataArr.budgetDuration)
            }
        }

        Map updatededValue = [
                budgetType: "${dataArr.privateType}"
        ]

        def tableName = "privateBudgetMaster"
        def updatedWhereSrting = "id=" + "'" + dataArr.privateBudgetId + "'"
        budgetViewDatabaseService.update(updatededValue, tableName, updatedWhereSrting)

        /*def canCreate = false
        def alreadycreate = false*/

        Map flagArr = [canCreate: false, alreadyCreate: false ]

        for (int x = start_year; x <= end_year; x++) {
            if (x == start_year) {
                if (start_year == end_year) {
                    for (int j = start_month; j <= end_month; j = j + budgetIntervalMonth) {
                        def isAvailable = checkBudgetitemPrivate(dataArr, j, start_year)
                        if(isAvailable){
                            flagArr.alreadyCreate = true
                        }else{
                            flagArr.canCreate =true
                            newBudgetItemReservationId = privateAndReservationBudgetService.saveBudgetItemPrivate(dataArr, j, start_year)
                        }

                    }
                } else {
                    for (int j = start_month; j <= 12; j = j + budgetIntervalMonth) {

                        def isAvailable = checkBudgetitemPrivate(dataArr, j, start_year)
                        if(isAvailable){
                            flagArr.alreadyCreate = true
                        }else{
                            flagArr.canCreate =true
                            newBudgetItemReservationId = privateAndReservationBudgetService.saveBudgetItemPrivate(dataArr, j, start_year)
                        }
                        /*newBudgetItemReservationId = privateAndReservationBudgetService.saveBudgetItemPrivate(dataArr, j, start_year)*/
                    }
                }
            } else if (x == end_year) {
                for (int k = 1; k <= end_month; k = k + budgetIntervalMonth) {
                    def isAvailable = checkBudgetitemPrivate(dataArr, k, end_year)
                    if(isAvailable){
                        flagArr.alreadyCreate = true
                    }else{
                        flagArr.canCreate =true
                        newBudgetItemReservationId = privateAndReservationBudgetService.saveBudgetItemPrivate(dataArr, k, end_year)
                    }
//                    newBudgetItemReservationId = privateAndReservationBudgetService.saveBudgetItemPrivate(dataArr, k, end_year)
                }
            } else {
                for (int m = 1; m <= 12; m = m + budgetIntervalMonth) {

                    def isAvailable = checkBudgetitemPrivate(dataArr, m, x)
                    if(isAvailable){
                        flagArr.alreadyCreate = true
                    }else{
                        flagArr.canCreate =true
                        newBudgetItemReservationId = privateAndReservationBudgetService.saveBudgetItemPrivate(dataArr, m, x)
                    }
//                    newBudgetItemReservationId = privateAndReservationBudgetService.saveBudgetItemPrivate(dataArr, m, x)
                }
            }
        }

        return flagArr;
    }

    def checkBudgetitemPrivate(def dataArr, def bookingMonth, def bookingyear){
        def isAvailable = false
        def isAvailableArr = new BudgetViewDatabaseService().executeQuery("""SELECT * from private_budget_item
                                                                            WHERE booking_period = '${bookingMonth}' AND booking_year = '${bookingyear}'
                                                                            AND budget_name_id ='${dataArr.privateBudgetId}' """)

        if(isAvailableArr.size()>0){
            isAvailable = true
        }
        return isAvailable;
    }


    def saveBudgetItemPrivate(def dataArr, def bookingMonth, def bookingyear) {

        def date = new Date()
        def sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        String createdDate = sdf.format(date)
        String updatedDate = sdf.format(date)
        def budgetCode = getGeneratedPrivateBudgetItemCode()


        def tempAmount = ''
        def budgetTypeId =dataArr.privateType
        tempAmount = dataArr.amount.replaceAll(",", ".")

        if(Integer.parseInt(budgetTypeId) == 1){
            tempAmount = tempAmount
        }else{
//            tempAmount = Integer.parseInt(tempAmount)*(-1).toString()
            tempAmount = Double.parseDouble(tempAmount)*(-1)

        }

        Map mapPrivateMaster = [

                bookingPeriod : bookingMonth,
                bookingYear   : bookingyear,
                budgetCodeId  : budgetCode,
                createdDate   : createdDate,
                paymentTermsId: 1,
                status        : 1,
//                totalAmount   : dataArr.amount,
                totalAmount   : tempAmount,
                updatedDate   : updatedDate,
                budgetNameId  : dataArr.privateBudgetId

        ]

        def tableName = "privateBudgetItem"
        Integer newBudgetId = budgetViewDatabaseService.insert(mapPrivateMaster, tableName)

        return newBudgetId;

    }


    def updateReservationBudget(def dataArr) {

        Map updatededValue = [
                reservationName : "${dataArr.reservationName}",
                reservationType : "${dataArr.reservationType}",
                defaultGlAccount: "${dataArr.defaultGlAccount}",
                vat             : "${dataArr.vat}"
        ]

        def tableName = "reservation_budget_master"
        def updatedWhereSrting = "id=" + "'" + dataArr.editId + "'"
        budgetViewDatabaseService.update(updatededValue, tableName, updatedWhereSrting)

    }


    def updatePrivateBudget(def dataArr) {

        Map updatededValue = [
                budgetName: "${dataArr.privateName}",
                budgetType: "${dataArr.privateType}"
        ]

        def tableName = "privateBudgetMaster"
        def updatedWhereSrting = "id=" + "'" + dataArr.editId + "'"
        budgetViewDatabaseService.update(updatededValue, tableName, updatedWhereSrting)

    }

    def updatePrivateBudgetItem(def dataArr) {

        def date = new Date()
        def sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        String createdDate = sdf.format(date)
        String updatedDate = sdf.format(date)

        Map updatededPrivateMaster = [
                budgetType: "${dataArr.privateType}"
        ]

        def tableNamePrivateMaster = "privateBudgetMaster"
        def updatedWhereSrtingPrivateMaster = "id=" + "'" + dataArr.privateId + "'"
        budgetViewDatabaseService.update(updatededPrivateMaster, tableNamePrivateMaster, updatedWhereSrtingPrivateMaster)

        if (dataArr.budgetItemId) {
            String[] ids = dataArr.budgetItemId instanceof String ? [dataArr.budgetItemId] : dataArr.budgetItemId;

            for (editId in ids) {
                def tempAmount = dataArr.amount
                tempAmount = tempAmount.replaceAll(",", ".")
                def budgetTypeArr = new BudgetViewDatabaseService().executeQuery("""SELECT budget_type
                FROM private_budget_master WHERE id = (SELECT budget_name_id from private_budget_item WHERE id = ${editId}) """)

                if(budgetTypeArr[0][0] == 1){
                    tempAmount = Double.parseDouble(tempAmount.toString())
                }else{
                    tempAmount = Double.parseDouble(tempAmount.toString())*(-1)

                }

                Map updatededValue = [
                        totalAmount: Double.parseDouble(tempAmount.toString()),
                        updatedDate: updatedDate
                ]

                def tableName = "privateBudgetItem"
                def updatedWhereSrting = "id=" + "'" + editId + "'"
                budgetViewDatabaseService.update(updatededValue, tableName, updatedWhereSrting)

            }

        }
    }


    def getReservationMasterInfo(def infoArr) {

        String select = """id as id, version as version, reservation_code as reservationCode, reservation_name as reservationName,
                        reservation_type as reservationType, default_gl_account as defaultGlAccount, `status` as status1,  vat as vat"""
        String selectIndex = """id,version, reservationCode, reservationName, reservationType, defaultGlAccount, status1, vat"""
        String from = "reservation_budget_master AS a "
        String where = ""
        String orderBy = ""
        if (infoArr.sort && infoArr.orderBY) {
            orderBy = "a.${infoArr.sort} ${infoArr.orderBY}"
        } else {
            orderBy = "a.id ASC"
        }
        LinkedHashMap gridResult = budgetViewDatabaseService.select(select, from, where, orderBy, '', 'false', selectIndex, 0, infoArr.offset)
//        LinkedHashMap gridResult = budgetViewDatabaseService.select(select, from, where, orderBy, '', 'false', selectIndex, infoArr.limit, infoArr.offset)
//        LinkedHashMap gridResult = budgetViewDatabaseService.select(select, from, where, orderBy, '', 'false', selectIndex,0, infoArr.offset)

        String whereInstance = ""
        if (infoArr.id) {
            whereInstance = "a.id=${infoArr.id}"
        } else {
            whereInstance = "a.id=0"
        }

        LinkedHashMap gridResultInstance = budgetViewDatabaseService.select(select, from, whereInstance, orderBy, '', 'false', selectIndex)

        Map result = [
                gridResult        : gridResult['dataGridList'],
                gridResultInstance: gridResultInstance['dataGridList'][0]
        ]

        return result
    }


    def getPrivateMasterInfo(def infoArr) {

        String select = """id as id, version as version, budget_code as budgetCode, budget_name as budgetName,
                        budget_type as budgetType,  `status` as status1"""
        String selectIndex = """id,version, budgetCode, budgetName, budgetType, status1"""
        String from = "private_budget_master AS a "
        String where = ""
        String orderBy = ""
        if (infoArr.sort && infoArr.orderBY) {
            orderBy = "a.${infoArr.sort} ${infoArr.orderBY}"
        } else {
            orderBy = "a.id ASC"
        }
//        LinkedHashMap gridResult = budgetViewDatabaseService.select(select, from, where, orderBy, '', 'false', selectIndex, infoArr.limit, infoArr.offset)
        LinkedHashMap gridResult = budgetViewDatabaseService.select(select, from, where, orderBy, '', 'false', selectIndex, 0, infoArr.offset)
//        LinkedHashMap gridResult = budgetViewDatabaseService.select(select, from, where, orderBy, '', 'false', selectIndex,0,0)

        String whereInstance = ""
        if (infoArr.id) {
            whereInstance = "a.id=${infoArr.id}"
        } else {
            whereInstance = "a.id=0"
        }

        LinkedHashMap gridResultInstance = budgetViewDatabaseService.select(select, from, whereInstance, orderBy, '', 'false', selectIndex)

        Map result = [
                gridResult        : gridResult['dataGridList'],
                gridResultInstance: gridResultInstance['dataGridList'][0]
        ]

        return result
    }


    def getReservationBankInfo(def id) {
        String selectba = """a.id as id, a.version as version, a.bank_account_name as bankAccountName,
                           a.bank_account_no as bankAccountNo,  a.iban_prefix as ibanPrefix,  a.budget_master_id as budgetMasterId, a.`status` as status1"""
        String selectIndexba = """a.id as id, version, bankAccountName,bankAccountNo,  ibanPrefix,  budgetMasterId, status1"""
        String fromba = "reservation_budget_bank_account AS a "
        String whereba = "a. budget_master_id='" + id + "'"
        String orderByba = "a.id ASC"

        LinkedHashMap gridResultba = budgetViewDatabaseService.select(selectba, fromba, whereba, orderByba, '', 'false', selectIndexba)

        return gridResultba['dataGridList']
    }


    def getReservationDataGridAsJSON(def params, def liveUrl) {
        int aInt = 0;
        String gridOutput = ''
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=14")
        def reservMasterPrefix = PrefixDataArr[0][0]
        def reservationMasterinfo = getReservationMasterInfo(params)
        def controllerName = "reservationBudgetMaster"

        List quickExpenseList = new ArrayList()
        GridEntity gridEntity
        String budgetEdit = ""

        reservationMasterinfo.gridResult.each { phn ->

            def reservationType = getReservationType(phn.reservation_type)

            gridEntity = new GridEntity();
            aInt = aInt + 1
            gridEntity.id = aInt
            budgetEdit = "<a href='javascript:editBudget(\"${phn.id}\",\"${liveUrl}\",\"${controllerName}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${liveUrl}/images/edit.png\"></a>"
            gridEntity.cell = ["reservationCode": reservMasterPrefix + "-" + phn.reservation_code, "reservationName": phn.reservation_name, "reservationType": reservationType, "action": budgetEdit]
            quickExpenseList.add(gridEntity)
        }

        LinkedHashMap result = [draw: 1, recordsTotal: reservationMasterinfo.gridResult.size(), recordsFiltered: reservationMasterinfo.gridResult.size(), data: quickExpenseList.cell]
        gridOutput = result as JSON

        return gridOutput;
    }


    def getPrivateDataGridAsJSON(def params, def liveUrl) {
        int aInt = 0;
        String gridOutput = ''
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=13")
        def privateMasterPrefix = PrefixDataArr[0][0]
        def privateMasterinfo = getPrivateMasterInfo(params)
        def controllerName = "privateBudgetMaster"

        List quickExpenseList = new ArrayList()
        GridEntity gridEntity
        String budgetEdit = ""

        privateMasterinfo.gridResult.each { phn ->

            def privateType = getPrivateType(phn.budget_type)

            gridEntity = new GridEntity();
            aInt = aInt + 1
            gridEntity.id = aInt
            budgetEdit = "<a href='javascript:editBudget(\"${phn.id}\",\"${liveUrl}\",\"${controllerName}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${liveUrl}/images/edit.png\"></a>"
            gridEntity.cell = ["budgetCode": privateMasterPrefix + "-" + phn.budget_code, "budgetName": phn.budget_name, "budgetType": privateType, "action": budgetEdit]
            quickExpenseList.add(gridEntity)
        }

        LinkedHashMap result = [draw: 1, recordsTotal: privateMasterinfo.gridResult.size(), recordsFiltered: privateMasterinfo.gridResult.size(), data: quickExpenseList.cell]
        gridOutput = result as JSON

        return gridOutput;
    }


    public LinkedHashMap listOfEntryBudgetItem(String bookingPeriod, String fiscalYearId, String reservationId) {

        def companyConfig = new BudgetViewDatabaseService().getConnectionInformation()
        def db = Sql.newInstance(companyConfig.serverUrl + companyConfig.dbName, companyConfig.dbUser, companyConfig.dbPassword, companyConfig.driverName)


        String wherePostCondition = ""

        if (bookingPeriod) {
            wherePostCondition = wherePostCondition + " AND a.booking_period_month=" + bookingPeriod
        }
        if (fiscalYearId) {
            wherePostCondition = wherePostCondition + " AND a.booking_period_year=" + fiscalYearId
        }
        if (reservationId) {
            wherePostCondition = wherePostCondition + " AND a.budget_name_id=" + reservationId
        }


        String reservationEntry = """SELECT a.id,CONCAT(sp.prefix,'-',a.budget_id) AS budgetCode,a.budget_name_id,
CONCAT(rm.reservation_name,
        ' [',spvm.prefix,'-',rm.reservation_code,']') AS budgetItemName,a.booking_period_month,a.booking_period_year,
        CONCAT((CASE a.booking_period_month WHEN '1' THEN 'Jan' WHEN '2' THEN 'Feb' WHEN '3' THEN 'Mar' WHEN '4' THEN 'Apr' WHEN '5'
         THEN 'May' WHEN '6' THEN 'Jun' WHEN '7' THEN 'Jul' WHEN '8' THEN 'Aug' WHEN '9' THEN 'Sep' WHEN '10' THEN 'Oct' WHEN '11' THEN 'Nov'
         WHEN '12' THEN 'Dec' END),'-',a.booking_period_year) AS bookStartPeriod,a.booking_period_month,a.booking_period_year,
         CONCAT((CASE a.booking_period_month WHEN '1' THEN 'Jan' WHEN '2' THEN 'Feb' WHEN '3' THEN 'Mar' WHEN '4' THEN 'Apr' WHEN '5' THEN 'May'
          WHEN '6' THEN 'Jun' WHEN '7' THEN 'Jul' WHEN '8' THEN 'Aug' WHEN '9' THEN 'Sep' WHEN '10' THEN 'Oct' WHEN '11' THEN 'Nov' WHEN '12' THEN 'Dec' END),
          '-',a.booking_period_year) AS bookEndPeriod,CAST(a.total_amount AS CHAR) AS totalGlAmount ,
          CAST(a.total_vat AS CHAR) AS totalVat FROM reservation_budget_item AS a,system_prefix AS sp,reservation_budget_master AS rm,system_prefix AS spvm
          WHERE sp.id=15 AND a.budget_name_id=rm.id AND spvm.id=14 ${wherePostCondition}""";

        List<GroovyRowResult> dashboardReservationBudgetItemList = db.rows(reservationEntry);

        String countQuery = """SELECT  COUNT(a.id) AS totalBudgetItem FROM reservation_budget_item AS a,system_prefix AS sp,
reservation_budget_master AS vm,system_prefix AS spvm WHERE sp.id=15 AND a.budget_name_id=vm.id AND spvm.id=14 ${
            wherePostCondition
        } """;
        List<GroovyRowResult> count_result = db.rows(countQuery)
        int total = count_result[0].totalBudgetItem
        db.close();
        return [dashboardReservationBudgetItemList: dashboardReservationBudgetItemList, count: total]
    }


    public LinkedHashMap listOfEntryBudgetItemPrivate(String bookingPeriod, String fiscalYearId, String privateId) {

        def companyConfig = new BudgetViewDatabaseService().getConnectionInformation()
        def db = Sql.newInstance(companyConfig.serverUrl + companyConfig.dbName, companyConfig.dbUser, companyConfig.dbPassword, companyConfig.driverName)

        String wherePostCondition = ""

        if (bookingPeriod) {
            wherePostCondition = wherePostCondition + " AND a.booking_period=" + bookingPeriod
        }
        if (fiscalYearId) {
            wherePostCondition = wherePostCondition + " AND a.booking_year=" + fiscalYearId
        }
        if (privateId) {
            wherePostCondition = wherePostCondition + " AND a.budget_name_id=" + privateId
        }

        String privateEntry = """SELECT a.id,CONCAT(sp.prefix,'-',a.budget_code_id) AS budgetCode,a.budget_name_id,
                                CONCAT(rm.budget_name,' [',spvm.prefix,'-',rm.budget_code,']') AS budgetItemName,a.booking_period,a.booking_year,
                                CONCAT((CASE a.booking_period WHEN '1' THEN 'Jan' WHEN '2' THEN 'Feb' WHEN '3' THEN 'Mar' WHEN '4' THEN 'Apr' WHEN '5'
                                THEN 'May' WHEN '6' THEN 'Jun' WHEN '7' THEN 'Jul' WHEN '8' THEN 'Aug' WHEN '9' THEN 'Sep' WHEN '10' THEN 'Oct' WHEN '11' THEN 'Nov'
                                WHEN '12' THEN 'Dec' END),'-',a.booking_year) AS bookStartPeriod,a.booking_period,a.booking_year,
                                CONCAT((CASE a.booking_period WHEN '1' THEN 'Jan' WHEN '2' THEN 'Feb' WHEN '3' THEN 'Mar' WHEN '4' THEN 'Apr' WHEN '5' THEN 'May'
                                WHEN '6' THEN 'Jun' WHEN '7' THEN 'Jul' WHEN '8' THEN 'Aug' WHEN '9' THEN 'Sep' WHEN '10' THEN 'Oct' WHEN '11' THEN 'Nov' WHEN '12' THEN 'Dec' END),
                                '-',a.booking_year) AS bookEndPeriod,CAST(a.total_amount AS CHAR) AS totalGlAmount FROM private_budget_item AS a,system_prefix AS sp,private_budget_master AS rm,system_prefix AS spvm
                                WHERE sp.id=16 AND a.budget_name_id=rm.id AND spvm.id=13  ${wherePostCondition}""";

        List<GroovyRowResult> dashboardPrivateBudgetItemList = db.rows(privateEntry);

        String countQuery = """SELECT  COUNT(a.id) AS totalBudgetItem FROM private_budget_item AS a,system_prefix AS sp,
private_budget_master AS vm,system_prefix AS spvm WHERE sp.id=16 AND a.budget_name_id=vm.id AND spvm.id=13 ${
            wherePostCondition
        } """;
        List<GroovyRowResult> count_result = db.rows(countQuery)
        int total = count_result[0].totalBudgetItem
        db.close();
        return [dashboardPrivateBudgetItemList: dashboardPrivateBudgetItemList, count: total]
    }


    public List wrapEntryPrivateBudgetItemInGrid(List<GroovyRowResult> quickEntries, int start, journalId,
                                                 bookingPeriod, isForDelete = false, isForEdit = false) {
        List quickPrivateList = new ArrayList()
        def privateEntry
        GridEntity obj
        String changeBooking
        String tempBookingPeriod = ""
        try {
            int counter = start + 1
            for (int i = 0; i < quickEntries.size(); i++) {
                privateEntry = quickEntries[i];

                tempBookingPeriod = ""
                def bookStartPeriod = ""
                if (privateEntry.booking_period == 12) {
                    bookStartPeriod = "Dec" + "-" + privateEntry.booking_year
                } else {
                    bookStartPeriod = new CoreParamsHelper().monthNameShow(privateEntry.booking_period) + "-" + privateEntry.booking_year
                }
                def bookEndPeriod = ""
                if (privateEntry.booking_period == 12) {
                    bookEndPeriod = "Dec" + "-" + privateEntry.booking_year
                } else {
                    bookEndPeriod = new CoreParamsHelper().monthNameShow(privateEntry.booking_period) + "-" + privateEntry.booking_year
                }

                def showtotalGlAmount = privateEntry.totalGlAmount


                Double showtotalGlAmountc = Double.parseDouble(showtotalGlAmount)


                def showtotalGlAmounta = String.format("%.2f", showtotalGlAmountc)



                obj = new GridEntity();
                obj.id = privateEntry.id

                def canDelete
                def isReconcilateArr = new BudgetViewDatabaseService().executeQuery("""SELECT COUNT(prst.id)
                                                                                    FROM private_budget_item as pbi
                                                                                    INNER JOIN private_reservation_spending_trans  as prst
                                                                                    ON pbi.id=prst.budget_item_id where trans_type= '6' and pbi.id = '${privateEntry.id}';""")


                def budgetTypeIdArr = new BudgetViewDatabaseService().executeQuery("""SELECT budget_type
                                                                                    from private_budget_master where id = '${privateEntry.budget_name_id}'""")

                def budgetTypeId
                if(budgetTypeIdArr.size()){
                    budgetTypeId = budgetTypeIdArr[0][0];
                }


                if(isReconcilateArr[0][0]>0){
                    canDelete = false
                }
                else{
                    canDelete = true
                }

                if (bookingPeriod == "") {
                    tempBookingPeriod = privateEntry.booking_period
                } else {
                    tempBookingPeriod = bookingPeriod;
                }
                if (isForDelete) {
                    String firstCol = "<input type=\"checkbox\" name=\"budgetItemId\" value=\"${privateEntry.id}\">";
                    obj.cell = ["firstCol": firstCol, "budgetItemName": privateEntry.budgetItemName, "budgetCode": privateEntry.budgetCode, "totalGlAmount": showtotalGlAmounta,/*"totalVat": showtotalVata,*/ "bookStartPeriod": bookStartPeriod]
                } else if (isForEdit) {

                    String firstCol = "<input type=\"checkbox\" name=\"budgetItemId\" value=\"${privateEntry.id}\">";
                    if(canDelete){
                        changeBooking = "<a href='javascript:deletePrivateBudgetItems(\"${privateEntry.id}\")'><img width=\"16\" height=\"15\" alt=\"Delete\" src=\"${contextPath}/images/delete.png\"></a>"
                    }
                    else{
                        changeBooking = ""
                    }
//                    changeBooking = "<a href='javascript:showPrivateDeleteDialog(\"${privateEntry.id}\",\"${privateEntry.budget_name_id}\")'><img width=\"16\" height=\"15\" alt=\"Delete\" src=\"/images/delete.png\"></a>"
                    obj.cell = ["firstCol": firstCol, "budgetItemName": privateEntry.budgetItemName, "budgetCode": privateEntry.budgetCode, "totalGlAmount": showtotalGlAmounta/*,"totalVat": showtotalVata*/, "bookStartPeriod": bookStartPeriod, "action": changeBooking]

                } else {

                    if(canDelete){
                        changeBooking = "<a href='javascript:changePrivateBudget(\"${privateEntry.id}\",\"${privateEntry.budget_name_id}\",\"${journalId}\",\"${tempBookingPeriod}\",\"${budgetTypeId}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:deletePrivateBudgetItems(\"${privateEntry.id}\")'><img width=\"16\" height=\"15\" alt=\"Delete\" src=\"${contextPath}/images/delete.png\"></a>"
                    }else{
                        changeBooking = "<a href='javascript:changePrivateBudget(\"${privateEntry.id}\",\"${privateEntry.budget_name_id}\",\"${journalId}\",\"${tempBookingPeriod}\",\"${budgetTypeId}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>"
                    }

//                    changeBooking = "<a href='javascript:changePrivateBudget(\"${privateEntry.id}\",\"${privateEntry.budget_name_id}\",\"${journalId}\",\"${tempBookingPeriod}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"/images/edit.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:showPrivateDeleteDialog(\"${privateEntry.id}\",\"${privateEntry.budget_name_id}\")'><img width=\"16\" height=\"15\" alt=\"Delete\" src=\"/images/delete.png\"></a>"
                    obj.cell = ["budgetItemName": privateEntry.budgetItemName, "budgetCode": privateEntry.budgetCode, "totalGlAmount": showtotalGlAmounta/*,"totalVat": showtotalVata*/, "bookStartPeriod": bookStartPeriod, "action": changeBooking]
                }

                quickPrivateList.add(obj)
                counter++;
            }

            return quickPrivateList;
        } catch (Exception ex) {
            quickPrivateList = [];
            return quickPrivateList;
        }
    }


    def getReservationType(def value) {


        def dataArr = new BudgetDetailsTagLib().getReservationTypeArr()

        def type = ""
        if (value == "1") {
            type = dataArr[0][1]
        } else if (value == "2") {
            type = dataArr[1][1]
        } else if (value == "3") {
            type = dataArr[2][1]
        } else if (value == "4") {
            type = dataArr[3][1]
        } else if (value == "5") {
            type = dataArr[4][1]
        } else if (value == "6") {
            type = dataArr[5][1]
        }

        return type


    }


    def getPrivateType(def value) {

        def type = ""
        if (value == 1) {
            type = "Income"
        } else if (value == 2) {
            type = "Fixed Expense"
        } else if (value == 3) {
            type = "Variable Expense"
        }

        return type


    }

    /**
     * reservation budget item area
     * */


    def getReservationDropdownElement(def paramReservationId) {

        def reservationArr = []
        def reservationIdArr = []
        def reservationList
        def selectedIndex

        String selectedReservation = ""
        String reservationID = paramReservationId ? paramReservationId : '0'

        def reservationIndex = 0;

        reservationList = new BudgetViewDatabaseService().executeQuery("SELECT id, reservation_name from reservation_budget_master where status=1")
        for (int i = 0; i < reservationList.size(); i++) {

            reservationIdArr[i] = reservationList[i][1]
            reservationArr += "{text:'" + reservationList[i][1] + "'" + ",value:" + reservationList[i][0] + "}"
            def reservationNo = reservationList[i][1]

            if (reservationNo == Integer.parseInt(reservationID)) {
                reservationIndex = i;

            }
        }
        selectedReservation = ""

        Map dataArr = [
                reservationArr     : reservationArr,
                reservationIdArr   : reservationIdArr,
                selectedIndex      : selectedIndex,
                selectedReservation: selectedReservation,
                reservationIndex   : reservationIndex
        ]

        return dataArr
    }

    def getSelectReservationRelatedInformation(def resvId) {
        def dataArr = new BudgetViewDatabaseService().executeQuery("SELECT id,default_gl_account,vat FROM reservation_budget_master where id = " + resvId)
        Map result = [gl_account: dataArr[0][1],
                      vat       : dataArr[0][2]]
        return result as JSON


    }


    def saveBudgetItemReservationRoot(def dataArr, def budgetItemsReservation) {

        def FiscalYearRange = new CoreParamsHelper().getFiscalYearRange()
        def startYear = Integer.parseInt(dataArr.bookingPeriodStartYear)
        def startMonth = Integer.parseInt(dataArr.bookingPeriodStartMonth)
        def endYear = 0
        def endMonth = 12
        def reservationId =dataArr.reservationId
        if(reservationId){
            endYear = startYear
            endMonth = startMonth
        }
        else{
            if (Integer.parseInt(dataArr.budgetDuration) == 0) {
                endYear = FiscalYearRange.maxYear
            } else {
                endYear = startYear + Integer.parseInt(dataArr.budgetDuration)
            }
        }



        double totalHeadGLAmount = 0.00
        double totalHeadVatAmount = 0.00
        for (int i = 0; i < budgetItemsReservation.size(); i++) {
            totalHeadGLAmount = totalHeadGLAmount + Double.parseDouble(budgetItemsReservation[i].totalPriceWithoutTax)
            totalHeadVatAmount = totalHeadVatAmount + (Double.parseDouble(budgetItemsReservation[i].totalPriceWithTax) - Double.parseDouble(budgetItemsReservation[i].totalPriceWithoutTax))
        }

        def date = new Date()
        def sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        String createdDate = sdf.format(date)
        String updatedDate = sdf.format(date)

        Map BudgetItemReservationRoot = [
                bookingPeriodMonthStart: startMonth,
                bookingPeriodYearStart : startYear,
                bookingPeriodMonthEnd  : endMonth,
                bookingPeriodYearEnd   : endYear,
                createdDate            : createdDate,
                invoiceFrequency       : dataArr.invoiceFrequency,
                status                 : 1,
                totalAmount            : totalHeadGLAmount.round(2),
                totalVat               : totalHeadVatAmount.round(2),
                updatedDate            : updatedDate,
                budgetItemId           : Integer.parseInt(dataArr.reservationBudgetId.toString())
        ]

        def tableNameBudgetItemReservationRoot = "reservationBudgetItemRoot"
        def RootInsertedIdBudgetItemReservationRoot = new BudgetViewDatabaseService().insert(BudgetItemReservationRoot,
                tableNameBudgetItemReservationRoot)

        return RootInsertedIdBudgetItemReservationRoot
    }


    def saveBudgetItemPrivateRoot(def dataArr) {

        def FiscalYearRange = new CoreParamsHelper().getFiscalYearRange()
        def startYear = Integer.parseInt(dataArr.bookingPeriodStartYear)
        def startMonth = Integer.parseInt(dataArr.bookingPeriodStartMonth)
        def endYear = 0
        def endMonth = 12

        def budgetNameIdPr = dataArr.budgetNameId

        if(budgetNameIdPr) {
            endYear = Integer.parseInt(dataArr.bookingPeriodEndYear)
            endMonth = Integer.parseInt(dataArr.bookingPeriodEndMonth)

        } else {
            if (Integer.parseInt(dataArr.budgetDuration) == 0) {
                endYear = FiscalYearRange.maxYear
            } else {
                endYear = startYear + Integer.parseInt(dataArr.budgetDuration)
            }
        }

        def tempAmount = dataArr.amount
        tempAmount = tempAmount.replaceAll(",", ".")

        def date = new Date()
        def sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        String createdDate = sdf.format(date)
        String updatedDate = sdf.format(date)

        Map BudgetItemReservationRoot = [

                bookingPeriodStart: startMonth,
                bookingYearStart  : startYear,
                bookingPeriodEnd  : endMonth,
                bookingYearEnd    : endYear,
                createdDate       : createdDate,
                invoiceFrequency  : dataArr.BudgetFrequency,
                totalAmount       : tempAmount,
                updatedDate       : createdDate,
                budgetItemId      : dataArr.privateBudgetId
        ]

        def tableNameBudgetItemReservationRoot = "privateBudgetItemRoot"
        def RootInsertedIdBudgetItemReservationRoot = new BudgetViewDatabaseService().insert(BudgetItemReservationRoot,
                tableNameBudgetItemReservationRoot)

        return RootInsertedIdBudgetItemReservationRoot
    }


    def saveBudgetItemReservationDetailsRoot(def budgetItemsReservation, def newBudgetItemReservationRootId) {

        for (int i = 0; i < budgetItemsReservation.size(); i++) {
            Double tempAmountTemp = Double.parseDouble(budgetItemsReservation[i].totalPriceWithTax) - Double.parseDouble(budgetItemsReservation[i].totalPriceWithoutTax)
            Map BudgetItemReservationDetailsRoot = [

                    reservBudgetRootId  : newBudgetItemReservationRootId,
                    note                : "",
                    glAccount           : budgetItemsReservation[i].glAccount,
                    totalPriceWithVat   : Double.parseDouble(budgetItemsReservation[i].totalPriceWithTax.toString()).round(2),
                    totalPriceWithoutVat: Double.parseDouble(budgetItemsReservation[i].totalPriceWithoutTax.toString()).round(2),
                    unitPrice           : Double.parseDouble(budgetItemsReservation[i].unitPrice.toString()).round(2),
                    vatAmount           : Double.parseDouble(tempAmountTemp.toString()).round(2),
                    vatCategoryId       : Integer.parseInt(budgetItemsReservation[i].vatCategoryId),
                    vatRate             : budgetItemsReservation[i].vatRate

            ]

            def tableNameBudgetItemReservationDetailsRoot = "reservationBudgetItemDetailsRoot"
            def newBudgetItemReservationDetailsRootId = new BudgetViewDatabaseService().insert(BudgetItemReservationDetailsRoot, tableNameBudgetItemReservationDetailsRoot)
        }

    }


    def saveBudgetItemResrvation(def dataArr, def bookingMonth, def bookingYear, def budgetItemsReservation) {

        def budgetId = getGeneratedReservationBudgetItemCode()

        double totalHeadGLAmount = 0.00
        double totalHeadVatAmount = 0.00
        for (int i = 0; i < budgetItemsReservation.size(); i++) {
            totalHeadGLAmount = totalHeadGLAmount + Double.parseDouble(budgetItemsReservation[i].totalPriceWithoutTax)
            totalHeadVatAmount = totalHeadVatAmount + (Double.parseDouble(budgetItemsReservation[i].totalPriceWithTax) - Double.parseDouble(budgetItemsReservation[i].totalPriceWithoutTax))
        }

        def date = new Date()
        def sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        String createdDate = sdf.format(date)
        String updatedDate = sdf.format(date)

        Map BudgetItemReservation = [

                bookingPeriodMonth: bookingMonth,
                bookingPeriodYear : bookingYear,
                budgetId          : budgetId,
                createdDate       : createdDate,
                paymentTermsId    : 1,
                status            : 1,
                totalAmount       : Double.parseDouble(totalHeadGLAmount.toString()).round(2),
                totalVat          : Double.parseDouble(totalHeadVatAmount.toString()).round(2),
                updatedDate       : updatedDate,
                budgetNameId      : dataArr.reservationBudgetId

        ]

        def tableNameBudgetItemReservation = "reservationBudgetItem"
        def newInsertedBudgetItemId = new BudgetViewDatabaseService().insert(BudgetItemReservation,
                tableNameBudgetItemReservation)

    }


    def saveBudgetItemReservationDetails(def newBudgetitemId, def budgetItemsReservation) {

        for (int y = 0; y < budgetItemsReservation.size(); y++) {
            Double tempAmountyyyy = Double.parseDouble(budgetItemsReservation[y].totalPriceWithTax) - Double.parseDouble(budgetItemsReservation[y].totalPriceWithoutTax)
            Map BudgetItemReservationDetails = [

                    reservBudgetItemId  : newBudgetitemId,
                    note                : "",
                    glAccount           : budgetItemsReservation[y].glAccount,
                    totalPriceWithVat   : Double.parseDouble(budgetItemsReservation[y].totalPriceWithTax.toString()).round(2),
                    totalPriceWithoutVat: Double.parseDouble(budgetItemsReservation[y].totalPriceWithoutTax.toString()).round(2),
                    unitPrice           : Double.parseDouble(budgetItemsReservation[y].price.toString()).round(2),
                    vatAmount           : Double.parseDouble(tempAmountyyyy.toString()).round(2),
                    vatCategoryId       : budgetItemsReservation[y].vatCategoryId,
                    vatRate             : budgetItemsReservation[y].vatRate
            ]

            def tableNameBudgetItemReservationDetails = "reservationBudgetItemDetails"
            new BudgetViewDatabaseService().insert(BudgetItemReservationDetails, tableNameBudgetItemReservationDetails)
        }

    }

    def getGeneratedReservationBudgetItemCode() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix," +
                "prefixLen FROM bv.SystemPrefix where id=15")
        def Prefix = PrefixDataArr[0][0]
        def PrefixLength = PrefixDataArr[0][1]
        def reservationItemCode = new CoreParamsHelper().getCodeSequence(Prefix, PrefixLength)

        return reservationItemCode
    }


    def getGeneratedPrivateBudgetItemCode() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix," +
                "prefixLen FROM bv.SystemPrefix where id=16")
        def Prefix = PrefixDataArr[0][0]
        def PrefixLength = PrefixDataArr[0][1]
        def privateItemCode = new CoreParamsHelper().getCodeSequence(Prefix, PrefixLength)

        return privateItemCode
    }


    def saveResBankAccountInfo(def dataArr) {
        Map details = [
                bankAccountName :dataArr.bankAccountName,
                bankAccountNo :dataArr.bankAccountNo,
                ibanPrefix:dataArr.iban,
                budgetMasterId:dataArr.bugetMasterId,
                status : dataArr.bankAccountStatus,
//                status : 1,
                type:1
        ]
        def tableName = "reservationBudgetBankAccount"
        new BudgetViewDatabaseService().insert(details, tableName)
    }


    def saveResBankSearchfo(def dataArr) {
        Map details = [
                bankAccountName :"",
                bankAccountNo :dataArr.searchString,
                ibanPrefix:"",
                budgetMasterId:dataArr.bugetMasterId,
                status : dataArr.status,
                type:2
        ]
        def tableName = "reservationBudgetBankAccount"
        new BudgetViewDatabaseService().insert(details, tableName)
    }


    def savePriBankSearchfo(def dataArr) {
        Map details = [
                bankAccountName :"",
                bankAccountNo :dataArr.searchString,
                ibanPrefix:"",
                budgetMasterId:dataArr.bugetMasterId,
                status : dataArr.status,
                type:2
        ]
        def tableName = "privateBudgetBankAccount"
        new BudgetViewDatabaseService().insert(details, tableName)
    }

    def savePriBankAccountInfo(def dataArr) {
        Map details = [
                bankAccountName:dataArr.bankAccountName,
                bankAccountNo:dataArr.bankAccountNo,
                ibanPrefix:dataArr.iban,
                budgetMasterId:dataArr.bugetMasterId,
                status: dataArr.bankAccountStatus,
//                status: 1,
                type:1
        ]
        def tableName = "privateBudgetBankAccount"
        new BudgetViewDatabaseService().insert(details, tableName)
    }

    def updateResBankAccountInfo(def dataArr) {

        Map updatededValue = [
                bankAccountName :dataArr.bankAccountName,
                bankAccountNo :dataArr.bankAccountNo,
                ibanPrefix:dataArr.iban,
                status : dataArr.bankAccountStatus
        ]

        def tableName = "reservationBudgetBankAccount"
        def updatedWhereSrting = "id=" + "'" + dataArr.bankId + "'"
        budgetViewDatabaseService.update(updatededValue, tableName, updatedWhereSrting)

    }

    def updatePriBankAccountInfo(def dataArr) {

        Map updatededValue = [
                bankAccountName :dataArr.bankAccountName,
                bankAccountNo :dataArr.bankAccountNo,
                ibanPrefix:dataArr.iban,
                status: dataArr.bankAccountStatus
        ]

        def tableName = "privateBudgetBankAccount"
        def updatedWhereSrting = "id=" + "'" + dataArr.bankId + "'"
        budgetViewDatabaseService.update(updatededValue, tableName, updatedWhereSrting)

    }

    def updateResBankSearchInfo(def dataArr) {

        Map updatededValue = [
                bankAccountNo :dataArr.searchString,
                status : dataArr.status
        ]

        def tableName = "reservationBudgetBankAccount"
        def updatedWhereSrting = "id=" + "'" + dataArr.bankId + "'"
        budgetViewDatabaseService.update(updatededValue, tableName, updatedWhereSrting)

    }

    def updatePriBankSearchInfo(def dataArr) {

        Map updatededValue = [

            bankAccountNo :dataArr.searchString,
            status : dataArr.status

        ]

        def tableName = "privateBudgetBankAccount"
        def updatedWhereSrting = "id=" + "'" + dataArr.bankId + "'"
        budgetViewDatabaseService.update(updatededValue, tableName, updatedWhereSrting)

    }

    def getResBankAccountDetails(def budgetMaster){

        def sqlQuery = "SELECT  bankAccountName, bankAccountNo, ibanPrefix, IF(status = '1', UPPER('Active'), UPPER('Inactive')), IF(type = 1, 'Active', 'Inactive'),id,budgetMasterId FROM ReservationBudgetBankAccount  WHERE type = '1'  AND  budgetMasterId =" + budgetMaster;
        def bankAccDetailsArr = new BudgetViewDatabaseService().executeQuery(sqlQuery)
        return bankAccDetailsArr

    }

    def getPriBankAccountDetails(def budgetMaster){

        def sqlQuery = "SELECT  bankAccountName, bankAccountNo, ibanPrefix, IF(status = '1', UPPER('Active'), UPPER('Inactive')), IF(type = 1, 'Active', 'Inactive'),id,budgetMasterId FROM PrivateBudgetBankAccount WHERE type = '1'  AND  budgetMasterId =" + budgetMaster;
        def bankAccDetailsArr = new BudgetViewDatabaseService().executeQuery(sqlQuery)
        return bankAccDetailsArr

    }

    def getResBankSearchDetails(def budgetMaster){
        def sqlQuery = "SELECT  bankAccountName, bankAccountNo, ibanPrefix, IF(status = '1', UPPER('Active'), UPPER('Inactive')), IF(type = 1, 'Active', 'Inactive'),id,budgetMasterId FROM ReservationBudgetBankAccount WHERE type = '2'  AND  budgetMasterId =" + budgetMaster;
        def bankAccDetailsArr = new BudgetViewDatabaseService().executeQuery(sqlQuery)
        return bankAccDetailsArr
    }

    def getPriBankSearchDetails(def budgetMaster){
        def sqlQuery = "SELECT  bankAccountName, bankAccountNo, ibanPrefix, IF(status = '1',  UPPER('Active'), UPPER('Inactive')), IF(type = 1, 'Active', 'Inactive'),id,budgetMasterId FROM PrivateBudgetBankAccount WHERE type = '2'  AND  budgetMasterId =" + budgetMaster;
        def bankAccDetailsArr = new BudgetViewDatabaseService().executeQuery(sqlQuery)
        return bankAccDetailsArr
    }

    /*
    * return budget intervation
    * */

    def getBudgetInterval(def strInvoiceFrequency) {

        int budgetIntervalMonth

        if (strInvoiceFrequency.equals("Once")) {
            budgetIntervalMonth = 0
        } else if (strInvoiceFrequency.equals("monthly")) {
            budgetIntervalMonth = 1
        } else if (strInvoiceFrequency.equals("two_monthly")) {
            budgetIntervalMonth = 2
        } else if (strInvoiceFrequency.equals("quarterly")) {
            budgetIntervalMonth = 3
        } else if (strInvoiceFrequency.equals("twice_a_year")) {
            budgetIntervalMonth = 6
        } else if (strInvoiceFrequency.equals("yearly")) {
            budgetIntervalMonth = 12
        }

        return budgetIntervalMonth

    }


    public LinkedHashMap reportListOfReservationEntryBudgetItem(String bookingPeriod, String fiscalYearId, String reservationId) {

        def companyConfig = new BudgetViewDatabaseService().getConnectionInformation()
        def db = Sql.newInstance(companyConfig.serverUrl + companyConfig.dbName, companyConfig.dbUser, companyConfig.dbPassword, companyConfig.driverName)

        String wherePostCondition = ""

        if (bookingPeriod) {
            wherePostCondition = wherePostCondition + " AND a.booking_period_month=" + bookingPeriod
        }
        if (fiscalYearId) {
            wherePostCondition = wherePostCondition + " AND a.booking_period_year=" + fiscalYearId
        }
        if (reservationId) {
            wherePostCondition = wherePostCondition + " AND a.budget_name_id=" + reservationId
        }

        String incomeEntry = """SELECT a.id,CONCAT(sp.prefix,'-',a.budget_id) AS budgetCode,a.budget_name_id,
        CONCAT(cm.reservation_name,' [',spcm.prefix,'-',cm.reservation_code,']') AS budgetItemName,a.booking_period_month,a.booking_period_year,
        CONCAT((CASE a.booking_period_month WHEN '1' THEN 'Jan' WHEN '2' THEN 'Feb' WHEN '3' THEN 'Mar' WHEN '4' THEN 'Apr' WHEN '5' THEN 'May' WHEN '6' THEN 'Jun'
        WHEN '7' THEN 'Jul' WHEN '8' THEN 'Aug' WHEN '9' THEN 'Sep' WHEN '10' THEN 'Oct' WHEN '11' THEN 'Nov' WHEN '12' THEN 'Dec' END),'-',a.booking_period_year) AS bookStartPeriod,
        a.booking_period_month,a.booking_period_year,
        CONCAT((CASE a.booking_period_month WHEN '1' THEN 'Jan' WHEN '2' THEN 'Feb' WHEN '3' THEN 'Mar' WHEN '4' THEN 'Apr' WHEN '5' THEN 'May' WHEN '6' THEN 'Jun'
        WHEN '7' THEN 'Jul' WHEN '8' THEN 'Aug' WHEN '9' THEN 'Sep' WHEN '10' THEN 'Oct' WHEN '11' THEN 'Nov' WHEN '12' THEN 'Dec' END),'-',a.booking_period_year) AS bookEndPeriod,
        a.total_amount AS totalGlAmount ,a.total_vat AS totalVat FROM reservation_budget_item  AS a,system_prefix AS sp,reservation_budget_master AS cm,system_prefix AS spcm
        WHERE sp.id=15 AND a.budget_name_id =cm.id AND spcm.id=14  ${wherePostCondition} """;

        List<GroovyRowResult> dashboardReservationBudgetItemList = db.rows(incomeEntry);

        /*String countQuery = """SELECT  COUNT(a.id) AS totalBudgetItem FROM budget_item_expense AS a,system_prefix AS sp,vendor_master AS cm,system_prefix AS spcm WHERE sp.id=6 AND a.vendor_id=cm.id AND spcm.id=2 ${wherePostCondition} """;*/

        String countQuery = """SELECT  COUNT(a.id) AS totalBudgetItem FROM reservation_budget_item AS a,system_prefix AS sp,reservation_budget_master AS cm,system_prefix AS spcm WHERE sp.id=15 AND a.budget_name_id=cm.id AND spcm.id=14  ${wherePostCondition} """;
        List<GroovyRowResult> count_result = db.rows(countQuery)
        int total = count_result[0].totalBudgetItem
        db.close();
        return [dashboardReservationBudgetItemList: dashboardReservationBudgetItemList, count: total]
    }


    public LinkedHashMap listOfBudgetItem(int offset, String limit, String sortItem, String sortOrder, String bookingPeriod, String fiscalYearId, String customerId) {

        //def db = new Sql(dataSource)
        def companyConfig = new BudgetViewDatabaseService().getConnectionInformation()
        def db = Sql.newInstance(companyConfig.serverUrl + companyConfig.dbName, companyConfig.dbUser, companyConfig.dbPassword, companyConfig.driverName)

         String reservationEntry = """SELECT  a.id AS invoiceIncomeId,  CONCAT(b.gl_account,'-',c.account_name) AS glAccountName,  CONCAT(sp.prefix,'-',a.budget_id) AS budgetItemID,
            DATE_FORMAT(a.created_date,'%d-%m-%Y') AS createdDate, b.total_price_without_vat AS totalPriceWithoutVat, b.total_price_with_vat
            AS totalPriceWithVat,b.gl_account AS tempGLAccount, b.id AS detailsID
            FROM reservation_budget_item AS a,reservation_budget_item_details AS b,chart_master AS c,
            system_prefix AS sp WHERE b.reserv_budget_item_id=a.id AND b.gl_account=c.account_code AND a.budget_name_id= ${customerId}
            AND a.booking_period_month = ${bookingPeriod} AND a.booking_period_year =${fiscalYearId} AND sp.id=14
            ORDER BY ${sortItem} ${sortOrder} LIMIT ${limit} OFFSET ${offset} """;

        List<GroovyRowResult> dashboardReservationBudgetItemList = db.rows(reservationEntry);

        ArrayList dashboardReservationBudgetItemListFinal = new ArrayList()
        dashboardReservationBudgetItemList.eachWithIndex { indexValue, keyValue ->

        String reservationTotalEntry = """SELECT COUNT(DISTINCT(id)) AS count_total FROM private_reservation_spending_trans
        WHERE budget_item_id = ${indexValue.invoiceIncomeId} AND trans_type = 5""";

            List<GroovyRowResult> dashboardReservationBudgetTotalItemList = db.rows(reservationTotalEntry);
            indexValue.total = dashboardReservationBudgetTotalItemList[0].count_total

            BigDecimal showtotalPriceWithoutVat = new BigDecimal(indexValue.totalPriceWithoutVat)
            indexValue.totalPriceWithoutVat = String.format("%.2f", showtotalPriceWithoutVat)

            BigDecimal showtotalPriceWithVat = new BigDecimal(indexValue.totalPriceWithVat)
            indexValue.totalPriceWithVat = String.format("%.2f", showtotalPriceWithVat)

            dashboardReservationBudgetItemListFinal << indexValue
        }

        String countQuery = """SELECT  COUNT(a.id) AS totalBudgetItem
        FROM reservation_budget_item AS a,reservation_budget_item_details AS b,
        chart_master AS c, system_prefix AS sp WHERE b.reserv_budget_item_id=a.id AND b.gl_account=c.account_code
        AND a.budget_name_id= ${customerId} AND a.booking_period_month <= ${bookingPeriod}
        AND a.booking_period_year<=${fiscalYearId} AND sp.id=14""";

        List<GroovyRowResult> count_result = db.rows(countQuery)
        int total = count_result[0].totalBudgetItem
        db.close();
        return [dashboardIncomeBudgetItemList: dashboardReservationBudgetItemListFinal, count: total]
    }



}
