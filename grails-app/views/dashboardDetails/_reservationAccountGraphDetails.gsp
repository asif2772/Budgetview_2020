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
    def budgetAccountCode = reservationAHWArr[nTabInd][0];
    def budgetNameId = reservationAHWArr[nTabInd][3];

//    List<Map> monthlyBudgetAmount = dashboardDetailsTagLib.getMonthlyReservationBudgetAmountAHWise(budgetNameId, reservationAccountInstance);

    List<Map> monthlyBudgetAmount = dashboardDetailsTagLib.getMonthlyReservationBudgetAmountAHWise(budgetAccountCode, reservationAccountInstance);
    List<Map> monthlyInvoiceAmount = dashboardDetailsTagLib.getMonthlyReservationInvoiceAmountAHWise(glAccountArr, budgetNameId, reservationInvoiceAccountInstance,true);

%>

<div id="containerVenHC${tabInd}" style="min-width: 150px; height: 200px; margin: 0 auto"></div>
<%
    /////////////////SKB----TOTAL SUM//////////////////
    int janBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].janAmount);
    int febBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].febAmount);
    int marBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].marAmount);
    int aprBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].aprAmount);
    int mayBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].mayAmount);
    int junBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].junAmount);
    int julBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].julAmount);
    int augBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].augAmount);
    int sepBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].sepAmount);
    int octBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].octAmount);
    int novBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].novAmount);
    int decBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].decAmount);

   /* Double positiveJanInvoiceAmount = dashboardDetailsTagLib.getPositiveValue(monthlyInvoiceAmount[0].janAmount);
    Double positiveFebInvoiceAmount = dashboardDetailsTagLib.getPositiveValue(monthlyInvoiceAmount[0].febAmount);
    Double positiveMarInvoiceAmount = dashboardDetailsTagLib.getPositiveValue(monthlyInvoiceAmount[0].marAmount);
    Double positiveAprInvoiceAmount = dashboardDetailsTagLib.getPositiveValue(monthlyInvoiceAmount[0].aprAmount);
    Double positiveMayInvoiceAmount = dashboardDetailsTagLib.getPositiveValue(monthlyInvoiceAmount[0].mayAmount);
    Double positiveJunInvoiceAmount = dashboardDetailsTagLib.getPositiveValue(monthlyInvoiceAmount[0].junAmount);
    Double positiveJulInvoiceAmount = dashboardDetailsTagLib.getPositiveValue(monthlyInvoiceAmount[0].julAmount);
    Double positiveAugInvoiceAmount = dashboardDetailsTagLib.getPositiveValue(monthlyInvoiceAmount[0].augAmount);
    Double positiveSepInvoiceAmount = dashboardDetailsTagLib.getPositiveValue(monthlyInvoiceAmount[0].sepAmount);
    Double positiveOctInvoiceAmount = dashboardDetailsTagLib.getPositiveValue(monthlyInvoiceAmount[0].octAmount);
    Double positiveNovInvoiceAmount = dashboardDetailsTagLib.getPositiveValue(monthlyInvoiceAmount[0].novAmount);
    Double positiveDecInvoiceAmount = dashboardDetailsTagLib.getPositiveValue(monthlyInvoiceAmount[0].decAmount);*/


    int janInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].janAmount);
    int febInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].febAmount);
    int marInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].marAmount);
    int aprInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].aprAmount);
    int mayInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].mayAmount);
    int junInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].junAmount);
    int julInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].julAmount);
    int augInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].augAmount);
    int sepInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].sepAmount);
    int octInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].octAmount);
    int novInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].novAmount);
    int decInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].decAmount);

    Integer nTickInterval = dashboardDetailsTagLib.getTickInterval(monthlyBudgetAmount,monthlyInvoiceAmount);
%>

