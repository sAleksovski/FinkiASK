package mk.ukim.finki.tr.finkiask.data;

import android.app.Application;
import com.raizlabs.android.dbflow.config.FlowManager;

public class FinkiaskApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
