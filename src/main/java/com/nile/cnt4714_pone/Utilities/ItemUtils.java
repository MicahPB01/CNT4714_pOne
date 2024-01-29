package com.nile.cnt4714_pone.Utilities;

import com.nile.cnt4714_pone.Models.Item;
import javafx.scene.control.TextField;

import java.util.List;

public class ItemUtils {

    public static int findDiscount(String quantity)   {
        int intQ = Integer.parseInt(quantity);

        return switch (intQ) {
            case 1, 2, 3, 4 -> 0;
            case 5, 6, 7, 8, 9 -> 10;
            case 10, 11, 12, 13, 14 -> 15;
            default -> 20;
        };

    }

    public static TextField findTextField(int itemCounter, List<TextField> itemNumberText)   {
        System.out.println(itemNumberText);

        return switch (itemCounter)   {
            case 2 -> itemNumberText.get(0);
            case 3 -> itemNumberText.get(1);
            case 4 -> itemNumberText.get(2);
            case 5 -> itemNumberText.get(3);
            default -> null;
        };
    }

    public static String buildString(List<Item> addedItems)   {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < addedItems.size(); i++)   {
            stringBuilder.append(i + 1).append(". ").append(addedItems.get(i).getItemID()).append(" ").append(addedItems.get(i).getDescription()).append(" ")
                    .append(addedItems.get(i).getUnitPrice()).append(" ").append(addedItems.get(i).getRequestedQuantity()).append(" ")
                    .append(ItemUtils.findDiscount(String.valueOf(addedItems.get(i).getRequestedQuantity()))).append(" ")
                    .append(addedItems.get(i).getTotalPrice()).append("\n");
        }

        return String.valueOf(stringBuilder);

    }

    public static double findPreviousSub(String subtotalField)   {
        double previousSub;

        if(subtotalField.isEmpty())   {
            previousSub = 0;
        }
        else   {
            String previousStringSub = subtotalField.replaceAll("\\$", "");
            previousSub = Double.parseDouble(previousStringSub);
        }

        return previousSub;
    }



}
