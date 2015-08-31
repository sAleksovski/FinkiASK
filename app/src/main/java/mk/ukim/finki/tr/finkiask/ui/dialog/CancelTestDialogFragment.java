package mk.ukim.finki.tr.finkiask.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;

public class CancelTestDialogFragment extends BaseDialogFragment {

    public CancelTestDialogFragment() {}

    public static CancelTestDialogFragment newInstance(BaseDialogFragment.OnPositiveCallback positiveCallback) {
        CancelTestDialogFragment frag = new CancelTestDialogFragment();
        frag.setPositiveCallback(positiveCallback);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_cancel_test, container);
        getDialog().setTitle(R.string.test_cancel_title);
        ButterKnife.bind(this, view);
        return view;
    }

}
