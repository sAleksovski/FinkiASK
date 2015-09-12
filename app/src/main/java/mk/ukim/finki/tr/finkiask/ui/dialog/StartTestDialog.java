package mk.ukim.finki.tr.finkiask.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;

public class StartTestDialog extends BaseDialogFragment {

    public StartTestDialog() {
    }

    public static StartTestDialog newInstance(int minutes, BaseDialogFragment.OnPositiveCallback positiveCallback) {
        StartTestDialog frag = new StartTestDialog();
        frag.setPositiveCallback(positiveCallback);
        Bundle args = new Bundle();
        args.putInt("minutes", minutes);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_start_test, container);
        String textFormat = getActivity().getResources().getString(R.string.start_test_text);
        getDialog().setTitle(R.string.start_test_title);
        ButterKnife.bind(this, view);
        text.setText(String.format(textFormat, getArguments().getInt("minutes")));
        return view;
    }
}
