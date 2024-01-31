package com.nile.cnt4714_pone.Utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemUtilsTest {


    @Test
    void findDiscount() {
        assertEquals(0, ItemUtils.findDiscount("4"));
        assertEquals(10, ItemUtils.findDiscount("9"));
        assertEquals(15, ItemUtils.findDiscount("14"));
        assertEquals(20, ItemUtils.findDiscount("25"));
    }

    @Test
    void findTextField() {



    }

    @Test
    void buildCart() {
    }

    @Test
    void buildTransactionList() {
    }

    @Test
    void addTransaction() {
    }

    @Test
    void updateWorkingItem() {
    }

    @Test
    void formatCartItem() {
    }
}