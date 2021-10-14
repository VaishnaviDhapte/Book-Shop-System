package com.book.Book_Shop_System;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Color;

public class Login {

	private JFrame frame;
	private JTextField userIdTxt;
	private JTextField passTxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(240, 248, 255));
		frame.setBounds(100, 100, 378, 376);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Email-Id :");
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel.setBounds(70, 61, 86, 31);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password :");
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(70, 126, 86, 31);
		frame.getContentPane().add(lblNewLabel_1);
		
		userIdTxt = new JTextField();
		userIdTxt.setBackground(SystemColor.menu);
		userIdTxt.setFont(new Font("Verdana", Font.PLAIN, 14));
		userIdTxt.setBounds(179, 60, 133, 32);
		frame.getContentPane().add(userIdTxt);
		userIdTxt.setColumns(10);
		
		passTxt = new JTextField();
		passTxt.setBackground(SystemColor.menu);
		passTxt.setFont(new Font("Verdana", Font.PLAIN, 14));
		passTxt.setBounds(180, 126, 132, 31);
		frame.getContentPane().add(passTxt);
		passTxt.setColumns(10);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.setForeground(new Color(0, 0, 0));
		loginBtn.setBackground(new Color(0, 0, 255));
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userId = userIdTxt.getText();
				String pass = passTxt.getText();
				try {
					Connection con = ConnectionProvider.getCon();
					PreparedStatement ps = con.prepareStatement("select * from Admin where A_Mail_Id=? and Password=?");
					ps.setString(1,userId);
					ps.setString(2,pass);
					ResultSet rs = ps.executeQuery();
					if(rs.next())
					{
						JOptionPane.showMessageDialog(null,"Login Successfully...");
						Home home = new Home();
						home.main(new String[] {});
				        frame.dispose();
						
					}else {
						JOptionPane.showMessageDialog(null,"Invalid Username or Password...");
					}
					
					
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null,"Error : "+e1);
				}
			}
		});
		loginBtn.setFont(new Font("Verdana", Font.PLAIN, 14));
		loginBtn.setBounds(43, 217, 113, 32);
		frame.getContentPane().add(loginBtn);
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setBackground(new Color(0, 0, 255));
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		cancelBtn.setFont(new Font("Verdana", Font.PLAIN, 14));
		cancelBtn.setBounds(196, 217, 116, 32);
		frame.getContentPane().add(cancelBtn);
	}
}
