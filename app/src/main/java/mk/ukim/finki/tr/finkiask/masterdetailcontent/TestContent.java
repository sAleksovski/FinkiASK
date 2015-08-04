package mk.ukim.finki.tr.finkiask.masterdetailcontent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mk.ukim.finki.tr.finkiask.database.models.Question;


public class TestContent {

    /**
     * An array of sample items.
     */
    public static List<Question> ITEMS = new ArrayList<>();

    /**
     * A map of sample items, by ID.
     */
    public static Map<String, Question> ITEM_MAP = new HashMap<>();

    static {
        // Add 3 sample items.

    }
    public static void addAll(List<Question> questions) {
        if(ITEMS.size() > 0)
            ITEMS = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++)
            addItem(questions.get(i));
    }

    private static void addItem(Question item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId() + "", item);
    }
    public static String getNextID(String id){
        Question q = ITEM_MAP.get(id);
        if(ITEMS.get(ITEMS.size()-1).getId() == q.getId()) return null;
        return String.valueOf(ITEMS.get(ITEMS.indexOf(q)+1).getId());
    }

}
