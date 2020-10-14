package bv

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import vb.ApplicationUtil

import java.text.DateFormat
import java.text.SimpleDateFormat

@Transactional
class IncomeInvoiceService {

    SpringSecurityService springSecurityService

    public LinkedHashMap listOfIncomeInvoice(String bookingPeriod, String fiscalYearId, String customerId, String bookInvoiceId) {

        def companyConfig = new BudgetViewDatabaseService().getConnectionInformation()
        def db = Sql.newInstance(companyConfig.serverUrl + companyConfig.dbName, companyConfig.dbUser, companyConfig.dbPassword, companyConfig.driverName)

        String incomeInvoice = """SELECT a.id as id, CONCAT(sp2.prefix,'-',a.invoice_no) AS invoiceNumber,a.booking_period AS bookingPeriod,
                                    CONCAT(v.customer_name,' [',sp.prefix,'-',v.customer_code,']') AS customerName,a.customer_id AS customerId,
                                    DATE_FORMAT(a.trans_date,'%d-%m-%Y') AS invoiceDate, a.payment_ref AS paymentRef,
                                    a.total_gl_amount AS totalAmountIncVat, a.total_vat AS totalVat
                                    FROM invoice_income AS a,budget_item_income AS b, customer_master AS v,
                                    system_prefix AS sp, system_prefix AS sp1, system_prefix AS sp2
                                    WHERE a.budget_item_income_id=b.id AND a.customer_id= v.id
                                    AND a.booking_year=${fiscalYearId}  AND a.budget_customer_id= ${customerId}
                                    AND sp.id=1 AND sp1.id=11 AND sp2.id=8 """;

        List<GroovyRowResult> incomeInvoiceList = db.rows(incomeInvoice);

        int total = incomeInvoiceList.size();
        db.close();
        return [incomeInvoiceList: incomeInvoiceList, count: total]
    }

    def Map getInvoiceIncomeDataAsMap(def invIncId) {

        def invoiceIncomeInstanceTemp
        Map invoiceIncomeInstance = ["id"         : 0, "VERSION": 0, "bookingPeriod": '',
                                     "bookingYear": '', "budgetCustomerId": 0, "budgetItemIncomeId": 0,
                                     "comments"   : '', "currencyCode": '', "customerAccountNo": '',
                                     "customerId" : 0, "dueDate": '', "invoiceNo": '',
                                     "isReverse"  : 0, "paidAmount": 0, "paidStatus": 0,
                                     "paymentRef" : '', "reverseInvoiceId": '', "STATUS": 0,
                                     "termsId"    : 0, "totalGlAmount": 0, "totalVat": 0,
                                     "transDate"  : '', "showInvoiceNumber": '']


        def editId = 0;
        if (invIncId instanceof String) {
            editId = Integer.parseInt(invIncId);
        } else {
            editId = invIncId
        }

        if (editId > 0) {
            String strQuery = "SELECT a.*,CONCAT(sp.prefix,'-',a.invoice_no) AS showInvoiceNumber " +
                    " FROM invoice_income AS a,system_prefix AS sp WHERE sp.id=8 AND a.id=" + editId

            invoiceIncomeInstanceTemp = new BudgetViewDatabaseService().executeQueryAtSingle(strQuery)

            invoiceIncomeInstance.id = invoiceIncomeInstanceTemp[0]
            invoiceIncomeInstance.VERSION = invoiceIncomeInstanceTemp[1]
            invoiceIncomeInstance.bookingPeriod = invoiceIncomeInstanceTemp[2]
            invoiceIncomeInstance.bookingYear = invoiceIncomeInstanceTemp[3]
            invoiceIncomeInstance.budgetCustomerId = invoiceIncomeInstanceTemp[4]
            invoiceIncomeInstance.budgetItemIncomeId = invoiceIncomeInstanceTemp[5]
            invoiceIncomeInstance.comments = invoiceIncomeInstanceTemp[6]
            invoiceIncomeInstance.currencyCode = invoiceIncomeInstanceTemp[7]
            invoiceIncomeInstance.customerAccountNo = invoiceIncomeInstanceTemp[8]
            invoiceIncomeInstance.customerId = invoiceIncomeInstanceTemp[9]
            invoiceIncomeInstance.dueDate = invoiceIncomeInstanceTemp[10]
            invoiceIncomeInstance.invoiceNo = invoiceIncomeInstanceTemp[11]
            invoiceIncomeInstance.isReverse = invoiceIncomeInstanceTemp[12]
            invoiceIncomeInstance.paidAmount = invoiceIncomeInstanceTemp[13]
            invoiceIncomeInstance.paidStatus = invoiceIncomeInstanceTemp[14]
            invoiceIncomeInstance.paymentRef = invoiceIncomeInstanceTemp[15]
            invoiceIncomeInstance.reverseInvoiceId = invoiceIncomeInstanceTemp[16]
            invoiceIncomeInstance.STATUS = invoiceIncomeInstanceTemp[17]
            invoiceIncomeInstance.termsId = invoiceIncomeInstanceTemp[18]
            invoiceIncomeInstance.totalGlAmount = invoiceIncomeInstanceTemp[19]
            invoiceIncomeInstance.totalVat = invoiceIncomeInstanceTemp[20]
            invoiceIncomeInstance.transDate = invoiceIncomeInstanceTemp[21]
            invoiceIncomeInstance.showInvoiceNumber = invoiceIncomeInstanceTemp[22]
        }

        return invoiceIncomeInstance;
    }

