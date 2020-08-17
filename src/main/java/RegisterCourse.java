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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fahd_
 */
public class RegisterCourse extends HttpServlet {

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
        
        ServletContext scontext=getServletContext(); //gives you the path of the servlet where it is and the context variables. what the server is currenlty holding  
        String SQLDriver=scontext.getInitParameter("MySQLDriver");
        String Host =scontext.getInitParameter("Host");
        String root =scontext.getInitParameter("root");
        String Pass =scontext.getInitParameter("Pass");
        
       
        
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterCourse</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterCourse at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            
            
            String CourseValue = request.getParameter("Courses");
            
            
            
            
            Class.forName(SQLDriver);  
            
            //out.println("Driver connected");  
            
            Connection con=DriverManager.getConnection(Host,root,Pass);
boolean status2;   
String userID = null;
//String sessionID = null;
Cookie[] cookies = request.getCookies();
if(cookies != null){
for(Cookie cookie : cookies){
	if(cookie.getName().equals("UserId")) userID= cookie.getValue();
        
	//if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
}
}
            
         PreparedStatement ps2=con.prepareStatement("select * from course_master where course_name=?");
                  ps2.setString(1,CourseValue);
                  ResultSet rs2 =ps2.executeQuery();  
                 int course_id = 0;
                  while(rs2.next()){
                      
                 course_id=rs2.getInt("course_id");
                     
                 }   
            out.println("Course Id" + course_id);
            
           int userid= Integer.parseInt(userID);
                    

          PreparedStatement ps1=con.prepareStatement("insert into student_course values(?,?,?)");
                            ps1.setInt(1,userid );
                            ps1.setInt(2,1);
                            ps1.setInt(3,course_id);
                            int row=ps1.executeUpdate();
                  out.println("number of rows inserted" +row);
                  //request.getRequestDispatcher("admin/AdminLoginSuccess.jsp").include(request, response);  this does not change url
                  if(row>0){
                  response.sendRedirect("admin/LoginSuccess.jsp");
                  out.println("<font color=green>Couse has been added sucessfully.</font>");
                  }
                  else{
                  
                  request.getRequestDispatcher("admin/LoginSuccess.jsp").include(request, response);  
                  out.println("<font color=red>Error registering course.</font>");
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
            Logger.getLogger(RegisterCourse.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterCourse.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RegisterCourse.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterCourse.class.getName()).log(Level.SEVERE, null, ex);
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
