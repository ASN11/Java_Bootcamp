package edu.school21.models;

import java.util.Objects;

public class Product {
    private int identifier;
    private String name;
    private double price;

    public Product() {}

    public Product(int identifier, String name, double price) {
        this.identifier = identifier;
        this.name = name;
        this.price = price;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "identifier=" + identifier +
                ", name='" + name + '\'' +
                ", price=" + price +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return identifier == product.identifier && Double.compare(product.price, price) == 0 && name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, name, price);
    }
}
