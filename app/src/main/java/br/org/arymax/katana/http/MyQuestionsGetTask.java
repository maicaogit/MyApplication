package br.org.arymax.katana.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

import br.org.arymax.katana.R;
import br.org.arymax.katana.fragment.HomeFragment;
import br.org.arymax.katana.fragment.MyQuestionsFragment;
import br.org.arymax.katana.model.ArrayPerguntas;
import br.org.arymax.katana.model.ArrayRespostas;
import br.org.arymax.katana.model.Pergunta;
import br.org.arymax.katana.model.Resposta;

/**
 * Criado por Marco em 16/10/2016.
 */
public class MyQuestionsGetTask extends AsyncTask<Long, Void, String> {

    private Context mContext;
    private ProgressDialog mProgress;
    private MyQuestionsFragment mCallerFragment;
    private View rootView;

    private static final String TAG = "MyQuestionsTask.java";

    public MyQuestionsGetTask(View rootView, Context context, MyQuestionsFragment callerFragment)
    {
        mContext = context;
        this.rootView = rootView;
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
    protected String doInBackground(Long... params) {
        long id = params[0];
        String result = "";
        try{
            result = ServerCalls.callGet(
                    ServerCalls.SERVER_URL + "funcoes" + ServerCalls.GET_USER_QUESTIONS_PATH + id,
                    ServerCalls.GET,
                    ServerCalls.TEXT_XML
            );
        } catch (Exception e){
            this.cancel(true);
        }
        return result;
    }

    @Override
    protected void onCancelled()
    {
        mProgress.dismiss();
    }

    @Override
    protected void onPostExecute(String result)
    {
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
            mCallerFragment.setRecyclerView(rootView);
            mProgress.dismiss();
        }
    }
}


