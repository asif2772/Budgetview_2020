package vb

import bv.BudgetViewDatabaseService
import bv.BusinessCompany
import bv.CoreParamsHelper
import bv.ImportInvoiceService
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellValue
import org.grails.web.json.JSONObject

import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class QuickEntryUtil {

    public DateFormat formatter;
    public Date date;
    public static final String STR_DATE_FORMAT = "yyyy-MM-dd";
    public static final String GRID_DATE_FORMAT = "dd-MM-yyyy";
    public String STR_DATE_RETURN = "";
    private String contextPath = Holders.applicationContext.servletContext.contextPath
//  private String contextPath = ConfigurationHolder.config.grails.applicationContext;

    public String[] allDateFormats = [
            "dd-MM-yy", "dd-MM-yyyy", "MM-dd-yy", "MM-dd-yyyy", "yyyy-MM-dd",
            "dd/MM/yy", "dd/MM/yyyy", "MM/dd/yy", "MM/dd/yyyy", "yyyy/MM/dd",
            "ddMMyy", "ddMMyyyy", "MMddyy", "MMddyyyy", "yyyyMMdd",
            "dd:MM:yy", "dd:MM:yyyy", "MM:dd:yy", "MM:dd:yyyy", "yyyy:MM:dd",
            "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd'T'HH:mm:ssZ",
            "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd HH:mm:ss",
            "MM/dd/yyyy HH:mm:ss", "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'",
            "MM/dd/yyyy'T'HH:mm:ss.SSSZ", "MM/dd/yyyy'T'HH:mm:ss.SSS",
            "MM/dd/yyyy'T'HH:mm:ssZ", "MM/dd/yyyy'T'HH:mm:ss",
            "yyyy:MM:dd HH:mm:ss"
    ]

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

    public List wrapListInGrid(List<GroovyRowResult> quickEntries, int start) {
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
                changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\")'>Edit</a>"
                obj.cell = [counter, expenseEntry.invoice_number, expenseEntry.vendor_id, "", getStrDateForGrid(expenseEntry.created_date), getStrDateForGrid(expenseEntry.trans_date), expenseEntry.total_gl_amount, expenseEntry.total_vat, changeBooking]
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

    /*
    * Grid for List budget Item page which comes from dashboard
    *
    * */

    public List wrapBudgetItemInGrid(List<GroovyRowResult> quickEntries, int start, journalId, vendorId, bookingPeriod) {
        //public List wrapBudgetItemInGrid(List<GroovyRowResult> quickEntries, int start) {
        List quickExpenseList = new ArrayList()
        def expenseEntry
        GridEntity obj
        String changeBooking
        try {
            int counter = start + 1
            for (int i = 0; i < quickEntries.size(); i++) {
                expenseEntry = quickEntries[i];

                DecimalFormat twoDForm = new DecimalFormat("#.00");

                def aa = expenseEntry.totalPriceWithoutVat
                Double bb = Double.parseDouble(aa)
                def totalPriceWithoutVatMd = twoDForm.format(bb)

                def cc = expenseEntry.totalPriceWithVat
                Double dd = Double.parseDouble(cc)
                def totalPriceWithVatMd = twoDForm.format(dd)

                obj = new GridEntity();
                obj.id = expenseEntry.invoiceExpenseId + "::" + expenseEntry.detailsID
                //changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.invoiceExpenseId}\",\"${journalId}\",\"${vendorId}\",\"${bookingPeriod}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"../../images/edit.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:deleteBooking(\"${journalId}\",\"${journalId}\",\"${journalId}\",\"${journalId}\")'><img width=\"16\" height=\"15\" alt=\"Delete\" src=\"../../images/delete.png\"></a>"
                changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.invoiceExpenseId}\",\"${journalId}\",\"${vendorId}\",\"${bookingPeriod}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>"
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

    public List wrapEntryBudgetItemInGrid(List<GroovyRowResult> quickEntries, int start, vendorId, journalId, bookingPeriod, invoiceBudgetExpenseData) {
        List quickExpenseList = new ArrayList()
        def expenseEntry
        GridEntity obj
        String changeBooking

        try {
            def notDelete = 0
            int counter = start + 1
            for (int i = 0; i < quickEntries.size(); i++) {
                expenseEntry = quickEntries[i];
                notDelete = 0
                invoiceBudgetExpenseData.each { phn ->
                    if (expenseEntry.id == phn) {
                        notDelete = 1
                    }
                }

                def bookStartPeriod = ""
                if (expenseEntry.booking_period_start_month == 12) {
                    bookStartPeriod = "Dec" + "-" + expenseEntry.booking_period_start_year
                } else {
                    bookStartPeriod = new CoreParamsHelper().monthNameShow(expenseEntry.booking_period_start_month) + "-" + expenseEntry.booking_period_start_year
                }
                def bookEndPeriod = ""
                if (expenseEntry.booking_period_end_month == 12) {
                    bookEndPeriod = "Dec" + "-" + expenseEntry.booking_period_end_year
                } else {
                    bookEndPeriod = new CoreParamsHelper().monthNameShow(expenseEntry.booking_period_end_month) + "-" + expenseEntry.booking_period_end_year
                }

                def showtotalGlAmount = expenseEntry.totalGlAmount
                def showtotalVat = expenseEntry.totalVat

                Double showtotalGlAmountc = Double.parseDouble(showtotalGlAmount)
                Double showtotalVatc = Double.parseDouble(showtotalVat)

                def showtotalGlAmounta = String.format("%.2f", showtotalGlAmountc)
                def showtotalVata = String.format("%.2f", showtotalVatc)

                obj = new GridEntity();
                obj.id = expenseEntry.id

                if (bookingPeriod) {

                }


                if (notDelete == 0) {
                    changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${expenseEntry.vendor_id}\",\"${journalId}\",\"${bookingPeriod}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:deleteExpenseBudgetItem(\"${expenseEntry.id}\")'><img width=\"16\" height=\"15\" alt=\"Delete\" src=\"${contextPath}/images/delete.png\"></a>"
//                    changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${expenseEntry.vendor_id}\",\"${journalId}\",\"${bookingPeriod}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:showDeleteDialog(\"${expenseEntry.id}\",\"${expenseEntry.vendor_id}\")'><img width=\"16\" height=\"15\" alt=\"Delete\" src=\"${contextPath}/images/delete.png\"></a>"
                } else {
                    changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${expenseEntry.vendor_id}\",\"${journalId}\",\"${bookingPeriod}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>"
                }

                //obj.cell = [counter, expenseEntry.budgetItemName, expenseEntry.budgetCode, showtotalGlAmounta, showtotalVata, bookStartPeriod, bookEndPeriod, changeBooking]
                obj.cell = ["budgetItemName": expenseEntry.budgetItemName, "budgetCode": expenseEntry.budgetCode, "totalGlAmount": showtotalGlAmounta, "totalVat": showtotalVata, "bookStartPeriod": bookStartPeriod, "action": changeBooking]
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
// *****************************************************

    public List wrapEntryBudgetItemSearchInGrid(List<GroovyRowResult> quickEntries, int start, vendorId, journalId, bookingPeriod, invoiceBudgetExpenseData) {
        List quickExpenseList = new ArrayList()
        def expenseEntry
        GridEntity obj
        String changeBooking

        try {
            def notDelete = 0
            int counter = start + 1
            for (int i = 0; i < quickEntries.size(); i++) {
                expenseEntry = quickEntries[i];
                notDelete = 0
                invoiceBudgetExpenseData.each { phn ->
                    if (expenseEntry.id == phn) {
                        notDelete = 1
                    }
                }

                def bookStartPeriod = ""
                if (expenseEntry.booking_period_start_month == 12) {
                    bookStartPeriod = "Dec " + " - " + expenseEntry.booking_period_start_year
                } else {
                    bookStartPeriod = new CoreParamsHelper().monthNameShow(expenseEntry.booking_period_start_month) + " - " + expenseEntry.booking_period_start_year
                }
                def bookEndPeriod = ""
                if (expenseEntry.booking_period_end_month == 12) {
                    bookEndPeriod = "Dec " + " - " + expenseEntry.booking_period_end_year
                } else {
                    bookEndPeriod = new CoreParamsHelper().monthNameShow(expenseEntry.booking_period_end_month) + " - " + expenseEntry.booking_period_end_year
                }

                def showtotalGlAmount = expenseEntry.totalGlAmount
                def showtotalVat = expenseEntry.totalVat

//                Double showtotalGlAmountc = Double.parseDouble(showtotalGlAmount)
//                Double showtotalVatc = Double.parseDouble(showtotalVat)

                def showtotalGlAmounta = String.format("%.2f", showtotalGlAmount)
                def showtotalVata = String.format("%.2f", showtotalVat)

                obj = new GridEntity();
                obj.id = expenseEntry.id

                if (bookingPeriod) {

                }


                if (notDelete == 0) {
                    changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${expenseEntry.vendor_id}\",\"${journalId}\",\"${bookingPeriod}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:deleteExpenseBudgetItem(\"${expenseEntry.id}\")'><img width=\"16\" height=\"15\" alt=\"Delete\" src=\"${contextPath}/images/delete.png\"></a>"
//                    changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${expenseEntry.vendor_id}\",\"${journalId}\",\"${bookingPeriod}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:showDeleteDialog(\"${expenseEntry.id}\",\"${expenseEntry.vendor_id}\")'><img width=\"16\" height=\"15\" alt=\"Delete\" src=\"${contextPath}/images/delete.png\"></a>"
                } else {
                    changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${expenseEntry.vendor_id}\",\"${journalId}\",\"${bookingPeriod}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>"
                }

                //obj.cell = [counter, expenseEntry.budgetItemName, expenseEntry.budgetCode, showtotalGlAmounta, showtotalVata, bookStartPeriod, bookEndPeriod, changeBooking]
                obj.cell = [expenseEntry.budgetItemName, expenseEntry.budgetCode, showtotalGlAmounta, showtotalVata, bookStartPeriod, changeBooking]
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

// ******************************************************
    /*
   * Grid for List Expense Invoice List
   *
   * */

    public List wrapInvoiceExpenseInGrid(List<GroovyRowResult> quickEntries, int start, budgetVendorId, bookingPeriod, bookInvoiceId, fiscalYearId) {
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

                DecimalFormat twoDForm = new DecimalFormat("#.00");

                BigDecimal showtotalGlAmounta = new BigDecimal(expenseEntry.totalAmountIncVat)
                def showtotalGlAmount = twoDForm.format(showtotalGlAmounta)
                BigDecimal showtotalVata = new BigDecimal(expenseEntry.totalVat)
                def showtotalVat = twoDForm.format(showtotalVata)
                def vendorId = expenseEntry.vendorId

                def bookingPeriodFormat = (new CoreParamsHelper().monthNameShow(Integer.parseInt(expenseEntry.bookingPeriod))) + '-' + fiscalYearId;
                def budgetItemDetailsId = new CoreParamsHelper().getBudgetItemExpenseDetailsIdFromInvoiceExpense(expenseEntry.id)

                def vendorShopId = expenseEntry.shopId

                changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${vendorId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\",\"${budgetItemDetailsId}\",\"${budgetVendorId}\",\"${vendorShopId}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>"
//                changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${vendorId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\",\"${budgetItemDetailsId}\",\"${budgetVendorId}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>"
                obj.cell = ["invoiceNumber": expenseEntry.invoiceNumber, "bookingPeriod": bookingPeriodFormat, "vendorName": expenseEntry.vendorName, "invoiceDate": expenseEntry.invoiceDate, "paymentReference": expenseEntry.paymentRef, "totalGlAmount": showtotalGlAmount, "totalVat": showtotalVat, "action": changeBooking]

                quickExpenseList.add(obj)
                counter++;
            }
            return quickExpenseList;
        } catch (Exception ex) {

            quickExpenseList = [];
            return quickExpenseList;
        }
    }

    public List wrapQuickEntryInvoiceExpenseInGrid(List<GroovyRowResult> quickEntries, int start, budgetVendorId, bookingPeriod, bookInvoiceId, fiscalYearId,liveUrl) {
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

                DecimalFormat twoDForm = new DecimalFormat("#.00");

                BigDecimal showtotalGlAmounta = new BigDecimal(expenseEntry.totalAmountIncVat)
                def showtotalGlAmount = twoDForm.format(showtotalGlAmounta)
                BigDecimal showtotalVata = new BigDecimal(expenseEntry.totalVat)
                def showtotalVat = twoDForm.format(showtotalVata)
                def vendorId = expenseEntry.vendorId

                def bookingPeriodFormat = (new CoreParamsHelper().monthNameShow(Integer.parseInt(expenseEntry.bookingPeriod))) + '-' + fiscalYearId;
                def budgetItemDetailsId = new CoreParamsHelper().getBudgetItemExpenseDetailsIdFromInvoiceExpense(expenseEntry.id)

                def vendorShopId = expenseEntry.shopId

                changeBooking = "<a href='javascript:changeBookingExpense(\"${expenseEntry.id}\",\"${budgetVendorId}\",\"${vendorId}\",\"${vendorShopId}\",\"${bookingPeriod}\",\"${liveUrl}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>"
//                changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${vendorId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\",\"${budgetItemDetailsId}\",\"${budgetVendorId}\",\"${vendorShopId}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>"
//                changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${vendorId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\",\"${budgetItemDetailsId}\",\"${budgetVendorId}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>"
                obj.cell = ["invoiceNumber": expenseEntry.invoiceNumber, "bookingPeriod": bookingPeriodFormat, "venOrCusName": expenseEntry.vendorName, "invoiceDate": expenseEntry.invoiceDate, "paymentReference": expenseEntry.paymentRef, "totalGlAmount": showtotalGlAmount, "totalVat": showtotalVat, "action": changeBooking]

                quickExpenseList.add(obj)
                counter++;
            }
            return quickExpenseList;
        } catch (Exception ex) {

            quickExpenseList = [];
            return quickExpenseList;
        }
    }


    public List wrapPrivateProcessInGrid(List<GroovyRowResult> quickEntries, int start) {
        List quickPrivateProcessList = new ArrayList()
        def processEntry
        GridEntity obj
        String changeBooking
        try {
            int counter = start + 1
            for (int i = 0; i < quickEntries.size(); i++) {
                processEntry = quickEntries[i];
                obj = new GridEntity();
                obj.id = processEntry.id

                def amount = processEntry.amount.round(2)
                DecimalFormat twoDForm = new DecimalFormat("#.00");
                amount = twoDForm.format(amount)
                def description = processEntry.description
                def budgetName = "<div>" + new CoreParamsHelper().getBudgetNameDropDownForPrivateProcess(processEntry.budgetMasterId, processEntry.id) + "</div>"
                def bookingPeriod = "<div id ='pvtBookingPeriod_${processEntry.id}' >" + new CoreParamsHelper().getMonthDropDownForPrivateProcess(processEntry.budgetMasterId, processEntry.bookingPeriod, processEntry.id) + "</div>"
                def date = processEntry.transDate
                def bankId = processEntry.bankId
                def action = "<input class='greenBtnDataGrid' type='button' value='Update' onclick='updatePrivateProcessAmount(${processEntry.id})'>"
                obj.cell = ["bankId": bankId, "date": date, "bookingPeriod": bookingPeriod, "budgetName": budgetName, "description": description, "amount": amount, "action": action]

                quickPrivateProcessList.add(obj)
                counter++;
            }
            return quickPrivateProcessList;
        } catch (Exception ex) {
            quickPrivateProcessList = [];
            return quickPrivateProcessList;
        }
    }


    public List wrapReservationInvoiceInGrid(List<GroovyRowResult> quickEntries, int start) {
        List quickReservationInvoiceList = new ArrayList()
        def processEntry
        GridEntity obj
        String changeBooking
        try {
            int counter = start + 1
            for (int i = 0; i < quickEntries.size(); i++) {
                processEntry = quickEntries[i];
                obj = new GridEntity();
                obj.id = processEntry.id

                def amount = processEntry.amount.round(2)
                def description = processEntry.description
                def budgetName = "<div>" + new CoreParamsHelper().getBudgetNameDropDownForReservationInvoice(processEntry.budgetMasterId, processEntry.id) + "</div>"
                def bookingPeriod = "<div id ='pvtBookingPeriod_${processEntry.id}' >" + new CoreParamsHelper().getMonthDropDownForReservationInvoice(processEntry.budgetMasterId, processEntry.bookingPeriod, processEntry.id) + "</div>"
                def date = processEntry.transDate
                def bankId = processEntry.bankId
                def action = "<input class='greenBtnDataGrid' type='button' value='Update' onclick='updateReservationInvoiceAmount(${processEntry.id})'>"
                obj.cell = ["bankId": bankId, "date": date, "bookingPeriod": bookingPeriod, "budgetName": budgetName, "description": description, "amount": amount, "action": action]

                quickReservationInvoiceList.add(obj)
                counter++;
            }
            return quickReservationInvoiceList;
        } catch (Exception ex) {
            quickReservationInvoiceList = [];
            return quickReservationInvoiceList;
        }
    }


    public List wrapSearchInvoiceExpenseInGrid(List<GroovyRowResult> quickEntries, int start, fiscalYearId) {
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

                DecimalFormat twoDForm = new DecimalFormat("#.00");

                BigDecimal showtotalGlAmounta = new BigDecimal(expenseEntry.totalAmountIncVat)
                def showtotalGlAmount = twoDForm.format(showtotalGlAmounta)
                BigDecimal showtotalVata = new BigDecimal(expenseEntry.totalVat)
                def showtotalVat = twoDForm.format(showtotalVata)
                def vendorId = expenseEntry.vendorId
                //println(expenseEntry.bookingPeriod)

                def bookingPeriodFormat = (new CoreParamsHelper().monthNameShow(Integer.parseInt(expenseEntry.bookingPeriod))) + '-' + fiscalYearId;
                def budgetItemDetailsId = new CoreParamsHelper().getBudgetItemExpenseDetailsIdFromInvoiceExpense(expenseEntry.id)

                //changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${vendorId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\",\"${budgetItemDetailsId}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:deleteBooking(\"${expenseEntry.id}\",\"${vendorId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\")'><img width=\"16\" height=\"15\" alt=\"Delete\" src=\"${contextPath}/images/delete.png\"></a>"
                changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${vendorId}\",\"${expenseEntry.bookingPeriod}\",\"${expenseEntry.budget_item_expense_id}\",\"${budgetItemDetailsId}\",\"${expenseEntry.budget_vendor_id}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>"
                //obj.cell = [counter, expenseEntry.invoiceNumber, expenseEntry.budgetItemID, expenseEntry.budgetItemName, expenseEntry.InvoiceVendorName, expenseEntry.invoiceDate, expenseEntry.dueDate, showtotalGlAmount, showtotalVat, changeBooking]
                obj.cell = [expenseEntry.invoiceNumber, bookingPeriodFormat, expenseEntry.vendorName, expenseEntry.invoiceDate, expenseEntry.paymentRef, showtotalGlAmount, showtotalVat, changeBooking]

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


    public List wrapReceiptInGrid(List<GroovyRowResult> quickEntries, int start, budgetVendorId, bookingPeriod, bookInvoiceId, fiscalYearId) {
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

                BigDecimal showtotalGlAmounta = new BigDecimal(expenseEntry.totalAmountIncVat)
                def showtotalGlAmount = twoDForm.format(showtotalGlAmounta)
                BigDecimal showtotalVata = new BigDecimal(expenseEntry.totalVat)
                def showtotalVat = twoDForm.format(showtotalVata)

                def bookingPeriodFormat = (new CoreParamsHelper().monthNameShow(Integer.parseInt(expenseEntry.bookingPeriod))) + '-' + fiscalYearId;
                def budgetItemDetailsId = new CoreParamsHelper().getBudgetItemExpenseDetailsIdFromInvoiceExpense(expenseEntry.id)
                def vendorId = expenseEntry.vendorId
                def vendorShopId = expenseEntry.shopId

                changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${vendorId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\",\"${budgetItemDetailsId}\",\"${budgetVendorId}\",\"${vendorShopId}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>"
                obj.cell = ["invoiceNumber": expenseEntry.invoiceNumber, "bookingPeriod": bookingPeriodFormat, "vendorName": expenseEntry.vendorName, "invoiceDate": expenseEntry.invoiceDate, "paymentReference": expenseEntry.paymentRef, "totalGlAmount": showtotalGlAmount, "totalVat": showtotalVat, "action": changeBooking]
                quickExpenseList.add(obj)
                counter++;
            }

            return quickExpenseList;
        } catch (Exception ex) {

            quickExpenseList = [];
            return quickExpenseList;
        }
    }


    public List wrapSearchReceiptInGrid(List<GroovyRowResult> quickEntries, int start, fiscalYearId) {
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

                BigDecimal showtotalGlAmounta = new BigDecimal(expenseEntry.totalAmountIncVat)
                def showtotalGlAmount = twoDForm.format(showtotalGlAmounta)
                BigDecimal showtotalVata = new BigDecimal(expenseEntry.totalVat)
                def showtotalVat = twoDForm.format(showtotalVata)

                def bookingPeriodFormat = (new CoreParamsHelper().monthNameShow(Integer.parseInt(expenseEntry.bookingPeriod))) + '-' + fiscalYearId;
                def budgetItemDetailsId = new CoreParamsHelper().getBudgetItemExpenseDetailsIdFromInvoiceExpense(expenseEntry.id)
                def vendorId = expenseEntry.vendorId
                def vendorShopId = expenseEntry.shopId

                //changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${vendorId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\",\"${budgetItemDetailsId}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:deleteBooking(\"${expenseEntry.id}\",\"${vendorId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\")'><img width=\"16\" height=\"15\" alt=\"Delete\" src=\"${contextPath}/images/delete.png\"></a>"
//                ${expenseEntry.id}\",\"${vendorId}\",\"${bookingPeriod}\",\"${bookInvoiceId}\",\"${budgetItemDetailsId}\",\"${budgetVendorId}\",\"${vendorShopId}\
                changeBooking = "<a href='javascript:changeBooking(\"${expenseEntry.id}\",\"${vendorId}\",\"${expenseEntry.bookingPeriod}\",\"${expenseEntry.budget_item_expense_id}\",\"${budgetItemDetailsId}\",\"${expenseEntry.budget_vendor_id}\",\"${vendorShopId}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>"
                //obj.cell = [counter, expenseEntry.invoiceNumber, expenseEntry.budgetItemID, expenseEntry.budgetItemName, expenseEntry.InvoiceVendorName, expenseEntry.invoiceDate, expenseEntry.dueDate, showtotalGlAmount, showtotalVat, changeBooking]
                obj.cell = [expenseEntry.invoiceNumber, bookingPeriodFormat, expenseEntry.vendorName, expenseEntry.invoiceDate, expenseEntry.paymentRef, showtotalGlAmount, showtotalVat, changeBooking]
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

    /*
    * function list for Journal Entry Util
    * */

    public List wrapJournalEntryInGrid(List<GroovyRowResult> journalEntries, int start, liveUrl) {
        List journalEntriesList = new ArrayList()
        def journalEntry
        GridEntity obj
        String changeBooking
        try {
            int counter = start + 1
            for (int i = 0; i < journalEntries.size(); i++) {
                journalEntry = journalEntries[i];
                obj = new GridEntity();
                obj.id = journalEntry.id

                DecimalFormat twoDForm = new DecimalFormat("#.00");
                BigDecimal showtotalGlAmounta = new BigDecimal(journalEntry.totlaAmount)
                def showtotalGlAmount = twoDForm.format(showtotalGlAmounta)
                changeBooking = "<a href='javascript:changeJournalEntry(\"${journalEntry.id}\",\"${journalEntry.invoiceNumber}\",\"${liveUrl}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:copyJournalEntry(\"${journalEntry.id}\",\"${journalEntry.invoiceNumber}\",\"${liveUrl}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/copy.png\" class=\"copyJs\"></a> "
                obj.cell = ["journalNumber": journalEntry.invoiceNumber, "transactionDate": journalEntry.transactionDate, "totalAmount": showtotalGlAmount, "description": journalEntry.note, "bookingPeriod": journalEntry.bookingPeriod, "action": changeBooking]
                journalEntriesList.add(obj)
                counter++;
            }
            return journalEntriesList;
        } catch (Exception ex) {
//            log.error(ex.getMessage());
            journalEntriesList = [];
            return journalEntriesList;
        }
    }

    public List wrapChartMasterEntryInGrid(List<GroovyRowResult> chartMasterEntries, int start, liveUrl, fromAction) {
        List chartMasterEntriesList = new ArrayList()
        def chartMasterEntry
        GridEntity obj
        String changeBooking
        try {
            int counter = start + 1
            for (int i = 0; i < chartMasterEntries.size(); i++) {
                chartMasterEntry = chartMasterEntries[i];
                obj = new GridEntity();
                obj.id = chartMasterEntry.id

                if (fromAction == "list") {
                    changeBooking = "<a href='javascript:changeBooking(\"${chartMasterEntry.id}\",\"${liveUrl}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                } else {
                    changeBooking = "<a href='javascript:changeBooking(\"${chartMasterEntry.id}\",\"${liveUrl}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                }

                obj.cell = ["accountCode": chartMasterEntry.accountCode, "accountName": chartMasterEntry.accountName, "accountantName": chartMasterEntry.accountantName, "chartGroup": chartMasterEntry.groupName, "status": chartMasterEntry.showStatus, "action": changeBooking]
                chartMasterEntriesList.add(obj)

                counter++;
            }
            return chartMasterEntriesList;
        } catch (Exception ex) {
            chartMasterEntriesList = [];
            return chartMasterEntriesList;
        }
    }

    /*
   * function list for Manual Reconciliation Entry Util
   * */

    public List wrapManualReconcileEntryInGrid(List<GroovyRowResult> manualEntries, int start) {
        List manuallEntriesList = new ArrayList()
        def manualEntry
        GridEntity obj
        String changeBooking
        try {
            int counter = start + 1
            for (int i = 0; i < manualEntries.size(); i++) {
                manualEntry = manualEntries[i];
                obj = new GridEntity();
                obj.id = manualEntry.id
                DecimalFormat twoDForm = new DecimalFormat("#.00");

                BigDecimal showTotalGlAmounta = new BigDecimal(manualEntry.totalAmnt)
                def showTotalGlAmount = twoDForm.format(showTotalGlAmounta)
                BigDecimal showTotalPaidAmounta = new BigDecimal(manualEntry.paid_amount)
                def showTotalPaidAmount = twoDForm.format(showTotalPaidAmounta)

                changeBooking = "<a href='javascript:changeBooking(\"${manualEntry.id}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:deleteBooking(\"${manualEntry.id}\")'><img width=\"16\" height=\"15\" alt=\"Delete\" src=\"${contextPath}/images/delete.png\"></a>"
                obj.cell = [counter, manualEntry.invoiceNumber, showTotalGlAmount, showTotalPaidAmount, manualEntry.transactionDate, changeBooking]
                manuallEntriesList.add(obj)
                counter++;
            }
            return manuallEntriesList;
        } catch (Exception ex) {
//            log.error(ex.getMessage());
            manuallEntriesList = [];
            return manuallEntriesList;
        }
    }

    List wrapUndoReconciliateListInGrid(List<GroovyRowResult> undoReconciliateList) {

        List undoReconciliateItem = new ArrayList()
        GridEntity obj
        def undoReconciliate
        DecimalFormat twoDForm = new DecimalFormat("#.00")
        try {
            for (int i = 0; i < undoReconciliateList.size(); i++) {
                undoReconciliate = undoReconciliateList[i]
                obj = new GridEntity()
                obj.id = undoReconciliate.id

                BigDecimal showAmount = new BigDecimal(undoReconciliate.amount)
                def amount = twoDForm.format(showAmount)

                String firstCol = "<input type=\"checkbox\" value=\"${undoReconciliate.bank_payment_id}\" id=\"UndoRecon\" name=\"UndoRecon\">"
                String detailsButton = "<input type=\"button\"  value=\"+\" class=\"details-control\" onclick=\"\">"

                obj.cell = ["firstCol": firstCol, "date": undoReconciliate.date, "relationalBankAccount": undoReconciliate.by_bank_account_no, "description": undoReconciliate.description, "bankPaymentId": undoReconciliate.bank_payment_id, "amount": amount, "detailsButton": detailsButton]
                undoReconciliateItem.add(obj)
            }

            return undoReconciliateItem

        } catch (Exception ex) {

            undoReconciliateItem = []
            return undoReconciliateItem
        }

    }

    public List wrapEntryReservationBudgetItemInGrid(List<GroovyRowResult> quickEntries, int start, journalId,
                                                     bookingPeriod, isForDelete = false) {
        List quickRerservationList = new ArrayList()
        def reservationEntry
        GridEntity obj
        String changeBooking
        String tempBookingPeriod = ""
        try {
            int counter = start + 1
            for (int i = 0; i < quickEntries.size(); i++) {
                reservationEntry = quickEntries[i];

                tempBookingPeriod = ""
                def bookStartPeriod = ""
                if (reservationEntry.booking_period_month == 12) {
                    bookStartPeriod = "Dec" + "-" + reservationEntry.booking_period_year
                } else {
                    bookStartPeriod = new CoreParamsHelper().monthNameShow(reservationEntry.booking_period_month) + "-" + reservationEntry.booking_period_year
                }
                def bookEndPeriod = ""
                if (reservationEntry.booking_period_month == 12) {
                    bookEndPeriod = "Dec" + "-" + reservationEntry.booking_period_year
                } else {
                    bookEndPeriod = new CoreParamsHelper().monthNameShow(reservationEntry.booking_period_month) + "-" + reservationEntry.booking_period_year
                }

                def showtotalGlAmount = reservationEntry.totalGlAmount
                def showtotalVat = reservationEntry.totalVat

                Double showtotalGlAmountc = Double.parseDouble(showtotalGlAmount)
                Double showtotalVatc = Double.parseDouble(showtotalVat)

                def showtotalGlAmounta = String.format("%.2f", showtotalGlAmountc)
                def showtotalVata = String.format("%.2f", showtotalVatc)

                obj = new GridEntity();
                obj.id = reservationEntry.id

                if (bookingPeriod == "") {
                    tempBookingPeriod = reservationEntry.booking_period_month
                } else {
                    tempBookingPeriod = bookingPeriod;
                }

                def canDelete
                def isReconcilateArr = new BudgetViewDatabaseService().executeQuery("""SELECT COUNT(prst.id)
                                                                                       FROM reservation_budget_item as rbi
                                                                                       INNER JOIN private_reservation_spending_trans  as prst
                                                                                       ON rbi.id=prst.budget_item_id where trans_type= '5' and rbi.id = '${
                    reservationEntry.id
                }';""")

                if (isReconcilateArr[0][0] > 0) {
                    canDelete = false
                } else {
                    canDelete = true
                }


                if (isForDelete) {
                    String firstCol = "<input type=\"checkbox\" name=\"budgetItemId\" value=\"${reservationEntry.id}\">";
                    obj.cell = ["firstCol": firstCol, "budgetItemName": reservationEntry.budgetItemName, "budgetCode": reservationEntry.budgetCode, "totalGlAmount": showtotalGlAmounta, "totalVat": showtotalVata, "bookStartPeriod": bookStartPeriod]
                } else {

                    if (canDelete) {
                        changeBooking = "<a href='javascript:changeReservationBudget(\"${reservationEntry.id}\",\"${reservationEntry.budget_name_id}\",\"${journalId}\",\"${tempBookingPeriod}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:deleteReservationBudgetItem(\"${reservationEntry.id}\")'><img width=\"16\" height=\"15\" alt=\"Delete\" src=\"${contextPath}/images/delete.png\"></a>"
                    } else {
                        changeBooking = "<a href='javascript:changeReservationBudget(\"${reservationEntry.id}\",\"${reservationEntry.budget_name_id}\",\"${journalId}\",\"${tempBookingPeriod}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>"
                    }

//                    changeBooking = "<a href='javascript:changeReservationBudget(\"${reservationEntry.id}\",\"${reservationEntry.budget_name_id}\",\"${journalId}\",\"${tempBookingPeriod}\")'><img width=\"16\" height=\"15\" alt=\"Edit\" src=\"${contextPath}/images/edit.png\"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:showReservationDeleteDialog(\"${reservationEntry.id}\",\"${reservationEntry.budget_name_id}\")'><img width=\"16\" height=\"15\" alt=\"Delete\" src=\"${contextPath}/images/delete.png\"></a>"
                    obj.cell = ["budgetItemName": reservationEntry.budgetItemName, "budgetCode": reservationEntry.budgetCode, "totalGlAmount": showtotalGlAmounta, "totalVat": showtotalVata, "bookStartPeriod": bookStartPeriod, "action": changeBooking]
                }
                quickRerservationList.add(obj)
                counter++;
            }

            return quickRerservationList;
        } catch (Exception ex) {
            quickRerservationList = [];
            return quickRerservationList;
        }
    }

    public List wrapUnpaidInvoiceInGrid(List<GroovyRowResult> quickEntries,  def liveUrl) {
        List quickInvoiceList = new ArrayList()
        def invoiceEntry
        GridEntity obj
        String changeBooking
        int start = 1
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd")

        try {
            for (int i = 0; i < quickEntries.size(); i++) {
                invoiceEntry = quickEntries[i];
                def isValidIncoice = true
                if(invoiceEntry.lastReminderDate && (invoiceEntry.lastReminderDate + 14) > new Date())
                    isValidIncoice = false
                if(isValidIncoice) {
                    obj = new GridEntity()
                    DecimalFormat twoDForm = new DecimalFormat("#.00");
                    BigDecimal tobePaidAmount = new BigDecimal(invoiceEntry.tobePaidAmount)
                    def unpaidAmount = twoDForm.format(tobePaidAmount)
                    def errorMessage = ''
                    def paymentReference = invoiceEntry.paymentReference
                    def invoiceDate = invoiceEntry.invoiceDate
                    def customerName =  "<a href='${liveUrl}/customerMaster/list/${invoiceEntry.customerId}?/eid=${invoiceEntry.customerId}' target='_blank'>" + invoiceEntry.customerName + "</a>"
                    def amount = invoiceEntry.amount
                    def showTobePaidAmount = unpaidAmount

                    def reminderType = invoiceEntry.reminderNumber
                    def isEmail = invoiceEntry.email

                    def reminderSkippedStatus
                    if (invoiceEntry.isSkipped > 0)
                        reminderSkippedStatus = "<a class=\"btnYes\" id=skiped_${invoiceEntry.id} href=\"javascript:changeReminderStatus(${invoiceEntry.id})\">Yes</a>"
                    else
                        reminderSkippedStatus = "<a class=\"btnNo\" id=skiped_${invoiceEntry.id} href=\"javascript:changeReminderStatus(${invoiceEntry.id})\">No</a>"
                    def reminderChecked = "<input type=\"checkbox\" id=\"isChecked_${invoiceEntry.id}\" name=\"isChecked_${invoiceEntry.id}\" value=\"${invoiceEntry.id}_${invoiceEntry.customerId}_${invoiceEntry.reminderNumber}_${invoiceEntry.isSkipped}\">"


                    def tempReminderDate
                    Date today = new Date()

                    //Reminder Status
                    String reminderTypeString = "First"

                    if (reminderType == 1) {
                        reminderTypeString = "Second"
                        tempReminderDate = invoiceEntry.reminderDate1

                    } else if (reminderType == 2) {
                        reminderTypeString = "Third"
                        tempReminderDate = invoiceEntry.reminderDate2

                    } else if (reminderType == 3) {
                        reminderTypeString = "Final"
                        tempReminderDate = invoiceEntry.reminderDate3

                    }

                    if (tempReminderDate == null && invoiceEntry.tobePaidAmount > 0) {


                        obj.cell = ["reminderChecked": reminderChecked,
                                    "paymentReference": paymentReference,
                                    "invoiceDate": invoiceDate,
                                    "customerName": customerName,
                                    "amount": amount,
                                    "showTobePaidAmount": showTobePaidAmount,
                                    "reminderTypeString": reminderTypeString,
                                    "skipId": reminderSkippedStatus,
                                    "isEmail" : isEmail]

                        quickInvoiceList.add(obj)

                    } else if (today - tempReminderDate >= 14 && invoiceEntry.tobePaidAmount > 0) {

                        obj.cell = ["reminderChecked": reminderChecked,
                                    "paymentReference": paymentReference,
                                    "invoiceDate": invoiceDate,
                                    "customerName": customerName,
                                    "amount": amount,
                                    "showTobePaidAmount": showTobePaidAmount,
                                    "reminderTypeString": reminderTypeString,
                                    "skipId": reminderSkippedStatus,
                                    "isEmail" : isEmail]

                        quickInvoiceList.add(obj)
                    }


                    start++
                }
            }
            return quickInvoiceList;
        } catch (Exception ex) {
            quickInvoiceList = [];
            return quickInvoiceList;
        }
    }

    def getCellValue(Cell cell, def inputValueType, def formulaEvaluator = null) {
        if (cell.cellType == Cell.CELL_TYPE_NUMERIC) {
            if (inputValueType == XlsxCellType.DATE) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue()
                } else {
                    Double dblValue = cell.getNumericCellValue()
                    BigDecimal bd = new BigDecimal(dblValue.toString())
                    Long lngDate = bd.longValue()
                    return DateUtils.parseDateStrictly(lngDate.toString(), allDateFormats)
                }

            } else {
                return cell.getNumericCellValue()
            }
        } else if (cell.cellType == Cell.CELL_TYPE_STRING) {
            if (inputValueType == XlsxCellType.DATE) {
                return DateUtils.parseDateStrictly(cell.getStringCellValue(), allDateFormats)
            }
            if (inputValueType == XlsxCellType.DECIMAL) {
                def strData = cell.getStringCellValue()
                strData = strData.replaceAll("'", "")
                def dataLength = strData.length()
                if (strData.lastIndexOf(",") == dataLength - 3 || strData.lastIndexOf(",") == dataLength - 2) {
                    strData = strData.replaceAll(";", "") // replace all ; to empty
                    strData = strData.substring(0, strData.lastIndexOf(",")) + ';' + strData.substring(strData.lastIndexOf(",") + 1);
                    strData = strData.replaceAll("\\.", "") // replace all . to empty
                    strData = strData.replaceAll(";", ".") // replace all ; to empty
                } else {
                    strData = strData.replaceAll(",", "")
                }
                BigDecimal bdData = new BigDecimal(strData)
                DecimalFormat twoDForm = new DecimalFormat("#.00")
                def numCellValue = twoDForm.format(bdData)
                return numCellValue
            } else {
                return cell.getStringCellValue()
            }
        } else if (cell.cellType == Cell.CELL_TYPE_BOOLEAN) {
            return cell.getBooleanCellValue()
        } else if (cell.cellType == Cell.CELL_TYPE_BLANK) {
            if (inputValueType == XlsxCellType.DECIMAL) {
                return 0
            } else if (inputValueType == XlsxCellType.STRING) {
                return ""
            } else {
                return null
            }
        } else if (cell.cellType == Cell.CELL_TYPE_FORMULA) {
            CellValue cellValue = formulaEvaluator.evaluate(cell)
            return cellValue.getNumberValue()
        }
    }


    def checkBudget(def date, def budgetName) {

        def budgetTypeArr = new ImportInvoiceService().hasVendor(budgetName);

        def venOrCus
        def flag = false
        if (budgetTypeArr.size()) {
            venOrCus = budgetTypeArr.venOrCus
        } else {
            return flag
        }

        def dateArr = date.toString().split("-")
        def bookingPeriod = dateArr[1]
        def bookingYear = dateArr[2]

        if (venOrCus == 'VEN') {
            def sql = """SELECT bie.id FROM budget_item_expense as bie
                    INNER JOIN vendor_master as vm on  bie.vendor_id = vm.id
                    WHERE bie.booking_period_end_month ='${bookingPeriod}' AND bie.booking_period_start_month = '${
                bookingPeriod
            }'
                    AND bie.booking_period_end_year = '${bookingYear}' And bie.booking_period_start_year ='${
                bookingYear
            }'
                     and vm.vendor_name = '${budgetName}';"""
            def BudgetArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase(sql)

            if (BudgetArr.size() == 0) {
                flag = true
            }
        } else {
            def sql = """SELECT bii.id FROM budget_item_income as bii
                    INNER JOIN customer_master as cm on  bii.customer_id = cm.id
                    WHERE bii.booking_period_end_month ='${bookingPeriod}'
                    AND bii.booking_period_start_month = '${bookingPeriod}'
                    AND bii.booking_period_end_year = '${bookingYear}'
                    And bii.booking_period_start_year ='${bookingYear}'
                    and cm.customer_name = '${budgetName}';"""
            def BudgetArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase(sql)

            if (BudgetArr.size() == 0) {
                flag = true
            }
        }
        return flag;
    }

    def checkBudgetAutomatic(def date, def budgetName, BusinessCompany businessCompany) {

        def budgetTypeArr = new ImportInvoiceService().hasVendorAutomatic(budgetName, businessCompany)

        def venOrCus
        def flag = false
        if (budgetTypeArr.size()) {
            venOrCus = budgetTypeArr.venOrCus
        } else {
            return flag
        }

        def dateArr = date.toString().split("-")
        def bookingPeriod = dateArr[1]
        def bookingYear = dateArr[2]

        if (venOrCus == 'VEN') {
            def sql = """SELECT bie.id FROM budget_item_expense as bie
                    INNER JOIN vendor_master as vm on  bie.vendor_id = vm.id
                    WHERE bie.booking_period_end_month ='${bookingPeriod}' AND 
                                                bie.booking_period_start_month = '${bookingPeriod}'
                    AND bie.booking_period_end_year = '${bookingYear}' And 
                                                bie.booking_period_start_year ='${bookingYear}'
                                                and vm.vendor_name = '${budgetName}';"""

            def BudgetArr = new BudgetViewDatabaseService().executeQueryPerDatabase(sql, businessCompany)

            if (BudgetArr.size() == 0) {
                flag = true
            }
        } else {
            def sql = """SELECT bii.id FROM budget_item_income as bii
                    INNER JOIN customer_master as cm on  bii.customer_id = cm.id
                    WHERE bii.booking_period_end_month ='${bookingPeriod}'
                    AND bii.booking_period_start_month = '${bookingPeriod}'
                    AND bii.booking_period_end_year = '${bookingYear}'
                    And bii.booking_period_start_year ='${bookingYear}'
                    and cm.customer_name = '${budgetName}';"""
            def BudgetArr = new BudgetViewDatabaseService().executeQueryPerDatabase(sql, businessCompany)

            if (BudgetArr.size() == 0) {
                flag = true
            }
        }
        return flag
    }

    def duplicateRowImportFile(def vandorName, def amount, def date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date parsed = format.parse(date.toString());
        date = new java.sql.Date(parsed.getTime());
        def importArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase("""SELECT * from import_invoice
                                                WHERE vendor_or_customer = '${vandorName}'
                                                AND total = '${amount}' AND invoice_date = '${date}'""")
        def res = false
        if (importArr.size() > 1) {
            res = true
        }
        return res
    }

    def duplicateRowImportFileAutomatic(def vandorName, def amount, def date, BusinessCompany businessCompany) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date parsed = format.parse(date.toString())
        date = new java.sql.Date(parsed.getTime())
        def importArr = new BudgetViewDatabaseService().executeQueryPerDatabase("""SELECT * from import_invoice
                                                WHERE vendor_or_customer = '${vandorName}'
                                                AND total = '${amount}' AND invoice_date = '${date}'""", businessCompany)
        def res = false
        if (importArr.size() > 1) {
            res = true
        }
        return res
    }

    def getRowImportFile(def vandorName, def amount, def date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy")
        Date parsed = format.parse(date.toString())
        date = new java.sql.Date(parsed.getTime())

        def companyConfig = new BudgetViewDatabaseService().getConnectionInformation()
        def db = Sql.newInstance(companyConfig.serverUrl + companyConfig.dbName, companyConfig.dbUser, companyConfig.dbPassword, companyConfig.driverName)
        String importInvoice = """SELECT
                                    id,
                                    budget_name AS budgetName,
                                    DATE_FORMAT(invoice_date,'%d-%m-%Y') AS invoiceDate,
                                    vendor_or_customer AS vendorOrCustomer,
                                    total,
                                    subtotal,
                                    vat_low as vatLow,
                                    vat_high as vatHigh,
                                    vat_declaration as totalVat,
                                    description,
                                    invoice_number as invoiceNumber,
                                    pdf_link_url as pdfLink,
                                    iban,
                                    vat_id,
                                    is_excel_import,
                                    label,
                                    wezp_receipt_id as wezpReceiptId,
                                    aws_details as awsDetails,
                                    sub_category as subCategory
                                    FROM
                                    import_invoice WHERE vendor_or_customer = '${vandorName}'
                                    AND total = '${amount}' AND invoice_date = '${date}'"""

        List<GroovyRowResult> importInvoiceList = db.rows(importInvoice)

        db.close()

        return importInvoiceList

    }

    def isBookingYearIsVaild(def date) {
        def dateArr = date.toString().split("-")
        def bookingYear = dateArr[2]
        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)
        def activeBookingYear = FiscalYearInfo[0][4]
        def flag = false
        if (bookingYear != activeBookingYear) {
            flag = true
        }
        return flag;
    }

    def isBookingYearIsVaildAutomatic(def date, BusinessCompany businessCompany) {
        def dateArr = date.toString().split("-")
        def bookingYear = dateArr[2]
        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYearAutomatic(businessCompany)
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformationAutomatic(ActiveFiscalYear, businessCompany)
        def activeBookingYear = FiscalYearInfo[0][4]
        def flag = false
        if (bookingYear != activeBookingYear) {
            flag = true
        }
        return flag
    }

    def isInvoiceAlreadyProcess(def vendorName, def amount, def date, def budgetName) {

        def budgetTypeArr = new ImportInvoiceService().hasVendor(budgetName);
        def venOrCus
        def flag = false
        if (budgetTypeArr.size()) {
            venOrCus = budgetTypeArr.venOrCus
        } else {
            return flag
        }
        def dateArr = date.split("-");


        if (venOrCus == 'VEN') {
            def sql111 = """SELECT ie.id FROM invoice_expense as ie
                    INNER JOIN vendor_master as vm on ie.vendor_id = vm.id
                    WHERE ie.total_gl_amount = '${amount}'
                    AND DAY(ie.trans_date) = '${dateArr[0]}' AND
                    MONTH(ie.trans_date) = '${dateArr[1]}'
                    AND YEAR(ie.trans_date) = '${dateArr[2]}'
                    and vm.vendor_name = '${vendorName}';"""

            def invoiceArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase(sql111)
            if (invoiceArr.size()) {
                flag = true
            }
        } else {
            def customerMasterSql = """SELECT ii.id from invoice_income as ii
                                    INNER JOIN customer_master as cm on cm.id = ii.customer_id
                                    where ii.total_gl_amount ='${amount}'
                                    AND DAY(ii.trans_date) = '${dateArr[0]}' AND
                                    MONTH(ii.trans_date) = '${dateArr[1]}'
                                    AND YEAR(ii.trans_date) = '${dateArr[2]}'
                                    and cm.customer_name = '${vendorName}';"""

            def invoiceArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase(customerMasterSql)
            if (invoiceArr.size()) {
                flag = true
            }
        }
        return flag
    }

    def isInvoiceAlreadyProcessAutomatic(def vendorName, def amount, def date, def budgetName, BusinessCompany businessCompany) {

        def budgetTypeArr = new ImportInvoiceService().hasVendorAutomatic(budgetName, businessCompany)
        def venOrCus
        def flag = false
        if (budgetTypeArr.size()) {
            venOrCus = budgetTypeArr.venOrCus
        } else {
            return flag
        }
        def dateArr = date.split("-");


        if (venOrCus == 'VEN') {
            def sql111 = """SELECT ie.id FROM invoice_expense as ie
                    INNER JOIN vendor_master as vm on ie.vendor_id = vm.id
                    WHERE ie.total_gl_amount = '${amount}'
                    AND DAY(ie.trans_date) = '${dateArr[0]}' AND
                    MONTH(ie.trans_date) = '${dateArr[1]}'
                    AND YEAR(ie.trans_date) = '${dateArr[2]}'
                    and vm.vendor_name = '${vendorName}';"""

            def invoiceArr = new BudgetViewDatabaseService().executeQueryPerDatabase(sql111, businessCompany)
            if (invoiceArr.size()) {
                flag = true
            }
        } else {
            def customerMasterSql = """SELECT ii.id from invoice_income as ii
                                    INNER JOIN customer_master as cm on cm.id = ii.customer_id
                                    where ii.total_gl_amount ='${amount}'
                                    AND DAY(ii.trans_date) = '${dateArr[0]}' AND
                                    MONTH(ii.trans_date) = '${dateArr[1]}'
                                    AND YEAR(ii.trans_date) = '${dateArr[2]}'
                                    and cm.customer_name = '${vendorName}';"""

            def invoiceArr = new BudgetViewDatabaseService().executeQueryPerDatabase(customerMasterSql, businessCompany)
            if (invoiceArr.size()) {
                flag = true
            }
        }
        return flag
    }

    def isNoteAvailable(String description) {

        if (description != null && !description.isEmpty()) {
            return true
        }

        return false
    }

    def isCategoryAutoWithVatZero(String categoryName, def  totalVat, def vatLow, def vatHigh) {
        boolean flag = false

        if(categoryName.equals("Auto")){
            if(totalVat == 0){
                if(vatLow!= 0 || vatHigh!=0)
                    flag = true
            }
        }

        return flag
    }

    def isVatMatch(def totalAmountWithOutVat, def lowVat, def highVat, def totalVat) {
        def flag = true
        if (totalVat == 0) {
            flag = false

        } else if (lowVat != 0 || highVat != 0) {
            def vatFor21 = ((totalAmountWithOutVat * 21) / 100).round(2)
            if (Math.abs(vatFor21 - highVat) <= 0.05) {
                flag = false
            } else {
                def vatFor06 = ((totalAmountWithOutVat * 6) / 100).round(2)
                def vatFor09 = ((totalAmountWithOutVat * 9) / 100).round(2)
                if (Math.abs(vatFor06 - lowVat) <= 0.05 || Math.abs(vatFor09 - lowVat) <= 0.05) {
                    flag = false
                }
            }
        } else {
            flag = false
        }
        return flag
    }


    List wrapImportInvoiceInGrid(List<GroovyRowResult> quickEntries, int start) {
        List quickInvoiceList = new ArrayList()
        def invoiceEntry
        GridEntity obj
        StringBuilder changeBooking = new StringBuilder()
        DecimalFormat twoDForm = new DecimalFormat("#.00")
        int listSize = quickEntries.size()

        try {
            for (int i = 0; i < listSize; i++) {
                invoiceEntry = quickEntries[i]
                obj = new GridEntity()
                obj.id = invoiceEntry.id

                BigDecimal showtotalAmount = new BigDecimal(invoiceEntry.total)
                BigDecimal showtotalVata = new BigDecimal(invoiceEntry.totalVat)

                def flag = false
                def errorMessage = ''
                def vatFlag
                def duplicateFlag
                def noteFlag
                def categoryAutoFlag

                def invoiceNumber = invoiceEntry.invoiceNumber
                def budgetName = invoiceEntry.budgetName
                def vendorOrCustomer = invoiceEntry.vendorOrCustomer
                def invoiceDate = invoiceEntry.invoiceDate
                def totalAmount = twoDForm.format(showtotalAmount)
                def totalVat = twoDForm.format(showtotalVata)
                def checkDuplicateRowImportInvoice = duplicateRowImportFile(vendorOrCustomer, totalAmount, invoiceDate)
                def checkBudget = checkBudget(invoiceDate, budgetName)
                def isVatMatch = isVatMatch(invoiceEntry.subtotal, invoiceEntry.vatLow, invoiceEntry.vatHigh, invoiceEntry.totalVat)
                def isInvoiceProcess = isInvoiceAlreadyProcess(vendorOrCustomer,
                        (invoiceEntry.totalVat==0)?invoiceEntry.total:invoiceEntry.subtotal,invoiceDate,budgetName)
                def isBookingYearIsVaild = isBookingYearIsVaild(invoiceDate)
                def descriptionAsNote = isNoteAvailable(invoiceEntry.description)
                def isCategoryAutoWithVatZero = isCategoryAutoWithVatZero(invoiceEntry.budgetName, invoiceEntry.totalVat,
                        invoiceEntry.vatLow, invoiceEntry.vatHigh)
                def pdfLink = ""
                try {
                    JSONObject jsonObject = new JSONObject(invoiceEntry.awsDetails)
                    pdfLink = "<a href='javascript:downloadWezpPdf(\"${invoiceEntry.pdfLink}\",\"${jsonObject.aws_bucket}\",\"${jsonObject.aws_key}\")' >" +
                            "<img title='${invoiceEntry.wezpReceiptid}' width='16' height='15'src='${contextPath}/images/file_download.png' /></a>"
                } catch (Exception err) {
                    pdfLink = "<a href='javascript:downloadWezpPdf(\"${invoiceEntry.pdfLink}\",null,null)' >" +
                            "<img title='${invoiceEntry.wezpReceiptid}' width='16' height='15'src='${contextPath}/file_download.png' /></a>"
                }

                if(isInvoiceProcess){
                    flag = true
                    errorMessage = 'Invoice already Processed.'
                } else if (checkDuplicateRowImportInvoice) {
                    flag = true
                    errorMessage = 'Duplicate row in Import file'
                    duplicateFlag = 3 // 3 is default value of duplicateFlag
                } else if (isBookingYearIsVaild) {
                    flag = true
                    errorMessage = 'Date of invoice not in active bookyear'
                } else if (checkBudget) {
                    flag = true
                    errorMessage = 'No budget for this booking period'
                } else if (isVatMatch){
                    flag = true
                    vatFlag = 2 // 2 is default value of vatFlag
                    errorMessage = 'Vat not matching (not 21% or 9% or 6%)'
                } else if (descriptionAsNote){
                    flag = true
                    noteFlag = 4 // 4 is default value of noteFlag
                    errorMessage = "Note: " + invoiceEntry.description
                } else if (isCategoryAutoWithVatZero) {
                    flag = true
                    categoryAutoFlag = 5 // 5 is default value of auo Category with Vat 0; special case
                    errorMessage = 'Category Auto with Vat-Declaration = 0'
                }

                changeBooking.append("<a href='${contextPath}/importInvoice/importInvoice?editId=${invoiceEntry.id}'><img width='16' height='15' alt='Edit' src='${contextPath}/images/edit.png'></a>")
                        .append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
                        .append("<a href='javascript:deleteImportInvoice(${invoiceEntry.id}, \"${invoiceEntry.wezpReceiptId}\")'><img width='16' height='15' alt='Delete' src='${contextPath}/images/delete.png'></a>")

                if (flag) {
                    changeBooking.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<abbr title='${errorMessage}'><img width='16' height='17' alt='Delete' src='${contextPath}/images/skin/information.png'></abbr>")
                }

                obj.cell = ["invoiceNumber": invoiceNumber, "budgetName": budgetName, "vendorOrCustomer": vendorOrCustomer,
                            "invoiceDate": invoiceDate, "pdfLink": pdfLink, "totalAmount": totalAmount, "totalVat": totalVat, "action": changeBooking.toString(), "flag": flag]

                changeBooking.setLength(0)

                //warning setting when there is a flag
                if(vatFlag == 2 ) {
                    obj.cell.flag = vatFlag
                }else if(duplicateFlag == 3) {
                    obj.cell.flag = duplicateFlag
                } else if(noteFlag == 4) {
                    obj.cell.flag = noteFlag
                } else if(categoryAutoFlag == 5) {
                    obj.cell.flag = categoryAutoFlag
                }

                quickInvoiceList.add(obj)
            }

        } catch (Exception ex) {
            quickInvoiceList = []
            return quickInvoiceList
        }

        return quickInvoiceList
    }

}
