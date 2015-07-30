package mk.ukim.finki.tr.finkiask.masterdetailcontent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mk.ukim.finki.tr.finkiask.TempData.Question;


public class TestContent {

    /**
     * An array of sample items.
     */
    public static List<Question> ITEMS = new ArrayList<Question>();

    /**
     * A map of sample items, by ID.
     */
    public static Map<String, Question> ITEM_MAP = new HashMap<String, Question>();

    static {
        // Add 3 sample items.

    }
    public static void addAll(ArrayList<Question> questions) {
        if(ITEMS.size() > 0)
            ITEMS = new ArrayList<Question>();
        for (int i = 0; i < questions.size(); i++)
            addItem(questions.get(i));
    }

    private static void addItem(Question item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getID(), item);
    }

}
