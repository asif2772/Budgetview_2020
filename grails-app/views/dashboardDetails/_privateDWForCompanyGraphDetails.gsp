<%@ page import="bv.DashboardDetails;bv.DashboardDetailsTagLib;" %>

<%
    def contextPath = request.getServletContext().getContextPath()
    def protocol = request.isSecure() ? "https://" : "http://"
    def host = request.getServerName()
    def port = request.getServerPort()
    def context = request.getServletContext().getContextPath()
    def liveURL = protocol + host + ":" + port + context

    def dashboardDetailsTagLib = new DashboardDetailsTagLib();
    int nTabInd = Integer.parseInt(tabInd);

    //int nTotalProcessAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.allTotal)

    //List<Map> monthlyBudgetAmount = dashboardDetailsTagLib.getMonthlyBudgetAmount(glAccountArr,budgetCustomerIdArr,customerAccountInstance);
    //List<Map> monthlyInvoiceAmount = dashboardDetailsTagLib.getMonthlyInvoiceAmount(null,null,customerInvoiceAccountInstance,false);
%>

<div id="containerHC${tabInd}" style="min-width: 150px; height: 200px; margin: 0 auto"></div>
<%
    int janBudgetAmount = 0;//dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].janAmount);
    int febBudgetAmount = 0;//dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].febAmount);
    int marBudgetAmount = 0;//dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].marAmount);
    int aprBudgetAmount = 0;//dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].aprAmount);
    int mayBudgetAmount = 0;//dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].mayAmount);
    int junBudgetAmount = 0;//dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].junAmount);
    int julBudgetAmount = 0;//dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].julAmount);
    int augBudgetAmount = 0;//dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].augAmount);
    int sepBudgetAmount = 0;//dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].sepAmount);
    int octBudgetAmount = 0;//dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].octAmount);
    int novBudgetAmount = 0;//dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].novAmount);
    int decBudgetAmount = 0;//dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].decAmount);

//        println("janBudgetAmount : "+janBudgetAmount);

    int janInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.janAmount);
    int febInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.febAmount);
    int marInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.marAmount);
    int aprInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.aprAmount);
    int mayInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.mayAmount);
    int junInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.junAmount);
    int julInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.julAmount);
    int augInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.augAmount);
    int sepInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.sepAmount);
    int octInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.octAmount);
    int novInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.novAmount);
    int decInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.decAmount);

    Integer nTickInterval = dashboardDetailsTagLib.getTickInterval(monthlyBudgetAmount,monthlyInvoiceAmount);
%>

<script>

    $(function() {
        $('#containerHC${tabInd}').highcharts({
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
            yAxis: [{
                min: 0,
                offset: 50,
                %{--tickInterval: ${nTickInterval},--}%
                title: {
                    text: 'Amount',
                    align: 'middle'

                },
                labels: {
                    overflow: 'justify'
                }
            }, {
                title: {
                    text: ''
                },
                opposite: true
            }],
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
            series: [{
                name:  'Budget Amount',
                color: 'rgba(120,120,120,1)',
                data: [${janBudgetAmount}, ${febBudgetAmount},${marBudgetAmount}, ${aprBudgetAmount},
                    ${mayBudgetAmount},${junBudgetAmount},${julBudgetAmount}, ${augBudgetAmount},
                    ${sepBudgetAmount},${octBudgetAmount},${novBudgetAmount}, ${decBudgetAmount}],
                pointPadding: 0.2,
                groupPadding: 0,
                pointPlacement: -0.2
            }, {
                name:  'Invoice Amount',
                color: 'rgba(18,243,14,.9)',
                data: [{y:${janInvoiceAmount},color:'${dashboardDetailsTagLib.getInvoiceIncomeBarColor(janInvoiceAmount,janBudgetAmount)}'},
                    {y:${febInvoiceAmount},color:'${dashboardDetailsTagLib.getInvoiceIncomeBarColor(febInvoiceAmount,febBudgetAmount)}'},
                    {y:${marInvoiceAmount},color:'${dashboardDetailsTagLib.getInvoiceIncomeBarColor(marInvoiceAmount,marBudgetAmount)}'},
                    {y:${aprInvoiceAmount},color:'${dashboardDetailsTagLib.getInvoiceIncomeBarColor(aprInvoiceAmount,aprBudgetAmount)}'},
                    {y:${mayInvoiceAmount},color:'${dashboardDetailsTagLib.getInvoiceIncomeBarColor(mayInvoiceAmount,mayBudgetAmount)}'},
                    {y:${junInvoiceAmount},color:'${dashboardDetailsTagLib.getInvoiceIncomeBarColor(junInvoiceAmount,junBudgetAmount)}'},
                    {y:${julInvoiceAmount},color:'${dashboardDetailsTagLib.getInvoiceIncomeBarColor(julInvoiceAmount,julBudgetAmount)}'},
                    {y:${augInvoiceAmount},color:'${dashboardDetailsTagLib.getInvoiceIncomeBarColor(augInvoiceAmount,augBudgetAmount)}'},
                    {y:${sepInvoiceAmount},color:'${dashboardDetailsTagLib.getInvoiceIncomeBarColor(sepInvoiceAmount,sepBudgetAmount)}'},
                    {y:${octInvoiceAmount},color:'${dashboardDetailsTagLib.getInvoiceIncomeBarColor(octInvoiceAmount,octBudgetAmount)}'},
                    {y:${novInvoiceAmount},color:'${dashboardDetailsTagLib.getInvoiceIncomeBarColor(novInvoiceAmount,novBudgetAmount)}'},
                    {y:${decInvoiceAmount},color:'${dashboardDetailsTagLib.getInvoiceIncomeBarColor(decInvoiceAmount,decBudgetAmount)}'}
                ],
                pointPadding: 0.3,
                groupPadding: 0,
                pointPlacement: -0.2
            }]
        });
    });

