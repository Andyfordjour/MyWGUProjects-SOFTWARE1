/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package softwareinv;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * The `AddProductController` class is a controller for the Add Product screen.
 * It handles the initialization of the screen and the user interactions related
 * to adding a new product. "FUTURE ENHANCEMENT" Data Persistence: Implement a
 * mechanism to save the data entered by the user, such as storing it in a
 * database or a file. This will allow users to retrieve and modify the data
 * later, ensuring data persistence across application sessions.
 *
 * @author fordjour andy
 */
public class AddProductController implements Initializable {
    // private static int lastGeneratedPartId = 0;

    Stage stage;
    Parent scene;
    /**
     * TableView for displaying parts in the top section.
     */
    @FXML
    private TableView<Part> topTableview;
    /**
     * TableColumn for displaying the part ID in the top section.
     */
    @FXML
    private TableColumn<Part, Integer> topPartID;
    /**
     * TableColumn for displaying the part name in the top section.
     */
    @FXML
    private TableColumn<Part, String> topPartName;
    /**
     * TableColumn for displaying the part inventory level in the top section.
     */
    @FXML
    private TableColumn<Part, Integer> topInventoryLv;
    /**
     * TableColumn for displaying the part price per unit in the top section.
     */
    @FXML
    private TableColumn<Part, Double> topPricePerUnit;
    /**
     * TableView for displaying parts in the bottom section.
     */
    @FXML
    private TableView<Part> bottomTalbleview;
    /**
     * TableColumn for displaying the part ID in the bottom section.
     */
    @FXML
    private TableColumn<Part, Integer> bPartID;
    /**
     * TableColumn for displaying the part name in the bottom section.
     */
    @FXML
    private TableColumn<Part, String> bPartName;
    /**
     * TableColumn for displaying the part inventory level in the bottom
     * section.
     */
    @FXML
    private TableColumn<Part, Integer> bInventoryLv;
    /**
     * TableColumn for displaying the part price per unit in the bottom section.
     */
    @FXML
    private TableColumn<Part, Double> bPricePerUnit;
    /**
     * Text field for searching parts.
     */
    @FXML
    private TextField searchTxt;
    /**
     * Text field for entering the part ID.
     */
    @FXML
    private TextField idTxt;
    /**
     * Text field for entering the part name.
     */
    @FXML
    private TextField nameTxt;
    /**
     * Text field for entering the part inventory level.
     */
    @FXML
    private TextField invTxt;
    /**
     * Text field for entering the part price.
     */
    @FXML
    private TextField priceTxt;
    /**
     * Text field for entering the maximum allowed quantity of the part.
     */
    @FXML
    private TextField maxTxt;
    /**
     * Text field for entering the minimum allowed quantity of the part.
     */
    @FXML
    private TextField minTxt;

    /**
     * An ObservableList of Part objects used to store temporary parts. An
     * ObservableList of Part objects used to store the current parts.
     */
    @FXML
    public ObservableList<Part> tempPart = FXCollections.observableArrayList();
    private ObservableList<Part> currentPart = FXCollections.observableArrayList();

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     *
     * @param url The location used to resolve relative paths for the root
     * object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the
     * root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        topTableview.setItems(Inventory.getAllParts());

        topPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        topPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        topInventoryLv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        topPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

        bPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        bPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        bInventoryLv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        bPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Generate a new part ID
        int newPartId = generateNextPartId();
        idTxt.setText(String.valueOf(newPartId));
        idTxt.setDisable(true);

    }

    /**
     * Generates the next part ID by finding the maximum ID in the current parts
     * list and incrementing it by 1.
     *
     * @return The next part ID.
     */
    private int generateNextPartId() {
        int maxId = 999;
        for (Product part : Inventory.getAllProducts()) {
            if (part.getId() > maxId) {
                maxId = part.getId();
            }
        }
        return maxId + 1;
    }

    /**
     * Deletes a part from the current parts list.
     *
     * @param event The action event triggered by the delete button.
     */
    @FXML
    public void onActionDeletePart(ActionEvent event) {
        Part part = bottomTalbleview.getSelectionModel().getSelectedItem();
        currentPart.remove(part);
        bottomTalbleview.setItems(currentPart);
        //MainController.deleteConfirmation();
    }

    /**
     * Navigates to the main menu screen.
     *
     * @param event The action event triggered by the main menu button.
     * @throws IOException if an error occurs while loading the main menu FXML.
     * "LOGICAL ERROR" for some reason the load resources to mainMenu fxml
     * wasn't working so i had to give a relative file part instead of just
     * providing the name of the file.
     */
    @FXML
    public void onActionGoToMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Adds a part to the current parts list.
     *
     * @param event The action event triggered by the add button.
     */
    @FXML
    public void onActionAddPart(ActionEvent event) {
        Part part = topTableview.getSelectionModel().getSelectedItem();
        //part.setId(generateNextPartId()); 
        currentPart.add(part);
        bottomTalbleview.setItems(currentPart);
    }

    /**
     * Saves the product and navigates to the main menu screen.
     *
     * @param event The action event triggered by the save button.
     * @throws IOException if an error occurs while loading the main menu FXML.
     * "RUNTIME ERROR" exception errors was the main issue but since i
     * introduced the IOException it solved the issue
     */
    @FXML
    public void onActionSaveProduct(ActionEvent event) throws IOException {
        if (isInputValid()) {
            int itemID = Integer.parseInt(idTxt.getText());
            String name = nameTxt.getText();
            int stock = Integer.parseInt(invTxt.getText());
            double price = Double.parseDouble(priceTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());

            Product newProduct = new Product(itemID, name, price, stock, min, max);

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Values");
                alert.setHeaderText("Minimum value cannot be greater than maximum value.");
                alert.setContentText("Please enter valid minimum and maximum values.");
                alert.showAndWait();
                return;
            }

            if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Values");
                alert.setHeaderText("Stock value is out of range.");
                alert.setContentText("Please enter a stock value between the minimum and maximum values.");
                alert.showAndWait();
                return;

            }

            ObservableList<Part> parts = bottomTalbleview.getItems(); // Assuming the table view is named bottomTableView

            for (Part part : parts) {
                newProduct.addAssociatedPart(part);
            }

            Inventory.addProduct(newProduct);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     * Searches for parts based on the search input.
     *
     * @param event The action event triggered by the search button.
     */
    @FXML
    public void onActionSearchParts(ActionEvent event) {
        String searchItem = searchTxt.getText();
        if (searchItem.equals("")) {
            topTableview.setItems(Inventory.getAllParts());
        } else {
            boolean found = false;
            try {
                int itemNumber = Integer.parseInt(searchItem);
                Part p = Inventory.lookupPart(itemNumber);
                if (p != null) {
                    found = true;
                    infoMessage(itemNumber);
                    tempPart.clear();
                    tempPart.add(p);
                    topTableview.setItems(tempPart);
                }
                if (found == false) {
                    topTableview.setItems(Inventory.getAllParts());
                    errorMessage();
                }
            } //catches strings used instead of numbers 
            catch (NumberFormatException e) {
                String itemName = searchItem;
                ObservableList<Part> foundParts = Inventory.lookupPart(itemName);
                if (!foundParts.isEmpty()) {
                    infoMessage(itemName);
                    found = true;
                    topTableview.setItems(foundParts);
                }

                if (!found) {
                    topTableview.setItems(Inventory.getAllParts());
                    errorMessage();
                }
            }

        }

    }

    /**
     * Displays an error message for unsuccessful searches.
     */
    public void errorMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Item not found");
        alert.setContentText("Please search by item ID# or exact Name");
        alert.showAndWait();
    }

    /**
     * Displays an information message for successful searches by item ID.
     *
     * @param item The item ID that was successfully found.
     */
    public void infoMessage(int item) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText("Item ID:" + item + " Successfully Found");
        alert.setContentText("Item is displayed in table");
        alert.showAndWait();
    }

    /**
     * Displays an information message for successful searches by item name.
     *
     * @param item The item name that was successfully found.
     */
    public void infoMessage(String item) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText("Item \"" + item + "\" successfully found");
        alert.setContentText("Item is displayed in table");
        alert.showAndWait();
    }

      /**
     * Validates the input fields in the AddPartController.
     *
     * @return true if all input fields are valid, false otherwise.
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (idTxt.getText().isEmpty()) {
            errorMessage += "ID field is empty.\n";
        }
        if (nameTxt.getText().isEmpty()) {
            errorMessage += "Name field is empty.\n";
        }
        if (invTxt.getText().isEmpty()) {
            errorMessage += "Inventory field is empty.\n";
        }
        if (priceTxt.getText().isEmpty()) {
            errorMessage += "Price field is empty.\n";
        }
        if (minTxt.getText().isEmpty()) {
            errorMessage += "Min field is empty.\n";
        }
        if (maxTxt.getText().isEmpty()) {
            errorMessage += "Max field is empty.\n";
        }
        

        if (!isNumeric(idTxt.getText())) {
            errorMessage += "ID must be a number.\n";
        }
        if (!isNumeric(invTxt.getText())) {
            errorMessage += "Inventory must be a number.\n";
        }else{
            int inv = Integer.parseInt(invTxt.getText());
            if(inv < 0){
                errorMessage += "Inventory must be not be negative.\n";
            }
        }
        if (!isDouble(priceTxt.getText())) {
            errorMessage += "Price/Cost must be a valid decimal number.\n";
        }
        if (!isNumeric(minTxt.getText())) {
            errorMessage += "Min must be a number.\n";
        }
        if (!isNumeric(maxTxt.getText())) {
            errorMessage += "Max must be a number.\n";
        }
        if (!isNumeric(minTxt.getText()) || !isNumeric(maxTxt.getText())) {
        errorMessage += "Min and Max must be numbers.\n";
    } else {
        int min = Integer.parseInt(minTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());
        if (min < 0 || max < 0) {
            errorMessage += "Min and Max cannot be negative.\n";
        }
    }
        if (errorMessage.isEmpty()) {
            return true;
        } else {
            // Display error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    /**
     * Checks if a given string represents a numeric value.
     *
     * @param input the string to check
     * @return true if the string represents a numeric value, false otherwise
     */
    private boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if a given string represents a valid double value.
     *
     * @param value the string to check
     * @return true if the string represents a valid double value, false
     * otherwise
     */
    public boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
