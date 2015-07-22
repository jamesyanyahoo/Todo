package com.yahoo.shopping.todo.model;

/**
 * Created by jamesyan on 7/21/15.
 */
public class Item {
    private String item;
    private boolean displayDeleteButton = false;

    public Item(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isDisplayDeleteButton() {
        return displayDeleteButton;
    }

    public void setDisplayDeleteButton(boolean displayDeleteButton) {
        this.displayDeleteButton = displayDeleteButton;
    }
}
