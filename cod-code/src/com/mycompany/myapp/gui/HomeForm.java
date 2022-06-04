/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.gui.ProfileForm;

/**
 *
 * @author RoKy-Dev
 */
public class HomeForm extends Form {

    Form current;
    Resources res;

    public HomeForm(Resources res) {
        current = this; //Back
        setTitle("Home");
        setLayout(BoxLayout.y());

        current.add(new Label("Choose an option"));
        Button btnAddCoach = new Button("Add Menu");
        Button btnListCoach = new Button("List Menu");
        Button btnstat = new Button("stat Menu");
        Button btnrt = new Button("Profile");

       

        btnAddCoach.addActionListener(e -> new AddMenuForm(current).show());
        btnListCoach.addActionListener(e -> new ListMenusForm(current).show());
        btnrt.addActionListener(e -> new ProfileForm(res).show());
        current.addAll(btnAddCoach, btnListCoach,
                btnstat, btnrt);

        current.getToolbar().addCommandToSideMenu("menu", null, (e) -> current.show());

    }

}
