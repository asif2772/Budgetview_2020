<%@ page import="bv.BusinessCompany; bv.PrivateAndReservationBudgetService; bv.UserController; bv.UserTagLib; auth.User; bv.CustomerMaster; bv.VendorMaster; bv.BudgetItemIncome; bv.BudgetItemExpense; bv.CompanySetup" %>
<%@ page import="bv.BudgetViewDatabaseService; bv.FiscalYear; bv.UserLog; auth.User; org.springframework.security.core.context.SecurityContextHolder; org.springframework.security.core.Authentication; org.springframework.security.core.context.SecurityContext" %>

<%
    def CompanySetup = 1;
    SecurityContext ctx = SecurityContextHolder.getContext()
    Authentication auth = ctx.getAuthentication()
    String username = auth.getName()

    def roleArray = auth.getAuthorities();
    def userRole = roleArray[0];
    User user = User.findByUsername(username)
    Integer authUserId = user.getAt('id')
    def businessCompanyId = user.getAt('businessCompanyId')

    def permittedBusinessCompanyId = session.permittedBusinessCompanyId
    def contextPath = request.getServletContext().getContextPath()

    if(session.permittedBusinessCompanyId == null && session.businessCompany == null){
        session.permittedBusinessCompanyId = businessCompanyId
        session.businessCompany = username
    }
%>

<script>

    function showSkippedTrans() {

        $.ajax({
            type: 'GET',
            url: '${contextPath}/BankReconciliation/ifExistSkippedTrans',
            success: function (response) {
                if(response != "") {
                    swal("${message(code:'com.showSkippedTransaction.message')}", {
                        icon: "warning",
                        buttons: {
                            skipped: {
                                customClass: 'swal-back',
                                text: "${message(code:'com.buttonLabel.showAll')}",
                                value: "skipped",
                            },
                            catch: {
                                text: "${message(code:'com.common.ok')}",
                                value: "catch",
                            },
                        },
                    }).then((value) => {
                        switch (value) {
                        case "skipped":
                            window.location.href = '${contextPath}/BankReconciliation/manualReconciliation';
                            break;

                        case "catch":
                            window.location.href = '${contextPath}/BankReconciliation/manualReconciliation?showAll='+'showAll';
                            break;

                        }
                    });

                } else {
                    window.location.href = '${contextPath}/BankReconciliation/manualReconciliation';
                }
            }
        });
    }

</script>


