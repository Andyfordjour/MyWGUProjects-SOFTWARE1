/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package softwareinv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * The `Main` class is the Main entry point for the application. It extends the
 * `Application` class and provides the `start` method to initialize and display
 * the Main Main. "Future Enhancement" Implement Logging: Introduce a logging
 * framework, such as Java's built-in java.util.logging or a third-party library
 * like Log4j or SLF4J. Logging can help capture and record important
 * information, including exceptions, warnings, and debug messages. It allows
 * you to analyze and troubleshoot issues more effectively.
 *
 * @see <a href="../dist/javadoc/index.html">SoftwareInv</a> for more details
 * * @author fordjour andy
 *
 */
public class Main extends Application {

    /**
     * Initializes and displays the Main Main of the application.
     *
     * @param stage The primary stage for the application.
     * @throws Exception If an error occurs during the initialization or loading
     * of the Main Main. 'Runtime error" i had a problem trying to load the Main
     * fxml file due to the path so i used the relative path
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }
    /**
     * The main method used for testing the Inventory functionality.
     *
     * @param args The command-line arguments.
     */
    private static int nextPartId = 1;
    private static int nextProductId = 1000;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        InHouse part1 = createPart("Brakes", 20.00, 5, 1, 12, 2045);
        InHouse part2 = createPart("Tire", 150.11, 9, 3, 16, 2089);
        InHouse part3 = createPart("Rim", 2036.56, 5, 1, 10, 3075);
        InHouse part4 = createPart("Steer", 2.99, 17, 6, 18, 9044);

        Product product1 = createProduct("Giant Bike", 299.27, 4, 2, 5);
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part2);

        Product product2 = createProduct("Tricycle", 99.01, 3, 2, 4);
        product2.addAssociatedPart(part3);
        product2.addAssociatedPart(part4);

        
        launch(args);
    }

    /**
     * Creates an InHouse part with the specified attributes, adds it to the
     * inventory, and returns the created part.
     *
     * @param name The name of the part.
     * @param price The price of the part.
     * @param stock The current stock of the part.
     * @param min The minimum stock of the part.
     * @param max The maximum stock of the part.
     * @param machineId The ID of the machine associated with the part.
     * @return The created InHouse part.
     */
    private static InHouse createPart(String name, double price, int stock, int min, int max, int machineId) {
        InHouse part = new InHouse(nextPartId++, name, price, stock, min, max, machineId);
        Inventory.addPart(part);
        return part;
    }

    /**
     * Creates a Product with the specified attributes, adds it to the
     * inventory, and returns the created product.
     *
     * @param name The name of the product.
     * @param price The price of the product.
     * @param stock The current stock of the product.
     * @param min The minimum stock of the product.
     * @param max The maximum stock of the product.
     * @return The created Product.
     */
    private static Product createProduct(String name, double price, int stock, int min, int max) {
        Product product = new Product(nextProductId++, name, price, stock, min, max);
        Inventory.addProduct(product);
        return product;
    }

    /**
     * Removes a part from the inventory and adjusts the nextPartId if
     * necessary.
     *
     * @param part The part to be removed.
     */
    public static void removePart(Part part) {
        Inventory.deletePart(part);
        // Adjust the nextPartId if the deleted part's ID is lower than the current nextPartId
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
}
