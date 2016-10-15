package br.org.arymax.katana.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.org.arymax.katana.R;
import br.org.arymax.katana.http.UserLoginTask;
import br.org.arymax.katana.utility.Constants;
import br.org.arymax.katana.utility.Validacao;

import static java.lang.System.out;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonLogin;
    private TextView mTextViewRegister;
    private EditText mEditTextUser;
    private EditText mEditTextPassword;

    //MAICUDO
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCES, 0);
        boolean logado = preferences.getBoolean("logado", false);
        if(logado){
            Intent intent = new Intent(LoginActivity.this, UserActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_login);

        mButtonLogin = (Button) findViewById(R.id.btn_login);
        mTextViewRegister = (TextView) findViewById(R.id.text_cadastrar);
        mEditTextUser = (EditText) findViewById(R.id.edit_text_pront);
        mEditTextPassword = (EditText) findViewById(R.id.edit_text_senha);

        mEditTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mTextViewRegister.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.btn_login:
                String senha = mEditTextPassword.getText().toString();
                String pront = mEditTextUser.getText().toString();
                if(senha.equals("") || pront.equals("")){
                    String errorMessage = getResources().getString(R.string.preencher_campos);
                    if(senha.equals("")){
                        mEditTextPassword.setError(errorMessage);
                    }
                    if(pront.equals("")){
                        mEditTextUser.setError(errorMessage);
                    }
                } else if(pront.length() < 7){
                    String invalidPront = getResources().getString(R.string.campo_invalido);
                    mEditTextUser.setError(invalidPront);
                } else {
                    String prontuario = mEditTextUser.getText().toString().substring(0,6);
                    out.println(prontuario);
                    String dvUsuario = mEditTextUser.getText().toString().substring(6);
                    if(!Validacao.isProntuarioValido(dvUsuario.toLowerCase(), prontuario)) {
                        Toast.makeText(this, "Prontuário Invalido", Toast.LENGTH_SHORT).show();
                    } else {
                        UserLoginTask task = new UserLoginTask(this);
                        task.execute(pront, senha);
                    }
                }
                break;

            case R.id.text_cadastrar:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}