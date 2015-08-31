package mk.ukim.finki.tr.finkiask.data.api;

import mk.ukim.finki.tr.finkiask.data.pojo.TestInstanceWrapperPOJO;
import mk.ukim.finki.tr.finkiask.data.pojo.TestPOJO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.List;

public interface TestsRestInterface {
    @GET("/api/tests")
    void listAllActive(@Query("type") String type, Callback<List<TestPOJO>> cb);

    @GET("/api/tests/{id}")
    void getTest(@Path("id") long id, @Query("password") String password, Callback<TestInstanceWrapperPOJO> cb);
}
