<%-- 
    Document   : viewCourses
    Created on : Jun 26, 2020, 3:27:18 PM
    Author     : fahd_

this is whe a user is looking for his courses 
how will you show it? 
first need to connect to the DB 
then we need to get the data from course master  and display all courses along with the number of session in an html table
--%>


<%@page import="java.sql.Statement"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        
        <link rel="stylesheet" href="backuppage.css">
    </head>
    <body>
        <script>
function getcourseid(clicked_id) {
 //var x = document.getElementsByClassName("coursebutton"); // getting 3 button objects
 var id = clicked_id;
 //session.setAttribute("clicked_id", id);

 // we were tyring to set it as an atrribute in session 
 // but now we are passing it as a request 
 //logic is when ever we do a get request to a page it includes the variable(passed parameters) names in the url
 window.location.href = "viewSession.jsp?id1="+id;
  //var text = ""; // display the content of the button (name) 
  //var i;
//for (i = 0; i < x.length; i++) {
  //text += x[i].innerHTML + "<br>";
//}
 
 
}
</script>

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



        <h1>Available courses are shown below</h1>
        <div class="table-responsive">
        <table class = "table table-hover table-striped table-bordered">
            <thead>
                <tr class="bg-primary">
                <th>User_id</th>
                <th>course_id</th>
                <th>No_of_sessions</th>
               <th>Course</th>
                </tr>
            </thead>
        <%   
            
String AUserName = null;
String UserName=   null;
//String sessionID = null;
boolean status;
boolean status2;
Cookie[] cookies = request.getCookies();
if(cookies != null){
for(Cookie cookie : cookies){
	if(cookie.getName().equals("AUserName")) AUserName = cookie.getValue();
        if(cookie.getName().equals("UserId")) UserName=cookie.getValue();
	//if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
        
}
}
            Class.forName("com.mysql.cj.jdbc.Driver");  
           // out.println("Driver connected");  
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/javascript1","root","iolnyno");
            //Statement stmt = con.createStatement(); 
            
           //ResultSet rs = stmt.executeQuery("SELECT * from course_master"); // no where or parameter clause so no need of prepared statement. since no input thus no need of prepared statement
           PreparedStatement ps=con.prepareStatement("Select userName, Current_session,course_id, (CASE WHEN course_id = 1 THEN 'C' WHEN course_id = 2 THEN 'Java' WHEN course_id = 3 THEN 'C++' END) as 'course' from student_course where UserName=?");
           ps.setString(1, UserName);
           ResultSet rs = ps.executeQuery(); 
           int i=0;
           int idCounter =1;
           while(rs.next()){
           
        %>
        <tbody>
        <tr>
                <td><%=rs.getInt("userName")%></td>
                <td><%=rs.getInt("Current_session")%></td>
                <td><%=rs.getInt("course_id")%></td>
                <td><center> <button type="button" class="btn btn-primary"  id ="<%=idCounter%>" onclick="getcourseid(this.id)"><%=rs.getString("course")%></button> </center> </td>  
            </tr>
            <% idCounter++; }
        // rs.close();
        //stmt.close();
        //con.close();

%>
        </tbody>
        </table>
    </div>
<%
    //this is the msg saved in the userlog in servlet welcome user name


// i want to go into the db and check if the user is an admin or not
     PreparedStatement ps2=con.prepareStatement("Select * from user_master where UserName=? and user_type='user' ");
            ps2.setString(1, UserName);
            ResultSet rs3 =ps2.executeQuery();
            status2 = rs3.next(); // will itterate trough DB
            if(status2){ 
             //out.println("You are a user");
           
    

%>
   



<div id="myLink">
 <a href="admin/LoginSuccess.jsp"> Go back to Portal</a>
 <% }  %>

</div>



    </body>
</html>
