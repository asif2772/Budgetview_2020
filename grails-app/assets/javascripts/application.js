if (typeof jQuery !== 'undefined') {
    (function ($) {
        $('#spinner').ajaxStart(function () {
            $(this).fadeIn();
        }).ajaxStop(function () {
            $(this).fadeOut();
        });
    })(jQuery);
}


$.fn.refreshDropDown = function (data, options) {
    try {
        var ctrlDropDown = this;
        ctrlDropDown.children().remove();
        options = $.extend({
            valueMember: 'id',
            textMember: 'name',
            addAllAttributes: false,
            unselectedValue: '-1',
            selectHints: 'Please Select...',
            defaultValue: -1
        }, options);

        /* if (options.unselectedValue) {
         $($('<option></option>')).val(options.unselectedValue).html(options.selectHints).appendTo(ctrlDropDown);
         }*/

        $(data).each(function (e) {
            var opt = $($('<option></option>'));
            $.each(this, function (k, v) {
                if (k == options.textMember) opt.html(v);
                else if (k == options.valueMember) opt.val(v);
                else if (options.addAllAttributes) opt.attr(k, v);
            });
            opt.appendTo(ctrlDropDown);
        });
        // initial selection with default
        if (options.defaultValue) {
            ctrlDropDown.attr('value', options.defaultValue);
        }
    } catch (e) {

    }

};

$.fn.buildPaidWithDropDown = function (data, options) {
    var ctrlDropDown = this;
    $(data).each(function (e) {
        var opt = $('<option></option>').html(this.category_name + ' (' + this.vat_rate_str + '%)').val(this.id).attr('rate', this.rate);
        opt.appendTo(ctrlDropDown);
    });
    if (options.defaultValue) {
        ctrlDropDown.attr('value', options.defaultValue);
    }
}

$.fn.buildVatAmountDropDown = function (data) {
    var ctrlDropDown = this;
    $(data).each(function (e) {
        var opt = $('<option></option>').html(this.name).val(this.id);
        opt.appendTo(ctrlDropDown);
    });
}
/*
 * This code used for delete Expense budget in earlier
 * */
var showDeleteDialog = function (id, vendorId) {
    $("#dialog-modal").dialog(
        {   modal: true,
            autoOpen: true,
            resizable: false,
            width: 1000,
            height: 400,
            open: function (event, ui) {
                $(this).load(appContext + '/budgetItemExpense/expenseBudgetOfVendor?listForPopup=true&isForDelete=true&vendorId=' + vendorId + "&budgetItemId=" + id);
            },
            buttons: {
                Cancel: function (event) {
                    $(this).dialog('close');
                },
                Delete: function (event) {

                    if (confirm("Do you want to delete?")) {
                        $.ajax({
                            url: appContext + '/budgetItemExpense/deleteBudgetItems',
                            type: 'POST',
                            data: $("form[name='deleteBudgetItemForm']").serializeArray(),
                            success: function (resp) {
                                if (resp.result == 'success') {
                                    $("#dialog-modal").dialog('close');
//                                    location.href = location.href;
                                    location.href=appContext+ "/budgetItemExpenseDetails/list?sort=id&order=desc"
                                }
                            },
                            error: function (resp) {
                                alert(JSON.stringify(resp));
                            }
                        });
                    }
                }
            }
        });

}


function deleteExpenseBudgetItem(budgetItemId){


        $.ajax({
            url: appContext + '/budgetItemExpense/deleteBudgetItems',
            type: 'POST',
            data: {
                budgetItemId:budgetItemId
            },
            success: function (resp) {
                if (resp.result == 'success') {
                    location.href=appContext+ "/dashboard/index"
//                    location.href=appContext+ "/budgetItemExpenseDetails/list?sort=id&order=desc"
                }
            },
            error: function (resp) {
                alert(JSON.stringify(resp));
            }
        });

}

/*
* This code used for delete income budget in earlier
* */

