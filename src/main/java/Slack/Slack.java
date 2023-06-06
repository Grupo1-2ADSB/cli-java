/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slack;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;



/**
 *
 * @author Alexa
 */
public class Slack {

    private static HttpClient client = HttpClient.newHttpClient();
    private static final String url = "https://hooks.slack.com/services/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";
    private static final JSONObject alertaNotifica = new JSONObject();

        public void validaMemoria(Integer nSerie) throws IOException, InterruptedException {
        alertaNotifica.put("text", "Maquina " +nSerie+ " com memória acima de 80% em uso!");
        sendMessage(alertaNotifica);
    }

    public void validaDisco(Integer nSerie) throws IOException, InterruptedException {
        alertaNotifica.put("text", "Maquina " +nSerie+ " com disco acima de 90% em uso!");
        sendMessage(alertaNotifica);
    }

    public void validaProcessador(Integer nSerie) throws IOException, InterruptedException {
        alertaNotifica.put("text", "Maquina " +nSerie+ " com processador acima de 90% em uso!");
        sendMessage(alertaNotifica);
    }

    public static void sendMessage(JSONObject content) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .header("accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(content.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(String.format("Status: %s", response.statusCode()));
        System.out.println(String.format("Response: %s", response.body()));
    }
}
