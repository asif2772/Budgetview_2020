<%@ page import="bv.DashboardDetails;bv.DashboardDetailsTagLib;" %>
<asset:javascript src="highcharts-3.0.4.min.js" />
<%
    def context = request.getServletContext().getContextPath()
    def dashboardDetailsTagLib = new DashboardDetailsTagLib();
    int totalPrivateDAWithdrawelForcompany = dashboardDetailsTagLib.getRoundedValue(processDataDetailsInstance.allTotal);
    def isPrivateDAWithdrawelValueExist = (totalPrivateDAWithdrawelForcompany < 0 || totalPrivateDAWithdrawelForcompany > 0) ? true : false;
%>

<g:javascript>
    //PDWC = Private deposits and widrawals for company
    function loadPDWCGraph(curTabInd){

        var graphDivId = "#income" + curTabInd;
        var spinnerDivId = "#spinner" + curTabInd;

        curTabInd = curTabInd - 1
        $.ajax({type:'POST',
                data:'curTabInd=' + curTabInd,
                url:'${context}/dashboardDetails/updatePrivateDWCGraph',
              success:function(data,textStatus){
                 $(spinnerDivId).hide();
                 $(graphDivId).html(data);
              },
              error: function() {
                 $(spinnerDivId).hide();
                 $(graphDivId).text('An error occurred');
              }
            });
    }

    function loadDetailsGraph(curTabInd){

        var graphDivId = "#income" + curTabInd;
        var spinnerDivId = "#spinner" + curTabInd;

        curTabInd = curTabInd - 1
        $.ajax({type:'POST',
                data:'curTabInd=' + curTabInd,
                url:'${context}/dashboardDetails/updatePrivateGraphArea',
              success:function(data,textStatus){
                 $(spinnerDivId).hide();
                 $(graphDivId).html(data);
              },
              error: function() {
                 $(spinnerDivId).hide();
                 $(graphDivId).text('An error occurred');
              }
            });
    }

    $(function () {
        var sesStorage = window.sessionStorage;
        //var loadPDWGraph = '${isPrivateDAWithdrawelValueExist}';
        //alert("loadPDWCGraph"+loadPDWGraph);
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

                //Graph Details
                if(curTabInd == 0){
                    loadPDWCGraph(curTabInd);
                }else{
                    loadDetailsGraph(curTabInd);
                }

            },
            init: function (event, ui) {

            }
            });

            var selIndexs = [0]
            var jsSelectedValue = sesStorage.getItem('selectedTabIndex');
            selIndexs[0] = parseInt(jsSelectedValue);
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
    totalPrivateInvoiceAmount = totalPrivateInvoiceAmount + totalPrivateDAWithdrawelForcompany
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

<div id="accordion" class="dbIframeBody">
    <h3 class="incomeHeaderStyle">
        <p class="barHeader"><g:message code="bv.privateDepositsWithdrawalsforCompany.title.label" default="Private Deposits And Withdrawals for Company"/></p>
        <div id="spinner0" class="spinner dashboardSpinner">
            <img src="${assetPath(src:'spinner.gif')}" alt="${message(code:'spinner.alt.Loading',default:'Loading...')}" />
        </div>
        <div class="budgetItemsAmount budgetAmountInColor" style="float:right;">
            <span style="line-height: 30px;">0</span>
        </div>
        <div class="invoiceItemsAmount" style="float:right;">
            <%if(totalPrivateDAWithdrawelForcompany < 0 ){%>
                <span style="line-height: 30px;"> (${totalPrivateDAWithdrawelForcompany}) </span>
            <%}else{%>
                <span style="line-height: 30px;"> ${totalPrivateDAWithdrawelForcompany} </span>
            <%}%>
        </div>
    </h3>

    <div id="customerId" class="dashGraphDiv">
        <div id="list-page-body-inner" class="content graphBodyInside">
            <div id="income0" style="padding: 0px; border-radius:none;">

            </div>
        </div>
    </div>

    <%
        if (privateBudgetArr) {
            int nStartValue = 0;
            int nEndValue = privateBudgetArr.size()
            //if(isPrivateDAWithdrawelValueExist){
                nStartValue = 1;
                nEndValue = privateBudgetArr.size() + 1
            //}
            def v = 0
            for (int p = nStartValue; p < nEndValue; p++) {
                //if(isPrivateDAWithdrawelValueExist) {
                    v = p - 1
//                }else{
//                    v = p
//                }

                def budgetType = privateBudgetArr[v][4]

                int budgetNameId = privateBudgetArr[v][0];
                def tempTotalBudgetAmount = privateBudgetArr[v][3];
                if(budgetType > 1){
                    tempTotalBudgetAmount = tempTotalBudgetAmount * (-1)//dashboardDetailsTagLib.changeSignForPrivateBudget(tempTotalBudgetAmount)
                }

                int totalBudgetAmount = dashboardDetailsTagLib.getRoundedValue(tempTotalBudgetAmount);

                def tempTotalInvoiceAmount = privateBudgetArr[v][6];
                int totalInvoiceAmount = dashboardDetailsTagLib.getRoundedValue(tempTotalInvoiceAmount);

//                println("totalInvoiceAmount"+totalInvoiceAmount)

    %>

    <h3 class="incomeHeaderStyle">
        <p class="barHeader"><g:message code="companySetup.vendorname.label" default="${privateBudgetArr[v][1]}"/></p>
        <div id="spinner${p}" class="spinner dashboardSpinner">
            <img src="${assetPath(src:'spinner.gif')}" alt="${message(code:'spinner.alt.Loading',default:'Loading...')}" />
        </div>

    <% if(budgetType == 1){ %>
        <div class="budgetItemsAmount budgetAmountInColor" style="float:right;">
        <%if(totalBudgetAmount < 0 ){%>
            <span style="line-height: 30px;"> + (${totalBudgetAmount}) </span>
        <%}else{%>
            <span style="line-height: 30px;"> + ${totalBudgetAmount} </span>
        <%}%>
        </div>

    <%}else{%>
        <div class="budgetItemsAmount budgetAmountExColor" style="float:right;">
            <%if(totalBudgetAmount < 0 ){%>
                <span style="line-height: 30px;"> - (${totalBudgetAmount}) </span>
            <%}else{%>
                <span style="line-height: 30px;"> - ${totalBudgetAmount} </span>
            <%}%>
        </div>
    <%}%>

        <div class="invoiceItemsAmount" style="float:right;">
            <span style="line-height: 30px;"> ${totalInvoiceAmount} </span>
        </div>
    </h3>

    <div id="customerId" class="dashGraphDiv">
        <div id="list-page-body-inner" class="content graphBodyInside">
            <div id="income${p}" style="padding: 0px; border-radius:none;">

            </div>
        </div>
    </div>
    <%
            }
        }
    %>
</div>