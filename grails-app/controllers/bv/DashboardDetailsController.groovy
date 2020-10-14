package bv

class DashboardDetailsController {

    DashboardDetailsService dashboardDetailsService

    def index() {
        redirect(action: "list", params: params)
    }

    def updateIncomeGraphArea(){

        def tabIndex = params.curTabInd;

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        //Getting budget customer data.
        ArrayList budgetCustomerArr = dashboardDetailsService.getBudgetCustomerData(fiscalYearInfo);
//        println("budgetCustomerArr: "+budgetCustomerArr)
        //Getting customer account data.
        ArrayList customerAccountData = dashboardDetailsService.getCustomerAccountData(fiscalYearInfo,budgetCustomerArr);
//        println("customerAccountData: "+customerAccountData)
        //Getting income invoice data.
        ArrayList invoiceIncomeDetails = dashboardDetailsService.getIncomeInvoiceData(fiscalYearInfo,budgetCustomerArr);

        render(layout: "ajax",template: "incomeGraphDetails",model: [tabInd                        : tabIndex,
                                                                     budgetCustomerArr             : budgetCustomerArr,
                                                                     customerAccountInstance       : customerAccountData,
                                                                     customerInvoiceAccountInstance: invoiceIncomeDetails]);
    }

    def updateExpenseGraphArea(){

        def tabIndex = params.curTabInd;

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        //Getting budget vendor data.
        ArrayList budgetVendorArr = dashboardDetailsService.getBudgetVendorData(fiscalYearInfo);

        //Getting vendor account data.
        ArrayList vendorAccountData = dashboardDetailsService.getVendorAccountData(fiscalYearInfo,budgetVendorArr);

        //Getting expense invoice data.
        ArrayList expenseInvoiceData = dashboardDetailsService.getExpenseInvoiceData(fiscalYearInfo,budgetVendorArr);


        render(layout: "ajax",template: "expenseGraphDetails",model: [tabInd                      : tabIndex,
                                                                      budgetVendorArr             : budgetVendorArr,
                                                                      vendorAccountInstance       : vendorAccountData,
                                                                      vendorInvoiceAccountInstance: expenseInvoiceData]);
    }

    def updatePrivateDWCGraph() {
        def tabIndex = params.curTabInd;
        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        //Getting process month wise amount data.
        Map processDataDetails = dashboardDetailsService.getProcessDataForPrivateWDC(fiscalYearInfo);
//        def masterId = new BudgetViewDatabaseService().executeQuery("""SELECT * from private_budget_master WHERE id = '${1}'""")

        render(layout: "ajax",template: "privateDWForCompanyGraphDetails",model: [tabInd : tabIndex,
                                                                                  processDataDetailsInstance  : processDataDetails,]);
    }

    def updatePrivateGraphArea() {

        def tabIndex = params.curTabInd;

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        //Getting private budget name wise data.
        ArrayList privateBudgetArr = dashboardDetailsService.getBudgetPrivateData(fiscalYearInfo);

        //Getting private budget month wise amount data.
        ArrayList privateBudAmountData = dashboardDetailsService.getPrivateBudgetAmountData(fiscalYearInfo,privateBudgetArr);

        //Getting process month wise amount data.
        ArrayList invoiceIncomePrivateDetails = dashboardDetailsService.getProcessDataForPrivate(fiscalYearInfo,privateBudgetArr);

        render(layout: "ajax",template: "privateGraphDetails",model: [tabInd                        : tabIndex,
                                                                      privateBudgetArr              : privateBudgetArr,
                                                                      privateBudAmountInstance      : privateBudAmountData,
                                                                      invoiceIncomePrivateInstance  : invoiceIncomePrivateDetails]);
    }

    def updateReservationGraphArea(){

        def tabIndex = params.curTabInd;

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        //Getting budget vendor data.
        ArrayList budgetReservationArr = dashboardDetailsService.getBudgetReservationData(fiscalYearInfo);

        //Getting vendor account data.
        ArrayList reservationAccountData = dashboardDetailsService.getReservationAccountData(fiscalYearInfo,budgetReservationArr);

        //Getting expense invoice data.
        ArrayList InvoiceData = dashboardDetailsService.getReservationData(fiscalYearInfo,budgetReservationArr);

        render(layout: "ajax",template: "reservationGraphDetails",model: [  tabInd:tabIndex,budgetReservationArr:budgetReservationArr,
                                                                            reservationAccountInstance:reservationAccountData,
                                                                            reservationInvoiceAccountInstance:InvoiceData]);
    }

    def loadExpenseGraphAccountWise(){

        def tabIndex = params.curTabInd;

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        //Getting budget vendor data.
        ArrayList vendorAHWArr = dashboardDetailsService.getVendorDataAHWise(fiscalYearInfo);

        //Getting vendor account data.
        ArrayList vendorAccountData = dashboardDetailsService.getVendorBudgetAHWiseData(fiscalYearInfo,vendorAHWArr);

        //Getting expense invoice data.
        ArrayList expenseInvoiceData = dashboardDetailsService.getExpenseInvoiceDataAHWise(fiscalYearInfo,vendorAHWArr);

        render(layout: "ajax",template: "expenseAccountGraphDetails",model: [tabInd:tabIndex,vendorAHWArr:vendorAHWArr,
                                                                             vendorAccountInstance:vendorAccountData,
                                                                             vendorInvoiceAccountInstance:expenseInvoiceData]);

    }


    def loadReservationGraphAccountWise(){

        def tabIndex = params.curTabInd;

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        //Getting budget vendor data.
        ArrayList reservationAHWArr = dashboardDetailsService.getResevationDataAHWise(fiscalYearInfo);

        //Getting vendor account data.
        ArrayList reservationAccountData = dashboardDetailsService.getReservationBudgetAHWiseData(fiscalYearInfo,reservationAHWArr);

        //Getting expense invoice data.
        ArrayList InvoiceData = dashboardDetailsService.getReservationInvoiceDataAHWise(fiscalYearInfo,reservationAHWArr);

        render(layout: "ajax",template: "reservationAccountGraphDetails",model: [tabInd:tabIndex,reservationAHWArr:reservationAHWArr,
                                                                                 reservationAccountInstance:reservationAccountData,
                                                                                 reservationInvoiceAccountInstance:InvoiceData]);

    }

