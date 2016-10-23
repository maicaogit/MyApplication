package br.org.arymax.katana.http;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    private ProgressBar mProgress;
    private HomeFragment mCallerFragment;
    private View rootView;
    private String ordenacao = "";

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
        ordenacao = params[0];
        String xmlQuestion = "";
        try{
            xmlQuestion = ServerCalls.callGet(
                    ServerCalls.SERVER_URL + "funcoes" + ServerCalls.GET_ALL_QUESTIONS_PATH  + ordenacao,
                    ServerCalls.GET,
                    ServerCalls.TEXT_XML
            );
        } catch (Exception e){
            Log.e(TAG, "Exceção lançada: ", e);
            this.cancel(true);
        }
        Log.d(TAG, "XML: " + xmlQuestion);
        return xmlQuestion;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(TAG, "onPostExecute");
        if(!result.equals("")){
            List<Pergunta> listPerguntas = XMLParser.xmlToListObject(result, ArrayPerguntas.class, Pergunta.class);
            if(listPerguntas != null){
                mCallerFragment.setPerguntasList(listPerguntas);
                mCallerFragment.setViews(rootView);
                mProgress.setVisibility(View.GONE);
            } else {
                mProgress.setVisibility(View.GONE);
                ((TextView) rootView.findViewById(R.id.erro_text_view)).setText(R.string.no_questions);
                rootView.findViewById(R.id.erro_text_view).setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onCancelled() {
        Snackbar snackbar = Snackbar.make(rootView, R.string.questions_error, Snackbar.LENGTH_INDEFINITE)

                .setAction(R.string.try_again, new View.OnClickListener() {
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
