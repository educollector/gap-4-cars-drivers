package domain; /**
 * Created by olaskierbiszewska on 13.12.15.
 */
import org.javalite.activejdbc.Model;

import java.util.List;

public class Driver extends Model {

    public Driver() {
    }

    public Driver(String name, String surname, Integer age, String info, List<Car> cars) {

        setName(name);
        setSurname(surname);
        setAge(age);
        setInfo(info);
        setCars(cars);
    }

    public Driver(String name, String surname, Integer age, String info) {
        setName(name);
        setSurname(surname);
        setAge(age);
        setInfo(info);
    }

    public Integer getId() { return getInteger("id"); }

    public void setId(Integer id) { set("id", id); }

    public String getName() { return getString("name"); }

    public void setName(String name) { set("name", name); }

    public String getSurname() { return getString("surname"); }

    public void setSurname(String surname) { set("surname", surname); }

    public Integer getAge() { return getInteger("age"); }

    public void setAge(Integer age) { set("age", age); }

    public String getInfo() { return getString("info"); }

    public void setInfo(String info) { set("info", info); }

    public List<Car> getCars() {
        Integer driverId = getInteger("id");
        Car c = new Car();
        List<Car> cars = c.where("id_driver = ?", driverId);
        return cars;
    }

    public void setCars(List<Car> cars) {
        for(Car car : cars){
        }
    }

}
