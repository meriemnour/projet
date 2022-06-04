/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.GUI;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author HP
 */
public class VehiculeDocumentController implements Initializable {
    

    @FXML
    private TextField tfId;
    @FXML
    private TextField tfType;
    @FXML
    private TextField tfNum;
    @FXML
    private TextField tfImmat;
    @FXML
    private TableView<Vehicules> tvVehicule;
    @FXML
    private TableColumn<Vehicules, Integer> colId;
    @FXML
    private TableColumn<Vehicules, String> colType;
    @FXML
    private TableColumn<Vehicules,String > colNum;
    @FXML
    private TableColumn<Vehicules, String> colImmat;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private ListView<String> listview;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
        
        if(event.getSource()== btnInsert){
            insertRecord();
        }else if (event.getSource()== btnUpdate){
            updateRecord();
        }else if (event.getSource()== btnDelete){
            deleteButton();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showVehicule();
        tableConnection1();
    }    
     public Connection getConnetion(){
        Connection conn;
        try{
          conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/foody","root","");
          return conn;
        }catch(Exception ex){
            System.out.println("Error:" + ex.getMessage());   
            return null;
        }
        

    }
      public ObservableList<Vehicules> getVehiculesList(){
            ObservableList<Vehicules> VehiculesList = FXCollections.observableArrayList();
            Connection conn = getConnetion();
            String query = "SELECT * FROM véhicule ";
            Statement st;
            ResultSet rs;
            
            try{
               st = conn.createStatement ();
               rs = st.executeQuery(query);
               Vehicules vehicules;
               while(rs.next()){
                   vehicules = new Vehicules(rs.getInt("id_vehicule"), rs.getString("type_vehicule"), rs.getString("num_chassis"), rs.getString("num_immatriculation"));
                   VehiculesList.add(vehicules);
               }
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return VehiculesList;
        }
     public void tableConnection1(){
        
        try {
              Connection conn = getConnetion();
            String query="SELECT * FROM véhicule ";
            ResultSet rs =conn.createStatement().executeQuery(query);
            while(rs.next()){
           String type_vehicule = rs.getString("type_vehicule");
           int id = rs.getInt("id_vehicule");
         String immatriculation = rs.getString("num_immatriculation");
         String num = rs.getString("num_chassis");
String listOut = id + "******\"" + type_vehicule + "*****\"" + num + "*****\"" + immatriculation  ;
      listview.getItems().add(listOut);
            }
        } catch (SQLException ex) {

            Logger.getLogger(delivery.GUI.Vehicules.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void showVehicule(){
         ObservableList<Vehicules> list = getVehiculesList();
         colId.setCellValueFactory(new PropertyValueFactory<Vehicules, Integer>("id_vehicule"));
         colType.setCellValueFactory(new PropertyValueFactory<Vehicules, String>("type_vehicule"));
         colNum.setCellValueFactory(new PropertyValueFactory<Vehicules, String>("num_chassis"));
         colImmat.setCellValueFactory(new PropertyValueFactory<Vehicules, String>("num_immatriculation"));
         tvVehicule.setItems(list);
     }
     private void insertRecord(){
           String query="INSERT INTO `véhicule`(`type_vehicule`, `num_chassis`, `num_immatriculation`)"
                    + " VALUES ('"+tfType.getText()+"','"+tfNum.getText()+"','"+tfImmat.getText()+"')";
            executeQuery(query);
            showVehicule();
        }
      private void updateRecord(){
           String query="UPDATE `véhicule` SET "
                    + "`type_vehicule`='"+tfType.getText()+"',`num_chassis`='"+tfNum.getText()+"',`num_immatriculation`='"+tfImmat.getText()+"' WHERE id_vehicule="+tfId.getText();
                   
            executeQuery(query);
            showVehicule();
        }
       private void deleteButton(){
           String query="DELETE FROM véhicule WHERE id_vehicule=" +tfId.getText() + "";
;           executeQuery(query);
            showVehicule();
        }

    private void executeQuery(String query) {
         Connection conn = getConnetion();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    
    @FXML
    private void handleMouseAction(MouseEvent event) {
         Vehicules vehicules = tvVehicule.getSelectionModel().getSelectedItem();
        //System.out.println("id" + livraisons.getId_livraison());
        //System.out.println("nom" + livraisons.getNom_livraison());
        tfId.setText("" +vehicules.getId_vehicule());
        tfType.setText(vehicules.getType_vehicule());
        tfNum.setText(vehicules.getNum_chassis());
        tfImmat.setText(vehicules.getNum_immatriculation());
    }
}
