<%@ page import="bv.BusinessCompany; java.text.SimpleDateFormat; bv.UserTagLib; bv.CoreParamsHelper; bv.DashboardDetailsTagLib; bv.Dashboard; org.springframework.web.servlet.support.RequestContextUtils" %>
<!doctype html>
<html>
<%
    def protocol = request.isSecure() ? "https://" : "http://"
    def host = request.getServerName()
    def port = request.getServerPort()
    def context = request.getServletContext().getContextPath()

    def dashboardURL = ""
    dashboardURL = protocol + host + ":" + port + context + "/dashboardDetails/incomeAndExpenseNameWise"

    def activeFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
    def fiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(activeFiscalYear)

    //def dashboardDetailsTagLib = new DashboardDetailsTagLib();

    //int nNetProfitBasedOnBudget = dashboardDetailsTagLib.getRoundedValue(totalNetProfitAmount)
    //int nGrossProfitBasedOnInvoice = dashboardDetailsTagLib.getRoundedValue(totalGrossProfitAmount)
%>

<head>

    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'dashboard.label', default: 'Dashboard')}"/>
    <title><g:message code="default.name.label" args="[entityName]"/></title>

    <g:javascript>
        var sesStorage = window.sessionStorage;

        function onChangeBudgetDetails(budgetType) {
            showButton();
            var url = "";
            var sortType = document.getElementById("budgetSortType").value;

//            alert("Budget Details: "+ budgetType);
            if(budgetType == "incNexp"){
            document.getElementById("budgetSortType").disabled = false;
                if(sortType == "name_wise"){
                    url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/incomeAndExpenseNameWise";
                }else if(sortType == "acc_head_wise"){
                    url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/incomeAndExpenseAccountHeadWise";
                }

                document.getElementById("ifrmBudgetDetails").src = url
            }
            else if(budgetType == "income"){
            document.getElementById("budgetSortType").disabled = false;
                if(sortType == "name_wise"){
                    url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/incomeCustomerWise";
                }else if(sortType == "acc_head_wise"){
                    url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/incomeAccountWise";
                }
                document.getElementById("ifrmBudgetDetails").src = url
            }
            else if(budgetType == "expense"){
                document.getElementById("budgetSortType").disabled = false;
                if(sortType == "name_wise"){
                    url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/expanseVendorWise";
                }else if(sortType == "acc_head_wise"){
                    url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/expanseAccountWise";
                }

                document.getElementById("ifrmBudgetDetails").src = url
            }
            else if(budgetType == "privateSpend"){
                document.getElementById("budgetSortType").selectedIndex = 'name_wise';
                document.getElementById("budgetSortType").disabled = true;

                url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/privateSpendingsNameWise";
                document.getElementById("ifrmBudgetDetails").src = url
            }
            else if(budgetType == "reserve"){
                document.getElementById("budgetSortType").disabled = false;
                if(sortType == "name_wise"){
                    url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/reservationNameWise";
                }else if(sortType == "acc_head_wise"){
                    url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/reservationAccountWise";
                }
                document.getElementById("ifrmBudgetDetails").src = url
            }

            //alert("budgetType: "+ budgetType);

            sesStorage.setItem("dashboardURL",url);
            sesStorage.setItem("budgetType",budgetType);
            sesStorage.setItem('selectedValue','-1')

            $.ajax({type:'POST',
                data:'budgetType=' + budgetType,
                url:'${context}/dashboard/updateBudgetInvoiceSummary',
                success:function(data,textStatus){
                    $(profitSummary).html(data);
                },
                error: function() {

                }
            });
        }

        function onChangeBudgetDetailsSortType(sortType){
            var url = "";
            var budgetType = document.getElementById("budgetType").value;
            //alert("Budget Details: "+ budgetType);
            if(sortType == "name_wise"){
                if(budgetType == "incNexp"){
                    url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/incomeAndExpenseNameWise";
                }else if(budgetType == "income"){
                    url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/incomeCustomerWise";
                }else if(budgetType == "expense"){
                    url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/expanseVendorWise";
                }else if(budgetType == "privateSpend"){
                    url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/privateSpendingsNameWise";
                }else if(budgetType == "reserve"){
                    url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/reservationNameWise";
                }
                //alert("url : "+ url);
                document.getElementById("ifrmBudgetDetails").src = url
            }
            else if(sortType == "acc_head_wise"){
                if(budgetType == "incNexp"){
                    url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/incomeAndExpenseAccountHeadWise";
                }else if(budgetType == "income"){
                    url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/incomeAccountWise";
                }else if(budgetType == "expense"){
                    url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/expanseAccountWise";
                }else if(budgetType == "reserve"){
                    url = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/reservationAccountWise";
                }
                document.getElementById("ifrmBudgetDetails").src = url
            }

            sesStorage.setItem("dashboardURL",url);
            sesStorage.setItem("budgetSortType",sortType);
            sesStorage.setItem('selectedValue','-1')
        }

        $(document).ready(function(){
            showButton();
            var budgetType = sesStorage.getItem("budgetType");
            $("#budgetType").val(budgetType);

            var sortType = sesStorage.getItem("budgetSortType");
            $("#budgetSortType").val(sortType);

            var dashboarURL = sesStorage.getItem("dashboardURL");

            if(dashboarURL == "" || dashboarURL == null){
                dashboarURL = '${protocol}' + '${host}' + ":" + '${port}' + '${context}' + "/dashboardDetails/incomeAndExpenseNameWise";
                sesStorage.setItem("dashboardURL",dashboarURL);
            }

            document.getElementById("ifrmBudgetDetails").src = dashboarURL;

            onChangeBudgetDetails(budgetType);
        });

        function showButton(){
            var budgetType = document.getElementById("budgetType").value;

            if(budgetType == 'incNexp' || budgetType == 'income' || budgetType == 'expense' ){
//            alert(budgetType);
                 $("#privateButton").hide();
                 $("#reservationButton").hide();

            }
            else if(budgetType == 'reserve'){
//            alert(budgetType);
                 $("#privateButton").hide();
                 $("#reservationButton").show();
            }
            else if(budgetType == 'privateSpend' ){
//            alert(budgetType);
                 $("#reservationButton").hide();
                 $("#privateButton").show();
            }
        }

    </g:javascript>