    def incomeCustomerWise() {

        def activeFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(activeFiscalYear)

        //Getting budget customer data.
        ArrayList budgetCustomerArr = dashboardDetailsService.getBudgetCustomerData(fiscalYearInfo);
        dashboardDetailsService.setTotalIncomeInvoiceAmount(fiscalYearInfo,budgetCustomerArr);

        //Total Budget.
        Map totalIncomeBudget = dashboardDetailsService.getIncomeBudgetTotalMonthWise(fiscalYearInfo);
        Map totalExpenseBudget = dashboardDetailsService.getExpenseBudgetTotalMonthWise(fiscalYearInfo);

        //Total Invoice.
        Map totalInvoiceIncome = dashboardDetailsService.getInvoiceIncomeTotalMonthWise(fiscalYearInfo);
        Map totalInvoiceExpense = dashboardDetailsService.getInvoiceExpenseTotalMonthWise(fiscalYearInfo);

        //Private Budget
        //Total Budget.
        Map totalPvtIncomeBudget = dashboardDetailsService.getPrivateBudgetTotalIncomeMonthWise(fiscalYearInfo);
        Map totalPvtExpenseBudget = dashboardDetailsService.getPrivateBudgetTotalExpenseMonthWise(fiscalYearInfo);

        //Invoice wise
        def totalPvtInvoiceIncome = dashboardDetailsService.getTotalIncomeInvoiceAmountForPrivateBudget(fiscalYearInfo);
        def totalPvtInvoiceExpense = dashboardDetailsService.getTotalExpenseInvoiceAmountForPrivateBudget(fiscalYearInfo);

        //Reservation Budget
        //Total Invoice
        def totalResvInvoiceAmount = dashboardDetailsService.getTotalInvoiceAmountForReservationBudget(fiscalYearInfo);
        //Total Budget.
        Map totalReservationBudget = dashboardDetailsService.getReservationBudgetTotalMonthWise(fiscalYearInfo);
        def abd = 0.0;

        //All Models
        [dashboardDetailsInstance: new DashboardDetails(params), budgetCustomerArr: budgetCustomerArr,

         totalIncomeBudgetAmount:totalIncomeBudget.allTotal,totalExpenseBudgetAmount:totalExpenseBudget.allTotal,
         totalIncomeInvoiceAmount:totalInvoiceIncome.allTotal,totalExpenseInvoiceAmount:totalInvoiceExpense.allTotal,

         totalPvtIncomeBudgetAmount:totalPvtIncomeBudget.allTotal,totalPvtExpenseBudgetAmount:totalPvtExpenseBudget.allTotal,
         totalPvtInvoiceIncomeAmount:totalPvtInvoiceIncome,totalPvtInvoiceExpenseAmount:totalPvtInvoiceExpense,

         totalReservBudgetAmount:totalReservationBudget.allTotal,totalReservInvoiceAmount:totalResvInvoiceAmount]
    }

    def incomeAccountWise() {
        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        //Getting budget vendor data.
        ArrayList customerAHWArr = dashboardDetailsService.getCustomerDataAHWise(fiscalYearInfo);
        dashboardDetailsService.setTotalIncomeInvoiceAmountAHWise(fiscalYearInfo,customerAHWArr);

        //[dashboardDetailsInstance: new DashboardDetails(params), customerAHWArr: customerAHWArr]
        //Total Budget.
        Map totalIncomeBudget = dashboardDetailsService.getIncomeBudgetTotalMonthWise(fiscalYearInfo);
        Map totalExpenseBudget = dashboardDetailsService.getExpenseBudgetTotalMonthWise(fiscalYearInfo);

        //Total Invoice.
        Map totalInvoiceIncome = dashboardDetailsService.getInvoiceIncomeTotalMonthWise(fiscalYearInfo);
        Map totalInvoiceExpense = dashboardDetailsService.getInvoiceExpenseTotalMonthWise(fiscalYearInfo);

        //Private Budget
        //Total Budget.
        Map totalPvtIncomeBudget = dashboardDetailsService.getPrivateBudgetTotalIncomeMonthWise(fiscalYearInfo);
        Map totalPvtExpenseBudget = dashboardDetailsService.getPrivateBudgetTotalExpenseMonthWise(fiscalYearInfo);

        //Invoice wise
        def totalPvtInvoiceIncome = dashboardDetailsService.getTotalIncomeInvoiceAmountForPrivateBudget(fiscalYearInfo);
        def totalPvtInvoiceExpense = dashboardDetailsService.getTotalExpenseInvoiceAmountForPrivateBudget(fiscalYearInfo);

        //Reservation Budget
        //Total Invoice
        def totalResvInvoiceAmount = dashboardDetailsService.getTotalInvoiceAmountForReservationBudget(fiscalYearInfo);
        //Total Budget.
        Map totalReservationBudget = dashboardDetailsService.getReservationBudgetTotalMonthWise(fiscalYearInfo);
        def abd = 0.0;

        //All Models
        [dashboardDetailsInstance: new DashboardDetails(params), customerAHWArr: customerAHWArr,

         totalIncomeBudgetAmount:totalIncomeBudget.allTotal,totalExpenseBudgetAmount:totalExpenseBudget.allTotal,
         totalIncomeInvoiceAmount:totalInvoiceIncome.allTotal,totalExpenseInvoiceAmount:totalInvoiceExpense.allTotal,

         totalPvtIncomeBudgetAmount:totalPvtIncomeBudget.allTotal,totalPvtExpenseBudgetAmount:totalPvtExpenseBudget.allTotal,
         totalPvtInvoiceIncomeAmount:totalPvtInvoiceIncome,totalPvtInvoiceExpenseAmount:totalPvtInvoiceExpense,

         totalReservBudgetAmount:totalReservationBudget.allTotal,totalReservInvoiceAmount:totalResvInvoiceAmount]
    }

