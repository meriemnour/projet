/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.GUI;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.util.Collections.list;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Ilyes
 */
public class AdminPanelController implements Initializable {
    AdminModel adminModel=new AdminModel();
    @FXML
    private JFXTextField lastNametxt;

    @FXML
    private JFXTextField firstNametxt;
        @FXML
    private JFXTextField lastNametxt1;

    @FXML
    private JFXTextField firstNametxt1;
    @FXML
    private ListView<String> listview;
  Connection con;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
                 tableConnection1();

          if(adminModel.isDbConnected()){
             System.out.println("Db connected");
        }else{
             System.out.println("Db not connected");
        }
    }
    @FXML
    public void exitScreen(ActionEvent event){
        System.exit(0);
    }

       public AdminPanelController(){
        con=SqlConnection.Connector();
    }
    
    public void AddEmployee(ActionEvent event){
       String fname=firstNametxt.getText();
       int lname=Integer.valueOf(lastNametxt.getText());
       try {
           if((fname.isEmpty()|| lname==0 )){
               infoBox("Enter valid fields",null,"Error");
           }else{
               adminModel.isAdd(fname, lname);
               infoBox(" Added Sucessfully\n menu Id is:"+adminModel.aid,null,"Success" );
               firstNametxt.clear();
               lastNametxt.clear();

               listview.refresh();

           }
           
       } catch (SQLException ex) {
           infoBox("please fill balnk fields",null,"Alert" );
           Logger.getLogger(AdminPanelController.class.getName()).log(Level.SEVERE, null, ex);
       }
      }
        public void MàdEmployee(ActionEvent event){
       String fname=firstNametxt1.getText();
       int lname=Integer.valueOf(lastNametxt1.getText());
       try {
           if((fname.isEmpty()|| lname==0 )){
               infoBox("Enter valid fields",null,"Error");
           }else{
               adminModel.isAdd(fname, lname);
               infoBox("Modifiied Sucessfully\n menu Id is:"+adminModel.aid,null,"Success" );
               firstNametxt.clear();
               listview.refresh();

               lastNametxt.clear();
           }
           
       } catch (SQLException ex) {
           infoBox("please fill balnk fields",null,"Alert" );
           Logger.getLogger(AdminPanelController.class.getName()).log(Level.SEVERE, null, ex);
       }
      }
     public void TakeOrderScreen(ActionEvent event) throws Exception  {
		Stage primaryStage =new Stage();
                primaryStage.initStyle(StageStyle.UNDECORATED);
		Parent root =FXMLLoader.load(getClass().getResource("TakeOrder.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
                
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();
	}
  
          public static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

      public void tableConnection1(){
        
        try {
             
            String query="SELECT * FROM menu ";
            ResultSet rs =con.createStatement().executeQuery(query);
            while(rs.next()){
           String name = rs.getString("menu_name");
           int id = rs.getInt("menu_id");
         int prix = rs.getInt("price");
String listOut = id + "******\"" + name + "*****\"" + prix  ;
      listview.getItems().add(listOut);
            }
        } catch (SQLException ex) {

            Logger.getLogger(AdminPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    public void deleteItem(ActionEvent event){
       try{
       int tempMenuid = listview.getSelectionModel().getSelectedIndex() ;
        tempMenuid = listview.getSelectionModel().getSelectedIndex() +1 ;

       }catch(Exception e){
           infoBox1("no item selected!", null, "Error");
           
       }
          int tempMenuid = listview.getSelectionModel().getSelectedIndex() ;

    if(tempMenuid >= 0){
        String query = "DELETE FROM menu WHERE  ( menu_id = ? ) ";  
        PreparedStatement pst;
           try {              
               pst = con.prepareStatement(query);
               pst.setInt(1, tempMenuid);
               pst.execute();

               listview.getItems().remove(tempMenuid);
               listview.refresh();
               listview.getSelectionModel().clearSelection();
               
              
           } catch (SQLException ex) {
               Logger.getLogger(AdminPanelController.class.getName()).log(Level.SEVERE, null, ex);
           }catch(Exception e){
               infoBox1("no item selected!", null, "Error");
           }
               
       
    } else {
        System.out.println("no selction made");
    }
}
    
        
     public static void infoBox1(String infoMessage, String headerText, String title){
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle(title);
         alert.setHeaderText(headerText);
         alert.setContentText(infoMessage);
         alert.showAndWait();
     }
     public  void Logout(ActionEvent event){
         System.exit(0);
     }
    @FXML
     public void TakeOrderScreen1(ActionEvent event) throws Exception  {
		Stage primaryStage =new Stage();
                primaryStage.initStyle(StageStyle.UNDECORATED);
		Parent root =FXMLLoader.load(getClass().getResource("api.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
                
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();
	}
}
