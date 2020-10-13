package bv

import grails.gorm.transactions.Transactional

@Transactional
class DashboardDetailsService {

    def dashboardDetailsTagLib = new DashboardDetailsTagLib();

    def serviceMethod() {

    }

    def ArrayList getBudgetCustomerData(fiscalYearInfo){

        LinkedHashMap gridResultCustomerArr
        String select = "bii.customerId AS customer_id,cm.customerName As customer_name,bii.id AS bookInvoiceId,SUM(bii.total_gl_amount) as totalBudAmt"
        String selectIndex = "customer_id,customer_name,bookInvoiceId,totalBudAmt"
        String from = "BudgetItemIncome AS bii,CustomerMaster AS cm"
        String where = "bii.bookingPeriodStartMonth >= '" + fiscalYearInfo[0][3] +"' AND bii.bookingPeriodStartYear >= '" + fiscalYearInfo[0][4] +
                "' AND bii.bookingPeriodStartMonth <= '" + fiscalYearInfo[0][5] + "' AND bii.bookingPeriodStartYear <= '" + fiscalYearInfo[0][6] +
                "' AND  bii.customerId = cm.id AND bii.customerId != '' AND bii.status=1";

        String groupBy = "bii.customer_id"
        String orderBy = "cm.customerName ASC"
        gridResultCustomerArr = new BudgetViewDatabaseService().select(select,from,where,orderBy,groupBy,'false',selectIndex)

        ArrayList budgetCustomerArr = gridResultCustomerArr['dataGridList']

        return budgetCustomerArr;
    }

    def ArrayList getBudgetPrivateData(fiscalYearInfo) {

        LinkedHashMap gridResultPrivateBudgetArr

        String select = "pbi.budgetNameId AS budget_name_id,pbm.budgetName As budget_name,pbi.id AS bookInvoiceId,SUM(pbi.total_amount) as total_amount, pbm.budget_type as budget_type,IF(pbm.budget_type = 1,1,3) as tempBudgetType"
        String selectIndex = "budget_name_id,budget_name,bookInvoiceId,total_amount,budget_type,tempBudgetType"
        String from = "PrivateBudgetItem AS pbi, PrivateBudgetMaster AS pbm"
        String where = "pbi.booking_period >= '" + fiscalYearInfo[0][3] +"' AND pbi.booking_year >= '" + fiscalYearInfo[0][4] +"' " +
                "AND pbi.booking_period <= '" + fiscalYearInfo[0][5] + "' AND pbi.booking_year <= '" + fiscalYearInfo[0][6] +"' " +
                "AND  pbi.budget_name_id = pbm.id AND pbi.budget_name_id != '' AND pbi.status=1";
        String groupBy = "pbi.budgetNameId"
//        String orderBy = "tempBudgetType,pbm.budgetName"
        String orderBy = "pbm.budget_type,pbm.budgetName"

        /* String select = "pbi.budgetNameId AS budget_name_id,pbm.budgetName As budget_name,pbi.id AS bookInvoiceId,SUM(pbi.total_amount) as total_amount, pbm.budget_type as budget_type"
         String selectIndex = "budget_name_id,budget_name,bookInvoiceId,total_amount,budget_type"
         String from = "PrivateBudgetItem AS pbi, PrivateBudgetMaster AS pbm"
         String where = "pbi.booking_period >= '" + fiscalYearInfo[0][3] +"' AND pbi.booking_year >= '" + fiscalYearInfo[0][4] +"' " +
                         "AND pbi.booking_period <= '" + fiscalYearInfo[0][5] + "' AND pbi.booking_year <= '" + fiscalYearInfo[0][6] +"' " +
                         "AND  pbi.budget_name_id = pbm.id AND pbi.budget_name_id != '' AND pbi.status=1";
         String groupBy = "pbi.budgetNameId"
         String orderBy = "pbm.budget_type,pbm.budgetName"*/

        gridResultPrivateBudgetArr = new BudgetViewDatabaseService().select(select,from,where,orderBy,groupBy,'false',selectIndex)
        ArrayList privateBudgetArr = gridResultPrivateBudgetArr['dataGridList']

        return privateBudgetArr;
    }

    def ArrayList getCustomerDataAHWise(fiscalYearInfo){
        LinkedHashMap gridResultCustomerArr

        String select = "DISTINCT(a.glAccount) AS gl_account,cm.accountName As account_name,SUM(a.total_price_without_vat) as totalBudAmt"
        String selectIndex = "gl_account,account_name,totalBudAmt"
        String from = " BudgetItemIncomeDetails AS a,BudgetItemIncome AS b,ChartMaster AS cm"
        String where = "a.glAccount = cm.accountCode  AND a.budgetItemIncomeId = b.id AND b.bookingPeriodStartMonth >= '" + fiscalYearInfo[0][3] +"' AND b.bookingPeriodStartYear >= '" + fiscalYearInfo[0][4] +
                "' AND b.bookingPeriodEndMonth <= '" + fiscalYearInfo[0][5] + "' AND b.bookingPeriodEndYear <= '" + fiscalYearInfo[0][6] + "'"
        String groupBy="a.glAccount"
        String orderBy = "a.glAccount"

        gridResultCustomerArr = new BudgetViewDatabaseService().select(select,from,where,orderBy,groupBy,'false',selectIndex)
        ArrayList budgetCustomerArr = gridResultCustomerArr['dataGridList']

        return budgetCustomerArr;
    }

    def ArrayList getBudgetVendorData(fiscalYearInfo){

        LinkedHashMap gridResultVendorArr

        String select="a.vendorId AS vendor_id,v.vendorName As vendor_name,a.id AS bookInvoiceId,SUM(a.total_gl_amount) as totalBudAmt"
        String selectIndex="vendor_id,vendor_name,bookInvoiceId,totalBudAmt"
        String from="BudgetItemExpense AS a,VendorMaster AS v "
        String where="a.bookingPeriodStartMonth >='" + fiscalYearInfo[0][3] +"' AND a.bookingPeriodStartYear >='" + fiscalYearInfo[0][4] + "' AND a.bookingPeriodStartMonth <='" + fiscalYearInfo[0][5] + "' AND a.bookingPeriodStartYear<='" + fiscalYearInfo[0][6] + "' AND a.vendorId=v.id AND a.vendorId!='' AND a.status=1"
        String groupBy="a.vendor_id"
        String orderBy="v.vendorName ASC"
        gridResultVendorArr = new BudgetViewDatabaseService().select(select,from,where,orderBy,groupBy,'false',selectIndex)

        ArrayList budgetVendorArr = gridResultVendorArr['dataGridList']

        return budgetVendorArr;
    }

    def ArrayList getBudgetReservationData(fiscalYearInfo){

        LinkedHashMap gridResultArr

        String select = "a.budget_name_id AS budgetNameId,v.reservation_name As reservation_name,a.id AS bookInvoiceId,SUM(a.total_amount) as totalBudAmt"
        String selectIndex = "budgetNameId,reservation_name,bookInvoiceId,totalBudAmt"
        String from = "reservation_budget_item AS a,reservation_budget_master AS v"
        String where = "a.booking_period_month >='" + fiscalYearInfo[0][3] + "' AND a.booking_period_year >='" + fiscalYearInfo[0][4] + "' AND a.booking_period_month <='" + fiscalYearInfo[0][5] + "' AND a.booking_period_year<='" + fiscalYearInfo[0][6] + "' AND a.budget_name_id=v.id AND a.budget_name_id!='' AND a.status=1 "
        String groupBy = "a.budget_name_id"
        String orderBy = "v.reservation_name"
        gridResultArr = new BudgetViewDatabaseService().select(select, from, where, orderBy, groupBy, 'false', selectIndex)

        ArrayList budgetArr = gridResultArr['dataGridList']

        return budgetArr;
    }

    def ArrayList getVendorDataAHWise(fiscalYearInfo){

        LinkedHashMap gridResultVendorArr

        String select = "DISTINCT(a.glAccount) AS gl_account,v.accountName As account_name,SUM(a.total_price_without_vat) as totalBudAmt"
        String selectIndex = "gl_account,account_name,totalBudAmt"
        String from = "BudgetItemExpenseDetails AS a,BudgetItemExpense AS b,ChartMaster AS v"
        String where = "a.glAccount = v.accountCode AND a.glAccount != '' AND a.budgetItemExpenseId = b.id AND b.bookingPeriodStartMonth >='" + fiscalYearInfo[0][3] +"' AND b.bookingPeriodStartYear >='" + fiscalYearInfo[0][4] + "' AND b.bookingPeriodEndMonth <='" + fiscalYearInfo[0][5] + "' AND b.bookingPeriodEndYear<='" + fiscalYearInfo[0][6]+"'"
        String groupBy = "a.glAccount"
        String orderBy = "a.glAccount"
        gridResultVendorArr = new BudgetViewDatabaseService().select(select,from,where,orderBy,groupBy,'false',selectIndex)

        ArrayList budgetVendorArr = gridResultVendorArr['dataGridList']

        return budgetVendorArr;
    }



    def ArrayList getResevationDataAHWise(fiscalYearInfo){

        LinkedHashMap gridResultReservationArr

        String select = "DISTINCT(a.gl_account) AS gl_account,v.account_name As account_name,SUM(a.total_price_without_vat) as totalBudAmt,b.budget_name_id"
        String selectIndex = "gl_account,account_name,totalBudAmt"
        String from = "reservation_budget_item_details AS a,reservation_budget_item AS b,chart_master AS v"
        String where = "a.gl_account = v.account_code AND a.gl_account != '' AND a.reserv_budget_item_id = b.id AND b.booking_period_month >='"+ fiscalYearInfo[0][3] +"' AND b.booking_period_year >='" + fiscalYearInfo[0][4] +"' AND b.booking_period_month <='" + fiscalYearInfo[0][5] + "' AND b.booking_period_year<='"+ fiscalYearInfo[0][6]+"'"
        String groupBy = "a.gl_account"
        String orderBy = "a.gl_account"
        gridResultReservationArr = new BudgetViewDatabaseService().select(select,from,where,orderBy,groupBy,'false',selectIndex)

        ArrayList budgetArr = gridResultReservationArr['dataGridList']

        return budgetArr;
    }

    def ArrayList getCustomerAccountData(fiscalYearInfo,budgetCustomerArr){

        ArrayList customerAccount = new ArrayList()

        for (int j = 0; j < budgetCustomerArr.size(); j++) {
            String customerId = budgetCustomerArr[j][0]

            LinkedHashMap gridResultCustomerViewArr
            String selectVendorViewArr = "a.customerId As temp_c_id,b.glAccount As temp_gl_account,c.accountName As temp_account_name," +
                    "a.bookingPeriodStartMonth As temp_b_p_s_m,SUM(b.totalPriceWithoutVat) As temp_t_p_w_v,COUNT(DISTINCT b.budgetItemIncomeId) " +
                    "As temp_b_i_id,b.budgetItemIncomeId AS bii_id,b.id AS biid_id";
            String selectIndexVendorViewArr = "temp_c_id,temp_gl_account,temp_account_name,temp_b_p_s_m,temp_t_p_w_v,temp_b_i_id,bii_id,biid_id"
            String fromVendorViewArr = "BudgetItemIncome AS a, BudgetItemIncomeDetails AS b,ChartMaster AS c "
            String whereVendorViewArr = "a.id=b.budgetItemIncomeId AND b.glAccount=c.accountCode AND a.bookingPeriodStartMonth >='" + fiscalYearInfo[0][3] +
                    "' AND a.bookingPeriodStartYear >='" + fiscalYearInfo[0][4] + "' AND a.bookingPeriodStartMonth <='" + fiscalYearInfo[0][5] +
                    "' AND a.bookingPeriodStartYear<='" + fiscalYearInfo[0][6] + "' AND a.customerId='" + customerId + "'";

            String orderByVendorViewArr = "b.glAccount"
            String groupByVendorViewArr = "b.budgetItemIncomeId,b.glAccount"
            gridResultCustomerViewArr = new BudgetViewDatabaseService().select(selectVendorViewArr,fromVendorViewArr,whereVendorViewArr,orderByVendorViewArr,groupByVendorViewArr,'true',selectIndexVendorViewArr)

            def customerAccountDetailsArr = gridResultCustomerViewArr['dataGridList']
            if (customerAccountDetailsArr.size() > 0) {
                customerAccount << customerAccountDetailsArr
            }
        }

        return customerAccount;
    }

    def ArrayList getPrivateBudgetAmountData(fiscalYearInfo,privateBudgetArr) {

        ArrayList privateBudgetAmount = new ArrayList()

        for (int j = 0; j < privateBudgetArr.size(); j++) {
            String budgetNameId = privateBudgetArr[j][0]

            LinkedHashMap gridResultPrivateViewArr
            String selectVendorViewArr = "pvi.budgetNameId As budgetNameId, pvi.bookingPeriod As bookingPeriod,pvi.totalAmount as totalAmount,pvi.id as budgetId";
            String selectIndexVendorViewArr = "budgetNameId,bookingPeriod,totalAmount,budgetId"
            String fromVendorViewArr = "PrivateBudgetItem AS pvi "
            String whereVendorViewArr = " pvi.booking_year = '" + fiscalYearInfo[0][4] + "' " + " AND pvi.budgetNameId='" + budgetNameId + "'";
            String orderByVendorViewArr = "pvi.budgetNameId"
            String groupByVendorViewArr = ""

            gridResultPrivateViewArr = new BudgetViewDatabaseService().select(selectVendorViewArr,fromVendorViewArr,whereVendorViewArr,orderByVendorViewArr,groupByVendorViewArr,'true',selectIndexVendorViewArr)

            def privateAmountDetailsArr = gridResultPrivateViewArr['dataGridList']
            if (privateAmountDetailsArr.size() > 0) {
                privateBudgetAmount << privateAmountDetailsArr
            }
        }

        return privateBudgetAmount;
    }


    def ArrayList getVendorAccountData(fiscalYearInfo,budgetVendorArr){

        ArrayList vendorAccountData = new ArrayList()

        for (int j = 0; j < budgetVendorArr.size(); j++) {
            def vendorId = budgetVendorArr[j][0]

            LinkedHashMap gridResultVendorViewArr;
            String selectVendorViewArr = "a.vendorId AS vi,b.glAccount AS ga,c.accountName AS an,a.bookingPeriodStartMonth AS bpsm,SUM(b.totalPriceWithoutVat) AS tpwv,COUNT(DISTINCT b.budgetItemExpenseId) AS biei,b.budgetItemExpenseId AS bie_id,b.id AS bied_id"
            String selectIndexVendorViewArr = "vi,ga,an,bpsm,tpwv,biei,bie_id,bied_id"
            String fromVendorViewArr = "BudgetItemExpense AS a,BudgetItemExpenseDetails AS b , ChartMaster AS c"
            String whereVendorViewArr = "a.id=b.budgetItemExpenseId AND b.glAccount=c.accountCode AND a.vendorId='" + vendorId +
                    "' AND a.bookingPeriodStartMonth >='" + fiscalYearInfo[0][3] +"' AND a.bookingPeriodStartYear >='" +
                    fiscalYearInfo[0][4] + "' AND a.bookingPeriodEndMonth <='" + fiscalYearInfo[0][5] +
                    "' AND a.bookingPeriodEndYear <='" + fiscalYearInfo[0][6] +"'"

            String orderByVendorViewArr = "b.glAccount"
            String groupByVendorViewArr = "b.budgetItemExpenseId,b.glAccount"
            gridResultVendorViewArr = new BudgetViewDatabaseService().select(selectVendorViewArr,fromVendorViewArr,whereVendorViewArr,orderByVendorViewArr,groupByVendorViewArr,'true',selectIndexVendorViewArr)

            def vendorAccountDetailsArr = gridResultVendorViewArr['dataGridList']

            if (vendorAccountDetailsArr.size() > 0){
                vendorAccountData << vendorAccountDetailsArr
            }
        }

        return vendorAccountData;
    }

    def ArrayList getReservationAccountData(fiscalYearInfo,budgetReservationArr){

        ArrayList resevationAccountData = new ArrayList()

        for (int j = 0; j < budgetReservationArr.size(); j++) {
            def budgetNameId = budgetReservationArr[j][0]

            LinkedHashMap gridResultReservationViewArr;
            String selectReservationViewArr = "a.budget_name_id AS vi,b.gl_account AS ga,c.account_name AS an,a.booking_period_month AS bpsm,SUM(b.total_price_without_vat) AS tpwv, COUNT(DISTINCT b.reserv_budget_item_id) AS biei,b.reserv_budget_item_id AS bie_id,b.id AS bied_id"
            String selectIndexReservationViewArr = "vi,ga,an,bpsm,tpwv,biei,bie_id,bied_id"
            String fromReservationViewArr = "reservation_budget_item AS a,reservation_budget_item_details AS b , chart_master AS c"
            String whereReservationViewArr = "a.id=b.reserv_budget_item_id AND b.gl_account=c.account_code AND a.budget_name_id='"+ budgetNameId +"' AND a.booking_period_month >='"+ fiscalYearInfo[0][3] +"' " +
                    "AND a.booking_period_year >='"+fiscalYearInfo[0][4]+"' AND a.booking_period_month <='"+ fiscalYearInfo[0][5] +"' AND a.booking_period_year <='" + fiscalYearInfo[0][6] +"'"

            String orderByReservationViewArr = "b.gl_account"
            String groupByReservationViewArr = "b.reserv_budget_item_id,b.gl_account"

            gridResultReservationViewArr = new BudgetViewDatabaseService().select(selectReservationViewArr,fromReservationViewArr,whereReservationViewArr,orderByReservationViewArr,groupByReservationViewArr,'true',selectIndexReservationViewArr)

            def reservationAccountDetailsArr = gridResultReservationViewArr['dataGridList']

            if (reservationAccountDetailsArr.size() > 0){
                resevationAccountData << reservationAccountDetailsArr
            }
        }

        return resevationAccountData;
    }


    def ArrayList getVendorBudgetAHWiseData(fiscalYearInfo,budgetVendorArr){

        ArrayList vendorAccountData = new ArrayList()

        for (int j = 0; j < budgetVendorArr.size(); j++) {
            def glAccount = budgetVendorArr[j][0]

            LinkedHashMap gridResultVendorViewArr
            String selectVendorViewArr = "b.glAccount AS ga,a.vendorId AS vi,c.vendorName AS vn,a.bookingPeriodStartMonth AS bpsm,SUM(b.totalPriceWithoutVat) AS stpwv,COUNT(DISTINCT b.budgetItemExpenseId) AS cbiei,b.budgetItemExpenseId AS biedi,b.id AS biei"
            String selectIndexVendorViewArr = "ga,vi,vn,bpsm,stpwv,cbiei,biedi,biei"
            String fromVendorViewArr = "BudgetItemExpense AS a,BudgetItemExpenseDetails AS b , VendorMaster AS c "
            String whereVendorViewArr = "a.id=b.budgetItemExpenseId AND a.vendorId = c.id AND b.glAccount='" + glAccount +
                    "' AND a.bookingPeriodStartMonth >= '" + fiscalYearInfo[0][3] +"' AND a.bookingPeriodStartYear >=' " + fiscalYearInfo[0][4] +
                    "' AND a.bookingPeriodEndMonth <= '" + fiscalYearInfo[0][5] + "' AND a.bookingPeriodEndYear <=' " + fiscalYearInfo[0][6] +"'"

            String orderByVendorViewArr = "b.glAccount,a.vendorId"
            String groupByVendorViewArr = "b.budgetItemExpenseId"

            gridResultVendorViewArr = new BudgetViewDatabaseService().select(selectVendorViewArr,fromVendorViewArr,whereVendorViewArr,orderByVendorViewArr,groupByVendorViewArr,'true',selectIndexVendorViewArr)

            def vendorAccountDetailsArr = gridResultVendorViewArr['dataGridList']

            if (vendorAccountDetailsArr.size() > 0){
                vendorAccountData << vendorAccountDetailsArr
            }
        }

        return vendorAccountData;
    }


    def ArrayList getReservationBudgetAHWiseData(fiscalYearInfo,budgetReservationArr){

        ArrayList reservationAccountData = new ArrayList()

        for (int j = 0; j < budgetReservationArr.size(); j++) {
            def glAccount = budgetReservationArr[j][0]

            LinkedHashMap gridResultReservationViewArr
            String selectReservationViewArr = "b.gl_account AS ga,a.budget_name_id AS vi,c.reservation_name AS vn,a.booking_period_month AS bpsm, SUM(b.total_price_without_vat) AS stpwv,COUNT(DISTINCT b.reserv_budget_item_id) AS cbiei,b.reserv_budget_item_id AS biedi,b.id AS biei"
            String selectIndexReservationViewArr = "ga,vi,vn,bpsm,stpwv,cbiei,biedi,biei"
            String fromReservationViewArr = "reservation_budget_item AS a,reservation_budget_item_details AS b , reservation_budget_master AS c "

            String whereReservationViewArr = "a.id=b.reserv_budget_item_id AND a.budget_name_id = c.id AND b.gl_account='"+ glAccount +"' AND a.booking_period_month >= '"+ fiscalYearInfo[0][3] +"'" +
                    " AND a.booking_period_year >='"+ fiscalYearInfo[0][4] +"' AND a.booking_period_month <= '"+ fiscalYearInfo[0][5] +"' " +
                    "AND a.booking_period_year <='"+ fiscalYearInfo[0][6] +"'"

            String orderByReservationViewArr = "b.gl_account,a.budget_name_id"
            String groupByReservationViewArr = "b.reserv_budget_item_id"

            gridResultReservationViewArr = new BudgetViewDatabaseService().select(selectReservationViewArr,fromReservationViewArr,whereReservationViewArr,orderByReservationViewArr,groupByReservationViewArr,'true',selectIndexReservationViewArr)

            def reservationAccountDetailsArr = gridResultReservationViewArr['dataGridList']

            if (reservationAccountDetailsArr.size() > 0){
                reservationAccountData << reservationAccountDetailsArr
            }
        }

        return reservationAccountData;
    }


    def ArrayList getCustomerBudgetAHWiseData(fiscalYearInfo,budgetCustomerArr){

        ArrayList customerAccountData = new ArrayList()

        for (int j = 0; j < budgetCustomerArr.size(); j++) {
            def glAccount = budgetCustomerArr[j][0]

            LinkedHashMap gridResultCustomerViewArr

            String selectCustomerViewArr = "b.glAccount AS ga,a.customerId AS ci,c.customerName AS cn,a.bookingPeriodStartMonth AS bpsm,SUM(b.totalPriceWithoutVat) AS tpwv,COUNT(DISTINCT b.budgetItemIncomeId) AS cbiii,b.budgetItemIncomeId AS biidi,b.id AS biii"
            String selectIndexCustomerViewArr = "ga,ci,cn,bpsm,tpwv,cbiii,biidi,biii"
            String fromCustomerViewArr = "BudgetItemIncome AS a,BudgetItemIncomeDetails AS b ,CustomerMaster AS c"

            String whereCustomerViewArr = "a.id = b.budgetItemIncomeId AND a.customerId = c.id AND b.glAccount='" + glAccount +
                    "' AND a.bookingPeriodStartMonth >= '" + fiscalYearInfo[0][3] +"' AND a.bookingPeriodStartYear >=' " + fiscalYearInfo[0][4] +
                    "' AND a.bookingPeriodEndMonth <= '" + fiscalYearInfo[0][5] + "' AND a.bookingPeriodEndYear <=' " + fiscalYearInfo[0][6] +"'"

            String orderByCustomerViewArr = "b.glAccount,a.customerId"
            String groupByCustomerViewArr = "b.budgetItemIncomeId"
            gridResultCustomerViewArr = new BudgetViewDatabaseService().select(selectCustomerViewArr,fromCustomerViewArr,whereCustomerViewArr,orderByCustomerViewArr,groupByCustomerViewArr,'true',selectIndexCustomerViewArr)

            def customerAccountDataArr = gridResultCustomerViewArr['dataGridList']

            if (customerAccountDataArr.size() > 0){
                customerAccountData << customerAccountDataArr
            }
        }

        return customerAccountData;
    }

