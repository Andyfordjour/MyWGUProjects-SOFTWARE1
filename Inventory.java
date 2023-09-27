/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package softwareinv;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * The Inventory class represents the inventory management system. It provides
 * methods to add, update, delete, and lookup parts and products. This class
 * utilizes JavaFX ObservableList for storing parts and products.
 *
 * @author fordjour andy
 */
public class Inventory {

    /**
     *
     * A static ObservableList of Part objects used to store all the parts.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * A static ObservableList of products used to store all the parts
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a part to the inventory.
     *
     * @param part The part to be added.
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /**
     * Adds a product to the inventory.
     *
     * @param product The product to be added.
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**
     * Looks up a part by its ID.
     *
     * @param partID The ID of the part to be looked up.
     * @return The part with the specified ID, or null if not found.
     */
    public static Part lookupPart(int partID) {
        for (Part part : getAllParts()) {
            if (part.getId() == partID) {
                return part;
            }
        }
        return null;
    }

    /**
     * Looks up a product by its ID.
     *
     * @param productID The ID of the product to be looked up.
     * @return The product with the specified ID, or null if not found.
     */
    public static Product lookupProduct(int productID) {
        for (Product product : getAllProducts()) {
            if (product.getId() == productID) {
                return product;
            }
        }
        return null;
    }

    /**
     * Looks up parts by their name.
     *
     * @param partName The name of the parts to be looked up.
     * @return A list of parts with the specified name, or an empty list if not
     * found.
    *
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        for (Part part : getAllParts()) {
            if (part.getName().equals(partName)) {
                foundParts.add(part);
            }
        }
        return foundParts;
    }

    /**
     * Looks up products by their name.
     *
     * @param productName The name of the products to be looked up.
     * @return A list of products with the specified name, or an empty list if
     * not found.
     *
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        for (Product product : getAllProducts()) {
            if (product.getName().equals(productName)) {
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }

    /**
     * Updates a part at the specified index.
     *
     * @param index The index of the part to be updated.
     * @param selectedPart The updated part.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates a product at the specified index.
     *
     * @param index The index of the product to be updated.
     * @param newProduct The updated product.
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes a part from the inventory.
     *
     * @param selectedPart The part to be deleted.
     * @return True if the part was successfully deleted, false otherwise.
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes a product from the inventory.
     *
     * @param selectedProduct The product to be deleted.
     * @return True if the product was successfully
     *
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Retrieves all parts in the inventory.
     *
     * @return An ObservableList of all parts.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Retrieves all products in the inventory.
     *
     * @return An ObservableList of all products.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
