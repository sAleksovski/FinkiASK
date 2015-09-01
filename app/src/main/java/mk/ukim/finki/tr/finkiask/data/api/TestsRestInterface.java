package mk.ukim.finki.tr.finkiask.data.api;

import mk.ukim.finki.tr.finkiask.data.models.Answer;
import mk.ukim.finki.tr.finkiask.data.pojo.TestPOJO;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.List;

public interface TestsRestInterface {
    @GET("/ask//api/tests")
    void listAllActive(@Query("type") String type, Callback<List<TestPOJO>> cb);

    @GET("/ask/api/tests/{id}")
    void getTest(@Path("id") long id, @Query("password") String password, Callback<ServerResponseWrapper> cb);

    @POST("/ask/api/tests/{id}")
    void postAnswer(@Header("Cookie") String cookie, @Path("id") long id, @Body List<Answer> answer, Callback<ServerResponseWrapper> cb);
}
