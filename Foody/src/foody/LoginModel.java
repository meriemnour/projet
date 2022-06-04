/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ilyes
 */

public class LoginModel {
    static int id;
    
    Connection connection;
    public LoginModel(){
        connection =SqlConnection.Connector();
        if(connection==null){
            System.exit(0);
            System.out.println("notconnected");
        }
    }
    public boolean isDbConnected(){
        try {
            return !connection.isClosed();
        } catch (SQLException ex) {
            System.out.println("error");
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error");
            return false;
            
        }
        
    } 
    public boolean isLogin(String email ,String pass) throws SQLException{
        PreparedStatement preparedStatement = null ;
        ResultSet resultSet =null;
        ResultSet resultSet1 =null;
        String query="select id , status from user where email=? and password=?";
        try{
            preparedStatement =connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);

      resultSet1=preparedStatement.executeQuery();
resultSet1.next() ;

 int status = resultSet1.getInt("status") ;

  
resultSet=preparedStatement.executeQuery();

            if(resultSet.next() && (status==1)) {
                id =resultSet.getInt("id");
                System.out.println(""+id);
                LoginController.CustomerId(id);
                return true;
            }else{
                return false;
            }
            
        }catch(SQLException e){
            System.out.println(" no!"+e);
            return false;
        }finally{
            preparedStatement.close();
            resultSet.close();
            resultSet1.close();

        }
        
        
        
    }
    
    
}
