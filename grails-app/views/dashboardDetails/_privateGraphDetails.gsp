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
    int budgetPrivateMasterId = privateBudgetArr[nTabInd][0];
    def budgetType = privateBudgetArr[nTabInd][4];
    ArrayList glAccountArr = dashboardDetailsTagLib.getCustomerGLAccountArray(privateBudAmountInstance,budgetPrivateMasterId);
    ArrayList budgetPrivateIdArr = dashboardDetailsTagLib.getBudgetCustomerIdArray(privateBudAmountInstance,budgetPrivateMasterId);

    List<Map> monthlyBudgetAmount = dashboardDetailsTagLib.getMonthlyPrivateBudgetAmount(budgetPrivateMasterId,privateBudAmountInstance);
    List<Map> monthlyInvoiceAmount = dashboardDetailsTagLib.getPrivateMonthlyInvoiceAmount(budgetPrivateMasterId,invoiceIncomePrivateInstance);
%>

<div id="containerHC${tabInd}" style="min-width: 150px; height: 200px; margin: 0 auto"></div>
<%
//    def positiveBudgetAmount= dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].janAmount)
    /*def positiveJanBudgetAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].janAmount,budgetType);
    def positiveFebBudgetAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].febAmount,budgetType);
    def positiveMarBudgetAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].marAmount,budgetType);
    def positiveAprBudgetAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].aprAmount,budgetType);
    def positiveMayBudgetAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].mayAmount,budgetType);
    def positiveJunBudgetAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].junAmount,budgetType);
    def positiveJulBudgetAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].julAmount,budgetType);
    def positiveAugBudgetAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].augAmount,budgetType);
    def positiveSepBudgetAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].sepAmount,budgetType);
    def positiveOctBudgetAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].octAmount,budgetType);
    def positiveNovBudgetAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].novAmount,budgetType);
    def positiveDecBudgetAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].decAmount,budgetType);*/

    int janBudgetAmount,febBudgetAmount, marBudgetAmount, aprBudgetAmount,mayBudgetAmount,junBudgetAmount,julBudgetAmount,augBudgetAmount,sepBudgetAmount,octBudgetAmount,novBudgetAmount,decBudgetAmount
    if(budgetType == 1){
        janBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].janAmount);
        febBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].febAmount);
        marBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].marAmount);
        aprBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].aprAmount);
        mayBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].mayAmount);
        junBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].junAmount);
        julBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].julAmount);
        augBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].augAmount);
        sepBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].sepAmount);
        octBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].octAmount);
        novBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].novAmount);
        decBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].decAmount);
    }else{
        janBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].janAmount*(-1));
        febBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].febAmount*(-1));
        marBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].marAmount*(-1));
        aprBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].aprAmount*(-1));
        mayBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].mayAmount*(-1));
        junBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].junAmount*(-1));
        julBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].julAmount*(-1));
        augBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].augAmount*(-1));
        sepBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].sepAmount*(-1));
        octBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].octAmount*(-1));
        novBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].novAmount*(-1));
        decBudgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].decAmount*(-1));
    }

   /* def positiveJanInvoiceAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyInvoiceAmount[0].janAmount,budgetType);
    def positiveFebInvoiceAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyInvoiceAmount[0].febAmount,budgetType);
    def positiveMarInvoiceAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyInvoiceAmount[0].marAmount,budgetType);
    def positiveAprInvoiceAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyInvoiceAmount[0].aprAmount,budgetType);
    def positiveMayInvoiceAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyInvoiceAmount[0].mayAmount,budgetType);
    def positiveJunInvoiceAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyInvoiceAmount[0].junAmount,budgetType);
    def positiveJulInvoiceAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyInvoiceAmount[0].julAmount,budgetType);
    def positiveAugInvoiceAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyInvoiceAmount[0].augAmount,budgetType);
    def positiveSepInvoiceAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyInvoiceAmount[0].sepAmount,budgetType);
    def positiveOctInvoiceAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyInvoiceAmount[0].octAmount,budgetType);
    def positiveNovInvoiceAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyInvoiceAmount[0].novAmount,budgetType);
    def positiveDecInvoiceAmount = dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyInvoiceAmount[0].decAmount,budgetType);*/

     def janInvoiceAmount,febInvoiceAmount,marInvoiceAmount,aprInvoiceAmount,mayInvoiceAmount,junInvoiceAmount,julInvoiceAmount,augInvoiceAmount,sepInvoiceAmount,octInvoiceAmount,novInvoiceAmount,decInvoiceAmount
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

    if(budgetType>1){
        janInvoiceAmount = janInvoiceAmount * (-1)
        febInvoiceAmount = febInvoiceAmount * (-1)
        marInvoiceAmount = marInvoiceAmount * (-1)
        aprInvoiceAmount = aprInvoiceAmount * (-1)
        mayInvoiceAmount = mayInvoiceAmount * (-1)
        junInvoiceAmount = junInvoiceAmount * (-1)
        julInvoiceAmount = julInvoiceAmount * (-1)
        augInvoiceAmount = augInvoiceAmount * (-1)
        sepInvoiceAmount = sepInvoiceAmount * (-1)
        octInvoiceAmount = octInvoiceAmount * (-1)
        novInvoiceAmount = novInvoiceAmount * (-1)
        decInvoiceAmount = decInvoiceAmount * (-1)
    }

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
                data: [{y:${janInvoiceAmount},color:'${dashboardDetailsTagLib.getPrivateInvoiceBarColor(monthlyInvoiceAmount[0].janAmount,monthlyBudgetAmount[0].janAmount)}'},
                    {y:${febInvoiceAmount},color:'${dashboardDetailsTagLib.getPrivateInvoiceBarColor(monthlyInvoiceAmount[0].febAmount,monthlyBudgetAmount[0].febAmount)}'},
                    {y:${marInvoiceAmount},color:'${dashboardDetailsTagLib.getPrivateInvoiceBarColor(monthlyInvoiceAmount[0].marAmount,monthlyBudgetAmount[0].marAmount)}'},
                    {y:${aprInvoiceAmount},color:'${dashboardDetailsTagLib.getPrivateInvoiceBarColor(monthlyInvoiceAmount[0].aprAmount,monthlyBudgetAmount[0].aprAmount)}'},
                    {y:${mayInvoiceAmount},color:'${dashboardDetailsTagLib.getPrivateInvoiceBarColor(monthlyInvoiceAmount[0].mayAmount,monthlyBudgetAmount[0].mayAmount)}'},
                    {y:${junInvoiceAmount},color:'${dashboardDetailsTagLib.getPrivateInvoiceBarColor(monthlyInvoiceAmount[0].junAmount,monthlyBudgetAmount[0].junAmount)}'},
                    {y:${julInvoiceAmount},color:'${dashboardDetailsTagLib.getPrivateInvoiceBarColor(monthlyInvoiceAmount[0].julAmount,monthlyBudgetAmount[0].julAmount)}'},
                    {y:${augInvoiceAmount},color:'${dashboardDetailsTagLib.getPrivateInvoiceBarColor(monthlyInvoiceAmount[0].augAmount,monthlyBudgetAmount[0].augAmount)}'},
                    {y:${sepInvoiceAmount},color:'${dashboardDetailsTagLib.getPrivateInvoiceBarColor(monthlyInvoiceAmount[0].sepAmount,monthlyBudgetAmount[0].sepAmount)}'},
                    {y:${octInvoiceAmount},color:'${dashboardDetailsTagLib.getPrivateInvoiceBarColor(monthlyInvoiceAmount[0].octAmount,monthlyBudgetAmount[0].octAmount)}'},
                    {y:${novInvoiceAmount},color:'${dashboardDetailsTagLib.getPrivateInvoiceBarColor(monthlyInvoiceAmount[0].novAmount,monthlyBudgetAmount[0].novAmount)}'},
                    {y:${decInvoiceAmount},color:'${dashboardDetailsTagLib.getPrivateInvoiceBarColor(monthlyInvoiceAmount[0].decAmount,monthlyBudgetAmount[0].decAmount)}'}
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
    <div class="monthAmountLabel" style="margin-top: 18px;"><g:message code="bv.dashboard.process.label" default="PROCESS" />:</div>
</div>

<div class="monthItemsValue">
<%
    Integer loopmonth = 1
    def tempGlAccount = ""

    int budgetAmount = 0
    int invoiceAmount = 0

    Integer showCountBudget = 0
    Integer showCountBudgetInvoice = 0
    def privateSpendingId = 0

    def budgetItemId = 0;

    for (int monthc = 0; monthc < 12; monthc++) {

        if (monthc == 0) {
//            def positiveBudgetAmount= dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].janAmount,budgetType)
//            budgetAmount = dashboardDetailsTagLib.getRoundedValue(positiveBudgetAmount);
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].janAmount);
            invoiceAmount = janInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].janItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].janItemCount;
            privateSpendingId = monthlyInvoiceAmount[2].janPrivateSpendingId;
            budgetItemId = monthlyBudgetAmount[2].janBudId;
        }
        else if (monthc == 1) {
            /*def positiveBudgetAmount= dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].febAmount,budgetType)
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(positiveBudgetAmount);*/
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].febAmount);
            invoiceAmount = febInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].febItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].febItemCount;
            privateSpendingId = monthlyInvoiceAmount[2].febPrivateSpendingId;
            budgetItemId = monthlyBudgetAmount[2].febBudId;

        }
        else if (monthc == 2) {
//            def positiveBudgetAmount= dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].marAmount,budgetType)
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].marAmount);
//            budgetAmount = dashboardDetailsTagLib.getRoundedValue(positiveBudgetAmount);
            invoiceAmount =marInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].marItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].marItemCount;
            privateSpendingId = monthlyInvoiceAmount[2].marPrivateSpendingId;
            budgetItemId = monthlyBudgetAmount[2].marBudId;
        }
        else if (monthc == 3) {
//            def positiveBudgetAmount= dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].aprAmount,budgetType)
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].aprAmount);
//            budgetAmount = dashboardDetailsTagLib.getRoundedValue(positiveBudgetAmount);
            invoiceAmount = aprInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].aprItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].aprItemCount;
            privateSpendingId = monthlyInvoiceAmount[2].aprPrivateSpendingId;
            budgetItemId = monthlyBudgetAmount[2].aprBudId;
        }
        else if (monthc == 4) {
//            def positiveBudgetAmount= dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].mayAmount,budgetType)
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].mayAmount);
//            budgetAmount = dashboardDetailsTagLib.getRoundedValue(positiveBudgetAmount);
            invoiceAmount = mayInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].mayItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].mayItemCount;
            privateSpendingId = monthlyInvoiceAmount[2].mayPrivateSpendingId;
            budgetItemId = monthlyBudgetAmount[2].mayBudId;
        }
        else if (monthc == 5) {
//            def positiveBudgetAmount= dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].junAmount,budgetType)
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].junAmount);
//            budgetAmount = dashboardDetailsTagLib.getRoundedValue(positiveBudgetAmount);
            invoiceAmount = junInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].junItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].junItemCount;
            privateSpendingId = monthlyInvoiceAmount[2].junPrivateSpendingId;
            budgetItemId = monthlyBudgetAmount[2].junBudId;
        }
        else if (monthc == 6) {
//            def positiveBudgetAmount= dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].julAmount,budgetType)
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].julAmount);
//            budgetAmount = dashboardDetailsTagLib.getRoundedValue(positiveBudgetAmount);
            invoiceAmount = julInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].julItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].julItemCount;
            privateSpendingId = monthlyInvoiceAmount[2].julPrivateSpendingId;
            budgetItemId = monthlyBudgetAmount[2].julBudId;
        }
        else if (monthc == 7) {
//            def positiveBudgetAmount= dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].augAmount,budgetType)
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].augAmount);
//            budgetAmount = dashboardDetailsTagLib.getRoundedValue(positiveBudgetAmount);
            invoiceAmount = augInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].augItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].augItemCount;
            privateSpendingId = monthlyInvoiceAmount[2].augPrivateSpendingId;
            budgetItemId = monthlyBudgetAmount[2].augBudId;
        }
        else if (monthc == 8) {
//            def positiveBudgetAmount= dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].sepAmount,budgetType)
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].sepAmount);
//            budgetAmount = dashboardDetailsTagLib.getRoundedValue(positiveBudgetAmount);
            invoiceAmount = sepInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].sepItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].sepItemCount;
            privateSpendingId = monthlyInvoiceAmount[2].sepPrivateSpendingId;
            budgetItemId = monthlyBudgetAmount[2].sepBudId;
        }
        else if (monthc == 9) {
//            def positiveBudgetAmount= dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].octAmount,budgetType)
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].octAmount);
//            budgetAmount = dashboardDetailsTagLib.getRoundedValue(positiveBudgetAmount);
            invoiceAmount = octInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].octItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].octItemCount;
            privateSpendingId = monthlyInvoiceAmount[2].octPrivateSpendingId;
            budgetItemId = monthlyBudgetAmount[2].octBudId;
        }
        else if (monthc == 10) {
//            def positiveBudgetAmount= dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].novAmount,budgetType)
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].novAmount);
//            budgetAmount = dashboardDetailsTagLib.getRoundedValue(positiveBudgetAmount);
            invoiceAmount = novInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].novItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].novItemCount;
            privateSpendingId = monthlyInvoiceAmount[2].novPrivateSpendingId;
            budgetItemId = monthlyBudgetAmount[2].novBudId;
        }
        else if (monthc == 11) {
//            def positiveBudgetAmount= dashboardDetailsTagLib.changeSignForPrivateBudget(monthlyBudgetAmount[0].decAmount,budgetType)
            budgetAmount = dashboardDetailsTagLib.getRoundedValue(monthlyBudgetAmount[0].decAmount);
//            budgetAmount = dashboardDetailsTagLib.getRoundedValue(positiveBudgetAmount);
            invoiceAmount = decInvoiceAmount

            showCountBudget = monthlyBudgetAmount[1].decItemCount;
            showCountBudgetInvoice = monthlyInvoiceAmount[1].decItemCount;
            privateSpendingId = monthlyInvoiceAmount[2].decPrivateSpendingId;
            budgetItemId = monthlyBudgetAmount[2].decBudId;
        }


        def bookingPeriod = (monthc+1)
        def masterId = budgetPrivateMasterId
