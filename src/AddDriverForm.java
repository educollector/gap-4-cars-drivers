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
    private boolean isEditMode;
    private Driver driverToEdit;
    private Car carToEdit;

    public void setIsDriverMode(Boolean isDriverMode) { this.isDriverMode = isDriverMode; }
    public void setDriverToEdit(Driver driverToEdit) {
        this.driverToEdit = driverToEdit;
        isEditMode = true;
        isDriverMode = true;
        setValuesForEditMode();
    }
    public void setCarToEdit(Car carToEdit) {
        this.carToEdit = carToEdit;
        isEditMode = true;
        isDriverMode =false;
        setValuesForEditMode();
    }

    public AddDriverForm(MainWindow mainW) {
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
                //TODO: validate fields
                try {

                    if(isEditMode){
                        if(driverToEdit != null){
                            makeAndSaveDriverBasedOnInputData(); // modifies driverToEdit
                            mainWindow.reloadDriversTable(driverToEdit);
                        }
                        if(carToEdit != null){
                            maekAndSaveCarBasedOnInputData(); // modifies carToEdit
                            mainWindow.reloadCarsTable(carToEdit);
                        }
                    }else{
                        if(isDriverMode){
                            Driver driver = makeAndSaveDriverBasedOnInputData();
                            mainWindow.addDriver(driver);
                        }
                        if(!isDriverMode){
                           Car car = maekAndSaveCarBasedOnInputData();
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

    public Car maekAndSaveCarBasedOnInputData() {
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
}
