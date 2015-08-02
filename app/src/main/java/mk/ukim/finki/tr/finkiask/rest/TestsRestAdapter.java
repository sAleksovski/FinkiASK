package mk.ukim.finki.tr.finkiask.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class TestsRestAdapter {
    private static TestsRestInterface testsRestInterface;
    private static final String BASE_URL = "http://192.168.1.140:3000/";
    private static RestAdapter restAdapter;

    private TestsRestAdapter() { }

    private static void initialize() {
        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yy hh:mm")
                .create();

        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();
        testsRestInterface = restAdapter.create(TestsRestInterface.class);
    }

    public static TestsRestInterface getInstance() {
        if (testsRestInterface == null)
            initialize();
        return testsRestInterface;
    }
}
