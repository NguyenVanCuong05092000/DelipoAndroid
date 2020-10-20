package jp.adapter.delipo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import jp.adapter.delipo.R;
import jp.adapter.delipo.data.PrefManager;

public class DialogTutorial extends Dialog {

    private TextView tvTitle;
    private TextView tvMessage;
    private String keyPref;
    private CheckBox checkBox;

    public DialogTutorial(Context context) {
        super(context, R.style.dialogWithoutBox);
        setContentView(R.layout.dialog_tutorial);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        View vContainer = findViewById(R.id.rlContent);
        vContainer.getLayoutParams().width = displayMetrics.widthPixels;
        vContainer.getLayoutParams().height = displayMetrics.heightPixels;
        vContainer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tvTitle = findViewById(R.id.tvTitle);
        tvMessage = findViewById(R.id.tvMessage);
        checkBox = findViewById(R.id.cbDontAskAgain);
        findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    if (!TextUtils.isEmpty(keyPref) && checkBox.isChecked())
                        PrefManager.getInstance(getContext()).writeBoolean(keyPref, true);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                dismiss();
            }
        });
    }

    public void show(int title, boolean titleHighLight, int message, String keyPref, OnDismissListener listener) {
        try {
            setOnDismissListener(listener);
            this.keyPref = keyPref;
            checkBox.setVisibility(View.VISIBLE);
            if (title == 0)
                tvTitle.setVisibility(View.GONE);
            else {
                tvTitle.setText(title);
                tvTitle.setSelected(titleHighLight);
                tvTitle.setVisibility(View.VISIBLE);
            }
            tvMessage.setText(message);
            checkBox.setChecked(false);

            show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(CharSequence title, boolean titleHighLight, CharSequence message, String keyPref, OnDismissListener listener) {
        try {
            setOnDismissListener(listener);
            this.keyPref = keyPref;
            checkBox.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(title))
                tvTitle.setVisibility(View.GONE);
            else {
                tvTitle.setText(title);
                tvTitle.setSelected(titleHighLight);
                tvTitle.setVisibility(View.VISIBLE);
            }
            tvMessage.setText(message);
            checkBox.setChecked(false);

            show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showWithoutCheckbox(int title, boolean titleHighLight, int message, OnDismissListener listener) {
        try {
            setOnDismissListener(listener);
            this.keyPref = null;
            checkBox.setVisibility(View.GONE);
            if (title == 0)
                tvTitle.setVisibility(View.GONE);
            else {
                tvTitle.setText(title);
                tvTitle.setSelected(titleHighLight);
                tvTitle.setVisibility(View.VISIBLE);
            }
            tvMessage.setText(message);
            checkBox.setChecked(false);

            show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showWithoutCheckbox(CharSequence title, boolean titleHighLight, CharSequence message, OnDismissListener listener) {
        try {
            setOnDismissListener(listener);
            this.keyPref = null;
            checkBox.setVisibility(View.GONE);
            if (TextUtils.isEmpty(title))
                tvTitle.setVisibility(View.GONE);
            else {
                tvTitle.setText(title);
                tvTitle.setSelected(titleHighLight);
                tvTitle.setVisibility(View.VISIBLE);
            }
            tvMessage.setText(message);
            checkBox.setChecked(false);

            show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