<div>
<div class="blue">
<ul id="mega-menu-1" class="mega-menu">
<% if (CompanySetup) { %>
<li><g:link url="[action: 'index', controller: 'dashboard']"><g:message code="bv.menu.Home" default="Home"/></g:link></li>
<li>
    <g:link url="#"><g:message code="bv.menu.bank" default="Bank"/></g:link>
    <ul>
        <li>
            <g:link url="[controller: 'BankReconciliation', action: 'reconciliation']"><g:message
                    code="bv.menu.Reconciliation" default="Reconciliation"/></g:link>
        </li>
        <li>
            <g:link onclick="return showSkippedTrans()" url="javascript:void(0)">
                <g:message code="bv.menu.ManualReconciliation" default="Manual Reconciliation"/>
            </g:link>
        </li>
        <li>
            <g:link url="[controller: 'UndoReconciliation', action: 'index']">
                <g:message code="bv.menu.UndoReconciliation" default="Undo Reconciliation"/>
            </g:link>
        </li>
        <li>
            <g:link url="[controller: 'BankReconciliation', action: 'bookingRules']">
                <g:message code="bv.menu.bookingRules.label" default="Booking Rules"/>
            </g:link>
        </li>
        <li>
            <g:link url="[controller: 'BankReconciliation', action: 'automaticBankProcess']">
                <g:message code="bv.menu.AutomaticReconciliation" default="Automatic Reconciliation"/>
            </g:link>
        </li>
    </ul>
</li>    <!--end Bank-->

%{--<li>
    <g:link url="#"><g:message code="bv.menu.budgets" default="Manage Budget"/></g:link>
    <ul>
        <li>
            <g:link url="[controller: 'budgetItemIncomeDetails', action: 'index']"><g:message code="bv.menu.IncomeBudget" default="Income Budget"/></g:link>
        </li>
        <li>
            <g:link url="[controller: 'budgetItemExpenseDetails', action: 'index']"><g:message code="bv.menu.ExpenseBudget" default="Expense Budget"/></g:link>
        </li>
        <li>
            <g:link url="[controller: 'budgetItemReservation', action: 'index']"><g:message code="bv.menu.ReservationBudget" default="Reservation Budget"/></g:link>
        </li>
        <li>
            <g:link url="[controller: 'budgetItemPrivate', action: 'index']"><g:message code="bv.menu.PrivateBudget" default="Private Budget"/></g:link>
        </li>
    </ul>
</li>--}%   <!---end Manage Budget-->

<li>
    <g:link url="#"><g:message code="bv.menu.relations" default="Relations"/></g:link>
    <ul>
        <li>
            <g:link url="[controller: 'customerMaster', action: 'index']"><g:message code="bv.menu.Customers" default="Customers"/></g:link>
        </li>
        <li>
            <g:link url="[controller: 'vendorMaster', action: 'index']"><g:message code="bv.menu.Vendors" default="Vendors"/></g:link>
        </li>
        <li>
            <g:link url="[controller: 'reservationBudgetMaster', action: 'index']"><g:message code="bv.menu.ReservationBudget" default="Reservation Budget"/></g:link>
        </li>
        <li>
            <g:link url="[controller: 'privateBudgetMaster', action: 'index']"><g:message code="bv.menu.PrivateBudget" default="Private Budget"/></g:link>
        </li>
    </ul>
</li>   <!--end Relations-->

<li>
    <g:link url="#"><g:message code="bv.menu.reports" default="Reports"/></g:link>
    <ul>

        <li>
            <g:link url="#"><g:message code="bv.menu.bookings" default="Bookings"/></g:link>
            <ul>
                <li><g:link url="[controller: 'reportIncomeBudget', action: 'listofincomebook']"><g:message code="bv.menu.ListIncomeBooked" default="List of Income Booked"/></g:link></li>
                <li><g:link url="[controller: 'reportExpenseBudget', action: 'listofexpenseinvoice']"><g:message code="bv.menu.ListExpenseBooked" default="List of Expense Booked"/></g:link></li>
                <li><g:link url="[controller: 'reportJournalEntry', action: 'list']"><g:message code="bv.menu.ListJournalEntry" default="List of Journal Entry"/></g:link></li>
                <li><g:link url="[controller: 'reportReconciliation', action: 'listOfManualReconciliationEntry']"><g:message code="bv.menu.ListManualBankStatement" default="List of All Bank Statement"/></g:link></li>
            </ul>
        </li>

        <li>
            <g:link url="#"><g:message code="bv.menu.financialReports" default="Financial Reports"/></g:link>
            <ul>
                <% if(userRole == "ROLE_FREE_VERSION_USER"){%>
                <li><g:link url="[controller: 'reports', action: 'freeVersionMessage']"><g:message code="bv.menu.IncomeStatement" default="Income Statement"/></g:link></li>
                <li><g:link url="[controller: 'reports', action: 'freeVersionMessage']"><g:message code="bv.menu.BalanceSheet" default="Balance Sheet"/></g:link></li>
                <% } else { %>
                <li><g:link url="[controller: 'reports', action: 'incomeStatement']"><g:message code="bv.menu.IncomeStatement" default="Income Statement"/></g:link></li>
                <li><g:link url="[controller: 'reports', action: 'balanceSheetNew']"><g:message code="bv.menu.BalanceSheet" default="Balance Sheet"/></g:link></li>
                <% } %>

                <li><g:link url="[controller: 'reports', action: 'vatReport']"><g:message code="bv.menu.VatReport" default="Vat Report"/></g:link></li>
                <li><g:link url="[controller: 'reports', action: 'agingReport']"><g:message code="bv.menu.AgingReport" default="Aging Report"/></g:link></li>
                <li><g:link url="[controller: 'reports', action: 'privateBudgetReport']"><g:message code="bv.menu.privateBudgetReport" default="Private Budget Report"/></g:link></li>
                <li><g:link url="[controller: 'reports', action: 'sendReminder']"><g:message code="bv.menu.sendReminder" default="Send Reminder"/></g:link></li>
                %{--<li><g:link url="[controller: 'reports', action: 'privateBudget']"><g:message code="bv.menu.privateBudgetReport" default="Private Budget Report"/></g:link></li>--}%
            </ul>
        </li>

    </ul>
</li>   <!--end Reports-->

<li>
    <g:link url="#"><g:message code="bv.menu.bookkeeper" default="Bookkeeper"/></g:link>
    <ul>
        <li>
            <g:link url="#"><g:message code="bv.menu.advancedEntry" default="Advanced Entry"/></g:link>
            <ul>
                <li>
                    <g:link url="[controller: 'JournalEntry', action: 'index']"><g:message code="bv.menu.JournalEntry" default="Journal Entry"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'ManualEntryOfBankStatement', action: 'list']"><g:message code="bv.menu.ManualEntry" default="Manual Entry"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'ImportInvoice', action: 'ImportInvoice']"><g:message code="bv.menu.ImportExpenseInvoice" default="Import Expense Invoice"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'QuickEntryInvoice', action: 'quickEntryForm']"><g:message code="bv.menu.quickEntry" default="Quick Entry"/></g:link>
                </li>
            <li>
                <g:link url="[controller: 'ImportBudget', action: 'index']"><g:message code="bv.menu.ImportBudget" default="Import Budget"/></g:link>
            </li>
            <li>
                <a href="${contextPath}/bankReconciliation/manualReconciliation?formType=3"><g:message code="bv.BookingWithoutInvoiceReceipt.title.label" default="Booking without invoice or receipt"/></a>
                %{--<g:link url="[controller: 'bookingWithoutInvoiceReceipt', action: 'list']"><g:message code="##bv.menu.ImportExpenseInvoice" default="Booking without invoice or receipt"/></g:link>--}%
            </li>
            </ul>
        </li>

        <li>
            <g:link url="#"><g:message code="bv.menu.reports" default="Reports"/></g:link>
            <ul>
                <% if(userRole == "ROLE_FREE_VERSION_USER") {%>
                <li><g:link url="[controller: 'reports', action: 'glReport']"><g:message code="bv.menu.GLReport" default="GL Report"/></g:link></li>
                <li><g:link url="[controller: 'reports', action: 'freeVersionMessage']"><g:message code="bv.menu.TrialBalanceReport" default="Trial Balance Report"/></g:link></li>
                <% } else { %>
                <li><g:link url="[controller: 'reports', action: 'glReport']"><g:message code="bv.menu.GLReport" default="GL Report"/></g:link></li>
                <li><g:link url="[controller: 'reports', action: 'trialBalanceReport']"><g:message code="bv.menu.TrialBalanceReport" default="Trial Balance Report"/></g:link></li>
                <% } %>
                %{--<% if(userRole != "ROLE_FREE_VERSION_USER"){%>--}%
                %{--<li><g:link url="[controller: 'reports', action: 'trialBalanceReport']"><g:message code="bv.menu.TrialBalanceReport" default="Trial Balance Report"/></g:link></li>--}%
                %{--<%}%>--}%
                <li><g:link url="[controller: 'reports', action: 'exportAuditFile']"><g:message code="bv.menu.xmlAuditFile" default="XML Audit File"/></g:link></li>
                <li><g:link url="[controller: 'reports', action: 'exportSummaryView']"><g:message code="bv.menu.excelSummaryView" default="Summary Report"/></g:link></li>
                <li><g:link url="[controller: 'reportReconciliation', action: 'unprocessedBankTransaction']"><g:message code="bv.reports.unprocessed.bank.transaction.label" default="Unprocessed bank Transaction"/></g:link></li>
            </ul>
        </li>

    </ul>
