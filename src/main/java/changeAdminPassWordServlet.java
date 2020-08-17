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
import javax.servlet.RequestDispatcher;
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
public class changeAdminPassWordServlet extends HttpServlet {

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
            out.println("<title>Servlet changeAdminPassWordServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet changeAdminPassWordServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            
        ServletContext scontext=getServletContext(); //gives you the path of the servlet where it is and the context variables. what the server is currenlty holding  
        String SQLDriver=scontext.getInitParameter("MySQLDriver");
        String Host =scontext.getInitParameter("Host");
        String root =scontext.getInitParameter("root");
        String Pass =scontext.getInitParameter("Pass");
            
            String oldAdminPass = request.getParameter("Aoldp");
            String newAdminPass = request.getParameter("Anewp");
            
            Class.forName("com.mysql.cj.jdbc.Driver");  
            
            out.println("Driver connected");  
            
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/javascript1","root","iolnyno");
            
            String UserId = null;
            Cookie[] cookies = request.getCookies();
            if(cookies != null){
            for(Cookie cookie : cookies){
            if(cookie.getName().equals("AUserName")) UserId = cookie.getValue(); // know we will have the user ID here 
                }
        }
            
            PreparedStatement ps=con.prepareStatement("Select * from user_master where UserName=? and Password=? and user_type='admin' ");
            
            boolean status;
            ps.setString(1, UserId);
            ps.setString(2, oldAdminPass);
            ResultSet rs =ps.executeQuery();
            status = rs.next(); // will itterate trough DB
            
                if(status == true){
                out.println("The username and pasword exist");
                PreparedStatement ps1=con.prepareStatement("Update user_master set Password=? where UserName=?");
                ps1.setString(1, newAdminPass);
                ps1.setString(2,UserId);
                ps1.executeUpdate();
                //out.println("<font color=green> Password has been changed for admin.</font>");
                request.getRequestDispatcher("admin/AdminLoginSuccess.jsp").include(request, response);
                out.println("<font color=green> Password has been changed for admin.</font>");
               // RequestDispatcher rd = getServletContext().getRequestDispatcher("admin/AdminLoginSuccess.jsp");
             // here after the user the user has logged in we are checking if there is any current session going on for that user
             // if there is we then destroy it and create a new one. 
                //HttpSession oldSession = request.getSession(false); // to create a new one we send true to get old session we send false. 
                //out.println("hiii");
            }else{
             
             RequestDispatcher rd = getServletContext().getRequestDispatcher("adminLogin.html");
            
            out.println("<font color=red> there is no user with that name and password.</font>");
            rd.include(request, response);
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
            Logger.getLogger(changeAdminPassWordServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(changeAdminPassWordServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(changeAdminPassWordServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(changeAdminPassWordServlet.class.getName()).log(Level.SEVERE, null, ex);
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