<script>

    $(function () {
        $('#containerVenHC${tabInd}').highcharts({
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
                    min: 0,
                    offset: 50,
                    tickInterval: ${nTickInterval},
                    title: {
                        text: 'Amount',
                        align: 'middle'

                    },
                    labels: {
                        overflow: 'justify'
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
                    name: 'Budget Amount',
                    color: 'rgba(120,120,120,1)',
                    data: [${janBudgetAmount}, ${febBudgetAmount}, ${marBudgetAmount}, ${aprBudgetAmount},
                        ${mayBudgetAmount}, ${junBudgetAmount}, ${julBudgetAmount}, ${augBudgetAmount},
                        ${sepBudgetAmount}, ${octBudgetAmount}, ${novBudgetAmount}, ${decBudgetAmount}],
                    pointPadding: 0.2,
                    groupPadding: 0,
                    pointPlacement: -0.2
                },
                {
                    name: 'Invoice Amount',
                    color: 'rgba(18,243,14,.9)',
                    data: [
                        {y:${janInvoiceAmount}, color: '${dashboardDetailsTagLib.getInvoiceExpenseBarColor(janInvoiceAmount,janBudgetAmount)}'},
                        {y:${febInvoiceAmount}, color: '${dashboardDetailsTagLib.getInvoiceExpenseBarColor(febInvoiceAmount,febBudgetAmount)}'},
                        {y:${marInvoiceAmount}, color: '${dashboardDetailsTagLib.getInvoiceExpenseBarColor(marInvoiceAmount,marBudgetAmount)}'},
                        {y:${aprInvoiceAmount}, color: '${dashboardDetailsTagLib.getInvoiceExpenseBarColor(aprInvoiceAmount,aprBudgetAmount)}'},
                        {y:${mayInvoiceAmount}, color: '${dashboardDetailsTagLib.getInvoiceExpenseBarColor(mayInvoiceAmount,mayBudgetAmount)}'},
                        {y:${junInvoiceAmount}, color: '${dashboardDetailsTagLib.getInvoiceExpenseBarColor(junInvoiceAmount,junBudgetAmount)}'},
                        {y:${julInvoiceAmount}, color: '${dashboardDetailsTagLib.getInvoiceExpenseBarColor(julInvoiceAmount,julBudgetAmount)}'},
                        {y:${augInvoiceAmount}, color: '${dashboardDetailsTagLib.getInvoiceExpenseBarColor(augInvoiceAmount,augBudgetAmount)}'},
                        {y:${sepInvoiceAmount}, color: '${dashboardDetailsTagLib.getInvoiceExpenseBarColor(sepInvoiceAmount,sepBudgetAmount)}'},
                        {y:${octInvoiceAmount}, color: '${dashboardDetailsTagLib.getInvoiceExpenseBarColor(octInvoiceAmount,octBudgetAmount)}'},
                        {y:${novInvoiceAmount}, color: '${dashboardDetailsTagLib.getInvoiceExpenseBarColor(novInvoiceAmount,novBudgetAmount)}'},
                        {y:${decInvoiceAmount}, color: '${dashboardDetailsTagLib.getInvoiceExpenseBarColor(decInvoiceAmount,decBudgetAmount)}'}
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
    <div class="monthAmountLabel"><g:message code="bv.dashboard.budget.label" default="BUDGET" /> :</div>
    <div class="monthAmountLabel" style="margin-top: 18px;"><g:message code="bv.dashboard.invoice.label" default="INVOICE " /> :</div>
</div>

<div class="monthItemsValue">
<%
    Integer loopmonth = 1
    def showVenCusName = ""

    int budgetAmount = 0
    int invoiceAmount = 0

    Integer showCountBudget = 0
    Integer showCountBudgetInvoice = 0

    def budgetItemId = 0
    def vendorId = 0

    for (int monthc = 0; monthc < 12; monthc++) {

        if (monthc == 0) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].janAmount);
            invoiceAmount = janInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].janItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].janItemCount;

            showVenCusName = monthlyBudgetAmount[2].janVenCusName;
            vendorId = monthlyBudgetAmount[5].janVenCusId;

            budgetItemId = monthlyBudgetAmount[3].janBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].janBudDetId;
        }
        else if (monthc == 1) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].febAmount);
            invoiceAmount = febInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].febItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].febItemCount;

            showVenCusName = monthlyBudgetAmount[2].febVenCusName;
            vendorId = monthlyBudgetAmount[5].febVenCusId;

            budgetItemId = monthlyBudgetAmount[3].febBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].febBudDetId;
        }
        else if (monthc == 2) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].marAmount);
            invoiceAmount = marInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].marItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].marItemCount;

            showVenCusName = monthlyBudgetAmount[2].marVenCusName;
            vendorId = monthlyBudgetAmount[5].marVenCusId;

            budgetItemId = monthlyBudgetAmount[3].marBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].marBudDetId;
        }
        else if (monthc == 3) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].aprAmount);
            invoiceAmount = aprInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].aprItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].aprItemCount;

            showVenCusName = monthlyBudgetAmount[2].aprVenCusName;
            vendorId = monthlyBudgetAmount[5].aprVenCusId;

            budgetItemId = monthlyBudgetAmount[3].aprBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].aprBudDetId;
        }
        else if (monthc == 4) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].mayAmount);
            invoiceAmount = mayInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].mayItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].mayItemCount;

            showVenCusName = monthlyBudgetAmount[2].mayVenCusName;
            vendorId = monthlyBudgetAmount[5].mayVenCusId;

            budgetItemId = monthlyBudgetAmount[3].mayBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].mayBudDetId;
        }
        else if (monthc == 5) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].junAmount);
            invoiceAmount = junInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].junItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].junItemCount;

            showVenCusName = monthlyBudgetAmount[2].junVenCusName;
            vendorId = monthlyBudgetAmount[5].junVenCusId;

            budgetItemId = monthlyBudgetAmount[3].junBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].junBudDetId;
        }
        else if (monthc == 6) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].julAmount);
            invoiceAmount = julInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].julItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].julItemCount;

            showVenCusName = monthlyBudgetAmount[2].julVenCusName;
            vendorId = monthlyBudgetAmount[5].julVenCusId;

            budgetItemId = monthlyBudgetAmount[3].julBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].julBudDetId;
        }
        else if (monthc == 7) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].augAmount);
            invoiceAmount = augInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].augItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].augItemCount;

            showVenCusName = monthlyBudgetAmount[2].augVenCusName;
            vendorId = monthlyBudgetAmount[5].augVenCusId;

            budgetItemId = monthlyBudgetAmount[3].augBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].augBudDetId;
        }
        else if (monthc == 8) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].sepAmount);
            invoiceAmount = sepInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].sepItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].sepItemCount;

            showVenCusName = monthlyBudgetAmount[2].sepVenCusName;
            vendorId = monthlyBudgetAmount[5].sepVenCusId;

            budgetItemId = monthlyBudgetAmount[3].sepBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].sepBudDetId;
        }
        else if (monthc == 9) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].octAmount);
            invoiceAmount = octInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].octItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].octItemCount;

            showVenCusName = monthlyBudgetAmount[2].octVenCusName;
            vendorId = monthlyBudgetAmount[5].octVenCusId;

            budgetItemId = monthlyBudgetAmount[3].octBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].octBudDetId;
        }
        else if (monthc == 10) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].novAmount);
            invoiceAmount = novInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].novItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].novItemCount;

            showVenCusName = monthlyBudgetAmount[2].novVenCusName;
            vendorId = monthlyBudgetAmount[5].novVenCusId;

            budgetItemId = monthlyBudgetAmount[3].novBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].novBudDetId;
        }
        else if (monthc == 11) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].decAmount);
            invoiceAmount = decInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].decItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].decItemCount;

            showVenCusName = monthlyBudgetAmount[2].decVenCusName;
            vendorId = monthlyBudgetAmount[5].decVenCusId;

            budgetItemId = monthlyBudgetAmount[3].decBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].decBudDetId;
        }

        def bookingPeriod = (monthc + 1)

        def tempVenCusName;
        def listVenCusName = showVenCusName.split(",");
        if(listVenCusName.size() > 0){
            tempVenCusName = listVenCusName[0]
        }else{
            tempVenCusName = showVenCusName
        }
