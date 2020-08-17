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
import javax.servlet.http.HttpSession;

/**
 *
 * @author fahd_
 */
public class LoginUser extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        boolean status;
            String userName= request.getParameter("LogID");
            String passWord= request.getParameter("logpW");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
                
            //Class.forName("com.mysql.cj.jdbc.Driver"); 
            LoginDoa doa = new LoginDoa();
            //Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/javascript1","root","iolnyno");
            
           // PreparedStatement ps=con.prepareStatement("Select * from user_master where user_type='user' and UserName=? and Password=?");
            //ps.setString(1, userName);
            //ps.setString(2, passWord);
           // ResultSet rs =ps.executeQuery(); // this will have all rows which will match the query
            //status = rs.next(); // will itterate trough DB
            
            
            if(doa.check(userName, passWord)){
            Cookie userId = new Cookie("UserId", userName); // we are adding user ID into the coookie
                                                          // this cookie will be available to the session untill the user logs out
            
            response.addCookie(userId); // this is adding the user id cookie to the response 
            
           HttpSession session = request.getSession();
           session.setAttribute("userName",userName);
            response.sendRedirect("admin/LoginSuccess.jsp");
            //out.print(session.getAttribute("userName"));
           
            }
            
            else {
            response.sendRedirect("ULogIn.jsp");
            
            //if i use the code below i can get a output indicating that that something went wrong put does not redirect to jsp page 
            //request.getRequestDispatcher("UserLogin.html").include(request, response); 
            out.println("<font color=red>Either username or password is wrong.</font>");
            }
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginUser</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginUser at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
            Logger.getLogger(LoginUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginUser.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(LoginUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginUser.class.getName()).log(Level.SEVERE, null, ex);
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