    def expanseVendorWise() {

        def activeFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(activeFiscalYear)

        //Getting budget vendor data.
        ArrayList budgetVendorArr = dashboardDetailsService.getBudgetVendorData(fiscalYearInfo);
        dashboardDetailsService.setTotalExpenseInvoiceAmount(fiscalYearInfo,budgetVendorArr);

        //Income & Expense budget
        //Total Budget.
        Map totalIncomeBudget = dashboardDetailsService.getIncomeBudgetTotalMonthWise(fiscalYearInfo);
        Map totalExpenseBudget = dashboardDetailsService.getExpenseBudgetTotalMonthWise(fiscalYearInfo);

        //Total Invoice.
        Map totalInvoiceIncome = dashboardDetailsService.getInvoiceIncomeTotalMonthWise(fiscalYearInfo);
        Map totalInvoiceExpense = dashboardDetailsService.getInvoiceExpenseTotalMonthWise(fiscalYearInfo);

        //Private Budget
        //Invoice wise
        def totalPvtInvoiceIncome = dashboardDetailsService.getTotalIncomeInvoiceAmountForPrivateBudget(fiscalYearInfo);
        def totalPvtInvoiceExpense = dashboardDetailsService.getTotalExpenseInvoiceAmountForPrivateBudget(fiscalYearInfo);

        //Total Budget.
        Map totalPvtIncomeBudget = dashboardDetailsService.getPrivateBudgetTotalIncomeMonthWise(fiscalYearInfo);
        Map totalPvtExpenseBudget = dashboardDetailsService.getPrivateBudgetTotalExpenseMonthWise(fiscalYearInfo);

        //Reservation Budget
        //Total Budget.
        Map totalReservationBudget = dashboardDetailsService.getReservationBudgetTotalMonthWise(fiscalYearInfo);
        //Total Invoice
        def totalResvInvoiceAmount = dashboardDetailsService.getTotalInvoiceAmountForReservationBudget(fiscalYearInfo);

        //All Models
        [dashboardDetailsInstance: new DashboardDetails(params), budgetVendorArr: budgetVendorArr,

         totalIncomeBudgetAmount:totalIncomeBudget.allTotal,totalExpenseBudgetAmount:totalExpenseBudget.allTotal,
         totalIncomeInvoiceAmount:totalInvoiceIncome.allTotal,totalExpenseInvoiceAmount:totalInvoiceExpense.allTotal,

         totalPvtIncomeBudgetAmount:totalPvtIncomeBudget.allTotal,totalPvtExpenseBudgetAmount:totalPvtExpenseBudget.allTotal,
         totalPvtInvoiceIncomeAmount:totalPvtInvoiceIncome,totalPvtInvoiceExpenseAmount:totalPvtInvoiceExpense,

         totalReservBudgetAmount:totalReservationBudget.allTotal,totalReservInvoiceAmount:totalResvInvoiceAmount]
    }

    def expanseAccountWise() {

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        //Getting budget vendor data.
        ArrayList vendorAHWArr = dashboardDetailsService.getVendorDataAHWise(fiscalYearInfo);
        dashboardDetailsService.setTotalExpenseInvoiceAmountAHWise(fiscalYearInfo,vendorAHWArr);

        //Income & Expense budget
        //Total Budget.
        Map totalIncomeBudget = dashboardDetailsService.getIncomeBudgetTotalMonthWise(fiscalYearInfo);
        Map totalExpenseBudget = dashboardDetailsService.getExpenseBudgetTotalMonthWise(fiscalYearInfo);

        //Total Invoice.
        Map totalInvoiceIncome = dashboardDetailsService.getInvoiceIncomeTotalMonthWise(fiscalYearInfo);
        Map totalInvoiceExpense = dashboardDetailsService.getInvoiceExpenseTotalMonthWise(fiscalYearInfo);

        //Private Budget
        //Invoice wise
        def totalPvtInvoiceIncome = dashboardDetailsService.getTotalIncomeInvoiceAmountForPrivateBudget(fiscalYearInfo);
        def totalPvtInvoiceExpense = dashboardDetailsService.getTotalExpenseInvoiceAmountForPrivateBudget(fiscalYearInfo);

        //Total Budget.
        Map totalPvtIncomeBudget = dashboardDetailsService.getPrivateBudgetTotalIncomeMonthWise(fiscalYearInfo);
        Map totalPvtExpenseBudget = dashboardDetailsService.getPrivateBudgetTotalExpenseMonthWise(fiscalYearInfo);

        //Reservation Budget
        //Total Budget.
        Map totalReservationBudget = dashboardDetailsService.getReservationBudgetTotalMonthWise(fiscalYearInfo);
        //Total Invoice
        def totalResvInvoiceAmount = dashboardDetailsService.getTotalInvoiceAmountForReservationBudget(fiscalYearInfo);

        //All Models
        [dashboardDetailsInstance: new DashboardDetails(params), vendorAHWArr: vendorAHWArr,

         totalIncomeBudgetAmount:totalIncomeBudget.allTotal,totalExpenseBudgetAmount:totalExpenseBudget.allTotal,
         totalIncomeInvoiceAmount:totalInvoiceIncome.allTotal,totalExpenseInvoiceAmount:totalInvoiceExpense.allTotal,

         totalPvtIncomeBudgetAmount:totalPvtIncomeBudget.allTotal,totalPvtExpenseBudgetAmount:totalPvtExpenseBudget.allTotal,
         totalPvtInvoiceIncomeAmount:totalPvtInvoiceIncome,totalPvtInvoiceExpenseAmount:totalPvtInvoiceExpense,

         totalReservBudgetAmount:totalReservationBudget.allTotal,totalReservInvoiceAmount:totalResvInvoiceAmount]
    }

