package com.example.shivam.materialnotes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Shivam on 01/04/15 at 2:23 PM.
 */
public class NotifyService extends Service {

    public class ServiceBinder extends Binder {
        NotifyService getService() {
            return NotifyService.this;
        }
    }

    // Unique id to identify the notification.
    private static final int NOTIFICATION = 123;
    // Name of an intent extra we can use to identify if this service was started to create a notification
    public static final String INTENT_NOTIFY = "com.blundell.tut.service.INTENT_NOTIFY";
    // The system notification manager
    private NotificationManager mNM;

    @Override
    public void onCreate() {
        Log.i("NotifyService", "onCreate()");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        // If this service was started by out AlarmTask intent then we want to show our notification
        if(intent.getBooleanExtra(INTENT_NOTIFY, false))
            showNotification();

        // We don't care if this service is stopped as we have already delivered our notification
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients
    private final IBinder mBinder = new ServiceBinder();

    /**
     * Creates a notification and shows it in the OS drag-down status bar
     */
    private void showNotification() {
        // This is the 'title' of the notification
        CharSequence title = "Reminder";
        // This is the icon to use on the notification
        int icon = R.drawable.ic_event_available_white_24dp;
        // This is the scrolling text of the notification
        CharSequence text = "You have a reminder which is due !";
        // What time to show on the notification
        long time = System.currentTimeMillis();
        Notification notification = new Notification(icon, text, time);
        //notification.set

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        //contentIntent.

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, title, text, contentIntent);

        // Clear the notification when it is pressed
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Send the notification to the system.
        mNM.notify(NOTIFICATION, notification);

        /*PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Notification notification = new Notification.Builder(this)
                // Show controls on lock screen even when user hides sensitive content.
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setContentTitle("Reminder")
                .setContentText("You have a reminder which is due !")
                .setPriority(Notification.PRIORITY_HIGH)
                .setWhen(time)
                .setSmallIcon(R.drawable.ic_event_available_white_24dp)
                .addAction(R.drawable.ic_snooze_white_24dp, "Reminder Later", contentIntent)
                .build();*/
        // Stop the service when we are finished
        stopSelf();
    }
}
