



<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
		<link rel="stylesheet" href="newcss.css">
    </head>
    <body>
	<center>
        <h1>Login Page</h1>
		</center>
		<center>
		  <div class="card" >
    <div class="card-body" style="width:400px">
		
		<form method = "post" action="LoginUser">
		<div class="form-group">
		
		<label for="UserName"> UserId:</label>
        <input type="text" class="form-control" id="LoginID" name="LogID" placeholder ="Enter your UserId">       
	   </div>
	   
		<div class="form-group">
		<label for="password"> PassWord:</label> 
	    <input type="password" class="form-control" id="LoginpassWord" name="logpW" placeholder ="Enter your PassWord"> 
	    <small class="form-text text-muted">Your password is protected.</small>
         </div>
		<button type="submit" class="btn btn-primary" value="Submit"> Submit </button>
		</form>
	  	

    </div>
  </div>
  </center>
 <center>    <a href="registration.html"> Register</a>  &nbsp
      <a href="adminLogin.html"> Admin Login</a>
	  
	  </center>
		
		
		
		

	    
     
        
       
          
        
        
        
        
        
        
    </body>
</html>
