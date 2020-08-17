<%-- 
    Document   : viewCourses
    Created on : Jun 26, 2020, 3:27:18 PM
    Author     : fahd_

this is whe a user is looking for his courses 
how will you show it? 
first need to connect to the DB 
then we need to get the data from course master  and display all courses along with the number of session in an html table
--%>


<%@page import= "java.sql.Statement"%>
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
        <link rel="stylesheet" href="viewCourses.css">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Available courses are shown below</h1>
        <table>
            <tr>
                <th>course_id</th>
                <th>course_name</th>
                <th>No_of_sessions</th>
            </tr>
        <%   
            String AUserName = null;
String UserName=   null;
//String sessionID = null;
int flag=0;
boolean status;

Cookie[] cookies = request.getCookies();
if(cookies != null){
for(Cookie cookie : cookies){
	if(cookie.getName().equals("AUserName")) AUserName = cookie.getValue();
        
        //if(cookie.getName().equals("UserId")) UserName=cookie.getValue();
	//if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
        
}
}

            Class.forName("com.mysql.cj.jdbc.Driver");  
            out.println("Driver connected");  
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/javascript1","root","iolnyno");
            Statement stmt = con.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * from student_course"); // no where or parameter clause so no need of prepared statement. since no input thus no need of prepared statement
            while(rs.next()){
            
        %>
            <tr>
                <td><%=rs.getInt("userName")%></td>
                <td><%=rs.getString("Current_session")%></td>
                <td><%=rs.getString("course_id")%></td>
            </tr>
            <% }
        // rs.close();
        stmt.close();
        //con.close();

%>
  
        </table>
<%
    //this is the msg saved in the userlog in servlet welcome user name


            PreparedStatement ps=con.prepareStatement("Select * from user_master where UserName=? and user_type='admin'");
            ps.setString(1, AUserName);
            ResultSet rs2 =ps.executeQuery();
            status = rs2.next(); // will itterate trough DB
            if(status){
    out.println("You are an admin");
%>

 <a href="admin/AdminLoginSuccess.jsp"> Go back to Admin Portal</a> 
 
 <%  
     }
       
%>





    </body>
</html>
