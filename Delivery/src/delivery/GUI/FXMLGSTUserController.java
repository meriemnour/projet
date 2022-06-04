/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.GUI;

import delivery.entity.Role;
import delivery.entity.User;
import delivery.service.UserService;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Filter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import static org.apache.http.client.methods.RequestBuilder.options;

/**
 * FXML Controller class
 *
 * @author Mortadha
 */
public class FXMLGSTUserController implements Initializable {
    //new Map class(options:Object);
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfusername;
    @FXML
    private TextField tfemail;
    @FXML
    private PasswordField pfpassword;
    @FXML
    private TextField tfaddress;
    @FXML
    private TextField tfnumtel;
    @FXML
    private ComboBox<Role> comborole;
    @FXML
    private DatePicker dpdate;
    UserService us=new UserService();
    @FXML
    private CheckBox checkboxapp;
    @FXML
    private ListView<User> listviewuser;
    ObservableList<User> data=FXCollections.observableArrayList();
    @FXML
    private TextField tfrecherche;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        refreshlist();
        comborole.getItems().setAll(Role.values());
        recherche_avance();
        //fillforum();
    }    

    @FXML
    private void ajouter(ActionEvent event) {
        StringBuilder errors=new StringBuilder();
        //.trim ==> tna7ili les espace khater ken fama espace twali ma aadech empty hors lezem tkoun empty donc naamlou trim
        //.append ==> ken par exemple ma ktebtech adresse w email donc ijibli ken lekhrenya donc .append yaamle aala l lkoll 
        //kima une list kol ma nektebech tzid'ha f lista w baeed yaffichili ili ma ktebtech fih
        if(tfaddress.getText().trim().isEmpty()){
            errors.append("Please enter an adress\n");
        }
        if(tfemail.getText().trim().isEmpty() ){
            errors.append("Please enter an email\n");
        }
        if(tfnom.getText().trim().isEmpty()){
            errors.append("Please enter a lastname\n");
        }
        if(tfprenom.getText().trim().isEmpty()){
            errors.append("Please enter a firstname\n");
        }
        if(tfusername.getText().trim().isEmpty()){
            errors.append("Please enter a username\n");
        }
        if(tfnumtel.getText().trim().isEmpty() ){
            errors.append("Please enter a phone number\n");
        }
        if(pfpassword.getText().trim().isEmpty()){
            errors.append("Please enter a password\n");
        }
        if(dpdate.getValue()==null){
            errors.append("Pleanse enter a birthday");
        }
        if(errors.length()>0){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(errors.toString());
            alert.showAndWait();
        }
        else{
            User u =new User();
            u.setNom(tfnom.getText());
            u.setPrenom(tfprenom.getText());
            u.setUsername(tfusername.getText());
            u.setAdresse(tfaddress.getText());
            u.setEmail(tfemail.getText());
            u.setPassword(pfpassword.getText());
            u.setNumTel(Integer.parseInt(tfnumtel.getText()));
            u.setDate_naissance(Date.valueOf(dpdate.getValue()));
            u.setRole(comborole.getValue());
            u.setStatus(checkboxapp.isSelected());
            us.ajouter(u);
            refreshlist();
        }
    }

    @FXML
    private void modifier(ActionEvent event) {
       User u=new User();
       u.setNom(tfnom.getText());
            u.setPrenom(tfprenom.getText());
            u.setUsername(tfusername.getText());
            u.setAdresse(tfaddress.getText());
            u.setEmail(tfemail.getText());
            u.setPassword(pfpassword.getText());
            u.setNumTel(Integer.parseInt(tfnumtel.getText()));
            u.setDate_naissance(Date.valueOf(dpdate.getValue()));
            u.setRole(comborole.getValue());
            u.setStatus(checkboxapp.isSelected());
            //System.out.println(checkboxapp.isSelected());
            us.modifier(listviewuser.getSelectionModel().getSelectedItem().getId(), u);
       refreshlist();
    }

    @FXML
    private void supprimer(ActionEvent event) {
        User u=listviewuser.getSelectionModel().getSelectedItem();
        us.supprimer(u.getId());
        refreshlist();
    }
    public void refreshlist(){
        data=FXCollections.observableArrayList(us.afficher());
        listviewuser.setItems(data);
    }
    @FXML
    public void fillforum(){
        User u=listviewuser.getSelectionModel().getSelectedItem();
        tfnom.setText(u.getNom());
        tfprenom.setText(u.getPrenom());
        tfusername.setText(u.getUsername());
        tfemail.setText(u.getEmail());
        pfpassword.setText(u.getPassword());
        tfaddress.setText(u.getAdresse());
        tfnumtel.setText(String.valueOf(u.getNumTel()));
        comborole.setValue(u.getRole());
        checkboxapp.setSelected(u.isStatus());
        Instant instant = Instant.ofEpochMilli(u.getDate_naissance().getTime());
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        dpdate.setValue(ldt.toLocalDate());
        
    }
    public void recherche_avance(){
        FilteredList<User> filteredlist=new FilteredList<>(data,b->true);
        tfrecherche.textProperty().addListener(
                (observable,oldValue,newValue)->{
                    filteredlist.setPredicate(user->{
                        if(newValue==null || newValue.isEmpty()){
                            return true;
                        }
                        String lowercasenewvalue=newValue.toLowerCase();
                        if(user.getNom().toLowerCase().indexOf(lowercasenewvalue)!=-1){
                            return true;
                        }
                        else if(user.getPrenom().toLowerCase().indexOf(lowercasenewvalue)!=-1){
                            return true;
                        }

                        else if(user.getUsername().toLowerCase().indexOf(lowercasenewvalue)!=-1){
                            return true;
                        }

                        else if(user.getEmail().toLowerCase().indexOf(lowercasenewvalue)!=-1){
                            return true;
                        }

                        else if(user.getPassword().toLowerCase().indexOf(lowercasenewvalue)!=-1){
                            return true;
                        }

                        else if(user.getAdresse().toLowerCase().indexOf(lowercasenewvalue)!=-1){
                            return true;
                        } 
                        
                        else if(String.valueOf(user.getNumTel()).toLowerCase().indexOf(lowercasenewvalue)!=-1){
                            return true;
                        }
                        
                        else if(user.getRole().toString().toLowerCase().indexOf(lowercasenewvalue)!=-1){
                            return true;
                        }
                        
                        else if(user.getDate_naissance().toString().toLowerCase().indexOf(lowercasenewvalue)!=-1){
                            return true;
                        }
                        else{
                            return false;
                        }
                        
                    });
                }
        );
        listviewuser.setItems(filteredlist);
    }
} 
    
    
    /* public void liste(){
    v1=new VBox();
    //ProduitService ps=new ProduitService() ;
    UserService us=new UserService();
    List<User> u=us.readAlladmin2(ch,filtre.getValue(),us);
    for(int i=0;i<u.size();i++){
    HBox h1=new HBox();
    File file = new File("C:\\Users\\ahmed amine\\Documents\\NetBeansProjects\\gameland2\\src\\GUI\\images\\"+u.get(i).getId());
    Image image = new Image(file.toURI().toString());
    ImageView iv=new ImageView(image);
    iv.setFitWidth(200);
    iv.setFitHeight(105);
    Button b=new Button();
    //b.setText("");
    //b.setPrefHeight(200);
    b.setPrefWidth(105);
    b.setId(String.valueOf(u.get(i).getId()));
    //b.setGraphic(iv);
    VBox v2=new VBox();
    v2.setPrefHeight(140);
    v2.setPrefWidth(122);
    Label l1=new Label();
    Label l2=new Label();
    Label l3=new Label();
    Label l4=new Label();
    Label l5=new Label();
    l1.setPrefHeight(30);
    l1.setPrefWidth(122);
    
    l2.setPrefHeight(30);
    l2.setPrefWidth(122);
    
    l3.setPrefHeight(80);
    l3.setPrefWidth(122);
    
    l4.setPrefHeight(30);
    l4.setPrefWidth(122);
    
    l5.setPrefHeight(30);
    l5.setPrefWidth(122);
    
    l1.setText(u.get(i).getNom());
    l2.setText(String.valueOf(u.get(i).getPrenom())+" DNT");
    l3.setText(u.get(i).getEmail());
    l4.setText(u.get(i).getPassword());
    l5.setText(u.get(i).getUsername());
    //v2.getChildren().addAll(l1,l2,l3,14,15);
    v2.getChildren().;
    v2.setStyle("-fx-padding:10px;");
    Button ajouter=new Button();
    ajouter.setText("Ajouter au panier");
    ajouter.setPrefHeight(100);
    ajouter.setPrefWidth(150);
    h1.getChildren().addAll(iv,v2,ajouter);
    h1.setStyle("-fx-padding-top:10px;");
    v1.getChildren().addAll(h1);
    }
    listD.setContent(v1);
    }
    
    
    
    }*/

