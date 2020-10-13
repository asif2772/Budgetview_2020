package budgetview_2020

import auth.InitService

class BootStrap {
    InitService initService

    def init = { servletContext ->

//        initService.tempUserData()
    }
    def destroy = {
    }
}


//import bv.CompanyTransactionType
//import com.gsl.bv.SystemConfiguration
//import bv.Currencies
//import bv.Countries
//import bv.CoreParams
//
////import com.bv.RequestMap
//import com.bv.User
//import com.bv.Role
//import com.bv.UserRole
//import bv.ChartClass
//import bv.CompanySetup
//
//import static org.springframework.security.acls.domain.BasePermission.ADMINISTRATION
//import static org.springframework.security.acls.domain.BasePermission.DELETE
//import static org.springframework.security.acls.domain.BasePermission.READ
//import static org.springframework.security.acls.domain.BasePermission.WRITE
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.authority.AuthorityUtils
//import org.springframework.security.core.context.SecurityContextHolder as SCH
//import bv.ChartClassType
//import bv.TaxCategory
//import bv.PaymentTerms
//import bv.MomentOfSendingInvoice
//import bv.VatCategory
//
////MANUALl DATA INSERTION
//import groovy.sql.Sql
//import bv.ChartGroup
//import bv.ChartMaster
//import groovy.sql.GroovyRowResult
//import javax.sql.DataSource
//import bv.DebitCreditGlSetup
//
//class BootStrap {
//
//    def aclService
//    def aclUtilService
//    def objectIdentityRetrievalStrategy
//    def sessionFactory
//    def databaseSynchService
//    //def springSecurityService
//    //def ipAddressFilter
//
//    //MANUAL DATA INSERTION
//    DataSource dataSource
//    def init = { servletContext ->
//
////        databaseSynchService.updateAllDatabase();
//        //def ipmap = ['/admin': '10.10.*', '/other':'192.168.0.123']
//        //ipAddressFilter.setIpRestrictions(ipmap)
//        //Locale.setDefault(new Locale("nl"));
//
//        boolean configured = false;
//
//        try {
//            //always check for update and inset if found
//
//            SystemConfiguration configuration = SystemConfiguration.get(1);
//
//            if (!configuration || !configuration.configured) {
//
//                this.insertTransactionType();
//                this.insertCurrencies();
//                this.insertNextGeneratedNumber();
//                this.insertCoreParams();
//                this.insertChartClassType();
//                //this.debitCreditGlSetup();
//                this.vendorMaster();
//
//                if (!configuration) {
//                    configuration = new SystemConfiguration(configured: true);
//                    configuration.id = 1;
//                } else {
//                    configuration.configured = true;
//                }
//
//                configuration.save();
//                // at the end of all system data initialization
//            }
//
//            this.initUsers()
//            //this.loginAsAdmin()
//            //this.grantPermissions()
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//    }
//
//    def insertTransactionType() {
//        def pvtMoney = CompanyTransactionType.findByCode("pMoney")?:new CompanyTransactionType(name: "Private Money", code: "pMoney", status: 1).save()
//        def cCard = CompanyTransactionType.findByCode("cCard")?:new CompanyTransactionType(name: "Company Credit Card", code: "cCard", status: 1).save()
//        def pTransaction = CompanyTransactionType.findByCode("pTransactions")?:new CompanyTransactionType(name: "Company Pin transactions", code: "pTransactions", status: 1).save()
//        def cRecipts = CompanyTransactionType.findByCode("cReceipts")?:new CompanyTransactionType(name: "Company Cash Receipts", code: "cReceipts", status: 1).save()
//    }
//
//    def vendorMaster = {
//        //Vendor Master INITIAL DATA INSERT
//        def db = new Sql(dataSource)
//        String venSetup = """insert into vendor_master (id, version, by_shop, cham_of_commerce, comments, company_name, credit_status, curr_code, default_gl_account, email, first_name, frequency_of_invoice, gender, last_name, middle_name, moment_of_sending, payment_term_id, status, vat, vendor_code, vendor_name, vendor_type) values
//        (1,1,1,'','','','Good History','EUR','4500','','','monthly','Male','','','',1,1,'1','00001','Private Money','rp'),
//        (2,1,1,'','','','Good History','EUR','7000','','','monthly','Male','','','',1,1,'1','00002','Company Credit Card','rp'),
//        (3,1,1,'','','','Good History','EUR','4500','','','monthly','Male','','','',1,1,'1','00003','Company Pin transactions','rp'),
//        (4,1,1,'','','','Good History','EUR','4500','','','monthly','Male','','','',1,1,'1','00004','Company Cash Receipts','rp')""";
//        db.execute(venSetup);
//    }
//
//    def insertCurrencies = {
//        Countries usa = new Countries(name: "USA", iso2: "US", iso3: "USA", printablename: "United States", numcode: 1, status: 1).save()
//        new Currencies(currency: "USD", currCode: "USD", currSymbol: "\$", hundredsName: "Cent", status: 1, country: usa).save()
//
//        Countries nld = new Countries(name: "NLD", iso2: "NL", iso3: "NLD", printablename: "Netherlands", numcode: 31, status: 1).save()
//        new Currencies(currency: "EUR", currCode: "EUR", currSymbol: "€", hundredsName: "---", status: 1, country: nld).save()
//    }
//
//    def insertNextGeneratedNumber(){
//        //new bv.NextGeneratedNumber(id: 1, version: 0, customer: 0, vendor: 0,product:0,journalEntry:0,quickEntry:0,budgetExpense:0,invoiceExpense:0,invoiceIncome:0,invoiceInvestment:0,internalBanking:0,budgetIncome:0,receiptEntry:0).save()
//        def db = new Sql(dataSource)
//        String NextGeneratedNumberEntry = """insert  into `next_generated_number`(`id`,`version`,`budget_expense`,`budget_income`,`customer`,`internal_banking`,`invoice_expense`,`invoice_income`,`invoice_investment`,`journal_entry`,`product`,`quick_entry`,`receipt_entry`,`vendor`,`recenciliation_entry`) values (1,0,0,0,0,0,0,0,0,0,0,0,0,0,0)""";
//        db.execute(NextGeneratedNumberEntry);
//    }
//
//    def insertCoreParams() {
//        ///////
//        new bv.DebitCreditGlSetup(id: 1, version: 0, creditorGlCode: '1600', debitorGlCode: '1300', reconcilationGlCode: '1100').save()
//
//        //TAX INITIAL DATA INSERT
//        new TaxCategory(categoryName: "Tax 5.0", rate: 5, salesGlCode: '1200', status: 1).save()
//        //PAYMENT INITIAL DATA INSERT
//        new PaymentTerms(alertRepeatDays: 1, alertStartDays: 1, daysBeforeDue: 14, status: 1, terms: '2 week').save()
//        //MOMENT OF SENDING INITIAL DATA INSERT
//        new MomentOfSendingInvoice(description: 'Test', invoiceSendValue: 25, name: 'End of the Month', status: 1).save()
//
//        //VAT INITIAL DATA INSERT
//        //new VatCategory(categoryName: 'Test', rate: 25, status: 1, purchasingGlCode:1).save()
//        new VatCategory(categoryName: 'VAT High', purchaseGlAccount:'2105', rate: 21, salesGlAccount:'2100', status: 1).save()
//        new VatCategory(categoryName: 'VAT low', purchaseGlAccount:'2115', rate: 6, salesGlAccount:'2110', status: 1).save()
//        new VatCategory(categoryName: 'No VAT', purchaseGlAccount:'2115', rate: 0, salesGlAccount:'2110', status: 1).save()
//
//        //SYSTEM PREFIX DATA INSERT
//        new bv.SystemPrefix(id: 1, prefix: 'CUS', prefixLen: 3, title: 'Customer').save()
//        new bv.SystemPrefix(id: 2, prefix: 'VEN', prefixLen: 5, title: 'Vendor').save()
//        new bv.SystemPrefix(id: 3, prefix: 'PRO', prefixLen: 5, title: 'Product').save()
//        new bv.SystemPrefix(id: 4, prefix: 'J', prefixLen: 8, title: 'Journal Entry').save()
//        new bv.SystemPrefix(id: 5, prefix: 'Q', prefixLen: 6, title: 'Quick Entry').save()
//        new bv.SystemPrefix(id: 6, prefix: 'EXP', prefixLen: 6, title: 'Budget Expense').save()
//        new bv.SystemPrefix(id: 7, prefix: 'INVE', prefixLen: 6, title: 'Invoice Expense').save()
//        new bv.SystemPrefix(id: 8, prefix: 'INVI', prefixLen: 6, title: 'Invoice Income').save()
//        new bv.SystemPrefix(id: 9, prefix: 'INVEST', prefixLen: 6, title: 'Investment Invoice').save()
//        new bv.SystemPrefix(id: 10, prefix: 'INB', prefixLen: 6, title: 'Internal Banking').save()
//        new bv.SystemPrefix(id: 11, prefix: 'INC', prefixLen: 6, title: 'Budget Income').save()
//        new bv.SystemPrefix(id: 12, prefix: 'RE', prefixLen: 6, title: 'Receipt Entry').save()
//
//        //Reconciliation Booking Type/////
//        new bv.ReconciliationBookingType(formType:1,glAccount: '2300',isFixedGl: 1,paymentType:'Booked Invoice or Receipt Payment',status: 1).save()
//        new bv.ReconciliationBookingType(formType:2,glAccount: '2300',isFixedGl: 1,paymentType:'Payment for not yet Booked Invoice or Receipt',status: 1).save()
//        new bv.ReconciliationBookingType(formType:3,glAccount: '',isFixedGl: 0,paymentType:'Payment Expenses without Invoice or Receipt',status: 1).save()
//        new bv.ReconciliationBookingType(formType:4,glAccount: '1550',isFixedGl: 1,paymentType:'VAT Payment',status: 1).save()
//        new bv.ReconciliationBookingType(formType:4,glAccount: '1410',isFixedGl: 1,paymentType:'Private withdrawles or Deposits(no declearation)',status: 1).save()
//        new bv.ReconciliationBookingType(formType:4,glAccount: '2200',isFixedGl: 1,paymentType:'Internal Banking',status: 1).save()
//        new bv.ReconciliationBookingType(formType:4,glAccount: '1200',isFixedGl: 1,paymentType:'Cash Register Deposits or Withdrawels',status: 1).save()
//        new bv.ReconciliationBookingType(formType:4,glAccount: '2220',isFixedGl: 1,paymentType:'Loans and Downpayments',status: 1).save()
//        new bv.ReconciliationBookingType(formType:4,glAccount: '',isFixedGl: 0,paymentType:'Other Non Expense or Profit Payment',status: 1).save()
//
//
//        //CORE PARAMS INITIAL DATA INSERT
//        //CoreParams statusType = new CoreParams(varName: "STATUS", paramsName: "Select Status{0}::Active{1}::Inactive{2}::Delete{-1}", typeName: "select").save()
//        CoreParams statusType = new CoreParams(varName: "STATUS", paramsName: "Status{0}::Active{1}::Inactive{2}::Delete{-1}", typeName: "select").save()
//        //CoreParams genderName = new CoreParams(varName: "GENDER", paramsName: "Select Gender{''}::Male{'Male'}::Female{'Female'}", typeName: "select").save()
//        CoreParams genderName = new CoreParams(varName: "GENDER", paramsName: "Male{'Male'}::Female{'Female'}", typeName: "select").save()
//        //CoreParams invoiceFrequency = new CoreParams(varName: "INVOICE_FREQUENCE", paramsName: "Select frequency of invoice{''}::Once{'Once'}::weekly{'weekly'}::every two weeks{'every two weeks'}::monthly{'monthly'}::two monthly{'two monthly'}::Quarterly{'Quarterly'}::twice a year{'twice a year'}::yearly{'yearly'}", typeName: "select").save()
//        CoreParams invoiceFrequency = new CoreParams(varName: "INVOICE_FREQUENCE", paramsName: "Once{'Once'}::monthly{'monthly'}::two monthly{'two monthly'}::Quarterly{'Quarterly'}::twice a year{'twice a year'}::yearly{'yearly'}", typeName: "select").save()
//        //CoreParams salesType = new CoreParams(varName: "SALES_TYPE", paramsName: "Select sales type{''}::Retail{'Retail'}::Whole Sale{'Whole Sale'}", typeName: "select").save()
//        CoreParams salesType = new CoreParams(varName: "SALES_TYPE", paramsName: "Retail{'Retail'}::Whole Sale{'Whole Sale'}", typeName: "select").save()
//        //CoreParams productType = new CoreParams(varName: "PRODUCT_TYPE", paramsName: "Select Item Type{''}::Manufactered{'Manufactered'}::Purchased{'Purchased'}::sales{'Sales'}::Serveice{'Service'}", typeName: "select").save()
//        CoreParams productType =new CoreParams(varName: "PRODUCT_TYPE", paramsName:"Sales{'Sales'}::Purchase{'Purchase'}::Both{'Both'}", typeName: "select").save()
//        //CoreParams dateFormat = new CoreParams(varName: "DATEFORMAT", paramsName: "Select Date Format{''}::DDMMYYYY{'DDMMYYYY'}::DDMMYY{'DDMMYY'}::MMDDYYYY{'MMDDYYYY'}::MMDDYY{'MMDDYY'}::YYYYMMDD{'YYYYMMDD'}::YYMMDD{'YYMMDD'}::YYYYDDMM{'YYYYDDMM'}::YYDDMM{'YYDDMM'}", typeName: "select").save()
//        CoreParams dateFormat = new CoreParams(varName: "DATEFORMAT", paramsName: "DDMMYYYY{'DDMMYYYY'}::DDMMYY{'DDMMYY'}::MMDDYYYY{'MMDDYYYY'}::MMDDYY{'MMDDYY'}::YYYYMMDD{'YYYYMMDD'}::YYMMDD{'YYMMDD'}::YYYYDDMM{'YYYYDDMM'}::YYDDMM{'YYDDMM'}", typeName: "select").save()
//        //CoreParams amountDecimalPoint = new CoreParams(varName: "AMOUNT_DECIMAL_POINT", paramsName: "Select Decimal Point{''}::1{'1'}::2{'2'}::3{'3'}::4{'4'}", typeName: "select").save()
//        CoreParams amountDecimalPoint = new CoreParams(varName: "AMOUNT_DECIMAL_POINT", paramsName: "1{'1'}::2{'2'}::3{'3'}::4{'4'}", typeName: "select").save()
//        //CoreParams percentageDecimalPoint = new CoreParams(varName: "PERCENTAGE_DECIMAL_POINT", paramsName: "Select Percentage Decimal Point{''}::1{'1'}::2{'2'}::3{'3'}::4{'4'}", typeName: "select").save()
//        CoreParams percentageDecimalPoint = new CoreParams(varName: "PERCENTAGE_DECIMAL_POINT", paramsName: "1{'1'}::2{'2'}::3{'3'}::4{'4'}", typeName: "select").save()
//        //CoreParams quantityDecimalPoint = new CoreParams(varName: "QUANTITY_DECIMAL_POINT", paramsName: "Select Quantity Decimal Point{''}::1{'1'}::2{'2'}::3{'3'}::4{'4'}", typeName: "select").save()
//        CoreParams quantityDecimalPoint = new CoreParams(varName: "QUANTITY_DECIMAL_POINT", paramsName: "1{'1'}::2{'2'}::3{'3'}::4{'4'}", typeName: "select").save()
//        //CoreParams decimalRounding = new CoreParams(varName: "DECIMAL_ROUNDING", paramsName: "Select Decimal Rounding Type{''}::Floor Rounging{'Floor Rounding'}::Celing Rounding{'Celing Rounding'}", typeName: "select").save()
//        CoreParams decimalRounding = new CoreParams(varName: "DECIMAL_ROUNDING", paramsName: "Floor Rounging{'Floor Rounding'}::Celing Rounding{'Celing Rounding'}", typeName: "select").save()
//        //CoreParams decimalSeparator = new CoreParams(varName: "DECIMAL_SEPARATOR", paramsName: "Select Decimal Seperator{''}::DOT(.){'.'}::Comma(,){','}", typeName: "select").save()
//        CoreParams decimalSeparator = new CoreParams(varName: "DECIMAL_SEPARATOR", paramsName: "DOT(.){'.'}::Comma(,){','}", typeName: "select").save()
//       // CoreParams thousandSeparator = new CoreParams(varName: "THOUSAND_SEPARATOR", paramsName: "Select Thousand Seperator{''}:: Dot(.){'.'}::Comma(,){','}::Space( ){' '}", typeName: "select").save()
//        CoreParams thousandSeparator = new CoreParams(varName: "THOUSAND_SEPARATOR", paramsName: "Dot(.){'.'}::Comma(,){','}::Space( ){' '}", typeName: "select").save()
//       // CoreParams language = new CoreParams(varName: "LANGUAGE", paramsName: "Select Languaga{''}::English{'en'}::Dutch{'du'}", typeName: "select").save()
//        CoreParams language = new CoreParams(varName: "LANGUAGE", paramsName: "English{'en'}::Dutch{'du'}", typeName: "select").save()
//        //CoreParams dateSeparator = new CoreParams(varName: "DATE_SEPARATOR", paramsName: "Select Date Separator{''}::Slash(/){'/'}::Dot(.){'.'}::Hyphen(-){'-'}::Colon(:){':'}::Comma(','){','}::Space( ){' '}", typeName: "select").save()
//        CoreParams dateSeparator = new CoreParams(varName: "DATE_SEPARATOR", paramsName: "Slash(/){'/'}::Dot(.){'.'}::Hyphen(-){'-'}::Colon(:){':'}::Comma(','){','}::Space( ){' '}", typeName: "select").save()
//        //CoreParams paperSIze = new CoreParams(varName: "PAPER_SIZE", paramsName: "Select Paper Size{''}::Letter{'letter'}::A4{'A4'}", typeName: "select").save()
//        CoreParams paperSIze = new CoreParams(varName: "PAPER_SIZE", paramsName: "Letter{'letter'}::A4{'A4'}", typeName: "select").save()
//        //CoreParams bankAccountType = new CoreParams(varName: "BANK_ACCOUNT_TYPE", paramsName: "Select Bank Account Type{''}:: Savings Account{'Saving Account'}::Chequing Account{'Cheque Account'}::Credit Account{'Credit Account'}::Cash Account{'Cash Account'}", typeName: "select").save()
//        CoreParams bankAccountType = new CoreParams(varName: "BANK_ACCOUNT_TYPE", paramsName: "Savings Account{'Saving Account'}::Chequing Account{'Cheque Account'}::Credit Account{'Credit Account'}::Cash Account{'Cash Account'}", typeName: "select").save()
//        CoreParams bookingPeriod = new CoreParams(varName: "BOOKING_PERIOD", paramsName: "Monthly{'1'}:: Quarterly{'3'}::Half Yearly{'6'}::Yearly{'12'}", typeName: "select").save()
//        CoreParams frequencyInvoice = new CoreParams(varName: "FREQUENCY_INVOICE", paramsName: "Once{'once'}::Weekly{'weekly'}::Every Two Weeks{'half_month'}::Monthly{'monthly'}::Two Monthly{'two_monthly'}:: Quarterly{'three_monthly'}::Half Yearly{'six_monthly'}::Yearly{'yearly'}", typeName: "select").save()
//        CoreParams bookType = new CoreParams(varName: "BLOCK_TYPE", paramsName: "User Block{'normal'}::User IP Block{'user_ip_block'}::User IP Range Block{'user_ip_range_block'}::IP Block{'ip_block'}::MAC Block{'mac_block'}::Country Block{'country_block'}", typeName: "select").save()
//        CoreParams awareNotification = new CoreParams(varName: "AWARE_NOTIFICATION", paramsName: "Yes{'1'}::No{'0'}", typeName: "select").save()
//        CoreParams securityQuestion = new CoreParams(varName: "SECURITY_QUESTION", paramsName: "Yes{'1'}::No{'0'}", typeName: "select").save()
//        //CoreParams purchaseType = new CoreParams(varName: "PURCHASE_TYPE", paramsName: "Select parchase type{''}::Retail{'Retail'}::Whole Sale{'Whole Sale'}", typeName: "select").save()
//        CoreParams purchaseType = new CoreParams(varName: "PURCHASE_TYPE", paramsName: "Retail{'Retail'}::Whole Sale{'Whole Sale'}", typeName: "select").save()
//        CoreParams creditStatus = new CoreParams(varName: "CREDIT_STATUS", paramsName: "Good History{'Good History'}::In Liquidation{'In Liquidation'}::No More Work untill Payment Receive{'No More Work untill Payment Receive'}", typeName: "select").save()
//        CoreParams yesNo = new CoreParams(varName: "YESNO", paramsName: "Yes{'1'}::No{'0'}", typeName: "select").save()
//        CoreParams contactDealType = new CoreParams(varName: "CONTACT_DEAL_TYPE", paramsName: "Accont{'Account'}::Marketing{'Marketing'}", typeName: "select").save()
//        CoreParams budgetFrequency = new CoreParams(varName: "BUDGET_FREQUENCE", paramsName: "monthly{'monthly'}::two monthly{'two_monthly'}::Quarterly{'Quarterly'}::twice a year{'twice_a_year'}::yearly{'yearly'}", typeName: "select").save()
//        //CoreParams vendortype = new CoreParams(varName: "VENDOR_TYPE", paramsName: "Budget Item Name{'bn'}::Vendor Name{'vn'}::Shop Name{'sn'}::Receipt Payment{'rp'}", typeName: "select").save()
//        CoreParams vendortype = new CoreParams(varName: "VENDOR_TYPE", paramsName: "Budget Item Name{'bn'}::Vendor Name{'vn'}::Shop Name{'sn'}", typeName: "select").save()
//        CoreParams customertype = new CoreParams(varName: "CUSTOMER_TYPE", paramsName: "Budget Item Name{'bn'}::Customer Name{'cn'}", typeName: "select").save()
//
//        //COMPANY SETUP INITIAL DATA INSERT
//        def comSetup = new Sql(dataSource)
//        comSetup.execute("insert into company_setup (id, version, language, address_line1, address_line2, amount_decimal_point, chamber_commerce_no, city, company_date_format, company_full_name, company_short_name, country, currency_id, date_seperator, decimal_rounding_type, decimal_seprator, email_address, fax_no, income_tax_reservation, logo, mobile_no, moment_of_sending_invoice_id, number_of_booking_period, payment_term_id, percentage_decimal_point, phone_no, postal_address_line1, postal_city, postal_country, postal_state, postal_zip_code, quantity_decimal_point, report_page_size, second_email_address, show_glcode_in_report, show_itemcode_in_report, state, tax_category_id, tax_no, thousand_seperator, unforeseen_expense_reservation, vat_category_id, vat_no, website_address, zip_code) values (1, 1, 'en', 'test', 'test', 1, 1, 'Amsterdam', 'DDMMYYYY', 'BudgetView', 'BV', 'Netherlands', 2, ',', 2, '.', 'info@budgetview.nl', '9865', 1, 'logo.jpg', '456', 1, 0, 1, 1, '6465', 'test', 'Amsterdam', 'Netherlands', 'Amsterdam', '1216', 1, 'A4', 'admin@budgetview.nl', 'Yes', 'Yes', 'Amsterdam', 1, '123', ',', '0', 1, '123', 'budgetview.nl', '1216')")
//
//    }
//
//    def insertChartClassType() {
//
//        def db = new Sql(dataSource)
//        String chartClassTypeEntry = """insert  into `chart_class_type`(`id`,`version`,`class_type`,`status`) values
//        (1,0,'Assets',1),
//        (2,0,'Liabilities',1),
//        (3,0,'Equity',1),
//        (4,0,'Income',1),
//        (5,0,'Cost of Goods Sold',1),
//        (6,0,'Expanse',1),
//        (7,0,'Financial income and expenses',1)""";
//        db.execute(chartClassTypeEntry);
//
//        String chartClassEntry = """insert  into `chart_class`(`id`,`version`,`chart_class_type_id`,`name`,`status`) values
//        (1,1,1,'Assets',1),
//        (2,1,2,'Liabilities',1),
//        (3,1,4,'Income',1),
//        (4,1,5,'Cost of Goods Sold',1),
//        (5,1,6,'Expense',1),
//        (6,1,7,'Financial income and expenses',1)""";
//        db.execute(chartClassEntry);
//
//        String chartGroupEntry = """insert  into `chart_group`(`id`,`version`,`chart_class_id`,`name`,`status`) values
//        (1,0,1,'Tangible Fixed Assets',1),
//        (2,0,1,'Fiinancial Fixed Assets',1),
//        (3,0,1,'Intangible Assets',1),
//        (4,0,1,'Stock',1),
//        (5,0,1,'Receivables',1),
//        (6,0,1,'Accruals Assets',1),
//        (7,0,1,'Cash',1),
//        (8,0,2,'Long-term Debt',1),
//        (9,0,2,'Current Liabilities',1),
//        (10,0,2,'Accruals Liabilities',1),
//        (11,0,2,'Provisions',1),
//        (12,0,2,'Equity',1),
//        (13,1,2,'Cross Booking',1),
//        (14,0,4,'Cost Price',1),
//        (15,0,5,'Income Insurance',1),
//        (16,0,5,'Food, Drinks, Gifts and Fun',1),
//        (17,0,5,'Education and Training',1),
//        (18,0,5,'Travel, Transport and Parking',1),
//        (19,0,5,'Cost of Staff',1),
//        (20,0,5,'Office - Rent and Maintenance',1),
//        (21,0,5,'Subscriptions & Other Insurance',1),
//        (22,0,5,'Workplace and Tools',1),
//        (23,0,5,'Marketing, Advertising & Sales',1),
//        (24,0,5,'Depreciation',1),
//        (25,0,5,'Financial Services & Advice',1),
//        (26,0,5,'Other Operating Cost',1),
//        (27,0,6,'Interest',1),
//        (28,0,6,'Result',1),
//        (29,0,3,'Turnover',1)""";
//        db.execute(chartGroupEntry);
//
//        String chartMasterEntry = """insert  into `chart_master`(`id`,`version`,`account_code`,`account_code2`,`account_name`,`chart_group_id`,`ordering`,`status`,`vat_category_id`) values
//        (1,0,'1100',NULL,'Bank',7,1,1,1),
//        (2,0,'1200',NULL,'Cash',7,1,1,1),
//        (3,0,'1300',NULL,'Accounts Receivable',5,1,1,1),
//        (4,0,'1350',NULL,'Other Receivables',5,1,1,1),
//        (5,0,'1400',NULL,'Current Account Owner - Private Deposits',5,1,1,1),
//        (6,0,'1450',NULL,'Investments',5,1,1,1),
//        (7,0,'1460',NULL,'Unrealized Exchange Gains on Foreign Currency',5,1,1,1),
//        (8,0,'1500',NULL,'Accrued Assets',6,1,1,1),
//        (9,0,'1510',NULL,'Unbilled Turnover',6,1,1,1),
//        (10,0,'1520',NULL,'Prepaid Expenses',6,1,1,1),
//        (11,0,'1530',NULL,'Accrued Interest',6,1,1,1),
//        (12,0,'1540',NULL,'Other Receivables From Financial Closure',6,1,1,1),
//        (13,0,'1585',NULL,'To Receive Tax and Social Security Contributions',5,1,1,1),
//        (14,0,'2105',NULL,'VAT Receivable High ',5,1,1,1),
//        (15,0,'2115',NULL,'VAT Receivable Low',5,1,1,1),
//        (16,0,'3000',NULL,'Stock',4,1,1,1),
//        (17,0,'0400',NULL,'Goodwill',3,1,1,1),
//        (18,0,'0405',NULL,'Amortization of Goodwill',3,1,1,1),
//        (19,0,'0410',NULL,'Patents, Patents and Rights / Liberties',3,1,1,1),
//        (20,0,'0420',NULL,'Research and Development Costs',3,1,1,1),
//        (21,0,'0425',NULL,'Depreciation Research and Development Costs',3,1,1,1),
//        (22,0,'0430',NULL,'Other Intangible Assets',3,1,1,1),
//        (23,0,'0300',NULL,'Deposits',2,1,1,1),
//        (24,0,'0310',NULL,'Participations',2,1,1,1),
//        (25,0,'0320',NULL,'Strategic Long-Term Investments',2,1,1,1),
//        (26,0,'0330',NULL,'Long-Term Loans (Mortgage, etc.)',2,1,1,1),
//        (27,0,'0340',NULL,'Other Financial Fixed Assets',2,1,1,1),
//        (28,0,'0200',NULL,'Machinery and Equipment',1,1,1,1),
//        (29,0,'0201',NULL,'Depreciation of Machinery and Equipment',1,1,1,1),
//        (30,0,'0210',NULL,'Computers and Computer Equipment',1,1,1,1),
//        (31,0,'0211',NULL,'Depreciation of Computers and Computer Equipment',1,1,1,1),
//        (32,0,'0220',NULL,'Car and Vehicle',1,1,1,1),
//        (33,0,'0221',NULL,'Depreciation Cars and Vehicles',1,1,1,1),
//        (34,0,'0230',NULL,'Inventory - Furniture etc.',1,1,1,1),
//        (35,0,'0231',NULL,'Amortization of Inventory',1,1,1,1),
//        (36,0,'0240',NULL,'Buildings and Land',1,1,1,1),
//        (37,0,'0241',NULL,'Depreciation of Buildings and Land',1,1,1,1),
//        (38,0,'0250',NULL,'Other Tangible Fixed Assets',1,1,1,1),
//        (39,0,'0251',NULL,'Depreciation of Other Tangible Fixed Assets',1,1,1,1),
//        (40,0,'1580',NULL,'Accrued Payments',10,1,1,1),
//        (41,0,'1590',NULL,'Reservation Accountant and Bookkeeper',10,1,1,1),
//        (42,0,'1595',NULL,'Provision Holiday Allowance',10,1,1,1),
//        (43,0,'2200',NULL,'Cross Booking',13,1,1,1),
//        (44,0,'2300',NULL,'Unclassified Bank Transactions',13,1,1,1),
//        (45,0,'0600',NULL,'Long-Term Loans',8,1,1,1),
//        (46,0,'0700',NULL,'Pension Provisions',11,1,1,1),
//        (47,0,'0710',NULL,'Other Provisions',11,1,1,1),
//        (48,0,'1410',NULL,'Bank Overdrafts Owner - Private Recordings',9,1,1,1),
//        (49,0,'1560',NULL,'Accrued Payroll',9,1,1,1),
//        (50,0,'1570',NULL,'Accrued Other Taxes and Social Security Contributions',9,1,1,1),
//        (51,0,'1600',NULL,'Creditors',9,1,1,1),
//        (52,0,'1650',NULL,'Short Term Loans',9,1,1,1),
//        (53,0,'1655',NULL,'Other Payables Business',9,1,1,1),
//        (54,0,'2100',NULL,'Tax Payable High',9,1,1,1),
//        (55,0,'2110',NULL,'Tax Payable Low',9,1,1,1),
//        (56,0,'0110',NULL,'Reservations',12,1,1,1),
//        (57,0,'0100',NULL,'Total Assets Of The Company',12,1,1,1),
//        (58,0,'0120',NULL,'Profit For The Year',12,1,1,1),
//        (59,0,'1550',NULL,'Sales Tax Payable or Receivable',9,1,1,1),
//        (60,0,'8000',NULL,'Sales Tax Services High',29,1,1,1),
//        (61,0,'8100',NULL,'Sales Tax Goods High',29,1,1,1),
//        (62,0,'8200',NULL,'Sales Taxes Other High',29,1,1,1),
//        (63,0,'8800',NULL,'Sales Tax Low',29,1,1,1),
//        (64,0,'8900',NULL,'Sales Tax Zero',29,1,1,1),
//        (65,0,'4960',NULL,'Interest Paid and Banking Cost',27,1,1,1),
//        (66,0,'4970',NULL,'Interest Paid on Outstanding Invoices',27,1,1,1),
//        (67,0,'4980',NULL,'Interest Paid on Current Account Owner',27,1,1,1),
//        (68,0,'4985',NULL,'Other Interest Paid',27,1,1,1),
//        (69,0,'9900',NULL,'Interest Received',28,1,1,1),
//        (70,0,'9910',NULL,'Financial Results for The Year',28,1,1,1),
//        (71,0,'9990',NULL,'Other Results',28,1,1,1),
//        (72,0,'9999',NULL,'Nonrecurring Results',28,1,1,1),
//        (73,0,'4000',NULL,'Annuity Premiums *',15,1,1,1),
//        (74,0,'4010',NULL,'Disability Insurance (AOV)',15,1,1,1),
//        (75,0,'4020',NULL,'Reservation Fiscal Retirement Reserve (FOR) *',15,1,1,1),
//        (76,0,'4030',NULL,'Other Income Insurance',15,1,1,1),
//        (77,0,'4100',NULL,'Eating, Drinking, Shopping and Gifts *',16,1,1,1),
//        (78,0,'4110',NULL,'Conferences, Seminars and Study *',16,1,1,1),
//        (79,0,'4120',NULL,'Other Entertainment *',16,1,1,1),
//        (80,0,'4150',NULL,'Workshops, Training and Education *',17,1,1,1),
//        (81,0,'4160',NULL,'Textbooks and Materials *',17,1,1,1),
//        (82,0,'4170',NULL,'Other Education and Training *',17,1,1,1),
//        (83,0,'4270',NULL,'Other Travel and Subsistence',18,1,1,1),
//        (84,0,'4300',NULL,'Net Wages and Salaries',19,1,1,1),
//        (85,0,'4310',NULL,'Social Security Staff - Payment of Income Tax',19,1,1,1),
//        (86,0,'4320',NULL,'Holiday Allowance Staff',19,1,1,1),
//        (87,0,'4330',NULL,'Staff Travel Expenses',19,1,1,1),
//        (88,0,'4340',NULL,'Pension Contributions Staff',19,1,1,1),
//        (89,0,'4350',NULL,'Other Costs Staff',19,1,1,1),
//        (90,0,'4400',NULL,'Rent',20,1,1,1),
//        (91,0,'4405',NULL,'Service Charge & Cleaning',20,1,1,1),
//        (92,0,'4410',NULL,'Electricity and Gas',20,1,1,1),
//        (93,0,'4415',NULL,'Maintenance, Repairs and Repainting',20,1,1,1),
//        (94,0,'4425',NULL,'Other Office - Rent and Maintenance',20,1,1,1),
//        (95,0,'4450',NULL,'Subscriptions and Membership Fees',21,1,1,1),
//        (96,0,'4455',NULL,'Other Insurance',21,1,1,1),
//        (97,0,'4500',NULL,'Workplace and Tools',22,1,1,1),
//        (98,0,'4520',NULL,'Telephone & Internet',22,1,1,1),
//        (99,0,'4540',NULL,'Postage Cost',22,1,1,1),
//        (100,0,'4600',NULL,'Marketing, Advertising & Marketing Expenses',23,1,1,1),
//        (101,0,'4610',NULL,'Printing Inc. Business Cards and Stationery, Leaflets',23,1,1,1),
//        (102,0,'4630',NULL,'Sponsorship',23,1,1,1),
//        (103,0,'4650',NULL,'Provision for Doubtful Debts',23,1,1,1),
//        (104,0,'4700',NULL,'Car',18,1,1,1),
//        (105,0,'4705',NULL,'Fuel',18,1,1,1),
//        (106,0,'4710',NULL,'Travel Expenses Public Transport Commuting *',18,1,1,1),
//        (107,0,'4740',NULL,'Transport Costs (Train, Bus, Taxi, etc.)',18,1,1,1),
//        (108,0,'4760',NULL,'Car Expenses Reimbursement',18,1,1,1),
//        (109,0,'4800',NULL,'Administrator & Auditor',25,1,1,1),
//        (110,0,'4820',NULL,'Bailiffs and Debt Collection',25,1,1,1),
//        (111,0,'4840',NULL,'Consultants, Lawyers and Other Professional Fees',25,1,1,1),
//        (112,0,'4890',NULL,'Small Additions',26,1,1,1),
//        (113,0,'4895',NULL,'Other Operating Cost',26,1,1,1),
//        (114,0,'4900',NULL,'Depreciation',24,1,1,1),
//        (115,0,'7000',NULL,'Cost - Purchase Materials For Sales',14,1,1,1),
//        (116,0,'7100',NULL,'Cost - Hiring of Third Parties',14,1,1,1),
//        (117,0,'7200',NULL,'Cost - Purchase Other Serving Turnover',14,1,1,1)""";
//        db.execute(chartMasterEntry);
//
//    }
//
//    /*def debitCreditGlSetup(){
//        def db2 = new Sql(dataSource)
//        String debitCreditGlSetup = """insert  into `debit_credit_gl_setup`(`id`,`version`,`creditor_gl_code`,`debitor_gl_code`,'reconcilation_gl_code','updated_by','updated_date') values
//        (1,0,'1600','0300','1100','1','2013-06-13 00:00:00')""";
//        db2.execute(debitCreditGlSetup);
//    }*/
//
//    def initUsers = {
//        def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(failOnError: true)
//        def adminUser = User.findByUsername('admin') ?: new User(
//                username: 'admin',
//                password: 'admin',
//                email: 'farhadbd14@yahoo.com',
//                firstName: 'Farhad',
//                lastName: 'Hossain',
//                enabled: true).save(failOnError: true)
//
//        if (!adminUser.authorities.contains(adminRole)) {
//            UserRole.create(adminUser, adminRole,true)
//        }
//
//        def accountantRole = Role.findByAuthority('ROLE_ACCOUNTANT') ?: new Role(authority: 'ROLE_ACCOUNTANT').save(failOnError: true)
//        def accountantUser = User.findByUsername('accountant') ?: new User(
//                username: 'accountant',
//                password: 'accountant',
//                email: 'farhadbd14@yahoo.com',
//                firstName: 'Farhad',
//                lastName: 'Hossain',
//                enabled: true).save(failOnError: true)
//
//        if (!accountantUser.authorities.contains(accountantRole)) {
//            UserRole.create accountantUser, accountantRole
//        }
//
//
//    }
//
//    private void loginAsAdmin() {
//        // have to be authenticated as an admin to create ACLs
//        SCH.context.authentication = new UsernamePasswordAuthenticationToken(
//                'admin', 'admin',
//                AuthorityUtils.createAuthorityList('ROLE_ADMIN'))
//    }
//
//    private void grantPermissions() {
//        def chartClasses = []
//        100.times {
//            long id = it + 1
//            def chartClass = new ChartClass(name: "chartclass$id", status: 1).save()
//            chartClasses << chartClass
//            try {
//                aclService.createAcl(objectIdentityRetrievalStrategy.getObjectIdentity(chartClass))
//            } catch (Exception e) {
//                e.printStackTrace()
//            }
//        }
//
//        // grant user 1 admin on 11,12 and read on 1-67
//        aclUtilService.addPermission chartClasses[10], 'admin', ADMINISTRATION
//        aclUtilService.addPermission chartClasses[11], 'admin', ADMINISTRATION
//        67.times {
//            aclUtilService.addPermission chartClasses[it], 'admin', READ
//        }
//
//        // grant user 2 read on 1-5, write on 5
//        5.times {
//            aclUtilService.addPermission chartClasses[it], 'accountant', READ
//        }
//        aclUtilService.addPermission chartClasses[4], 'accountant', WRITE
//
//        // user 3 has no grants
//
//        // grant admin admin on all
//        for (chartClass in chartClasses) {
//            aclUtilService.addPermission chartClass, 'admin', ADMINISTRATION
//        }
//
//        // grant user 1 ownership on 1,2 to allow the user to grant
//        aclUtilService.changeOwner chartClasses[0], 'admin'
//        aclUtilService.changeOwner chartClasses[1], 'admin'
//    }
//
//
//    def destroy = {
//    }
//
//
//}
