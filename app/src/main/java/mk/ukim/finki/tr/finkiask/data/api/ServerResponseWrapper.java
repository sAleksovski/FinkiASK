package mk.ukim.finki.tr.finkiask.data.api;

import mk.ukim.finki.tr.finkiask.data.models.TestInstance;

public class ServerResponseWrapper {

    private String responseStatus;
    private String description;
    private TestInstance data;

    public ServerResponseWrapper(String responseStatus, String description, TestInstance testInstance) {
        this.responseStatus = responseStatus;
        this.description = description;
        this.data = testInstance;
    }


    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TestInstance getData() {
        return data;
    }

    public void setData(TestInstance data) {
        this.data = data;
    }
}
