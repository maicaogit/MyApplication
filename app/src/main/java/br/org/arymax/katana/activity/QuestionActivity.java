package br.org.arymax.katana.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.org.arymax.katana.R;
import br.org.arymax.katana.adapter.QuestionReplyRecyclerViewAdapter;
import br.org.arymax.katana.http.AnswerPostTask;
import br.org.arymax.katana.interfaces.RecyclerViewOnLongClickListener;
import br.org.arymax.katana.model.Pergunta;
import br.org.arymax.katana.model.Resposta;
import br.org.arymax.katana.model.Usuario;
import br.org.arymax.katana.utility.Constants;
import br.org.arymax.katana.utility.XMLParser;

public class QuestionActivity extends AppCompatActivity implements RecyclerViewOnLongClickListener,
View.OnClickListener{

    private List<Resposta> mAnswerList;
    private TextView mTitle;
    private TextView mText;
    private TextView mNoAnswersMessage;
    private RecyclerView mRecyclerViewAnswers;
    private QuestionReplyRecyclerViewAdapter mAdapter;
    private Button mBtnAnswer;
    private Button mBtnFlag;
    private EditText mAnswerArea;
    private View mRevealView;
    private LinearLayout mAnswerPanel;
    private ImageView mSend;
    private long pkPergunta;

    private static final String TAG = "QuestionActivity.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        mTitle = (TextView) findViewById(R.id.tv_title);
        mText = (TextView) findViewById(R.id.tv_text);
        mRecyclerViewAnswers = (RecyclerView) findViewById(R.id.rv_respostas);
        mNoAnswersMessage = (TextView) findViewById(R.id.tv_no_answers);
        mBtnAnswer = (Button) findViewById(R.id.btn_reply);
        mBtnFlag = (Button) findViewById(R.id.btn_flag);
        mAnswerArea = (EditText) findViewById(R.id.text_answer);
        mRevealView = findViewById(R.id.reveal_view);
        mAnswerPanel = (LinearLayout) findViewById(R.id.answer_panel);
        mSend = (ImageView) findViewById(R.id.iv_send);

        mAnswerPanel.setVisibility(View.GONE);

        mBtnAnswer.setOnClickListener(this);
        mBtnFlag.setOnClickListener(this);
        mSend.setOnClickListener(this);

        mAnswerArea.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    mRevealView.setVisibility(View.VISIBLE);
                } else {
                    mRevealView.setVisibility(View.GONE);
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            mAnswerList = (List<Resposta>) bundle.getSerializable("respostas");
            if(mAnswerList.size() > 0){
                Toast.makeText(this, "IGÃO CARAIO", Toast.LENGTH_LONG).show();
                mNoAnswersMessage.setVisibility(View.GONE);
                mAdapter = new QuestionReplyRecyclerViewAdapter(mAnswerList);
                mAdapter.setOnLongClickListener(this);
                mRecyclerViewAnswers.setAdapter(mAdapter);
            } else {
                mAnswerList = new ArrayList<>();
            }
            String title = bundle.getString("titulo");
            String texto = bundle.getString("pergunta");
            mTitle.setText(title);
            mText.setText(texto);
            pkPergunta = bundle.getLong("pkPergunta");
        }

    }

    @Override
    public boolean onLongClick(View view, int position) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_flag:

                break;

            case R.id.btn_reply:
                mAnswerPanel.setVisibility(View.VISIBLE);
                mAnswerArea.requestFocus();
                mAnswerArea.post(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                    }
                });
                break;

            case R.id.iv_send:
                if(!mAnswerArea.getText().toString().equals("")){
                    mAnswerArea.clearFocus();
                    mAnswerPanel.setVisibility(View.GONE);
                    AnswerPostTask task = new AnswerPostTask(this);
                    Resposta resposta = new Resposta();
                    resposta.setResposta(mAnswerArea.getText().toString());
                    mAnswerArea.setText("");
                    resposta.setUsuario(new Usuario(getSharedPreferences(Constants.PREFERENCES, 0).getLong("pk", -1)));
                    resposta.setPergunta(new Pergunta(pkPergunta));
                    String xmlResposta = XMLParser.objectToXML(resposta, Resposta.class);
                    Log.d(TAG, "XML da resposta: " + xmlResposta);
                    task.execute(xmlResposta);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mRevealView.getVisibility() == View.VISIBLE){
            mAnswerArea.clearFocus();
            mAnswerPanel.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }
}
