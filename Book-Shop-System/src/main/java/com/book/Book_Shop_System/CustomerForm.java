package com.book.Book_Shop_System;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Font;

public class CustomerForm {

	private JFrame frame;
	private JTextField nameTxt;
	private JTextField contactTxt;
	private JTextField emailTxt;
	private SimpleDateFormat dateFormat;
	private Date regDate;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerForm window = new CustomerForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/*
	 * isValidate() function checks the validation of input members
	 * */
	private boolean isValidate() {
		while(true) {
			if(nameTxt.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "Please Enter Name of Customer..."); nameTxt.requestFocus(); break;
			}
			if(contactTxt.getText().trim().equals("") || contactTxt.getText().trim().length()!=12 ) {
				JOptionPane.showMessageDialog(null, "Please Enter Contact of Customer with country code + 10 digit like 91XXXXXXXXXX"); contactTxt.requestFocus(); break;
			}
			if(emailTxt.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "Please Enter Email-Id of Customer..."); emailTxt.requestFocus(); break;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Create the application.
	 */
	public CustomerForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(240, 248, 255));
		frame.setBounds(100, 100, 737, 471);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		regDate = new Date();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		nameTxt = new JTextField();
		nameTxt.setBackground(new Color(248, 248, 255));
		nameTxt.setFont(new Font("Verdana", Font.PLAIN, 18));
		nameTxt.setBounds(289, 54, 211, 32);
		frame.getContentPane().add(nameTxt);
		nameTxt.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Name :");
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		lblNewLabel.setBounds(159, 54, 76, 33);
		frame.getContentPane().add(lblNewLabel);
		
		contactTxt = new JTextField();
		contactTxt.setBackground(new Color(248, 248, 255));
		contactTxt.setFont(new Font("Verdana", Font.PLAIN, 18));
		contactTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(contactTxt.getText().length()>=12) {
					e.consume();
				}
				if(((e.getKeyChar()<'0')||(e.getKeyChar()>'9'))&&(e.getKeyChar()!='\b')&&(e.getKeyChar()!='\n')) {
					e.consume(); JOptionPane.showMessageDialog(null, "Enter Only Number");
				}
				if(e.getKeyChar()==10 && (!contactTxt.getText().trim().equals(""))) {
					try {
						Connection con = ConnectionProvider.getCon();
						PreparedStatement pst = con.prepareStatement("select * from customer where mobile=?");
						pst.setString(1,(contactTxt.getText()));
						ResultSet rs = pst.executeQuery();
						if(rs.next()) {
							nameTxt.setText(rs.getString("name"));
							emailTxt.setText(rs.getString("email"));
						}else {
							JOptionPane.showMessageDialog(null, "Record Not Found, Please enter mobile number with country code");
							contactTxt.setText("");
							contactTxt.requestFocus();
						}
					}catch(Exception ex) {
							JOptionPane.showMessageDialog(null, ex);
						}
					contactTxt.requestFocus();
				}else {
					if(contactTxt.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(null, "Please Enter Something");
						contactTxt.setText("");
						contactTxt.requestFocus();

					}
				}
				}
		});
		contactTxt.setBounds(289, 112, 211, 32);
		frame.getContentPane().add(contactTxt);
		contactTxt.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Contact :");
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(159, 112, 114, 33);
		frame.getContentPane().add(lblNewLabel_1);
		
		emailTxt = new JTextField();
		emailTxt.setBackground(new Color(248, 248, 255));
		emailTxt.setForeground(new Color(30, 144, 255));
		emailTxt.setFont(new Font("Verdana", Font.PLAIN, 18));
		emailTxt.setBounds(289, 170, 297, 32);
		frame.getContentPane().add(emailTxt);
		emailTxt.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("E-Mail :");
		lblNewLabel_2.setFont(new Font("Verdana", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(159, 170, 93, 33);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton addBtn = new JButton("ADD");
		addBtn.setBackground(new Color(139, 0, 139));
		addBtn.setFont(new Font("Verdana", Font.PLAIN, 18));
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isValidate()) {
					try {
					Connection con = ConnectionProvider.getCon();
					PreparedStatement pst = con.prepareStatement("select * from Customer where mobile=? or email=?");
					pst.setString(1,contactTxt.getText().trim());
					pst.setString(2,emailTxt.getText().trim());
					ResultSet rs = pst.executeQuery();
					if(rs.next()) {
						if(rs.getString("mobile").equals(contactTxt.getText().trim()))
							JOptionPane.showMessageDialog(null, "This contact number already exist, Please enter valid contact number:) ");
						else
							JOptionPane.showMessageDialog(null, "This Email-Id already exist, Please enter valid Email-Id :) ");
					}else {
							int status = JOptionPane.showConfirmDialog(null,"Are you sure you want to add this "+nameTxt.getText().trim()+" Customer?","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
							if(status == JOptionPane.YES_OPTION)
							{
									pst = con.prepareStatement("insert into customer values(?,?,?,TO_DATE('"+dateFormat.format(regDate)+"','YYYY/MM/DD'))");
									pst.setString(1, nameTxt.getText().trim());
									pst.setString(2,contactTxt.getText().trim());
									pst.setString(3,emailTxt.getText().trim());
									int ans = pst.executeUpdate();
									if(ans > 0)
									{
										JOptionPane.showMessageDialog(null,ans+" row inserted succusfully..." );
									}
							}
					}
					}catch(Exception ex) {	JOptionPane.showMessageDialog(null,"error:"+ex);	}
				}
			}
		});
		addBtn.setBounds(118, 280, 100, 35);
		frame.getContentPane().add(addBtn);
		
		JButton updateBtn = new JButton("UPDATE");
		updateBtn.setBackground(new Color(139, 0, 139));
		updateBtn.setFont(new Font("Verdana", Font.PLAIN, 18));
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isValidate()) {
					try {
					Connection con = ConnectionProvider.getCon();
					PreparedStatement pst = con.prepareStatement("select * from Customer where mobile=?");
					pst.setString(1,contactTxt.getText().trim());
					ResultSet rs = pst.executeQuery();
					if(rs.next()) {
						int status = JOptionPane.showConfirmDialog(null,"Are you sure you want to update this "+nameTxt.getText().trim()+" Customers info?","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
						if(status == JOptionPane.YES_OPTION)
						{
								pst = con.prepareStatement("update customer set name=?,email=? where mobile=?");
								pst.setString(1, nameTxt.getText().trim());
								pst.setString(2,emailTxt.getText().trim());
								pst.setString(3, contactTxt.getText().trim());
								
								int ans = pst.executeUpdate();
								if(ans > 0)
								{
									JOptionPane.showMessageDialog(null,ans+" row updated succusfully..." );
								}
						}
					}else{
						JOptionPane.showMessageDialog(null, "Sorry Mobile number does not exist, Please enter mobile number with country code like: 91XXXXXXXXXX");
					}
					}catch(Exception ex) {	JOptionPane.showMessageDialog(null,"error:"+ex);	}
				}
			}
		});
		updateBtn.setBounds(303, 280, 110, 35);
		frame.getContentPane().add(updateBtn);
		
		JButton clearBtn = new JButton("CLEAR");
		clearBtn.setBackground(new Color(139, 0, 139));
		clearBtn.setFont(new Font("Verdana", Font.PLAIN, 18));
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameTxt.setText("");
				contactTxt.setText("");
				emailTxt.setText("");
			}
		});
		clearBtn.setBounds(488, 280, 100, 35);
		frame.getContentPane().add(clearBtn);
	}
}
