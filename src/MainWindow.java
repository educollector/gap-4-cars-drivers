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

    private AddDriverForm addDriverForm;

    public MainWindow() {
        super("App");
        pack();
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(600, 600);

        /** Import data from data base */
        drivers = new Driver().where("id >= ?", 0);

        /** Displaying data */
        //TODO select programatically first row on app start
        displayDrivers();
        clearCarsTable();

        /** LISTENERS TABLE DRIVERS */
        addDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDriverForm = new AddDriverForm(MainWindow.this);
                addDriverForm.setIsDriverMode(true);
                addDriverForm.setLabels();
            }
        });

        deleteDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableDrivers.getSelectedRow() > -1) {
                    int rowIndex = tableDrivers.getSelectedRow();
                    deleteDriver(rowIndex);
                }else{
                    showAlertWithMessage("Wybierz kierowcę i samochód");
                }
                clearCarsTable();
            }
        });

        editDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableDrivers.getSelectedRow() > -1) {
                    addDriverForm = new AddDriverForm(MainWindow.this);
                    Driver d = drivers.get(tableDrivers.getSelectedRow());
                    addDriverForm.setDriverToEdit(d); //set isEditMode=true,isDriverMode=true, set labels for editMode
                    addDriverForm.setLabels();
                } else {
                    showAlertWithMessage("Wybierz kierowcę");
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

        /** LISTENER TABLE CARS */
        addCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableDrivers.getSelectedRow() > -1) {
                    addDriverForm = new AddDriverForm(MainWindow.this);
                    addDriverForm.setIsDriverMode(false);
                    addDriverForm.setLabels();
                }else {
                    showAlertWithMessage("Wybierz kierowcę");
                }
            }
        });

        editCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((tableCars.getSelectedRow() > -1) && (tableDrivers.getSelectedRow() > -1)) {
                    addDriverForm = new AddDriverForm(MainWindow.this);
                    Driver d = drivers.get(tableDrivers.getSelectedRow());
                    Car c = d.getCars().get(tableCars.getSelectedRow());
                    addDriverForm.setCarToEdit(c); //set isEditMode=true,isDriverMode=false, set labels for editMode
                    addDriverForm.setLabels();
                }else {
                    showAlertWithMessage("Wybierz kierowcę i samochód");
                }

            }
        });

        deleteCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCar();
            }
        });

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
        CarTableModel carModel = new CarTableModel(dummyMacData);
        tableCars = new JTable(carModel);
        tableCars.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        Vector dummyDataDriver = new Vector(10, 10);
        DriverTableModel driverModel = new DriverTableModel(dummyDataDriver);
        tableDrivers = new JTable(driverModel);
        tableDrivers.setRowSelectionAllowed(true);
        tableDrivers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /** Driver CRUD methods */
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
        d.delete();
        displayDrivers();
    }

    /** Car CRUD methods */
    public void addCar(Car c){
        if (tableDrivers.getSelectedRow() > -1) {
            Driver d = drivers.get(tableDrivers.getSelectedRow());
            c.setDriverID(d.getId());
            c.save();
            d.getCars().add(c);
            displayCars(d);
        }
    }

    public void saveEditedCar(Car c){

    }

    public void deleteCar(){
        if ((tableDrivers.getSelectedRow() > -1) && (tableCars.getSelectedRow() > -1)) {
            Driver d = drivers.get(tableDrivers.getSelectedRow());
            Car c = d.getCars().get(tableCars.getSelectedRow());
            c.delete(); //delete from db
            d.getCars().remove(c); //remove c from local drivers list - is it necessary?
            displayCars(d);
        }else {
            showAlertWithMessage("Wybierz kierowcę i samochód");
        }
    }

    /** Display data methods */
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

    private void showAlertWithMessage(String message){
        String[] options = {"OK"};
        JPanel panel = new JPanel();
        JLabel lbl = new JLabel(message);
        panel.add(lbl);
        JOptionPane.showOptionDialog(null,
                panel,
                "Informacja",
                JOptionPane.NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options , options[0]);
    }
}
