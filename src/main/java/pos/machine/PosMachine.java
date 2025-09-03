package pos.machine;

import java.util.List;

public class PosMachine {
        static class ItemQuantity {
        String barcode;
        String name;
        int quantity;
        ItemQuantity(String barcode, String name, int quantity) {
            this.barcode = barcode;
            this.name = name;
            this.quantity = quantity;
        }
    }
    static class ItemWithPrice {
        String barcode;
        String name;
        int quantity;
        int unitPrice;
        ItemWithPrice(String barcode, String name, int quantity, int unitPrice) {
            this.barcode = barcode;
            this.name = name;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }
    }
    static class ReceiptItem {
        String barcode;
        String name;
        int quantity;
        int unitPrice;
        int subTotal;
        ReceiptItem(String barcode, String name, int quantity, int unitPrice, int subTotal) {
            this.barcode = barcode;
            this.name = name;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.subTotal = subTotal;
        }
    }
    private static class AmountResult {
    List<ReceiptItem> receiptItems;
    int total;
    AmountResult(List<ReceiptItem> receiptItems, int total) {
        this.receiptItems = receiptItems;
        this.total = total;
    }
    }
    public String printReceipt(List<String> barcodes) {
        return null;
    }
}
