package mk.ukim.finki.tr.finkiask.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.TempData.Test;
import mk.ukim.finki.tr.finkiask.masterdetail.TestListActivity;

/**
 * Created by stefan on 7/31/15.
 */
public class TestRecyclerViewAdapter
        extends RecyclerView.Adapter<TestRecyclerViewAdapter.ViewHolder> {

    private List<Test> mValues;

    public TestRecyclerViewAdapter(List<Test> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_test_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mNumberRowTextView.setText(mValues.get(position).getName());
        holder.mNameTextView.setText(mValues.get(position).getSubject());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, TestListActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("test", mValues.get(position));
                intent.putExtra("test", b);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;

        public TextView mNumberRowTextView;
        public TextView mNameTextView;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            mNumberRowTextView = (TextView) mView.findViewById(R.id.rowNumberTextView);
            mNameTextView = (TextView) mView.findViewById(R.id.nameTextView);
        }
    }
}
