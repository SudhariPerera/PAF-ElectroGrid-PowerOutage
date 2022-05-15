<%@ page import="model.Outage" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    
       
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Electric Grid</title>
	<link rel="stylesheet" href="css/bootstrap.css">
	<script src="js/jquery-3.6.0.min.js" type="text/javascript"></script>
	<script src="js/validation.js" type="text/javascript"></script>
	<script src="js/outage.js" type="text/javascript"></script>
</head>
<body>


<div class="container">
	 <div class="row">
		 <div class="col">
		 	
		 	
		 	<h1><center>ELECTRIC GRID SYSTEM</center></h1>
		 	<br>
		 	
		 	<h2><b><u>Power Outage</u></b></h2>
		 	<br>
			<form id="formOutage" name="formOutage">
			
				 Power Outage Code: 
				<input id="ocode" name="ocode" type="text" class="form-control form-control-sm">
				<br>
				
				Outage Time: 
				<input id="time" name="time" type="text" class="form-control form-control-sm">
				<br>
				
				Outage Date: 
				<input id="date" name="date" type="text" class="form-control form-control-sm">
				<br> 
				
				Assigned Employee ID: 
				<input id="empid" name="empid" type="text" class="form-control form-control-sm">
				<br> 
				
				Assigned Area Code: 
				<input id="areacode" name="areacode" type="text" class="form-control form-control-sm">
				<br>
				
				<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
				
				<input type="hidden" id="hidOutageIDSave" name="hidOutageIDSave" value="">

			</form>
			
			<br>
			
			<%--Insert Status Message print --%>
			<div id="alertSuccess" class="alert alert-success"></div>
			<div id="alertError" class="alert alert-danger"></div>
		 	
			
			<br><br>
			
			<%-- Data fetch to this Table --%>
			<div id="divOutageGrid">
				<%
					 Outage outageObj = new Outage(); 
					 out.print(outageObj.readOutage()); 
				%>
			</div>
			
		 	
		 </div>
	 </div>
</div>


</body>
</html>