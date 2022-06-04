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
import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
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
import com.codename1.util.Base64;
import com.mycompany.services.ServiceUser;
import java.util.Map;
import java.util.Random;

/**
 * Account activation UI
 *
 * @author Shai Almog
 */
public class ActivateForm extends BaseForm {

    TextField email;

    public ActivateForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("IMGLogin");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("Activate");

        add(BorderLayout.NORTH,
                BoxLayout.encloseY(
                        new Label(res.getImage("smily.png"), "LogoLabel"),
                        new Label("We got Your Back!", "LogoLabel")
                )
        );

        email = new TextField("", "saisir votre numero", 20, TextField.ANY);
        email.setSingleLineTextArea(false);
        Button valider = new Button("valider");
    //    Label haveAnAccount = new Label("retour");
     //   Button signIn = new Button("Renouveler votre mdp");
     //   signIn.addActionListener(e -> previous.showBack());
    //    signIn.setUIID("CenterLink");
        Container content = BoxLayout.encloseY(
                new Label(res.getImage("oublier.jfif"), "CenterLabel"),
                new FloatingHint(email),
                createLineSeparator(),
                valider,
                FlowLayout.encloseCenter(/*haveAnAccount, signIn*/)
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        valider.requestFocus();
        valider.addActionListener(e -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDialog = ip.showInfiniteBlocking();
sendSms(res);
ipDialog.dispose();
Dialog.show("mot de passe","nous avons envoyé le mot de passe sur votre telephone");
new SignInForm(res).show();
refreshTheme();
        });
    }

    public void sendSms(Resources res) {
     String accountSID = "AC0910f591ea614fc16f7d056d273029fc";
String authToken = "d12f798cd9533c3c9c9ad9da34f97aa6";
String fromPhone = "+14758897095";

        String result = ServiceUser.getInstance().getPasswordbyPhone(email.getText().toString(), res);


 Response<Map> smsresult = Rest.post("https://api.twilio.com/2010-04-01/Accounts/" + accountSID + "/Messages.json").
                        queryParam("To", "+216"+email.getText().toString()).
                        queryParam("From", fromPhone).
                        queryParam("Body", "Welcome To ENERGYM "
                                + ". Your password is :!"+result).
                        header("Authorization", "Basic " + Base64.encodeNoNewline((accountSID + ":" + authToken).getBytes())).
                        getAsJsonMap();;



}
}
