/**
 * Created by olaskierbiszewska on 13.12.15.
 * Provides implementations for most of the methods in the TableModel interface
 * @author Ola Skierbiszewska
 * @version 1.0 Build Jan 5, 2016.
 */
import domain.Driver;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

/**
 * Defines the coluns headers names.
 * Defines the data source collection.
 */
    public class DriverTableModel extends AbstractTableModel {
    private static final String[] COLUMN_NAMES= { "Imie", "Nazwisko", "Wiek","Uwagi" };
    private static final Class[] COLUMN_CLASSES = { String.class, String.class, Integer.class, String.class};
    Vector m_macDataVector;

    public DriverTableModel(Vector macDataVector) {
        super();
        m_macDataVector = macDataVector;
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
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

    //the column header
    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    //if you want to change the columns class
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_CLASSES[columnIndex];
    }

    @Override
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

