package com.book.Book_Shop_System;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class Report implements Runnable {

	private JFrame frame;
	private JLabel lblNewLabel;
	public Customer customer;
	private JTextField bookNameTxt;
	private JTextField bookAuthorTxt;
	private JTable bookTable;
	private JScrollPane jsp;
	private DefaultTableModel model, model2,  selectedModel;
	private int selectedRow, cartSelectedRow = -1;
	private SimpleDateFormat dateFormat;
	private Date regDate;
	private double totalPrice = 0.0d;
	private String cust_contact_id = "None";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Report window = new Report();
					Thread t1 = new Thread(window);
					t1.start();
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
	public Report() {
		initialize();
		
	}
	
	public void run() {
		int i = 1320;
		while(true) {
			try {
				Thread.sleep(10);
				i--;
				lblNewLabel.setBounds(i, 11, 638, 54);
				if(i<-370) {
					i = 1330;
				}
			}catch(Exception ex) {}
		}
	}
	
	/*
	 * JTable handling for books
	 * */
	private void jTableTo() {
		try {
			bookTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"Barcode","ISBN","Book Name","Author","Publication","Publication Year","Language","Price"}));
		/*	jsp.setViewportView(bookTable);
			model = (DefaultTableModel) bookTable.getModel();
			Connection con = ConnectionProvider.getCon();
			PreparedStatement pst = con.prepareStatement("select * from book where not exists (select barcode from sold where sold.barcode = book.b_barcode)");
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				model.addRow(new Object[] {rs.getString("b_barcode"),rs.getString("isbn"),rs.getString("b_name"),rs.getString("author"),rs.getString("publication"),rs.getString("publi_year"),rs.getString("language"),rs.getString("price")});
			}
	*/ 	}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}
	private void searchBook(ResultSet rs) {
		try {
			
	/*		model = (DefaultTableModel) bookTable.getModel();
			do {
				model.addRow(new Object[] {rs.getString("b_barcode"),rs.getString("isbn"),rs.getString("b_name"),rs.getString("author"),rs.getString("publication"),rs.getString("publi_year"),rs.getString("language"),rs.getString("price")});
			}while(rs.next());
	/**/	}catch(Exception ex) { JOptionPane.showMessageDialog(null, ex); }
	}
	/*
	 * JTable for selected books 
	 * */
	private void addBook() {
		try {
	/*		jsp2.setViewportView(selectedBookTable);
			model2 = (DefaultTableModel) selectedBookTable.getModel();
	/*  	Connection con = ConnectionProvider.getCon();
			PreparedStatement pst = con.prepareStatement("select * from book");
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				model2.addRow(new Object[] {rs.getString("b_barcode"),rs.getString("b_name"),rs.getString("author")});
			}
	*/	}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 1385, 746);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		regDate = new Date();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(240, 248, 255));
		panel1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		panel1.setBounds(44, 93, 1282, 548);
		frame.getContentPane().add(panel1);
		panel1.setLayout(null);
		
		bookNameTxt = new JTextField();
		bookNameTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar()==10 && (!bookNameTxt.getText().trim().equals(""))) {
					try {
						Connection con = ConnectionProvider.getCon();
						PreparedStatement pst = con.prepareStatement("select * from book where Initcap(b_name) like Initcap(?)");
						pst.setString(1,("%"+bookNameTxt.getText()+"%"));
						ResultSet rs = pst.executeQuery();
						if(rs.next()) {
							bookTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"Barcode","ISBN","Book Name","Author","Publication","Publication Year","Language","Price"}));
							jsp.setViewportView(bookTable);
							searchBook(rs);
							while(rs.next()) {
								searchBook(rs);
							
							}
						}else{
							JOptionPane.showMessageDialog(null, "Record Not Found,Please Enter Valid Book Name");
							bookNameTxt.setText("");
							bookNameTxt.requestFocus();
						}
					}catch(Exception ex) {
							JOptionPane.showMessageDialog(null, ex);
						}
					bookNameTxt.requestFocus();
				}else {
					if(bookNameTxt.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(null, "Please Enter Something");
						bookNameTxt.setText("");
						bookNameTxt.requestFocus();

					}
				}

			}
		});
		bookNameTxt.setFont(new Font("Verdana", Font.PLAIN, 18));
		bookNameTxt.setColumns(20);
		bookNameTxt.setBounds(26, 36, 221, 29);
		panel1.add(bookNameTxt);
		
		bookAuthorTxt = new JTextField();
		bookAuthorTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar()==10 && (!bookAuthorTxt.getText().trim().equals(""))) {
					try {
						Connection con = ConnectionProvider.getCon();
						PreparedStatement pst = con.prepareStatement("select * from book where Initcap(author) like Initcap(?)");
						pst.setString(1,("%"+bookAuthorTxt.getText()+"%"));
						ResultSet rs = pst.executeQuery();
						if(rs.next()) {
							bookTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"Barcode","ISBN","Book Name","Author","Publication","Publication Year","Language","Price"}));
							jsp.setViewportView(bookTable);
							searchBook(rs);
							while(rs.next()) {
								searchBook(rs);
							}
						}else{
							JOptionPane.showMessageDialog(null, "Record Not Found,Please Enter Valid Author Name");
							bookAuthorTxt.setText("");
							bookAuthorTxt.requestFocus();
						}
					}catch(Exception ex) {
							JOptionPane.showMessageDialog(null, ex);
						}
					bookAuthorTxt.requestFocus();
				}else {
					if(bookAuthorTxt.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(null, "Please Enter Something");
						bookAuthorTxt.setText("");
						bookAuthorTxt.requestFocus();
					}
				}
			}
		});
		bookAuthorTxt.setFont(new Font("Verdana", Font.PLAIN, 18));
		bookAuthorTxt.setColumns(20);
		bookAuthorTxt.setBounds(273, 36, 181, 29);
		panel1.add(bookAuthorTxt);
		
		JLabel lblNewLabel_1_1 = new JLabel("Book Name");
		lblNewLabel_1_1.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(32, 14, 137, 23);
		panel1.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Book Author");
		lblNewLabel_1_2.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblNewLabel_1_2.setBounds(283, 14, 137, 23);
		panel1.add(lblNewLabel_1_2);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(15, 76, 1230, 0);
		panel1.add(separator_1);
		
		jsp = new JScrollPane();
		jsp.setBounds(26, 179, 1219, 350);
		panel1.add(jsp);
		
		bookTable = new JTable();
		jsp.setRowHeaderView(bookTable);
		bookTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedModel = (DefaultTableModel) bookTable.getModel();
				selectedRow = bookTable.getSelectedRow();
				
				
			}
		});
		bookTable.setBackground(Color.WHITE);
		bookTable.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		bookTable.setFont(new Font("Verdana", Font.PLAIN, 15));
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTableTo();
			}
		});
		btnNewButton_1.setBounds(1239, 179, 33, 29);
		panel1.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("All Sold Books");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection con = ConnectionProvider.getCon();
					PreparedStatement pst = con.prepareStatement("select s.SOLD_ID,s.BARCODE,s.CUST_NO,s.SOLD_DATE,p.PAY_ID,p.BOOK_PRICE,p.TAX,p.GST,p.TOTAL from sold s left Join Payment p on p.sold_id = s.sold_id");
					ResultSet rs = pst.executeQuery();
					if(rs.next()) {
//						DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//						centerRenderer.setHorizontalAlignment( JLabel.CENTER );
//						bookTable.setDefaultRenderer(String.class, centerRenderer);
						bookTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"SOLD ID","BOOK BARCODE","CUST MOBILE","SOLD DATE","PAYEMENT ID","BOOK PRICE","TAX","GST","GRAND TOTAL"}));
						jsp.setViewportView(bookTable);
						searchBook(rs);
						
							model = (DefaultTableModel) bookTable.getModel();
							
							do {
								
								model.addRow(new Object[] {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)});
							}while(rs.next());
