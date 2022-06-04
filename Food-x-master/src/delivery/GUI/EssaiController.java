/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.GUI;

import delivery.entity.Role;
import delivery.entity.User;
import delivery.service.UserService;
import delivery.utils.MailApi;
import delivery.utils.SmsApi;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCrypt;


/**
 * FXML Controller class
 *
 * @author asus
 */
public class EssaiController implements Initializable {

    @FXML
    private AnchorPane pane_login;
    @FXML
    private TextField tfemail;
    @FXML
    private PasswordField pfpassword;
    @FXML
    private Button button_login;
    @FXML
    private AnchorPane pane_signup;
    @FXML
    private TextField tflastname;
    @FXML
    private TextField tfemail_signup;
    @FXML
    private TextField tfusername_signup;
    @FXML
    private TextField pfpassword_signup;
    @FXML
    private TextField tfname;
    @FXML
    private TextField tftel;
    @FXML
    private DatePicker dpdate;
    @FXML
    private TextField tfadresse;
    @FXML
    private Label labelclock;
    @FXML
    private Button tfforgot;

    private volatile boolean stop = false;
      private void Timenow() {
        Thread thread = new Thread (() -> {
         SimpleDateFormat sdf = new SimpleDateFormat ("hh:mm:ss a");
         while (!stop) {
             try {
                 Thread.sleep(1000);
         } catch(Exception e) {
             System.out.println(e);
         }
             final String timenow = sdf.format(new java.util.Date());
             Platform.runLater(() -> {
             labelclock.setText(timenow); 
             } );
         }
        }
        );
        thread.start();
    }

                
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //clock();
        Timenow();
        /*        System.out.println("captcha numbers:");
        for(int i=0;i<10;i++){
        System.out.println(createCaptchaValue());
        }*/
        String generatedCapcha=createCaptchaValue();
        System.out.println("captcha: "+createCaptchaValue());
        Scanner sc=new Scanner(System.in);
        System.out.println("Verify captcha: ");
        String verification =sc.next();
        if(generatedCapcha.equals(verification)){
              System.out.println("true ");
        }else{
              System.out.println("false ");
        }
        sc.close();
    }    


    public static String createCaptchaValue(){
       Random random=new Random();
       int length= 7+(Math.abs(random.nextInt())%3);
       StringBuilder captchaBuffer= new StringBuilder();
       for(int i=0;i<length;i++){
           int baseCharNumber=Math.abs(random.nextInt())%62;
           int charNumber=0;
           if(baseCharNumber<26){
               charNumber=65+baseCharNumber;
           }
           else
               if(baseCharNumber<52){
                   charNumber=97+(baseCharNumber-26);
               }
               else{
                   charNumber=48+(baseCharNumber-52);
               }
           captchaBuffer.append((char)charNumber);
       }
       return captchaBuffer.toString();
    }
    


    
    
    
    
    UserService us =new UserService();
    @FXML
    private void login(ActionEvent event) {
        String hashPassword = BCrypt.hashpw(pfpassword.getText(),BCrypt.gensalt(13));
        boolean resultat= us.checklogin(tfemail.getText());
        if (resultat==true){
            System.out.println("login succes");
            if(us.approvedlogin(tfemail.getText())){
                if(us.findByEmail(tfemail.getText()).getRole().equals(Role.ROLE_USER)){
                    System.out.println("interface client");
                }
                else if(us.findByEmail(tfemail.getText()).getRole().equals(Role.ROLE_ADMIN)){
                    try {
                        Parent root=FXMLLoader.load(getClass().getResource("/delivery/GUI/FXMLGSTUser.fxml"));
                        Scene scene = new Scene(root);
                        // aandi interface 9dim a w bech n7el interface jdida w nsaker l 9dima donc nestaamel node
                        Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
                        stage.setTitle("Chef");
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(LoginFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(us.findByEmail(tfemail.getText()).getRole().equals(Role.ROLE_RESPONSABLE)){
              try {
                        Parent root=FXMLLoader.load(getClass().getResource("/delivery/GUI/AdminPanel.fxml"));
                        Scene scene = new Scene(root);
                        // aandi interface 9dima w bech n7el interface jdida w nsaker l 9dima donc nestaamel node
                        Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
                        stage.setTitle("Forgot Password!!");
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(LoginFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(us.findByEmail(tfemail.getText()).getRole().equals(Role.ROLE_LIVREUR)){
                    System.out.println("interface collaborateur");
                }
                else{
                    System.out.println("interface livreur");
                }
           }
            else{
                StringBuilder errors=new StringBuilder();
                errors.append("Wait for admin approval\n");
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText(errors.toString());
                alert.showAndWait();
            }
            

        }else {
            StringBuilder errors=new StringBuilder();
                errors.append("Invalid email or password\n");
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText(errors.toString());
                alert.showAndWait();
        }
    }
    @FXML
    private void LoginpaneShow(ActionEvent event) {
        pane_login.setVisible(true);
        pane_signup.setVisible(false);
    }

    @FXML
    private void SignupaneShow(ActionEvent event) {
        pane_login.setVisible(false);
        pane_signup.setVisible(true);
    }
    

    
    public String email="^[a-zA-Z0-9]+[@]+[a-zA-Z0-9]+[.]+[a-zA-Z0-9]+$";
    public String numTel="^[0-9]*$";
    
    ZoneId defaultZoneId = ZoneId.systemDefault();
    @FXML
    private void signin(ActionEvent event) {
         String hashPassword = BCrypt.hashpw(pfpassword_signup.getText(),BCrypt.gensalt(13));
        
        StringBuilder errors=new StringBuilder();
        //.trim ==> tna7ili les espace khater ken fama espace twali ma aadech empty hors lezem tkoun empty donc naamlou trim
        //.append ==> ken par exemple ma ktebtech adresse w email donc ijibli ken lekhrenya donc .append yaamle aala l lkoll 
        //kima une list kol ma nektebech tzid'ha f lista w baeed yaffichili ili ma ktebtech fih
        
        if(tfemail.getText().trim().isEmpty() ){
            errors.append("Please enter an email\n");
        }
        if(tfname.getText().trim().isEmpty()){
            errors.append("Please enter a lastname\n");
        }
        if(tflastname.getText().trim().isEmpty()){
            errors.append("Please enter a firstname\n");
        }
        if(tftel.getText().trim().isEmpty() ){
            errors.append("Please enter a phone number\n");
        }
        if(hashPassword.trim().isEmpty()){
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
            u.setCin_u(Integer.parseInt(tfadresse.getText()));
            u.setNom_u(tfname.getText());
            u.setPrenom_u(tflastname.getText());
            u.setEmail_u(tfemail.getText());
            u.setMot_de_passe(hashPassword);
            
          //  u.setEmail(tfemail.getText());
           
            u.setNum_tel(Integer.parseInt(tftel.getText()));
            u.setDate_naissance(Date.valueOf(dpdate.getValue()));
            u.setRole(Role.ROLE_USER);
            us.signin(u);
            //MailApi m=new MailApi();
            //m.SendMail(u.getEmail_u(), "Bienvenue", "Sign in");
            //SmsApi s=new SmsApi();
            //  s.sendSMS("+21652778549","bienvenue");
            //s.sendSMS("+216"+String.valueOf(u.getNum_tel()), "bienvenue");
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Wait for admin approval\n");
            alert.showAndWait();
        }
        pane_login.setVisible(true);
        pane_signup.setVisible(false);
        
    
    
    }

    @FXML
    private void forgotpassword(ActionEvent event) {
                try {
            Parent root=FXMLLoader.load(getClass().getResource("/delivery/GUI/ForgotPassword.fxml"));
            Scene scene = new Scene(root);
            // aandi interface 9dima w bech n7el interface jdida w nsaker l 9dima donc nestaamel node
            Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Forgot Password!!");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
