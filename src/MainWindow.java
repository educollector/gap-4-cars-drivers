import javax.swing.*;

/**
 * Created by olaskierbiszewska on 13.12.15.
 */
public class MainWindow extends JFrame {
    private JPanel rootPanel;
    private JTable table1;
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;

    public MainWindow() {
        super("App");
        pack();
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(600, 600);
    }
}
