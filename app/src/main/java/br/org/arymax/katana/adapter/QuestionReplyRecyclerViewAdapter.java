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
import br.org.arymax.katana.interfaces.RecyclerViewOnItemClickListener;
import br.org.arymax.katana.model.Pergunta;

/**
 * Created by douglas on 16/10/16.
 */

public class QuestionReplyRecyclerViewAdapter extends RecyclerView.Adapter<QuestionReplyRecyclerViewAdapter.ViewHolder> {

    public List<Pergunta> mPerguntasList;
    private Context context;
    private RecyclerViewOnItemClickListener listener;

    public QuestionReplyRecyclerViewAdapter(List<Pergunta> mPerguntasList) {
        this.mPerguntasList = mPerguntasList;
    }

    @Override
    public QuestionReplyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new QuestionReplyRecyclerViewAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_questions_recycler_child, parent, false));
    }

    @Override
    public void onBindViewHolder(QuestionReplyRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.txtTitulo.setText(mPerguntasList.get(position).getTitulo());
        holder.txtPergunta.setText(mPerguntasList.get(position).getTexto());
    }

    @Override
    public int getItemCount()
    {
        return mPerguntasList.size();
    }

    public void setListener(RecyclerViewOnItemClickListener l){
        listener = l;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtTitulo;
        TextView txtPergunta;
        ImageView avatar;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitulo = (TextView) itemView.findViewById(R.id.txtTitulo);
            txtPergunta = (TextView) itemView.findViewById(R.id.txtPergunta);
            avatar = (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listener != null){
                listener.onItemClick(v, getLayoutPosition());
            }
        }
    }
}
