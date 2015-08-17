package mk.ukim.finki.tr.finkiask.data.api;

import mk.ukim.finki.tr.finkiask.data.models.TestInstance;
import mk.ukim.finki.tr.finkiask.data.pojo.AllActivePOJO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface TestsRestInterface {
    @GET("/allActive")
    void listAllActive(Callback<AllActivePOJO> cb);

    @GET("/test/{id}")
    void getTest(@Path("id") long id, Callback<TestInstance> cb);
}
