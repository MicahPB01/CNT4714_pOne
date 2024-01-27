package com.nile.cnt4714_pone.Utilities;

import javafx.scene.control.Label;
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

    public static Label findLabel(int itemCounter, List<Label> itemIDLabel)   {

        return switch (itemCounter)   {
            case 1 -> itemIDLabel.get(0);
            case 2 -> itemIDLabel.get(1);
            case 3 -> itemIDLabel.get(2);
            case 4 -> itemIDLabel.get(3);
            default -> null;
        };


    }



}
