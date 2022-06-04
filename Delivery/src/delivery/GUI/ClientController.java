/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.GUI;


import delivery.entity.BonPlan;
import delivery.entity.Reclamation;
import delivery.service.BonPlanService;
import delivery.service.MyListener;
import delivery.service.ReclamationService;
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
import javafx.scene.control.Alert;
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
public class ClientController implements Initializable {

    @FXML
    private GridPane grid;
    @FXML
    private TextField tfobjet;
    @FXML
    private TextArea tfdescription;
    
    BonPlanService bp=new BonPlanService();
    ReclamationService rs=new ReclamationService();
    @FXML
    private Label idgetter;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        refresh();
    }    

    @FXML
    private void reclamation(ActionEvent event) {
        String erreurs="";
        if(tfdescription.getText().trim().isEmpty()){
            erreurs+="description vide";
        }
        if(tfobjet.getText().trim().isEmpty()){
            erreurs+="objet vide";
        }
        if(erreurs.length()>0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur ajout reclamation");
            alert.setContentText(erreurs);
            alert.showAndWait();
        }
        rs.ajouter(new Reclamation(1, Integer.parseInt(idgetter.getText()), LocalDateTime.now(), tfdescription.getText(), tfobjet.getText(), 0));
        TrayNotification tray=new TrayNotification();
            tray.setAnimationType(AnimationType.POPUP);
            tray.setTitle("Envoye du reclamation avec success");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(2000));
    }

    @FXML
    private void like(ActionEvent event) {
        BonPlan b=bp.SearchById(Integer.parseInt(idgetter.getText()));
        b.setNbrreact(b.getNbrreact()+1);
        bp.modifier(b.getId_bonplan(), b);
        TrayNotification tray=new TrayNotification();
            tray.setAnimationType(AnimationType.POPUP);
            tray.setTitle("Like du bonplan "+idgetter+" avec succes");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(2000));
        refresh();
        
    }
    MyListener myListener;
    public void selectedBonPlan(BonPlan bp){
        idgetter.setText(String.valueOf(bp.getId_bonplan()));
         System.out.println(idgetter.getText());
        
    }

    public void refresh(){
    
          

         List<BonPlan> bonplans = bp.afficher();
          
         
         if(bonplans.size() > 0){
          selectedBonPlan(bonplans.get(0));
          myListener = new MyListener() {
              @Override
              public void onClickListener(BonPlan bp) {
                  selectedBonPlan(bp);
              }

              @Override
              public void onClickListener2(Reclamation r) {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }
              
         };
                  }
        int column = 0;
        int row = 1;
        
        try {
            for (int i = 0; i < bonplans.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/delivery/GUI/Affichebonplan.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                AffichebonplanController itemController = fxmlLoader.getController();
                itemController.setData(bonplans.get(i),myListener);

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
