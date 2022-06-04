/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

/**
 *
 * @author RoKy-Dev
 */
import com.mycompany.entities.Produit;
import com.mycompany.services.ServiceProduit;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author bhk
 */
public class AddProduitForm extends Form {


    public AddProduitForm(Form previous, Resources res) {
        /*
        Le paramètre previous définit l'interface(Form) précédente.
        Quelque soit l'interface faisant appel à AddTask, on peut y revenir
        en utilisant le bouton back
         */
        setTitle("Add a new Vehicule");
        setLayout(BoxLayout.y());
        TextField tfNumchassis = new TextField("", "numchassis");
        TextField tfypevehicule = new TextField("", "type Vehicule");
        TextField tfnumimmat = new TextField("", "Immatricule");

        Button btnValider = new Button("Add Vehicule");

        btnValider.addActionListener((ActionEvent evt) -> {

            if ((tfNumchassis.getText().length() == 0) || (tfypevehicule.getText().length() == 0) || (tfnumimmat.getText().length() == 0)) {
                Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
            } else {
                try {
                    Produit t = new Produit(tfNumchassis.getText().toString(), tfypevehicule.getText().toString(), tfnumimmat.getText().toString());
                    if (ServiceProduit.getInstance().ajouterProduit(t)) {
                        Dialog.show("Success", "Connection accepted", new Command("OK"));
                    } else {
                        Dialog.show("ERROR", "Server error", new Command("OK"));
                    }
                } catch (NumberFormatException e) {
                    Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                }

            }

        });
        Button Back = new Button("Back");
        Back.addActionListener(e -> new ListVehicule(previous, res).show());

        addAll(tfNumchassis, tfypevehicule, tfnumimmat, btnValider, Back);
        //getToolbar().addMaterialCommandToLeftBar("Back", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }

}
