import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import domain.Car;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;

import java.io.IOException;

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
        car.setModel("Toyota");
        car.setBrand("Supra");

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
