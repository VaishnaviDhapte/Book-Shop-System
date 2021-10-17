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

import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class Home implements Runnable {

	private JFrame frame;
	private JLabel lblNewLabel;
	private JTextField contactTxt;
	private JLabel cNameLbl;
	private JLabel cEmailLbl;
	public Customer customer;
	private JTextField bookBarcodeTxt;
	private JTextField bookNameTxt;
	private JTextField bookAuthorTxt;
	private JTable bookTable;
	private JTable selectedBookTable;
	private JScrollPane jsp,jsp2;
	private DefaultTableModel model, model2,  selectedModel;
	private JLabel barcodeLbl,bookNameLbl, authorLbl;
	private int selectedRow, cartSelectedRow = -1;
	private JTextField gstTxt;
	private JTextField taxTxt;
	private JTextField totalPriceTxt;
	private JTextField totalTxt;
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
					Home window = new Home();
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
	public Home() {
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
			selectedBookTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"Barcode","Book Name","Author","Price"}));
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
		panel1.setBounds(44, 93, 855, 548);
		frame.getContentPane().add(panel1);
		panel1.setLayout(null);
		
		bookBarcodeTxt = new JTextField();
		bookBarcodeTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar()==10 && (!bookBarcodeTxt.getText().trim().equals(""))) {
					try {
						Connection con = ConnectionProvider.getCon();
						PreparedStatement pst = con.prepareStatement("select * from book where b_barcode=?");
						pst.setString(1,(bookBarcodeTxt.getText()));
						ResultSet rs = pst.executeQuery();
						if(rs.next()) {
							bookTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"Barcode","ISBN","Book Name","Author","Publication","Publication Year","Language","Price"}));
							jsp.setViewportView(bookTable);
							searchBook(rs);
						}else {
							JOptionPane.showMessageDialog(null, "Record Not Found, Please enter valid borcode number");
							bookBarcodeTxt.setText("");
							bookBarcodeTxt.requestFocus();
						}
					}catch(Exception ex) {
							JOptionPane.showMessageDialog(null, ex);
						}
					bookBarcodeTxt.requestFocus();
				}else
					if(bookBarcodeTxt.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(null, "Please Enter Something");
						bookBarcodeTxt.setText("");
						bookBarcodeTxt.requestFocus();
					}
			}
		});
		bookBarcodeTxt.setFont(new Font("Verdana", Font.PLAIN, 18));
		bookBarcodeTxt.setColumns(20);
		bookBarcodeTxt.setBounds(26, 36, 154, 29);
		panel1.add(bookBarcodeTxt);
		
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
		bookNameTxt.setBounds(218, 36, 309, 29);
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
		bookAuthorTxt.setBounds(564, 36, 252, 29);
		panel1.add(bookAuthorTxt);
		
		JLabel lblNewLabel_1 = new JLabel("Book Barcode");
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(43, 14, 137, 23);
		panel1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Book Name");
		lblNewLabel_1_1.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(254, 14, 137, 23);
		panel1.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Book Author");
		lblNewLabel_1_2.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblNewLabel_1_2.setBounds(578, 14, 137, 23);
		panel1.add(lblNewLabel_1_2);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(15, 75, 820, 1);
		panel1.add(separator_1);
		
		jsp = new JScrollPane();
		jsp.setBounds(26, 179, 790, 350);
		panel1.add(jsp);
		
		bookTable = new JTable();
		jsp.setRowHeaderView(bookTable);
		bookTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedModel = (DefaultTableModel) bookTable.getModel();
				selectedRow = bookTable.getSelectedRow();
				barcodeLbl.setText(selectedModel.getValueAt(selectedRow, 0).toString());
				bookNameLbl.setText(selectedModel.getValueAt(selectedRow, 2).toString());
				authorLbl.setText(selectedModel.getValueAt(selectedRow, 3).toString());
				
			}
		});
		bookTable.setBackground(Color.WHITE);
		bookTable.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		bookTable.setFont(new Font("Verdana", Font.PLAIN, 15));
		
		barcodeLbl = new JLabel("");
		barcodeLbl.setFont(new Font("Verdana", Font.PLAIN, 18));
		barcodeLbl.setBounds(27, 87, 153, 29);
		panel1.add(barcodeLbl);
		
		bookNameLbl = new JLabel("");
		bookNameLbl.setFont(new Font("Verdana", Font.PLAIN, 18));
		bookNameLbl.setBounds(218, 87, 309, 29);
		panel1.add(bookNameLbl);
		
		authorLbl = new JLabel("");
		authorLbl.setFont(new Font("Verdana", Font.PLAIN, 18));
		authorLbl.setBounds(564, 87, 252, 29);
		panel1.add(authorLbl);
		
		JButton btnNewButton = new JButton("ADD TO CART-->");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(barcodeLbl.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Please select a book");
				}else {
					String strBarcode = selectedModel.getValueAt(selectedRow, 0).toString();
					int n = model2.getRowCount();
					boolean flag = true;
					for(int i=0; i<n; i++) {
							String isBarcode = model2.getValueAt(i, 0).toString();
							if(isBarcode.equals(strBarcode)) {
								flag = false;
								break; 
							}
					} 
					if(flag) {
						String strBookName = selectedModel.getValueAt(selectedRow, 2).toString();
						String strAuthor = selectedModel.getValueAt(selectedRow, 3).toString();
						String strPrice = selectedModel.getValueAt(selectedRow, 7).toString();
						totalPrice = totalPrice + Double.parseDouble(strPrice);
						model2.addRow(new Object[] {strBarcode,strBookName,strAuthor,strPrice});
						totalTxt.setText(totalPrice + "");
						taxTxt.setText((totalPrice*0.11)+"");
						gstTxt.setText((totalPrice*0.12)+"");
						totalPriceTxt.setText((totalPrice + (totalPrice*0.11) + (totalPrice*0.12)) + "");
						
					}else {
						JOptionPane.showMessageDialog(null, "Sorry, this book already exists in the cart...");
					}
				}
			}
		});
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnNewButton.setBounds(640, 139, 176, 29);
		panel1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTableTo();
			}
		});
		btnNewButton_1.setBounds(814, 178, 35, 29);
		panel1.add(btnNewButton_1);
		jTableTo();
		
		JPanel panel2 = new JPanel();
		panel2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		panel2.setBackground(new Color(240, 248, 255));
		panel2.setBounds(909, 93, 415, 548);
		frame.getContentPane().add(panel2);
		panel2.setLayout(null);
		
		contactTxt = new JTextField();
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
						cust_contact_id = contactTxt.getText().trim();
						ResultSet rs = pst.executeQuery();
						if(rs.next()) {
							cNameLbl.setText(rs.getString("name"));
							cEmailLbl.setText(rs.getString("email"));
							
							customer = Customer.getInstance();
							customer.setName(rs.getString("name"));
							customer.setContact(rs.getString("email"));
							customer.setEmail(rs.getString("mobile"));
						}else {
							JOptionPane.showMessageDialog(null, "Record Not Found, Please enter mobile number with country code like 91XXXXXXXXXX");
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
		contactTxt.setFont(new Font("Verdana", Font.PLAIN, 18));
		contactTxt.setBounds(15, 38, 145, 29);
		panel2.add(contactTxt);
		contactTxt.setColumns(20);
		
		cNameLbl = new JLabel("");
		cNameLbl.setFont(new Font("Verdana", Font.PLAIN, 18));
		//cNameLbl.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		cNameLbl.setBounds(182, 38, 210, 29);
		panel2.add(cNameLbl);
		
		cEmailLbl = new JLabel("",JLabel.CENTER);
		cEmailLbl.setForeground(new Color(30, 144, 255));
		cEmailLbl.setFont(new Font("Verdana", Font.PLAIN, 18));
		//cEmailLbl.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		cEmailLbl.setBounds(49, 78, 311, 29);
		panel2.add(cEmailLbl);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 120, 400, 1);
		panel2.add(separator);
		
		JLabel lblNewLabel_1_3 = new JLabel("Customer Mobile");
		lblNewLabel_1_3.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblNewLabel_1_3.setBounds(15, 14, 137, 23);
		panel2.add(lblNewLabel_1_3);
		
		selectedBookTable = new JTable();
		selectedBookTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				model2 = (DefaultTableModel) selectedBookTable.getModel();
				cartSelectedRow = selectedBookTable.getSelectedRow();
			}
		});
		selectedBookTable.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		selectedBookTable.setBounds(15, 180, 385, 250);
		panel2.add(selectedBookTable);
		
		jsp2 = new JScrollPane();
		jsp2.setBounds(15, 180, 385, 250);
		panel2.add(jsp2);
		
		JButton removeBtn = new JButton("<--REMOVE");
		removeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cartSelectedRow >= 0) {
					String price = model2.getValueAt(cartSelectedRow, 3).toString();
					totalPrice = totalPrice - Double.parseDouble(price);
				
					model2.removeRow(cartSelectedRow);
					cartSelectedRow = -1;
					totalTxt.setText(totalPrice + "");
					taxTxt.setText((totalPrice*0.11)+"");
					gstTxt.setText((totalPrice*0.12)+"");
					totalPriceTxt.setText((totalPrice + (totalPrice*0.11) + (totalPrice*0.12)) + "");
				}else
					JOptionPane.showMessageDialog(null, "Please select book...");
			}
		});
		removeBtn.setFont(new Font("Verdana", Font.PLAIN, 14));
		removeBtn.setBounds(15, 140, 137, 29);
		panel2.add(removeBtn);
		
		gstTxt = new JTextField();
		gstTxt.setFont(new Font("Verdana", Font.PLAIN, 14));
		gstTxt.setBounds(88, 480, 103, 29);
		panel2.add(gstTxt);
		gstTxt.setColumns(10);
		
		taxTxt = new JTextField();
		taxTxt.setFont(new Font("Verdana", Font.PLAIN, 14));
		taxTxt.setBounds(88, 442, 103, 29);
		panel2.add(taxTxt);
		taxTxt.setColumns(10);
		
		totalPriceTxt = new JTextField();
		totalPriceTxt.setFont(new Font("Verdana", Font.PLAIN, 14));
		totalPriceTxt.setBounds(310, 480, 90, 29);
		panel2.add(totalPriceTxt);
		totalPriceTxt.setColumns(10);
		
		JButton sellBtn = new JButton("SELL");
		sellBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cust_contact_id.equals(contactTxt.getText().trim()))
				{
					int status = JOptionPane.showConfirmDialog(null,"Are you sure you want to Sell these books?","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(status == JOptionPane.YES_OPTION)
					{
						try {
							Connection con = ConnectionProvider.getCon();
							int record_count = 0;
							int n = model2.getRowCount();
							for(int i=0; i<n;i++) {
								String barcode = model2.getValueAt(0, 0).toString();
								
								PreparedStatement pst = con.prepareStatement("insert into sold values(seq_sold.nextval, ?, ?,TO_DATE('"+dateFormat.format(regDate)+"','YYYY/MM/DD'))");
								pst.setString(1, barcode);
								pst.setString(2,cust_contact_id);
								
								int ans = pst.executeUpdate();
								
								record_count++;
								if(ans > 0) {
									PreparedStatement get_sold_id = con.prepareStatement("select sold_id from sold where barcode=?");
									get_sold_id.setString(1,barcode);
									ResultSet rs = get_sold_id.executeQuery();
									if(rs.next()) {
										int sold_id = Integer.parseInt(rs.getString("SOLD_ID"));
										
										PreparedStatement pstPayment = con.prepareStatement("insert into payment values(seq_payment.nextval,?,?,?,?,?)");
										double bPrice = Double.parseDouble(model2.getValueAt(0, 3).toString());
										double btax = bPrice * 0.11;
										double bgst = bPrice * 0.12;
										double btotal = bPrice + btax + bgst;
										pstPayment.setDouble(1,bPrice);
										pstPayment.setDouble(2, btax);
										pstPayment.setDouble(3, bgst);
										pstPayment.setDouble(4, btotal);
										pstPayment.setInt(5, sold_id);
										
										pstPayment.executeUpdate();
									}
									model2.removeRow(0);
								}
							}
							
							JOptionPane.showMessageDialog(null,record_count+" Books Sold Successfully" );
							jTableTo();
							contactTxt.setText("");
							cNameLbl.setText("");
							cEmailLbl.setText("");
							taxTxt.setText("");
							gstTxt.setText("");
							totalPriceTxt.setText("");
							totalTxt.setText("");
							totalPrice = 0.0d;
							cust_contact_id = "None";
							
						}catch(Exception ex) { JOptionPane.showMessageDialog(null, ex); }
					}
				}else {
					JOptionPane.showMessageDialog(null, "Please Select Customer who wants to buy the books.");
				}
			}
		});
		sellBtn.setFont(new Font("Verdana", Font.PLAIN, 14));
		sellBtn.setBounds(160, 514, 100, 29);
		panel2.add(sellBtn);
		
		JLabel lblNewLabel_2 = new JLabel("TAX (11%):");
		lblNewLabel_2.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(15, 449, 78, 17);
		panel2.add(lblNewLabel_2);
		
		totalTxt = new JTextField();
		totalTxt.setFont(new Font("Verdana", Font.PLAIN, 14));
		totalTxt.setBounds(310, 442, 90, 29);
		panel2.add(totalTxt);
		totalTxt.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("GST (12%):");
		lblNewLabel_3.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(15, 486, 78, 17);
		panel2.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("SUB TOTAL :");
		lblNewLabel_4.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(218, 449, 103, 17);
		panel2.add(lblNewLabel_4);
		
		JLabel lblNewLabel_6 = new JLabel("GRAND TOTAL :");
		lblNewLabel_6.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel_6.setBounds(204, 484, 126, 23);
		panel2.add(lblNewLabel_6);
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
