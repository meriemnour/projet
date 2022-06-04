/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.User;
import com.mycompany.gui.ProfileForm;
import com.mycompany.gui.SessionManager;
import com.mycompany.gui.SignInForm;
import com.mycompany.gui.WalkthruForm;

import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author RoKy-Dev
 */
public class ServiceUser {

    Form current;

    public ArrayList<User> User;

    public static ServiceUser instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    String json;

    private ServiceUser() {
        req = new ConnectionRequest();
    }

    public static ServiceUser getInstance() {
        if (instance == null) {
            instance = new ServiceUser();
        }
        return instance;
    }

    public void signup(TextField cin_u, TextField nom_u, TextField prenom_u, TextField date_naissance, TextField email_u, TextField num_tel, TextField mot_de_passe, Resources res) {
        String url = Statics.BASE_URL + "/signup/mobile?email_u=" + email_u.getText().toString() + "&cin_u=" + cin_u.getText().toString() + "&nom_u=" + nom_u.getText().toString() + "&prenom_u=" + prenom_u.getText().toString() + "&mot_de_passe=" + mot_de_passe.getText().toString() + "&num_tel=" + num_tel.getText().toString() + "&date_naissance=" + date_naissance.getText().toString();
        req.setUrl(url);
        System.out.println(url);
        if (email_u.getText().equals(" ") && mot_de_passe.getText().equals(" ")) {
            Dialog.show("erreur", "veuillez remplir les champs", "ok", null);
        }
        req.addResponseListener((e) -> {
            byte[] data = (byte[]) e.getMetaData();
            String responseData = new String(data);
            System.out.println("data===>" + responseData);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

    }

    public void signin(TextField email_u, TextField mot_de_passe, Resources res) {
        String url = Statics.BASE_URL + "/login/mobile?email_u=" + email_u.getText().toString() + "&mot_de_passe=" + mot_de_passe.getText().toString();
        req.setUrl(url);
        System.out.println(url);
//ahmed.turki1@esprit
        req.addResponseListener((e) -> {
            JSONParser j = new JSONParser();
            String json = new String(req.getResponseData()) + "";
            try {
                if (json.equals("failed")) {
                    Dialog.show("echec d'authentification", "email or mot_de_passe incorrect", "OK", null);

                } else {
                    System.out.println("data ==" + json);
                    // Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
                    //java.util.List<String> role = (java.util.List<String>) user.get("roles");

                    //  Map<String, Object> role = j.parseJSON((Reader) user.get("roles"));
//String role=(user.get("roles").getJsonObject(0)) ;
                    //Float status = Float.parseFloat(user.get("status").toString());
                    //  System.out.println(role.get(0));
                    /*  if (status == 0) {
                        Dialog.show("error", "you are banned", "ok", null);
                    } else if (status == 2) {
                        Dialog.show("error", "wait until we approve your subscription by then you are simple user ", "ok", null);
                        new NewsfeedForm(res).show();
                    }
                     */
                    new ProfileForm(res).show();// yemchi lel home yelzem nrigelha

                    /*   if (!user.isEmpty()  && "ROLE_ADMIN".equals(role.get(0))) {
                        new UserFormBack(res).show();// yemchi lel home yelzem nrigelha
                        SessionManager.setEmail(user.get("email_u").toString());
                        float id = Float.parseFloat(user.get("id").toString());
                        SessionManager.setId((int) id);
                        //      SessionManager.setId();// hedhi mochkla
                        SessionManager.setNom_u(user.get("nom_u").toString());
                        SessionManager.setPrenom_u(user.get("prenom_u").toString());
                        SessionManager.setPassword(user.get("mot_de_passe").toString());
                    }
                    if (!user.isEmpty()  && "ROLE_USER".equals(role.get(0))) {
                        new ProfileForm(res).show();// yemchi lel home yelzem nrigelha
                        SessionManager.setEmail(user.get("email_u").toString());
                        float id = Float.parseFloat(user.get("id").toString());
                        SessionManager.setId((int) id);
                        //      SessionManager.setId();// hedhi mochkla
                        SessionManager.setNom_u(user.get("nom_u").toString());
                        SessionManager.setPrenom_u(user.get("prenom_u").toString());
                        SessionManager.setPassword(user.get("mot_de_passe").toString());
                    } */
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

    }

    public String getPasswordbyPhone(String phoneNumber, Resources res) {
        String url = Statics.BASE_URL + "user/passwordMobile?phoneNumber=" + phoneNumber;
        req.setUrl(url);
        System.out.println(url);

        req.addResponseListener((e) -> {
            JSONParser j = new JSONParser();
            json = new String(req.getResponseData()) + "";
            try {

                System.out.println("data ==" + json);
                Map<String, Object> password = j.parseJSON(new CharArrayReader(json.toCharArray()));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return json;
    }

    /*
    public static void EditUser(String nom, String prenom, String email, String password, String imageFile) {
        String url = Statics.BASE_URL + "/user/editUserMobile?&email=" + email + "&password=" + password + "&nom=" + nom + "&prenom=" + prenom + "&imageFile=" + imageFile;
        MultipartRequest req = new MultipartRequest();
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("nom", nom);
        req.addArgument("prenom", prenom);
        req.addArgument("password", password);
        req.addArgument("email", email);
        req.addResponseListener((response) -> {
            byte[] data = (byte[]) response.getMetaData();
            String a = new String(data);
            System.out.println(a);
            if (a.equals("success")) {
            } else {
                // Dialog.show("erreur", "echec de modification", "OK", null);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

    }

     */
    public ArrayList<User> parseUser(String jsonText) {
        try {
            User = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            /*
                On doit convertir notre réponse texte en CharArray à fin de
            permettre au JSONParser de la lire et la manipuler d'ou vient
            l'utilité de new CharArrayReader(json.toCharArray())

            La méthode parse json retourne une MAP<String,Object> ou String est
            la clé principale de notre résultat.
            Dans notre cas la clé principale n'est pas définie cela ne veux pas
            dire qu'elle est manquante mais plutôt gardée à la valeur par defaut
            qui est root.
            En fait c'est la clé de l'objet qui englobe la totalité des objets
                    c'est la clé définissant le tableau de tâches.
             */
            Map<String, Object> UserListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            /* Ici on récupère l'objet contenant notre liste dans une liste
            d'objets json List<MAP<String,Object>> ou chaque Map est une tâche.

            Le format Json impose que l'objet soit définit sous forme
            de clé valeur avec la valeur elle même peut être un objet Json.
            Pour cela on utilise la structure Map comme elle est la structure la
            plus adéquate en Java pour stocker des couples Key/Value.

            Pour le cas d'un tableau (Json Array) contenant plusieurs objets
            sa valeur est une liste d'objets Json, donc une liste de Map
             */
            List<Map<String, Object>> list = (List<Map<String, Object>>) UserListJson.get("root");
            //Parcourir la liste des tâches Json

            for (Map<String, Object> obj : list) {
                java.util.List<String> role = (java.util.List<String>) obj.get("roles");

                //Création des tâches et récupération de leurs données
                User t = new User();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int) id);
                float cin_u = Float.parseFloat(obj.get("cin_u").toString());
                t.setCin_u((int) cin_u);
                t.setNom_u(obj.get("nom_u").toString());
                t.setPrenom_u(obj.get("prenom_u").toString());
                float num_tel = Float.parseFloat(obj.get("num_tel").toString());
                t.setNum_tel((int) num_tel);
                t.setMot_de_passe(obj.get("mot_de_passe").toString());

                //t.setRoles(role.get(0));
                //Ajouter la tâche extraite de la réponse Json à la liste
                User.add(t);
            }

        } catch (IOException ex) {

        }
        /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web

         */
        return User;
    }

    public ArrayList<User> getAllUser() {
        ArrayList<User> listUser = new ArrayList<>();

        String url = Statics.BASE_URL + "/user/all/users";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                User = parseUser(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return User;
    }

    //////////activer//////////
    public boolean BanUser(float id) {

        String url = Statics.BASE_URL + "/ban?id=" + id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    //////////activer//////////
    public boolean UnBanUser(float id) {

        String url = Statics.BASE_URL + "/unban?id=" + id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public void login(TextField gui_Text_Field_2, TextField gui_Text_Field_1, Resources res) {

        String url = Statics.BASE_URL + "/loginUser/json?email_u=" + gui_Text_Field_2.getText().toString() + "&mot_de_passe=" + gui_Text_Field_1.getText().toString();
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server

        req.setUrl(url);
        req.setPost(true);

        req.addResponseListener((e) -> {

            JSONParser j = new JSONParser();

            String json = new String(req.getResponseData()) + "";
            try {

                if (json.equals("user not found")) {
                    Dialog.show("Echec d'authentification", "Email or password incorrect", "OK", null);
                } else {
                    System.out.println("data ==" + json);

                    Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));

                    //Session
                    float id = Float.parseFloat(user.get("id").toString());
                    SessionManager.setId((int) id);//jibt id ta3 user ly3ml login w sajltha fi session ta3i

                    SessionManager.setPassword(user.get("mot_de_passe").toString());
                    SessionManager.setEmail(user.get("email_u").toString());
                    SessionManager.setNom(user.get("nom_u").toString());
                    SessionManager.setPrenom(user.get("prenom_u").toString());
                    // SessionManager.setNumTel(String.valueOf((int)Float.parseFloat(user.get("num_tel").toString())));
                    /* SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd");
                    Date date = formatter.parse(user.get("date_naissance").toString());
                    SessionManager.setDateNaissance(date);*/

                    if (user.size() > 0) // l9a user
                    // new ListReclamationForm(rs).show();//yemchi lel list reclamation
                    {

                        //houni awl interface yet7al
                        new ProfileForm(res).show(); //n7o
                    }

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
    }

}
