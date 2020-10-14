<%@ page import="bv.FiscalYear" %>
<%@ page import="bv.CoreParamsHelper" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'DashboardListExpanseRegularSuppliers.label', default: 'Expanse Regular Suppliers Invoice(s)')}" />
    <title><g:message code="DashboardListExpanseRegularSuppliers.label" default="Expanse Regular Suppliers Invoice(s)" args="[entityName]" /></title>
</head>
<body>
<a href="#list-DashboardListExpanse" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>


<div id="list-page-body-inner" class="content">
    <div id="list-page">
        <div class="list-boxtitle">
            <div class="list-boxtitle-left">
                <g:message code="DashboardListExpanseRegularSuppliers.invoice.list.label" default="Expanse Regular Suppliers Invoice(s)" args="[entityName]" />
            </div>

        </div>

        <table>
            <thead>
            <tr class="even">
                <td width="350px" colspan="3"><b><g:message code="DashboardListIncome.FiscalYear.list.label" default="Fiscal Year" /> :</b> ${FiscalYearInfo[0][1]} TO  ${FiscalYearInfo[0][2]}</td>
                <td width="350px" colspan="3"><b><g:message code="DashboardListIncome.CustomerName.list.label" default="Vendor Name" /> :</b> ${CustomerNameInstance}</td>
            </tr>
            <tr class="even">
                <td width="350px" colspan="3"><b><g:message code="DashboardListIncome.GLAccountCode.list.label" default="GL Account Code" /> :</b> ${glAccountInstance}</td>
                <td width="350px" colspan="3"><b><g:message code="DashboardListIncome.BookingPeriod.list.label" default="Booking Period" /> :</b> ${monthShowInstance}</td>
            </tr>
            </thead>

            <thead>

            <tr>
                <th><g:message code="DashboardListIncome.InvoiceNo.label" default="Invoice No" /></th>
                <th><g:message code="DashboardListIncome.BudgetItem.label" default="Budget Item" /></th>
                <th><g:message code="DashboardListIncome.TransDate.label" default="Trans Date" /></th>
                <th><g:message code="DashboardListIncome.DueDate.label" default="Due Date" /></th>
                %{--<th><g:message code="DashboardListIncome.TotalVAT.label" default="Total VAT" /></th>--}%
                <th><g:message code="DashboardListIncome.TotalAmount.label" default="Total Amount" /></th>
                <th><g:message code="DashboardListIncome.Action.label" default="Action" /></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${invoiceListInstanceList}" status="i" var="invoiceListInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td>${invoiceListInstance[8]}</td>
                    <td>${invoiceListInstance[7]}</td>
                    <td>${invoiceListInstance[9]}</td>
                    <td>${invoiceListInstance[10]}</td>
                    <td>${invoiceListInstance[5]}</td>
                    <td>
                        <g:link controller="invoiceExpense" action="listwisereport" id="${invoiceListInstance[0]}" ><g:img dir="images" file="edit.png" width="15" height="16"  alt="View" /></g:link>&nbsp;
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