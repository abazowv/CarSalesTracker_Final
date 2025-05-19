package org.example.autos;

import javafx.beans.property.SimpleStringProperty;

public class Car extends Vehicle {
    private final SimpleStringProperty bodyType;
    private final SimpleStringProperty trans;
    private final SimpleStringProperty status;
    private final SimpleStringProperty date;

    public Car(int id, String brand, String model, String bodyType, String trans, String status, double price, String date) {
        super(id, brand, model, price);
        this.bodyType = new SimpleStringProperty(bodyType);
        this.trans = new SimpleStringProperty(trans);
        this.status = new SimpleStringProperty(status);
        this.date = new SimpleStringProperty(date);
    }

    public String getBodyType() { return bodyType.get(); }
    public String getTrans() { return trans.get(); }
    public String getStatus() { return status.get(); }
    public String getDate() { return date.get(); }

    public SimpleStringProperty bodyTypeProperty() { return bodyType; }
    public SimpleStringProperty transProperty() { return trans; }
    public SimpleStringProperty statusProperty() { return status; }
    public SimpleStringProperty dateProperty() { return date; }
}