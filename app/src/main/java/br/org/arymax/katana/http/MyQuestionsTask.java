package br.org.arymax.katana.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import br.org.arymax.katana.R;

/**
 * Criado por Marco em 16/10/2016.
 */
public class MyQuestionsTask extends AsyncTask<Long, Void, String> {

    private Context mContext;
    private ProgressDialog mProgress;

    private static final String TAG = "MyQuestionsTask.java";

    public MyQuestionsTask(Context c){
        mContext = c;
    }

    @Override
    protected void onPreExecute() {
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage(mContext.getResources().getString(R.string.loading));
        mProgress.setCancelable(false);
        mProgress.show();
    }

    @Override
    protected String doInBackground(Long... params) {
        long id = params[0];
        String result = "";
        try{
            result = ServerCalls.callGet(
                    ServerCalls.SERVER_URL + "funcoes" + ServerCalls.GET_USER_QUESTIONS_PATH + id,
                    ServerCalls.GET,
                    ServerCalls.APP_XML
            );
        } catch (Exception e){
            this.cancel(true);
        }
        return result;
    }

    @Override
    protected void onCancelled() {
        mProgress.dismiss();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
