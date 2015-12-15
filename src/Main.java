import org.javalite.activejdbc.Base;

/**
 * Created by olaskierbiszewska on 13.12.15.
 */
public class Main {
    public static void main(String[] args) {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/wwsiti", "wwsiti", "wwsi2015");
        MainWindow okno = new MainWindow();
    }
}
