/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package softwareinv;

/**
 ** The Outsourced class represents a part that is obtained from an external
 * company. It extends the Part class and adds a company name field.
 *
 * @author fordjour andy
 */
public class Outsourced extends Part {

    private String companyName;

    /**
     * Constructs a new Outsourced part with the specified properties.
     *
     * @param id The ID of the part.
     * @param name The name of the part.
     * @param price The price of the part.
     * @param stock The current stock level of the part.
     * @param min The minimum stock level required for the part.
     * @param max The maximum stock level allowed for the part.
     * @param companyName The name of the company supplying the part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets the company name for the outsourced part.
     *
     * @param companyName The name of the company supplying the part.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Returns the company name of the outsourced part.
     *
     * @return The name of the company supplying the part.
     */
    public String getCompanyName() {
        return companyName;
    }
}
