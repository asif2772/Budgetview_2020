package bv

import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired
import vb.QuickEntryUtil

class DashboardExpanseListController {
    @Autowired
    QuickEntryService quickEntryService
    QuickEntryUtil quickEntryUtil

    def index() {}


    def showBudgetList() {

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)
        String fiscalYearId = FiscalYearInfo[0][4]

        if (request.method == "GET") {
            println "GEt request"
            return;
        }

        String gridOutput
        quickEntryUtil = new QuickEntryUtil();
        int start = 0
        String pageNumber = "1"
        String pageSize = "10"          //default 10 hardcoded
        String sortItem = "id"

        String bookingPeriod = params.bookingPeriod
        String journalId = params.journalId
        String vendorId = params.vendorId

        String sortOrder = "desc"       //default sort order
        if ((params.page) && (params.rp)) {
            pageNumber = params.page
            pageSize = params.rp
            sortItem = params.sortname
            sortOrder = params.sortorder
            start = (Integer.parseInt(pageNumber) - 1) * Integer.parseInt(pageSize)
        }

        LinkedHashMap gridResult
        if (params.query) { //search request
            String searchItem = params.qtype;
            String searchString = params.query;


            gridResult = quickEntryService.listOfBudgetItemSearch(start, pageSize, sortItem, sortOrder, bookingPeriod, fiscalYearId, vendorId, searchItem, searchString);
        } else { //list request
            //Grid data menupulate
            gridResult = quickEntryService.listOfBudgetItem(start, pageSize, sortItem, sortOrder, bookingPeriod, fiscalYearId, vendorId);
        }
        List dashExpenseBIList = quickEntryUtil.wrapBudgetItemInGrid(gridResult.dashboardExpenseBudgetItemList, start, journalId, vendorId, bookingPeriod)
        //List dashExpenseBIList = quickEntryUtil.wrapBudgetItemInGrid(gridResult.dashboardExpenseBudgetItemList, start)
        LinkedHashMap result = [page: pageNumber, total: gridResult.count, rows: dashExpenseBIList]
        gridOutput = result as JSON
        render gridOutput;
    }

    /*def receiptEntryList(){
        if (request.method == "GET"){
            println "GEt request"
            return;
        }
        String gridOutput
        quickEntryUtil = new QuickEntryUtil();
        int start = 0
        String pageNumber = "1"
        String pageSize = "10"          //default 10 hardcoded
        String sortItem = "id"
        String sortOrder = "desc"       //default sort order
        if ((params.page) && (params.rp)) {
            pageNumber = params.page
            pageSize = params.rp
            sortItem =params.sortname
            sortOrder=params.sortorder
            start = (Integer.parseInt(pageNumber) - 1) * Integer.parseInt(pageSize)
        }

        LinkedHashMap gridResult
        if (params.query) { //search request
            String searchItem=params.qtype;
            String searchString=params.query;
            gridResult = quickEntryService.receiptEntrySearch(start, pageSize, sortItem, sortOrder, searchItem, searchString);
        } else { //list request
            gridResult = quickEntryService.receiptEntryList(start, pageSize, sortItem, sortOrder);
        }
        List expenseEntryList = quickEntryUtil.wrapListInGrid(gridResult.expenseEntryList, start)
        LinkedHashMap result = [page: pageNumber, total: gridResult.count, rows: expenseEntryList]
        gridOutput = result as JSON
        render gridOutput;
    }*/



    def invoice() {
        def searchVendorId = params.id
        def searchGlAccount = params.g
        def searchFiscalMonth = params.m
        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        /*
        * SELECT a.id,CONCAT(b.gl_account,'-',c.account_name),
            CONCAT(sp.prefix,'-',a.budget_id),DATE_FORMAT(a.created_date,'%d-%m-%Y'),
            b.total_price_without_vat,
            b.total_price_with_vat
        FROM 	budget_item_expense AS a,budget_item_expense_details AS b,chart_master AS c, system_prefix AS sp
        WHERE 	b.budget_item_expense_id=a.id AND b.gl_account=c.account_code AND a.vendor_id='4'
        AND a.booking_period_start_month="3" AND a.booking_period_start_year="2013"
        AND sp.id=6
        */
        /*
           SELECT 	CONCAT(spe.prefix,'-',d.budget_id),
         a.id,
         a.vendor_id,
         a.booking_period,
         a.paid_amount,
         a.total_gl_amount,
         (a.total_gl_amount + a.total_vat) AS totalAmountWithVat,
         CONCAT(spe.prefix,'-',d.budget_id),
         CONCAT(sp.prefix,'-',a.invoice_no),
         DATE_FORMAT(a.trans_date,'%d-%m-%Y'),
         DATE_FORMAT(a.due_date,'%d-%m-%Y'),
         d.budget_id
     FROM 	invoice_expense AS a,
     budget_item_expense AS d,
     system_prefix AS sp,
     system_prefix AS spe
     WHERE 	a.budget_item_expense_id='158' AND d.id=a.budget_item_expense_id
     AND 	a.booking_period="3" AND a.booking_year="2013"
     AND 	a.vendor_id='4'
     AND 	sp.id=7 AND spe.id=6
     ORDER BY a.budget_item_expense_id */

        def budgetItemListArr = new BudgetViewDatabaseService().executeQuery("SELECT a.id,CONCAT(b.glAccount,'-',c.accountName), CONCAT(sp.prefix,'-',a.budgetId),DATE_FORMAT(a.createdDate,'%d-%m-%Y'), b.totalPriceWithoutVat, b.totalPriceWithVat FROM bv.BudgetItemExpense AS a,bv.BudgetItemExpenseDetails AS b,bv.ChartMaster AS c, bv.SystemPrefix AS sp WHERE b.budgetItemExpenseId=a.id AND b.glAccount=c.accountCode AND a.vendorId='" + searchVendorId + "' AND a.bookingPeriodStartMonth=" + searchFiscalMonth + " AND a.bookingPeriodStartYear=" + FiscalYearInfo[0][4] + " AND sp.id=6")
        /// def budgetItemListArr = BudgetItemIncome.executeQuery("SELECT a.id,CONCAT(b.glAccount,'-',c.accountName), CONCAT(sp.prefix,'-',a.budgetId),DATE_FORMAT(a.createdDate,'%d-%m-%Y'), b.totalPriceWithoutVat, b.totalPriceWithVat FROM bv.BudgetItemExpense AS a,bv.BudgetItemExpenseDetails AS b,bv.ChartMaster AS c, bv.SystemPrefix AS sp WHERE b.budgetItemExpenseId=a.id AND b.glAccount=c.accountCode AND a.vendorId='" + searchVendorId + "' AND a.bookingPeriodStartMonth=" + searchFiscalMonth + " AND a.bookingPeriodStartYear=" + FiscalYearInfo[0][4] + " AND sp.id=6")

        ArrayList invoiceInsListFinal = new ArrayList()
        ArrayList tmpIds = new ArrayList()

        for (int i = 0; i < budgetItemListArr.size(); i++) {
            def budId = budgetItemListArr[i][0]
            tmpIds << budId
        }

        String tempIdsJoin = tmpIds.join(",")
        if (tempIdsJoin) {

            def invoiceInsList = new BudgetViewDatabaseService().executeQuery("SELECT a.id,a.vendorId,a.bookingPeriod, a.paidAmount, a.totalGlAmount, (a.totalGlAmount + a.totalVat) AS totalAmountWithVat, CONCAT(spe.prefix,'-',d.budgetId),a.invoiceNo,DATE_FORMAT(a.transDate,'%d-%m-%Y'), DATE_FORMAT(a.dueDate,'%d-%m-%Y'),d.budgetId,((a.totalGlAmount + a.totalVat)-a.paidAmount) AS unpaidAmount,a.budgetItemExpenseId,a.comments AS AllGLAccount,(CASE a.isBookReceive WHEN 0 THEN CONCAT(spe.prefix,'-') ELSE CONCAT(spe2.prefix,'-') END) AS inType,a.isBookReceive FROM bv.InvoiceExpense AS a,bv.BudgetItemExpense AS d,bv.SystemPrefix AS spe ,bv.SystemPrefix AS spe2 WHERE a.bookingPeriod=" + searchFiscalMonth + " AND a.bookingYear=" + FiscalYearInfo[0][4] + " AND a.budgetItemExpenseId IN( " + tempIdsJoin + ") AND spe.id=6 AND spe2.id=12 AND a.budgetItemExpenseId=d.id AND a.isReverse=0 AND a.reverseInvoiceId=0 AND a.totalGlAmount!=0 ORDER BY a.budgetItemExpenseId")
            // def invoiceInsList = InvoiceExpense.executeQuery("SELECT a.id,a.vendorId,a.bookingPeriod, a.paidAmount, a.totalGlAmount, (a.totalGlAmount + a.totalVat) AS totalAmountWithVat, CONCAT(spe.prefix,'-',d.budgetId),a.invoiceNo,DATE_FORMAT(a.transDate,'%d-%m-%Y'), DATE_FORMAT(a.dueDate,'%d-%m-%Y'),d.budgetId,((a.totalGlAmount + a.totalVat)-a.paidAmount) AS unpaidAmount,a.budgetItemExpenseId,a.comments AS AllGLAccount,(CASE a.isBookReceive WHEN 0 THEN CONCAT(spe.prefix,'-') ELSE CONCAT(spe2.prefix,'-') END) AS inType,a.isBookReceive FROM bv.InvoiceExpense AS a,bv.BudgetItemExpense AS d,bv.SystemPrefix AS spe ,bv.SystemPrefix AS spe2 WHERE a.bookingPeriod=" + searchFiscalMonth + " AND a.bookingYear=" + FiscalYearInfo[0][4] + " AND a.budgetItemExpenseId IN( " + tempIdsJoin + ") AND spe.id=6 AND spe2.id=12 AND a.budgetItemExpenseId=d.id AND a.isReverse=0 AND a.reverseInvoiceId=0 AND a.totalGlAmount!=0 ORDER BY a.budgetItemExpenseId")

            invoiceInsList.eachWithIndex { IndexVal, Key ->
                ////GL ACCOUNTS////////
                def glAccountsList = new BudgetViewDatabaseService().executeQuery("SELECT a.accountCode,CONCAT(a.accountCode,'-',b.accountName) AS accountName  FROM InvoiceExpenseDetails As a, ChartMaster As b  WHERE a.invoiceId=" + IndexVal[0] + " AND a.accountCode=b.accountCode")
                //def glAccountsList = InvoiceExpenseDetails.executeQuery("SELECT a.accountCode,CONCAT(a.accountCode,'-',b.accountName) AS accountName  FROM InvoiceExpenseDetails As a, ChartMaster As b  WHERE a.invoiceId=" + IndexVal[0] + " AND a.accountCode=b.accountCode")

                String tempGLsJoin = ""
                ArrayList tmpGLs = new ArrayList()
                if (glAccountsList.size()) {
                    for (int i = 0; i < glAccountsList.size(); i++) {
                        tmpGLs << glAccountsList[i][0]
                    }
                    tempGLsJoin = tmpGLs.join(",")
                }
                IndexVal[13] = tempGLsJoin
                invoiceInsListFinal << IndexVal
            }
        }

        def CustomerArr = new BudgetViewDatabaseService().executeQuery("SELECT CONCAT(a.vendorName,'[',b.prefix,'-', a.vendorCode,']') FROM bv.VendorMaster AS a, bv.SystemPrefix As b WHERE a.id=" + searchVendorId + " AND b.id=2")
        //def CustomerArr = VendorMaster.executeQuery("SELECT CONCAT(a.vendorName,'[',b.prefix,'-', a.vendorCode,']') FROM bv.VendorMaster AS a, bv.SystemPrefix As b WHERE a.id=" + searchVendorId + " AND b.id=2")
        def glAccountDetails = new CoreParamsHelper().getChartMasterInformationByCode(searchGlAccount)

        def monthShow
        if (Integer.parseInt(searchFiscalMonth) == 12){
            monthShow = "Dec - " + FiscalYearInfo[0][4]
        }else{
            monthShow = new CoreParamsHelper().monthNameShow(Integer.parseInt(searchFiscalMonth)) + " - " + FiscalYearInfo[0][4]
        }
        [invoiceListInstanceList: invoiceInsListFinal, invoiceListInstanceTotal: invoiceInsListFinal.size(), budgetListInstanceList: budgetItemListArr, budgetListInstanceTotal: budgetItemListArr.size(), FiscalYearInfo: FiscalYearInfo, CustomerNameInstance: CustomerArr[0], glAccountInstance: glAccountDetails[0] + " - " + glAccountDetails[1], monthShowInstance: monthShow]

    }

    def invoiceFirstLoading() {
        println('asjosj')
    }

    def budget() {

        def searchVendorId = params.id
        def searchGlAccount = params.g
        def searchFiscalMonth = params.m
        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        def budgetItemListArr = new BudgetViewDatabaseService().executeQuery("SELECT a.id,CONCAT(sp.prefix,'-',a.budgetId),DATE_FORMAT(a.createdDate,'%d-%m-%Y'),b.totalPriceWithoutVat FROM bv.BudgetItemExpense AS a,bv.BudgetItemExpenseDetails AS b,bv.SystemPrefix AS sp WHERE b.budgetItemExpenseId=a.id AND a.vendorId='" + searchVendorId + "' AND b.glAccount='" + searchGlAccount + "' AND a.bookingPeriodStartMonth=" + searchFiscalMonth + " AND a.bookingPeriodStartYear=" + FiscalYearInfo[0][4] + " AND sp.id=6")
        //def budgetItemListArr = BudgetItemIncome.executeQuery("SELECT a.id,CONCAT(sp.prefix,'-',a.budgetId),DATE_FORMAT(a.createdDate,'%d-%m-%Y'),b.totalPriceWithoutVat FROM bv.BudgetItemExpense AS a,bv.BudgetItemExpenseDetails AS b,bv.SystemPrefix AS sp WHERE b.budgetItemExpenseId=a.id AND a.vendorId='" + searchVendorId + "' AND b.glAccount='" + searchGlAccount + "' AND a.bookingPeriodStartMonth=" + searchFiscalMonth + " AND a.bookingPeriodStartYear=" + FiscalYearInfo[0][4] + " AND sp.id=6")

        def CustomerArr = new BudgetViewDatabaseService().executeQuery("SELECT CONCAT(a.vendorName,'[',b.prefix,'-', a.vendorCode,']') FROM bv.VendorMaster AS a, bv.SystemPrefix As b WHERE a.id=" + searchVendorId + " AND b.id=2")
        // def CustomerArr = VendorMaster.executeQuery("SELECT CONCAT(a.vendorName,'[',b.prefix,'-', a.vendorCode,']') FROM bv.VendorMaster AS a, bv.SystemPrefix As b WHERE a.id=" + searchVendorId + " AND b.id=2")

        def glAccountDetails = new CoreParamsHelper().getChartMasterInformationByCode(searchGlAccount)

        //def monthShow = new CoreParamsHelper().monthNameShow(Integer.parseInt(searchFiscalMonth)) + " - " + FiscalYearInfo[0][4]
        def monthShow
        if(searchFiscalMonth == 12){
            monthShow = "Dec - "+ FiscalYearInfo[0][4]
        }else{
            monthShow = new CoreParamsHelper().monthNameShow(Integer.parseInt(searchFiscalMonth))+" - "+ FiscalYearInfo[0][4]
        }

        [invoiceListInstanceList: budgetItemListArr, invoiceListInstanceTotal: budgetItemListArr.size(), FiscalYearInfo: FiscalYearInfo, CustomerNameInstance: CustomerArr[0], glAccountInstance: glAccountDetails[0] + " - " + glAccountDetails[1], monthShowInstance: monthShow]

    }

    def budgetRegularSummery() {

        def searchVendorId = params.id
        def searchFiscalMonth = params.m
        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)


        def budgetItemListArr = new BudgetViewDatabaseService().executeQuery("SELECT a.id,CONCAT(sp.prefix,'-',a.budgetId),DATE_FORMAT(a.createdDate,'%d-%m-%Y'),b.totalPriceWithoutVat FROM bv.BudgetItemExpense AS a,bv.BudgetItemExpenseDetails AS b,bv.SystemPrefix AS sp WHERE b.budgetItemExpenseId=a.id AND a.vendorId='" + searchVendorId + "' AND a.bookingPeriodStartMonth=" + searchFiscalMonth + " AND a.bookingPeriodStartYear=" + FiscalYearInfo[0][4] + " AND sp.id=6")
        //def budgetItemListArr = BudgetItemIncome.executeQuery("SELECT a.id,CONCAT(sp.prefix,'-',a.budgetId),DATE_FORMAT(a.createdDate,'%d-%m-%Y'),b.totalPriceWithoutVat FROM bv.BudgetItemExpense AS a,bv.BudgetItemExpenseDetails AS b,bv.SystemPrefix AS sp WHERE b.budgetItemExpenseId=a.id AND a.vendorId='" + searchVendorId + "' AND a.bookingPeriodStartMonth=" + searchFiscalMonth + " AND a.bookingPeriodStartYear=" + FiscalYearInfo[0][4] + " AND sp.id=6")

        def CustomerArr = new BudgetViewDatabaseService().executeQuery("SELECT CONCAT(a.vendorName,'[',b.prefix,'-', a.vendorCode,']') FROM bv.VendorMaster AS a, bv.SystemPrefix As b WHERE a.id=" + searchVendorId + " AND b.id=2")
        //def CustomerArr = VendorMaster.executeQuery("SELECT CONCAT(a.vendorName,'[',b.prefix,'-', a.vendorCode,']') FROM bv.VendorMaster AS a, bv.SystemPrefix As b WHERE a.id=" + searchVendorId + " AND b.id=2")

        def glAccountDetails = ""

        def monthShow = new CoreParamsHelper().monthNameShow(Integer.parseInt(searchFiscalMonth)) + " - " + FiscalYearInfo[0][4]

        [invoiceListInstanceList: budgetItemListArr, invoiceListInstanceTotal: budgetItemListArr.size(), FiscalYearInfo: FiscalYearInfo, CustomerNameInstance: CustomerArr[0], glAccountInstance: glAccountDetails, monthShowInstance: monthShow]

    }

    def invoiceRegularSummery() {

        def searchVendorId = params.id
        def searchFiscalMonth = params.m
        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        def VendorInvoiceViewArr = new BudgetViewDatabaseService().executeQuery("SELECT a.id,a.vendorId,b.accountCode,c.accountName,a.bookingPeriod,SUM(b.totalAmountWithoutVat),COUNT(DISTINCT b.invoiceId),CONCAT(spe.prefix,'-',d.budgetId),CONCAT(sp.prefix,'-',a.invoiceNo),DATE_FORMAT(a.transDate,'%d-%m-%Y'),DATE_FORMAT(a.dueDate,'%d-%m-%Y') FROM bv.InvoiceExpense AS a,bv.InvoiceExpenseDetails AS b,bv.ChartMaster AS c, bv.BudgetItemExpense AS d,bv.SystemPrefix AS sp,bv.SystemPrefix AS spe WHERE a.vendorId='" + searchVendorId + "'  AND a.id=b.invoiceId AND b.accountCode=c.accountCode AND a.bookingPeriod=" + searchFiscalMonth + " AND a.bookingYear=" + FiscalYearInfo[0][4] + " AND b.accountCode!='' AND a.budgetItemExpenseId=d.id AND sp.id=7 AND spe.id=6 GROUP BY b.invoiceId,b.accountCode ORDER BY b.invoiceId,b.accountCode,a.vendorId")
        //def VendorInvoiceViewArr = InvoiceExpense.executeQuery("SELECT a.id,a.vendorId,b.accountCode,c.accountName,a.bookingPeriod,SUM(b.totalAmountWithoutVat),COUNT(DISTINCT b.invoiceId),CONCAT(spe.prefix,'-',d.budgetId),CONCAT(sp.prefix,'-',a.invoiceNo),DATE_FORMAT(a.transDate,'%d-%m-%Y'),DATE_FORMAT(a.dueDate,'%d-%m-%Y') FROM bv.InvoiceExpense AS a,bv.InvoiceExpenseDetails AS b,bv.ChartMaster AS c, bv.BudgetItemExpense AS d,bv.SystemPrefix AS sp,bv.SystemPrefix AS spe WHERE a.vendorId='" + searchVendorId + "'  AND a.id=b.invoiceId AND b.accountCode=c.accountCode AND a.bookingPeriod=" + searchFiscalMonth + " AND a.bookingYear=" + FiscalYearInfo[0][4] + " AND b.accountCode!='' AND a.budgetItemExpenseId=d.id AND sp.id=7 AND spe.id=6 GROUP BY b.invoiceId,b.accountCode ORDER BY b.invoiceId,b.accountCode,a.vendorId")

        def CustomerArr = new BudgetViewDatabaseService().executeQuery("SELECT CONCAT(a.vendorName,'[',b.prefix,'-', a.vendorCode,']') FROM bv.VendorMaster AS a, bv.SystemPrefix As b WHERE a.id=" + searchVendorId + " AND b.id=2")
        //def CustomerArr = VendorMaster.executeQuery("SELECT CONCAT(a.vendorName,'[',b.prefix,'-', a.vendorCode,']') FROM bv.VendorMaster AS a, bv.SystemPrefix As b WHERE a.id=" + searchVendorId + " AND b.id=2")

        def glAccountDetails = ""

        def monthShow = new CoreParamsHelper().monthNameShow(Integer.parseInt(searchFiscalMonth)) + " - " + FiscalYearInfo[0][4]

        [invoiceListInstanceList: VendorInvoiceViewArr, invoiceListInstanceTotal: VendorInvoiceViewArr.size(), FiscalYearInfo: FiscalYearInfo, CustomerNameInstance: CustomerArr[0], glAccountInstance: glAccountDetails, monthShowInstance: monthShow]

    }


    def budgetShopSummery() {

        def searchGlAccount = params.id
        def searchFiscalMonth = params.m
        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        def budgetItemListArr = new BudgetViewDatabaseService().executeQuery("SELECT a.id,CONCAT(sp.prefix,'-',a.budgetId),DATE_FORMAT(a.createdDate,'%d-%m-%Y'),b.totalPriceWithoutVat FROM bv.BudgetItemExpense AS a,bv.BudgetItemExpenseDetails AS b,bv.SystemPrefix AS sp WHERE b.budgetItemExpenseId=a.id AND b.glAccount='" + searchGlAccount + "' AND a.bookingPeriodStartMonth=" + searchFiscalMonth + " AND a.bookingPeriodStartYear=" + FiscalYearInfo[0][4] + " AND sp.id=6")
        //def budgetItemListArr = BudgetItemIncome.executeQuery("SELECT a.id,CONCAT(sp.prefix,'-',a.budgetId),DATE_FORMAT(a.createdDate,'%d-%m-%Y'),b.totalPriceWithoutVat FROM bv.BudgetItemExpense AS a,bv.BudgetItemExpenseDetails AS b,bv.SystemPrefix AS sp WHERE b.budgetItemExpenseId=a.id AND b.glAccount='" + searchGlAccount + "' AND a.bookingPeriodStartMonth=" + searchFiscalMonth + " AND a.bookingPeriodStartYear=" + FiscalYearInfo[0][4] + " AND sp.id=6")

        def CustomerArr = ""

        def glAccountDetails = new CoreParamsHelper().getChartMasterInformationByCode(searchGlAccount)

        def monthShow = new CoreParamsHelper().monthNameShow(Integer.parseInt(searchFiscalMonth)) + " - " + FiscalYearInfo[0][4]

        [invoiceListInstanceList: budgetItemListArr, invoiceListInstanceTotal: budgetItemListArr.size(), FiscalYearInfo: FiscalYearInfo, CustomerNameInstance: CustomerArr, glAccountInstance: glAccountDetails[0] + " - " + glAccountDetails[1], monthShowInstance: monthShow]

    }

    def invoiceShopSummery() {

        def searchGlAccount = params.id
        def searchFiscalMonth = params.m
        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        def VendorInvoiceViewArr = new BudgetViewDatabaseService().executeQuery("SELECT a.id,a.vendorId,b.accountCode,c.accountName,a.bookingPeriod,SUM(b.totalAmountWithoutVat),COUNT(DISTINCT b.invoiceId),CONCAT(spe.prefix,'-',d.budgetId),CONCAT(sp.prefix,'-',a.invoiceNo),DATE_FORMAT(a.transDate,'%d-%m-%Y'),DATE_FORMAT(a.dueDate,'%d-%m-%Y') FROM bv.InvoiceExpense AS a,bv.InvoiceExpenseDetails AS b,bv.ChartMaster AS c, bv.BudgetItemExpense AS d,bv.SystemPrefix AS sp,bv.SystemPrefix AS spe WHERE a.id=b.invoiceId AND b.accountCode=c.accountCode AND a.bookingPeriod=" + searchFiscalMonth + " AND a.bookingYear=" + FiscalYearInfo[0][4] + " AND b.accountCode=" + searchGlAccount + " AND a.budgetItemExpenseId=d.id AND sp.id=7 AND spe.id=6 GROUP BY b.invoiceId,b.accountCode ORDER BY b.invoiceId,b.accountCode,a.vendorId")
        //def VendorInvoiceViewArr = InvoiceExpense.executeQuery("SELECT a.id,a.vendorId,b.accountCode,c.accountName,a.bookingPeriod,SUM(b.totalAmountWithoutVat),COUNT(DISTINCT b.invoiceId),CONCAT(spe.prefix,'-',d.budgetId),CONCAT(sp.prefix,'-',a.invoiceNo),DATE_FORMAT(a.transDate,'%d-%m-%Y'),DATE_FORMAT(a.dueDate,'%d-%m-%Y') FROM bv.InvoiceExpense AS a,bv.InvoiceExpenseDetails AS b,bv.ChartMaster AS c, bv.BudgetItemExpense AS d,bv.SystemPrefix AS sp,bv.SystemPrefix AS spe WHERE a.id=b.invoiceId AND b.accountCode=c.accountCode AND a.bookingPeriod=" + searchFiscalMonth + " AND a.bookingYear=" + FiscalYearInfo[0][4] + " AND b.accountCode=" + searchGlAccount + " AND a.budgetItemExpenseId=d.id AND sp.id=7 AND spe.id=6 GROUP BY b.invoiceId,b.accountCode ORDER BY b.invoiceId,b.accountCode,a.vendorId")

        def CustomerArr = ""

        def glAccountDetails = new CoreParamsHelper().getChartMasterInformationByCode(searchGlAccount)

        def monthShow = new CoreParamsHelper().monthNameShow(Integer.parseInt(searchFiscalMonth)) + " - " + FiscalYearInfo[0][4]

        [invoiceListInstanceList: VendorInvoiceViewArr, invoiceListInstanceTotal: VendorInvoiceViewArr.size(), FiscalYearInfo: FiscalYearInfo, CustomerNameInstance: CustomerArr, glAccountInstance: glAccountDetails[0] + " - " + glAccountDetails[1], monthShowInstance: monthShow]

    }

}
