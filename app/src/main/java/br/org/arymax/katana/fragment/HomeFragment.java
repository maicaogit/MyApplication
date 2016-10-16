package br.org.arymax.katana.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import br.org.arymax.katana.R;
import br.org.arymax.katana.http.QuestionGetTask;
import br.org.arymax.katana.model.Pergunta;

/**
 * Criado por Marco em 16/10/2016.
 */
public class HomeFragment extends Fragment {

    private View rootView;
    private Button teste;
    private List<Pergunta> mPerguntasList;

    private static final String TAG = "HomeFragment.java";

    public HomeFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        teste = (Button) rootView.findViewById(R.id.button);

        teste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTask();
            }
        });

        return rootView;
    }

    private void callTask(){
        QuestionGetTask task = new QuestionGetTask(getActivity(), this);
        task.execute("data");
    }

    public void setPerguntasList(List perguntasList){
        mPerguntasList = perguntasList;
        Log.d(TAG, "Primeiro item da lista: " + mPerguntasList.get(0).getTitulo());
    }

}
