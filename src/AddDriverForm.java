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

    public AddDriverForm() {
        super("App");
        pack();
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(600, 300);
        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: validate fields, save data
                //call another method in the same class which will close this Jframe
                CloseFrame();
            }
        });
    }

    public void CloseFrame(){
        super.dispose();
    }
}
