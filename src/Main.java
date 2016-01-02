import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Car;
import domain.Driver;
import org.javalite.activejdbc.Base;

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

        // creates window and starts AWT thread
        new MainWindow();
    }
}
