package info.ntek.gsp.data.validation;

import info.ntek.gsp.data.ScheduleItem;

/**
 * @author Milos Milanovic
 */
public class ValidationItem extends ScheduleItem {

    private String mValidation = "";

    public ValidationItem(String validationID, String validation) {
        super(validationID);
        mValidation = validation;
    }

    public String getValidation() {
        return mValidation;
    }
}
