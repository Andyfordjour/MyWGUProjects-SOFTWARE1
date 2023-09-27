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
 *
 * The `ModifyProductController` class is responsible for managing the user
 * interface and logic for modifying a product in the application. It implements
 * the `Initializable` interface to initialize the controller's state. "FUTURE
 * ENHANCEMENT" Undo/Redo functionality: Implement undo and redo functionality
 * to allow users to revert or redo their actions. This can be useful when
 * managing parts or modifying product information.
 *
 * @author fordjour andy
 */
public class ModifyProductController implements Initializable {

    /**
     * Represents the main stage and parent scene for managing parts in a
     * product.
     */
    Stage stage;
    Parent scene;
    /**
     * TableView for displaying the top part table.
     */
    @FXML
    private TableView<Part> topTableView;
    /**
     * TableColumn for displaying the ID of parts in the top table.
     */
    @FXML
    private TableColumn<Part, Integer> topPartID;
    /**
     * TableColumn for displaying the name of parts in the top table.
     */
    @FXML
    private TableColumn<Part, String> topPartName;
    /**
     * TableColumn for displaying the inventory level of parts in the top table.
     */
    @FXML
    private TableColumn<Part, Integer> topInventoryLv;
    /**
     * TableColumn for displaying the price per unit of parts in the top table.
     */
    @FXML
    private TableColumn<Part, Double> topPricePerUnit;
    /**
     * TableView for displaying the bottom part table.
     */
    @FXML
    private TableView<Part> bottomTableView;
    /**
     * TableColumn for displaying the ID of parts in the bottom table.
     */
    @FXML
    private TableColumn<Part, Integer> bPartID;
    /**
     * TableColumn for displaying the name of parts in the bottom table.
     */
    @FXML
    private TableColumn<Part, String> bPartName;
    /**
     * TableColumn for displaying the inventory level of parts in the bottom
     * table.
     */
    @FXML
    private TableColumn<Part, Integer> bInventoryLv;
    /**
     * TableColumn for displaying the price per unit of parts in the bottom
     * table.
     */
    @FXML
    private TableColumn<Part, Integer> bPricePerUnit;
    /**
     * TextField used for searching.
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
     * Text field for entering the part price or cost.
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

    private ObservableList<Part> tempPart = FXCollections.observableArrayList();
    private ObservableList<Part> currentPart = FXCollections.observableArrayList();
    private Product selectedProduct;

    /**
     * Initializes the controller's state after its root element has been
     * completely processed. This method is automatically called by the
     * FXMLLoader.
     *
     * @param url The location used to resolve relative paths for the root
     * object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the
     * root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        topTableView.setItems(Inventory.getAllParts());

        topPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        topPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        topInventoryLv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        topPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        
        
        bottomTableView.setItems(currentPart);
        bPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        bPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        bInventoryLv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        bPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        idTxt.setDisable(true);
    }

    /**
     * Handles the action event when the "Delete Part" button is clicked.Removes
     * the selected part from the current list of parts and updates the table
     * view. Displays a delete confirmation dialog.
     *
     * @param event The action event representing the button click.
     * @throws java.io.IOException
     */
    @FXML
    public void onActionDeletePart(ActionEvent event) throws IOException {
        Part part = bottomTableView.getSelectionModel().getSelectedItem();

        if (MainController.deleteConfirmation("part")) {
            MainController.performDelete("part");
            currentPart.remove(part);
            selectedProduct.getAllAssociatedParts().remove(part);
            bottomTableView.setItems(currentPart);

        }

    }

    /**
     * returns you back to the MainMenu
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void onActionGoToMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Adds Part allows you to select the part to be associated with the product
     *
     * @param event
     */
    @FXML
    public void onActionAddPart(ActionEvent event) {
        Part part = topTableView.getSelectionModel().getSelectedItem();
       currentPart.add(part);
       selectedProduct.addAssociatedPart(part);
        bottomTableView.setItems(currentPart);
    }

