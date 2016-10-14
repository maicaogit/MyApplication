package br.org.arymax.katana.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import br.org.arymax.katana.R;
import br.org.arymax.katana.activity.UserActivity;

/**
 * Criado por Marco em 14/10/2016.
 */
public class UserLoginTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ProgressDialog mProgress;
    private static final String TAG = "UserLoginTask.java";

    public UserLoginTask(Context context){
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        String message = mContext.getResources().getString(R.string.loading);
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage(message);
        mProgress.setCancelable(false);
        mProgress.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String pront = params[0];
        String senha = params[1];
        String userXml = "";
        try {
            userXml = ServerCalls.callGet(
                    ServerCalls.URL + "funcoes" + ServerCalls.GET_USER_PATH  + pront + "/" + senha ,
                    ServerCalls.GET,
                    ServerCalls.APP_XML
            );
        } catch (Exception e) {
            Log.e(TAG, "Exceção lançada: ", e);
        }
        return userXml;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("")){
           mProgress.dismiss();
        } else {
            mProgress.dismiss();
            Intent intent = new Intent(mContext, UserActivity.class);
            mContext.startActivity(intent);
            ((AppCompatActivity)mContext).finish();
        }
    }
}
