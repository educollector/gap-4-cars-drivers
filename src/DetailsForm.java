import domain.Car;
import domain.Driver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a JFrame of the add/edit window with fields representing added/edited entity.
 * The window provides checking if all text fields are fulfilled and a button to save data.
 * @author Ola Skierbiszewska
 * @version 1.0 Build Jan 5, 2016.
 */

public class DetailsForm extends JFrame {
    private JPanel rootPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton dodajButton;
    private JLabel nameOrBrand;
    private JLabel surnameOrModel;
    private JLabel ageOrYear;
    private JLabel infoOrVim;
    private MainWindow mainWindow;

    private boolean isDriverMode;
    private boolean isEditMode;
    private Driver driverToEdit;
    private Car carToEdit;

    /**
     * Sets the mode of teh window according to the entity type
     * @param isDriverMode boolean value representing mode, if true it is driver mode, if false - car mode
     */
    public void setIsDriverMode(Boolean isDriverMode) { this.isDriverMode = isDriverMode; }

    /**
     * Sets the driverToEdit property
     * @param driverToEdit the object that is to be edited
     */
    public void setDriverToEdit(Driver driverToEdit) {
        this.driverToEdit = driverToEdit;
        isEditMode = true;
        isDriverMode = true;
        setValuesForEditMode();
    }

    /**
     * Sets the carToEdit property
     * @param carToEdit the object that is to be edited
     */
    public void setCarToEdit(Car carToEdit) {
        this.carToEdit = carToEdit;
        isEditMode = true;
        isDriverMode =false;
        setValuesForEditMode();
    }

    /**
     * Prepares the frame to display content, contains all necessary listeners
     * @param mainW the refference for the main application window
     */
    public DetailsForm(MainWindow mainW) {
        super("App");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setContentPane(rootPanel);
        setVisible(true);
        setSize(600, 300);
        this.mainWindow = mainW;

        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!validateFields()){
                    showAlertWithMessage("Uzupełnij wszystkie pola");
                    return;
                }
                try {

                    if(isEditMode){
                        if(driverToEdit != null){
                            makeAndSaveDriverBasedOnInputData(); // modifies driverToEdit
                            mainWindow.reloadDriversTable();
                        }
                        if(carToEdit != null){
                            maekandsavecarbasedoninputdata(); // modifies carToEdit
                            mainWindow.reloadCarsTable();
                        }
                    }else{
                        if(isDriverMode){
                            Driver driver = makeAndSaveDriverBasedOnInputData();
                            mainWindow.addDriver(driver);
                        }
                        if(!isDriverMode){
                           Car car = maekandsavecarbasedoninputdata();
                            mainWindow.addCar(car);
                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                CloseFrame();
            }
        });
    }

    /**
     * Reads the data from the text fields, create a driver object and save it to the data base
     * @return a driver object crated from data entered in text fields
     */
    public Driver makeAndSaveDriverBasedOnInputData() {
        Driver d = driverToEdit == null ? new Driver() : driverToEdit;
        d.setName(textField1.getText());
        d.setSurname(textField2.getText());
        //TODO parse age
        d.setAge(23);
        d.setInfo(textField4.getText());
        d.saveIt();
        return d;
    }

    /**
     * Reads the data from the text fields, create a car object and save it to the data base
     * @return a car object crated from data entered in text fields
     */

    public Car maekandsavecarbasedoninputdata() {
        Car c = carToEdit == null ? new Car() : carToEdit;
        c.setBrand(textField1.getText());
        c.setModel(textField2.getText());
        //TODO parse year
        c.setYear(1999);
        c.setVin(textField4.getText());
        c.saveIt();
        return c;
    }



    private boolean validateFields(){
        if(textField1.getText().equals("") && textField1.getText().equals("") && textField1.getText().equals("") && textField1.getText().equals("")){
            return false;
        }else{
            return true;
        }

    }
    private void CloseFrame(){
        super.dispose();
    }

    /**
     * Sets initial values of the text fields of a driver or a car that is to be edited
     */

    public void setValuesForEditMode(){
        if(driverToEdit!=null){
            textField1.setText(driverToEdit.getName());
            textField2.setText(driverToEdit.getSurname());
            textField3.setText("" + driverToEdit.getAge());
            textField4.setText(driverToEdit.getInfo());
        }
        else if(carToEdit!=null){
            textField1.setText(carToEdit.getBrand());
            textField2.setText(carToEdit.getModel());
            textField3.setText("" + carToEdit.getYear());
            textField4.setText(""+ carToEdit.getVin());
        }
    }

    /**
     * Set the labels texts according to the isDriverMode property
     */
    public void setLabels(){
        //labels
        if(isDriverMode){
            nameOrBrand.setText("Imię");
            surnameOrModel.setText("Nazwisko");
            ageOrYear.setText("Wiek");
            infoOrVim.setText("Uwagi");
        }
        if(!isDriverMode){
            nameOrBrand.setText("Marka");
            surnameOrModel.setText("Model");
            ageOrYear.setText("Rok produkcji");
            infoOrVim.setText("VIN");
        }
        nameOrBrand.paintImmediately(nameOrBrand.getVisibleRect());
        surnameOrModel.paintImmediately(surnameOrModel.getVisibleRect());
        ageOrYear.paintImmediately(ageOrYear.getVisibleRect());
        infoOrVim.paintImmediately(infoOrVim.getVisibleRect());
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
