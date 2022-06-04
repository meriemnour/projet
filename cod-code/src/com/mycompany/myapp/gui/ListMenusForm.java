/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Menu;
import com.mycompany.services.ServiceTask;
import java.util.ArrayList;
import com.mycompany.gui.ListVehicule;

/**
 *
 * @author RoKy-Dev
 */
public class ListMenusForm extends Form {

    Form current;
    Resources res;

    public Container addItem(Menu u) {
        current = this;
        Container cnt = new Container(BoxLayout.y());
        Label lbid = new Label(u.idtoString());
        Label lbnom = new Label(u.getName());
        Label lblastname = new Label(u.getCategory());
        Label lbcategorie = new Label(u.getDescription());
        Label lbrank = new Label(u.getPrice() + "");
        Button btdelete = new Button("Delete");
        Button btnmodify = new Button("Modify");
        Button btnR = new Button("Commander");
        Button Back = new Button("Back");
        Back.addActionListener(e -> new ListVehicule(current, res).show());
        cnt.addAll(lbid, lbnom, lblastname, lbcategorie, lbrank, btdelete, btnmodify, btnR, Back);
        Container cnt2 = new Container(BoxLayout.x());

        cnt2.addAll(cnt);
        btnmodify.addActionListener(e -> new ModifyMenu(current, u).show());
        // btnR.addActionListener(e-> new AddReservation(current,u).show());

        btdelete.addActionListener((evt) -> {

            try {
                if (ServiceTask.getInstance().deleteMenu(u)) {
                    Dialog.show("Success", "Connection accepted", new Command("OK"));
                    ArrayList<Menu> list = ServiceTask.getInstance().getAllMenu();

                } else {
                    Dialog.show("ERROR", "Server error", new Command("OK"));
                }
            } catch (NumberFormatException e) {
                Dialog.show("ERROR", "Status must be a number", new Command("OK"));
            }

        }
        );
        return cnt2;
    }

    public ListMenusForm(Form previous) {
        setTitle("List Users");
        setLayout(BoxLayout.y());
        ArrayList<Menu> list = ServiceTask.getInstance().getAllMenu();
        SpanLabel sp = new SpanLabel();

        for (Menu user : list) {
            add(addItem(user));
        }
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

}
