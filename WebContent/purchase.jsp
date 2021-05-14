<%@ page import="com.Purchase"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Purchase Order Management</title>

<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/purchase.js"></script>
<link rel="stylesheet" href="Views/bootstrap.min.css">

</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">
			
				<h1>Purchase Order Management</h1>
				
				<form id="formPurchase" name="formPurchase">
 						Purchase code:
 						<input id="purchaseCode" name="purchaseCode" type="text" class="form-control form-control-sm">
 						<br> 
 						
 						Purchase Type:
 						<input id="purchaseType" name="purchaseType" type="text" class="form-control form-control-sm">
 						<br> 
 						
 						Total Price:
 						<input id="TotalPrice" name="TotalPrice" type="text" class="form-control form-control-sm">
 						<br> 
 						
 						Purchase Description:
 						<input id="purchaseDesc" name="purchaseDesc" type="text" class="form-control form-control-sm">
 						<br>
 						
 						Purchase Date:
 						<input id="purchaseDate" name="purchaseDate" type="text" class="form-control form-control-sm">
 						<br> 
 						
 						
 						<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
 						<input type="hidden" id="hidPurchaseIDSave" name="hidPurchaseIDSave" value="">
				</form>

				<br>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

				<br>
				<div id="divPurchaseGrid">
					<%
							Purchase purchaseObjRead = new Purchase();
							out.print(purchaseObjRead.readPurchase());
					%>
				</div>

			</div>
		</div>
	</div>
</body>
</html>