<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main2">
</head>
<body>

<div id="create-dashboardDetails" class="content scaffold-create" role="main" style="margin-top:0px;margin-bottom:0px; background-color: #fff;border: medium none;border-radius: 3px;">
    <fieldset class="form">
        <%if(budgetItemType == "reservationBudget" ){%>
            <g:render template="formReservationBudNBookWithoutTax"/>
        <%} else if(taxReservType == "taxWithReservation" && summaryType == "budNForcast"){%>
            <g:render template="formBudgetAndForecastWithTax"/>
        <%} else  if(taxReservType == "taxWithoutReservation" && summaryType == "budNForcast"){%>
            <g:render template="formBudgetAndForecastWithoutTax"/>
        <%} else  if(taxReservType == "taxWithReservation" && summaryType == "budNBook"){%>
            <g:render template="formBudgetAndBookingWithTax"/>
        <%} else  if(taxReservType == "taxWithoutReservation" && summaryType == "budNBook"){%>
            <g:render template="formBudgetAndBookingWithoutTax"/>
        <%} else  if(taxReservType == "taxWithReservation" && summaryType == "bookNForecast"){%>
            <g:render template="formForecastAndBookingWithTax"/>
        <%} else  if(taxReservType == "taxWithoutReservation" && summaryType == "bookNForecast"){%>
            <g:render template="formForecastAndBookingWithoutTax"/>
        <%}%>

    </fieldset>
</div>
</body>
</html>