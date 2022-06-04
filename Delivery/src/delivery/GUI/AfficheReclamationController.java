/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.GUI;

import delivery.entity.Reclamation;
import delivery.service.MyListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Mortadha
 */
public class AfficheReclamationController implements Initializable {

    @FXML
    private Label lobjet;
    @FXML
    private Label ldescription;
    @FXML
    private Label ldate;
    @FXML
    private Label lstatus;
    private MyListener myListener;
    private Reclamation r;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setData(Reclamation r,MyListener myListener){
        this.r=r;
         this.myListener=myListener;
        lobjet.setText(r.getObjet());
        ldescription.setText(r.getDescription());
        ldate.setText(String.valueOf(r.getDate_creation()));
        lstatus.setText(String.valueOf(r.getStatus()));
        
    }
    @FXML
    private void selectedreclamation(MouseEvent event) {
         myListener.onClickListener2(r);
    }
    
}