    def List<Map> getInvoiceIncomeDetailsDataAsMapList(def invIncId) {

        List invoiceDetailsArr = new ArrayList();

        String strQuery = "SELECT accountCode,vatCategoryId,unitPrice,quantity,totalAmountWithVat," +
                "totalAmountWithoutVat,vatRate,productCode,discountAmount,note,id " +
                " FROM bv.InvoiceIncomeDetails WHERE invoiceId = " + invIncId

        ArrayList queryResults = new BudgetViewDatabaseService().executeQuery(strQuery)

        queryResults.eachWithIndex { Phn, key ->
            Map map = ["JournalChartId"       : 0, "vatCategoryId": '', "unitPrice": 0,
                       "quantity"             : 0, "totalAmountWithVat": 0, "totalPriceWithoutTax": 0,
                       "totalAmountWithoutVat": 0, "vatAmount": 0, "productId": 0,
                       "discount"             : 0, "note": '', "vatRate": 0]

            map.JournalChartId = Phn[0]
            map.vatCategoryId = Phn[1].toString()
            map.unitPrice = Phn[2].toString()
            map.quantity = Phn[3]
            map.totalAmountWithVat = Phn[4].toString()
            map.totalPriceWithoutTax = Phn[5]
            map.totalAmountWithoutVat = Phn[5].toString()
            map.vatAmount = Phn[4] - Phn[5]
            map.productId = Phn[7]
            map.discount = Phn[8]
            map.vatRate = Phn[6]
            map.note = Phn[9]
            map.ii_id = Phn[10]

            invoiceDetailsArr.add(map);
        }

        return invoiceDetailsArr;
    }

    def incomeInvoiceDataArray(def invIncId) {
        def incomeHeadData
        def editId = 0;
        if (invIncId instanceof String) {
            editId = Integer.parseInt(invIncId);
        } else {
            editId = invIncId
        }

        if (editId > 0) {
            String strQuery = "Select CONCAT(sp.prefix,'-',ie.invoiceNo), ie.paidAmount,vm.customerName," +
                    "vm.email,CONCAT(sp.prefix,'-',vm.customerCode),ie.totalVat,ie.comments,ie.dueDate,ie.paymentRef, ie.transDate, ie.bookingPeriod, " +
                    "ie.bookingYear,vmb.customerName As budgetCustomerName,vmb.id AS budgetCId,ie.pdf_comment as pdfComment,vm.vat_number,ie.totalGlAmount, ie.id " +
                    "FROM bv.InvoiceIncome AS ie ,bv.SystemPrefix As sp," +
                    "bv.CustomerMaster as vm,bv.CustomerMaster as vmb WHERE ie.id=" + editId + " AND sp.id=8 AND vm.id = ie.customerId AND " +
                    "vmb.id = ie.budgetCustomerId"

            incomeHeadData = new BudgetViewDatabaseService().executeQuery(strQuery)
        }


        return incomeHeadData;
    }

