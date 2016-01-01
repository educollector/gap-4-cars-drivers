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
                //TODO: validate fields, save data
                mainWindow.addDriver(getDriverBasedOnInputData());
                CloseFrame();
            }
        });
    }

    public Driver getDriverBasedOnInputData() {
        Driver d = new Driver();
        d.setName(textField1.getText());
        d.setSurname(textField2.getText());
        d.setAge(23);
        //d.setAge(Integer.parseInt(textField3.getText()));
        d.setInfo(textField4.getText());
        d.saveIt();
        return d;
    }

    public void CloseFrame(){
        super.dispose();
    }
}
