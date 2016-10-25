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
import android.widget.EditText;

import br.org.arymax.katana.R;

/**
 * Criado por Marco em 14/10/2016.
 */
public class QuestionPostTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ProgressDialog mProgress;
    private View mRootView;
    private Intent mIntent;

    private static final String TAG = "QuestionPostTask.java";

    public QuestionPostTask(Context context, View rootView, Intent mIntent){
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
            Snackbar.make(mRootView, R.string.snackbar_message_ok, Snackbar.LENGTH_LONG).show();
            mContext.startActivity(mIntent);
            mProgress.dismiss();
        } else {
            Snackbar.make(mRootView, R.string.snackbar_message_error, Snackbar.LENGTH_LONG).show();
            mProgress.dismiss();
        }
    }
}
