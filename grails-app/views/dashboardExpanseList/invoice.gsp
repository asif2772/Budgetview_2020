<%@ page import="bv.FiscalYear" %>
<%@ page import="bv.CoreParamsHelper" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'DashboardListExpanse.label', default: 'Expanse Invoice(s)')}" />
    <title><g:message code="DashboardListExpanse.label" default="Expanse Invoice(s)" args="[entityName]" /></title>
    <r:require modules="flexiGrid" />
</head>
<body>
<a href="#list-DashboardListExpanse" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<g:javascript>
    $("#flex1").flexigrid({
        url: '${resource(dir: 'dashboardExpanseList', file: 'showBudgetList')}?vendorId='+${params.id}+'&journalId='+${params.g}+'&bookingPeriod='+${params.m},
        dataType: 'json',
        colModel : [
            {display: '${message(code: 'inventoryLocations.serial.label', default: 'SL#')}',            name : 'invoiceExpenseId',      width : 40, sortable : true, align: 'center'},
            {display: '${message(code: 'quick.expense.budget.label', default: 'Budget Item')}',    name : 'budgetItemID',          width : 145, sortable : true, align: 'center'},
            {display: '${message(code: 'quick.expense.glaccount.label', default: 'GL Account')}',     name : 'glAccountName',       width : 190, sortable : true, align: 'left'},
            {display: '${message(code: 'DashboardListIncomeBudget.TransDate.label', default: 'Creation Date')}',  name : 'createdDate',       width : 130, sortable : true, align: 'left'},
            //{display: 'Amount ex VAT',  name : 'amountWithoutVat',  width : 130, sortable : true, align: 'left', hide: true},
            {display: '${message(code: 'DashboardListIncome.amountExVat.label', default: 'Amount ex VAT')}',  name : 'totalPriceWithoutVat',  width : 100, sortable : true, align: 'right'},
            {display: '${message(code: 'invoiceExpenseDetails.vatCategory.totalAmount.label', default: 'Total Amount')}',   name : 'totalPriceWithVat',       width : 100, sortable : true, align: 'right'},
            {display: '${message(code: 'invoiceExpense.gridList.invoiceNumberExpense.label', default: 'Invoice')}',      name : 'total',     width : 100, sortable : false, align: 'right'},
            {display: '${message(code: 'Dashboard.incomeBudgetItem.changeBudget.label', default: 'Change Budget')}',  name : 'Action',              width : 100, sortable : false, align: 'center'}
        ],
        buttons : [
            {name: '<g:message code="dashboardExpanseList.invoice.Button.createNewBudget.label" default="Create New Budget" />&nbsp&nbsp&nbsp&nbsp', bclass: 'add', onpress : createNewBudget},

            {name: '<g:message code="dashboardExpanseList.invoice.Button.bookReceipt.label" default="Book Receipt" />&nbsp&nbsp&nbsp&nbsp', bclass: 'delete', onpress : bookedReceipt},
            {name: '<g:message code="dashboardExpanseList.invoice.Button.bookInvoice.label" default="Book Invoice" />&nbsp&nbsp&nbsp&nbsp', bclass: 'delete', onpress : bookedInvoice},
            {separator: true}
        ],
         searchitems : [
                    {display : 'Budget Item', name : "BudgetItemCode"},
                    {display : 'GL Account', name : "GLAccount"}
                ],
        sortname: "invoiceExpenseId",
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
        url = "${resource(dir: 'budgetItemExpenseDetails', file: 'index')}?vendorId=${params.id}&budgetVendorId=${params.id}&journalId=${params.g}&bookingPeriod=${params.m}";
        window.location = url;
    }

    function bookedInvestment(com, grid) {       //receiptEntry
        url = "${resource(dir: 'investmentInvoice', file: 'list')}";
        window.location = url;
    }

    function bookedReceipt(com, grid) {
       var ids = $('.trSelected',grid);
        var idCount = ids.length;

        if (idCount == 0) {
          alert("Please select an invoice to show detail");
          return false;
        }


        var fullBookInvoiceId = $(ids[ids.length-1]).attr('id').replace('row', '');
        if (idCount == 1) {
          alert(fullBookInvoiceId);

        }
        var mySplitResult=fullBookInvoiceId.split("::");
        var bookInvoiceId= mySplitResult[0];
        var budgetItemDetailsId= mySplitResult[1];

        url = "${resource(dir: 'invoiceExpense', file: 'receiptList')}?bookInvoiceId="+bookInvoiceId+'&bookingPeriod='+${params.m}+'&vendorId='+${params.id}+'&budgetVendorId='+${params.id}+'&budgetItemDetailsId='+budgetItemDetailsId;
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

        url = "${resource(dir: 'invoiceExpense', file: 'list')}?bookInvoiceId="+bookInvoiceId+'&bookingPeriod='+${params.m}+'&vendorId='+${params.id}+'&budgetVendorId='+${params.id}+'&budgetItemDetailsId='+budgetItemDetailsId;
        window.location = url;
    }

    function changeBooking(expenseInvoiceId,journalId,vendorId,bookingPeriod,liveUrlForEditBudgetItem){

     url = "${resource(dir: 'budgetItemExpenseDetails', file: 'list')}?editId="+expenseInvoiceId+"&journalId="+journalId+"&budgetVendorId="+vendorId+"&vendorId="+vendorId+"&bookingPeriod="+bookingPeriod;
        window.location = url;
    }
</g:javascript>

<div id="list-page-body-inner" class="content">
    <div id="list-page">
        <div class="list-boxtitle" id="defaultFlexiHead">
            ${message(code: 'default.forTheSelectedBudgetExpenseItem.label', default: 'For Expense Selected Budget Item')}
        </div>
        <g:if test="${params.errorEditInv}">
            <div class="errors" style="font-size: 12px;">
                ${message(code: 'bv.invoice.UndoReconciliationFirst.label', default: 'This booking is reconciliated. To change it please undo the reconciliation first.')}
                <g:link controller="undoReconciliation">${message(code: 'bv.invoice.undo.label', default: 'For Income Selected Budget Item')}</g:link>
            </div>
        </g:if>
        <div class="info_message">
            ${message(code: 'bv.invoiceExpense.info.message.label', default: 'There are multiple budgets available for your selection. Please select the budget you want to use for booking and proceed by clicking on the "Book Invoice" button.')}
        </div>
        <table>
            <thead>
                <tr class="even">
                    <td width="385px"><b>${CustomerNameInstance[0]}</b> </td>
                    <td width="150px"><b>${monthShowInstance}</b> </td>
                    <td width="150px" ></td>

                    %{--<td width="350px" colspan="4"><b><g:message code="DashboardListIncome.CustomerName.list.label" default="Budget Item" /> :</b> ${CustomerNameInstance[0]}</td>--}%
                    %{--<td width="350px" colspan="4"><b><g:message code="DashboardListIncome.FiscalYear.list.label" default="Fiscal Year" /> :</b> ${FiscalYearInfo[0][1]} TO  ${FiscalYearInfo[0][2]}</td>--}%
                </tr>

                %{--<tr class="even">--}%
                    %{--<td width="350px" colspan="4"><b><g:message code="DashboardListIncome.BookingPeriod.list.label" default="Booking Period" /> :</b> ${monthShowInstance}</td>--}%
                    %{--<td width="350px" colspan="4">--}%%{--<b><g:message code="DashboardListIncome.GLAccountCode.list.label" default="GL Account Code" /> :</b> ${glAccountInstance}--}%%{--</td>--}%
                %{--</tr>--}%
            </thead>
        </table>

        <table id="flex1" style="display: none"></table>

    </div>

</div>


%{--<div id="list-page-body-inner" class="content">--}%
    %{--<div id="list-page">--}%
        %{--<table>--}%
            %{--<thead>--}%
            %{--<tr class="expenseSecondTable">--}%
                %{--<th width="100"><g:message code="DashboardListIncome.invoiceNo.label"  default="Invoice No" /></th>--}%
                %{--<th width="120">&nbsp<g:message code="DashboardListIncome.budgetItem.label" default="Budget Item" /></th>--}%
                %{--<th width="100">&nbsp<g:message code="DashboardListIncome.glAccount.label"  default="GL Account(s)" /></th>--}%
                %{--<th width="90">&nbsp<g:message code="DashboardListIncome.TransDate.label"  default="Trans Date" /></th>--}%
                %{--<th width="90">&nbsp<g:message code="DashboardListIncome.DueDate.label"     default="Due Date" /></th>--}%
                %{--<th width="100" style="text-align: right"><g:message code="DashboardListIncome.amountExVat.label"    default="Amount ex VAT" />&nbsp</th>--}%
                %{--<th width="112" style="text-align: right"><g:message code="DashboardListIncome.totalAmount.label"   default="Total Amount" />&nbsp</th>--}%
                %{--<th width="130" style="text-align: right"><g:message code="DashboardListIncome.unpaidAmount.label"   default="Unpaid Amount" />&nbsp</th>--}%
                %{--<th width="150" style="text-align: center"><g:message code="DashboardListIncome.changeInvoice.label"   default="Change Invoice" /></th>--}%
            %{--</tr>--}%
            %{--</thead>--}%

            %{--<%--}%
           %{--// println(params)--}%

             %{--if(invoiceListInstanceList.size()){--}%
                %{--//for (int i=0; i<invoiceListInstanceList.size(); i++){--}%
            %{--%>--}%
                %{--<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">--}%
                    %{--<td>${invoiceListInstanceList[i][7]}</td>--}%
                    %{--<td>${invoiceListInstanceList[i][6]}</td>--}%
                    %{----}%%{----}%%{--<td>${invoiceListInst[1]}</td>--}%%{----}%%{----}%
                    %{--<td>${invoiceListInstanceList[i][8]}</td>--}%
                    %{--<td>${invoiceListInstanceList[i][9]}</td>--}%
                    %{--<td style="text-align: center">${invoiceListInstanceList[i][4]}</td>--}%
                    %{--<td style="text-align: center">${invoiceListInstanceList[i][5]}</td>--}%
                    %{--<td style="text-align: center">${invoiceListInstanceList[i][11]}</td>--}%
                    %{--<td style="text-align: center">--}%
                        %{--<g:link controller="budgetItemExpenseDetails" action="listwisereport" id="${invoiceListInstanceList[i][0]}" ><g:img dir="images" file="edit.png" width="15" height="16"  alt="View" /></g:link>&nbsp;--}%
                    %{--</td>--}%
                %{--</tr>--}%
                %{--<g:each in="${invoiceListInstanceList}" status="i" var="invoiceListInst">--}%
                    %{--<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">--}%
                        %{--<td>${invoiceListInst[14]+invoiceListInst[7]}</td>--}%
                        %{--<td>${invoiceListInst[6]}</td>--}%
                        %{--<td>${invoiceListInst[13]}</td>--}%
                        %{--<td>${invoiceListInst[8]}</td>--}%
                        %{--<td>${invoiceListInst[9]}</td>--}%
                        %{--<td style="text-align: right"><g:formatNumber number="${invoiceListInst[4]}" format="#.00" /></td>--}%
                        %{--<td style="text-align: right"><g:formatNumber number="${invoiceListInst[5]}" format="#.00" /></td>--}%
                        %{--<td style="text-align: right"><g:formatNumber number="${invoiceListInst[11]}" format="#.00" /></td>--}%
                        %{--<td style="text-align: center">--}%
                            %{--<% if(invoiceListInst[15]==1) {--}%
                                %{--def budgetItemDetailsId = new CoreParamsHelper().getBudgetItemExpenseDetailsIdFromInvoiceExpense(invoiceListInst[0])--}%
                            %{--%>--}%
                            %{--<g:link controller="invoiceExpense" action="receiptList" params="[editId: invoiceListInst[0], bookInvoiceId:invoiceListInst[12], vendorId: params.id, bookingPeriod: params.m,budgetItemDetailsId:budgetItemDetailsId, g : params.g]" ><g:img dir="images" file="edit.png" width="15" height="16"  alt="View" /></g:link>&nbsp;--}%
                            %{--<g:img dir="images" file="edit.png" width="15" height="16"  alt="View" />--}%
                            %{--<% }else{--}%
                                %{--def budgetItemDetailsId = new CoreParamsHelper().getBudgetItemExpenseDetailsIdFromInvoiceExpense(invoiceListInst[0])--}%

                            %{--%>--}%
                            %{--<g:link controller="invoiceExpense" action="list" params="[editId: invoiceListInst[0], bookInvoiceId:invoiceListInst[12], vendorId: params.id, bookingPeriod: params.m,budgetItemDetailsId:budgetItemDetailsId, g : params.g]" ><g:img dir="images" file="edit.png" width="15" height="16"  alt="View" /></g:link>&nbsp;--}%
                            %{--<% } %>--}%
                        %{--</td>--}%
                    %{--</tr>--}%
                %{--</g:each>--}%
            %{--<%  //}--}%
                    %{--} else { %>--}%
            %{--<tr>--}%
                %{--<td colspan="9">There are no invoice.</td>--}%
            %{--</tr>--}%
            %{--<% } %>--}%

        %{--</table>--}%
    %{--</div>--}%
%{--</div>--}%
</body>
</html>
<g:javascript>
    $(document).ready(function () {
        setTimeout(function(){
            $("#flex1 tr:first").addClass("trSelected");
        }, 1000);
    });
</g:javascript>