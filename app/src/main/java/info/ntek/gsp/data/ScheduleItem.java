package info.ntek.gsp.data;

/**
 * @author Milos Milanovic
 */
public abstract class ScheduleItem {

    private String mValue = "";

    public ScheduleItem(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }

    @Override
    public String toString() {
        return mValue;
    }
}