</li>   <!--end Bookkeeper-->

<li>
    <g:link url="#"><g:message code="bv.menu.settings" default="Settings"/></g:link>
    <ul>
        <li>
            <g:link url="#"><g:message code="bv.menu.Company" default="COMPANY"/></g:link>
            <ul>
                <li>
                    <g:link url="[controller: 'companySetup', action: 'index']"><g:message code="bv.menu.CompanySetup" default="Company Setup"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'companyBankAccounts', action: 'index']"><g:message code="bv.menu.CompanyBankAccount" default="Your Bank Account"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'fiscalYear', action: 'index']"><g:message code="bv.menu.FiscalYear" default="Fiscal Year"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'uploadPdf', action: 'uploadPdf' ]"><g:message code="bv.uploadPDF.label" default="Upload PDF"/></g:link>
                </li>
            </ul>
        </li>

        <li>
            <g:link url="#"><g:message code="bv.menu.SetUp" default="SETUP"/></g:link>
            <ul>
                <li>
                    <g:link url="[controller: 'paymentTerms', action: 'index']"><g:message code="bv.menu.PaymentTerms" default="Payment Terms"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'bankAccountType', action: 'index']"><g:message code="bv.menu.BankAccountType" default="Bank Account Type"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'countries', action: 'index']"><g:message code="bv.menu.Countries" default="Countries"/></g:link>
                </li>
            </ul>
        </li>

        <li>
            <g:link url="#"><g:message code="bv.menu.Security" default="SECURITY"/></g:link>
            <ul>
                %{--<li>
                    <g:link url="[controller: 'reports', action: 'dbDump' ]"><g:message code="bv.menu.DBDump" default="Database Backup"/></g:link>
                </li>--}%
                <li>
                    <g:link url="[controller: 'user', action: 'index']"><g:message code="bv.menu.Users" default="Users"/></g:link>
                </li>
                <li>
                    <g:link controller="user" action="updatePassword" params="[id: sec.loggedInUserInfo(field: 'id'), st: 1]">
                        <g:message code="bv.menu.ChangePassword" default="Change Password"/></g:link>
                </li>
                <li>
                    <g:link controller="user" action="managePermission" params="[id: sec.loggedInUserInfo(field: 'id'), st: 1]">
                        <g:message code="bv.menu.ManagePermission" default="Manage Permission"/></g:link>
                </li>
            <%if(authUserId==1){%>
                <li>
                    <g:link url="[controller: 'BusinessCompany', action: 'index']"><g:message code="bv.menu.BusinessCompany" default="Business Company"/></g:link>
                </li>
            <%}%>
            </ul>
        </li>

    </ul>
