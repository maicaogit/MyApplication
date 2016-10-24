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
import br.org.arymax.katana.interfaces.RecyclerViewOnLongClickListener;
import br.org.arymax.katana.model.Resposta;

/**
 * Created by douglas on 16/10/16.
 */

public class QuestionReplyRecyclerViewAdapter extends RecyclerView.Adapter<QuestionReplyRecyclerViewAdapter.ViewHolder> {

    public List<Resposta> mRespostasList;
    private Context context;
    private RecyclerViewOnItemClickListener mOnItemClickListener;
    private RecyclerViewOnLongClickListener mOnLongClickListener;

    public QuestionReplyRecyclerViewAdapter(List<Resposta> list) {
        this.mRespostasList = list;
    }

    @Override
    public QuestionReplyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new QuestionReplyRecyclerViewAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.answers_recycler_child, parent, false));
    }

    @Override
    public void onBindViewHolder(QuestionReplyRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.textResposta.setText(mRespostasList.get(position).getResposta());
    }

    @Override
    public int getItemCount()
    {
        return mRespostasList.size();
    }

    public void setOnItemClickListener(RecyclerViewOnItemClickListener l){
        mOnItemClickListener = l;
    }

    public void setOnLongClickListener(RecyclerViewOnLongClickListener l){
        mOnLongClickListener = l;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,
    View.OnClickListener{

        private TextView textResposta;
        private ImageView avatar;
        public ViewHolder(View itemView) {
            super(itemView);
            textResposta = (TextView) itemView.findViewById(R.id.txt_resposta);
            avatar = (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }


        @Override
        public boolean onLongClick(View v) {
            if(mOnLongClickListener != null){
                mOnLongClickListener.onLongClick(v, getLayoutPosition());
            }
            return false;
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null){
                mOnItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }
}
