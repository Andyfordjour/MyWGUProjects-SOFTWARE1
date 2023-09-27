/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package softwareinv;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
//import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 *
 * The `MainController` class is responsible for managing the user interface and
 * logic for the main menu screen of the application. It implements the
 * `Initializable` interface to initialize the controller's state. "FUTURE
 * ENHANCEMENT" This could include validating input fields for correct data
 * types (e.g., numeric fields only accept numbers) and checking for valid
 * ranges or constraints (e.g., ensuring that the price is not negative).
 *
 * * @author fordjour andy
 */
public class MainController implements Initializable {

    /**
     * Represents the stage of the JavaFX application.
     */
    Stage stage;
    /**
     * Represents the parent scene of the JavaFX application.
     */
    Parent scene;

    /**
     * Represents the current index.
     */
    private static int index;
    /**
     * Represents the ID for the next part.
     */
    private static int nextPartId = 1;

    /**
     * Modifies the index and returns its value.
     *
     * @return The modified index value.
     */
    public static int modifyIndex() {
        return index;
    }
    /**
     * JavaFX TableView displaying the parts.
     */
    @FXML
    private TableView<Part> partsTableview;
    /**
     * TableColumn displaying the ID of each part.
     */
    @FXML
    private TableColumn<Part, Integer> partID;
    /**
     * TableColumn displaying the name of each part.
     */
    @FXML
    private TableColumn<Part, String> partName;
    /**
     * TableColumn displaying the inventory level of each part.
     */
    @FXML
    private TableColumn<Part, Integer> partsInventLvL;
    /**
     * TableColumn displaying the price cost of each part.
     */
    @FXML
    private TableColumn<Part, Double> partsPriceCost;
    /**
     * TextField used for searching parts.
     */
    @FXML
    private TextField searchPartsTxt;
    /**
     * TextField used for searching products.
     */
    @FXML
    private TextField searchProductsTxt;
    /**
     * Button for modifying parts.
     */
    @FXML
    private Button modifyPartsBTN;
    /**
     * Button for modifying products.
     */
    @FXML
    private Button modifyProductsBTN;
    /**
     * JavaFX TableView displaying the products.
     */
    @FXML
    private TableView<Product> productsTableview;
    /**
     * TableColumn displaying the ID of each product.
     */
    @FXML
    private TableColumn<Product, Integer> productID;
    /**
     * TableColumn displaying the name of each product.
     */
    @FXML
    private TableColumn<Product, String> productName;
    /**
     * TableColumn displaying the inventory level of each product.
     */
    @FXML
    private TableColumn<Product, Integer> ProdInventLvL;
    /**
     * TableColumn displaying the price cost of each product.
     */
    @FXML
    private TableColumn<Product, Double> productPriceCost;

    /**
     * ObservableList containing temporary parts data.
     */
    @FXML
    public ObservableList<Part> tempPart = FXCollections.observableArrayList();

    /**
     * ObservableList containing temporary products data.
     */
    public ObservableList<Product> tempProduct = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     *
     * @param url The location used to resolve relative paths for the root
     * object.
     * @param rb The resource bundle used to localize the root object. "LOGICAL
     * ERROR" The ' PropertyValueFactory' did not match the property names in
     * the Part and Product but later found the problem and solved it out
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partsTableview.setItems(Inventory.getAllParts());

        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventLvL.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTableview.setItems(Inventory.getAllProducts());

        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProdInventLvL.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCost.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Displays a delete confirmation message.
     *
     */
    /**
     * public static void deleteConfirmation() { Alert alert = new
     * Alert(Alert.AlertType.CONFIRMATION); alert.setTitle("Confirmation");
     * alert.setHeaderText("Delete"); alert.setContentText("Do you want to
     * delete this?");
     *
     */
    /**
     * Displays a delete confirmation message.
     *
     * @param objectType
     * @return
     */
    public static boolean deleteConfirmation(String objectType) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete");
        alert.setContentText("Do you want to delete this " + objectType + "?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
        // User clicked OK, perform the delete operation

    }

