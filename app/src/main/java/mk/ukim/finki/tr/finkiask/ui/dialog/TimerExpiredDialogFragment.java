package mk.ukim.finki.tr.finkiask.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;

/**
 * Created by Bojan on 9/6/2015.
 */
public class TimerExpiredDialogFragment extends BaseDialogFragment {
    public TimerExpiredDialogFragment() {}

    public static TimerExpiredDialogFragment newInstance(BaseDialogFragment.OnPositiveCallback positiveCallback) {
        TimerExpiredDialogFragment frag = new TimerExpiredDialogFragment();
        frag.setPositiveCallback(positiveCallback);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_timer_finished, container);
        getDialog().setTitle(R.string.dialog_timer_finished_title);
        ButterKnife.bind(this, view);
        return view;
    }
}
