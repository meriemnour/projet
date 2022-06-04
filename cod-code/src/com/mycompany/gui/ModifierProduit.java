/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.mycompany.entities.Produit;
import com.mycompany.services.ServiceProduit;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author RoKy-Dev
 */
public class ModifierProduit extends Form {

    Form current;
    Resources res;

    public ModifierProduit(Produit p, Form previous, Resources res) {
        setTitle("edit product");
        setLayout(BoxLayout.y());
        TextField id = new TextField(String.valueOf(p.getId_vehicule()), "Vehicule id");
        TextField tfNumch = new TextField(p.getNum_chassis(), "Num chassis");
        TextField tfNumim = new TextField(p.getNum_immatriculation(), "Num Immatricule");
        TextField tfTypev = new TextField(p.getType_vehicule(), "Type vehicule");

        Button btnValider = new Button("edit product");
        Button btnRet = new Button("Return");
        btnRet.addActionListener(e -> new ListVehicule(previous, res).show());

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfNumch.getText().length() == 0) && (tfNumim.getText().length() == 0) && (tfTypev.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {
                        Produit p = new Produit(Integer.parseInt(id.getText()), tfNumch.getText(), tfNumim.getText(), tfTypev.getText());
                        System.out.println(p.getId_vehicule());
                        System.out.println("---------");
                        System.out.println(id.getText());
                        if (ServiceProduit.getInstance().modifierProduct(p)) {
                            Dialog.show("Success", "Connection accepted", new Command("OK"));
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }

                }

            }

        });

        addAll(tfNumch, tfNumim, tfTypev, btnValider, btnRet);
        // getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> this.previous.showBack());

    }

}
