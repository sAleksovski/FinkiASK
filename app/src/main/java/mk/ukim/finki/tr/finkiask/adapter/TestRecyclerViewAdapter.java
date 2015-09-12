package mk.ukim.finki.tr.finkiask.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.data.DBHelper;
import mk.ukim.finki.tr.finkiask.data.pojo.TestPOJO;

public class TestRecyclerViewAdapter
        extends RecyclerView.Adapter<TestRecyclerViewAdapter.ViewHolder> {

    private List<TestPOJO> mValues;
    TestRecyclerViewAdapterClickCallback mCallback;

    public TestRecyclerViewAdapter(List<TestPOJO> items, TestRecyclerViewAdapterClickCallback callback) {
        mValues = items;
        mCallback = callback;
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
        if (DBHelper.isTestInstanceFound() && DBHelper.getSingleTestInstance().getId() == mValues.get(position).getId()) {
            holder.mIconOpened.setVisibility(View.VISIBLE);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mCallback.onItemClick(position);
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
        @Bind(R.id.testOpened)
        ImageView mIconOpened;



        public ViewHolder(View view) {
            super(view);
            mView = view;

            ButterKnife.bind(this, mView);
        }
    }

    public interface TestRecyclerViewAdapterClickCallback {
        void onItemClick(int position);
    }
}
