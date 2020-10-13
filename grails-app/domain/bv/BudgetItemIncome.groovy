package bv

class BudgetItemIncome {

    String  budgetId
    Integer customerId
    Integer bookingPeriodStartMonth
    Integer bookingPeriodStartYear
    Integer bookingPeriodEndMonth
    Integer bookingPeriodEndYear
    String  bookingPeriodType
    Integer fiscalId
    Integer paymentTermsId
    String  invoiceFrequency
    Integer momentOfSendingInvoice
    Double  totalVat
    Double  totalGlAmount
    Integer status
    Date    createdDate
    Date    updatedDate

    static constraints = {

    }

}
