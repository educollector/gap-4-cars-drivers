import domain.Car;
import domain.Driver;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

    private JButton addDriverButton;
    private JButton deleteDriverButton;
    private JButton editDriverButton;

    private JButton addCarButton;
    private JButton deleteCarButton;
    private JButton editCarButton;
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
        //cars = new Car().where("id >= ?, 0");

        //DISPLAY DATA
        //TODO select programatically first row on app start
        displayDrivers();
        clearCarsTable();

        //LISTENERS TABLE DRIVERS
        addDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDriverForm = new AddDriverForm(MainWindow.this);
                addDriverForm.setIsEditMode(false);
                addDriverForm.setDriverToEdit(null);
            }
        });

        deleteDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableDrivers.getSelectedRow() > -1) {
                    int rowIndex = tableDrivers.getSelectedRow();
                    deleteDriver(rowIndex);
                }
                clearCarsTable();
            }
        });

        editDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableDrivers.getSelectedRow() > -1) {
                    addDriverForm = new AddDriverForm(MainWindow.this);
                    addDriverForm.setIsEditMode(true);
                    Driver d = drivers.get(tableDrivers.getSelectedRow());
                    System.out.print(  System.identityHashCode(d)+ "\n");
                    addDriverForm.setDriverToEdit(d);
                    addDriverForm.setValuesForEditMode();
                }
            }
        });

        tableDrivers.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (tableDrivers.getSelectedRow() > -1) {
                    // print first column value from selected row
                    System.out.println(tableDrivers.getValueAt(tableDrivers.getSelectedRow(), 0).toString());
                    Driver d = drivers.get(tableDrivers.getSelectedRow());
                    d.refresh();
                    displayCars(d);
                }
            }
        });

        //LISTENER TABLE CARS
        tableCars.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (tableCars.getSelectedRow() > -1) {
                    // print first column value from selected row
                    System.out.println(tableCars.getValueAt(tableCars.getSelectedRow(), 0).toString());
                }
            }
        });
    }

    private void createUIComponents() {
        // custom component creation code here
        Vector dummyMacData = new Vector(10, 10);
        dummyMacData.addElement(new Car("Toyota", "Avensis", new Integer(2011), "398389", 1));
        CarTableModel carModel = new CarTableModel(dummyMacData);
        tableCars = new JTable(carModel);


        Vector dummyDataDriver = new Vector(10, 10);
        DriverTableModel driverModel = new DriverTableModel(dummyDataDriver);
        tableDrivers = new JTable(driverModel);
        tableDrivers.setRowSelectionAllowed(true);
        tableDrivers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void addDriver(Driver d){
        drivers.add(d);
        displayDrivers();
    }

    public void saveEditedDriver(Driver d){
        System.out.print(System.identityHashCode(d)+ "\n");
        displayDrivers();
    }

    public void deleteDriver(int index){
        Driver d = drivers.get(index);
        drivers.remove(index);
        d.delete();//TODO: save change
        displayDrivers();
    }


    private void displayDrivers(){
        DriverTableModel driversModel = (DriverTableModel) tableDrivers.getModel();
        driversModel.m_macDataVector.clear();
        for(Driver d : drivers){
            driversModel.m_macDataVector.addElement(d);
        }
        tableDrivers.setModel(driversModel);
        driversModel.fireTableDataChanged();
    }

    private void displayCars(Driver d){
        CarTableModel carsModel = (CarTableModel) tableCars.getModel();
        carsModel.m_macDataVector.clear();
        for(Car c : d.getCars()){
            carsModel.m_macDataVector.addElement(c);
        }
        tableCars.setModel(carsModel);
        carsModel.fireTableDataChanged();

    }

    private void clearCarsTable(){
        CarTableModel carsModel = (CarTableModel) tableCars.getModel();
        carsModel.m_macDataVector.clear();
        tableCars.setModel(carsModel);
        carsModel.fireTableDataChanged();

    }
}
