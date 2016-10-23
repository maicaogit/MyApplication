package br.org.arymax.katana.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.org.arymax.katana.R;
import br.org.arymax.katana.model.Pergunta;
import br.org.arymax.katana.model.Resposta;

public class QuestionActivity extends AppCompatActivity {

    private List<Resposta> mAnswerList;
    private TextView mTitle;
    private TextView mText;
    private RecyclerView mRecyclerViewAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        mTitle = (TextView) findViewById(R.id.tv_title);
        mText = (TextView) findViewById(R.id.tv_text);
        mRecyclerViewAnswers = (RecyclerView) findViewById(R.id.rv_respostas);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            mAnswerList = (List<Resposta>) bundle.getSerializable("respostas");
            if(mAnswerList == null){
                Toast.makeText(this, "IGÃO CARAIO", Toast.LENGTH_LONG).show();
            }
            String title = bundle.getString("titulo");
            String texto = bundle.getString("pergunta");
            mTitle.setText(title);
            mText.setText(texto);

        }

    }
}
