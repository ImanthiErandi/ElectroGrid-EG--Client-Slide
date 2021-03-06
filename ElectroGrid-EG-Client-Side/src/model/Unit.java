package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Unit {

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
		
	public String insertUnit(String code, String amount,String price)
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
			 String query = " insert into unitchargers (`idUnit`,`UnitCode`,`UnitAmount`,`UnitPrice`)"
			 + " values (?, ?, ?, ?)";
					 PreparedStatement preparedStmt = con.prepareStatement(query);
					 // binding values
					 preparedStmt.setInt(1, 0);
					 preparedStmt.setString(2, code);
					 preparedStmt.setString(3, amount);
					 preparedStmt.setDouble(4, Double.parseDouble(price));
					
					 // execute the statement
					 preparedStmt.execute();
					 con.close();
					 String newUnits = readUnits();
					 output = "{\"status\":\"success\", \"data\": \"" +newUnits + "\"}";
					 }
					 catch (Exception e)
					 {
					 output = "{\"status\":\"error\", \"data\":\"Error while inserting the unit.\"}";
					 System.err.println(e.getMessage());
					 }
					 return output;
					 } 

			 		
	public String readUnits()
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
				 output = "<table border='1'><tr><th>Unit Code</th>"
				 +"<th>Unit Amount</th><th>Unit Price</th>"
				 + "<th>Update</th><th>Remove</th></tr>";
				 
				 String query = "select * from unitchargers";
				 Statement stmt = con.createStatement();
				 ResultSet rs = stmt.executeQuery(query);
				 
				 // iterate through the rows in the result set
				 while (rs.next())
				 {
					 String idUnit = Integer.toString(rs.getInt("idUnit"));
					 String UnitCode= rs.getString("UnitCode");
					 String UnitAmount = rs.getString("UnitAmount");
					 String UnitPrice = Double.toString(rs.getDouble("UnitPrice"));
					
					 
					 // Add a row into the html table
					 output += "<tr><td><input id='hidUnitIDUpdate' name='hidUnitIDUpdate' type='hidden' value='" + idUnit + "'>"+ UnitCode + "</td>";
					 output += "<td>" + UnitAmount + "</td>";
					 output += "<td>" + UnitPrice + "</td>";
					 
					 
					// buttons
					 output += "<td><input name='btnUpdate' type='button' value='Update' "
							 + "class='btnUpdate btn btn-secondary' data-unitid='" + idUnit + "'></td>"
					 		+"<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-unitid='" + idUnit + "'></td></tr>";

				  }
				 con.close();
				 // Complete the html table
				 output += "</table>";
		  }
		  catch (Exception e)
		  {
			 output = "Error while reading the units.";
			 System.err.println(e.getMessage());
		  }
		return output;
	}
	
	public String deleteUnit(String idUnit)
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
			 String query = "delete from unitchargers where idUnit=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(idUnit));
		
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 
			 
			 String newUnits = readUnits();
			 output = "{\"status\":\"success\", \"data\": \"" +newUnits + "\"}"; 

		 }
		 catch (Exception e)
		 {
		
			 output = "{\"status\":\"error\", \"data\":\"Error while deleting the unit.\"}"; 
			 System.err.println(e.getMessage());
		 }
		return output;
	}
	
	//method to update bill details in db
	public String updateUnits(String idUnit,String code, String amount, String price)
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
				 String query = "UPDATE unitchargers SET UnitCode=?,UnitAmount=?,UnitPrice=? WHERE idUnit=?";
				 PreparedStatement preparedStmt = con.prepareStatement(query);
				 
				 // binding values
				 preparedStmt.setString(1, code);
				 preparedStmt.setString(2, amount);
				 preparedStmt.setDouble(3, Double.parseDouble(price));
				 preparedStmt.setInt(4, Integer.parseInt(idUnit));
				 
				 // execute the statement
				 preparedStmt.execute();
				 con.close();
				 
				 String newUnits = readUnits();
				 output = "{\"status\":\"success\", \"data\": \"" +newUnits + "\"}"; 

			}
			catch (Exception e)
			{
				 
				output = "{\"status\":\"error\", \"data\":\"Error while updating the unit.\"}"; 
				 System.err.println(e.getMessage());
			 }
			 return output;
		}
	
}
