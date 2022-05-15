
//Let’s hide the alerts on page load. 
$(document).ready(function() { 
	if ($("#alertSuccess").text().trim() == "") { 
	 	$("#alertSuccess").hide(); 
	} 
	
 	$("#alertError").hide(); 
});


// CLIENT-MODEL================================================================

//Form validation function
function validateOutageForm() 
{ 
	// CODE
	if ($("#ocode").val().trim() == "") 
	{ 
	 	return "Insert Outage Code."; 
	} 
	// Time
	if ($("#time").val().trim() == "") 
	{ 
	 	return "Insert Outage Time."; 
	} 
	// Date-------------------------------
	if ($("#date").val().trim() == "") 
	{ 
	 	return "Insert Outage Date."; 
	} 
	// Employee ID
	if ($("#empid").val().trim() == "") 
	{ 
	 	return "Insert Employee ID."; 
	}
	
	// Area Code
	if ($("#areacode").val().trim() == "") 
	{ 
	 	return "Insert Area Code."; 
	}
	 
	return true; 
}


//
function onOutageSaveComplete(response, status) 
{ 
	 if (status == "success") 
	 { 
		 var resultSet = JSON.parse(response); 
		 
		 if (resultSet.status.trim() == "success") 
		 { 
			 $("#alertSuccess").text("Successfully saved."); 
			 $("#alertSuccess").show(); 
			 $("#divOutageGrid").html(resultSet.data); 
		 } else if (resultSet.status.trim() == "error") 
		 { 
			 $("#alertError").text(resultSet.data); 
			 $("#alertError").show(); 
		 } 
		 
	 } else if (status == "error") 
	 { 
		 $("#alertError").text("Error while saving."); 
		 $("#alertError").show(); 
	 } else
	 { 
		 $("#alertError").text("Unknown error while saving.."); 
		 $("#alertError").show(); 
	 }
	 
	 $("#hidOutageIDSave").val(""); 
	 $("#formOutage")[0].reset(); 
}





function onOutageDeleteComplete(response, status) 
{ 
	 if (status == "success") 
	 { 
		 var resultSet = JSON.parse(response); 
		 
		 if (resultSet.status.trim() == "success") 
		 { 
			 $("#alertSuccess").text("Successfully deleted."); 
			 $("#alertSuccess").show(); 
			 $("#divOutageGrid").html(resultSet.data); 
		 } else if (resultSet.status.trim() == "error") 
		 { 
			 $("#alertError").text(resultSet.data); 
			 $("#alertError").show(); 
		 }
		  
	 } else if (status == "error") 
	 { 
		 $("#alertError").text("Error while deleting."); 
		 $("#alertError").show(); 
	 } else
	 { 
		 $("#alertError").text("Unknown error while deleting.."); 
		 $("#alertError").show(); 
	 } 
}





//Implement Save button click handler
$(document).on("click", "#btnSave", function(event) { 
 	// Clear alerts---------------------
	 $("#alertSuccess").text(""); 
	 $("#alertSuccess").hide(); 
	 $("#alertError").text(""); 
	 $("#alertError").hide();
	 
	 // Form validation-------------------
	 var status = validateOutageForm(); 
	
	 // If not valid-------------------
	 if (status != true) 
	 { 
		 $("#alertError").text(status); 
		 $("#alertError").show(); 
		 
		 return; 
	 }
	 
	 // If valid------------------------
 	 var type = ($("#hidOutageIDSave").val() == "") ? "POST" : "PUT"; 
	 $.ajax( 
	 { 
		 url : "OutageAPI", 
		 type : type, 
		 data : $("#formOutage").serialize(), 
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 	onOutageSaveComplete(response.responseText, status); 
		 } 
	 }); 	 

});



// Implement the Update button handler
$(document).on("click", ".btnUpdate", function(event) {
	  $("#hidOutageIDSave").val($(this).data("itemid"));   
	  $("#ocode").val($(this).closest("tr").find('td:eq(0)').text()); 
	  $("#time").val($(this).closest("tr").find('td:eq(1)').text()); 
	  $("#date").val($(this).closest("tr").find('td:eq(2)').text()); 
	  $("#empid").val($(this).closest("tr").find('td:eq(3)').text());
	  $("#areacode").val($(this).closest("tr").find('td:eq(4)').text());
});



//For the Delete operation, we can get the item ID from the data attribute of the Remove button. 
$(document).on("click", ".btnRemove", function(event) 
{ 
	 $.ajax( 
	 { 
		 url : "OutageAPI", 
		 type : "DELETE", 
		 data : "oid=" + $(this).data("itemid"),
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 	onOutageDeleteComplete(response.responseText, status); 
		 } 
	 }); 
});