    def reservationNameWise(){
        ///////////ALL VENDOR///////////////////
        def activeFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(activeFiscalYear)

        //Getting budget vendor data.
        ArrayList budgetResevationArr = dashboardDetailsService.getBudgetReservationData(fiscalYearInfo);
        dashboardDetailsService.setTotalReservationAmount(fiscalYearInfo,budgetResevationArr);

        //[dashboardDetailsInstance: new DashboardDetails(params), budgetResevationArr: budgetResevationArr]
        //Income & Expense budget
        //Total Budget.
        Map totalIncomeBudget = dashboardDetailsService.getIncomeBudgetTotalMonthWise(fiscalYearInfo);
        Map totalExpenseBudget = dashboardDetailsService.getExpenseBudgetTotalMonthWise(fiscalYearInfo);

        //Total Invoice.
        Map totalInvoiceIncome = dashboardDetailsService.getInvoiceIncomeTotalMonthWise(fiscalYearInfo);
        Map totalInvoiceExpense = dashboardDetailsService.getInvoiceExpenseTotalMonthWise(fiscalYearInfo);

        //Private Budget
        //Invoice wise
        def totalPvtInvoiceIncome = dashboardDetailsService.getTotalIncomeInvoiceAmountForPrivateBudget(fiscalYearInfo);
        def totalPvtInvoiceExpense = dashboardDetailsService.getTotalExpenseInvoiceAmountForPrivateBudget(fiscalYearInfo);

        //Total Budget.
        Map totalPvtIncomeBudget = dashboardDetailsService.getPrivateBudgetTotalIncomeMonthWise(fiscalYearInfo);
        Map totalPvtExpenseBudget = dashboardDetailsService.getPrivateBudgetTotalExpenseMonthWise(fiscalYearInfo);

        //Reservation Budget
        //Total Budget.
        Map totalReservationBudget = dashboardDetailsService.getReservationBudgetTotalMonthWise(fiscalYearInfo);
        //Total Invoice
        def totalResvInvoiceAmount = dashboardDetailsService.getTotalInvoiceAmountForReservationBudget(fiscalYearInfo);

        //All Models
        [dashboardDetailsInstance: new DashboardDetails(params), budgetResevationArr: budgetResevationArr,

         totalIncomeBudgetAmount:totalIncomeBudget.allTotal,totalExpenseBudgetAmount:totalExpenseBudget.allTotal,
         totalIncomeInvoiceAmount:totalInvoiceIncome.allTotal,totalExpenseInvoiceAmount:totalInvoiceExpense.allTotal,

         totalPvtIncomeBudgetAmount:totalPvtIncomeBudget.allTotal,totalPvtExpenseBudgetAmount:totalPvtExpenseBudget.allTotal,
         totalPvtInvoiceIncomeAmount:totalPvtInvoiceIncome,totalPvtInvoiceExpenseAmount:totalPvtInvoiceExpense,

         totalReservBudgetAmount:totalReservationBudget.allTotal,totalReservInvoiceAmount:totalResvInvoiceAmount]
    }

    def reservationAccountWise() {

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        //Getting budget vendor data.
        ArrayList reservationAHWArr = dashboardDetailsService.getResevationDataAHWise(fiscalYearInfo);
        dashboardDetailsService.setTotalReservationInvoiceAmountAHWise(fiscalYearInfo,reservationAHWArr);

        //[dashboardDetailsInstance: new DashboardDetails(params), reservationAHWArr: reservationAHWArr]
        //Income & Expense budget
        //Total Budget.
        Map totalIncomeBudget = dashboardDetailsService.getIncomeBudgetTotalMonthWise(fiscalYearInfo);
        Map totalExpenseBudget = dashboardDetailsService.getExpenseBudgetTotalMonthWise(fiscalYearInfo);

        //Total Invoice.
        Map totalInvoiceIncome = dashboardDetailsService.getInvoiceIncomeTotalMonthWise(fiscalYearInfo);
        Map totalInvoiceExpense = dashboardDetailsService.getInvoiceExpenseTotalMonthWise(fiscalYearInfo);

        //Private Budget
        //Invoice wise
        def totalPvtInvoiceIncome = dashboardDetailsService.getTotalIncomeInvoiceAmountForPrivateBudget(fiscalYearInfo);
        def totalPvtInvoiceExpense = dashboardDetailsService.getTotalExpenseInvoiceAmountForPrivateBudget(fiscalYearInfo);

        //Total Budget.
        Map totalPvtIncomeBudget = dashboardDetailsService.getPrivateBudgetTotalIncomeMonthWise(fiscalYearInfo);
        Map totalPvtExpenseBudget = dashboardDetailsService.getPrivateBudgetTotalExpenseMonthWise(fiscalYearInfo);

        //Reservation Budget
        //Total Budget.
        Map totalReservationBudget = dashboardDetailsService.getReservationBudgetTotalMonthWise(fiscalYearInfo);
        //Total Invoice
        def totalResvInvoiceAmount = dashboardDetailsService.getTotalInvoiceAmountForReservationBudget(fiscalYearInfo);

        //All Models
        [dashboardDetailsInstance: new DashboardDetails(params), reservationAHWArr: reservationAHWArr,

         totalIncomeBudgetAmount:totalIncomeBudget.allTotal,totalExpenseBudgetAmount:totalExpenseBudget.allTotal,
         totalIncomeInvoiceAmount:totalInvoiceIncome.allTotal,totalExpenseInvoiceAmount:totalInvoiceExpense.allTotal,

         totalPvtIncomeBudgetAmount:totalPvtIncomeBudget.allTotal,totalPvtExpenseBudgetAmount:totalPvtExpenseBudget.allTotal,
         totalPvtInvoiceIncomeAmount:totalPvtInvoiceIncome,totalPvtInvoiceExpenseAmount:totalPvtInvoiceExpense,

         totalReservBudgetAmount:totalReservationBudget.allTotal,totalReservInvoiceAmount:totalResvInvoiceAmount]
    }

