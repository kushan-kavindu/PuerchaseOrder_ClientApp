package com;

import java.sql.*;

public class Purchase 
{

			//CONNECTION
			public Connection connect()
			{
					Connection con = null;

					try
					{
							Class.forName("com.mysql.jdbc.Driver");
							con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/purchase_order",	"root", "");
			
					}
					catch(Exception e)
					{
							e.printStackTrace();
					}

					return con;
			}
			
			
			
			
			//READ
			public String readPurchase()
			{
					String output = "";
					
					try
					{
							Connection con = connect();
							
							if (con == null)
							{
									return "Error while connecting to the database for reading.";
							}
							
							// Prepare the HTML table to be displayed
							output = "<table  class='table table-dark table-striped'><tr><th>Purchase Code</th>"
									+"<th>Purchase Type</th><th>Total Price</th>"
									+ "<th>Purchase Description</th>"
									+ "<th>Purchase Date</th>"
									+ "<th>Edit</th><th>Delete</th></tr>";

							String query = "select * from purchase_order";
							Statement stmt = con.createStatement();
							ResultSet rs = stmt.executeQuery(query);

							// iterate through the rows in the result set
							while (rs.next())
							{
									String purchaseID = Integer.toString(rs.getInt("purchaseID"));
									String purchaseCode = rs.getString("purchaseCode");
									String purchaseType = rs.getString("purchaseType");
									String TotalPrice = Double.toString(rs.getDouble("TotalPrice"));
									String purchaseDesc = rs.getString("purchaseDesc");
									String purchaseDate = rs.getString("purchaseDate");
									

									// Add a row into the HTML table
									output += "<tr><td>" + purchaseCode + "</td>";
									output += "<td>" + purchaseType + "</td>";
									output += "<td>" + TotalPrice + "</td>"; 
									output += "<td>" + purchaseDesc + "</td>";
									output += "<td>" + purchaseDate + "</td>"; 
								
									
									// buttons
									output += "<td><input name='btnUpdate' type='button' value='Edit' class='btnUpdate btn btn-secondary' data-purchaseid='" + purchaseID + "'></td>"
											+"<td><input name='btnRemove' type='button' value='Delete' class='btnRemove btn btn-danger' data-purchaseid='" + purchaseID + "'>" + "</td></tr>";
							}

							con.close();

							// Complete the HTML table
							output += "</table>";
					}
					catch (Exception e)
					{
							output = "Error while reading the purchase.";
							System.err.println(e.getMessage());
					}
					
					return output;
			}
			
			
			
			

			//INSERT
			public String insertPurchase(String code, String type, String tprice, String desc, String date)
			{
					String output = "";
					
					try
					{
							Connection con = connect();
							
							if (con == null)
							{
									return "Error while connecting to the database for inserting";
							}

							// create a prepared statement
							String query = " insert into purchase_order (`purchaseID`,`purchaseCode`,`purchaseType`,`TotalPrice`,`purchaseDesc`,`purchaseDate`) values (?, ?, ?, ?, ?, ?)";
							
							PreparedStatement preparedStmt = con.prepareStatement(query);

							// binding values
							preparedStmt.setInt(1, 0);
							preparedStmt.setString(2, code);
							preparedStmt.setString(3, type);
							preparedStmt.setDouble(4, Double.parseDouble(tprice));
							preparedStmt.setString(5, desc);
							preparedStmt.setString(6, date);

							//execute the statement
							preparedStmt.execute();
							con.close();

							String newPurchase = readPurchase();
							output = "{\"status\":\"success\", \"data\": \"" + newPurchase + "\"}";
			
					}
					catch (Exception e)
					{
								output = "{\"status\":\"error\", \"data\":\"Error while inserting the purchase.\"}";
								System.err.println(e.getMessage());
					}
					
					return output;
			}
			

			
			//UPDATE
			public String updatePurchase(String ID,String code, String type, String tprice, String desc, String date)
			{
					String output = "";
					
					try
					{
							Connection con = connect();
							
							if (con == null)
							{
									return "Error while connecting to the database for updating";
							}

							// create a prepared statement
							String query = "UPDATE purchase_order SET purchaseCode=?, purchaseType=?, TotalPrice=?, purchaseDesc=?, purchaseDate=? WHERE purchaseID=?";
							
							PreparedStatement preparedStmt = con.prepareStatement(query);

							// binding values
							preparedStmt.setString(1, code);
							preparedStmt.setString(2, type);
							preparedStmt.setDouble(3, Double.parseDouble(tprice));
							preparedStmt.setString(4, desc);
							preparedStmt.setString(5, date);
							preparedStmt.setInt(6, Integer.parseInt(ID));

							//execute the statement
							preparedStmt.executeUpdate();
							con.close();

							String newPurchase = readPurchase();
							output = "{\"status\":\"success\", \"data\": \"" + newPurchase + "\"}";
			
			
					}
					catch (Exception e)
					{
							output = "{\"status\":\"error\", \"data\":\"Error while updating the purchase.\"}";
							System.err.println(e.getMessage());
					}
					
					return output;
			}
			
			

			//DELETE
			public String deletePurchase(String id)
			{
					String output = "";
					
					try
					{
							Connection con = connect();
							
							if (con == null)
							{
									return "Error while connecting to the database for deleting";
							}

							// create a prepared statement
							String query = "DELETE from purchase_order where purchaseID=?";
							
							PreparedStatement preparedStmt = con.prepareStatement(query);

							// binding values
							preparedStmt.setInt(1, Integer.parseInt(id));

							//execute the statement
							preparedStmt.executeUpdate();
							con.close();

							String newPurchase = readPurchase();
							output = "{\"status\":\"success\", \"data\": \"" + newPurchase + "\"}";
					}
					catch (Exception e)
					{
						output = "{\"status\":\"error\", \"data\":\"Error while deleting the purchase.\"}";
						System.err.println(e.getMessage());
					}
					
					return output;
			}

	
}
