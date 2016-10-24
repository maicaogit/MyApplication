package br.org.arymax.katana.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import br.org.arymax.katana.R;
import br.org.arymax.katana.activity.LoginActivity;
import br.org.arymax.katana.model.Usuario;
import br.org.arymax.katana.utility.XMLParser;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Criado por Marco em 12/10/2016.
 */
public class RegisterTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ProgressDialog mProgress;
    private static final String TAG = "RegisterTask.java";
    private Usuario usuario;
    private MaterialDialog mMaterialDialog;


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
        ServerCalls.Status status = ServerCalls.Status.fromString(result);
        Log.d(TAG, "Resposta do server: " + result);
        if(status == ServerCalls.Status.OK){
            mProgress.dismiss();
            mMaterialDialog = new MaterialDialog(mContext)
                    .setMessage(R.string.cadastro_realizado)
                    .setPositiveButton(R.string.positive_button, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            mContext.startActivity(intent);
                            ((AppCompatActivity) mContext).finish();
                        }
                    })
                    .setCanceledOnTouchOutside(false);
            mMaterialDialog.show();
        } else {
            mProgress.dismiss();
            View rootView = ((AppCompatActivity) mContext).findViewById(R.id.activity_register_root);
            Snackbar.make(rootView, R.string.register_fail, Snackbar.LENGTH_LONG).show();
        }
    }
}
