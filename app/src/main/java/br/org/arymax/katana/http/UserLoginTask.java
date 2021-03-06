package br.org.arymax.katana.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.rey.material.widget.SnackBar;

import br.org.arymax.katana.R;
import br.org.arymax.katana.activity.UserActivity;
import br.org.arymax.katana.model.Usuario;
import br.org.arymax.katana.utility.Constants;
import br.org.arymax.katana.utility.XMLParser;

/**
 * Criado por Marco em 14/10/2016.
 */
public class UserLoginTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ProgressDialog mProgress;
    private static final String TAG = "UserLoginTask.java";
    private String zze;

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
        zze = params[1];
        String userXml = "";
        try {
            userXml = ServerCalls.callGet(
                    ServerCalls.SERVER_URL + "funcoes" + ServerCalls.GET_USER_PATH  + pront + "/" + zze ,
                    ServerCalls.GET,
                    ServerCalls.TEXT_XML
            );
        } catch (Exception e) {
            Log.e(TAG, "Exceção lançada: ", e);
        }
        return userXml;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(TAG, "XML do usuário: " + result);
        if(result.equals("")){
            View rootView = ((AppCompatActivity) mContext).findViewById(R.id.activity_login_root);
            //Snackbar.make(rootView, R.string.login_fail, Snackbar.LENGTH_LONG).show();
            SnackBar snackBar = SnackBar.make(mContext)
                    .text(R.string.login_fail)
                    .duration(3000);
            snackBar.applyStyle(com.rey.material.R.style.Material_Widget_SnackBar_Mobile);
            snackBar.show((ViewGroup) rootView);

            mProgress.dismiss();
        } else {
            Usuario usuario = XMLParser.XMLToObject(result, Usuario.class);
            SharedPreferences preferences = mContext.getSharedPreferences(Constants.PREFERENCES, 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("logado", true);
            editor.putString("prontuario", usuario.getProntuario());
            editor.putLong("pk", usuario.getPkUsuario());
            editor.putString("zze", zze);
            editor.putString("nome", usuario.getNome());
            editor.putString("email", usuario.getEmail());
            editor.commit();
            mProgress.dismiss();
            Intent intent = new Intent(mContext, UserActivity.class);
            mContext.startActivity(intent);
            ((AppCompatActivity)mContext).finish();
        }
    }
}

