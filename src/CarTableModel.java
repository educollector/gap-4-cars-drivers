/**
 * Created by olaskierbiszewska on 13.12.15.
 */

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class CarTableModel extends AbstractTableModel {
    public String[] m_colNames = { "Marka", "Model", "Rok produkcji","VIM" };
    public Class[] m_colTypes = { String.class, String.class, Integer.class, String.class};
    Vector m_macDataVector;

    public CarTableModel(Vector macDataVector) {
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
        Car macData = (Car) (m_macDataVector.elementAt(row));

        switch (col) {
            case 0:
                macData.setBrand((String) value);
                break;
            case 1:
                macData.setBrand((String) value);
                break;
            case 2:
                macData.setYear((Integer) value);
                break;
            case 3:
                macData.setVim((String) value);
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
        Car macData = (Car) (m_macDataVector.elementAt(row));

        switch (col) {
            case 0:
                return macData.getBrand();
            case 1:
                return macData.getModel();
            case 2:
                return macData.getYear();
            case 3:
                return macData.getVim();
        }

        return new String();
    }
}


