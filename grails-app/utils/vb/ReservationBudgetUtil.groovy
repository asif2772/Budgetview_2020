package vb

import groovy.sql.GroovyRowResult

import java.text.DecimalFormat

class ReservationBudgetUtil {

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


}
