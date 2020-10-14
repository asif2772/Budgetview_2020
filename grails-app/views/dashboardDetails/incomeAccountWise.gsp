<%@ page import="bv.DashboardDetails" %>
<!DOCTYPE html>
<style type="text/css">
.jspHorizontalBar {
    bottom: 0;
}

#create-dashboardDetails form {
    margin-top: -25px;
}
</style>
<html>
<head>
    <meta name="layout" content="main2">
</head>

<body>

<div id="create-dashboardDetails" class="scaffold-create" role="main" style="margin-top:0px;margin-bottom:0px;">
    <fieldset class="form" style="background: none repeat scroll 0 0 #fcfcfc;">
        <g:render template="formIncomeAccountWise"/>
    </fieldset>
</div>
</body>
</html>