package mk.ukim.finki.tr.finkiask.data.api;

public class ServerResponseWrapper<T> {

    private String responseStatus;
    private String description;
    private T data;

    public ServerResponseWrapper(String responseStatus, String description, T data) {
        this.responseStatus = responseStatus;
        this.description = description;
        this.data = data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
