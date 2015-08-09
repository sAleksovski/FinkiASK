package mk.ukim.finki.tr.finkiask.database.pojo;

import java.util.ArrayList;
import java.util.List;

public class AllActivePOJO {

    private List<TestPOJO> tests;
    private List<TestPOJO> surveys;
    private List<TestPOJO> anonymousSurveys;

    public AllActivePOJO(List<TestPOJO> tests, List<TestPOJO> surveys, List<TestPOJO> anonymousSurveys) {
        this.tests = tests;
        this.surveys = surveys;
        this.anonymousSurveys = anonymousSurveys;
    }

    public List<TestPOJO> get(String type) {
        List<TestPOJO> r = new ArrayList<>();

        if (type.equals(TestPOJO.TEST)) {
            r = tests;
        } else if (type.equals(TestPOJO.SURVEY)) {
            r = surveys;
        } else if (type.equals(TestPOJO.ANONYMOUS_SURVEY)) {
            r = anonymousSurveys;
        }

        if (r == null) {
            r = new ArrayList<>();
        }

        return r;
    }

}
