package com.nile.cnt4714_pone.Models;

public class Item {

    private String itemID;
    private String description;
    private String inStock;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
    private int requestedQuantity;

    public Item (String itemID, String description, String inStock, int quantity, double unitPrice)   {

        this.itemID = itemID;
        this.description = description;
        this.inStock = inStock;
        this.quantity = quantity;
        this.unitPrice = unitPrice;

    }


    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInStock() {
        return inStock;
    }

    public void setInStock(String inStock) {
        this.inStock = inStock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice()   {
        return totalPrice;
    }

    public void setTotalPrice(double total)   {
        this.totalPrice = total;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }
}
