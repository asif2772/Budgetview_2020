<%@ page import="bv.FiscalYear" %>
<%@ page import="bv.CoreParamsHelper" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'DashboardListIncome.label', default: 'Income Invoice(s)')}"/>
    <title><g:message code="DashboardListIncome.label" default="Income Invoice(s)" args="[entityName]"/></title>
    <r:require modules="flexiGrid"/>
</head>

<body>
<a href="#list-DashboardListIncome" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                          default="Skip to content&hellip;"/></a>
<g:javascript>

    $("#flex1").flexigrid({
        url: '${resource(dir: 'dashboardIncomeList', file: 'showBudgetList')}?customerId='+${params.id}+'&journalId='+${params.g}+'&bookingPeriod='+${params.m},
        dataType: 'json',
        colModel : [
            {display: 'SL#',            name : 'invoiceIncomeId',       width : 40, sortable : true, align: 'center'},
            {display: '${message(code: 'bv.investmentInvoice.BudgetItem.label', default: 'Budget Item')}',    name : 'budgetItemID', width : 125, sortable : true, align: 'center'},
            {display: '${message(code: 'investmentInvoiceDetails.glAccount.label', default: 'GL Account')}',     name : 'glAccountName', width : 215, sortable : true, align: 'left'},
            {display: '${message(code: 'DashboardListIncomeBudget.TransDate.label', default: 'Creation Date')}',  name : 'createdDate',  width : 130, sortable : true, align: 'left'},
            {display: '${message(code: 'DashboardListIncome.amountExVat.label', default: 'Amount ex VAT')}',  name : 'totalPriceWithoutVat',  width : 100, sortable : true, align: 'right'},
            {display: '${message(code: 'bv.autoReconciliationOpenInvoicesTotalAmount.label', default: 'Total Amount')}',   name : 'totalPriceWithVat',     width : 100, sortable : true, align: 'right'},
            {display: '${message(code: 'Dashboard.incomeBudgetItem.invoice.label', default: 'Invoice')}',      name : 'total', width : 100, sortable : false, align: 'right'},
            {display: '${message(code: 'Dashboard.incomeBudgetItem.changeBudget.label', default: 'Change Budget')}',  name : 'Action',width : 100, sortable : false, align: 'center'}
        ],
        buttons : [
            {name: '<g:message code="dashboardExpanseList.invoice.Button.createNewBudget.label" default="Create New Budget"/>&nbsp;&nbsp;&nbsp;&nbsp;', bclass: 'add', onpress : createNewBudget},
            {name: '<g:message code="dashboardExpanseList.invoice.Button.bookInvoice.label" default="Book Invoice"/>&nbsp;&nbsp;&nbsp;&nbsp;', bclass: 'delete', onpress : bookedInvoice},
            {separator: true}
        ],
         searchitems : [
                    {display : 'Budget Item',   name : "BudgetItemCode"},
                    {display : 'GL Account',    name : "GLAccount"}
                    ],
        sortname: "invoiceIncomeId",
        sortorder: "asc",
        usepager: true,
        title: false,
        useRp: true,
        rp: 15,
        showTableToggleBtn: true,
        width: "1000",
        singleSelect: true,
        height: "auto"
    });

    function createNewBudget(com, grid){
        url = "${resource(dir: 'budgetItemIncomeDetails', file: 'index')}?customerId=${params.id}&journalId=${params.g}&bookingPeriod=${params.m}";
        window.location = url;
    }


    function bookedInvoice(com, grid) {
       var ids = $('.trSelected',grid);
        var idCount = ids.length;

        if (idCount == 0) {
          alert("Please select an invoice to show detail");
          return false;
        }

        var fullBookInvoiceId = $(ids[ids.length-1]).attr('id').replace('row', '');
        var mySplitResult=fullBookInvoiceId.split("::");
        var bookInvoiceId= mySplitResult[0];
        var budgetItemDetailsId= mySplitResult[1];
        url = "${resource(dir: 'invoiceIncome', file: 'list')}?bookInvoiceId="+bookInvoiceId+'&bookingPeriod='+${params.m}+'&budgetCustomerId='+${params.id}+'&customerId='+${params.id}+'&budgetItemDetailsId='+budgetItemDetailsId;
        //alert(url)
        window.location = url;
    }

    function changeBooking(incomeInvoiceId,journalId,customerId,bookingPeriod,liveUrlForEditBudgetItem){
        url = "${resource(dir: 'budgetItemIncomeDetails', file: 'list')}?editId="+incomeInvoiceId+"&journalId="+journalId+"&budgetCustomerId="+customerId+"&customerId="+customerId+"&bookingPeriod="+bookingPeriod;
        window.location = url;
    }

</g:javascript>

<div id="list-page-body-inner" class="content">
    <div id="list-page">
        <div class="list-boxtitle" id="defaultFlexiHead">
            ${message(code: 'default.forTheSelectedBudgetIncomeItem.label', default: 'Standard Income Budget')}
        </div>
        <% if(params.errorEditInv){%>
            <div class="errors" style="font-size: 12px;">
                ${message(code: 'bv.invoice.UndoReconciliationFirst.label', default: 'This booking is reconciliated. To change it please undo the reconciliation first.')}
                <g:link controller="undoReconciliation">${message(code: 'bv.invoice.undo.label', default: 'For Income Selected Budget Item')}</g:link>
            </div>
        <% } %>
        <div class="info_message">
        ${message(code: 'bv.invoice.info.message.label', default: 'There are multiple budgets available for your selection. Please select the budget you want to use for booking and proceed by clicking on the "Book Invoice" button.')}
        </div>
        <table>
            <thead>
            <tr class="even">
                <td width="385px"><b>${CustomerNameInstance[0]}</b> </td>
                <td width="150px"><b>${monthShowInstance}</b> </td>
                <td width="150px" ></td>
                %{--<td width="350px" colspan="4"><b><g:message code="DashboardListIncome.FiscalYear.list.label"--}%
                                                            %{--default="Fiscal Year"/> :</b> ${FiscalYearInfo[0][1]} <g:message code="bv.Dashboard.FiscalYear.To.label" default="to"/>  ${FiscalYearInfo[0][2]}--}%
                %{--</td>--}%
            </tr>
            %{--<tr class="even">--}%
                %{--<td width="350px" colspan="4"><b><g:message code="DashboardListIncome.BookingPeriod.list.label"--}%
                                                            %{--default="Booking Period"/> :</b> ${monthShowInstance}</td>--}%
               %{----}%
            %{--</tr>--}%
            </thead>
        </table>
        <table id="flex1" style="display: none"></table>
        </div>
</div>

</body>
</html>
<g:javascript>
    $(document).ready(function () {
        setTimeout(function () {
            $("#flex1 tr:first").addClass("trSelected");
        }, 1000);
    });
</g:javascript>