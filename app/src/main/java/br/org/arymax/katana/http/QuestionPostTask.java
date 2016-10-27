package br.org.arymax.katana.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.rey.material.widget.SnackBar;

import br.org.arymax.katana.R;

/**
 * Criado por Marco em 14/10/2016.
 */
public class QuestionPostTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ProgressDialog mProgress;
    private ViewGroup mRootView;
    private Intent mIntent;

    private static final String TAG = "QuestionPostTask.java";

    public QuestionPostTask(Context context, ViewGroup rootView, Intent mIntent){
        mRootView = rootView;
        mContext = context;
        this.mIntent = mIntent;
    }

    @Override
    protected void onPreExecute(){
        String message = mContext.getResources().getString(R.string.sending_question);
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage(message);
        mProgress.setCancelable(false);
        mProgress.show();
    }

    @Override
    protected String doInBackground(String... params){
        String xmlQuestion = params[0];
        String result = "";
        try {
            result = ServerCalls.callSet(
                    ServerCalls.SERVER_URL,
                    xmlQuestion,
                    ServerCalls.REGISTER_QUESTION_PATH,
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
    protected void onPostExecute(String result){
        ServerCalls.Status status = ServerCalls.Status.fromString(result);
        if(status == ServerCalls.Status.OK){
            AppCompatActivity activity = (AppCompatActivity) mContext;
            EditText text = (EditText) activity.findViewById(R.id.edit_text_titulo);
            EditText text1 = (EditText) activity.findViewById(R.id.text_pergunta);
            SwitchCompat switchCompat = (SwitchCompat) activity.findViewById(R.id.modo_anonimo);
            text.setText("");
            text1.setText("");
            switchCompat.setChecked(false);
            SnackBar snackBar = SnackBar.make(mContext)
                    .text(R.string.snackbar_message_ok)
                    .singleLine(true)
                    .duration(3000);

            snackBar.applyStyle(com.rey.material.R.style.Material_Widget_SnackBar_Mobile);
            snackBar.actionTextColor(mContext.getResources().getColor(R.color.colorAccent));
            snackBar.show(mRootView);
            //mContext.startActivity(mIntent);
            mProgress.dismiss();
        } else {
            SnackBar snackBar = SnackBar.make(mContext)
                    .text(R.string.snackbar_message_error)
                    .singleLine(true)
                    .duration(3000);

            snackBar.applyStyle(com.rey.material.R.style.Material_Widget_SnackBar_Mobile);
            snackBar.actionTextColor(mContext.getResources().getColor(R.color.colorAccent));
            snackBar.show(mRootView);
            mProgress.dismiss();
        }
    }
}
