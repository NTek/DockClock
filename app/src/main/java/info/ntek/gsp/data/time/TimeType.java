package info.ntek.gsp.data.time;

/**
 * @author Milos Milanovic
 */
public enum TimeType {
    SMER_A(0),
    SMER_B(1);
    private int mValue = 0;

    TimeType(int value) {
        this.mValue = value;
    }

    public int getValue() {
        return mValue;
    }

    public static String fromValue(int value) {
        switch (values()[value]) {
            case SMER_A: {
                return "A";
            }
            case SMER_B: {
                return "B";
            }
        }
        return null;
    }
}
