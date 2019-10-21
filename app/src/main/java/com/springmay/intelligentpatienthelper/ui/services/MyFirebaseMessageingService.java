package com.springmay.intelligentpatienthelper.ui.services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.springmay.intelligentpatienthelper.R;
import com.springmay.intelligentpatienthelper.ui.activities.MainActivity;
import java.util.Objects;
import java.util.Random;


public class MyFirebaseMessageingService extends FirebaseMessagingService {

   @Override
   public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

       super.onMessageReceived(remoteMessage);
       Log.d("msg", "onMessageReceived: " + remoteMessage.getData().get("message"));
       //
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
       {
           sendSimpleNotificationOreoPush(Objects.requireNonNull(remoteMessage.getNotification())
                   .getTitle(),remoteMessage.getNotification().getBody());
       } else
           {
           sendNotification(Objects.requireNonNull(remoteMessage.getNotification())
                   .getTitle(),remoteMessage.getNotification().getBody());
           }

   }
   //
   private static int getNotificationIcon() {
       boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
       return useWhiteIcon ? R.drawable.ic_noti : R.drawable.ic_noti ;
   }
   //
   @RequiresApi(api = Build.VERSION_CODES.O)
   private void sendSimpleNotificationOreoPush(String title,String desc) {

       NotificationManager notificationManager =
               (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
       //
       String id = "id_product";
       CharSequence name = "Badger";
       @SuppressLint("WrongConstant") NotificationChannel mChannel = new NotificationChannel
               (id, name, NotificationManager.IMPORTANCE_MAX);
       mChannel.setDescription(desc);
       mChannel.enableLights(true);
       //
       assert notificationManager != null;
       notificationManager.createNotificationChannel(mChannel);
       //
       Intent intent = new Intent(this, MainActivity.class);
       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
               | Intent.FLAG_ACTIVITY_CLEAR_TASK);
       intent.putExtra("isFromFcm", true);
       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
               PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
       //
       Random random = new Random();
       int notifyID = random.nextInt(9999 - 1000) + 1000;
       NotificationCompat.Builder notificationBuilder = new NotificationCompat
               .Builder(getApplicationContext(), "id_product")
               .setSmallIcon(getNotificationIcon())
               .setBadgeIconType(getNotificationIcon())
               .setChannelId(id)
               .setContentTitle(title)
               .setAutoCancel(true).setContentIntent(pendingIntent)
               .setNumber(1)
               .setContentText(desc)
               .setWhen(System.currentTimeMillis());
       //
       notificationManager.notify(notifyID, notificationBuilder.build());

   }
   //
   private void sendNotification(String title,String desc) {

       Intent intent = new Intent(this, MainActivity.class);
       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
               | Intent.FLAG_ACTIVITY_CLEAR_TASK);
       intent.putExtra("isFromFcm", true);
       //
       PendingIntent pendingIntent;
       pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
       //
       Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
       //
       NotificationCompat.Builder mBuilder = new NotificationCompat
               .Builder(this,"");
       //
       NotificationCompat.BigTextStyle s = new NotificationCompat.BigTextStyle();
       s.setBigContentTitle(title);
       s.setSummaryText(desc);
       s.setBigContentTitle(title);
       s.setSummaryText(desc);
       //
       Notification notification;
       notification = mBuilder.setSmallIcon(R.drawable.ic_noti )
               .setTicker(title)
               .setAutoCancel(true)
               .setSound(defaultSoundUri)
               .setContentIntent(pendingIntent)
               .setContentTitle(title)
               .setSmallIcon(R.drawable.ic_noti )
               .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_noti ))
               .setContentText(desc)
               .setStyle(new NotificationCompat.BigTextStyle().bigText(desc))
               .build();
       //
       Random random = new Random();
       int m = random.nextInt(9999 - 1000) + 1000;
       //
       NotificationManager notificationManager = (NotificationManager)
               getSystemService(Context.NOTIFICATION_SERVICE);
       //
       assert notificationManager != null;
       notificationManager.notify(m, notification);
   }
   //
   @Override
   public void onNewToken(@NonNull String s) {
       super.onNewToken(s);
       storeRegIdInPref(s);
   }
   //
   private void storeRegIdInPref(String token) {

       Log.e("token", token);
       //
       SharedPreferences sharedPreferences = PreferenceManager.
               getDefaultSharedPreferences(MyFirebaseMessageingService.this);
       SharedPreferences.Editor editor = sharedPreferences.edit();
       editor.putString("token", token);
       editor.apply();
       //
       Toast.makeText(this, token+"", Toast.LENGTH_SHORT).show();

   }
}