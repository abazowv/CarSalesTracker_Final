package org.example.autos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javafx.scene.Node;
import javafx.scene.Scene;

public class DirectorController implements Initializable {
    @FXML
    private TextField BrandTb;
    @FXML
    private TextField ModelTb;
    @FXML
    private ComboBox<String> TypeCmb;
    @FXML
    private ComboBox<String> TransCmb;
    @FXML
    private ComboBox<String> StatusCmb;
    @FXML
    private TextField PriceTb;
    @FXML
    private TextField DateTb;
    @FXML
    private Button SaveBtn;
    @FXML
    private Button UpdateBtn;
    @FXML
    private Button DeleteBtn;
    @FXML
    private Button SoldBtn;
    @FXML
    private Button ExpBtn;
    @FXML
    private Button CheapBtn;
    @FXML
    private Button resetBtn;

    @FXML
    private TableView<Car> CarsTable;
    @FXML
    private TableColumn<Car, String> brandCol;
    @FXML
    private TableColumn<Car, String> modelCol;
    @FXML
    private TableColumn<Car, String> bodyTypeCol;
    @FXML
    private TableColumn<Car, String> transCol;
    @FXML
    private TableColumn<Car, String> statusCol;
    @FXML
    private TableColumn<Car, Double> priceCol;
    @FXML
    private TableColumn<Car, String> dateCol;

    Connection Con;
    private ObservableList<Car> carList = FXCollections.observableArrayList();

    public void Connect() {
        try {
            // Явная регистрация драйвера
            Class.forName("org.postgresql.Driver");

            // Подключение к базе данных
            Con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/isko",
                    "postgres",
                    "postgres"
            );

            System.out.println("Connected to the database successfully!");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DirectorController.class.getName()).log(Level.SEVERE, "PostgreSQL JDBC Driver not found!", ex);
        } catch (SQLException ex) {
            Logger.getLogger(DirectorController.class.getName()).log(Level.SEVERE, "Database connection failed!", ex);
        }
    }

    public void DisplayCars() {
        carList.clear();

        String query = "SELECT id, brand, model, body_type, trans, status, price, date FROM cars";

        try (PreparedStatement pst = Con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Car car = new Car(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("body_type"),
                        rs.getString("trans"),
                        rs.getString("status"),
                        rs.getDouble("price"),
                        rs.getString("date")
                );
                carList.add(car);
            }

            CarsTable.setItems(carList);

        } catch (SQLException e) {
            Logger.getLogger(DirectorController.class.getName()).log(Level.SEVERE, "Failed to load cars from database", e);
            JOptionPane.showMessageDialog(null, "Failed to load cars from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connect();
        brandCol.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        modelCol.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
        bodyTypeCol.setCellValueFactory(cellData -> cellData.getValue().bodyTypeProperty());
        transCol.setCellValueFactory(cellData -> cellData.getValue().transProperty());
        statusCol.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        priceCol.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        DisplayCars();
    }

    @FXML
    public void onTableRowSelected(MouseEvent mouseEvent) {
        Car selectedCar = CarsTable.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            BrandTb.setText(selectedCar.getBrand());
            ModelTb.setText(selectedCar.getModel());
            TypeCmb.setValue(selectedCar.getBodyType());
            TransCmb.setValue(selectedCar.getTrans());
            StatusCmb.setValue(selectedCar.getStatus());
            PriceTb.setText(String.valueOf(selectedCar.getPrice()));
            DateTb.setText(selectedCar.getDate());
        }
    }

    @FXML
    public void saveBtn() {
        try {
            PreparedStatement add = Con.prepareStatement(
                    "INSERT INTO cars (brand, model, body_type, trans, status, price, date) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );

            add.setString(1, BrandTb.getText());
            add.setString(2, ModelTb.getText());
            add.setString(3, TypeCmb.getValue());
            add.setString(4, TransCmb.getValue());
            add.setString(5, StatusCmb.getValue());
            add.setDouble(6, Double.parseDouble(PriceTb.getText()));
            add.setString(7, DateTb.getText());

            add.executeUpdate();
            JOptionPane.showMessageDialog(null, "Car saved successfully!");
            DisplayCars();

        } catch (Exception e) {
            Logger.getLogger(DirectorController.class.getName()).log(Level.SEVERE, "Failed to record", e);
            JOptionPane.showMessageDialog(null, "Failed to record", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    public void resetBtn() {
        BrandTb.setText("");
        ModelTb.setText("");
        TypeCmb.setValue(null);
        TransCmb.setValue(null);
        StatusCmb.setValue(null);
        PriceTb.setText("");
        DateTb.setText("");
    }

    @FXML
    public void updateBtn() {
        Car selectedCar = CarsTable.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            try {
                String query = "UPDATE cars SET brand = ?, model = ?, body_type = ?, trans = ?, status = ?, price = ?, date = ? WHERE id = ?";
                PreparedStatement pst = Con.prepareStatement(query);
                pst.setString(1, BrandTb.getText());
                pst.setString(2, ModelTb.getText());
                pst.setString(3, TypeCmb.getValue());
                pst.setString(4, TransCmb.getValue());
                pst.setString(5, StatusCmb.getValue());
                pst.setDouble(6, Double.parseDouble(PriceTb.getText()));
                pst.setString(7, DateTb.getText());
                pst.setInt(8, selectedCar.getId());

                int rowsUpdated = pst.executeUpdate();
                if (rowsUpdated > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Update Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Car updated successfully!");
                    alert.showAndWait();
                }
                DisplayCars();
            } catch (SQLException e) {
                Logger.getLogger(DirectorController.class.getName()).log(Level.SEVERE, "Failed to update car", e);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update car.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void deleteBtn() {
        Car selectedCar = CarsTable.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            try {
                String query = "DELETE FROM cars WHERE id = ?";
                PreparedStatement pst = Con.prepareStatement(query);
                pst.setInt(1, selectedCar.getId());

                int rowsDeleted = pst.executeUpdate();
                if (rowsDeleted > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Delete Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Car deleted successfully!");
                    alert.showAndWait();
                }
                DisplayCars();
            } catch (SQLException e) {
                Logger.getLogger(DirectorController.class.getName()).log(Level.SEVERE, "Failed to delete car", e);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to delete car.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void cheapBtn() {
        Car cheapestCar = carList.stream()
                .min(Comparator.comparingDouble(Car::getPrice))
                .orElse(null);

        if (cheapestCar != null) {
            carList.clear();
            carList.add(cheapestCar);
            CarsTable.setItems(carList);
        }
    }

    @FXML
    public void expBtn() {
        Car expensiveCar = carList.stream()
                .max(Comparator.comparingDouble(Car::getPrice))
                .orElse(null);

        if (expensiveCar != null) {
            carList.clear();
            carList.add(expensiveCar);
            CarsTable.setItems(carList);
        }
    }

    @FXML
    public void showAllBtn() {
        carList.clear();
        DisplayCars();
        CarsTable.setItems(carList);
    }

    @FXML
    public void goToLogin(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = fxmlLoader.load();
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene newScene = new Scene(root, 525, 458);
            currentStage.setScene(newScene);
            currentStage.setTitle("Login");
            currentStage.show();
        } catch (IOException e) {
            Logger.getLogger(DirectorController.class.getName()).log(Level.SEVERE, "Failed to load Login scene", e);
            JOptionPane.showMessageDialog(null, "Failed to load Login scene.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}