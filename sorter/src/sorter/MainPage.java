package sorter;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class MainPage extends JFrame{
	JFileChooser fileChooser = new JFileChooser();
	JPanel northPanel = new JPanel();
	JButton selectFile = new JButton("SELECT FILE");
	Analyse analyse;
	protected final String[] columnNames = { "Date", "Time", "Stream Name", "Stream Status", "Port", "Platform", 
			  "Links", "Packets"};
	  protected Object[][] data ;

	  protected DefaultTableModel model;
	  protected JTable table;
	  protected TableRowSorter<TableModel> rowSorter;
	  protected final JTextField searchFilter = new JTextField(19);
	  protected final JButton open = new JButton("open");
	
	public MainPage() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 500);
		this.setTitle("F-SLICER");
		this.setVisible(true);
		BorderLayout borderLayout = new BorderLayout();
		this.getContentPane().add(northPanel, borderLayout.NORTH);
		
		
		selectFile.addActionListener(v->{
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
			    File selectedFile = fileChooser.getSelectedFile();
			    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			    try {
					analyse = new Analyse(selectedFile.getAbsolutePath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		
		
		model = new DefaultTableModel(data, columnNames) {
		    @Override public Class<?> getColumnClass(int column) {
		    	Object val = getValueAt(0, column);
		    	if(val == null) val=new String();
		      return val.getClass();
		    }
		  };
		
		table = new JTable(model);
		rowSorter = new TableRowSorter<>(model);
	    table.setRowSorter(rowSorter);
	    table.setRowHeight(30);
	    
	    BorderLayout bord = new BorderLayout();
	    JPanel southPanel = new JPanel(bord);
	    
	    southPanel.add(selectFile, bord.WEST);
	    JLabel label = new JLabel("Search:  ", SwingConstants.RIGHT);
	    southPanel.add(label, bord.CENTER);
	    southPanel.add(searchFilter, bord.EAST);
	    
	    
	    this.getContentPane().add(southPanel, borderLayout.SOUTH);
	    
		});
		
		
	    searchFilter.getDocument().addDocumentListener(new DocumentListener() {
	        @Override
	        public void insertUpdate(DocumentEvent e) {
	          String text = searchFilter.getText();
	          System.out.println(text);
	          if (text.trim().length() == 0) {
	            rowSorter.setRowFilter(null);
	            
	          } else {
	            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
	            
	          }
	          
	          table.repaint();
	          table.revalidate();
	          
	        
	        }
	        @Override
	        public void removeUpdate(DocumentEvent e) {
	          String text = searchFilter.getText();
	          if (text.trim().length() == 0) {
	            rowSorter.setRowFilter(null);
	          } else {
	            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
	          }
	        }
	        @Override
	        public void changedUpdate(DocumentEvent e) {
	          //not needed: throw new UnsupportedOperationException("Not supported yet.");
	        }
	      });
	    
	   
	    this.getContentPane().add(new JScrollPane(table), borderLayout.CENTER);
	   
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new MainPage();
				
			}
			
		});

	}

}
