<%@ page import="bv.DashboardDetails;bv.DashboardDetailsTagLib;org.codehaus.groovy.grails.plugins.web.taglib.ValidationTagLib" %>
<asset:javascript src="highcharts-3.0.4.min.js" />

<%
    def protocol = request.isSecure() ? "https://" : "http://"
    def host = request.getServerName()
    def port = request.getServerPort()
    def context = request.getServletContext().getContextPath()
    def liveURL = protocol + host + ":" + port + context
    def g = new ValidationTagLib()

    def footerFirstLabel = [g.message(code: 'bv.dashboard.budget.label'),g.message(code: 'bv.dashboard.budget.label'),g.message(code: 'bv.dashboard.budget.label')]
    def footerLastLabel = [g.message(code: 'bv.dashboardDetails.booking.label'),g.message(code: 'bv.dashboardDetails.booking.label'),g.message(code: 'bv.dashboardDetails.booking.label')]

    def barBGColor = ['rgba(120,120,120,1)','rgba(120,120,120,1)','rgba(120,120,120,1)']

    def chartToolTipBG = [g.message(code: 'bv.invoiceIncome.budgetAmount.label'),g.message(code: 'bv.invoiceIncome.budgetAmount.label'),g.message(code: 'bv.invoiceIncome.budgetAmount.label')]
    def chartToolTipFG = [g.message(code: 'report.aging.invoiceAmount.label'),g.message(code: 'report.aging.invoiceAmount.label'),g.message(code: 'report.aging.invoiceAmount.label')]

//    def headerArr = [g.message(code: 'bv.dashboardDetails.budgetAndBooking.label'),g.message(code: 'bv.dashboardDetails.incomeAndExpenseBudget.label'),g.message(code: 'bv.dashboardDetails.incomeAndExpenseInvoice.label')]
    def headerArr = [g.message(code: 'bv.dashboard.totalBudgetAndBookingSummary.label'),g.message(code: 'bv.dashboardDetails.incomeBudgetAndBooking.label'),g.message(code: 'bv.dashboardDetails.expenseBudgetAndBooking.label')]

    def headerArr1 = [g.message(code: 'bv.dashboard.totalBudgetAmount.label'),g.message(code: 'bv.dashboard.totalBudgetAmount.label'),g.message(code: 'bv.dashboard.totalBudgetAmount.label')]
    def headerArr2 = [g.message(code: 'bv.dashboard.totalInvoiceAmount.label'),g.message(code: 'bv.dashboard.totalInvoiceAmount.label'),g.message(code: 'bv.dashboard.totalInvoiceAmount.label')]

    def dashboardDetailsTagLib = new DashboardDetailsTagLib();

    Map monthlyBudgetAmount
    Map monthlyBookingAmount

%>

<div id="accordion" style="height:985px;background: none repeat scroll 0 0 #fcfcfc;">
<% for (int v = 0; v < 3; v++) {

    if(v == 0){
        monthlyBudgetAmount = grossProfitInstance;
        monthlyBookingAmount = grossProfitBookingInstance;
    }else if(v == 1) {
        monthlyBudgetAmount = incomeBudgetInstance;
        monthlyBookingAmount = incomeInvoiceInstance;
    }else if(v == 2) {
        monthlyBudgetAmount = expenseBudgetInstance;
        monthlyBookingAmount = expenseInvoiceInstance;
    }

    int nTotalBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount.allTotal)
    int nTotalBookingAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBookingAmount.allTotal)
%>

<% if(v > 0) { %>
%{--<div class="dashUpperSubBar"></div>--}%
<% } %>

%{--<hr>--}%
<div class="dashUpperSubBar">
    <div class="dashAmountHeader barStyleMiddle">
        <label class="dashAmountLabel" style="float: left;margin-left: 46px;">${headerArr[v]}</label>
        <label class="dashAmountLabel" style="margin-left: 100px;">${headerArr1[v]} : ${nTotalBudgetAmount}</label>
        <label class="dashAmountLabel" >${headerArr2[v]} : ${nTotalBookingAmount}</label>
    </div>
