package info.ntek.clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * @author Milos Milanovic
 */
public class ScreenReceiver extends BroadcastReceiver {

    public ScreenReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        context.registerReceiver(this, filter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Intent lIntent = new Intent(context, ClockActivity.class);
            lIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(lIntent);
        }
    }
}
