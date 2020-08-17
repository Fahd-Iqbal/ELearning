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
import javax.servlet.http.HttpSession;

/**
 *
 * @author fahd_
 */
public class UserLogin extends HttpServlet {

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
        ServletContext scontext=getServletContext(); //gives you the path of the servlet where it is and the context variables. what the server is currenlty holding  
        String SQLDriver=scontext.getInitParameter("MySQLDriver");
        String Host =scontext.getInitParameter("Host");
        String root =scontext.getInitParameter("root");
        String Pass =scontext.getInitParameter("Pass");
        
        String UserId = request.getParameter("LID"); // uses the name attribute from html page (UserLogin.html)
        String pass =request.getParameter("lpW");
        boolean status = false;
        // User name should exist and corresponding passWord 
        
        
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {

            Class.forName(SQLDriver);  
            
            //out.println("Driver connected");  
            
            Connection con=DriverManager.getConnection(Host,root,Pass);
            // we can use prepared statement for insert and execute 
            PreparedStatement ps=con.prepareStatement("Select * from user_master where user_type='user' and UserName=? and Password=?");
            ps.setString(1, UserId);
            ps.setString(2, pass);
            ResultSet rs =ps.executeQuery(); // this will have all rows which will match the query
            status = rs.next(); // will itterate trough DB

             if(status == true){
                 out.println("you have logged in");
             // here after the user the user has logged in we are checking if there is any current session going on for that user
             // if there is we then destroy it and create a new one. 
                HttpSession oldSession = request.getSession(false); // to create a new one we send true to get old session we send false. 
                //out.println("hiii");
            if (oldSession != null) {
                oldSession.invalidate(); // this is for the same user we identify the user by session ID 
                                         // id is created by tomcat and we store it in a cookie this how session management is done. 
            }
            //generate a new session and create jsessionid cookie
            HttpSession newSession = request.getSession();
            newSession.setAttribute("UserId", UserId);
            
            //out.println("hlllll");
            //setting session to expiry in 10 mins
            newSession.setMaxInactiveInterval(10*60);
            
            //this will add to the cookie msg with weolcome username this will create 
            Cookie userId = new Cookie("UserId", UserId); // we are adding user ID into the coookie
                                                          // this cookie will be available to the session untill the user logs out
            
            response.addCookie(userId); // this is adding the user id cookie to the response 
            out.println("cookie is created");
            response.sendRedirect("admin/LoginSuccess.jsp");
                 
                 
             }
             // this is too dispatch the request back to html incase the user does not exist. 
              
             else {
             //out.println("no user exist");
             request.getRequestDispatcher("UserLogin.html").include(request, response);  
             
            // RequestDispatcher rd = getServletContext().getRequestDispatcher("UserLogin.html");
            
            out.println("<font color=red>Either username or password is wrong.</font>");
            //rd.include(request, response);
        }
             
             
            /* TODO output your page here. You may use following sample code. */
            
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
            Logger.getLogger(UserLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserLogin.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UserLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserLogin.class.getName()).log(Level.SEVERE, null, ex);
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
