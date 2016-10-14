package br.org.arymax.katana.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.org.arymax.katana.R;
import br.org.arymax.katana.http.RegisterTask;
import br.org.arymax.katana.model.Usuario;
import br.org.arymax.katana.utility.XMLParser;

/**
 * Criado por Marco em 12/10/2016.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mButtonRegister;
    private EditText mEditTextPront;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;

    private static final String TAG = "RegisterActivity.java";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mButtonRegister = (Button) findViewById(R.id.btn_cadastrar);
        mEditTextEmail = (EditText) findViewById(R.id.edit_text_email);
        mEditTextPront = (EditText) findViewById(R.id.edit_text_prontuario);
        mEditTextPassword = (EditText) findViewById(R.id.edit_text_password);

        mEditTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        mButtonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.btn_cadastrar:
                if(areFieldsFilled()){
                    String email = mEditTextEmail.getText().toString();
                    String pront = mEditTextPront.getText().toString();
                    String password = mEditTextPassword.getText().toString();
                    RegisterTask task = new RegisterTask(this);
                    Usuario user = new Usuario("Maicão", pront, email, password);
                    String xml = XMLParser.objectToXML(user, Usuario.class);
                    Log.d(TAG, "XML do usuário: " + xml);
                    task.execute(xml);
                } else {
                    String errorMessage = getResources().getString(R.string.preencher_campos);
                    if(mEditTextEmail.getText().toString().equals("")){
                        mEditTextEmail.setError(errorMessage);
                    }
                    if(mEditTextPront.getText().toString().equals("")){
                        mEditTextPront.setError(errorMessage);
                    }
                    if(mEditTextPassword.getText().toString().equals("")){
                        mEditTextPassword.setError(errorMessage);
                    }
                }
                break;
        }
    }

    /**
     * Verifica se os campos estão preenchidos
     * @return true
     *              se os campos estiverem preenchidos
     *         false
     *              se os campos não estiverem preenchidos
     */
    private boolean areFieldsFilled(){
        if(mEditTextPront.getText().toString().equals("") ||
                mEditTextPassword.getText().toString().equals("") ||
                mEditTextEmail.getText().toString().equals("")){
            return false;
        } else
            return true;
    }

}
