<%-- 
    Document   : AdminLoginSuccess
    Created on : Jun 24, 2020, 4:07:59 PM
    Author     : fahd_
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
<%
//response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
//response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
//response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
//response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
response.setHeader("Cache-Control", "no-cache, no-store,must-revalidate");    
    //this is the msg saved in the userlog in servlet welcome user name
String AUserName = null;
String sessionID = null;
Cookie[] cookies = request.getCookies();
if(cookies != null){
for(Cookie cookie : cookies){
	if(cookie.getName().equals("AUserName")) AUserName = "Welcome"+cookie.getValue();
        
	if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
}
}




if(session.getAttribute("userName")==null){
    response.sendRedirect("ErrorPage.html");
    }


%>
        <h1>Hello World!</h1>
        <h3>Login Success</h3>
<h4><%=AUserName%></h4>
<h4>Session ID = <%=sessionID %></h4>
<br><br>
<form action="../LogoutServlet" method="post">
<input type="submit" value="Logout" >
</form>

<a href="../adminChangePass.html"> Change PassWord</a>

<a href="../AddCourse.html"> Add a course</a>

<a href="../viewCourses.jsp"> View Courses</a>

<a href="../AddCourseData.jsp"> Add Course Data</a>
    </body>
</html>
