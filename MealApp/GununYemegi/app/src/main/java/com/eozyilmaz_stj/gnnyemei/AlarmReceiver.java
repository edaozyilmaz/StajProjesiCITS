package com.eozyilmaz_stj.gnnyemei;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;


public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //<----Create NotificationChannel---->
        final NotificationManager mNotific =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        CharSequence name = "Ragav";
        String desc = "this is notific";
        int imp = NotificationManager.IMPORTANCE_HIGH;
        final String CHANNEL_ID = "my_channel_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name,
                    imp);
            mChannel.setDescription(desc);
            mChannel.setLightColor(Color.CYAN);
            mChannel.canShowBadge();
            mChannel.setShowBadge(true);
            mNotific.createNotificationChannel(mChannel);
        }

        final int ncode = 101;
        // Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(context, MainScrollingActivity.class);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.food_icon)
                .setContentTitle("Günün Yemek Menüsü")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Günün Yemek Menüsü    "))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent);


        RingtoneManager.getRingtone(context,Uri.parse("android.resource://com.my.package/" + R.raw.notification_sound)).play();

        Notification n = mBuilder.build();
        mNotific.notify(ncode, n);

    }
}