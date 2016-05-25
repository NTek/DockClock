package info.ntek.gsp.data.route;

/**
 * Types of Directions.
 *
 * @author Milos Milanovic
 */
public enum RouteType {
    GRADSKI("rvg", "gradski"),
    PRIGRADSKI("rvp", "prigradski");
    private String mShortName = "";
    private String mName = "";

    RouteType(String shortName, String name) {
        mShortName = shortName;
        mName = name;
    }

    public String getShortRouteName() {
        return mShortName;
    }

    public String getRouteName() {
        return mName;
    }
}