</div>
%{--<hr>--}%

<div id="summary${v}" class="seeResultBodyHeader">  <!-- class="dashGraphDiv"-->
<div id="list-page-body-inner" class="content" style="background-color: #fff; border: none; margin: 0">
<div id="expense" style="padding: 0px; border-radius:none;">
    <div id="containerHC${v}" style="min-width: 150px; height: 250px; margin: 0 auto"></div>
<%
    /////////////////SKB----TOTAL SUM//////////////////
    int janBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount.janTotal);
    int febBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount.febTotal);
    int marBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount.marTotal);
    int aprBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount.aprTotal);
    int mayBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount.mayTotal);
    int junBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount.junTotal);
    int julBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount.julTotal);
    int augBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount.augTotal);
    int sepBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount.sepTotal);
    int octBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount.octTotal);
    int novBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount.novTotal);
    int decBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount.decTotal);

    int janBookingAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBookingAmount.janTotal);
    int febBookingAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBookingAmount.febTotal);
    int marBookingAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBookingAmount.marTotal);
    int aprBookingAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBookingAmount.aprTotal);
    int mayBookingAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBookingAmount.mayTotal);
    int junBookingAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBookingAmount.junTotal);
    int julBookingAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBookingAmount.julTotal);
    int augBookingAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBookingAmount.augTotal);
    int sepBookingAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBookingAmount.sepTotal);
    int octBookingAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBookingAmount.octTotal);
    int novBookingAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBookingAmount.novTotal);
    int decBookingAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBookingAmount.decTotal);

    Integer nTickInterval = dashboardDetailsTagLib.getTickIntervalForSummaryView(monthlyBudgetAmount,monthlyBookingAmount);
%>

