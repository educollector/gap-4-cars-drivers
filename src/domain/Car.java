package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.javalite.activejdbc.Model;

/**
 * Created by olaskierbiszewska on 13.12.15.
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

    public String getBrand() {
        return getString("brand");
    }

    public void setBrand(String brand) {
        set("brand", brand);
    }

    public String getModel() {
        return getString("model");
    }

    public void setModel(String model) {
        set("model", model);
    }

    public Integer getYear() { return getInteger("year"); }

    public void setYear(Integer year) {
        set("year", year);
    }

    public String getVin() {
        return getString("vin");
    }

    public void setVin(String vin) {
        set("vin", vin);
    }

    @JsonIgnore
    public Integer getDriverId() {
        return getInteger("id_driver");
    }

    public void setDriverId(Integer driverId) {
        set("id_driver", driverId);
    }
}
