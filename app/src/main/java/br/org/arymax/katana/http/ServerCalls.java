package br.org.arymax.katana.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Classe que contém funções de uso geral.
 *
 * @author Lorhan Sohaky
 */
public class ServerCalls {

    protected static final String URL = "";
    private static final String TAG = "ServerCalls.java";

    public enum Status{

        OK,

        EXISTE,

        ERRO;


        public static Status fromString(String status){
            Status stats = null;
            switch (status){
                case "OK":
                    stats = OK;
                    break;

                case "EXISTE":
                    stats = EXISTE;
                    break;

                case "ERRO":
                    stats = ERRO;
                    break;
            }
            return stats;
        }

    }

    /**
     * Método que chama um RESTful com a passagem de parâmetros.
     *
     * @param host Endereço (URL).
     * @param urlParameters Parâmetros.
     * @param path Local do REST.
     * @param method Método de comunicação do protocolo HTTP.
     * @param contentType Tipo de dado aceito.
     * @param accept Tipo aceito.
     * @return String com a resposta do servidor.
     * @throws MalformedURLException Erro.
     * @throws IOException Erro.
     */
    public static String callSet(String host, String urlParameters, String path, String method, String contentType, String accept) throws Exception {

        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        URL url;
        try {
            url = new URL(host + "funcoes" + path);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestProperty("Content-type", contentType);
            httpCon.setRequestProperty("Accept", accept);
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod(method);
            try (DataOutputStream wr = new DataOutputStream(httpCon.getOutputStream())) {
                wr.write(postData);
                wr.close();
            }
            return httpCon.getResponseMessage();
        } catch (IOException ex) {
            Log.e(TAG, "Erro ao chamar o site com o envio de argumentos.", ex);
            throw new Exception("Erro ao chamar o site com o envio de argumento.", ex);
        }
    }

    /**
     * Método que chama um RESTful sem a passagem de parâmetros.
     *
     * @param host Endreço (URL).
     * @param method Método.
     * @param accept Tipo aceito.
     * @return String com a resposta do servidor.
     * @throws MalformedURLException Erro.
     * @throws IOException Erro.
     */
    public static String callGet(String host, String method, String accept) throws Exception {
        String result = "";
        String tmp;
        URL url;
        BufferedReader br = null;
        try {
            url = new URL(host);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestProperty("Accept", accept);
            httpCon.setRequestMethod(method);
            httpCon.setDoOutput(true);
            try (InputStreamReader in = new InputStreamReader(httpCon.getInputStream(), StandardCharsets.UTF_8)) {
                br = new BufferedReader(in);
                while ((tmp = br.readLine()) != null) {
                    result = result.concat(tmp);
                }
            }
        } catch (IOException ex) {
            Log.e(TAG, "Erro ao chamar o site sem envio de argumentos.", ex);
            throw new Exception("Erro ao chamar o site sem o envio de argumentos.", ex);
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return result;
    }
}
