package info.ntek.clock;

import android.app.Application;

/**
 * @author Milos Milanovic
 */
public class ClockApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new ScreenReceiver(this);
    }
}
