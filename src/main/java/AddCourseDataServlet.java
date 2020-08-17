/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author fahd_
 */
@MultipartConfig(maxFileSize = 16177215)
public class AddCourseDataServlet extends HttpServlet {

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
            out.println("<title>Servlet AddCourseDataServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddCourseDataServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            
            String course_name = request.getParameter("Courses"); // selected course
            String topic_name=request.getParameter("tname"); 
            String description_topic = request.getParameter("dname");
            
            
            
            boolean status;
            boolean status2;
            int courseID=0;
            Class.forName("com.mysql.cj.jdbc.Driver");  
            
           out.println("Driver connected");  
            
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/javascript1","root","iolnyno");
            
            PreparedStatement ps2=con.prepareStatement("select * from course_master where course_name=?");
                  ps2.setString(1,course_name);
                  ResultSet rs2 =ps2.executeQuery();  
                  status2 = rs2.next();
            if(status2){
           courseID =rs2.getInt("course_id");
           out.println("Course name is:" + course_name);
           out.println("Course Id is:" + courseID);
           // we neeed to get the CourseID inorder to insert it into session Master because it is a foreign key 
            
            }
            // file is stroed in a server 
            // create an input stream to accept the file 
            InputStream inputStream = null; // input stream of the upload file
            Part filePart = request.getPart("data");
        if (filePart != null) {
            // prints out some information for debugging
            out.println(filePart.getName());
            out.println(filePart.getSize());
            out.println(filePart.getContentType());
             
            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        }
            
        // servlet file upload java     
         
        
                PreparedStatement ps1=con.prepareStatement("insert into session_master (course_id,topicName,topic,file,fileName,fileType) values(?,?,?,?,?,?)");
                 
                  ps1.setInt(1, courseID);
                  ps1.setString(2,topic_name);
                  ps1.setString(3, description_topic);
                  if (inputStream != null) {
                // fetches input stream of the upload file for the blob column
               ps1.setBinaryStream(4,inputStream, inputStream.available()); // inputStream.available() gives you the length 
               ps1.setString(5,filePart.getName());
               ps1.setString(6,filePart.getContentType());
            }
                  
                  
                  
                  //out.println("query is working");
                 // ps1.execute();
                 int row=ps1.executeUpdate();
                 
                  out.println("number of rows inserted" +row);
                if(row>0){
                out.println("File is uploaded");
                }
                else{
                out.println("Error in file uploading");
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
            Logger.getLogger(AddCourseDataServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddCourseDataServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AddCourseDataServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddCourseDataServlet.class.getName()).log(Level.SEVERE, null, ex);
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
