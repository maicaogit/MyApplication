package br.org.arymax.katana.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.org.arymax.katana.R;
import br.org.arymax.katana.adapter.UserInfoRecyclerViewAdapter;

public class UserInfoFragment extends Fragment {

    public static final String USER_INFO_FRAGMENT_TAG = "usiFragment";
    private RecyclerView mRecyclerView;

    public UserInfoFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_user_info, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.user_ReyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setAdapter(new UserInfoRecyclerViewAdapter(getActivity()));
        setRetainInstance(true);
        return v;
    }

}
