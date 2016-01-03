import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Car;
import domain.Driver;

import javax.swing.*;
import java.io.*;
import java.util.List;
import java.util.Vector;

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
    private JButton readFileButton;
    private JButton writeToFileButton;
    private List<Driver> drivers;
    private List<Driver>driversFromFile;

    private AddDriverForm addDriverForm;

    /**
     * Creates main app window and registers all required listeners inside.
     */
    public MainWindow() {
        super("App");
        pack();
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(600, 600);

        /* Import data from data base */
        drivers = new Driver().where("id >= ?", 0);

        /* Displaying data */
        displayDrivers();
        clearCarsTable();

        /* LISTENERS TABLE DRIVERS */
        addDriverButton.addActionListener(e -> {
            addDriverForm = new AddDriverForm(MainWindow.this);
            addDriverForm.setIsDriverMode(true);
            addDriverForm.setLabels();
        });

        deleteDriverButton.addActionListener(e -> {
            if (tableDrivers.getSelectedRow() > -1) {
                int rowIndex = tableDrivers.getSelectedRow();
                deleteDriver(rowIndex);
            }else{
                showAlertWithMessage("Wybierz kierowcę");
            }
            clearCarsTable();
        });

        editDriverButton.addActionListener(e -> {
            if (tableDrivers.getSelectedRow() > -1) {
                addDriverForm = new AddDriverForm(MainWindow.this);
                Driver d = drivers.get(tableDrivers.getSelectedRow());
                addDriverForm.setDriverToEdit(d); //set isEditMode=true,isDriverMode=true, set labels for editMode
                addDriverForm.setLabels();
            } else {
                showAlertWithMessage("Wybierz kierowcę");
            }
        });

        tableDrivers.getSelectionModel().addListSelectionListener(event -> {
            if (tableDrivers.getSelectedRow() > -1) {
                // print first column value from selected row
                System.out.println(tableDrivers.getValueAt(tableDrivers.getSelectedRow(), 0).toString());
                Driver d = drivers.get(tableDrivers.getSelectedRow());
                displayCars(d);
            }
        });

        /* LISTENER TABLE CARS */
        addCarButton.addActionListener(e -> {
            if (tableDrivers.getSelectedRow() > -1) {
                addDriverForm = new AddDriverForm(MainWindow.this);
                addDriverForm.setIsDriverMode(false);
                addDriverForm.setLabels();
            }else {
                showAlertWithMessage("Wybierz kierowcę");
            }
        });

        editCarButton.addActionListener(e -> {
            if ((tableCars.getSelectedRow() > -1) && (tableDrivers.getSelectedRow() > -1)) {
                addDriverForm = new AddDriverForm(MainWindow.this);
                Driver d = drivers.get(tableDrivers.getSelectedRow());
                Car c = d.getCars().get(tableCars.getSelectedRow());
                addDriverForm.setCarToEdit(c); //set isEditMode=true,isDriverMode=false, set labels for editMode
                addDriverForm.setLabels();
            }else {
                showAlertWithMessage("Wybierz kierowcę i samochód");
            }
        });

        deleteCarButton.addActionListener(e -> deleteCar());

        tableCars.getSelectionModel().addListSelectionListener(event -> {
            if (tableCars.getSelectedRow() > -1) {
                // print first column value from selected row
                System.out.println(tableCars.getValueAt(tableCars.getSelectedRow(), 0).toString());
            }
        });

        /* LISTENER FILE OPERATIONS' BUTTONS */
        readFileButton.addActionListener(e -> {
            //Create a file chooser
            final JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(MainWindow.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would open the file.
                System.out.print("Wybrano plik do wczytania danych: " + file.getName() + "." + "\n");
                readDataFromFile(file.getAbsolutePath());
            } else {
                System.out.print("Open command cancelled by user."  +  "\n");
            }
            displayDrivers();
        });

        writeToFileButton.addActionListener(e -> {
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Wybierz plik");
            int userSelection = fileChooser.showSaveDialog(MainWindow.this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                saveDataToFile(fileToSave.getAbsolutePath());
            }
        });

        /* LISTENER TO THIS WINDOW*/

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                //MainWindow.this.showAlertWithMessage("Aplikacja zostanie zamknięta a dane zapisane do pliku");
                saveDataToFile("Data.txt");
            }
        });
    }

    private void createUIComponents() {
        // custom component creation code here
        Vector dummyMacData = new Vector(10, 10);
        CarTableModel carModel = new CarTableModel(dummyMacData);
        tableCars = new JTable(carModel);
        tableCars.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableCars.getColumnModel().getColumn(2).setPreferredWidth(5);


        Vector dummyDataDriver = new Vector(10, 10);
        DriverTableModel driverModel = new DriverTableModel(dummyDataDriver);
        tableDrivers = new JTable(driverModel);
        tableDrivers.setRowSelectionAllowed(true);
        tableDrivers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableDrivers.getColumnModel().getColumn(2).setPreferredWidth(5);
    }

    /* Driver CRUD methods */

    /**
     * <p>Adds a driver d to data source od Drivers list a and reload teh list to display current data</p>
     * @param d the Driver object saved previously in data base via {@link Driver#save()}
     */
    public void addDriver(Driver d){
        drivers.add(d);
        displayDrivers();
    }

    /**
     *<p>Reloads Drivers list adding current data to Driver table model</p>
     */
    public void reloadDriversTable(){
        displayDrivers();
    }

    /**
     * <p>Deletes a driver from Drivers list and from database based on the index of the driver on the list.</p>
     * <p>After deletion the Drivers list is reloaded to display changes</p>
     * @param index the index of the driver to delete (based on its position on the Drivers list)
     */
    public void deleteDriver(int index){
        Driver d = drivers.get(index);
        drivers.remove(index);
        d.delete();
        displayDrivers();
    }

    /**
     * <p>Adds new car to the current data set.</p>
     * <p>Car has to reference existing driver via {@link Car#setDriverId(Integer)}.</p>
     * @param c a car to add
     */
    public void addCar(Car c){
        if (tableDrivers.getSelectedRow() > -1) {
            Driver d = drivers.get(tableDrivers.getSelectedRow());
            c.setDriverId(d.getDriverId());
            c.save();
            d.getCars().add(c);
            displayCars(d);
        }
    }

    /**
     * <p>Reloads data in Cars list for the driver selected on teh Drivers list</p>
     */
    public void reloadCarsTable(){
        if (tableDrivers.getSelectedRow() > -1) {
            Driver d = drivers.get(tableDrivers.getSelectedRow()); //or get Driver from drivers wher id=id_driver from c
            displayCars(d);
        }
    }

    /**
     * <p>Deletes the car whe one is selected on Cars list.</p>
     * <p>The car references to its driver via {@link Car#setDriverId(Integer)}.
     * The car is deleted from database and from its driver Cars list.
     * </p>
     */
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

    /*Display data methods */
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

    /* Saving and reading to file */
    private void saveDataToFile(String filePath){
        final ObjectMapper mapper = MapperSingleton.get();
        try {
            File file = new File(filePath);
            if (!file.exists()) file.createNewFile();
            mapper.writeValue(file, drivers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readDataFromFile(String filePath){
        final ObjectMapper mapper = MapperSingleton.get();
        try{
            String jsonString = readFile(filePath);
            driversFromFile = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, Driver.class));
            for (Driver d : driversFromFile) {
                d.saveIt();
            }
            drivers.addAll(driversFromFile);
            showAlertWithMessage("Wczytano dane z pliku");
            System.out.println("Successfully read JSON Object from File...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads file contents into String object
     *
     * @param filePath full path to file, eg: /data/files/myfile.json
     * @return String file contents
     * @throws IOException when IO operation fails
     */
    public String readFile(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
}
