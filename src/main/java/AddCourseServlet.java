/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fahd_
 */
public class AddCourseServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddCourseServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddCourseServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            
            String course_name = request.getParameter("cname");
            String numberOfSessions=request.getParameter("nSession"); //everything is a string then you can convert it into a int 
            boolean status;
            boolean status2;
            Class.forName("com.mysql.cj.jdbc.Driver");  
            
           // out.println("Driver connected");  
            
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/javascript1","root","iolnyno");
            
            
//this is the msg saved in the userlog in servlet welcome user name
String AUserName = null;
Cookie[] cookies = request.getCookies();
if(cookies != null){
for(Cookie cookie : cookies){
	if(cookie.getName().equals("AUserName")) AUserName = cookie.getValue();
        
}
}


            
        
PreparedStatement ps=con.prepareStatement("Select * from user_master where UserName=? and user_type='admin'");
                ps.setString(1, AUserName);
                // this will return a result set 
               ResultSet rs =ps.executeQuery();
               // this wil give a list of rows that will match the query
               status = rs.next(); // will itterate trough DB
               // if status equals true then it means it is not empty 
               if(status==true){
               out.println("you are authorised to add a course");
               
          
PreparedStatement ps2=con.prepareStatement("select * from course_master where course_name=?");
                  ps2.setString(1,course_name);
                  ResultSet rs2 =ps2.executeQuery();  
                  status2 = rs2.next();
                  //out.println(status2);
                  
                  if(status2==false){
                      
                  PreparedStatement ps1=con.prepareStatement("insert into course_master (course_name, No_of_sessions) values(?,?)");
                 
                  ps1.setString(1, course_name);
                  ps1.setString(2,numberOfSessions);
                  
                  //out.println("query is working");
                 // ps1.execute();
                 int row=ps1.executeUpdate();
                  out.println("number of rows inserted" +row);
                
                  Cookie userCourse = new Cookie("course_name", course_name);
                  response.addCookie(userCourse);
                  
                  //request.getRequestDispatcher("admin/AdminLoginSuccess.jsp").include(request, response);  this does not change url
                  response.sendRedirect("AddCourseData.jsp");
                  out.println("<font color=green>Couse has been added sucessfully.</font>");
                  
                  
                  
                  }
                  else{
                  
                  request.getRequestDispatcher("AddCourse.html").include(request, response);  
                  out.println("<font color=red>Course Already exists.</font>");
                  }
                  
               

               
               }
               else{
               // if person is not an admin 
               request.getRequestDispatcher("UserLogin.html").include(request, response); 
               out.println("<font color=red>Not authorized to add a course. \n logIn usinf Admin page.</font>");
               
               }

                        

            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddCourseServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddCourseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddCourseServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddCourseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
