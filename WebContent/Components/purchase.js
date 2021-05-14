$(document).ready(function () {
    if ($("#alertSuccess").text().trim() == "") {
        $("#alertSuccess").hide();
    }
    $("#alertError").hide();
});



///CLIENT-MODEL================================================================
function validatePurchaseForm() {
	
    // CODE-------------------------------------
    if ($("#purchaseCode").val().trim() == "") {
        return "Insert Purchase Code.";
    }
    // TYPE-------------------------------------
    if ($("#purchaseType").val().trim() == "") {
        return "Insert Purchase Type.";
    }
    // TOTALPRICE-------------------------------
    if ($("#TotalPrice").val().trim() == "") {
        return "Insert Purchase Price.";
    }
    // is numerical value
    var tmpPrice = $("#TotalPrice").val().trim();
    if (!$.isNumeric(tmpPrice)) {
        return "Insert a numerical value for Purchase Price.";
    }
    // convert to decimal price
    $("#TotalPrice").val(parseFloat(tmpPrice).toFixed(2));
    
    // STATUS----------------------------------
    if ($("#purchaseDesc").val().trim() == "") {
        return "Insert Purchase Description.";
    }
    // DATE------------------------------------
    if ($("#purchaseDate").val().trim() == "") {
        return "Insert Purchase Date.";
    }
    
    return true;
}



///SAVE-BUTTON================================================================
$(document).on("click", "#btnSave", function (event) 
{
    // Clear alerts
    $("#alertSuccess").text("");
    $("#alertSuccess").hide();
    $("#alertError").text("");
    $("#alertError").hide();
    
    // Form validation
    var status = validatePurchaseForm();
    if (status != true) 
    {
        $("#alertError").text(status);
        $("#alertError").show();
        
        return;
    }
    
    // If valid
    var type = ($("#validatePurchaseForm").val() == "") ? "POST" : "PUT";
    $.ajax(
        {
            url: "PurchaseAPI",
            type: type,
            data: $("#formPurchase").serialize(),
            dataType: "text",
            complete: function (response, status) {
            	onPurchaseSaveComplete(response.responseText, status);
            }
        });
});


function onPurchaseSaveComplete(response, status) 
{
    	if (status == "success") 
    	{
    			var resultSet = JSON.parse(response);
    			
    			if (resultSet.status.trim() == "success") 
    			{
    					$("#alertSuccess").text("Successfully saved.");
	    				$("#alertSuccess").show();
	    				
	    				$("#divPurchaseGrid").html(resultSet.data);
    			} 
    			else if (resultSet.status.trim() == "error") 
    			{
    					$("#alertError").text(resultSet.data);
    					$("#alertError").show();
    			}
    	}
    	
    	else if (status == "error") 
    	{
    			$("#alertError").text("Error while saving.");
    			$("#alertError").show();
    	}	 
    	
    	else 
    	{
    			$("#alertError").text("Unknown error while saving..");
    			$("#alertError").show();
    	}
    	
    	$("#hidPurchaseIDSave").val("");
    	$("#formPurchase")[0].reset();
}


///UPDATE-BUTTON================================================================
$(document).on("click", ".btnUpdate", function (event) 
{
    	$("#hidPurchaseIDSave").val($(this).data("purchaseid"));
    	$("#purchaseCode").val($(this).closest("tr").find('td:eq(0)').text());
    	$("#purchaseType").val($(this).closest("tr").find('td:eq(1)').text());
    	$("#TotalPrice").val($(this).closest("tr").find('td:eq(2)').text());
    	$("#purchaseDesc").val($(this).closest("tr").find('td:eq(3)').text());
    	$("#purchaseDate").val($(this).closest("tr").find('td:eq(4)').text());
});


///DELETE-BUTTON================================================================
$(document).on("click", ".btnRemove", function (event) 
{
    $.ajax(
        {
            url: "PurchaseAPI",
            type: "DELETE",
            data: "purchaseID=" + $(this).data("purchaseid"),
            dataType: "text",
            complete: function (response, status) 
            {
            	onPurchaseDeleteComplete(response.responseText, status);
            }
        });
});


function onPurchaseDeleteComplete(response, status) 
{
    	if (status == "success") 
    	{
    			var resultSet = JSON.parse(response);
    			
    			if (resultSet.status.trim() == "success") 
    			{
    					$("#alertSuccess").text("Successfully deleted.");
    					$("#alertSuccess").show();
    					
    					$("#divPurchaseGrid").html(resultSet.data);
    			}
    			
    			else if (resultSet.status.trim() == "error")
    			{
    					$("#alertError").text(resultSet.data);
    					$("#alertError").show();
    			}
    	} 
    	
    	else if (status == "error") 
    	{
    			$("#alertError").text("Error while deleting.");
    			$("#alertError").show();
    	} 
    	
    	else 
    	{
    			$("#alertError").text("Unknown error while deleting..");
    			$("#alertError").show();
    	}
}