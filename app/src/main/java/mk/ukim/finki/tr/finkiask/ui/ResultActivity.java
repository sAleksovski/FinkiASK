package mk.ukim.finki.tr.finkiask.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.ui.masterdetail.BaseTestActivity;
import mk.ukim.finki.tr.finkiask.ui.result.Circle;
import mk.ukim.finki.tr.finkiask.ui.result.CircleAngleAnimation;

public class ResultActivity extends AppCompatActivity {

    @Bind(R.id.grade) TextView gradeTv;
    @Bind(R.id.circle) Circle circle;
    @Bind(R.id.points) TextView pointsTv;
    @Bind(R.id.comment) TextView commentTv;
    @Bind(R.id.success_survey) ImageView successImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        String type = getIntent().getStringExtra(BaseTestActivity.ARG_TYPE);
        if (type.equals("SURVEY")) {
            pointsTv.setText(getResources().getString(R.string.survey_finish_text));
            gradeTv.setText("");
            circle.setVisibility(View.GONE);
            commentTv.setText("");
            successImage.setVisibility(View.VISIBLE);
            return;
        }

        int points = getIntent().getIntExtra(BaseTestActivity.ARG_RESULT, 0);

        CircleAngleAnimation animation = new CircleAngleAnimation(circle, points);
        animation.setDuration(2000);
        circle.startAnimation(animation);

        String youScoredFormat = getResources().getString(R.string.you_scored_xx_percent);
        pointsTv.setText(String.format(youScoredFormat, points));

        String grade;
        if (points > 90) {
            grade = "A";
        } else if (points > 80) {
            grade = "B";
        } else if (points > 70) {
            grade = "C";
        } else if (points > 60) {
            grade = "D";
        } else if (points > 50) {
            grade = "E";
        } else {
            grade = "F";
        }

        gradeTv.setText(grade);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
