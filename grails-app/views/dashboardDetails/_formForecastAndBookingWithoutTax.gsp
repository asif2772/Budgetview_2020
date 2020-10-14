<%@ page import="bv.DashboardDetails;bv.DashboardDetailsTagLib;" %>
<asset:javascript src="highcharts-3.0.4.min.js" />

<%
    def protocol = request.isSecure() ? "https://" : "http://"
    def host = request.getServerName()
    def port = request.getServerPort()
    def context = request.getServletContext().getContextPath()
    def liveURL = protocol + host + ":" + port + context

    def footerFirstLabel = [g.message(code: 'bv.dashboardDetails.forcast.label'),g.message(code: 'bv.dashboardDetails.forcast.label'),g.message(code: 'bv.dashboardDetails.forcast.label')]
    def footerLastLabel = [g.message(code: 'bv.dashboardDetails.booking.label'),g.message(code: 'bv.dashboardDetails.booking.label'),g.message(code: 'bv.dashboardDetails.booking.label')]

    def barBGColor = ['rgba(120,120,120,1)','rgba(120,120,120,1)','rgba(120,120,120,1)']

    def chartToolTipBG = [g.message(code: 'bv.invoiceIncome.budgetAmount.label'),g.message(code: 'bv.invoiceIncome.budgetAmount.label'),g.message(code: 'bv.invoiceIncome.budgetAmount.label')]
    def chartToolTipFG = [g.message(code: 'report.aging.invoiceAmount.label'),g.message(code: 'report.aging.invoiceAmount.label'),g.message(code: 'report.aging.invoiceAmount.label')]

    def headerArr = [g.message(code: 'bv.dashboard.totalForecastAndBookingSummary.label'),g.message(code: 'bv.dashboardDetails.incomeForcastAndBooking.label'),g.message(code: 'bv.dashboardDetails.expenseForecastAndBooking.label')]

    def headerArr1 = [g.message(code: 'bv.dashboard.totalBudgetAmount.label'),g.message(code: 'bv.dashboard.totalBudgetAmount.label'),g.message(code: 'bv.dashboard.totalBudgetAmount.label')]
    def headerArr2 = [g.message(code: 'bv.dashboard.totalInvoiceAmount.label'),g.message(code: 'bv.dashboard.totalInvoiceAmount.label'),g.message(code: 'bv.dashboard.totalInvoiceAmount.label')]

    def dashboardDetailsTagLib = new DashboardDetailsTagLib();

    Map monthlyBudgetAmount
    Map monthlyBookingAmount
%>

<div id="accordion" style="height:985px;background: none repeat scroll 0 0 #fcfcfc;">
<% for (int v = 0; v < 3; v++) {

    if(v == 0){
        monthlyBudgetAmount = netProfitForecastInstance;
        monthlyBookingAmount = netProfitBookingInstance;
    }else if(v == 1) {
        monthlyBudgetAmount = incomeForcast;
        monthlyBookingAmount = incomeInvoiceInstance;

       /* monthlyBudgetAmount = incomeBudgetInstance;
        monthlyBookingAmount = incomeInvoiceInstance;*/
    }else if(v == 2) {
        monthlyBudgetAmount = expenseForcast;
        monthlyBookingAmount = expenseInvoiceInstance;

       /* monthlyBudgetAmount = expenseBudgetInstance;
        monthlyBookingAmount = expenseInvoiceInstance;*/
    }

    int nTotalBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount.allTotal)
    int nTotalBookingAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBookingAmount.allTotal)
%>

<% if(v > 0) { %>
%{--<div class="dashUpperSubBar"></div>--}%
<% } %>

<div class="dashUpperSubBar">
    <div class="dashAmountHeader barStyleMiddle">
        <label class="dashAmountLabel" style="float: left;margin-left: 46px;">${headerArr[v]}</label>
        <label class="dashAmountLabel" style="margin-left: 100px;">${headerArr1[v]} : ${nTotalBudgetAmount}</label>
        <label class="dashAmountLabel" >${headerArr2[v]} : ${nTotalBookingAmount}</label>
    </div>