//							for(int x=0;x<9;x++){
//						         bookTable.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
//						        }
							//DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
							
							 DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
						        rightRenderer.setHorizontalAlignment( JLabel.CENTER);

						        //TableModel tableModel = table.getModel();

						        for (int columnIndex = 0; columnIndex < model.getColumnCount(); columnIndex++)
						        {
						        	if(columnIndex==1) continue;
						            bookTable.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
						        }
							
							
					}else{
						JOptionPane.showMessageDialog(null, "Record Not Found");
					}
				}catch(Exception ex) {
						JOptionPane.showMessageDialog(null, ex);
					}
			}
		});
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnNewButton.setBounds(25, 87, 155, 29);
		panel1.add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("Sold Books");
		btnNewButton_2.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnNewButton_2.setBounds(218, 87, 155, 29);
		panel1.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Sold Books");
		btnNewButton_3.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnNewButton_3.setBounds(411, 87, 155, 29);
		panel1.add(btnNewButton_3);
		jTableTo();
		addBook();
		
		lblNewLabel = new JLabel("BOOK SHOP MANAGEMENT SYSTEM");
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblNewLabel.setBounds(112, 11, 638, 54);
		frame.getContentPane().add(lblNewLabel);
		frame.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{panel1}));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Book");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Book Transaction");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Book book = new Book(); 
				book.main(new String[] {});
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnNewMenu_1 = new JMenu("Customer");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("ADD Customer");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerForm window = new CustomerForm();
				window.main(new String[] {});
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
	}
	private static class __Tmp {
		private static void __tmp() {
			  javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
		}
	}
}