var showIncomeDeleteDialog = function (id, customerId) {
    $("#dialog-modal").dialog(
        {   modal: true,
            autoOpen: true,
            resizable: false,
            width: 1000,
            height: 400,
            open: function (event, ui) {
                $(this).load(appContext + '/budgetItemIncome/incomeBudgetOfCustomer?listForPopup=true&isForDelete=true&customerId=' + customerId + "&budgetItemId=" + id);
            },
            buttons: {
                Cancel: function (event) {
                    $(this).dialog('close');
                },
                Delete: function (event) {
//                    alert(location.href);
                    if (confirm("Do you want to delete?")) {

                        $.ajax({
                            url: appContext + '/budgetItemIncome/deleteBudgetItems',
                            type: 'POST',
                            data: $("form[name='deleteBudgetItemForm']").serializeArray(),
                            success: function (resp) {
                                if (resp.result == 'success') {
                                    $("#dialog-modal").dialog('close');
                                    location.href=appContext +"/budgetItemIncomeDetails/list?sort=id&order=desc"

                                }
                            },
                            error: function (resp) {
                                alert(JSON.stringify(resp));
                            }
                        });
                    }
                }
            }
        });

}


/*
* delete income Budget Item Individual
* */
function deleteIncomeBudgetItems( budgetItemId){

     $.ajax({
         url: appContext + '/budgetItemIncome/deleteBudgetItems',
         type: 'POST',
        data: {
             budgetItemId: budgetItemId
         },
         success: function (resp) {
             if (resp.result == 'success') {
                location.href=appContext +"/dashboard/index";
//                location.href=appContext +"/budgetItemIncomeDetails/list?sort=id&order=desc";
             }
         },
         error: function (resp) {
            alert(JSON.stringify(resp));
         }
     });

}



var showReservationDeleteDialog = function (id, reservationId) {
    $("#dialog-modal").dialog(
        {   modal: true,
            autoOpen: true,
            resizable: false,
            width: 1000,
            height: 400,
            open: function (event, ui) {
                $(this).load(appContext +'/budgetItemReservation/budgetListOfReservation?listForPopup=true&isForDelete=true&reservationId=' + reservationId + "&budgetItemId=" + id);
            },
            buttons: {
                Cancel: function (event) {
                    $(this).dialog('close');
                },
                Delete: function (event) {
//                    alert(location.href);
                    if (confirm("Do you want to delete?")) {

                        $.ajax({
                            url: appContext + '/budgetItemReservation/deleteBudgetItems',
                            type: 'POST',
                            data: $("form[name='deleteBudgetItemForm']").serializeArray(),
                            success: function (resp) {
                                if (resp.result == 'success') {
                                    $("#dialog-modal").dialog('close');
//                                    location.href = location.href;
                                    location.href=appContext +"/budgetItemReservation/list?sort=id&order=desc"

                                }
                            },
                            error: function (resp) {
                                alert(JSON.stringify(resp));
                            }
                        });
                    }
                }
            }
        });

}

function deleteReservationBudgetItem(budgetItemId){


        $.ajax({
            url: appContext + '/budgetItemReservation/deleteBudgetItems',
            type: 'POST',
            data: {
                budgetItemId: budgetItemId
            },
            success: function (resp) {
                if (resp.result == 'success') {
                    location.href=appContext +"/budgetItemReservation/list?sort=id&order=desc"

                }
            },
            error: function (resp) {
                alert(JSON.stringify(resp));
            }
        });

}


var selectAllCheckbox = function (elemnt) {
    if (elemnt.checked) {
        $('[type="checkbox"]').each(function () {
            this.checked = true;
        });

    } else {
        $('[type="checkbox"]').each(function () {
            this.checked = false;
        });
    }
}

var formatFlexiGrid = function () {
    var gridContainer = $('.flexigrid');
    var headers = gridContainer.find('div.hDiv table tr:first th:not(:hidden)');
    var drags = gridContainer.find('div.cDrag div');
    var offset = 0;
    var firstDataRow = this.Grid.find('tr:first td:not(:hidden)');
    var columnWidths = new Array(firstDataRow.length);
    this.Grid.find('tr').each(function () {
        $(this).find('td:not(:hidden)').each(function (i) {
            var colWidth = $(this).outerWidth();
            if (!columnWidths[i] || columnWidths[i] < colWidth) {
                columnWidths[i] = colWidth;
            }
        });
    });
    for (var i = 0; i < columnWidths.length; ++i) {
        var bodyWidth = columnWidths[i];

        var header = headers.eq(i);
        var headerWidth = header.outerWidth();

        var realWidth = bodyWidth > headerWidth ? bodyWidth : headerWidth;

        firstDataRow.eq(i).css('width', realWidth);
        header.css('width', realWidth);
        drags.eq(i).css('left', offset + realWidth);
        offset += realWidth;
    }
}

