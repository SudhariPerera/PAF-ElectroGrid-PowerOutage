package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Outage {

	//Database Connection Methods
	public Connection connect() 
	{ 
	 Connection con = null; 
	 
	 try 
	 { 
	 Class.forName("com.mysql.jdbc.Driver"); 
	 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/electri", 
	 "root", ""); 
	 //For testing
	 System.out.print("Successfully connected"); 
	 } 
	 catch(Exception e) 
	 { 
	 e.printStackTrace(); 
	 } 
	 
	 return con; 
	}
	
	
	
	
	
	
	

	//Read Data Method
	public String readOutage() {
		String output = "";
		
		try
		{ 
			Connection con = connect(); 
			if (con == null) 
			{ 
				return "Error while connecting to the database for reading."; 
			}
			
			// Prepare the html table to be displayed
			 output = "<table border='1'class=\"table table-striped\"><tr><th>Outage Code</th>" 
					 + "<th>Time</th>"
					 + "<th>Date</th>" 
					 + "<th>Emp No.</th>" 
					 + "<th>Area Code</th>" 
					 + "<th>Update</th><th>Remove</th></tr>";  

			 
			 
			 String query = "select * from outage"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
				 	String oid = Integer.toString(rs.getInt("oid"));
				 	String ocode = rs.getString("ocode");
					String time = rs.getString("time");
					String date = rs.getString("date");
					String empid = rs.getString("empid");
					String areacode = rs.getString("areacode");
					 
					 // Add a row into the html table 
					
					output += "<tr><td>" + ocode + "</td>";
					output += "<td>" + time + "</td>";
					output += "<td>" + date + "</td>";
					output += "<td>" + empid + "</td>";
					output += "<td>" + areacode + "</td>";
					 
					// buttons				 
					 output += "<td><input name='btnUpdate' " + " type='button' value='Update' " + " class='btnUpdate btn btn-outline-secondary' data-itemid='" + oid + "'></td>"
							 + "<td><input name='btnRemove' " + " type='button' value='Remove' " + " class='btnRemove btn btn-outline-danger' data-itemid='" + oid + "'>"
							 + "</td></tr>";
					 
			 } 
			 
			 con.close(); 
			 // Complete the html table
			 output += "</table>";

			
		} 
		catch (Exception e) 
		{ 
			output = "Error while reading the Outage."; 
			System.err.println(e.getMessage()); 
		}
		
		return output;
	}
	
	
	
	
	
	
	//Insert Data Method
		public String insertOutage(String ocode,String time, String date, String empid, String areacode) {
			
			String output = ""; 
			try
			 { 
				Connection con = connect(); 
				
				if (con == null) 
				{ 
					return "Error while connecting to the database"; 
				} 
			 
			
				// create a prepared statement
				String query = " insert into outage(`oid`,`ocode`,`time`,`date`,`empid`,`areacode`)" + " values (?, ?, ?, ?, ?, ?)";
				
				
				PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, ocode);
				preparedStmt.setString(3, time);
				preparedStmt.setString(4, date);
				preparedStmt.setString(5, empid);
				preparedStmt.setString(6, areacode);
				
				//execute the statement
				preparedStmt.execute(); 
				con.close(); // close the connection
				 
				output = "Outage Details Inserted successfully!"; 
				
				String newOutage = readOutage(); 
				output = "{\"status\":\"success\", \"data\": \"" + newOutage + "\"}";
			} 		
			
			
			catch (Exception e) 
			{ 
				output = "{\"status\":\"error\", \"data\": \"Error while inserting the outage details.\"}";
				System.err.println(e.getMessage()); 
			} 
			
			return output;
		}
	
	
	
	
	

		//update method
		public String updateOutage(String oid, String ocode, String  time, String date, String empid, String areacode)  {
			String output = "";

			try {
				Connection con = connect();

				if (con == null) {
					return "Error while connecting to the database for updating.";
				}

				// create a prepared statement
				String query = "UPDATE outage SET ocode=?,time=?,date=?,empid=?,areacode=?" + "WHERE oid=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setString(1, ocode);
				preparedStmt.setString(2, time);
				preparedStmt.setString(3, date);
				preparedStmt.setString(4, empid);
				preparedStmt.setString(5, areacode);
				preparedStmt.setInt(6, Integer.parseInt(oid));
				
				// execute the statement
				preparedStmt.execute();
				con.close();

				output = "Outage Details Updated successfully!";
				
				
				String newOutage = readOutage(); 
				output = "{\"status\":\"success\", \"data\": \"" + newOutage + "\"}";
				
			} catch (Exception e) {
				output = "{\"status\":\"error\", \"data\": \"Error while updating the Outage Details.\"}";
				System.err.println(e.getMessage());
			}

			return output;
		}
	
	
	
	
	
	
	

		//Delete Methods
		public String deleteOutage(String oid)
		{ 
		 String output = ""; 
		 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) { 
				 return "Error while connecting to the database for deleting."; 
			 }
			 
			 
			 // create a prepared statement
			 String query = "delete from outage where oid=?";
			 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(oid)); 
			  
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 
			 output = "Outage Detail Deleted successfully!";
			 
			 String newOutage = readOutage(); 
			 output = "{\"status\":\"success\", \"data\": \"" + newOutage + "\"}";

		 } 
		 catch (Exception e) 
		 { 
			 output = "{\"status\":\"error\", \"data\": \"Error while deleting the Outage detail.\"}";
			 System.err.println(e.getMessage()); 
		 }
		 
		 return output; 
		}


	
}
