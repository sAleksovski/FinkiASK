package mk.ukim.finki.tr.finkiask.data.api;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class TestsRestAdapter {
    private static TestsRestInterface testsRestInterface;
    private static final String BASE_URL = "http://192.168.1.140:8080/";

    private TestsRestAdapter() { }

    private static void initialize() {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaredClass().equals(ModelAdapter.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .setDateFormat("dd/MM/yyyy HH:mm")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();
        testsRestInterface = restAdapter.create(TestsRestInterface.class);
    }

    public static TestsRestInterface getInstance() {
        if (testsRestInterface == null) {
            initialize();
        }
        return testsRestInterface;
    }
}
