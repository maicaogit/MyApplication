package br.org.arymax.katana.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.org.arymax.katana.R;
import br.org.arymax.katana.adapter.MyQuestionsRecyclerViewAdapter;
import br.org.arymax.katana.adapter.QuestionsRecyclerViewAdapter;
import br.org.arymax.katana.http.MyQuestionsGetTask;
import br.org.arymax.katana.model.Pergunta;
import br.org.arymax.katana.utility.Constants;


public class MyQuestionsFragment extends Fragment {


    private View rootView;
    private RecyclerView mRecyclerView;
    private List<Pergunta> mPerguntasList;
    private Context context;

    public static final String My_QUESTIONS_FRAGMENT_TAG = "mqFragment";
    public static final String TAG = "mqFragment";


    public MyQuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        context = container.getContext();
        rootView = inflater.inflate(R.layout.fragment_my_questions, container, false);

        callTask();
        return rootView;
    }

    private void callTask()
    {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCES,0);
        long id = preferences.getLong("pk", 0);
        MyQuestionsGetTask task = new MyQuestionsGetTask(rootView, getActivity(), this);
        task.execute(id);
    }

    public void setPerguntasList(List perguntasList)
    {
        mPerguntasList = perguntasList;
        Log.d(TAG, "Primeiro item da lista: " + mPerguntasList.get(0).getTitulo());
    }

    public void setRecyclerView(View rootView)
    {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.questions_ReyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new MyQuestionsRecyclerViewAdapter(mPerguntasList));
    }
}
