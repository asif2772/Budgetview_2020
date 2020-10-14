package vb

import bv.CoreParamsHelper
import grails.converters.JSON
import grails.util.Holders
import groovy.sql.GroovyRowResult

import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class IncomeInvoiceUtil {

    public DateFormat formatter;
    public Date date;
    public static final String STR_DATE_FORMAT = "yyyy-MM-dd";
    public static final String GRID_DATE_FORMAT = "dd-MM-yyyy";
    public String STR_DATE_RETURN = "";
    //private String contextPath = ConfigurationHolder.config.grails.applicationContext;
    private String contextPath = Holders.applicationContext.servletContext.contextPath

    public Date getDateFormInput(String str) {
        try {
            formatter = new SimpleDateFormat(GRID_DATE_FORMAT);
            date = (Date) formatter.parse(str);
        } catch (Exception e) {
            STR_DATE_RETURN = "";
        }
        return date;
    }

    public String getStrTransDate(Date transDate) {
        try {
            formatter = new SimpleDateFormat(GRID_DATE_FORMAT);
            STR_DATE_RETURN = formatter.format(transDate);
        } catch (Exception e) {
            STR_DATE_RETURN = "";
        }
        return STR_DATE_RETURN;
    }
    //method to get UI grid str date from rest api date str

    public String getStrDateForGrid(String str) {
        try {
            formatter = new SimpleDateFormat(STR_DATE_FORMAT);
            date = (Date) formatter.parse(str);
            formatter = new SimpleDateFormat(GRID_DATE_FORMAT);
            STR_DATE_RETURN = formatter.format(date);
        } catch (Exception e) {
            STR_DATE_RETURN = "";
        }
        return STR_DATE_RETURN;
    }

    public String getStrDateForGrid(Date transDate) {
        try {
            formatter = new SimpleDateFormat(GRID_DATE_FORMAT);
            STR_DATE_RETURN = formatter.format(transDate);
        } catch (Exception e) {
            STR_DATE_RETURN = "";
        }
        return STR_DATE_RETURN;
    }

    public String replaceComaToDot(String strInput){
        String strOutput = ""

        String strTemp = strInput.replaceAll(",", ".")
        strOutput = strTemp

        return strOutput;
    }

    public boolean isValidVATInput(double dInputVat,double dActualVat){
        double vatDifference = (dInputVat - dActualVat);
        vatDifference = vatDifference.round(2);

        if(vatDifference > 0.05 || vatDifference < -0.05){
            //flash.message = message(code: 'bv.invoiceVatDifference.label', default: 'Difference more than +/- 0.05 not acceptable')
            //render(layout: 'ajax',template: 'linelist')
            return false;
        }

        return true;
    }

    /*
    * Grid for income invoice list
    *
    * */
    public List wrapInvoiceIncomeInGrid(List<GroovyRowResult> quickEntries, int start, budgetCustomerId, bookingPeriod, bookInvoiceId, fiscalYearId, pdforNot) {
        List quickExpenseList = new ArrayList()
        def expenseEntry
        GridEntity obj
        String changeBooking
        try {
            int counter = start + 1
            for (int i = 0; i < quickEntries.size(); i++) {
                expenseEntry = quickEntries[i];
                obj = new GridEntity();
                obj.id = expenseEntry.id

                DecimalFormat twoDForm = new DecimalFormat("#0.00");

                def budgetItemDetailsId = new CoreParamsHelper().getBudgetItemIncomeDetailsIdFromInvoiceIncome(expenseEntry.id)
                def bookingPeriodFormat = (new CoreParamsHelper().monthNameShow(Integer.parseInt(expenseEntry.bookingPeriod ))) + '-' + fiscalYearId;
                def customerId = expenseEntry.customerId


                BigDecimal totalAmountIncVat=new BigDecimal(expenseEntry.totalAmountIncVat)
                def tAmountIncVat=twoDForm.format(totalAmountIncVat)

                //println(expenseEntry.bookingPeriod);
                //changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${customerId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\",\"${budgetItemDetailsId}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:deleteBooking(\"${expenseEntry.id}\",\"${customerId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\")'><img width=\"16\" height=\"15\" alt=\"Delete\" src=\"${contextPath}/images/delete.png\"></a>"
                if(pdforNot == 'bookInvoice'){
                    changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${customerId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\",\"${budgetItemDetailsId}\",\"${budgetCustomerId}\")' ><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>"
                }else{
                    changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${customerId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\",\"${budgetItemDetailsId}\",\"${budgetCustomerId}\")' ><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:copyBooking(\"${expenseEntry.id}\",\"${customerId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\",\"${budgetItemDetailsId}\",\"${budgetCustomerId}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/copy.png\" class=\"copyJs\"></a> "
                }


                obj.cell = ["invoiceNumber":expenseEntry.invoiceNumber,"bookingPeriod": bookingPeriodFormat,"customerName": expenseEntry.customerName,"invoiceDate": expenseEntry.invoiceDate,"paymentReference": expenseEntry.paymentRef,"totalGlAmount":tAmountIncVat,"totalVat": expenseEntry.totalVat, "action":changeBooking]
                quickExpenseList.add(obj)
                counter++;
            }
            return quickExpenseList;
        } catch (Exception ex) {

            quickExpenseList = [];
            return quickExpenseList;
        }
    }

    public List wrapQuickEntryInvoiceIncomeInGrid(List<GroovyRowResult> quickEntries, int start, budgetCustomerId, bookingPeriod, bookInvoiceId, fiscalYearId, pdforNot,liveUrl) {
        List quickExpenseList = new ArrayList()
        def expenseEntry
        GridEntity obj
        String changeBooking
        try {
            int counter = start + 1
            for (int i = 0; i < quickEntries.size(); i++) {
                expenseEntry = quickEntries[i];
                obj = new GridEntity();
                obj.id = expenseEntry.id

                DecimalFormat twoDForm = new DecimalFormat("#0.00");

                def budgetItemDetailsId = new CoreParamsHelper().getBudgetItemIncomeDetailsIdFromInvoiceIncome(expenseEntry.id)
                def bookingPeriodFormat = (new CoreParamsHelper().monthNameShow(Integer.parseInt(expenseEntry.bookingPeriod ))) + '-' + fiscalYearId;
                def customerId = expenseEntry.customerId


                BigDecimal totalAmountIncVat=new BigDecimal(expenseEntry.totalAmountIncVat)
                def tAmountIncVat=twoDForm.format(totalAmountIncVat)

                //changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${customerId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\",\"${budgetItemDetailsId}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:deleteBooking(\"${expenseEntry.id}\",\"${customerId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\")'><img width=\"16\" height=\"15\" alt=\"Delete\" src=\"${contextPath}/images/delete.png\"></a>"

//               if(pdforNot == 'bookInvoice'){
//                    changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${customerId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\",\"${budgetItemDetailsId}\",\"${budgetCustomerId}\")' ><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>"
//                }else{
//                    changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${customerId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\",\"${budgetItemDetailsId}\",\"${budgetCustomerId}\")' ><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:copyBooking(\"${expenseEntry.id}\",\"${customerId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\",\"${budgetItemDetailsId}\",\"${budgetCustomerId}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/copy.png\" class=\"copyJs\"></a> "
//                }

//                changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${customerId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\",\"${budgetItemDetailsId}\",\"${budgetCustomerId}\")' ><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>"
                changeBooking = "<a href='javascript:changeBookingIncome(\"${expenseEntry.id}\",\"${budgetCustomerId}\",\"${customerId}\",\"${bookingPeriod}\",\"${liveUrl}\")' ><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>"


                obj.cell = ["invoiceNumber":expenseEntry.invoiceNumber,"bookingPeriod": bookingPeriodFormat,"venOrCusName": expenseEntry.customerName,"invoiceDate": expenseEntry.invoiceDate,"paymentReference": expenseEntry.paymentRef,"totalGlAmount":tAmountIncVat,"totalVat": expenseEntry.totalVat, "action":changeBooking]
                quickExpenseList.add(obj)
                counter++;
            }
            return quickExpenseList;
        } catch (Exception ex) {

            quickExpenseList = [];
            return quickExpenseList;
        }
    }

    public List wrapBudgetItemInGrid(List<GroovyRowResult> quickEntries, int start, journalId, customerId, bookingPeriod) {
        //public List wrapBudgetItemInGrid(List<GroovyRowResult> quickEntries, int start) {
        List quickExpenseList = new ArrayList()
        def expenseEntry
        GridEntity obj
        String changeBooking
        try {
            int counter = start + 1
            for (int i = 0; i < quickEntries.size(); i++) {
                expenseEntry = quickEntries[i];

                obj = new GridEntity();
                obj.id = expenseEntry.invoiceIncomeId + "::" + expenseEntry.detailsID

                DecimalFormat twoDForm = new DecimalFormat("#0.00");

                def aa = expenseEntry.totalPriceWithoutVat
                Double bb = Double.parseDouble(aa)
                def totalPriceWithoutVatMd = twoDForm.format(bb)

                def cc = expenseEntry.totalPriceWithVat
                Double dd = Double.parseDouble(cc)
                def totalPriceWithVatMd = twoDForm.format(dd)

                //changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.invoiceExpenseId}\",\"${journalId}\",\"${vendorId}\",\"${bookingPeriod}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"../../images/edit.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:deleteBooking(\"${journalId}\",\"${journalId}\",\"${journalId}\",\"${journalId}\")'><img width=\"16\" height=\"15\" alt=\"Delete\" src=\"../../images/delete.png\"></a>"
                changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.invoiceIncomeId}\",\"${journalId}\",\"${customerId}\",\"${bookingPeriod}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"../../images/edit.png\"></a>"
                obj.cell = [counter, expenseEntry.budgetItemID, expenseEntry.glAccountName, expenseEntry.createdDate, totalPriceWithoutVatMd, totalPriceWithVatMd, expenseEntry.total, changeBooking]
                quickExpenseList.add(obj)
                counter++;
            }
            return quickExpenseList;
        } catch (Exception ex) {
//            log.error(ex.getMessage());
            quickExpenseList = [];
            return quickExpenseList;
        }
    }

    public List wrapUnpaidInvoOfCusInGrid(List<GroovyRowResult> unpaidList) {
        List customerUnpaidList = new ArrayList()
        def eachList
        GridEntity obj
        String changeBooking
        try {
            for (int i = 0; i < unpaidList.size(); i++) {
                eachList = unpaidList[i]
                obj = new GridEntity()
                def eachListJson = eachList as JSON
                changeBooking = "<input type='checkbox'class='checkBoxClass' name='rowObj' value='${eachListJson}'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                obj.cell = ["customerName": eachList[4], "invoiceNumber":eachList[5],
                            "paymentRef": eachList[9], "invoiceDate": eachList[12],
                            "invoiceAmount": eachList[6], "paidAmount": eachList[14].toString(),
                            "outstandingAmount": eachList[7], "action": changeBooking]
                customerUnpaidList.add(obj)

            }
            return customerUnpaidList
        } catch (Exception ex) {
            customerUnpaidList = []
            return customerUnpaidList
        }
    }

}
