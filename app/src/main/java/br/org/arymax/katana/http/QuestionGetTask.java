package br.org.arymax.katana.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

import br.org.arymax.katana.R;
import br.org.arymax.katana.fragment.HomeFragment;
import br.org.arymax.katana.model.ArrayPerguntas;
import br.org.arymax.katana.model.Pergunta;
import br.org.arymax.katana.utility.XMLParser;

/**
 * Criado por Marco em 14/10/2016.
 */
public class QuestionGetTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ProgressDialog mProgress;
    private HomeFragment mCallerFragment;

    private static final String TAG = "QuestionGetTask.java";

    public QuestionGetTask(Context context, HomeFragment callerFragment) {
        mContext = context;
        mCallerFragment = callerFragment;
    }

    @Override
    protected void onPreExecute() {
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage(mContext.getResources().getString(R.string.loading));
        mProgress.setCancelable(false);
        mProgress.show();
    }

    @Override
    protected String doInBackground(String... params){
        String ordenacao = params[0];
        String xmlQuestion = "";
        try{
            xmlQuestion = ServerCalls.callGet(
                    ServerCalls.SERVER_URL + "funcoes" + ServerCalls.GET_ALL_QUESTIONS_PATH  + ordenacao,
                    ServerCalls.GET,
                    ServerCalls.TEXT_XML
            );
        } catch (Exception e){
            Log.e(TAG, "Exceção lançada: ", e);
        }
        Log.d(TAG, "XML: " + xmlQuestion);
        return xmlQuestion;
    }

    @Override
    protected void onPostExecute(String result) {
        if(!result.equals("")){
            List<Pergunta> listPergunta = new ArrayList<>();
            Log.d(TAG, "XML da lista: " + result);
            XStream stream = new XStream();
            stream.processAnnotations(ArrayPerguntas.class);
            stream.processAnnotations(Pergunta.class);
            ArrayPerguntas listPerguntas = (ArrayPerguntas) stream.fromXML(result);
            for(int i = 0; i < listPerguntas.getPerguntas().size(); i++){
                listPergunta.add(listPerguntas.getPerguntas().get(i));
            }
            mCallerFragment.setPerguntasList(listPergunta);
            mProgress.dismiss();
        }
    }

}
