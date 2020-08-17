/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Register extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        response.setContentType("text/html;charset=UTF-8");

        ServletContext scontext=getServletContext(); //gives you the path of the servlet where it is and the context variables. what the server is currenlty holding  
        String SQLDriver=scontext.getInitParameter("MySQLDriver");
        String Host =scontext.getInitParameter("Host");
        String root =scontext.getInitParameter("root");
        String Pass =scontext.getInitParameter("Pass");
        
        
        
        String userID= request.getParameter("UID");
        String passWord= request.getParameter("pW");
        String firstName= request.getParameter("fname");
        String lastName = request.getParameter("Lname");
        String email= request.getParameter("email");
        String phoneNumber = request.getParameter("num");
        String gender= request.getParameter("gen");
        String course = request.getParameter("cor");
        String user_type="user";
         
        HttpSession session = request.getSession(); 
        session.setAttribute("firstName",firstName);
        
        
        
        try  {  PrintWriter out = response.getWriter();
          out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Register</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Register at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            Class.forName(SQLDriver);  
            out.println("Driver connected");     
            Connection con=DriverManager.getConnection(Host,root,Pass);
            /* TODO output your page here. You may use following sample code. */
            PreparedStatement ps=con.prepareStatement( "insert into user_master values(?,?,?,?,?,?,?,?)");
            
            // setting data base arguments to the values we have accepted from the user 
            ps.setString(1,userID);  
            ps.setString(2,passWord);  
            ps.setString(3,firstName);  
            ps.setString(4,lastName); 
            ps.setString(5,gender);  
            ps.setString(6,email);  
            ps.setString(7,phoneNumber);
            ps.setString(8, user_type);
            int i=ps.executeUpdate(); // this staement will update the DB 
            if(i>0)  
            out.print("You are successfully registered..."); 
          response.sendRedirect("admin/LoginSuccess.jsp");
              
        }
        catch (IOException | ClassNotFoundException | SQLException e2) {
            out.println(e2);}  
          
            out.close();  
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
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
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