    public static void performDelete(String objectType) {

        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Message");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Item successfully deleted");
        successAlert.showAndWait();
    }

    /**
     * Handles the action event when the "Delete Part" button is clicked.
     *
     * @param event The action event.
     */
    @FXML

    public void onActionDeletePart(ActionEvent event) {
        Part part = partsTableview.getSelectionModel().getSelectedItem();
        if (part != null) {
            if (deleteConfirmation("part")) {
                Inventory.deletePart(part);
                performDelete("part");

                // Generate the next part ID and update AddPartController
                removePart(part);
            }
        } else {
            // Show an error message indicating that no part is selected
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please select a part to delete.");
            errorAlert.showAndWait();
        }
    }

    /**
     * Handles the action event when the "Delete Product" button is clicked.
     *
     * @param event The action event.
     */
    private void removePart(Part part) {
        // Remove the part from the inventory
        Inventory.getAllParts().remove(part);

        // Adjust the nextPartId if the deleted part's ID is equal to nextPartId
        if (part.getId() == nextPartId - 1) {
            int maxId = 0;

            // Find the maximum ID value among the remaining parts
            for (Part p : Inventory.getAllParts()) {
                if (p.getId() > maxId) {
                    maxId = p.getId();
                }
            }

            nextPartId = maxId + 1;
        }
    }

    /**
     * Handles the action event when the "Delete Product" button is clicked.
     * "Logical Error" i had to find a way to catching the error on null. i
     * created a notification for it .
     *
     * @param event The action event.
     */
    @FXML

    public void onActionDeleteProduct(ActionEvent event) {
        Product product = productsTableview.getSelectionModel().getSelectedItem();
        if (product != null) {
            // ObservableList<Part> associatedParts = product.getAllAssociatedParts();

            if (!product.getAllAssociatedParts().isEmpty()) {
                // Show a message indicating that the product can't be deleted because a part is assigned to it
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("The product cannot be deleted because it has associated parts.");
                errorAlert.showAndWait();
            } else {
                if (deleteConfirmation("product")) {
                    Inventory.deleteProduct(product);
                    performDelete("product");
                }
            }
        } else {
            // Show an error message indicating that no product is selected
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please select a product to delete.");
            errorAlert.showAndWait();
        }
    }

    /**
     * Handles the action event when the "Exit Program" button is clicked.
     *
     * @param event The action event.
     */
    @FXML
    public void onActionExitProgram(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Handles the action event when the "Go to Add Part Screen" button is
     * clicked.
     *
     * @param event The action event.
     * @throws IOException If an error occurs during loading the FXML file.
     */
    @FXML
    public void onActionGoToAddPartScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Handles the action event when the "Go to Modify Part Screen" button is
     * clicked.
     *
     * @param event The action event.
     * @throws IOException If an error occurs during loading the FXML file.
     */
    @FXML
    public void onActionGoToModifyPartScreen(ActionEvent event) throws IOException {
        Part selectedPart = partsTableview.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            // Show an error message indicating that no part is selected
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please select a part to modify.");
            errorAlert.showAndWait();

        } else {
            index = Inventory.getAllParts().indexOf(selectedPart);

            Stage stage;
            Parent root;

            stage = (Stage) modifyPartsBTN.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            ModifyPartController controller = loader.getController();
            Part part = partsTableview.getSelectionModel().getSelectedItem();
            controller.setPart(part);

        }
    }

    /**
     * Handles the action event when the "Go to Add Product Screen" button is
     * clicked.
     *
     * @param event The action event.
     * @throws IOException If an error occurs during loading the FXML file.
     */
    @FXML
    public void onActionGoToAddProductScreen(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Handles the action event when the "Go to Modify Product Screen" button is
     * clicked.
     *
     * @param event The action event.
     * @throws IOException If an error occurs during loading the FXML file.
     */
    @FXML
    public void onActionGoToModifyProductScreen(ActionEvent event) throws IOException {
        Product selectedProduct = productsTableview.getSelectionModel().getSelectedItem();
        //index = Inventory.getAllProducts().indexOf(selectedProduct);
        if (selectedProduct == null) {
            // Show an error message indicating that no product is selected
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please select a product to modify.");
            errorAlert.showAndWait();
        } else {
            Stage stage;
            Parent root;

            stage = (Stage) modifyProductsBTN.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            ModifyProductController controller = loader.getController();
            Product product = productsTableview.getSelectionModel().getSelectedItem();
            controller.setProduct(product);
        }
    }

    /**
     * Handles the action event when the "Search Parts" button is clicked.
     *
     * @param event The action event.
     */
@FXML
public void onActionSearchParts(ActionEvent event) {
    String searchItem = searchPartsTxt.getText().toLowerCase();

    if (searchItem.isEmpty()) {
        partsTableview.setItems(Inventory.getAllParts());
    } else {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();

        for (Part part : Inventory.getAllParts()) {
            String partName = part.getName();

            if (partName.toLowerCase().contains(searchItem)) {
                matchingParts.add(part);
                partsTableview.getSelectionModel().select(part);
                partsTableview.requestFocus();
            }
        }

        if (matchingParts.isEmpty()) {
            // If no exact matches, search for partial matches
            for (Part part : Inventory.getAllParts()) {
                String partName = part.getName().toLowerCase();

                if (partName.startsWith(searchItem)) {
                    matchingParts.add(part);
                    partsTableview.getSelectionModel().select(part);
                    partsTableview.requestFocus();
                }
            }
        }

        if (matchingParts.isEmpty()) {
            // Check if the search input can be parsed as an integer
            try {
                int itemNumber = Integer.parseInt(searchItem);
                Part p = Inventory.lookupPart(itemNumber);
                if (p != null) {
                    matchingParts.add(p);
                    partsTableview.getSelectionModel().select(p);
                    partsTableview.requestFocus();
                }
            } catch (NumberFormatException e) {
                // Handle invalid input (non-numeric)
                errorMessage("Invalid input", "Please enter a valid part ID.");
                return;
            }
        }

        if (matchingParts.isEmpty()) {
            errorMessage("Item not found", "No parts found matching the search criteria.");
        } else {
            partsTableview.setItems(matchingParts);
        }
    }
}



    /**
     * Handles the action event when the "Search Products" button is clicked.
     *
     * @param event The action event.
     */
    @FXML
    public void onActionSearchProducts(ActionEvent event) {
        String searchItem = searchProductsTxt.getText();
        if (searchItem.isEmpty()) {
        productsTableview.setItems(Inventory.getAllProducts());
    } else {
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();

        for (Product product : Inventory.getAllProducts()) {
            String productName = product.getName().toLowerCase();

            if (productName.contains(searchItem)) {
                matchingProducts.add(product);
                 productsTableview.getSelectionModel().select(product);
                 productsTableview.requestFocus();
            }
        }

        if (matchingProducts.isEmpty()) {
            // If no exact matches, search for partial matches
            for (Product product : Inventory.getAllProducts()) {
                String productName = product.getName().toLowerCase();

                if (productName.startsWith(searchItem)) {
                    matchingProducts.add(product);
                     productsTableview.getSelectionModel().select(product);
                    productsTableview.requestFocus();
                }
            }
        }

        if (matchingProducts.isEmpty()) {
            // Check if the search input can be parsed as an integer
            try {
                int itemNumber = Integer.parseInt(searchItem);
                Product p = Inventory.lookupProduct(itemNumber);
                if (p != null) {
                    matchingProducts.add(p);
                    productsTableview.getSelectionModel().select(p);
                    productsTableview.requestFocus();
                }
            } catch (NumberFormatException e) {
                // Handle invalid input (non-numeric)
                errorMessage("Invalid input", "Please enter a valid part ID.");
                return;
            }
        }

        if (matchingProducts.isEmpty()) {
            errorMessage("Item not found", "No products found matching the search criteria.");
        } else {
            productsTableview.setItems(matchingProducts);
        }
    }
    }

    /**
     * Displays an error message with the specified title and content.
     *
     * @param title The title of the error message.
     * @param content The content text of the error message.
     */
    private void errorMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    

}
