package mk.ukim.finki.tr.finkiask.database.models;

public class Test {
    private String name;

    private String testingType;

    private int duration;

    public Test() {}

    public Test(String name, String testingType, int duration) {
        this.name = name;
        this.testingType = testingType;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTestingType() {
        return testingType;
    }

    public void setTestingType(String testingType) {
        this.testingType = testingType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
