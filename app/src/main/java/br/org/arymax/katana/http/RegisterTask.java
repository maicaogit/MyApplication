package br.org.arymax.katana.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.View;

import br.org.arymax.katana.R;
import br.org.arymax.katana.activity.LoginActivity;
import br.org.arymax.katana.model.Usuario;
import br.org.arymax.katana.utility.XMLParser;

import com.rey.material.app.SimpleDialog;

/**
 * Criado por Marco em 12/10/2016.
 */
public class RegisterTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ProgressDialog mProgress;
    private static final String TAG = "RegisterTask.java";
    private Usuario usuario;
    private SimpleDialog goToLoginActivityDialog;
    private ServerCalls.Status status;


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
        usuario = XMLParser.XMLToObject(params[0], Usuario.class);
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
        ServerCalls.Status status = this.status.fromString(result);
        if(status == ServerCalls.Status.OK){
            mProgress.dismiss();
            goToLoginActivityDialog = new SimpleDialog(mContext);
            goToLoginActivityDialog.message(R.string.cadastro_realizado);
            goToLoginActivityDialog.positiveAction(R.string.positive_button);
            goToLoginActivityDialog.positiveActionClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToLoginActivityDialog.dismiss();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                    ((AppCompatActivity) mContext).finish();
                }
            });
            goToLoginActivityDialog.cancelable(false);
            goToLoginActivityDialog.show();
        } else {
            mProgress.dismiss();
            View rootView = ((AppCompatActivity) mContext).findViewById(R.id.activity_register_root);
            Snackbar.make(rootView, R.string.register_fail, Snackbar.LENGTH_LONG).show();
        }
    }
}
