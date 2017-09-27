package at.makubi.notificationtest;

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
        Intent intent = new Intent("com.cybertel.SWIPE_RIGHT");
        intent.putExtra("telno", "123-45678");
        sendBroadcast(intent);
    }

    @Override
    protected void onSwipeLeft(float velocity, float distance, float x1, float x2) {
        Log.d("S-trace", "onSwipeLeft, velocity=" + velocity + ", distance=" + distance + ", x1=" + x1 + ", x2=" + x2);
        Intent intent = new Intent("com.cybertel.SWIPE_LEFT");
        intent.putExtra("telno", "123-45678");
        sendBroadcast(intent);
    }
}
