package org.example.autos;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ServicedCar extends Vehicle {
    private final SimpleStringProperty bodyType;
    private final SimpleStringProperty trans;
    private final SimpleStringProperty serviceDate;
    private final SimpleStringProperty reason;
    private final SimpleStringProperty status;

    public ServicedCar(int id, String brand, String model, String bodyType, String trans, String serviceDate, String reason, String status, double price) {
        super(id, brand, model, price);
        this.bodyType = new SimpleStringProperty(bodyType);
        this.trans = new SimpleStringProperty(trans);
        this.serviceDate = new SimpleStringProperty(serviceDate);
        this.reason = new SimpleStringProperty(reason);
        this.status = new SimpleStringProperty(status);
    }

    public String getBodyType() { return bodyType.get(); }
    public String getTrans() { return trans.get(); }
    public String getServiceDate() { return serviceDate.get(); }
    public String getReason() { return reason.get(); }
    public String getStatus() { return status.get(); }

    public SimpleStringProperty bodyTypeProperty() { return bodyType; }
    public SimpleStringProperty transProperty() { return trans; }
    public SimpleStringProperty serviceDateProperty() { return serviceDate; }
    public SimpleStringProperty reasonProperty() { return reason; }
    public SimpleStringProperty statusProperty() { return status; }
}