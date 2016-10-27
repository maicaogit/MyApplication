package br.org.arymax.katana.fragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.rey.material.widget.ProgressView;

import java.util.List;

import br.org.arymax.katana.R;
import br.org.arymax.katana.activity.QuestionActivity;
import br.org.arymax.katana.adapter.QuestionsRecyclerViewAdapter;
import br.org.arymax.katana.http.AnswerGetTask;
import br.org.arymax.katana.http.MyQuestionsGetTask;
import br.org.arymax.katana.interfaces.RecyclerViewOnItemClickListener;
import br.org.arymax.katana.model.Pergunta;
import br.org.arymax.katana.utility.Constants;
import br.org.arymax.katana.utility.SerializableListHolder;


public class MyQuestionsFragment extends Fragment implements RecyclerViewOnItemClickListener {


    private View rootView;
    private RecyclerView mRecyclerView;
    private List<Pergunta> mPerguntasList;
    private Context context;
    private SwipeRefreshLayout mSwipe;
    private ProgressView mProgress;
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
        mProgress = (ProgressView) rootView.findViewById(R.id.my_questions_progress);
        mSwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.my_questions_swipe_refresh);
        if(savedInstanceState == null){
            callTask();
        } else {
            SerializableListHolder holder = (SerializableListHolder) savedInstanceState.getSerializable(SerializableListHolder.KEY);
            mPerguntasList = holder.getList();
            if(mPerguntasList == null || mPerguntasList.size() == 0) {
                callTask();
            } else {
                mProgress.setVisibility(View.GONE);
                setViews(rootView);
            }
        }
        setRetainInstance(true);
        return rootView;
    }

    public void callTask() {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCES,0);
        long id = preferences.getLong("pk", 0);
        MyQuestionsGetTask task = new MyQuestionsGetTask((ViewGroup) rootView.findViewById(R.id.rootView), getActivity(), this, mProgress);
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
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mErrorMessageTextView.setVisibility(View.GONE);
                //mRecyclerView.setVisibility(View.GONE);
                callTask();
                mSwipe.setRefreshing(true);
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
        long id = mPerguntasList.get(position).getPkPergunta();
        Intent intent = new Intent(getActivity(), QuestionActivity.class);
        intent.putExtra("pk", id);
        AnswerGetTask task = new AnswerGetTask(getActivity(), intent);
        task.execute(id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        SerializableListHolder holder = new SerializableListHolder(mPerguntasList);
        outState.putSerializable(SerializableListHolder.KEY, holder);
    }
}
