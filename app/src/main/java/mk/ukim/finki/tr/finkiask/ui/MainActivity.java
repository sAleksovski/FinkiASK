package mk.ukim.finki.tr.finkiask.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.data.DBHelper;
import mk.ukim.finki.tr.finkiask.data.models.TestInstance;
import mk.ukim.finki.tr.finkiask.data.pojo.TestPOJO;
import mk.ukim.finki.tr.finkiask.ui.dialog.BaseDialogFragment;
import mk.ukim.finki.tr.finkiask.ui.dialog.ReopenTestDialogFragment;
import mk.ukim.finki.tr.finkiask.ui.masterdetail.TestListActivity;
import mk.ukim.finki.tr.finkiask.util.AuthHelper;
import mk.ukim.finki.tr.finkiask.util.timer.TimeUtils;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.tabs) TabLayout tabLayout;
    @Bind(R.id.viewpager) ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setHomeAsUpIndicator(R.drawable.ic_action_school);
        ab.setDisplayHomeAsUpEnabled(true);

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

        if (DBHelper.isTestInstanceFound()) {
            TestInstance testInstance = DBHelper.getSingleTestInstance();
            if (TimeUtils.remainingTime(testInstance, TimeUnit.MINUTES) <= 0) {
                DBHelper.deleteEverything();
                return;
            }

            ReopenTestDialogFragment.newInstance(TimeUtils.remainingTime(testInstance, TimeUnit.MINUTES),
                    new BaseDialogFragment.OnPositiveCallback() {
                        @Override
                        public void onPositiveClick(String data) {
                            Intent intent = new Intent(getApplicationContext(), TestListActivity.class);
                            intent.putExtra("testInstanceId", DBHelper.getSingleTestInstance().getId());

                            startActivity(intent);
                        }
                    }).show(getSupportFragmentManager(), "reopen_test_dialog");
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());

        if (AuthHelper.isLoggedIn(this)) {
            MainTestListFragment tests = MainTestListFragment.newInstance(TestPOJO.TEST);
            adapter.addFragment(tests, "Tests");
        }

        MainTestListFragment anonSurvey = MainTestListFragment.newInstance(TestPOJO.ANONYMOUS_TEST);
        adapter.addFragment(anonSurvey, "Anonymous test");

        MainTestListFragment surveys = MainTestListFragment.newInstance(TestPOJO.SURVEY);
        adapter.addFragment(surveys, "Surveys");

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_logout) {
            AuthHelper.logout(this);
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
