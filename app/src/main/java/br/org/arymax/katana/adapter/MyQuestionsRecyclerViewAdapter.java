package br.org.arymax.katana.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.org.arymax.katana.R;
import br.org.arymax.katana.fragment.MyQuestionsFragment;
import br.org.arymax.katana.model.Pergunta;

/**
 * Created by douglas on 16/10/16.
 */

public class MyQuestionsRecyclerViewAdapter extends RecyclerView.Adapter<MyQuestionsRecyclerViewAdapter.ViewHolder> {

    public List<Pergunta> mPerguntasList;
    private Context context;

    public MyQuestionsRecyclerViewAdapter(List<Pergunta> mPerguntasList) {
        this.mPerguntasList = mPerguntasList;
    }

    @Override
    public MyQuestionsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        this.context = parent.getContext();
        return new MyQuestionsRecyclerViewAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_questions_recycler_child, parent, false));
    }

    @Override
    public void onBindViewHolder(MyQuestionsRecyclerViewAdapter.ViewHolder holder, int position)
    {
        holder.txtTitulo.setText(mPerguntasList.get(position).getTitulo());
        holder.txtPergunta.setText(mPerguntasList.get(position).getTexto());
    }

    @Override
    public int getItemCount()
    {
        return mPerguntasList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView txtTitulo;
        TextView txtPergunta;
        ImageView avatar;
        public ViewHolder(View itemView)
        {
            super(itemView);
            txtTitulo = (TextView) itemView.findViewById(R.id.txtTitulo);
            txtPergunta = (TextView) itemView.findViewById(R.id.txtPergunta);
            avatar = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