    def incomeInvoiceDetailsDataArray(def invIncId) {
        def incomeDetailsData
        def editId = 0;
        if (invIncId instanceof String) {
            editId = Integer.parseInt(invIncId);
        } else {
            editId = invIncId
        }

        if (editId > 0) {
            String strQuery = "Select ied.accountCode,ied.discountAmount,ied.note,ied.productCode," +
                    "ied.quantity,ied.totalAmountWithoutVat,ied.totalAmountWithVat,ied.unitPrice,ied.vatCategoryId,ied.vatRate,CONCAT(sp.prefix,'-',iin.invoiceNo) " +
                    "AS invoiceId, iin.pdf_comment as pdfComment from bv.InvoiceIncome as iin, bv.InvoiceIncomeDetails  as ied, bv.SystemPrefix AS sp WHERE sp.id = 8 AND iin.id = ied.invoiceId AND" +
                    " ied.invoiceId='" + editId + "' order by ied.vatCategoryId"

            incomeDetailsData = new BudgetViewDatabaseService().executeQuery(strQuery)
        }
        return incomeDetailsData;
    }

    def getVatAmountByCategory(def invIncId) {
        def totalVatAmountByCategory
        def editId = 0;
        if (invIncId instanceof String) {
            editId = Integer.parseInt(invIncId);
        } else {
            editId = invIncId
        }

        if (editId > 0) {
            String strQuery = "Select iid.vat_rate, SUM(iid.total_amount_with_vat-iid.total_amount_without_vat) as toatalVatAmount" +
                    " from invoice_income_details as iid where iid.invoice_id = " + editId + " group by iid.vat_category_id"

            totalVatAmountByCategory = new BudgetViewDatabaseService().executeQuery(strQuery)

        }

        return totalVatAmountByCategory;
    }

    def Map getCustomerGeneralAddress(def customerId) {
        String strQuery = "SELECT address_line1,address_line2,city,postal_code,contact_person_name FROM " +
                "customer_general_address WHERE customer_id=" + customerId
        def customerGeneralAddress = new BudgetViewDatabaseService().executeQuery(strQuery)
        Map generalAddressMap = ["addressLine1": '', "addressLine2": '', "city": '', "postalCode": '', "contactPersonName": '']

        if (customerGeneralAddress.size() > 0) {
            generalAddressMap.addressLine1 = customerGeneralAddress[0][0]
            generalAddressMap.addressLine2 = customerGeneralAddress[0][1]
            generalAddressMap.city = customerGeneralAddress[0][2]
            generalAddressMap.postalCode = customerGeneralAddress[0][3]
            generalAddressMap.contactPersonName = customerGeneralAddress[0][4]
        }

        return generalAddressMap
    }

    def saveIncomeInvoice(def sessionDataArr, def paramsDataArr, def fiscalYear) {


        def bookingPeriod
        def budgetItemId
        def budgetCustomerId

        if (paramsDataArr.fromAfterSave == "1" || (paramsDataArr.copyId != null && paramsDataArr.copyId != "")) {
            bookingPeriod = paramsDataArr.bookingPeriodStartMonthForChange
            def customerIdArr = paramsDataArr.customerIdForChange.split("::")
            def customerId = customerIdArr[0]
            def budgetItemIdArr = new BudgetViewDatabaseService().executeQuery("""SELECT id
                                                                                FROM budget_item_income
                                                                                where customer_id = '${ customerId}' and
                                                                                booking_period_start_month ='${bookingPeriod}'
                                                                                and booking_period_start_year ='${ fiscalYear }'""")
            if (budgetItemIdArr.size()) {
                budgetItemId = budgetItemIdArr[0][0]
            }
            budgetCustomerId = customerId
        } else {
            bookingPeriod = paramsDataArr.bookingPeriod
            budgetItemId = paramsDataArr.budgetItemIncomeId
            budgetCustomerId = paramsDataArr.budgetCustomerId
        }

        Double headTotalGlAmount = 0.00
        Double lineTotalVATAmount = 0.00

        for (int k = 0; k < sessionDataArr.invoiceIncomeArr.size(); k++) {
            headTotalGlAmount = headTotalGlAmount + sessionDataArr.invoiceIncomeArr[k].totalPriceWithoutTax
            lineTotalVATAmount = lineTotalVATAmount + sessionDataArr.invoiceIncomeArr[k].vatAmount
        }

        ///////////INVOICE NO/////////////////
        def InvoiceNo = new CoreParamsHelper().getNextGeneratedNumber('invoiceIncome')
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date tempDueDate = df.parse(paramsDataArr.dueDate);
        Date tempTransDate = df.parse(paramsDataArr.transDate);
        String dueDate = tempDueDate.format("yyyy-MM-dd hh:mm:ss")
        String TransDate = tempTransDate.format("yyyy-MM-dd hh:mm:ss")
        def bookingDate = new ApplicationUtil().convertDateFromMonthAndYear(bookingPeriod, paramsDataArr.bookingYear)

        Map InvoiceIncome = [
                bookingPeriod     : bookingPeriod,
                bookingYear       : paramsDataArr.bookingYear,
                budgetItemIncomeId: budgetItemId,
                comments          : paramsDataArr.comments,
                currencyCode      : "EURO",
                customerAccountNo : paramsDataArr.customerAccountNo,
                customerId        : paramsDataArr.customerId,
                dueDate           : dueDate,
                invoiceNo         : InvoiceNo,
                isReverse         : 0,
                paidStatus        : paramsDataArr.paidStatus,
                paidAmount        : 0.00,
                reverseInvoiceId  : 0,
                status            : paramsDataArr.status,
                termsId           : paramsDataArr.termsId,
                totalGlAmount     : Double.parseDouble(headTotalGlAmount.toString()).round(sessionDataArr.companyInfo[0]['amountDecimalPoint']),
                totalVat          : Double.parseDouble(lineTotalVATAmount.toString()).round(sessionDataArr.companyInfo[0]['amountDecimalPoint']),
                transDate         : TransDate,
                paymentRef        : paramsDataArr.paymentRef,
                budgetCustomerId  : budgetCustomerId,
                userId            : springSecurityService.principal.id
        ]

        def tableNameInvoiceIncome = "InvoiceIncome"
        def insertedInvoiceId = new BudgetViewDatabaseService().insert(InvoiceIncome, tableNameInvoiceIncome)

        //Insert data to rest of the tables.
        insertDetailsAndTransDataToTable(sessionDataArr.invoiceIncomeArr, paramsDataArr, headTotalGlAmount,
                lineTotalVATAmount, bookingDate, insertedInvoiceId, false);

        return insertedInvoiceId;
    }

