package br.org.arymax.katana.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.Serializable;
import java.util.List;

import br.org.arymax.katana.R;
import br.org.arymax.katana.activity.QuestionActivity;
import br.org.arymax.katana.fragment.HomeFragment;
import br.org.arymax.katana.model.ArrayRespostas;
import br.org.arymax.katana.model.Resposta;
import br.org.arymax.katana.utility.XMLParser;

/**
 * Criado por Marco em 14/10/2016.
 */
public class AnswerGetTask extends AsyncTask<Long, Void, String> {

    private Context mContext;
    private ProgressDialog mProgress;
    private HomeFragment mCallerFragment;
    private Intent mOpenQuestionActivity;

    private static final String TAG = "AnswerGetTask.java";

    public AnswerGetTask(Context context, HomeFragment fragment, Intent intent){
        mCallerFragment = fragment;
        mContext = context;
        mOpenQuestionActivity = intent;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
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
        long pkPergunta = params[0];
        String xmlAnswers = "";
        try {
            xmlAnswers = ServerCalls.callGet(
                    ServerCalls.SERVER_URL + "funcoes" + ServerCalls.GET_ANSWERS_PATH + pkPergunta,
                    ServerCalls.GET,
                    ServerCalls.TEXT_XML
            );
        } catch (Exception e){
            this.cancel(true);
        }
        return xmlAnswers;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(TAG, "Result: " + result);
        if(!result.equals("")){
            List<Resposta> respostaList = XMLParser.xmlToListObject(result, ArrayRespostas.class, Resposta.class);
            mOpenQuestionActivity.putExtra("respostas", (Serializable) respostaList);
            mContext.startActivity(mOpenQuestionActivity);
            mProgress.dismiss();
        }
    }
}
