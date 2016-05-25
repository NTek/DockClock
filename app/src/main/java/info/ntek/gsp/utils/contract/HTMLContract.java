package info.ntek.gsp.utils.contract;

import info.ntek.gsp.data.ScheduleItem;
import info.ntek.gsp.data.route.RouteType;
import info.ntek.gsp.data.time.DayType;
import info.ntek.gsp.utils.parser.HTMLParser;

import java.util.ArrayList;

/**
 * Constants & Queries.
 *
 * @author Milos Milanovic
 */
public class HTMLContract {

    /** URL IDs */
    private static final int VALIDATION_URL_ID = 0;
    private static final int DIRECTION_TYPE_URL_ID = 1;
    private static final int SCHEDULE_URL_ID = 2;
    /** URLs */
    private static final String GSP_URL = "http://www.gspns.co.rs";
    private static final String VALIDATION_URL = "/red-voznje";
    private static final String DIRECTION_TYPE_URL = "/lista-linija";
    private static final String SCHEDULE_URL = VALIDATION_URL + "/ispis-polazaka";
    /** TAGs */
    private static final String DIRECTION_TYPE_TAG = "?rv=";
    private static final String VALIDATION_TAG = "&vaziod=";
    private static final String DAY_TAG = "&dan=";
    private static final String DIRECTION_TAG = "&linija%5B%5D=";
    private static HTMLContract sInstance = null;
    private StringBuilder mURL = null;
    private int mURLID = -1;

    private HTMLContract() {
        mURL = new StringBuilder();
    }

    public static HTMLContract buildURL() {
        if (sInstance == null) {
            sInstance = new HTMLContract();
        }
        sInstance.setMainURL();
        return sInstance;
    }

    private void setMainURL() {
        mURL.delete(0, mURL.length());
        mURL.append(GSP_URL);
    }

    public HTMLContract getValidation(RouteType routeType) {
        mURL.append(VALIDATION_URL).append("/").append(routeType.getRouteName());
        mURLID = VALIDATION_URL_ID;
        return this;
    }

    public HTMLContract getDirections() {
        mURL.append(VALIDATION_URL).append(DIRECTION_TYPE_URL);
        mURLID = DIRECTION_TYPE_URL_ID;
        return this;
    }

    public HTMLContract getSchedules() {
        mURL.append(SCHEDULE_URL);
        mURLID = SCHEDULE_URL_ID;
        return this;
    }

    public HTMLContract setRouteType(RouteType routeType) {
        mURL.append(DIRECTION_TYPE_TAG).append(routeType.getShortRouteName());
        return this;
    }

    public HTMLContract setValidation(ScheduleItem validationItem) {
        mURL.append(VALIDATION_TAG).append(validationItem.getValue());
        return this;
    }

    public HTMLContract setDay(DayType dayType) {
        mURL.append(DAY_TAG).append(DayType.fromValue(dayType));
        return this;
    }

    public HTMLContract setDirection(ScheduleItem directionItem) {
        mURL.append(DIRECTION_TAG).append(directionItem.getValue());
        return this;
    }

    public ArrayList<ScheduleItem> run() {
        switch (mURLID) {
            case VALIDATION_URL_ID: {
                return HTMLParser.getInstance().parseValidation(mURL.toString());
            }
            case DIRECTION_TYPE_URL_ID: {
                return HTMLParser.getInstance().parseDirection(mURL.toString());
            }
            case SCHEDULE_URL_ID: {
                return HTMLParser.getInstance().parseTime(mURL.toString());
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public String toString() {
        return mURL.toString();
    }
}
