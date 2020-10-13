<%@ page import="bv.DashboardDetailsTagLib" %>

<%
    def dashboardDetailsTagLib = new DashboardDetailsTagLib();

    int nNetProfitBasedOnBudget = dashboardDetailsTagLib.getRoundedValue(totalNetProfitAmount)
    int nGrossProfitBasedOnInvoice = dashboardDetailsTagLib.getRoundedValue(totalGrossProfitAmount)
%>

<div class="headerRightDiv1">
    <label class="categoryLabel"><g:message code="bv.dashboard.netProfit.label" default="Net Profit" /></label>
    <label class="amountLabel">${nNetProfitBasedOnBudget}</label>
</div>

<div class="headerRightDiv2">
    <label class="categoryLabel"><g:message code="bv.dashboard.grossProfit.label" default="Gross Profit" /></label>
    <label class="amountLabel">${nGrossProfitBasedOnInvoice}</label>
</div>