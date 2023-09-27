/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package softwareinv;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *The Product class represents a product in the inventory management system.
 * It contains information about the product, such as ID, name, price, stock,
 * and associated parts.
 * @author fordjour andy
 */
public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
     /**
     * Constructs a new Product with the specified properties.
     *
     * @param id     The ID of the product.
     * @param name   The name of the product.
     * @param price  The price of the product.
     * @param stock  The current stock level of the product.
     * @param min    The minimum stock level required for the product.
     * @param max    The maximum stock level allowed for the product.
     */

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = FXCollections.observableArrayList();
    }
/**
     * Sets the ID of the product.
     *
     * @param id The ID of the product.
     */
    public void setId(int id) {
        this.id = id;
    }
 /**
     * Sets the name of the product.
     *
     * @param name The name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }
/**
     * Sets the price of the product.
     *
     * @param price The price of the product.
     */
    public void setPrice(double price) {
        this.price = price;
    }
/**
     * Sets the stock level of the product.
     *
     * @param stock The stock level of the product.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
/**
     * Sets the minimum stock level required for the product.
     *
     * @param min The minimum stock level required for the product.
     */
    public void setMin(int min) {
        this.min = min;
    }
 /**
     * Sets the maximum stock level allowed for the product.
     *
     * @param max The maximum stock level allowed for the product.
     */
    public void setMax(int max) {
        this.max = max;
    }
/**
     * Returns the ID of the product.
     *
     * @return The ID of the product.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the product.
     *
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }
/**
     * Returns the price of the product.
     *
     * @return The price of the product.
     */
    public double getPrice() {
        return price;
    }
 /**
     * Returns the current stock level of the product.
     *
     * @return The current stock level of the product.
     */
    public int getStock() {
        return stock;
    }
 /**
     * Returns the minimum stock level required for the product.
     *
     * @return The minimum stock level required for the product.
     */
    public int getMin() {
        return min;
    }
/**
     * Returns the maximum stock level allowed for the product.
     *
     * @return The maximum stock level allowed for the product.
     */
    public int getMax() {
        return max;
    }
/**
     * Adds an associated part to the product.
     *
     * @param part The associated part to add.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
/**
     * Deletes an associated part from the product.
     *
     * @param selectedAssociatedPart The associated part to delete.
     * @return true if the associated part is successfully deleted, false otherwise.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }
 /**
     * Returns an observable list of all associated parts of the product.
     *
     * @return An observable list of all associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
