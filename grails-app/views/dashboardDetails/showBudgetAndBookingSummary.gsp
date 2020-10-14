<%@ page import="java.text.SimpleDateFormat;bv.DashboardDetailsTagLib; bv.CoreParamsHelper; bv.Dashboard;org.springframework.web.servlet.support.RequestContextUtils;" %>
<%@ page import="com.bv.User;bv.BudgetViewDatabaseService; bv.FiscalYear; bv.UserLog; com.bv.User; org.springframework.security.core.context.SecurityContextHolder; org.springframework.security.core.Authentication; org.springframework.security.core.context.SecurityContext" %>

<%
    SecurityContext ctx = SecurityContextHolder.getContext()
    Authentication auth = ctx.getAuthentication()

    def roleArray = auth.getAuthorities();
    def userRole = roleArray[0];

    def protocol = request.isSecure() ? "https://" : "http://"
    def host = request.getServerName()
    def port = request.getServerPort()
    def context = request.getServletContext().getContextPath()

    def combineSummaryURL = ""
//    def paramsData = "?summaryType=budNBook&taxReservType=taxWithoutReservation&budgetItemType=privateBudget";
    def paramsData = "?summaryType=budNBook&taxReservType=taxWithoutReservation&budgetItemType=incomeNexpense";
   // println("combineSummaryURL "+paramsData);
    combineSummaryURL = protocol + host + ":" + port + context + "/dashboardDetails/showProfitSummaryAsTypeWise" + paramsData;

    def activeFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
    def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(activeFiscalYear)

    def incomeTaxPercentage = new CoreParamsHelper().getCompanySetupReservationTaxAmount()

    def dashboardDetailsTagLib = new DashboardDetailsTagLib();
    int nNetProfitBasedOnBudget = dashboardDetailsTagLib.getRoundedValue(totalNetProfitAmount)
    int nGrossProfitBasedOnInvoice = dashboardDetailsTagLib.getRoundedValue(totalGrossProfitAmount)
%>

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="bv.dashboard.budgetAndBookingSummary.label" default="Budget & Booking Summary" /></title>
    <script>

        function onChangeSummaryDetails(summaryType) {
            var url = "";
            var taxTypeValue = document.getElementById("taxReservType").value;
            var summaryTypValue = document.getElementById("summaryType").value;
            var budgetItemType = document.getElementById("budgetType").value;
            var paramsData = "?summaryType=" + summaryTypValue + "&taxReservType=" + taxTypeValue + "&budgetItemType=" + budgetItemType;
            url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/showProfitSummaryAsTypeWise" + paramsData;
            document.getElementById("ifrmProfitSummary").src = url;
        }

        function onChangeTaxReservationType(taxType) {
            var url = "";
            var taxTypeValue = document.getElementById("taxReservType").value;
            var summaryTypValue = document.getElementById("summaryType").value;
            var budgetItemType = document.getElementById("budgetType").value;

            var paramsData = "?summaryType=" + summaryTypValue + "&taxReservType=" + taxTypeValue + "&budgetItemType=" + budgetItemType;
            url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/showProfitSummaryAsTypeWise" + paramsData;
            document.getElementById("ifrmProfitSummary").src = url;
        }

        function onChangeBudgetDetails() {
            var url = "";
            var taxTypeValue = document.getElementById("taxReservType").value;
            var summaryTypValue = document.getElementById("summaryType").value;
            var budgetItemType = document.getElementById("budgetType").value;
            var paramsData = "";

            if(budgetItemType == "privateBudget"){
                $('#taxReservType option')[1].selected = true;
                $('#taxReservType').attr('disabled',true);
                taxTypeValue = "taxWithReservation";

                $('#summaryType option')[0].selected = true;
                $('#summaryType').removeAttr('disabled');
                summaryTypValue = "budNBook";

            }if(budgetItemType == "reservationBudget"){
                // for with tax reservation
                $('#taxReservType option')[1].selected = true;
                $('#taxReservType').attr('disabled',true);
                taxTypeValue = "taxWithReservation";

                // for summaryType (budget & booking)
                $('#summaryType option')[0].selected = true;
                $('#summaryType').attr('disabled',true);
                summaryTypValue = "budNBook";

            }else if(budgetItemType == "incomeNexpense"){
                // for with tax reservation
                $('#taxReservType option')[0].selected = true;
                $('#taxReservType').removeAttr('disabled');
                taxTypeValue = "taxWithoutReservation";

                // for summaryType (budget & booking)
                $('#summaryType option')[0].selected = true;
                $('#summaryType').removeAttr('disabled');
                summaryTypValue = "budNBook";
            }

            paramsData = "?summaryType=" + summaryTypValue + "&taxReservType=" + taxTypeValue + "&budgetItemType=" + budgetItemType;
            url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/showProfitSummaryAsTypeWise" + paramsData;
            document.getElementById("ifrmProfitSummary").src = url;
        }


    </script>

