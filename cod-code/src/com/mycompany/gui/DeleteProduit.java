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
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;

/**
 *
 * @author RoKy-Dev
 */
public class DeleteProduit extends Form {

    Resources res;

    public DeleteProduit(Produit p, Form previous) {
        setTitle("delete Product");

        Button btnSubmit = new Button("Delete");
        Button btnret = new Button("return");

        btnret.addActionListener(e -> new ListVehicule(previous, res).show());

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dialog.show("Alert", "Are you sure !!", new Command("OK"));

                System.out.println(p.getId_vehicule());
                System.out.println("deleted Product");

                if (ServiceProduit.getInstance().Delete(p)) {
                    Dialog.show("Success", "Connection Accepted", new Command("OK"));
                } else {
                    Dialog.show("ERROR", "Connection Failed", new Command("OK"));
                }

            }

        });

        addAll(btnSubmit, btnret);
        //  this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

}
