import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import domain.Car;
import domain.Driver;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olaskierbiszewska on 13.12.15.
 */
public class Main {

    private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final static String JDBC_URL = "jdbc:mysql://localhost:3306/wwsiti";
    private final static String DB_USER = "wwsiti";
    private final static String DB_PASS = "wwsi2015";

    public static void main(String[] args) {

        Base.open(JDBC_DRIVER, JDBC_URL, DB_USER, DB_PASS);
        EventQueue.invokeLater(() -> Base.open(JDBC_DRIVER, JDBC_URL, DB_USER, DB_PASS));

        ObjectMapper mapper = new ObjectMapper().setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
            @Override
            public boolean hasIgnoreMarker(final AnnotatedMember m) {
                return m.getDeclaringClass() == Model.class || super.hasIgnoreMarker(m);
            }
        });

        Car car = new Car();
        car.setYear(1233);
        car.setModel("BMW");
        car.setBrand("Z3");
        car.setDriverID(1);
//        car.saveIt();
        List<Car> cars =  new ArrayList<Car>();
        cars.add(car);

        Driver driver = new Driver("Marek", "Nowak", 25, "Brak info");
//        driver.saveIt();

        try {
            String json = mapper.writeValueAsString(car);
            System.out.println(json);

            String carJson = "{\"year\":1233,\"model\":\"Toyota\",\"brand\":\"Supra\",\"vin\":null}";
            Car carFromJson = mapper.readValue(carJson, Car.class);
            System.out.println(carFromJson.toString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // creates window and starts AWT thread
        new MainWindow();
    }
}
