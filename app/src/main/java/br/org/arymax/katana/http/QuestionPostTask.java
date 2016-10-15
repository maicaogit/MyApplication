package br.org.arymax.katana.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import br.org.arymax.katana.R;

/**
 * Criado por Marco em 14/10/2016.
 */
public class QuestionPostTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ProgressDialog mProgress;
    private View mRootView;

    private static final String TAG = "QuestionPostTask.java";

    public QuestionPostTask(Context context, View rootView){
        mRootView = rootView;
        mContext = context;
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
        mProgress.dismiss();
        ServerCalls.Status status = ServerCalls.Status.fromString(result);
        if(status == ServerCalls.Status.OK){
            Snackbar.make(mRootView, R.string.snackbar_message_ok, Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(mRootView, R.string.snackbar_message_error, Snackbar.LENGTH_SHORT).show();
        }
    }

}
