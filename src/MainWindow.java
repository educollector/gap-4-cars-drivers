import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by olaskierbiszewska on 13.12.15.
 */
public class MainWindow extends JFrame {
    private JPanel rootPanel;
    private JTable tableDrivers;
    private JTable tableCars;
    private JButton addCarButton;
    private JButton deleteCarButton;
    private JButton editCarButton;
    private JButton addDriverButton;
    private JButton deleteDriverButton;
    private JButton editDriverButton;


    public MainWindow() {
        super("App");
        pack();
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(600, 600);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        DefaultTableModel model = new DefaultTableModel(new Object[][] {
                { "some", "text" }, { "any", "text" }, { "even", "more" },
                { "text", "strings" }, { "and", "other" }, { "text", "values" } },
                new Object[] { "Column 1", "Column 2" });
        tableDrivers = new JTable(model);
    }
}
