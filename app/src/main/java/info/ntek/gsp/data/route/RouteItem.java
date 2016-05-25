package info.ntek.gsp.data.route;

import info.ntek.gsp.data.ScheduleItem;
import info.ntek.gsp.data.time.TimeItem;

import java.util.ArrayList;

/**
 * @author Milos Milanovic.
 */
public class RouteItem extends ScheduleItem {

    private String mDirection = "";
    private ArrayList<TimeItem> mTimeItems = null;

    public RouteItem(String directionID, String direction) {
        super(directionID);
        mDirection = direction;
    }

    public void setTimeItems(ArrayList<TimeItem> timeItems) {
        mTimeItems = timeItems;
    }

    public String getDirection() {
        return mDirection;
    }
}
