package br.org.arymax.katana.http;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;
import com.rey.material.widget.SnackBar;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.extended.ISO8601DateConverter;

import java.util.ArrayList;
import java.util.List;

import br.org.arymax.katana.R;
import br.org.arymax.katana.fragment.MyQuestionsFragment;
import br.org.arymax.katana.model.ArrayPerguntas;
import br.org.arymax.katana.model.Pergunta;

/**
 * Criado por Marco em 16/10/2016.
 */
public class MyQuestionsGetTask extends AsyncTask<Long, Void, String> {

    private Context mContext;
    private ProgressView mProgress;
    private MyQuestionsFragment mCallerFragment;
    private ViewGroup rootView;

    private static final String TAG = "MyQuestionsTask.java";

    public MyQuestionsGetTask(ViewGroup rootView, Context context, MyQuestionsFragment callerFragment, ProgressView p) {
        mContext = context;
        this.rootView = rootView;
        mCallerFragment = callerFragment;
        mProgress = p;
    }

    @Override
    protected void onPreExecute() {

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
            if(listPerguntas.getPerguntas() != null){
                for(int i = 0; i < listPerguntas.getPerguntas().size(); i++){
                    listPergunta.add(listPerguntas.getPerguntas().get(i));
                }
                mCallerFragment.setPerguntasList(listPergunta);
                mCallerFragment.setViews(rootView);
                mProgress.setVisibility(View.GONE);
                ((SwipeRefreshLayout)(((AppCompatActivity) mContext)
                        .findViewById(R.id.my_questions_swipe_refresh))).setRefreshing(false);
            } else {
                mProgress.setVisibility(View.GONE);
                ((TextView) rootView.findViewById(R.id.erro_text_view)).setText(R.string.no_questions);
                (rootView.findViewById(R.id.erro_text_view)).setVisibility(View.VISIBLE);
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
                            mCallerFragment.callTask();
                        }
                    }
                });
        snackBar.applyStyle(com.rey.material.R.style.Material_Widget_SnackBar_Mobile);
        snackBar.actionTextColor(mContext.getResources().getColor(R.color.colorAccent));
        snackBar.show(rootView);
    }
}


