/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Menu;
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
public class ServiceTask {

    public ArrayList<Menu> Menus;

    public static ServiceTask instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceTask() {
        req = new ConnectionRequest();
    }

    public static ServiceTask getInstance() {
        if (instance == null) {
            instance = new ServiceTask();
        }
        return instance;
    }

    public boolean addMenu(Menu t) {
        System.out.println(t);
        System.out.println("********");
        String url = Statics.BASE_URL + "/addmenumobile";

        req.setUrl(url);

        req.addArgument("name", t.getName());
        req.addArgument("category", t.getCategory());
        req.addArgument("description", t.getDescription());
        req.addArgument("price", "" + t.getPrice());
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Menu> parseMenu(String jsonText) {
        try {
            Menus = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Menu t = new Menu();

                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int) id);

                t.setPrice(((int) Float.parseFloat(obj.get("price").toString())));

                t.setName(obj.get("name").toString());

                t.setCategory(obj.get("category").toString());

                t.setDescription(obj.get("description").toString());
                Menus.add(t);
                System.out.println(t);

            }

        } catch (IOException ex) {

        }
        return Menus;
    }

    public ArrayList<Menu> getAllMenu() {
        //String url = Statics.BASE_URL+"/tasks/";
        req = new ConnectionRequest();
        String url = Statics.BASE_URL + "/ListMenumobile/";
        System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Menus = parseMenu(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Menus;
    }


    /*
    public ArrayList<reservation> getAllReservations(){
req=new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"listreservationmobile/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reservations = parseReservations(new String(req.getResponseData()));
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reservations;
    }

     */
    public boolean deleteMenu(Menu U) {
        System.out.println(U);
        System.out.println("********");
        //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
        String url = Statics.BASE_URL + "/deletemenumobile/" + U.getId();
        System.out.println(url);
        req.setUrl(url);
        req.addArgument("id", U.idtoString());
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }


    /*
 public boolean deleteReservation(reservation U) {
        System.out.println(U);
        System.out.println("********");
       //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
       String url = Statics.BASE_URL + "deletereservationmobile/"+U.getId();
       System.out.println(url);
       req.setUrl(url);
       req.addArgument("id", U.idtoString());
       req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
     */
    public boolean ModifyCoach(Menu t) {
        System.out.println(t);
        //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
        String url = Statics.BASE_URL + "/updatemenumobile/";
        req.setUrl(url);
        System.out.println(url);
        req.addArgument("id", t.getId() + "");
        req.addArgument("name", t.getName());
        req.addArgument("category", t.getCategory());
        req.addArgument("description", t.getDescription());
        req.addArgument("price", t.getPrice() + "");
        System.out.println(Statics.BASE_URL + "updatemenumobile/");
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    /*
public boolean ModifyReservation(reservation t) {
        System.out.println(t);
       //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
       String url = Statics.BASE_URL + "updatereservationmobile/";
        req.setUrl(url);
       System.out.println(url);
    req.addArgument("id", t.getId()+"");
       req.addArgument("start", t.getTempsstart());
       req.addArgument("end", t.getTempsend());
 if(t.isDispo()==true)
{
req.addArgument("dispo","1");
}
else if(t.isDispo()==false)
{
req.addArgument("dispo","0");
}
       req.addArgument("coach", t.getCoach()+"");

        System.out.println(Statics.BASE_URL + "updatereservationmobile/");
       req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
       NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
        }




     */
}
