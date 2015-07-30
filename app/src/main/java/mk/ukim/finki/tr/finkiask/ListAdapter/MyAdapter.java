package mk.ukim.finki.tr.finkiask.ListAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mk.ukim.finki.tr.finkiask.R;
import mk.ukim.finki.tr.finkiask.TempData.TestMock;


public class  MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements
        View.OnClickListener{

    private ArrayList<TestMock> mDataset;
    private static Context sContext;

    public MyAdapter(Context context, ArrayList<TestMock> myDataset){
        mDataset = myDataset;
        sContext = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);

        ViewHolder holder = new ViewHolder(v);
        holder.mNameTextView.setOnClickListener(MyAdapter.this);

        holder.mNameTextView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        holder.mNumberRowTextView.setText(String.valueOf(position)+ ". ");
        holder.mNameTextView.setText(mDataset.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onClick(View v) {
        ViewHolder holder = (ViewHolder) v.getTag();
        if(v.getId() == holder.mNameTextView.getId()){
            Toast.makeText(sContext, holder.mNameTextView.getText(), Toast.LENGTH_SHORT).show();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mNumberRowTextView;
        public TextView mNameTextView;

        public ViewHolder(View v) {
            super(v);
            mNumberRowTextView = (TextView) v.findViewById(R.id.rowNumberTextView);
            mNameTextView = (TextView) v.findViewById(R.id.nameTextView);
        }
    }
}

