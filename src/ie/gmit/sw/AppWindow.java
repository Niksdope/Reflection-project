package ie.gmit.sw;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class AppWindow {
	private JFrame frame;
	private JTable table = null;
	private JScrollPane tableScroller = null;
	private SummaryTableModel stm = new SummaryTableModel();
	
	public AppWindow(){
		//Create a window for the application
		frame = new JFrame();
		frame.setTitle("JAR Stability Calculator");
		frame.setSize(550, 500);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		
        //The file panel will contain the file  chooser
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEADING));
        top.setBorder(new javax.swing.border.TitledBorder("Select File to Encode"));
        top.setPreferredSize(new java.awt.Dimension(500, 100));
        top.setMaximumSize(new java.awt.Dimension(500, 100));
        top.setMinimumSize(new java.awt.Dimension(500, 100));
        
        final JTextField txtFileName =  new JTextField(34);
		txtFileName.setPreferredSize(new java.awt.Dimension(300, 30));
		txtFileName.setMaximumSize(new java.awt.Dimension(300, 30));
		txtFileName.setMargin(new java.awt.Insets(2, 2, 2, 2));
		txtFileName.setMinimumSize(new java.awt.Dimension(300, 30));
		
		JButton btnChooseFile = new JButton("Browse...");
		btnChooseFile.setToolTipText("Select File to Encode");
        btnChooseFile.setPreferredSize(new java.awt.Dimension(90, 30));
        btnChooseFile.setMaximumSize(new java.awt.Dimension(90, 30));
        btnChooseFile.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnChooseFile.setMinimumSize(new java.awt.Dimension(90, 30));
		btnChooseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
        		JFileChooser fc = new JFileChooser("./");
        		int returnVal = fc.showOpenDialog(frame);
            	if (returnVal == JFileChooser.APPROVE_OPTION) {
                	File file = fc.getSelectedFile().getAbsoluteFile();
                	String name = file.getAbsolutePath();
                	// If a jar file was selected
                	if (name.endsWith(".jar")){
                		// Parse file
                		JARParser jp = new JARParser();
                		try {
                			// Populate table and repaint the table
                			stm.populateData(jp.parse(file));
                			table.repaint();
                		} catch (Exception e) {
                			e.printStackTrace();
                		}
                		
                    	txtFileName.setText(name);
                    	System.out.println("You selected the following file: " + name);
                	}else{
                		System.out.println("Please select a jar file");
                	}                	
            	} 	
			}
        });
		
        top.add(txtFileName);
        top.add(btnChooseFile);
        frame.getContentPane().add(top); //Add the panel to the window
        
        //A separate panel for the programme output
        JPanel mid = new JPanel(new FlowLayout(FlowLayout.LEADING));
        mid.setBorder(new BevelBorder(BevelBorder.RAISED));
        mid.setPreferredSize(new java.awt.Dimension(500, 300));
        mid.setMaximumSize(new java.awt.Dimension(500, 300));
        mid.setMinimumSize(new java.awt.Dimension(500, 300));
        
        table = new JTable(stm);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionBackground(Color.GRAY);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBackground(Color.WHITE);
        
        tableScroller = new JScrollPane(table);
		tableScroller.setPreferredSize(new java.awt.Dimension(485, 285));
		tableScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		TableColumn column = null;
		for (int i = 0; i < table.getColumnCount(); i++){
			column = table.getColumnModel().getColumn(i);
			if (i == 0 || i == 2 || i == 3){
				column.setPreferredWidth(88);
				column.setMaxWidth(88);
				column.setMinWidth(88);
			}else {
				column.setPreferredWidth(204);
				column.setMaxWidth(204);
				column.setMinWidth(204);
			}
		}
        
        table.setPreferredSize(new java.awt.Dimension(500, 500));
        table.setMaximumSize(new java.awt.Dimension(500, 500));
        table.setMinimumSize(new java.awt.Dimension(500, 500));
        
        mid.add(tableScroller, FlowLayout.LEFT);
		frame.getContentPane().add(mid);
		
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setPreferredSize(new java.awt.Dimension(500, 50));
        bottom.setMaximumSize(new java.awt.Dimension(500, 50));
        bottom.setMinimumSize(new java.awt.Dimension(500, 50));
        
        JButton btnQuit = new JButton("Quit"); //Create Quit button
        btnQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	System.exit(0);
			}
        });
        bottom.add(btnQuit);
        
        frame.getContentPane().add(bottom);     
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new AppWindow();
	}
}