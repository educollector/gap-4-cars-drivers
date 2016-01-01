import domain.Car;
import domain.Driver;

import javax.swing.*;
import javax.swing.table.JTableHeader;
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

        //DISPLAY DATA
        displayDrivers();

        //LISTENERS
        addDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDriverForm = new AddDriverForm(MainWindow.this);
            }
        });
    }



    private void createUIComponents() {
        // custom component creation code here
        Vector dummyMacData = new Vector(10, 10);
        dummyMacData.addElement(new Car("Toyota", "Corolla", new Integer(2011), "398389", 1));
        CarTableModel carModel = new CarTableModel(dummyMacData);
        tableCars = new JTable(carModel);


        Vector dummyDataDriver = new Vector(10, 10);
        DriverTableModel driverModel = new DriverTableModel(dummyDataDriver);
        tableDrivers = new JTable(driverModel);
        JTableHeader header =  tableDrivers.getTableHeader();
    }

    private void displayDrivers(){
        DriverTableModel driversModel = (DriverTableModel) tableDrivers.getModel();
        driversModel.m_macDataVector.clear();
        for(Driver d : drivers){
            driversModel.m_macDataVector.addElement(d);
//            List<Car> cars = new ArrayList<Car>();
//            for(Car c : cars){
//                if(d.id == c.getDriverId()){
//                }
//            }
        }
        tableDrivers.setModel(driversModel);
        tableDrivers.repaint();

    }

    public void addDriver(Driver d){
        //TODO: save "d"
        drivers.add(d);
        displayDrivers();
//        DriverTableModel tableModel = (DriverTableModel) tableCars.getModel();
//        tableModel.fireTableDataChanged();
    }





}