</li>   <!--end Settings-->
%{--<% if(userRole == "ROLE_FREE_VERSION_USER"){%>--}%
    <% if(userRole != "ROLE_ACCOUNTANT" && userRole != "ROLE_FREE_VERSION_USER"){%>
    <li>
        <g:link url="#"><g:message code="bv.menu.extraSettings" default="Extra Settings"/></g:link>
        <ul>
            <li>
                <g:link url="#"><g:message code="bv.menu.GLACCOUNT" default="GL ACCOUNT"/></g:link>
                <ul>
                    <li>
                        <g:link url="[controller: 'chartClass', action: 'index']"><g:message code="bv.menu.ChartClass" default="Chart Class"/></g:link>
                    </li>

                    <li>
                        <g:link url="[controller: 'chartGroup', action: 'index']"><g:message code="bv.menu.ChartGroup" default="Chart Group"/></g:link>
                    </li>
                    <li>
                        <g:link url="[controller: 'chartMaster', action: 'index']"><g:message code="bv.menu.ChartMaster" default="Chart Master"/></g:link>
                    </li>
                </ul>
            </li>

            %{--<li>
                <g:link url="#"><g:message code="bv.menu.PRODUCT" default="PRODUCT"/></g:link>
                <ul>
                    <li>
                        <g:link url="[controller: 'productCategory', action: 'index']"><g:message code="bv.menu.ProductCategory" default="Product Category"/></g:link>
                    </li>
                    <li>
                        <g:link url="[controller: 'productMaster', action: 'index']"><g:message code="bv.menu.ProductMaster" default="Product Master"/></g:link>
                    </li>
                    <li>
                        <g:link url="[controller: 'productUnit', action: 'index']"><g:message code="bv.menu.ProductUnit" default="Product Unit"/></g:link>
                    </li>
                </ul>
            </li> --}%  <!--end PRODUCT-->

            <li>
                <g:link url="#"><g:message code="bv.menu.ADVANCEsETTINGS" default="ADVANCE SETTINGS"/></g:link>
                <ul>
                    %{--<li>
                        <g:link url="[controller: 'currencies', action: 'index']"><g:message code="bv.menu.Currencies" default="Currencies"/></g:link>
                    </li>
                    <li>
                        <g:link url="[controller: 'exchangeRates', action: 'index']"><g:message code="bv.menu.ExchangeRates" default="Exchange Rates"/></g:link>
                    </li>--}%
                    <li>
                        <g:link url="[controller: 'vatCategory', action: 'index']"><g:message code="bv.menu.VATCategory" default="VAT Category"/></g:link>
                    </li>
                    <li>
                        <g:link url="[controller: 'reconciliationBookingType', action: 'index']"><g:message code="bv.menu.ReconciliationBookingType" default="Reconciliation Booking Type"/></g:link>
                    </li>
                    <li>
                        <g:link url="[controller: 'debitCreditGlSetup', action: 'index']"><g:message code="bv.menu.DebtorCreditorSetup" default="Default GL Setup"/></g:link>
                    </li>
                    <li>
                        <g:link url="[controller: 'IPRestriction', action: 'index']"><g:message code="bv.menu.IPRestriction" default="IP Restriction"/></g:link>
                    </li>
                    <li>
                        <g:link url="[controller: 'companyBankGlRelation', action: 'index']"><g:message code="bv.menu.CompanyGLRelation" default="Company GL Relation"/></g:link>
                    </li>
                    <li>
                        <g:link url="[controller: 'user', action: 'addAndRemovePermission']"><g:message code="bv.user.addAndRemovePermission.label" default="Add and Remove Rermission"/></g:link>
                    </li>
                    <li>
                        <g:link controller="reportExpenseBudget" action="listBatchPayment" params="[showDeleted: 'No']"><g:message code="bv.menu.batchPayment.label" default="Batch Payment"/></g:link>
                    </li>
                    <li>
                        <g:link url="[controller: 'wezpIntegration', action: 'index']"><g:message code="bv.menu.wezpIntegration.label" default="Wezp Integration"/></g:link>
                    </li>
               </ul>
            </li>

        </ul>
    </li>   <!--end Extra Settings-->
<%}%>


<li>
    %{--<g:link url="#"><sec:loggedInUserInfo field="username"/></g:link>--}%

    <%if (permittedBusinessCompanyId == null) {%>
        <g:link url="#"><sec:loggedInUserInfo field="username"/></g:link>

    <% } else  {
         BusinessCompany businessCompany = BusinessCompany.findById(permittedBusinessCompanyId)
    %>
        <g:link url="#">${businessCompany.name}</g:link>
    <%}%>

    <ul>
        <%
                def permittedDbArr = new UserTagLib().gettingPermittedDBInfo(sec.loggedInUserInfo(field: 'id'));
                if(permittedDbArr.size()){
        %>
        <li>
            <g:link url="#">Permitted Database</g:link>
            <ul>
                <%
                        if( permittedBusinessCompanyId == businessCompanyId || permittedBusinessCompanyId == null){%>
                <li>
                    <g:link controller="user" action="selectedDataBase" style="color:#000000;font-weight: bold;"
                            params="[businessCompanyId: businessCompanyId, userId: sec.loggedInUserInfo(field: 'id')]">
                        <g:message code="bv.menu.ChangePassword1" default="${sec.loggedInUserInfo(field: 'username')}"/></g:link>
                </li>
                <%}else{%>
                <li>
                    <g:link controller="user" action="selectedDataBase" params="[businessCompanyId: businessCompanyId, userId: sec.loggedInUserInfo(field: 'id')]">
                        <g:message code="bv.menu.ChangePassword1" default="${sec.loggedInUserInfo(field: 'username')}"/></g:link>
                </li>
                <%}%>

                <%

                        if(permittedDbArr.size()>0){
                            permittedDbArr.each { phn ->
                                if(permittedBusinessCompanyId == phn[2] ){
                %>

                <li>
                    <g:link controller="user" action="selectedDataBase" style="color:#000000;font-weight: bold;"
                            params="[businessCompanyId: phn[2], userId: phn[3]]"><g:message code="bv.menu.ChangePassword1" default="${phn[7]}"/></g:link>
                </li>
                <%}else{%>
                <li>
                    <g:link controller="user" action="selectedDataBase" params="[businessCompanyId: phn[2], userId: phn[3]]">
                        <g:message code="bv.menu.ChangePassword1" default="${phn[7]}"/></g:link>
                </li>
                <%}%>
                <% } } %>
            </ul>
        </li>
        <%}%>

        <li>
            <g:link url="#"><g:message code="bv.manageUser" default="MANAGE USER"/></g:link>
            <ul>
                <li><g:link controller="logout" action="index"><g:message code="bv.menu.Logout" default="Logout"/></g:link></li>
            </ul>
        </li>

        </ul>
</li>   <!--End Login Name-->





%{--// old code and its logic--}%
%{--<% if (CompanySetup) { %>
<li>
    <g:link url="#"><g:message code="bv.menu.bookings" default="Bookings"/></g:link>
    <ul>
        <li><g:link url="[controller: 'reportIncomeBudget', action: 'listofincomebook']"><g:message code="bv.menu.ListIncomeBooked" default="List of Income Booked"/></g:link></li>
        <li><g:link url="[controller: 'reportExpenseBudget', action: 'listofexpenseinvoice']"><g:message code="bv.menu.ListExpenseBooked" default="List of Expense Booked"/></g:link></li>
        <li><g:link url="[controller: 'reportJournalEntry', action: 'list']"><g:message code="bv.menu.ListJournalEntry" default="List of Journal Entry"/></g:link></li>
        <li><g:link url="[controller: 'reportReconciliation', action: 'listOfManualReconciliationEntry']"><g:message code="bv.menu.ListManualBankStatement" default="List of All Bank Statement"/></g:link></li>
    </ul>
</li>
<li>
    <g:link url="#"><g:message code="bv.menu.reports" default="Reports"/></g:link>
    <ul>
        <% if(userRole == "ROLE_FREE_VERSION_USER"){%>
            <li><g:link url="[controller: 'reports', action: 'freeVersionMessage']"><g:message code="bv.menu.GLReport" default="GL Report"/></g:link></li>
            <li><g:link url="[controller: 'reports', action: 'freeVersionMessage']"><g:message code="bv.menu.IncomeStatement" default="Income Statement"/></g:link></li>
            <li><g:link url="[controller: 'reports', action: 'freeVersionMessage']"><g:message code="bv.menu.BalanceSheet" default="Balance Sheet"/></g:link></li>
        <% } else { %>
            <li><g:link url="[controller: 'reports', action: 'glReport']"><g:message code="bv.menu.GLReport" default="GL Report"/></g:link></li>
            <li><g:link url="[controller: 'reports', action: 'incomeStatement']"><g:message code="bv.menu.IncomeStatement" default="Income Statement"/></g:link></li>

            <li><g:link url="[controller: 'reports', action: 'balanceSheetNew']"><g:message code="bv.menu.BalanceSheet" default="Balance Sheet"/></g:link></li>
        <% } %>


        <li><g:link url="[controller: 'reports', action: 'vatReport']"><g:message code="bv.menu.VatReport" default="Vat Report"/></g:link></li>
        <li><g:link url="[controller: 'reports', action: 'agingReport']"><g:message code="bv.menu.AgingReport" default="Aging Report"/></g:link></li>
        <li><g:link url="[controller: 'reports', action: 'trialBalanceReport']"><g:message code="bv.menu.TrialBalanceReport" default="Trial Balance Report"/></g:link></li>
        <li><g:link url="[controller: 'reports', action: 'exportAuditFile']"><g:message code="bv.menu.xmlAuditFile" default="XML Audit File"/></g:link></li>
        <li><g:link url="[controller: 'reports', action: 'exportSummaryView']"><g:message code="bv.menu.excelSummaryView" default="Summary Report"/></g:link></li>
        <li><g:link url="[controller: 'reports', action: 'privateBudget']"><g:message code="bv.menu.privateBudgetReport" default="Private Budget Report"/></g:link></li>
    </ul>
</li>
    <%if(authUserId==1){%>
        <li>
            <g:link url="#"><g:message code="bv.menu.dbreports" default="DB Reports"/></g:link>
            <ul>
                <li><g:link url="[controller: 'reports', action: 'reportInvoiceWithoutBudget']">
                    <g:message code="bv.menu.invoiceWithoutBudget" default="Invoice Without Budget Report"/></g:link>
                </li>
                <li><g:link url="[controller: 'reports', action: 'databaseMaintenance']">
                    <g:message code="bv.menu.databaseMaintenance" default="Database Maintenance Report"/></g:link>
                </li>
                <li><g:link url="[controller: 'reports', action: 'exportSummaryView']">
                    <g:message code="bv.menu.excelSummaryView" default="Summary Report"/></g:link>
                </li>
            </ul>
        </li>
    <% } %>
<% } %>

<li>
    <g:link url="#"><g:message code="bv.menu.settings" default="Settings"/></g:link>
    <ul>
        <li>
            <g:link url="#"><g:message code="bv.menu.Company" default="COMPANY"/></g:link>
            <ul>

                <li>
                    <g:link url="[controller: 'companySetup', action: 'index']"><g:message code="bv.menu.CompanySetup" default="Company Setup"/></g:link>
                </li>

                <% if (CompanySetup) { %>
                <li>
                    <g:link url="[controller: 'companyBankAccounts', action: 'index']"><g:message code="bv.menu.CompanyBankAccount" default="Your Bank Account"/></g:link>
                </li>

                <li>
                    <g:link url="[controller: 'bankAccountType', action: 'index']"><g:message code="bv.menu.BankAccountType" default="Bank Account Type"/></g:link>
                </li>

                <% } %>
                <li>
                    <g:link url="[controller: 'reports', action: 'dbDump' ]"><g:message code="bv.menu.DBDump" default="Database Backup"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'uploadPdf', action: 'uploadPdf' ]"><g:message code="bv.uploadPDF.label" default="Upload PDF"/></g:link>
                </li>
            </ul>
        </li>

        <% if (CompanySetup) { %>
        <li>
            <g:link url="#"><g:message code="bv.menu.SetUp" default="SETUP"/></g:link>
            <ul>
                <li>
                    <g:link url="[controller: 'customerMaster', action: 'index']"><g:message code="bv.menu.Customers" default="Customers"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'vendorMaster', action: 'index']"><g:message code="bv.menu.Vendors" default="Vendors"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'reservationBudgetMaster', action: 'index']"><g:message code="bv.menu.ReservationBudget" default="Reservation Budget"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'privateBudgetMaster', action: 'index']"><g:message code="bv.menu.PrivateBudget" default="Private Budget"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'fiscalYear', action: 'index']"><g:message code="bv.menu.FiscalYear" default="Fiscal Year"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'paymentTerms', action: 'index']"><g:message code="bv.menu.PaymentTerms" default="Payment Terms"/></g:link>
                </li>
            </ul>
        </li>
    <sec:ifAnyGranted roles="ROLE_ADMIN">
        <li>
            <g:link url="#"><g:message code="bv.menu.Security" default="SECURITY"/></g:link>
            <ul>
                <li>
                    <g:link url="[controller: 'user', action: 'index']"><g:message code="bv.menu.Users" default="Users"/></g:link>
                </li>
                <%if(authUserId==1){%>
                    <li>
                        <g:link url="[controller: 'BusinessCompany', action: 'index']"><g:message code="bv.menu.BusinessCompany" default="Business Company"/></g:link>
                    </li>
                 <%}%>
                    <li>
                        <g:link url="[controller: 'IPRestriction', action: 'index']"><g:message code="bv.menu.IPRestriction" default="IP Restriction"/></g:link>
                    </li>
            </ul>
        </li>
    </sec:ifAnyGranted>

        <% } %>
    </ul>
