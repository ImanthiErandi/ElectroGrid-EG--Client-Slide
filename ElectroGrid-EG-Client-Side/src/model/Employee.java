package model;
import java.sql.*;


public class Employee {

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
		
	public String insertEmployee(String name, String nic,String bDate,String dep,String address,String phone)
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
			 String query = " insert into employee (`empID`,`empName`,`empNIC`,`empBdate`,`empDep`,`empAddress`,`empPhone`)"
			 + " values (?, ?, ?, ?, ?, ?, ?)";
					 PreparedStatement preparedStmt = con.prepareStatement(query);
					 // binding values
					 preparedStmt.setInt(1, 0);
					 preparedStmt.setString(2, name);
					 preparedStmt.setString(3, nic);
					 preparedStmt.setString(4, bDate);
					 preparedStmt.setString(5, dep);
					 preparedStmt.setString(6, address);
					 preparedStmt.setString(7, phone);
					 
					 // execute the statement
					 preparedStmt.execute();
					 con.close();
					 String newEmployee = readEmployee();
					 output = "{\"status\":\"success\", \"data\": \"" +newEmployee + "\"}";
					 }
					 catch (Exception e)
					 {
					 output = "{\"status\":\"error\", \"data\":\"Error while inserting the unit.\"}";
					 System.err.println(e.getMessage());
					 }
					 return output;
					 } 

			 		
	public String readEmployee()
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
				 output = "<table border='1'><tr><th>Name</th>"
				 +"<th>NIC</th><th>Birth Date</th>"
				 +"<th>Department</th><th>Address</th>"
				 +"<th>Phone</th>"
				 + "<th>Update</th><th>Remove</th></tr>";
				 
				 String query = "select * from employee";
				 Statement stmt = con.createStatement();
				 ResultSet rs = stmt.executeQuery(query);
				 
				 // iterate through the rows in the result set
				 while (rs.next())
				 {
					 String empID = Integer.toString(rs.getInt("empID"));
					 String empName= rs.getString("empName");
					 String empNIC = rs.getString("empNIC");
					 String empBdate= rs.getString("empBdate");
					 String empDep = rs.getString("empDep");
					 String empAddress= rs.getString("empAddress");
					 String empPhone = rs.getString("empPhone");
					 
					 // Add a row into the html table
					 output += "<tr><td><input id='hidEmpIDUpdate' name='hidEmpIDUpdate' type='hidden' value='" + empID + "'>"+empName + "</td>";
					 output += "<td>" + empNIC + "</td>";
					 output += "<td>" + empBdate + "</td>";
					 output += "<td>" + empDep + "</td>";
					 output += "<td>" + empAddress + "</td>";
					 output += "<td>" + empPhone + "</td>";
					 
					// buttons
					 output += "<td><input name='btnUpdate' type='button' value='Update' "
							 + "class='btnUpdate btn btn-secondary' data-empid='" + empID + "'></td>"
					 		+"<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-empid='" + empID + "'></td></tr>";

				  }
				 con.close();
				 // Complete the html table
				 output += "</table>";
		  }
		  catch (Exception e)
		  {
			 output = "Error while reading the employee.";
			 System.err.println(e.getMessage());
		  }
		return output;
	}
	
	public String deleteUnit(String empID)
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
			 String query = "delete from employee where empID=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(empID));
		
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 
			 
			 String newEmployee = readEmployee();
			 output = "{\"status\":\"success\", \"data\": \"" +newEmployee + "\"}"; 

		 }
		 catch (Exception e)
		 {
		
			 output = "{\"status\":\"error\", \"data\":\"Error while deleting the employee.\"}"; 
			 System.err.println(e.getMessage());
		 }
		return output;
	}
	
	//method to update bill details in db
	public String updateEmployee(String empID,String name, String nic,String bDate,String dep,String address,String phone)
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
				 String query = "UPDATE employee SET empName=?,empNIC=?,empBdate=?,empDep=?,empAddress=?,empPhone=? WHERE empID=?";
				 PreparedStatement preparedStmt = con.prepareStatement(query);
				 
				 // binding values
				 preparedStmt.setString(1, name);
				 preparedStmt.setString(2, nic);
				 preparedStmt.setString(3, bDate);
				 preparedStmt.setString(4, dep);
				 preparedStmt.setString(5, address);
				 preparedStmt.setString(6, phone);
				 preparedStmt.setInt(7, Integer.parseInt(empID));
				 
				 // execute the statement
				 preparedStmt.execute();
				 con.close();
				 
				 String newEmployee = readEmployee();
				 output = "{\"status\":\"success\", \"data\": \"" +newEmployee + "\"}"; 

			}
			catch (Exception e)
			{
				 
				output = "{\"status\":\"error\", \"data\":\"Error while updating the employee.\"}"; 
				 System.err.println(e.getMessage());
			 }
			 return output;
		}
	
}
