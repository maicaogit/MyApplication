package br.org.arymax.katana.http;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.org.arymax.katana.model.ArrayPerguntas;
import br.org.arymax.katana.model.Pergunta;
import br.org.arymax.katana.model.Rest;
import br.org.arymax.katana.utility.Constants;
import br.org.arymax.katana.utility.XMLParser;

/**
 * Criado por Marco em 25/10/2016.
 */

public class NotificationService extends Service {


    private List<Worker> threads = new ArrayList<>();
    private boolean notificado = false;
    private static final int TIME_INTERVAL = /* 60 */ 1000;

    private static final String TAG = "NotificationService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                long id = getSharedPreferences(Constants.PREFERENCES, 0).getLong("pk", -1);
                Worker worker = new Worker(id, startId);
                worker.start();
                threads.add(worker);
            }
        }, TIME_INTERVAL);

        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        threads.get(threads.size() - 1).interrupt();
    }

    private class Worker extends Thread {
        private long userId;
        private int startId;

        public Worker(long userId, int startId){
            this.userId = userId;
            this.startId = startId;
        }

        @Override
        public void run(){
            String aux;
            try {
                aux = ServerCalls.callGet(
                        ServerCalls.SERVER_URL + "funcoes" + ServerCalls.GET_USER_NOTIFICATION + userId,
                        ServerCalls.GET,
                        ServerCalls.TEXT_PLAIN
                );
                notificado = Boolean.valueOf(aux);
                Log.d(TAG, "isNotificado: " + notificado);
                if(notificado){
                    String result = "";
                    try{
                        result = ServerCalls.callGet(ServerCalls.SERVER_URL + "funcoes" + ServerCalls.GET_NOTIFICATED_QUESTIONS_PATH + userId,
                                ServerCalls.GET,
                                ServerCalls.TEXT_XML
                        );
                    } catch (Exception e){

                    }
                    List<Pergunta> notificatedQuestions = XMLParser.xmlToListObject(result, ArrayPerguntas.class, Pergunta.class);
                    if(notificatedQuestions != null){
                        Intent intent = new Intent(Constants.ACTION_NOTIFICATE_USER);
                        intent.putExtra("titulo", notificatedQuestions.get(0).getTitulo());
                        intent.putExtra("pergunta", notificatedQuestions.get(0).getTexto());
                        intent.putExtra("pk", notificatedQuestions.get(0).getPkPergunta());
                        String xmlAnswers = ServerCalls.callGet(
                                ServerCalls.SERVER_URL + "funcoes" + ServerCalls.GET_ANSWERS_PATH + notificatedQuestions.get(0).getPkPergunta(),
                                ServerCalls.GET,
                                ServerCalls.TEXT_XML
                        );
                        intent.putExtra("respostas", xmlAnswers);
                        sendBroadcast(intent);
                        for(Pergunta pergunta : notificatedQuestions){
                            long perguntaId = pergunta.getPkPergunta();
                            try {
                                Rest restPergunta = new Rest(
                                        ServerCalls.SERVER_URL,
                                        null,
                                        ServerCalls.SET_QUESTION_NOTIFICATED + perguntaId,
                                        ServerCalls.PUT,
                                        null,
                                        ServerCalls.TEXT_PLAIN
                                );
                                String responseQuestion = ServerCalls.callGet(restPergunta);
                            } catch (Exception exception){
                                exception.printStackTrace();
                            }
                        }
                        try {
                            Rest restUsuario = new Rest();
                            restUsuario.setHost(ServerCalls.SERVER_URL);
                            restUsuario.setMethod(ServerCalls.PUT);
                            restUsuario.setPath(ServerCalls.SET_USER_NOTIFICATED + userId);
                            restUsuario.setAccept(ServerCalls.TEXT_PLAIN);
                            String responseUser = ServerCalls.callGet(restUsuario);
                        } catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
                stopSelf(startId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
