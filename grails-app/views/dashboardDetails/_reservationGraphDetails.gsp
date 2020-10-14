<%@ page import="bv.DashboardDetails;bv.DashboardDetailsTagLib;" %>
<%
    def contextPath = request.getServletContext().getContextPath()
    def protocol = request.isSecure() ? "https://" : "http://"
    def host = request.getServerName()
    def port = request.getServerPort()
    def context = request.getServletContext().getContextPath()
    def liveURL = protocol + host + ":" + port + context

    def dashboardDetailsTagLib = new DashboardDetailsTagLib();

//    int nTabInd = 1;
//    int budgetNameId = 29;
    int nTabInd = Integer.parseInt(tabInd.toString());
    //println("nTabInd " + nTabInd);
    int budgetNameId = Integer.parseInt(budgetReservationArr[nTabInd][0].toString());
    //println("budgetNameId " + budgetNameId);
    int bookInvoiceId = budgetReservationArr[nTabInd][2]

    //println("bookInvoiceId " + bookInvoiceId);

    ArrayList glAccountArr = dashboardDetailsTagLib.getReservationGLAccountArray(reservationAccountInstance, budgetNameId,false);
    ArrayList budgetReservationIdArr = dashboardDetailsTagLib.getBudgetReservationArray(reservationAccountInstance, budgetNameId,false);

    //println("glAccountArr " + glAccountArr);

    List<Map> monthlyBudgetAmount = dashboardDetailsTagLib.getMonthlyBudgetAmount(glAccountArr, budgetReservationIdArr, reservationAccountInstance);
    List<Map> monthlyInvoiceAmount = dashboardDetailsTagLib.getPrivateReservationMonthlyInvoiceAmount(glAccountArr, budgetReservationIdArr, reservationInvoiceAccountInstance,true);
%>

