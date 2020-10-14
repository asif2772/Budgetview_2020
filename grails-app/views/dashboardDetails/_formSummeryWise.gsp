<%@ page import="bv.DashboardDetails" %>
<g:javascript>
    $(function () {
        $('#accordion').multiOpenAccordion({
            active:[1],
            click:function (event, ui) {
                console.log('clicked')
            },
            init:function (event, ui) {
                //console.log('whoooooha')
            },
            tabShown:function (event, ui) {
                //console.log('shown')
            },
            tabHidden:function (event, ui) {
                //console.log('hidden')
            }
        });

//        $('#accordion').multiOpenAccordion("option", "active", [0]);
        $('#accordion').multiOpenAccordion("option", "active", 'none');
    });
</g:javascript>

<script>
    $(function () {
        $('.months').jScrollPane();
        $('.allForcast-mid').jScrollPane();
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
    /*  -moz-box-shadow: 0px 0px 1.25em #ccc;
   -webkit-box-shadow: 0px 0px 1.25em #ccc;
   box-shadow: 0px 0px 1.25em #ccc;
   -moz-border-radius: 0.6em;
   -webkit-border-radius: 0.6em;
   border-radius: 0.6em;*/
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


<div id="accordion">

<%
    Integer loopmonth = 0
    double summaryjanBudgetHead = 0.0
    double summaryfebBudgetHead = 0.0
    double summarymarBudgetHead = 0.0
    double summaryaprBudgetHead = 0.0
    double summarymayBudgetHead = 0.0
    double summaryjunBudgetHead = 0.0
    double summaryjulBudgetHead = 0.0
    double summaryaugBudgetHead = 0.0
    double summarysepBudgetHead = 0.0
    double summaryoctBudgetHead = 0.0
    double summarynovBudgetHead = 0.0
    double summarydecBudgetHead = 0.0

    double InvsummaryjanBudgetHead = 0.0
    double InvsummaryfebBudgetHead = 0.0
    double InvsummarymarBudgetHead = 0.0
    double InvsummaryaprBudgetHead = 0.0
    double InvsummarymayBudgetHead = 0.0
    double InvsummaryjunBudgetHead = 0.0
    double InvsummaryjulBudgetHead = 0.0
    double InvsummaryaugBudgetHead = 0.0
    double InvsummarysepBudgetHead = 0.0
    double InvsummaryoctBudgetHead = 0.0
    double InvsummarynovBudgetHead = 0.0
    double InvsummarydecBudgetHead = 0.0

    if (vendorInstance.size()) {
        for (int v = 0; v < 2; v++) {
%>
<% if (v == 0) { %>
<h3><g:message code="dashBoard.regularSuppliers.label" default="By Vendor"/></h3>
<% } else if (v == 1) { %>
<h3><g:message code="dashBoard.shoppingSpend.label" default="By Account Head"/></h3>
<% } %>



<div id="vendorid${v}" style="padding: 0px; border-radius: 0px;  margin: 0">

<div id="list-page-body-inner" class="content" style="background-color: #fff; border: none; margin: 0">

<div id="income" style="padding: 0px; border-radius:none;">

<div class="accountHead" style="width:27%">

    <% if (v == 0) { %>
    <div class="colTitle" style="text-align: left;">
        <g:message code="${message(code: 'invoiceExpense.gridList.vendorName.label', default: 'Vendor Name')}"/>
    </div>
    <% } else if (v == 1) { %>
    <div class="colTitle" style="text-align: left;">
        <g:message code="bv.invoiceIncomeDetails.accountCode.label" default="Account Head"/>
    </div>
    <% }
        Integer loop
        ArrayList glAccountArr = new ArrayList()

    %>

    <% if (v == 0) { %>
    <div class="budgetItemCol">
        <%
                loop = 1
                for (int i = 0; i < vendorInstance.size(); i++) {
                    loop++
        %>
        <div class="accountHeadItem ${(loop % 2) == 0 ? 'even' : 'odd'}">${vendorInstance[i][1]}</div>
        <%
                }
        %>
    </div>
    <% } else if (v == 1) { %>
    <div class="budgetItemCol">
        <%
                loop = 1
                for (int i = 0; i < accountInstance.size(); i++) {
                    loop++
        %>
        <div class="accountHeadItem ${(loop % 2) == 0 ? 'even' : 'odd'}">${accountInstance[i][0] + "-" + accountInstance[i][1]}</div>
        <%
                }
        %>
    </div>
    <% } %>
</div>

<%



            /////////////////SKB----TOTAL SUM//////////////////
            Integer summaryloopTotalMonth = 1
            def summarytotalTempGlAccount = ""
            def summarytotalTempmonthVendorId = "";

            double summaryjanBudget = 0.0
            double summaryfebBudget = 0.0
            double summarymarBudget = 0.0
            double summaryaprBudget = 0.0
            double summarymayBudget = 0.0
            double summaryjunBudget = 0.0
            double summaryjulBudget = 0.0
            double summaryaugBudget = 0.0
            double summarysepBudget = 0.0
            double summaryoctBudget = 0.0
            double summarynovBudget = 0.0
            double summarydecBudget = 0.0

            double InvsummaryjanBudget = 0.0
            double InvsummaryfebBudget = 0.0
            double InvsummarymarBudget = 0.0
            double InvsummaryaprBudget = 0.0
            double InvsummarymayBudget = 0.0
            double InvsummaryjunBudget = 0.0
            double InvsummaryjulBudget = 0.0
            double InvsummaryaugBudget = 0.0
            double InvsummarysepBudget = 0.0
            double InvsummaryoctBudget = 0.0
            double InvsummarynovBudget = 0.0
            double InvsummarydecBudget = 0.0


            for (int u = 0; u < vendorInstance.size(); u++) {
                double summarytotalTempAmount = 0.0
                summarytotalTempmonthVendorId = vendorInstance[u][0];

                vendorAccountInstance.each {phnBudgetRootRegular ->
                    phnBudgetRootRegular.each {phnBudgetRegular ->

                        if (phnBudgetRegular[0] == summarytotalTempmonthVendorId) {
                            if (phnBudgetRegular[3] == 1) {
                                summaryjanBudget = summaryjanBudget + phnBudgetRegular[4]
                            } else if (phnBudgetRegular[3] == 2) {
                                summaryfebBudget = summaryfebBudget + phnBudgetRegular[4]
                            } else if (phnBudgetRegular[3] == 3) {
                                summarymarBudget = summarymarBudget + phnBudgetRegular[4]
                            } else if (phnBudgetRegular[3] == 4) {
                                summaryaprBudget = summaryaprBudget + phnBudgetRegular[4]
                            } else if (phnBudgetRegular[3] == 5) {
                                summarymayBudget = summarymayBudget + phnBudgetRegular[4]
                            } else if (phnBudgetRegular[3] == 6) {
                                summaryjunBudget = summaryjunBudget + phnBudgetRegular[4]
                            } else if (phnBudgetRegular[3] == 7) {
                                summaryjulBudget = summaryjulBudget + phnBudgetRegular[4]
                            } else if (phnBudgetRegular[3] == 8) {
                                summaryaugBudget = summaryaugBudget + phnBudgetRegular[4]
                            } else if (phnBudgetRegular[3] == 9) {
                                summarysepBudget = summarysepBudget + phnBudgetRegular[4]
                            } else if (phnBudgetRegular[3] == 10) {
                                summaryoctBudget = summaryoctBudget + phnBudgetRegular[4]
                            } else if (phnBudgetRegular[3] == 11) {
                                summarynovBudget = summarynovBudget + phnBudgetRegular[4]
                            } else if (phnBudgetRegular[3] == 12) {
                                summarydecBudget = summarydecBudget + phnBudgetRegular[4]
                            }
                        }

                    }
                }
                def summarytotaltempInvoiceExpanseGlCode = ""
                def summarytotaltempInvoiceExpanseVendor = ""
                double summarytotalTempAmountInvoiceInvoice = 0.0

                vendorInvoiceAccountInstance.each {phnRootRegular ->

                    if (phnRootRegular.size() > 0) {
                        phnRootRegular.each {phnRegular ->

                            if (phnRegular[0] == Integer.parseInt(summarytotalTempmonthVendorId)) {


                                if (Integer.parseInt(phnRegular[3]) == 1) {
                                    InvsummaryjanBudget = InvsummaryjanBudget + phnRegular[4]
                                } else if (Integer.parseInt(phnRegular[3]) == 2) {
                                    InvsummaryfebBudget = InvsummaryfebBudget + phnRegular[4]
                                } else if (Integer.parseInt(phnRegular[3]) == 3) {
                                    InvsummarymarBudget = InvsummarymarBudget + phnRegular[4]
                                } else if (Integer.parseInt(phnRegular[3]) == 4) {
                                    InvsummaryaprBudget = InvsummaryaprBudget + phnRegular[4]
                                } else if (Integer.parseInt(phnRegular[3]) == 5) {
                                    InvsummarymayBudget = InvsummarymayBudget + phnRegular[4]
                                } else if (Integer.parseInt(phnRegular[3]) == 6) {
                                    InvsummaryjunBudget = InvsummaryjunBudget + phnRegular[4]
                                } else if (Integer.parseInt(phnRegular[3]) == 7) {
                                    InvsummaryjulBudget = InvsummaryjulBudget + phnRegular[4]
                                } else if (Integer.parseInt(phnRegular[3]) == 8) {
                                    InvsummaryaugBudget = InvsummaryaugBudget + phnRegular[4]
                                } else if (Integer.parseInt(phnRegular[3]) == 9) {
                                    InvsummarysepBudget = InvsummarysepBudget + phnRegular[4]
                                } else if (Integer.parseInt(phnRegular[3]) == 10) {
                                    InvsummaryoctBudget = InvsummaryoctBudget + phnRegular[4]
                                } else if (Integer.parseInt(phnRegular[3]) == 11) {
                                    InvsummarynovBudget = InvsummarynovBudget + phnRegular[4]
                                } else if (Integer.parseInt(phnRegular[3]) == 12) {
                                    InvsummarydecBudget = InvsummarydecBudget + phnRegular[4]
                                }
                            }

                        }
                    }
                }
            }
%>

<div class="months" id="scrollbar1">
<div class="monthName">
    <div class="div11">Jan<div>${Math.round(new BigDecimal(summaryjanBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())} / ${Math.round(new BigDecimal(InvsummaryjanBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
    </div>

    <div class="div12">Feb<div>${Math.round(new BigDecimal(summaryfebBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())} / ${Math.round(new BigDecimal(InvsummaryfebBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
    </div>

    <div class="div13">Mar<div>${Math.round(new BigDecimal(summarymarBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())} / ${Math.round(new BigDecimal(InvsummarymarBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
    </div>

    <div class="div14">Apr<div>${Math.round(new BigDecimal(summaryaprBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())} / ${Math.round(new BigDecimal(InvsummaryaprBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
    </div>

    <div class="div15">May<div>${Math.round(new BigDecimal(summarymayBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())} / ${Math.round(new BigDecimal(InvsummarymayBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
    </div>

    <div class="div16">Jun<div>${Math.round(new BigDecimal(summaryjunBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())} / ${Math.round(new BigDecimal(InvsummaryjunBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
    </div>

    <div class="div17">Jul<div>${Math.round(new BigDecimal(summaryjulBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())} / ${Math.round(new BigDecimal(InvsummaryjulBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
    </div>

    <div class="div18">Aug<div>${Math.round(new BigDecimal(summaryaugBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())} / ${Math.round(new BigDecimal(InvsummaryaugBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
    </div>

    <div class="div19">Sep<div>${Math.round(new BigDecimal(summarysepBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())} / ${Math.round(new BigDecimal(InvsummarysepBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
    </div>

    <div class="div20">Oct<div>${Math.round(new BigDecimal(summaryoctBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())} / ${Math.round(new BigDecimal(InvsummaryoctBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
    </div>

    <div class="div21">Nov<div>${Math.round(new BigDecimal(summarynovBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())} / ${Math.round(new BigDecimal(InvsummarynovBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
    </div>

    <div class="div22">Dec<div>${Math.round(new BigDecimal(summarydecBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())} / ${Math.round(new BigDecimal(InvsummarydecBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())}</div>
    </div>
</div>


<div class="monthItems">
<%

        if (v == 0) {
            for (int g = 0; g < vendorInstance.size(); g++) {

                double janBudget = 0.0
                double febBudget = 0.0
                double marBudget = 0.0
                double aprBudget = 0.0
                double mayBudget = 0.0
                double junBudget = 0.0
                double julBudget = 0.0
                double augBudget = 0.0
                double sepBudget = 0.0
                double octBudget = 0.0
                double novBudget = 0.0
                double decBudget = 0.0

                Integer countjanBudget = 0
                Integer countfebBudget = 0
                Integer countmarBudget = 0
                Integer countaprBudget = 0
                Integer countmayBudget = 0
                Integer countjunBudget = 0
                Integer countjulBudget = 0
                Integer countaugBudget = 0
                Integer countsepBudget = 0
                Integer countoctBudget = 0
                Integer countnovBudget = 0
                Integer countdecBudget = 0

                double totalAmount = 0.0

                def vendorId = vendorInstance[g][0]


                vendorAccountInstance.each {phnBudgetRootRegular ->

                    if (phnBudgetRootRegular.size() > 0) {
                        phnBudgetRootRegular.each {phnBudgetRegular ->
                            if (phnBudgetRegular[0] == vendorId) {
                                if (phnBudgetRegular[3] == 1) {
                                    janBudget = janBudget + phnBudgetRegular[4]
                                    countjanBudget = countjanBudget + 1
                                } else if (phnBudgetRegular[3] == 2) {
                                    febBudget = febBudget + phnBudgetRegular[4]
                                    countfebBudget = countfebBudget + 1
                                } else if (phnBudgetRegular[3] == 3) {
                                    marBudget = marBudget + phnBudgetRegular[4]
                                    countmarBudget = countmarBudget + 1
                                } else if (phnBudgetRegular[3] == 4) {
                                    aprBudget = aprBudget + phnBudgetRegular[4]
                                    countaprBudget = countaprBudget + 1
                                } else if (phnBudgetRegular[3] == 5) {
                                    mayBudget = mayBudget + phnBudgetRegular[4]
                                    countmayBudget = countmayBudget + 1
                                } else if (phnBudgetRegular[3] == 6) {
                                    junBudget = junBudget + phnBudgetRegular[4]
                                    countjunBudget = countjunBudget + 1
                                } else if (phnBudgetRegular[3] == 7) {
                                    julBudget = julBudget + phnBudgetRegular[4]
                                    countjulBudget = countjulBudget + 1
                                } else if (phnBudgetRegular[3] == 8) {
                                    augBudget = augBudget + phnBudgetRegular[4]
                                    countaugBudget = countaugBudget + 1
                                } else if (phnBudgetRegular[3] == 9) {
                                    sepBudget = sepBudget + phnBudgetRegular[4]
                                    countsepBudget = countsepBudget + 1
                                } else if (phnBudgetRegular[3] == 10) {
                                    octBudget = octBudget + phnBudgetRegular[4]
                                    countoctBudget = countoctBudget + 1
                                } else if (phnBudgetRegular[3] == 11) {
                                    novBudget = novBudget + phnBudgetRegular[4]
                                    countnovBudget = countnovBudget + 1
                                } else if (phnBudgetRegular[3] == 12) {
                                    decBudget = decBudget + phnBudgetRegular[4]
                                    countdecBudget = countdecBudget + 1
                                }
                                totalAmount = totalAmount + phnBudgetRegular[4]
                            }
                        }
                    }
                }

                double janBudgetInvoice = 0.0
                double febBudgetInvoice = 0.0
                double marBudgetInvoice = 0.0
                double aprBudgetInvoice = 0.0
                double mayBudgetInvoice = 0.0
                double junBudgetInvoice = 0.0
                double julBudgetInvoice = 0.0
                double augBudgetInvoice = 0.0
                double sepBudgetInvoice = 0.0
                double octBudgetInvoice = 0.0
                double novBudgetInvoice = 0.0
                double decBudgetInvoice = 0.0

                Integer countjanBudgetInvoice = 0
                Integer countfebBudgetInvoice = 0
                Integer countmarBudgetInvoice = 0
                Integer countaprBudgetInvoice = 0
                Integer countmayBudgetInvoice = 0
                Integer countjunBudgetInvoice = 0
                Integer countjulBudgetInvoice = 0
                Integer countaugBudgetInvoice = 0
                Integer countsepBudgetInvoice = 0
                Integer countoctBudgetInvoice = 0
                Integer countnovBudgetInvoice = 0
                Integer countdecBudgetInvoice = 0

                double totalAmountInvoiceInvoice = 0.0

                def tempmonthVendorId = vendorId


                vendorInvoiceAccountInstance.each {phnRootRegular ->

                    if (phnRootRegular.size() > 0) {

                        def tempInvoiceExpanseVendor = 0
                        def tempInvoiceExpanseGlCode = ""
                        phnRootRegular.each {phnRegular ->

                            if (phnRegular[0] == Integer.parseInt(tempmonthVendorId)) {
                                if (Integer.parseInt(phnRegular[3]) == 1) {
                                    janBudgetInvoice = janBudgetInvoice + phnRegular[4]
                                    countjanBudgetInvoice = countjanBudgetInvoice + 1
                                } else if (Integer.parseInt(phnRegular[3]) == 2) {
                                    febBudgetInvoice = febBudgetInvoice + phnRegular[4]
                                    countfebBudgetInvoice = countfebBudgetInvoice + 1
                                } else if (Integer.parseInt(phnRegular[3]) == 3) {
                                    marBudgetInvoice = marBudgetInvoice + phnRegular[4]
                                    countmarBudgetInvoice = countmarBudgetInvoice + 1
                                } else if (Integer.parseInt(phnRegular[3]) == 4) {
                                    aprBudgetInvoice = aprBudgetInvoice + phnRegular[4]
                                    countaprBudgetInvoice = countaprBudgetInvoice + 1
                                } else if (Integer.parseInt(phnRegular[3]) == 5) {
                                    mayBudgetInvoice = mayBudgetInvoice + phnRegular[4]
                                    countmayBudgetInvoice = countmayBudgetInvoice + 1
                                } else if (Integer.parseInt(phnRegular[3]) == 6) {
                                    junBudgetInvoice = junBudgetInvoice + phnRegular[4]
                                    countjunBudgetInvoice = countjunBudgetInvoice + 1
                                } else if (Integer.parseInt(phnRegular[3]) == 7) {
                                    julBudgetInvoice = julBudgetInvoice + phnRegular[4]
                                    countjulBudgetInvoice = countjulBudgetInvoice + 1
                                } else if (Integer.parseInt(phnRegular[3]) == 8) {
                                    augBudgetInvoice = augBudgetInvoice + phnRegular[4]
                                    countaugBudgetInvoice = countaugBudgetInvoice + 1
                                } else if (Integer.parseInt(phnRegular[3]) == 9) {
                                    sepBudgetInvoice = sepBudgetInvoice + phnRegular[4]
                                    countsepBudgetInvoice = countsepBudgetInvoice + 1
                                } else if (Integer.parseInt(phnRegular[3]) == 10) {
                                    octBudgetInvoice = octBudgetInvoice + phnRegular[4]
                                    countoctBudgetInvoice = countoctBudgetInvoice + 1
                                } else if (Integer.parseInt(phnRegular[3]) == 11) {
                                    novBudgetInvoice = novBudgetInvoice + phnRegular[4]
                                    countnovBudgetInvoice = countnovBudgetInvoice + 1
                                } else if (Integer.parseInt(phnRegular[3]) == 12) {
                                    decBudgetInvoice = decBudgetInvoice + phnRegular[4]
                                    countdecBudgetInvoice = countdecBudgetInvoice + 1
                                }
                                totalAmountInvoiceInvoice = totalAmountInvoiceInvoice + phnRegular[4]
                            }
                        }
                    }
                }

                def protocol = request.isSecure() ? "https://" : "http://"
                def host = request.getServerName()
                def port = request.getServerPort()
                def context = request.getServletContext().getContextPath()
                def liveURL = protocol + host + ":" + port + context

                double showBudget = 0.0
                Integer showCountBudget = 0
                double showBudgetInvoice = 0.0
                Integer showCountBudgetInvoice = 0

                for (int monthc = 0; monthc < 12; monthc++) {

                    if (monthc == 0) {
                        showBudget = janBudget
                        showBudgetInvoice = janBudgetInvoice
                        showCountBudget = countjanBudget
                        showCountBudgetInvoice = countjanBudgetInvoice
                    }
                    else if (monthc == 1) {
                        showBudget = febBudget
                        showBudgetInvoice = febBudgetInvoice
                        showCountBudget = countfebBudget
                        showCountBudgetInvoice = countfebBudgetInvoice
                    }
                    else if (monthc == 2) {
                        showBudget = marBudget
                        showBudgetInvoice = marBudgetInvoice
                        showCountBudget = countmarBudget
                        showCountBudgetInvoice = countmarBudgetInvoice
                    }
                    else if (monthc == 3) {
                        showBudget = aprBudget
                        showBudgetInvoice = aprBudgetInvoice
                        showCountBudget = countaprBudget
                        showCountBudgetInvoice = countaprBudgetInvoice
                    }
                    else if (monthc == 4) {
                        showBudget = mayBudget
                        showBudgetInvoice = mayBudgetInvoice
                        showCountBudget = countmayBudget
                        showCountBudgetInvoice = countmayBudgetInvoice
                    }
                    else if (monthc == 5) {
                        showBudget = junBudget
                        showBudgetInvoice = junBudgetInvoice
                        showCountBudget = countjunBudget
                        showCountBudgetInvoice = countjunBudgetInvoice
                    }
                    else if (monthc == 6) {
                        showBudget = julBudget
                        showBudgetInvoice = julBudgetInvoice
                        showCountBudget = countjulBudget
                        showCountBudgetInvoice = countjulBudgetInvoice
                    }
                    else if (monthc == 7) {
                        showBudget = augBudget
                        showBudgetInvoice = augBudgetInvoice
                        showCountBudget = countaugBudget
                        showCountBudgetInvoice = countaugBudgetInvoice
                    }
                    else if (monthc == 8) {
                        showBudget = sepBudget
                        showBudgetInvoice = sepBudgetInvoice
                        showCountBudget = countsepBudget
                        showCountBudgetInvoice = countsepBudgetInvoice
                    }
                    else if (monthc == 9) {
                        showBudget = octBudget
                        showBudgetInvoice = octBudgetInvoice
                        showCountBudget = countoctBudget
                        showCountBudgetInvoice = countoctBudgetInvoice
                    }
                    else if (monthc == 10) {
                        showBudget = novBudget
                        showBudgetInvoice = novBudgetInvoice
                        showCountBudget = countnovBudget
                        showCountBudgetInvoice = countnovBudgetInvoice
                    }
                    else if (monthc == 11) {
                        showBudget = decBudget
                        showBudgetInvoice = decBudgetInvoice
                        showCountBudget = countdecBudget
                        showCountBudgetInvoice = countdecBudgetInvoice
                    }


%>
<div class="div31 ${(loopmonth % 2) == 0 ? 'even' : 'odd'}"><a href="#"
                                                               title="Total Invoice: ${showCountBudget}&#xD; Entered Invoice: ${showCountBudgetInvoice}" target="_parent"
                                                               class="tooltip">${Math.round(new BigDecimal(showBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) > 0 ? '<a href="' + liveURL + '/dashboardExpanseList/budgetRegularSummery/' + tempmonthVendorId + '?m=' + (monthc + 1) + '"  title="Total Invoice: ' + showCountBudget + '&#xD; Entered Invoice: ' + showCountBudgetInvoice + '" target="_parent" class="tooltip">' + Math.round(new BigDecimal(showBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) + (Math.round(new BigDecimal(showBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) <= Math.round(new BigDecimal(showBudgetInvoice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) ? '<img src="' + resource(dir: 'images', file: 'red.gif') + '" />' : '<img src="' + resource(dir: 'images', file: 'green.gif') + '" />') + "</a>" : '<a href="' + liveURL + '/dashboardExpanseList/budgetRegularSummery/' + tempmonthVendorId + '?m=' + (monthc + 1) + '"  title="Total Invoice: ' + showCountBudget + '&#xD; Entered Invoice: ' + showCountBudgetInvoice + '" target="_parent" class="tooltip">-' + '<img src="' + resource(dir: 'images', file: 'gray.gif') + '" /></a>'} ${Math.round(new BigDecimal(showBudgetInvoice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) > 0 ? '<a href="' + liveURL + '/dashboardExpanseList/invoiceRegularSummery/' + tempmonthVendorId + '?m=' + (monthc + 1) + '"  title="Total Invoice: ' + showCountBudget + '&#xD; Entered Invoice: ' + showCountBudgetInvoice + '" target="_parent" class="tooltip">' + Math.round(new BigDecimal(showBudgetInvoice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) + (Math.round(new BigDecimal(showBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) <= Math.round(new BigDecimal(showBudgetInvoice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) ? '<img src="' + resource(dir: 'images', file: 'green.gif') + '" />' : '<img src="' + resource(dir: 'images', file: 'red.gif') + '" />') + "</a>" : '<a href="' + liveURL + '/dashboardExpanseList/invoiceRegularSummery/' + tempmonthVendorId + '?m=' + (monthc + 1) + '"  target="_parent" title="Total Invoice: ' + showCountBudget + '&#xD; Entered Invoice: ' + showCountBudgetInvoice + '" class="tooltip">-' + '<img src="' + resource(dir: 'images', file: 'gray.gif') + '" /></a>'}</a>
</div>
<%
            }
            loopmonth++
        }

%>
</div>
</div>

<div class="budgetTotal">
    <div class="colTitle">
        ${message(code: 'dashboard.incomeBudgetTotal.label', default: 'Total')}
    </div>

    <div class="budgetItemCol">

        <%
                Integer loopTotalMonth = 1
                def totalTempGlAccount = ""
                def totalTempmonthVendorId = "";

                for (int k = 0; k < vendorInstance.size(); k++) {


                    double totalTempAmount = 0.0
                    totalTempGlAccount = vendorInstance[k][1];
                    totalTempmonthVendorId = vendorInstance[k][0];


                    vendorAccountInstance.each {phnBudgetRootRegular ->

                        if (phnBudgetRootRegular.size() > 0) {
                            phnBudgetRootRegular.each {phnBudgetRegular ->
                                if (phnBudgetRegular[0] == totalTempmonthVendorId) {
                                    totalTempAmount = totalTempAmount + phnBudgetRegular[4]
                                }
                            }
                        }
                    }


                    def totaltempInvoiceExpanseGlCode = ""
                    def totaltempInvoiceExpanseVendor = ""
                    double totalTempAmountInvoiceInvoice = 0.0

                    vendorInvoiceAccountInstance.each {phnRootRegular ->
                        if (phnRootRegular.size() > 0) {
                            phnRootRegular.each {phnRegular ->
                                if (phnRegular[0] == Integer.parseInt(totalTempmonthVendorId)) {
                                    totalTempAmountInvoiceInvoice = totalTempAmountInvoiceInvoice + phnRegular[4]
                                }
                            }
                        }
                    }
        %>
        <div class="accountHeadItem ${(loopTotalMonth % 2) == 0 ? 'odd' : 'even'}"
             style="text-align: center;">${Math.round(new BigDecimal(totalTempAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) > 0 ? Math.round(new BigDecimal(totalTempAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) : "-"} / ${Math.round(new BigDecimal(totalTempAmountInvoiceInvoice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) > 0 ? Math.round(new BigDecimal(totalTempAmountInvoiceInvoice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) : "-"}</div>
        <%
                    loopTotalMonth++
                }
        %>

        <div class="accountHeadItem"
             style="text-align: center; height:16px;">&nbsp;</div>
    </div>
</div>
</div>
</div>
</div>
<%
    } else if (v == 1) {

        Integer accloopmonth = 1
        for (int g = 0; g < accountInstance.size(); g++) {

            double accjanBudget = 0.0
            double accfebBudget = 0.0
            double accmarBudget = 0.0
            double accaprBudget = 0.0
            double accmayBudget = 0.0
            double accjunBudget = 0.0
            double accjulBudget = 0.0
            double accaugBudget = 0.0
            double accsepBudget = 0.0
            double accoctBudget = 0.0
            double accnovBudget = 0.0
            double accdecBudget = 0.0

            Integer acccountjanBudget = 0
            Integer acccountfebBudget = 0
            Integer acccountmarBudget = 0
            Integer acccountaprBudget = 0
            Integer acccountmayBudget = 0
            Integer acccountjunBudget = 0
            Integer acccountjulBudget = 0
            Integer acccountaugBudget = 0
            Integer acccountsepBudget = 0
            Integer acccountoctBudget = 0
            Integer acccountnovBudget = 0
            Integer acccountdecBudget = 0

            double acctotalAmount = 0.0

            def accvendorId = accountInstance[g][0]


            accountAccountInstance.each {phnBudgetRoot ->

                if (phnBudgetRoot.size() > 0) {
                    phnBudgetRoot.each {phnBudget ->

                        if (phnBudget[0] == accvendorId) {
                            if (phnBudget[3] == 1) {
                                accjanBudget += phnBudget[4]
                                acccountjanBudget += 1
                            } else if (phnBudget[3] == 2) {
                                accfebBudget += phnBudget[4]
                                acccountfebBudget += 1
                            } else if (phnBudget[3] == 3) {
                                accmarBudget += phnBudget[4]
                                acccountmarBudget += 1
                            } else if (phnBudget[3] == 4) {
                                accaprBudget += phnBudget[4]
                                acccountaprBudget += 1
                            } else if (phnBudget[3] == 5) {
                                accmayBudget += phnBudget[4]
                                acccountmayBudget += 1
                            } else if (phnBudget[3] == 6) {
                                accjunBudget += phnBudget[4]
                                acccountjunBudget += 1
                            } else if (phnBudget[3] == 7) {
                                accjulBudget += phnBudget[4]
                                acccountjulBudget += 1
                            } else if (phnBudget[3] == 8) {
                                accaugBudget += phnBudget[4]
                                acccountaugBudget += 1
                            } else if (phnBudget[3] == 9) {
                                accsepBudget += phnBudget[4]
                                acccountsepBudget += 1
                            } else if (phnBudget[3] == 10) {
                                accoctBudget += phnBudget[4]
                                acccountoctBudget += 1
                            } else if (phnBudget[3] == 11) {
                                accnovBudget += phnBudget[4]
                                acccountnovBudget += 1
                            } else if (phnBudget[3] == 12) {
                                accdecBudget += phnBudget[4]
                                acccountdecBudget += 1
                            }
                            acctotalAmount += phnBudget[4]
                        }
                    }
                }

            }

            double accjanBudgetInvoice = 0.0
            double accfebBudgetInvoice = 0.0
            double accmarBudgetInvoice = 0.0
            double accaprBudgetInvoice = 0.0
            double accmayBudgetInvoice = 0.0
            double accjunBudgetInvoice = 0.0
            double accjulBudgetInvoice = 0.0
            double accaugBudgetInvoice = 0.0
            double accsepBudgetInvoice = 0.0
            double accoctBudgetInvoice = 0.0
            double accnovBudgetInvoice = 0.0
            double accdecBudgetInvoice = 0.0

            Integer acccountjanBudgetInvoice = 0
            Integer acccountfebBudgetInvoice = 0
            Integer acccountmarBudgetInvoice = 0
            Integer acccountaprBudgetInvoice = 0
            Integer acccountmayBudgetInvoice = 0
            Integer acccountjunBudgetInvoice = 0
            Integer acccountjulBudgetInvoice = 0
            Integer acccountaugBudgetInvoice = 0
            Integer acccountsepBudgetInvoice = 0
            Integer acccountoctBudgetInvoice = 0
            Integer acccountnovBudgetInvoice = 0
            Integer acccountdecBudgetInvoice = 0


            double acctotalAmountInvoiceInvoice = 0.0

            def acctempmonthVendorId = accvendorId

            accountInvoiceAccountInstance.each {phnRoot ->
                if (phnRoot.size() > 0) {
                    phnRoot.each {phn ->
                        acctempInvoiceExpanseVendor = phn[0];
                        if (acctempInvoiceExpanseVendor == accvendorId) {
                            if (Integer.parseInt(phn[3]) == 1) {
                                accjanBudgetInvoice += phn[4]
                                acccountjanBudgetInvoice += 1
                            } else if (Integer.parseInt(phn[3]) == 2) {
                                accfebBudgetInvoice += phn[4]
                                acccountfebBudgetInvoice += 1
                            } else if (Integer.parseInt(phn[3]) == 3) {
                                accmarBudgetInvoice += phn[4]
                                acccountmarBudgetInvoice += 1
                            } else if (Integer.parseInt(phn[3]) == 4) {
                                accaprBudgetInvoice += phn[4]
                                acccountaprBudgetInvoice += 1
                            } else if (Integer.parseInt(phn[3]) == 5) {
                                accmayBudgetInvoice += phn[4]
                                acccountmayBudgetInvoice += 1
                            } else if (Integer.parseInt(phn[3]) == 6) {
                                accjunBudgetInvoice += phn[4]
                                acccountjunBudgetInvoice += 1
                            } else if (Integer.parseInt(phn[3]) == 7) {
                                accjulBudgetInvoice += phn[4]
                                acccountjulBudgetInvoice += 1
                            } else if (Integer.parseInt(phn[3]) == 8) {
                                accaugBudgetInvoice += phn[4]
                                acccountaugBudgetInvoice += 1
                            } else if (Integer.parseInt(phn[3]) == 9) {
                                accsepBudgetInvoice += phn[4]
                                acccountsepBudgetInvoice += 1
                            } else if (Integer.parseInt(phn[3]) == 10) {
                                accoctBudgetInvoice += phn[4]
                                acccountoctBudgetInvoice += 1
                            } else if (Integer.parseInt(phn[3]) == 11) {
                                accnovBudgetInvoice += phn[4]
                                acccountnovBudgetInvoice += 1
                            } else if (Integer.parseInt(phn[3]) == 12) {
                                accdecBudgetInvoice += phn[4]
                                acccountdecBudgetInvoice += 1
                            }
                            acctotalAmountInvoiceInvoice += phn[4]
                        }
                    }

                }

            }

            def accprotocol = request.isSecure() ? "https://" : "http://"
            def acchost = request.getServerName()
            def accport = request.getServerPort()
            def acccontext = request.getServletContext().getContextPath()
            def accliveURL = accprotocol + acchost + ":" + accport + acccontext

            double accshowBudget = 0.0
            Integer accshowCountBudget = 0
            double accshowBudgetInvoice = 0.0
            Integer accshowCountBudgetInvoice = 0

            for (int accmonthc = 0; accmonthc < 12; accmonthc++) {

                if (accmonthc == 0) {
                    accshowBudget = accjanBudget
                    accshowBudgetInvoice = accjanBudgetInvoice
                    accshowCountBudget = acccountjanBudget
                    accshowCountBudgetInvoice = acccountjanBudgetInvoice
                }
                else if (accmonthc == 1) {
                    accshowBudget = accfebBudget
                    accshowBudgetInvoice = accfebBudgetInvoice
                    accshowCountBudget = acccountfebBudget
                    accshowCountBudgetInvoice = acccountfebBudgetInvoice
                }
                else if (accmonthc == 2) {
                    accshowBudget = accmarBudget
                    accshowBudgetInvoice = accmarBudgetInvoice
                    accshowCountBudget = acccountmarBudget
                    accshowCountBudgetInvoice = acccountmarBudgetInvoice
                }
                else if (accmonthc == 3) {
                    accshowBudget = accaprBudget
                    accshowBudgetInvoice = accaprBudgetInvoice
                    accshowCountBudget = acccountaprBudget
                    accshowCountBudgetInvoice = acccountaprBudgetInvoice
                }
                else if (accmonthc == 4) {
                    accshowBudget = accmayBudget
                    accshowBudgetInvoice = accmayBudgetInvoice
                    accshowCountBudget = acccountmayBudget
                    accshowCountBudgetInvoice = acccountmayBudgetInvoice
                }
                else if (accmonthc == 5) {
                    accshowBudget = accjunBudget
                    accshowBudgetInvoice = accjunBudgetInvoice
                    accshowCountBudget = acccountjunBudget
                    accshowCountBudgetInvoice = acccountjunBudgetInvoice
                }
                else if (accmonthc == 6) {
                    accshowBudget = accjulBudget
                    accshowBudgetInvoice = accjulBudgetInvoice
                    accshowCountBudget = acccountjulBudget
                    accshowCountBudgetInvoice = acccountjulBudgetInvoice
                }
                else if (accmonthc == 7) {
                    accshowBudget = accaugBudget
                    accshowBudgetInvoice = accaugBudgetInvoice
                    accshowCountBudget = acccountaugBudget
                    accshowCountBudgetInvoice = acccountaugBudgetInvoice
                }
                else if (accmonthc == 8) {
                    accshowBudget = accsepBudget
                    accshowBudgetInvoice = accsepBudgetInvoice
                    accshowCountBudget = acccountsepBudget
                    accshowCountBudgetInvoice = acccountsepBudgetInvoice
                }
                else if (accmonthc == 9) {
                    accshowBudget = accoctBudget
                    accshowBudgetInvoice = accoctBudgetInvoice
                    accshowCountBudget = acccountoctBudget
                    accshowCountBudgetInvoice = acccountoctBudgetInvoice
                }
                else if (accmonthc == 10) {
                    accshowBudget = accnovBudget
                    accshowBudgetInvoice = accnovBudgetInvoice
                    accshowCountBudget = acccountnovBudget
                    accshowCountBudgetInvoice = acccountnovBudgetInvoice
                }
                else if (accmonthc == 11) {
                    accshowBudget = accdecBudget
                    accshowBudgetInvoice = accdecBudgetInvoice
                    accshowCountBudget = acccountdecBudget
                    accshowCountBudgetInvoice = acccountdecBudgetInvoice
                }



%>
<div class="div31 ${(accloopmonth % 2) == 0 ? 'odd' : 'even'}"><a href="#"
                                                                  title="Total Invoice: ${accshowCountBudget}&#xD; Entered Invoice: ${accshowCountBudgetInvoice}" target="_parent"
                                                                  class="tooltip">${Math.round(new BigDecimal(accshowBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) > 0 ? '<a href="' + accliveURL + '/dashboardExpanseList/budgetShopSummery/' + acctempmonthVendorId + '?m=' + (accmonthc + 1) + '" target="_parent" title="Total Invoice: ' + accshowCountBudget + '&#xD; Entered Invoice: ' + accshowCountBudgetInvoice + '" class="tooltip">' + Math.round(new BigDecimal(accshowBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) + (Math.round(new BigDecimal(accshowBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) <= Math.round(new BigDecimal(accshowBudgetInvoice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) ? '<img src="' + resource(dir: 'images', file: 'red.gif') + '" />' : '<img src="' + resource(dir: 'images', file: 'green.gif') + '" />') + "</a>" : "-" + '<img src="' + resource(dir: 'images', file: 'gray.gif') + '" />'} ${Math.round(new BigDecimal(accshowBudgetInvoice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) > 0 ? '<a href="' + accliveURL + '/dashboardExpanseList/invoiceShopSummery/' + acctempmonthVendorId + '?m=' + (accmonthc + 1) + '" target="_parent" title="Total Invoice: ' + accshowCountBudget + '&#xD; Entered Invoice: ' + accshowCountBudgetInvoice + '" class="tooltip">' + Math.round(new BigDecimal(accshowBudgetInvoice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) + (Math.round(new BigDecimal(accshowBudget).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) <= Math.round(new BigDecimal(accshowBudgetInvoice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) ? '<img src="' + resource(dir: 'images', file: 'green.gif') + '" />' : '<img src="' + resource(dir: 'images', file: 'red.gif') + '" />') + "</a>" : "-" + '<img src="' + resource(dir: 'images', file: 'gray.gif') + '" />'}</a>
</div>
<%
            }
            accloopmonth++
        }

%>
</div>
</div>

<div class="budgetTotal">
    <div class="colTitle">
        ${message(code: 'dashboard.incomeBudgetTotal.label', default: 'Total')}
    </div>

    <div class="budgetItemCol">

        <%
                Integer accloopTotalMonth = 1
                def acctotalTempGlAccount = ""
                def acctotalTempmonthVendorId = "";

                for (int k = 0; k < accountInstance.size(); k++) {


                    double acctotalTempAmount = 0.0
                    acctotalTempGlAccount = accountInstance[k][1];
                    acctotalTempmonthVendorId = accountInstance[k][0];

                    accountAccountInstance.each {phnBudgetRoot ->
                        if (phnBudgetRoot.size() > 0) {
                            phnBudgetRoot.each {phnBudget ->
                                if (phnBudget[0] == acctotalTempmonthVendorId) {
                                    acctotalTempAmount = acctotalTempAmount + phnBudget[4]
                                }
                            }
                        }
                    }

                    def acctotaltempInvoiceExpanseGlCode = ""
                    def acctotaltempInvoiceExpanseVendor = ""
                    double acctotalTempAmountInvoiceInvoice = 0.0

                    accountInvoiceAccountInstance.each {phnRoot ->
                        if (phnRoot.size() > 0) {
                            phnRoot.each {phn ->
                                if (phn[0] == acctotalTempmonthVendorId) {
                                    acctotalTempAmountInvoiceInvoice = acctotalTempAmountInvoiceInvoice + phn[4]
                                }
                            }
                        }
                    }

        %>
        <div class="accountHeadItem ${(accloopTotalMonth % 2) == 0 ? 'odd' : 'even'}"
             style="text-align: center;">${Math.round(new BigDecimal(acctotalTempAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) > 0 ? Math.round(new BigDecimal(acctotalTempAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) : "-"} / ${Math.round(new BigDecimal(acctotalTempAmountInvoiceInvoice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) > 0 ? Math.round(new BigDecimal(acctotalTempAmountInvoiceInvoice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) : "-"}</div>
        <%
                    accloopTotalMonth++
                }
        %>

        <div class="accountHeadItem"
             style="text-align: center; height:16px;">&nbsp;</div>
    </div>
</div>
</div>
</div>
</div>
<%

            }
        }
    }
%>
</div>
</div>

