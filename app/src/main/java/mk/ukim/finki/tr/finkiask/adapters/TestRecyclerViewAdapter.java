package mk.ukim.finki.tr.finkiask.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.database.DBHelper;
import mk.ukim.finki.tr.finkiask.database.models.TestInstance;
import mk.ukim.finki.tr.finkiask.database.pojo.TestPOJO;
import mk.ukim.finki.tr.finkiask.masterdetail.TestListActivity;
import mk.ukim.finki.tr.finkiask.rest.RestError;
import mk.ukim.finki.tr.finkiask.rest.TestsRestAdapter;
import mk.ukim.finki.tr.finkiask.rest.TestsRestInterface;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by stefan on 7/31/15.
 */
public class TestRecyclerViewAdapter
        extends RecyclerView.Adapter<TestRecyclerViewAdapter.ViewHolder> {

    private List<TestPOJO> mValues;

    public TestRecyclerViewAdapter(List<TestPOJO> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_test_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTestName.setText(mValues.get(position).getName());
        holder.mDuration.setText("Allowed time: " + mValues.get(position).getDuration() + " minutes");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final Drawable originalDrawable = ContextCompat.getDrawable(v.getContext(), R.drawable.ic_action_school);
                final Drawable wrappedDrawable = DrawableCompat.wrap(originalDrawable);
                DrawableCompat.setTint(wrappedDrawable, v.getContext().getResources().getColor(R.color.primary));

                // TODO maybe use DialogFragment?
                // And use a callback instead of startTest
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Start test")
                        .setMessage("Are you sure you want to start this test?\n"
                                + "You have " + mValues.get(position).getDuration() + " minutes to solve it.")
                        .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO
//                                startTest(v.getContext(), mValues.get(position).getId());
                                startTest(v.getContext(), 1);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(wrappedDrawable)
                        .show();

            }
        });

    }

    private void startTest(final Context context, long id) {
        TestsRestInterface testsRestAdapter = TestsRestAdapter.getInstance();
        testsRestAdapter.getTest(id, new Callback<TestInstance>() {

            @Override
            public void success(TestInstance testInstance, Response response) {

                DBHelper.saveTestInstanceToDb(testInstance);

                Intent intent = new Intent(context, TestListActivity.class);
                intent.putExtra("testInstanceId", testInstance.getId());

                context.startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("REST", "Not loaded correctly");
                if (error.getResponse() != null) {
                    RestError body = (RestError) error.getBodyAs(RestError.class);
                    Log.e("RESTError", body.errorDetails);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;

        @Bind(R.id.testName)
        TextView mTestName;
        @Bind(R.id.testDuration)
        TextView mDuration;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            ButterKnife.bind(this, mView);
        }
    }
}