    /**
     * Handles the action event when the "Update Product" button is clicked.
     * Retrieves the entered values from the text fields and creates a new
     * product with the updated information. Updates the associated parts of the
     * product and updates the product in the inventory. Loads and displays the
     * main menu scene.
     *
     * "LOGICAL ERROR" i had issues with the populating the product.i had
     * duplicated for the products after modification i fixed it by calling only
     * the selected product
     *
     * @param event The action event representing the button click.
     * @throws IOException If an I/O error occurs while loading the main menu
     * scene.
     */
    @FXML
    public void onActionUpdateProduct(ActionEvent event) throws IOException {
        if (isInputValid()) {
            int itemID = Integer.parseInt(idTxt.getText());
            String name = nameTxt.getText();
            int stock = Integer.parseInt(invTxt.getText());
            double price = Double.parseDouble(priceTxt.getText());
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
        
            // Update the selectedProduct directly
            //selectedProduct.setId(itemID);
            selectedProduct.setName(name);
            selectedProduct.setPrice(price);
            selectedProduct.setStock(stock);
            selectedProduct.setMin(min);
            selectedProduct.setMax(max);
        
            

             
            //    for (Part part : currentPart) {
            //        selectedProduct.addAssociatedPart(part);
            //    }
            
            // Update the product in the inventory
            int index1 = Inventory.getAllProducts().indexOf(selectedProduct);
            Inventory.updateProduct(index1, selectedProduct);
            

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }   
    }

    /**
     * Sets the product to be modified and populates the UI fields with the
     * product's information. "Logical Error" I had issues trying to get all the
     * parts for the required products but later realized i needed a for loop
     *
     * @param product The product to be modified.
     */
    public void setProduct(Product product) {
        selectedProduct = product;
        
        for (Part part : selectedProduct.getAllAssociatedParts()) {
            currentPart.add(part);
        }

        idTxt.setText(Integer.toString(selectedProduct.getId()));
        nameTxt.setText(selectedProduct.getName());
        invTxt.setText(Integer.toString(selectedProduct.getStock()));
        priceTxt.setText(Double.toString(selectedProduct.getPrice()));
        minTxt.setText(Integer.toString(selectedProduct.getMin()));
        maxTxt.setText(Integer.toString(selectedProduct.getMax()));
    }

    /**
     * Handles the action event when the "Search Parts" button is clicked.
     * Retrieves the search item from the text field and performs a search in
     * the inventory. Displays the search results in the top table view or shows
     * an error message if no match is found.
     *
     * @param event The action event representing the button click. "LOGICAL
     * ERROR" I missed a curly bracket but later found it and fix it
     */
    @FXML
public void onActionSearchParts(ActionEvent event) {
    String searchItem = searchTxt.getText().toLowerCase();

    if (searchItem.isEmpty()) {
        topTableView.setItems(Inventory.getAllParts());
    } else {
        boolean found = false;
        try {
            int itemNumber = Integer.parseInt(searchItem);
            Part p = Inventory.lookupPart(itemNumber);
            if (p != null) {
                //System.out.println("This is part " + p.getId());
                //infoMessage(itemNumber);
                found = true;
                tempPart.clear();
                tempPart.add(p);
                topTableView.getSelectionModel().select(p);
                topTableView.requestFocus();
                topTableView.setItems(tempPart);
            }
            if (!found) {
                topTableView.setItems(Inventory.getAllParts());
                errorMessage();
            }
        } catch (NumberFormatException e) {
            // Handle invalid input (non-numeric)
            ObservableList<Part> matchingParts = FXCollections.observableArrayList();

            for (Part part : Inventory.getAllParts()) {
                String partName = part.getName().toLowerCase();

                if (partName.contains(searchItem)) {
                    matchingParts.add(part);
                    topTableView.getSelectionModel().select(part);
                topTableView.requestFocus();
                }
            }

            if (matchingParts.isEmpty()) {
                // If no exact matches, search for partial matches
                for (Part part : Inventory.getAllParts()) {
                    String partName = part.getName().toLowerCase();

                    if (partName.startsWith(searchItem)) {
                        matchingParts.add(part);
                        topTableView.getSelectionModel().select(part);
                topTableView.requestFocus();
                    }
                }
            }

            if (matchingParts.isEmpty()) {
                errorMessage();
            } else {
                topTableView.setItems(matchingParts);
            }
        }
    }
}

    /**
     * Displays an error message dialog indicating that the item was not found
     * in the inventory.
     */
    public void errorMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Item not found");
        alert.setContentText("Please search by item ID# or exact Name");
        alert.showAndWait();
    }

    /**
     * Displays an information message dialog indicating that the item with the
     * specified ID was found in the inventory.
     *
     * @param item The ID of the found item.
     */
    public void infoMessage(int item) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText("Item ID:" + item + " Successfully Found");
        alert.setContentText("Item is displayed in table");
        alert.showAndWait();
    }

    /**
     * Displays an information message dialog indicating that the item with the
     * specified name was found in the inventory.
     *
     * @param item The name of the found item.
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
            errorMessage += "Price must be a valid decimal number.\n";
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
