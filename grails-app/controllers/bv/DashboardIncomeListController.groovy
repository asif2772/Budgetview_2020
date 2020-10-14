package bv

import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired
import vb.IncomeInvoiceUtil
import vb.QuickEntryUtil

class DashboardIncomeListController {
    @Autowired
    IncomeInvoiceService incomeInvoiceService
    IncomeInvoiceUtil incomeInvoiceUtil
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
        incomeInvoiceUtil = new IncomeInvoiceUtil();
        int start = 0
        String pageNumber = "1"
        String pageSize = "10"          //default 10 hardcoded
        String sortItem = "id"

        String bookingPeriod = params.bookingPeriod
        String journalId = params.journalId
        String customerId = params.customerId

        String sortOrder = "desc"       //default sort order
        if ((params.page) && (params.rp)) {
            pageNumber = params.page
            pageSize = params.rp
            sortItem = params.sortname
            sortOrder = params.sortorder
            start = (Integer.parseInt(pageNumber) - 1) * Integer.parseInt(pageSize)
        }

        LinkedHashMap gridResult

        gridResult = incomeInvoiceService.listOfBudgetItem(start, pageSize, sortItem, sortOrder, bookingPeriod, fiscalYearId, customerId);


        List dashIncomeBIList = incomeInvoiceUtil.wrapBudgetItemInGrid(gridResult.dashboardIncomeBudgetItemList, start, journalId, customerId, bookingPeriod)
//        List dashExpenseBIList = quickEntryUtil.wrapBudgetItemInGrid(gridResult.dashboardIncomeBudgetItemList, start, journalId, customerId, bookingPeriod)
        LinkedHashMap result = [page: pageNumber, total: gridResult.count, rows: dashIncomeBIList]
        gridOutput = result as JSON
        render gridOutput;
    }

    def invoice() {
        def searchCustomerId = params.id
        def searchGlAccount = params.g
        def searchFiscalMonth = params.m
        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        def budgetItemListArr = new BudgetViewDatabaseService().executeQuery("SELECT a.id,CONCAT(b.glAccount,'-',c.accountName), CONCAT(sp.prefix,'-',a.budgetId),DATE_FORMAT(a.createdDate,'%d-%m-%Y'), b.totalPriceWithoutVat, b.totalPriceWithVat FROM bv.BudgetItemIncome AS a,bv.BudgetItemIncomeDetails AS b,bv.ChartMaster AS c, bv.SystemPrefix AS sp WHERE b.budgetItemIncomeId=a.id AND b.glAccount=c.accountCode AND a.customerId='" + searchCustomerId + "' AND a.bookingPeriodStartMonth=" + searchFiscalMonth + " AND a.bookingPeriodStartYear=" + FiscalYearInfo[0][4] + " AND sp.id=6")

        ArrayList invoiceInsListFinal = new ArrayList()
        ArrayList tmpIds = new ArrayList()

        for (int i = 0; i < budgetItemListArr.size(); i++) {
            def budId = budgetItemListArr[i][0]
            tmpIds << budId
        }

        String tempIdsJoin = tmpIds.join(",")
        if (tempIdsJoin) {

            def invoiceInsList = new BudgetViewDatabaseService().executeQuery("SELECT a.id,a.customerId,a.bookingPeriod, a.paidAmount, a.totalGlAmount, (a.totalGlAmount + a.totalVat) AS totalAmountWithVat, CONCAT(spe.prefix,'-',d.budgetId),CONCAT(sp.prefix,'-',a.invoiceNo),DATE_FORMAT(a.transDate,'%d-%m-%Y'), DATE_FORMAT(a.dueDate,'%d-%m-%Y'),d.budgetId,((a.totalGlAmount + a.totalVat)-a.paidAmount) AS unpaidAmount,a.budgetItemIncomeId,a.comments AS AllGLAccount  FROM bv.InvoiceIncome AS a,bv.BudgetItemIncome AS d,bv.SystemPrefix AS sp,bv.SystemPrefix AS spe WHERE a.bookingPeriod=" + searchFiscalMonth + " AND a.bookingYear=" + FiscalYearInfo[0][4] + " AND a.budgetItemIncomeId IN( " + tempIdsJoin + ") AND sp.id=8 AND spe.id=11 AND a.budgetItemIncomeId=d.id AND a.isReverse=0 AND a.reverseInvoiceId=0 AND a.totalGlAmount!=0 ORDER BY a.budgetItemIncomeId")
            invoiceInsList.eachWithIndex { IndexVal, Key ->
                ////GL ACCOUNTS////////
                def glAccountsList = new BudgetViewDatabaseService().executeQuery("SELECT a.accountCode,CONCAT(a.accountCode,'-',b.accountName) AS accountName  FROM InvoiceIncomeDetails As a, ChartMaster As b  WHERE a.invoiceId=" + IndexVal[0] + " AND a.accountCode=b.accountCode")
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

        def CustomerArr = new BudgetViewDatabaseService().executeQuery("SELECT CONCAT(a.customerName,'[',b.prefix,'-', a.customerCode,']') FROM bv.CustomerMaster AS a, bv.SystemPrefix As b WHERE a.id=" + searchCustomerId + " AND b.id=1")
        def glAccountDetails = new CoreParamsHelper().getChartMasterInformationByCode(searchGlAccount)

        def monthShow
        if (Integer.parseInt(searchFiscalMonth) == 12) {
            monthShow = "Dec - " + FiscalYearInfo[0][4]
        } else {
            monthShow = new CoreParamsHelper().monthNameShow(Integer.parseInt(searchFiscalMonth)) + " - " + FiscalYearInfo[0][4]
        }
        [invoiceListInstanceList: invoiceInsListFinal, invoiceListInstanceTotal: invoiceInsListFinal.size(), budgetListInstanceList: budgetItemListArr, budgetListInstanceTotal: budgetItemListArr.size(), FiscalYearInfo: FiscalYearInfo, CustomerNameInstance: CustomerArr[0], glAccountInstance: glAccountDetails[0] + " - " + glAccountDetails[1], monthShowInstance: monthShow]


    }

    def budget() {

        def searchVendorId = params.id
        def searchGlAccount = params.g
        def searchFiscalMonth = params.m
        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        def budgetItemListArr = new BudgetViewDatabaseService().executeQuery("SELECT a.id,CONCAT(sp.prefix,'-',a.budgetId),DATE_FORMAT(a.createdDate,'%d-%m-%Y'),b.totalPriceWithoutVat FROM bv.BudgetItemIncome AS a,bv.BudgetItemIncomeDetails AS b,bv.SystemPrefix AS sp WHERE b.budgetItemIncomeId=a.id AND a.customerId='" + searchVendorId + "' AND b.glAccount='" + searchGlAccount + "' AND a.bookingPeriodStartMonth=" + searchFiscalMonth + " AND a.bookingPeriodStartYear=" + FiscalYearInfo[0][4] + " AND sp.id=11")

        def CustomerArr = new BudgetViewDatabaseService().executeQuery("SELECT CONCAT(a.customerName,'[',b.prefix,'-', a.customerCode,']') FROM bv.CustomerMaster AS a, bv.SystemPrefix As b WHERE a.id=" + searchVendorId + " AND b.id=1")

        def glAccountDetails = new CoreParamsHelper().getChartMasterInformationByCode(searchGlAccount)

        //def monthShow = new CoreParamsHelper().monthNameShow(Integer.parseInt(searchFiscalMonth))+" - "+ FiscalYearInfo[0][4]
        def monthShow
        if (Integer.parseInt(searchFiscalMonth) == 12) {
            monthShow = "Dec - " + FiscalYearInfo[0][4]
        } else {
            monthShow = new CoreParamsHelper().monthNameShow(Integer.parseInt(searchFiscalMonth)) + " - " + FiscalYearInfo[0][4]
        }

        [invoiceListInstanceList: budgetItemListArr, invoiceListInstanceTotal: budgetItemListArr.size(), FiscalYearInfo: FiscalYearInfo, CustomerNameInstance: CustomerArr[0], glAccountInstance: glAccountDetails[0] + " - " + glAccountDetails[1], monthShowInstance: monthShow]

    }

    /* def privateBudget() {

         def searchVendorId = params.id
         def searchGlAccount = params.g
         def searchFiscalMonth = params.m
         def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
         def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

         def budgetItemListArr = new BudgetViewDatabaseService().executeQuery("SELECT a.id,CONCAT(sp.prefix,'-',a.budgetId),DATE_FORMAT(a.createdDate,'%d-%m-%Y'),b.totalPriceWithoutVat FROM bv.BudgetItemIncome AS a,bv.BudgetItemIncomeDetails AS b,bv.SystemPrefix AS sp WHERE b.budgetItemIncomeId=a.id AND a.customerId='" + searchVendorId + "' AND b.glAccount='" + searchGlAccount + "' AND a.bookingPeriodStartMonth=" + searchFiscalMonth + " AND a.bookingPeriodStartYear=" + FiscalYearInfo[0][4] + " AND sp.id=11")

         def CustomerArr = new BudgetViewDatabaseService().executeQuery("SELECT CONCAT(a.customerName,'[',b.prefix,'-', a.customerCode,']') FROM bv.CustomerMaster AS a, bv.SystemPrefix As b WHERE a.id=" + searchVendorId + " AND b.id=1")

         def glAccountDetails = new CoreParamsHelper().getChartMasterInformationByCode(searchGlAccount)

         //def monthShow = new CoreParamsHelper().monthNameShow(Integer.parseInt(searchFiscalMonth))+" - "+ FiscalYearInfo[0][4]
         def monthShow
         if (Integer.parseInt(searchFiscalMonth) == 12) {
             monthShow = "Dec - " + FiscalYearInfo[0][4]
         } else {
             monthShow = new CoreParamsHelper().monthNameShow(Integer.parseInt(searchFiscalMonth)) + " - " + FiscalYearInfo[0][4]
         }

         [invoiceListInstanceList : budgetItemListArr,
          invoiceListInstanceTotal: budgetItemListArr.size(),
          FiscalYearInfo          : FiscalYearInfo,
          CustomerNameInstance    : CustomerArr[0],
          glAccountInstance       : glAccountDetails[0] + " - " + glAccountDetails[1],
          monthShowInstance       : monthShow]

     }*/
}
