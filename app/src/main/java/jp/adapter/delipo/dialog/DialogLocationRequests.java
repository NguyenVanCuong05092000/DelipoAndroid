package jp.adapter.delipo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import jp.adapter.delipo.R;

public class DialogLocationRequests extends Dialog implements View.OnClickListener {

    private boolean isCancelAble;

    public DialogLocationRequests(Context context) {
        super(context, R.style.dialogWithoutBox);
        setContentView(R.layout.dialog_location_requests);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        View vContainer = findViewById(R.id.relative_dialog_request_permission);
        vContainer.getLayoutParams().width = displayMetrics.widthPixels;
        vContainer.getLayoutParams().height = displayMetrics.heightPixels;

        vContainer.setOnClickListener(this);
        findViewById(R.id.btnWhileUsingApp).setOnClickListener(this);
        findViewById(R.id.btnOnlyOnce).setOnClickListener(this);
        findViewById(R.id.btnNotAllowed).setOnClickListener(this);
    }

    public void show(int header, int messageTop, int messageBottom, boolean cancelAble, OnDismissListener dismissListener) {
        try {
            this.isCancelAble = cancelAble;
            setCancelable(cancelAble);
            setOnDismissListener(dismissListener);
            ((TextView) findViewById(R.id.tvHeader)).setText(header);
            ((TextView) findViewById(R.id.tvMessageTop)).setText(messageTop);
            ((TextView) findViewById(R.id.tvMessageBottom)).setText(messageBottom);
            show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(CharSequence header, CharSequence messageTop, CharSequence messageBottom, boolean cancelAble, OnDismissListener dismissListener) {
        try {
            this.isCancelAble = cancelAble;
            setCancelable(cancelAble);
            setOnDismissListener(dismissListener);
            ((TextView) findViewById(R.id.tvHeader)).setText(header);
            ((TextView) findViewById(R.id.tvMessageTop)).setText(messageTop);
            ((TextView) findViewById(R.id.tvMessageBottom)).setText(messageBottom);
            show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlContent:
                if (isCancelAble)
                    dismiss();
                break;
            case R.id.btnWhileUsingApp:
                dismiss();
                break;
            case R.id.btnOnlyOnce:
                dismiss();
                break;
            case R.id.btnNotAllowed:
                dismiss();
                break;
        }
    }
}
