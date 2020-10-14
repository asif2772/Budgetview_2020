<%@ page import="bv.DashboardDetails;bv.DashboardDetailsTagLib;" %>
<asset:javascript src="highcharts-3.0.4.min.js" />
<%
    def context = request.getServletContext().getContextPath()
    def dashboardDetailsTagLib = new DashboardDetailsTagLib();
    //println("budgetCustomerArr "+budgetCustomerArr);
%>
<g:javascript>
    function loadDetailsGraph(curTabInd){
        var divId = "#expense" + curTabInd;
        var spinnerDivId = "#spinner" + curTabInd;

        $.ajax({type:'POST',
                data:'curTabInd=' + curTabInd,
                url:'${context}/dashboardDetails/loadReservationGraphAccountWise',
                success:function(data,textStatus){
                    $(spinnerDivId).hide();
                    $(divId).html(data);
                },
                error: function() {
                    $(spinnerDivId).hide();
                    $(divId).text('An error occurred');
                }
            });
    }

    $(function () {
        var sesStorage = window.sessionStorage;

        $('#accordion').multiOpenAccordion({
            active: 'none',
            click: function (event, ui) {
                var curTabInd = ui['tab'].index();
                curTabInd = curTabInd / 2;
                sesStorage.setItem('selectedTabIndex',curTabInd);
            },
            tabShown: function(event, ui) {
                var curTabInd = ui['tab'].index();
                curTabInd = curTabInd / 2;

                //Spinner
                var spinnerDivId = "#spinner" + curTabInd;
                $(spinnerDivId).show();

                //Graph details
                loadDetailsGraph(curTabInd);
            },
            init: function (event, ui) {
                //alert('tab inited!');
            }
        });

        var selIndexs = [0]
        var jsSelectedValue = sesStorage.getItem('selectedTabIndex');
        selIndexs[0] = parseInt(jsSelectedValue);

        //alert('tab is clicked: '+selIndexs);
        if(selIndexs[0] >= 0){
            $('#accordion').multiOpenAccordion("option", "active", selIndexs[0]);
        }else{
            $('#accordion').multiOpenAccordion("option", "active", 'none');
        }

    });//End of function.

</g:javascript>

<%
    int totalIncomeBudgetAmount = dashboardDetailsTagLib.getRoundedValue(totalIncomeBudgetAmount);
    int totalIncomeInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(totalIncomeInvoiceAmount);

    int totalExpenseBudgetAmount = dashboardDetailsTagLib.getRoundedValue(totalExpenseBudgetAmount);
    int totalExpenseInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(totalExpenseInvoiceAmount);

    int totalReservBudgetAmount = dashboardDetailsTagLib.getRoundedValue(totalReservBudgetAmount);
    int totalReservInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(totalReservInvoiceAmount);

    int totalPrivateBudgetAmount = dashboardDetailsTagLib.getRoundedValue(totalPvtIncomeBudgetAmount - totalPvtExpenseBudgetAmount);
    int totalPrivateInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(totalPvtInvoiceIncomeAmount - totalPvtInvoiceExpenseAmount);
%>

<div id="income" class="dashUpperSubBar">
    <div class="budgetItemsAmount budgetItemsName">
        <span class="upperBarHeader"><g:message code="bv.dashboard.income.dropdown" default="Income"/></span>
    </div>
    <div class="budgetItemsAmount" style="float:right;">
        <span class="upperBarHeader"> ${totalIncomeBudgetAmount}</span>
    </div>
    <div class="invoiceItemsAmount" style="float:right;">
        <span class="upperBarHeader">${totalIncomeInvoiceAmount}</span>
    </div>
</div>

<div class="dashUpperSubBar">
    <div class="budgetItemsAmount budgetItemsName">
        <span class="upperBarHeader"><g:message code="bv.dashboard.expense.dropdown" default="Expense"/></span>
    </div>
    <div class="budgetItemsAmount" style="float:right;">
        <span class="upperBarHeader"> ${totalExpenseBudgetAmount}</span>
    </div>
    <div class="invoiceItemsAmount" style="float:right;">
        <span class="upperBarHeader">${totalExpenseInvoiceAmount}</span>
    </div>
</div>

<div id="private" class="dashUpperSubBar">
    <div class="budgetItemsAmount budgetItemsName">
        <span class="upperBarHeader"><g:message code="bv.privateBudgetIncomeExpense.label" default="Private Income & Expense"/></span>
    </div>
    <div class="budgetItemsAmount" style="float:right;">
        <span class="upperBarHeader"> ${totalPrivateBudgetAmount}</span>
    </div>
    <div class="invoiceItemsAmount" style="float:right;">
        <span class="upperBarHeader">${totalPrivateInvoiceAmount}</span>
    </div>
</div>

<div id="reservation" class="dashUpperSubBar">
    <div class="budgetItemsAmount budgetItemsName">
        <span class="upperBarHeader"><g:message code="bv.dashboardReservation.label" default="Reservation"/></span>
    </div>
    <div class="budgetItemsAmount" style="float:right;">
        <span class="upperBarHeader"> ${totalReservBudgetAmount}</span>
    </div>
    <div class="invoiceItemsAmount" style="float:right;">
        <span class="upperBarHeader">${totalReservInvoiceAmount}</span>
    </div>
</div>

<div id="accordion" class="dbIframeBody">

    <%
        if (reservationAHWArr) {
            for (int v = 0; v < reservationAHWArr.size(); v++) {
                int totalBudgetAmount = dashboardDetailsTagLib.getRoundedValue(reservationAHWArr[v][2]);
//                Double positiveTotalInvoiceAmount = dashboardDetailsTagLib.getPositiveValue(reservationAHWArr[v][4]);
                int totalInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(reservationAHWArr[v][4]);
    %>
    %{--style="float:right;${(totalInvoiceAmount > totalBudgetAmount) ? 'color: #ff0000;' : 'color: #000000;'}--}%
    <h3 class="expenseHeaderStyle">
        <p class="barHeader"><g:message code="companySetup.vendorname.label" default="${reservationAHWArr[v][0] + " " + reservationAHWArr[v][1]}"/></p>
        <div id="spinner${v}" class="spinner dashboardSpinner">
            <img src="${assetPath(src:'spinner.gif')}" alt="${message(code:'spinner.alt.Loading',default:'Loading...')}" />
        </div>
        <div class="budgetItemsAmount budgetAmountExColor" style="float:right;">
            <% if(totalBudgetAmount >= 0) {%>
            <span style="line-height: 30px;"> - ${totalBudgetAmount}</span>
            <%}else{ %>
            <span style="line-height: 30px;"> -(${totalBudgetAmount})</span>
            <%}%>
        </div>
        <div class="invoiceItemsAmount" style="float:right;">
            <span style="line-height: 30px;">${totalInvoiceAmount}</span></div>
    </h3>

    <div id="vendorid${v}" style="padding: 0px; border-radius: 0px;  margin: 0;width: 1018px; border: 1px none;">
        <div id="list-page-body-inner" class="content graphBodyInsideReserve">
            <div id="expense${v}" style="padding: 0px; border-radius:none;">
        </div>
        </div>
    </div>
    <%
            }
        }
    %>
</div>
