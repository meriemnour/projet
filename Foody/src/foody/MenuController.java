/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foody;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author Ilyes
 */
public class MenuController implements Initializable {
    public MenuModel menuModel =new MenuModel();
    
    @FXML
    private Label custLabel;
    @FXML
    private TableView<ModelTable> table;
    @FXML
    private TableColumn<ModelTable, String> MenuIdCol;

    @FXML
    private TableColumn<ModelTable, String> MenuNameCol;

    @FXML
    private TableColumn<ModelTable, String> PriceCol;

    @FXML
    private TableColumn<ModelTable, String> QuantityCol;

    @FXML
    private Label totalAmount;
    
    @FXML
    private TableView<ModelTable1> table1;
    @FXML
    private TableColumn<ModelTable1, String> OrderidCol1;

    @FXML
    private TableColumn<ModelTable1, String> MenuNameCol1;

    @FXML
    private TableColumn<ModelTable1, String> QuantityCol1;

    @FXML
    private TableColumn<ModelTable1, String> OrderStatusCol1;

    @FXML
    private Rating rating1;
    @FXML
    private Rating rating2;
    @FXML
    private Rating rating3;
    @FXML
    private Rating rating4;
    @FXML
    private Rating rating5;
    @FXML
    private Rating rating6;
    @FXML
    private Rating rating7;
    @FXML
    private Rating rating8;
    @FXML
    private Rating rating9;
    @FXML
    private Rating rating10;
    @FXML
    private Rating rating11;
    @FXML
    private Rating rating12;
    @FXML
    private JFXButton stopbtn;
       @FXML
    private JFXButton button;
       @FXML
    private JFXTextField QR_Read ;
    Connection con; //connection for table 
    
    public static int i;
    Stage primaryStage ;
    boolean type;
               MediaPlayer mediaPlayer ;

    ObservableList<ModelTable> obList= FXCollections.observableArrayList();
    ObservableList<ModelTable1> obList1= FXCollections.observableArrayList();

    
    public MenuController(){
        con=SqlConnection.Connector();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         totalAmount.setText("0");
        
         i= LoginController.cust_id;//customer id which is primary key
         custLabel.setText(Integer.toString(i));
         if(menuModel.isDbConnected()){
             System.out.println("Db connected");
        }else{
             System.out.println("Db not connected");
        }
         
        music();
         MenuIdCol.setCellValueFactory(new PropertyValueFactory<>("menuid"));
         MenuNameCol.setCellValueFactory(new PropertyValueFactory<>("menuname"));
         PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
         tableConnection();
         table.setItems(obList);
         table.refresh();
         table.getSelectionModel().clearSelection();
         calculate();
         Rate_Init1();
         Rate_Init2();
         Rate_Init3();
         Rate_Init4();
         Rate_Init5();
         Rate_Init6();
         Rate_Init7();
         Rate_Init8();
         Rate_Init9();
         Rate_Init10();
         Rate_Init11();
        Rate_Init12();
         QuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity_item"));
                OrderidCol1.setCellValueFactory(new PropertyValueFactory<>("orderno"));
         MenuNameCol1.setCellValueFactory(new PropertyValueFactory<>("menuname"));  
         QuantityCol1.setCellValueFactory(new PropertyValueFactory<>("quantity_item"));
         OrderStatusCol1.setCellValueFactory(new PropertyValueFactory<>("status"));
         tableConnection1();
         table1.setItems(obList1);
         table1.refresh();
        
         
    }
       
    public void music (){
    String s = "falfoul.mp3" ;
    Media h = new  Media(Paths.get(s).toUri().toString());
    mediaPlayer = new MediaPlayer(h);
    mediaPlayer.play();
    
    
    
    }
     public void musicstop (ActionEvent event){
if (    "Stop".equals(stopbtn.getText())) {
    mediaPlayer.pause();
    stopbtn.setText("Play");
    
}
else {
    stopbtn.setText("Stop");
    mediaPlayer.play();}

    
    }
    public void deleteItem(ActionEvent event){
       ModelTable tableIndex = (ModelTable)table.getSelectionModel().getSelectedItem();
       int tempMenuid = -1;
       try{
       tempMenuid = tableIndex.getMenuid();
       }catch(Exception e){
           infoBox1("no item selected!", null, "Error");
           
       }
   
    if(tempMenuid >= 0){
        String query = "DELETE FROM orders WHERE  ( menu_id = ? and customer_id=? and order_status='ADDED_TO_CART') ";  
        PreparedStatement pst;
           try {              
               pst = con.prepareStatement(query);
               pst.setInt(1, tempMenuid);
               pst.setInt(2, i);
               pst.execute();
               table.getItems().remove(tableIndex);
               table.refresh();
               table.getSelectionModel().clearSelection();
               
               calculate();
              
           } catch (SQLException ex) {
               Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
           }catch(Exception e){
               infoBox1("no item selected!", null, "Error");
           }
               
       
    } else {
        System.out.println("no selction made");
    }
}
    
    
    
    
    
