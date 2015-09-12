package mk.ukim.finki.tr.finkiask.ui.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Bind;
import mk.ukim.finki.tr.finkiask.R;

public class BaseDialogFragment extends DialogFragment {

    @Bind(R.id.text) TextView text;
    @Bind(R.id.positive) Button btnPositive;
    @Bind(R.id.negative) Button btnNegative;

    static OnPositiveCallback mPositiveCallback;

    public BaseDialogFragment() {}

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
                mPositiveCallback.onPositiveClick("");
                dismiss();
            }
        });
    }

    public void setPositiveCallback(OnPositiveCallback callback) {
        mPositiveCallback = callback;
    }

    public interface OnPositiveCallback {
        void onPositiveClick(String data);
    }
}
