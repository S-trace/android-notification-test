package at.makubi.notificationtest;

import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class NotificationTest {

    static final int notificationSummaryId = 100;
    static final String GROUP_KEY = "group_key";

    public void testNotifications(final Context context) {

        final VoidAsyncTask task4 = new VoidAsyncTask() {
            @Override
            protected void run() {

                issueSummaryNotification(context, 4);
                issueNotification(context, 4);
            }
        };

        task4.executeSerially();
    }

    private void issueNotification(Context context, int threadId) {
        NotificationCompat.Builder builder = mainBuilder(context);
        builder.setContentText("notification " + threadId);

        NotificationManagerCompat.from(context).notify(threadId + "", notificationSummaryId, builder.build());
    }

    private void issueSummaryNotification(Context context, int numberOfThreads) {
        NotificationCompat.Builder builder = mainBuilder(context);
        builder.setContentText("summary " + numberOfThreads);
        builder.setGroupSummary(true);

        // Setting sound and priority high (to enable heads-up notifications) results in displaying of non-summary notifications on the handheld
        // builder.setSound(RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION));
        //builder.setLights(Color.RED, 300, 100);
        builder.setLights(Color.YELLOW, 300, 100);
        //builder.setLights(Color.GREEN, 300, 100);
        NotificationManagerCompat.from(context).notify(notificationSummaryId, builder.build());
    }

    private NotificationCompat.Builder mainBuilder(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setGroup(GROUP_KEY);
        builder.setContentTitle("title");
        builder.setSmallIcon(android.R.drawable.ic_input_get);

        builder.setPriority(NotificationCompat.PRIORITY_HIGH);

        return builder;
    }

    private void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