</li>

<sec:ifAnyGranted roles="ROLE_ADMIN">
<li>
    <g:link url="#"><g:message code="bv.menu.extraSetting" default="Extra Setting"/></g:link>
    <ul>
        <li>
            <g:link url="#">GL Account</g:link>
            <ul>
                <li>
                    <g:link url="[controller: 'chartClass', action: 'index']"><g:message code="bv.menu.ChartClass" default="Chart Class"/></g:link>
                </li>

                <li>
                    <g:link url="[controller: 'chartGroup', action: 'index']"><g:message code="bv.menu.ChartGroup" default="Chart Group"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'chartMaster', action: 'index']"><g:message code="bv.menu.ChartMaster" default="Chart Master"/></g:link>
                </li>
            </ul>
        </li>
        <li>
            <g:link url="#">Product</g:link>
            <ul>
                <li>
                    <g:link url="[controller: 'productCategory', action: 'index']"><g:message code="bv.menu.ProductCategory" default="Product Category"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'productMaster', action: 'index']"><g:message code="bv.menu.ProductMaster" default="Product Master"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'productUnit', action: 'index']"><g:message code="bv.menu.ProductUnit" default="Product Unit"/></g:link>
                </li>
            </ul>
        </li>

        <li style="margin-right: 0"><g:link url="#">Default Setting</g:link>
            <ul>
                <li>
                    <g:link url="[controller: 'countries', action: 'index']"><g:message code="bv.menu.Countries" default="Countries"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'currencies', action: 'index']"><g:message code="bv.menu.Currencies" default="Currencies"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'exchangeRates', action: 'index']"><g:message code="bv.menu.ExchangeRates" default="Exchange Rates"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'vatCategory', action: 'index']"><g:message code="bv.menu.VATCategory" default="VAT Category"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'reconciliationBookingType', action: 'index']"><g:message code="bv.menu.ReconciliationBookingType" default="Reconciliation Booking Type"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'debitCreditGlSetup', action: 'index']"><g:message code="bv.menu.DebtorCreditorSetup" default="Default GL Setup"/></g:link>
                </li>
                <li>
                    <g:link url="[controller: 'companyBankGlRelation', action: 'index']"><g:message code="bv.menu.CompanyGLRelation" default="Company GL Relation"/></g:link>
                </li>
            </ul>
        </li>

    </ul>
