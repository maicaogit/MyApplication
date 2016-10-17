package br.org.arymax.katana.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

import br.org.arymax.katana.R;
import br.org.arymax.katana.activity.QuestionActivity;
import br.org.arymax.katana.model.ArrayPerguntas;
import br.org.arymax.katana.model.ArrayRespostas;
import br.org.arymax.katana.model.Resposta;

/**
 * Criado por Marco em 14/10/2016.
 */
public class AnswerGetTask extends AsyncTask<Long, Void, String> {

    private Context mContext;
    private ProgressDialog mProgress;
    private QuestionActivity mCallerActivity;

    private static final String TAG = "AnswerGetTask.java";

    public AnswerGetTask(Context context, AppCompatActivity activity){
        mCallerActivity = (QuestionActivity) activity;
        mContext = context;
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
                    ServerCalls.SERVER_URL + "funcoes" + ServerCalls.GET_ANSWERS_PATH + "/" + pkPergunta,
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
        if(!result.equals("")){
            List<Resposta> perguntaList = new ArrayList<>();
            XStream stream = new XStream();
            stream.processAnnotations(ArrayPerguntas.class);
            stream.processAnnotations(Resposta.class);
            ArrayRespostas respostas = (ArrayRespostas) stream.fromXML(result);
            if(respostas.getRespostas() != null){
                for(int i = 0; i < respostas.getRespostas().size(); i++){
                    perguntaList.add(respostas.getRespostas().get(i));
                }
                mCallerActivity.setAnswerList(perguntaList);
            }
        } else {

        }
        mProgress.dismiss();
    }
}
