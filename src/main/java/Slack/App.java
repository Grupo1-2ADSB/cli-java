/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slack;

import java.io.IOException;
import org.json.JSONObject;


/**
 *
 * @author Alexa
 */
public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        JSONObject alertaNotifica = new JSONObject();
        
        //json.put("text", "Teste test :double_exclamation_mark: :cross_mark: :check_mark: "
        //        + ":check_mark_button: :red_exclamation_mark: :white_exclamation_mark:");
        //Slack.sendMessage(json);
        
        alertaNotifica.put("text", "!!!");
 
        Slack.sendMessage(alertaNotifica);
    }
}
