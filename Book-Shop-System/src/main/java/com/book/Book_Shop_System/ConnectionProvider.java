package com.book.Book_Shop_System;
import static com.book.Book_Shop_System.Provider.*;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider 
{
	private static Connection con = null;
	static {
		try {
				Class.forName(DRIVER);
				con = DriverManager.getConnection(CONNECTION_URL,USERNAME,PASSWORD);
		}catch(Exception e){ System.out.println(e);  }
	}
	public static Connection getCon() 
	{
		return con;
	}
}