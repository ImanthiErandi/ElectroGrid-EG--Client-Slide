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
var status = validateUnitForm();
	if (status != true)
	 {
		 $("#alertError").text(status);
		 $("#alertError").show();
		 return;
     }
 
// If valid------------------------
var type = ($("#hidUnitIDSave").val() == "") ? "POST" : "PUT";
 $.ajax(
 {
 url : "UnitsAPI",
 type : type,
 data : $("#formUnit").serialize(),
 dataType : "text",
 complete : function(response, status)
 {
 onUnitSaveComplete(response.responseText, status);
 }
 });
});

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
$("#hidUnitIDSave").val($(this).data("unitid"));
 $("#UnitCode").val($(this).closest("tr").find('td:eq(0)').text());
 $("#UnitAmount").val($(this).closest("tr").find('td:eq(1)').text());
 $("#UnitPrice").val($(this).closest("tr").find('td:eq(2)').text());
});

//DELETE==========================================================
$(document).on("click", ".btnRemove", function(event)
{
 $.ajax(
 {
 url : "UnitsAPI",
 type : "DELETE",
 data : "idUnit=" + $(this).data("unitid"),
 dataType : "text",
 complete : function(response, status)
 {
 onUnitDeleteComplete(response.responseText, status);
 }
 });
});

// CLIENT-MODEL================================================================
function validateUnitForm()
{
	
// CODE
if ($("#UnitCode").val().trim() == "")
 {
 return "Insert Unit Code.";
 }

// Unit Amount
if ($("#UnitAmount").val().trim() == "")
 {
 return "Insert Unit Amount.";
 } 

// PRICE-------------------------------
if ($("#UnitPrice").val().trim() == "")
 {
 return "Insert Unit Price.";
 }

// is numerical value
var tmpPrice = $("#UnitPrice").val().trim();
if (!$.isNumeric(tmpPrice))
 {
 return "Insert a numerical value for Unit Price.";
 }

// convert to decimal price
 $("#UnitPrice").val(parseFloat(tmpPrice).toFixed(2));

return true;
}
function onUnitSaveComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully saved.");
 $("#alertSuccess").show();
 $("#divUnitsGrid").html(resultSet.data);
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
$("#hidUnitIDSave").val("");
 $("#formUnit")[0].reset();
}

function onUnitDeleteComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully deleted.");
 $("#alertSuccess").show();
 $("#divUnitsGrid").html(resultSet.data);
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
