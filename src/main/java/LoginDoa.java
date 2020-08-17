
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fahd_
 */
public class LoginDoa {
   
public boolean check(String uname, String pass) throws ClassNotFoundException, SQLException  
{
Class.forName("com.mysql.cj.jdbc.Driver");  
            
           // out.println("Driver connected");  
            
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/javascript1","root","iolnyno");
            
            PreparedStatement ps=con.prepareStatement("Select * from user_master where UserName=? and Password=? AND user_type='user'");
            ps.setString(1, uname);
            ps.setString(2, pass);
            ResultSet rs =ps.executeQuery();
            if(rs.next()){
            return true;
            }
return false;
}
    
    // i can change boolean to String and then return a firstNAme in case of false its null
   // then in loginUser i can check to see if firstName is null 
    
}
