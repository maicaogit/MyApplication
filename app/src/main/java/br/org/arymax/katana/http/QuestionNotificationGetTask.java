package br.org.arymax.katana.http;

import android.content.Context;
import android.os.AsyncTask;
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
 * Criado por Marco em 24/10/2016.
 */
public class QuestionNotificationGetTask extends AsyncTask<Long, Void, String> {
    private Context mContext;
    private ProgressBar mProgress;
    private HomeFragment mCallerFragment;
    private View rootView;
    private String ordenacao = "";
    private static final String TAG = "QuestionNotificationGetTask.java";

    public QuestionNotificationGetTask(View rootView, Context context, HomeFragment callerFragment, ProgressBar p) {
        mContext = context;
        mCallerFragment = callerFragment;
        this.rootView = rootView;
        mProgress = p;
    }
    @Override
    protected String doInBackground(Long... params) {
        Long param = params[0];
        String result = "";
        try{
            result = ServerCalls.callGet(ServerCalls.SERVER_URL + "funcoes" + ServerCalls.SET_NOTIFICATION+ params,
                    ServerCalls.GET,
                    ServerCalls.TEXT_XML
            );
        } catch (Exception e){
            this.cancel(true);
        }
        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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
}
