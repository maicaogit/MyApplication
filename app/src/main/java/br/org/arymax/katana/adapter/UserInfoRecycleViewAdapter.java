package br.org.arymax.katana.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import br.org.arymax.katana.R;
import br.org.arymax.katana.utility.Constants;

/**
 * Created by douglas on 15/10/16.
 */

public class UserInfoRecycleViewAdapter extends RecyclerView.Adapter<UserInfoRecycleViewAdapter.ViewHolder>{

    private Context context;

    public UserInfoRecycleViewAdapter(Context context){
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_info_recycler_child, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCES, 0);
        LinearLayout layout = holder.layout;

        switch (position) {
            case 0:
                holder.label.setText("Nome");
                holder.editInfoToolbar.setSubtitle((preferences.getString("nome", "")));
                holder.editInfoToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_edit:
                                Toast.makeText(context, "MUDOU O NOME VIADO", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });
                break;

            case 1:
                layout.removeView(holder.profileImageLayout);
                holder.label.setText("Prontuário");
                holder.editInfoToolbar.setSubtitle((preferences.getString("prontuario", "")));
                MenuItem item = holder.editInfoToolbar.getMenu().getItem(0);
                item.setVisible(false);
                break;

            case 2:
                layout.removeView(holder.profileImageLayout);
                holder.label.setText("Email");
                holder.editInfoToolbar.setSubtitle(preferences.getString("email", "N/A"));
                holder.editInfoToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_edit:

                                break;
                        }
                        return false;
                    }
                });
                break;

            case 3:
                layout.removeView(holder.profileImageLayout);
                holder.label.setText("Senha");
                holder.editInfoToolbar.setSubtitle("********");
                holder.editInfoToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_edit:
                                break;
                        }
                        return false;
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    //ViewHolder

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView label;
        private FrameLayout profileImageLayout;
        private LinearLayout layout;
        private Toolbar editInfoToolbar;
        private FloatingActionButton fabChangePhoto;
        private View rootView;

        private ViewHolder(View itemView) {
            super(itemView);

            rootView = itemView;
            label = (TextView) rootView.findViewById(R.id.label);
            profileImageLayout = (FrameLayout) rootView.findViewById(R.id.profile_layout);
            layout = (LinearLayout) rootView.findViewById(R.id.ll_user_info);
            editInfoToolbar = (Toolbar) rootView.findViewById(R.id.toolbar_holder);
            fabChangePhoto = (FloatingActionButton) rootView.findViewById(R.id.change_photo);


            fabChangePhoto.setOnClickListener(this);

            editInfoToolbar.inflateMenu(R.menu.viewholder);
            MenuItem item = editInfoToolbar.getMenu().getItem(0);
            Drawable drawable = editInfoToolbar.getMenu().getItem(0).getIcon();
            drawable.setColorFilter(context.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            item.setIcon(drawable);
        }

        @Override
        public void onClick(View view){
            int id = view.getId();

            switch (id){
                case R.id.change_photo:
                    Toast.makeText(context, "MUDOU A FOTO VIADO", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
