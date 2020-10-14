package bv

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import jdk.internal.joptsimple.internal.Row
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.FormulaEvaluator
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import vb.ApplicationUtil
import vb.QuickEntryUtil

import java.text.DateFormat
import java.text.SimpleDateFormat

@Transactional
class ImportInvoiceService {

    SpringSecurityService springSecurityService
    QuickEntryUtil quickEntryUtil = new QuickEntryUtil()

    def readXlsx(def filePath, def userid) {

        try {
            saveImportinvoice(filePath, userid)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    def saveImportinvoice(filePath, userid) {

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath)
//            Workbook workbook = new XSSFWorkbook(fis)
            Workbook workbook = new HSSFWorkbook(fis)  // this is for new version
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator()
            int numberOfSheets = workbook.getNumberOfSheets()
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i)
                Iterator rowIterator = sheet.iterator()
                def skipFirstRow = 0
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next()
                    if (skipFirstRow != 0) {

                        Map invoiceMap = ["vendorOrCustomer": '',
                                          "invoiceDate"     : '',
                                          "budgetName"      : '',
                                          "total"           : '',
                                          "subtotal"        : '',
                                          "vatLow"          : 0,
                                          "vatHigh"         : 0,
                                          "vatDeclaration"  : '',
                                          "description"     : '',
                                          "abroad"          : '',
                                          "invoiceNumber"   : '',
                                          "pdfLinkUrl"      : '',
                                          "iban"            : '',
                                          "userId"          : userid,
                                          "isExcelImport"   : 1,
                                          "vatId"           : '',
                                          "wezpReceiptId"   : '',
                                          "label"           : '',
                                          "subCategory"     : '',
                                          "awsDetails"      : ''
                        ]

                        Iterator cellIterator = row.cellIterator()
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next()
                            int columnIndex = cell.getColumnIndex()
                            if (columnIndex == 0) {
                                invoiceMap.vendorOrCustomer = quickEntryUtil.getCellValue(cell, XlsxCellType.STRING)
                            } else if (columnIndex == 1) {
                                Date tmpDate = quickEntryUtil.getCellValue(cell, XlsxCellType.DATE)
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                invoiceMap.invoiceDate = sdf.format(tmpDate)
                            } else if (columnIndex == 2) {
                                invoiceMap.budgetName = quickEntryUtil.getCellValue(cell, XlsxCellType.STRING)
                            } else if (columnIndex == 3) {
                                invoiceMap.total = quickEntryUtil.getCellValue(cell, XlsxCellType.DECIMAL, evaluator)
                            } else if (columnIndex == 4) {
                                invoiceMap.subtotal = quickEntryUtil.getCellValue(cell, XlsxCellType.DECIMAL, evaluator)
                            } else if (columnIndex == 5) {
                                invoiceMap.vatLow = quickEntryUtil.getCellValue(cell, XlsxCellType.DECIMAL, evaluator)
                            } else if (columnIndex == 6) {
                                invoiceMap.vatHigh = quickEntryUtil.getCellValue(cell, XlsxCellType.DECIMAL, evaluator)
                            } else if (columnIndex == 7) {
                                invoiceMap.vatDeclaration = quickEntryUtil.getCellValue(cell, XlsxCellType.DECIMAL, evaluator)
                            } else if (columnIndex == 8) {
                                invoiceMap.description = quickEntryUtil.getCellValue(cell, XlsxCellType.STRING)
                            } else if (columnIndex == 9) {
                                def temp = cell.getBooleanCellValue()
                                if (temp) {
                                    invoiceMap.abroad = 1
                                } else {
                                    invoiceMap.abroad = 0
                                }
                            } else if (columnIndex == 10) {
                                cell.setCellType(Cell.CELL_TYPE_STRING)
                                invoiceMap.invoiceNumber = quickEntryUtil.getCellValue(cell, XlsxCellType.STRING)
                            } else if (columnIndex == 11) {
                                invoiceMap.pdfLinkUrl = quickEntryUtil.getCellValue(cell, XlsxCellType.STRING)
                            } else if (columnIndex == 12) {
                                invoiceMap.iban = quickEntryUtil.getCellValue(cell, XlsxCellType.STRING)
                            } else if (columnIndex == 13) {
                                invoiceMap.wezpReceiptId = quickEntryUtil.getCellValue(cell, XlsxCellType.STRING)
                            } else if (columnIndex == 15) {
                                invoiceMap.label = quickEntryUtil.getCellValue(cell, XlsxCellType.STRING)
                            } else if (columnIndex == 16) {
                                invoiceMap.subCategory = quickEntryUtil.getCellValue(cell, XlsxCellType.STRING)
                            } else if (columnIndex == 17) {
                                invoiceMap.awsDetails = quickEntryUtil.getCellValue(cell, XlsxCellType.STRING)
                            }
                        }
                        def tableName = "import_invoice"
                        new BudgetViewDatabaseService().insert(invoiceMap, tableName)
                    }
                    skipFirstRow++
                }
            }
            fis.close()
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    Map hasVendor(def vendorName) {
        Map map = ["result": false, "gl": '', "id": '', "venOrCus": '']

        def vendorArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase("""SELECT default_gl_account,id
            from vendor_master WHERE vendor_name ='${vendorName}'""");
        if (vendorArr.size() > 0) {
            map.result = true
            map.gl = vendorArr[0][0]
            map.id = vendorArr[0][1]
            map.venOrCus = 'VEN'
        }
        return map;
    }

    Map hasVendorAutomatic(def vendorName, BusinessCompany businessCompany) {
        Map map = ["result": false, "gl": '', "id": '', "venOrCus": '']

        def vendorArr = new BudgetViewDatabaseService().executeQueryPerDatabase("""SELECT default_gl_account,id
                                            from vendor_master WHERE vendor_name ='${vendorName}'""", businessCompany)
        if (vendorArr.size() > 0) {
            map.result = true
            map.gl = vendorArr[0][0]
            map.id = vendorArr[0][1]
            map.venOrCus = 'VEN'
        }
        return map
    }

    Map hasCustomer(def customerName) {
        Map map = ["result": false, "gl": '', "id": '', "venOrCus": '']

        def customerArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase("""SELECT default_gl_account,id
from customer_master WHERE customer_name ='${
            customerName
        }'""");
        if (customerArr.size() > 0) {
            map.result = true
            map.gl = customerArr[0][0]
            map.id = customerArr[0][1]
            map.venOrCus = 'CUS'
        }
        return map;
    }

    Map hasCustomerAutomatic(def customerName, BusinessCompany businessCompany) {
        Map map = ["result": false, "gl": '', "id": '', "venOrCus": '']

        def customerArr = new BudgetViewDatabaseService().executeQueryPerDatabase("""SELECT default_gl_account,id
                                    from customer_master WHERE customer_name ='${customerName}'""", businessCompany)

        if (customerArr.size() > 0) {
            map.result = true
            map.gl = customerArr[0][0]
            map.id = customerArr[0][1]
            map.venOrCus = 'CUS'
        }
        return map
    }


    def saveCustomer(def customerMap, def invoiceEntry) {
        def customerCode = new CoreParamsHelper().getNextGeneratedNumber('customer')
        def customerType = "cn"
        def creditStatus = "Good History"

        Map insertedValue = [
                chamOfCommerce  : "",
                comments        : "",
                customerName    : customerMap.customerName,
                defaultGlAccount: customerMap.gl,
                email           : "",
                firstName       : "",
                lastName        : "",
                middleName      : "",
                paymentTermId   : 1,
                vat             : customerMap.vat,
                customerType    : customerType,
                status          : 1,
                gender          : "Male",
                creditStatus    : creditStatus,
                customerCode    : customerCode
        ]

        def tableName = "customerMaster"
        Integer customerMasterInstanceId = new BudgetViewDatabaseService().insert(insertedValue, tableName)
        saveCustomerBankAccount(customerMasterInstanceId, customerMap, invoiceEntry)
    }

    def saveCustomerAutomatic(def customerMap, def invoiceEntry, BusinessCompany businessCompany) {
        def customerCode = new CoreParamsHelper().getNextGeneratedNumberAutomatic('customer', businessCompany)
        def customerType = "cn"
        def creditStatus = "Good History"

        Map insertedValue = [
                chamOfCommerce  : "",
                comments        : "",
                customerName    : customerMap.customerName,
                defaultGlAccount: customerMap.gl,
                email           : "",
                firstName       : "",
                lastName        : "",
                middleName      : "",
                paymentTermId   : 1,
                vat             : customerMap.vat,
                customerType    : customerType,
                status          : 1,
                gender          : "Male",
                creditStatus    : creditStatus,
                customerCode    : customerCode
        ]

        def tableName = "customerMaster"
        Integer customerMasterInstanceId = new BudgetViewDatabaseService()
                .insertAutomatic(insertedValue, tableName, businessCompany)
        saveCustomerBankAccountAutomatic(customerMasterInstanceId, customerMap, invoiceEntry, businessCompany)
    }

    def saveCustomerBankAccount(def customerId, def customerMap, def invoiceEntry) {

        Map insertStr = [
                bank_account_name: customerMap.customerName,
                bank_account_no  : customerMap.customerName,
                iban_prefix      : "",
                status           : 1,
                customer_id     : customerId

        ]
        def tableName = "customer_bank_account"
        new BudgetViewDatabaseService().insert(insertStr, tableName)

        if(invoiceEntry.iban){
            def ibanPrefix
            def accountNo
            try {
                ibanPrefix=invoiceEntry.iban.substring(0,8)
                accountNo=invoiceEntry.iban.substring(8,invoiceEntry.iban.length())
            } catch (Exception e){

            }
            Map insertIbn = [
                    bank_account_name: customerMap.customerName,
                    bank_account_no  : removeZero(accountNo),
                    iban_prefix      : ibanPrefix,
                    status           : 1,
                    customer_id     : customerId

            ]
            tableName = "customer_bank_account"
            new BudgetViewDatabaseService().insert(insertIbn, tableName)
        }
    }

    def saveCustomerBankAccountAutomatic(def customerId, def customerMap, def invoiceEntry, BusinessCompany businessCompany) {

        Map insertStr = [
                bank_account_name: customerMap.customerName,
                bank_account_no  : customerMap.customerName,
                iban_prefix      : "",
                status           : 1,
                customer_id     : customerId

        ]
        def tableName = "customer_bank_account"
        new BudgetViewDatabaseService().insertAutomatic(insertStr, tableName, businessCompany)

        if(invoiceEntry.iban){
            def ibanPrefix
            def accountNo
            try {
                ibanPrefix=invoiceEntry.iban.substring(0,8)
                accountNo=invoiceEntry.iban.substring(8,invoiceEntry.iban.length())
            } catch (Exception e){

            }
            Map insertIbn = [
                    bank_account_name: customerMap.customerName,
                    bank_account_no  : removeZero(accountNo),
                    iban_prefix      : ibanPrefix,
                    status           : 1,
                    customer_id     : customerId

            ]
            tableName = "customer_bank_account"
            new BudgetViewDatabaseService().insertAutomatic(insertIbn, tableName, businessCompany)
        }
    }

    def saveVendor(def vendorMap, def invoiceEntry) {
        def vendorCode = new CoreParamsHelper().getNextGeneratedNumber('vendor')
        def vendorType = "vn"
        def creditStatus = "Good History"

        Map insertStr = [
                cham_of_commerce  : "",
                comments          : "",
                default_gl_account: vendorMap.gl,
                email             : "",
                first_name        : "",
                last_name         : "",
                middle_name       : "",
                payment_term_id   : vendorMap.paymentTerm,
                status            : "1",
                gender            : "",
                vat               : vendorMap.vat,
                vendor_type       : vendorType,
                vendor_name       : vendorMap.vendorName,
                by_shop           : 0,
                credit_status     : creditStatus,
                vendor_code       : vendorCode

        ]

        def tableName = "vendor_master"
        def insertId = new BudgetViewDatabaseService().insert(insertStr, tableName)
        saveVendorBankAccount(insertId, vendorMap, invoiceEntry)
    }

    def saveVendorAutomatic(def vendorMap, def invoiceEntry, BusinessCompany businessCompany) {
        def vendorCode = new CoreParamsHelper().getNextGeneratedNumberAutomatic('vendor', businessCompany )
        def vendorType = "vn"
        def creditStatus = "Good History"

        Map insertStr = [
                cham_of_commerce  : "",
                comments          : "",
                default_gl_account: vendorMap.gl,
                email             : "",
                first_name        : "",
                last_name         : "",
                middle_name       : "",
                payment_term_id   : vendorMap.paymentTerm,
                status            : "1",
                gender            : "",
                vat               : vendorMap.vat,
                vendor_type       : vendorType,
                vendor_name       : vendorMap.vendorName,
                by_shop           : 0,
                credit_status     : creditStatus,
                vendor_code       : vendorCode

        ]

        def tableName = "vendor_master"
        def insertId = new BudgetViewDatabaseService().insertAutomatic(insertStr, tableName, businessCompany)
        saveVendorBankAccountAutomatic(insertId, vendorMap, invoiceEntry, businessCompany)
    }

    def saveVendorBankAccount(def vendorId, def vendorMap, def invoiceEntry) {
        Map insertStr = [
                bank_account_name: vendorMap.vendorName,
                bank_account_no  : vendorMap.vendorName,
                iban_prefix      : "",
                status           : 1,
                vendor_id        : vendorId

        ]
        def tableName = "vendor_bank_account"
        new BudgetViewDatabaseService().insert(insertStr, tableName)

        if(invoiceEntry.iban){
            def ibanPrefix
            def accountNo
            try {
                ibanPrefix=invoiceEntry.iban.substring(0,8)
                accountNo=invoiceEntry.iban.substring(8,invoiceEntry.iban.length())
            } catch (Exception e) {

            }
            Map insertIban = [
                    bank_account_name: vendorMap.vendorName,
                    bank_account_no  : removeZero(accountNo),
                    iban_prefix      : ibanPrefix,
                    status           : 1,
                    vendor_id        : vendorId

            ]
            tableName = "vendor_bank_account"
            new BudgetViewDatabaseService().insert(insertIban, tableName)
        }
    }

    def saveVendorBankAccountAutomatic(def vendorId, def vendorMap, def invoiceEntry, BusinessCompany businessCompany) {
        Map insertStr = [
                bank_account_name: vendorMap.vendorName,
                bank_account_no  : vendorMap.vendorName,
                iban_prefix      : "",
                status           : 1,
                vendor_id        : vendorId

        ]
        def tableName = "vendor_bank_account"
        new BudgetViewDatabaseService().insertAutomatic(insertStr, tableName, businessCompany)

        if(invoiceEntry.iban){
            def ibanPrefix
            def accountNo
            try {
                ibanPrefix=invoiceEntry.iban.substring(0,8)
                accountNo=invoiceEntry.iban.substring(8,invoiceEntry.iban.length())
            } catch (Exception e) {

            }
            Map insertIban = [
                    bank_account_name: vendorMap.vendorName,
                    bank_account_no  : removeZero(accountNo),
                    iban_prefix      : ibanPrefix,
                    status           : 1,
                    vendor_id        : vendorId

            ]
            tableName = "vendor_bank_account"
            new BudgetViewDatabaseService().insertAutomatic(insertIban, tableName, businessCompany)
        }
    }

    def createVendorOrCustomer(def invoiceEntry) {
        def infoArr = checkBudgetFor(invoiceEntry.budgetName);
        def checkVendorOrCustomer = infoArr.venOrCus
        if (checkVendorOrCustomer == 'VEN') {
            createVendorMap(invoiceEntry)
            updateVendorIban(invoiceEntry)
        } else if (checkVendorOrCustomer == 'CUS') {
            createCustomerMap(invoiceEntry)
            updateCustomerIban(invoiceEntry)
        }
    }

    def createVendorOrCustomerAutomatic(def invoiceEntry, BusinessCompany businessCompany) {
        def infoArr = checkBudgetForAutomatic(invoiceEntry.budgetName, businessCompany)
        def checkVendorOrCustomer = infoArr.venOrCus

        if (checkVendorOrCustomer == 'VEN') {
            createVendorMapAutomatic(invoiceEntry, businessCompany)
            updateVendorIbanAutomatic(invoiceEntry, businessCompany)
        } else if (checkVendorOrCustomer == 'CUS') {
            createCustomerMapAutomatic(invoiceEntry, businessCompany)
            updateCustomerIbanAutomatic(invoiceEntry, businessCompany)
        }
    }

    def updateVendorIban(def invoiceEntry){
        def vendorBankAcc = new BudgetViewDatabaseService().executeQueryWithoutCamelCase("""SELECT bc.bank_account_name,bc.bank_account_no,bc.id from vendor_master vm
INNER JOIN vendor_bank_account bc on vm.id = bc.vendor_id where vm.vendor_name ='${invoiceEntry.vendorOrCustomer}'""")
        def vendor = new BudgetViewDatabaseService().executeQueryWithoutCamelCase("""
SELECT vm.id from vendor_master vm where vm.vendor_name ='${invoiceEntry.vendorOrCustomer}'""")
        if(vendorBankAcc.size()==0 && invoiceEntry.iban.length()>9){
            def ibanPrefix=invoiceEntry.iban.substring(0,8)
            def accountNo=invoiceEntry.iban.substring(8,invoiceEntry.iban.length())
            Map insertIban = [
                    bank_account_name: invoiceEntry.vendorOrCustomer,
                    bank_account_no  : invoiceEntry.vendorOrCustomer,
                    iban_prefix      : "",
                    status           : 1,
                    vendor_id        : vendor[0][0]

            ]
            def tableName = "vendor_bank_account"
            new BudgetViewDatabaseService().insert(insertIban, tableName)

            Map VendorBankAccount = [
                    bank_account_name     : invoiceEntry.vendorOrCustomer,
                    bank_account_no       : removeZero(accountNo),
                    iban_prefix: ibanPrefix,
                    status          : 1,
                    vendor_id      : vendor[0][0]
            ]
            def tableNameVendorBankAccount = "VendorBankAccount"
            def invoiceId = new BudgetViewDatabaseService().insert(VendorBankAccount, tableNameVendorBankAccount)


        }else if(vendorBankAcc.size()>0 && invoiceEntry.iban && invoiceEntry.iban.length()>9){
            def ibanPrefix=invoiceEntry.iban.substring(0,8)
            def accountNo=invoiceEntry.iban.substring(8,invoiceEntry.iban.length())
            def accountNoSubStr = removeZero(accountNo)
            List vanAccountName = new ArrayList()
            List vanAccountNo = new ArrayList()
            List searchTagStr = new ArrayList()
            for(int i=0;i<vendorBankAcc.size();i++){
                if(vendorBankAcc[i][0]==invoiceEntry.vendorOrCustomer)
                    vanAccountName.add(vendorBankAcc[i][0])
                if(vendorBankAcc[i][1]==accountNoSubStr)
                    vanAccountNo.add(vendorBankAcc[i][1])
                if(vendorBankAcc[i][1]==invoiceEntry.vendorOrCustomer)
                    searchTagStr.add(vendorBankAcc[i][1])
            }

            if (!vanAccountName || !searchTagStr){
                Map insertIban = [
                        bank_account_name: invoiceEntry.vendorOrCustomer,
                        bank_account_no  : invoiceEntry.vendorOrCustomer,
                        iban_prefix      : "",
                        status           : 1,
                        vendor_id        : vendor[0][0]
                ]
                def tableName = "vendor_bank_account"
                new BudgetViewDatabaseService().insert(insertIban, tableName)
            }
            if(!vanAccountNo){
                Map insertIban = [
                        bank_account_name: invoiceEntry.vendorOrCustomer,
                        bank_account_no  : accountNoSubStr,
                        iban_prefix      : ibanPrefix,
                        status           : 1,
                        vendor_id        : vendor[0][0]
                ]
                def tableName = "vendor_bank_account"
                new BudgetViewDatabaseService().insert(insertIban, tableName)
            }
        }
    }

    def updateVendorIbanAutomatic(def invoiceEntry, BusinessCompany businessCompany){

        def vendorBankAcc = new BudgetViewDatabaseService().executeQueryPerDatabase(
                """SELECT bc.bank_account_name,bc.bank_account_no,bc.id from vendor_master vm
                                               INNER JOIN vendor_bank_account bc on vm.id = bc.vendor_id 
                                               where vm.vendor_name ='${invoiceEntry.vendorOrCustomer}'""", businessCompany)

        def vendor = new BudgetViewDatabaseService().executeQueryPerDatabase(
                """SELECT vm.id from vendor_master vm where vm.vendor_name ='${invoiceEntry.vendorOrCustomer}'""",
                businessCompany)


        if(vendorBankAcc.size()==0 && invoiceEntry.iban.length()>9){
            def ibanPrefix=invoiceEntry.iban.substring(0,8)
            def accountNo=invoiceEntry.iban.substring(8,invoiceEntry.iban.length())
            Map insertIban = [
                    bank_account_name: invoiceEntry.vendorOrCustomer,
                    bank_account_no  : invoiceEntry.vendorOrCustomer,
                    iban_prefix      : "",
                    status           : 1,
                    vendor_id        : vendor[0][0]

            ]
            def tableName = "vendor_bank_account"
            new BudgetViewDatabaseService().insertAutomatic(insertIban, tableName, businessCompany)

            Map VendorBankAccount = [
                    bank_account_name     : invoiceEntry.vendorOrCustomer,
                    bank_account_no       : removeZero(accountNo),
                    iban_prefix           : ibanPrefix,
                    status                : 1,
                    vendor_id             : vendor[0][0]
            ]
            def tableNameVendorBankAccount = "VendorBankAccount"
            def invoiceId = new BudgetViewDatabaseService().
                    insertAutomatic(VendorBankAccount, tableNameVendorBankAccount, businessCompany)

        }
        else if(vendorBankAcc.size()>0 && invoiceEntry.iban && invoiceEntry.iban.length()>9){

            def ibanPrefix=invoiceEntry.iban.substring(0,8)
            def accountNo=invoiceEntry.iban.substring(8,invoiceEntry.iban.length())
            def accountNoSubStr = removeZero(accountNo)
            List vanAccountName = new ArrayList()
            List vanAccountNo = new ArrayList()
            List searchTagStr = new ArrayList()
            for(int i=0;i<vendorBankAcc.size();i++){
                if(vendorBankAcc[i][0]==invoiceEntry.vendorOrCustomer)
                    vanAccountName.add(vendorBankAcc[i][0])
                if(vendorBankAcc[i][1]==accountNoSubStr)
                    vanAccountNo.add(vendorBankAcc[i][1])
                if(vendorBankAcc[i][1]==invoiceEntry.vendorOrCustomer)
                    searchTagStr.add(vendorBankAcc[i][1])
            }

            if (!vanAccountName || !searchTagStr){
                Map insertIban = [
                        bank_account_name: invoiceEntry.vendorOrCustomer,
                        bank_account_no  : invoiceEntry.vendorOrCustomer,
                        iban_prefix      : "",
                        status           : 1,
                        vendor_id        : vendor[0][0]
                ]
                def tableName = "vendor_bank_account"
                new BudgetViewDatabaseService().insertAutomatic(insertIban, tableName, businessCompany)
            }
            if(!vanAccountNo){
                Map insertIban = [
                        bank_account_name: invoiceEntry.vendorOrCustomer,
                        bank_account_no  : accountNoSubStr,
                        iban_prefix      : ibanPrefix,
                        status           : 1,
                        vendor_id        : vendor[0][0]
                ]
                def tableName = "vendor_bank_account"
                new BudgetViewDatabaseService().insertAutomatic(insertIban, tableName, businessCompany)
            }
        }
    }

    String removeZero (String str){

        for(int i=0;i<str.length();i++){
            if(str.startsWith("0")){
                str=str.subSequence(1,str.length())
            }else{
                break;
            }
        }
        return str;
    }

    def updateCustomerIban(def invoiceEntry){
        def customerBankAcc = new BudgetViewDatabaseService().executeQueryWithoutCamelCase("""SELECT  bc.bank_account_name,bc.bank_account_no,bc.id from customer_master cm
INNER JOIN customer_bank_account bc on cm.id = bc.customer_id where cm.customer_name ='${invoiceEntry.vendorOrCustomer}'""")
        def customer= new BudgetViewDatabaseService().executeQueryWithoutCamelCase("""
SELECT cm.id from customer_master cm where cm.customer_name ='${invoiceEntry.vendorOrCustomer}'""")
        if(customerBankAcc.size()==0&& invoiceEntry.iban.length()>9){
            def ibanPrefix=invoiceEntry.iban.substring(0,8)
            def accountNo=invoiceEntry.iban.substring(8,invoiceEntry.iban.length())
            Map customerBankAccount = [
                    bank_account_name     : invoiceEntry.vendorOrCustomer,
                    bank_account_no       :invoiceEntry.vendorOrCustomer,
                    iban_prefix: "",
                    status          : 1,
                    customer_id      : customer[0][0]
            ]
            def tableNameCustomerBankAccount = "CustomerBankAccount"
            def invoiceId = new BudgetViewDatabaseService().insert(customerBankAccount, tableNameCustomerBankAccount)
            Map customerBankIban = [
                    bank_account_name     : invoiceEntry.vendorOrCustomer,
                    bank_account_no       : removeZero(accountNo),
                    iban_prefix: ibanPrefix,
                    status          : 1,
                    customer_id      : customer[0][0]
            ]
            tableNameCustomerBankAccount = "CustomerBankAccount"
            new BudgetViewDatabaseService().insert(customerBankIban, tableNameCustomerBankAccount)

        }else if(customerBankAcc.size()>0  && invoiceEntry.iban && invoiceEntry.iban.length()>9){
            def ibanPrefix=invoiceEntry.iban.substring(0,8)
            def accountNo=invoiceEntry.iban.substring(8,invoiceEntry.iban.length())
            def accountNoSubStr = removeZero(accountNo)
            List cusAccountName = new ArrayList()
            List cusAccountNo = new ArrayList()
            List searchTagStr = new ArrayList()
            for(int i=0;i<customerBankAcc.size();i++){
                if(customerBankAcc[i][0]==invoiceEntry.vendorOrCustomer)
                    cusAccountName.add(customerBankAcc[i][0])
                if(customerBankAcc[i][1]==accountNoSubStr)
                    cusAccountNo.add(customerBankAcc[i][1])
                if(customerBankAcc[i][1]==invoiceEntry.vendorOrCustomer)
                    searchTagStr.add(customerBankAcc[i][1])
            }

            if (!cusAccountName || !searchTagStr){
                Map insertIbn = [
                        bank_account_name: invoiceEntry.vendorOrCustomer,
                        bank_account_no  : invoiceEntry.vendorOrCustomer,
                        iban_prefix      : "",
                        status           : 1,
                        customer_id     :  customer[0][0]
                ]
                def tableName = "customer_bank_account"
                new BudgetViewDatabaseService().insert(insertIbn, tableName)
            }
            if(!cusAccountNo){
                Map insertIban = [
                        bank_account_name: invoiceEntry.vendorOrCustomer,
                        bank_account_no  : accountNoSubStr,
                        iban_prefix      : ibanPrefix,
                        status           : 1,
                        customer_id        : customer[0][0]
                ]
                def tableName = "customer_bank_account"
                new BudgetViewDatabaseService().insert(insertIban, tableName)
            }
        }
    }

    def updateCustomerIbanAutomatic(def invoiceEntry, BusinessCompany businessCompany){
        def customerBankAcc = new BudgetViewDatabaseService()
                .executeQueryPerDatabase("""SELECT  bc.bank_account_name,bc.bank_account_no,bc.id from customer_master cm
                                            INNER JOIN customer_bank_account bc on cm.id = bc.customer_id 
                                            where cm.customer_name ='${invoiceEntry.vendorOrCustomer}'""", businessCompany)

        def customer= new BudgetViewDatabaseService()
                .executeQueryPerDatabase("""SELECT cm.id from customer_master cm 
                                        where cm.customer_name ='${invoiceEntry.vendorOrCustomer}'""", businessCompany)

        if(customerBankAcc.size()==0&& invoiceEntry.iban.length()>9){
            def ibanPrefix=invoiceEntry.iban.substring(0,8)
            def accountNo=invoiceEntry.iban.substring(8,invoiceEntry.iban.length())
            Map customerBankAccount = [
                    bank_account_name     : invoiceEntry.vendorOrCustomer,
                    bank_account_no       :invoiceEntry.vendorOrCustomer,
                    iban_prefix: "",
                    status          : 1,
                    customer_id      : customer[0][0]
            ]
            def tableNameCustomerBankAccount = "CustomerBankAccount"
            def invoiceId = new BudgetViewDatabaseService()
                    .insertAutomatic(customerBankAccount, tableNameCustomerBankAccount, businessCompany)
            Map customerBankIban = [
                    bank_account_name     : invoiceEntry.vendorOrCustomer,
                    bank_account_no       : removeZero(accountNo),
                    iban_prefix: ibanPrefix,
                    status          : 1,
                    customer_id      : customer[0][0]
            ]
            tableNameCustomerBankAccount = "CustomerBankAccount"
            new BudgetViewDatabaseService().insertAutomatic(customerBankIban, tableNameCustomerBankAccount, businessCompany)

        }else if(customerBankAcc.size()>0  && invoiceEntry.iban && invoiceEntry.iban.length()>9){
            def ibanPrefix=invoiceEntry.iban.substring(0,8)
            def accountNo=invoiceEntry.iban.substring(8,invoiceEntry.iban.length())
            def accountNoSubStr = removeZero(accountNo)
            List cusAccountName = new ArrayList()
            List cusAccountNo = new ArrayList()
            List searchTagStr = new ArrayList()
            for(int i=0;i<customerBankAcc.size();i++){
                if(customerBankAcc[i][0]==invoiceEntry.vendorOrCustomer)
                    cusAccountName.add(customerBankAcc[i][0])
                if(customerBankAcc[i][1]==accountNoSubStr)
                    cusAccountNo.add(customerBankAcc[i][1])
                if(customerBankAcc[i][1]==invoiceEntry.vendorOrCustomer)
                    searchTagStr.add(customerBankAcc[i][1])
            }

            if (!cusAccountName || !searchTagStr){
                Map insertIbn = [
                        bank_account_name: invoiceEntry.vendorOrCustomer,
                        bank_account_no  : invoiceEntry.vendorOrCustomer,
                        iban_prefix      : "",
                        status           : 1,
                        customer_id     :  customer[0][0]
                ]
                def tableName = "customer_bank_account"
                new BudgetViewDatabaseService().insertAutomatic(insertIbn, tableName, businessCompany)
            }
            if(!cusAccountNo){
                Map insertIban = [
                        bank_account_name: invoiceEntry.vendorOrCustomer,
                        bank_account_no  : accountNoSubStr,
                        iban_prefix      : ibanPrefix,
                        status           : 1,
                        customer_id        : customer[0][0]
                ]
                def tableName = "customer_bank_account"
                new BudgetViewDatabaseService().insertAutomatic(insertIban, tableName, businessCompany)
            }
        }
    }

    def createIncomeOrExpenseInvoice(def invoiceEntry, def userId) {
        def infoArr = checkBudgetFor(invoiceEntry.budgetName);
        if (infoArr.venOrCus == 'VEN') {
            createExpenseInvoiceMap(invoiceEntry, userId)
        } else if (infoArr.venOrCus == 'CUS') {
            createIncomeInvoiceMap(invoiceEntry, userId)
        }
    }

    def createIncomeOrExpenseInvoiceAutomatic(def invoiceEntry, def userId, BusinessCompany businessCompany) {
        def infoArr = checkBudgetForAutomatic(invoiceEntry.budgetName, businessCompany)
        if (infoArr.venOrCus == 'VEN') {
            createExpenseInvoiceMapAutomatic(invoiceEntry, userId, businessCompany)
        } else if (infoArr.venOrCus == 'CUS') {
            createIncomeInvoiceMapAutomatic(invoiceEntry, userId, businessCompany)
        }
    }

    def createCustomerMap(def invoiceEntry) {
        def checkCustomer = false
        def customerArr = hasCustomer(invoiceEntry.vendorOrCustomer);
        checkCustomer = customerArr.result
        if (!checkCustomer) {
            Map customerMap = ["customerName": '', "gl": '', "vat": '', "paymentTerm": 1]
            customerMap.customerName = invoiceEntry.vendorOrCustomer
            def glArr = hasCustomer(invoiceEntry.budgetName);
            customerMap.gl = glArr.gl
            def vatLow = invoiceEntry.vatLow
            def vatHigh = invoiceEntry.vatHigh
            customerMap.vat = getvatCategoryId(vatLow, vatHigh)
            saveCustomer(customerMap, invoiceEntry)
        }
    }

    def createCustomerMapAutomatic(def invoiceEntry, BusinessCompany businessCompany) {
        def checkCustomer = false
        def customerArr = hasCustomerAutomatic(invoiceEntry.vendorOrCustomer, businessCompany)
        checkCustomer = customerArr.result
        if (!checkCustomer) {
            Map customerMap = ["customerName": '', "gl": '', "vat": '', "paymentTerm": 1]
            customerMap.customerName = invoiceEntry.vendorOrCustomer
            def glArr = hasCustomerAutomatic(invoiceEntry.budgetName, businessCompany)
            customerMap.gl = glArr.gl
            def vatLow = invoiceEntry.vatLow
            def vatHigh = invoiceEntry.vatHigh
            customerMap.vat = getvatCategoryId(vatLow, vatHigh)
            saveCustomerAutomatic(customerMap, invoiceEntry, businessCompany)
        }
    }

    def createVendorMap(def invoiceEntry) {
        def checkVendor = false

        def vendorArr = hasVendor(invoiceEntry.vendorOrCustomer);
        checkVendor = vendorArr.result
        if (!checkVendor) {
            Map vendorMap = ["vendorName": '', "gl": '', "vat": '', "paymentTerm": 1]
            vendorMap.vendorName = invoiceEntry.vendorOrCustomer
            def glArr = hasVendor(invoiceEntry.budgetName);
            vendorMap.gl = glArr.gl
            def vatLow = invoiceEntry.vatLow
            def vatHigh = invoiceEntry.vatHigh
            vendorMap.vat = getvatCategoryId(vatLow, vatHigh)
            saveVendor(vendorMap, invoiceEntry);
        }
    }

    def createVendorMapAutomatic(def invoiceEntry, BusinessCompany businessCompany) {
        def checkVendor = false

        def vendorArr = hasVendorAutomatic(invoiceEntry.vendorOrCustomer, businessCompany)
        checkVendor = vendorArr.result
        if (!checkVendor) {
            Map vendorMap = ["vendorName": '', "gl": '', "vat": '', "paymentTerm": 1]
            vendorMap.vendorName = invoiceEntry.vendorOrCustomer
            def glArr = hasVendorAutomatic(invoiceEntry.budgetName, businessCompany)
            vendorMap.gl = glArr.gl
            def vatLow = invoiceEntry.vatLow
            def vatHigh = invoiceEntry.vatHigh
            vendorMap.vat = getvatCategoryId(vatLow, vatHigh)
            saveVendorAutomatic(vendorMap, invoiceEntry, businessCompany)
        }
    }

    def getvatCategoryId(def vatLow, def vatHigh) {
        def vatId
        if ((vatLow == '' && vatHigh == "") || (vatLow == 0 && vatHigh == 0)) {
            vatId = '4'
        } else if (vatLow != 0 && vatHigh != 0) {
            vatId = '1' //take vatHigh
        } else if (vatLow) {
            vatId = '6'
        } else if (vatHigh) {
            vatId = '1'
        }

        return vatId
    }

    def createExpenseInvoiceMap(def invoiceEntry, def userid) {

        Map invoiceMap = ["vendorName"           : '', "description": '',
                          "date"                 : '', "budgetName": '', "id": '',
                          "invoiceNumber"        : '',
                          "totalAmountWithVat"   : '',
                          "totalAmountWithoutVat": '',
                          "vatCategoryId"        : '',
                          "totalVat"             : '',
                          "gl"                   : '',
                          "pdfLink"              : '',
                          "label"                : '',
                          "awsDetails"           : '',
                          "subCategory"          : ''

        ]

        def glArr = hasVendor(invoiceEntry.budgetName)
        def vatLow = invoiceEntry.vatLow
        def vatHigh = invoiceEntry.vatHigh
        invoiceMap.vendorName = invoiceEntry.vendorOrCustomer
        invoiceMap.description = invoiceEntry.description
        invoiceMap.budgetName = invoiceEntry.budgetName
        invoiceMap.date = invoiceEntry.invoiceDate
        invoiceMap.totalAmountWithVat = invoiceEntry.total
        invoiceMap.totalAmountWithoutVat = invoiceEntry.subtotal

        if(invoiceEntry.is_excel_import == 1) { //import file
            if (invoiceEntry.totalVat == 0) { // this is an exception**
                invoiceMap.vatCategoryId = 4 // vatNull
                invoiceMap.totalAmountWithoutVat = invoiceEntry.total
            }
            else {
                invoiceMap.vatCategoryId =  getvatCategoryId(vatLow, vatHigh)
            }

        }
        else if(invoiceEntry.is_excel_import == 0) { //quickEntry
            invoiceMap.vatCategoryId =  invoiceEntry.vat_id
        }

        if (invoiceEntry.totalVat != 0){
            invoiceMap.totalVat = invoiceEntry.vatLow + invoiceEntry.vatHigh
        } else {
            invoiceMap.totalVat = invoiceEntry.totalVat
        }

        invoiceMap.gl = glArr.gl
        invoiceMap.id = invoiceEntry.id
        invoiceMap.pdfLink = invoiceEntry.pdfLink
        invoiceMap.invoiceNumber = invoiceEntry.invoiceNumber
        invoiceMap.label = invoiceEntry.label
        invoiceMap.awsDetails = invoiceEntry.awsDetails
        invoiceMap.subCategory = invoiceEntry.subCategory
        saveInvoiceExpense(invoiceMap, userid)

    }

    def createExpenseInvoiceMapAutomatic(def invoiceEntry, def userid, BusinessCompany businessCompany) {

        Map invoiceMap = ["vendorName"           : '', "description": '',
                          "date"                 : '', "budgetName": '', "id": '',
                          "invoiceNumber"        : '',
                          "totalAmountWithVat"   : '',
                          "totalAmountWithoutVat": '',
                          "vatCategoryId"        : '',
                          "totalVat"             : '',
                          "gl"                   : '',
                          "pdfLink"              : ''
        ]

        def glArr = hasVendorAutomatic(invoiceEntry.budgetName, businessCompany)
        def vatLow = invoiceEntry.vatLow
        def vatHigh = invoiceEntry.vatHigh
        invoiceMap.vendorName = invoiceEntry.vendorOrCustomer
        invoiceMap.description = invoiceEntry.description
        invoiceMap.budgetName = invoiceEntry.budgetName
        invoiceMap.date = invoiceEntry.invoiceDate
        invoiceMap.totalAmountWithVat = invoiceEntry.total
        invoiceMap.totalAmountWithoutVat = invoiceEntry.subtotal

        if(invoiceEntry.is_excel_import == 1) { //import file
            if (invoiceEntry.totalVat == 0) { // this is an exception**
                invoiceMap.vatCategoryId = 4 // vatNull
                invoiceMap.totalAmountWithoutVat = invoiceEntry.total
            }
            else {
                invoiceMap.vatCategoryId =  getvatCategoryId(vatLow, vatHigh)
            }

        }
        else if(invoiceEntry.is_excel_import == 0) { //quickEntry
            invoiceMap.vatCategoryId =  invoiceEntry.vat_id
        }

        if (invoiceEntry.totalVat != 0){
            invoiceMap.totalVat = invoiceEntry.vatLow + invoiceEntry.vatHigh
        } else {
            invoiceMap.totalVat = invoiceEntry.totalVat
        }

        invoiceMap.gl = glArr.gl
        invoiceMap.id = invoiceEntry.id
        invoiceMap.pdfLink = invoiceEntry.pdfLink
        invoiceMap.invoiceNumber = invoiceEntry.invoiceNumber
        saveInvoiceExpenseAutomatic(invoiceMap, userid, businessCompany)
    }

    def createIncomeInvoiceMap(def invoiceEntry, def userId) {

        Map invoiceMap = ["customerName"         : '', "description": '',
                          "date"                 : '', "budgetName": '', "id": '',
                          "totalAmountWithVat"   : '', "invoiceNumber": '',
                          "totalAmountWithoutVat": '',
                          "vatCategoryId"        : '',
                          "totalVat"             : '',
                          "gl"                   : '',
                          "pdfLink"              : '',
                          "label"                : '',
                          "awsDetails"           : '',
                          "subCategory"          : ''
        ]

        def glArr = hasCustomer(invoiceEntry.budgetName)
        def vatLow = invoiceEntry.vatLow
        def vatHigh = invoiceEntry.vatHigh
        invoiceMap.customerName = invoiceEntry.vendorOrCustomer
        invoiceMap.description = invoiceEntry.description
        invoiceMap.budgetName = invoiceEntry.budgetName
        invoiceMap.date = invoiceEntry.invoiceDate
        invoiceMap.totalAmountWithVat = invoiceEntry.total
        invoiceMap.totalAmountWithoutVat = invoiceEntry.subtotal

        if(invoiceEntry.is_excel_import == 1) { // import file
            invoiceMap.vatCategoryId =  getvatCategoryId(vatLow, vatHigh)
        }
        else if(invoiceEntry.is_excel_import == 0) { //quickEntry
            invoiceMap.vatCategoryId =  invoiceEntry.vat_id
        }

        invoiceMap.totalVat = invoiceEntry.vatLow + invoiceEntry.vatHigh
        invoiceMap.gl = glArr.gl
        invoiceMap.id = invoiceEntry.id
        invoiceMap.pdfLink = invoiceEntry.pdfLink
        invoiceMap.invoiceNumber = invoiceEntry.invoiceNumber
        invoiceMap.label = invoiceEntry.label
        invoiceMap.awsDetails = invoiceEntry.awsDetails
        invoiceMap.subCategory = invoiceEntry.subCategory
        saveIncomeInvoice(invoiceMap, userId)
    }

    def createIncomeInvoiceMapAutomatic(def invoiceEntry, def userId, BusinessCompany businessCompany) {

        Map invoiceMap = ["customerName"         : '', "description": '',
                          "date"                 : '', "budgetName": '', "id": '',
                          "totalAmountWithVat"   : '', "invoiceNumber": '',
                          "totalAmountWithoutVat": '',
                          "vatCategoryId"        : '',
                          "totalVat"             : '',
                          "gl"                   : '',
                          "pdfLink"              : ''
        ]

        def glArr = hasCustomerAutomatic(invoiceEntry.budgetName, businessCompany)
        def vatLow = invoiceEntry.vatLow
        def vatHigh = invoiceEntry.vatHigh
        invoiceMap.customerName = invoiceEntry.vendorOrCustomer
        invoiceMap.description = invoiceEntry.description
        invoiceMap.budgetName = invoiceEntry.budgetName
        invoiceMap.date = invoiceEntry.invoiceDate
        invoiceMap.totalAmountWithVat = invoiceEntry.total
        invoiceMap.totalAmountWithoutVat = invoiceEntry.subtotal

        if(invoiceEntry.is_excel_import == 1) { // import file
            invoiceMap.vatCategoryId =  getvatCategoryId(vatLow, vatHigh)
        }
        else if(invoiceEntry.is_excel_import == 0) { //quickEntry
            invoiceMap.vatCategoryId =  invoiceEntry.vat_id
        }

        invoiceMap.totalVat = invoiceEntry.vatLow + invoiceEntry.vatHigh
        invoiceMap.gl = glArr.gl
        invoiceMap.id = invoiceEntry.id
        invoiceMap.pdfLink = invoiceEntry.pdfLink
        invoiceMap.invoiceNumber = invoiceEntry.invoiceNumber
        saveIncomeInvoiceAutomatic(invoiceMap, userId, businessCompany)
    }

    def saveIncomeInvoice(def invoiceMap, def userid) {

        def InvoiceNo = new CoreParamsHelper().getNextGeneratedNumber('invoiceIncome')
        def date = invoiceMap.date
        def dateArr = date.split("-")
        def bookingPeriod = Integer.parseInt(dateArr[1])
        bookingPeriod = bookingPeriod.toString();
        def bookingYear = dateArr[2]
        def paymentRef
        if (invoiceMap.invoiceNumber) {
            paymentRef = invoiceMap.invoiceNumber
        } else {
            paymentRef = invoiceMap.customerName + invoiceMap.date
        }


        def budgetArr = hasCustomer(invoiceMap.budgetName)
        def budgetCustomerId = budgetArr.id


        def budgetItemIncomeeSql = """SELECT id FROM budget_item_income
                                    WHERE booking_period_end_month = '${bookingPeriod}'
                                    and booking_period_end_year = '${bookingYear}'
                                    AND booking_period_start_month = '${bookingPeriod}'
                                    AND booking_period_start_year = '${bookingYear}'
                                    and customer_id ='${budgetCustomerId}'"""

        def budgetItemIncomeArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase(budgetItemIncomeeSql)
        def budgetItemId
        if (budgetItemIncomeArr.size() > 0) {
            budgetItemId = budgetItemIncomeArr[0][0]
        }

        def customerArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase(
                """SELECT id from customer_master WHERE customer_name ='${invoiceMap.customerName}'""");
        def customerId = customerArr[0][0]

        def paymenTermSql = """SELECT payment_term_id FROM company_setup where id = '1'"""
        def paymentTermArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase(paymenTermSql)
        def paymentTermId
        if (paymentTermArr.size() > 0) {
            paymentTermId = paymentTermArr[0][0]
        }

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date tempTransDate = df.parse(date);

        Calendar tempDueDate = Calendar.getInstance();
        tempDueDate.setTime(tempTransDate);
        tempDueDate.add(Calendar.MONTH, 1);

        String dueDate = tempDueDate.format("yyyy-MM-dd hh:mm:ss")
        String TransDate = tempTransDate.format("yyyy-MM-dd hh:mm:ss")


        def totalAmountWithoutVat = invoiceMap.totalAmountWithoutVat
        def checkVatAmount = invoiceMap.totalAmountWithVat - invoiceMap.totalAmountWithoutVat

        def totalVat = invoiceMap.totalVat
        def vatAmount
        if(totalVat > 0) {
            vatAmount = totalVat
        } else {
            vatAmount = checkVatAmount
        }


        Map InvoiceIncome = [
                bookingPeriod     : bookingPeriod,
                bookingYear       : bookingYear,
                budgetItemIncomeId: budgetItemId,
                comments          : "test",
                currencyCode      : "EURO",
                customerAccountNo : "",
                customerId        : customerId,
                dueDate           : dueDate,
                invoiceNo         : InvoiceNo,
                isReverse         : 0,
                paidStatus        : 0,
                paidAmount        : 0.00,
                reverseInvoiceId  : 0,
                status            : 1,
                termsId           : paymentTermId,
                totalGlAmount     : totalAmountWithoutVat,
                totalVat          : vatAmount.round(2),
                transDate         : TransDate,
                paymentRef        : paymentRef,
                budgetCustomerId  : budgetCustomerId,
                userId            : userid,
                pdfUrl            : invoiceMap.pdfLink,
                label             : invoiceMap.label,
                awsDetails        : invoiceMap.awsDetails,
                subCategory       : invoiceMap.subCategory
        ]

        def tableNameInvoiceIncome = "InvoiceIncome"
        def invoiceId = new BudgetViewDatabaseService().insert(InvoiceIncome, tableNameInvoiceIncome)
        saveInvoiceIncomeDetails(invoiceMap, invoiceId, userid)
    }

    def saveIncomeInvoiceAutomatic(def invoiceMap, def userid, BusinessCompany businessCompany) {

        def InvoiceNo = new CoreParamsHelper().getNextGeneratedNumberAutomatic('invoiceIncome', businessCompany)
        def date = invoiceMap.date
        def dateArr = date.split("-")
        def bookingPeriod = Integer.parseInt(dateArr[1])
        bookingPeriod = bookingPeriod.toString()
        def bookingYear = dateArr[2]
        def paymentRef
        if (invoiceMap.invoiceNumber) {
            paymentRef = invoiceMap.invoiceNumber
        } else {
            paymentRef = invoiceMap.customerName + invoiceMap.date
        }


        def budgetArr = hasCustomerAutomatic(invoiceMap.budgetName, businessCompany)
        def budgetCustomerId = budgetArr.id


        def budgetItemIncomeeSql = """SELECT id FROM budget_item_income
                                    WHERE booking_period_end_month = '${bookingPeriod}'
                                    and booking_period_end_year = '${bookingYear}'
                                    AND booking_period_start_month = '${bookingPeriod}'
                                    AND booking_period_start_year = '${bookingYear}'
                                    and customer_id ='${budgetCustomerId}'"""

        def budgetItemIncomeArr = new BudgetViewDatabaseService()
                .executeQueryPerDatabase(budgetItemIncomeeSql, businessCompany)
        def budgetItemId
        if (budgetItemIncomeArr.size() > 0) {
            budgetItemId = budgetItemIncomeArr[0][0]
        }

        def customerArr = new BudgetViewDatabaseService().executeQueryPerDatabase(
                """SELECT id from customer_master WHERE customer_name ='${invoiceMap.customerName}'""", businessCompany)
        def customerId = customerArr[0][0]

        def paymenTermSql = """SELECT payment_term_id FROM company_setup where id = '1'"""
        def paymentTermArr = new BudgetViewDatabaseService().executeQueryPerDatabase(paymenTermSql, businessCompany)
        def paymentTermId
        if (paymentTermArr.size() > 0) {
            paymentTermId = paymentTermArr[0][0]
        }

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy")
        Date tempTransDate = df.parse(date)

        Calendar tempDueDate = Calendar.getInstance()
        tempDueDate.setTime(tempTransDate)
        tempDueDate.add(Calendar.MONTH, 1)

        String dueDate = tempDueDate.format("yyyy-MM-dd hh:mm:ss")
        String TransDate = tempTransDate.format("yyyy-MM-dd hh:mm:ss")


        def totalAmountWithoutVat = invoiceMap.totalAmountWithoutVat
        def checkVatAmount = invoiceMap.totalAmountWithVat - invoiceMap.totalAmountWithoutVat

        def totalVat = invoiceMap.totalVat
        def vatAmount
        if(totalVat > 0) {
            vatAmount = totalVat
        } else {
            vatAmount = checkVatAmount
        }


        Map InvoiceIncome = [
                bookingPeriod     : bookingPeriod,
                bookingYear       : bookingYear,
                budgetItemIncomeId: budgetItemId,
                comments          : "test",
                currencyCode      : "EURO",
                customerAccountNo : "",
                customerId        : customerId,
                dueDate           : dueDate,
                invoiceNo         : InvoiceNo,
                isReverse         : 0,
                paidStatus        : 0,
                paidAmount        : 0.00,
                reverseInvoiceId  : 0,
                status            : 1,
                termsId           : paymentTermId,
                totalGlAmount     : totalAmountWithoutVat,
                totalVat          : vatAmount,
                transDate         : TransDate,
                paymentRef        : paymentRef,
                budgetCustomerId  : budgetCustomerId,
                userId            : userid,
                pdfUrl            : invoiceMap.pdfLink
        ]

        def tableNameInvoiceIncome = "InvoiceIncome"
        def invoiceId = new BudgetViewDatabaseService().insertAutomatic(InvoiceIncome, tableNameInvoiceIncome, businessCompany)
        saveInvoiceIncomeDetailsAutomatic(invoiceMap, invoiceId, userid, businessCompany)
    }

    def saveInvoiceIncomeDetails(def invoiceMap, def invoiceId, def userid) {

        def vatRate
        def date = invoiceMap.date
        def dateArr = date.split("-")
        def bookingPeriod = dateArr[1]
        def bookingYear = dateArr[2]
        def processString = com.bv.constants.Process.INVOICE_INCOME
        def strRecenciliationCode = "" + invoiceId + "#1"
        def vatRateArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase("SELECT rate FROM vat_category WHERE id = '${invoiceMap.vatCategoryId}'")
        if (vatRateArr.size() > 0) {
            vatRate = vatRateArr[0][0]
        }
        def totalAmountWithoutVat = invoiceMap.totalAmountWithoutVat

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date tempTransDate = df.parse(date);

        Calendar tempDueDate = Calendar.getInstance();
        tempDueDate.setTime(tempTransDate);
        tempDueDate.add(Calendar.MONTH, 1);

        String dueDate = tempDueDate.format("yyyy-MM-dd hh:mm:ss")
        String transDate = tempTransDate.format("yyyy-MM-dd hh:mm:ss")

        def customerArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase(
                """SELECT id from customer_master WHERE customer_name ='${invoiceMap.customerName}'""");
        def customerId = customerArr[0][0]
        def bookingDate = new ApplicationUtil().convertDateFromMonthAndYear(bookingPeriod, bookingYear)

        Map invoiceLineDetails = [
                accountCode          : invoiceMap.gl,
                discountAmount       : 0,
                invoiceId            : invoiceId,
                note                 : invoiceMap.description,
                productCode          : 1,
                quantity             : 1,
                totalAmountWithVat   : invoiceMap.totalAmountWithVat,
                totalAmountWithoutVat: totalAmountWithoutVat,
                unitPrice            : totalAmountWithoutVat,
                vatCategoryId        : invoiceMap.vatCategoryId ? invoiceMap.vatCategoryId : "",
                vatRate              : vatRate
        ]

        def tableNameInvoiceIncomeDetails = "InvoiceIncomeDetails"
        def detailsInsertedId = new BudgetViewDatabaseService().insert(invoiceLineDetails, tableNameInvoiceIncomeDetails)

        Map trnMas = [
                accountCode       : invoiceMap.gl,
                amount            : -totalAmountWithoutVat,
                transDate         : transDate,
                transType         : 1,
                invoiceNo         : invoiceId,
                bookingPeriod     : bookingPeriod,
                bookingYear       : bookingYear,
                userId            : userid,
                createDate        : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),
                process           : processString,
                recenciliationCode: strRecenciliationCode,
                customerId        : customerId,
                vendorId          : 0,
                bookingDate       : bookingDate
        ]

        def tableNameTransMaster = "TransMaster" //BDR-4
        new BudgetViewDatabaseService().insert(trnMas, tableNameTransMaster)

        def comBankInstance = new CoreParamsHelper().getCompanyBankAccountByGlAccount(invoiceMap.gl)

        if (comBankInstance.size()) {
            Map comBankTrans = [
                    amount         : totalAmountWithoutVat,
                    companyBankCode: comBankInstance,
                    invoiceNo      : invoiceId,
                    personCode     : customerId,
                    transDate      : transDate,
                    transType      : 1,
                    bookingPeriod  : bookingPeriod,
                    bookingYear    : bookingYear
            ]

            def tableNameCompanyBankTrans = "CompanyBankTrans"
            new BudgetViewDatabaseService().insert(comBankTrans, tableNameCompanyBankTrans)
        }

        def checkVatAmount = invoiceMap.totalAmountWithVat - invoiceMap.totalAmountWithoutVat

        if (vatRate > 0 || checkVatAmount > 0) {
            def vatGLAccountInfo = new CoreParamsHelper().getSpacificVatGLAccount(invoiceMap.vatCategoryId)
            def vatGLAcc = ""
            if (vatGLAccountInfo.size()) {
                vatGLAcc = vatGLAccountInfo[2];
            }

            def vatAmount
            if (vatRate <= 0) {
                vatAmount = checkVatAmount
            } else {
                vatAmount = invoiceMap.totalVat
            }

            if (vatGLAcc) {
                Double tempVatAmount = -vatAmount
                Map trnVatMas = [
                        accountCode       : vatGLAcc,
                        amount            : tempVatAmount.round(2),
                        transDate         : transDate,
                        transType         : 1,
                        invoiceNo         : invoiceId,
                        bookingPeriod     : bookingPeriod,
                        bookingYear       : bookingYear,
                        userId            : userid,
                        createDate        : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),
                        process           : processString,
                        recenciliationCode: strRecenciliationCode,
                        customerId        : customerId,
                        vendorId          : 0,
                        bookingDate       : bookingDate
                ]

                def tableNametrnVatMas = "TransMaster" //BDR-4
                new BudgetViewDatabaseService().insert(trnVatMas, tableNametrnVatMas)
            }

            def comVatBankInstance = new CoreParamsHelper().getCompanyBankAccountByGlAccount(vatGLAcc)

            if (comVatBankInstance.size()) {

                Map comVatBankTrans = [
                        amount         : invoiceMap.totalVat,
                        companyBankCode: comVatBankInstance,
                        invoiceNo      : invoiceId,
                        personCode     : customerId,
                        transDate      : transDate,
                        transType      : 1,
                        bookingPeriod  : bookingPeriod,
                        bookingYear    : bookingYear
                ]

                def tableNamecomVatBankTrans = "CompanyBankTrans"
                new BudgetViewDatabaseService().insert(comVatBankTrans, tableNamecomVatBankTrans)

            }
        }

        def creditorCreditGlSetupInfo = new CoreParamsHelper().getDebitCreditGlSetupInfo()
        Double totalAmount = invoiceMap.totalAmountWithVat
        totalAmount = totalAmount.round(2)
        //Trans Master
        Map trnMas2 = [
                accountCode       : creditorCreditGlSetupInfo[2],
                amount            : totalAmount,
                transDate         : transDate,
                transType         : 1,
                invoiceNo         : invoiceId,
                bookingPeriod     : bookingPeriod,
                bookingYear       : bookingYear,
                userId            : userid,
                createDate        : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),
                process           : processString,
                recenciliationCode: strRecenciliationCode,
                customerId        : customerId,
                vendorId          : 0,
                bookingDate       : bookingDate
        ]

        def tableNametrnMas2 = "TransMaster" //BDR-4
        new BudgetViewDatabaseService().insert(trnMas2, tableNametrnMas2)
        if (invoiceMap.id != "" || invoiceMap.id != null) {
            deleteImportinvoice(invoiceMap.id)
        }
    }

    def saveInvoiceIncomeDetailsAutomatic(def invoiceMap, def invoiceId, def userid, BusinessCompany businessCompany) {

        def vatRate
        def date = invoiceMap.date
        def dateArr = date.split("-")
        def bookingPeriod = dateArr[1]
        def bookingYear = dateArr[2]
        def processString = com.bv.constants.Process.INVOICE_INCOME
        def strRecenciliationCode = "" + invoiceId + "#1"
        def vatRateArr = new BudgetViewDatabaseService()
                .executeQueryPerDatabase("SELECT rate FROM vat_category WHERE id = '${invoiceMap.vatCategoryId}'", businessCompany)
        if (vatRateArr.size() > 0) {
            vatRate = vatRateArr[0][0]
        }
        def totalAmountWithoutVat = invoiceMap.totalAmountWithoutVat

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy")
        Date tempTransDate = df.parse(date)

        Calendar tempDueDate = Calendar.getInstance()
        tempDueDate.setTime(tempTransDate)
        tempDueDate.add(Calendar.MONTH, 1)

        String dueDate = tempDueDate.format("yyyy-MM-dd hh:mm:ss")
        String transDate = tempTransDate.format("yyyy-MM-dd hh:mm:ss")

        def customerArr = new BudgetViewDatabaseService()
                .executeQueryPerDatabase(
                        """SELECT id from customer_master WHERE customer_name ='${invoiceMap.customerName}'""", businessCompany)
        def customerId = customerArr[0][0]
        def bookingDate = new ApplicationUtil().convertDateFromMonthAndYear(bookingPeriod, bookingYear)

        Map invoiceLineDetails = [
                accountCode          : invoiceMap.gl,
                discountAmount       : 0,
                invoiceId            : invoiceId,
                note                 : invoiceMap.description,
                productCode          : 1,
                quantity             : 1,
                totalAmountWithVat   : invoiceMap.totalAmountWithVat,
                totalAmountWithoutVat: totalAmountWithoutVat,
                unitPrice            : totalAmountWithoutVat,
                vatCategoryId        : invoiceMap.vatCategoryId ? invoiceMap.vatCategoryId : "",
                vatRate              : vatRate
        ]

        def tableNameInvoiceIncomeDetails = "InvoiceIncomeDetails"
        def detailsInsertedId = new BudgetViewDatabaseService()
                .insertAutomatic(invoiceLineDetails, tableNameInvoiceIncomeDetails, businessCompany)

        Map trnMas = [
                accountCode       : invoiceMap.gl,
                amount            : -totalAmountWithoutVat,
                transDate         : transDate,
                transType         : 1,
                invoiceNo         : invoiceId,
                bookingPeriod     : bookingPeriod,
                bookingYear       : bookingYear,
                userId            : userid,
                createDate        : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),
                process           : processString,
                recenciliationCode: strRecenciliationCode,
                customerId        : customerId,
                vendorId          : 0,
                bookingDate       : bookingDate
        ]

        def tableNameTransMaster = "TransMaster" //BDR-4
        new BudgetViewDatabaseService().insertAutomatic(trnMas, tableNameTransMaster, businessCompany)

        def comBankInstance = new CoreParamsHelper().getCompanyBankAccountByGlAccountAutomatic(invoiceMap.gl, businessCompany)

        if (comBankInstance.size()) {
            Map comBankTrans = [
                    amount         : totalAmountWithoutVat,
                    companyBankCode: comBankInstance,
                    invoiceNo      : invoiceId,
                    personCode     : customerId,
                    transDate      : transDate,
                    transType      : 1,
                    bookingPeriod  : bookingPeriod,
                    bookingYear    : bookingYear
            ]

            def tableNameCompanyBankTrans = "CompanyBankTrans"
            new BudgetViewDatabaseService().insertAutomatic(comBankTrans, tableNameCompanyBankTrans, businessCompany)
        }

        def checkVatAmount = invoiceMap.totalAmountWithVat - invoiceMap.totalAmountWithoutVat

        if (vatRate > 0 || checkVatAmount > 0) {
            def vatGLAccountInfo = new CoreParamsHelper().getSpacificVatGLAccountAutomatic(invoiceMap.vatCategoryId, businessCompany)
            def vatGLAcc = ""
            if (vatGLAccountInfo.size()) {
                vatGLAcc = vatGLAccountInfo[2]
            }

            def vatAmount
            if (vatRate <= 0) {
                vatAmount = checkVatAmount
            } else {
                vatAmount = invoiceMap.totalVat
            }

            if (vatGLAcc) {
                Double tempVatAmount = -vatAmount
                Map trnVatMas = [
                        accountCode       : vatGLAcc,
                        amount            : tempVatAmount,
                        transDate         : transDate,
                        transType         : 1,
                        invoiceNo         : invoiceId,
                        bookingPeriod     : bookingPeriod,
                        bookingYear       : bookingYear,
                        userId            : userid,
                        createDate        : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),
                        process           : processString,
                        recenciliationCode: strRecenciliationCode,
                        customerId        : customerId,
                        vendorId          : 0,
                        bookingDate       : bookingDate
                ]

                def tableNametrnVatMas = "TransMaster" //BDR-4
                new BudgetViewDatabaseService().insertAutomatic(trnVatMas, tableNametrnVatMas, businessCompany)
            }

            def comVatBankInstance = new CoreParamsHelper().getCompanyBankAccountByGlAccountAutomatic(vatGLAcc, businessCompany)

            if (comVatBankInstance.size()) {

                Map comVatBankTrans = [
                        amount         : invoiceMap.totalVat,
                        companyBankCode: comVatBankInstance,
                        invoiceNo      : invoiceId,
                        personCode     : customerId,
                        transDate      : transDate,
                        transType      : 1,
                        bookingPeriod  : bookingPeriod,
                        bookingYear    : bookingYear
                ]

                def tableNamecomVatBankTrans = "CompanyBankTrans"
                new BudgetViewDatabaseService().insertAutomatic(comVatBankTrans, tableNamecomVatBankTrans, businessCompany)

            }
        }

        def creditorCreditGlSetupInfo = new CoreParamsHelper().getDebitCreditGlSetupInfoAutomatic(businessCompany)
        Double totalAmount = invoiceMap.totalAmountWithVat
        totalAmount = totalAmount.round(2)
        //Trans Master
        Map trnMas2 = [
                accountCode       : creditorCreditGlSetupInfo[2],
                amount            : totalAmount,
                transDate         : transDate,
                transType         : 1,
                invoiceNo         : invoiceId,
                bookingPeriod     : bookingPeriod,
                bookingYear       : bookingYear,
                userId            : userid,
                createDate        : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),
                process           : processString,
                recenciliationCode: strRecenciliationCode,
                customerId        : customerId,
                vendorId          : 0,
                bookingDate       : bookingDate
        ]

        def tableNametrnMas2 = "TransMaster" //BDR-4
        new BudgetViewDatabaseService().insertAutomatic(trnMas2, tableNametrnMas2, businessCompany)
        if (invoiceMap.id != "" || invoiceMap.id != null) {
            deleteImportinvoiceAutomatic(invoiceMap.id, businessCompany)
        }
    }

    def saveInvoiceExpense(def invoiceMap, def userid) {

        def InvoiceNo = new CoreParamsHelper().getNextGeneratedNumber('invoiceExpense')
        def date = invoiceMap.date
        def dateArr = date.split("-")
        def bookingPeriod = Integer.parseInt(dateArr[1])
        bookingPeriod = bookingPeriod.toString();
        def bookingYear = dateArr[2]
//        def paymentRef = invoiceMap.vendorName + invoiceMap.date
        def budgetArr = hasVendor(invoiceMap.budgetName)
        def budgetVendorId = budgetArr.id
        def paymentRef
        if (invoiceMap.invoiceNumber) {
            paymentRef = invoiceMap.invoiceNumber
        } else {
            paymentRef = invoiceMap.vendorName + invoiceMap.date
        }

        def budgetItemExpenseSql = """SELECT id FROM budget_item_expense
                                    WHERE booking_period_end_month = '${
            bookingPeriod
        }' and booking_period_end_year = '${bookingYear}'
                                    AND booking_period_start_month = '${
            bookingPeriod
        }' AND booking_period_start_year = '${bookingYear}'
                                    and vendor_id ='${budgetVendorId}'"""

        def budgetItemExpenseArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase(budgetItemExpenseSql)
        def budgetItemId
        if (budgetItemExpenseArr.size() > 0) {
            budgetItemId = budgetItemExpenseArr[0][0]
        }


        def vendorArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase(
                """SELECT id from vendor_master WHERE vendor_name ='${invoiceMap.vendorName}'""");
        def vendorId = vendorArr[0][0]

        def paymenTermSql = """SELECT payment_term_id FROM company_setup where id = '1'"""
        def paymentTermArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase(paymenTermSql)
        def paymentTermId
        if (paymentTermArr.size() > 0) {
            paymentTermId = paymentTermArr[0][0]
        }

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date tempTransDate = df.parse(date);

        Calendar tempDueDate = Calendar.getInstance();
        tempDueDate.setTime(tempTransDate);
        tempDueDate.add(Calendar.MONTH, 1);

        String dueDate = tempDueDate.format("yyyy-MM-dd hh:mm:ss")
        String TransDate = tempTransDate.format("yyyy-MM-dd hh:mm:ss")


        def totalAmountWithoutVat = invoiceMap.totalAmountWithoutVat

        def checkVatAmount = invoiceMap.totalAmountWithVat - invoiceMap.totalAmountWithoutVat

        def totalVat = invoiceMap.totalVat
        def vatAmount
        if(totalVat > 0) {
            vatAmount = totalVat
        } else {
            vatAmount = checkVatAmount
        }

        Map invoiceHead = [
                bookingPeriod      : bookingPeriod,
                bookingYear        : bookingYear,
                budgetItemExpenseId: budgetItemId,
                comments           : "test",
                currencyCode       : "EURO",
                vendorAccountNo    : "",
                vendorId           : vendorId,
                dueDate            : dueDate,
                invoiceNo          : InvoiceNo,
                isReverse          : 0,
                paidStatus         : 0,
                paidAmount         : 0.00,
                reverseInvoiceId   : 0,
                status             : 1,
                termsId            : paymentTermId,
                totalGlAmount      : totalAmountWithoutVat,
                totalVat           : vatAmount.round(2),
                transDate          : TransDate,
                paymentRef         : paymentRef,
                budgetVendorId     : budgetVendorId,
                userId             : userid,
                isBookReceive      : 0,
                pdfUrl             : invoiceMap.pdfLink,
                label              : invoiceMap.label,
                awsDetails         : invoiceMap.awsDetails,
                subCategory        : invoiceMap.subCategory
        ]

        def tableNameinvoiceHead = "InvoiceExpense"
        def insertedId = new BudgetViewDatabaseService().insert(invoiceHead, tableNameinvoiceHead)

        saveInvoiceExpenseDetails(invoiceMap, insertedId, userid)
    }

    def saveInvoiceExpenseAutomatic(def invoiceMap, def userid, BusinessCompany businessCompany) {

        def InvoiceNo = new CoreParamsHelper().getNextGeneratedNumberAutomatic('invoiceExpense', businessCompany)
        def date = invoiceMap.date
        def dateArr = date.split("-")
        def bookingPeriod = Integer.parseInt(dateArr[1])
        bookingPeriod = bookingPeriod.toString()
        def bookingYear = dateArr[2]
        def budgetArr = hasVendorAutomatic(invoiceMap.budgetName, businessCompany)
        def budgetVendorId = budgetArr.id
        def paymentRef
        if (invoiceMap.invoiceNumber) {
            paymentRef = invoiceMap.invoiceNumber
        } else {
            paymentRef = invoiceMap.vendorName + invoiceMap.date
        }

        def budgetItemExpenseSql = """SELECT id
                                        FROM budget_item_expense
                                        WHERE booking_period_end_month = '${bookingPeriod}'
                                          AND booking_period_end_year = '${bookingYear}'
                                          AND booking_period_start_month = '${bookingPeriod}'
                                          AND booking_period_start_year = '${bookingYear}'
                                          AND vendor_id ='${budgetVendorId}'"""


        def budgetItemExpenseArr = new BudgetViewDatabaseService().executeQueryPerDatabase(budgetItemExpenseSql, businessCompany)
        def budgetItemId
        if (budgetItemExpenseArr.size() > 0) {
            budgetItemId = budgetItemExpenseArr[0][0]
        }

        def vendorArr = new BudgetViewDatabaseService().executeQueryPerDatabase(
                """SELECT id from vendor_master WHERE vendor_name ='${invoiceMap.vendorName}'""", businessCompany)

        def vendorId = vendorArr[0][0]

        def paymenTermSql = """SELECT payment_term_id FROM company_setup where id = '1'"""
        def paymentTermArr = new BudgetViewDatabaseService().executeQueryPerDatabase(paymenTermSql, businessCompany)
        def paymentTermId
        if (paymentTermArr.size() > 0) {
            paymentTermId = paymentTermArr[0][0]
        }

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy")
        Date tempTransDate = df.parse(date)

        Calendar tempDueDate = Calendar.getInstance()
        tempDueDate.setTime(tempTransDate)
        tempDueDate.add(Calendar.MONTH, 1)

        String dueDate = tempDueDate.format("yyyy-MM-dd hh:mm:ss")
        String TransDate = tempTransDate.format("yyyy-MM-dd hh:mm:ss")


        def totalAmountWithoutVat = invoiceMap.totalAmountWithoutVat

        def checkVatAmount = invoiceMap.totalAmountWithVat - invoiceMap.totalAmountWithoutVat

        def totalVat = invoiceMap.totalVat
        def vatAmount
        if(totalVat > 0) {
            vatAmount = totalVat
        } else {
            vatAmount = checkVatAmount
        }

        Map invoiceHead = [
                bookingPeriod      : bookingPeriod,
                bookingYear        : bookingYear,
                budgetItemExpenseId: budgetItemId,
                comments           : "test",
                currencyCode       : "EURO",
                vendorAccountNo    : "",
                vendorId           : vendorId,
                dueDate            : dueDate,
                invoiceNo          : InvoiceNo,
                isReverse          : 0,
                paidStatus         : 0,
                paidAmount         : 0.00,
                reverseInvoiceId   : 0,
                status             : 1,
                termsId            : paymentTermId,
                totalGlAmount      : totalAmountWithoutVat,
                totalVat           : vatAmount,
                transDate          : TransDate,
                paymentRef         : paymentRef,
                budgetVendorId     : budgetVendorId,
                userId             : userid,
                isBookReceive      : 0,
                pdfUrl             : invoiceMap.pdfLink
        ]

        def tableNameinvoiceHead = "InvoiceExpense"
        def insertedId = new BudgetViewDatabaseService().insertAutomatic(invoiceHead, tableNameinvoiceHead, businessCompany)

        saveInvoiceExpenseDetailsAutomatic(invoiceMap, insertedId, userid, businessCompany)
    }

    def saveInvoiceExpenseDetails(def invoiceMap, def invoiceId, def userid) {
        def vendorShopId = 0

        def vatRateArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase("SELECT rate FROM vat_category WHERE id = '${invoiceMap.vatCategoryId}'")
        def vatRate
        if (vatRateArr.size() > 0) {
            vatRate = vatRateArr[0][0]
        }

        def transType = 2
        def date = invoiceMap.date
        def dateArr = date.split("-")
        def bookingPeriod = dateArr[1]
        def bookingYear = dateArr[2]
        def processString = com.bv.constants.Process.INVOICE_EXPENSE
        def strRecenciliationCode = "" + invoiceId + "#2"//BDR-77
/*        def vendorArr = hasVendor(invoiceMap.vendorName)
        def vendorId = vendorArr.id*/
        def vendorArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase(
                """SELECT id from vendor_master WHERE vendor_name ='${invoiceMap.vendorName}'""");
        def vendorId = vendorArr[0][0]
        def bookingDate = new ApplicationUtil().convertDateFromMonthAndYear(bookingPeriod, bookingYear)

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date tempTransDate = df.parse(date);

        Calendar tempDueDate = Calendar.getInstance();
        tempDueDate.setTime(tempTransDate);
        tempDueDate.add(Calendar.MONTH, 1);

        String dueDate = tempDueDate.format("yyyy-MM-dd hh:mm:ss")
        String TransDate = tempTransDate.format("yyyy-MM-dd hh:mm:ss")

        def totalAmountWithoutVat = invoiceMap.totalAmountWithoutVat

        // Expense invoice details

        Map invoiceLineDetails = [
                accountCode          : invoiceMap.gl,
                discountAmount       : 0,
                invoiceId            : invoiceId,
                note                 : invoiceMap.description,
                productCode          : 1,
                quantity             : 1,
                totalAmountWithVat   : invoiceMap.totalAmountWithVat,
                totalAmountWithoutVat: totalAmountWithoutVat,
                unitPrice            : totalAmountWithoutVat,
                vatCategoryId        : invoiceMap.vatCategoryId ? invoiceMap.vatCategoryId : "",
                vatRate              : vatRate,
                shopInfo             : vendorShopId
        ]

        def tableNameinvoiceLineDetails = "InvoiceExpenseDetails"
        def detailsInsertedId = new BudgetViewDatabaseService().insert(invoiceLineDetails, tableNameinvoiceLineDetails)

        //Trans Master
        Map trnMas = [
                accountCode       : invoiceMap.gl,
                amount            : totalAmountWithoutVat,
                transDate         : TransDate,
                transType         : transType,
                invoiceNo         : invoiceId,
                bookingPeriod     : bookingPeriod,
                bookingYear       : bookingYear,
                userId            : userid,
                createDate        : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),
                process           : processString,
                recenciliationCode: strRecenciliationCode,
                customerId        : 0,
                vendorId          : vendorId,
                bookingDate       : bookingDate
        ]

        def tableNametrnMas = "TransMaster" //BDR-4

        new BudgetViewDatabaseService().insert(trnMas, tableNametrnMas)

        def comBankInstance = new CoreParamsHelper().getCompanyBankAccountByGlAccount(invoiceMap.gl)

        if (comBankInstance.size()) {
            Map comBankTrans = [
                    amount         : totalAmountWithoutVat,
                    companyBankCode: comBankInstance,
                    invoiceNo      : invoiceId,
                    personCode     : vendorId,
                    transDate      : TransDate,
                    transType      : transType,
                    bookingPeriod  : bookingPeriod,
                    bookingYear    : bookingYear
            ]

            def tableNamecomBankTrans = "CompanyBankTrans"
            new BudgetViewDatabaseService().insert(comBankTrans, tableNamecomBankTrans)
        }

        def checkVatAmount = invoiceMap.totalAmountWithVat - invoiceMap.totalAmountWithoutVat
        if (vatRate > 0 || checkVatAmount > 0) {
            def vatGLAccountInfo = new CoreParamsHelper().getSpacificVatGLAccount(invoiceMap.vatCategoryId)
            def vatGLAcc = ""
            if (vatGLAccountInfo.size()) {
                vatGLAcc = vatGLAccountInfo[3];
            }

            def vatAmount
            if (vatRate <= 0) {
                vatAmount = checkVatAmount
            } else {
                vatAmount = invoiceMap.totalVat
            }

            if (vatGLAcc) {
                Map trnVatMas = [
                        accountCode       : vatGLAcc,
                        amount            : vatAmount.round(2),
                        transDate         : TransDate,
                        transType         : transType,
                        invoiceNo         : invoiceId,
                        bookingPeriod     : bookingPeriod,
                        bookingYear       : bookingYear,
                        userId            : userid,
                        createDate        : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),
                        process           : processString,
                        recenciliationCode: strRecenciliationCode,
                        customerId        : 0,
                        vendorId          : vendorId,
                        bookingDate       : bookingDate

                ]

                def tableNametrnVatMas = "TransMaster" //BDR-4
                new BudgetViewDatabaseService().insert(trnVatMas, tableNametrnVatMas)
            }

            def comVatBankInstance = new CoreParamsHelper().getCompanyBankAccountByGlAccount(vatGLAcc)

            if (comVatBankInstance.size()) {
                Map comVatBankTrans = [
                        amount         : vatAmount.round(2),
                        companyBankCode: comVatBankInstance,
                        invoiceNo      : invoiceId,
                        personCode     : vendorId,
                        transDate      : TransDate,
                        transType      : transType,
                        bookingPeriod  : bookingPeriod,
                        bookingYear    : bookingYear
                ]

                def tableNamecomVatBankTrans = "CompanyBankTrans"
                new BudgetViewDatabaseService().insert(comVatBankTrans, tableNamecomVatBankTrans)
            }
        }

        //////////////////////////Creditor Entry IN Master Table/////////////////////
        def creditorCreditGlSetupInfo = new CoreParamsHelper().getDebitCreditGlSetupInfo()
        //Trans Master
        Double totalAmount = -invoiceMap.totalAmountWithVat
        totalAmount = totalAmount.round(2)
        Map trnMas2 = [
                accountCode       : creditorCreditGlSetupInfo[1],
                amount            : totalAmount,
                transDate         : TransDate,
                transType         : transType,
                invoiceNo         : invoiceId,
                bookingPeriod     : bookingPeriod,
                bookingYear       : bookingYear,
                userId            : userid,
                createDate        : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),
                process           : processString,
                recenciliationCode: strRecenciliationCode,
                customerId        : 0,
                vendorId          : vendorId,
                bookingDate       : bookingDate
        ]

        def tableNametrnMas2 = "TransMaster" //BDR-4
        new BudgetViewDatabaseService().insert(trnMas2, tableNametrnMas2)
        if (invoiceMap.id != "" || invoiceMap.id != null) {
            deleteImportinvoice(invoiceMap.id)
        }
    }

    def saveInvoiceExpenseDetailsAutomatic(def invoiceMap, def invoiceId, def userid, BusinessCompany businessCompany) {
        def vendorShopId = 0

        def vatRateArr = new BudgetViewDatabaseService()
                .executeQueryPerDatabase("SELECT rate FROM vat_category WHERE id = '${invoiceMap.vatCategoryId}'",
                        businessCompany)
        def vatRate
        if (vatRateArr.size() > 0) {
            vatRate = vatRateArr[0][0]
        }

        def transType = 2
        def date = invoiceMap.date
        def dateArr = date.split("-")
        def bookingPeriod = dateArr[1]
        def bookingYear = dateArr[2]
        def processString = com.bv.constants.Process.INVOICE_EXPENSE
        def strRecenciliationCode = "" + invoiceId + "#2"//BDR-77

        def vendorArr = new BudgetViewDatabaseService()
                .executeQueryPerDatabase(
                        """SELECT id from vendor_master WHERE vendor_name ='${invoiceMap.vendorName}'""", businessCompany)
        def vendorId = vendorArr[0][0]
        def bookingDate = new ApplicationUtil().convertDateFromMonthAndYear(bookingPeriod, bookingYear)

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy")
        Date tempTransDate = df.parse(date)

        Calendar tempDueDate = Calendar.getInstance()
        tempDueDate.setTime(tempTransDate)
        tempDueDate.add(Calendar.MONTH, 1)

        String dueDate = tempDueDate.format("yyyy-MM-dd hh:mm:ss")
        String TransDate = tempTransDate.format("yyyy-MM-dd hh:mm:ss")

        def totalAmountWithoutVat = invoiceMap.totalAmountWithoutVat

        // Expense invoice details
        Map invoiceLineDetails = [
                accountCode          : invoiceMap.gl,
                discountAmount       : 0,
                invoiceId            : invoiceId,
                note                 : invoiceMap.description,
                productCode          : 1,
                quantity             : 1,
                totalAmountWithVat   : invoiceMap.totalAmountWithVat,
                totalAmountWithoutVat: totalAmountWithoutVat,
                unitPrice            : totalAmountWithoutVat,
                vatCategoryId        : invoiceMap.vatCategoryId ? invoiceMap.vatCategoryId : "",
                vatRate              : vatRate,
                shopInfo             : vendorShopId
        ]

        def tableNameinvoiceLineDetails = "InvoiceExpenseDetails"
        def detailsInsertedId = new BudgetViewDatabaseService()
                .insertAutomatic(invoiceLineDetails, tableNameinvoiceLineDetails, businessCompany)

        //Trans Master
        Map trnMas = [
                accountCode       : invoiceMap.gl,
                amount            : totalAmountWithoutVat,
                transDate         : TransDate,
                transType         : transType,
                invoiceNo         : invoiceId,
                bookingPeriod     : bookingPeriod,
                bookingYear       : bookingYear,
                userId            : userid,
                createDate        : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),
                process           : processString,
                recenciliationCode: strRecenciliationCode,
                customerId        : 0,
                vendorId          : vendorId,
                bookingDate       : bookingDate
        ]

        def tableNametrnMas = "TransMaster" //BDR-4
        new BudgetViewDatabaseService().insertAutomatic(trnMas, tableNametrnMas, businessCompany)

        def comBankInstance = new CoreParamsHelper().getCompanyBankAccountByGlAccountAutomatic(invoiceMap.gl, businessCompany)

        if (comBankInstance.size()) {
            Map comBankTrans = [
                    amount         : totalAmountWithoutVat,
                    companyBankCode: comBankInstance,
                    invoiceNo      : invoiceId,
                    personCode     : vendorId,
                    transDate      : TransDate,
                    transType      : transType,
                    bookingPeriod  : bookingPeriod,
                    bookingYear    : bookingYear
            ]

            def tableNamecomBankTrans = "CompanyBankTrans"
            new BudgetViewDatabaseService().insertAutomatic(comBankTrans, tableNamecomBankTrans, businessCompany)
        }

        def checkVatAmount = invoiceMap.totalAmountWithVat - invoiceMap.totalAmountWithoutVat
        if (vatRate > 0 || checkVatAmount > 0) {
            def vatGLAccountInfo = new CoreParamsHelper().getSpacificVatGLAccountAutomatic(invoiceMap.vatCategoryId, businessCompany)
            def vatGLAcc = ""
            if (vatGLAccountInfo.size()) {
                vatGLAcc = vatGLAccountInfo[3]
            }

            def vatAmount
            if (vatRate <= 0) {
                vatAmount = checkVatAmount
            } else {
                vatAmount = invoiceMap.totalVat
            }

            if (vatGLAcc) {
                Map trnVatMas = [
                        accountCode       : vatGLAcc,
                        amount            : vatAmount,
                        transDate         : TransDate,
                        transType         : transType,
                        invoiceNo         : invoiceId,
                        bookingPeriod     : bookingPeriod,
                        bookingYear       : bookingYear,
                        userId            : userid,
                        createDate        : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),
                        process           : processString,
                        recenciliationCode: strRecenciliationCode,
                        customerId        : 0,
                        vendorId          : vendorId,
                        bookingDate       : bookingDate

                ]

                def tableNametrnVatMas = "TransMaster" //BDR-4
                new BudgetViewDatabaseService().insertAutomatic(trnVatMas, tableNametrnVatMas, businessCompany)
            }

            def comVatBankInstance = new CoreParamsHelper().getCompanyBankAccountByGlAccountAutomatic(vatGLAcc, businessCompany)

            if (comVatBankInstance.size()) {
                Map comVatBankTrans = [
                        amount         : vatAmount,
                        companyBankCode: comVatBankInstance,
                        invoiceNo      : invoiceId,
                        personCode     : vendorId,
                        transDate      : TransDate,
                        transType      : transType,
                        bookingPeriod  : bookingPeriod,
                        bookingYear    : bookingYear
                ]

                def tableNamecomVatBankTrans = "CompanyBankTrans"
                new BudgetViewDatabaseService().insertAutomatic(comVatBankTrans, tableNamecomVatBankTrans, businessCompany)
            }
        }

        //////////////////////////Creditor Entry IN Master Table/////////////////////
        def creditorCreditGlSetupInfo = new CoreParamsHelper().getDebitCreditGlSetupInfoAutomatic(businessCompany)
        //Trans Master
        Double totalAmount = -invoiceMap.totalAmountWithVat
        totalAmount = totalAmount.round(2)
        Map trnMas2 = [
                accountCode       : creditorCreditGlSetupInfo[1],
                amount            : totalAmount,
                transDate         : TransDate,
                transType         : transType,
                invoiceNo         : invoiceId,
                bookingPeriod     : bookingPeriod,
                bookingYear       : bookingYear,
                userId            : userid,
                createDate        : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),
                process           : processString,
                recenciliationCode: strRecenciliationCode,
                customerId        : 0,
                vendorId          : vendorId,
                bookingDate       : bookingDate
        ]

        def tableNametrnMas2 = "TransMaster" //BDR-4
        new BudgetViewDatabaseService().insertAutomatic(trnMas2, tableNametrnMas2, businessCompany)
        if (invoiceMap.id != "" || invoiceMap.id != null) {
            deleteImportinvoiceAutomatic(invoiceMap.id, businessCompany)
        }
    }

    def getInvoiceInfo(def ImportInvoiceId) {
        def sql = """SELECT budget_name,invoice_date,vendor_or_customer,total,
                    subtotal, vat_low, vat_high, vat_declaration, description, abroad,invoice_number,
                    pdf_link_url, iban FROM import_invoice WHERE id = '${ImportInvoiceId}'"""
        def infoArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase(sql)

        String date_s = infoArr[0][1].toString();
        SimpleDateFormat dt = new SimpleDateFormat("yyyyy-MM-dd hh:mm:ss");
        Date date = dt.parse(date_s);
        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");


        Map invoiceInfo = [
                budgetName      : infoArr[0][0],
                invoiceDate     : dt1.format(date),
//                invoiceDate     : infoArr[0][1],
                vendorOrCustomer: infoArr[0][2],
                total           : infoArr[0][3],
                subtotal        : infoArr[0][4],
                vatLow          : infoArr[0][5],
                vatHigh         : infoArr[0][6],
                vatDeclaration  : infoArr[0][7],
                description     : infoArr[0][8],
                abroad          : infoArr[0][9],
                invoiceNumber   : infoArr[0][10],
                pdfLinkUrl      : infoArr[0][11],
                iban            : infoArr[0][12]
        ]

        return invoiceInfo
    }

    def deleteImportinvoice(def importId) {
        def sql = """DELETE FROM import_invoice WHERE id ='${importId}'"""
        new BudgetViewDatabaseService().executeUpdate(sql)
    }

    def deleteImportinvoiceAutomatic(def importId, BusinessCompany businessCompany) {
        def sql = """DELETE FROM import_invoice WHERE id ='${importId}'"""
        new BudgetViewDatabaseService().executeUpdateAutomatic(sql, businessCompany)
    }

    def Map checkBudgetFor(def budgetName) {
        Map map = ["result": false, "gl": '', "id": '', "venOrCus": '']

        def incomeBudget = new BudgetViewDatabaseService().executeQueryWithoutCamelCase("""
            SELECT cm.default_gl_account,cm.id FROM customer_master cm
            INNER JOIN budget_item_income bi on cm.id = bi.customer_id
            where cm.customer_name='${budgetName}'""")

        if (incomeBudget.size() > 0) {
            def customerSql = """SELECT default_gl_account,id from customer_master WHERE customer_name ='${
                budgetName
            }'"""
            def customerArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase(customerSql);
            if (customerArr.size() > 0) {
                map.result = true
                map.gl = customerArr[0][0]
                map.id = customerArr[0][1]
                map.venOrCus = 'CUS'
                return map;
            }
        }

        def expenseBudget = new BudgetViewDatabaseService().executeQueryWithoutCamelCase("""
           SELECT  vm.default_gl_account,vm.id  FROM vendor_master vm
            INNER JOIN budget_item_expense bex on vm.id = bex.vendor_id
            where vm.vendor_name='${budgetName}'""")

        if (expenseBudget.size() > 0) {
            def vendorSql = """SELECT default_gl_account,id from vendor_master WHERE vendor_name ='${budgetName}'"""
            def vendorArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase(vendorSql);
            if (vendorArr.size() > 0) {
                map.result = true
                map.gl = vendorArr[0][0]
                map.id = vendorArr[0][1]
                map.venOrCus = 'VEN'
                return map
            }
        }

        return map
    }

    Map checkBudgetForAutomatic(def budgetName, BusinessCompany businessCompany) {
        Map map = ["result": false, "gl": '', "id": '', "venOrCus": '']

        def incomeBudget = new BudgetViewDatabaseService().executeQueryPerDatabase("""
                                                SELECT cm.default_gl_account,cm.id FROM customer_master cm
                                                INNER JOIN budget_item_income bi on cm.id = bi.customer_id
                                                where cm.customer_name='${budgetName}'""", businessCompany)

        if (incomeBudget.size() > 0) {
            def customerSql = """SELECT default_gl_account,id from customer_master WHERE customer_name ='${budgetName}'"""

            def customerArr = new BudgetViewDatabaseService().executeQueryPerDatabase(customerSql, businessCompany)
            if (customerArr.size() > 0) {
                map.result = true
                map.gl = customerArr[0][0]
                map.id = customerArr[0][1]
                map.venOrCus = 'CUS'
                return map
            }
        }

        def expenseBudget = new BudgetViewDatabaseService().executeQueryPerDatabase("""
                                                       SELECT  vm.default_gl_account,vm.id  FROM vendor_master vm
                                                        INNER JOIN budget_item_expense bex on vm.id = bex.vendor_id
                                                        where vm.vendor_name='${budgetName}' """, businessCompany)

        if (expenseBudget.size() > 0) {
            def vendorSql = """SELECT default_gl_account,id from vendor_master WHERE vendor_name ='${budgetName}'"""
            def vendorArr = new BudgetViewDatabaseService().executeQueryPerDatabase(vendorSql, businessCompany)
            if (vendorArr.size() > 0) {
                map.result = true
                map.gl = vendorArr[0][0]
                map.id = vendorArr[0][1]
                map.venOrCus = 'VEN'
                return map
            }
        }

        return map
    }
}
