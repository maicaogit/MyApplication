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


    //Paths de rest
        /**
         * URL do server
         */
        protected static final String URL = "http://pdscidadeajuda.ddns.net:8080/ServidorConecta/";


        //POST
            /**
             * Path de cadastro de usuário
             */
            protected static final String REGISTER_USER_PATH = "/usuario/cadastro";



            /**
             * Path de cadastro de pergunta
             */
            protected static final String REGISTER_QUESTION_PATH = "/pergunta/cadastro";



            /**
             * Path de cadastro de resposta
             */
            protected static final String REGISTER_ANSWER_PATH = "/resposta/cadastro";
        //
        //GET
            /**
             *
             */
            protected static final String GET_USER_PATH = "";
        //



    //METHODS
        /**
         * Constante para o método de comunicação HTTP POST
         */
        protected static final String POST = "POST";



        /**
         * Constante para o método de comunicação HTTP GET
         */
        protected static final String GET = "GET";



    //CONTENT-TYPE
        /**
         * Constante para o content-type APP/XML
         */
        protected static final String APP_XML = "APPLICATION/XML";



        /**
         * Constante para o content-type TEXT/PLAIN;
         */
        protected static final String TEXT_PLAIN = "TEXT/PLAIN";



        /**
         * Constante para o content-type TEXT/XML
         */
        protected static final String TEXT_XML = "TEXT/XML";


    private static final String TAG = "ServerCalls.java";

    /**
     * Enum para status de chamadas do servidor
     */
    public enum Status{
        /**
         * Tipo de enumeração que indica sucesso
         */
        OK,

        /**
         * Tipo de enumeração que indica que a operação da foi realizada
         */
        EXISTE,

        /**
         * Tipo de enumeração que indica erro
         */
        ERRO;


        /**
         * Converte uma string em um Status
         *
         * @param status string a ser convertida
         * @return string convertida em status
         */
        public static Status fromString(String status){
            Status stats;
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

                default:
                    stats = null;
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
            Log.d(TAG, "URL: " + url);
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
