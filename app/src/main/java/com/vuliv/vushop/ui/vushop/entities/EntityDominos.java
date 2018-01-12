package com.vuliv.vushop.ui.vushop.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MB0000004 on 03-Jan-18.
 */

public class EntityDominos implements Serializable{
    private String id;
    private String name;
    private String size="L";
    private List<String> sizes;
    private String description;
    private Integer max_quantity;
    private Integer quantity=0;
    private float price;
    private float gst_price;
    private boolean veg;
    private String image;
    private String crust;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMax_quantity() {
        return max_quantity;
    }

    public void setMax_quantity(Integer max_quantity) {
        this.max_quantity = max_quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getGst_price() {
        return gst_price;
    }

    public void setGst_price(float gst_price) {
        this.gst_price = gst_price;
    }

    public boolean isVeg() {
        return veg;
    }

    public void setVeg(boolean veg) {
        this.veg = veg;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCrust() {
        return crust;
    }

    public void setCrust(String crust) {
        this.crust = crust;
    }
}

