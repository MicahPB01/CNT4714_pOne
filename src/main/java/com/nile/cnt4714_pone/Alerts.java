package com.nile.cnt4714_pone;

import javafx.scene.control.Alert;

public class Alerts {

    public static void oOS()   {
        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Out of Stock");
        alert.setContentText("This item is out of stock.");

        alert.showAndWait();
    }

    public static void notFound(String itemID)   {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Not Found");
        alert.setContentText("Item " + itemID + " is not in file.");

        alert.showAndWait();
    }

    public static void cart(String items)   {


        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Shopping Cart Status");



        alert.setContentText(items);

        alert.showAndWait();

    }

    public static void checkOut(String invoice)   {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Final Invoice");
        alert.setContentText(invoice);

        alert.showAndWait();



    }
}
