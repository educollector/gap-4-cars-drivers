package domain; /**
 * Created by olaskierbiszewska on 13.12.15.
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.javalite.activejdbc.Model;

import java.util.List;

public class Driver extends Model {

    private List<Car> carList;

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

    @JsonIgnore
    public Integer getDriverId() { return getInteger("id"); }

    public void setDriverId(Integer id) { set("id", id); }

    public String getName() { return getString("name"); }

    public void setName(String name) { set("name", name); }

    public String getSurname() { return getString("surname"); }

    public void setSurname(String surname) { set("surname", surname); }

    public Integer getAge() { return getInteger("age"); }

    public void setAge(Integer age) { set("age", age); }

    public String getInfo() { return getString("info"); }

    public void setInfo(String info) { set("info", info); }

    public List<Car> getCars() {
        if (carList == null) {
            refresh();
        }
        return carList;
    }

    public void setCars(List<Car> cars) {
        this.carList = cars;
    }

    public void refresh() {
        Integer driverId = getDriverId();
        this.carList = new Car().where("id_driver = ?", driverId);
    }

    /** Do uzycia przy zapisie do bazy po wczytaniu z pliku */
    @Override
    public boolean saveIt() {
        final boolean flgSaved = super.saveIt();
        final int driverId = getDriverId();
        if (carList != null) {
            for (Car car : carList) {
                car.setDriverId(driverId);
                car.saveIt();
            }
        }
        return flgSaved;
    }

    @Override
    public boolean delete() {
        if (carList != null) {
            for (Car car : carList) {
                car.delete();
            }
        }
        return super.delete();
    }
}