    def ArrayList  getIncomeInvoiceData(fiscalYearInfo,budgetCustomerArr){

        ArrayList invoiceIncomeDetails = new ArrayList()

        for (int j = 0; j < budgetCustomerArr.size(); j++) {
            Integer customerId = budgetCustomerArr[j][0]
            LinkedHashMap gridResultInvoiceIncomeDetailsArr;

            String selectVendorInvoiceViewArr = "a.budgetCustomerId As temp_b_c_id,b.accountCode As temp_a_code,c.accountName As temp_a_name,a.bookingPeriod As temp_b_period,SUM(b.totalAmountWithoutVat) As temp_tawv,COUNT(DISTINCT b.invoiceId)  As temp_i_id"
            String selectIndexVendorInvoiceViewArr = "temp_b_c_id,temp_a_code,temp_a_name,temp_b_period,temp_tawv,temp_i_id"
            String fromVendorInvoiceViewArr = "InvoiceIncome AS a,InvoiceIncomeDetails AS b,ChartMaster AS c "
            String whereVendorInvoiceViewArr = "a.budgetCustomerId='" + customerId + "'  AND a.id = b.invoiceId AND b.accountCode = c.accountCode AND a.bookingYear='" + fiscalYearInfo[0][4] + "'"
            String orderByVendorInvoiceViewArr = "b.invoiceId,b.accountCode,a.budgetCustomerId"
            String groupByVendorInvoiceViewArr = "b.invoiceId,b.accountCode"

            gridResultInvoiceIncomeDetailsArr = new BudgetViewDatabaseService().select(selectVendorInvoiceViewArr,fromVendorInvoiceViewArr,whereVendorInvoiceViewArr,orderByVendorInvoiceViewArr,groupByVendorInvoiceViewArr,'true',selectIndexVendorInvoiceViewArr)
            def invoiceIncomeDetailsArr = gridResultInvoiceIncomeDetailsArr['dataGridList']

            if (invoiceIncomeDetailsArr.size() > 0){
                invoiceIncomeDetails << invoiceIncomeDetailsArr
            }
        }

        return invoiceIncomeDetails;
    }

    def Map getProcessDataForPrivateWDC(fiscalYearInfo){

        Map monthlyAmount = ["janAmount": 0, "febAmount": 0, "marAmount": 0, "aprAmount": 0, "mayAmount": 0, "junAmount": 0,
                             "julAmount": 0, "augAmount": 0, "sepAmount": 0, "octAmount": 0, "novAmount": 0, "decAmount": 0,
                             "allTotal": 0];


        String selectField="bookingPeriod AS bp,SUM(amount) AS totalAmount"
        String selectIndexField="bp,totalAmount"
        String fromTableName="TransMaster "
        String whereCondition="bookingYear ='" + fiscalYearInfo[0][4] + "' AND trans_type IN (10,6) AND account_code = '1410' "
        String orderByFields=""
        String groupByFields="bookingPeriod"

        LinkedHashMap gridResultProcessAmountArr
        gridResultProcessAmountArr = new BudgetViewDatabaseService().select(selectField,fromTableName,whereCondition,orderByFields,groupByFields,'true',selectIndexField)

        ArrayList processAmountArr = gridResultProcessAmountArr['dataGridList']

        def allTotal = 0.00;
        processAmountArr.eachWithIndex {arrayDataIndex, arrayKey ->
            if (arrayDataIndex[0] == 1){
                monthlyAmount.janAmount = dashboardDetailsTagLib.getRoundedValue(arrayDataIndex[1])
                allTotal += monthlyAmount.janAmount;
            }
            else if (arrayDataIndex[0] == 2) {
                monthlyAmount.febAmount = dashboardDetailsTagLib.getRoundedValue(arrayDataIndex[1])
                allTotal += monthlyAmount.febAmount;
            }
            else if (arrayDataIndex[0] == 3) {
                monthlyAmount.marAmount = dashboardDetailsTagLib.getRoundedValue(arrayDataIndex[1])
                allTotal += monthlyAmount.marAmount;
            }
            else if (arrayDataIndex[0] == 4) {
                monthlyAmount.aprAmount = dashboardDetailsTagLib.getRoundedValue(arrayDataIndex[1])
                allTotal += monthlyAmount.aprAmount;
            }
            else if (arrayDataIndex[0] == 5) {
                monthlyAmount.mayAmount = dashboardDetailsTagLib.getRoundedValue(arrayDataIndex[1])
                allTotal += monthlyAmount.mayAmount;
            }
            else if (arrayDataIndex[0] == 6) {
                monthlyAmount.junAmount = dashboardDetailsTagLib.getRoundedValue(arrayDataIndex[1])
                allTotal += monthlyAmount.junAmount;
            }
            else if (arrayDataIndex[0] == 7) {
                monthlyAmount.julAmount = dashboardDetailsTagLib.getRoundedValue(arrayDataIndex[1])
                allTotal += monthlyAmount.julAmount;
            }
            else if (arrayDataIndex[0] == 8) {
                monthlyAmount.augAmount = dashboardDetailsTagLib.getRoundedValue(arrayDataIndex[1])
                allTotal += monthlyAmount.augAmount;
            }
            else if (arrayDataIndex[0] == 9) {
                monthlyAmount.sepAmount = dashboardDetailsTagLib.getRoundedValue(arrayDataIndex[1])
                allTotal += monthlyAmount.sepAmount;
            }
            else if (arrayDataIndex[0] == 10) {
                monthlyAmount.octAmount = dashboardDetailsTagLib.getRoundedValue(arrayDataIndex[1])
                allTotal += monthlyAmount.octAmount;
            }
            else if (arrayDataIndex[0] == 11) {
                monthlyAmount.novAmount = dashboardDetailsTagLib.getRoundedValue(arrayDataIndex[1])
                allTotal += monthlyAmount.novAmount;
            }
            else if (arrayDataIndex[0] == 12) {
                monthlyAmount.decAmount = dashboardDetailsTagLib.getRoundedValue(arrayDataIndex[1])
                allTotal += monthlyAmount.decAmount;
            }

        }

        monthlyAmount.allTotal = allTotal

        return monthlyAmount;
    }

    def List<Map>  getProcessDataForPrivate(fiscalYearInfo,privateBudgetArr) {

        ArrayList invoicePrivateDetails = new ArrayList()

        for (int j = 0; j < privateBudgetArr.size(); j++) {
            Integer budgetNameId = privateBudgetArr[j][0]
            Integer transType = 6
            LinkedHashMap gridResultinvoicePrivateDetailsArr;

            String selectVendorInvoiceViewArr = "a.budgetMasterId AS budgetMasterId,a.bookingPeriod AS bookingPeriod,a.amount AS amount,a.id as id"
            String selectIndexVendorInvoiceViewArr = "budgetMasterId,bookingPeriod,amount,id"
            String fromVendorInvoiceViewArr = "PrivateReservationSpendingTrans AS a"
            String whereVendorInvoiceViewArr = "a.trans_type = '" + transType + "' AND a.budget_master_id = '" + budgetNameId + "' AND a.booking_year=   '" + fiscalYearInfo[0][4] + "'"
            String orderByVendorInvoiceViewArr = "a.budget_master_id"
            String groupByVendorInvoiceViewArr = ""

            gridResultinvoicePrivateDetailsArr = new BudgetViewDatabaseService().select(selectVendorInvoiceViewArr,fromVendorInvoiceViewArr,whereVendorInvoiceViewArr,orderByVendorInvoiceViewArr,groupByVendorInvoiceViewArr,'true',selectIndexVendorInvoiceViewArr)
            def invoicePrivateDetailsArr = gridResultinvoicePrivateDetailsArr['dataGridList']

            if (invoicePrivateDetailsArr.size() > 0){
                invoicePrivateDetails << invoicePrivateDetailsArr
            }
        }

        return invoicePrivateDetails;
    }

    def setTotalIncomeInvoiceAmount(fiscalYearInfo,budgetCustomerArr){

        int nSize = budgetCustomerArr.size();
        for (int j = 0; j < nSize; j++) {
            Integer customerId = budgetCustomerArr[j][0]
            //Total invoice amount.
            String strQuery = "SELECT SUM(ii.total_gl_amount) as totalInvAmt from invoice_income as ii WHERE ii.booking_year = " +
                    " '" + fiscalYearInfo[0][4] + "' AND ii.budget_customer_id = " + customerId +
                    " AND status=1 AND isReverse=0 AND reverseInvoiceId=0 GROUP BY ii.budget_customer_id;"

            def gridResultInvoiceIncomeTotalArr = new BudgetViewDatabaseService().executeQueryAtSingle(strQuery);
            if (gridResultInvoiceIncomeTotalArr.size() > 0) {
                budgetCustomerArr[j][4] = gridResultInvoiceIncomeTotalArr[0];
            } else {
                budgetCustomerArr[j][4] = 0.0;
            }
        }
    }

    def setTotalInvPrivateAmount(fiscalYearInfo,privateBudgetArr) {

        int nSize = privateBudgetArr.size();
        for (int j = 0; j < nSize; j++) {
            Integer budgetNameId = privateBudgetArr[j][0]
            //Total amount.
            String strQuery = "SELECT ROUND(SUM(prst.amount),2) as totalInvAmt from private_reservation_spending_trans as prst " +
                    "WHERE prst.booking_year =  '" + fiscalYearInfo[0][4] + "' AND prst.budget_master_id = " + budgetNameId +
                    " AND status=1 AND trans_type=6 GROUP BY prst.budget_master_id;"

            def gridResultTotalAmountArr = new BudgetViewDatabaseService().executeQueryAtSingle(strQuery);

            if (gridResultTotalAmountArr.size() > 0) {
//                privateBudgetArr[j][5] = gridResultTotalAmountArr[0];
                privateBudgetArr[j][6] = gridResultTotalAmountArr[0];
            } else {
//                privateBudgetArr[j][5] = 0.0;
                privateBudgetArr[j][6] = 0.0;
            }
        }
    }

    def ArrayList getExpenseInvoiceData(fiscalYearInfo,budgetVendorArr){

        ArrayList invoiceExpenseDetails = new ArrayList()

        for (int j = 0; j < budgetVendorArr.size(); j++) {
            def vendorId = budgetVendorArr[j][0]

            LinkedHashMap gridResultVendorInvoiceViewArr
            String selectVendorInvoiceViewArr = "a.budgetVendorId AS bvi,b.accountCode AS ac,c.accountName AS an,a.bookingPeriod AS bp,SUM(b.totalAmountWithoutVat) AS stawov,COUNT(DISTINCT b.invoiceId) AS cii"
            String selectIndexVendorInvoiceViewArr = "bvi,ac,an,bp,stawov,cii"
            String fromVendorInvoiceViewArr = "InvoiceExpense AS a,InvoiceExpenseDetails AS b,ChartMaster AS c"
            String whereVendorInvoiceViewArr = "a.budgetVendorId='" + vendorId + "'  AND a.id=b.invoiceId AND b.accountCode=c.accountCode AND a.bookingYear=" + fiscalYearInfo[0][4]
            String orderByVendorInvoiceViewArr = "b.invoiceId,b.accountCode,a.vendorId"
            String groupByVendorInvoiceViewArr = "b.invoiceId,b.accountCode"
            gridResultVendorInvoiceViewArr = new BudgetViewDatabaseService().select(selectVendorInvoiceViewArr,fromVendorInvoiceViewArr,whereVendorInvoiceViewArr,orderByVendorInvoiceViewArr,groupByVendorInvoiceViewArr,'true',selectIndexVendorInvoiceViewArr)

            def vendorInvoiceViewArr = gridResultVendorInvoiceViewArr['dataGridList']
            if (vendorInvoiceViewArr.size() > 0) {
                invoiceExpenseDetails << vendorInvoiceViewArr
            }

        }

        return invoiceExpenseDetails;
    }

    def ArrayList getReservationData(fiscalYearInfo,budgetReservationArr){

        ArrayList reservationDetails = new ArrayList()

        for (int j = 0; j < budgetReservationArr.size(); j++) {
            def budgetNameId = budgetReservationArr[j][0]

            LinkedHashMap gridResultInvoiceViewArr
            String selectInvoiceViewArr = "a.budget_master_id AS bvi,a.tracking_number AS ac ,a.booking_period AS bp,a.amount AS stawov, a.budget_item_id AS cii"
            String selectIndexInvoiceViewArr = "bvi,ac,bp,stawov,cii"
            String fromInvoiceViewArr = "private_reservation_spending_trans AS a"
            String whereInvoiceViewArr = "a.budget_master_id='" + budgetNameId + "' AND trans_type = '5'  AND a.booking_year=" + fiscalYearInfo[0][4]
//            String whereVendorInvoiceViewArr = "a.budgetVendorId='" + vendorId + "'  AND a.id=b.invoiceId AND b.accountCode=c.accountCode AND a.bookingYear=" + fiscalYearInfo[0][4]
            String orderByInvoiceViewArr = "a.budget_master_id"
            String groupByInvoiceViewArr = ""
//            String groupByInvoiceViewArr = "a.budget_master_id"


            gridResultInvoiceViewArr = new BudgetViewDatabaseService().select(selectInvoiceViewArr,fromInvoiceViewArr,whereInvoiceViewArr,orderByInvoiceViewArr,groupByInvoiceViewArr,'true',selectIndexInvoiceViewArr)

            def InvoiceViewArr = gridResultInvoiceViewArr['dataGridList']
            if (InvoiceViewArr.size() > 0) {
                reservationDetails << InvoiceViewArr
            }

        }

        return reservationDetails;
    }



    def ArrayList getIncomeInvoiceDataAHWise(fiscalYearInfo,budgetCustomerArr){

        ArrayList invoiceIncomeDetails = new ArrayList()

        for (int j = 0; j < budgetCustomerArr.size(); j++) {
            def glAccount = budgetCustomerArr[j][0]

            LinkedHashMap gridResultCustomerInvoiceViewArr
            String selectCustomerInvoiceViewArr = "b.accountCode AS ac, a.budgetCustomerId AS bvi, c.accountName AS an,a.bookingPeriod AS bp,SUM(b.totalAmountWithoutVat) AS stawv,COUNT(DISTINCT b.invoiceId) AS cii"
            String selectIndexCustomerInvoiceViewArr = "ac,bvi,an,bp,stawv,cii"
            String fromCustomerInvoiceViewArr = "InvoiceIncome AS a,InvoiceIncomeDetails AS b,ChartMaster AS c"
            String whereCustomerInvoiceViewArr = "b.accountCode='" + glAccount + "' AND a.id=b.invoiceId AND b.accountCode=c.accountCode AND a.bookingYear='"+fiscalYearInfo[0][4]+"'"
            String orderByCustomerInvoiceViewArr = "a.budgetCustomerId,b.accountCode,b.invoiceId"
            String groupByCustomerInvoiceViewArr = "b.invoiceId"

            gridResultCustomerInvoiceViewArr = new BudgetViewDatabaseService().select(selectCustomerInvoiceViewArr,fromCustomerInvoiceViewArr,whereCustomerInvoiceViewArr,orderByCustomerInvoiceViewArr,groupByCustomerInvoiceViewArr,'true',selectIndexCustomerInvoiceViewArr)

            def customerInvoiceViewArr = gridResultCustomerInvoiceViewArr['dataGridList']

            if (customerInvoiceViewArr.size() > 0) {
                invoiceIncomeDetails << customerInvoiceViewArr
            }
        }

        return invoiceIncomeDetails;
    }

    def ArrayList getExpenseInvoiceDataAHWise(fiscalYearInfo,budgetVendorArr){

        ArrayList invoiceExpenseDetails = new ArrayList()

        for (int j = 0; j < budgetVendorArr.size(); j++) {
            def glAccount = budgetVendorArr[j][0]

            LinkedHashMap gridResultVendorInvoiceViewArr
            String selectVendorInvoiceViewArr = "b.accountCode AS ac, a.budgetVendorId AS bvi, c.accountName AS an,a.bookingPeriod AS bp,SUM(b.totalAmountWithoutVat) AS stawv,COUNT(DISTINCT b.invoiceId) AS cii"
            String selectIndexVendorInvoiceViewArr = "ac,bvi,an,bp,stawv,cii"
            String fromVendorInvoiceViewArr = "InvoiceExpense AS a,InvoiceExpenseDetails AS b,ChartMaster AS c"
            String whereVendorInvoiceViewArr = "b.accountCode='" + glAccount + "' AND a.id=b.invoiceId AND b.accountCode=c.accountCode AND a.bookingYear='"+fiscalYearInfo[0][4]+"'"
            String orderByVendorInvoiceViewArr = "a.budgetVendorId,b.accountCode,b.invoiceId"
            String groupByVendorInvoiceViewArr = "b.invoiceId"
            gridResultVendorInvoiceViewArr = new BudgetViewDatabaseService().select(selectVendorInvoiceViewArr,fromVendorInvoiceViewArr,whereVendorInvoiceViewArr,orderByVendorInvoiceViewArr,groupByVendorInvoiceViewArr,'true',selectIndexVendorInvoiceViewArr)

            def vendorInvoiceViewArr = gridResultVendorInvoiceViewArr['dataGridList']

            if (vendorInvoiceViewArr.size() > 0) {
                invoiceExpenseDetails << vendorInvoiceViewArr
            }
        }

        return invoiceExpenseDetails;
    }


    def ArrayList getReservationInvoiceDataAHWise(fiscalYearInfo,budgetReservationArr){

        ArrayList invoiceReservationDetails = new ArrayList()

        for (int j = 0; j < budgetReservationArr.size(); j++) {
            def budgetNameId = budgetReservationArr[j][3]

            LinkedHashMap gridResultReservationInvoiceViewArr
            String selectReservationInvoiceViewArr = "a.budget_master_id AS bvi,a.tracking_number AS ac ,a.booking_period AS bp,SUM(a.amount) AS stawov,COUNT(DISTINCT a.budget_item_id) AS cii"
            String selectIndexReservationInvoiceViewArr = "bvi,ac,bp,stawov,cii"
            String fromReservationInvoiceViewArr = "private_reservation_spending_trans AS a"
            String whereReservationInvoiceViewArr = "a.budget_master_id='" + budgetNameId + "' AND trans_type = '5'  AND a.booking_year=" + fiscalYearInfo[0][4]
            String orderByReservationInvoiceViewArr = "a.budget_master_id"
            String groupByReservationInvoiceViewArr = "a.budget_master_id"
            gridResultReservationInvoiceViewArr = new BudgetViewDatabaseService().select(selectReservationInvoiceViewArr,fromReservationInvoiceViewArr,whereReservationInvoiceViewArr,orderByReservationInvoiceViewArr,groupByReservationInvoiceViewArr,'true',selectIndexReservationInvoiceViewArr)

            def reservationInvoiceViewArr = gridResultReservationInvoiceViewArr['dataGridList']

            if (reservationInvoiceViewArr.size() > 0) {
                invoiceReservationDetails << reservationInvoiceViewArr
            }
        }

        return invoiceReservationDetails;
    }


    def setTotalExpenseInvoiceAmount(fiscalYearInfo,budgetVendorArr){

        for (int j = 0; j < budgetVendorArr.size(); j++) {
            def vendorId = budgetVendorArr[j][0]
            //Total invoice amount.
            String strQuery = "SELECT SUM(ie.total_gl_amount) as totalInvAmt from invoice_expense as ie WHERE ie.booking_year = " +
                    " '" + fiscalYearInfo[0][4] + "' AND ie.budget_vendor_id = " + vendorId +
                    " AND status=1 AND isReverse=0 AND reverseInvoiceId=0 GROUP BY ie.budget_vendor_id;"

            def gridResultTotalInvoiceArr = new BudgetViewDatabaseService().executeQueryAtSingle(strQuery);
            if (gridResultTotalInvoiceArr.size() > 0) {
                budgetVendorArr[j][4] = gridResultTotalInvoiceArr[0];
            } else {
                budgetVendorArr[j][4] = 0.0;
            }
        }
    }


    def setTotalReservationAmount(fiscalYearInfo,budgetResevationArr){

        for (int j = 0; j < budgetResevationArr.size(); j++) {
            def budgetNameId = budgetResevationArr[j][0]
            //Total invoice amount.
            String strQuery = """SELECT SUM(ie.amount) as totalInvAmt from private_reservation_spending_trans as ie
                WHERE ie.booking_year =  '${fiscalYearInfo[0][4]}' AND ie.budget_master_id = ${budgetNameId} AND status=1 AND trans_type=5
                GROUP BY ie.budget_master_id;"""

            /* String strQuery = """SELECT SUM(ie.amount) as totalInvAmt from private_reservation_spending_trans as ie
                 WHERE ie.booking_year =  '${fiscalYearInfo[0][4]}' AND ie.budget_master_id = ${budgetNameId} AND status=1 AND trans_type=5
                 GROUP BY ie.budget_master_id;"""*/

            def gridResultTotalInvoiceArr = new BudgetViewDatabaseService().executeQueryAtSingle(strQuery);
            if (gridResultTotalInvoiceArr.size() > 0) {
                budgetResevationArr[j][4] = gridResultTotalInvoiceArr[0];
            } else {
                budgetResevationArr[j][4] = 0.0;
            }
        }
    }

    def setTotalExpenseInvoiceAmountAHWise(fiscalYearInfo,expenseAHWiseArr){

        for (int j = 0; j < expenseAHWiseArr.size(); j++) {
            def accountCode = expenseAHWiseArr[j][0]
            //Total invoice amount.
            String strQuery = "SELECT SUM(ied.total_amount_without_vat) as totalInvAmt from invoice_expense_details as ied " +
                    "INNER JOIN invoice_expense as ie ON ied.invoice_id = ie.id  " +
                    "WHERE ie.booking_year = '"+ fiscalYearInfo[0][4]  +"' AND ied.account_code = '" + accountCode +
                    "' AND ie.status=1 AND ie.is_reverse=0 AND ie.reverse_invoice_id=0 GROUP BY ied.account_code;"

            def gridResultTotalInvoiceArr = new BudgetViewDatabaseService().executeQueryAtSingle(strQuery);
            if (gridResultTotalInvoiceArr.size() > 0) {
                expenseAHWiseArr[j][3] = gridResultTotalInvoiceArr[0];
            } else {
                expenseAHWiseArr[j][3] = 0.0;
            }
        }
    }


    def setTotalReservationInvoiceAmountAHWise(fiscalYearInfo,reservationAHWiseArr){

        for (int j = 0; j < reservationAHWiseArr.size(); j++) {
            def budgetNameId = reservationAHWiseArr[j][3]
            //Total invoice amount.
            String strQuery = """SELECT SUM(ie.amount) as totalInvAmt from private_reservation_spending_trans as ie
                WHERE ie.booking_year =  '${fiscalYearInfo[0][4]}' AND ie.budget_master_id = ${budgetNameId} AND status=1 AND trans_type=5
                GROUP BY ie.budget_master_id;"""

            def gridResultTotalInvoiceArr = new BudgetViewDatabaseService().executeQueryAtSingle(strQuery);
            if (gridResultTotalInvoiceArr.size() > 0) {
                reservationAHWiseArr[j][4] = gridResultTotalInvoiceArr[0];
            } else {
                reservationAHWiseArr[j][4] = 0.0;
            }
        }
    }



    def setTotalIncomeInvoiceAmountAHWise(fiscalYearInfo,incomeAHWiseArr){

        for (int j = 0; j < incomeAHWiseArr.size(); j++) {
            def accountCode = incomeAHWiseArr[j][0]
            //Total invoice amount.
            String strQuery = "SELECT SUM(iid.total_amount_without_vat) as totalInvAmt from invoice_income_details as iid " +
                    "INNER JOIN invoice_income as ii ON iid.invoice_id = ii.id  " +
                    "WHERE ii.booking_year = '"+ fiscalYearInfo[0][4]  +"' AND iid.account_code = '" + accountCode +
                    "' AND ii.status=1 AND ii.is_reverse=0 AND ii.reverse_invoice_id=0 GROUP BY iid.account_code;"

            def gridResultTotalInvoiceArr = new BudgetViewDatabaseService().executeQueryAtSingle(strQuery);
            if (gridResultTotalInvoiceArr.size() > 0) {
                incomeAHWiseArr[j][3] = gridResultTotalInvoiceArr[0];
            } else {
                incomeAHWiseArr[j][3] = 0.0;
            }
        }
    }

