/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.entities.Menu;
import com.mycompany.services.ServiceTask;

/**
 *
 * @author RoKy-Dev
 */
public class ModifyMenu extends Form {

    public ModifyMenu(Form previous, Menu menu1) {
        setTitle("Modify a Menu");
        setLayout(BoxLayout.y());
        Label lbid = new Label(menu1.idtoString());
        TextField tfname = new TextField("", "name");
        TextField tfCategory = new TextField("", "category");
        TextField tfdescription = new TextField("", "description");
        TextField tfprice = new TextField("", "price");

        tfname.setText(menu1.getName());
        tfCategory.setText(menu1.getCategory());
        tfdescription.setText(menu1.getDescription());
        tfprice.setText(menu1.getPrice() + "");
        Button btnValider = new Button("Update");

        btnValider.addActionListener((evt) -> {

            if ((tfname.getText().length() == 0) || (tfCategory.getText().length() == 0) || (tfdescription.getText().length() == 0) || (tfprice.getText().length() == 0)) {
                Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
            } else {
                try {
                    Menu U = new Menu(Integer.parseInt(tfprice.getText()), tfname.getText().toString(), tfCategory.getText().toString(), tfdescription.getText().toString());
                    U.setName(tfname.getText().toString());
                    U.setCategory(tfCategory.getText().toString());
                    U.setDescription(tfdescription.getText().toString());
                    U.setPrice(Integer.parseInt(tfprice.getText()));
                    U.setId(menu1.getId());
                    if (ServiceTask.getInstance().ModifyCoach(U)) {
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
        addAll(lbid, tfname, tfCategory, tfdescription, tfprice, btnValider, Back);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }
}
