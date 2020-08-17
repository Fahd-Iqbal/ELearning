<%-- 
    Document   : RegisterCourse
    Created on : Jun 29, 2020, 3:29:20 PM
    Author     : fahd_

Why made a jsp 
because a person has to know the available courses which are in the DB 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register course Page</title>
        
<%@page import= "java.sql.Statement"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
    </head>
    <body>
        <h1>Register for course</h1>
    <form action="RegisterCourse">
        <label for="courses">Choose A Course:</label>
        <select name="Courses" id="Courses">
        
    
               <%   
            

            Class.forName("com.mysql.cj.jdbc.Driver");  
            out.println("Driver connected");  
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/javascript1","root","iolnyno");
            Statement stmt = con.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * from course_master"); // no where or parameter clause so no need of prepared statement. since no input thus no need of prepared statement
            while(rs.next()){
            
        %>
        
        <option><%= rs.getString("course_name")%> </option>
        
        <%}

            %>
    </select>
  <br><br>
  <input type="submit" value="Submit">
</form>
    
    </body>
    
    
</html>
