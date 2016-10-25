package br.org.arymax.katana.broadcastreceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import br.org.arymax.katana.R;
//import br.org.arymax.katana.activity.QuestionActivity;
import br.org.arymax.katana.activity.QuestionActivity;
import br.org.arymax.katana.activity.SearchActivity;
import br.org.arymax.katana.utility.Constants;

/**
 * Created by Usuário on 16/10/2016.
 */

public class NotificationReceiver extends BroadcastReceiver  {
    private Boolean estado;
    @Override
    public void onReceive(Context context, Intent intent){
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(context, QuestionActivity.class);
        intent1.putExtras(intent.getExtras());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) context.getSharedPreferences(Constants.PREFERENCES,0).getLong("pk",-1), intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker("Você tem preguntas respondidas não vizualizadas");
        builder.setContentTitle("Você possui alguma pergunta não visualizada");
        builder.setContentText("Toque para mais informações");
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo));
        Notification n = builder.build();
        n.vibrate = new long[]{150, 300, 150, 300};
        nm.notify(R.drawable.logo, n);
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
