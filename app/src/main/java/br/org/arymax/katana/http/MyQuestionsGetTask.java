package br.org.arymax.katana.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.extended.ISO8601DateConverter;

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
    private ProgressBar mProgress;
    private MyQuestionsFragment mCallerFragment;
    private View rootView;

    private static final String TAG = "MyQuestionsTask.java";

    public MyQuestionsGetTask(View rootView, Context context, MyQuestionsFragment callerFragment, ProgressBar p) {
        mContext = context;
        this.rootView = rootView;
        mCallerFragment = callerFragment;
        mProgress = p;
    }

    @Override
    protected void onPreExecute() {
        mProgress.setVisibility(View.VISIBLE);
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
    protected void onPostExecute(String result) {
        if(!result.equals("")){
            List<Pergunta> listPergunta = new ArrayList<>();
            Log.d(TAG, "XML da lista: " + result);
            XStream stream = new XStream();
            stream.registerConverter(new ISO8601DateConverter());
            stream.processAnnotations(ArrayPerguntas.class);
            stream.processAnnotations(Pergunta.class);
            ArrayPerguntas listPerguntas = (ArrayPerguntas) stream.fromXML(result);
            for(int i = 0; i < listPerguntas.getPerguntas().size(); i++){
                listPergunta.add(listPerguntas.getPerguntas().get(i));
            }
            mCallerFragment.setPerguntasList(listPergunta);
            mCallerFragment.setRecyclerView(rootView);
            mProgress.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCancelled() {
        Snackbar snackbar = Snackbar.make(rootView, "Ocorreu um erro ao atualizar as perguntas", Snackbar.LENGTH_INDEFINITE)

                .setAction("Tentar novamente", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallerFragment.callTask();
                    }
                });
        View view = snackbar.getView();
        TextView message = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        message.setMaxLines(1);
        snackbar.show();
    }
}


