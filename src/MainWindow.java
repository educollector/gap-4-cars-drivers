import domain.Car;
import domain.Driver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by olaskierbiszewska on 13.12.15.
 */
public class MainWindow extends JFrame {
    private JPanel rootPanel;
    private JTable tableDrivers;
    private JTable tableCars;
    private JButton addCarButton;
    private JButton deleteCarButton;
    private JButton editCarButton;
    private JButton addDriverButton;
    private JButton deleteDriverButton;
    private JButton editDriverButton;
    private List<Driver> drivers;
    private List<Car> cars;

    private AddDriverForm addDriverForm;

    public MainWindow() {
        super("App");
        pack();
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(600, 600);

        //import data from databse
        drivers = new Driver().where("id >= ?", 0);
        cars = new Car().where("id >= ?, 0");

        for(Driver d : drivers){

            Object[] row = {d.getName(), d.getSurname(), d.getAge(), d.getInfo()};

            DriverTableModel model = (DriverTableModel) tableDrivers.getModel();

            model.(row);


            List<Car> cars = new ArrayList<Car>();
            for(Car c : cars){
//                if(d.id == c.getDriverId()){
//
//                }
            }
        }

        addDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDriverForm = new AddDriverForm(MainWindow.this);
            }
        });
    }

    public void addDriver(Driver d){
        //TODO: save "d"
        CarTableModel tableModel = (CarTableModel) tableCars.getModel();
        tableModel.fireTableDataChanged();
    }


    private void createUIComponents() {
        // custom component creation code here
        Vector dummyMacData = new Vector(10, 10);
        dummyMacData.addElement(new Car("Toyota", "Corolla", new Integer(2011), "398389", 1));
        CarTableModel carModel = new CarTableModel(dummyMacData);
        tableCars = new JTable(carModel);

        Vector dummyDataDriver = new Vector(10, 10);
        dummyDataDriver.addElement(new Driver("Jan", "Kowalski", new Integer(34), "Brak uwag"));
        DriverTableModel driverModel = new DriverTableModel(dummyDataDriver);
        tableDrivers = new JTable(driverModel);

        tableDrivers.getColumnModel().getColumn(1).setHeaderValue("newHeader");
    }
}
