package info.ntek.gsp.data.time;

import info.ntek.gsp.data.ScheduleItem;

import java.util.ArrayList;

/**
 * @author Milos Milanovic
 */
public class HourItem extends ScheduleItem {

    private ArrayList<MinuteItem> mMinutes = null;

    public HourItem(String hour, ArrayList<MinuteItem> minutes) {
        super(hour);
        mMinutes = minutes;
    }

    public ArrayList<MinuteItem> getMinutes() {
        return mMinutes;
    }

    @Override
    public String toString() {
        return getValue() + " " + mMinutes;
    }
}
