package br.org.arymax.katana.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.rey.material.widget.SnackBar;

import br.org.arymax.katana.R;
import br.org.arymax.katana.activity.UserActivity;
import br.org.arymax.katana.http.QuestionPostTask;
import br.org.arymax.katana.model.Pergunta;
import br.org.arymax.katana.utility.Constants;
import br.org.arymax.katana.utility.XMLParser;


public class MakeQuestionFragment extends Fragment {

    private View rootView;
    private EditText mQuestionTitle;
    private EditText mQuestionContent;
    private SwitchCompat mAnonymousMode;
    private boolean anonymousMode = false;

    private static final String TAG = "MakeQuestionFragment";
    public static final String MAKE_QUESTION_FRAGMENT_TAG = "mkqFragment";

    public MakeQuestionFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_make_question, container, false);

        mQuestionTitle = (EditText) rootView.findViewById(R.id.edit_text_titulo);
        mQuestionContent = (EditText) rootView.findViewById(R.id.text_pergunta);
        mAnonymousMode = (SwitchCompat) rootView.findViewById(R.id.modo_anonimo);

        mAnonymousMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                anonymousMode = isChecked;
            }
        });

        final ViewGroup root = (ViewGroup) rootView.findViewById(R.id.make_question_root);

        Menu menu = UserActivity.mMenu;

        MenuItem ok = menu.findItem(R.id.action_enviar);

        ok.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(areFieldsFilled()){
                    SharedPreferences preferences = getActivity().getSharedPreferences(Constants.PREFERENCES, 0);
                    long pk = preferences.getLong("pk", -1);
                    String title = mQuestionTitle.getText().toString();
                    String content = mQuestionContent.getText().toString();
                    Pergunta pergunta = new Pergunta(title, content, anonymousMode, pk);
                    String XML = XMLParser.objectToXML(pergunta, Pergunta.class);
                    Log.d(TAG, "XML da pergunta: " + XML);
                    Intent intent = new Intent(getActivity(), UserActivity.class);
                    QuestionPostTask task = new QuestionPostTask(getActivity(), root, intent);
                    task.execute(XML);///
                    return true;
                } else {
                   SnackBar snackBar = SnackBar.make(getContext())
                            .text("Preencha os campos")
                            .singleLine(true)
                            .actionText("Fechar")
                            .duration(3000)
                            .actionClickListener(new SnackBar.OnActionClickListener() {
                                @Override
                                public void onActionClick(SnackBar sb, int actionId) {
                                    sb.dismiss();
                                }
                            });

                    snackBar.applyStyle(com.rey.material.R.style.Material_Widget_SnackBar_Mobile);
                    snackBar.actionTextColor(getActivity().getResources().getColor(R.color.colorAccent));
                    snackBar.show(root);
                    return false;
                }
            }
        });
        setRetainInstance(true);
        return rootView;
    }

    private boolean areFieldsFilled(){
        String title = mQuestionTitle.getText().toString();
        String content = mQuestionContent.getText().toString();
        if(title.equals("") || content.equals(""))
            return false;
        else
            return true;
    }


}
