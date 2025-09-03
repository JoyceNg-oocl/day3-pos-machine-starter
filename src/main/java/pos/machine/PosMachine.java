package pos.machine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private List<ItemQuantity> countQuantity(List<ItemQuantity> items) {
        Map<String, ItemQuantity> map = new LinkedHashMap<>();
        for (ItemQuantity iq : items) {
            if (map.containsKey(iq.barcode)) {
                map.get(iq.barcode).quantity++;
            } else {
                map.put(iq.barcode, new ItemQuantity(iq.barcode, iq.name, 1));
            }
        }
        return new ArrayList<>(map.values());
    }

    private List<ItemQuantity> sortOccurrence(List<ItemQuantity> items) {
        List<ItemQuantity> sortedList = new ArrayList<>(items);
        sortedList.sort((a, b) -> b.quantity - a.quantity);
        return sortedList;
    }

    public String printReceipt(List<String> barcodes) {
        return null;
    }
}