function getBrowserName() {
    var nVer = navigator.appVersion;
    var userAgent = navigator.userAgent;
    var browserName = navigator.appName;
    var nameOffset, verOffset, ix;

    //alert("User Agent : " + userAgent);
    // In Opera, the true version is after "Opera" or after "Version"
    if ((verOffset = userAgent.indexOf("Opera")) != -1) {
        browserName = "Opera";
    }
    // In MSIE, the true version is after "MSIE" in userAgent
    else if ((verOffset = userAgent.indexOf("MSIE")) != -1) {
        //alert(" Ver of Set: "+userAgent.indexOf("MSIE"));
        browserName = "MSIE";
    }
    // In Chrome, the true version is after "Chrome"
    else if ((verOffset = userAgent.indexOf("Chrome")) != -1) {
        browserName = "Chrome";
    }
    // In Safari, the true version is after "Safari" or after "Version"
    else if ((verOffset = userAgent.indexOf("Safari")) != -1) {
        browserName = "Safari";
    }
    // In Firefox, the true version is after "Firefox"
    else if ((verOffset = userAgent.indexOf("Firefox")) != -1) {
        //alert("Firefox : " + userAgent.indexOf("Firefox"));
        browserName = "Firefox";
    }
    else if ((verOffset = userAgent.indexOf("rv")) != -1) { //checking if Internet Explorer 11
        var substring = userAgent.substring(userAgent.indexOf("rv"), userAgent.indexOf(")"));
        //alert("IE11 : " + substring);
        if (substring == 'rv:11.0') {
            browserName = "IE11";
        }
    }
    // In most other browsers, "name/version" is at the end of userAgent
    else if ((nameOffset = userAgent.lastIndexOf(' ') + 1) <
        (verOffset = userAgent.lastIndexOf('/'))) {
        browserName = userAgent.substring(nameOffset, verOffset);
        if (browserName.toLowerCase() == browserName.toUpperCase()) {
            browserName = navigator.appName;
        }
    }

    return browserName;
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toGMTString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length,c.length);
    }
    return "";
}

function checkSession() {
    var sessionExpiry = Math.abs(getCookie('sessionExpiry'));
    var timeOffset = Math.abs(getCookie('clientTimeOffset'));
    var localTime = new Date().getTime();
    if (localTime - timeOffset > (sessionExpiry-600*1000)) {
        if (Math.abs(getCookie('needToShowMsg'))) {
            setCookie('needToShowMsg', 0 ,1);

        // This added for run
            /*if(confirm(sessionExpMsg)){
                top.location.href = top.location.href;
            }*/
        }
    } else {
        setTimeout('checkSession()', 10000);
    }
}

function UpdateFiscalYear(){
//    alert("jijjoioijoi")
    $.ajax({
        url: appContext + '/fiscalYear/update',
        type: 'POST',
        data: $("form[name='updateFiscalYear']").serializeArray(),
        success: function (resp) {
//        alert(resp.result);
//            location.href="/fiscalYear/list";
            if(resp.result == 'success'){location.href=appContext +"/fiscalYear/list";}
            else if(resp.result == 'failed'){location.reload();}
        },
        error: function (resp) {
            alert(JSON.stringify(resp));
        }
    });

}

function createFiscalYear(){
//    alert("create ......")
    $.ajax({
        url: appContext + '/fiscalYear/save',
        type: 'POST',
        data: $("form[name='createFiscalYear']").serializeArray(),
        success: function (resp) {
            if(resp.result == 'success'){
                location.href= appContext +"/fiscalYear/list";
            }
            else if(resp.result == 'failed'){
                location.reload();
            }
//            location.href="/fiscalYear/list";
        },
        error: function (resp) {
            alert(JSON.stringify(resp));
        }
    });

}

$(document).ready(function(){

    $("input[placeholder]").focus(function(){
        $(this).data('placeholder', $(this).attr('placeholder'));
        $(this).attr('placeholder','');
    });

    $("input[placeholder]").blur(function(){
        $(this).attr('placeholder', $(this).data('placeholder'));
    });

    var serverTime = getCookie('serverTime');
    serverTime = serverTime == null ? null : Math.abs(serverTime);
    var clientTimeOffset = new Date().getTime() - serverTime;
    setCookie('clientTimeOffset', clientTimeOffset,1);

    if(location.href.search("login/")==-1) {
        checkSession();
        setCookie('needToShowMsg', 1 ,1);
    }

    $('.slideToggler2').click(function(){
        $('.slideContent2').slideToggle('slow');
        $(this).toggleClass('slideSign2');
        return false;
    });

    $('.slideToggler3').click(function(){
        $('.slideContent3').slideToggle('slow');
        $(this).toggleClass('slideSign3');
        return false;
    });

    $('.collaps2').click(function(){
        $('.slideContent2').slideToggle('slow');
        $('.slideToggler2').toggleClass('slideSign2');
        return false;
    });

    //********************Dashboard Information*******************************
    $('.slideToggler').click(function(){

        $('.slideContent').slideToggle('slow');
        $(this).toggleClass('slideSign2');
        return false;
    });

    $('.collaps2').click(function(){
        $('.slideContent').slideToggle('slow');
        $('.slideToggler').toggleClass('slideSign2');
        return false;
    });
});

