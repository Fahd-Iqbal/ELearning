/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author fahd_
 */
public class AddTask extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
         ServletContext scontext=getServletContext(); //gives you the path of the servlet where it is and the context variables. what the server is currenlty holding  
        String SQLDriver=scontext.getInitParameter("MySQLDriver");
        String Host =scontext.getInitParameter("Host");
        String root =scontext.getInitParameter("root");
        String Pass =scontext.getInitParameter("Pass");
        Connection con=DriverManager.getConnection(Host,root,Pass);
        
        String task_title=request.getParameter("TID");
        String desc =request.getParameter("description");
        String datec=request.getParameter("complete_date");
        HttpSession session = request.getSession();
       int userId= Integer.parseInt((String) session.getAttribute("userName"));
        //java.util.Date date=new java.util.Date();
			
       // java.sql.Date sqlDate=new java.sql.Date(date.getTime());
      out.print(datec);
      
        //Calendar calendar = Calendar.getInstance();
        //java.sql.Date ourJavaDateObject = new java.sql.Date(calendar.getTime().getTime());
        
           //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
   //LocalDateTime now = LocalDateTime.now();
      java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());  
       //Date date = (Date) new SimpleDateFormat("dd-mm-yyyy").parse(datec); // was to convert string to date 
        Date date=Date.valueOf(datec);
       out.print(date);
        PreparedStatement ps=con.prepareStatement("Insert into todolist (userID,title,task_desc,creation,finish) values(?,?,?,?,?)");
        ps.setInt(1,userId);
        ps.setString(2,task_title );
        ps.setString(3, desc);
        ps.setDate(4, sqlDate);
        ps.setDate(5, date);
        
      int rows= ps.executeUpdate();
                 if(rows>0){
            out.println("task is been Added");
            response.sendRedirect("admin/LoginSuccess.jsp");
            
            }
            else{
            out.println("Error");
            response.sendRedirect("admin/LoginSuccess.jsp");
            
            
            }
       out.println(userId);
       
        
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddTask</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddTask at " + request.getContextPath() + "</h1>");
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
        } catch (SQLException ex) {
            Logger.getLogger(AddTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(AddTask.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(AddTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(AddTask.class.getName()).log(Level.SEVERE, null, ex);
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
