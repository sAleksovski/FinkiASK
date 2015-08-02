package mk.ukim.finki.tr.finkiask.rest;

import java.util.List;

import mk.ukim.finki.tr.finkiask.database.models.Test;
import retrofit.Callback;
import retrofit.http.GET;

public interface TestsRestInterface {
    @GET("/tests")
    void listTests(Callback<List<Test>> cb);
}