    def privateSpendingsNameWise() {

        def activeFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(activeFiscalYear)

        //Getting budget customer data.
        ArrayList privateBudgetArr = dashboardDetailsService.getBudgetPrivateData(fiscalYearInfo);
        dashboardDetailsService.setTotalInvPrivateAmount(fiscalYearInfo,privateBudgetArr);


        //Income & Expense budget
        //Total Budget.
        Map totalIncomeBudget = dashboardDetailsService.getIncomeBudgetTotalMonthWise(fiscalYearInfo);
        Map totalExpenseBudget = dashboardDetailsService.getExpenseBudgetTotalMonthWise(fiscalYearInfo);

        //Total Invoice.
        Map totalInvoiceIncome = dashboardDetailsService.getInvoiceIncomeTotalMonthWise(fiscalYearInfo);
        Map totalInvoiceExpense = dashboardDetailsService.getInvoiceExpenseTotalMonthWise(fiscalYearInfo);

        //Private Budget
        //Invoice wise
        def totalPvtInvoiceIncome = dashboardDetailsService.getTotalIncomeInvoiceAmountForPrivateBudget(fiscalYearInfo);
        def totalPvtInvoiceExpense = dashboardDetailsService.getTotalExpenseInvoiceAmountForPrivateBudget(fiscalYearInfo);

        //Total Budget.
        Map totalPvtIncomeBudget = dashboardDetailsService.getPrivateBudgetTotalIncomeMonthWise(fiscalYearInfo);
        Map totalPvtExpenseBudget = dashboardDetailsService.getPrivateBudgetTotalExpenseMonthWise(fiscalYearInfo);

        //Reservation Budget
        //Total Budget.
        Map totalReservationBudget = dashboardDetailsService.getReservationBudgetTotalMonthWise(fiscalYearInfo);
        //Total Invoice
        def totalResvInvoiceAmount = dashboardDetailsService.getTotalInvoiceAmountForReservationBudget(fiscalYearInfo);

        // Private deposits and withdrawels for company
        Map processDataDetails = dashboardDetailsService.getProcessDataForPrivateWDC(fiscalYearInfo);


        //All Models
        [dashboardDetailsInstance: new DashboardDetails(params), privateBudgetArr: privateBudgetArr,

         totalIncomeBudgetAmount:totalIncomeBudget.allTotal,totalExpenseBudgetAmount:totalExpenseBudget.allTotal,
         totalIncomeInvoiceAmount:totalInvoiceIncome.allTotal,totalExpenseInvoiceAmount:totalInvoiceExpense.allTotal,
         totalPvtIncomeBudgetAmount:totalPvtIncomeBudget.allTotal,totalPvtExpenseBudgetAmount:totalPvtExpenseBudget.allTotal,
         totalPvtInvoiceIncomeAmount:totalPvtInvoiceIncome,totalPvtInvoiceExpenseAmount:totalPvtInvoiceExpense,

         totalReservBudgetAmount:totalReservationBudget.allTotal,totalReservInvoiceAmount:totalResvInvoiceAmount,
         processDataDetailsInstance: processDataDetails,
         totalReservBudgetAmount:totalReservationBudget.allTotal,totalReservInvoiceAmount:totalResvInvoiceAmount]
    }

    def loadIncomeGraphAccountWise(){
        def tabIndex = params.curTabInd;

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        //Getting budget vendor data.
        ArrayList customerAHWArr = dashboardDetailsService.getCustomerDataAHWise(fiscalYearInfo);

        //Getting vendor account data.
        ArrayList customerAccountData = dashboardDetailsService.getCustomerBudgetAHWiseData(fiscalYearInfo,customerAHWArr);

        //Getting expense invoice data.
        ArrayList incomeInvoiceData = dashboardDetailsService.getIncomeInvoiceDataAHWise(fiscalYearInfo,customerAHWArr);

        render(layout: "ajax",template: "incomeAccountGraphDetails",model: [tabInd:tabIndex,customerAHWArr:customerAHWArr,
                                                                            customerAccountInstance:customerAccountData,
                                                                            customerInvoiceAccountInstance:incomeInvoiceData]);
    }

    def incomeAndExpenseNameWise() {

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        //Getting budget customer data.
        ArrayList budgetCustomerArr = dashboardDetailsService.getBudgetCustomerData(fiscalYearInfo);
        dashboardDetailsService.setTotalIncomeInvoiceAmount(fiscalYearInfo,budgetCustomerArr);

        //Getting budget vendor data.
        ArrayList budgetVendorArr = dashboardDetailsService.getBudgetVendorData(fiscalYearInfo);
        dashboardDetailsService.setTotalExpenseInvoiceAmount(fiscalYearInfo,budgetVendorArr);

        //[dashboardDetailsInstance: new DashboardDetails(params),budgetVendorArr: budgetVendorArr,budgetCustomerArr: budgetCustomerArr]

        //Total Budget.
        Map totalIncomeBudget = dashboardDetailsService.getIncomeBudgetTotalMonthWise(fiscalYearInfo);
        Map totalExpenseBudget = dashboardDetailsService.getExpenseBudgetTotalMonthWise(fiscalYearInfo);

        //Total Invoice.
        Map totalInvoiceIncome = dashboardDetailsService.getInvoiceIncomeTotalMonthWise(fiscalYearInfo);
        Map totalInvoiceExpense = dashboardDetailsService.getInvoiceExpenseTotalMonthWise(fiscalYearInfo);

        //Private Budget
        //Invoice wise
        def totalPvtInvoiceIncome = dashboardDetailsService.getTotalIncomeInvoiceAmountForPrivateBudget(fiscalYearInfo);
        def totalPvtInvoiceExpense = dashboardDetailsService.getTotalExpenseInvoiceAmountForPrivateBudget(fiscalYearInfo);

        //Total Budget.
        Map totalPvtIncomeBudget = dashboardDetailsService.getPrivateBudgetTotalIncomeMonthWise(fiscalYearInfo);
        Map totalPvtExpenseBudget = dashboardDetailsService.getPrivateBudgetTotalExpenseMonthWise(fiscalYearInfo);

        //Reservation Budget
        //Total Budget.
        Map totalReservationBudget = dashboardDetailsService.getReservationBudgetTotalMonthWise(fiscalYearInfo);
        //Total Invoice
        def totalResvInvoiceAmount = dashboardDetailsService.getTotalInvoiceAmountForReservationBudget(fiscalYearInfo);

        //All Models
        [dashboardDetailsInstance: new DashboardDetails(params), budgetVendorArr: budgetVendorArr,budgetCustomerArr: budgetCustomerArr,

         totalIncomeBudgetAmount:totalIncomeBudget.allTotal,totalExpenseBudgetAmount:totalExpenseBudget.allTotal,
         totalIncomeInvoiceAmount:totalInvoiceIncome.allTotal,totalExpenseInvoiceAmount:totalInvoiceExpense.allTotal,

         totalPvtIncomeBudgetAmount:totalPvtIncomeBudget.allTotal,totalPvtExpenseBudgetAmount:totalPvtExpenseBudget.allTotal,
         totalPvtInvoiceIncomeAmount:totalPvtInvoiceIncome,totalPvtInvoiceExpenseAmount:totalPvtInvoiceExpense,

         totalReservBudgetAmount:totalReservationBudget.allTotal,totalReservInvoiceAmount:totalResvInvoiceAmount]
    }

