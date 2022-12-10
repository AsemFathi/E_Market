package com.egtactile.e_market;

public class items {
    String price;
    String name;
    String image;

    public items(String price, String name, String image) {
        this.price = price;
        this.name = name;
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
