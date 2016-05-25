package info.ntek.gsp.data.time;

/**
 * Type of Day.
 *
 * @author Milos Milanovic
 */
public enum DayType {
    RADNI_DAN(0),
    SUBOTA(1),
    NEDELJA(2);
    private int mValue = 0;

    DayType(int value) {
        this.mValue = value;
    }

    public int getValue() {
        return mValue;
    }

    public static String fromValue(DayType value) {
        switch (value) {
            case RADNI_DAN: {
                return "R";
            }
            case SUBOTA: {
                return "S";
            }
            case NEDELJA: {
                return "N";
            }
        }
        return null;
    }
}
