<%@ page import="bv.BusinessCompany; java.text.SimpleDateFormat; bv.UserTagLib; bv.CoreParamsHelper; bv.BudgetDetailsTagLib; bv.Dashboard; org.springframework.web.servlet.support.RequestContextUtils" %>
<!doctype html>
<%
    def protocol = request.isSecure() ? "https://" : "http://"
    def host = request.getServerName()
    def port = request.getServerPort()
    def context = request.getServletContext().getContextPath()

    def dashboardURL = ""
    dashboardURL = protocol + host + ":" + port + context + "/dashboardDetails/incomeAndExpenseNameWise"

    //def dashboardDetailsTagLib = new DashboardDetailsTagLib();

    //int nNetProfitBasedOnBudget = dashboardDetailsTagLib.getRoundedValue(totalNetProfitAmount)
    //int nGrossProfitBasedOnInvoice = dashboardDetailsTagLib.getRoundedValue(totalGrossProfitAmount)
%>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Budget View"/></title>
    %{--<meta http-equiv="cache-control" content="max-age=0" />--}%
    %{--<meta http-equiv="cache-control" content="no-cache" />--}%
    %{--<meta http-equiv="expires" content="0" />--}%
    %{--<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />--}%
    %{--<meta http-equiv="pragma" content="no-cache" />--}%

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">

    <asset:stylesheet href="budget_view_main_mnf.css"/>
    <asset:stylesheet href="budget_view_plugin_mnf.css"/>

    <asset:javascript src="jquery-et-al.js"/> %{-- Arafat: Making Defered JS might create problems--}%
    <asset:javascript src="jquery-ui-1.9.2.legacy/jquery-ui.js"/>
    <asset:javascript src="datatable-et-al.js"/>
    <asset:javascript src="jqwidgets.js"/>
    <asset:javascript src="jqwidgets2.js"/>
    <script src="//cdn.datatables.net/plug-ins/1.10.11/sorting/date-eu.js" type="text/javascript"></script>


    <g:layoutHead/>
    <script>

        var appContext = '${request.contextPath}';
        var sessionExpMsg = '<g:message code="session.expiry.msg" default="Your session is about to finish, do you want to keep current session?"/>';
    </script>

</head>
<body>

<g:render template="/common/header" />
<div id="content-whole">
    <div id="content-whole-inner">
        <div class="logoDiv">

            <div class="logoDivLeft">
                <a href="#"><img src="${assetPath(src: 'logo.png')}" alt="Budget View"/></a>
            </div>

            <span class="businessComName"> <%if ( session.permittedBusinessCompanyId == null) {%>
            <g:link style="text-decoration: none;" url="#"><sec:loggedInUserInfo field="username"/></g:link>

            <% } else  {
                BusinessCompany businessCompany = BusinessCompany.findById(session.permittedBusinessCompanyId)
            %>
            <g:link style="text-decoration: none;" url="#">${businessCompany.name}</g:link>
                <%}%></span>

            <div class="logoDivRight">
                <div id="spinner" class="spinner" style="display:none;">
                    <img src="${assetPath(src:'spinner.gif')}" alt="${message(code:'spinner.alt.Loading',default:'Loading...')}" />
                </div>
            </div>
        </div>

        <g:layoutBody/>
        <div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt.Loading&hellip" default="Loading&hellip;"/></div>
    </div>

    <g:render template="/common/footer" />

</div>


<script type="text/javascript">
    $(document).ready(function(){

        //Mega menu init
        $('#mega-menu-1').dcMegaMenu({
            rowItems: '3',
            speed: 'fast',
            effect: 'slide'});

        $('.inner').corner('3px');
        //$('.pagination').corner('bottom');
        $('.content').corner('3px');
        $('.buttons').corner('3px');
        $('.save').corner('3px');
        $('#income').corner('3px');
        $('#footer').corner('bottom');
        $('.allIncomeHead').corner('top');
        $('input').corner('3px');

        //Help dialog init
        $( "#helpinfodlg" ).dialog({ autoOpen: false, show : "scale", hide : "scale",height: 'auto',width: 450,});
        $( "#helpBtn" ).click(function() {
            var position =  $("#helpBtn").position();
            $("#helpinfodlg").dialog("option", "position", [position.left-40, position.top+70]);
            $( "#helpinfodlg" ).dialog("open");
        });

//        $('table.highchart').highchartTable();
    });


</script>

</body>
</html>

