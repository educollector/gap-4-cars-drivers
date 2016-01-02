import com.sun.org.apache.xpath.internal.operations.Bool;
import domain.Car;
import domain.Driver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by olaskierbiszewska on 13.12.15.
 */
public class AddDriverForm extends JFrame {
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
    private boolean isCarMode;
    private boolean isEditMode;
    private Driver driverToEdit;
    private Car carToEdit;

    public void setIsDriverMode(Boolean isDriverMode) { this.isDriverMode = isDriverMode; }
    public void setIsCarMode(Boolean isCarMode) {
        this.isCarMode = isCarMode;
    }
    public void setIsEditMode(Boolean isEditMode) { this.isEditMode = isEditMode; }
    public void setDriverToEdit(Driver driverToEdit) {  this.driverToEdit = driverToEdit; }
    public void setCarToEdit(Driver driverToEdit) {  this.carToEdit = carToEdit; }

    public AddDriverForm(MainWindow mainW) {
        super("App");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setContentPane(rootPanel);
        setVisible(true);
        setSize(600, 300);
        this.mainWindow = mainW;

        //labels
        if(isDriverMode){
            nameOrBrand.setText("Imię");
            surnameOrModel.setText("Nazwisko");
            ageOrYear.setText("Wiek");
            infoOrVim.setText("Uwagi");
        }
        if(isCarMode){
            nameOrBrand.setText("Marka");
            surnameOrModel.setText("Model");
            ageOrYear.setText("Rok produkcji");
            infoOrVim.setText("VIN");
        }

        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: validate fields
                try {

                    if(isEditMode){
                        if(driverToEdit != null){
                            System.out.print(  System.identityHashCode(driverToEdit) + "\n");
                            mainWindow.saveEditedDriver(driverToEdit);
                        }
                        if(carToEdit != null){
                            //TODO car edut method in main window
                            //mainWindow.saveEditedCar(carToEdit);
                        }
                    }else{
                        if(isDriverMode){
                            Driver driver = getDriverBasedOnInputData();
                            mainWindow.addDriver(driver);
                        }
                        if(isCarMode){
                           Car car = getCarBasedOnInputData();
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

    public Driver getDriverBasedOnInputData() {
        Driver d = driverToEdit == null ? new Driver() : driverToEdit;
        d.setName(textField1.getText());
        d.setSurname(textField2.getText());
        //TODO parse age
        d.setAge(23);
        d.setInfo(textField4.getText());
        d.saveIt();
        return d;
    }

    public Car getCarBasedOnInputData() {
        Car c = carToEdit == null ? new Car() : carToEdit;
        c.setBrand(textField1.getText());
        c.setModel(textField2.getText());
        //TODO parse year
        c.setYear(1999);
        c.setVin(textField4.getText());
        c.saveIt();
        return c;
    }

    public void CloseFrame(){
        super.dispose();
    }

    public void setValuesForEditMode(){
        if(driverToEdit!=null && isEditMode){
            System.out.print(  System.identityHashCode(driverToEdit) + "\n");
            textField1.setText(driverToEdit.getName());
            textField2.setText(driverToEdit.getSurname());
            textField3.setText("" + driverToEdit.getAge());
            textField4.setText(driverToEdit.getInfo());
        }
    }
}
