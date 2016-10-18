package br.org.arymax.katana.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import com.rey.material.app.SimpleDialog;

import br.org.arymax.katana.R;
import br.org.arymax.katana.model.Usuario;
import br.org.arymax.katana.utility.XMLParser;

/**
 * Created by aluno on 17/10/2016.
 */

public class ChangeUserInfoTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ProgressDialog mProgress;
    private static final String TAG = "ChangeUserInfoTask.java";
    private Usuario usuario;
    private ServerCalls.Status status;


    public ChangeUserInfoTask(Context context){
        mContext = context;
    }

    @Override
    protected void onPreExecute(){

        String message = mContext.getResources().getString(R.string.loading);
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage(message);
        mProgress.setCancelable(false);
        mProgress.show();
    }

    @Override
    protected String doInBackground(String... params) {
        usuario = XMLParser.XMLToObject(params[0], Usuario.class);
        String userXML = params[0];
        String result = "";
        try {
            result = ServerCalls.callSet(
                    ServerCalls.SERVER_URL,
                    userXML,
                    ServerCalls.CHANGE_USER_INFO_PATH,
                    ServerCalls.POST,
                    ServerCalls.TEXT_XML,
                    ServerCalls.TEXT_PLAIN
            );
        } catch (Exception e) {
            Log.e(TAG, "Exceção lançada", e);
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if (ServerCalls.Status.fromString(result) == ServerCalls.Status.OK)
        {
            mProgress.dismiss();

        }
    }
}