/**
 * Created by olaskierbiszewska on 13.12.15.
 */
import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class DriverTableModel extends AbstractTableModel {
    public String[] m_colNames = { "Imieę", "Nazwisko", "Wiek","Uwagi" };
    public Class[] m_colTypes = { String.class, String.class, Integer.class, String.class};
    Vector m_macDataVector;

    public DriverTableModel(Vector macDataVector) {
        super();
        m_macDataVector = macDataVector;
    }

    public int getColumnCount() {
        return m_colNames.length;
    }

    public int getRowCount() {
        return m_macDataVector.size();
    }

    public void setValueAt(Object value, int row, int col) {
        Driver macData = (Driver) (m_macDataVector.elementAt(row));

        switch (col) {
            case 0:
                macData.setName((String) value);
                break;
            case 1:
                macData.setSurname((String) value);
                break;
            case 2:
                macData.setAge((Integer) value);
                break;
            case 3:
                macData.setInfo((String) value);
                break;
        }
    }

    public String getColumnName(int col) {
        return m_colNames[col];
    }

    public Class getColumnClass(int col) {
        return m_colTypes[col];
    }
    public Object getValueAt(int row, int col) {
        Driver macData = (Driver) (m_macDataVector.elementAt(row));

        switch (col) {
            case 0:
                return macData.getName();
            case 1:
                return macData.getSurname();
            case 2:
                return macData.getAge();
            case 3:
                return macData.getInfo();
        }

        return new String();
    }
}

