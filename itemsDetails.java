import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class itemsDetails extends JFrame {

	private JPanel contentPane;
	private static int itemID;
	String type1 = "";
	String title1 = "";
	String author1 = "";
	int year1 = 0;
	int value1 = 0;
	String region1 = "";
	String emirates1 = "";
	String availability1 = "";

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		itemsDetails.itemID = itemID;
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getTitle1() {
		return title1;
	}

	public void setTitle1(String title1) {
		this.title1 = title1;
	}

	public String getAuthor1() {
		return author1;
	}

	public void setAuthor1(String author1) {
		this.author1 = author1;
	}

	public int getYear1() {
		return year1;
	}

	public void setYear1(int year1) {
		this.year1 = year1;
	}

	public int getValue1() {
		return value1;
	}

	public void setValue1(int value1) {
		this.value1 = value1;
	}

	public String getEmirates1() {
		return emirates1;
	}

	public void setEmirates1(String emirates1) {
		this.emirates1 = emirates1;
	}

	public String getRegion1() {
		return region1;
	}

	public void setRegion1(String region1) {
		this.region1 = region1;
	}

	public String getAvailability1() {
		return availability1;
	}

	public void setAvailability1(String availability1) {
		this.availability1 = availability1;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					itemsDetails frame = new itemsDetails(itemID);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public itemsDetails(int itemID1) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 831, 643);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("Item Details");
		lblNewLabel.setFont(new Font("Georgia", Font.PLAIN, 24));
		lblNewLabel.setBounds(355, 11, 130, 53);
		contentPane.add(lblNewLabel);
		
		JLabel type = new JLabel("Type:");
		type.setFont(new Font("Georgia", Font.PLAIN, 18));
		type.setBounds(56, 80, 88, 32);
		contentPane.add(type);
		
		JLabel title = new JLabel("Title:");
		title.setFont(new Font("Georgia", Font.PLAIN, 18));
		title.setBounds(56, 144, 88, 32);
		contentPane.add(title);
		
		JLabel author = new JLabel("Author:");
		author.setFont(new Font("Georgia", Font.PLAIN, 18));
		author.setBounds(56, 200, 88, 32);
		contentPane.add(author);
		
		JLabel year = new JLabel("Year:");
		year.setFont(new Font("Georgia", Font.PLAIN, 18));
		year.setBounds(56, 258, 88, 32);
		contentPane.add(year);
		
		JLabel value = new JLabel("Value:");
		value.setFont(new Font("Georgia", Font.PLAIN, 18));
		value.setBounds(56, 321, 88, 32);
		contentPane.add(value);
		
		JLabel region = new JLabel("Region:");
		region.setFont(new Font("Georgia", Font.PLAIN, 18));
		region.setBounds(56, 372, 88, 32);
		contentPane.add(region);
		
		JLabel emirates = new JLabel("Emirates:");
		emirates.setFont(new Font("Georgia", Font.PLAIN, 18));
		emirates.setBounds(56, 427, 88, 32);
		contentPane.add(emirates);
		
		JLabel availability = new JLabel("Availability:");
		availability.setFont(new Font("Georgia", Font.PLAIN, 18));
		availability.setBounds(48, 487, 96, 32);
		contentPane.add(availability);
		
		JLabel itemid = new JLabel("ItemID:");
		itemid.setFont(new Font("Georgia", Font.PLAIN, 18));
		itemid.setBounds(56, 541, 88, 32);
		contentPane.add(itemid);
		
		JTextField typeArea = new JTextField();
		typeArea.setBorder(null);
		typeArea.setFont(new Font("Georgia", Font.PLAIN, 19));
		typeArea.setBackground(new Color(229, 232, 237));
		typeArea.setBounds(154, 87, 589, 32);
		contentPane.add(typeArea);
		
		JTextField titleArea = new JTextField();
		titleArea.setBorder(null);
		titleArea.setFont(new Font("Georgia", Font.PLAIN, 18));
		titleArea.setBackground(new Color(229, 232, 237));
		titleArea.setBounds(154, 144, 589, 32);
		contentPane.add(titleArea);
		
		JTextField authorArea = new JTextField();
		authorArea.setBorder(null);
		authorArea.setFont(new Font("Georgia", Font.PLAIN, 18));
		authorArea.setBackground(new Color(229, 232, 237));
		authorArea.setBounds(154, 200, 589, 32);
		contentPane.add(authorArea);
		
		JTextField yearArea = new JTextField();
		yearArea.setBorder(null);
		yearArea.setFont(new Font("Georgia", Font.PLAIN, 18));
		yearArea.setBackground(new Color(229, 232, 237));
		yearArea.setBounds(154, 258, 589, 32);
		contentPane.add(yearArea);
		
		JTextField valArea = new JTextField();
		valArea.setBorder(null);
		valArea.setFont(new Font("Georgia", Font.PLAIN, 18));
		valArea.setBackground(new Color(229, 232, 237));
		valArea.setBounds(154, 316, 589, 32);
		contentPane.add(valArea);
		
		JTextField regionArea = new JTextField();
		regionArea.setBorder(null);
		regionArea.setFont(new Font("Georgia", Font.PLAIN, 18));
		regionArea.setBackground(new Color(229, 232, 237));
		regionArea.setBounds(154, 376, 589, 32);
		contentPane.add(regionArea);
		
		JTextField emArea = new JTextField();
		emArea.setBorder(null);
		emArea.setFont(new Font("Georgia", Font.PLAIN, 18));
		emArea.setBackground(new Color(229, 232, 237));
		emArea.setBounds(154, 434, 589, 32);
		contentPane.add(emArea);
		
		JTextField availArea = new JTextField();
		availArea.setBorder(null);
		availArea.setFont(new Font("Georgia", Font.PLAIN, 18));
		availArea.setBackground(new Color(229, 232, 237));
		availArea.setBounds(154, 487, 589, 32);
		contentPane.add(availArea);
		
		JTextField itemArea = new JTextField();
		itemArea.setBorder(null);
		itemArea.setFont(new Font("Georgia", Font.PLAIN, 18));
		itemArea.setBackground(new Color(229, 232, 237));
		itemArea.setBounds(154, 541, 589, 32);
		contentPane.add(itemArea);
		
		try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryapp", "root", "");
            String query = "SELECT * FROM items WHERE itemID=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, itemID1);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                 type1 = rs.getString("type");
                 System.out.println(type1);
                 title1 = rs.getString("title");
                 author1 = rs.getString("author");
                 year1 = rs.getInt("year");
                 value1 = rs.getInt("value");
                 region1 = rs.getString("region");
                 emirates1 = rs.getString("emirates");
                 availability1 = rs.getString("available");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
		System.out.println(type1);
		typeArea.setText(type1);
		typeArea.setEditable(false);
		titleArea.setText("\""+title1+"\"");
		titleArea.setEditable(false);
		authorArea.setText(author1);
		authorArea.setEditable(false);
		yearArea.setText(String.valueOf(year1));
		yearArea.setEditable(false);
		valArea.setText(String.valueOf(value1)+" AED");
		valArea.setEditable(false);
		regionArea.setText(region1);
		regionArea.setEditable(false);
		emArea.setText(emirates1);
		emArea.setEditable(false);
		availArea.setText(availability1);
		availArea.setEditable(false);
		itemArea.setText(String.valueOf(itemID1));
		itemArea.setEditable(false);
	}
	//add button and functionality
	//georgia size 16 default text/font
}
