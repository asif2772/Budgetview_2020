<%@ page import="bv.DashboardDetails;bv.DashboardDetailsTagLib;" %>
<asset:javascript src="highcharts-3.0.4.min.js" />
<%
    def context = request.getServletContext().getContextPath()
    def dashboardDetailsTagLib = new DashboardDetailsTagLib();

    int nIncomeBudgetSize = budgetCustomerArr.size();
    //println("budgetCustomerArr "+budgetCustomerArr);
%>
<g:javascript>
    var expenseBarStart = '${nIncomeBudgetSize}';
    function loadIncomeGraphDetails(curTabInd){
        var divId = "#income" + curTabInd;
        var spinnerDivId = "#spinnerC" + curTabInd;

        $.ajax({type:'POST',
                data:'curTabInd=' + curTabInd,
                url:'${context}/dashboardDetails/updateIncomeGraphArea',
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

    function loadExpenseGraphDetails(curTabInd){
        var adjustedTabInd = curTabInd - expenseBarStart;
        var divId = "#expense" + adjustedTabInd;
        var spinnerDivId = "#spinnerV" + adjustedTabInd;

        $.ajax({type:'POST',
                data:'curTabInd=' + adjustedTabInd,
                url:'${context}/dashboardDetails/updateExpenseGraphArea',
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
                //alert("expenseBarStart : " + expenseBarStart);

                if(curTabInd >=  expenseBarStart){
                //Spinner
                    var spinnerDivId = "#spinnerV" + (curTabInd - expenseBarStart);
                    $(spinnerDivId).show();

                    loadExpenseGraphDetails(curTabInd);
                }else{
                //Spinner
                    var spinnerDivId = "#spinnerC" + curTabInd;
                    $(spinnerDivId).show();

                    loadIncomeGraphDetails(curTabInd);
                }

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
    int totalReservBudgetAmount = dashboardDetailsTagLib.getRoundedValue(totalReservBudgetAmount);
    int totalReservInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(totalReservInvoiceAmount);

    int totalComBudgetAmount = dashboardDetailsTagLib.getRoundedValue(totalIncomeBudgetAmount - totalExpenseBudgetAmount);
    int totalComInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(totalIncomeInvoiceAmount - totalExpenseInvoiceAmount);

    int totalPrivateBudgetAmount = dashboardDetailsTagLib.getRoundedValue(totalPvtIncomeBudgetAmount - totalPvtExpenseBudgetAmount);
    int totalPrivateInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(totalPvtInvoiceIncomeAmount - totalPvtInvoiceExpenseAmount);
%>

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
%{--<div class="dashUpperSubBar"></div>--}%
<div class="dashUpperSubBar">
    <div class="budgetItemsAmount budgetItemsName">
        <span class="upperBarHeader"><g:message code="bv.dashboard.income&Expense.dropdown" default="Income & Expense"/></span>
    </div>
    <div class="budgetItemsAmount" style="float:right;">
        <span class="upperBarHeader"> ${totalComBudgetAmount}</span>
    </div>
    <div class="invoiceItemsAmount" style="float:right;">
        <span class="upperBarHeader">${totalComInvoiceAmount}</span>
    </div>
</div>  <!--dashUpperSubBar-->


<div id="accordion" class="dbIframeBody">
<%
    if (budgetCustomerArr) {
        for (int v = 0; v < budgetCustomerArr.size(); v++) {

            int totalBudgetAmount = dashboardDetailsTagLib.getRoundedValue(budgetCustomerArr[v][3]);
            int totalInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(budgetCustomerArr[v][4]);
%>

<h3 class="incomeHeaderStyle">
        <p class="barHeader"><g:message code="companySetup.vendorname.label" default="${budgetCustomerArr[v][1]}"/></p>
    <div id="spinnerC${v}" class="spinner dashboardSpinner">
        <img src="${assetPath(src:'spinner.gif')}" alt="${message(code:'spinner.alt.Loading',default:'Loading...')}" />
    </div>

    <div class="budgetItemsAmount budgetAmountInColor" style="float:right;height: 25px;">

        <%if(totalBudgetAmount<0){%>
             <span style="line-height: 30px;"> + (${totalBudgetAmount})</span>
        <%}else{%>
            <span style="line-height: 30px;"> + ${totalBudgetAmount} </span>
        <%}%>

    </div>
    <div class="invoiceItemsAmount" style="float:right;height: 25px;">
        <span style="line-height: 30px;"> ${totalInvoiceAmount} </span>
    </div>
</h3>

%{--<div id="customerId${v}" class="dashGraphDiv">--}%
<div id="list-page-body-inner" class="content graphBodyInside">
    <div id="income${v}" style="padding: 0px; border-radius:none;">
    </div>
</div>
%{--</div>--}%
<%
        }
    }
%>
%{--</div>

--}%%{--<div class="dashUpperSubBar"></div>--}%%{--
<div id="accordion" style="height: auto;overflow:auto;background: none repeat scroll 0 0 #fcfcfc;">--}%
%{--Start code ExpenseNameWise--}%
<%
    if (budgetVendorArr) {
        for (int v = 0; v < budgetVendorArr.size(); v++) {

            int totalBudgetAmount = dashboardDetailsTagLib.getRoundedValue(budgetVendorArr[v][3]);
            int totalInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(budgetVendorArr[v][4]);
%>

<h3 class="expenseHeaderStyle">
    <p class="barHeader"><g:message code="companySetup.vendorname.label" default="${budgetVendorArr[v][1]}"/></p>
    <div id="spinnerV${v}" class="spinner dashboardSpinner">
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
        <span style="line-height: 30px;"> ${totalInvoiceAmount}</span>
    </div>
</h3>

%{--<div id="vendorid${v}" class="dashGraphDiv">--}%
<div id="list-page-body-inner" class="content graphBodyInside">
    <div id="expense${v}" style="padding: 0px; border-radius:none;">
    </div>
</div>
%{--</div>--}%
<%
        }
    }
%>

</div>
