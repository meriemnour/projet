/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  delivery.GUI;;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ilyes
 */
public class AdminModel {
     Connection connection;
     int aid;
    public AdminModel(){
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
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error");
            return false;
            
        }
       
        
    } 
     public void isAdd(String fname,int lname) throws SQLException{
        PreparedStatement preparedStatement =null;
        String query="insert into menu (menu_name,price)"+"value(?,?)";
      try {
          preparedStatement=connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
          preparedStatement.setString(1,fname);
          preparedStatement.setInt(2,lname);
          preparedStatement.execute();
          ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
          if (generatedKeys.next()) {
              aid =generatedKeys.getInt(1);
            }
      } catch (SQLException ex) {
          Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
      }finally{
      }
        

        
    }
           public void MenuUpdate( String menu_name , int price,String description,double poids,boolean vegetarien, String categorie) throws SQLException{
        PreparedStatement preparedStatement =null;
        String query="Update menu SET price=?  where  menu_name=?";
try {
          preparedStatement=connection.prepareStatement(query);
          preparedStatement.setInt(1,price);
          preparedStatement.setString(2,menu_name);

          preparedStatement.execute();
      } catch (SQLException ex) {
          Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
      }finally{
      }

     
    }  
}