</head>
<body>

<div id="list-page-body-inner" class="content" style="background-color: #fff; border: none;">

    <div class="budgetHeader">
        <div class="headerMainLeft">
            <label class=""><g:message code="bv.dashboardDetails.myResults.label" default="My Results" /></label>
        </div>

        <div class="headerMainMiddle" style="width: 12.7%;">
            <a href="${context}/fiscalYear/list">${fiscalYearInfo[0][4]}</a>
        </div>

        <div class="headerMainRight">
            <div class="headerRightDiv1">
                <label class="categoryLabel"><g:message code="bv.dashboard.netProfit.label" default="Net budgetted Profit" /></label>
                <label class="amountLabel">${nNetProfitBasedOnBudget}</label>
            </div>

            <div class="headerRightDiv2">
                <label class="categoryLabel"><g:message code="bv.dashboard.grossProfit.label" default="Sum Booked invoices" /></label>
                <label class="amountLabel">${nGrossProfitBasedOnInvoice}</label>
            </div>

        </div>
    </div>

    <div id="" class="explanationDiv">
        <h4 class="slideToggler2 slideSign2">
            <label class="explanationLabel">
                <span class="explanationArrow"></span>
                <p><g:message code="bv.dashboard.explanation.label" default="Explanation" /></p>
            </label>
        </h4>

        <div class="slideContent2 explanationSlideContainer">
            <ul class="explanationList">
                <li><a href="${context}/explanation/seeMyResult"><g:message code="explanation.seeMyResult.label"  /></a></li>
            </ul>
        </div>
    </div> <!--explanationDiv-->


    <div class="navigation" style="height: 153px">
        <div class="navigationWizard">
            <div id="incNexpButton">

                <div class="row">
                    <div class="column">
                        <div class="navigationbtn" style="margin-left: 40px;">
                            <a  style="width: 244px;" href="${context}/quickEntryInvoice/quickEntryForm"><g:message
                                    code="bv.menu.quickEntry" default="Quick Entry"/></a>
                        </div>

                        <div class="navigationbtn" style="margin-left: 40px;">
                            <a  style="width: 244px;" href="${context}/budgetItemIncomeDetails/budgetForInvoIncome"><g:message
                                    code="report.aging.incomeInvoice.label" default="Income invoice"/></a>
                        </div>

                        <div class="navigationbtn" style="margin-left: 40px;">
                            <a style="width: 244px;" href="${context}/budgetItemExpenseDetails/budgetForInvoExpense"><g:message
                                    code="report.aging.expenseInvoice.label" default="Expense invoice"/></a>
                        </div>
                    </div>

                    <div class="column">
                        <div class="navigationbtn" style="margin-left: 32px;">
                            <a  style="width: 244px;" href="${context}/reports/sendReminder"><g:message
                                    code="bv.menu.sendReminder" default="Send Reminder"/></a>
                        </div>

                        <div class="navigationbtn" style="margin-left: 32px;">
                            <a  style="width: 244px;" href="${context}/BankReconciliation/reconciliation"><g:message
                                    code="bv.dashboard.bankUpload.label" default="Bank upload"/></a>
                        </div>

                        <div class="navigationbtn" style="margin-left: 32px;">
                            <a style="width: 244px;" href="${context}/reportExpenseBudget/listBatchPayment"><g:message
                                    code="bv.dashboard.createPayment.label" default="Create payment file"/></a>
                        </div>
                    </div>

                    <div class="column">
                        <div class="navigationbtn" style="margin-left: 12px;">
                            <a style="width: 244px;" href="${context}/dashboard/index"><g:message
                                    code="bv.dashboardDetails.BudgetsAndBookings.label" default="Budgets & bookings" /></a>
                        </div>

                        <div class="navigationbtn" style="margin-left: 12px;">
                            <a style="width: 244px;" href="${context}/budgetItemIncomeDetails/list?sort=id&order=desc"><g:message
                                    code="bv.dashboard.IncomeBudgetWizard.label" default="Income budget wizard"/></a>
                        </div>

                        <div class="navigationbtn" style="margin-left: 12px;">
                            <a style="width: 244px;" href="${context}/budgetItemExpenseDetails/list?sort=id&order=desc"><g:message
                                    code="bv.dashboard.expenseBudgetWizard.label" default="Expense budget wizard"/></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="selectionBudgetDiv">
        %{--<label class="showBudgetLabel">Show Profit For<b>:</b></label>--}%
        <div class="showBudgetLabel">
            <p><g:message code="bv.dashboardDetails.showProfitFor.label" default="Show Profit For" /><b>:</b></p>
        </div>
        <div class="comboOptionSelection">
            <div class="comboDiv">
                <%= "${new CoreParamsHelper().getDropDownForBudget('budgetType','0')}" %>
            </div>

            <div class="comboDiv">
                <%= "${new CoreParamsHelper().getDropDownBudgetSummaryType('summaryType','0')}" %>
            </div>

            <div class="comboDiv fcSpecialCombo">
                <%= "${new CoreParamsHelper().getDropDownTaxReservationType('taxReservType','0')}" %> (${incomeTaxPercentage}%)
            </div>
        </div>
    </div>

    %{--<hr>--}%

    <div id="detalis_view" class="graph" style="border-radius: 3px;height:1576px;">
            <iframe id="ifrmProfitSummary" src="${combineSummaryURL}" width="100%" frameborder="0" scrolling="yes" style="height: 1576px;"></iframe>
    </div>

    <div class="navigation" style="margin-bottom:100px;">
        <div class="navigationWizard">
            <% if(userRole == "ROLE_FREE_VERSION_USER"){%>
                <div class="navigationbtn fixedNaviBtn" style="margin-left: 43px;">
                    <a href="${context}/reports/freeVersionMessage"><g:message code="bv.menu.GLReport" default="GL Report"/></a>
                </div>

                <div class="navigationbtn fixedNaviBtn">
                    <a href="${context}/reports/freeVersionMessage"><g:message code="bv.menu.IncomeStatement" default="Income Statement"/></a>
                </div>

                <div class="navigationbtn fixedNaviBtn" style="">
                    <a href="${context}/reports/freeVersionMessage"><g:message code="bv.menu.BalanceSheet" default="Balance Sheet"/></a>
                </div>

            <% } else { %>
                %{--<div class="navigationbtn fixedNaviBtn" style="margin-left: 43px;">
                    <a href="${context}/reports/glReport"><g:message code="bv.menu.GLReport" default="GL Report"/></a>
                </div>--}%

                <div class="navigationbtn fixedNaviBtn">
                    <a href="${context}/reports/incomeStatement"><g:message code="bv.menu.IncomeStatement" default="Income Statement"/></a>
                </div>

                <div class="navigationbtn fixedNaviBtn" style="">
                    <a href="${context}/reports/balanceSheetNew"><g:message code="bv.menu.BalanceSheet" default="Balance Sheet"/></a>
                </div>

            <% } %>


            <div class="navigationbtn fixedNaviBtn">
                <a href="${context}/reports/vatReport"><g:message code="bv.menu.VatReport" default="Vat Report"/></a>
            </div>

            <div class="navigationbtn fixedNaviBtn">
                <a href="${context}/reports/agingReport"><g:message code="bv.menu.AgingReport" default="Aging Report"/></a>
            </div>

        </div>
    </div>

</div>

</body>
</html>