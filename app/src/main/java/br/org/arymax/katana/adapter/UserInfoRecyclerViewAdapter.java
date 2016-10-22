package br.org.arymax.katana.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import br.org.arymax.katana.R;
import br.org.arymax.katana.http.ChangeUserInfoTask;
import br.org.arymax.katana.http.RegisterTask;
import br.org.arymax.katana.model.Usuario;
import br.org.arymax.katana.utility.Constants;
import br.org.arymax.katana.utility.XMLParser;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by douglas on 15/10/16.
 */

public class UserInfoRecyclerViewAdapter extends RecyclerView.Adapter<UserInfoRecyclerViewAdapter.ViewHolder>{

    private Context context;
    public static final String TAG = "Script";

    public UserInfoRecyclerViewAdapter(Context context){
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_info_recycler_child, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
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
                                   setAlertDialog(holder, 1);
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
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        switch (item.getItemId()){
                            case R.id.action_edit:
                                setAlertDialog(holder, 2);
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
                        switch (item.getItemId())
                        {
                            case R.id.action_edit:

                                //setAlertDialog(holder, 3);
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




    public void setAlertDialog(final ViewHolder holder, final int code) {
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_edit_user_info, null);
        final EditText txtEdt = (EditText) v.findViewById(R.id.txtEdit);
        final MaterialDialog mMaterialDialog = new MaterialDialog(context);
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCES, 0);

        txtEdt.setText(holder.editInfoToolbar.getSubtitle());
        mMaterialDialog
                .setView(v)
                .setCanceledOnTouchOutside(true)
                .setPositiveButton("Alterar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Ele chamara uma AsyncTask no futuro, mas por enquanto só altera
                        //o SharedPreference (Ou o label caso n de certo)

                        String dadoAlt = txtEdt.getText().toString();
                        switch (code) {
                            case 1:
                                callTask(dadoAlt, 1, holder);
                                txtEdt.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                                break;
                            case 2:
                                txtEdt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                                callTask(dadoAlt, 2, holder);
                                break;

                        }
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("Cancelar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
        txtEdt.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            }
        });
        txtEdt.selectAll();
        txtEdt.requestFocus();
    }


    public void callTask(String dadoAlt, int code, ViewHolder holder) {
        String email = null;
        String nome = null;
        final SharedPreferences sp = context.getSharedPreferences(Constants.PREFERENCES, 0);
        switch (code) {
            case 1:
                nome = dadoAlt;
                email = sp.getString("email", "");
                holder.editInfoToolbar.setSubtitle(nome);
                break;
            case 2:
                email = dadoAlt;
                nome = sp.getString("nome", "");
                break;
        }

        String pront = sp.getString("prontuario", "");
        long pk = sp.getLong("pk", 0);
        ChangeUserInfoTask task = new ChangeUserInfoTask(context, code);
        Usuario user = new Usuario(pk, nome, pront, email);
        String xml = XMLParser.objectToXML(user, Usuario.class);
        Log.i(TAG, "XML do usuário: " + xml);
        //task.execute(xml);
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
