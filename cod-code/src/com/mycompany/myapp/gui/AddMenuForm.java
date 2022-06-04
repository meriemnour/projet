/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.entities.Menu;
import com.mycompany.services.ServiceTask;

/**
 *
 * @author RoKy-Dev
 */
public class AddMenuForm extends Form {

    public AddMenuForm(Form previous) {
        setTitle("Add a new Menu");
        setLayout(BoxLayout.y());

        TextField tfName = new TextField("", "name");
        TextField tfCategory = new TextField("", "category");
        TextField tfprice = new TextField("", "price");
        TextField tfdescription = new TextField("", "description");

        Button btnValider = new Button("Add Menu");

        btnValider.addActionListener((evt) -> {

            if ((tfName.getText().length() == 0) || (tfCategory.getText().length() == 0) || (tfdescription.getText().length() == 0) || (tfdescription.getText().length() == 0)) {
                Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
            } else {
                try {
                    Menu t = new Menu(Integer.parseInt(tfprice.getText()), tfName.getText().toString(), tfCategory.getText().toString(), tfdescription.getText().toString());
                    if (ServiceTask.getInstance().addMenu(t)) {
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
        Back.addActionListener(e -> previous.showBack());

        addAll(tfName, tfCategory, tfprice, tfdescription, btnValider, Back);
        //getToolbar().addMaterialCommandToLeftBar("Back", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }
}
