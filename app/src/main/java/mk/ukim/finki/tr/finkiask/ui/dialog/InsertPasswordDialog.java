package mk.ukim.finki.tr.finkiask.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.R;

public class InsertPasswordDialog extends BaseDialogFragment {

    public InsertPasswordDialog() {
    }

    @Bind(R.id.password)
    EditText password;

    public static InsertPasswordDialog newInstance(int minutes, BaseDialogFragment.OnPositiveCallback positiveCallback) {
        InsertPasswordDialog frag = new InsertPasswordDialog();
        frag.setPositiveCallback(positiveCallback);
        Bundle args = new Bundle();
        args.putInt("minutes", minutes);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_test_has_password, container);
        String textFormat = getActivity().getResources().getString(R.string.start_test_with_password_text);
        getDialog().setTitle(R.string.test_locked);
        ButterKnife.bind(this, view);
        text.setText(String.format(textFormat, getArguments().getInt("minutes")));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPositiveCallback.onPositiveClick(getPassword());
                dismiss();
            }
        });
    }

    public String getPassword() {
        return password.getText().toString();
    }
}
