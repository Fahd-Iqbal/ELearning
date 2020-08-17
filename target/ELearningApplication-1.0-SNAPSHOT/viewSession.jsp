<%-- 
    Document   : viewSession
    Created on : Jul 16, 2020, 5:37:17 PM
    Author     : fahd_
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

    </head>
    <body>
        <h1>Hello World!</h1>
         
               <table>
            <tr>
                <th>Session_id</th>
                <th>topic Name</th>
                <th>topic</th>
                <th> file_download</th>
            </tr>
        <% 
            
          // how can i call the class doa?
          //String id= (String)session.getAttribute("clicked_id");
          String id=request.getParameter("id1");
           Class.forName("com.mysql.cj.jdbc.Driver");  
           out.println("Driver connected");  
           Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/javascript1","root","iolnyno");

           PreparedStatement ps=con.prepareStatement("Select Session_id, topicName, topic  from session_master where course_id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            out.println("query has been executed");
            while(rs.next()){
           
        %>
                  
        <tr>   
                <td><%=rs.getInt("Session_id")%></td>
                <td><%=rs.getString("topicName")%></td>
                <td><%=rs.getString("topic")%></td>
                <td><a href="fileDownload?sessid=<%=rs.getInt("Session_id")%>">Download File</a></td> 
              <%--  // passing it as a parameter to the servlet --%>
                    
            </tr>
               </table>
            
        <% }%>
        
        <p> id is <%=id%> </p>
       

        
        
        
        
        
        
        
        
    </body>
</html>
