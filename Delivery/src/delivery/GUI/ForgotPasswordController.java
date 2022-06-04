/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.GUI;

import delivery.entity.User;
import delivery.service.UserService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class ForgotPasswordController implements Initializable {

    private String email="^[a-zA-Z0-9]+[@]+[a-zA-Z0-9]+[.]+[a-zA-Z0-9]+$";
    @FXML
    private TextField tfemail_tel;
    @FXML
    private PasswordField pfnew_password;
    @FXML
    private PasswordField pfconfirm;
    @FXML
    private Button btupdate;
    @FXML
    private Button btlogin;
    @FXML
    private Button btsearch;
    UserService us=new UserService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btupdate.setDisable(true);
    }    
     
    public void validatefields(){
        String Email =tfemail_tel.getText();
        String password=pfnew_password.getText();
        String confirmpassword=pfconfirm.getText();
         btupdate.setDisable(false);
         User u=us.findByEmailORUsername(Email);
        if(u!=null){
            btupdate.setDisable(true);
        }else  btupdate.setDisable(false);
    }
    @FXML
    private void update(ActionEvent event) {
        String EmailorUsername =tfemail_tel.getText();
        User u=us.findByEmailORUsername(EmailorUsername);
        if(pfnew_password.getText().equals(pfconfirm.getText())){
            u.setPassword(pfnew_password.getText());
            us.modifier(u.getId(), u);
            //Notification
            TrayNotification tray=new TrayNotification();
            tray.setAnimationType(AnimationType.POPUP);
            tray.setTitle("Modification du mot de passe avec succes");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(2000));
            
        }
        else{
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Verify your password \n");
            alert.showAndWait();
        }
    }

    @FXML
    private void btlogin(ActionEvent event) {
    }

    @FXML
    private void search(ActionEvent event) {
        String EmailorUsername =tfemail_tel.getText();
        User u=us.findByEmailORUsername(EmailorUsername);
        if(u!=null){
            System.out.println("true");
            btupdate.setDisable(false);
        }
        else{
            System.out.println("false");
            btupdate.setDisable(true);
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Invalid Username or Email\n");
            alert.showAndWait();
        }
        tfemail_tel.setDisable(true);
    }
    
}
