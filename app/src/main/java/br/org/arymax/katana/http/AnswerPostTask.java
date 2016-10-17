package br.org.arymax.katana.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import br.org.arymax.katana.R;

/**
 * Criado por Marco em 14/10/2016.
 */
public class AnswerPostTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ProgressDialog mProgress;

    public AnswerPostTask(Context context){
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage(mContext.getResources().getString(R.string.loading));
        mProgress.setCancelable(false);
        mProgress.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String xmlAnswer = params[0];
        String result = "";
        try {
            result = ServerCalls.callSet(
                    ServerCalls.SERVER_URL,
                    xmlAnswer,
                    ServerCalls.REGISTER_ANSWER_PATH,
                    ServerCalls.POST,
                    ServerCalls.TEXT_XML,
                    ServerCalls.TEXT_PLAIN
            );
        } catch (Exception e){
            this.cancel(true);
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        mProgress.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
