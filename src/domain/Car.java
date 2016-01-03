package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.javalite.activejdbc.Model;

/**
 * Car entity, has refference to Driver via {@link Car# setDriverId(Integer driverId)}.
 * All properties are saved to the data base when save() is called on the instance of the object (as a result of inheritance Model)
 * @author Ola Skierbiszewska
 * @version 1.0 Build Jan 5, 2016.
 */
public class Car extends Model {

    public Car() {
    }

    public Car(String brand, String model, Integer year, String vin, Integer driverId) {
        setBrand(brand);
        setModel(model);
        setYear(year);
        setVin(vin);
        setDriverId(driverId);
    }

    /**
     * @return string value for car brand
     */
    public String getBrand() {
        return getString("brand");
    }
    /**
     * Set brand of the car
     * @param brand string value for car brand
     */
    public void setBrand(String brand) {
        set("brand", brand);
    }
    /**
     * @return string value for car model
     */
    public String getModel() {
        return getString("model");
    }

    /**
     *
     * @param model a car model
     */
    public void setModel(String model) {
        set("model", model);
    }

    /**
     * @return integer value for car production year
     */
    public Integer getYear() { return getInteger("year"); }

    /**
     *
     * @param year a car production year
     */
    public void setYear(Integer year) {
        set("year", year);
    }

    /**
     * @return string value for car vehicle identification number
     */
    public String getVin() {
        return getString("vin");
    }

    /**
     *
     * @param vin a car vehicle identification number
     */
    public void setVin(String vin) {
        set("vin", vin);
    }

    /**
     * @return integer value of car's driver id
     */
    @JsonIgnore
    public Integer getDriverId() {
        return getInteger("id_driver");
    }

    /**
     *
     * @param driverId  integer value of car's driver id
     */
    public void setDriverId(Integer driverId) {
        set("id_driver", driverId);
    }
}
