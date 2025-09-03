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

    private List<ItemWithPrice> searchUnitPrice(List<ItemQuantity> items) {
        List<Item> allItems = ItemsLoader.loadAllItems();
        Map<String, Item> itemMap = new LinkedHashMap<>();
        for (Item item : allItems) {
            itemMap.put(item.getBarcode(), item);
        }
        List<ItemWithPrice> result = new ArrayList<>();
        for (ItemQuantity iq : items) {
            Item item = itemMap.get(iq.barcode);
            int price = (item != null) ? item.getPrice() : 0;
            result.add(new ItemWithPrice(iq.barcode, iq.name, iq.quantity, price));
        }
        return result;
    }

    private List<ItemWithPrice> lookupItem(List<String> barcodes) {
        List<Item> allItems = ItemsLoader.loadAllItems();

        Map<String, Item> itemMap = new LinkedHashMap<>();
        for (Item item : allItems) {
            itemMap.put(item.getBarcode(), item);
        }

        List<ItemQuantity> items = new ArrayList<>();
        for (String bc : barcodes) {
            Item item = itemMap.get(bc);
            String name = (item != null) ? item.getName() : "Unknown";
            items.add(new ItemQuantity(bc, name, 1));
        }

        List<ItemQuantity> counted = countQuantity(items);
//        List<ItemQuantity> sorted = sortOccurrence(counted);
        return searchUnitPrice(counted);
    }

    private List<ReceiptItem> calculateSubTotal(List<ItemWithPrice> items) {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        for (ItemWithPrice iwp : items) {
            int subTotal = iwp.quantity * iwp.unitPrice;
            ReceiptItem receiptItem = new ReceiptItem(iwp.barcode, iwp.name, iwp.quantity, iwp.unitPrice, subTotal);
            receiptItems.add(receiptItem);
        }
        return receiptItems;
    }

    private int calculateTotal(List<ReceiptItem> items) {
        int total = 0;
        for (ReceiptItem item : items) {
            total += item.subTotal;
        }
        return total;
    }

    private AmountResult calculateAmount(List<ItemWithPrice> withPrices) {
        List<ReceiptItem> receiptItems = calculateSubTotal(withPrices);
        int total = calculateTotal(receiptItems);
        return new AmountResult(receiptItems, total);
    }

    private List<String> generateItemReceipt(List<ReceiptItem> items) {
        List<String> receipts = new ArrayList<>();
        for (ReceiptItem item : items) {
            String line = "Name: " + item.name + ", Quantity: " + item.quantity +
                    ", Unit price: " + item.unitPrice + " (yuan), Subtotal: " + item.subTotal + " (yuan)";
            receipts.add(line);
        }
        return receipts;
    }

    private String generateReceipt(List<String> itemReceipts, int total) {
        StringBuilder sb = new StringBuilder();
        sb.append("***<store earning no money>Receipt***\n");
        for (String line : itemReceipts) {
            sb.append(line).append("\n");
        }
        sb.append("----------------------\n");
        sb.append(String.format("Total: %d (yuan)\n", total));
        sb.append("**********************");
        return sb.toString();
    }
    
    public String printReceipt(List<String> barcodes) {
        return null;
    }
}
