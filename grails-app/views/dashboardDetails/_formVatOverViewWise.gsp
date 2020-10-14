<%@ page import="bv.InvoiceExpense; bv.CoreParamsHelper; bv.BudgetItemExpense; bv.DashboardDetails" %>
<g:javascript>
    $(function () {
        $("#accordion").accordion({
            autoHeight:false,
            navigation:true
        });
    });
</g:javascript>

<script>
    $(function () {
        $('.months').jScrollPane();
    });
</script>
<style type="text/css" media="screen">
#status {
    background-color: #eee;
    border: .2em solid #fff;
    margin: 2em 2em 1em;
    padding: 1em;
    width: 12em;
    float: left;
}

.ie6 #status {
    display: inline; /* float double margin fix http://www.positioniseverything.net/explorer/doubled-margin.html */
}

#status ul {
    font-size: 0.9em;
    list-style-type: none;
    margin-bottom: 0.6em;
    padding: 0;
}

#status li {
    line-height: 1.3;
}

#status h1 {
    text-transform: uppercase;
    font-size: 1.1em;
    margin: 0 0 0.3em;
}

h2 {
    margin-top: 1em;
    margin-bottom: 0.3em;
    font-size: 1em;
}

p {
    line-height: 1.5;
    margin: 0.25em 0;
}

#controller-list ul {
    list-style-position: inside;
}

#controller-list li {
    line-height: 1.3;
    list-style-position: inside;
    margin: 0.25em 0;
}

.jspVerticalBar {
    width: 8px;
    background: transparent;
    right: 10px;
}

.jspHorizontalBar {
    bottom: 5px;
    width: 100%;
    height: 8px;
    background: transparent;
}

.jspTrack {
    background: transparent;
}

/*.jspDrag {*/
    /*background: url("../images/transparent-black.png") repeat;*/
    /*-webkit-border-radius: 4px;*/
    /*-moz-border-radius: 4px;*/
    /*border-radius: 4px;*/
    /*border-radius: 4px 4px 4px 4px;*/
/*}*/

.jspHorizontalBar .jspTrack,
.jspHorizontalBar .jspDrag {
    float: left;
    height: 100%;
    margin-left: 1px;
    margin-top: 2px;
}

.jspCorner {
    display: none
}

@media screen and (max-width: 480px) {
    #status {
        display: none;
    }

    #page-body {
        margin: 0 1em 1em;
    }

    #page-body h1 {
        margin-top: 0;
    }
}

.tooltip {
    display: inline;
    position: relative;
    text-decoration: none;
}

.tooltip:hover {
    text-decoration: none;
}

.tooltip:hover:after {
    background: #111;
    background: rgba(0, 0, 0, .8);
    border-radius: 5px;
    bottom: 18px;
    color: #fff;
    content: attr(title);
    display: block;
    left: 50%;
    padding: 5px 15px;
    position: absolute;
    white-space: nowrap;
    z-index: 98

}

.tooltip:hover:before {
    border: solid;
    border-color: #111 transparent;
    border-width: 6px 6px 0 6px;
    bottom: 12px;
    content: "";
    display: block;
    left: 75%;
    position: absolute;
    z-index: 99
}

