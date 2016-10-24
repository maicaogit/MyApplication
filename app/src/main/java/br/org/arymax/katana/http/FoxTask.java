package br.org.arymax.katana.http;

import android.os.AsyncTask;

/**
 * Criado por Marco em 24/10/2016.
 */
public class FoxTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        String param = params[0];
        String result = "";
        try{
            result = ServerCalls.callSet(
                    ServerCalls.SERVER_URL,
                    param,
                    ServerCalls.SET_NOTIFICATION,
                    ServerCalls.PUT,
                    ServerCalls.TEXT_XML,
                    ServerCalls.TEXT_PLAIN
            );
        } catch (Exception e){
            this.cancel(true);
        }
        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