    public void tableConnection(){
        table.getItems().clear();
        try {
             
            String query="SELECT menu.price as Price ,menu.menu_id,menu.menu_name as Name,quantity FROM orders JOIN menu ON orders.menu_id=menu.menu_id WHERE orders.customer_id="+i+" and order_status='ADDED_TO_CART'";
            ResultSet rs =con.createStatement().executeQuery(query);
            while(rs.next()){
                obList.add(new ModelTable(rs.getString("Name"), rs.getInt("menu_id"),rs.getInt("quantity"), rs.getInt("Price")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void tableConnection1(){
        
        try {
             
            String query="SELECT orders.order_id as OrderId,menu.menu_name as Name,quantity, order_status FROM orders JOIN menu ON orders.menu_id=menu.menu_id WHERE orders.customer_id="+i+" and (order_status='PAYMENT_CONFIRMED' OR order_status='DELIVERED') ";
            ResultSet rs =con.createStatement().executeQuery(query);
            while(rs.next()){
                obList1.add(new ModelTable1( rs.getInt("OrderId"), rs.getString("Name"), rs.getInt("quantity"),rs.getString("order_status")) );
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void calculate(){
        try {
            
            String query="SELECT sum(menu.price*quantity) as totalamount FROM orders JOIN menu ON orders.menu_id=menu.menu_id WHERE orders.customer_id="+i+" and order_status='ADDED_TO_CART'";
            ResultSet rs =con.createStatement().executeQuery(query);
            while(rs.next()){
             
                int totalamount=rs.getInt("totalamount");
                
                totalAmount.setText(Integer.toString(totalamount));
                if (   "Valid Code".equals(QR_Read.getText())){
                    Integer somme = Integer.valueOf(totalAmount.getText());
                    somme =  somme -  (int)(Math.round(0.1*somme)) ;
                   totalAmount.setText(Integer.toString(somme));

                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //code below is load the payment screen
    @FXML
     public void PaymentScreen(ActionEvent event) throws Exception  {
		Stage primaryStage =new Stage();
                primaryStage.initStyle(StageStyle.UNDECORATED);
		Parent root =FXMLLoader.load(getClass().getResource("Payment.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
                
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();
	}
     public void Menu1(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(1)){
               type =infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
               // btn1.setDisable(true);
               if(type){
                   menuModel.increment_qnt(1);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
            }else{
                menuModel.cart_add(1);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
               // btn1.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu2(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(2)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                //btn2.setDisable(true);
                if(type){
                   menuModel.increment_qnt(2);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
            }else{
                menuModel.cart_add(2);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
               // btn2.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu3(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(3)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(3);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
                //btn3.setDisable(true);
            }else{
                menuModel.cart_add(3);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
               // btn3.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu4(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(4)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(4);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
                //btn4.setDisable(true);
            }else{
                menuModel.cart_add(4);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
                //btn4.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu5(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(5)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(5);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
                //btn5.setDisable(true);
            }else{
                menuModel.cart_add(5);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
               // btn5.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu6(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(6)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(6);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
               // btn6.setDisable(true);
            }else{
                menuModel.cart_add(6);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
               // btn6.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu7(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(7)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(7);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
                //btn7.setDisable(true);
            }else{
                menuModel.cart_add(7);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
               // btn7.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu8(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(8)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(8);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
                
                //btn8.setDisable(true);
            }else{
                menuModel.cart_add(8);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
               // btn8.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu9(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(9)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(9);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
               // btn9.setDisable(true);
            }else{
                menuModel.cart_add(9);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
                //btn9.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu10(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(10)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(10);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
                //btn10.setDisable(true);
            }else{
                menuModel.cart_add(10);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
               // btn10.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu11(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(11)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(11);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
                //btn11.setDisable(true);
            }else{
                menuModel.cart_add(11);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
               // btn11.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void Menu12(ActionEvent event){
        try {
            if(menuModel.check_if_added_to_cart(12)){
                type=infoBox("Item is already in the cart.\nDo you really want to add another one?",null,"Alert!" );
                if(type){
                   menuModel.increment_qnt(12);
                   tableConnection();
                   table.refresh();
                   calculate();
                   infoBox1("Item added to the cart!",null,"Success" );
               }
                
               // btn12.setDisable(true);
            }else{
                menuModel.cart_add(12);
                tableConnection();
                table.refresh();
                calculate();
                infoBox1("Item added to the cart!",null,"Success" );
                //btn12.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }



    public void paymentScreen(ActionEvent event){
         if(menuModel.isItemInCart()){
             type=infoBox("Do you really want Confirm order",null,"Alter!");
             if(type){
                 menuModel.update_status_to_confirmed();
                 menuModel.copy_to_payment();
                 try {
                    Node node = (Node)event.getSource();
                    Stage dialogStage = (Stage) node.getScene().getWindow();
                    dialogStage.close();
                    Scene scene;
                    scene = new Scene(FXMLLoader.load(getClass().getResource("Payment.fxml")));
                    dialogStage.setScene(scene);
                    dialogStage.show();
              } catch (IOException ex) {
                     Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
                 
             }else{
                 System.out.println("order is not placed");
             }
         }else{
             infoBox1("No items in the cart",null,"Alert!");
         }
               
        
               
    }

     public static boolean infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.getButtonTypes();
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK button
         return true;
        } else {
        // ... user chose CANCEL or closed the dialog
        return false;
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
   
    public void statscreen(ActionEvent event) throws IOException{
         
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Stat.fxml"));
    Parent root1 = (Parent) fxmlLoader.load();
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initStyle(StageStyle.UNDECORATED);
    stage.setTitle("Stat");
    stage.setScene(new Scene(root1));  
    stage.show();
        }
    
    
    public void Rate_Init1(){
        table.getItems().clear();
        try {
             
                  Statement s1 = con.createStatement();
            ResultSet r1 = s1.executeQuery("select rating_num AS old from rating where  menu_id=1");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            rating1.setRating(old);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       public void Rate_Init2(){
        table.getItems().clear();
        try {
             
                  Statement s1 = con.createStatement();
            ResultSet r1 = s1.executeQuery("select rating_num AS old from rating where  menu_id=2");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            rating2.setRating(old);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
          public void Rate_Init3(){
        table.getItems().clear();
        try {
             
                  Statement s1 = con.createStatement();
            ResultSet r1 = s1.executeQuery("select rating_num AS old from rating where  menu_id=3");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            rating3.setRating(old);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
             public void Rate_Init4(){
        table.getItems().clear();
        try {
             
                  Statement s1 = con.createStatement();
            ResultSet r1 = s1.executeQuery("select rating_num AS old from rating where  menu_id=4");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            rating4.setRating(old);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
                          public void Rate_Init5(){
        table.getItems().clear();
        try {
             
                  Statement s1 = con.createStatement();
            ResultSet r1 = s1.executeQuery("select rating_num AS old from rating where  menu_id=5");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            rating5.setRating(old);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
                                       public void Rate_Init6(){
        table.getItems().clear();
        try {
             
                  Statement s1 = con.createStatement();
            ResultSet r1 = s1.executeQuery("select rating_num AS old from rating where  menu_id=6");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            rating6.setRating(old);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       public void Rate_Init7(){
        table.getItems().clear();
        try {
             
                  Statement s1 = con.createStatement();
            ResultSet r1 = s1.executeQuery("select rating_num AS old from rating where  menu_id=7");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            rating7.setRating(old);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
                                                                 public void Rate_Init8(){
        table.getItems().clear();
        try {
             
                  Statement s1 = con.createStatement();
            ResultSet r1 = s1.executeQuery("select rating_num AS old from rating where  menu_id=8");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            rating8.setRating(old);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        public void Rate_Init9(){
        table.getItems().clear();
        try {
             
                  Statement s1 = con.createStatement();
            ResultSet r1 = s1.executeQuery("select rating_num AS old from rating where  menu_id=9");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            rating9.setRating(old);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        public void Rate_Init10(){
        table.getItems().clear();
        try {
             
                  Statement s1 = con.createStatement();
            ResultSet r1 = s1.executeQuery("select rating_num AS old from rating where  menu_id=10");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            rating10.setRating(old);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       public void Rate_Init11(){
        table.getItems().clear();
        try {
             
                  Statement s1 = con.createStatement();
            ResultSet r1 = s1.executeQuery("select rating_num AS old from rating where  menu_id=11");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            rating11.setRating(old);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       public void Rate_Init12(){
        table.getItems().clear();
        try {
             
                  Statement s1 = con.createStatement();
            ResultSet r1 = s1.executeQuery("select rating_num AS old from rating where  menu_id=12");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            rating12.setRating(old);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       public void update_Rating1(ActionEvent event) throws SQLException{
         PreparedStatement preparedStatement ;
         String query="update rating SET rating_num=? , compteur=?  where menu_id=?";


        try {
            Statement s = con.createStatement();
            int i1=1;
            preparedStatement =con.prepareStatement(query);
            ResultSet r = s.executeQuery("select compteur AS cpt from rating where  menu_id=1");
            r.next();
            int compt = r.getInt("cpt") ;
            r.close();
            Statement s2 = con.createStatement();
            ResultSet r1 = s2.executeQuery("select rating_num AS old from rating where  menu_id=1");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            double rate = rating1.getRating() ;
            preparedStatement.setDouble(1, (rate+old*compt)/(compt+1));
            preparedStatement.setInt(2,compt+1 );
            preparedStatement.setInt(3,i1 );
            preparedStatement.execute();
            Rate_Init1();
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
       public void update_Rating2(ActionEvent event) throws SQLException{
         PreparedStatement preparedStatement ;
         String query="update rating SET rating_num=? , compteur=?  where menu_id=?";


        try {
            Statement s = con.createStatement();
            int i1=2;
            preparedStatement =con.prepareStatement(query);
            ResultSet r = s.executeQuery("select compteur AS cpt from rating where  menu_id=2");
            r.next();
            int compt = r.getInt("cpt") ;
            r.close();
            Statement s2 = con.createStatement();
            ResultSet r1 = s2.executeQuery("select rating_num AS old from rating where  menu_id=2");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            double rate = rating2.getRating() ;
            preparedStatement.setDouble(1, (rate+old*compt)/(compt+1));
            preparedStatement.setInt(2,compt+1 );
            preparedStatement.setInt(3,i1 );
            preparedStatement.execute();
              Rate_Init2();
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
              public void update_Rating3(ActionEvent event) throws SQLException{
         PreparedStatement preparedStatement ;
         String query="update rating SET rating_num=? , compteur=?  where menu_id=?";


        try {
            Statement s = con.createStatement();
            int i1=3;
            preparedStatement =con.prepareStatement(query);
            ResultSet r = s.executeQuery("select compteur AS cpt from rating where  menu_id=3");
            r.next();
            int compt = r.getInt("cpt") ;
            r.close();
            Statement s2 = con.createStatement();
            ResultSet r1 = s2.executeQuery("select rating_num AS old from rating where  menu_id=3");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            double rate = rating3.getRating() ;
            preparedStatement.setDouble(1, (rate+old*compt)/(compt+1));
            preparedStatement.setInt(2,compt+1 );
            preparedStatement.setInt(3,i1 );
            preparedStatement.execute();
              Rate_Init3();
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
                     public void update_Rating4(ActionEvent event) throws SQLException{
         PreparedStatement preparedStatement ;
         String query="update rating SET rating_num=? , compteur=?  where menu_id=?";


        try {
            Statement s = con.createStatement();
            int i1=4;
            preparedStatement =con.prepareStatement(query);
            ResultSet r = s.executeQuery("select compteur AS cpt from rating where  menu_id=4");
            r.next();
            int compt = r.getInt("cpt") ;
            r.close();
            Statement s2 = con.createStatement();
            ResultSet r1 = s2.executeQuery("select rating_num AS old from rating where  menu_id=4");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            double rate = rating4.getRating() ;
            preparedStatement.setDouble(1, (rate+old*compt)/(compt+1));
            preparedStatement.setInt(2,compt+1 );
            preparedStatement.setInt(3,i1 );
            preparedStatement.execute();
              Rate_Init4();
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
          public void update_Rating5(ActionEvent event) throws SQLException{
         PreparedStatement preparedStatement ;
         String query="update rating SET rating_num=? , compteur=?  where menu_id=?";


        try {
            Statement s = con.createStatement();
            int i1=5;
            preparedStatement =con.prepareStatement(query);
            ResultSet r = s.executeQuery("select compteur AS cpt from rating where  menu_id=5");
            r.next();
            int compt = r.getInt("cpt") ;
            r.close();
            Statement s2 = con.createStatement();
            ResultSet r1 = s2.executeQuery("select rating_num AS old from rating where  menu_id=5");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            double rate = rating5.getRating() ;
            preparedStatement.setDouble(1, (rate+old*compt)/(compt+1));
            preparedStatement.setInt(2,compt+1 );
            preparedStatement.setInt(3,i1 );
            preparedStatement.execute();
          Rate_Init5();
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
         public void update_Rating6(ActionEvent event) throws SQLException{
         PreparedStatement preparedStatement ;
         String query="update rating SET rating_num=? , compteur=?  where menu_id=?";


        try {
            Statement s = con.createStatement();
            int i1=6;
            preparedStatement =con.prepareStatement(query);
            ResultSet r = s.executeQuery("select compteur AS cpt from rating where  menu_id=6");
            r.next();
            int compt = r.getInt("cpt") ;
            r.close();
            Statement s2 = con.createStatement();
            ResultSet r1 = s2.executeQuery("select rating_num AS old from rating where  menu_id=6");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            double rate = rating6.getRating() ;
            preparedStatement.setDouble(1, (rate+old*compt)/(compt+1));
            preparedStatement.setInt(2,compt+1 );
            preparedStatement.setInt(3,i1 );
            preparedStatement.execute();
            Rate_Init6();
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
      public void update_Rating7(ActionEvent event) throws SQLException{
         PreparedStatement preparedStatement ;
         String query="update rating SET rating_num=? , compteur=?  where menu_id=?";


        try {
            Statement s = con.createStatement();
            int i1=7;
            preparedStatement =con.prepareStatement(query);
            ResultSet r = s.executeQuery("select compteur AS cpt from rating where  menu_id=7");
            r.next();
            int compt = r.getInt("cpt") ;
            r.close();
            Statement s2 = con.createStatement();
            ResultSet r1 = s2.executeQuery("select rating_num AS old from rating where  menu_id=7");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            double rate = rating7.getRating() ;
            preparedStatement.setDouble(1, (rate+old*compt)/(compt+1));
            preparedStatement.setInt(2,compt+1 );
            preparedStatement.setInt(3,i1 );
            preparedStatement.execute();
            Rate_Init7();
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        }
 }
    public void update_Rating8(ActionEvent event) throws SQLException{
         PreparedStatement preparedStatement ;
         String query="update rating SET rating_num=? , compteur=?  where menu_id=?";


        try {
            Statement s = con.createStatement();
            int i1=8;
            preparedStatement =con.prepareStatement(query);
            ResultSet r = s.executeQuery("select compteur AS cpt from rating where  menu_id=8");
            r.next();
            int compt = r.getInt("cpt") ;
            r.close();
            Statement s2 = con.createStatement();
            ResultSet r1 = s2.executeQuery("select rating_num AS old from rating where  menu_id=8");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            double rate = rating8.getRating() ;
            preparedStatement.setDouble(1, (rate+old*compt)/(compt+1));
            preparedStatement.setInt(2,compt+1 );
            preparedStatement.setInt(3,i1 );
            preparedStatement.execute();
          Rate_Init8();
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
 public void update_Rating9(ActionEvent event) throws SQLException{
         PreparedStatement preparedStatement ;
         String query="update rating SET rating_num=? , compteur=?  where menu_id=?";


        try {
            Statement s = con.createStatement();
            int i1=9;
            preparedStatement =con.prepareStatement(query);
            ResultSet r = s.executeQuery("select compteur AS cpt from rating where  menu_id=9");
            r.next();
            int compt = r.getInt("cpt") ;
            r.close();
            Statement s2 = con.createStatement();
            ResultSet r1 = s2.executeQuery("select rating_num AS old from rating where  menu_id=9");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            double rate = rating9.getRating() ;
            preparedStatement.setDouble(1, (rate+old*compt)/(compt+1));
            preparedStatement.setInt(2,compt+1 );
            preparedStatement.setInt(3,i1 );
            preparedStatement.execute();
          Rate_Init9();
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
          public void update_Rating10(ActionEvent event) throws SQLException{
         PreparedStatement preparedStatement ;
         String query="update rating SET rating_num=? , compteur=?  where menu_id=?";


        try {
            Statement s = con.createStatement();
            int i1=10;
            preparedStatement =con.prepareStatement(query);
            ResultSet r = s.executeQuery("select compteur AS cpt from rating where  menu_id=10");
            r.next();
            int compt = r.getInt("cpt") ;
            r.close();
            Statement s2 = con.createStatement();
            ResultSet r1 = s2.executeQuery("select rating_num AS old from rating where  menu_id=10");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            double rate = rating10.getRating() ;
            preparedStatement.setDouble(1, (rate+old*compt)/(compt+1));
            preparedStatement.setInt(2,compt+1 );
            preparedStatement.setInt(3,i1 );
            preparedStatement.execute();
            Rate_Init10();
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
public void update_Rating11(ActionEvent event) throws SQLException{
         PreparedStatement preparedStatement ;
         String query="update rating SET rating_num=? , compteur=?  where menu_id=?";


        try {
            Statement s = con.createStatement();
            int i1=11;
            preparedStatement =con.prepareStatement(query);
            ResultSet r = s.executeQuery("select compteur AS cpt from rating where  menu_id=11");
            r.next();
            int compt = r.getInt("cpt") ;
            r.close();
            Statement s2 = con.createStatement();
            ResultSet r1 = s2.executeQuery("select rating_num AS old from rating where  menu_id=11");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            double rate = rating11.getRating() ;
            preparedStatement.setDouble(1, (rate+old*compt)/(compt+1));
            preparedStatement.setInt(2,compt+1 );
            preparedStatement.setInt(3,i1 );
            preparedStatement.execute();
            Rate_Init11();
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
        public void update_Rating12(ActionEvent event) throws SQLException{
         PreparedStatement preparedStatement ;
         String query="update rating SET rating_num=? , compteur=?  where menu_id=?";


        try {
            Statement s = con.createStatement();
            int i1=12;
            preparedStatement =con.prepareStatement(query);
            ResultSet r = s.executeQuery("select compteur AS cpt from rating where  menu_id=12");
            r.next();
            int compt = r.getInt("cpt") ;
            r.close();
            Statement s2 = con.createStatement();
            ResultSet r1 = s2.executeQuery("select rating_num AS old from rating where  menu_id=12");
            r1.next();
            double old = r1.getDouble("old") ;
            r1.close();
            double rate = rating12.getRating() ;
            preparedStatement.setDouble(1, (rate+old*compt)/(compt+1));
            preparedStatement.setInt(2,compt+1 );
            preparedStatement.setInt(3,i1 );
            preparedStatement.execute();
              Rate_Init12();
        } catch (SQLException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
           public void ReadQR(ActionEvent event) throws IOException, NotFoundException, FormatException, ChecksumException{

  FileChooser fc = new FileChooser () ;
   fc.setTitle("Open Resource File");

  fc.getExtensionFilters().addAll(
         new ExtensionFilter("Png Files", "*.png"));
 File file = fc.showOpenDialog(primaryStage) ;

       BufferedImage barcBufferedImage = ImageIO.read(file);
       LuminanceSource source = new BufferedImageLuminanceSource(barcBufferedImage) ;
  BinaryBitmap bitmap  = new BinaryBitmap (new HybridBinarizer(source)) ;
   Reader reader = new MultiFormatReader();
   Result result = reader.decode(bitmap);
   if (result.getText().startsWith("Ilyes")){ 
      QR_Read.setText("Valid Code");
      calculate() ;
   }else {
        QR_Read.setText("Invalid Code");
      calculate() ;
   }
           }
}
