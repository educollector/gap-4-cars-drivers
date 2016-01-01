import com.sun.org.apache.xpath.internal.operations.Bool;
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
    private MainWindow mainWindow;
    private Boolean isEditMode;
    private Driver driverToEdit;

    public void setIsEditMode(Boolean isEditMode) { this.isEditMode = isEditMode; }
    public Driver getDriverToEdit() {  return driverToEdit;  }
    public void setDriverToEdit(Driver driverToEdit) {  this.driverToEdit = driverToEdit; }

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
                    Driver driver = getDriverBasedOnInputData();
                    if(isEditMode){
                        System.out.print(  System.identityHashCode(driverToEdit) + "\n");
                        mainWindow.saveEditedDriver(driverToEdit);
                    }else{
                        mainWindow.addDriver(driver);
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
        //d.setAge(Integer.parseInt(textField3.getText()));
        d.setInfo(textField4.getText());
        d.saveIt();
        return d;
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
