import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import domain.Car;
import domain.Driver;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olaskierbiszewska on 13.12.15.
 */
public class Main {
    public static void main(String[] args) {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/wwsiti", "wwsiti", "wwsi2015");

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
        car.saveIt();
        List<Car> cars =  new ArrayList<Car>();
        cars.add(car);

        Driver driver = new Driver("Marek", "Nowak", 25, "Brak info");
        driver.saveIt();

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


        MainWindow okno = new MainWindow();
    }
}
