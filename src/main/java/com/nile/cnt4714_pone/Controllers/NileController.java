package com.nile.cnt4714_pone.Controllers;

import com.nile.cnt4714_pone.Alerts;
import com.nile.cnt4714_pone.Models.Item;
import com.nile.cnt4714_pone.Utilities.InputUtils;
import com.nile.cnt4714_pone.Utilities.ItemUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class NileController {
    private final static double TAX = 0.06;

    public Label quantityIDLabel;
    public Label detailsLabel;
    public Label subtotalLabel;
    public Label itemIDLabel;
    public TextField subtotalField;
    public TextField quantityIDField;

    public TextField itemIDField;

    public TextField detailsField;
    public HashMap<String, Item> allItems;
    public Button exitButton;
    public Button emptyButton;
    public Button viewCartButton;
    public Button findItemButton;
    public Button checkButton;
    public Button addButton;
    public int itemCounter;
    public Label shoppingLabel;
    public TextField itemOneText;
    public TextField itemTwoText;
    public TextField itemThreeText;
    public TextField itemFourText;
    public TextField itemFiveText;

    public List<TextField> itemNumberText = new ArrayList<>();
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private Item workingItem;

    public List<Item> addedItems = new ArrayList<>();


    public void initialize()   {
        String path = "src/main/resources/com/nile/cnt4714_pone/inventory.csv";
        allItems = InputUtils.getItems(path);

        viewCartButton.setDisable(true);
        addButton.setDisable(true);
        checkButton.setDisable(true);

        itemCounter = 1;

        itemNumberText.add(itemOneText);
        itemNumberText.add(itemTwoText);
        itemNumberText.add(itemThreeText);
        itemNumberText.add(itemFourText);
        itemNumberText.add(itemFiveText);




    }

    @FXML
    public void findItem(ActionEvent actionEvent) {

        String itemID = itemIDField.getText();
        String quantity = quantityIDField.getText();
        int discount = ItemUtils.findDiscount(quantity);
        Item currentItem = null;




        if(allItems.containsKey(itemID))   {
            currentItem = allItems.get(itemID);
        }

        if(currentItem == null)   {
            Alerts.notFound(itemID);
            return;
        }


        if(currentItem.getInStock().equals("false"))   {
            Alerts.oOS();
            return;
        }

        currentItem.setRequestedQuantity(Integer.parseInt(quantity));

        String itemTotal = decimalFormat.format(currentItem.getUnitPrice() * currentItem.getRequestedQuantity());
        currentItem.setTotalPrice(Double.parseDouble(itemTotal));


        detailsField.setText(currentItem.getItemID() + " " + currentItem.getDescription() + " " + currentItem.getUnitPrice() + " " + currentItem.getRequestedQuantity() + " " + discount + "% $" + itemTotal);


        addButton.setDisable(false);
        findItemButton.setDisable(true);

        workingItem = currentItem;



    }


    public void addItemToCart(ActionEvent actionEvent) {
        shoppingLabel.setText("Your Current Shopping Cart With " + itemCounter + " Item(s)");
        subtotalLabel.setText("Current Subtotal for " + itemCounter + " item(s)");
        itemCounter++;



        ItemUtils.findTextField(itemCounter, itemNumberText).setText("Item " + itemCounter + " - SKU: " + workingItem.getItemID() + ", Desc: " + workingItem.getDescription()
                + ", Price Ea. $" + workingItem.getUnitPrice() + ", Qty: " + quantityIDField.getText() + ", Total: $" + workingItem.getTotalPrice());

        itemIDField.clear();
        quantityIDField.clear();

        itemIDLabel.setText("Enter item ID for Item #" + itemCounter);
        quantityIDLabel.setText("Enter quantity for Item #" + itemCounter);

        double previousSub = ItemUtils.findPreviousSub(subtotalField.getText());




        String subTotal = decimalFormat.format(previousSub + workingItem.getTotalPrice());

        subtotalField.setText("$" + subTotal);

        findItemButton.setText("Find Item #" + itemCounter);
        addButton.setText("Add Item #" + itemCounter + " To Cart");

        findItemButton.setDisable(false);
        addButton.setDisable(true);

        viewCartButton.setDisable(false);
        checkButton.setDisable(false);

        addedItems.add(workingItem);



    }

    public void viewCart(ActionEvent actionEvent) {


        Alerts.cart(ItemUtils.buildString(addedItems));

    }


    public void checkOut(ActionEvent actionEvent) {
        StringBuilder stringBuilder = new StringBuilder();
        Date date = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE d, yyyy, h:mma z", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("EST"));

        String formattedDate = simpleDateFormat.format(date);
        double previousSub = ItemUtils.findPreviousSub(subtotalField.getText());
        double taxAmount = Double.parseDouble(decimalFormat.format(previousSub * TAX));
        String total = decimalFormat.format(previousSub + workingItem.getTotalPrice() + taxAmount);

        stringBuilder.append("Date: ").append(formattedDate).append("\n\n").append("Number of line items: ").append(itemCounter).append("\n\n").append("$")
                .append(ItemUtils.buildString(addedItems)).append("\n\n").append("Order subtotal: ").append(subtotalField.getText()).append("\n\n")
                .append("Tax rate: 6%\n\n").append("Tax Amount: $").append(taxAmount).append("ORDER TOTAL: $").append(total).append("Thanks for shopping at Nile Dot Com!");

        Alerts.checkOut(String.valueOf(stringBuilder));

    }
}