


<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>
                <link rel="stylesheet" href="../loginS.css">
        <title>Client portal page</title>
		<style>
		.custombutton{
		background-color:red;
		
		}
		</style>
                
                
                
<script>
function DeleteTask(clicked_id) {
 
 var id = clicked_id;

 window.location.href = "../deleteTask?taskID="+id;


}

function editTask(clicked_id){
    var id= clicked_id;
    
    
    var Etitle =prompt("Change the title of the task");
    var desc =prompt("Change the description of the task");
    

    
    window.location.href = "../editTask?taskID="+id+"&title="+Etitle+"&desc="+desc;
    
}




</script>
    </head>
    <body>


        
<%


response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    
    
if(session.getAttribute("userName")== null){
    response.sendRedirect("ErrorPage.html");
    }
    
    
    
String message=((String)session.getAttribute("firstName"));
//String sessionID = null;

Cookie[] cookies = request.getCookies();
if(cookies != null){
for(Cookie cookie : cookies){
	if(cookie.getName().equals("firstName"))
        message = "Welcome"+cookie.getValue();
        
//	if(cookie.getName().equals("JSESSIONID")) 
 //       sessionID = cookie.getValue();
}
}
String userId1=((String)session.getAttribute("userName"));


%>
<%--        
// jsp allowes you to use java in html 
// like the msg field you have created in java
// we can use it by using and calling the java code by surronding it with the tag and mod sign 
// here we can acess the DB to get the courses this will be the maain page. 

--%>

<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">Elearning</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="#">Home</a></li>
      <li><a href="../changePassword.html"> Change PassWord</a></li>
      <li><a href="../backUpPage.jsp"> View Registered Courses </a></li>
      <li><a href="../RegisterCourse.jsp"> Register For a Course</a></li>
    </ul>
    <div class="nav navbar-nav navbar-right">
	<form action="../LogoutServlet" method="post">
    <button class="btn btn-danger navbar-btn" type="submit" value="Logout">LogOut</button> 
    </form>
                       <!-- replace danger by custonbutton to have your own customs done to it -->  
    </div>
  </div>
</nav>
<h3>Login Success</h3>
<h4><%=message%></h4> <%--this is how you get the value of the variable --%>


    <div class="centered-cstm">
        <div class="card" >
            <div class="card-body" style="width:775px">
		<div class="table-responsive">
		<table class="table">

        <tr>
                <th> <span class="glyphicon glyphicon-user"> </span>   Task Title</th>
                <th> <span class="glyphicon glyphicon-pencil"> </span>    Task description</th>
                <th> <span class="glyphicon glyphicon-time"> </span> creation date</th>
               <th> <span class="glyphicon glyphicon-hourglass"> </span> days remaining</th>
               <th>Delete A Task</th>
               <th>Edit The Task</th>
               
            </tr>
                  <%   
            
              
           
            String userId=((String)session.getAttribute("userName"));
            
            Class.forName("com.mysql.cj.jdbc.Driver");  
           // out.println("Driver connected");  
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/javascript1","root","iolnyno");
            
           PreparedStatement ps=con.prepareStatement("Select id ,title,task_desc,creation,finish from todolist where userID=?");
           ps.setString(1, userId);
           ResultSet rs = ps.executeQuery(); 
 
           while(rs.next()){
           
        %>

		
		<tr>
		
                <td> <span class="glyphicon glyphicon-user"> </span>  <%=rs.getString("title")%> </td>

	    
		
		<td><span class="glyphicon glyphicon-pencil"> </span> <%=rs.getString("task_desc")%></td>
	   
		
		<td><span class="glyphicon glyphicon-time"> </span> <%=rs.getDate("creation")%></td>

		
		<td><span class="glyphicon glyphicon-alert"> </span> <%=rs.getDate("finish")%></td>
                
                <td><button type="button"  id ="<%=rs.getInt("id")%>" onclick="DeleteTask(this.id)"><span class="glyphicon glyphicon-trash"> </span></button>  </td>
                
                <td><button type="button"  id ="<%=rs.getInt("id")%>" onclick="editTask(this.id)"><span class="glyphicon glyphicon-pencil"> </span></button>  </td>
                
		
	    </tr>
		            <% }
        // rs.close();
        //stmt.close();
        //con.close();

%>
	    
<tr>
<td></td> 
<td></td>   
<td></td>   
<td><a class="button1" href="../Addtask.html" data-target="#theModal" data-toggle="modal">Add Task</a>  </td>
</tr>
                </table>
    </div> 
        </div>
        </div>
    </div>

<div class="modal fade text-center" id="theModal">
  <div class="modal-dialog">
      <form method = "post" action="../AddTask" >

    <div class="modal-content">
    </div>
      </form>
  </div>
</div>



    </body>
</html>