function isFiscalYearActive(fiscalYear,status,from) {

    $.ajax({
        url: appContext + '/fiscalYear/isFiscalYearActive',
        type: 'POST',
        data: {
            status : status
        },
        success: function (resp) {
            if(resp.result == 'success'){
                fiscalYearWiseInvoiceCopy(fiscalYear,status,from)
            }
            else if(resp.result == 'failed'){
                location.href= appContext +"/fiscalYear/list";
            }
        },
        error: function (resp) {
            alert(JSON.stringify(resp));
        }
    });

}

var fiscalYearWiseInvoiceCopy = function (fiscalYear,status,from) {

    if(status == 2){
        if(from == 'Update'){
            UpdateFiscalYear();
        }
        else{
            createFiscalYear();
        }
    }
    else{
         $.ajax({
            type:'POST',
            dataType: 'json',
            url:appContext +'/FiscalYear/checkInvoiceForFiscalYear',
            data:{
                datePickerYearBegin: fiscalYear
            },
            success: function(ajax)
            {

                if(ajax.result == 'yes'){

                    $("#dialog-modal").dialog(
                        {   modal: true,
                            autoOpen: true,
                            resizable: false,
                            width: 1000,
                            height: 400,
                            open: function (event, ui) {
                                $(this).load(appContext + '/fiscalYear/fiscalYearWiseInvoice?listForPopup=true&isForUpdate=true&fiscalYear=' + fiscalYear );
                            },
                            buttons: {
                              "Bestaand Budget behouden": function (event) {
                                    $(this).dialog('close');
                                    if(from == 'Update'){
                                        UpdateFiscalYear();
                                    }
                                    else{
                                        createFiscalYear();
                                    }
                                },
                               "Kopieer Budget": function (event) {
                                    if (confirm("Do you want to Copy Budget?")) {
                                        $.ajax({
                                            url: appContext + '/fiscalYear/copyInvoiceFiscalYearwise?fiscalYear=' + fiscalYear,
                                            type: 'POST',
                                            data: $("form[name='copyInvoice']").serializeArray(),
                                            success: function (resp) {
                                                if (resp.result == 'success') {
                                                    if(from == 'Update'){
                                                        UpdateFiscalYear();
                                                        $("#dialog-modal").dialog('close');
                                                    }
                                                    else{
                                                        createFiscalYear();
                                                    }
                                                }
                                            },
                                            error: function (resp) {
                                                alert(JSON.stringify(resp));
                                            }
                                        });
                                    }
                                }
                            }
                        });
                }
                else if(ajax.result == 'no'){

                    if(from == 'Update'){
                        UpdateFiscalYear();
                    }
                    else{
                        createFiscalYear();
                    }

                }

            }
        });

    }

}

var showPrivateDeleteDialog = function (id, privateId) {
    $("#dialog-modal").dialog(
        {   modal: true,
            autoOpen: true,
            resizable: false,
            width: 1000,
            height: 400,
            open: function (event, ui) {
                $(this).load(appContext +'/BudgetItemPrivate/budgetListOfPrivate?listForPopup=true&isForDelete=true&privateId=' + privateId + "&budgetItemId=" + id);
            },
            buttons: {
                Cancel: function (event) {
                    $(this).dialog('close');
                },
                Delete: function (event) {
//                    alert(location.href);
                    if (confirm("Do you want to delete?")) {

                        $.ajax({
                            url: appContext + '/BudgetItemPrivate/deleteBudgetItems',
                            type: 'POST',
                            data: $("form[name='deleteBudgetItemForm']").serializeArray(),
                            success: function (resp) {
                                if (resp.result == 'success') {
                                    $("#dialog-modal").dialog('close');
//                                    location.href = location.href;
                                    location.href=appContext +"/BudgetItemPrivate/list?sort=id&order=desc"

                                }
                            },
                            error: function (resp) {
                                alert(JSON.stringify(resp));
                            }
                        });
                    }
                }
            }
        });

}


