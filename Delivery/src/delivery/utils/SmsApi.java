/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 *
 * @author asus
 */
public class SmsApi {
    public static final String ACCOUNT_SID = "AC0114357726010052be94eb917a5b437d";
    public static final String AUTH_TOKEN = "5425ab40a1f63355d39361d469d3a8e5";

    public static void sendSMS(String num, String msg) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(/*num ili bch yjih il msg */new PhoneNumber(num),new PhoneNumber("+15046887295"), msg).create();

        System.out.println(message.getSid());

    }
}
