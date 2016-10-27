package br.org.arymax.katana.http;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;
import com.rey.material.widget.SnackBar;

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
    private ProgressView mProgress;
    private HomeFragment mCallerFragment;
    private ViewGroup rootView;
    private String ordenacao = "";

    private static final String TAG = "QuestionGetTask.java";

    public QuestionGetTask(ViewGroup rootView, Context context, HomeFragment callerFragment, ProgressView p) {
        mContext = context;
        mCallerFragment = callerFragment;
        this.rootView = rootView;
        mProgress = p;
    }

    @Override
    protected void onPreExecute() {

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
                ((SwipeRefreshLayout)(((AppCompatActivity) mContext)
                        .findViewById(R.id.swipe_refresh_home_fragment))).setRefreshing(false);
            } else {
                mProgress.setVisibility(View.GONE);
                ((TextView) rootView.findViewById(R.id.erro_text_view)).setText(R.string.no_questions);
                rootView.findViewById(R.id.erro_text_view).setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onCancelled() {
        Log.d(TAG, "onCancelled()");
        SnackBar snackBar = SnackBar.make(mContext).text(R.string.questions_error)
                .actionText(R.string.try_again)
                .actionId(R.string.try_again)
                .actionClickListener(new SnackBar.OnActionClickListener() {
                    @Override
                    public void onActionClick(SnackBar sb, int actionId) {
                        if(actionId == R.string.try_again){
                            mCallerFragment.callTask(ordenacao);
                        }
                    }
                });

        snackBar.applyStyle(com.rey.material.R.style.Material_Widget_SnackBar_Mobile);
        snackBar.actionTextColor(mContext.getResources().getColor(R.color.colorAccent));
        snackBar.show(rootView);
    }
}
