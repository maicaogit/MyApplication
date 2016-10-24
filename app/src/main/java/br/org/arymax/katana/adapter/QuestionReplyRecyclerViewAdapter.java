package br.org.arymax.katana.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import java.util.Date;
import java.util.List;

import br.org.arymax.katana.R;
import br.org.arymax.katana.interfaces.RecyclerViewOnItemClickListener;
import br.org.arymax.katana.interfaces.RecyclerViewOnLongClickListener;
import br.org.arymax.katana.model.Resposta;

/**
 * Created by douglas on 16/10/16.
 */

public class QuestionReplyRecyclerViewAdapter extends RecyclerView.Adapter<QuestionReplyRecyclerViewAdapter.ViewHolder> {

    private List<Resposta> mRespostasList;
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
        holder.userName
                .setText(mRespostasList.get(position).getUsuario().getNome() + " " + context.getResources().getString(R.string.said));
        holder.textResposta.setText(mRespostasList.get(position).getResposta());
        Date date = mRespostasList.get(position).getData();
        DateTime dataResposta = new DateTime(date);
        DateTime dataAtual = new DateTime();
        Days days = Days.daysBetween(dataResposta, dataAtual);
        if(days.getDays() < 1){
            Hours hours = Hours.hoursBetween(dataResposta, dataAtual);
            if(hours.getHours() < 1){
                Minutes minutes = Minutes.minutesBetween(dataResposta, dataAtual);
                if(minutes.getMinutes() < 1){
                    Seconds seconds = Seconds.secondsBetween(dataResposta, dataAtual);
                    holder.data.setText(seconds.getSeconds() + "s");
                } else {
                    holder.data.setText(minutes.getMinutes() + "m");
                }
            } else {
                holder.data.setText(hours.getHours() + "h");
            }
        } else {
            holder.data.setText(days.getDays() + "d");
        }
    }

    @Override
    public int getItemCount() {
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

        private TextView userName;
        private TextView textResposta;
        private TextView data;
        private ImageView avatar;
        public ViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.tv_username);
            textResposta = (TextView) itemView.findViewById(R.id.tv_resposta);
            data = (TextView) itemView.findViewById(R.id.tv_data);
            avatar = (ImageView) itemView.findViewById(R.id.img_user);
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
