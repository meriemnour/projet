/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

/**
 * FXML Controller class
 *
 * @author Ilyes
 */
public class StatController implements Initializable {
    @FXML
    private PieChart pieChart ;
    Connection con; //connection for table 

    
    public StatController(){
        con=SqlConnection.Connector();
    }
    /**
     * Initializes the controller class.
     */
    
    
     //CONNECTION DATABASE SAVING DATA
    
  
    
 
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
             
        
        
        try {
            con=SqlConnection.Connector();
            Statement s = con.createStatement();           
            Statement s2 = con.createStatement();

            ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM payment where payment_type='CASH_ON_DELIVERY'");
            ResultSet r2 = s2.executeQuery("SELECT COUNT(*) AS rowcount2 FROM payment");

            r.next();
            int count = r.getInt("rowcount") ;
        r.close();
            
              r2.next();
            int count2 = r2.getInt("rowcount2") ;
           r2.close();
            int count3 = count2 - count;
            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data("Paiement En Ligne", count3),
                            new PieChart.Data("Paiement a dommicile", count2)
                    );
            pieChart.setData(pieChartData);
            pieChart.setTitle("Methode de Paiement");
        } catch (SQLException ex) {
            Logger.getLogger(StatController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}
