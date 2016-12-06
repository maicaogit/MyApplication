package br.org.arymax.katana.model;

import br.org.arymax.katana.http.ServerCalls;
import br.org.arymax.katana.http.ServerCalls.Status;

/**
 * Criado por Marco em 25/10/2016.
 */
public class Rest {

    private String method;
    private String contentType;
    private String accept;
    private String urlParameters;
    private String host;
    private String path;

    public Rest(){

    }

    public Rest(String host, String method, String accept){
        this.host = host;
        this.method = method;
        this.accept = accept;
    }

    public Rest(String host, String urlParameters, String path, String method, String contentType, String accept){
        this.host = host;
        this.urlParameters = urlParameters;
        this.path = path;
        this.method = method;
        this.contentType = contentType;
        this.accept = accept;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getUrlParameters() {
        return urlParameters;
    }

    public void setUrlParameters(String urlParameters) {
        this.urlParameters = urlParameters;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static String callGet(Rest rest) throws Exception {
        return ServerCalls.callGet(rest);
    }

    public static Status callSet(Rest rest) throws Exception {
        return Status.fromString(ServerCalls.callSet(rest));
    }
}