    def updateIncomeInvoice(def sessionDataArr, def paramsDataArr) {

        Double dAmountWithoutVAT = 0.00
        Double dTotalVATAmount = 0.00

        if (sessionDataArr) {
            for (int k = 0; k < sessionDataArr.size(); k++) {
                double tempAmountWithoutVat = sessionDataArr[k].totalPriceWithoutTax
                dAmountWithoutVAT = dAmountWithoutVAT + tempAmountWithoutVat
                dTotalVATAmount = dTotalVATAmount + sessionDataArr[k].vatAmount
            }
        }

        def dateArr = paramsDataArr.transDate.split("-")
        if (dateArr[0].length() < 4) {
            paramsDataArr.transDate = dateArr[2] + "-" + dateArr[1] + "-" + dateArr[0]
        }
        def dateDueArr = paramsDataArr.dueDate.split("-")
        if (dateDueArr[0].length() < 4) {
            paramsDataArr.dueDate = dateDueArr[2] + "-" + dateDueArr[1] + "-" + dateDueArr[0]
        }

        paramsDataArr.InvoiceIncomeEditId = paramsDataArr.editId.class.isArray() ? paramsDataArr.editId[0] : paramsDataArr.editId;
        def InvoiceIncomeEditId = paramsDataArr.InvoiceIncomeEditId
        if (InvoiceIncomeEditId == null) {
            InvoiceIncomeEditId = paramsDataArr.InvoiceIncomeEditIdx
        }
        paramsDataArr.InvoiceIncomeEditId = InvoiceIncomeEditId

        def cusAccNo = (paramsDataArr.customerAccountNo == null) ? 0 : paramsDataArr.customerAccountNo

        ///////////////Update the Invoice Income Head Information//////
        String strQuery = """ UPDATE InvoiceIncome SET paymentRef= '${paramsDataArr.paymentRef}',customerId=${
            paramsDataArr.customerId
        }, customerAccountNo=${cusAccNo}
                    ,transDate='${paramsDataArr.transDate}' ,dueDate='${paramsDataArr.dueDate}' ,termsId=${
            paramsDataArr.termsId
        }
                    ,paidStatus=${paramsDataArr.paidStatus},totalGlAmount=${
            Double.parseDouble(dAmountWithoutVAT.toString()).round(2)
        }
                    ,totalVat=${Double.parseDouble(dTotalVATAmount.toString()).round(2)}
                    ,comments='${paramsDataArr.comments}' ,userId=${
            springSecurityService.principal.id
        },paidAmount='0.00' WHERE id=${paramsDataArr.InvoiceIncomeEditId} """

        def InvoiceIncomeUpdate = new BudgetViewDatabaseService().executeUpdate(strQuery)

        ////////Delete InvoiceDetails///////////////
        def InvoiceIncomeDetailsDelete = new BudgetViewDatabaseService().executeUpdate("DELETE FROM  bv.InvoiceIncomeDetails WHERE invoiceId=" + paramsDataArr.InvoiceIncomeEditId)
        //////////Delete TransMaster///////////////
        def TransMasterDelete = new BudgetViewDatabaseService().executeUpdate("DELETE FROM  bv.TransMaster WHERE transType=1 AND invoiceNo=" + paramsDataArr.InvoiceIncomeEditId)
        //////////Delete CompanyBankTrans///////////////
        def CompanyBankTransDelete = new BudgetViewDatabaseService().executeUpdate("DELETE FROM  bv.CompanyBankTrans WHERE transType=1 AND invoiceNo=" + paramsDataArr.InvoiceIncomeEditId)


        def bookingDate = new ApplicationUtil().convertDateFromMonthAndYear(paramsDataArr.bookingPeriodStartMonthForChange,
                paramsDataArr.bookingYear)

        //Insert data to rest of the tables.
        insertDetailsAndTransDataToTable(sessionDataArr, paramsDataArr, dAmountWithoutVAT, dTotalVATAmount,
                bookingDate, paramsDataArr.InvoiceIncomeEditId, true);
    }


