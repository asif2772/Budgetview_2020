package bv

class ReceiptEntry {
//    Long budgetItemId;
    String budgetItemId;
    Integer bookingPeriodId
    String invoiceId;
    String invoiceNumber;
    Long vendorId;
    Long paidWithId;
    Date transDate;
    Boolean isReverse;
    Long reverseByInvoiceId;
    Date createdDate = new Date();
    String comments;
    Double totalGlAmount;
    Double totalVat;

    static constraints = {
        invoiceId(nullable: true)
        comments(nullable: true)
        vendorId(nullable: true)
        isReverse(nullable: true)
        reverseByInvoiceId(nullable: true)
        totalVat(scale: 2)
    }
}