</li>

</sec:ifAnyGranted>

<% } %>

<li>
    <g:link url="#"><sec:loggedInUserInfo field="username"/></g:link>
    <ul>
    <%
        def permittedDbArr = new UserTagLib().gettingPermittedDBInfo(sec.loggedInUserInfo(field: 'id'));
        if(permittedDbArr.size()){
    %>
        <li>
            <g:link url="#">Permitted Database</g:link>
            <ul>
            <%
            if( permittedBusinessCompanyId == businessCompanyId || permittedBusinessCompanyId == null){%>
            <li>
                <g:link controller="user" action="selectedDataBase" style="color:#000000;font-weight: bold;"
                params="[businessCompanyId: businessCompanyId, userId: sec.loggedInUserInfo(field: 'id')]">
                    <g:message code="bv.menu.ChangePassword1" default="${sec.loggedInUserInfo(field: 'username')}"/></g:link>
            </li>
            <%}else{%>
            <li>
                <g:link controller="user" action="selectedDataBase" params="[businessCompanyId: businessCompanyId, userId: sec.loggedInUserInfo(field: 'id')]">
                    <g:message code="bv.menu.ChangePassword1" default="${sec.loggedInUserInfo(field: 'username')}"/></g:link>
            </li>
            <%}%>

            <%

                if(permittedDbArr.size()>0){
                    permittedDbArr.each { phn ->
                        if(permittedBusinessCompanyId == phn[2] ){
            %>

            <li>
                <g:link controller="user" action="selectedDataBase" style="color:#000000;font-weight: bold;"
                params="[businessCompanyId: phn[2], userId: phn[3]]"><g:message code="bv.menu.ChangePassword1" default="${phn[7]}"/></g:link>
            </li>
            <%}else{%>
            <li>
                <g:link controller="user" action="selectedDataBase" params="[businessCompanyId: phn[2], userId: phn[3]]">
                    <g:message code="bv.menu.ChangePassword1" default="${phn[7]}"/></g:link>
            </li>
            <%}%>
            <% } } %>
        </ul>
        </li>
    <%}%>
        <li>
            <g:link url="#"><g:message code="bv.manageUser" default="Manage User"/></g:link>
            <ul>
                <li><g:link controller="logout" action="index"><g:message code="bv.menu.Logout" default="Logout"/></g:link></li>
                <li>
                    <g:link controller="user" action="updatePassword" params="[id: sec.loggedInUserInfo(field: 'id'), st: 1]">
                        <g:message code="bv.menu.ChangePassword" default="Change Password"/></g:link>
                </li>
                <li>
                    <g:link controller="user" action="managePermission" params="[id: sec.loggedInUserInfo(field: 'id'), st: 1]">
                        <g:message code="bv.menu.ChangePassword1" default="Manage Permission"/></g:link>
                </li>
            </ul>
        </li>

    </ul>
</li>--}%

<% } %>  <!--only for new design-->

</ul>   <!--mega-menu-->

</div>
</div>