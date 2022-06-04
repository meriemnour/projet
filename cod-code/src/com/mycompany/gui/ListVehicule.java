/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.mycompany.entities.Produit;
import com.mycompany.services.ServiceProduit;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.gui.BaseForm;
import com.mycompany.gui.NewsfeedForm;

/**
 *
 * @author RoKy-Dev
 */
public class ListVehicule extends BaseForm {

    Form current;
    Resources res;

    public ListVehicule(Resources res) {
        Button btnret = new Button("Back");

        btnret.addActionListener(e -> new NewsfeedForm(res).show());
        add(btnret);
    }

    public ListVehicule(Form previous, Resources res) {
        setTitle("List Vehicule");

        Container List = new Container(BoxLayout.y());

        for (Produit p : ServiceProduit.getInstance().affichdess()) {
            MultiButton mb = new MultiButton(p.toString());
            //System.out.println(user.getId());
            Button update = new Button("update");
            update.setUIID("update");
            update.addActionListener(e -> new ModifierProduit(p, previous, res).show());
            Button delete = new Button("delete");
            delete.setUIID("delete");
            delete.addActionListener(e -> new DeleteProduit(p, previous).show());
            Button btnret = new Button("Back");

            btnret.addActionListener(e -> new NewsfeedForm(res).show());
            add(mb);
            add(delete);
            add(update);
            add(btnret);

        }

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

}
