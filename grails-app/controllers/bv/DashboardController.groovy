package bv

import org.springframework.web.servlet.support.RequestContextUtils


class DashboardController {

    DashboardDetailsService dashboardDetailsService;

    def index() {

        //Company setup info.
        setCompanyInfo();
        Integer max
        params.max = Math.min(max ?: 2, 100)
        def CompanySetup = CompanySetup.executeQuery("select id from CompanySetup")
//        def languageField = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT language FROM company_setup ")

        if (CompanySetup) {
            [productUnitInstance: new ProductUnit(params), productUnitInstanceList: ProductUnit.list(params),
             productUnitInstanceTotal: ProductUnit.count()]
        } else {
            redirect(controller: 'companySetup', action: "index")
        }
    }

    def updateBudgetInvoiceSummary(){
        def budgetType = params.budgetType;
        //Profit calculation
        def activeFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(activeFiscalYear)

        //Budget wise.
        Map totalIncomeBudget = [:];
        Map totalExpenseBudget = [:];
        Map grossProfitMonthly = [:];
        Map netProfitMonthly = [:];

        /////Company Income Tax Reservation////
        def incomeTaxPercentage = new CoreParamsHelper().getCompanySetupReservationTaxAmount()

        if(budgetType == "null"){
            budgetType = "incNexp"
        }

        //Calculation for budget
        if(budgetType == "incNexp" || budgetType == "income" || budgetType == "expense"){
            totalIncomeBudget = dashboardDetailsService.getIncomeBudgetTotalMonthWise(fiscalYearInfo);
            totalExpenseBudget = dashboardDetailsService.getExpenseBudgetTotalMonthWise(fiscalYearInfo);

            grossProfitMonthly = dashboardDetailsService.getGrossProfitBasedOnBudget(totalIncomeBudget,totalExpenseBudget);
            //Net profit = Without Tax Reservation amount.
            netProfitMonthly = dashboardDetailsService.getNetProfitBasedOnBudget(grossProfitMonthly,incomeTaxPercentage);
        }else if(budgetType == "reserve"){
            grossProfitMonthly = dashboardDetailsService.getReservationBudgetTotalMonthWise(fiscalYearInfo);
            netProfitMonthly = grossProfitMonthly;
        }else if(budgetType == "privateSpend"){
            grossProfitMonthly = dashboardDetailsService.getPrivateBudgetTotalMonthWise(fiscalYearInfo);
            netProfitMonthly = grossProfitMonthly;
        }

        Double totalNetProfit = 0.00;
        totalNetProfit = netProfitMonthly["allTotal"];
        totalNetProfit = totalNetProfit.round(2);

        Map grossProfitInvoice = [:];
        Double totalGrossProfitBaseOnInvoice = 0.00;
        //Calculation for invoice
        if(budgetType == "incNexp" || budgetType == "income" || budgetType == "expense"){
            //Gross profit per booking wise.
            Map totalInvoiceIncome = dashboardDetailsService.getInvoiceIncomeTotalMonthWise(fiscalYearInfo);
            Map totalInvoiceExpense = dashboardDetailsService.getInvoiceExpenseTotalMonthWise(fiscalYearInfo);

            grossProfitInvoice = dashboardDetailsService.getGrossProfitBasedOnInvoice(totalInvoiceIncome,totalInvoiceExpense);
            totalGrossProfitBaseOnInvoice = dashboardDetailsService.getTotalGrossProfitFromMonthAmount(grossProfitInvoice);
        }else if(budgetType == "reserve"){
            Double totalIncInv = dashboardDetailsService.getTotalIncomeInvoiceAmountForReservationBudget(fiscalYearInfo);
            Double totalExpInv = dashboardDetailsService.getTotalExpenseInvoiceAmountForReservationBudget(fiscalYearInfo);

            totalGrossProfitBaseOnInvoice = totalIncInv - totalExpInv;
        }
        else if(budgetType == "privateSpend"){

            Double totalIncInv = dashboardDetailsService.getTotalIncomeInvoiceAmountForPrivateBudget(fiscalYearInfo);
            Double totalExpInv = dashboardDetailsService.getTotalExpenseInvoiceAmountForPrivateBudget(fiscalYearInfo);

            totalGrossProfitBaseOnInvoice = totalIncInv - totalExpInv;
//            totalGrossProfitBaseOnInvoice = totalIncInv - totalExpInv*(-1);
        }

        //totalGrossProfitBaseOnInvoice = totalGrossProfitBaseOnInvoice.round(2);

        render(layout: "ajax",template: "budgetProfitSummary",model: [totalGrossProfitAmount:totalGrossProfitBaseOnInvoice,totalNetProfitAmount:totalNetProfit]);
    }

    def setCompanyInfo() {

        if (!session["isLangSet"]) {
            String queryLanguage = "SELECT * FROM company_setup WHERE id= 1"
            ArrayList companyInfo = new BudgetViewDatabaseService().executeQuery(queryLanguage)

            Map map = ["Id"                   : 0,
                       "version"              : 0,
                       "language"             : '',
                       "address_line1"        : '',
                       "address_line2"        : '',
                       "amountDecimalPoint"   : 0,
                       "chamber_commerce_no"  : '',
                       "city"                 : '',
                       "company_date_format"  : '',
                       "company_full_name"    : '',
                       "company_short_name"   : '',
                       "country"              : '',
                       "currency_id"          : 0,
                       "date_seperator"       : '',
                       "decimal_rounding_type": '',
                       "decimal_seprator"     : '',
                       "email_address"        : '',
                       "iban"                 : '',
                       "bic"                  : ''
            ]

            companyInfo.eachWithIndex { item, key ->

                map.Id = item[0]
                map.version = item[1]
                map.language = item[2]
                map.address_line1 = item[3]
                map.address_line2 = item[4]
                map.amountDecimalPoint = item[5]
                map.chamber_commerce_no = item[6]
                map.city = item[7]
                map.company_date_format = item[8]
                map.company_full_name = item[9]
                map.company_short_name = item[10]
                map.country = item[11]
                map.currency_id = item[12]
                map.date_seperator = item[13]
                map.decimal_rounding_type = item[14]
                map.decimal_seprator = item[15]
                map.email_address = item[16]
                map.iban = item[50]
                map.bic = item[52]
            }

            session.companyInfo = []
            session.companyInfo << map
            //println(session.companyInfo.currency_id);

            String resultSetLn = 'en'
            if (companyInfo) {
                resultSetLn = session.companyInfo[0].language
            }
            //println(session.companyInfo.language);

            //def langFile = bv.CompanySetup.findAllByCompanyFullName('BudgetView').LANGUAGE[0]
            def locale = new Locale(resultSetLn, resultSetLn.toUpperCase(), "") //
            //def lastLocale = RequestContextUtils.getLocale(request)
            RequestContextUtils.getLocaleResolver(request).setLocale(request, response, locale)
            session["isLangSet"] = true
        }

    }
}
