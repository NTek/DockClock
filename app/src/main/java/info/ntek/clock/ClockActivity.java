package info.ntek.clock;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.view.WindowManager;

/**
 * @author Milos Milanovic
 */
public class ClockActivity extends Activity {

    private PowerManager.WakeLock mWakeLock = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(
                (PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK
                        | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
        final View lClock = findViewById(R.id.clock);
        lClock.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        lClock.setOnSystemUiVisibilityChangeListener(
                new View.OnSystemUiVisibilityChangeListener() {

                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        if (visibility == View.VISIBLE) {
                            finish();
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = 1 / (float) 255;
        getWindow().setAttributes(lp);
        mWakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWakeLock.release();
    }
}