    def getBudgetIncomeForecastData(fiscalYearInfo){

        LinkedHashMap gridResultBudgetForecastArr

        String selectBudgetForecastArr = "id,SUM(totalGlAmount) AS total_amount,bookingPeriodStartMonth As bpsm"
        String selectIndexBudgetForecastArr = "id,total_amount,bpsm"
        String fromBudgetForecastArr = "BudgetItemIncome"
        String whereBudgetForecastArr = "bookingPeriodStartYear='"+fiscalYearInfo[0][4] +"' AND status=1"
        String orderByBudgetForecastArr = "bookingPeriodStartMonth,id"
        String groupByBudgetForecastArr = "bookingPeriodStartMonth,id"

        gridResultBudgetForecastArr = new BudgetViewDatabaseService().select(selectBudgetForecastArr,fromBudgetForecastArr,whereBudgetForecastArr,orderByBudgetForecastArr,groupByBudgetForecastArr,'true',selectIndexBudgetForecastArr)

        def budgetForecastArr =  gridResultBudgetForecastArr['dataGridList']

        return budgetForecastArr;
    }


    def getBudgetReservationForecastData(fiscalYearInfo){

        LinkedHashMap gridResultBudgetForecastArr

        String selectBudgetForecastArr = "id,SUM(totalAmount) AS total_amount,bookingPeriodMonth As bpm"
        String selectIndexBudgetForecastArr = "id,totalAmount,bpm"
        String fromBudgetForecastArr = "reservationBudgetItem"
        String whereBudgetForecastArr = "bookingPeriodYear='2015' AND `status`=1"
        String orderByBudgetForecastArr = "bookingPeriodMonth,id"
        String groupByBudgetForecastArr = "bookingPeriodMonth,id"

        gridResultBudgetForecastArr = new BudgetViewDatabaseService().select(selectBudgetForecastArr,fromBudgetForecastArr,whereBudgetForecastArr,orderByBudgetForecastArr,groupByBudgetForecastArr,'true',selectIndexBudgetForecastArr)

        def budgetForecastArr =  gridResultBudgetForecastArr['dataGridList']

        return budgetForecastArr;
    }

    def getBudgetIncomeForecastDataForPrivate(fiscalYearInfo){

        LinkedHashMap gridResultBudgetForecastArr

        String selectBudgetForecastArr = "pb.id as id,SUM(pb.total_amount) AS total_amount,pb.booking_period AS bpsm "
        String selectIndexBudgetForecastArr = "id,total_amount,bpsm"
        String fromBudgetForecastArr = "private_budget_item pb INNER JOIN private_budget_master pm ON pb.budget_name_id = pm.id "
        String whereBudgetForecastArr = "pb.booking_year ='"+fiscalYearInfo[0][4] +"' AND pb.status=1 AND pm.budget_type = 1"
        String orderByBudgetForecastArr = "booking_period,id"
        String groupByBudgetForecastArr = "booking_period,id"

        gridResultBudgetForecastArr = new BudgetViewDatabaseService().select(selectBudgetForecastArr,fromBudgetForecastArr,whereBudgetForecastArr,orderByBudgetForecastArr,groupByBudgetForecastArr,'true',selectIndexBudgetForecastArr)

        def budgetForecastArr =  gridResultBudgetForecastArr['dataGridList']

        return budgetForecastArr;
    }


    def getInvoiceIncomeForecastData(fiscalYearInfo){

        LinkedHashMap gridResultInvoiceForecastArr
        String selectInvoiceForecastArr = "budgetItemIncomeId AS bi_id,SUM(totalGlAmount) AS total_amount,bookingPeriod As bp"
        String selectIndexInvoiceForecastArr = "bi_id,total_amount,bp"
        String fromInvoiceForecastArr = "InvoiceIncome"
        String whereInvoiceForecastArr = "bookingYear='" + fiscalYearInfo[0][4] + "'  AND status=1 AND isReverse=0 AND reverseInvoiceId=0"
        String orderByInvoiceForecastArr = "bookingPeriod,budgetItemIncomeId"
        String groupByInvoiceForecastArr = "bookingPeriod,budgetItemIncomeId"
        gridResultInvoiceForecastArr = new BudgetViewDatabaseService().select(selectInvoiceForecastArr,fromInvoiceForecastArr,whereInvoiceForecastArr,orderByInvoiceForecastArr,groupByInvoiceForecastArr,'true',selectIndexInvoiceForecastArr)

        def InvoiceForecastArr = gridResultInvoiceForecastArr['dataGridList']

        return InvoiceForecastArr;
    }

    def getInvoiceReservationForecastData(fiscalYearInfo){

        LinkedHashMap gridResultInvoiceForecastArr
        String selectInvoiceForecastArr = "budgetItemIncomeId AS bi_id,SUM(totalGlAmount) AS total_amount,bookingPeriod As bp"
        String selectIndexInvoiceForecastArr = "bi_id,total_amount,bp"
        String fromInvoiceForecastArr = "InvoiceIncome"
        String whereInvoiceForecastArr = "bookingYear='" + fiscalYearInfo[0][4] + "'  AND status=1 AND isReverse=0 AND reverseInvoiceId=0"
        String orderByInvoiceForecastArr = "bookingPeriod,budgetItemIncomeId"
        String groupByInvoiceForecastArr = "bookingPeriod,budgetItemIncomeId"
        gridResultInvoiceForecastArr = new BudgetViewDatabaseService().select(selectInvoiceForecastArr,fromInvoiceForecastArr,whereInvoiceForecastArr,orderByInvoiceForecastArr,groupByInvoiceForecastArr,'true',selectIndexInvoiceForecastArr)

        def InvoiceForecastArr = gridResultInvoiceForecastArr['dataGridList']

        return InvoiceForecastArr;
    }

    def getInvoiceIncomeForecastDataForPrivate(fiscalYearInfo){

        LinkedHashMap gridResultInvoiceForecastArr
        String selectInvoiceForecastArr = "ps.budget_master_id AS bi_id,Sum(ps.amount) AS total_amount, ps.booking_period AS bp"
        String selectIndexInvoiceForecastArr = "bi_id,total_amount,bp"
        String fromInvoiceForecastArr = "private_reservation_spending_trans  ps INNER JOIN private_budget_master pm ON ps.budget_master_id = pm.id "
        String whereInvoiceForecastArr = "ps.booking_year ='" + fiscalYearInfo[0][4] + "'  AND ps.`status` = 1 AND pm.budget_type = 1"
//        String whereInvoiceForecastArr = "bookingYear='" + fiscalYearInfo[0][4] + "'  AND status=1 AND isReverse=0 AND reverseInvoiceId=0"
        String orderByInvoiceForecastArr = "ps.booking_period,ps.budget_master_id"
        String groupByInvoiceForecastArr = "ps.booking_period,ps.budget_master_id"
        gridResultInvoiceForecastArr = new BudgetViewDatabaseService().select(selectInvoiceForecastArr,fromInvoiceForecastArr,whereInvoiceForecastArr,orderByInvoiceForecastArr,groupByInvoiceForecastArr,'true',selectIndexInvoiceForecastArr)

        def InvoiceForecastArr = gridResultInvoiceForecastArr['dataGridList']

        return InvoiceForecastArr;
    }

    def Map prepareCurrentForecastForIncomeNameWise(budgetForecastArr,invoiceForecastArr){

        Map currentForecastArr = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                  "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal":0.00,"allTotal":0.00]

        def allTotal = 0.00
        budgetForecastArr.eachWithIndex {BudgetForecastIndex, BudgetForecastKey ->
            def tempBudetId =  BudgetForecastIndex[0]
            def tempBudetAmount =  BudgetForecastIndex[1]
            def tempBudetPeriod =  BudgetForecastIndex[2]

            Integer flagFound = 0

            invoiceForecastArr.eachWithIndex {InvoiceForecastIndex, InvoiceForecastKey ->

                def tempInvoiceId =  InvoiceForecastIndex[0]
                def tempInvoiceAmount =  InvoiceForecastIndex[1]
                def tempInvoicePeriod =  InvoiceForecastIndex[2]

                if (tempBudetId == tempInvoiceId){

                    if (Integer.parseInt(tempInvoicePeriod)==1){
                        currentForecastArr.janTotal= currentForecastArr.janTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.janTotal;
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==2){
                        currentForecastArr.febTotal=  currentForecastArr.febTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.febTotal;
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==3){
                        currentForecastArr.marTotal= currentForecastArr.marTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.marTotal;
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==4){
                        currentForecastArr.aprTotal= currentForecastArr.aprTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.aprTotal;
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==5){
                        currentForecastArr.mayTotal=  currentForecastArr.mayTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.mayTotal;
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==6){
                        currentForecastArr.junTotal= currentForecastArr.junTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.junTotal;
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==7){
                        currentForecastArr.julTotal= currentForecastArr.julTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.julTotal;
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==8){
                        currentForecastArr.augTotal= currentForecastArr.augTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.augTotal;
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==9){
                        currentForecastArr.sepTotal= currentForecastArr.sepTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.sepTotal;
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==10){
                        currentForecastArr.octTotal= currentForecastArr.octTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.octTotal;
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==11){
                        currentForecastArr.novTotal= currentForecastArr.novTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.novTotal;
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==12){
                        currentForecastArr.decTotal= currentForecastArr.decTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.decTotal;
                        flagFound=1
                    }
                }
            }

            if ( flagFound==0){
                if (tempBudetPeriod==1){
                    currentForecastArr.janTotal= currentForecastArr.janTotal+tempBudetAmount
                    allTotal += currentForecastArr.janTotal;
                }else if (tempBudetPeriod==2){
                    currentForecastArr.febTotal=  currentForecastArr.febTotal+tempBudetAmount
                    allTotal += currentForecastArr.febTotal;
                }else if (tempBudetPeriod==3){
                    currentForecastArr.marTotal= currentForecastArr.marTotal+tempBudetAmount
                    allTotal += currentForecastArr.marTotal;
                }else if (tempBudetPeriod==4){
                    currentForecastArr.aprTotal= currentForecastArr.aprTotal+tempBudetAmount
                    allTotal += currentForecastArr.aprTotal;
                }else if (tempBudetPeriod==5){
                    currentForecastArr.mayTotal=  currentForecastArr.mayTotal+tempBudetAmount
                    allTotal += currentForecastArr.mayTotal;
                }else if (tempBudetPeriod==6){
                    currentForecastArr.junTotal= currentForecastArr.junTotal+tempBudetAmount
                    allTotal += currentForecastArr.junTotal;
                }else if (tempBudetPeriod==7){
                    currentForecastArr.julTotal= currentForecastArr.julTotal+tempBudetAmount
                    allTotal += currentForecastArr.julTotal;
                }else if (tempBudetPeriod==8){
                    currentForecastArr.augTotal= currentForecastArr.augTotal+tempBudetAmount
                    allTotal += currentForecastArr.augTotal;
                }else if (tempBudetPeriod==9){
                    currentForecastArr.sepTotal= currentForecastArr.sepTotal+tempBudetAmount
                    allTotal += currentForecastArr.sepTotal;
                }else if (tempBudetPeriod==10){
                    currentForecastArr.octTotal= currentForecastArr.octTotal+tempBudetAmount
                    allTotal += currentForecastArr.octTotal;
                }else if (tempBudetPeriod==11){
                    currentForecastArr.novTotal= currentForecastArr.novTotal+tempBudetAmount
                    allTotal += currentForecastArr.novTotal;
                }else if (tempBudetPeriod==12){
                    currentForecastArr.decTotal= currentForecastArr.decTotal+tempBudetAmount
                    allTotal += currentForecastArr.decTotal;
                }

            }
        }
        currentForecastArr.allTotal = allTotal

