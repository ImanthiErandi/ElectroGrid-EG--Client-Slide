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
var status = validateEmpForm();
	if (status != true)
	 {
		 $("#alertError").text(status);
		 $("#alertError").show();
		 return;
     }
 
// If valid------------------------
var type = ($("#hidEmpIDSave").val() == "") ? "POST" : "PUT";
 $.ajax(
 {
 url : "EmployeeAPI",
 type : type,
 data : $("#formEmployee").serialize(),
 dataType : "text",
 complete : function(response, status)
 {
 onEmployeeSaveComplete(response.responseText, status);
 }
 });
});

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
$("#hidEmpIDSave").val($(this).data("empid"));
 $("#empName").val($(this).closest("tr").find('td:eq(0)').text());
 $("#empNIC").val($(this).closest("tr").find('td:eq(1)').text());
 $("#empBdate").val($(this).closest("tr").find('td:eq(2)').text());
 $("#empDep").val($(this).closest("tr").find('td:eq(3)').text());
 $("#empAddress").val($(this).closest("tr").find('td:eq(4)').text());
 $("#empPhone").val($(this).closest("tr").find('td:eq(5)').text());
});

//DELETE==========================================================
$(document).on("click", ".btnRemove", function(event)
{
 $.ajax(
 {
 url : "EmployeeAPI",
 type : "DELETE",
 data : "empID=" + $(this).data("empid"),
 dataType : "text",
 complete : function(response, status)
 {
 onEmployeeDeleteComplete(response.responseText, status);
 }
 });
});

// CLIENT-MODEL================================================================
function validateEmpForm()
{
	
//empName
if ($("#empName").val().trim() == "")
 {
 return "Insert empName.";
 }

// empNIC
if ($("#empNIC").val().trim() == "")
 {
 return "Insert empNIC.";
 } 

// empBdate-------------------------------
if ($("#empBdate").val().trim() == "")
 {
 return "Insert empBdate.";
 }

// empDep-------------------------------
if ($("#empDep").val().trim() == "")
 {
 return "Insert empDep.";
 }
 
 // empAddress-------------------------------
if ($("#empAddress").val().trim() == "")
 {
 return "Insert empAddress.";
 }
 
 // empPhone-------------------------------
if ($("#empPhone").val().trim() == "")
 {
 return "Insert empPhone.";
 }
 
return true;
}

function onEmployeeSaveComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully saved.");
 $("#alertSuccess").show();
 $("#divEmpGrid").html(resultSet.data);
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
$("#hidEmpIDSave").val("");
 $("#formEmployee")[0].reset();
}

function onEmployeeDeleteComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully deleted.");
 $("#alertSuccess").show();
 $("#divEmpGrid").html(resultSet.data);
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