function deletePrivateBudgetItems(budgetItemId){

        $.ajax({
            url: appContext + '/BudgetItemPrivate/deleteBudgetItems',
            type: 'POST',
            data: {
                budgetItemId:budgetItemId
            },
            success: function (resp) {
                if (resp.result == 'success') {
                    location.href=appContext +"/BudgetItemPrivate/list?sort=id&order=desc"
                }
            },
            error: function (resp) {
                alert(JSON.stringify(resp));
            }
        });

}


function showMenualReconDialog(){
    $("#dialogRecon").dialog(
        {   modal: true,
            autoOpen: true,
            resizable: false,
            width: 395,
            height: 200,
            open: function (event, ui) {
                $(this).load(appContext +'/bankReconciliation/dialogBoxOfReconciliation');
            },
            buttons: {
                Ok: function (event) {
                    $(this).dialog('close');
                }
            }
        });
}

function reconciliationVendorCustomerinfo(isSelectedVendor,relationBankId){

    var customerOrVednorid ='';
    var debitCredit = '';

    if(isSelectedVendor == true || isSelectedVendor == null ){
        customerOrVednorid = document.getElementById("customerOrVendorId").value;
    }
    else if(isSelectedVendor == false ){
        $.ajax({
            url: appContext +'/BankReconciliation/getDebitCreditType?relationBankId='+relationBankId,
            type: 'GET',
            async: false,
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(xhr.status);
                console.log(thrownError);
            },

            success: function (response) {
                debitCredit = response [0][0];

            }
        });

        if(debitCredit == "D"){
            customerOrVednorid = $("#customerOrVendorId").find("option:contains('All Vendor')").val();
            if(!customerOrVednorid ) { //when language in dutch
                customerOrVednorid = $("#customerOrVendorId").find("option:contains('Alle onbetaalde verkoopfacturen')").val();
            }
        }
        else if (debitCredit == "C"){
            customerOrVednorid = $("#customerOrVendorId").find("option:contains('All Customer')").val();
            if(!customerOrVednorid ) { //when language in dutch
                customerOrVednorid = $("#customerOrVendorId").find("option:contains('Alle onbetaalde kostenfacturen')").val();
            }
        }
    }
        $.ajax({
            type: 'POST',
            url: appContext +'/BankReconciliation/selectCustomerVendorRelatedInformation',
            async:false,
            data: {
                id: customerOrVednorid,
                relationBankNo: document.getElementById("byBankAccountNo").value,
                selectedOrNot: isSelectedVendor
            },
            dataType: "html",
            success: function (response) {
                $("#customerVendorAccountresults").html(response);

            }
        });

    }



/*
* this function is use for calculating Duedate for Income Invoice
* using Invoice date and Customer Terms
* */
function calculateDueDate(){

    var termsID =  $("#termsId").val();

    $.ajax({
//        url:"${g.createLink(controller: 'PaymentTerms', action: 'termsDuration')}",
        url:appContext +'/PaymentTerms/termsDuration',
        type:'POST',
        data:'termsId=' + termsID,
        success:function (data) {
            $("#termsValue").val(data);
            var tfromdate = $("#date_picker_transDate").val();
            var spldate = tfromdate.split('-');
            var joindate = new Date(
                parseInt(spldate[2], 10),
                    parseInt(spldate[1], 10) - 1,
                parseInt(spldate[0], 10)
            );
            var numberOfDaysToAdd = parseInt(data);
            joindate.setDate(joindate.getDate() + numberOfDaysToAdd);
            var newday = joindate.getDate();
            var newMonth = joindate.getMonth()+ 1;
            var newYear = joindate.getFullYear();
            var formateddate = newday+"-"+newMonth+"-"+newYear;
            if(formateddate=="NaN-NaN-NaN"){
                $("#date_picker_dueDate").val('');
            }  else{
                $("#date_picker_dueDate").val(formateddate);
            }
            //var sumDate = tfromdate + data;
        }
    });
}


function changeTypeOfPayment(id){
    if(!(id.substr(0,1)=="0")){
        jQuery.ajax({
            type:'POST',
            data: {
                id: id
            },
            url:appContext +'/BankReconciliation/selectManualReconciliationActionType',
            success:function(data,textStatus){
                jQuery('#updateReconciliationInfoArea').html(data);
            },
            error:function(XMLHttpRequest,textStatus,errorThrown){

            }
        });
    }
}

