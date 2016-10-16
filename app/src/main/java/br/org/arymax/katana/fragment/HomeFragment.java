package br.org.arymax.katana.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import br.org.arymax.katana.R;
import br.org.arymax.katana.adapter.QuestionsRecyclerViewAdapter;
import br.org.arymax.katana.adapter.UserInfoRecycleViewAdapter;
import br.org.arymax.katana.http.QuestionGetTask;
import br.org.arymax.katana.model.Pergunta;

/**
 * Criado por Marco em 16/10/2016.
 */
public class HomeFragment extends Fragment {

    private View rootView;
    private RecyclerView mRecyclerView;
    private List<Pergunta> mPerguntasList;

    private static final String TAG = "HomeFragment.java";

    public HomeFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        callTask();
        return rootView;
    }

    private void callTask()
    {
        QuestionGetTask task = new QuestionGetTask(rootView, getActivity(), this);
        task.execute("data");
    }

    public void setPerguntasList(List perguntasList){
        mPerguntasList = perguntasList;
        Log.d(TAG, "Primeiro item da lista: " + mPerguntasList.get(0).getTitulo());
    }

    public void setRecyclerView(View rootView)
    {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.questions_ReyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new QuestionsRecyclerViewAdapter(mPerguntasList));
    }

}
