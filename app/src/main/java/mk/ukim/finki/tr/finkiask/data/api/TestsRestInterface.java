package mk.ukim.finki.tr.finkiask.data.api;

import java.util.List;

import mk.ukim.finki.tr.finkiask.data.pojo.TestInstanceWrapperPOJO;
import mk.ukim.finki.tr.finkiask.data.pojo.TestPOJO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface TestsRestInterface {
    @GET("/ask/api/tests")
    void listAllActive(@Query("type") String type, Callback<List<TestPOJO>> cb);

    @GET("/ask/api/tests/{id}")
    void getTest(@Path("id") long id, @Query("password") String password, Callback<TestInstanceWrapperPOJO> cb);
}
