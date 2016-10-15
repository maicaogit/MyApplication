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
 * Criado por Marco em 12/10/2016.
 */
public class RegisterTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ProgressDialog mProgress;
    private static final String TAG = "RegisterTask.java";

    public RegisterTask(Context context){
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
        String userXML = params[0];
        String result = "";
        try {
             result = ServerCalls.callSet(
                    ServerCalls.SERVER_URL,
                    userXML,
                    ServerCalls.REGISTER_USER_PATH,
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
        ServerCalls.Status status = ServerCalls.Status.fromString(result);
        if(status == ServerCalls.Status.OK){
            mProgress.dismiss();
            mContext.startActivity(new Intent(mContext, UserActivity.class));
            ((AppCompatActivity) mContext).finish();
        } else {
            mProgress.dismiss();
        }
    }
}