        return currentForecastArr;
    }


    def Map prepareCurrentForecastForReservationNameWise(budgetForecastArr,invoiceForecastArr){

        Map currentForecastArr = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                  "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal":0.00]


        budgetForecastArr.eachWithIndex {BudgetForecastIndex, BudgetForecastKey ->
            def tempBudetId =  BudgetForecastIndex[0]
            def tempBudetAmount =  BudgetForecastIndex[1]
            def tempBudetPeriod =  BudgetForecastIndex[2]

            Integer flagFound = 0

            invoiceForecastArr.eachWithIndex {InvoiceForecastIndex, InvoiceForecastKey ->

                def tempInvoiceId =  InvoiceForecastIndex[0]
                def tempInvoiceAmount =  InvoiceForecastIndex[1]
                def tempInvoicePeriod =  InvoiceForecastIndex[2]

                if (tempBudetId == tempInvoiceId){

                    if (Integer.parseInt(tempInvoicePeriod)==1){
                        currentForecastArr.janTotal= currentForecastArr.janTotal+tempInvoiceAmount
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==2){
                        currentForecastArr.febTotal=  currentForecastArr.febTotal+tempInvoiceAmount
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==3){
                        currentForecastArr.marTotal= currentForecastArr.marTotal+tempInvoiceAmount
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==4){
                        currentForecastArr.aprTotal= currentForecastArr.aprTotal+tempInvoiceAmount
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==5){
                        currentForecastArr.mayTotal=  currentForecastArr.mayTotal+tempInvoiceAmount
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==6){
                        currentForecastArr.junTotal= currentForecastArr.junTotal+tempInvoiceAmount
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==7){
                        currentForecastArr.julTotal= currentForecastArr.julTotal+tempInvoiceAmount
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==8){
                        currentForecastArr.augTotal= currentForecastArr.augTotal+tempInvoiceAmount
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==9){
                        currentForecastArr.sepTotal= currentForecastArr.sepTotal+tempInvoiceAmount
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==10){
                        currentForecastArr.octTotal= currentForecastArr.octTotal+tempInvoiceAmount
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==11){
                        currentForecastArr.novTotal= currentForecastArr.novTotal+tempInvoiceAmount
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==12){
                        currentForecastArr.decTotal= currentForecastArr.decTotal+tempInvoiceAmount
                        flagFound=1
                    }
                }
            }

            if ( flagFound==0){
                if (tempBudetPeriod==1){
                    currentForecastArr.janTotal= currentForecastArr.janTotal+tempBudetAmount
                }else if (tempBudetPeriod==2){
                    currentForecastArr.febTotal=  currentForecastArr.febTotal+tempBudetAmount
                }else if (tempBudetPeriod==3){
                    currentForecastArr.marTotal= currentForecastArr.marTotal+tempBudetAmount
                }else if (tempBudetPeriod==4){
                    currentForecastArr.aprTotal= currentForecastArr.aprTotal+tempBudetAmount
                }else if (tempBudetPeriod==5){
                    currentForecastArr.mayTotal=  currentForecastArr.mayTotal+tempBudetAmount
                }else if (tempBudetPeriod==6){
                    currentForecastArr.junTotal= currentForecastArr.junTotal+tempBudetAmount
                }else if (tempBudetPeriod==7){
                    currentForecastArr.julTotal= currentForecastArr.julTotal+tempBudetAmount
                }else if (tempBudetPeriod==8){
                    currentForecastArr.augTotal= currentForecastArr.augTotal+tempBudetAmount
                }else if (tempBudetPeriod==9){
                    currentForecastArr.sepTotal= currentForecastArr.sepTotal+tempBudetAmount
                }else if (tempBudetPeriod==10){
                    currentForecastArr.octTotal= currentForecastArr.octTotal+tempBudetAmount
                }else if (tempBudetPeriod==11){
                    currentForecastArr.novTotal= currentForecastArr.novTotal+tempBudetAmount
                }else if (tempBudetPeriod==12){
                    currentForecastArr.decTotal= currentForecastArr.decTotal+tempBudetAmount
                }

            }
        }

        return currentForecastArr;
    }

    def Map prepareCurrentForecastForPrivateIncome(budgetForecastArr,invoiceForecastArr){

        Map currentForecastArr = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                  "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal":0.00,"allTotal":0.00]

        def allTotal = 0.00
        budgetForecastArr.eachWithIndex {BudgetForecastIndex, BudgetForecastKey ->
            def tempBudetId =  BudgetForecastIndex[0]
            def tempBudetAmount =  BudgetForecastIndex[1]
            def tempBudetPeriod =  BudgetForecastIndex[2]

            Integer flagFound = 0

            invoiceForecastArr.eachWithIndex {InvoiceForecastIndex, InvoiceForecastKey ->

                def tempInvoiceId =  InvoiceForecastIndex[0]
                def tempInvoiceAmount =  InvoiceForecastIndex[1]
                def tempInvoicePeriod =  InvoiceForecastIndex[2]

                if (tempBudetId == tempInvoiceId){

                    if (tempInvoicePeriod == 1){
                        currentForecastArr.janTotal= currentForecastArr.janTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.janTotal
                        flagFound=1
                    }else if (tempInvoicePeriod == 2){
                        currentForecastArr.febTotal=  currentForecastArr.febTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.febTotal
                        flagFound=1
                    }else if (tempInvoicePeriod == 3){
                        currentForecastArr.marTotal= currentForecastArr.marTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.marTotal
                        flagFound=1
                    }else if (tempInvoicePeriod == 4){
                        currentForecastArr.aprTotal= currentForecastArr.aprTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.aprTotal
                        flagFound=1
                    }else if (tempInvoicePeriod == 5){
                        currentForecastArr.mayTotal=  currentForecastArr.mayTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.mayTotal
                        flagFound=1
                    }else if (tempInvoicePeriod == 6){
                        currentForecastArr.junTotal= currentForecastArr.junTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.junTotal
                        flagFound=1
                    }else if (tempInvoicePeriod == 7){
                        currentForecastArr.julTotal= currentForecastArr.julTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.julTotal
                        flagFound=1
                    }else if (tempInvoicePeriod == 8){
                        currentForecastArr.augTotal= currentForecastArr.augTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.augTotal
                        flagFound=1
                    }else if (tempInvoicePeriod == 9){
                        currentForecastArr.sepTotal= currentForecastArr.sepTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.sepTotal
                        flagFound=1
                    }else if (tempInvoicePeriod == 10){
                        currentForecastArr.octTotal= currentForecastArr.octTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.octTotal
                        flagFound=1
                    }else if (tempInvoicePeriod == 11){
                        currentForecastArr.novTotal= currentForecastArr.novTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.novTotal
                        flagFound=1
                    }else if (tempInvoicePeriod == 12){
                        currentForecastArr.decTotal= currentForecastArr.decTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.decTotal
                        flagFound=1
                    }
                }
            }

            if ( flagFound==0){
                if (tempBudetPeriod==1){
                    currentForecastArr.janTotal= currentForecastArr.janTotal+tempBudetAmount
                    allTotal += currentForecastArr.janTotal
                }else if (tempBudetPeriod==2){
                    currentForecastArr.febTotal=  currentForecastArr.febTotal+tempBudetAmount
                    allTotal += currentForecastArr.febTotal
                }else if (tempBudetPeriod==3){
                    currentForecastArr.marTotal= currentForecastArr.marTotal+tempBudetAmount
                    allTotal += currentForecastArr.marTotal
                }else if (tempBudetPeriod==4){
                    currentForecastArr.aprTotal= currentForecastArr.aprTotal+tempBudetAmount
                    allTotal += currentForecastArr.aprTotal
                }else if (tempBudetPeriod==5){
                    currentForecastArr.mayTotal=  currentForecastArr.mayTotal+tempBudetAmount
                    allTotal += currentForecastArr.mayTotal
                }else if (tempBudetPeriod==6){
                    currentForecastArr.junTotal= currentForecastArr.junTotal+tempBudetAmount
                    allTotal += currentForecastArr.junTotal
                }else if (tempBudetPeriod==7){
                    currentForecastArr.julTotal= currentForecastArr.julTotal+tempBudetAmount
                    allTotal += currentForecastArr.julTotal
                }else if (tempBudetPeriod==8){
                    currentForecastArr.augTotal= currentForecastArr.augTotal+tempBudetAmount
                    allTotal += currentForecastArr.augTotal
                }else if (tempBudetPeriod==9){
                    currentForecastArr.sepTotal= currentForecastArr.sepTotal+tempBudetAmount
                    allTotal += currentForecastArr.sepTotal
                }else if (tempBudetPeriod==10){
                    currentForecastArr.octTotal= currentForecastArr.octTotal+tempBudetAmount
                    allTotal += currentForecastArr.octTotal
                }else if (tempBudetPeriod==11){
                    currentForecastArr.novTotal= currentForecastArr.novTotal+tempBudetAmount
                    allTotal += currentForecastArr.novTotal
                }else if (tempBudetPeriod==12){
                    currentForecastArr.decTotal= currentForecastArr.decTotal+tempBudetAmount
                    allTotal += currentForecastArr.decTotal
                }

            }
        }

        currentForecastArr.allTotal = allTotal

        return currentForecastArr;
    }

    def getInvoiceExpenseForecastData(fiscalYearInfo){

        LinkedHashMap gridResultInvoiceForecastArr
        String selectInvoiceForecastArr="budgetItemExpenseId AS bi_id,SUM(totalGlAmount) AS total_amount,bookingPeriod AS bp"
        String selectIndexInvoiceForecastArr="bi_id,total_amount,bp"
        String fromInvoiceForecastArr="InvoiceExpense"
        String whereInvoiceForecastArr="bookingYear='"+fiscalYearInfo[0][4]+"'  AND status=1 AND isReverse=0 AND reverseInvoiceId=0"
        String orderByInvoiceForecastArr="bookingPeriod,budgetItemExpenseId"
        String groupByInvoiceForecastArr="bookingPeriod,budgetItemExpenseId"
        gridResultInvoiceForecastArr=new BudgetViewDatabaseService().select(selectInvoiceForecastArr,fromInvoiceForecastArr,whereInvoiceForecastArr,orderByInvoiceForecastArr,groupByInvoiceForecastArr,'true',selectIndexInvoiceForecastArr)

        def invoiceForecastArr =gridResultInvoiceForecastArr['dataGridList']

        return invoiceForecastArr;
    }

    def getInvoiceExpenseForecastDataForPrivate(fiscalYearInfo){

        LinkedHashMap gridResultInvoiceForecastArr
        String selectInvoiceForecastArr="ps.budget_master_id AS bi_id,Sum(ps.amount) AS total_amount, ps.booking_period AS bp"
        String selectIndexInvoiceForecastArr="bi_id,total_amount,bp"
        String fromInvoiceForecastArr="private_reservation_spending_trans  ps INNER JOIN private_budget_master pm ON ps.budget_master_id = pm.id "
        String whereInvoiceForecastArr="ps.booking_year ='" + fiscalYearInfo[0][4] + "'  AND ps.`status` = 1 AND pm.budget_type <> 1"
        String orderByInvoiceForecastArr="ps.booking_period,ps.budget_master_id"
        String groupByInvoiceForecastArr="ps.booking_period,ps.budget_master_id"
        gridResultInvoiceForecastArr=new BudgetViewDatabaseService().select(selectInvoiceForecastArr,fromInvoiceForecastArr,whereInvoiceForecastArr,orderByInvoiceForecastArr,groupByInvoiceForecastArr,'true',selectIndexInvoiceForecastArr)

        def invoiceForecastArr = gridResultInvoiceForecastArr['dataGridList']

        return invoiceForecastArr;
    }


    def getBudgetExpenseForecastData(fiscalYearInfo){

        LinkedHashMap gridResultBudgetForecastArr
        String selectBudgetForecastArr="id,SUM(totalGlAmount) AS total_amount,bookingPeriodStartMonth AS bpsm"
        String selectIndexBudgetForecastArr="id,total_amount,bpsm"
        String fromBudgetForecastArr="BudgetItemExpense"
        String whereBudgetForecastArr="bookingPeriodStartYear='"+fiscalYearInfo[0][4] +"' AND status=1"
        String orderByBudgetForecastArr="bookingPeriodStartMonth,id"
        String groupByBudgetForecastArr="bookingPeriodStartMonth,id"
        gridResultBudgetForecastArr=new BudgetViewDatabaseService().select(selectBudgetForecastArr,fromBudgetForecastArr,whereBudgetForecastArr,orderByBudgetForecastArr,groupByBudgetForecastArr,'true',selectIndexBudgetForecastArr)

        def budgetForecastArr =  gridResultBudgetForecastArr['dataGridList']

        return budgetForecastArr;
    }


    def getBudgetExpenseForecastDataForPrivate(fiscalYearInfo){

        LinkedHashMap gridResultBudgetForecastArr
        String selectBudgetForecastArr="pb.id as id,SUM(pb.total_amount) AS total_amount,pb.booking_period AS bpsm "
        String selectIndexBudgetForecastArr="id,total_amount,bpsm"
        String fromBudgetForecastArr="private_budget_item pb INNER JOIN private_budget_master pm ON pb.budget_name_id = pm.id "
        String whereBudgetForecastArr="pb.booking_year ='"+fiscalYearInfo[0][4] +"' AND pb.status=1 AND pm.budget_type <> 1"
        String orderByBudgetForecastArr="booking_period,id"
        String groupByBudgetForecastArr="booking_period,id"
        gridResultBudgetForecastArr=new BudgetViewDatabaseService().select(selectBudgetForecastArr,fromBudgetForecastArr,whereBudgetForecastArr,orderByBudgetForecastArr,groupByBudgetForecastArr,'true',selectIndexBudgetForecastArr)

        def budgetForecastArr =  gridResultBudgetForecastArr['dataGridList']

        return budgetForecastArr;
    }

    def Map prepareCurrentForecastForExpenseNameWise(budgetForecastArr,invoiceForecastArr){

        Map currentForecastArr = ["janTotal": 0, "febTotal": 0, "marTotal": 0, "aprTotal": 0, "mayTotal": 0, "junTotal": 0,
                                  "julTotal": 0, "augTotal": 0, "sepTotal": 0, "octTotal": 0, "novTotal": 0,"decTotal": 0,"allTotal":0.00]

        def allTotal = 0.00
        budgetForecastArr.eachWithIndex {BudgetForecastIndex, BudgetForecastKey ->
            def tempBudetId =  BudgetForecastIndex[0]
            def tempBudetAmount =  BudgetForecastIndex[1]
            def tempBudetPeriod =  BudgetForecastIndex[2]

            Integer flagFound = 0

            invoiceForecastArr.eachWithIndex {InvoiceForecastIndex, InvoiceForecastKey ->

                def tempInvoiceId=  InvoiceForecastIndex[0]
                def tempInvoiceAmount=  InvoiceForecastIndex[1]
                def tempInvoicePeriod=  InvoiceForecastIndex[2]

                if (tempBudetId == tempInvoiceId){

                    if (Integer.parseInt(tempInvoicePeriod)==1){
                        currentForecastArr.janTotal= currentForecastArr.janTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.janTotal
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==2){
                        currentForecastArr.febTotal=  currentForecastArr.febTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.febTotal
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==3){
                        currentForecastArr.marTotal= currentForecastArr.marTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.marTotal
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==4){
                        currentForecastArr.aprTotal= currentForecastArr.aprTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.aprTotal
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==5){
                        currentForecastArr.mayTotal=  currentForecastArr.mayTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.mayTotal
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==6){
                        currentForecastArr.junTotal= currentForecastArr.junTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.junTotal
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==7){
                        currentForecastArr.julTotal= currentForecastArr.julTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.julTotal
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==8){
                        currentForecastArr.augTotal= currentForecastArr.augTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.augTotal
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==9){
                        currentForecastArr.sepTotal= currentForecastArr.sepTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.sepTotal
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==10){
                        currentForecastArr.octTotal= currentForecastArr.octTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.octTotal
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==11){
                        currentForecastArr.novTotal= currentForecastArr.novTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.novTotal
                        flagFound=1
                    }else if (Integer.parseInt(tempInvoicePeriod)==12){
                        currentForecastArr.decTotal= currentForecastArr.decTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.decTotal
                        flagFound=1
                    }
                }
            }

            if ( flagFound==0){
                if (tempBudetPeriod==1){
                    currentForecastArr.janTotal = currentForecastArr.janTotal+tempBudetAmount
                    allTotal += currentForecastArr.janTotal
                }else if (tempBudetPeriod == 2){
                    currentForecastArr.febTotal=  currentForecastArr.febTotal+tempBudetAmount
                    allTotal += currentForecastArr.febTotal
                }else if (tempBudetPeriod == 3){
                    currentForecastArr.marTotal= currentForecastArr.marTotal+tempBudetAmount
                    allTotal += currentForecastArr.marTotal
                }else if (tempBudetPeriod == 4){
                    currentForecastArr.aprTotal= currentForecastArr.aprTotal+tempBudetAmount
                    allTotal += currentForecastArr.aprTotal
                }else if (tempBudetPeriod == 5){
                    currentForecastArr.mayTotal=  currentForecastArr.mayTotal+tempBudetAmount
                    allTotal += currentForecastArr.mayTotal
                }else if (tempBudetPeriod == 6){
                    currentForecastArr.junTotal = currentForecastArr.junTotal+tempBudetAmount
                    allTotal += currentForecastArr.junTotal
                }else if (tempBudetPeriod ==7){
                    currentForecastArr.julTotal = currentForecastArr.julTotal+tempBudetAmount
                    allTotal += currentForecastArr.julTotal
                }else if (tempBudetPeriod == 8){
                    currentForecastArr.augTotal = currentForecastArr.augTotal+tempBudetAmount
                    allTotal += currentForecastArr.augTotal
                }else if (tempBudetPeriod == 9){
                    currentForecastArr.sepTotal = currentForecastArr.sepTotal+tempBudetAmount
                    allTotal += currentForecastArr.sepTotal
                }else if (tempBudetPeriod == 10){
                    currentForecastArr.octTotal= currentForecastArr.octTotal+tempBudetAmount
                    allTotal += currentForecastArr.octTotal
                }else if (tempBudetPeriod == 11){
                    currentForecastArr.novTotal = currentForecastArr.novTotal+tempBudetAmount
                    allTotal += currentForecastArr.novTotal
                }else if (tempBudetPeriod == 12){
                    currentForecastArr.decTotal = currentForecastArr.decTotal+tempBudetAmount
                    allTotal += currentForecastArr.decTotal
                }

            }
        }
        currentForecastArr.allTotal = allTotal

        return currentForecastArr;
    }

    def Map prepareCurrentForecastForPrivateExpense(budgetForecastArr,invoiceForecastArr){

        Map currentForecastArr = ["janTotal": 0, "febTotal": 0, "marTotal": 0, "aprTotal": 0, "mayTotal": 0, "junTotal": 0,
                                  "julTotal": 0, "augTotal": 0, "sepTotal": 0, "octTotal": 0, "novTotal": 0,"decTotal": 0,"allTotal":0.00]
        def allTotal = 0.00
        budgetForecastArr.eachWithIndex {BudgetForecastIndex, BudgetForecastKey ->
            def tempBudetId =  BudgetForecastIndex[0]
            def tempBudetAmount =  BudgetForecastIndex[1]
            def tempBudetPeriod =  BudgetForecastIndex[2]

            Integer flagFound = 0

            invoiceForecastArr.eachWithIndex {InvoiceForecastIndex, InvoiceForecastKey ->

                def tempInvoiceId=  InvoiceForecastIndex[0]
                def tempInvoiceAmount=  InvoiceForecastIndex[1]
                def tempInvoicePeriod=  InvoiceForecastIndex[2]

                if (tempBudetId == tempInvoiceId){

                    if (tempInvoicePeriod==1){
                        currentForecastArr.janTotal= currentForecastArr.janTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.janTotal
                        flagFound=1
                    }else if (tempInvoicePeriod==2){
                        currentForecastArr.febTotal=  currentForecastArr.febTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.febTotal
                        flagFound=1
                    }else if (tempInvoicePeriod==3){
                        currentForecastArr.marTotal= currentForecastArr.marTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.marTotal
                        flagFound=1
                    }else if (tempInvoicePeriod==4){
                        currentForecastArr.aprTotal= currentForecastArr.aprTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.aprTotal
                        flagFound=1
                    }else if (tempInvoicePeriod==5){
                        currentForecastArr.mayTotal=  currentForecastArr.mayTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.mayTotal
                        flagFound=1
                    }else if (tempInvoicePeriod==6){
                        currentForecastArr.junTotal= currentForecastArr.junTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.junTotal
                        flagFound=1
                    }else if (tempInvoicePeriod==7){
                        currentForecastArr.julTotal= currentForecastArr.julTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.julTotal
                        flagFound=1
                    }else if (tempInvoicePeriod==8){
                        currentForecastArr.augTotal= currentForecastArr.augTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.augTotal
                        flagFound=1
                    }else if (tempInvoicePeriod==9){
                        currentForecastArr.sepTotal= currentForecastArr.sepTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.sepTotal
                        flagFound=1
                    }else if (tempInvoicePeriod==10){
                        currentForecastArr.octTotal= currentForecastArr.octTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.octTotal
                        flagFound=1
                    }else if (tempInvoicePeriod==11){
                        currentForecastArr.novTotal= currentForecastArr.novTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.novTotal
                        flagFound=1
                    }else if (tempInvoicePeriod==12){
                        currentForecastArr.decTotal= currentForecastArr.decTotal+tempInvoiceAmount
                        allTotal += currentForecastArr.decTotal
                        flagFound=1
                    }
                }
            }

            if ( flagFound==0){
                if (tempBudetPeriod==1){
                    currentForecastArr.janTotal = currentForecastArr.janTotal+tempBudetAmount
                    allTotal += currentForecastArr.janTotal
                }else if (tempBudetPeriod == 2){
                    currentForecastArr.febTotal=  currentForecastArr.febTotal+tempBudetAmount
                    allTotal += currentForecastArr.febTotal
                }else if (tempBudetPeriod == 3){
                    currentForecastArr.marTotal= currentForecastArr.marTotal+tempBudetAmount
                    allTotal += currentForecastArr.marTotal
                }else if (tempBudetPeriod == 4){
                    currentForecastArr.aprTotal= currentForecastArr.aprTotal+tempBudetAmount
                    allTotal += currentForecastArr.aprTotal
                }else if (tempBudetPeriod == 5){
                    currentForecastArr.mayTotal=  currentForecastArr.mayTotal+tempBudetAmount
                    allTotal += currentForecastArr.mayTotal
                }else if (tempBudetPeriod == 6){
                    currentForecastArr.junTotal = currentForecastArr.junTotal+tempBudetAmount
                    allTotal += currentForecastArr.junTotal
                }else if (tempBudetPeriod ==7){
                    currentForecastArr.julTotal = currentForecastArr.julTotal+tempBudetAmount
                    allTotal += currentForecastArr.julTotal
                }else if (tempBudetPeriod == 8){
                    currentForecastArr.augTotal = currentForecastArr.augTotal+tempBudetAmount
                    allTotal += currentForecastArr.augTotal
                }else if (tempBudetPeriod == 9){
                    currentForecastArr.sepTotal = currentForecastArr.sepTotal+tempBudetAmount
                    allTotal += currentForecastArr.sepTotal
                }else if (tempBudetPeriod == 10){
                    currentForecastArr.octTotal= currentForecastArr.octTotal+tempBudetAmount
                    allTotal += currentForecastArr.octTotal
                }else if (tempBudetPeriod == 11){
                    currentForecastArr.novTotal = currentForecastArr.novTotal+tempBudetAmount
                    allTotal += currentForecastArr.novTotal
                }else if (tempBudetPeriod == 12){
                    currentForecastArr.decTotal = currentForecastArr.decTotal+tempBudetAmount
                    allTotal += currentForecastArr.decTotal
                }

            }
        }

        currentForecastArr.allTotal = allTotal

        return currentForecastArr;
    }


    def Map getInvoiceIncomeTotalMonthWise(fiscalYearInfo){

        Map totalInvoiceIncome = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                  "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal": 0.00,
                                  "allTotal": 0.00]


        LinkedHashMap gridResultTotalInvoiceIncomeArr
        String selectTotalInvoiceIncomeArr="bookingPeriod AS bp,SUM(totalGlAmount) AS total_income"
        String selectIndexTotalInvoiceIncomeArr="bp,total_income"
        String fromTotalInvoiceIncomeArr="InvoiceIncome "
        String whereTotalInvoiceIncomeArr="bookingYear ='" + fiscalYearInfo[0][4] + "' AND status=1 AND isReverse=0 AND reverseInvoiceId=0"
        String orderByTotalInvoiceIncomeArr=""
        String groupByTotalInvoiceIncomeArr="bookingPeriod"
        gridResultTotalInvoiceIncomeArr = new BudgetViewDatabaseService().select(selectTotalInvoiceIncomeArr,fromTotalInvoiceIncomeArr,whereTotalInvoiceIncomeArr,orderByTotalInvoiceIncomeArr,groupByTotalInvoiceIncomeArr,'true',selectIndexTotalInvoiceIncomeArr)

        ArrayList totalInvoiceIncomeArr = gridResultTotalInvoiceIncomeArr['dataGridList']

        def allTotal = 0.00;
        totalInvoiceIncomeArr.eachWithIndex {incomeTotalIndex, incomeTotalKey ->
            if (Integer.parseInt(incomeTotalIndex[0])==1){
                totalInvoiceIncome.janTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.janTotal;
            }else if (Integer.parseInt(incomeTotalIndex[0])==2){
                totalInvoiceIncome.febTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.febTotal;
            }else if (Integer.parseInt(incomeTotalIndex[0])==3){
                totalInvoiceIncome.marTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.marTotal;
            }else if (Integer.parseInt(incomeTotalIndex[0])==4){
                totalInvoiceIncome.aprTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.aprTotal;
            }else if (Integer.parseInt(incomeTotalIndex[0])==5){
                totalInvoiceIncome.mayTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.mayTotal;
            }else if (Integer.parseInt(incomeTotalIndex[0])==6){
                totalInvoiceIncome.junTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.junTotal;
            }else if (Integer.parseInt(incomeTotalIndex[0])==7){
                totalInvoiceIncome.julTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.julTotal;
            }else if (Integer.parseInt(incomeTotalIndex[0])==8){
                totalInvoiceIncome.augTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.augTotal;
            }else if (Integer.parseInt(incomeTotalIndex[0])==9){
                totalInvoiceIncome.sepTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.sepTotal;
            }else if (Integer.parseInt(incomeTotalIndex[0])==10){
                totalInvoiceIncome.octTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.octTotal;
            }else if (Integer.parseInt(incomeTotalIndex[0])==11){
                totalInvoiceIncome.novTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.novTotal;
            }else if (Integer.parseInt(incomeTotalIndex[0])==12){
                totalInvoiceIncome.decTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.decTotal;
            }
        }

        totalInvoiceIncome.allTotal = allTotal;

        return totalInvoiceIncome;
    }


    /*
    * for total Reservation invoice
    * */

    def Map getInvoiceReservationTotalMonthWise(fiscalYearInfo){

        Map totalInvoiceIncome = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                  "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal": 0.00,
                                  "allTotal": 0.00]


        LinkedHashMap gridResultTotalInvoiceIncomeArr
        String selectTotalInvoiceIncomeArr="bookingPeriod AS bp,ROUND(SUM(amount),2) AS totalIncome"
        String selectIndexTotalInvoiceIncomeArr="bp,totalIncome"
        String fromTotalInvoiceIncomeArr="privateReservationSpendingTrans "
        String whereTotalInvoiceIncomeArr=" bookingYear ='${fiscalYearInfo[0][4]}' AND status='1'  AND transType = '5'"
        String orderByTotalInvoiceIncomeArr=""
        String groupByTotalInvoiceIncomeArr="bookingPeriod"
        gridResultTotalInvoiceIncomeArr = new BudgetViewDatabaseService().select(selectTotalInvoiceIncomeArr,fromTotalInvoiceIncomeArr,whereTotalInvoiceIncomeArr,orderByTotalInvoiceIncomeArr,groupByTotalInvoiceIncomeArr,'true',selectIndexTotalInvoiceIncomeArr)

        ArrayList totalInvoiceIncomeArr = gridResultTotalInvoiceIncomeArr['dataGridList']



        def allTotal = 0.00;
        totalInvoiceIncomeArr.eachWithIndex {incomeTotalIndex, incomeTotalKey ->
            if (incomeTotalIndex[0]==1){
                totalInvoiceIncome.janTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.janTotal;
            }else if (incomeTotalIndex[0]==2){
                totalInvoiceIncome.febTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.febTotal;
            }else if (incomeTotalIndex[0]==3){
                totalInvoiceIncome.marTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.marTotal;
            }else if (incomeTotalIndex[0]==4){
                totalInvoiceIncome.aprTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.aprTotal;
            }else if (incomeTotalIndex[0]==5){
                totalInvoiceIncome.mayTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.mayTotal;
            }else if (incomeTotalIndex[0]==6){
                totalInvoiceIncome.junTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.junTotal;
            }else if (incomeTotalIndex[0]==7){
                totalInvoiceIncome.julTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.julTotal;
            }else if (incomeTotalIndex[0]==8){
                totalInvoiceIncome.augTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.augTotal;
            }else if (incomeTotalIndex[0]==9){
                totalInvoiceIncome.sepTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.sepTotal;
            }else if (incomeTotalIndex[0]==10){
                totalInvoiceIncome.octTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.octTotal;
            }else if (incomeTotalIndex[0]==11){
                totalInvoiceIncome.novTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.novTotal;
            }else if (incomeTotalIndex[0]==12){
                totalInvoiceIncome.decTotal = dashboardDetailsTagLib.getRoundedValue(incomeTotalIndex[1])
                allTotal += totalInvoiceIncome.decTotal;
            }
        }

        totalInvoiceIncome.allTotal = allTotal*(-1);

        return totalInvoiceIncome;
    }


    def Map getInvoiceIncomeTotalForPrivateMonthWise(fiscalYearInfo){

        Map totalInvoiceIncome = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                  "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal": 0.00,
                                  "allTotal": 0.00]


        LinkedHashMap gridResultTotalInvoiceIncomeArr
        String selectTotalInvoiceIncomeArr = "ps.booking_period AS bp,Sum(ps.amount) AS total_income"
        String selectIndexTotalInvoiceIncomeArr = "bp,total_income"
        String fromTotalInvoiceIncomeArr = "private_reservation_spending_trans  ps INNER JOIN private_budget_master pm ON ps.budget_master_id = pm.id "
        String whereTotalInvoiceIncomeArr = "ps.booking_year ='" + fiscalYearInfo[0][4] + "' AND ps.`status` = 1 AND pm.budget_type = 1 AND ps.trans_type = '6'"
        String orderByTotalInvoiceIncomeArr=""
        String groupByTotalInvoiceIncomeArr="bookingPeriod"
        gridResultTotalInvoiceIncomeArr=new BudgetViewDatabaseService().select(selectTotalInvoiceIncomeArr,fromTotalInvoiceIncomeArr,whereTotalInvoiceIncomeArr,orderByTotalInvoiceIncomeArr,groupByTotalInvoiceIncomeArr,'true',selectIndexTotalInvoiceIncomeArr)

        ArrayList totalInvoiceIncomeArr = gridResultTotalInvoiceIncomeArr['dataGridList']

        def allTotal = 0.00;
        totalInvoiceIncomeArr.eachWithIndex {incomeTotalIndex, incomeTotalKey ->
            if (incomeTotalIndex[0]==1){
                totalInvoiceIncome.janTotal = incomeTotalIndex[1]
                allTotal += totalInvoiceIncome.janTotal;
            }else if (incomeTotalIndex[0]==2){
                totalInvoiceIncome.febTotal = incomeTotalIndex[1]
                allTotal += totalInvoiceIncome.febTotal;
            }else if (incomeTotalIndex[0]==3){
                totalInvoiceIncome.marTotal = incomeTotalIndex[1]
                allTotal += totalInvoiceIncome.marTotal;
            }else if (incomeTotalIndex[0]==4){
                totalInvoiceIncome.aprTotal = incomeTotalIndex[1]
                allTotal += totalInvoiceIncome.aprTotal;
            }else if (incomeTotalIndex[0]==5){
                totalInvoiceIncome.mayTotal = incomeTotalIndex[1]
                allTotal += totalInvoiceIncome.mayTotal;
            }else if (incomeTotalIndex[0]==6){
                totalInvoiceIncome.junTotal = incomeTotalIndex[1]
                allTotal += totalInvoiceIncome.junTotal;
            }else if (incomeTotalIndex[0]==7){
                totalInvoiceIncome.julTotal = incomeTotalIndex[1]
                allTotal += totalInvoiceIncome.julTotal;
            }else if (incomeTotalIndex[0]==8){
                totalInvoiceIncome.augTotal = incomeTotalIndex[1]
                allTotal += totalInvoiceIncome.augTotal;
            }else if (incomeTotalIndex[0]==9){
                totalInvoiceIncome.sepTotal = incomeTotalIndex[1]
                allTotal += totalInvoiceIncome.sepTotal;
            }else if (incomeTotalIndex[0]==10) {
                totalInvoiceIncome.octTotal = incomeTotalIndex[1]
                allTotal += totalInvoiceIncome.octTotal;
            }else if (incomeTotalIndex[0]==11){
                totalInvoiceIncome.novTotal = incomeTotalIndex[1]
                allTotal += totalInvoiceIncome.novTotal;
            }else if (incomeTotalIndex[0]==12){
                totalInvoiceIncome.decTotal = incomeTotalIndex[1]
                allTotal += totalInvoiceIncome.decTotal;
            }
        }

        totalInvoiceIncome.allTotal = allTotal;

        def privateDWCArr=  getProcessDataForPrivateWDC(fiscalYearInfo)

        totalInvoiceIncome.janTotal = totalInvoiceIncome.janTotal+privateDWCArr.janAmount
        totalInvoiceIncome.febTotal = totalInvoiceIncome.febTotal+privateDWCArr.febAmount
        totalInvoiceIncome.marTotal = totalInvoiceIncome.marTotal+privateDWCArr.marAmount
        totalInvoiceIncome.aprTotal = totalInvoiceIncome.aprTotal+privateDWCArr.aprAmount
        totalInvoiceIncome.mayTotal = totalInvoiceIncome.mayTotal+privateDWCArr.mayAmount
        totalInvoiceIncome.junTotal = totalInvoiceIncome.junTotal+privateDWCArr.junAmount
        totalInvoiceIncome.julTotal = totalInvoiceIncome.julTotal+privateDWCArr.julAmount
        totalInvoiceIncome.augTotal = totalInvoiceIncome.augTotal+privateDWCArr.augAmount
        totalInvoiceIncome.sepTotal = totalInvoiceIncome.sepTotal+privateDWCArr.sepAmount
        totalInvoiceIncome.octTotal = totalInvoiceIncome.octTotal+privateDWCArr.octAmount
        totalInvoiceIncome.novTotal = totalInvoiceIncome.novTotal+privateDWCArr.novAmount
        totalInvoiceIncome.decTotal = totalInvoiceIncome.decTotal+privateDWCArr.decAmount
        totalInvoiceIncome.allTotal = totalInvoiceIncome.allTotal+privateDWCArr.allTotal


        return totalInvoiceIncome;
    }

    def Map getInvoiceExpenseTotalForPrivateMonthWise(fiscalYearInfo) {

        Map totalInvoiceExpense = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                   "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00, "decTotal": 0.00,
                                   "allTotal":0.00]

        String selectTotalInvoiceExpenseArr = "ps.booking_period AS bp,Sum(ps.amount) AS total_expense"
        String selectIndexTotalInvoiceExpenseArr = "bp,total_expense"
        String fromTotalInvoiceExpenseArr = "private_reservation_spending_trans  ps INNER JOIN private_budget_master pm ON ps.budget_master_id = pm.id"
        String whereTotalInvoiceExpenseArr = "ps.booking_year ='" + fiscalYearInfo[0][4] + "' AND ps.`status` = 1 AND pm.budget_type <> 1 AND ps.trans_type = '6'"
        String orderByTotalInvoiceExpenseArr = ""
        String groupByTotalInvoiceExpenseArr = "bookingPeriod"

        LinkedHashMap gridResultTotalInvoiceExpenseArr
        gridResultTotalInvoiceExpenseArr = new BudgetViewDatabaseService().select(selectTotalInvoiceExpenseArr,fromTotalInvoiceExpenseArr,whereTotalInvoiceExpenseArr,orderByTotalInvoiceExpenseArr,groupByTotalInvoiceExpenseArr,'true',selectIndexTotalInvoiceExpenseArr)

        ArrayList totalInvoiceExpenseArr = gridResultTotalInvoiceExpenseArr['dataGridList']
        def allTotal = 0.00;
        totalInvoiceExpenseArr.eachWithIndex {expenseTotalIndex, expenseGrossKey ->
            if (expenseTotalIndex[0] == 1){
                totalInvoiceExpense.janTotal = (-1) * expenseTotalIndex[1]
                allTotal += totalInvoiceExpense.janTotal;
            }else if (expenseTotalIndex[0] == 2){
                totalInvoiceExpense.febTotal = (-1) * expenseTotalIndex[1]
                allTotal += totalInvoiceExpense.febTotal;
            }else if (expenseTotalIndex[0] == 3){
                totalInvoiceExpense.marTotal = (-1) * expenseTotalIndex[1]
                allTotal += totalInvoiceExpense.marTotal;
            }else if (expenseTotalIndex[0] == 4){
                totalInvoiceExpense.aprTotal = (-1) * expenseTotalIndex[1]
                allTotal += totalInvoiceExpense.aprTotal;
            }else if (expenseTotalIndex[0] == 5){
                totalInvoiceExpense.mayTotal = (-1) * expenseTotalIndex[1]
                allTotal += totalInvoiceExpense.mayTotal;
            }else if (expenseTotalIndex[0] == 6){
                totalInvoiceExpense.junTotal = (-1) * expenseTotalIndex[1]
                allTotal += totalInvoiceExpense.junTotal;
            }else if (expenseTotalIndex[0] == 7){
                totalInvoiceExpense.julTotal = (-1) * expenseTotalIndex[1]
                allTotal += totalInvoiceExpense.julTotal;
            }else if (expenseTotalIndex[0] == 8){
                totalInvoiceExpense.augTotal = (-1) * expenseTotalIndex[1]
                allTotal += totalInvoiceExpense.augTotal;
            }else if (expenseTotalIndex[0] == 9){
                totalInvoiceExpense.sepTotal = (-1) * expenseTotalIndex[1]
                allTotal += totalInvoiceExpense.sepTotal;
            }else if (expenseTotalIndex[0] == 10){
                totalInvoiceExpense.octTotal = (-1) * expenseTotalIndex[1]
                allTotal += totalInvoiceExpense.octTotal;
            }else if (expenseTotalIndex[0] == 11){
                totalInvoiceExpense.novTotal = (-1) * expenseTotalIndex[1]
                allTotal += totalInvoiceExpense.novTotal;
            }else if (expenseTotalIndex[0] == 12){
                totalInvoiceExpense.decTotal =  (-1) * expenseTotalIndex[1]
                allTotal += totalInvoiceExpense.decTotal;
            }
        }

        totalInvoiceExpense.allTotal = allTotal;

        return totalInvoiceExpense;
    }
    def Map getInvoiceExpenseTotalMonthWise(fiscalYearInfo) {

        Map totalInvoiceExpense = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                   "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00, "decTotal": 0.00,
                                   "allTotal":0.00]


        String selectTotalInvoiceExpenseArr = "bookingPeriod AS bp,SUM(totalGlAmount) AS total_expense"
        String selectIndexTotalInvoiceExpenseArr = "bp,total_expense"
        String fromTotalInvoiceExpenseArr = "InvoiceExpense "
        String whereTotalInvoiceExpenseArr = "bookingYear ='" + fiscalYearInfo[0][4] + "' AND status=1 AND isReverse=0 AND reverseInvoiceId=0"
        String orderByTotalInvoiceExpenseArr = ""
        String groupByTotalInvoiceExpenseArr = "bookingPeriod"

        LinkedHashMap gridResultTotalInvoiceExpenseArr
        gridResultTotalInvoiceExpenseArr = new BudgetViewDatabaseService().select(selectTotalInvoiceExpenseArr,fromTotalInvoiceExpenseArr,whereTotalInvoiceExpenseArr,orderByTotalInvoiceExpenseArr,groupByTotalInvoiceExpenseArr,'true',selectIndexTotalInvoiceExpenseArr)

        ArrayList totalInvoiceExpenseArr = gridResultTotalInvoiceExpenseArr['dataGridList']
        def allTotal = 0.00;
        totalInvoiceExpenseArr.eachWithIndex {expenseTotalIndex, expenseGrossKey ->
            if (Integer.parseInt(expenseTotalIndex[0]) == 1){
                totalInvoiceExpense.janTotal = dashboardDetailsTagLib.getRoundedValue(expenseTotalIndex[1])
                allTotal += totalInvoiceExpense.janTotal;
            }else if (Integer.parseInt(expenseTotalIndex[0]) == 2){
                totalInvoiceExpense.febTotal = dashboardDetailsTagLib.getRoundedValue(expenseTotalIndex[1])
                allTotal += totalInvoiceExpense.febTotal;
            }else if (Integer.parseInt(expenseTotalIndex[0]) == 3){
                totalInvoiceExpense.marTotal = dashboardDetailsTagLib.getRoundedValue(expenseTotalIndex[1])
                allTotal += totalInvoiceExpense.marTotal;
            }else if (Integer.parseInt(expenseTotalIndex[0]) == 4){
                totalInvoiceExpense.aprTotal = dashboardDetailsTagLib.getRoundedValue(expenseTotalIndex[1])
                allTotal += totalInvoiceExpense.aprTotal;
            }else if (Integer.parseInt(expenseTotalIndex[0]) == 5){
                totalInvoiceExpense.mayTotal = dashboardDetailsTagLib.getRoundedValue(expenseTotalIndex[1])
                allTotal += totalInvoiceExpense.mayTotal;
            }else if (Integer.parseInt(expenseTotalIndex[0]) == 6){
                totalInvoiceExpense.junTotal = dashboardDetailsTagLib.getRoundedValue(expenseTotalIndex[1])
                allTotal += totalInvoiceExpense.junTotal;
            }else if (Integer.parseInt(expenseTotalIndex[0]) == 7){
                totalInvoiceExpense.julTotal = dashboardDetailsTagLib.getRoundedValue(expenseTotalIndex[1])
                allTotal += totalInvoiceExpense.julTotal;
            }else if (Integer.parseInt(expenseTotalIndex[0]) == 8){
                totalInvoiceExpense.augTotal = dashboardDetailsTagLib.getRoundedValue(expenseTotalIndex[1])
                allTotal += totalInvoiceExpense.augTotal;
            }else if (Integer.parseInt(expenseTotalIndex[0]) == 9){
                totalInvoiceExpense.sepTotal = dashboardDetailsTagLib.getRoundedValue(expenseTotalIndex[1])
                allTotal += totalInvoiceExpense.sepTotal;
            }else if (Integer.parseInt(expenseTotalIndex[0]) == 10){
                totalInvoiceExpense.octTotal = dashboardDetailsTagLib.getRoundedValue(expenseTotalIndex[1])
                allTotal += totalInvoiceExpense.octTotal;
            }else if (Integer.parseInt(expenseTotalIndex[0]) == 11){
                totalInvoiceExpense.novTotal = dashboardDetailsTagLib.getRoundedValue(expenseTotalIndex[1])
                allTotal += totalInvoiceExpense.novTotal;
            }else if (Integer.parseInt(expenseTotalIndex[0]) == 12){
                totalInvoiceExpense.decTotal =  dashboardDetailsTagLib.getRoundedValue(expenseTotalIndex[1])
                allTotal += totalInvoiceExpense.decTotal;
            }
        }

        totalInvoiceExpense.allTotal = allTotal;

        return totalInvoiceExpense;
    }

    def getTotalIncomeInvoiceAmountForPrivateBudget(fiscalYearInfo){

        String strQuery = """SELECT pbm.budget_type AS budType,SUM(prst.amount) AS total_amount  from private_reservation_spending_trans as prst
                          INNER JOIN private_budget_master as pbm ON pbm.id = prst.budget_master_id
                          WHERE prst.booking_year =${fiscalYearInfo[0][4]} AND prst.status=1
                          AND prst.trans_type=6 AND pbm.budget_type = 1 GROUP BY pbm.budget_type;"""

        def totalInvoiceArr = new BudgetViewDatabaseService().executeQuery(strQuery);

        def allTotal = 0.00;
        for(int i=0;i<totalInvoiceArr.size();i++) {
            allTotal += dashboardDetailsTagLib.getRoundedValue(totalInvoiceArr[i][1])
        }

        return allTotal;
    }

    def getTotalExpenseInvoiceAmountForPrivateBudget(fiscalYearInfo){

        String strQuery = """SELECT pbm.budget_type AS budType,SUM(prst.amount) AS total_amount  from private_reservation_spending_trans as prst
                          INNER JOIN private_budget_master as pbm ON pbm.id = prst.budget_master_id
                          WHERE prst.booking_year =${fiscalYearInfo[0][4]} AND prst.status=1
                          AND prst.trans_type=6 AND pbm.budget_type > 1 GROUP BY pbm.budget_type;"""

        def totalInvoiceArr = new BudgetViewDatabaseService().executeQuery(strQuery);

        def allTotal = 0.00;
        for(int i=0;i<totalInvoiceArr.size();i++) {
            allTotal += dashboardDetailsTagLib.getRoundedValue(totalInvoiceArr[i][1])
        }

        allTotal = allTotal * (-1);

        return allTotal;
    }

    def getTotalInvoiceAmountForReservationBudget(fiscalYearInfo){

        String strQuery = """SELECT rbm.reservation_type AS budType,SUM(prst.amount) AS total_amount from private_reservation_spending_trans as prst
                          INNER JOIN reservation_budget_master as rbm ON rbm.id = prst.budget_master_id
                          WHERE prst.booking_year =${fiscalYearInfo[0][4]} AND prst.status=1
                          AND prst.trans_type=5 GROUP BY rbm.reservation_type;"""

        def totalInvoiceArr = new BudgetViewDatabaseService().executeQuery(strQuery);
        def allTotal = 0.00;

        for (int i = 0;i<totalInvoiceArr.size();i++) {
            allTotal += dashboardDetailsTagLib.getRoundedValue(totalInvoiceArr[i][1])
        }

        return allTotal *(-1);
    }

    def getTotalIncomeInvoiceAmountForReservationBudget(fiscalYearInfo){

        String strQuery = """SELECT rbm.reservation_type AS budType,SUM(prst.amount) AS total_amount  from private_reservation_spending_trans as prst
                          INNER JOIN reservation_budget_master as rbm ON rbm.id = prst.budget_master_id
                          WHERE prst.booking_year =${fiscalYearInfo[0][4]} AND prst.status=1
                          AND prst.trans_type=5 AND rbm.reservation_type = 1 GROUP BY rbm.reservation_type;"""

        def totalInvoiceArr = new BudgetViewDatabaseService().executeQuery(strQuery);

        def allTotal = 0.00;
        if (totalInvoiceArr.size() > 0) {
            allTotal = totalInvoiceArr[0][1]
        }

        return allTotal;
    }

    def getTotalExpenseInvoiceAmountForReservationBudget(fiscalYearInfo){

        /* String strQuery = """SELECT rbm.reservation_type AS budType,SUM(prst.amount) AS total_amount  from private_reservation_spending_trans as prst
                           INNER JOIN reservation_budget_master as rbm ON rbm.id = prst.budget_master_id
                           WHERE prst.booking_year =${fiscalYearInfo[0][4]} AND prst.status=1
                           AND prst.trans_type=5 AND rbm.reservation_type > 1 GROUP BY rbm.reservation_type;"""
 */

        String strQuery = """SELECT rbm.reservation_type AS budType,SUM(prst.amount) AS total_amount  from private_reservation_spending_trans as prst
                          INNER JOIN reservation_budget_master as rbm ON rbm.id = prst.budget_master_id
                          WHERE prst.booking_year =${fiscalYearInfo[0][4]} AND prst.status=1
                          AND prst.trans_type=5 AND rbm.reservation_type > 1 ;"""

        def totalInvoiceArr = new BudgetViewDatabaseService().executeQuery(strQuery);

        def allTotal = 0.00;
        if (totalInvoiceArr.size() > 0) {
            allTotal = totalInvoiceArr[0][1]
        }

        return allTotal;
    }

    def Map getIncomeBudgetTotalMonthWise(fiscalYearInfo) {

        Map totalIncomeBudget = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                 "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00, "decTotal": 0.00,
                                 "allTotal":0.00]


        LinkedHashMap gridResultTotalBudgetIncomeArr
        String selectTotalBudgetIncomeArr = "bookingPeriodStartMonth AS bpsm,SUM(totalGlAmount) AS total_income_budget"
        String selectIndexTotalBudgetIncomeArr = "bpsm,total_income_budget"
        String fromTotalBudgetIncomeArr = "BudgetItemIncome "
        String whereTotalBudgetIncomeArr = "bookingPeriodStartYear ='" + fiscalYearInfo[0][4] + "' AND status=1"
        String orderByTotalBudgetIncomeArr = ""
        String groupByTotalBudgetIncomeArr = "bookingPeriodStartMonth"
        gridResultTotalBudgetIncomeArr = new BudgetViewDatabaseService().select(selectTotalBudgetIncomeArr,fromTotalBudgetIncomeArr,whereTotalBudgetIncomeArr,orderByTotalBudgetIncomeArr,groupByTotalBudgetIncomeArr,'true',selectIndexTotalBudgetIncomeArr)

        ArrayList totalBudgetIncomeArr = gridResultTotalBudgetIncomeArr['dataGridList']

        def allTotal = 0.00
        totalBudgetIncomeArr.eachWithIndex {incomeBudgetIndex, incomeBudgetKey ->
            def totalAmount = dashboardDetailsTagLib.getRoundedValue(incomeBudgetIndex[1]);
            if (incomeBudgetIndex[0] == 1){
                totalIncomeBudget.janTotal = totalAmount
                allTotal += totalIncomeBudget.janTotal
            }else if (incomeBudgetIndex[0] == 2){
                totalIncomeBudget.febTotal = totalAmount
                allTotal += totalIncomeBudget.febTotal
            }else if (incomeBudgetIndex[0] == 3){
                totalIncomeBudget.marTotal = totalAmount
                allTotal += totalIncomeBudget.marTotal
            }else if (incomeBudgetIndex[0] == 4){
                totalIncomeBudget.aprTotal = totalAmount
                allTotal += totalIncomeBudget.aprTotal
            }else if (incomeBudgetIndex[0] == 5){
                totalIncomeBudget.mayTotal = totalAmount
                allTotal += totalIncomeBudget.mayTotal
            }else if (incomeBudgetIndex[0] == 6){
                totalIncomeBudget.junTotal = totalAmount
                allTotal += totalIncomeBudget.junTotal
            }else if (incomeBudgetIndex[0] == 7){
                totalIncomeBudget.julTotal = totalAmount
                allTotal += totalIncomeBudget.julTotal
            }else if (incomeBudgetIndex[0] == 8){
                totalIncomeBudget.augTotal = totalAmount
                allTotal += totalIncomeBudget.augTotal
            }else if (incomeBudgetIndex[0] == 9){
                totalIncomeBudget.sepTotal = totalAmount
                allTotal += totalIncomeBudget.sepTotal
            }else if (incomeBudgetIndex[0] == 10){
                totalIncomeBudget.octTotal = totalAmount
                allTotal += totalIncomeBudget.octTotal
            }else if (incomeBudgetIndex[0] == 11){
                totalIncomeBudget.novTotal = totalAmount
                allTotal += totalIncomeBudget.novTotal
            }else if (incomeBudgetIndex[0] == 12){
                totalIncomeBudget.decTotal =  totalAmount
                allTotal += totalIncomeBudget.decTotal
            }
        }

        totalIncomeBudget.allTotal = allTotal

        return totalIncomeBudget;
    }


    def Map getPrivateBudgetTotalIncomeMonthWise(fiscalYearInfo) {

        Map totalIncomeBudget = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                 "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00, "decTotal": 0.00,
                                 "allTotal":0.00]


        LinkedHashMap gridResultTotalBudgetIncomeArr
        String selectTotalBudgetIncomeArr = "pb.booking_period AS bpsm,SUM(pb.total_amount) AS total_budget "
        String selectIndexTotalBudgetIncomeArr = "bpsm,total_budget"
        String fromTotalBudgetIncomeArr = "private_budget_item pb INNER JOIN private_budget_master pm ON pb.budget_name_id = pm.id "
        String whereTotalBudgetIncomeArr = "pb.booking_year ='" + fiscalYearInfo[0][4] + "' AND pb.status=1 AND pm.budget_type = 1"
