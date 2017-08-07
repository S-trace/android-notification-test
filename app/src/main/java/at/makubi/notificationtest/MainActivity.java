package at.makubi.notificationtest;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends _SwipeActivityClass {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(at.makubi.notificationtest.R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        new NotificationTest().testNotifications(getApplicationContext());
    }

    @Override
    protected void onSwipeRight(float velocity, float distance, float x1, float x2) {
        Log.d("S-trace", "onSwipeRight, velocity=" + velocity + ", distance=" + distance + ", x1=" + x1 + ", x2=" + x2);
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.settings");
        if (launchIntent != null) {
            // Start non-default activity from another app:
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setComponent(new ComponentName("com.android.settings","com.android.settings.accounts.AddAccountSettings"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);//null pointer check in case package name was not found
        }
    }

    @Override
    protected void onSwipeLeft(float velocity, float distance, float x1, float x2) {
        Log.d("S-trace", "onSwipeLeft, velocity=" + velocity + ", distance=" + distance + ", x1=" + x1 + ", x2=" + x2);
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.dialer");
        if (launchIntent != null) {
            // Start a default launcher activity from another app:
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(launchIntent);//null pointer check in case package name was not found
        }
    }
}
