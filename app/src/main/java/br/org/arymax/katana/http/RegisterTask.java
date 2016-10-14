package br.org.arymax.katana.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import br.org.arymax.katana.R;
import br.org.arymax.katana.activity.UserActivity;

/**
 * Criado por Marco em 12/10/2016.
 */
public class RegisterTask extends AsyncTask<String, Void, Void> {

    private Context mContext;
    private ProgressDialog mProgress;

    public RegisterTask(Context context){
        mContext = context;
    }

    @Override
    protected void onPreExecute(){
        String message = mContext.getResources().getString(R.string.loading);
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage(message);
        mProgress.setCancelable(true);
        mProgress.show();
    }

    @Override
    protected Void doInBackground(String... params) {
        int cont = 0;
        do{
            cont++;
        } while (cont < 5 * 10 * 10 * 10 * 10 * 10);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mProgress.dismiss();
        mContext.startActivity(new Intent(mContext, UserActivity.class));
        ((AppCompatActivity) mContext).finish();
    }

    public void beginTask(String[] params){
        this.execute(params);
    }
}
