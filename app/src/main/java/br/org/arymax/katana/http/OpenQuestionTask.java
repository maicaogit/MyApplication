package br.org.arymax.katana.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import br.org.arymax.katana.model.Pergunta;
import br.org.arymax.katana.model.Resposta;
import br.org.arymax.katana.utility.XMLParser;

/**
 * Criado por Marco em 24/10/2016.
 */
public class OpenQuestionTask extends AsyncTask<Long, Void, String> {

    private Context mContext;
    private ProgressDialog mProgress;
    private Intent mIntent;

    public OpenQuestionTask(Context context, ProgressDialog dialog, Intent intent){
        mContext = context;
        mProgress = dialog;
        mIntent = intent;
    }

    @Override
    protected String doInBackground(Long... params) {
        Long param = params[0];
        String result = "";
        try {
            result = ServerCalls.callGet(
                    ServerCalls.SERVER_URL + "funcoes" + ServerCalls.GET_QUESTION_PATH + param,
                    ServerCalls.GET,
                    ServerCalls.TEXT_XML
            );
        } catch (Exception e) {
            this.cancel(true);
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        Pergunta pergunta = XMLParser.XMLToObject(s, Pergunta.class);
        mIntent.putExtra("titulo", pergunta.getTitulo());
        mIntent.putExtra("pergunta", pergunta.getTexto());
        mIntent.putExtra("anonimo", pergunta.isAnonimo());
        mIntent.putExtra("autor", pergunta.getUsuario().getNome());
        mIntent.putExtra("pk_autor", pergunta.getUsuario().getPkUsuario());
        mContext.startActivity(mIntent);
        mProgress.dismiss();
    }
}