//        println("budgetItemId: "+budgetItemId)

%>
%{--Budget Amount data by months.--}%
<div class="grpMonthlyAmount">
    <div class="monthlyBudAmt ${(loopmonth % 2) == 0 ? 'odd' : 'even'}">
        <% if(showCountBudget > 1) {
            def multipleBudgetItemsURL = liveURL + '/budgetItemPrivate/list' + '?editId=' + budgetItemId + '&bookingPeriod='+bookingPeriod + '&multibudgetNameId='+masterId
        %>
        <a href="${multipleBudgetItemsURL}" target="_parent" title="Total Budget Item: ${showCountBudget}"  class="tooltip">
        %{--<b>${budgetAmount}</b>--}%
        <%if(budgetType == 1){%>
             <b> ${budgetAmount}</b>
        <%}else{%>
            <b> ${budgetAmount*(-1)}</b>
        <%}%>

        <img width="10" height="10" alt="Edit" src="${contextPath}/images/edit.png">
        </a>
        <% } else if(showCountBudget == 0) {
            def budgetItemURL = liveURL + '/budgetItemPrivate/list' + '?bookingPeriod='+bookingPeriod + '&budgetNameId='+masterId+ '&privateBudget='+masterId+ '&budgetTypeId='+budgetType
        %>
        <a href="${budgetItemURL}" target="_parent" title="Total Budget Item: ${showCountBudget}" class="tooltip">
            <b><g:message code="bv.dashboard.create.label" default="Create" /></b>
            <img width="10" height="10" alt="Add" src="${contextPath}/images/add.png">
        </a>
        <% } else if(showCountBudget == 1) {
            def budgetIncomeURL = liveURL + '/budgetItemPrivate/list' + '?editId=' + budgetItemId + '&bookingPeriod='+bookingPeriod + '&budgetNameId='+masterId+ '&privateId='+masterId+ '&budgetTypeId='+budgetType
            def toolTipText = "Total Budget Item: " + showCountBudget;
        %>
        <a href="${budgetIncomeURL}" target="_parent" title=" ${toolTipText}" class="tooltip">
        %{--<b>${budgetAmount}</b>--}%
        <%if(budgetType == 1){%>
        <b> ${budgetAmount}</b>
        <%}else{%>
        <b> ${budgetAmount*(-1)}</b>
        <%}%>

        <img width="10" height="10" alt="Edit" src="${contextPath}/images/edit.png"></a>
        <% } %>
    </div>  <!--budget amount-->

    %{--Invoice income.--}%
    <div class="monthlyInvAmt ${(loopmonth % 2) == 0 ? 'odd' : 'even'}">
        <% if(showCountBudget > 0){
            if(invoiceAmount == 0){
        %>
        <b><g:message code="bv." default="0" /></b>
            <img width="10" height="10" alt="Add" src="${contextPath}/images/empty.png">
            <% } else{
        %> <a href="${liveURL}/budgetItemPrivate/editProcessAmount/?privateSpendingId=${privateSpendingId}&bookingPeriod=${bookingPeriod}&budgetMasterId=${masterId}" target="_parent" title="Total Process Amount: ${invoiceAmount}"   class="tooltip">
            <b>${invoiceAmount}</b>
            <img width="10" height="10" alt="Add" src="${contextPath}/images/edit.png"></a>
            <% }
        }%>
    </div>  <!--invoice amount-->
</div>
<%
    }
%>

</div>
</div>