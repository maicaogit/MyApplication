package br.org.arymax.katana.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import br.org.arymax.katana.R;

/**
 * Created by aluno on 17/10/2016.
 */

public class ChangeUserInfoGetTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ProgressDialog mProgress;

    private static final String TAG = "ChangeUserInfoGetTask";

    public ChangeUserInfoGetTask(Context context){
        mContext = context;
    }

    @Override
    protected void onPreExecute(){
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage(mContext.getResources().getString(R.string.loading));
        mProgress.setCancelable(false);
        mProgress.show();
    }

    @Override
    protected String doInBackground(String... params){
        String id = params[0];
        String newInfo = params[1];
        String result = "";
        try {
            result = ServerCalls.callGet(
                    ServerCalls.SERVER_URL + "funcoes" + ServerCalls.CHANGE_USER_INFO_PATH + id + "/" + newPassword,
                    ServerCalls.GET,
                    ServerCalls.TEXT_PLAIN
            );
        } catch (Exception e){
            this.cancel(true);
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result){
        if(ServerCalls.Status.fromString(result) == ServerCalls.Status.OK){
            mProgress.dismiss();
        }
    }