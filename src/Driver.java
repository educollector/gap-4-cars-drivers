/**
 * Created by olaskierbiszewska on 13.12.15.
 */
public class Driver {

    private String name;
    private  String surname;
    private Integer age;
    private String info;

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

    public String getName() { return name; }

    public void setName(String name) {  this.name = name; }

    public String getSurname() {  return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public Integer getAge() { return age; }

    public void setAge(Integer age) { this.age = age; }

    public String getInfo() { return info; }

    public void setInfo(String info) { this.info = info; }

}
