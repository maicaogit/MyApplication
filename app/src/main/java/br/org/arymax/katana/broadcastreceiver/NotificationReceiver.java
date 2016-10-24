package br.org.arymax.katana.broadcastreceiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import br.org.arymax.katana.http.UserNotificationGetTask;
import br.org.arymax.katana.utility.Constants;

/**
 * Created by Usuário on 16/10/2016.
 */

public class NotificationReceiver extends BroadcastReceiver  {
    private Boolean estado;
    @Override
    public void onReceive(Context context, Intent intent){
        callTask(context);
        Calendar c= Calendar.getInstance();
        c.add(Calendar.MINUTE,5);
        Intent i= new Intent(context,NotificationReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,(int)context.getSharedPreferences(Constants.PREFERENCES,0).getLong("pk",-1),i,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmeManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmeManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    public void callTask(Context context) {

        UserNotificationGetTask task = new UserNotificationGetTask(context,this);
        task.execute(context.getSharedPreferences(Constants.PREFERENCES,0).getLong("pk",-1));
    }
}
