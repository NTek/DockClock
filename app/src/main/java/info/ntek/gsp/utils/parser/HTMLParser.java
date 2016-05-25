package info.ntek.gsp.utils.parser;

import info.ntek.gsp.data.ScheduleItem;
import info.ntek.gsp.data.route.RouteItem;
import info.ntek.gsp.data.time.HourItem;
import info.ntek.gsp.data.time.MinuteItem;
import info.ntek.gsp.data.time.TimeItem;
import info.ntek.gsp.data.validation.ValidationItem;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Html parser for JGSPs schedules.
 *
 * @author Milos Milanovic
 */
public class HTMLParser {

    private static final String VALIDATION_NAME = "vaziod";
    private static final String CELL_TAG = "td";
    private static final String BREAK_TAG = "br";
    private static final String CLASS_TAG = "class";
    private static final String SUPER_SCRIPT_TAG = "sup";
    private static final String SELECT_TAG = "select";
    private static final String NAME_TAG = "name";
    private static final String VALUE_TAG = "value";
    private static final String ENCODING = "utf-8";
    /** Fields. */
    private CleanerProperties mCleanerProperties = null;
    private static HTMLParser sInstance = null;

    private HTMLParser() {
        init();
    }

    public static HTMLParser getInstance() {
        if (sInstance == null) {
            sInstance = new HTMLParser();
        }
        return sInstance;
    }

    /** Initialize Fields. */
    private void init() {
        mCleanerProperties = new CleanerProperties();
        /** Set some properties to non-default values.*/
        mCleanerProperties.setTranslateSpecialEntities(true);
        mCleanerProperties.setTransResCharsToNCR(true);
        mCleanerProperties.setOmitComments(true);
        mCleanerProperties.setOmitDeprecatedTags(true);
    }

    public ArrayList<ScheduleItem> parseValidation(String url) {
        try {
            TagNode lHTML = new HtmlCleaner(mCleanerProperties).clean(new URL(url), ENCODING);
            Object[] lCells = lHTML
                    .evaluateXPath("//".concat(SELECT_TAG));
            for (Object cell : lCells) {
                TagNode lTagNode = ((TagNode) cell);
                if (lTagNode.getAttributeByName(NAME_TAG).equals(VALIDATION_NAME)) {
                    ArrayList<ScheduleItem> lValidationItems = new ArrayList<ScheduleItem>();
                    for (TagNode tagNode : lTagNode.getChildTags()) {
                        lValidationItems
                                .add(new ValidationItem(tagNode.getAttributeByName(VALUE_TAG)
                                        , tagNode.getText().toString()));
                    }
                    return lValidationItems;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<ScheduleItem> parseDirection(String url) {
        try {
            TagNode lHTML = new HtmlCleaner(mCleanerProperties).clean(new URL(url), ENCODING);
            Object[] lCells = lHTML
                    .evaluateXPath("//".concat(SELECT_TAG));
            for (Object cell : lCells) {
                TagNode[] lTagNode = ((TagNode) cell).getChildTags();
                ArrayList<ScheduleItem> lDirectionItems = new ArrayList<ScheduleItem>();
                for (TagNode tagNode : lTagNode) {
                    lDirectionItems.add(new RouteItem(tagNode.getAttributeByName(VALUE_TAG),
                            tagNode.getText().toString()));
                }
                return lDirectionItems;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<ScheduleItem> parseTime(String url) {
        try {
            TagNode lHTML = new HtmlCleaner(mCleanerProperties).clean(new URL(url), ENCODING);
            Object[] lCells = lHTML.evaluateXPath("//".concat(CELL_TAG));
            ArrayList<ScheduleItem> lTimeItems = new ArrayList<ScheduleItem>();
            for (int i = 0; i < lCells.length; i++) {
                ArrayList<HourItem> lHourItems = new ArrayList<HourItem>();
                TagNode lCell = ((TagNode) lCells[i]);
                if (!lCell.hasAttribute(CLASS_TAG)) {
                    List<TagNode> lTagNodes = lCell.getChildTagList();
                    for (int j = 0; j < lTagNodes.size(); j++) {
                        if (lTagNodes.get(j).getName().equals(BREAK_TAG)) {
                            lTagNodes.remove(j);
                        }
                    }
                    for (int j = 1, k = 0; j < lTagNodes.size(); k = j++) {
                        ArrayList<MinuteItem> lMinuteItems = new ArrayList<MinuteItem>();
                        for (; j < lTagNodes.size(); j++) {
                            if (lTagNodes.get(j).getName().equals(SUPER_SCRIPT_TAG)) {
                                lMinuteItems.add(new MinuteItem(lTagNodes.get(j).getText()
                                        .toString()));
                            } else {
                                break;
                            }
                        }
                        lHourItems
                                .add(new HourItem(lTagNodes.get(k).getText().toString(),
                                        lMinuteItems));
                    }
                    lTimeItems.add(new TimeItem(String.valueOf(i), lHourItems));
                }
            }
            return lTimeItems;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Save XML to Local Storage. */
    public void saveXmlToSdCard(String fileName, TagNode root) {
        File htmlClenaerFile = new File(fileName + ".xml");
        try {
            new PrettyXmlSerializer(mCleanerProperties).writeToFile(root,
                    htmlClenaerFile.getAbsolutePath());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
