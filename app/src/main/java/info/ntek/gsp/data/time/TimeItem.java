package info.ntek.gsp.data.time;

import info.ntek.gsp.data.ScheduleItem;

import java.util.ArrayList;

/**
 * @author Milos Milanovic
 */
public class TimeItem extends ScheduleItem {

    private ArrayList<HourItem> mHourItems = null;

    public TimeItem(String timeID, ArrayList<HourItem> hourItems) {
        super(timeID);
        mHourItems = hourItems;
    }

    public ArrayList<HourItem> getHourItems() {
        return mHourItems;
    }

    @Override
    public String toString() {
        return TimeType.fromValue(Integer.valueOf(getValue())) + " " + mHourItems;
    }
}
