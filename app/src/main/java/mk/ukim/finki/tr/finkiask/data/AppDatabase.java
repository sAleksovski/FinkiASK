package mk.ukim.finki.tr.finkiask.data;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION, foreignKeysSupported = true)
public class AppDatabase {
    public static final String NAME = "Tests";
    public static final int VERSION = 1;
}
