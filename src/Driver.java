/**
 * Created by olaskierbiszewska on 13.12.15.
 */
import org.javalite.activejdbc.Model;

import java.io.Serializable;
import java.util.List;

public class Driver extends Model implements Serializable {

    private String name;
    private String surname;
    private Integer age;
    private String info;

    private List<Car> cars;

    public Driver() {
        this.name = "testName";
        this.surname = "testSurname";
        this.age = 111;
        this.info = "testInfo";
    }

    public Driver(String name, String surname, Integer age, String info) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.info = info;
    }

    public Driver(String name, String surname, Integer age, String info, List<Car> cars) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.info = info;
        this.cars = cars;
    }

    public String getName() { return name; }

    public void setName(String name) {  this.name = name; }

    public String getSurname() {  return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public Integer getAge() { return age; }

    public void setAge(Integer age) { this.age = age; }

    public String getInfo() { return info; }

    public void setInfo(String info) { this.info = info; }

    public List<Car> getCars() {  return cars; }

    public void setCars(List<Car> cars) {  this.cars = cars; }

}
