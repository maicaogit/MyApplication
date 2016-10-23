package br.org.arymax.katana.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.org.arymax.katana.R;
import br.org.arymax.katana.adapter.QuestionsRecyclerViewAdapter;
import br.org.arymax.katana.http.MyQuestionsGetTask;
import br.org.arymax.katana.interfaces.RecyclerViewOnItemClickListener;
import br.org.arymax.katana.model.Pergunta;
import br.org.arymax.katana.utility.Constants;


public class MyQuestionsFragment extends Fragment implements RecyclerViewOnItemClickListener {


    private View rootView;
    private RecyclerView mRecyclerView;
    private List<Pergunta> mPerguntasList;
    private Context context;
    private SwipeRefreshLayout mSwipe;
    private ProgressBar mProgress;
    private TextView mErrorMessageTextView;

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
                             Bundle savedInstanceState){
        context = container.getContext();
        rootView = inflater.inflate(R.layout.fragment_my_questions, container, false);
        mProgress = (ProgressBar) rootView.findViewById(R.id.fragment_my_questions_progress_bar);
        mSwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.my_questions_swipe_refresh);

        callTask();
        return rootView;
    }

    public void callTask() {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCES,0);
        long id = preferences.getLong("pk", 0);
        MyQuestionsGetTask task = new MyQuestionsGetTask(rootView, getActivity(), this, mProgress);
        task.execute(id);
    }

    public void setPerguntasList(List perguntasList) {
        mPerguntasList = perguntasList;
        Log.d(TAG, "Primeiro item da lista: " + mPerguntasList.get(0).getTitulo());
    }

    public void setViews(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.questions_ReyclerView);
        mErrorMessageTextView = (TextView) rootView.findViewById(R.id.erro_text_view);

        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        QuestionsRecyclerViewAdapter adapter = new QuestionsRecyclerViewAdapter(mPerguntasList);
        adapter.setListener(this);
        mRecyclerView.setAdapter(adapter);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mErrorMessageTextView.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                callTask();
                mSwipe.setRefreshing(false);
            }
        });

        mErrorMessageTextView.setClickable(true);
        mErrorMessageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mErrorMessageTextView.setVisibility(View.GONE);
                callTask();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "Item na posição: " + position, Toast.LENGTH_SHORT).show();
    }
}
