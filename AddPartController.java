/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package softwareinv;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
//import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 *
 *
 * The `AddPartController` class is a controller for the Add Part screen. It
 * handles the user interactions and actions related to adding a new part.
 * "FUTURE ENHANCEMENT" Data Persistence: Implement a mechanism to save the data
 * entered by the user, such as storing it in a database or a file. This will
 * allow users to retrieve and modify the data later, ensuring data persistence
 * across application sessions.
 *
 * * @author fordjour andy
 */
public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * Toggle group for the in-house and outsourced radio buttons.
     */
    @FXML
    private ToggleGroup InOutTG;

    /**
     * Radio button for selecting the part as in-house.
     */
    @FXML
    private RadioButton inHouseTogBTN;

    /**
     * Radio button for selecting the part as outsourced.
     */
    @FXML
    private RadioButton outsourcedTogBTN;
    /**
     * Label for displaying the company or machine information.
     */

    @FXML
    private Label companyMachineLabel;
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
     * Text field for entering the part price or cost.
     */
    @FXML
    private TextField priceCostTxt;
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
     * Text field for entering the machine or company name.
     */
    @FXML
    private TextField machineCompanyTxt;

    /**
     * Initializes the controller class.
     *
     * @param url The location used to resolve relative paths for the root
     * object.
     * @param rb The resources used to localize the root object. "logical Error"
     * i encountered was not properly assigning the both buttons to the correct
     * toggle which resulted in a two different toggle group as i corrected it
     * by putting them in a the same toggle group
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        InOutTG = new ToggleGroup();
        this.inHouseTogBTN.setToggleGroup(InOutTG);
        this.outsourcedTogBTN.setToggleGroup(InOutTG);
        idTxt.setDisable(true);

        // Generate next part ID
        int nextPartId = generateNextPartId();
        idTxt.setText(String.valueOf(nextPartId));

    }

    /**
     * Generates the next available ID for a new Part.
     *
     * @return the next available ID for a new Part
     */
    private int generateNextPartId() {
        int maxId = 0;

        // Iterate over the parts list and find the maximum ID
        for (Part part : Inventory.getAllParts()) {
            int partId = part.getId();
            if (partId > maxId) {
                maxId = partId;
            }
        }

        // Increment the maximum ID by 1
        return maxId + 1;
    }

    /**
     * Handles the action when a radio button is selected. Updates the label and
     * prompt text based on the selected radio button. "RUNTIME ERROR" initially
     * the InOutTG object had a null button which created a NullPointerException
     * so i changed it so that one of the buttons will be automatically selected
     */
    public void onActoinRadioButton() {
        if (this.InOutTG.getSelectedToggle().equals(this.inHouseTogBTN)) {
            companyMachineLabel.setText("Machine ID");
            machineCompanyTxt.setPromptText("Machine ID");
        }
        if (this.InOutTG.getSelectedToggle().equals(this.outsourcedTogBTN)) {
            companyMachineLabel.setText("Company Name");
            machineCompanyTxt.setPromptText("Company Name");
        }
    }

    /**
     * Navigates to the main menu screen.
     *
     * @param event The action event triggered by the main menu button.
     * @throws IOException if an error occurs while loading the main menu FXML.
     */
    @FXML
    public void onActionGoToMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Saves the part data and navigates to the main menu screen.
     *
     * @param event The action event triggered by the save button.
     * @throws IOException if an error occurs while loading the main menu FXML.
     * "RUNTIME ERROR" initially i realized that when ever i had a an empty
     * input it will throw a NullPointException so i ensured that these objects
     * are initialized correctly before using them.
     */
    @FXML
    public void onActionSaveData(ActionEvent event) throws IOException {
        if (isInputValid()) {
            int itemID = Integer.parseInt(idTxt.getText());

            String name = nameTxt.getText();
            int stock = Integer.parseInt(invTxt.getText());
            double price = Double.parseDouble(priceCostTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());

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

            if (this.InOutTG.getSelectedToggle().equals(this.inHouseTogBTN)){
                
                int machineID = Integer.parseInt(machineCompanyTxt.getText());
                Inventory.addPart(new InHouse(itemID, name, price, stock, min, max, machineID));
            }
            else {
                String companyName = machineCompanyTxt.getText();
                Inventory.addPart(new Outsourced(itemID, name, price, stock, min, max, companyName));
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
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
        if (priceCostTxt.getText().isEmpty()) {
            errorMessage += "Price/Cost field is empty.\n";
        }
        if (minTxt.getText().isEmpty()) {
            errorMessage += "Min field is empty.\n";
        }
        if (maxTxt.getText().isEmpty()) {
            errorMessage += "Max field is empty.\n";
        }
        if (machineCompanyTxt.getText().isEmpty()) {
            errorMessage += "Machine ID/Company Name field is empty.\n";
        }
        if (!isNumeric(machineCompanyTxt.getText()) && this.InOutTG.getSelectedToggle().equals(this.inHouseTogBTN)) {
            errorMessage += "Machine ID must be a number.\n";
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
        if (!isDouble(priceCostTxt.getText())) {
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
