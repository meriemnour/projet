/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.mycompany.entities.Produit;
import com.mycompany.utils.Statics;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author RoKy-Dev
 */
public class ServiceProduit {

    public ArrayList<Produit> News;
    String s;
    public static ServiceProduit instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceProduit() {
        req = new ConnectionRequest();
    }

    public static ServiceProduit getInstance() {
        if (instance == null) {
            instance = new ServiceProduit();
        }
        return instance;
    }

    public boolean ajouterProduit(Produit p) {

        String url = Statics.BASE_URL + "/vehicule/mobile/newvehicule?num_chassis=" + p.getNum_chassis() + "&type_vehicule=" + p.getType_vehicule() + "&num_immatriculation=" + p.getNum_immatriculation();
        req.setUrl(url);
        System.out.println(url);
        System.out.println(url);
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

    public ArrayList<Produit> parseTasks(String jsonText) {
        try {
            News = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Produit t = new Produit();
                float id = Float.parseFloat(obj.get("idv").toString());
                t.setId_vehicule((int) id);

                t.setNum_chassis(obj.get("num").toString());

                News.add(t);
            }

        } catch (IOException ex) {

        }
        return News;
    }

    public ArrayList<Produit> affichdess() {
        String url = Statics.BASE_URL + "/vehicule/mobile/vehicule";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                News = parseTasks(new String(req.getResponseData()));

                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return News;
    }

//    public void Editprod(Produit p) {
//        String url = Statics.BASE_URL + "/vehicule/mobile/updatevehicule/" + p.getId_vehicule() + "?num_chassis=" + p.getNum_chassis() + "&type_vehicule=" + p.getType_vehicule() + "&num_immatriculation=" + p.getNum_immatriculation();
//        System.out.println(url);
//        req.setUrl(url);
//        req.addResponseListener((e) -> {
//
//            String str = new String(req.getResponseData());
//            //reponse json hedhi elli rynaha fil naviguateur
//
//            System.out.println("data == " + str);
//        });
//
//        NetworkManager.getInstance().addToQueueAndWait(req);//execution mtaie request sinon yitada chay dima nalkawha
//
//    }
//
//    public boolean deleteprod(int id) {
//        String url = Statics.BASE_URL + "/vehicule/delprod/id=" + id;
//        System.out.println(url);
//        req.setUrl(url);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
//                req.removeResponseListener(this);
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return resultOK;
//    }
    public boolean Delete(Produit p) {
        String url = Statics.BASE_URL + "/vehicule/delprod/" + p.getId_vehicule();

        req.setUrl(url);
        req.setPost(false);
        req.setFailSilently(true);
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
    //Update

    public boolean modifierProduct(Produit p) {
        String url = Statics.BASE_URL + "/vehicule/mobile/updatevehicule/" + p.getId_vehicule() + "?num_chassis=" + p.getNum_chassis() + "&type_vehicule=" + p.getType_vehicule() + "&num_immatriculation=" + p.getNum_immatriculation();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        return resultOK;

    }
}
