package org.example.autos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class Vehicle {
    protected final SimpleIntegerProperty id;
    protected final SimpleStringProperty brand;
    protected final SimpleStringProperty model;
    protected final SimpleDoubleProperty price;

    public Vehicle(int id, String brand, String model, double price) {
        this.id = new SimpleIntegerProperty(id);
        this.brand = new SimpleStringProperty(brand);
        this.model = new SimpleStringProperty(model);
        this.price = new SimpleDoubleProperty(price);
    }

    public int getId() { return id.get(); }
    public String getBrand() { return brand.get(); }
    public String getModel() { return model.get(); }
    public double getPrice() { return price.get(); }

    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleStringProperty brandProperty() { return brand; }
    public SimpleStringProperty modelProperty() { return model; }
    public SimpleDoubleProperty priceProperty() { return price; }
}