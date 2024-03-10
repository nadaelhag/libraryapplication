import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class transactions extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private final String[] column = {"Transactions"};
	private static int user;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					transactions frame = new transactions(user);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public transactions(int libraryID) {
		initialize(libraryID, saveitems(libraryID));
	}
	
	public static String[][] saveitems(int libraryID) {
		String[][] sort = null;
    try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/libraryapp", "root", "");
        String query = "SELECT transaction FROM transactions WHERE userID = ?";
        PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, 
        		ResultSet.CONCUR_UPDATABLE);
        pstmt.setInt(1, libraryID);

        ResultSet rs = pstmt.executeQuery();
        int numRows = 0;
        while (rs.next()) {
            numRows++;
        }
        rs.beforeFirst(); 
        int numCols = rs.getMetaData().getColumnCount();
        sort = new String[numRows][numCols];
        int i = 0;
        while (rs.next()) {
            for (int j = 1; j <= numCols; j++) {
                sort[i][j-1] = rs.getString(j);
            }
            i++;
        }
    } catch (SQLException e) {
        System.err.println("Error retrieving data from database: " + e.getMessage());
    }
	return sort; 
    }
	
	
	private void initialize(int libraryID, String[][] arr) {
		setTitle("Transactions");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 971, 326);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Georgia", Font.PLAIN, 13));
		scrollPane.setBackground(new Color(255, 255, 255));
		scrollPane.setBounds(10, 19, 937, 259);
		getContentPane().add(scrollPane);
		
		String row[][]= arr ;
		DefaultTableModel model1 = new DefaultTableModel(row, column);
		table = new JTable();
		table.setBackground(new Color(255, 255, 255));
		table.setShowVerticalLines(false);
		table.setRowHeight(25);
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setGridColor(new Color(255, 255, 255));
		table.setFont(new Font("Georgia", Font.PLAIN, 14));
		table.getTableHeader().setFont(new Font("Georgia", Font.PLAIN, 16));
		table.getTableHeader().setOpaque(true); 
		table.getTableHeader().setBackground(new Color(116, 107, 222));
		table.getTableHeader().setForeground(new Color(255, 255, 255));
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
	        public Component getTableCellRendererComponent(JTable table, 
	                Object value, boolean isSelected, boolean hasFocus,
	                int row, int column) {
	            Component c = super.getTableCellRendererComponent(table, 
	                value, isSelected, hasFocus, row, column);
	            if (isSelected) {
	            	   c.setBackground(table.getSelectionBackground());
	            	} else {
	            	   c.setBackground(row % 2 == 0 ? Color.white : new Color(205, 205, 205));
	            	}
	            return c;
	        };
	    });
		table.setModel(model1);
		scrollPane.setViewportView(table);
	}
}
