package V1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class searchApartment {

	private JFrame frame;
	private JTextField textFieldMin;
	private JTextField textFieldMax;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					searchApartment window = new searchApartment();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//this part is required to make sure the connection is made
	Connection connection = null;
	
	/**
	 * Create the application.
	 */
	public searchApartment() {
		initialize();
		//this line of code is to make sure the connection is made on initialization
		connection = sqliteConnection.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 477, 316);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Min");
		lblNewLabel.setBounds(67, 41, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Max");
		lblNewLabel_1.setBounds(67, 66, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setBounds(67, 91, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBounds(67, 116, 46, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		textFieldMin = new JTextField();
		textFieldMin.setBounds(134, 38, 86, 20);
		frame.getContentPane().add(textFieldMin);
		textFieldMin.setColumns(10);
		
		textFieldMax = new JTextField();
		textFieldMax.setBounds(134, 63, 86, 20);
		frame.getContentPane().add(textFieldMax);
		textFieldMax.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(134, 88, 86, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(134, 113, 86, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		final JTextArea textOutput = new JTextArea();
		textOutput.setEditable(false);
		textOutput.setBounds(249, 36, 153, 94);
		frame.getContentPane().add(textOutput);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//query is not case sensitive, but JAVA is.
					String query="select * from ApartmentData where Rent>? and Rent<? ";
					
					//connection is from Connection connection = null;
					PreparedStatement pst = connection.prepareStatement(query); //we are passing query through the statement
					pst.setString(1, textFieldMin.getText()); //first value passed is from username=? which is at index [1]. password=? is at index [2].
					//second value of is the text field name, and we get the value inside the textbox with getText()
					pst.setString(2, textFieldMax.getText());
				     					
					
					ResultSet rs = pst.executeQuery();
					
					while (rs.next())
				      {
				        int aid = rs.getInt("aID");
				        String rent = rs.getString("rent");
				        String location = rs.getString("location");
				         
				        // print the results
				        //System.out.format("%s, %s, %s\n", aid, rent, location);
				        textOutput.setText("ID: " + aid + "\nRent: " + rent + "\nLocation: " + location);
				      }
					
					
					//textOutput.setText(rs.getString(query));
				
					//always close the connection for each query. it is a constraint with sqlite
					rs.close();
					pst.close();
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnSearch.setBounds(131, 155, 89, 23);
		frame.getContentPane().add(btnSearch);
	}
}
