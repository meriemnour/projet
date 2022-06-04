/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.GUI;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.dateTime;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import delivery.service.UserService;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class LoginFormController implements Initializable {
    @FXML

    private TextField tfusername;
    @FXML
    private PasswordField pfpassword;
    @FXML
    private Button buttonsignIn;
    @FXML
    private Button buttonsignup;
     @FXML
    private Label textclick;
       
            /* @FXML
        public void clock()   {
            Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dateTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
          clock.setCycleCount(Animation.INDEFINITE);
    clock.play();
}*/
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }    

    UserService us =new UserService();
    @FXML
    private void login(ActionEvent event) {
        boolean resultat= us.checklogin(tfusername.getText(),pfpassword.getText());
        if (resultat==true){
            System.out.println("login succes");
        }else {
            System.out.println("login succes");
        }
    }

    @FXML
    private void signup(ActionEvent event) {
               
        try {
            Parent root=FXMLLoader.load(getClass().getResource("/delivery/GUI/SignUp.fxml"));
            Scene scene = new Scene(root);
            // aandi interface 9dima w bech n7el interface jdida w nsaker l 9dima donc nestaamel node
            Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Signin");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        } 
}
    
    
