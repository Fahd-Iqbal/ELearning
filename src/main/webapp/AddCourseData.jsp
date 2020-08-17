<%-- 
    Document   : AddCourseData
    Created on : Jul 1, 2020, 3:04:41 PM
    Author     : fahd_
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import= "java.sql.Statement"%>
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
    </head>
    <body>
        
                                                       
        <form method="post" action="AddCourseDataServlet" enctype="multipart/form-data">
              <%
    //this is the msg saved in the userlog in servlet welcome user name
/* String course_name = null;
//String sessionID = null;
Cookie[] cookies = request.getCookies();
if(cookies != null){
for(Cookie cookie : cookies){
	if(cookie.getName().equals("course_name")) course_name= cookie.getValue();
        
	
}
}
*/

Class.forName("com.mysql.cj.jdbc.Driver");  
            out.println("Driver connected");  
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/javascript1","root","iolnyno");
            Statement stmt = con.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * from course_master"); // no where or parameter clause so no need of prepared statement. since no input thus no need of prepared statement
            

%>
    <label for="courses">Choose A Course:</label>
        <select name="Courses" id="Courses">
           
            <%
            while(rs.next()){
            %>
             <option><%= rs.getString("course_name")%> </option>
            <%
            }
            
            

// get course id first 
// create interface for user to upload a file 
// once we have the id then redirect to servlet. 
// show pre existing sessions 




%>
        <label for="topicName"> Topic Name:</label> 
	<input type="text" id="TopicName" name="tname">
        
        <label for="topicDescription"> topic Description :</label> 
	<input type="text" id="descrName" name="dname">
        
       <%-- this is how to acept a file input in html --%>
        
        <label for="file">Select A File:</label>
        <input type="file" id="fdata" name="data">  
         
         <%-- accept="application/pdf" this is the parameter you have to change in order to accept a type of file
          if you write image/* you can take any images --%>
        
        <input type="submit" value="Submit">    
            
            
        </form>
      
        
        
        <h1>Hello World!</h1>
    </body>
</html>
