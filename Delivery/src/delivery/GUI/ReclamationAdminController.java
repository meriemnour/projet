/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.GUI;


import delivery.entity.BonPlan;
import delivery.entity.Reclamation;
import delivery.service.MyListener;
import delivery.service.ReclamationService;
import delivery.utils.MailApi;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Mortadha
 */
public class ReclamationAdminController implements Initializable {

    @FXML
    private GridPane grid;
    @FXML
    private Label idgetter;
    ReclamationService rs=new ReclamationService();
    //UserService us=new UserService()
    @FXML
    private TextField tfobjet;
    @FXML
    private TextArea tfmessage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        refresh();
    }    

    @FXML
    private void repondre(ActionEvent event) {
        Reclamation r=rs.SearchById(Integer.parseInt(idgetter.getText()));
        //User u=us.findById(r.getId_author());
        r.setDate_traitement(LocalDateTime.now());
        r.setStatus(0);
        rs.modifier(r.getId_reclamation(), r);
        MailApi m=new MailApi();
        m.SendMail("meriemnour.ghozzi@esprit.tn", tfobjet.getText(), tfmessage.getText());
        TrayNotification tray=new TrayNotification();
            tray.setAnimationType(AnimationType.POPUP);
            tray.setTitle("Envoye du reponse avec succes");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(2000));
        refresh();
        
    }
    MyListener myListener;
    public void selectedReclamation(Reclamation r){
        idgetter.setText(String.valueOf(r.getId_reclamation()));
         System.out.println(idgetter.getText());
        
    }

    public void refresh(){
    
          

         List<Reclamation> reclamations = rs.afficher();
          
         
         if(reclamations.size() > 0){
          selectedReclamation(reclamations.get(0));
          myListener = new MyListener() {
              @Override
              public void onClickListener(BonPlan bp) {
                  
              }

              @Override
              public void onClickListener2(Reclamation r) {
                  selectedReclamation(r);
              }
              
         };
                  }
        int column = 0;
        int row = 1;
        
        try {
            for (int i = 0; i < reclamations.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/delivery/GUI/AfficheReclamation.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                AfficheReclamationController itemController = fxmlLoader.getController();
                itemController.setData(reclamations.get(i),myListener);

                if (column == 1) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
}
    
}
