<%@ page import="bv.FiscalYear" %>
<%@ page import="bv.CoreParamsHelper" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'DashboardListIncomeBudget.label', default: 'Income Budget Items(s)')}" />
    <title><g:message code="DashboardListIncomeBudget.label" default="Income Budget Items(s)" args="[entityName]" /></title>
</head>
<body>
<a href="#list-DashboardListIncomeBudget" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>


<div id="list-page-body-inner" class="content">
    <div id="list-page">
        <div class="list-boxtitle">
            <div class="list-boxtitle-left">
                <g:message code="DashboardListIncomeBudget.invoice.list.label" default="Income Budget Item(s)" args="[entityName]" />
            </div>
            <div class="list-boxtitle-right">
            %{--<a href="' + liveURL + '/dashboardExpanseList/budget/' + tempmonthVendorId + '?g=' + tempGlAccount + '&m='+(monthc+1)+'" target="_blank" title="Total Invoice: ' + showCountBudget + '&#xD; Entered Invoice: ' + showCountBudgetInvoice + '" class="tooltip">--}%
            %{--<a style="font-size: 12px; text-decoration: none;" href="">Create New Invoice</a>--}%
                <g:form controller="budgetItemIncomeDetails" action="list" id="1">

                    <g:hiddenField name="order" value="${params.g}" />
                    <g:hiddenField name="customerId" value="${params.id}" />
                    <g:hiddenField name="bookingPeriod" value="${params.m}" />
                    <g:hiddenField name="max" value="${params.max}" />
                    <div class="buttonClass" style="margin: 5px 0 0 0">
                        <div class="buttonCLassInner">
                            <g:submitButton name="create" class="save" value="${message(code: 'default.button.createnew.label', default: 'Create New Budget')}" />
                        </div>
                    </div>

                </g:form>
            </div>
        </div>

        <table>
            <thead>
            <tr class="even">
                <td width="350px" colspan="2"><b><g:message code="DashboardListIncomeBudget.FiscalYear.list.label" default="Fiscal Year" /> :</b> ${FiscalYearInfo[0][1]} TO  ${FiscalYearInfo[0][2]}</td>
                <td width="350px" colspan="2"><b><g:message code="DashboardListIncomeBudget.CustomerName.list.label" default="Customer Name" /> :</b> ${CustomerNameInstance}</td>
            </tr>
            <tr class="even">
                <td width="350px" colspan="2"><b><g:message code="DashboardListIncomeBudget.GLAccountCode.list.label" default="GL Account Code" /> :</b> ${glAccountInstance}</td>
                <td width="350px" colspan="2"><b><g:message code="DashboardListIncomeBudget.BookingPeriod.list.label" default="Booking Period" /> :</b> ${monthShowInstance}</td>
            </tr>
            </thead>

            <thead>

            <tr>
                <th><g:message code="DashboardListIncomeBudget.BudgetItem.label" default="Budget Item" /></th>
                <th><g:message code="DashboardListIncomeBudget.TransDate.label" default="Creation Date" /></th>
                <th><g:message code="DashboardListIncomeBudget.TotalAmount.label" default="Total Amount" /></th>
                <th><g:message code="DashboardListIncomeBudget.Action.label" default="Action" /></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${invoiceListInstanceList}" status="i" var="invoiceListInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td>${invoiceListInstance[1]}</td>
                    <td>${invoiceListInstance[2]}</td>
                    <td>${invoiceListInstance[3]}</td>
                    <td>
                        <g:link controller="budgetItemIncomeDetails" action="listwisereport" id="${invoiceListInstance[0]}" ><g:img dir="images" file="edit.png" width="15" height="16"  alt="View" /></g:link>&nbsp;
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
        <div class="pagination">
            %{-- <g:paginate total="${fiscalYearInstanceTotal}" />--}%
        </div>

    </div>
</div>
</body>
</html>