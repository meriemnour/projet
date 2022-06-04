/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.GUI ;

import com.barcodelib.barcode.Linear;
import static delivery.GUI.AdminPanelController.infoBox1;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author HP
 */
public class FXMLDocumentController implements Initializable {
    
   
    @FXML
    private TextField tfid;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfdate;
    @FXML
    private TextField tfdestination;
    @FXML
    private TableView<Livraisons> tvLivraisons;
    @FXML
    private TableColumn<Livraisons, Integer> colid;
    @FXML
    private TableColumn<Livraisons, String> colnom;
    @FXML
    private TableColumn<Livraisons, String> coldate;
    @FXML
    private TableColumn<Livraisons, String> coldestination;
    @FXML
    private Button btninsert;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btndelete;
    @FXML
    private ListView<String> listview;
    @FXML
    private Button btnrecherche;
    @FXML
    private Button brcode;
    @FXML
    private TextField txtbarcode;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
       
        
        if(event.getSource()== btninsert){
            insertRecord();
        }else if (event.getSource()== btnupdate){
            updateRecord();
        }else if (event.getSource()== btndelete){
            deleteButton();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       showLivraisons();
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
     
        public ObservableList<Livraisons> getLivraisonsList(){
            ObservableList<Livraisons> livraisonsList = FXCollections.observableArrayList();
            Connection conn = getConnetion();
            String query = "SELECT * FROM livraison ";
            Statement st;
            ResultSet rs;
            
            try{
               st = conn.createStatement ();
               rs = st.executeQuery(query);
               Livraisons livraisons;
               while(rs.next()){
                   livraisons = new Livraisons(rs.getInt("id_livraison"), rs.getString("nom_livraison"), rs.getString("date"), rs.getString("destination"));
                   livraisonsList.add(livraisons);
               }
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return livraisonsList;
        }
     public void tableConnection1(){
        
        try {
              Connection conn = getConnetion();
            String query="SELECT * FROM livraison ";
            ResultSet rs =conn.createStatement().executeQuery(query);
            while(rs.next()){
           String name = rs.getString("nom_livraison");
           int id = rs.getInt("id_livraison");
         String date = rs.getString("date");
         String destination = rs.getString("destination");
String listOut = id + "******\"" + name + "*****\"" + date + "*****\"" + destination  ;
      listview.getItems().add(listOut);
            }
        } catch (SQLException ex) {

            Logger.getLogger(Livraisons.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        public void showLivraisons(){
            ObservableList<Livraisons> list = getLivraisonsList();
            
            colid.setCellValueFactory(new PropertyValueFactory<Livraisons, Integer>("id_livraison"));
            colnom.setCellValueFactory(new PropertyValueFactory<Livraisons, String>("nom_livraison"));
            coldate.setCellValueFactory(new PropertyValueFactory<Livraisons, String>("date"));
            coldestination.setCellValueFactory(new PropertyValueFactory<Livraisons, String>("destination"));
            
            tvLivraisons.setItems(list);
        }
        private void insertRecord(){
           String query="INSERT INTO `livraison`(`nom_livraison`, `date`, `destination`)"
                    + " VALUES ('"+tfnom.getText()+"','"+tfdate.getText()+"','"+tfdestination.getText()+"')";
            executeQuery(query);
            showLivraisons();
        }
        private void updateRecord(){
           String query="UPDATE `livraison` SET "
                    + "`nom_livraison`='"+tfnom.getText()+"',`date`='"+tfdate.getText()+"',`destination`='"+tfdestination.getText()+"' WHERE id_livraison="+tfid.getText();
                   
            executeQuery(query);
            showLivraisons();
        }
        private void deleteButton(){
           String query="DELETE FROM livraison WHERE id_livraison=" +tfid.getText() + "";
;           executeQuery(query);
            showLivraisons();
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
        Livraisons livraisons = tvLivraisons.getSelectionModel().getSelectedItem();
        //System.out.println("id" + livraisons.getId_livraison());
        //System.out.println("nom" + livraisons.getNom_livraison());
        tfid.setText("" +livraisons.getId_livraison());
        tfnom.setText(livraisons.getNom_livraison());
        tfdate.setText(livraisons.getDate());
        tfdestination.setText(livraisons.getDestination());
                
    }
          public  void btn11(ActionEvent event){
try{
Linear barcode = new Linear() ;
   barcode.setType(Linear.CODE128B);
   barcode.setData(txtbarcode.getText());
   barcode.setI(11.0f);
   String f_name = txtbarcode.getText() ;
barcode.renderBarcode("D:\\"+f_name+".png");
}
catch (Exception e) {
               infoBox1("Error", null, "Error");


}
       }
}   