</div>


    <div id="summary${v}" class="seeResultBodyHeader">  <!---dashGraphDiv-->
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
                                name:   '${chartToolTipBG[v]}',
                                color:  '${barBGColor[v]}',
                                data: [ ${janBudgetAmount}, ${febBudgetAmount}, ${marBudgetAmount}, ${aprBudgetAmount},
                                        ${mayBudgetAmount}, ${junBudgetAmount}, ${julBudgetAmount}, ${augBudgetAmount},
                                        ${sepBudgetAmount}, ${octBudgetAmount}, ${novBudgetAmount}, ${decBudgetAmount}],
                                pointPadding: 0.2,
                                groupPadding: 0,
                                pointPlacement: -0.2
                            },
                            {
                                name: '${chartToolTipFG[v]}',
                                color: 'rgba(255,102,0,.9)',
                                data: [
                                    {y:${janBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryBarColorWithoutCond(janBookingAmount,janBudgetAmount,v)}'},
                                    {y:${febBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryBarColorWithoutCond(febBookingAmount,febBudgetAmount,v)}'},
                                    {y:${marBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryBarColorWithoutCond(marBookingAmount,marBudgetAmount,v)}'},
                                    {y:${aprBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryBarColorWithoutCond(aprBookingAmount,aprBudgetAmount,v)}'},
                                    {y:${mayBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryBarColorWithoutCond(mayBookingAmount,mayBudgetAmount,v)}'},
                                    {y:${junBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryBarColorWithoutCond(junBookingAmount,junBudgetAmount,v)}'},
                                    {y:${julBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryBarColorWithoutCond(julBookingAmount,julBudgetAmount,v)}'},
                                    {y:${augBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryBarColorWithoutCond(augBookingAmount,augBudgetAmount,v)}'},
                                    {y:${sepBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryBarColorWithoutCond(sepBookingAmount,sepBudgetAmount,v)}'},
                                    {y:${octBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryBarColorWithoutCond(octBookingAmount,octBudgetAmount,v)}'},
                                    {y:${novBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryBarColorWithoutCond(novBookingAmount,novBudgetAmount,v)}'},
                                    {y:${decBookingAmount}, color: '${dashboardDetailsTagLib.getSummaryBarColorWithoutCond(decBookingAmount,decBudgetAmount,v)}'}
                                ],
                                pointPadding: 0.3,
                                groupPadding: 0,
                                pointPlacement: -0.2
                            }
                        ]
                    });
                });

            </script>

                %{--<% if(v == 0){%>--}%
                %{--<div class="footerAmountSummary">--}%
                    %{--<div class="labelOfBudgetInvoice">--}%
                        %{--<div class="monthAmountLabel"><g:message code="bv.dashboardDetails.forcast.label" default="FORECAST" />:</div>--}%
                        %{--<div class="monthAmountLabel" style="margin-top: 18px;"><g:message code="bv.dashboardDetails.booking.label" default="BOOKING" />:</div>--}%
                        %{--<div class="monthAmountLabel" style="margin-top: 18px;"><g:message code="bv.dashboardDetails.income.label" default="INCOME" />:</div>--}%
                        %{--<div class="monthAmountLabel" style="margin-top: 16px;"><g:message code="bv.dashboardDetails.expense.label" default="EXPENSE" />:</div>--}%
                        %{--<div class="monthAmountLabel" style="margin-top: 16px;"><g:message code="bv.companySetup.taxReservation.label" default="Tax Reservation" />:</div>--}%
                        %{--<div class="monthAmountLabel" style="margin-top: 15px;"><g:message code="bv.dashboardDetails.netProfit.label" default="Net Profit" />:</div>--}%
                        %{--<div class="monthAmountLabel">${footerFirstLabel[v]}:</div>--}%
                    %{--</div>--}%
                %{--<% }else{ %>--}%
                <div class="footerAmount">
                    <div class="labelOfBudgetInvoice">
                        <div class="monthAmountLabel">${footerFirstLabel[v]}:</div>
                        <div class="monthAmountLabel" style="margin-top: 18px;">${footerLastLabel[v]}:</div>
                    </div>
                    %{--<%} %>--}%

                <div class="monthItemsValue">
                    <%
                            Integer loopmonth = 1

                            int budgetAmount = 0
                            int bookingAmount = 0

                            int incomeBudgetAmount = 0;
                            int expenseBudgetAmount = 0;
                            int taxReservation = 0;
                            int netProfit = 0;

                            for (int monthc = 0; monthc < 12; monthc++) {

                                if (monthc == 0) {
                                    budgetAmount = monthlyBudgetAmount.janTotal;
                                    bookingAmount = monthlyBookingAmount.janTotal;

                                    incomeBudgetAmount = dashboardDetailsTagLib.getRoundedValue(incomeBudgetInstance.janTotal);
                                    expenseBudgetAmount = dashboardDetailsTagLib.getRoundedValue(expenseBudgetInstance.janTotal);
                                    taxReservation = dashboardDetailsTagLib.getRoundedValue(taxReservationAmountInstance.janTotal);
                                    netProfit = (incomeBudgetAmount - expenseBudgetAmount) - taxReservation;
                                }
                                else if (monthc == 1) {
                                    budgetAmount = monthlyBudgetAmount.febTotal;
                                    bookingAmount = monthlyBookingAmount.febTotal;

                                    incomeBudgetAmount = dashboardDetailsTagLib.getRoundedValue(incomeBudgetInstance.febTotal);
                                    expenseBudgetAmount = dashboardDetailsTagLib.getRoundedValue(expenseBudgetInstance.febTotal);
                                    taxReservation = dashboardDetailsTagLib.getRoundedValue(taxReservationAmountInstance.febTotal);
                                    netProfit = (incomeBudgetAmount - expenseBudgetAmount) - taxReservation;
                                }
                                else if (monthc == 2) {
                                    budgetAmount = monthlyBudgetAmount.marTotal;
                                    bookingAmount = monthlyBookingAmount.marTotal;

                                    incomeBudgetAmount = dashboardDetailsTagLib.getRoundedValue(incomeBudgetInstance.marTotal);
                                    expenseBudgetAmount = dashboardDetailsTagLib.getRoundedValue(expenseBudgetInstance.marTotal);
                                    taxReservation = dashboardDetailsTagLib.getRoundedValue(taxReservationAmountInstance.marTotal);
                                    netProfit = (incomeBudgetAmount - expenseBudgetAmount) - taxReservation;
                                }
                                else if (monthc == 3) {
                                    budgetAmount = monthlyBudgetAmount.aprTotal;
                                    bookingAmount = monthlyBookingAmount.aprTotal;

                                    incomeBudgetAmount = dashboardDetailsTagLib.getRoundedValue(incomeBudgetInstance.aprTotal);
                                    expenseBudgetAmount = dashboardDetailsTagLib.getRoundedValue(expenseBudgetInstance.aprTotal);
                                    taxReservation = dashboardDetailsTagLib.getRoundedValue(taxReservationAmountInstance.aprTotal);
                                    netProfit = (incomeBudgetAmount - expenseBudgetAmount) - taxReservation;
                                }
                                else if (monthc == 4) {
                                    budgetAmount = monthlyBudgetAmount.mayTotal;
                                    bookingAmount = monthlyBookingAmount.mayTotal;

                                    incomeBudgetAmount = dashboardDetailsTagLib.getRoundedValue(incomeBudgetInstance.mayTotal);
                                    expenseBudgetAmount = dashboardDetailsTagLib.getRoundedValue(expenseBudgetInstance.mayTotal);
                                    taxReservation = dashboardDetailsTagLib.getRoundedValue(taxReservationAmountInstance.mayTotal);
                                    netProfit = (incomeBudgetAmount - expenseBudgetAmount) - taxReservation;
                                }
                                else if (monthc == 5) {
                                    budgetAmount = monthlyBudgetAmount.junTotal;
                                    bookingAmount = monthlyBookingAmount.junTotal;

                                    incomeBudgetAmount = dashboardDetailsTagLib.getRoundedValue(incomeBudgetInstance.junTotal);
                                    expenseBudgetAmount = dashboardDetailsTagLib.getRoundedValue(expenseBudgetInstance.junTotal);
                                    taxReservation = dashboardDetailsTagLib.getRoundedValue(taxReservationAmountInstance.junTotal);
                                    netProfit = (incomeBudgetAmount - expenseBudgetAmount) - taxReservation;
                                }
                                else if (monthc == 6) {
                                    budgetAmount = monthlyBudgetAmount.julTotal;
                                    bookingAmount = monthlyBookingAmount.julTotal;

                                    incomeBudgetAmount = dashboardDetailsTagLib.getRoundedValue(incomeBudgetInstance.julTotal);
                                    expenseBudgetAmount = dashboardDetailsTagLib.getRoundedValue(expenseBudgetInstance.julTotal);
                                    taxReservation = dashboardDetailsTagLib.getRoundedValue(taxReservationAmountInstance.julTotal);
                                    netProfit = (incomeBudgetAmount - expenseBudgetAmount) - taxReservation;
                                }
                                else if (monthc == 7) {
                                    budgetAmount = monthlyBudgetAmount.augTotal;
                                    bookingAmount = monthlyBookingAmount.augTotal;

                                    incomeBudgetAmount = dashboardDetailsTagLib.getRoundedValue(incomeBudgetInstance.augTotal);
                                    expenseBudgetAmount = dashboardDetailsTagLib.getRoundedValue(expenseBudgetInstance.augTotal);
                                    taxReservation = dashboardDetailsTagLib.getRoundedValue(taxReservationAmountInstance.augTotal);
                                    netProfit = (incomeBudgetAmount - expenseBudgetAmount) - taxReservation;
                                }
                                else if (monthc == 8) {
                                    budgetAmount = monthlyBudgetAmount.sepTotal;
                                    bookingAmount = monthlyBookingAmount.sepTotal;

                                    incomeBudgetAmount = dashboardDetailsTagLib.getRoundedValue(incomeBudgetInstance.sepTotal);
                                    expenseBudgetAmount = dashboardDetailsTagLib.getRoundedValue(expenseBudgetInstance.sepTotal);
                                    taxReservation = dashboardDetailsTagLib.getRoundedValue(taxReservationAmountInstance.sepTotal);
                                    netProfit = (incomeBudgetAmount - expenseBudgetAmount) - taxReservation;
                                }
                                else if (monthc == 9) {
                                    budgetAmount = monthlyBudgetAmount.octTotal;
                                    bookingAmount = monthlyBookingAmount.octTotal;

                                    incomeBudgetAmount = dashboardDetailsTagLib.getRoundedValue(incomeBudgetInstance.octTotal);
                                    expenseBudgetAmount = dashboardDetailsTagLib.getRoundedValue(expenseBudgetInstance.octTotal);
                                    taxReservation = dashboardDetailsTagLib.getRoundedValue(taxReservationAmountInstance.octTotal);
                                    netProfit = (incomeBudgetAmount - expenseBudgetAmount) - taxReservation;
                                }
                                else if (monthc == 10) {
                                    budgetAmount = monthlyBudgetAmount.novTotal;
                                    bookingAmount = monthlyBookingAmount.novTotal;

                                    incomeBudgetAmount = dashboardDetailsTagLib.getRoundedValue(incomeBudgetInstance.novTotal);
                                    expenseBudgetAmount = dashboardDetailsTagLib.getRoundedValue(expenseBudgetInstance.novTotal);
                                    taxReservation = dashboardDetailsTagLib.getRoundedValue(taxReservationAmountInstance.novTotal);
                                    netProfit = (incomeBudgetAmount - expenseBudgetAmount) - taxReservation;
                                }
                                else if (monthc == 11) {
                                    budgetAmount = monthlyBudgetAmount.decTotal;
                                    bookingAmount = monthlyBookingAmount.decTotal;

                                    incomeBudgetAmount = dashboardDetailsTagLib.getRoundedValue(incomeBudgetInstance.decTotal);
                                    expenseBudgetAmount = dashboardDetailsTagLib.getRoundedValue(expenseBudgetInstance.decTotal);
                                    taxReservation = dashboardDetailsTagLib.getRoundedValue(taxReservationAmountInstance.decTotal);
                                    netProfit = (incomeBudgetAmount - expenseBudgetAmount) - taxReservation;
                                }

                    %>
                    %{--Budget Amount data by months.--}%
                    %{--<% if(v == 0){%>--}%
                        %{--<div class="grpMonthlyAmount">--}%
                            %{--<div class="monthlyBudAmt even">${budgetAmount}</div>--}%
                            %{--<div class="monthlyBudAmt even" >${bookingAmount}</div>--}%
                            %{--<div class="monthlyBudAmt even">${incomeBudgetAmount}</div>--}%
                            %{--<div class="monthlyBudAmt even" >${expenseBudgetAmount}</div>--}%
                            %{--<div class="monthlyBudAmt even">${taxReservation}</div>--}%
                            %{--<div class="monthlyBudAmt even" >${netProfit}</div>--}%
                        %{--</div>--}%
                        %{--<%} else {%>--}%
                        <div class="grpMonthlyAmount">
                            <div class="monthlyBudAmt even">${budgetAmount}</div>
                            <div class="monthlyBudAmt even" >${bookingAmount}</div>
                        </div>
                        %{--<%}%>--}%

                    <% } %>

                </div>
            </div>
            </div>

        </div>
    </div>
<div style="height:10px"> </div>
<%
    }
%>
</div>