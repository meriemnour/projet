/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.GUI;

import delivery.entity.Role;
import delivery.entity.User;
import delivery.service.UserService;
import java.net.URL;
import java.sql.Date;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class SignUpController implements Initializable {

    @FXML
    private Button signinbutton;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfemail;
    @FXML
    private TextField tfusername;
    @FXML
    private TextField tfadresse;
    @FXML
    private TextField tftel;
    @FXML
    private DatePicker dpdate;
    @FXML
    private PasswordField pfpassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

        UserService us=new UserService();
    ZoneId defaultZoneId = ZoneId.systemDefault();
    @FXML
    private void signin(ActionEvent event) {
              StringBuilder errors=new StringBuilder();
        //.trim ==> tna7ili les espace khater ken fama espace twali ma aadech empty hors lezem tkoun empty donc naamlou trim
        //.append ==> ken par exemple ma ktebtech adresse w email donc ijibli ken lekhrenya donc .append yaamle aala l lkoll 
        //kima une list kol ma nektebech tzid'ha f lista w baeed yaffichili ili ma ktebtech fih
        if(tfadresse.getText().trim().isEmpty()){
            errors.append("Please enter an adress\n");
        }
        if(tfemail.getText().trim().isEmpty()){
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
        if(tftel.getText().trim().isEmpty()){
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
            u.setAdresse(tfadresse.getText());
            u.setEmail(tfemail.getText());
            u.setPassword(pfpassword.getText());
            u.setNumTel(Integer.parseInt(tftel.getText()));
            u.setDate_naissance(Date.valueOf(dpdate.getValue()));
            u.setRole(Role.CLIENT);
            us.ajouter(u);
        }  
    }
    
}
