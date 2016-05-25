package info.ntek.gsp.data.time;

/**
 * Type of Minute.
 *
 * @author Milos Milanovic.
 */
public enum MinuteType {
    BUS(0);
    private int mValue = 0;

    MinuteType(int value) {
        this.mValue = value;
    }

    public int getValue() {
        return mValue;
    }

    public static MinuteType fromValue(String value) {
        switch (value) {
            case "C": {
                return BUS;
            }
        }
        return null;
    }
}
