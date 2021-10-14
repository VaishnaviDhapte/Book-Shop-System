package com.book.Book_Shop_System;

import com.book.Book_Shop_System.ConnectionProvider;

import oracle.security.pki.OraclePKIX509CrlDPStore;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
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

public class Book {

	private JFrame frame;
	private JTextField barcodeTxt;
	private JTextField isbnTxt;
	private JTextField bNameTxt;
	private JTextField authorTxt;
	private JTextField publicationTxt;
	private JTextField publiYearTxt;
	private JTextField languageTxt;
	private JTextField bpriceTxt;
	private SimpleDateFormat dateFormat;
	private Date regDate;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Book window = new Book();
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
	public Book() {
		initialize();
	}
	private boolean isValidate() {
		while(true)
		{
			if(barcodeTxt.getText().trim().equals(""))
			{	JOptionPane.showMessageDialog(null, "Please Enter Barcode of Book"); barcodeTxt.requestFocus(); break; }
			if(isbnTxt.getText().trim().equals(""))
			{	JOptionPane.showMessageDialog(null, "Please Enter ISBN Number of Book"); isbnTxt.requestFocus(); break; }
			if(bNameTxt.getText().trim().equals(""))
			{	JOptionPane.showMessageDialog(null, "Please Enter Name of Book"); bNameTxt.requestFocus(); break; }
			if(authorTxt.getText().trim().equals(""))
			{	JOptionPane.showMessageDialog(null, "Please Enter Author of Book"); authorTxt.requestFocus(); break; }
			if(publicationTxt.getText().trim().equals(""))
			{	JOptionPane.showMessageDialog(null, "Please Enter Publication of Book"); publicationTxt.requestFocus(); break; }
			if(publiYearTxt.getText().trim().equals("") || publiYearTxt.getText().trim().length()!=4)
			{	JOptionPane.showMessageDialog(null, "Please Enter Valid Publication Year of Book  "); publiYearTxt.requestFocus(); break; }
			if(languageTxt.getText().trim().equals(""))
			{	JOptionPane.showMessageDialog(null, "Please Enter Language of Book"); languageTxt.requestFocus(); break; }
			if(bpriceTxt.getText().trim().equals(""))
			{	JOptionPane.showMessageDialog(null, "Please Enter Price of Book"); bpriceTxt.requestFocus(); break; }
			return true;
		}
		return false;
	}
	private void editable(boolean ans) {
		if(ans) {
			isbnTxt.setEditable(true);
			bNameTxt.setEditable(true);
			authorTxt.setEditable(true);
			publicationTxt.setEditable(true);
			publiYearTxt.setEditable(true);
			languageTxt.setEditable(true);
			bpriceTxt.setEditable(true);
		}else {
			isbnTxt.setEditable(false);
			bNameTxt.setEditable(false);
			authorTxt.setEditable(false);
			publicationTxt.setEditable(false);
			publiYearTxt.setEditable(false);
			languageTxt.setEditable(false);
			bpriceTxt.setEditable(false);
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(240, 248, 255));
		frame.setBounds(100, 100, 986, 528);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		regDate = new Date();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		JLabel lblNewLabel = new JLabel("Barcode :");
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel.setBounds(60, 84, 92, 27);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("ISBN Number :");
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(60, 161, 130, 27);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Book Name :");
		lblNewLabel_2.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(60, 240, 92, 27);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Author:");
		lblNewLabel_3.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(60, 320, 92, 27);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Publication :");
		lblNewLabel_4.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(560, 80, 130, 35);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Publication Year :");
		lblNewLabel_5.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel_5.setBounds(560, 159, 130, 31);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Language :");
		lblNewLabel_6.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel_6.setBounds(560, 236, 140, 35);
		frame.getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Price :");
		lblNewLabel_7.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel_7.setBounds(560, 320, 73, 27);
		frame.getContentPane().add(lblNewLabel_7);
		
		barcodeTxt = new JTextField();
		barcodeTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				//barcodeTxt.setBackground(Color.lightGray);
				if(barcodeTxt.getText().trim().length()>=15) {
					e.consume();
				}
				if(e.getKeyChar()==10 && (!barcodeTxt.getText().trim().equals(""))) {
					try {
						Connection con = ConnectionProvider.getCon();
						PreparedStatement pst = con.prepareStatement("select * from Book where B_Barcode=?");
						pst.setString(1,(barcodeTxt.getText()));
						ResultSet rs = pst.executeQuery();
						if(rs.next()) {
							isbnTxt.setText(rs.getString(2));
							bNameTxt.setText(rs.getString(3));
							authorTxt.setText(rs.getString(4));
							publicationTxt.setText(rs.getString(5));
							publiYearTxt.setText(rs.getString(6));
							languageTxt.setText(rs.getString(7));
							bpriceTxt.setText(rs.getString(8));
							//(rs.getString(9));
							editable(false);
						}else {
							JOptionPane.showMessageDialog(null, "Record Not Found");
							barcodeTxt.setText("");
							barcodeTxt.requestFocus();
						}
					}catch(Exception ex) {
							JOptionPane.showMessageDialog(null, ex);
						}
					barcodeTxt.requestFocus();
				}else {
					if(barcodeTxt.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(null, "Please Enter Something");
						barcodeTxt.setText("");
						barcodeTxt.requestFocus();

					}
				}
			}
		});
		barcodeTxt.setBounds(200, 80, 200, 30);
		frame.getContentPane().add(barcodeTxt);
		barcodeTxt.setColumns(10);
		
		isbnTxt = new JTextField();
		isbnTxt.setFont(new Font("Tahoma", Font.PLAIN, 13));
		isbnTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(isbnTxt.getText().length()>=13) {
					e.consume();
				}
				if(((e.getKeyChar()<'0')||(e.getKeyChar()>'9'))&&(e.getKeyChar()!='\b')&&(e.getKeyChar()!='\n')) {
					e.consume(); JOptionPane.showMessageDialog(null, "Enter Only Number");
				}
			}
		});
		isbnTxt.setBounds(200, 160, 200, 30);
		frame.getContentPane().add(isbnTxt);
		isbnTxt.setColumns(10);
		
		bNameTxt = new JTextField();
		bNameTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(bNameTxt.getText().length()>=50) {
					e.consume();
				}
			}
		});
		bNameTxt.setBounds(200, 240, 200, 30);
		frame.getContentPane().add(bNameTxt);
		bNameTxt.setColumns(10);
		
		authorTxt = new JTextField();
		authorTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(authorTxt.getText().length()>=50) {
					e.consume();
				}
			}
		});
		authorTxt.setBounds(200, 320, 200, 30);
		frame.getContentPane().add(authorTxt);
		authorTxt.setColumns(10);
		
		publicationTxt = new JTextField();
		publicationTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(publicationTxt.getText().length()>=50) {
					e.consume();
				}
			}
		});
		publicationTxt.setBounds(700, 80, 200, 30);
		frame.getContentPane().add(publicationTxt);
		publicationTxt.setColumns(10);
		
		publiYearTxt = new JTextField();
		publiYearTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(publiYearTxt.getText().length()>=4) {
					e.consume();
				}
				if(((e.getKeyChar()<'0')||(e.getKeyChar()>'9'))&&(e.getKeyChar()!='\b')&&(e.getKeyChar()!='\n')) {
					e.consume(); JOptionPane.showMessageDialog(null, "Enter Only Number");
				}
			}
		});
		publiYearTxt.setBounds(700, 160, 200, 30);
		frame.getContentPane().add(publiYearTxt);
		publiYearTxt.setColumns(10);
		
		languageTxt = new JTextField();
		languageTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(languageTxt.getText().length()>=30) {
					e.consume();
				}
			}
		});
		languageTxt.setBounds(700, 240, 200, 30);
		frame.getContentPane().add(languageTxt);
		languageTxt.setColumns(10);
		
		bpriceTxt = new JTextField();
		bpriceTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(bpriceTxt.getText().length()>=8) {
					e.consume();
				}
				if(((e.getKeyChar()<'0')||(e.getKeyChar()>'9'))&&(e.getKeyChar()!='\b')&&(e.getKeyChar()!='\n')) {
					e.consume(); JOptionPane.showMessageDialog(null, "Enter Only Number");
				}
			}
		});
		bpriceTxt.setBounds(700, 320, 200, 30);
		frame.getContentPane().add(bpriceTxt);
		bpriceTxt.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Book Details");
		lblNewLabel_8.setForeground(Color.MAGENTA);
		lblNewLabel_8.setFont(new Font("Verdana", Font.BOLD, 20));
		lblNewLabel_8.setBounds(373, 11, 167, 37);
		frame.getContentPane().add(lblNewLabel_8);
		
		JButton addBtn = new JButton("ADD");
		addBtn.setFont(new Font("Verdana", Font.BOLD, 14));
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				Connection con = ConnectionProvider.getCon();
				PreparedStatement pst = con.prepareStatement("select * from Book where B_Barcode=?");
				pst.setString(1,barcodeTxt.getText().trim());
				ResultSet rs = pst.executeQuery();
				PreparedStatement ipst = con.prepareStatement("select * from Book where ISBN=?");
				ipst.setString(1,isbnTxt.getText());
				ResultSet irs = ipst.executeQuery();
				boolean barcode = rs.next();
				if((irs.next())||(barcode))
				{
					if(barcode)
						JOptionPane.showMessageDialog(null,"This Barcode Number already Exists, Book Name is:  "+rs.getString("B_Name"));
					else	
						JOptionPane.showMessageDialog(null,"This ISBN Number already Exists, Book Name is:  "+irs.getString("B_Name"));
					
				}else	
				if(isValidate())
				{
					int status = JOptionPane.showConfirmDialog(null,"Are you sure you want to add this "+bNameTxt.getText()+" book?","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(status == JOptionPane.YES_OPTION)
					{
						pst = con.prepareStatement("insert into Book values(?,"+isbnTxt.getText()+",?,?,?,"+Integer.parseInt(publiYearTxt.getText())+",?,"+Integer.parseInt(bpriceTxt.getText().trim())+",TO_DATE('"+dateFormat.format(regDate)+"','YYYY/MM/DD'))");
						pst.setString(1, barcodeTxt.getText().trim()); 
						pst.setString(2, bNameTxt.getText().trim());
						pst.setString(3, authorTxt.getText().trim());
						pst.setString(4, publicationTxt.getText().trim()); 
						pst.setString(5, languageTxt.getText().trim());
						
					
						int ans = pst.executeUpdate();
						if(ans > 0)
						{
							JOptionPane.showMessageDialog(null,ans+" row inserted succusfully..." );
						}
					}
				}
				}catch(Exception exp) { JOptionPane.showMessageDialog(null, exp); }
			}
		});
		addBtn.setBounds(70, 395, 108, 35);
		frame.getContentPane().add(addBtn);
		
		JButton editBtn = new JButton("EDIT");
		editBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				Connection con = ConnectionProvider.getCon();
				PreparedStatement pst = con.prepareStatement("select * from Book where B_Barcode=?");
				pst.setString(1,barcodeTxt.getText());
				ResultSet rs = pst.executeQuery();
				if(rs.next()) {
					editable(true);
					barcodeTxt.setEditable(false);
				}else {
					JOptionPane.showMessageDialog(null, "Please search existable book by entering valid Barcode ID");
				}
				}catch(Exception exp) { JOptionPane.showMessageDialog(null, exp); }
			}
		});
		editBtn.setFont(new Font("Verdana", Font.BOLD, 14));
		editBtn.setBounds(216, 395, 103, 35);
		frame.getContentPane().add(editBtn);
		
		JButton updateBtn = new JButton("UPDATE");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection con = ConnectionProvider.getCon();
					PreparedStatement pst = con.prepareStatement("select * from Book where B_Barcode=?");
					pst.setString(1,barcodeTxt.getText().trim());
					ResultSet rs = pst.executeQuery();
					PreparedStatement ipst = con.prepareStatement("select * from Book where ISBN=?");
					ipst.setString(1,isbnTxt.getText());
					ResultSet irs = ipst.executeQuery();
					boolean barcode = rs.next();
					if((! irs.next()) || (! barcode))
					{
						if(!barcode)
							JOptionPane.showMessageDialog(null,"Sorry! This Barcode Number Doesn't Exist, Please Enter Valid Number");
						else	
							JOptionPane.showMessageDialog(null,"Sorry! This ISBN Number Doesn't Exist, Please Enter Valid ISBN Number" );
						
					}else	
					if(isValidate())
					{
						int status = JOptionPane.showConfirmDialog(null,"Are you sure you want to Update this "+bNameTxt.getText()+" book?","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
						if(status == JOptionPane.YES_OPTION)
						{
							pst = con.prepareStatement("update book set b_Name=?,author=?,publication=?,publi_year="+Integer.parseInt(publiYearTxt.getText())+",language=?,price="+Integer.parseInt(bpriceTxt.getText().trim())+",b_regdate=TO_DATE('"+dateFormat.format(regDate)+"','YYYY/MM/DD') where b_barcode=?");
							
							pst.setString(1, bNameTxt.getText().trim());
							pst.setString(2, authorTxt.getText().trim());
							pst.setString(3, publicationTxt.getText().trim()); 
							pst.setString(4, languageTxt.getText().trim());
							pst.setString(5, barcodeTxt.getText().trim());
							
							
						
							int ans = pst.executeUpdate();
							if(ans > 0)
							{
								JOptionPane.showMessageDialog(null,ans+" Row Updated Successfully..." );
							}
						}
					}
					}catch(Exception exp) { JOptionPane.showMessageDialog(null, exp); }
			}
		});
		updateBtn.setFont(new Font("Verdana", Font.BOLD, 14));
		updateBtn.setBounds(368, 395, 103, 35);
		frame.getContentPane().add(updateBtn);
		
		JButton closeBtn = new JButton("CLOSE");
		closeBtn.setFont(new Font("Verdana", Font.BOLD, 14));
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		closeBtn.setBounds(802, 395, 98, 35);
		frame.getContentPane().add(closeBtn);
		
		JButton deleteBtn = new JButton("DELETE");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection con = ConnectionProvider.getCon();
					PreparedStatement pst = con.prepareStatement("select * from Book where B_Barcode=?");
					pst.setString(1,barcodeTxt.getText().trim());
					ResultSet rs = pst.executeQuery();
					
					if(rs.next())
					{
						int status = JOptionPane.showConfirmDialog(null,"Are you sure you want to Delete this "+bNameTxt.getText()+" book?","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
						if(status == JOptionPane.YES_OPTION)
						{
							PreparedStatement pst2 = con.prepareStatement("delete from Book where B_Barcode=?");
							pst2.setString(1,barcodeTxt.getText().trim());
							int ans = pst2.executeUpdate();
							if(ans>0) {
								JOptionPane.showMessageDialog(null, "Record Deleted Successfully :)");
								editable(true);
								barcodeTxt.setEditable(true);
								barcodeTxt.setText("");
								isbnTxt.setText("");
								bNameTxt.setText("");
								authorTxt.setText("");
								publicationTxt.setText("");
								publiYearTxt.setText("");
								languageTxt.setText("");
								bpriceTxt.setText("");
							}
						}
					}else {
						JOptionPane.showMessageDialog(null, "This Barcode does not  match any record, Please  Enter Valid Barcode Number");
						}
				}catch(Exception ex) { JOptionPane.showMessageDialog(null, ex); }
			}
		});
		deleteBtn.setFont(new Font("Verdana", Font.BOLD, 14));
		deleteBtn.setBounds(519, 395, 103, 35);
		frame.getContentPane().add(deleteBtn);
		
		JButton clearBtn = new JButton("CLEAR");
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editable(true);
				barcodeTxt.setEditable(true);
				barcodeTxt.setText("");
				isbnTxt.setText("");
				bNameTxt.setText("");
				authorTxt.setText("");
				publicationTxt.setText("");
				publiYearTxt.setText("");
				languageTxt.setText("");
				bpriceTxt.setText("");
			}
		});
		clearBtn.setFont(new Font("Verdana", Font.BOLD, 14));
		clearBtn.setBounds(658, 395, 103, 35);
		frame.getContentPane().add(clearBtn);
	}
}