    def incomeAndExpenseAccountHeadWise() {

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        //Getting budget customer data.
        ArrayList customerAHWArr = dashboardDetailsService.getCustomerDataAHWise(fiscalYearInfo);
        dashboardDetailsService.setTotalIncomeInvoiceAmountAHWise(fiscalYearInfo,customerAHWArr);

        //Getting budget vendor data.
        ArrayList vendorAHWArr = dashboardDetailsService.getVendorDataAHWise(fiscalYearInfo);
        dashboardDetailsService.setTotalExpenseInvoiceAmountAHWise(fiscalYearInfo,vendorAHWArr);

        //[dashboardDetailsInstance: new DashboardDetails(params),vendorAHWArr: vendorAHWArr,customerAHWArr: customerAHWArr]

        //Total Budget.
        Map totalIncomeBudget = dashboardDetailsService.getIncomeBudgetTotalMonthWise(fiscalYearInfo);
        Map totalExpenseBudget = dashboardDetailsService.getExpenseBudgetTotalMonthWise(fiscalYearInfo);

        //Total Invoice.
        Map totalInvoiceIncome = dashboardDetailsService.getInvoiceIncomeTotalMonthWise(fiscalYearInfo);
        Map totalInvoiceExpense = dashboardDetailsService.getInvoiceExpenseTotalMonthWise(fiscalYearInfo);

        //Private Budget
        //Invoice wise
        def totalPvtInvoiceIncome = dashboardDetailsService.getTotalIncomeInvoiceAmountForPrivateBudget(fiscalYearInfo);
        def totalPvtInvoiceExpense = dashboardDetailsService.getTotalExpenseInvoiceAmountForPrivateBudget(fiscalYearInfo);

        //Total Budget.
        Map totalPvtIncomeBudget = dashboardDetailsService.getPrivateBudgetTotalIncomeMonthWise(fiscalYearInfo);
        Map totalPvtExpenseBudget = dashboardDetailsService.getPrivateBudgetTotalExpenseMonthWise(fiscalYearInfo);

        //Reservation Budget
        //Total Budget.
        Map totalReservationBudget = dashboardDetailsService.getReservationBudgetTotalMonthWise(fiscalYearInfo);
        //Total Invoice
        def totalResvInvoiceAmount = dashboardDetailsService.getTotalInvoiceAmountForReservationBudget(fiscalYearInfo);

        //All Models
        [dashboardDetailsInstance: new DashboardDetails(params), vendorAHWArr: vendorAHWArr,customerAHWArr: customerAHWArr,

         totalIncomeBudgetAmount:totalIncomeBudget.allTotal,totalExpenseBudgetAmount:totalExpenseBudget.allTotal,
         totalIncomeInvoiceAmount:totalInvoiceIncome.allTotal,totalExpenseInvoiceAmount:totalInvoiceExpense.allTotal,

         totalPvtIncomeBudgetAmount:totalPvtIncomeBudget.allTotal,totalPvtExpenseBudgetAmount:totalPvtExpenseBudget.allTotal,
         totalPvtInvoiceIncomeAmount:totalPvtInvoiceIncome,totalPvtInvoiceExpenseAmount:totalPvtInvoiceExpense,

         totalReservBudgetAmount:totalReservationBudget.allTotal,totalReservInvoiceAmount:totalResvInvoiceAmount]
    }

    def showBudgetAndBookingSummary() {

        def activeFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(activeFiscalYear)

        //Budget wise.
        Map totalIncomeBudget = dashboardDetailsService.getIncomeBudgetTotalMonthWise(fiscalYearInfo);
        Map totalExpenseBudget = dashboardDetailsService.getExpenseBudgetTotalMonthWise(fiscalYearInfo);

        Map grossProfitMonthly = dashboardDetailsService.getGrossProfitBasedOnBudget(totalIncomeBudget,totalExpenseBudget);
        /////Company Income Tax Reservation////
        def incomeTaxPercentage = new CoreParamsHelper().getCompanySetupReservationTaxAmount()

        //Net profit = Without Tax Reservation amount.
        Map netProfitMonthly = dashboardDetailsService.getNetProfitBasedOnBudget(grossProfitMonthly,incomeTaxPercentage);

        Double totalNetProfit = 0.00;
        totalNetProfit = dashboardDetailsService.getTotalGrossProfitFromMonthAmount(netProfitMonthly);
        totalNetProfit = totalNetProfit.round(2);

        //Gross profit per booking wise.
        Map totalInvoiceIncome = dashboardDetailsService.getInvoiceIncomeTotalMonthWise(fiscalYearInfo);
        Map totalInvoiceExpense = dashboardDetailsService.getInvoiceExpenseTotalMonthWise(fiscalYearInfo);

        Map grossProfitInvoice = dashboardDetailsService.getGrossProfitBasedOnInvoice(totalInvoiceIncome,totalInvoiceExpense);

        Double totalGrossProfitBaseOnInvoice = 0.00;
        totalGrossProfitBaseOnInvoice = dashboardDetailsService.getTotalGrossProfitFromMonthAmount(grossProfitInvoice);
        totalGrossProfitBaseOnInvoice = totalGrossProfitBaseOnInvoice.round(2);

        [totalGrossProfitAmount:totalGrossProfitBaseOnInvoice,totalNetProfitAmount:totalNetProfit]
    }