<script>
    $(function () {
        $('#containerHC${v}').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: ''
            },
            xAxis: {
                categories: [
                    'January',
                    'February',
                    'March',
                    'April',
                    'May',
                    'June',
                    'July',
                    'August',
                    'September',
                    'October',
                    'November',
                    'December'
                ]
            },
            yAxis: [
                {
                    offset: 50,
                    tickInterval: ${nTickInterval},
                    title: {
                        text: 'Amount',
                        align: 'middle'

                    },
                    labels: {
                        overflow: 'justify',
                        x: 25
                    }
                },
                {
                    title: {
                        text: ''
                    },
                    opposite: true
                }
            ],
            legend: {
                enabled: false
            },
            tooltip: {
                shared: true
            },
            plotOptions: {
                column: {
                    grouping: false,
                    shadow: false,
                    borderWidth: 0
                }
            },
            series: [
                {
                    name: '${chartToolTipBG[v]}',
                    color: '${barBGColor[v]}',
                    data: [${janBudgetAmount}, ${febBudgetAmount}, ${marBudgetAmount}, ${aprBudgetAmount},
                        ${mayBudgetAmount}, ${junBudgetAmount}, ${julBudgetAmount}, ${augBudgetAmount},
                        ${sepBudgetAmount}, ${octBudgetAmount}, ${novBudgetAmount}, ${decBudgetAmount}],
                    pointPadding: 0.2,
                    groupPadding: 0,
                    pointPlacement: -0.2
                },
                {
                    name: '${chartToolTipFG[v]}',
                    color: 'rgba(18,243,14,.9)',
                    data: [
                        {y:${janBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryViewBarColor(janBookingAmount,janBudgetAmount,v)}'},
                        {y:${febBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryViewBarColor(febBookingAmount,febBudgetAmount,v)}'},
                        {y:${marBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryViewBarColor(marBookingAmount,marBudgetAmount,v)}'},
                        {y:${aprBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryViewBarColor(aprBookingAmount,aprBudgetAmount,v)}'},
                        {y:${mayBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryViewBarColor(mayBookingAmount,mayBudgetAmount,v)}'},
                        {y:${junBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryViewBarColor(junBookingAmount,junBudgetAmount,v)}'},
                        {y:${julBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryViewBarColor(julBookingAmount,julBudgetAmount,v)}'},
                        {y:${augBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryViewBarColor(augBookingAmount,augBudgetAmount,v)}'},
                        {y:${sepBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryViewBarColor(sepBookingAmount,sepBudgetAmount,v)}'},
                        {y:${octBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryViewBarColor(octBookingAmount,octBudgetAmount,v)}'},
                        {y:${novBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryViewBarColor(novBookingAmount,novBudgetAmount,v)}'},
                        {y:${decBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryViewBarColor(decBookingAmount,decBudgetAmount,v)}'}
                    ],
                    pointPadding: 0.3,
                    groupPadding: 0,
                    pointPlacement: -0.2
                }
            ]
        });
    });

</script>

<div class="footerAmount">
    <div class="labelOfBudgetInvoice">
        <div class="monthAmountLabel">${footerFirstLabel[v]}:</div>
        <div class="monthAmountLabel" style="margin-top: 18px;">${footerLastLabel[v]}:</div>
    </div>

    <div class="monthItemsValue">
        <%
            Integer loopmonth = 1

            int budgetAmount = 0
            int bookingAmount = 0

            for (int monthc = 0; monthc < 12; monthc++) {

                if (monthc == 0) {
                    budgetAmount = monthlyBudgetAmount.janTotal;
                    bookingAmount = monthlyBookingAmount.janTotal;
                }
                else if (monthc == 1) {
                    budgetAmount = monthlyBudgetAmount.febTotal;
                    bookingAmount = monthlyBookingAmount.febTotal;
                }
                else if (monthc == 2) {
                    budgetAmount = monthlyBudgetAmount.marTotal;
                    bookingAmount = monthlyBookingAmount.marTotal;
                }
                else if (monthc == 3) {
                    budgetAmount = monthlyBudgetAmount.aprTotal;
                    bookingAmount = monthlyBookingAmount.aprTotal;
                }
                else if (monthc == 4) {
                    budgetAmount = monthlyBudgetAmount.mayTotal;
                    bookingAmount = monthlyBookingAmount.mayTotal;
                }
                else if (monthc == 5) {
                    budgetAmount = monthlyBudgetAmount.junTotal;
                    bookingAmount = monthlyBookingAmount.junTotal;
                }
                else if (monthc == 6) {
                    budgetAmount = monthlyBudgetAmount.julTotal;
                    bookingAmount = monthlyBookingAmount.julTotal;
                }
                else if (monthc == 7) {
                    budgetAmount = monthlyBudgetAmount.augTotal;
                    bookingAmount = monthlyBookingAmount.augTotal;
                }
                else if (monthc == 8) {
                    budgetAmount = monthlyBudgetAmount.sepTotal;
                    bookingAmount = monthlyBookingAmount.sepTotal;
                }
                else if (monthc == 9) {
                    budgetAmount = monthlyBudgetAmount.octTotal;
                    bookingAmount = monthlyBookingAmount.octTotal;
                }
                else if (monthc == 10) {
                    budgetAmount = monthlyBudgetAmount.novTotal;
                    bookingAmount = monthlyBookingAmount.novTotal;
                }
                else if (monthc == 11) {
                    budgetAmount = monthlyBudgetAmount.decTotal;
                    bookingAmount = monthlyBookingAmount.decTotal;
                }

        %>
        %{--Budget Amount data by months.--}%
        <div class="grpMonthlyAmount">
            <div class="monthlyBudAmt ${(loopmonth % 2) == 0 ? 'odd' : 'even'}">
                ${budgetAmount}
            </div>
            %{--Invoice income.--}%
            <div class="monthlyInvAmt ${(loopmonth % 2) == 0 ? 'odd' : 'even'}" style="color:${dashboardDetailsTagLib.getSummaryViewBarColor(bookingAmount,budgetAmount,v)}">
                ${bookingAmount}
            </div>
        </div>
        <%
            }
        %>

    </div>
</div>
</div>  <!--id="expense"-->

</div>  <!--content-->
</div>

<%
    }
%>
</div>  <!--id="accordion"-->