</style>
<%
    for (int v = 0; v < 1; v++) {
%>

<div id="list-page-body-inner" class="content" style="background-color: #fff; border: none; margin: 0">

<div id="income" style="padding: 0px; border-radius:none;">

<div class="accountHead" style="width:27%">
    <div class="colTitle" style="text-align: center;">${message(code: 'bv.dashBoardHeaderSummeryKeyNumbers.label', default: 'Key Numbers')}</div>

    <div class="budgetItemCol" style="margin-top: 1px;">
        <div class="accountHeadItem odd" style="background:#BFBFBF;color:#FFFFFF; line-height: 30px;"> ${message(code: 'bv.dashBoardHeaderSummeryGrossProfit.label', default: 'Gross Profit(Income-Expense Invoices)')}</div>
        <div class="accountHeadItem even" style="background:#BFBFBF;color:#FFFFFF;margin-top: 1px;line-height: 30px;  "> ${message(code: 'bv.dashBoardHeaderSummeryIncomeTaxReservation.label', default: 'Income Tax Reservation')}&nbsp;(${IncomeTaxPercentage}%)</div>
        <div class="accountHeadItem odd" style="background:#808080;color:#FFFFFF;margin-top: 1px;line-height: 30px;  "> ${message(code: 'bv.dashBoardHeaderSummeryNetProfitBasedBudget.label', default: 'Net Profit Based on Budget')}</div>
        <div class="accountHeadItem even" style="background:#538DD5;color:#FFFFFF;margin-top: 1px;line-height: 30px;  "> ${message(code: 'bv.dashBoardHeaderSummeryNetProfitBasedBookedInvoices.label', default: 'Net Profit Based on Booked Invoices')}</div>
        <div class="accountHeadItem odd" style="background:#538DD5;color:#FFFFFF;margin-top: 1px;line-height: 30px; "> ${message(code: 'bv.dashBoardHeaderSummeryNetProfitBasedForecast.label', default: 'Net Profit Based on Forecast')}</div>
    </div>
</div>

<div class="months" id="scrollbar1">
<div class="monthName" style="line-height: 30px;">
    <div class="div11">Jan<div></div>
    </div>

    <div class="div12">Feb<div></div>
    </div>

    <div class="div13">Mar<div></div>
    </div>

    <div class="div14">Apr<div></div>
    </div>

    <div class="div15">May<div></div>
    </div>

    <div class="div16">Jun<div></div>
    </div>

    <div class="div17">Jul<div></div>
    </div>

    <div class="div18">Aug<div></div>
    </div>

    <div class="div19">Sep<div></div>
    </div>

    <div class="div20">Oct<div></div>
    </div>

    <div class="div21">Nov<div></div>
    </div>

    <div class="div22">Dec<div></div>
    </div>
</div>


<div class="monthItems" style="margin-top: 1px;">
<%
        def  showGrossProfit
        Double sumGrossProfit=0.00
        for (int monthc = 1; monthc <= 12; monthc++) {
            if (monthc == 1) {
                showGrossProfit = grossProfitInstance['janGross']
                sumGrossProfit = sumGrossProfit+grossProfitInstance['janGross']
            }
            else if (monthc == 2) {
                showGrossProfit = grossProfitInstance['febGross']
                sumGrossProfit = sumGrossProfit+grossProfitInstance['febGross']
            }
            else if (monthc == 3) {
                showGrossProfit = grossProfitInstance['marGross']
                sumGrossProfit = sumGrossProfit+grossProfitInstance['marGross']
            }
            else if (monthc == 4) {
                showGrossProfit = grossProfitInstance['aprGross']
                sumGrossProfit = sumGrossProfit+grossProfitInstance['aprGross']
            }
            else if (monthc == 5) {
                showGrossProfit = grossProfitInstance['mayGross']
                sumGrossProfit = sumGrossProfit+grossProfitInstance['mayGross']
            }
            else if (monthc == 6) {
                showGrossProfit = grossProfitInstance['junGross']
                sumGrossProfit = sumGrossProfit+grossProfitInstance['junGross']
            }
            else if (monthc == 7) {
                showGrossProfit = grossProfitInstance['julGross']
                sumGrossProfit = sumGrossProfit+grossProfitInstance['julGross']
            }
            else if (monthc == 8) {
                showGrossProfit = grossProfitInstance['augGross']
                sumGrossProfit = sumGrossProfit+grossProfitInstance['augGross']
            }
            else if (monthc == 9) {
                showGrossProfit = grossProfitInstance['sepGross']
                sumGrossProfit = sumGrossProfit+grossProfitInstance['sepGross']
            }
            else if (monthc == 10) {
                showGrossProfit = grossProfitInstance['octGross']
                sumGrossProfit = sumGrossProfit+grossProfitInstance['octGross']
            }
            else if (monthc == 11) {
                showGrossProfit = grossProfitInstance['novGross']
                sumGrossProfit = sumGrossProfit+grossProfitInstance['novGross']
            }
            else if (monthc == 12) {
                showGrossProfit = grossProfitInstance['decGross']
                sumGrossProfit = sumGrossProfit+grossProfitInstance['decGross']
            }
%>
<div class="div31 odd" style="background:#BFBFBF;color:#FFFFFF;line-height: 30px; ">${Math.round(new BigDecimal(showGrossProfit).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
<%
        }
%>

<%
        def  showIncomeTaxReservation
        Double sumIncomeTaxReservation=0.00
        for (int monthc = 1; monthc <= 12; monthc++) {
            if (monthc == 1) {
                showIncomeTaxReservation = incomeTaxReservationInstance['janITR']
                sumIncomeTaxReservation = sumIncomeTaxReservation+incomeTaxReservationInstance['janITR']
            }
            else if (monthc == 2) {
                showIncomeTaxReservation = incomeTaxReservationInstance['febITR']
                sumIncomeTaxReservation = sumIncomeTaxReservation+incomeTaxReservationInstance['febITR']
            }
            else if (monthc == 3) {
                showIncomeTaxReservation = incomeTaxReservationInstance['marITR']
                sumIncomeTaxReservation = sumIncomeTaxReservation+incomeTaxReservationInstance['marITR']
            }
            else if (monthc == 4) {
                showIncomeTaxReservation = incomeTaxReservationInstance['aprITR']
                sumIncomeTaxReservation = sumIncomeTaxReservation+incomeTaxReservationInstance['aprITR']
            }
            else if (monthc == 5) {
                showIncomeTaxReservation = incomeTaxReservationInstance['mayITR']
                sumIncomeTaxReservation = sumIncomeTaxReservation+incomeTaxReservationInstance['mayITR']
            }
            else if (monthc == 6) {
                showIncomeTaxReservation = incomeTaxReservationInstance['junITR']
                sumIncomeTaxReservation = sumIncomeTaxReservation+incomeTaxReservationInstance['junITR']
            }
            else if (monthc == 7) {
                showIncomeTaxReservation = incomeTaxReservationInstance['julITR']
                sumIncomeTaxReservation = sumIncomeTaxReservation+incomeTaxReservationInstance['julITR']
            }
            else if (monthc == 8) {
                showIncomeTaxReservation = incomeTaxReservationInstance['augITR']
                sumIncomeTaxReservation = sumIncomeTaxReservation+incomeTaxReservationInstance['augITR']
            }
            else if (monthc == 9) {
                showIncomeTaxReservation = incomeTaxReservationInstance['sepITR']
                sumIncomeTaxReservation = sumIncomeTaxReservation+incomeTaxReservationInstance['sepITR']
            }
            else if (monthc == 10) {
                showIncomeTaxReservation = incomeTaxReservationInstance['octITR']
                sumIncomeTaxReservation = sumIncomeTaxReservation+incomeTaxReservationInstance['octITR']
            }
            else if (monthc == 11) {
                showIncomeTaxReservation = incomeTaxReservationInstance['novITR']
                sumIncomeTaxReservation = sumIncomeTaxReservation+incomeTaxReservationInstance['novITR']
            }
            else if (monthc == 12) {
                showIncomeTaxReservation = incomeTaxReservationInstance['decITR']
                sumIncomeTaxReservation = sumIncomeTaxReservation+incomeTaxReservationInstance['decITR']
            }
%>
<div class="div31 even" style="background:#BFBFBF;color:#FFFFFF; border-top:1px #FFFFFF;margin-top: 1px;line-height: 30px;">${Math.round(new BigDecimal(showIncomeTaxReservation).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
<%
        }
%>

<%
        def  showNetProfitBudget
        Double  sumNetProfitBudget=0.00
        for (int monthc = 1; monthc <= 12; monthc++) {
            if (monthc == 1) {
                showNetProfitBudget = netProfitBudgetInstance['janBNP']
                sumNetProfitBudget = sumNetProfitBudget+netProfitBudgetInstance['janBNP']
            }
            else if (monthc == 2) {
                showNetProfitBudget = netProfitBudgetInstance['febBNP']
                sumNetProfitBudget = sumNetProfitBudget+netProfitBudgetInstance['febBNP']
            }
            else if (monthc == 3) {
                showNetProfitBudget = netProfitBudgetInstance['marBNP']
                sumNetProfitBudget = sumNetProfitBudget+netProfitBudgetInstance['marBNP']
            }
            else if (monthc == 4) {
                showNetProfitBudget = netProfitBudgetInstance['aprBNP']
                sumNetProfitBudget = sumNetProfitBudget+netProfitBudgetInstance['aprBNP']
            }
            else if (monthc == 5) {
                showNetProfitBudget = netProfitBudgetInstance['mayBNP']
                sumNetProfitBudget = sumNetProfitBudget+netProfitBudgetInstance['mayBNP']
            }
            else if (monthc == 6) {
                showNetProfitBudget = netProfitBudgetInstance['junBNP']
                sumNetProfitBudget = sumNetProfitBudget+netProfitBudgetInstance['junBNP']
            }
            else if (monthc == 7) {
                showNetProfitBudget = netProfitBudgetInstance['julBNP']
                sumNetProfitBudget = sumNetProfitBudget+netProfitBudgetInstance['julBNP']
            }
            else if (monthc == 8) {
                showNetProfitBudget = netProfitBudgetInstance['augBNP']
                sumNetProfitBudget = sumNetProfitBudget+netProfitBudgetInstance['augBNP']
            }
            else if (monthc == 9) {
                showNetProfitBudget = netProfitBudgetInstance['sepBNP']
                sumNetProfitBudget = sumNetProfitBudget+netProfitBudgetInstance['sepBNP']
            }
            else if (monthc == 10) {
                showNetProfitBudget = netProfitBudgetInstance['octBNP']
                sumNetProfitBudget = sumNetProfitBudget+netProfitBudgetInstance['octBNP']
            }
            else if (monthc == 11) {
                showNetProfitBudget = netProfitBudgetInstance['novBNP']
                sumNetProfitBudget = sumNetProfitBudget+netProfitBudgetInstance['novBNP']
            }
            else if (monthc == 12) {
                showNetProfitBudget = netProfitBudgetInstance['decBNP']
                sumNetProfitBudget = sumNetProfitBudget+netProfitBudgetInstance['decBNP']
            }
%>
<div class="div31 odd" style="background:#808080;color:#FFFFFF;margin-top: 1px;line-height: 30px; ">${Math.round(new BigDecimal(showNetProfitBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
<%
        }
%>
<%

        def  showNetProfitInvoice
        Double  sumNetProfitInvoice=0.00
        for (int monthc = 1; monthc <= 12; monthc++) {
            if (monthc == 1) {
                showNetProfitInvoice = netProfitInvoiceInstance['janINP']
                sumNetProfitInvoice = sumNetProfitInvoice+netProfitInvoiceInstance['janINP']
            }
            else if (monthc == 2) {
                showNetProfitInvoice = netProfitInvoiceInstance['febINP']
                sumNetProfitInvoice = sumNetProfitInvoice+netProfitInvoiceInstance['febINP']
            }
            else if (monthc == 3) {
                showNetProfitInvoice = netProfitInvoiceInstance['marINP']
                sumNetProfitInvoice = sumNetProfitInvoice+netProfitInvoiceInstance['marINP']
            }
            else if (monthc == 4) {
                showNetProfitInvoice = netProfitInvoiceInstance['aprINP']
                sumNetProfitInvoice = sumNetProfitInvoice+netProfitInvoiceInstance['aprINP']
            }
            else if (monthc == 5) {
                showNetProfitInvoice = netProfitInvoiceInstance['mayINP']
                sumNetProfitInvoice = sumNetProfitInvoice+netProfitInvoiceInstance['mayINP']
            }
            else if (monthc == 6) {
                showNetProfitInvoice = netProfitInvoiceInstance['junINP']
                sumNetProfitInvoice = sumNetProfitInvoice+netProfitInvoiceInstance['junINP']
            }
            else if (monthc == 7) {
                showNetProfitInvoice = netProfitInvoiceInstance['julINP']
                sumNetProfitInvoice = sumNetProfitInvoice+netProfitInvoiceInstance['julINP']
            }
            else if (monthc == 8) {
                showNetProfitInvoice = netProfitInvoiceInstance['augINP']
                sumNetProfitInvoice = sumNetProfitInvoice+netProfitInvoiceInstance['augINP']
            }
            else if (monthc == 9) {
                showNetProfitInvoice = netProfitInvoiceInstance['sepINP']
                sumNetProfitInvoice = sumNetProfitInvoice+netProfitInvoiceInstance['sepINP']
            }
            else if (monthc == 10) {
                showNetProfitInvoice = netProfitInvoiceInstance['octINP']
                sumNetProfitInvoice = sumNetProfitInvoice+netProfitInvoiceInstance['octINP']
            }
            else if (monthc == 11) {
                showNetProfitInvoice = netProfitInvoiceInstance['novINP']
                sumNetProfitInvoice = sumNetProfitInvoice+netProfitInvoiceInstance['novINP']
            }
            else if (monthc == 12) {
                showNetProfitInvoice = netProfitInvoiceInstance['decINP']
                sumNetProfitInvoice = sumNetProfitInvoice+netProfitInvoiceInstance['decINP']
            }
%>
<div class="div31 even" style="background:#538DD5;color:#FFFFFF;margin-top: 1px;line-height: 30px;">${Math.round(new BigDecimal(showNetProfitInvoice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
<%
        }
%>

<%
        def  showProfitCurrentForecast
        Double  sumProfitCurrentForecast=0.00
        for (int monthc = 1; monthc <= 12; monthc++) {
            if (monthc == 1) {
                showProfitCurrentForecast = netProfitCurrentForecastInstance['janCFNP']
                sumProfitCurrentForecast = sumProfitCurrentForecast+netProfitCurrentForecastInstance['janCFNP']
            }
            else if (monthc == 2) {
                showProfitCurrentForecast = netProfitCurrentForecastInstance['febCFNP']
                sumProfitCurrentForecast = sumProfitCurrentForecast+netProfitCurrentForecastInstance['febCFNP']
            }
            else if (monthc == 3) {
                showProfitCurrentForecast = netProfitCurrentForecastInstance['marCFNP']
                sumProfitCurrentForecast = sumProfitCurrentForecast+netProfitCurrentForecastInstance['marCFNP']
            }
            else if (monthc == 4) {
                showProfitCurrentForecast = netProfitCurrentForecastInstance['aprCFNP']
                sumProfitCurrentForecast = sumProfitCurrentForecast+netProfitCurrentForecastInstance['aprCFNP']
            }
            else if (monthc == 5) {
                showProfitCurrentForecast = netProfitCurrentForecastInstance['mayCFNP']
                sumProfitCurrentForecast = sumProfitCurrentForecast+netProfitCurrentForecastInstance['mayCFNP']
            }
            else if (monthc == 6) {
                showProfitCurrentForecast = netProfitCurrentForecastInstance['junCFNP']
                sumProfitCurrentForecast = sumProfitCurrentForecast+netProfitCurrentForecastInstance['junCFNP']
            }
            else if (monthc == 7) {
                showProfitCurrentForecast = netProfitCurrentForecastInstance['julCFNP']
                sumProfitCurrentForecast = sumProfitCurrentForecast+netProfitCurrentForecastInstance['julCFNP']
            }
            else if (monthc == 8) {
                showProfitCurrentForecast = netProfitCurrentForecastInstance['augCFNP']
                sumProfitCurrentForecast = sumProfitCurrentForecast+netProfitCurrentForecastInstance['augCFNP']
            }
            else if (monthc == 9) {
                showProfitCurrentForecast = netProfitCurrentForecastInstance['sepCFNP']
                sumProfitCurrentForecast = sumProfitCurrentForecast+netProfitCurrentForecastInstance['sepCFNP']
            }
            else if (monthc == 10) {
                showProfitCurrentForecast = netProfitCurrentForecastInstance['octCFNP']
                sumProfitCurrentForecast = sumProfitCurrentForecast+netProfitCurrentForecastInstance['octCFNP']
            }
            else if (monthc == 11) {
                showProfitCurrentForecast = netProfitCurrentForecastInstance['novCFNP']
                sumProfitCurrentForecast = sumProfitCurrentForecast+netProfitCurrentForecastInstance['novCFNP']
            }
            else if (monthc == 12) {
                showProfitCurrentForecast = netProfitCurrentForecastInstance['decCFNP']
                sumProfitCurrentForecast = sumProfitCurrentForecast+netProfitCurrentForecastInstance['decCFNP']
            }
%>
<div class="div31 odd" style="background:#538DD5;color:#FFFFFF;margin-top: 1px;line-height: 30px;">${Math.round(new BigDecimal(showProfitCurrentForecast).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
<%
        }
%>
</div>
</div>
<div class="budgetTotal">
    <div class="colTitle">
        ${message(code: 'dashboard.incomeBudgetTotal.label', default: 'Total')}
    </div>

    <div class="budgetItemCol" style="margin-top: 1px;">


        <div class="accountHeadItem odd" style="background:#BFBFBF;color:#FFFFFF;text-align: center;line-height: 30px;">${Math.round(new BigDecimal(sumGrossProfit).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
        <div class="accountHeadItem even" style="background:#BFBFBF;color:#FFFFFF;text-align: center;margin-top: 1px;line-height: 30px;">${Math.round(new BigDecimal(sumIncomeTaxReservation).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
        <div class="accountHeadItem odd" style="background:#808080;color:#FFFFFF;text-align: center;margin-top: 1px;line-height: 30px;">${Math.round(new BigDecimal(sumNetProfitBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
        <div class="accountHeadItem even" style="background:#538DD5;color:#FFFFFF;text-align: center;margin-top: 1px;line-height: 30px;">${Math.round(new BigDecimal(sumNetProfitInvoice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
        <div class="accountHeadItem odd" style="background:#538DD5;color:#FFFFFF;text-align: center;margin-top: 1px;line-height: 30px;">${Math.round(new BigDecimal(sumProfitCurrentForecast).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>

        <div class="accountHeadItem"
             style="text-align: center; height:16px;">&nbsp;</div>
    </div>
</div>
</div>
</div>
<% } %>
</div>