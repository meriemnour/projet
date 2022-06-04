/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery;

import static delivery.GUI.EssaiController.createCaptchaValue;
import delivery.entity.Role;
import delivery.entity.User;
import delivery.service.UserService;
import delivery.utils.Myconnexion;
import delivery.utils.SmsApi;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;


/**
 *
 * @author asus
 */
public class Delivery {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        java.sql.Date d =java.sql.Date.valueOf(LocalDate.now());
        //User u = new User("mohamed","mohamed","collaborateur","collaborateur","mohamed@gmail.com","rue des camelias",50737864,Role.CLIENT,d,true);
        //UserService su= new UserService();
        //System.out.println(su.findByEmailORUsername("ddd"));
        //su.supprimer(16);
       // System.out.println(su.findByUsername("test"));
        //su.ajouter(u);
        //su.modifier(10,u);
        //su.supprimer(10);
        //System.out.println(su.afficher());
        //System.out.println(su.checklogin("collaborateur", "collaborateur"));
        //System.out.println(su.approvedlogin("collaborateur", "collaborateur"));
        //System.out.println(su.findByName("hosni"));
        //System.out.println(su.sortedByDate());
        //SmsApi.sendSMS("+21652778549","bienvenue");

        String generatedCapcha=createCaptchaValue();
        System.out.println("captcha: "+createCaptchaValue());
        Scanner sc=new Scanner(System.in);
        System.out.println("Verify captcha: ");
        String verification =sc.next();
        if(generatedCapcha.equals(verification)){
              System.out.println("true ");
        }else{
              System.out.println("false ");
        }
        sc.close();
    }
    
     public static String createCaptchaValue(){
       Random random=new Random();
       int length= 7+(Math.abs(random.nextInt())%3);
       StringBuilder captchaBuffer= new StringBuilder();
       for(int i=0;i<length;i++){
           int baseCharNumber=Math.abs(random.nextInt())%62;
           int charNumber=0;
           if(baseCharNumber<26){
               charNumber=65+baseCharNumber;
           }
           else
               if(baseCharNumber<52){
                   charNumber=97+(baseCharNumber-26);
               }
               else{
                   charNumber=48+(baseCharNumber-52);
               }
           captchaBuffer.append((char)charNumber);
       }
       return captchaBuffer.toString();
    }
    
}
