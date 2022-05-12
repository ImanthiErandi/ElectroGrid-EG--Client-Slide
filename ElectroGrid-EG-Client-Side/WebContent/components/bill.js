$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
	 {
	 	$("#alertSuccess").hide();
	 }
	 	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	 $("#alertSuccess").text("");
	 $("#alertSuccess").hide();
	 $("#alertError").text("");
	 $("#alertError").hide();

// Form validation-------------------
var status = validateBillForm();
	if (status != true)
	 {
		 $("#alertError").text(status);
		 $("#alertError").show();
		 return;
     }
 
// If valid------------------------
var type = ($("#hidBillIDSave").val() == "") ? "POST" : "PUT";
 $.ajax(
 {
 url : "BillAPI",
 type : type,
 data : $("#formBill").serialize(),
 dataType : "text",
 complete : function(response, status)
 {
 onBillSaveComplete(response.responseText, status);
 }
 });
});

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
$("#hidBillIDSave").val($(this).data("billid"));
 $("#CustomerAccNo").val($(this).closest("tr").find('td:eq(0)').text());
 $("#CustomerName").val($(this).closest("tr").find('td:eq(1)').text());
 $("#Unit").val($(this).closest("tr").find('td:eq(2)').text());
 $("#Total").val($(this).closest("tr").find('td:eq(3)').text());
$("#Date").val($(this).closest("tr").find('td:eq(4)').text());
});

//DELETE==========================================================
$(document).on("click", ".btnRemove", function(event)
{
 $.ajax(
 {
 url : "BillAPI",
 type : "DELETE",
 data : "billNo=" + $(this).data("billid"),
 dataType : "text",
 complete : function(response, status)
 {
 onBillDeleteComplete(response.responseText, status);
 }
 });
});

// CLIENT-MODEL================================================================
function validateBillForm()
{
	
// Customer AccNo
if ($("#CustomerAccNo").val().trim() == "")
 {
 return "Insert CustomerAccNo.";
 }

// Customer Name
if ($("#CustomerName").val().trim() == "")
 {
 return "Insert Customer Name.";
 } 


// Unit
if ($("#Unit").val().trim() == "")
 {
 return "Insert Unit.";
 } 

// Total-------------------------------
if ($("#Total").val().trim() == "")
 {
 return "Insert Total.";
 }

// is numerical value
var tmpPrice = $("#Total").val().trim();
if (!$.isNumeric(tmpPrice))
 {
 return "Insert a numerical value for Total.";
 }

// convert to decimal price
 $("#Total").val(parseFloat(tmpPrice).toFixed(2));

return true;
}


function onBillSaveComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully saved.");
 $("#alertSuccess").show();
 $("#divBillGrid").html(resultSet.data);
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
$("#hidBillIDSave").val("");
 $("#formBill")[0].reset();
}

function onBillDeleteComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully deleted.");
 $("#alertSuccess").show();
 $("#divBillGrid").html(resultSet.data);
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
