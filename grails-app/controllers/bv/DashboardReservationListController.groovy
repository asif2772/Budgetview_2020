package bv

import grails.converters.JSON
import vb.ReservationBudgetUtil

class DashboardReservationListController {

    IncomeInvoiceService incomeInvoiceService
    PrivateAndReservationBudgetService privateAndReservationBudgetService
    def index() {}
    def invoice(){

        def searchBudgetId = params.id
        def searchGlAccount = params.g
        def searchFiscalMonth = params.m
        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        def sql ="""SELECT a.id,CONCAT(b.gl_account,'-',c.account_name), CONCAT(sp.prefix,'-',a.budget_id),DATE_FORMAT(a.created_date,'%d-%m-%Y'),
        b.total_price_without_vat, b.total_price_with_vat,sp.prefix FROM reservation_budget_item AS a,reservation_budget_item_details AS b,chart_master AS
        c, system_prefix AS sp WHERE b.reserv_budget_item_id=a.id AND b.gl_account=c.account_code
        AND a.budget_name_id='6' AND a.booking_period_month=${searchFiscalMonth} AND a.booking_period_year=${FiscalYearInfo[0][4]} AND sp.id=15"""
        def budgetItemListArr = new BudgetViewDatabaseService().executeQuery(sql)

        ArrayList invoiceInsListFinal = new ArrayList()
        ArrayList tmpIds = new ArrayList()

        for (int i = 0; i < budgetItemListArr.size(); i++) {
            def budId = budgetItemListArr[i][0]
            tmpIds << budId
        }

        /*String tempIdsJoin = tmpIds.join(",")
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
        }*/

        def BudgetArr = new BudgetViewDatabaseService().executeQuery("""SELECT CONCAT(a.reservation_name,'[',b.prefix,'-', a.reservation_code,']')
            FROM reservation_budget_master AS a, system_prefix As b WHERE a.id=${searchBudgetId} AND b.id=14""")
        def glAccountDetails = new CoreParamsHelper().getChartMasterInformationByCode(searchGlAccount)

        def monthShow
        if (Integer.parseInt(searchFiscalMonth) == 12) {
            monthShow = "Dec - " + FiscalYearInfo[0][4]
        } else {
            monthShow = new CoreParamsHelper().monthNameShow(Integer.parseInt(searchFiscalMonth)) + " - " + FiscalYearInfo[0][4]
        }
        [ budgetListInstanceList: budgetItemListArr, budgetListInstanceTotal: budgetItemListArr.size(), FiscalYearInfo: FiscalYearInfo, CustomerNameInstance: BudgetArr[0], glAccountInstance: glAccountDetails[0] + " - " + glAccountDetails[1], monthShowInstance: monthShow]
//        [invoiceListInstanceList: invoiceInsListFinal, invoiceListInstanceTotal: invoiceInsListFinal.size(), budgetListInstanceList: budgetItemListArr, budgetListInstanceTotal: budgetItemListArr.size(), FiscalYearInfo: FiscalYearInfo, CustomerNameInstance: BudgetArr[0], glAccountInstance: glAccountDetails[0] + " - " + glAccountDetails[1], monthShowInstance: monthShow]

    }


    def showBudgetList() {
        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)
        String fiscalYearId = FiscalYearInfo[0][4]

        if (request.method == "GET") {
            println "GEt request"
            return;
        }

        String gridOutput
        def reservationBudgetUtil = new ReservationBudgetUtil()
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


        gridResult = privateAndReservationBudgetService.listOfBudgetItem(start, pageSize, sortItem, sortOrder, bookingPeriod, fiscalYearId, customerId);


        List dashIncomeBIList = reservationBudgetUtil.wrapBudgetItemInGrid(gridResult.dashboardIncomeBudgetItemList, start, journalId, customerId, bookingPeriod)
        //List dashExpenseBIList = quickEntryUtil.wrapBudgetItemInGrid(gridResult.dashboardExpenseBudgetItemList, start)
        LinkedHashMap result = [page: pageNumber, total: gridResult.count, rows: dashIncomeBIList]
        gridOutput = result as JSON
        render gridOutput;
    }
}
