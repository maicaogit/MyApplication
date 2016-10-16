package br.org.arymax.katana.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

import br.org.arymax.katana.R;
import br.org.arymax.katana.fragment.HomeFragment;
import br.org.arymax.katana.model.ArrayPerguntas;
import br.org.arymax.katana.model.Pergunta;

/**
 * Criado por Marco em 14/10/2016.
 */
public class QuestionGetTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ProgressBar mProgress;
    private HomeFragment mCallerFragment;
    private View rootView;

    private static final String TAG = "QuestionGetTask.java";

    public QuestionGetTask(View rootView, Context context, HomeFragment callerFragment, ProgressBar p) {
        mContext = context;
        mCallerFragment = callerFragment;
        this.rootView = rootView;
        mProgress = p;
    }

    @Override
    protected void onPreExecute() {
        mProgress.setVisibility(View.VISIBLE);
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
        Log.d(TAG, "onPostExecute");
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
            mCallerFragment.setViews(rootView);
            mProgress.setVisibility(View.GONE);
        }
    }

}