//        String whereTotalBudgetIncomeArr = "bookingPeriodStartYear ='" + fiscalYearInfo[0][4] + "' AND status=1"
        String orderByTotalBudgetIncomeArr = ""
        String groupByTotalBudgetIncomeArr = "booking_period"
        gridResultTotalBudgetIncomeArr = new BudgetViewDatabaseService().select(selectTotalBudgetIncomeArr,fromTotalBudgetIncomeArr,whereTotalBudgetIncomeArr,orderByTotalBudgetIncomeArr,groupByTotalBudgetIncomeArr,'true',selectIndexTotalBudgetIncomeArr)

        ArrayList totalBudgetIncomeArr = gridResultTotalBudgetIncomeArr['dataGridList']

        def allTotal = 0.00
        totalBudgetIncomeArr.eachWithIndex {incomeBudgetIndex, incomeBudgetKey ->
            def totalAmount = dashboardDetailsTagLib.getRoundedValue(incomeBudgetIndex[1]);
            if (incomeBudgetIndex[0] == 1){
                totalIncomeBudget.janTotal = totalAmount
                allTotal += totalIncomeBudget.janTotal
            }else if (incomeBudgetIndex[0] == 2){
                totalIncomeBudget.febTotal = totalAmount
                allTotal += totalIncomeBudget.febTotal
            }else if (incomeBudgetIndex[0] == 3){
                totalIncomeBudget.marTotal = totalAmount
                allTotal += totalIncomeBudget.marTotal
            }else if (incomeBudgetIndex[0] == 4){
                totalIncomeBudget.aprTotal = totalAmount
                allTotal += totalIncomeBudget.aprTotal
            }else if (incomeBudgetIndex[0] == 5){
                totalIncomeBudget.mayTotal = totalAmount
                allTotal += totalIncomeBudget.mayTotal
            }else if (incomeBudgetIndex[0] == 6){
                totalIncomeBudget.junTotal = totalAmount
                allTotal += totalIncomeBudget.junTotal
            }else if (incomeBudgetIndex[0] == 7){
                totalIncomeBudget.julTotal = totalAmount
                allTotal += totalIncomeBudget.julTotal
            }else if (incomeBudgetIndex[0] == 8){
                totalIncomeBudget.augTotal = totalAmount
                allTotal += totalIncomeBudget.augTotal
            }else if (incomeBudgetIndex[0] == 9){
                totalIncomeBudget.sepTotal = totalAmount
                allTotal += totalIncomeBudget.sepTotal
            }else if (incomeBudgetIndex[0] == 10){
                totalIncomeBudget.octTotal = totalAmount
                allTotal += totalIncomeBudget.octTotal
            }else if (incomeBudgetIndex[0] == 11){
                totalIncomeBudget.novTotal = totalAmount
                allTotal += totalIncomeBudget.novTotal
            }else if (incomeBudgetIndex[0] == 12){
                totalIncomeBudget.decTotal =  totalAmount
                allTotal += totalIncomeBudget.decTotal
            }
        }

        totalIncomeBudget.allTotal = allTotal

        return totalIncomeBudget;
    }

    def Map getPrivateBudgetTotalExpenseMonthWise(fiscalYearInfo) {

        Map totalExpenseBudget = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                  "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00, "decTotal": 0.00,
                                  "allTotal":0.00]


        String selectTotalBudgetExpenseArr="pb.booking_period AS bpsm,SUM(pb.total_amount) AS total_budget"
        String selectIndexTotalBudgetExpenseArr="bpsm,total_budget"
        String fromTotalBudgetExpenseArr="private_budget_item pb INNER JOIN private_budget_master pm ON pb.budget_name_id = pm.id "
        String whereTotalBudgetExpenseArr="pb.booking_year ='" + fiscalYearInfo[0][4] + "' AND pb.status=1 AND pm.budget_type <> 1"
        String orderByTotalBudgetExpenseArr = ""
        String groupByTotalBudgetExpenseArr = "booking_period"

        LinkedHashMap gridResultTotalBudgetExpenseArr
        gridResultTotalBudgetExpenseArr = new BudgetViewDatabaseService().select(selectTotalBudgetExpenseArr,fromTotalBudgetExpenseArr,whereTotalBudgetExpenseArr,orderByTotalBudgetExpenseArr,groupByTotalBudgetExpenseArr,'true',selectIndexTotalBudgetExpenseArr)

        ArrayList totalBudgetExpenseArr = gridResultTotalBudgetExpenseArr['dataGridList']

        def allTotal = 0.00;
        totalBudgetExpenseArr.eachWithIndex {expenseBudgetIndex, incomeBudgetKey ->
            def totalAmount = dashboardDetailsTagLib.getRoundedValue(expenseBudgetIndex[1]);
            totalAmount = dashboardDetailsTagLib.getPositiveValue(totalAmount)
            if (expenseBudgetIndex[0] == 1){
                totalExpenseBudget.janTotal = totalAmount
                allTotal += totalExpenseBudget.janTotal;
            }else if (expenseBudgetIndex[0] == 2){
                totalExpenseBudget.febTotal = totalAmount
                allTotal += totalExpenseBudget.febTotal;
            }else if (expenseBudgetIndex[0] == 3){
                totalExpenseBudget.marTotal = totalAmount
                allTotal += totalExpenseBudget.marTotal;
            }else if (expenseBudgetIndex[0] == 4){
                totalExpenseBudget.aprTotal = totalAmount
                allTotal += totalExpenseBudget.aprTotal;
            }else if (expenseBudgetIndex[0] == 5){
                totalExpenseBudget.mayTotal = totalAmount
                allTotal += totalExpenseBudget.mayTotal;
            }else if (expenseBudgetIndex[0] == 6){
                totalExpenseBudget.junTotal = totalAmount
                allTotal += totalExpenseBudget.junTotal;
            }else if (expenseBudgetIndex[0] == 7){
                totalExpenseBudget.julTotal = totalAmount
                allTotal += totalExpenseBudget.julTotal;
            }else if (expenseBudgetIndex[0] == 8){
                totalExpenseBudget.augTotal = totalAmount
                allTotal += totalExpenseBudget.augTotal;
            }else if (expenseBudgetIndex[0] == 9){
                totalExpenseBudget.sepTotal = totalAmount
                allTotal += totalExpenseBudget.sepTotal;
            }else if (expenseBudgetIndex[0] == 10){
                totalExpenseBudget.octTotal = totalAmount
                allTotal += totalExpenseBudget.octTotal;
            }else if (expenseBudgetIndex[0] == 11){
                totalExpenseBudget.novTotal = totalAmount
                allTotal += totalExpenseBudget.novTotal;
            }else if (expenseBudgetIndex[0] == 12){
                totalExpenseBudget.decTotal = totalAmount
                allTotal += totalExpenseBudget.decTotal;
            }
        }

        totalExpenseBudget.allTotal = allTotal;

        return totalExpenseBudget;
    }


    def Map getPrivateBudgetTotalMonthWise(fiscalYearInfo) {


        Map totalPrivateBudget = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                  "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00, "decTotal": 0.00,
                                  "allTotal":0.00]


        LinkedHashMap gridResultTotalBudgetArr
        String selectTotalBudgetArr = "bookingPeriod AS bpsm,SUM(totalAmount) AS total_private_budget"
        String selectIndexTotalBudgetArr = "bpsm,total_private_budget"
        String fromTotalBudgetArr = "PrivateBudgetItem "
        String whereTotalBudgetArr = "bookingYear ='" + fiscalYearInfo[0][4] + "' AND status=1"
        String orderByTotalBudgetArr = ""
        String groupByTotalBudgetArr = "bookingPeriod"
        gridResultTotalBudgetArr = new BudgetViewDatabaseService().select(selectTotalBudgetArr,fromTotalBudgetArr,whereTotalBudgetArr,
                orderByTotalBudgetArr,groupByTotalBudgetArr,'true',selectIndexTotalBudgetArr)

        ArrayList totalBudgetArr = gridResultTotalBudgetArr['dataGridList']

        def allTotal = 0.00
        totalBudgetArr.eachWithIndex {incomeBudgetIndex, incomeBudgetKey ->

            def totalAmount = dashboardDetailsTagLib.getRoundedValue(incomeBudgetIndex[1]);
            if (incomeBudgetIndex[0] == 1){
                totalPrivateBudget.janTotal = totalAmount
                allTotal += totalPrivateBudget.janTotal
            }else if (incomeBudgetIndex[0] == 2){
                totalPrivateBudget.febTotal = totalAmount
                allTotal += totalPrivateBudget.febTotal
            }else if (incomeBudgetIndex[0] == 3){
                totalPrivateBudget.marTotal = totalAmount
                allTotal += totalPrivateBudget.marTotal
            }else if (incomeBudgetIndex[0] == 4){
                totalPrivateBudget.aprTotal = totalAmount
                allTotal += totalPrivateBudget.aprTotal
            }else if (incomeBudgetIndex[0] == 5){
                totalPrivateBudget.mayTotal = totalAmount
                allTotal += totalPrivateBudget.mayTotal
            }else if (incomeBudgetIndex[0] == 6){
                totalPrivateBudget.junTotal = totalAmount
                allTotal += totalPrivateBudget.junTotal
            }else if (incomeBudgetIndex[0] == 7){
                totalPrivateBudget.julTotal = totalAmount
                allTotal += totalPrivateBudget.julTotal
            }else if (incomeBudgetIndex[0] == 8){
                totalPrivateBudget.augTotal = totalAmount
                allTotal += totalPrivateBudget.augTotal
            }else if (incomeBudgetIndex[0] == 9){
                totalPrivateBudget.sepTotal = totalAmount
                allTotal += totalPrivateBudget.sepTotal
            }else if (incomeBudgetIndex[0] == 10){
                totalPrivateBudget.octTotal = totalAmount
                allTotal += totalPrivateBudget.octTotal
            }else if (incomeBudgetIndex[0] == 11){
                totalPrivateBudget.novTotal = totalAmount
                allTotal += totalPrivateBudget.novTotal
            }else if (incomeBudgetIndex[0] == 12){
                totalPrivateBudget.decTotal =  totalAmount
                allTotal += totalPrivateBudget.decTotal
            }
        }

        totalPrivateBudget.allTotal = allTotal

        return totalPrivateBudget;
    }

    def Map getReservationBudgetTotalMonthWise(fiscalYearInfo) {

        Map totalPrivateBudget = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                  "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00, "decTotal": 0.00,
                                  "allTotal":0.00]


        LinkedHashMap gridResultTotalBudgetArr
        String selectTotalBudgetArr = "bookingPeriodMonth AS bpsm,SUM(totalAmount) AS total_reserv_budget"
        String selectIndexTotalBudgetArr = "bpsm,total_reserv_budget"
        String fromTotalBudgetArr = "ReservationBudgetItem "
        String whereTotalBudgetArr = "bookingPeriodYear ='" + fiscalYearInfo[0][4] + "' AND status=1"
        String orderByTotalBudgetArr = ""
        String groupByTotalBudgetArr = "bookingPeriodMonth"
        gridResultTotalBudgetArr = new BudgetViewDatabaseService().select(selectTotalBudgetArr,fromTotalBudgetArr,whereTotalBudgetArr,
                orderByTotalBudgetArr,groupByTotalBudgetArr,'true',selectIndexTotalBudgetArr)

        ArrayList totalBudgetArr = gridResultTotalBudgetArr['dataGridList']

        def allTotal = 0.00
        totalBudgetArr.eachWithIndex {incomeBudgetIndex, incomeBudgetKey ->

            def totalAmount = dashboardDetailsTagLib.getRoundedValue(incomeBudgetIndex[1]);
            if (incomeBudgetIndex[0] == 1){
                totalPrivateBudget.janTotal = totalAmount
                allTotal += totalPrivateBudget.janTotal
            }else if (incomeBudgetIndex[0] == 2){
                totalPrivateBudget.febTotal = totalAmount
                allTotal += totalPrivateBudget.febTotal
            }else if (incomeBudgetIndex[0] == 3){
                totalPrivateBudget.marTotal = totalAmount
                allTotal += totalPrivateBudget.marTotal
            }else if (incomeBudgetIndex[0] == 4){
                totalPrivateBudget.aprTotal = totalAmount
                allTotal += totalPrivateBudget.aprTotal
            }else if (incomeBudgetIndex[0] == 5){
                totalPrivateBudget.mayTotal = totalAmount
                allTotal += totalPrivateBudget.mayTotal
            }else if (incomeBudgetIndex[0] == 6){
                totalPrivateBudget.junTotal = totalAmount
                allTotal += totalPrivateBudget.junTotal
            }else if (incomeBudgetIndex[0] == 7){
                totalPrivateBudget.julTotal = totalAmount
                allTotal += totalPrivateBudget.julTotal
            }else if (incomeBudgetIndex[0] == 8){
                totalPrivateBudget.augTotal = totalAmount
                allTotal += totalPrivateBudget.augTotal
            }else if (incomeBudgetIndex[0] == 9){
                totalPrivateBudget.sepTotal = totalAmount
                allTotal += totalPrivateBudget.sepTotal
            }else if (incomeBudgetIndex[0] == 10){
                totalPrivateBudget.octTotal = totalAmount
                allTotal += totalPrivateBudget.octTotal
            }else if (incomeBudgetIndex[0] == 11){
                totalPrivateBudget.novTotal = totalAmount
                allTotal += totalPrivateBudget.novTotal
            }else if (incomeBudgetIndex[0] == 12){
                totalPrivateBudget.decTotal =  totalAmount
                allTotal += totalPrivateBudget.decTotal
            }
        }

        totalPrivateBudget.allTotal = allTotal
