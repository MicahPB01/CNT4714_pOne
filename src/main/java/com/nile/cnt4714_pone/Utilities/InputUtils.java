package com.nile.cnt4714_pone.Utilities;

import com.nile.cnt4714_pone.Models.Item;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InputUtils {

    public static HashMap<String, Item> getItems(String path) {

        List<String> allLines = new ArrayList<>();

        try {
            allLines = Files.readAllLines(Path.of(path));
        }
        catch (IOException e)   {
            e.printStackTrace();
        }

        HashMap<String, Item> allItems = new HashMap();


        for(int i = 0; i < allLines.size(); i++)   {
            String currentLine = allLines.get(i);
            Item currentItem = getItem(currentLine);
            allItems.put(currentItem.getItemID(), currentItem);
        }

        return allItems;
    }

    private static Item getItem(String currentLine)   {

        String[] properties = currentLine.split(",");

        for(int i = 0; i < properties.length; i++)   {
            properties[i] = properties[i].trim();
        }

        String itemID = properties[0];
        String description = properties[1];
        String inStock = properties[2];
        int quantity = Integer.parseInt(properties[3]);
        double unitPrice = Double.parseDouble(properties[4]);


        return new Item(itemID, description, inStock, quantity, unitPrice);

    }



}
