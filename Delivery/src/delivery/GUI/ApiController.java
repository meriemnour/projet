/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  delivery.GUI;;

import com.jfoenix.controls.JFXTextField;
import static delivery.GUI.AdminPanelController.infoBox1;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 * FXML Controller class
 *
 * @author Ilyes
 */

public class ApiController implements Initializable {
    @FXML
    private JFXTextField qrtext;
@FXML 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
       public  void btn1(ActionEvent event){
try{
ByteArrayOutputStream out = QRCode.from("Ilyes"+qrtext.getText()).to(ImageType.PNG).stream() ;
String f_name = qrtext.getText() ;
String Path_name = "D:\\";
FileOutputStream fout = new FileOutputStream (new File(Path_name + (f_name + ".PNG" )));
fout.write(out.toByteArray());
fout.flush();
}
catch (Exception e) {
               infoBox1("Error", null, "Error");


}
       }
      
}