</script>

<div class="footerAmount">
<div class="labelOfBudgetInvoice">
    <div class="monthAmountLabel"><g:message code="bv.dashboard.budget.label" default="BUDGET" />:</div>
    <div class="monthAmountLabel" style="margin-top: 18px;"><g:message code="bv.dashboard.invoice.label" default="INVOICE" />:</div>
</div>

<div class="monthItemsValue">
<%
    Integer loopmonth = 1
    def tempGlAccount = ""

    int budgetAmount = 0
    int invoiceAmount = 0

    Integer showCountBudget = 0
    Integer showCountBudgetInvoice = 0

    def budgetItemId = 0;
    def budgetItemDetailsId = 0;
    String showGLCode = "";

    for (int monthc = 0; monthc < 12; monthc++) {

        if (monthc == 0) {
            //budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].janAmount);
            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.janAmount);
        }
        else if (monthc == 1) {
            //budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].febAmount);
            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.febAmount);
        }
        else if (monthc == 2) {
            //budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].marAmount);
            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.marAmount);
        }
        else if (monthc == 3) {
            //budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].aprAmount);
            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.aprAmount);
        }
        else if (monthc == 4) {
            //budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].mayAmount);
            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.mayAmount);
        }
        else if (monthc == 5) {
            //budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].junAmount);
            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.junAmount);
        }
        else if (monthc == 6) {
            //budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].julAmount);
            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.julAmount);
        }
        else if (monthc == 7) {
            //budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].augAmount);
            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.augAmount);
        }
        else if (monthc == 8) {
            //budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].sepAmount);
            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.sepAmount);
        }
        else if (monthc == 9) {
            //budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].octAmount);
            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.octAmount);
        }
        else if (monthc == 10) {
            //budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].novAmount);
            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.novAmount);
        }
        else if (monthc == 11) {
            //budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].decAmount);
            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.decAmount);
        }

%>
%{--Budget Amount data by months.--}%
<div class="grpMonthlyAmount">
    <div class="monthlyBudAmt ${(loopmonth % 2) == 0 ? 'odd' : 'even'}"><b>${budgetAmount}</b>
    </div>
    %{--Invoice income.--}%
    <div class="monthlyInvAmt ${(loopmonth % 2) == 0 ? 'odd' : 'even'}">

        <%
            def incomeInvoiceURL="${liveURL}/budgetItemPrivate/editProcessAmount/?privateSpendingId=${privateSpendingId}&bookingPeriod=${monthc+1}&budgetMasterId=${masterId}"
        %>
        <a href="${incomeInvoiceURL}" target="_parent" title="Total Income Invoice: ${showCountBudgetInvoice}" class="tooltip" >
            <b>${invoiceAmount}</b>
            <img width="10" height="10" alt="Add" src="${contextPath}/images/edit.png">
        </a>

    </div>
</div>
<%
    }
%>

</div>
</div>