    def showProfitSummaryAsTypeWise(){

        def grossProfitBudget
        def grossProfitForecast
        def grossProfitBooking

        def netProfitBudget
        def netProfitForecast
        def netProfitBooking

        def totalIncomeBudget
        def totalExpenseBudget
        def totalReservationBudget
        def totalInvoiceIncome
        def totalInvoiceExpense
        def totalInvoiceReservation

        def totalIncomeBudgetWithTax
        def totalExpenseBudgetWithTax
        def totalReservationBudgetWithTax
        def totalInvoiceIncomeWithTax
        def totalInvoiceExpenseWithTax
        def totalInvoiceReservationWithTax

        def incomeForcast
        def expenseForcast

        def taxReservationAmount
        Map summaryInfo

        String summaryType = params.summaryType;
        String taxReservType = params.taxReservType;
        def budgetItemType =params.budgetItemType

        def activeFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(activeFiscalYear)

        /////Company Income Tax Reservation////
        def incomeTaxPercentage = new CoreParamsHelper().getCompanySetupReservationTaxAmount()

        if(budgetItemType == "incomeNexpense"){
            summaryInfo = dashboardDetailsService.getProfitSummaryForIncNExp(fiscalYearInfo, incomeTaxPercentage, taxReservType )
        }else if(budgetItemType == "privateBudget"){
            summaryInfo = dashboardDetailsService.getProfitSummaryForPrivate(fiscalYearInfo, incomeTaxPercentage, taxReservType )
        }else if(budgetItemType =="reservationBudget"){
            summaryInfo = dashboardDetailsService.getProfitSummaryForReservation(fiscalYearInfo, incomeTaxPercentage, taxReservType )
        }

        //Without Tax reservation
        totalIncomeBudget = summaryInfo.totalIncomeBudget
        totalExpenseBudget = summaryInfo.totalExpenseBudget
        totalReservationBudget = summaryInfo.totalReservationBudget
        totalInvoiceIncome = summaryInfo.totalInvoiceIncome
        totalInvoiceExpense = summaryInfo.totalInvoiceExpense
        totalInvoiceReservation = summaryInfo.totalInvoiceReservation

        //With Tax reservation
        totalIncomeBudgetWithTax = summaryInfo.totalIncomeBudgetWithTax
        totalExpenseBudgetWithTax = summaryInfo.totalExpenseBudgetWithTax
        totalReservationBudgetWithTax = summaryInfo.totalReservationBudgetWithTax
        totalInvoiceIncomeWithTax = summaryInfo.totalInvoiceIncomeWithTax
        totalInvoiceExpenseWithTax = summaryInfo.totalInvoiceExpenseWithTax
        totalInvoiceReservationWithTax = summaryInfo.totalInvoiceReservationWithTax

        taxReservationAmount = summaryInfo.taxReservationAmount

        grossProfitBudget = summaryInfo.grossProfitBudget
        netProfitBudget = summaryInfo.netProfitBudget

        grossProfitBooking = summaryInfo.grossProfitBooking
        netProfitBooking = summaryInfo.netProfitBooking

        grossProfitForecast = summaryInfo.grossProfitForecast
        netProfitForecast = summaryInfo.netProfitForecast

        incomeForcast = summaryInfo.incomeForcast
        expenseForcast = summaryInfo.expenseForcast

        //Different combination of model for view.
        if(budgetItemType == "incomeNexpense"){
            if(taxReservType == "taxWithReservation" && summaryType == "budNForcast") {
                [grossProfitInstance:grossProfitBudget,grossProfitForecastInstance:grossProfitForecast,summaryType:summaryType,taxReservType:taxReservType,
                 incomeBudgetInstance:totalIncomeBudget,expenseBudgetInstance:totalExpenseBudget,
                 incomeInvoiceInstance:totalInvoiceIncome,expenseInvoiceInstance:totalInvoiceExpense,incomeForcast: incomeForcast,expenseForcast:expenseForcast]
            }else if(taxReservType == "taxWithoutReservation" && summaryType == "budNForcast") {
                [netProfitInstance:netProfitBudget,netProfitForecastInstance:netProfitForecast,summaryType:summaryType,taxReservType:taxReservType,
                 incomeBudgetInstance:totalIncomeBudget,expenseBudgetInstance:totalExpenseBudget,
                 incomeInvoiceInstance:totalInvoiceIncome,expenseInvoiceInstance:totalInvoiceExpense,
                 taxReservationAmountInstance:taxReservationAmount,incomeForcast: incomeForcast,expenseForcast:expenseForcast]
            }else if(taxReservType == "taxWithReservation" && summaryType == "budNBook") {
                [grossProfitInstance:grossProfitBudget,grossProfitBookingInstance:grossProfitBooking,summaryType:summaryType,taxReservType:taxReservType,
                 incomeBudgetInstance:totalIncomeBudget,expenseBudgetInstance:totalExpenseBudget,
                 incomeInvoiceInstance:totalInvoiceIncome,expenseInvoiceInstance:totalInvoiceExpense]
            }else if(taxReservType == "taxWithoutReservation" && summaryType == "budNBook") {
                [netProfitInstance:netProfitBudget,netProfitBookingInstance:netProfitBooking,summaryType:summaryType,taxReservType:taxReservType,
                 incomeBudgetInstance:totalIncomeBudget,expenseBudgetInstance:totalExpenseBudget,
                 incomeInvoiceInstance:totalInvoiceIncome,expenseInvoiceInstance:totalInvoiceExpense,
                 taxReservationAmountInstance:taxReservationAmount]
            }else if(taxReservType == "taxWithReservation" && summaryType == "bookNForecast") {
                [grossProfitForecastInstance:grossProfitForecast,grossProfitBookingInstance:grossProfitBooking,summaryType:summaryType,taxReservType:taxReservType,
                 incomeBudgetInstance:totalIncomeBudget,expenseBudgetInstance:totalExpenseBudget,
                 incomeInvoiceInstance:totalInvoiceIncome,expenseInvoiceInstance:totalInvoiceExpense,incomeForcast: incomeForcast,expenseForcast:expenseForcast]
            }else if(taxReservType == "taxWithoutReservation" && summaryType == "bookNForecast") {
                [netProfitForecastInstance:netProfitForecast,netProfitBookingInstance:netProfitBooking,summaryType:summaryType,taxReservType:taxReservType,
                 incomeBudgetInstance:totalIncomeBudget,expenseBudgetInstance:totalExpenseBudget,
                 incomeInvoiceInstance:totalInvoiceIncome,expenseInvoiceInstance:totalInvoiceExpense,
                 taxReservationAmountInstance:taxReservationAmount,incomeForcast: incomeForcast,expenseForcast:expenseForcast]
            }
        }else if(budgetItemType =="reservationBudget" ){

            if(taxReservType == "taxWithReservation" && summaryType == "budNBook") {
                [grossProfitInstance:grossProfitBudget,grossProfitBookingInstance:grossProfitBooking,summaryType:summaryType,taxReservType:taxReservType,
                 incomeBudgetInstance:totalIncomeBudget,expenseBudgetInstance:totalExpenseBudget,reservationBudgetInstance:totalReservationBudget,
                 incomeInvoiceInstance:totalInvoiceIncome,expenseInvoiceInstance:totalInvoiceExpense,reservationInvoiceInstance:totalInvoiceReservation,
                 budgetItemType:budgetItemType]
            }

        }else if(budgetItemType == "privateBudget" ){

            if(taxReservType == "taxWithReservation" && summaryType == "budNForcast") {
                [grossProfitInstance:grossProfitBudget,grossProfitForecastInstance:grossProfitForecast,summaryType:summaryType,taxReservType:taxReservType,
                 incomeBudgetInstance:totalIncomeBudget,expenseBudgetInstance:totalExpenseBudget,
                 incomeInvoiceInstance:totalInvoiceIncome,expenseInvoiceInstance:totalInvoiceExpense,incomeForcast: incomeForcast,expenseForcast:expenseForcast]
            }else if(taxReservType == "taxWithReservation" && summaryType == "budNBook") {
                [grossProfitInstance:grossProfitBudget,grossProfitBookingInstance:grossProfitBooking,summaryType:summaryType,taxReservType:taxReservType,
                 incomeBudgetInstance:totalIncomeBudget,expenseBudgetInstance:totalExpenseBudget,
                 incomeInvoiceInstance:totalInvoiceIncome,expenseInvoiceInstance:totalInvoiceExpense]
            }else if(taxReservType == "taxWithReservation" && summaryType == "bookNForecast") {
                [grossProfitForecastInstance:grossProfitForecast,grossProfitBookingInstance:grossProfitBooking,summaryType:summaryType,taxReservType:taxReservType,
                 incomeBudgetInstance:totalIncomeBudget,expenseBudgetInstance:totalExpenseBudget,
                 incomeInvoiceInstance:totalInvoiceIncome,expenseInvoiceInstance:totalInvoiceExpense,incomeForcast: incomeForcast,expenseForcast:expenseForcast]
            }
        }

    }

