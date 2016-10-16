package br.org.arymax.katana.fragment;

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
import android.widget.Button;
import android.widget.ProgressBar;

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
    private FloatingActionButton mFabReorganizeList;
    private ProgressBar mProgress;
    private SwipeRefreshLayout mSwipe;

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

    private void callTask() {
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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new QuestionsRecyclerViewAdapter(mPerguntasList));

        mSwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_home_fragment);
        mRecyclerView.setVisibility(View.VISIBLE);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFabReorganizeList.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                callTask();
                mSwipe.setRefreshing(false);
            }
        });

        mFabReorganizeList.show();

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

}
