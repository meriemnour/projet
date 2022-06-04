/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.GUI;

import delivery.entity.BonPlan;
import delivery.service.BonPlanService;
import delivery.utils.Myconnexion;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Mortadha
 */
public class AjouterBonPlanController implements Initializable {

    @FXML
    private TextField tfrefcompte;
    @FXML
    private TextField tfrefcategorie;
    @FXML
    private ComboBox<Integer> comborestaurant;
    @FXML
    private TextField tflibellle;
    @FXML
    private TextField tfdescription;
    @FXML
    private TextField tfprix;
    @FXML
    private DatePicker dpouverture;
    @FXML
    private DatePicker dpfermuture;
    @FXML
    private TextField tfrecherche;
    @FXML
    private ListView<BonPlan> listviewbonplan;
    BonPlanService bs=new BonPlanService();
    @FXML
    private TextField tfimage;
    @FXML
    private AnchorPane anchorePane;
    ObservableList<BonPlan> data=FXCollections.observableArrayList();
    
    ObservableList<Integer> options=FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        refreshlist();
        //fillcombo();
        //recherche_avance();
    }    

    @FXML
    private void Ajouter(ActionEvent event) {
        String erreurs="";
        if(tfdescription.getText().trim().isEmpty()){
            erreurs+="description vide\n";
        }
        if(tfimage.getText().trim().isEmpty()){
            erreurs+="image vide\n";
        }
        if(tflibellle.getText().trim().isEmpty()){
            erreurs+="libelle vide\n";
        }
        if(tfprix.getText().trim().isEmpty()){
            erreurs+="prix vide\n";
        }
        if(tfrefcategorie.getText().trim().isEmpty()){
            erreurs+="ref categorie vide\n";
        }
        if(tfrefcompte.getText().trim().isEmpty()){
            erreurs+="ref compte vide\n";
        }
        if(dpouverture.getValue()==null){
            erreurs+="date ouverture vide\n";
        }
        if(dpfermuture.getValue()==null){
            erreurs+="date fermuture vide\n";
        }
        if(erreurs.length()>0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur ajout bon plan");
            alert.setContentText(erreurs);
            alert.showAndWait();
        }
        else{
            bs.ajouter(new BonPlan(
                Integer.parseInt(tfrefcompte.getText()),
                Integer.parseInt(tfrefcategorie.getText()),
                1,1 ,tflibellle.getText(),
                tfdescription.getText(),
                tfimage.getText(),
                Float.parseFloat(tfprix.getText()),
                dpouverture.getValue().atStartOfDay(),
                dpfermuture.getValue().atStartOfDay(),
                0));
            TrayNotification tray=new TrayNotification();
            tray.setAnimationType(AnimationType.POPUP);
            tray.setTitle("ajout du bonplan avec succes");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(2000));
            refreshlist();
        }
        
    }

    @FXML
    private void modifier(ActionEvent event) {
        String erreurs="";
        if(tfdescription.getText().trim().isEmpty()){
            erreurs+="description vide\n";
        }
        if(tfimage.getText().trim().isEmpty()){
            erreurs+="image vide\n";
        }
        if(tflibellle.getText().trim().isEmpty()){
            erreurs+="libelle vide\n";
        }
        if(tfprix.getText().trim().isEmpty()){
            erreurs+="prix vide\n";
        }
        if(tfrefcategorie.getText().trim().isEmpty()){
            erreurs+="ref categorie vide\n";
        }
        if(tfrefcompte.getText().trim().isEmpty()){
            erreurs+="ref compte vide\n";
        }
        if(dpouverture.getValue()==null){
            erreurs+="date ouverture vide\n";
        }
        if(dpfermuture.getValue()==null){
            erreurs+="date fermuture vide\n";
        }
        if(erreurs.length()>0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur ajout bon plan");
            alert.setContentText(erreurs);
            alert.showAndWait();
        }
        if(listviewbonplan.getSelectionModel().getSelectedItem()!=null){
            bs.modifier(listviewbonplan.getSelectionModel().getSelectedItem().getId_bonplan(), new BonPlan(
                Integer.parseInt(tfrefcompte.getText()),
                Integer.parseInt(tfrefcategorie.getText()),
                1,1 ,tflibellle.getText(),
                tfdescription.getText(),
                tfimage.getText(),
                Float.parseFloat(tfprix.getText()),
                dpouverture.getValue().atStartOfDay(),
                dpfermuture.getValue().atStartOfDay(),
                0));
            TrayNotification tray=new TrayNotification();
            tray.setAnimationType(AnimationType.POPUP);
            tray.setTitle("Modification du bonplan avec succes");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(2000));
            refreshlist();
        }
        
        
    }

    @FXML
    private void supprimer(ActionEvent event) {
        if(listviewbonplan.getSelectionModel().getSelectedItem()!=null){
            bs.supprimer(listviewbonplan.getSelectionModel().getSelectedItem().getId_bonplan());
            TrayNotification tray=new TrayNotification();
            tray.setAnimationType(AnimationType.POPUP);
            tray.setTitle("Supprission du bonplan avec succes");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(2000));
            refreshlist();
        
        }
        
    }

    @FXML
    private void upload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        Stage stage = (Stage)anchorePane.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            tfimage.setText(file.getName());
        }
    }
    public void refreshlist(){
        
        
        data=FXCollections.observableArrayList(bs.afficher());
        listviewbonplan.setItems(data);
        recherche_avance();
    }
    public void fillcombo(){
        try {
            Connection cnx = Myconnexion.getInstance().getCnx();
            String req = " select * from restaurant ";
            PreparedStatement cs = cnx.prepareStatement(req);
            ResultSet rs = cs.executeQuery(req);
            while(rs.next()){
                options.add(rs.getInt("id"));
                
            }
            comborestaurant.setItems(options);
        } catch (SQLException ex) {
            Logger.getLogger(AjouterBonPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public void recherche_avance(){
          FilteredList<BonPlan> filtereddata=new FilteredList<>(data,b->true);
        tfrecherche.textProperty().addListener((observable,oldvalue,newValue) -> {
            filtereddata.setPredicate(bp->{
                if(newValue==null||newValue.isEmpty()){
                    return true;
                }
                String lowercasefilter=newValue.toLowerCase();
                if(bp.getDescription().toLowerCase().indexOf(lowercasefilter)!=-1){
                    return true;
                }
                else if(bp.getLibelle().toLowerCase().indexOf(lowercasefilter)!=-1){
                    return true;
                }
                else if(bp.getImage().toLowerCase().indexOf(lowercasefilter)!=-1){
                    return true;
                }
                
                else if(String.valueOf(bp.getId_bonplan()).indexOf(lowercasefilter)!=-1){
                    return true;
                }
                else if(String.valueOf(bp.getIdrestaurant()).indexOf(lowercasefilter)!=-1){
                    return true;
                }
                else if(String.valueOf(bp.getPrix()).indexOf(lowercasefilter)!=-1){
                    return true;
                }
                else if(String.valueOf(bp.getStatus()).indexOf(lowercasefilter)!=-1){
                    return true;
                }
                else if(String.valueOf(bp.getRefcategorie()).indexOf(lowercasefilter)!=-1){
                    return true;
                }
                else if(String.valueOf(bp.getRef_compte()).indexOf(lowercasefilter)!=-1){
                    return true;
                }
                else{
                    return false;
                }
                
            });
        });
        
        
        listviewbonplan.setItems(filtereddata);
    }

    @FXML
    private void fillforum(MouseEvent event) {
        if(listviewbonplan.getSelectionModel().getSelectedItem()!=null){
            BonPlan b=listviewbonplan.getSelectionModel().getSelectedItem();
            tfdescription.setText(b.getDescription());
            tfimage.setText(b.getImage());
            tflibellle.setText(b.getLibelle());
            tfrefcompte.setText(String.valueOf(b.getRef_compte()));
            tfrefcategorie.setText(String.valueOf(b.getRefcategorie()));
            tfprix.setText(String.valueOf(b.getPrix()));
            dpouverture.setValue(b.getOuverture().toLocalDate());
            dpfermuture.setValue(b.getFermuture().toLocalDate());
            
        }
    }
    
}
