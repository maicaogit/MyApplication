package br.org.arymax.katana.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import java.io.Serializable;
import java.util.List;

import br.org.arymax.katana.R;
import br.org.arymax.katana.activity.QuestionActivity;
import br.org.arymax.katana.adapter.QuestionsRecyclerViewAdapter;
import br.org.arymax.katana.http.AnswerGetTask;
import br.org.arymax.katana.http.QuestionGetTask;
import br.org.arymax.katana.interfaces.RecyclerViewOnItemClickListener;
import br.org.arymax.katana.model.Pergunta;
import br.org.arymax.katana.model.Resposta;

/**
 * Criado por Marco em 16/10/2016.
 */
public class HomeFragment extends Fragment implements RecyclerViewOnItemClickListener {

    private View rootView;
    private RecyclerView mRecyclerView;
    private List<Pergunta> mPerguntasList;
    private List<Resposta> mAnswerList;
    private FloatingActionButton mFabReorganizeList;
    private ProgressBar mProgress;
    private SwipeRefreshLayout mSwipe;
    private TextView mErrorMessageTextView;

    public static final String HOME_FRAGMENT_TAG = "homeFragment";

    private static final String TAG = "HomeFragment.java";

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mProgress = (ProgressBar) rootView.findViewById(R.id.fragment_home_progress_bar);

        callTask();
        return rootView;
    }

    public void callTask() {
        QuestionGetTask task = new QuestionGetTask(rootView, getActivity(), this, mProgress);
        task.execute("data");
    }

    public void setPerguntasList(List perguntasList){
        mPerguntasList = perguntasList;
        Log.d(TAG, "Primeiro item da lista: " + mPerguntasList.get(0).getTitulo());
    }

    public void setViews(View rootView) {

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.questions_ReyclerView);
        mFabReorganizeList = (FloatingActionButton) rootView.findViewById(R.id.fab_organizar_lista);
        mFabReorganizeList.setVisibility(View.GONE);
        mErrorMessageTextView = (TextView) rootView.findViewById(R.id.erro_text_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        QuestionsRecyclerViewAdapter adapter = new QuestionsRecyclerViewAdapter(mPerguntasList);
        adapter.setListener(this);
        mRecyclerView.setAdapter(adapter);

        mSwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_home_fragment);
        mRecyclerView.setVisibility(View.VISIBLE);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mErrorMessageTextView.setVisibility(View.GONE);
                mFabReorganizeList.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                callTask();
                mSwipe.setRefreshing(false);
            }
        });

        mFabReorganizeList.show();
        mFabReorganizeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "VOCÊ CLICOU NA BOLINHA AMARELA", Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0){
                    mFabReorganizeList.hide();
                } else
                    mFabReorganizeList.show();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "Item na posição: " + position, Toast.LENGTH_SHORT).show();
        long id = mPerguntasList.get(position).getPkPergunta();
        Intent intent = new Intent(getActivity(), QuestionActivity.class);
        intent.putExtra("titulo", mPerguntasList.get(position).getTitulo());
        intent.putExtra("pergunta", mPerguntasList.get(position).getTexto());
        intent.putExtra("pkPergunta", mPerguntasList.get(position).getPkPergunta());
        AnswerGetTask task = new AnswerGetTask(getActivity(), this, intent);
        task.execute(id);
    }
}
