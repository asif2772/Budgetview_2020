<%@ page import="bv.DashboardDetails" %>

<!DOCTYPE html>
<style type="text/css">
.jspHorizontalBar{
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

<div id="create-dashboardDetails" class="content scaffold-create" role="main" style="margin-top:0px;margin-bottom:0px; border: none;">
    <fieldset class="form" style="background: none repeat scroll 0 0 #fcfcfc;">
        <g:render template="formIncomeExpenseNameWise"/>
    </fieldset>
</div>
</body>
</html>