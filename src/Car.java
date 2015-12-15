import org.javalite.activejdbc.Model;

import java.io.Serializable;

/**
 * Created by olaskierbiszewska on 13.12.15.
 */
public class Car extends Model  implements Serializable {
    private String brand;
    private  String model;
    private Integer year;
    private String vim;

    public Car() {
    }

    public Car(String brand, String model, Integer year, String vim) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.vim = vim;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getVim() {
        return vim;
    }

    public void setVim(String vim) {
        this.vim = vim;
    }
}
