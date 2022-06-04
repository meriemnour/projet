/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServiceUser;

/**
 * Signup UI
 *
 * @author Shai Almog
 */
public class SignUpForm extends BaseForm {

    public SignUpForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
        TextField cin_u = new TextField("", "cin", 20, TextField.ANY);                
        TextField nom_u = new TextField("", "nom", 20, TextField.ANY);
        TextField prenom_u = new TextField("", "prenom", 20, TextField.ANY);
        TextField date_naissance = new TextField("", "dateNaissance", 20, TextField.ANY);
        TextField email_u = new TextField("", "E-Mail", 20, TextField.EMAILADDR);
        TextField num_tel = new TextField("", "numtel", 20, TextField.ANY);
        TextField role = new TextField("", "role", 20, TextField.ANY);        
        TextField mot_de_passe = new TextField("", "Password", 20, TextField.PASSWORD);

       
        email_u.setSingleLineTextArea(false);
        mot_de_passe.setSingleLineTextArea(false);
       
        Button next = new Button("Next");
        Button signIn = new Button("Sign In");
        signIn.addActionListener(e -> previous.showBack());
        signIn.setUIID("Link");
        Label alreadHaveAnAccount = new Label("Already have an account?");
        
        Container content = BoxLayout.encloseY(
                new Label("Sign Up", "LogoLabel"),
                createLineSeparator(),
                new FloatingHint(cin_u),
                createLineSeparator(),
                new FloatingHint(nom_u),
                createLineSeparator(),
                new FloatingHint(prenom_u),
                createLineSeparator(),
                new FloatingHint(date_naissance),
                createLineSeparator(),
                new FloatingHint(email_u),
                createLineSeparator(),
                new FloatingHint(num_tel),
                createLineSeparator(),
                new FloatingHint(role),
                createLineSeparator(),
                new FloatingHint(mot_de_passe)
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        ));
        next.requestFocus();
        next.addActionListener(e -> {
ServiceUser.getInstance().signup( cin_u,nom_u,  prenom_u, date_naissance, email_u, num_tel,mot_de_passe, res);
Dialog.show("success","account is saved","ok",null);
}
);
    }
    
}
