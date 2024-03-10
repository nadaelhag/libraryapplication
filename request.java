import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class request extends JFrame {

	private JPanel contentPane;
	private JTextField typef;
	private JTextField titlef;
	private JTextField authorf;
	private JTextField yearf;
	private JTextField valuef;
	private JTextField regionf;
	private JTextField emiratesf;
	private static int user;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
    String dateStr = now.format(formatter);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					request frame = new request(user);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public request(int libraryID) {
		initialize(libraryID);
	}
	
	private void initialize(int libraryID) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 762, 536);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel type = new JLabel("Type:");
		type.setFont(new Font("Georgia", Font.PLAIN, 18));
		type.setBounds(50, 72, 88, 32);
		contentPane.add(type);
		
		JLabel title = new JLabel("Title:");
		title.setFont(new Font("Georgia", Font.PLAIN, 18));
		title.setBounds(50, 128, 68, 32);
		contentPane.add(title);
		
		JLabel lblNewLabel = new JLabel("Request Forum");
		lblNewLabel.setFont(new Font("Georgia", Font.PLAIN, 24));
		lblNewLabel.setBounds(291, 11, 169, 53);
		contentPane.add(lblNewLabel);
		
		JLabel author = new JLabel("Author:");
		author.setFont(new Font("Georgia", Font.PLAIN, 18));
		author.setBounds(50, 181, 88, 32);
		contentPane.add(author);
		
		JLabel year = new JLabel("Year:");
		year.setFont(new Font("Georgia", Font.PLAIN, 18));
		year.setBounds(50, 233, 88, 32);
		contentPane.add(year);
		
		JLabel value = new JLabel("Value:");
		value.setFont(new Font("Georgia", Font.PLAIN, 18));
		value.setBounds(50, 282, 88, 32);
		contentPane.add(value);
		
		JLabel region = new JLabel("Region:");
		region.setFont(new Font("Georgia", Font.PLAIN, 18));
		region.setBounds(50, 331, 88, 32);
		contentPane.add(region);
		
		JLabel emirates = new JLabel("Emirates:");
		emirates.setFont(new Font("Georgia", Font.PLAIN, 18));
		emirates.setBounds(50, 380, 88, 32);
		contentPane.add(emirates);
		
		typef = new JTextField();
		typef.setBackground(new Color(229, 232, 237));
		typef.setFont(new Font("Georgia", Font.PLAIN, 19));
		typef.setBounds(138, 75, 503, 32);
		contentPane.add(typef);
		typef.setColumns(10);
		
		titlef = new JTextField();
		titlef.setBackground(new Color(229, 232, 237));
		titlef.setFont(new Font("Georgia", Font.PLAIN, 19));
		titlef.setColumns(10);
		titlef.setBounds(138, 128, 503, 32);
		contentPane.add(titlef);
		
		authorf = new JTextField();
		authorf.setBackground(new Color(229, 232, 237));
		authorf.setFont(new Font("Georgia", Font.PLAIN, 19));
		authorf.setColumns(10);
		authorf.setBounds(138, 181, 503, 32);
		contentPane.add(authorf);
		
		yearf = new JTextField();
		yearf.setBackground(new Color(229, 232, 237));
		yearf.setFont(new Font("Georgia", Font.PLAIN, 19));
		yearf.setColumns(10);
		yearf.setBounds(138, 233, 503, 32);
		contentPane.add(yearf);
		
		valuef = new JTextField();
		valuef.setBackground(new Color(229, 232, 237));
		valuef.setFont(new Font("Georgia", Font.PLAIN, 19));
		valuef.setColumns(10);
		valuef.setBounds(138, 285, 503, 32);
		contentPane.add(valuef);
		
		regionf = new JTextField();
		regionf.setBackground(new Color(229, 232, 237));
		regionf.setFont(new Font("Georgia", Font.PLAIN, 19));
		regionf.setColumns(10);
		regionf.setBounds(138, 334, 503, 32);
		contentPane.add(regionf);
		
		emiratesf = new JTextField();
		emiratesf.setBackground(new Color(229, 232, 237));
		emiratesf.setFont(new Font("Georgia", Font.PLAIN, 19));
		emiratesf.setColumns(10);
		emiratesf.setBounds(138, 383, 503, 32);
		contentPane.add(emiratesf);
		
		JButton btnNewButton = new JButton("Enter Details");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String typed = typef.getText().trim();
				String titled = titlef.getText().trim();
				String authord = authorf.getText().trim();
				int yeard;
				int valued;

				try {
				    yeard = Integer.parseInt(yearf.getText().trim());
				} catch (NumberFormatException e1) {
				    yeard = 0; 
				}

				try {
				    valued = Integer.parseInt(valuef.getText().trim());
				} catch (NumberFormatException e1) {
				    valued = 0; 
				}

				String regiond = regionf.getText().trim();
				String emiratesd = emiratesf.getText().trim();

				if (yeard == 0) {
					JOptionPane.showMessageDialog(null, "Please enter a valid input for year.");}
				else if (valued == 0) {
					JOptionPane.showMessageDialog(null, "Please enter a valid input for value.");}
				else {
				try {Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/libraryapp", "root", "");
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO requests (libraryID, type, title, author, year, value, region, emirates) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		            pstmt.setInt(1, libraryID); 
		            pstmt.setString(2, typed);
		            pstmt.setString(3, titled);
		            pstmt.setString(4, authord);
		            pstmt.setInt(5, yeard);
		            pstmt.setInt(6, valued);
		            pstmt.setString(7, regiond);
		            pstmt.setString(8, emiratesd);
		            
		            pstmt.executeUpdate();
		            JOptionPane.showMessageDialog(null, "Your request has been submitted.");
		            
		            String insertQuery = "INSERT INTO transactions (userId, transaction) VALUES (?, ?)";
				    PreparedStatement stmt1 = conn.prepareStatement(insertQuery);
				    stmt1.setInt(1, libraryID); 
				    stmt1.setString(2, "Your request for "+typed+" "+"\""+titled+"\" was submitted on "+dateStr); 
				    stmt1.executeUpdate(); 
		            setVisible(false);
		}catch (SQLException e1) {
		            System.out.println("Error saving request to table: " + e1.getMessage());}}
				
				}
		});
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(116, 107, 222));
		btnNewButton.setFont(new Font("Georgia", Font.PLAIN, 19));
		btnNewButton.setBounds(291, 437, 169, 38);
		contentPane.add(btnNewButton);
		
		
	}}