<div id="containerResrvHC${tabInd}" style="min-width: 150px; height: 200px; margin: 0 auto"></div>
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

    /*Double positiveJanInvoiceAmount = dashboardDetailsTagLib.getPositiveValue(monthlyInvoiceAmount[0].janAmount);
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

    int janInvoiceAmount,febInvoiceAmount,marInvoiceAmount,aprInvoiceAmount,mayInvoiceAmount,junInvoiceAmount,julInvoiceAmount,augInvoiceAmount,sepInvoiceAmount,
            octInvoiceAmount,novInvoiceAmount,decInvoiceAmount
    janInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].janAmount);
    febInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].febAmount);
    marInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].marAmount);
    aprInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].aprAmount);
    mayInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].mayAmount);
    junInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].junAmount);
    julInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].julAmount);
    augInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].augAmount);
    sepInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].sepAmount);
    octInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].octAmount);
    novInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].novAmount);
    decInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].decAmount);

    janInvoiceAmount = janInvoiceAmount*(-1);
    febInvoiceAmount = febInvoiceAmount*(-1);
    marInvoiceAmount = marInvoiceAmount*(-1);
    aprInvoiceAmount = aprInvoiceAmount*(-1);
    mayInvoiceAmount = mayInvoiceAmount*(-1);
    junInvoiceAmount = junInvoiceAmount*(-1);
    julInvoiceAmount = julInvoiceAmount*(-1);
    augInvoiceAmount = augInvoiceAmount*(-1);
    sepInvoiceAmount = sepInvoiceAmount*(-1);
    octInvoiceAmount = octInvoiceAmount*(-1);
    novInvoiceAmount = novInvoiceAmount*(-1);
    decInvoiceAmount = decInvoiceAmount*(-1);



    Integer nTickInterval = dashboardDetailsTagLib.getTickInterval(monthlyBudgetAmount,monthlyInvoiceAmount);
%>

<script>

    $(function () {
        $('#containerResrvHC${tabInd}').highcharts({
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
                    %{--tickInterval: ${nTickInterval},--}%
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
    def tempGlAccount = ""

    int budgetAmount = 0
    int invoiceAmount = 0

    Integer showCountBudget = 0
    Integer showCountBudgetInvoice = 0

    def budgetItemId = 0
    for (int monthc = 0; monthc < 12; monthc++) {

        if (monthc == 0) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].janAmount);
            invoiceAmount = janInvoiceAmount
            showCountBudget = monthlyBudgetAmount[1].janItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].janItemCount;

            showGLCode = monthlyBudgetAmount[2].janGLCode;

            budgetItemId = monthlyBudgetAmount[3].janBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].janBudDetId;
        }
        else if (monthc == 1) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].febAmount);
            invoiceAmount = febInvoiceAmount
//            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].febAmount);

            showCountBudget = monthlyBudgetAmount[1].febItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].febItemCount;

            showGLCode = monthlyBudgetAmount[2].febGLCode;
            budgetItemId = monthlyBudgetAmount[3].febBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].febBudDetId;
        }
        else if (monthc == 2) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].marAmount);
            invoiceAmount = marInvoiceAmount
//            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].marAmount);

            showCountBudget = monthlyBudgetAmount[1].marItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].marItemCount;

            showGLCode = monthlyBudgetAmount[2].marGLCode;
            budgetItemId = monthlyBudgetAmount[3].marBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].marBudDetId;
        }
        else if (monthc == 3) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].aprAmount);
            invoiceAmount = aprInvoiceAmount
//            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].aprAmount);

            showCountBudget = monthlyBudgetAmount[1].aprItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].aprItemCount;

            showGLCode = monthlyBudgetAmount[2].aprGLCode;
            budgetItemId = monthlyBudgetAmount[3].aprBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].aprBudDetId;
        }
        else if (monthc == 4) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].mayAmount);
            invoiceAmount = mayInvoiceAmount
//            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].mayAmount);

            showCountBudget = monthlyBudgetAmount[1].mayItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].mayItemCount;

            showGLCode = monthlyBudgetAmount[2].mayGLCode;
            budgetItemId = monthlyBudgetAmount[3].mayBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].mayBudDetId;
        }
        else if (monthc == 5) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].junAmount);
            invoiceAmount = junInvoiceAmount
//            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].junAmount);

            showCountBudget = monthlyBudgetAmount[1].junItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].junItemCount;

            showGLCode = monthlyBudgetAmount[2].junGLCode;
            budgetItemId = monthlyBudgetAmount[3].junBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].junBudDetId;
        }
        else if (monthc == 6) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].julAmount);
            invoiceAmount = julInvoiceAmount
//            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].julAmount);

            showCountBudget = monthlyBudgetAmount[1].julItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].julItemCount;

            showGLCode = monthlyBudgetAmount[2].julGLCode;
            budgetItemId = monthlyBudgetAmount[3].julBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].julBudDetId;
        }
        else if (monthc == 7) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].augAmount);
            invoiceAmount = augInvoiceAmount
//            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].augAmount);

            showCountBudget = monthlyBudgetAmount[1].augItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].augItemCount;

            showGLCode = monthlyBudgetAmount[2].augGLCode;
            budgetItemId = monthlyBudgetAmount[3].augBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].augBudDetId;
        }
        else if (monthc == 8) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].sepAmount);
            invoiceAmount = sepInvoiceAmount
//            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].sepAmount);

            showCountBudget = monthlyBudgetAmount[1].sepItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].sepItemCount;

            showGLCode = monthlyBudgetAmount[2].sepGLCode;
            budgetItemId = monthlyBudgetAmount[3].sepBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].sepBudDetId;
        }
        else if (monthc == 9) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].octAmount);
            invoiceAmount = octInvoiceAmount
//            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].octAmount);

            showCountBudget = monthlyBudgetAmount[1].octItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].octItemCount;

            showGLCode = monthlyBudgetAmount[2].octGLCode;
            budgetItemId = monthlyBudgetAmount[3].octBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].octBudDetId;
        }
        else if (monthc == 10) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].novAmount);
            invoiceAmount = novInvoiceAmount
//            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].novAmount);

            showCountBudget = monthlyBudgetAmount[1].novItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].novItemCount;

            showGLCode = monthlyBudgetAmount[2].novGLCode;
            budgetItemId = monthlyBudgetAmount[3].novBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].novBudDetId;
        }
        else if (monthc == 11) {
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].decAmount);
            invoiceAmount = decInvoiceAmount
//            invoiceAmount = dashboardDetailsTagLib.getRoundedValue(monthlyInvoiceAmount[0].decAmount);

            showCountBudget = monthlyBudgetAmount[1].decItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].decItemCount;

            showGLCode = monthlyBudgetAmount[2].decGLCode;
            budgetItemId = monthlyBudgetAmount[3].decBudId;
            budgetItemDetailsId = monthlyBudgetAmount[4].decBudDetId;
        }

        def bookingPeriod = (monthc + 1)
        def vendorId = budgetNameId

        def listGLCode = showGLCode.split(",");
        if(listGLCode.size() > 0){
            tempGlAccount = listGLCode[0]
        }else{
            tempGlAccount = showGLCode
        }
%>
%{--Budget Amount data by months.--}%
<div class="grpMonthlyAmount">
    <div class="monthlyBudAmt ${(loopmonth % 2) == 0 ? 'odd' : 'even'}">
        <% if (showCountBudget > 1) {
            def multipleBudgetItemsURL = liveURL + '/dashboardReservationList/invoice/' + vendorId + '?g=' + tempGlAccount + '&m=' + (monthc + 1)
            def toolTipText = "Total Budget Item: " + showCountBudget +" \nGL Code: " + showGLCode;
        %>
        <a href="${multipleBudgetItemsURL}" target="_parent" title="${toolTipText}"
           class="tooltip"><b>${budgetAmount}</b><img width="10" height="10" alt="Add" src="${contextPath}/images/edit.png"></a>
        <% } else if (showCountBudget == 0) {
            def budgetItemURL = liveURL + '/budgetItemReservation/list' + '?bookingPeriod=' + bookingPeriod + '&reservationId=' + vendorId+'&dashboardFlag=1'
        %>

        <a href="${budgetItemURL}" target="_parent" title="Create Expense Budget"
           class="tooltip"><b><g:message code="bv.dashboard.create.label" default="Create" /></b>
            <img width="10" height="10" alt="Add" src="${contextPath}/images/add.png"></a>

        <% } else if (showCountBudget == 1) {
            def budgetExpenseURL = liveURL + '/budgetItemReservation/list' + '?editId=' + budgetItemId + '&bookingPeriod='+bookingPeriod + '&reservationId='+vendorId + '&budgetReservationId='+vendorId + '&journalId='+''
            def toolTipText = "Total Budget Item: " + showCountBudget +" \nGL Code: " + showGLCode;
        %>

        <a href="${budgetExpenseURL}" target="_parent" title="${toolTipText}"
           class="tooltip"><b>${budgetAmount}</b><img width="10" height="10" alt="Add" src="${contextPath}/images/edit.png"></a>
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
                    expenseInvoiceURL = liveURL + '/invoiceExpense/list' + '?bookInvoiceId=' + budgetItemId + '&bookingPeriod=' + bookingPeriod + '&budgetVendorId=' + budgetVendorId + '&vendorId=' + vendorId + '&budgetItemDetailsId=' + budgetItemDetailsId
                }else{
                    expenseInvoiceURL = liveURL + '/invoiceExpense/receiptList' + '?bookInvoiceId=' + budgetItemId + '&bookingPeriod=' + bookingPeriod + '&budgetVendorId=' + budgetVendorId + '&vendorId=' + vendorId + '&budgetItemDetailsId=' + budgetItemDetailsId
                }
        %>
        <% if (showCountBudget > 0) {
            if (invoiceAmount == 0) {
        %>

        <div title="Total Reservation Spending: ${showCountBudgetInvoice}" class=""><b>0</b></div>
            <img width="10" height="10" alt="Add" src="${contextPath}/images/empty.png">

        <% } else { %>
        %{--<div title="Total Reservation Spending: ${showCountBudgetInvoice}" class=""><b>${invoiceAmount}</b></div>
           <img width="10" height="10" alt="Add" src="${contextPath}/images/empty.png">--}%

        <a href="${liveURL}/budgetItemReservation/editProcessAmount/?bookingPeriod=${bookingPeriod}&budgetMasterId=${vendorId}" target="_parent" title="Total Reservation Spending: ${showCountBudgetInvoice}"   class="tooltip">
            <b>${invoiceAmount}</b>
            <img width="10" height="10" alt="Add" src="${contextPath}/images/edit.png"></a>

        <% }
        } %>
    </div>
</div>
<%
    }
%>

</div>
</div>