package br.org.arymax.katana.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import br.org.arymax.katana.R;
import br.org.arymax.katana.model.Pergunta;
import br.org.arymax.katana.model.Resposta;

public class QuestionActivity extends AppCompatActivity {

    private List<Resposta> mAnswerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
    }


    public void setAnswerList(List<Resposta> list){
        mAnswerList = list;
    }
}
