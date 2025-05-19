package org.example.autos;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class SoldCar extends Vehicle {
    private final SimpleDoubleProperty tax;
    private final SimpleDoubleProperty commission; // Исправлено с "commision" на "commission"
    private final SimpleStringProperty availability;

    public SoldCar(int id, String brand, String model, double price, double tax, double commission, String availability) {
        super(id, brand, model, price);
        this.tax = new SimpleDoubleProperty(tax);
        this.commission = new SimpleDoubleProperty(commission);
        this.availability = new SimpleStringProperty(availability);
    }

    public double getTax() { return tax.get(); }
    public double getCommission() { return commission.get(); } // Исправлено с "commision" на "commission"
    public String getAvailability() { return availability.get(); }

    public SimpleDoubleProperty taxProperty() { return tax; }
    public SimpleDoubleProperty commissionProperty() { return commission; } // Исправлено с "commision" на "commission"
    public SimpleStringProperty availabilityProperty() { return availability; }
}