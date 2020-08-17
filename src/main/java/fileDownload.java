/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Blob;
import javax.servlet.ServletContext;
/**
 *
 * @author fahd_
 */
public class fileDownload extends HttpServlet {
private static final int BUFFER_SIZE = 4096; 


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
         Class.forName("com.mysql.cj.jdbc.Driver");  
         response.setHeader("cache-control", "must-revalidate");   
            //out.println("Driver connected");  
            
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/javascript1","root","iolnyno");
        try ( PrintWriter out = response.getWriter()) {
            
           int sessid =Integer.parseInt( request.getParameter("sessid"));
           
           
            PreparedStatement ps=con.prepareStatement("Select * from session_master where Session_id=?");
            ps.setInt(1,sessid);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
            
                
                
                
          // when ever there a request response to the server. first when file download you have to say the meta data (data about the data the type of content [imagefile/pdf,size its basically the description of the data object])
          //  you have to discribe what you want what is the mime type with what name 
          // then the actual content will be downloaded 
                
                
                
                
                
            String topic =rs.getString("topic");
            String topicn =rs.getString("topicName");
            out.println("topic name is " +topicn+ "  :" +topic);
            String fileName=rs.getString("fileName"); // we are stroing the file name 
            String mimeType = rs.getString("fileType");
             if(mimeType.equals("text/plain")){// if i want to change i have to play with the if statement  {
             fileName +=".txt";
             
             }
             String Stringfile= "";
             Stringfile =rs.getString("file"); // this will give you the file in string format
             int fileLength = Stringfile.length(); // size of file 
             byte[] rb = new byte[fileLength]; // creating a byte the same size as the file 
             InputStream readFile = rs.getBinaryStream("file"); // read binary stream and put it into inputstream 
             int index = readFile.read(rb,0,fileLength); // array of byes , 0 is where you start and fileLength where you end
             ps.close(); // at this point everything is read and we need to transfer to output stream 
             
             response.reset(); // its a pre-caution to see there is nothing in the reponse idea of clearing a buffer before putting something into it 
             // we need to set the response headers so in them there will be meta data 
             response.setContentType(mimeType);
             response.setHeader("Content-disposition","attachment; filename=" +fileName);
           // this is telling the browser that the type of response will be an attachment with the fileName as specified. 
           
             response.getOutputStream().write(rb, 0, fileLength); // thsi will make the binary into an outputstream 
             response.getOutputStream().flush(); //this will make sure the bytes are wirtten and nothing is left
            
            }
            else {
                // no file found
                response.getWriter().print("File not found for the id: " + sessid);  
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            response.getWriter().print("SQL Error: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            response.getWriter().print("IO Error: " + ex.getMessage());
        } finally {
            if (con != null) {
                // closes the database connection
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
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
            Logger.getLogger(fileDownload.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(fileDownload.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(fileDownload.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(fileDownload.class.getName()).log(Level.SEVERE, null, ex);
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
