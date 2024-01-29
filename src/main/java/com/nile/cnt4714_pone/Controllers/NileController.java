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

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class NileController {
    private final static double TAX = 0.06;
    private final static int MAX_ITEMS = 5;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private final SimpleDateFormat transactionDate = new SimpleDateFormat("MMddyyyyHHmmss", Locale.US);
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE d, yyyy, h:mm:ss a z", Locale.US);
    @FXML
    public Label quantityIDLabel, detailsLabel, subtotalLabel, itemIDLabel, shoppingLabel;
    public TextField subtotalField, quantityIDField, itemIDField, detailsField;
    public TextField itemOneText, itemTwoText, itemThreeText, itemFourText, itemFiveText;
    public Button exitButton, emptyButton, viewCartButton, findItemButton, checkButton, addButton;

    private int itemCounter;
    private Item workingItem;
    private HashMap<String, Item> allItems;
    private final List<TextField> itemNumberText = new ArrayList<>();
    private List<Item> addedItems = new ArrayList<>();

    public void initialize() {
        String path = "src/main/resources/com/nile/cnt4714_pone/inventory.csv";
        allItems = InputUtils.getItems(path);
        setupButtons();
        itemCounter = 1;
        itemNumberText.addAll(Arrays.asList(itemOneText, itemTwoText, itemThreeText, itemFourText, itemFiveText));
    }


    @FXML
    public void findItem(ActionEvent actionEvent) {
        String itemID = itemIDField.getText();
        Item currentItem = allItems.getOrDefault(itemID, null);

        if (currentItem == null || !currentItem.getInStock().equals("true")) {
            handleItemNotFound();
            return;
        }

        int quantity = Integer.parseInt(quantityIDField.getText());
        if (currentItem.getQuantity() < quantity) {
            Alerts.iStock(String.valueOf(currentItem.getQuantity()));
            clearInputFields();
            return;
        }

        workingItem = ItemUtils.updateWorkingItem(currentItem, quantity);
        updateDetailsField(currentItem, quantity);
        enableAddButton();
    }

    @FXML
    public void viewCart(ActionEvent actionEvent) {
        Alerts.cart(ItemUtils.buildCart(addedItems));
    }

    @FXML
    public void checkOut(ActionEvent actionEvent) throws IOException {
        StringBuilder receipt = buildReceipt();
        Alerts.checkOut(receipt.toString());
        processTransaction();
    }

    @FXML
    public void addItemToCart(ActionEvent actionEvent) {
        if (itemCounter > MAX_ITEMS) {
            disableItemInput();
            return;
        }

        updateShoppingCartDisplay();
        addItemTextField();
        resetInputFields();

        updateSubtotal();

        prepareForNextItem();
        addedItems.add(workingItem);
    }

    private void setupButtons() {
        viewCartButton.setDisable(true);
        addButton.setDisable(true);
        checkButton.setDisable(true);
    }

    private void handleItemNotFound() {
        Alerts.oOS();
        clearInputFields();
    }

    private void clearInputFields() {
        itemIDField.clear();
        quantityIDField.clear();
    }



    private void updateDetailsField(Item item, int quantity) {
        int discount = ItemUtils.findDiscount(String.valueOf(quantity));
        String itemTotal = decimalFormat.format(item.getUnitPrice() * quantity);
        detailsField.setText(item.getItemID() + " " + item.getDescription() + " " +
                item.getUnitPrice() + " " + quantity + " " + discount + "% $" + itemTotal);
    }

    private void enableAddButton() {
        addButton.setDisable(false);
        findItemButton.setDisable(true);
    }

    private void disableItemInput() {
        itemIDField.setDisable(true);
        quantityIDField.setDisable(true);
        findItemButton.setDisable(true);
        addButton.setDisable(true);
    }

    private void updateShoppingCartDisplay() {
        shoppingLabel.setText("Your Current Shopping Cart With " + itemCounter + " Item(s)");
        subtotalLabel.setText("Current Subtotal for " + itemCounter + " item(s)");
    }

    private void addItemTextField() {
        TextField currentTextField = ItemUtils.findTextField(itemCounter, itemNumberText);
        int quantity = Integer.parseInt(quantityIDField.getText());
        if (currentTextField != null) {
            currentTextField.setText(ItemUtils.formatCartItem(itemCounter, workingItem, quantity));
        }
    }


    private void resetInputFields() {
        itemIDField.clear();
        quantityIDField.clear();
    }

    private void updateSubtotal() {
        double previousSub = ItemUtils.findPreviousSub(subtotalField.getText());
        double newSubtotal = previousSub + workingItem.getTotalPrice();
        subtotalField.setText("$" + decimalFormat.format(newSubtotal));
    }

    private void prepareForNextItem() {
        itemCounter++;
        itemIDLabel.setText("Enter item ID for Item #" + itemCounter);
        quantityIDLabel.setText("Enter quantity for Item #" + itemCounter);
        findItemButton.setText("Find Item #" + itemCounter);
        addButton.setText("Add Item #" + itemCounter + " To Cart");
        findItemButton.setDisable(false);
        addButton.setDisable(true);
        viewCartButton.setDisable(false);
        checkButton.setDisable(false);
    }

    private StringBuilder buildReceipt() {
        StringBuilder stringBuilder = new StringBuilder();
        Date date = new Date();
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("EST"));

        String formattedDate = simpleDateFormat.format(date);
        double previousSub = ItemUtils.findPreviousSub(subtotalField.getText());
        double taxAmount = previousSub * TAX;
        double total = previousSub + taxAmount;

        stringBuilder.append("Date: ").append(formattedDate)
                .append("\n\nNumber of line items: ").append(itemCounter)
                .append("\n\n").append(ItemUtils.buildCart(addedItems))
                .append("\n\nOrder subtotal: $").append(decimalFormat.format(previousSub))
                .append("\n\nTax rate: 6%\n\nTax Amount: $").append(decimalFormat.format(taxAmount))
                .append("\n\nORDER TOTAL: $").append(decimalFormat.format(total))
                .append("\n\nThanks for shopping at Nile Dot Com!");

        return stringBuilder;
    }


    private void processTransaction() throws IOException {
        String transactionData = buildTransactionData();
        ItemUtils.addTransaction(transactionData);
        disableInteractionPostCheckout();
    }

    private String buildTransactionData() {
        Date date = new Date();
        StringBuilder transactionBuilder = new StringBuilder();
        String tDate = transactionDate.format(date);
        String spellDate = simpleDateFormat.format(date);

        for (Item item : addedItems) {
            transactionBuilder.append(tDate).append(ItemUtils.buildTransactionList(item, spellDate));
        }
        transactionBuilder.append("\n");
        return transactionBuilder.toString();
    }

    private void disableInteractionPostCheckout() {
        findItemButton.setDisable(true);
        addButton.setDisable(true);
    }
}