    def summeryKeyNumberWise() {

        def activeFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(activeFiscalYear)

        //Invoice wise
        Map totalInvoiceIncome = dashboardDetailsService.getInvoiceIncomeTotalMonthWise(fiscalYearInfo);
        Map totalInvoiceExpense = dashboardDetailsService.getInvoiceExpenseTotalMonthWise(fiscalYearInfo);

        /////Company Income Tax Reservation////
        def incomeTaxPercentage = new CoreParamsHelper().getCompanySetupReservationTaxAmount()

        //Gross profit = With Tax Reservation amount.
        Map grossProfitInvoice = dashboardDetailsService.getGrossProfitBasedOnInvoice(totalInvoiceIncome,totalInvoiceExpense);
        //Net profit = Without Tax Reservation amount.
        Map netProfitInvoice = dashboardDetailsService.getNetProfitBasedOnInvoice(grossProfitInvoice,incomeTaxPercentage);

        //Budget wise.
        Map totalIncomeBudget = dashboardDetailsService.getIncomeBudgetTotalMonthWise(fiscalYearInfo);
        Map totalExpenseBudget = dashboardDetailsService.getExpenseBudgetTotalMonthWise(fiscalYearInfo);

        //Forecast from Gross profit.
        Map forecastOnNetProfit = dashboardDetailsService.getForecastFromGrossProfit(totalInvoiceIncome,totalInvoiceExpense,
                totalIncomeBudget,totalExpenseBudget);

        //Gross profit = With Tax Reservation amount.
        Map grossProfitBudget = dashboardDetailsService.getGrossProfitBasedOnBudget(totalIncomeBudget,totalExpenseBudget);
        //Net profit = Without Tax Reservation amount.
        Map netProfitBudget = dashboardDetailsService.getNetProfitBasedOnBudget(grossProfitBudget,incomeTaxPercentage);

        //Forecast Gross profit = With Tax Reservation amount.
        Map forecastGrossProfit = dashboardDetailsService.getForecastOfGrossProfit(totalInvoiceIncome,totalInvoiceExpense,
                totalIncomeBudget,totalExpenseBudget);

        //Forecast Net profit = Without Tax Reservation amount.
        Map netProfitForecast = dashboardDetailsService.getForecastOfNetProfit(forecastGrossProfit,incomeTaxPercentage);

        [grossProfitInstance:grossProfitBudget,incomeTaxReservationInstance:IncomeTaxReservation,netProfitBudgetInstance:BudgetNetProfit,netProfitInvoiceInstance:InvoiceNetProfit,netProfitCurrentForecastInstance:CurrentForecastNetProfit,IncomeTaxPercentage:IncomeTaxPercentage]
    }
}