//        totalPrivateBudget.allTotal = allTotal*(-1)

        return totalPrivateBudget;
    }

    def Map getExpenseBudgetTotalMonthWise(fiscalYearInfo) {

        Map totalExpenseBudget = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                  "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00, "decTotal": 0.00,
                                  "allTotal":0.00]


        String selectTotalBudgetExpenseArr="bookingPeriodStartMonth AS bpsm,SUM(totalGlAmount) AS total_expense_budget"
        String selectIndexTotalBudgetExpenseArr="bpsm,total_expense_budget"
        String fromTotalBudgetExpenseArr="BudgetItemExpense "
        String whereTotalBudgetExpenseArr="bookingPeriodStartYear ='" + fiscalYearInfo[0][4] + "' AND status=1"
        String orderByTotalBudgetExpenseArr = ""
        String groupByTotalBudgetExpenseArr = "bookingPeriodStartMonth"

        LinkedHashMap gridResultTotalBudgetExpenseArr
        gridResultTotalBudgetExpenseArr = new BudgetViewDatabaseService().select(selectTotalBudgetExpenseArr,fromTotalBudgetExpenseArr,whereTotalBudgetExpenseArr,orderByTotalBudgetExpenseArr,groupByTotalBudgetExpenseArr,'true',selectIndexTotalBudgetExpenseArr)

        ArrayList totalBudgetExpenseArr = gridResultTotalBudgetExpenseArr['dataGridList']

        def allTotal = 0.00;
        totalBudgetExpenseArr.eachWithIndex {expenseBudgetIndex, incomeBudgetKey ->
            def totalAmount = dashboardDetailsTagLib.getRoundedValue(expenseBudgetIndex[1]);
            if (expenseBudgetIndex[0] == 1){
                totalExpenseBudget.janTotal = totalAmount
                allTotal += totalExpenseBudget.janTotal;
            }else if (expenseBudgetIndex[0] == 2){
                totalExpenseBudget.febTotal = totalAmount
                allTotal += totalExpenseBudget.febTotal;
            }else if (expenseBudgetIndex[0] == 3){
                totalExpenseBudget.marTotal = totalAmount
                allTotal += totalExpenseBudget.marTotal;
            }else if (expenseBudgetIndex[0] == 4){
                totalExpenseBudget.aprTotal = totalAmount
                allTotal += totalExpenseBudget.aprTotal;
            }else if (expenseBudgetIndex[0] == 5){
                totalExpenseBudget.mayTotal = totalAmount
                allTotal += totalExpenseBudget.mayTotal;
            }else if (expenseBudgetIndex[0] == 6){
                totalExpenseBudget.junTotal = totalAmount
                allTotal += totalExpenseBudget.junTotal;
            }else if (expenseBudgetIndex[0] == 7){
                totalExpenseBudget.julTotal = totalAmount
                allTotal += totalExpenseBudget.julTotal;
            }else if (expenseBudgetIndex[0] == 8){
                totalExpenseBudget.augTotal = totalAmount
                allTotal += totalExpenseBudget.augTotal;
            }else if (expenseBudgetIndex[0] == 9){
                totalExpenseBudget.sepTotal = totalAmount
                allTotal += totalExpenseBudget.sepTotal;
            }else if (expenseBudgetIndex[0] == 10){
                totalExpenseBudget.octTotal = totalAmount
                allTotal += totalExpenseBudget.octTotal;
            }else if (expenseBudgetIndex[0] == 11){
                totalExpenseBudget.novTotal = totalAmount
                allTotal += totalExpenseBudget.novTotal;
            }else if (expenseBudgetIndex[0] == 12){
                totalExpenseBudget.decTotal = totalAmount
                allTotal += totalExpenseBudget.decTotal;
            }
        }

        totalExpenseBudget.allTotal = allTotal

        return totalExpenseBudget;
    }


    /*
    * for reservation
    * *//*

    def Map getReservationBudgetTotalMonthWise(fiscalYearInfo) {

        Map totalExpenseBudget = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                  "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00, "decTotal": 0.00,
                                  "allTotal":0.00]


        String selectTotalBudgetExpenseArr="bookingPeriodStartMonth AS bpsm,SUM(totalGlAmount) AS total_expense_budget"
        String selectIndexTotalBudgetExpenseArr="bpsm,total_expense_budget"
        String fromTotalBudgetExpenseArr="BudgetItemExpense "
        String whereTotalBudgetExpenseArr="bookingPeriodStartYear ='" + fiscalYearInfo[0][4] + "' AND status=1"
        String orderByTotalBudgetExpenseArr = ""
        String groupByTotalBudgetExpenseArr = "bookingPeriodStartMonth"

        LinkedHashMap gridResultTotalBudgetExpenseArr
        gridResultTotalBudgetExpenseArr = new BudgetViewDatabaseService().select(selectTotalBudgetExpenseArr,fromTotalBudgetExpenseArr,whereTotalBudgetExpenseArr,orderByTotalBudgetExpenseArr,groupByTotalBudgetExpenseArr,'true',selectIndexTotalBudgetExpenseArr)

        ArrayList totalBudgetExpenseArr = gridResultTotalBudgetExpenseArr['dataGridList']

        def allTotal = 0.00;
        totalBudgetExpenseArr.eachWithIndex {expenseBudgetIndex, incomeBudgetKey ->
            def totalAmount = dashboardDetailsTagLib.getRoundedValue(expenseBudgetIndex[1]);
            if (expenseBudgetIndex[0] == 1){
                totalExpenseBudget.janTotal = totalAmount
                allTotal += totalExpenseBudget.janTotal;
            }else if (expenseBudgetIndex[0] == 2){
                totalExpenseBudget.febTotal = totalAmount
                allTotal += totalExpenseBudget.febTotal;
            }else if (expenseBudgetIndex[0] == 3){
                totalExpenseBudget.marTotal = totalAmount
                allTotal += totalExpenseBudget.marTotal;
            }else if (expenseBudgetIndex[0] == 4){
                totalExpenseBudget.aprTotal = totalAmount
                allTotal += totalExpenseBudget.aprTotal;
            }else if (expenseBudgetIndex[0] == 5){
                totalExpenseBudget.mayTotal = totalAmount
                allTotal += totalExpenseBudget.mayTotal;
            }else if (expenseBudgetIndex[0] == 6){
                totalExpenseBudget.junTotal = totalAmount
                allTotal += totalExpenseBudget.junTotal;
            }else if (expenseBudgetIndex[0] == 7){
                totalExpenseBudget.julTotal = totalAmount
                allTotal += totalExpenseBudget.julTotal;
            }else if (expenseBudgetIndex[0] == 8){
                totalExpenseBudget.augTotal = totalAmount
                allTotal += totalExpenseBudget.augTotal;
            }else if (expenseBudgetIndex[0] == 9){
                totalExpenseBudget.sepTotal = totalAmount
                allTotal += totalExpenseBudget.sepTotal;
            }else if (expenseBudgetIndex[0] == 10){
                totalExpenseBudget.octTotal = totalAmount
                allTotal += totalExpenseBudget.octTotal;
            }else if (expenseBudgetIndex[0] == 11){
                totalExpenseBudget.novTotal = totalAmount
                allTotal += totalExpenseBudget.novTotal;
            }else if (expenseBudgetIndex[0] == 12){
                totalExpenseBudget.decTotal = totalAmount
                allTotal += totalExpenseBudget.decTotal;
            }
        }

        totalExpenseBudget.allTotal = allTotal

        return totalExpenseBudget;
    }*/



    def Map getNetProfitBasedOnInvoice(grossProfitInvoice,incomeTaxPercentage) {
        Map netProfit = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                         "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal":0.00]


        for(int i=0;i<12;i++){
            if(i == 0){
                netProfit.janTotal = grossProfitInvoice.janTotal - (grossProfitInvoice.janTotal*incomeTaxPercentage)/100;
            }else if(i == 1){
                netProfit.febTotal = grossProfitInvoice.febTotal - (grossProfitInvoice.febTotal*incomeTaxPercentage)/100;
            }else if(i == 2){
                netProfit.marTotal = grossProfitInvoice.marTotal - (grossProfitInvoice.marTotal*incomeTaxPercentage)/100;
            }else if(i == 3){
                netProfit.aprTotal = grossProfitInvoice.aprTotal - (grossProfitInvoice.aprTotal*incomeTaxPercentage)/100;
            }else if(i == 4){
                netProfit.mayTotal = grossProfitInvoice.mayTotal - (grossProfitInvoice.mayTotal*incomeTaxPercentage)/100;
            }else if(i == 5){
                netProfit.junTotal = grossProfitInvoice.junTotal - (grossProfitInvoice.junTotal*incomeTaxPercentage)/100;
            }else if(i == 6){
                netProfit.julTotal = grossProfitInvoice.julTotal - (grossProfitInvoice.julTotal*incomeTaxPercentage)/100;
            }else if(i == 7){
                netProfit.augTotal = grossProfitInvoice.augTotal - (grossProfitInvoice.augTotal*incomeTaxPercentage)/100;
            }else if(i == 8){
                netProfit.sepTotal = grossProfitInvoice.sepTotal - (grossProfitInvoice.sepTotal*incomeTaxPercentage)/100;
            }else if(i == 9){
                netProfit.octTotal = grossProfitInvoice.octTotal - (grossProfitInvoice.octTotal*incomeTaxPercentage)/100;
            }else if(i == 10){
                netProfit.novTotal = grossProfitInvoice.novTotal - (grossProfitInvoice.novTotal*incomeTaxPercentage)/100;
            }else if(i == 11){
                netProfit.decTotal = grossProfitInvoice.decTotal - (grossProfitInvoice.decTotal*incomeTaxPercentage)/100;
            }
        }

        return netProfit;
    }

    def Map getNetProfitBasedOnBooking(grossProfitBooking,incomeTaxPercentage){

        Map netProfit = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                         "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal":0.00,
                         "allTotal": 0.00]

        def allTotal = 0.00;
        for(int i=0;i<12;i++){
            if(i == 0){
                netProfit.janTotal = grossProfitBooking.janTotal - (grossProfitBooking.janTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.janTotal;
            }else if(i == 1){
                netProfit.febTotal = grossProfitBooking.febTotal - (grossProfitBooking.febTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.febTotal;
            }else if(i == 2){
                netProfit.marTotal = grossProfitBooking.marTotal - (grossProfitBooking.marTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.marTotal;
            }else if(i == 3){
                netProfit.aprTotal = grossProfitBooking.aprTotal - (grossProfitBooking.aprTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.aprTotal;
            }else if(i == 4){
                netProfit.mayTotal = grossProfitBooking.mayTotal - (grossProfitBooking.mayTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.mayTotal;
            }else if(i == 5){
                netProfit.junTotal = grossProfitBooking.junTotal - (grossProfitBooking.junTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.junTotal;
            }else if(i == 6){
                netProfit.julTotal = grossProfitBooking.julTotal - (grossProfitBooking.julTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.julTotal;
            }else if(i == 7){
                netProfit.augTotal = grossProfitBooking.augTotal - (grossProfitBooking.augTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.augTotal;
            }else if(i == 8){
                netProfit.sepTotal = grossProfitBooking.sepTotal - (grossProfitBooking.sepTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.sepTotal;
            }else if(i == 9){
                netProfit.octTotal = grossProfitBooking.octTotal - (grossProfitBooking.octTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.octTotal;
            }else if(i == 10){
                netProfit.novTotal = grossProfitBooking.novTotal - (grossProfitBooking.novTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.novTotal;
            }else if(i == 11){
                netProfit.decTotal = grossProfitBooking.decTotal - (grossProfitBooking.decTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.decTotal;
            }
        }

        netProfit.allTotal = allTotal;

        return netProfit;
    }

    def Map getNetProfitBasedOnBudget(grossProfitBudget,incomeTaxPercentage) {
        Map netProfit = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                         "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal":0.00,
                         "allTotal": 0.00]

        def allTotal = 0.00;
        for(int i=0;i<12;i++){
            if(i == 0){
                netProfit.janTotal = grossProfitBudget.janTotal - (grossProfitBudget.janTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.janTotal;
            }else if(i == 1){
                netProfit.febTotal = grossProfitBudget.febTotal - (grossProfitBudget.febTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.febTotal;
            }else if(i == 2){
                netProfit.marTotal = grossProfitBudget.marTotal - (grossProfitBudget.marTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.marTotal;
            }else if(i == 3){
                netProfit.aprTotal = grossProfitBudget.aprTotal - (grossProfitBudget.aprTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.aprTotal;
            }else if(i == 4){
                netProfit.mayTotal = grossProfitBudget.mayTotal - (grossProfitBudget.mayTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.mayTotal;
            }else if(i == 5){
                netProfit.junTotal = grossProfitBudget.junTotal - (grossProfitBudget.junTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.junTotal;
            }else if(i == 6){
                netProfit.julTotal = grossProfitBudget.julTotal - (grossProfitBudget.julTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.julTotal;
            }else if(i == 7){
                netProfit.augTotal = grossProfitBudget.augTotal - (grossProfitBudget.augTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.augTotal;
            }else if(i == 8){
                netProfit.sepTotal = grossProfitBudget.sepTotal - (grossProfitBudget.sepTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.sepTotal;
            }else if(i == 9){
                netProfit.octTotal = grossProfitBudget.octTotal - (grossProfitBudget.octTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.octTotal;
            }else if(i == 10){
                netProfit.novTotal = grossProfitBudget.novTotal - (grossProfitBudget.novTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.novTotal;
            }else if(i == 11){
                netProfit.decTotal = grossProfitBudget.decTotal - (grossProfitBudget.decTotal*incomeTaxPercentage)/100;
                allTotal += netProfit.decTotal;
            }
        }
        netProfit.allTotal = allTotal;
        return netProfit;
    }

    def Map getGrossProfitBasedOnInvoice(totalInvoiceIncome,totalInvoiceExpense) {

        Map grossProfit = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                           "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal":0.00]

        for(int i=0;i<12;i++){
            if(i == 0){
                grossProfit.janTotal = totalInvoiceIncome.janTotal - totalInvoiceExpense.janTotal
            }else if(i == 1){
                grossProfit.febTotal = totalInvoiceIncome.febTotal - totalInvoiceExpense.febTotal
            }else if(i == 2){
                grossProfit.marTotal = totalInvoiceIncome.marTotal - totalInvoiceExpense.marTotal
            }else if(i == 3){
                grossProfit.aprTotal = totalInvoiceIncome.aprTotal - totalInvoiceExpense.aprTotal
            }else if(i == 4){
                grossProfit.mayTotal = totalInvoiceIncome.mayTotal - totalInvoiceExpense.mayTotal
            }else if(i == 5){
                grossProfit.junTotal = totalInvoiceIncome.junTotal - totalInvoiceExpense.junTotal
            }else if(i == 6){
                grossProfit.julTotal = totalInvoiceIncome.julTotal - totalInvoiceExpense.julTotal
            }else if(i == 7){
                grossProfit.augTotal = totalInvoiceIncome.augTotal - totalInvoiceExpense.augTotal
            }else if(i == 8){
                grossProfit.sepTotal = totalInvoiceIncome.sepTotal - totalInvoiceExpense.sepTotal
            }else if(i == 9){
                grossProfit.octTotal = totalInvoiceIncome.octTotal - totalInvoiceExpense.octTotal
            }else if(i == 10){
                grossProfit.novTotal = totalInvoiceIncome.novTotal - totalInvoiceExpense.novTotal
            }else if(i == 11){
                grossProfit.decTotal = totalInvoiceIncome.decTotal - totalInvoiceExpense.decTotal
            }
        }

        return grossProfit;

    }

    def Double getForecastValue(valInvoiceIncome,valInvoiceExpense,valIncomeBudget,valExpenseBudget){

        def forecastProfit = 0.00;
        def forecastExpenseTemp = 0.00;

        def forecastIncomeTemp = 0.00;

//        println("valInvoiceIncome "+ valInvoiceIncome)
//        println("valInvoiceExpense"+ valInvoiceExpense)
//        println("valIncomeBudget "+ valIncomeBudget)
//        println("valExpenseBudget "+ valExpenseBudget)

        //INCOME
        if(valInvoiceIncome == 0.00){
            forecastIncomeTemp = valIncomeBudget
        }
        else{
            forecastIncomeTemp = valInvoiceIncome
        }

//        println("forecastIncomeTemp "+ forecastIncomeTemp)

        //EXPENSE
        if(valInvoiceExpense == 0.00){
            forecastExpenseTemp = valExpenseBudget
        }else{
            forecastExpenseTemp = valInvoiceExpense
        }

//        println("forecastExpenseTemp "+ forecastExpenseTemp)

        forecastProfit = forecastIncomeTemp - forecastExpenseTemp;

        return forecastProfit;
    }

    def Map getForecastOfGrossProfit(incomeForcast,expenseForcast){

        Map forecastGrossProfit = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                   "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal":0.00,
                                   "allTotal": 0.00]

        def allTotal = 0.00;
        for(int i=0;i<12;i++){
            if(i == 0){
                forecastGrossProfit.janTotal = incomeForcast.janTotal - expenseForcast.janTotal;
                allTotal += forecastGrossProfit.janTotal;
            }else if(i == 1){
                forecastGrossProfit.febTotal = incomeForcast.febTotal - expenseForcast.febTotal;
                allTotal += forecastGrossProfit.febTotal;
            }else if(i == 2){
                forecastGrossProfit.marTotal = incomeForcast.marTotal - expenseForcast.marTotal;
                allTotal += forecastGrossProfit.marTotal;
            }else if(i == 3){
                forecastGrossProfit.aprTotal = incomeForcast.aprTotal - expenseForcast.aprTotal;
                allTotal += forecastGrossProfit.aprTotal;
            }else if(i == 4){
                forecastGrossProfit.mayTotal = incomeForcast.mayTotal - expenseForcast.mayTotal;
                allTotal += forecastGrossProfit.mayTotal;
            }else if(i == 5){
                forecastGrossProfit.junTotal = incomeForcast.junTotal - expenseForcast.junTotal;
                allTotal += forecastGrossProfit.junTotal;
            }else if(i == 6){
                forecastGrossProfit.julTotal = incomeForcast.julTotal - expenseForcast.julTotal;
                allTotal += forecastGrossProfit.julTotal;
            }else if(i == 7){
                forecastGrossProfit.augTotal = incomeForcast.augTotal - expenseForcast.augTotal;
                allTotal += forecastGrossProfit.augTotal;
            }else if(i == 8){
                forecastGrossProfit.sepTotal = incomeForcast.sepTotal - expenseForcast.sepTotal;
                allTotal += forecastGrossProfit.sepTotal;
            }else if(i == 9){
                forecastGrossProfit.octTotal = incomeForcast.octTotal - expenseForcast.octTotal;
                allTotal += forecastGrossProfit.octTotal;
            }else if(i == 10){
                forecastGrossProfit.novTotal = incomeForcast.novTotal - expenseForcast.novTotal;
                allTotal += forecastGrossProfit.novTotal;
            }else if(i == 11){
                forecastGrossProfit.decTotal = incomeForcast.decTotal - expenseForcast.decTotal;
                allTotal += forecastGrossProfit.decTotal;
            }
        }

        forecastGrossProfit.allTotal = allTotal;

//        println("forecastGrossProfit" + forecastGrossProfit)

        return forecastGrossProfit;
    }

    def Map getForecastOfGrossProfitForReservation(incomeForcast,expenseForcast, reservationForcast){

        Map forecastGrossProfit = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                   "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal":0.00,
                                   "allTotal": 0.00]

        def allTotal = 0.00;
        for(int i=0;i<12;i++){
            if(i == 0){
                forecastGrossProfit.janTotal = incomeForcast.janTotal - expenseForcast.janTotal - reservationForcast.janTotal;
                allTotal += forecastGrossProfit.janTotal;
            }else if(i == 1){
                forecastGrossProfit.febTotal = incomeForcast.febTotal - expenseForcast.febTotal - reservationForcast.febTotal;
                allTotal += forecastGrossProfit.febTotal;
            }else if(i == 2){
                forecastGrossProfit.marTotal = incomeForcast.marTotal - expenseForcast.marTotal - reservationForcast.marTotal;
                allTotal += forecastGrossProfit.marTotal;
            }else if(i == 3){
                forecastGrossProfit.aprTotal = incomeForcast.aprTotal - expenseForcast.aprTotal - reservationForcast.aprTotal;
                allTotal += forecastGrossProfit.aprTotal;
            }else if(i == 4){
                forecastGrossProfit.mayTotal = incomeForcast.mayTotal - expenseForcast.mayTotal - reservationForcast.mayTotal;
                allTotal += forecastGrossProfit.mayTotal;
            }else if(i == 5){
                forecastGrossProfit.junTotal = incomeForcast.junTotal - expenseForcast.junTotal - reservationForcast.junTotal;
                allTotal += forecastGrossProfit.junTotal;
            }else if(i == 6){
                forecastGrossProfit.julTotal = incomeForcast.julTotal - expenseForcast.julTotal - reservationForcast.julTotal;
                allTotal += forecastGrossProfit.julTotal;
            }else if(i == 7){
                forecastGrossProfit.augTotal = incomeForcast.augTotal - expenseForcast.augTotal - reservationForcast.augTotal;
                allTotal += forecastGrossProfit.augTotal;
            }else if(i == 8){
                forecastGrossProfit.sepTotal = incomeForcast.sepTotal - expenseForcast.sepTotal - reservationForcast.sepTotal;
                allTotal += forecastGrossProfit.sepTotal;
            }else if(i == 9){
                forecastGrossProfit.octTotal = incomeForcast.octTotal - expenseForcast.octTotal - reservationForcast.octTotal;
                allTotal += forecastGrossProfit.octTotal;
            }else if(i == 10){
                forecastGrossProfit.novTotal = incomeForcast.novTotal - expenseForcast.novTotal - reservationForcast.novTotal;
                allTotal += forecastGrossProfit.novTotal;
            }else if(i == 11){
                forecastGrossProfit.decTotal = incomeForcast.decTotal - expenseForcast.decTotal - reservationForcast.decTotal;
                allTotal += forecastGrossProfit.decTotal;
            }
        }

        forecastGrossProfit.allTotal = allTotal;

//        println("forecastGrossProfit" + forecastGrossProfit)

        return forecastGrossProfit;
    }


    def Map getForecastOfNetProfit(forecastGrossProfit,incomeTaxPercentage) {

        Map forecastNetProfit = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                 "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal":0.00,
                                 "allTotal": 0.00]

        def allTotal = 0.00;
        for(int i=0;i<12;i++){
            if(i == 0){
                forecastNetProfit.janTotal = forecastGrossProfit.janTotal - (forecastGrossProfit.janTotal*incomeTaxPercentage)/100;
                allTotal += forecastNetProfit.janTotal;
            }else if(i == 1){
                forecastNetProfit.febTotal = forecastGrossProfit.febTotal - (forecastGrossProfit.febTotal*incomeTaxPercentage)/100;
                allTotal += forecastNetProfit.febTotal;
            }else if(i == 2){
                forecastNetProfit.marTotal = forecastGrossProfit.marTotal - (forecastGrossProfit.marTotal*incomeTaxPercentage)/100;
                allTotal += forecastNetProfit.marTotal;
            }else if(i == 3){
                forecastNetProfit.aprTotal = forecastGrossProfit.aprTotal - (forecastGrossProfit.aprTotal*incomeTaxPercentage)/100;
                allTotal += forecastNetProfit.aprTotal;
            }else if(i == 4){
                forecastNetProfit.mayTotal = forecastGrossProfit.mayTotal - (forecastGrossProfit.mayTotal*incomeTaxPercentage)/100;
                allTotal += forecastNetProfit.mayTotal;
            }else if(i == 5){
                forecastNetProfit.junTotal = forecastGrossProfit.junTotal - (forecastGrossProfit.junTotal*incomeTaxPercentage)/100;
                allTotal += forecastNetProfit.junTotal;
            }else if(i == 6){
                forecastNetProfit.julTotal = forecastGrossProfit.julTotal - (forecastGrossProfit.julTotal*incomeTaxPercentage)/100;
                allTotal += forecastNetProfit.julTotal;
            }else if(i == 7){
                forecastNetProfit.augTotal = forecastGrossProfit.augTotal - (forecastGrossProfit.augTotal*incomeTaxPercentage)/100;
                allTotal += forecastNetProfit.augTotal;
            }else if(i == 8){
                forecastNetProfit.sepTotal = forecastGrossProfit.sepTotal - (forecastGrossProfit.sepTotal*incomeTaxPercentage)/100;
                allTotal += forecastNetProfit.sepTotal;
            }else if(i == 9){
                forecastNetProfit.octTotal = forecastGrossProfit.octTotal - (forecastGrossProfit.octTotal*incomeTaxPercentage)/100;
                allTotal += forecastNetProfit.octTotal;
            }else if(i == 10){
                forecastNetProfit.novTotal = forecastGrossProfit.novTotal - (forecastGrossProfit.novTotal*incomeTaxPercentage)/100;
                allTotal += forecastNetProfit.novTotal;
            }else if(i == 11){
                forecastNetProfit.decTotal = forecastGrossProfit.decTotal - (forecastGrossProfit.decTotal*incomeTaxPercentage)/100;
                allTotal += forecastNetProfit.decTotal;
            }
        }

        forecastNetProfit.allTotal = allTotal;

        return forecastNetProfit;
    }

    def Map getGrossProfitBasedOnBooking(totalInvoiceIncome,totalInvoiceExpense){

        Map grossProfit = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                           "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal":0.00,
                           "allTotal": 0.00]

        def allTotal = 0.00;
        for(int i=0;i<12;i++){
            if(i == 0){
                grossProfit.janTotal = totalInvoiceIncome.janTotal - totalInvoiceExpense.janTotal
                allTotal += grossProfit.janTotal;
            }else if(i == 1){
                grossProfit.febTotal = totalInvoiceIncome.febTotal - totalInvoiceExpense.febTotal
                allTotal += grossProfit.febTotal;
            }else if(i == 2){
                grossProfit.marTotal = totalInvoiceIncome.marTotal - totalInvoiceExpense.marTotal
                allTotal += grossProfit.marTotal;
            }else if(i == 3){
                grossProfit.aprTotal = totalInvoiceIncome.aprTotal - totalInvoiceExpense.aprTotal
                allTotal += grossProfit.aprTotal;
            }else if(i == 4){
                grossProfit.mayTotal = totalInvoiceIncome.mayTotal - totalInvoiceExpense.mayTotal
                allTotal += grossProfit.mayTotal;
            }else if(i == 5){
                grossProfit.junTotal = totalInvoiceIncome.junTotal - totalInvoiceExpense.junTotal
                allTotal += grossProfit.junTotal;
            }else if(i == 6){
                grossProfit.julTotal = totalInvoiceIncome.julTotal - totalInvoiceExpense.julTotal
                allTotal += grossProfit.julTotal;
            }else if(i == 7){
                grossProfit.augTotal = totalInvoiceIncome.augTotal - totalInvoiceExpense.augTotal
                allTotal += grossProfit.augTotal;
            }else if(i == 8){
                grossProfit.sepTotal = totalInvoiceIncome.sepTotal - totalInvoiceExpense.sepTotal
                allTotal += grossProfit.sepTotal;
            }else if(i == 9){
                grossProfit.octTotal = totalInvoiceIncome.octTotal - totalInvoiceExpense.octTotal
                allTotal += grossProfit.octTotal;
            }else if(i == 10){
                grossProfit.novTotal = totalInvoiceIncome.novTotal - totalInvoiceExpense.novTotal
                allTotal += grossProfit.novTotal;
            }else if(i == 11){
                grossProfit.decTotal = totalInvoiceIncome.decTotal - totalInvoiceExpense.decTotal
                allTotal += grossProfit.decTotal;
            }
        }

        grossProfit.allTotal = allTotal;

        return grossProfit;
    }

    /*
    * For reservation
    * */
    def Map getGrossProfitBasedOnBookingForReservation(totalInvoiceIncome,totalInvoiceExpense, totalInvoiceReservation ){

        Map grossProfit = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                           "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal":0.00,
                           "allTotal": 0.00]

        def allTotal = 0.00;
        for(int i=0;i<12;i++){
            if(i == 0){
                grossProfit.janTotal = totalInvoiceIncome.janTotal - totalInvoiceExpense.janTotal - (totalInvoiceReservation.janTotal*(-1))
                allTotal += grossProfit.janTotal;
            }else if(i == 1){
                grossProfit.febTotal = totalInvoiceIncome.febTotal - totalInvoiceExpense.febTotal - (totalInvoiceReservation.febTotal*(-1))
                allTotal += grossProfit.febTotal;
            }else if(i == 2){
                grossProfit.marTotal = totalInvoiceIncome.marTotal - totalInvoiceExpense.marTotal - (totalInvoiceReservation.marTotal*(-1))
                allTotal += grossProfit.marTotal;
            }else if(i == 3){
                grossProfit.aprTotal = totalInvoiceIncome.aprTotal - totalInvoiceExpense.aprTotal - (totalInvoiceReservation.aprTotal*(-1))
                allTotal += grossProfit.aprTotal;
            }else if(i == 4){
                grossProfit.mayTotal = totalInvoiceIncome.mayTotal - totalInvoiceExpense.mayTotal - (totalInvoiceReservation.mayTotal*(-1))
                allTotal += grossProfit.mayTotal;
            }else if(i == 5){
                grossProfit.junTotal = totalInvoiceIncome.junTotal - totalInvoiceExpense.junTotal - (totalInvoiceReservation.junTotal*(-1))
                allTotal += grossProfit.junTotal;
            }else if(i == 6){
                grossProfit.julTotal = totalInvoiceIncome.julTotal - totalInvoiceExpense.julTotal - (totalInvoiceReservation.julTotal*(-1))
                allTotal += grossProfit.julTotal;
            }else if(i == 7){
                grossProfit.augTotal = totalInvoiceIncome.augTotal - totalInvoiceExpense.augTotal - (totalInvoiceReservation.augTotal*(-1))
                allTotal += grossProfit.augTotal;
            }else if(i == 8){
                grossProfit.sepTotal = totalInvoiceIncome.sepTotal - totalInvoiceExpense.sepTotal - (totalInvoiceReservation.sepTotal*(-1))
                allTotal += grossProfit.sepTotal;
            }else if(i == 9){
                grossProfit.octTotal = totalInvoiceIncome.octTotal - totalInvoiceExpense.octTotal - (totalInvoiceReservation.octTotal*(-1))
                allTotal += grossProfit.octTotal;
            }else if(i == 10){
                grossProfit.novTotal = totalInvoiceIncome.novTotal - totalInvoiceExpense.novTotal - (totalInvoiceReservation.novTotal*(-1))
                allTotal += grossProfit.novTotal;
            }else if(i == 11){
                grossProfit.decTotal = totalInvoiceIncome.decTotal - totalInvoiceExpense.decTotal - (totalInvoiceReservation.decTotal*(-1))
                allTotal += grossProfit.decTotal;
            }
        }

        grossProfit.allTotal = allTotal;

        return grossProfit;
    }

    def Double getTotalGrossProfitFromMonthAmount(grossProfitMonthly){

        Double totalProfit = 0.00;
        for(int i=0;i<12;i++){
            if(i == 0){
                totalProfit = totalProfit +grossProfitMonthly.janTotal;
            }else if(i == 1){
                totalProfit = totalProfit + grossProfitMonthly.febTotal
            }else if(i == 2){
                totalProfit = totalProfit + grossProfitMonthly.marTotal
            }else if(i == 3){
                totalProfit = totalProfit + grossProfitMonthly.aprTotal
            }else if(i == 4){
                totalProfit = totalProfit + grossProfitMonthly.mayTotal
            }else if(i == 5){
                totalProfit = totalProfit + grossProfitMonthly.junTotal
            }else if(i == 6){
                totalProfit = totalProfit + grossProfitMonthly.julTotal
            }else if(i == 7){
                totalProfit = totalProfit + grossProfitMonthly.augTotal
            }else if(i == 8){
                totalProfit = totalProfit + grossProfitMonthly.sepTotal
            }else if(i == 9){
                totalProfit = totalProfit + grossProfitMonthly.octTotal
            }else if(i == 10){
                totalProfit = totalProfit + grossProfitMonthly.novTotal
            }else if(i == 11){
                totalProfit = totalProfit + grossProfitMonthly.decTotal
            }
        }

        return totalProfit;
    }

    def Map getGrossProfitBasedOnBudget(totalIncomeBudget,totalExpenseBudget) {

        Map grossProfit = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                           "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal":0.00,
                           "allTotal": 0.00]


        def allTotal = 0.00;
        for(int i=0;i<12;i++){
            if(i == 0){
                grossProfit.janTotal = totalIncomeBudget.janTotal - totalExpenseBudget.janTotal
                allTotal += grossProfit.janTotal;
            }else if(i == 1){
                grossProfit.febTotal = totalIncomeBudget.febTotal - totalExpenseBudget.febTotal
                allTotal += grossProfit.febTotal;
            }else if(i == 2){
                grossProfit.marTotal = totalIncomeBudget.marTotal - totalExpenseBudget.marTotal
                allTotal += grossProfit.marTotal;
            }else if(i == 3){
                grossProfit.aprTotal = totalIncomeBudget.aprTotal - totalExpenseBudget.aprTotal
                allTotal += grossProfit.aprTotal;
            }else if(i == 4){
                grossProfit.mayTotal = totalIncomeBudget.mayTotal - totalExpenseBudget.mayTotal
                allTotal += grossProfit.mayTotal;
            }else if(i == 5){
                grossProfit.junTotal = totalIncomeBudget.junTotal - totalExpenseBudget.junTotal
                allTotal += grossProfit.junTotal;
            }else if(i == 6){
                grossProfit.julTotal = totalIncomeBudget.julTotal - totalExpenseBudget.julTotal
                allTotal += grossProfit.julTotal;
            }else if(i == 7){
                grossProfit.augTotal = totalIncomeBudget.augTotal - totalExpenseBudget.augTotal
                allTotal += grossProfit.augTotal;
            }else if(i == 8){
                grossProfit.sepTotal = totalIncomeBudget.sepTotal - totalExpenseBudget.sepTotal
                allTotal += grossProfit.sepTotal;
            }else if(i == 9){
                grossProfit.octTotal = totalIncomeBudget.octTotal - totalExpenseBudget.octTotal
                allTotal += grossProfit.octTotal;
            }else if(i == 10){
                grossProfit.novTotal = totalIncomeBudget.novTotal - totalExpenseBudget.novTotal
                allTotal += grossProfit.novTotal;
            }else if(i == 11){
                grossProfit.decTotal = totalIncomeBudget.decTotal - totalExpenseBudget.decTotal
                allTotal += grossProfit.decTotal;
            }
        }

        grossProfit.allTotal = allTotal;

        return grossProfit;
    }

    /*
    * For reservation
    * */
    def Map getGrossProfitBasedOnBudgetForReservation(totalIncomeBudget,totalExpenseBudget,totalReservationBudget) {

        Map grossProfit = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                           "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal":0.00,
                           "allTotal": 0.00]


        def allTotal = 0.00;
        for(int i=0;i<12;i++){
            if(i == 0){
                grossProfit.janTotal = totalIncomeBudget.janTotal - totalExpenseBudget.janTotal - totalReservationBudget.janTotal
                allTotal += grossProfit.janTotal;
            }else if(i == 1){
                grossProfit.febTotal = totalIncomeBudget.febTotal - totalExpenseBudget.febTotal- totalReservationBudget.febTotal
                allTotal += grossProfit.febTotal;
            }else if(i == 2){
                grossProfit.marTotal = totalIncomeBudget.marTotal - totalExpenseBudget.marTotal - totalReservationBudget.marTotal
                allTotal += grossProfit.marTotal;
            }else if(i == 3){
                grossProfit.aprTotal = totalIncomeBudget.aprTotal - totalExpenseBudget.aprTotal - totalReservationBudget.aprTotal
                allTotal += grossProfit.aprTotal;
            }else if(i == 4){
                grossProfit.mayTotal = totalIncomeBudget.mayTotal - totalExpenseBudget.mayTotal - totalReservationBudget.mayTotal
                allTotal += grossProfit.mayTotal;
            }else if(i == 5){
                grossProfit.junTotal = totalIncomeBudget.junTotal - totalExpenseBudget.junTotal - totalReservationBudget.junTotal
                allTotal += grossProfit.junTotal;
            }else if(i == 6){
                grossProfit.julTotal = totalIncomeBudget.julTotal - totalExpenseBudget.julTotal - totalReservationBudget.julTotal
                allTotal += grossProfit.julTotal;
            }else if(i == 7){
                grossProfit.augTotal = totalIncomeBudget.augTotal - totalExpenseBudget.augTotal - totalReservationBudget.augTotal
                allTotal += grossProfit.augTotal;
            }else if(i == 8){
                grossProfit.sepTotal = totalIncomeBudget.sepTotal - totalExpenseBudget.sepTotal - totalReservationBudget.sepTotal
                allTotal += grossProfit.sepTotal;
            }else if(i == 9){
                grossProfit.octTotal = totalIncomeBudget.octTotal - totalExpenseBudget.octTotal - totalReservationBudget.octTotal
                allTotal += grossProfit.octTotal;
            }else if(i == 10){
                grossProfit.novTotal = totalIncomeBudget.novTotal - totalExpenseBudget.novTotal - totalReservationBudget.novTotal
                allTotal += grossProfit.novTotal;
            }else if(i == 11){
                grossProfit.decTotal = totalIncomeBudget.decTotal - totalExpenseBudget.decTotal - totalReservationBudget.decTotal
                allTotal += grossProfit.decTotal;
            }
        }

        grossProfit.allTotal = allTotal;

        return grossProfit;
    }


    def Map getAmountWithoutTax(withTaxData,incomeTaxPercentage) {
        Map withoutTaxData = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                              "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal":0.00,
                              "allTotal": 0.00]

        def allTotal = 0.00;
        for(int i=0;i<12;i++){
            if(i == 0){
                withoutTaxData.janTotal = withTaxData.janTotal - (withTaxData.janTotal*incomeTaxPercentage)/100;
                allTotal += withoutTaxData.janTotal;
            }else if(i == 1){
                withoutTaxData.febTotal = withTaxData.febTotal - (withTaxData.febTotal*incomeTaxPercentage)/100;
                allTotal += withoutTaxData.febTotal;
            }else if(i == 2){
                withoutTaxData.marTotal = withTaxData.marTotal - (withTaxData.marTotal*incomeTaxPercentage)/100;
                allTotal += withoutTaxData.marTotal;
            }else if(i == 3){
                withoutTaxData.aprTotal = withTaxData.aprTotal - (withTaxData.aprTotal*incomeTaxPercentage)/100;
                allTotal += withoutTaxData.aprTotal;
            }else if(i == 4){
                withoutTaxData.mayTotal = withTaxData.mayTotal - (withTaxData.mayTotal*incomeTaxPercentage)/100;
                allTotal += withoutTaxData.mayTotal;
            }else if(i == 5){
                withoutTaxData.junTotal = withTaxData.junTotal - (withTaxData.junTotal*incomeTaxPercentage)/100;
                allTotal += withoutTaxData.junTotal;
            }else if(i == 6){
                withoutTaxData.julTotal = withTaxData.julTotal - (withTaxData.julTotal*incomeTaxPercentage)/100;
                allTotal += withoutTaxData.julTotal;
            }else if(i == 7){
                withoutTaxData.augTotal = withTaxData.augTotal - (withTaxData.augTotal*incomeTaxPercentage)/100;
                allTotal += withoutTaxData.augTotal;
            }else if(i == 8){
                withoutTaxData.sepTotal = withTaxData.sepTotal - (withTaxData.sepTotal*incomeTaxPercentage)/100;
                allTotal += withoutTaxData.sepTotal;
            }else if(i == 9){
                withoutTaxData.octTotal = withTaxData.octTotal - (withTaxData.octTotal*incomeTaxPercentage)/100;
                allTotal += withoutTaxData.octTotal;
            }else if(i == 10){
                withoutTaxData.novTotal = withTaxData.novTotal - (withTaxData.novTotal*incomeTaxPercentage)/100;
                allTotal += withoutTaxData.novTotal;
            }else if(i == 11){
                withoutTaxData.decTotal = withTaxData.decTotal - (withTaxData.decTotal*incomeTaxPercentage)/100;
                allTotal += withoutTaxData.decTotal;
            }
        }

        withoutTaxData.allTotal = allTotal;

        return withoutTaxData;
    }

    def Map getTaxReservationAmount(incomeBudget,expenseBudget,incomeTaxPercentage) {
        Map taxReservationAmount = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                    "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal":0.00]

        for(int i=0;i<12;i++){
            if(i == 0){
                taxReservationAmount.janTotal = ((incomeBudget.janTotal - expenseBudget.janTotal) * incomeTaxPercentage)/100;
            }else if(i == 1){
                taxReservationAmount.febTotal = ((incomeBudget.febTotal - expenseBudget.febTotal) * incomeTaxPercentage)/100;
            }else if(i == 2){
                taxReservationAmount.marTotal = ((incomeBudget.marTotal - expenseBudget.marTotal) * incomeTaxPercentage)/100;
            }else if(i == 3){
                taxReservationAmount.aprTotal = ((incomeBudget.aprTotal - expenseBudget.aprTotal) * incomeTaxPercentage)/100;
            }else if(i == 4){
                taxReservationAmount.mayTotal = ((incomeBudget.mayTotal - expenseBudget.mayTotal) * incomeTaxPercentage)/100;
            }else if(i == 5){
                taxReservationAmount.junTotal = ((incomeBudget.junTotal - expenseBudget.junTotal) * incomeTaxPercentage)/100;
            }else if(i == 6){
                taxReservationAmount.julTotal = ((incomeBudget.julTotal - expenseBudget.julTotal) * incomeTaxPercentage)/100;
            }else if(i == 7){
                taxReservationAmount.augTotal = ((incomeBudget.augTotal - expenseBudget.augTotal) * incomeTaxPercentage)/100;
            }else if(i == 8){
                taxReservationAmount.sepTotal = ((incomeBudget.sepTotal - expenseBudget.sepTotal) * incomeTaxPercentage)/100;
            }else if(i == 9){
                taxReservationAmount.octTotal = ((incomeBudget.octTotal - expenseBudget.octTotal) * incomeTaxPercentage)/100;
            }else if(i == 10){
                taxReservationAmount.novTotal = ((incomeBudget.novTotal - expenseBudget.novTotal) * incomeTaxPercentage)/100;
            }else if(i == 11){
                taxReservationAmount.decTotal = ((incomeBudget.decTotal - expenseBudget.decTotal) * incomeTaxPercentage)/100;
            }
        }

        return taxReservationAmount;
    }

    def Map getTaxReservationAmountForReservationBud(incomeBudget,expenseBudget,reservationBudget,incomeTaxPercentage) {
        Map taxReservationAmount = ["janTotal": 0.00, "febTotal": 0.00, "marTotal": 0.00, "aprTotal": 0.00, "mayTotal": 0.00, "junTotal": 0.00,
                                    "julTotal": 0.00, "augTotal": 0.00, "sepTotal": 0.00, "octTotal": 0.00, "novTotal": 0.00,"decTotal":0.00]

        for(int i=0;i<12;i++){
            if(i == 0){
                taxReservationAmount.janTotal = ((incomeBudget.janTotal - expenseBudget.janTotal - reservationBudget.janTotal) * incomeTaxPercentage)/100;
            }else if(i == 1){
                taxReservationAmount.febTotal = ((incomeBudget.febTotal - expenseBudget.febTotal - reservationBudget.febTotal) * incomeTaxPercentage)/100;
            }else if(i == 2){
                taxReservationAmount.marTotal = ((incomeBudget.marTotal - expenseBudget.marTotal - reservationBudget.marTotal) * incomeTaxPercentage)/100;
            }else if(i == 3){
                taxReservationAmount.aprTotal = ((incomeBudget.aprTotal - expenseBudget.aprTotal - reservationBudget.aprTotal) * incomeTaxPercentage)/100;
            }else if(i == 4){
                taxReservationAmount.mayTotal = ((incomeBudget.mayTotal - expenseBudget.mayTotal - reservationBudget.mayTotal) * incomeTaxPercentage)/100;
            }else if(i == 5){
                taxReservationAmount.junTotal = ((incomeBudget.junTotal - expenseBudget.junTotal - reservationBudget.junTotal) * incomeTaxPercentage)/100;
            }else if(i == 6){
                taxReservationAmount.julTotal = ((incomeBudget.julTotal - expenseBudget.julTotal - reservationBudget.julTotal) * incomeTaxPercentage)/100;
            }else if(i == 7){
                taxReservationAmount.augTotal = ((incomeBudget.augTotal - expenseBudget.augTotal - reservationBudget.augTotal) * incomeTaxPercentage)/100;
            }else if(i == 8){
                taxReservationAmount.sepTotal = ((incomeBudget.sepTotal - expenseBudget.sepTotal - reservationBudget.sepTotal) * incomeTaxPercentage)/100;
            }else if(i == 9){
                taxReservationAmount.octTotal = ((incomeBudget.octTotal - expenseBudget.octTotal - reservationBudget.octTotal) * incomeTaxPercentage)/100;
            }else if(i == 10){
                taxReservationAmount.novTotal = ((incomeBudget.novTotal - expenseBudget.novTotal - reservationBudget.novTotal) * incomeTaxPercentage)/100;
            }else if(i == 11){
                taxReservationAmount.decTotal = ((incomeBudget.decTotal - expenseBudget.decTotal - reservationBudget.decTotal) * incomeTaxPercentage)/100;
            }
        }

        return taxReservationAmount;
    }


    def List<Map> getMonthwiseBudgetSummary(ArrayList budgetArr,ArrayList accountDataArr,String incomeOrExpense,String budgetOrInvoice){

        List monthlyIncomeBudgetArr = new ArrayList();

        for(int i=0;i<budgetArr.size();i++){
            Map monthlyIncomeBudgetMap = ["budgetName":'',"incomeOrExpense":incomeOrExpense,"budgetOrInvoice":budgetOrInvoice,
                                          "janAmount": 0,"febAmount": 0,"marAmount": 0,"aprAmount": 0,"mayAmount": 0,"junAmount": 0,
                                          "julAmount": 0,"augAmount": 0,"sepAmount": 0,"octAmount": 0,"novAmount": 0,"decAmount": 0];

            def customerId=budgetArr[i][0];
            def customerTempId
            if(accountDataArr.size()>0) {
                for (int z = 0; z < accountDataArr.size(); z++) {
                    for (int j = 0; j < accountDataArr[z].size(); j++) {
                        customerTempId = accountDataArr[z][j][0];

                        if (customerId == customerTempId) {

                            monthlyIncomeBudgetMap.budgetName = budgetArr[i][1];

                            if (accountDataArr[z][j][3] == 1) {
                                monthlyIncomeBudgetMap.janAmount += accountDataArr[z][j][4]
                            } else if (accountDataArr[z][j][3] == 2) {
                                monthlyIncomeBudgetMap.febAmount += accountDataArr[z][j][4]
                            } else if (accountDataArr[z][j][3] == 3) {
                                monthlyIncomeBudgetMap.marAmount += accountDataArr[z][j][4]
                            } else if (accountDataArr[z][j][3] == 4) {
                                monthlyIncomeBudgetMap.aprAmount += accountDataArr[z][j][4]
                            } else if (accountDataArr[z][j][3] == 5) {
                                monthlyIncomeBudgetMap.mayAmount += accountDataArr[z][j][4]
                            } else if (accountDataArr[z][j][3] == 6) {
                                monthlyIncomeBudgetMap.junAmount += accountDataArr[z][j][4]
                            } else if (accountDataArr[z][j][3] == 7) {
                                monthlyIncomeBudgetMap.julAmount += accountDataArr[z][j][4]
                            } else if (accountDataArr[z][j][3] == 8) {
                                monthlyIncomeBudgetMap.augAmount += accountDataArr[z][j][4]
                            } else if (accountDataArr[z][j][3] == 9) {
                                monthlyIncomeBudgetMap.sepAmount += accountDataArr[z][j][4]
                            } else if (accountDataArr[z][j][3] == 10) {
                                monthlyIncomeBudgetMap.octAmount += accountDataArr[z][j][4]
                            } else if (accountDataArr[z][j][3] == 11) {
                                monthlyIncomeBudgetMap.novAmount += accountDataArr[z][j][4]
                            } else if (accountDataArr[z][j][3] == 12) {
                                monthlyIncomeBudgetMap.decAmount += accountDataArr[z][j][4]
                            }
                        }
                    }
                    if (customerId == customerTempId) {
                        monthlyIncomeBudgetArr.add(monthlyIncomeBudgetMap);
                    }else{}
                }
            }
        }

        return monthlyIncomeBudgetArr;
    }

    def List<Map> getMonthwiseInvoiceSummary(ArrayList invoiceArr,ArrayList accountDataArr,String incomeOrExpense,String budgetOrInvoice) {
        List monthlyInvoiceArr = new ArrayList();
        //println(accountDataArr)

        for (int i = 0; i < invoiceArr.size(); i++) {
            Map monthlyInvoiceMap = ["budgetName": '', "incomeOrExpense": incomeOrExpense, "budgetOrInvoice": budgetOrInvoice, "janAmount": 0, "febAmount": 0, "marAmount": 0, "aprAmount": 0, "mayAmount": 0, "junAmount": 0,
                                     "julAmount" : 0, "augAmount": 0, "sepAmount": 0, "octAmount": 0, "novAmount": 0, "decAmount": 0];

            def vendorId=invoiceArr[i][0];
            def vendorTempId
//            println(accountDataArr);

            if(accountDataArr.size()>0) {
                for (int z = 0; z < accountDataArr.size(); z++) {
                    for (int j = 0; j < accountDataArr[z].size(); j++) {
                        vendorTempId = accountDataArr[z][j][0];

                        if (vendorId.toString() == vendorTempId.toString()) {

                            monthlyInvoiceMap.budgetName = invoiceArr[i][1];
                            if (Integer.parseInt(accountDataArr[z][j][3]) == 1) {
                                monthlyInvoiceMap.janAmount += accountDataArr[z][j][4]
                            } else if (Integer.parseInt(accountDataArr[z][j][3]) == 2) {
                                monthlyInvoiceMap.febAmount += accountDataArr[z][j][4]
                            } else if (Integer.parseInt(accountDataArr[z][j][3]) == 3) {
                                monthlyInvoiceMap.marAmount += accountDataArr[z][j][4]
                            } else if (Integer.parseInt(accountDataArr[z][j][3]) == 4) {
                                monthlyInvoiceMap.aprAmount += accountDataArr[z][j][4]
                            } else if (Integer.parseInt(accountDataArr[z][j][3]) == 5) {
                                monthlyInvoiceMap.mayAmount += accountDataArr[z][j][4]
                            } else if (Integer.parseInt(accountDataArr[z][j][3]) == 6) {
                                monthlyInvoiceMap.junAmount += accountDataArr[z][j][4]
                            } else if (Integer.parseInt(accountDataArr[z][j][3]) == 7) {
                                monthlyInvoiceMap.julAmount += accountDataArr[z][j][4]
                            } else if (Integer.parseInt(accountDataArr[z][j][3]) == 8) {
                                monthlyInvoiceMap.augAmount += accountDataArr[z][j][4]
                            } else if (Integer.parseInt(accountDataArr[z][j][3]) == 9) {
                                monthlyInvoiceMap.sepAmount += accountDataArr[z][j][4]
                            } else if (Integer.parseInt(accountDataArr[z][j][3]) == 10) {
                                monthlyInvoiceMap.octAmount += accountDataArr[z][j][4]
                            } else if (Integer.parseInt(accountDataArr[z][j][3]) == 11) {
                                monthlyInvoiceMap.novAmount += accountDataArr[z][j][4]
                            } else if (Integer.parseInt(accountDataArr[z][j][3]) == 12) {
                                monthlyInvoiceMap.decAmount += accountDataArr[z][j][4]
                            }
                        }
                    }
                    if(vendorId.toString() == vendorTempId.toString()){
                        monthlyInvoiceArr.add(monthlyInvoiceMap);
                    }else{}

                }
            }



        }

        return monthlyInvoiceArr;
    }


    def Map getProfitSummaryForIncNExp(def fiscalYearInfo, def incomeTaxPercentage,def taxReservType) {

        def grossProfitBudget
        def netProfitBudget
        def grossProfitBooking
        def netProfitBooking
        def grossProfitForecast
        def netProfitForecast

        //Invoice wise
        Map totalInvoiceIncome = getInvoiceIncomeTotalMonthWise(fiscalYearInfo);
        Map totalInvoiceExpense = getInvoiceExpenseTotalMonthWise(fiscalYearInfo);

        //Budget wise.
        Map totalIncomeBudget = getIncomeBudgetTotalMonthWise(fiscalYearInfo);
        Map totalExpenseBudget = getExpenseBudgetTotalMonthWise(fiscalYearInfo);

        //With Tax Reservation
        Map totalInvoiceIncomeWithTax = getAmountWithoutTax(totalInvoiceIncome,incomeTaxPercentage);
        Map totalInvoiceExpenseWithTax = getAmountWithoutTax(totalInvoiceExpense,incomeTaxPercentage);

        Map totalIncomeBudgetWithTax = getAmountWithoutTax(totalIncomeBudget,incomeTaxPercentage);
        Map totalExpenseBudgetWithTax = getAmountWithoutTax(totalExpenseBudget,incomeTaxPercentage);

        //Only Tax reservation amount.
        Map taxReservationAmount = getTaxReservationAmount(totalIncomeBudgetWithTax,totalExpenseBudgetWithTax,incomeTaxPercentage);

        //Profit budget
        //With tax reservation means that part of the profit is reserved for tax so the displayed amount for profit is lower.
        if(taxReservType == "taxWithReservation"){
            grossProfitBudget = getGrossProfitBasedOnBudget(totalIncomeBudget,totalExpenseBudget);
        }
        else if(taxReservType == "taxWithoutReservation"){

            grossProfitBudget = getGrossProfitBasedOnBudget(totalIncomeBudget,totalExpenseBudget);

            //Net profit = Without Tax Reservation amount.
            netProfitBudget = getNetProfitBasedOnBudget(grossProfitBudget,incomeTaxPercentage);
        }

        //Profit booking
        if(taxReservType == "taxWithReservation"){
            //Gross profit = With Tax Reservation amount.
            grossProfitBooking = getGrossProfitBasedOnBooking(totalInvoiceIncome,totalInvoiceExpense);
        }
        else if(taxReservType == "taxWithoutReservation"){
            //Gross profit = With Tax Reservation amount.
            grossProfitBooking = getGrossProfitBasedOnBooking(totalInvoiceIncome,totalInvoiceExpense);

            //Net profit = Without Tax Reservation amount.
            netProfitBooking = getNetProfitBasedOnBooking(grossProfitBooking,incomeTaxPercentage);
        }

        //Profit Forecast
        //Forecast Gross profit = With Tax Reservation amount.
        def incomeBudgetForecastData = getBudgetIncomeForecastData(fiscalYearInfo);
        def incomeInvoiceForecastData = getInvoiceIncomeForecastData(fiscalYearInfo);

        def incomeForcast = prepareCurrentForecastForIncomeNameWise(incomeBudgetForecastData,incomeInvoiceForecastData)

        def expenseBudgetForecastData = getBudgetExpenseForecastData(fiscalYearInfo);
        def expenseInvoiceForecastData = getInvoiceExpenseForecastData(fiscalYearInfo);

        def expenseForcast = prepareCurrentForecastForExpenseNameWise(expenseBudgetForecastData,expenseInvoiceForecastData)
        //println("incomeForcast" + incomeForcast + " expenseForcast "+expenseForcast)
        grossProfitForecast = getForecastOfGrossProfit(incomeForcast,expenseForcast);

        if(taxReservType == "taxWithoutReservation") {
            netProfitForecast = getForecastOfNetProfit(grossProfitForecast,incomeTaxPercentage);
        }

        Map summaryInfo = ["totalIncomeBudget"            : totalIncomeBudget,
                           "totalExpenseBudget"           : totalExpenseBudget,
                           "totalInvoiceIncome"           : totalInvoiceIncome,
                           "totalInvoiceExpense"          : totalInvoiceExpense,
                           "totalIncomeBudgetWithTax"     : totalIncomeBudgetWithTax,
                           "totalExpenseBudgetWithTax"    : totalExpenseBudgetWithTax,
                           "totalInvoiceIncomeWithTax"    : totalInvoiceIncomeWithTax,
                           "totalInvoiceExpenseWithTax"   : totalInvoiceExpenseWithTax,
                           "taxReservationAmount"         : taxReservationAmount,
                           "grossProfitBudget"            : grossProfitBudget,
                           "netProfitBudget"              : netProfitBudget,
                           "grossProfitBooking"           : grossProfitBooking,
                           "netProfitBooking"             : netProfitBooking,
                           "grossProfitForecast"          : grossProfitForecast,
                           "netProfitForecast"            : netProfitForecast,
                           "incomeForcast"                : incomeForcast,
                           "expenseForcast"               : expenseForcast
        ];

        return summaryInfo
    }


//    for reservation Budget

    def Map getProfitSummaryForReservation(def fiscalYearInfo, def incomeTaxPercentage,def taxReservType) {

        def grossProfitBudget
        def netProfitBudget
        def grossProfitBooking
        def netProfitBooking
        def grossProfitForecast
        def netProfitForecast

        //Invoice wise
        Map totalInvoiceIncome = getInvoiceIncomeTotalMonthWise(fiscalYearInfo);
        Map totalInvoiceExpense = getInvoiceExpenseTotalMonthWise(fiscalYearInfo);
        Map totalInvoiceReservation = getInvoiceReservationTotalMonthWise(fiscalYearInfo);

        //Budget wise.
        Map totalIncomeBudget = getIncomeBudgetTotalMonthWise(fiscalYearInfo);
        Map totalExpenseBudget = getExpenseBudgetTotalMonthWise(fiscalYearInfo);
        Map totalReservationBudget = getReservationBudgetTotalMonthWise(fiscalYearInfo);

        //With Tax Reservation
        Map totalInvoiceIncomeWithTax = getAmountWithoutTax(totalInvoiceIncome,incomeTaxPercentage);
        Map totalInvoiceExpenseWithTax = getAmountWithoutTax(totalInvoiceExpense,incomeTaxPercentage);
        Map totalInvoiceReservationWithTax = getAmountWithoutTax(totalInvoiceReservation,incomeTaxPercentage);

        Map totalIncomeBudgetWithTax = getAmountWithoutTax(totalIncomeBudget,incomeTaxPercentage);
        Map totalExpenseBudgetWithTax = getAmountWithoutTax(totalExpenseBudget,incomeTaxPercentage);
        Map totalReservationBudgetWithTax = getAmountWithoutTax(totalReservationBudget,incomeTaxPercentage);

        //Only Tax reservation amount.
        Map taxReservationAmount = getTaxReservationAmountForReservationBud(totalIncomeBudgetWithTax,totalExpenseBudgetWithTax,totalReservationBudgetWithTax,incomeTaxPercentage);

        //Profit budget
        //With tax reservation means that part of the profit is reserved for tax so the displayed amount for profit is lower.
        if(taxReservType == "taxWithReservation"){
            grossProfitBudget = getGrossProfitBasedOnBudgetForReservation(totalIncomeBudget,totalExpenseBudget,totalReservationBudget);
        }
        /*else if(taxReservType == "taxWithoutReservation"){

            grossProfitBudget = getGrossProfitBasedOnBudget(totalIncomeBudget,totalExpenseBudget);

            //Net profit = Without Tax Reservation amount.
            netProfitBudget = getNetProfitBasedOnBudget(grossProfitBudget,incomeTaxPercentage);
        }*/

        //Profit booking
        if(taxReservType == "taxWithReservation"){
            //Gross profit = With Tax Reservation amount.
            grossProfitBooking = getGrossProfitBasedOnBookingForReservation(totalInvoiceIncome,totalInvoiceExpense,totalInvoiceReservation);
        }
        /*else if(taxReservType == "taxWithoutReservation"){
            //Gross profit = With Tax Reservation amount.
            grossProfitBooking = getGrossProfitBasedOnBooking(totalInvoiceIncome,totalInvoiceExpense);

            //Net profit = Without Tax Reservation amount.
            netProfitBooking = getNetProfitBasedOnBooking(grossProfitBooking,incomeTaxPercentage);
        }*/

        //Profit Forecast
        //Forecast Gross profit = With Tax Reservation amount.
        def incomeBudgetForecastData = getBudgetIncomeForecastData(fiscalYearInfo);
        def incomeInvoiceForecastData = getInvoiceIncomeForecastData(fiscalYearInfo);
        def incomeForcast = prepareCurrentForecastForIncomeNameWise(incomeBudgetForecastData,incomeInvoiceForecastData)

        def expenseBudgetForecastData = getBudgetExpenseForecastData(fiscalYearInfo);
        def expenseInvoiceForecastData = getInvoiceExpenseForecastData(fiscalYearInfo);
        def expenseForcast = prepareCurrentForecastForExpenseNameWise(expenseBudgetForecastData,expenseInvoiceForecastData)

        def reservationBudgetForecastData = getBudgetReservationForecastData(fiscalYearInfo);
        def reservationInvoiceForecastData = getInvoiceReservationForecastData(fiscalYearInfo);
        def reservationForcast = prepareCurrentForecastForReservationNameWise(reservationBudgetForecastData,reservationInvoiceForecastData)


        //println("incomeForcast" + incomeForcast + " expenseForcast "+expenseForcast)
        grossProfitForecast = getForecastOfGrossProfitForReservation(incomeForcast,expenseForcast, reservationForcast);

        /* if(taxReservType == "taxWithoutReservation") {
             netProfitForecast = getForecastOfNetProfit(grossProfitForecast,incomeTaxPercentage);
         }*/

        Map summaryInfo = ["totalIncomeBudget"            : totalIncomeBudget,
                           "totalExpenseBudget"           : totalExpenseBudget,
                           "totalReservationBudget"           : totalReservationBudget,
                           "totalInvoiceIncome"           : totalInvoiceIncome,
                           "totalInvoiceExpense"          : totalInvoiceExpense,
                           "totalInvoiceReservation"          : totalInvoiceReservation,
                           "totalIncomeBudgetWithTax"     : totalIncomeBudgetWithTax,
                           "totalReservationBudgetWithTax"    : totalReservationBudgetWithTax,
                           "totalInvoiceIncomeWithTax"    : totalInvoiceIncomeWithTax,
                           "totalInvoiceExpenseWithTax"   : totalInvoiceExpenseWithTax,
                           "totalInvoiceReservationWithTax"   : totalInvoiceReservationWithTax,
                           "taxReservationAmount"         : taxReservationAmount,
                           "grossProfitBudget"            : grossProfitBudget,
                           "netProfitBudget"              : netProfitBudget,
                           "grossProfitBooking"           : grossProfitBooking,
                           "netProfitBooking"             : netProfitBooking,
                           "grossProfitForecast"          : grossProfitForecast,
                           "netProfitForecast"            : netProfitForecast
        ];

        return summaryInfo
    }

    def Map getProfitSummaryForPrivate(def fiscalYearInfo, def incomeTaxPercentage,def taxReservType) {

        def grossProfitBudget
        def netProfitBudget
        def grossProfitBooking
        def netProfitBooking
        def grossProfitForecast
        def netProfitForecast

        //Invoice wise Without Tax
        Map totalInvoiceIncome = getInvoiceIncomeTotalForPrivateMonthWise(fiscalYearInfo);
        Map totalInvoiceExpense = getInvoiceExpenseTotalForPrivateMonthWise(fiscalYearInfo);

        //Budget wise Without Tax
        Map totalIncomeBudget = getPrivateBudgetTotalIncomeMonthWise(fiscalYearInfo);
        Map totalExpenseBudget = getPrivateBudgetTotalExpenseMonthWise(fiscalYearInfo);

//        Map totalInvoiceIncomeWithoutTax = getAmountWithoutTax(totalInvoiceIncome,incomeTaxPercentage);
//        Map totalInvoiceExpenseWithoutTax = getAmountWithoutTax(totalInvoiceExpense,incomeTaxPercentage);
//
//        Map totalIncomeBudgetWithoutTax = getAmountWithoutTax(totalIncomeBudget,incomeTaxPercentage);
//        Map totalExpenseBudgetWithoutTax = getAmountWithoutTax(totalExpenseBudget,incomeTaxPercentage);

        //Only Tax reservation amount.
//        Map taxReservationAmount = getTaxReservationAmount(totalIncomeBudgetWithoutTax,totalExpenseBudgetWithoutTax,incomeTaxPercentage);

        //Profit budget
        //With tax reservation means that part of the profit is reserved for tax so the displayed amount for profit is lower.
        if(taxReservType == "taxWithReservation"){
            grossProfitBudget = getGrossProfitBasedOnBudget(totalIncomeBudget,totalExpenseBudget);
        }

        //Profit booking
        if(taxReservType == "taxWithReservation"){
            //Gross profit = With Tax Reservation amount.
            grossProfitBooking = getGrossProfitBasedOnBooking(totalInvoiceIncome,totalInvoiceExpense);
        }

        //Profit Forecast
        def incomeBudgetForecastData = getBudgetIncomeForecastDataForPrivate(fiscalYearInfo);
        def incomeInvoiceForecastData = getInvoiceIncomeForecastDataForPrivate(fiscalYearInfo);

        def incomeForcast = prepareCurrentForecastForPrivateIncome(incomeBudgetForecastData,incomeInvoiceForecastData)

        def expenseBudgetForecastData = getBudgetExpenseForecastDataForPrivate(fiscalYearInfo);
        def expenseInvoiceForecastData = getInvoiceExpenseForecastDataForPrivate(fiscalYearInfo);

        def expenseForcast = prepareCurrentForecastForPrivateExpense(expenseBudgetForecastData,expenseInvoiceForecastData)

        grossProfitForecast = getForecastOfGrossProfit(incomeForcast,expenseForcast);

//        if(taxReservType == "taxWithoutReservation") {
//            //Forecast Net profit = Without Tax Reservation amount.
//            netProfitForecast = getForecastOfNetProfit(grossProfitForecast,incomeTaxPercentage);
//        }

        Map summaryInfo = ["totalIncomeBudget"            : totalIncomeBudget,
                           "totalExpenseBudget"           : totalExpenseBudget,
                           "totalInvoiceIncome"           : totalInvoiceIncome,
                           "totalInvoiceExpense"          : totalInvoiceExpense,
//                           "totalIncomeBudgetWithoutTax"  : totalIncomeBudgetWithoutTax,
//                           "totalExpenseBudgetWithoutTax" : totalExpenseBudgetWithTax,
//                           "totalInvoiceIncomeWithTax" : totalInvoiceIncomeWithTax,
//                           "totalInvoiceExpenseWithoutTax": totalInvoiceExpenseWithoutTax,
//                           "taxReservationAmount"         : taxReservationAmount,
                           "grossProfitBudget"            : grossProfitBudget,
//                           "netProfitBudget"              : netProfitBudget,
                           "grossProfitBooking"           : grossProfitBooking,
//                           "netProfitBooking"             : netProfitBooking,
                           "grossProfitForecast"          : grossProfitForecast,
                           "incomeForcast"                : incomeForcast,
                           "expenseForcast"               : expenseForcast
//                           "netProfitForecast"            : netProfitForecast
        ];

        return summaryInfo
    }
}
