/* Name: Micah Puccio-Ball
 Course: CNT 4714 – Spring 2024
 Assignment title: Project 1 – An Event-driven Enterprise Simulation
 Date: Friday, January 26, 2024
*/
package com.nile.cnt4714_pone.Utilities;

import com.nile.cnt4714_pone.Item;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

public class ItemUtils {



    public static int findDiscount(String quantity) {
        int intQ = Integer.parseInt(quantity);
        return switch (intQ) {
            case 1, 2, 3, 4 -> 0;
            case 5, 6, 7, 8, 9 -> 10;
            case 10, 11, 12, 13, 14 -> 15;
            default -> 20;
        };
    }

    public static TextField findTextField(int itemCounter, List<TextField> itemNumberText) {
        return itemCounter > 0 && itemCounter < itemNumberText.size() ? itemNumberText.get(itemCounter - 1) : null;
    }

    public static String buildCart(List<Item> addedItems) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < addedItems.size(); i++) {
            Item item = addedItems.get(i);
            stringBuilder.append(i + 1).append(". ")
                    .append(item.getItemID()).append(" ")
                    .append(item.getDescription()).append(" ")
                    .append(item.getUnitPrice()).append(" ")
                    .append(item.getRequestedQuantity()).append(" ")
                    .append(findDiscount(String.valueOf(item.getRequestedQuantity()))).append(" $")
                    .append(item.getTotalPrice()).append("\n");
        }
        return stringBuilder.toString();
    }

    public static String buildTransactionList(Item item, String date)   {

        return ", " + item.getItemID() + ", " +
                item.getDescription() + ", " +
                item.getUnitPrice() + ", " +
                item.getRequestedQuantity() + ", " +
                findDiscount(String.valueOf(item.getRequestedQuantity())) + ", $" +
                item.getTotalPrice() + ", " +
                date + "\n";

    }



    public static void addTransaction (String transaction) throws IOException {

        Files.write(Paths.get("transactions.csv"), Collections.singleton(transaction), StandardCharsets.UTF_8, StandardOpenOption.APPEND);

    }

    public static Item updateWorkingItem(Item item, int quantity) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        item.setRequestedQuantity(quantity);
        double itemTotal = item.getUnitPrice() * quantity;
        item.setTotalPrice(Double.parseDouble(decimalFormat.format(itemTotal)));
        return item;
    }

    public static String formatCartItem(int itemCounter, Item workingItem, int quantity) {
        return "Item " + itemCounter + " - SKU: " + workingItem.getItemID() + ", Desc: " + workingItem.getDescription()
                + ", Price Ea. $" + workingItem.getUnitPrice() + ", Qty: " + quantity + ", Total: $" + workingItem.getTotalPrice();
    }



}
