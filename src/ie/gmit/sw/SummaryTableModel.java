package ie.gmit.sw;

import java.util.Map;
import javax.swing.table.AbstractTableModel;

public class SummaryTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 777L;
	private String[] cols = {"Stability", "Class", "Indegree", "Outdegree"};
	private Object[][] data = {};
	
	public int getColumnCount() {
        return cols.length;
    }
	
    public int getRowCount() {
        return data.length;
	}

    public String getColumnName(int col) {
    	return cols[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
	}
   
    public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
	}
    
    public void populateData(Map<String, Metric> map){
    	int rowCounter = 0;
    	Object [][] temp = new Object[map.size()][4];
    	
    	for (Map.Entry<String, Metric> entry : map.entrySet()){
    		System.out.println(entry.getValue().getStability());
    		temp[rowCounter][0] = entry.getValue().getStability();
    		temp[rowCounter][1] = entry.getKey();
    		temp[rowCounter][2] = entry.getValue().getIndegree();
    		temp[rowCounter][3] = entry.getValue().getOutdegree();
    		
    		rowCounter ++;
    	}
    	
    	data = temp;
    }
    
    public Object[][] getData(){
    	return data;
    }
}