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
 * @author Winston
 */
public class SignupModel {
  Connection connection;
    public SignupModel(){
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
            Logger.getLogger(SignupModel.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error");
            return false;
            
        }
        
    } 
    public void isSignup(String fname,String lname,String email,String password,String num_tel,String username,String landmark, String date) throws SQLException{
        PreparedStatement preparedStatement =null;
        String query="insert into user (prenom,nom,email,password,num_tel,adresse,date_naissance,username,status)"+"value(?,?,?,?,?,?,?,?,?)";
      try {
          preparedStatement=connection.prepareStatement(query);
          preparedStatement.setString(1,fname);
          preparedStatement.setString(2,lname);
          preparedStatement.setString(3,email);
          preparedStatement.setString(4,password);
          preparedStatement.setString(5,num_tel);
          preparedStatement.setString(6,landmark );
          preparedStatement.setString(7, date);
          preparedStatement.setString(8, username);
          preparedStatement.setInt(9,1);
          preparedStatement.execute();
      } catch (SQLException ex) {
          Logger.getLogger(SignupModel.class.getName()).log(Level.SEVERE, null, ex);
      }finally{
          connection.close();
      }
        

        
    }
    public boolean isEmail(String email) throws SQLException{
        PreparedStatement preparedStatement = null ;
        ResultSet resultSet =null;
        String query="select * from user where email=?";
        try{
            preparedStatement =connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
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
        }
    }
}
