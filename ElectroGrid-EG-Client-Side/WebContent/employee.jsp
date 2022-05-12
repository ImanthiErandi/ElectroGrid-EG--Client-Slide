<%@page import="model.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<meta charset="ISO-8859-1">
<title>Employee Management</title>

<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<link rel="stylesheet" type="text/css" href="css\footer.css"> 
<script src="components/jquery.min.js"></script>
<script src="components/employee.js"></script>



 <nav class="navbar navbar-expand-md navbar-dark" style="background-color:#2BBBAD">
                   

                    <ul class="navbar-nav">
                        <li><a href="Index.jsp" class="nav-link">ElectroGrid Online Platform</a></li>
                    </ul>
                 </nav>
               


</head>
<body>




<br>
<br>


<div class="container"> 
		<div class="row">  
		 <br>
            <div class="container col-md-5">
                <div class="card">
                    <div class="card-body">
                       

                        <caption>
                            <h2>
                                Employee Management
                            </h2>
                        </caption>
		
			
				<form id="formEmployee" name="formEmployee" method="post" action="Employee.jsp">  
					Employee Name:  
					<input id="empName" name="empName" type="text" class="form-control form-control-sm">  
					
					<br> 
					NIC:  
					<input id="empNIC" name="empNIC" type="text" class="form-control form-control-sm">  
					
					<br>
					 Birth Date:  
					 <input id="empBdate" name="empBdate" type="text" class="form-control form-control-sm">  
					 
					 <br> 
					 Department:  
					 <input id="empDep" name="empDep" type="text" class="form-control form-control-sm">  
					 
					 <br> 
					 Address:  
					 <input id="empAddress" name="empAddress" type="text" class="form-control form-control-sm">  
					 
					  <br> 
					 Phone Number:  
					 <input id="empPhone" name="empPhone" type="text" class="form-control form-control-sm">  
					 
					 
					 
					 <br>  
					 <input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
                     <input type="hidden" id="hidEmpIDSave" name="hidEmpIDSave" value="">
					 
					 
				</form> 
				  </div>
                </div>
            </div>
    
				<div id="alertSuccess" class="alert alert-success"></div>  
				<div id="alertError" class="alert alert-danger"></div> 
				
				<br>
					
				
            <div class="row">
               
             
                <div class="container">
                 <h3 class="text-center">Employee Details</h3>
                    <hr>
                    <div class="container text-left">

                        <a href="Index.jsp" class="btn btn-success"style="background-color: #2BBBAD">Navigate To Home page</a>
                         <a href="Login.jsp" class="btn btn-success"style="background-color: #2BBBAD">Logout</a>
                    </div>
                    <br>
                
                   <div id="divEmpGrid">
 <%
 Employee empObj = new Employee();
   out.print(empObj.readEmployee());
 %>
					
				</div> 
                   
                </div>
            </div>
				  
 			</div>
 		 
 		</div>    
 		
<br>
	 

</body>

 <!-- Site footer -->
    <footer class="site-footer">
      <div class="container">
        <div class="row">
          <div class="col-sm-12 col-md-6">
            <h6>About</h6>
            <p class="text-justify">Wickramarathna I.E.<i> </i> This project is based on a company named ElectroGrid (EG)  is the company who maintains the power grid of the country. I used java , tomcat , mysql Workbench and JAX-RS Restful webservice as our tools to create our platform..</p>
          </div>

          <div class="col-xs-6 col-md-3">
            <h6>Categories</h6>
            <ul class="footer-links">
              <li><a href="Login.jsp">Bill Management</a></li>
              <li><a href="Login.jsp">Unit Management</a></li>
              <li><a href="Login.jsp">Employee Management</a></li>
             
            </ul>
          </div>

          <div class="col-xs-6 col-md-3">
            <h6>Quick Links</h6>
            <ul class="footer-links">
              <li><a href="Login.jsp">HomePage</a></li>
              <li><a href="">Contact Us</a></li>
              <li><a href="">Contribute</a></li>
              <li><a href="">Privacy Policy</a></li>
              
            </ul>
          </div>
        </div>
        <hr>
      </div>
      <div class="container">
        <div class="row">
          <div class="col-md-8 col-sm-6 col-xs-12">
            <p class="copyright-text">Copyright &copy; 2022 All Rights Reserved by 
         <a href="#">Wickramarathna I.E.</a>.
            </p>
          </div>

          <div class="col-md-4 col-sm-6 col-xs-12">
            <ul class="social-icons">
              <li><a class="facebook" href="#"><i class="fa fa-facebook"></i></a></li>
              <li><a class="twitter" href="#"><i class="fa fa-twitter"></i></a></li>
              <li><a class="dribbble" href="#"><i class="fa fa-dribbble"></i></a></li>
              <li><a class="linkedin" href="#"><i class="fa fa-linkedin"></i></a></li>   
            </ul>
          </div>
        </div>
      </div>
</footer>
</html>