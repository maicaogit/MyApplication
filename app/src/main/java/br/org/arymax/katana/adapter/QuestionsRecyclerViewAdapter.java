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
import br.org.arymax.katana.interfaces.RecyclerViewOnLoadMoreListener;
import br.org.arymax.katana.model.Pergunta;

/**
 * Created by douglas on 16/10/16.
 */

public class QuestionsRecyclerViewAdapter extends RecyclerView.Adapter<QuestionsRecyclerViewAdapter.ViewHolder> {

    private List<Pergunta> mPerguntasList;
    private RecyclerViewOnItemClickListener mOnItemClickListener;
    private RecyclerViewOnLoadMoreListener mOnLoadMoreClickListener;
    private Context context;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public QuestionsRecyclerViewAdapter(List<Pergunta> mPerguntasList) {
        this.mPerguntasList = mPerguntasList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_recycler_child, parent, false);
        return new QuestionsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtTitulo.setText(mPerguntasList.get(position).getTitulo());
        holder.txtPergunta.setText(mPerguntasList.get(position).getTexto());

    }

    @Override
    public int getItemCount() {
        return mPerguntasList.size();
    }

    public void setOnItemClickListener(RecyclerViewOnItemClickListener l){
        mOnItemClickListener = l;
    }

    public void setOnLoadMoreClickListener(RecyclerViewOnLoadMoreListener l){
        mOnLoadMoreClickListener = l;
    }

    public void addListItem(Pergunta p, int position){
        mPerguntasList.add(p);
        notifyItemInserted(position);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtTitulo;
        TextView txtPergunta;
        ImageView avatar;
        public ViewHolder(View itemView)
        {
            super(itemView);
            txtTitulo = (TextView) itemView.findViewById(R.id.txtTitulo);
            txtPergunta = (TextView) itemView.findViewById(R.id.txtPergunta);
            avatar = (ImageView) itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null){
                mOnItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }
}