</head>

<body style="zoom:100%;-moz-transform: scale(2);">
<div id="list-page-body-inner" class="content" style="background-color: #fff; border: none;">

    <div class="budgetHeader">
        <div class="headerMainLeft">
            <label class=""><g:message code="bv.dashboard.manageBudget&Bookings.label"
                                       default="Manage Budget & Bookings"/></label>
        </div>

        <% if (fiscalYearInfo) { %>
        <div class="headerMainMiddle" style="width: 22.7%;">
            <a href="${context}/fiscalYear/list">${fiscalYearInfo[0][4]}</a>
        </div>
        <% } %>

        <div id="profitSummary" class="headerMainRight">

        </div>
    </div>

    <div class="explanationDiv">
        <h4 class="slideToggler2 slideSign2">
            <label class="explanationLabel">
                <span class="explanationArrow"></span>
                <p><g:message code="bv.dashboard.explanation.label" default="Explanation"/></p>
            </label>
        </h4>

        <div class="slideContent2 explanationSlideContainer">
            <ul class="explanationList">
                <li><a href="${context}/explanation/index"><g:message code="explanation.budgetViewOvrview.label"/></a>
                </li>
                <li><a href="${context}/explanation/getStart"><g:message code="explanation.gettingStart.label"/></a>
                </li>
               %{-- <li><a href="${context}/explanation/estimateIncomeAndExpense"><g:message
                        code="explanation.estimateIncomeAndExpense.label"/></a></li>--}%
                <li><a href="${context}/explanation/createBudget"><g:message
                        code="explanation.createIncomeBudget.label"/></a></li>
                <li><a href="${context}/explanation/modifyAndDeleteTemperedEstimate"><g:message
                        code="explanation.createExpenseBudget.label"/></a></li>
                <li><a href="${context}/explanation/invoicesAndReceipt"><g:message
                        code="explanation.modifyAndDelete.label"/></a></li>
                <li><a href="${context}/explanation/privateBudget"><g:message
                        code="explanation.Private.budget.label"/></a></li>
                <li><a href="${context}/explanation/resultsAndReports"><g:message
                        code="explanation.result.report.label"/></a></li>
            </ul>
        </div>
    </div>

    <div class="navigation" style="height: 153px">
        <div class="navigationWizard">
            <div id="incNexpButton">

                <div class="row">
                    <div class="column">
                        <div class="navigationbtn" style="margin-left: 40px;">
                            <a  style="width: 225px;" href="${context}/quickEntryInvoice/quickEntryForm"><g:message
                                    code="bv.menu.quickEntry" default="Quick Entry"/></a>
                        </div>

                        <div class="navigationbtn" style="margin-left: 40px;">
                            <a  style="width: 225px;" href="${context}/budgetItemIncomeDetails/budgetForInvoIncome"><g:message
                                code="report.aging.incomeInvoice.label" default="Income invoice"/></a>
                        </div>

                        <div class="navigationbtn" style="margin-left: 40px;">
                            <a style="width: 225px;" href="${context}/budgetItemExpenseDetails/budgetForInvoExpense"><g:message
                                    code="report.aging.expenseInvoice.label" default="Expense invoice"/></a>
                        </div>
                    </div>

                    <div class="column">
                        <div class="navigationbtn" style="margin-left: 32px;">
                            <a  style="width: 225px;" href="${context}/reports/sendReminder"><g:message
                                    code="bv.menu.sendReminder" default="Send Reminder"/></a>
                        </div>

                        <div class="navigationbtn" style="margin-left: 32px;">
                            <a  style="width: 225px;" href="${context}/BankReconciliation/reconciliation"><g:message
                                    code="bv.dashboard.bankUpload.label" default="Bank upload"/></a>
                        </div>

                        <div class="navigationbtn" style="margin-left: 32px;">
                            <a style="width: 225px;" href="${context}/reportExpenseBudget/listBatchPayment"><g:message
                                code="bv.dashboard.createPayment.label" default="Create payment file"/></a>
                        </div>
                    </div>

                    <div class="column">
                        <div class="navigationbtn" style="margin-left: 12px;">
                            <a style="width: 225px;" href="${context}/dashboardDetails/showBudgetAndBookingSummary"><g:message
                                    code="bv.dashboard.seeMyResult.label" default="See My Results"/></a>
                        </div>

                        <div class="navigationbtn" style="margin-left: 12px;">
                            <a style="width: 225px;" href="${context}/budgetItemIncomeDetails/list?sort=id&order=desc"><g:message
                                    code="bv.dashboard.IncomeBudgetWizard.label" default="Income budget wizard"/></a>
                        </div>

                        <div class="navigationbtn" style="margin-left: 12px;">
                            <a style="width: 225px;" href="${context}/budgetItemExpenseDetails/list?sort=id&order=desc"><g:message
                                    code="bv.dashboard.expenseBudgetWizard.label" default="Expense budget wizard"/></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="selectionBudgetDiv">
        <div class="showBudgetLabel">
            <p><g:message code="bv.dashboard.showBudgetFor.label" default="Show budgets for"/><b>:</b></p>
        </div>

        <div class="comboOptionSelection">

            <div class="comboDiv">
                <%="${new CoreParamsHelper().getDropDownBudgetDetailsType('budgetType', '0')}"%>
            </div>

            <div class="comboDiv">
                <%="${new CoreParamsHelper().getDropDownBudgetDetailsSortType('budgetSortType', '0')}"%>
            </div>

        </div>
    </div>

    <hr>

    <div class="dashAmountHeader">
        <label class="dashAmountLabel" style="margin-left: 100px;color: #000000"><g:message
                code="bv.dashboard.totalInvoiceAmount.label" default="Total Invoice Amount"/></label>
        <label class="dashAmountLabel" style="color: #000000"><g:message code="bv.dashboard.totalBudgetAmount.label"
                                                                         default="Total Budget Amount"/></label>
    </div>
    <hr>

    <div id="detalis_view" class="graph" style="border-radius: 3px;height:2000px;">
        <iframe id="ifrmBudgetDetails" src="${dashboardURL}" width="100%" frameborder="0" scrolling="yes"
                style="height:2000px;">
        </iframe>
    </div>
</div>

</body>
</html>
