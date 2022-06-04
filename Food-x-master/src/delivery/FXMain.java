/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.GroupBuilder;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author asus
 */
public class FXMain extends Application {
    
    @Override
    public void start(Stage stage)throws Exception {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/delivery/GUI/Essai.fxml"));
            Scene scene= new Scene(root);
            stage.setTitle("Login!");
            stage.setScene(scene);
            stage.show();
            /* final Parent root = GroupBuilder.create().children(outerRim(),minuteHand(),hourHand(),secondsHand(),tickMarks(),centerPoint()).build();;
              setUpMouseForScaleAndMove(stage, root);
              Scene scene = makeATransparentScene(root);
              makeATransparentStage(stage, scene);*/
           
        } catch (IOException ex) {
            Logger.getLogger(FXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
}
