/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package softwareinv;

/**
 *
 * The `InHouse` class represents a part that is manufactured in-house. It is a
 * subclass of the `Part` class and includes additional properties specific to
 * in-house parts, such as the machine ID "Future Enhancement" To extend the
 * functionality of the application, a possible enhancement could be to
 * implement a serialization mechanism for the InHouse class. This would allow
 * the part objects to be serialized and stored in a file or database, enabling
 * persistence of part data across multiple sessions.
 *
 * * @author fordjour andy
 */
public class InHouse extends Part {

    /**
     * private machineId initialized
     */
    private int machineId;

    /**
     *
     * Constructs an InHouse object with the specified properties.
     *
     * @param id The ID of the part.
     * @param name The name of the part.
     * @param price The price of the part.
     * @param stock The number of parts in stock.
     * @param min The minimum allowed quantity of the part.
     * @param max The maximum allowed quantity of the part.
     * @param machineId The ID of the machine used to manufacture the part.
     * Logical Error: The min and max values need to be validated to ensure data
     * integrity, so a validator should be implemented.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Sets the machine ID of this product.
     *
     * @param machineId the machine ID to be set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     *
     * Returns the ID of the machine used to manufacture the part.
     *
     * @return The machine ID.
     */
    public int getMachineId() {
        return machineId;
    }
}
