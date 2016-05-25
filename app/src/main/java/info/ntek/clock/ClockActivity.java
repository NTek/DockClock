package info.ntek.clock;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextClock;

import info.ntek.R;
import info.ntek.clock.adapters.ViewPagerAdapter;
import info.ntek.gsp.data.ScheduleItem;
import info.ntek.gsp.data.route.RouteType;
import info.ntek.gsp.data.time.DayType;
import info.ntek.gsp.data.time.HourItem;
import info.ntek.gsp.data.time.TimeItem;
import info.ntek.gsp.utils.contract.HTMLContract;

import java.util.ArrayList;

/**
 * @author Milos Milanovic
 */
public class ClockActivity extends Activity {

    private static final int DURATION = 5000;
    private PowerManager.WakeLock mWakeLock = null;
    private TextClock mClock = null;
    private ViewPager mViewPager = null;
    private ViewPagerAdapter mViewPagerAdapter = null;
    private int mHour = 0;
    private Runnable mRunnableHideNavigation = new Runnable() {

        @Override
        public void run() {
            mClock.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            selectCurrentHour();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(
                (PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK
                        | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
        mClock = (TextClock) findViewById(R.id.clock);
        mClock.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

            @Override
            public void onSystemUiVisibilityChange(int i) {
                mClock.postDelayed(mRunnableHideNavigation, DURATION);
            }
        });
        mClock.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mClock.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mHour = Integer.valueOf(mClock.getText().toString().split(":")[0]);
            }

            @Override
            public void afterTextChanged(Editable s) {
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
        mClock.post(mRunnableHideNavigation);
        //GSP
        initializeGSP();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWakeLock.release();
    }

    private void initializeGSP() {
        mViewPagerAdapter = new ViewPagerAdapter();
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(mViewPagerAdapter);
        new AsyncTask<Void, Void, ArrayList>() {

            @Override
            protected ArrayList doInBackground(Void... voids) {
                ScheduleItem lValidationItem = HTMLContract.buildURL()
                        .getValidation(RouteType.GRADSKI)
                        .run().get(0);
                ArrayList<ScheduleItem> lDirections = HTMLContract.buildURL().getDirections()
                        .setRouteType(RouteType.GRADSKI).setValidation(lValidationItem)
                        .setDay(DayType.RADNI_DAN)
                        .run();
                ArrayList<ScheduleItem> lTimeItems = HTMLContract.buildURL().getSchedules()
                        .setRouteType(RouteType.GRADSKI).setDay(DayType.RADNI_DAN)
                        .setValidation(
                                lValidationItem).setDirection(lDirections.get(16)).run();
                return lTimeItems;
            }

            @Override
            protected void onPostExecute(ArrayList schedule) {
                //vrati samo smer A.
                mViewPagerAdapter.addItems(((TimeItem) schedule.get(0)).getHourItems());
                selectCurrentHour();
            }
        }.execute();
    }

    private void selectCurrentHour() {
        if (mViewPagerAdapter.getItems() != null) {
            HourItem lHourItem = null;
            int lHourIndex = 0;
            for (lHourIndex = 0; lHourIndex < mViewPagerAdapter.getItems().size(); lHourIndex++) {
                lHourItem = mViewPagerAdapter.getItems().get(lHourIndex);
                if (Integer.valueOf(lHourItem.getValue()) == mHour) {
                    break;
                }
            }
            mViewPager.setCurrentItem(lHourIndex);
        }
    }
}
