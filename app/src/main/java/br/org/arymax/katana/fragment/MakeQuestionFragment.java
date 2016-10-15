package br.org.arymax.katana.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import java.util.Date;

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
                    QuestionPostTask task = new QuestionPostTask(getActivity(), rootView);
                    task.execute(XML);
                    return true;
                } else {
                    Snackbar.make(rootView, "Preencha os campos", Snackbar.LENGTH_LONG).show();
                    return false;
                }
            }
        });

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
