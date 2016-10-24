package br.org.arymax.katana.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.org.arymax.katana.R;
import br.org.arymax.katana.adapter.QuestionReplyRecyclerViewAdapter;
import br.org.arymax.katana.interfaces.RecyclerViewOnLongClickListener;
import br.org.arymax.katana.model.Resposta;

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

        mAnswerPanel.setVisibility(View.GONE);

        mBtnAnswer.setOnClickListener(this);
        mBtnFlag.setOnClickListener(this);

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
                //Denunciar a pergunta
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
