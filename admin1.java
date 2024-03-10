import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class admin1 {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					admin1 frame = new admin1();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public admin1() {
		initialize();
	}
    
	private void initialize() {
    JFrame frame = new JFrame("Admin Interface");
    frame.getContentPane().setBackground(new Color(255, 255, 255));

    frame.setSize(653, 208);
    frame.setLocationRelativeTo(null);
    frame.getContentPane().setLayout(null);
    
    JButton btnReturnItem = new JButton("Return Item");
    btnReturnItem.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		returns r = new returns();
    		r.setVisible(true);
    		r.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	}
    });
    btnReturnItem.setForeground(new Color(0, 0, 0));
    btnReturnItem.setFont(new Font("Georgia", Font.PLAIN, 16));
    btnReturnItem.setBackground(new Color(211, 211, 211));
    btnReturnItem.setBounds(46, 62, 233, 63);
    frame.getContentPane().add(btnReturnItem);
    
    JButton btnViewRequests = new JButton("View Requests");
    btnViewRequests.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		requests r = new requests();
    		r.setVisible(true);
    		r.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	}
    });
    btnViewRequests.setForeground(new Color(0, 0, 0));
    btnViewRequests.setFont(new Font("Georgia", Font.PLAIN, 16));
    btnViewRequests.setBackground(new Color(211, 211, 211));
    btnViewRequests.setBounds(342, 62, 233, 63);
    frame.getContentPane().add(btnViewRequests);
    
    JLabel lblNewLabel = new JLabel("Welcome To Library Admin");
    lblNewLabel.setFont(new Font("Georgia", Font.PLAIN, 24));
    lblNewLabel.setBounds(167, 11, 315, 26);
    frame.getContentPane().add(lblNewLabel);

    frame.setVisible(true);
  }
}