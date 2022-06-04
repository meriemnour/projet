/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.GUI;


import delivery.entity.BonPlan;
import delivery.service.MyListener;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Mortadha
 */
public class AffichebonplanController implements Initializable {

    @FXML
    private Label llibelle;
    @FXML
    private Label ldescription;
    @FXML
    private Label lprix;
    @FXML
    private Label ldateouv;
    @FXML
    private Label ldatefermuture;
    @FXML
    private Label lreact;
    @FXML
    private ImageView imageview;
    private BonPlan bp;
    private MyListener myListener;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    public void setData(BonPlan bp,MyListener myListener){
        this.bp=bp;
         this.myListener=myListener;
        ldatefermuture.setText(String.valueOf(bp.getFermuture()));
        ldateouv.setText(String.valueOf(bp.getOuverture()));
        ldescription.setText(bp.getDescription());
        llibelle.setText(bp.getLibelle());
        lprix.setText(String.valueOf(bp.getPrix()));
        lreact.setText(String.valueOf(bp.getNbrreact()));
        File f = new File("" + bp.getImage());
         Image img1 = new Image(f.toURI().toString());
         imageview.setImage(img1);
        
    }

    @FXML
    private void selectedbp(MouseEvent event) {
        myListener.onClickListener(bp);
    }
    
}
