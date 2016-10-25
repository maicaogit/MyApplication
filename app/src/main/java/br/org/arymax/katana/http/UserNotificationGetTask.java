package br.org.arymax.katana.http;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.NotificationCompat;

import br.org.arymax.katana.R;
import br.org.arymax.katana.broadcastreceiver.NotificationReceiver;
import br.org.arymax.katana.fragment.HomeFragment;
import br.org.arymax.katana.utility.Constants;

/**
 * Criado por Marco em 24/10/2016.
 */
public class UserNotificationGetTask extends AsyncTask<Long, Void, String> {
    private Context mContext;
    private NotificationReceiver mNotificationReceiver;
    private static final String TAG = "UserNotificationGetTask.java";

    public UserNotificationGetTask(Context context, NotificationReceiver notifcationReceiver) {
        mContext = context;
        mNotificationReceiver=notifcationReceiver;
    }
    @Override
    protected String doInBackground(Long... params) {
        Long param = params[0];
        String result = "";
        try{
            result = ServerCalls.callGet(ServerCalls.SERVER_URL + "funcoes" + ServerCalls.SET_USER_NOTIFICATED + params,
                    ServerCalls.GET,
                    ServerCalls.TEXT_XML
            );
        } catch (Exception e){
            this.cancel(true);
        }
        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("true")){
            mNotificationReceiver.setEstado(true);
            NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent intent = new Intent(mContext, HomeFragment.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, (int) mContext.getSharedPreferences(Constants.PREFERENCES,0).getLong("pk",-1), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
            builder.setTicker("Alguém respondeu a sua pergunta");
            builder.setContentTitle("Você possui alguma pergunta não visualizada");
            builder.setContentText("Toque para mais informações");
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentIntent(pendingIntent);
            Notification n = builder.build();
            n.vibrate = new long[]{150, 300, 150, 600};
            nm.notify(R.mipmap.ic_launcher, n);}
        else
            mNotificationReceiver.setEstado(false);}
}