    def insertDetailsAndTransDataToTable(def sessionDataArr, def paramsDataArr,
                                         def dAmountWithoutVAT, def dTotalVATAmount,
                                         def bookingDate, def invoiceId, def isUpdate) {

        String strRecenciliationCode = ""

        DateFormat df = new SimpleDateFormat("dd-M-yyyy");
        Date tempDueDate = df.parse(paramsDataArr.dueDate);
        Date tempTransDate = df.parse(paramsDataArr.transDate);
        String dueDate = tempDueDate.format("yyyy-MM-dd hh:mm:ss")
        String transDate = tempTransDate.format("yyyy-MM-dd hh:mm:ss")


        def bookingPeriod
        if (paramsDataArr.fromAfterSave == "1") {
            bookingPeriod = paramsDataArr.bookingPeriodStartMonthForChange
        } else {
            bookingPeriod = paramsDataArr.bookingPeriod
            if (isUpdate) {
                transDate = paramsDataArr.transDate
                bookingPeriod = paramsDataArr.bookingPeriodStartMonthForChange
            }
        }

        for (int i = 0; i < sessionDataArr.size(); i++) {
            Map invoiceLineDetails = [
                    accountCode          : sessionDataArr[i].JournalChartId,
                    discountAmount       : Double.parseDouble(sessionDataArr[i].discount.toString()).round(2),
                    invoiceId            : invoiceId,
                    note                 : sessionDataArr[i].note,
                    productCode          : 1,
                    quantity             : sessionDataArr[i].quantity,
                    totalAmountWithVat   : Double.parseDouble((sessionDataArr[i].vatAmount + sessionDataArr[i].totalPriceWithoutTax).toString()).round(2),
                    totalAmountWithoutVat: Double.parseDouble(sessionDataArr[i].totalPriceWithoutTax.toString()).round(2),
                    unitPrice            : Double.parseDouble(sessionDataArr[i].unitPrice.toString()).round(2),
                    vatCategoryId        : sessionDataArr[i].vatCategoryId,
                    vatRate              : sessionDataArr[i].vatRate
            ]

            def tableNameInvoiceIncomeDetails = "InvoiceIncomeDetails"
            def detailsInsertedId = new BudgetViewDatabaseService().insert(invoiceLineDetails, tableNameInvoiceIncomeDetails)

            //Trans Master
            strRecenciliationCode = "" + invoiceId + "#1"//BDR-77
            Double tempAmount = -sessionDataArr[i].totalPriceWithoutTax
            Map trnMas = [
                    accountCode       : sessionDataArr[i].JournalChartId,
                    amount            : Double.parseDouble(tempAmount.toString()).round(2),
                    transDate         : transDate,
                    transType         : 1,
                    invoiceNo         : invoiceId,
                    bookingPeriod     : bookingPeriod,
                    bookingYear       : paramsDataArr.bookingYear,
                    userId            : springSecurityService.principal.id,
                    createDate        : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),
                    process           : com.bv.constants.Process.INVOICE_INCOME,
                    recenciliationCode: strRecenciliationCode,
                    customerId        : paramsDataArr.customerId,
                    vendorId          : 0,
                    bookingDate       : bookingDate
            ]

            def tableNameTransMaster = "TransMaster" //BDR-4
            new BudgetViewDatabaseService().insert(trnMas, tableNameTransMaster)

            def comBankInstance = new CoreParamsHelper().getCompanyBankAccountByGlAccount(sessionDataArr[i].JournalChartId)

            if (comBankInstance.size()) {
                Map comBankTrans = [
                        amount         : Double.parseDouble(sessionDataArr[i].totalPriceWithoutTax.toString()).round(2),
                        companyBankCode: comBankInstance,
                        invoiceNo      : invoiceId,
                        personCode     : paramsDataArr.customerId,
                        transDate      : transDate,
                        transType      : 1,
                        bookingPeriod  : bookingPeriod,
                        bookingYear    : paramsDataArr.bookingYear
                ]

                def tableNameCompanyBankTrans = "CompanyBankTrans"
                new BudgetViewDatabaseService().insert(comBankTrans, tableNameCompanyBankTrans)
            }
            //////////////////////////////

            String vat = sessionDataArr[i].vatRate
            def vatRate = Double.parseDouble(vat)
            def vatId = new CoreParamsHelper().getVatCategoryIdFromRate(vatRate)

            if (vatRate > 0 || sessionDataArr[i].vatAmount > 0) {
                def vatGLAccountInfo = new CoreParamsHelper().getSpacificVatGLAccount(vatId)
                def vatGLAcc = ""
                if (vatGLAccountInfo.size()) {
                    vatGLAcc = vatGLAccountInfo[2];
                }

                strRecenciliationCode = "" + invoiceId + "#1"//BDR-77
                if (vatGLAcc) {
                    Double tempVatAmount = -sessionDataArr[i].vatAmount
                    Map trnVatMas = [
                            accountCode       : vatGLAcc,
                            amount            : Double.parseDouble(tempVatAmount.toString()).round(2),
                            transDate         : transDate,
                            transType         : 1,
                            invoiceNo         : invoiceId,
                            bookingPeriod     : bookingPeriod,
                            bookingYear       : paramsDataArr.bookingYear,
                            userId            : springSecurityService.principal.id,
                            createDate        : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),
                            process           : com.bv.constants.Process.INVOICE_INCOME,
                            recenciliationCode: strRecenciliationCode,
                            customerId        : paramsDataArr.customerId,
                            vendorId          : 0,
                            bookingDate       : bookingDate
                    ]

                    def tableNametrnVatMas = "TransMaster" //BDR-4
                    new BudgetViewDatabaseService().insert(trnVatMas, tableNametrnVatMas)
                }

                def comVatBankInstance = new CoreParamsHelper().getCompanyBankAccountByGlAccount(vatGLAcc)

                if (comVatBankInstance.size()) {

                    Map comVatBankTrans = [
                            amount         : Double.parseDouble(sessionDataArr[i].vatAmount.toString()).round(2),
                            companyBankCode: comVatBankInstance,
                            invoiceNo      : invoiceId,
                            personCode     : paramsDataArr.customerId,
                            transDate      : transDate,
                            transType      : 1,
                            bookingPeriod  : bookingPeriod,
                            bookingYear    : paramsDataArr.bookingYear
                    ]

                    def tableNamecomVatBankTrans = "CompanyBankTrans"
                    new BudgetViewDatabaseService().insert(comVatBankTrans, tableNamecomVatBankTrans)
                }
            }
        }

        //////////////////////////Creditor Entry IN Master Table/////////////////////
        def creditorCreditGlSetupInfo = new CoreParamsHelper().getDebitCreditGlSetupInfo()
        strRecenciliationCode = "" + invoiceId + "#1"//BDR-77
        //Trans Master
        Map trnMas = [
                accountCode       : creditorCreditGlSetupInfo[2],
                amount            : Double.parseDouble((dAmountWithoutVAT + dTotalVATAmount).toString()).round(2),
                transDate         : transDate,
                transType         : 1,
                invoiceNo         : invoiceId,
                bookingPeriod     : bookingPeriod,
                bookingYear       : paramsDataArr.bookingYear,
                userId            : springSecurityService.principal.id,
                createDate        : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),
                process           : com.bv.constants.Process.INVOICE_INCOME,
                recenciliationCode: strRecenciliationCode,
                customerId        : paramsDataArr.customerId,
                vendorId          : 0,
                bookingDate       : bookingDate
        ]

        def tableNametrnMas = "TransMaster" //BDR-4
        new BudgetViewDatabaseService().insert(trnMas, tableNametrnMas)

        sessionDataArr = []

    }

    public LinkedHashMap listOfBudgetItem(int offset, String limit, String sortItem, String sortOrder, String bookingPeriod, String fiscalYearId, String customerId) {

        //def db = new Sql(dataSource)
        def companyConfig = new BudgetViewDatabaseService().getConnectionInformation()
        def db = Sql.newInstance(companyConfig.serverUrl + companyConfig.dbName, companyConfig.dbUser, companyConfig.dbPassword, companyConfig.driverName)

        String expenseEntry = """SELECT  a.id AS invoiceIncomeId,  CONCAT(b.gl_account,'-',c.account_name) AS glAccountName,  CONCAT(sp.prefix,'-',a.budget_id) AS budgetItemID, DATE_FORMAT(a.created_date,'%d-%m-%Y') AS createdDate, b.total_price_without_vat AS totalPriceWithoutVat, b.total_price_with_vat AS totalPriceWithVat, a.STATUS AS total,b.gl_account AS tempGLAccount, b.id AS detailsID  FROM budget_item_income AS a,budget_item_income_details AS b,chart_master AS c, system_prefix AS sp WHERE b.budget_item_income_id=a.id AND b.gl_account=c.account_code AND a.customer_id= ${
            customerId
        } AND a.booking_period_start_month <= ${bookingPeriod} AND a.booking_period_start_year<=${
            fiscalYearId
        } AND a.booking_period_end_month >= ${bookingPeriod} AND a.booking_period_end_year >= ${fiscalYearId} AND sp.id=11
        ORDER BY ${sortItem} ${sortOrder} LIMIT ${limit} OFFSET ${offset} """;

        List<GroovyRowResult> dashboardExpenseBudgetItemList = db.rows(expenseEntry);

        ArrayList dashboardExpenseBudgetItemListFinal = new ArrayList()
        dashboardExpenseBudgetItemList.eachWithIndex { indexValue, keyValue ->
            String expenseTotalEntry = """SELECT COUNT(DISTINCT(a.id)) AS count_total FROM invoice_income AS a LEFT JOIN invoice_income_details AS b ON a.id=b.invoice_id WHERE a.budget_item_income_id=${
                indexValue.invoiceIncomeId
            } AND b.account_code='${indexValue.tempGLAccount}'""";

            List<GroovyRowResult> dashboardExpenseBudgetTotalItemList = db.rows(expenseTotalEntry);
            indexValue.total = dashboardExpenseBudgetTotalItemList[0].count_total

            BigDecimal showtotalPriceWithoutVat = new BigDecimal(indexValue.totalPriceWithoutVat)
            indexValue.totalPriceWithoutVat = String.format("%.2f", showtotalPriceWithoutVat)

            BigDecimal showtotalPriceWithVat = new BigDecimal(indexValue.totalPriceWithVat)
            indexValue.totalPriceWithVat = String.format("%.2f", showtotalPriceWithVat)

            dashboardExpenseBudgetItemListFinal << indexValue
        }

        String countQuery = """SELECT  COUNT(a.id) AS totalBudgetItem FROM budget_item_income AS a,budget_item_income_details AS b,chart_master AS c, system_prefix AS sp WHERE b.budget_item_income_id=a.id AND b.gl_account=c.account_code AND a.customer_id= ${
            customerId
        } AND a.booking_period_start_month <= ${bookingPeriod} AND a.booking_period_start_year<=${
            fiscalYearId
        } AND a.booking_period_end_month>=${bookingPeriod} AND a.booking_period_end_year>=${
            fiscalYearId
        } AND sp.id=11""";

        List<GroovyRowResult> count_result = db.rows(countQuery)
        int total = count_result[0].totalBudgetItem
        db.close();
        return [dashboardIncomeBudgetItemList: dashboardExpenseBudgetItemListFinal, count: total]
    }
}
