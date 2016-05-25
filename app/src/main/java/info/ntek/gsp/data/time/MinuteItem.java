package info.ntek.gsp.data.time;

import info.ntek.gsp.data.ScheduleItem;

/**
 * @author Milos Milanovic
 */
public class MinuteItem extends ScheduleItem {

    private MinuteType mMinuteType = null;

    public MinuteItem(String minute) {
        super(minute);
        mMinuteType = MinuteType.fromValue("");
    }

    public MinuteType getMinuteType() {
        return mMinuteType;
    }
}
