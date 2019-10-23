package com.comp90018.photostyle.helpers;


public class Model {

    private boolean isSelected;
    private String itemLabel;

    public String getItemLabel() {
        return itemLabel;
    }

    public void setItemLabel(String animal) {
        this.itemLabel = animal;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
