package br.org.arymax.katana.http;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Criado por Marco em 14/10/2016.
 */
public class QuestionGetTask extends AsyncTask<String, Void, String> {

    private static final String TAG = "QuestionGetTask.java";

    public QuestionGetTask()
    {

    }

    @Override
    protected void onPreExecute()
    {

    }

    @Override
    protected String doInBackground(String... params){
        String ordenacao = params[0];
        String xmlQuestion = "";
        try{
            xmlQuestion = ServerCalls.callGet(
                    ServerCalls.SERVER_URL + "funcoes" + ServerCalls.GET_ALL_QUESTIONS_PATH + "/" + ordenacao,
                    ServerCalls.GET,
                    ServerCalls.APP_XML
            );
        } catch (Exception e){
            Log.e(TAG, "Exceção lançada: ", e);
        }
        return xmlQuestion;
    }

    @Override
    protected void onPostExecute(String result)
    {

    }

}