%>

%{--Budget Amount data by months.--}%
<div class="grpMonthlyAmount">
    <div class="monthlyBudAmt ${(loopmonth % 2) == 0 ? 'odd' : 'even'}">

        <% if (showCountBudget > 1) {
            def multipleBudgetItemsURL = liveURL + '/dashboardExpanseList/invoice/' + vendorId + '?g=' + budgetAccountCode + '&m=' + (monthc + 1)
            def toolTipText = "Total Budget Item: " + showCountBudget +" \n Budget Name : " + tempVenCusName;
        %>
        <a href="${multipleBudgetItemsURL}" target="_parent" title="${toolTipText}"
           class="tooltip"><b>${budgetAmount}</b><img width="10" height="10" alt="Add" src="${contextPath}/images/edit.png"></a></a>
        <% } else if (showCountBudget == 0) {

            def budgetItemURL = liveURL + '/budgetItemReservation/list' + '?bookingPeriod=' + bookingPeriod + '&reservationId='+budgetNameId+'&dashboardFlag= 1'
        %>
        <a href="${budgetItemURL}" target="_parent" title="Create Expense Budget"
           class="tooltip"><b><g:message code="bv.dashboard.create.label" default="Create" /></b>
            <img width="10" height="10" alt="Add" src="${contextPath}/images/add.png"></a></a>
        <% } else if (showCountBudget == 1) {
            def budgetExpenseURL = liveURL + '/budgetItemReservation/list' + '?editId=' + budgetItemId + '&bookingPeriod='+bookingPeriod + '&reservationId='+vendorId + '&budgetReservationId='+vendorId + '&journalId=""'
            def toolTipText = "Total Budget Item: " + showCountBudget +" \n Budget Name : " + tempVenCusName;
        %>
        <a href="${budgetExpenseURL}" target="_parent" title="${toolTipText}"
           class="tooltip"><b>${budgetAmount}</b><img width="10" height="10" alt="Add" src="${contextPath}/images/edit.png"></a></a>
        <% } %>
    </div>
    %{--Invoice income.--}%
    <div class="monthlyInvAmt ${(loopmonth % 2) == 0 ? 'odd' : 'even'}">
        <%
                def expenseInvoiceURL = "";
                def invOrReceipt = session.invOrReceipt;
                if(invOrReceipt == null){
                    invOrReceipt = "invoice";
                }

                if(invOrReceipt == "invoice"){
                    expenseInvoiceURL = liveURL + '/dashboard/index'
                }else{
                    expenseInvoiceURL = liveURL + '/dashboard/index'
                }
        %>
        <% if (showCountBudget > 0) {
            if (invoiceAmount == 0) {
        %>

        <div title="Total Reservation Spending: ${showCountBudgetInvoice}"class="tooltip"><b>0</b></div>
    </a>
        <% } else { %>

        <div title="Total Reservation Spending: ${showCountBudgetInvoice}" class="tooltip"><b>${invoiceAmount}</b></div>

        <% }
        } %>
    </div>
</div>
<%
    }
%>

</div>
</div>