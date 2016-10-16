package br.org.arymax.katana.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.org.arymax.katana.R;
import br.org.arymax.katana.utility.Constants;

/**
 * Created by douglas on 15/10/16.
 */

public class UserInfoRecycleViewAdapter extends RecyclerView.Adapter<UserInfoRecycleViewAdapter.ViewHolder>
{
    public Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_info_recycler_child, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCES, 0);

        switch (position)
        {
            case 0: holder.label.setText("Nome"); holder.cont.setText(preferences.getString("nome", ""));break;
            case 1: holder.label.setText("Prontuario");holder.cont.setText(preferences.getString("prontuario", "")); break;
            case 2: holder.label.setText("Email");holder.cont.setText("Email"); break;
            case 3: holder.label.setText("Senha");holder.cont.setText(preferences.getString("zze", "")); break;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView label;
        TextView cont;
        public ViewHolder(View itemView)
        {
            super(itemView);

            label = (TextView) itemView.findViewById(R.id.label);
            cont =  (TextView) itemView.findViewById(R.id.txtCont);

        }
    }
}
