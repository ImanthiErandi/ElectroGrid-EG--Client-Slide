package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Bill {
	
	public Connection connect()
	{
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid_db", "root", "User1994");
			
			//For testing
			System.out.print("Successfully connected");
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		
	    return con;
	}
		
	public String insertBill(String accNo, String name,String unit, String total, String date)
			 {
			 String output = "";
			 try
			 {
			 Connection con = connect();
			 if (con == null)
			 {
			 return "Error while connecting to the database for inserting.";
			 }
			 // create a prepared statement
			 String query = " insert into bill (`billNo`,`CustomerAccNo`,`CustomerName`,`Unit`,`Total`,`Date`)"+ " values (?, ?, ?, ?, ?, ?)";
					 PreparedStatement preparedStmt = con.prepareStatement(query);
					 // binding values
					 preparedStmt.setInt(1, 0);
					 preparedStmt.setString(2, accNo);
					 preparedStmt.setString(3, name);
					 preparedStmt.setString(4, unit);
					 preparedStmt.setDouble(5, Double.parseDouble(total));
					 preparedStmt.setString(6, date);
					
					 // execute the statement
					 preparedStmt.execute();
					 con.close();
					 String newBill = readBills();
					 output = "{\"status\":\"success\", \"data\": \"" +newBill + "\"}";
					 }
					 catch (Exception e)
					 {
					 output = "{\"status\":\"error\", \"data\":\"Error while inserting the bill.\"}";
					 System.err.println(e.getMessage());
					 }
					 return output;
					 } 

			 		
	public String readBills()
	{
		String output = "";
		try
		 {
			 Connection con = connect();
			 	if (con == null)
			 	{
			 		return "Error while connecting to the database for reading.";
			 	}
			 	
				 // Prepare the html table to be displayed
			 	 output = "<table border='1'><tr><th>Customer Account No</th><th>Customer Name</th><th>Unit</th><th>Total</th><th>Date</th>" +
						 "<th>Action</th></tr>";
				 
				 String query = "select * from bill";
				 Statement stmt = con.createStatement();
				 ResultSet rs = stmt.executeQuery(query);
				 
				 // iterate through the rows in the result set
				 while (rs.next())
				 {
					 String billNo = Integer.toString(rs.getInt("billNo"));
					 String CustomerAccNo = rs.getString("CustomerAccNo");
					 String CustomerName = rs.getString("CustomerName");
					 String Unit = rs.getString("Unit");
					 String Total = Double.toString(rs.getDouble("Total"));
					 String Date = rs.getString("Date");
					
					 
					 // Add a row into the html table
					 output += "<tr><td><input id='hidBillIDUpdate' name='hidBillIDUpdate' type='hidden' value='" + billNo + "'>"+ CustomerAccNo + "</td>";
					 output += "<td>" + CustomerName + "</td>";
					 output += "<td>" + Unit + "</td>";
					 output += "<td>" + Total + "</td>";
					 output += "<td>" + Date + "</td>";
					 
					 
					// buttons
					 output += "<td><input name='btnUpdate' type='button' value='Update' "
							 + "class='btnUpdate btn btn-secondary' data-billid='" + billNo + "'></td>"
					 		+"<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-billid='" + billNo + "'></td></tr>";

				  }
				 con.close();
				 // Complete the html table
				 output += "</table>";
		  }
		  catch (Exception e)
		  {
			 output = "Error while reading the bill.";
			 System.err.println(e.getMessage());
		  }
		return output;
	}
	
	public String deleteBill(String billNo)
	{
		 String output = "";
		 try
		 {
			 Connection con = connect();
			 if (con == null)
			 {
				 return "Error while connecting to the database for deleting.";
			 }
			 
			 // create a prepared statement
			 String query = "delete from bill where billNo=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(billNo));
		
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 
			 
			 String newBill = readBills();
			 output = "{\"status\":\"success\", \"data\": \"" +newBill + "\"}"; 

		 }
		 catch (Exception e)
		 {
		
			 output = "{\"status\":\"error\", \"data\":\"Error while deleting the bill.\"}"; 
			 System.err.println(e.getMessage());
		 }
		return output;
	}
	
	//method to update bill details in db
	public String updateBill(String billNo,String accNo, String name,String unit, String total, String date)
	{
		 String output = "";
			 try
			 {
				 Connection con = connect();
				 if (con == null)
				 {
					 return "Error while connecting to the database for updating."; 
				 }
				 
				 // create a prepared statement
				 String query = "UPDATE bill SET CustomerAccNo=?,CustomerName=?,Unit=?,Total=?,Date=? WHERE billNo=?";
				 PreparedStatement preparedStmt = con.prepareStatement(query);
				 
				 // binding values
				 preparedStmt.setString(1, accNo);
				 preparedStmt.setString(2, name);
				 preparedStmt.setString(3, unit);
				 preparedStmt.setDouble(4, Double.parseDouble(total));
				 preparedStmt.setString(5, date);
				 preparedStmt.setInt(6, Integer.parseInt(billNo));
				 
				 // execute the statement
				 preparedStmt.execute();
				 con.close();
				 
				 String newBill = readBills();
				 output = "{\"status\":\"success\", \"data\": \"" + newBill + "\"}"; 

			}
			catch (Exception e)
			{
				 
				output = "{\"status\":\"error\", \"data\":\"Error while updating the bill.\"}"; 
				 System.err.println(e.getMessage());
			 }
			 return output;
		}
	
}
