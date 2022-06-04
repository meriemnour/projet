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
     //   refreshlist();
        comborole.getItems().setAll(Role.values());
        recherche_avance();
        //fillforum();
        refreshlist();
        
       
    }    

    @FXML
    private void ajouter(ActionEvent event) {
        StringBuilder errors=new StringBuilder();
        //.trim ==> tna7ili les espace khater ken fama espace twali ma aadech empty hors lezem tkoun empty donc naamlou trim
        //.append ==> ken par exemple ma ktebtech adresse w email donc ijibli ken lekhrenya donc .append yaamle aala l lkoll 
        //kima une list kol ma nektebech tzid'ha f lista w baeed yaffichili ili ma ktebtech fih
        if(tfaddress.getText().trim().isEmpty()){
            errors.append("Please enter an cin\n");
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
            u.setCin_u(Integer.parseInt(tfaddress.getText()));
            u.setNom_u(tfnom.getText());
            u.setPrenom_u(tfprenom.getText());
            u.setDate_naissance(Date.valueOf(dpdate.getValue()));
            u.setEmail_u(tfemail.getText());
            u.setNum_tel(Integer.parseInt(tfnumtel.getText()));
            u.setRole(comborole.getValue());
            //u.setUsername(tfusername.getText());
            u.setMot_de_passe(pfpassword.getText());
            //u.setStatus(checkboxapp.isSelected());
            us.ajouter(u);
            refreshlist();
        }
    }

    @FXML
    private void modifier(ActionEvent event) {
       User u=new User();
            u.setCin_u(Integer.parseInt(tfaddress.getText()));
            u.setNom_u(tfnom.getText());
            u.setPrenom_u(tfprenom.getText());
            u.setDate_naissance(Date.valueOf(dpdate.getValue()));
            u.setEmail_u(tfemail.getText());
            u.setNum_tel(Integer.parseInt(tfnumtel.getText()));
            u.setRole(comborole.getValue());
            //u.setUsername(tfusername.getText());
            u.setMot_de_passe(pfpassword.getText());
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
        data =FXCollections.observableArrayList(us.afficher());
        listviewuser.setItems(data);
    }
    @FXML
    public void fillforum(){
        User u=listviewuser.getSelectionModel().getSelectedItem();
        if(u!=null){
            tfaddress.setText(String.valueOf(u.getCin_u()));
            tfnom.setText(u.getNom_u());
            tfprenom.setText(u.getPrenom_u());
            tfemail.setText(u.getEmail_u());
            tfnumtel.setText(String.valueOf(u.getNum_tel()));
            comborole.setValue(u.getRole());
            //tfusername.setText(u.getUsername());
            pfpassword.setText(u.getMot_de_passe());
           //checkboxapp.setSelected(u.isStatus());
            Instant instant = Instant.ofEpochMilli(u.getDate_naissance().getTime());
            LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
            dpdate.setValue(ldt.toLocalDate());
        }
        
        
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
                        if(user.getNom_u().toLowerCase().indexOf(lowercasenewvalue)!=-1){
                            return true;
                        }
                        else if(user.getPrenom_u().toLowerCase().indexOf(lowercasenewvalue)!=-1){
                            return true;
                        }

                        else if(String.valueOf(user.getCin_u()).toLowerCase().indexOf(lowercasenewvalue)!=-1){
                            return true;
                        }

                        else if(user.getEmail_u().toLowerCase().indexOf(lowercasenewvalue)!=-1){
                            return true;
                        }

                        else if(user.getMot_de_passe().toLowerCase().indexOf(lowercasenewvalue)!=-1){
                            return true;
                        }

                        /*else if(user.getAdresse().toLowerCase().indexOf(lowercasenewvalue)!=-1){
                            return true;
                        } */
                        
                        else if(String.valueOf(user.getNum_tel()).toLowerCase().indexOf(lowercasenewvalue)!=-1){
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
    
    
   