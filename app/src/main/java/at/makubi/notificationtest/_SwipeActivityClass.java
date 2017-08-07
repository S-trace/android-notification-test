package at.makubi.notificationtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
/**
 * Created by s-trace on 07.08.17.
 * Based on:
 * https://stackoverflow.com/questions/10125512/android-swipe-screen-to-open-another-activity
 * https://stackoverflow.com/questions/1016896/get-screen-dimensions-in-pixels
 */

public abstract class _SwipeActivityClass extends Activity {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_MAX_EDGE_PERCENT = 15;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    private int mDisplayHeight = 0;
    private int mDisplayWidth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        gestureDetector = new GestureDetector( this, new SwipeDetector());
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mDisplayHeight = displaymetrics.heightPixels;
        mDisplayWidth = displaymetrics.widthPixels;
    }

    protected abstract void onSwipeRight(float velocity, float distance, float x1, float x2);
    protected abstract void onSwipeLeft(float velocity, float distance, float x1, float x2);

    public class SwipeDetector extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {

            // Check movement along the Y-axis. If it exceeds SWIPE_MAX_OFF_PATH,
            // then dismiss the swipe.
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
            {
                return false;
            }

            //toast( "start = "+String.valueOf( e1.getX() )+" | end = "+String.valueOf( e2.getX() )  );
            //from left to right
            if( e2.getX() > e1.getX() )
            {
                if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                {
                    float maxAloowedX = mDisplayWidth * SWIPE_MAX_EDGE_PERCENT / 100;
                    if (e1.getX() < maxAloowedX) {
                        onSwipeRight(velocityX, e2.getX() - e1.getX(), e1.getX(), e2.getX());
                        return true;
                    }
                    return false;
                }
            }

            if( e1.getX() > e2.getX() )
            {
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                {
                    float minAloowedX = mDisplayWidth - (mDisplayWidth * SWIPE_MAX_EDGE_PERCENT / 100);
                    if (e1.getX() > minAloowedX) {
                        onSwipeLeft(velocityX, e2.getX() - e1.getX(), e1.getX(), e2.getX());
                        return true;
                    }
                    return false;
                }
            }

            return false;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        // TouchEvent dispatcher.
        if (gestureDetector != null)
        {
            if (gestureDetector.onTouchEvent(ev))
                // If the gestureDetector handles the event, a swipe has been
                // executed and no more needs to be done.
                return true;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return gestureDetector.onTouchEvent(event);
    }
}
